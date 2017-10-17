package com.hazy.di.poi;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import com.hazy.di.model.ColumnProperty;
import com.hazy.di.model.RowObject;
import com.hazy.di.model.SheetFactory;
import com.hazy.di.model.IColumnProperty;
import com.hazy.di.model.IRowObject;
import com.hazy.di.model.SheetObject;
import com.hazy.di.model.Title;


public class XLSDAO {

    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;
    private String filepath=null;
    private Title sheetTitle=new Title();
    private InputStream is=null;
    public XLSDAO(String filepath){
    	this.filepath=filepath;
    }
    
    private  void loadTitle() throws FileNotFoundException{
    	this.is= new 
				FileInputStream(filepath);
    	  try {
              fs = new POIFSFileSystem(is);
              wb = new HSSFWorkbook(fs);
          } catch (IOException e) {
              e.printStackTrace();
          }
          sheet = wb.getSheetAt(0);
          row = sheet.getRow(0);
          // 标题总列数
          int colNum = row.getPhysicalNumberOfCells();
          System.out.println("colNum:" + colNum);
          String[] title = new String[colNum];
          for (int i = 0; i < colNum; i++) {
              //title[i] = getStringCellValue(row.getCell((short) i));
              title[i] = getCellFormatValue(row.getCell( i));
              IColumnProperty column=SheetFactory.getInstance().createColumnProperty(i, title[i]);
            		 // new ColumnProperty(i,title[i]);
              sheetTitle.add(column);
          }
          
    }

    /**
     * 读取Excel表格表头的内容
     * @param InputStream
     * @return String 表头内容的数组
     * @throws FileNotFoundException 
     */
    public SheetObject loadHazyObject() throws FileNotFoundException {
    	this.loadTitle();
    	ArrayList<IRowObject> content = new ArrayList<IRowObject>();
    	  
        try {
            fs = new POIFSFileSystem(this.is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 2; i <= rowNum; i++) {
            row = sheet.getRow(i);
            IRowObject rts=createHazyObject(row,sheetTitle);
           content.add(rts);
          
        }
        SheetObject sheet=new SheetObject(this.sheetTitle,content);
        return sheet;
    }
    IRowObject createHazyObject(HSSFRow row,Title sheetTitle){
    	IRowObject hObj=new RowObject();
    	ArrayList<IColumnProperty> list=sheetTitle.getProperties();
    	for(IColumnProperty prop:list){
    		int index=prop.getIndex();
    		String key=prop.getValue();
    		String str=getCellFormatValue(row.getCell(index)).trim();
    		hObj.setValue(key,str);
    	
    	}
    	return hObj;
    	
    }
    public Map<String,String> readExcelPartType(InputStream is) {
    	Map<String,String> content = new HashMap<String,String>();
  
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(0);
        int colNum = row.getPhysicalNumberOfCells();
        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 1; i <= rowNum; i++) {
            row = sheet.getRow(i);
           
            	String partType=getCellFormatValue(row.getCell( 0)).trim();
            	String valid=getCellFormatValue(row.getCell( 1)).trim();

           content.put(partType, valid);
         // System.out.println(partType+":"+valid);
        }
        return content;
    }
 
/**
     * 获取单元格数据内容为字符串类型的数据
     * 
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellType()) {
        case HSSFCell.CELL_TYPE_STRING:
            strCell = cell.getStringCellValue();
            break;
        case HSSFCell.CELL_TYPE_NUMERIC:
            strCell = String.valueOf(cell.getNumericCellValue());
            break;
        case HSSFCell.CELL_TYPE_BOOLEAN:
            strCell = String.valueOf(cell.getBooleanCellValue());
            break;
        case HSSFCell.CELL_TYPE_BLANK:
            strCell = "";
            break;
        default:
            strCell = "";
            break;
        }
        if (strCell.equals("") || strCell == null) {
            return "";
        }
   
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     * 
     * @param cell
     *            Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            int cellType = cell.getCellType();
            if (cellType == HSSFCell.CELL_TYPE_NUMERIC) {
                Date date = cell.getDateCellValue();
                result = (date.getYear()+ 1900) + "-" + (date.getMonth() + 1)
                        + "-" + date.getDate();
            } else if (cellType == HSSFCell.CELL_TYPE_STRING) {
                String date = getStringCellValue(cell);
                result = date.replaceAll("[年月]", "-").replace("日", "").trim();
            } else if (cellType == HSSFCell.CELL_TYPE_BLANK) {
                result = "";
            }
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     * @param cell
     * @return
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellType()) {
            // 如果当前Cell的Type为NUMERIC
            case HSSFCell.CELL_TYPE_NUMERIC:
            case HSSFCell.CELL_TYPE_FORMULA: {
                // 判断当前的cell是否为Date
                if (HSSFDateUtil.isCellDateFormatted(cell)) {
                    // 如果是Date类型则，转化为Data格式
                    
                    //方法1：这样子的data格式是带时分秒的：2011-10-12 0:00:00
                    //cellvalue = cell.getDateCellValue().toLocaleString();
                    
                    //方法2：这样子的data格式是不带带时分秒的：2011-10-12
                    Date date = cell.getDateCellValue();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    cellvalue = sdf.format(date);
                    
                }
                // 如果是纯数字
                else {
                    // 取得当前Cell的数值
                    cellvalue = String.valueOf(cell.getNumericCellValue());
                }
                break;
            }
            // 如果当前Cell的Type为STRIN
            case HSSFCell.CELL_TYPE_STRING:
                // 取得当前的Cell字符串
                cellvalue = cell.getRichStringCellValue().getString();
                break;
            // 默认的Cell值
            default:
                cellvalue = " ";
            }
        } else {
            cellvalue = "";
        }
        return cellvalue;

    }

   
}
