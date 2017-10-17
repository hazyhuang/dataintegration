package com.hazy.di.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.junit.Before;
import org.junit.Test;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.IColumnProperty;
import com.hazy.di.model.IRowObject;
import com.hazy.di.model.ISheet;
import com.hazy.di.model.ITitle;
import com.hazy.di.model.SheetFactory;
import com.hazy.di.poi.IXlsxDAO;

public class ExcelDAOFactoryTest {
	static Logger logger = Logger.getLogger(ExcelDAOFactoryTest.class);
	IAgileSession session;

	@Before
	public void setUp() throws Exception {
		 HazyUtil.getLogHelper().initLogger();
		// session = HazyUtil.getLinuxUtil().getLocalSession();
	}
	
	@Test
	public void testUploadFileNewItem() throws HazyException, IOException {
	    IColumnProperty c1= SheetFactory.getInstance().createColumnProperty(0, "第一列");
	    IColumnProperty c2=   SheetFactory.getInstance().createColumnProperty(1, "第二列");
	    IColumnProperty c3= SheetFactory.getInstance().createColumnProperty(2, "第三列");
	    ITitle title=SheetFactory.getInstance().createTitle();
	    title.add(c1);
	    title.add(c2);
	    title.add(c3);
	    IRowObject row=SheetFactory.getInstance().createRow();
	    row.setValue(0, 1);
	    row.setValue(1, "2");
	    row.setValue(2, "3");
	    IRowObject row2=row.clone();
	    ArrayList<IRowObject> rows=new ArrayList<IRowObject>();
	    rows.add(row);
	    rows.add(row2);
	   ISheet disheet= SheetFactory.getInstance().createSheet(title, rows);
		HazyUtil.getLogHelper().initLogger();
		String fullPath = HazyUtil.getLinuxUtil().getLocalFullPath("conf.properties");
		Properties prop = HazyUtil.getInstance().loadPropertiesUTF8(fullPath);
		String filepath3= prop.getProperty("filepath3");
		String outputfilepath3 = prop.getProperty("outfilepath3");
		IXlsxDAO dao3=ExcelDAOFactory.getInstance().createXlsxDAO(filepath3);
		dao3.createHazyObject(disheet);
	}

	private String action = "create";
	
	public void testupload() throws APIException {

		String path = "C:/huanghua/";
		File file = new File(path);
		File[] files = file.listFiles();
		IItem item = HazyUtil.getAgileAPIHelper().loadItem(session, "0WL.352.002003");
		
		HazyUtil.getAgileAPIHelper().uploadFile(session, item, files);
	}





	// @Test
	public void testAddApproverByItemCreator() {

		try {
			String fullPath = HazyUtil.getLinuxUtil().getLocalFullPath("conf.properties");
			Properties prop = HazyUtil.getInstance().loadPropertiesUTF8(fullPath);
			String filepath1 = prop.getProperty("filepath1");
			String outputfilepath1 = prop.getProperty("outfilepath1");
			IXlsxDAO dao1=ExcelDAOFactory.getInstance().createXlsxDAO(filepath1);
			
			ISheet sheet = dao1.loadHazyObject();
			ITitle title = sheet.getTitle();
			ArrayList<IColumnProperty> columns = title.getProperties();
			ArrayList<IRowObject> listContent = sheet.getContent();

			for (IRowObject hObj : listContent) {

				for (IColumnProperty column : columns) {
					String key = column.getValue();
					int index = column.getIndex();
					String value = (String) hObj.getValue(index);
					System.out.print("[index]" + index + "[key]" + key + " [value]" + value);
				}
				System.out.println();
				String filekey = (String) hObj.getValue(0);
				String flag = (String) hObj.getValue(2);
				System.out.println("[filekey]" + filekey + "[flag]" + flag);
				String path = "E:/huanghua/OAfiles/tmpF/" + filekey + "/";
				File file = new File(path);
				File[] files = file.listFiles();
				if ("".equals(flag) || flag == null) {
					if (files != null) {
						for (int i = 0; i < files.length; i++) {
							System.out.println(files[i].getName());
						}
						if (action.equals("create")) {
							IItem item = (IItem) HazyUtil.getAgileAPIHelper().createObject(session,
									"RM_GeneralDocument");
							hObj.setValue(2, item.getName());
						} else if (action.equals("upload")) {

						}
					}
				}
			}
			dao1.createHazyObject(sheet);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	public static void main(String arg[]){
	String fullpath = HazyUtil.getLinuxUtil().getLocalFullPath("log.xml");
	System.out.println(fullpath);
	HazyUtil.getLogHelper().initXMLLogger(fullpath);
	//ThreadExecutor exec = new ThreadExecutor();
	//ArrayList<IAction> list = HazyService.getInstance().getUploadNewItemActions();
	///exec.exec(list);
	//exec.shutdown();
	}
}
