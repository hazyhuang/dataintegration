package com.hazy.di.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.openxml4j.opc.PackageAccess;

import com.hazy.di.model.SheetFactory;
import com.hazy.di.model.IColumnProperty;
import com.hazy.di.model.IRowObject;
import com.hazy.di.model.ISheet;
import com.hazy.di.model.ITitle;

public class XlsxDAO implements IXlsxDAO {

	private final static Integer TITLE_ROW_INDEX = 0;
	private String filepath = null;
	private String outputfilepath = null;

	public XlsxDAO(String filepath) {
		this.filepath = filepath;
	}

	public void setOutputfilepath(String outputfilepath) {
		this.outputfilepath = outputfilepath;
	}

	public ISheet loadHazyObject() throws IOException {
		Workbook wb = null;
		ITitle sheetTitle = null;
		ArrayList<IRowObject> content = new ArrayList<IRowObject>();
		try {
			wb = new XSSFWorkbook(new FileInputStream(filepath));
			int i = 0;
			Sheet sheet = wb.getSheetAt(i);
			sheetTitle = this.loadTitle(sheet);
			for (Row row : sheet) {
				if (row.getRowNum() != 0) {
					IRowObject ho = createHazyObject(row, sheetTitle);
					content.add(ho);
				}
			}
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		ISheet sheet = SheetFactory.getInstance().createSheet(sheetTitle, content);
		return sheet;
	}

	public void createHazyObject(ISheet disheet) throws IOException {
		Workbook wb = null;
		ITitle dititle = disheet.getTitle();
		ArrayList<IColumnProperty> columns = dititle.getProperties();
		ArrayList<IRowObject> content = disheet.getContent();
		try {
			wb = new XSSFWorkbook();

			Sheet sheet = wb.createSheet("output");
			this.createTitle(sheet, columns);
			int i = 1;
			for (IRowObject hzObj : content) {
				Row row = sheet.createRow(i);
				for (IColumnProperty column : columns) {
					int index = column.getIndex();
					Cell cell = row.createCell(index);
					Object value=hzObj.getValue(index);
					if(value instanceof Integer){
					cell.setCellValue(((Integer)value).doubleValue() );
					}else if(value instanceof String){
						cell.setCellValue((String)value );
					}else if(value instanceof Date){
						cell.setCellValue((Date)value);
					}else{
						cell.setCellValue((String)value );
					}
					
				}
				i++;
			}

			FileOutputStream outputStream = new FileOutputStream(outputfilepath);
			wb.write(outputStream);
			outputStream.flush();
			outputStream.close();
		} finally {
			if (wb != null) {
				try {
					wb.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

	private void createTitle(Sheet sheet, ArrayList<IColumnProperty> columns) {
		Row row = sheet.createRow(TITLE_ROW_INDEX);
		for (IColumnProperty column : columns) {
			String key = column.getValue();
			int index = column.getIndex();

			Cell cell = row.createCell(index);
			cell.setCellValue(key);
		}

	}

	private IRowObject createHazyObject(Row row, ITitle sheetTitle) {
		IRowObject hObj = SheetFactory.getInstance().createRow();
		ArrayList<IColumnProperty> list = sheetTitle.getProperties();
		for (IColumnProperty prop : list) {
			int index = prop.getIndex();
			Cell cell = row.getCell(index);
			String str = null;
			if (cell != null) {
				str = cell.toString();
				hObj.setValue(index, str);
			}else{
				hObj.setValue(index, "");
			}

		}
		return hObj;

	}

	private ITitle loadTitle(Sheet sheet) throws FileNotFoundException {
		ITitle sheetTitle = SheetFactory.getInstance().createTitle();
		Row row = sheet.getRow(TITLE_ROW_INDEX);
		// 标题总列数
		int colNum = row.getPhysicalNumberOfCells();
		String[] title = new String[colNum];
		for (int i = 0; i < colNum; i++) {
			title[i] = row.getCell(i).toString();
			IColumnProperty column = SheetFactory.getInstance().createColumnProperty(i, title[i]);
			sheetTitle.add(column);
		}
		return sheetTitle;

	}

	public void process() throws FileNotFoundException, IOException {
		Workbook wb = new XSSFWorkbook(new FileInputStream(filepath));
		for (int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			System.out.println(wb.getSheetName(i));
			for (Row row : sheet) {
				System.out.println("rownum: " + row.getRowNum());
				for (Cell cell : row) {
					System.out.println(cell);
				}
			}
		}
		wb.close();
	}

	public void processFirstSheet() throws Exception {
		OPCPackage pkg = OPCPackage.open(filepath, PackageAccess.READ);
		try {
			XSSFReader r = new XSSFReader(pkg);
			SharedStringsTable sst = r.getSharedStringsTable();

			XMLReader parser = fetchSheetParser(sst);

			// process the first sheet
			InputStream sheet2 = r.getSheetsData().next();
			InputSource sheetSource = new InputSource(sheet2);
			parser.parse(sheetSource);
			sheet2.close();
		} finally {
			pkg.close();
		}
	}

	public XMLReader fetchSheetParser(SharedStringsTable sst) throws SAXException {
		XMLReader parser = XMLReaderFactory.createXMLReader();
		ContentHandler handler = new SheetHandler(sst);
		parser.setContentHandler(handler);
		return parser;
	}

	/**
	 * See org.xml.sax.helpers.DefaultHandler javadocs
	 */
	private static class SheetHandler extends DefaultHandler {
		private final SharedStringsTable sst;
		private String lastContents;
		private boolean nextIsString;
		private boolean inlineStr;
		private final LruCache<Integer, String> lruCache = new LruCache<Integer, String>(50);

		private static class LruCache<A, B> extends LinkedHashMap<A, B> {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			private final int maxEntries;

			public LruCache(final int maxEntries) {
				super(maxEntries + 1, 1.0f, true);
				this.maxEntries = maxEntries;
			}

			@Override
			protected boolean removeEldestEntry(final Map.Entry<A, B> eldest) {
				return super.size() > maxEntries;
			}
		}

		private SheetHandler(SharedStringsTable sst) {
			this.sst = sst;
		}

		@Override
		public void startElement(String uri, String localName, String name, Attributes attributes) throws SAXException {
			// c => cell
			if (name.equals("c")) {
				// Print the cell reference
				System.out.print(attributes.getValue("r") + " - ");
				// Figure out if the value is an index in the SST
				String cellType = attributes.getValue("t");
				nextIsString = cellType != null && cellType.equals("s");
				inlineStr = cellType != null && cellType.equals("inlineStr");
			}
			// Clear contents cache
			lastContents = "";
		}

		@Override
		public void endElement(String uri, String localName, String name) throws SAXException {
			// Process the last contents as required.
			// Do now, as characters() may be called more than once
			if (nextIsString) {
				Integer idx = Integer.valueOf(lastContents);
				lastContents = lruCache.get(idx);
				if (lastContents == null && !lruCache.containsKey(idx)) {
					lastContents = new XSSFRichTextString(sst.getEntryAt(idx)).toString();
					lruCache.put(idx, lastContents);
				}
				nextIsString = false;
			}

			// v => contents of a cell
			// Output after we've seen the string contents
			if (name.equals("v") || (inlineStr && name.equals("c"))) {
				System.out.println(lastContents);
			}
		}

		@Override
		public void characters(char[] ch, int start, int length) throws SAXException { // NOSONAR
			lastContents += new String(ch, start, length);
		}
	}

	public static void main(String[] args) throws Exception {
		// HazyObjectXLSXDAO howto = new HazyObjectXLSXDAO();
		// howto.processFirstSheet(args[0]);

	}
}
