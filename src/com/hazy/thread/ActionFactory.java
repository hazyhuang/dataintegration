package com.hazy.thread;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.IRowObject;
import com.hazy.di.model.ISheet;
import com.hazy.di.model.ITitle;
import com.hazy.di.ui.IMainFrame;

public class ActionFactory extends AbstractActionFactory {

	public ArrayList<IAction> create(IMainFrame mFrame, ISheet sheet, Integer actionType) throws HazyException {

		String fullPath = HazyUtil.getLinuxUtil().getLocalFullPath("conf.properties");
		String OAFilePath = "";

		Properties prop = HazyUtil.getInstance().loadPropertiesUTF8(fullPath);
		OAFilePath = prop.getProperty("OAFilePath");

		Map<Object, Object> context = new HashMap<Object, Object>();
		ArrayList<IAction> list = new ArrayList<IAction>();
		try {
			ITitle title = sheet.getTitle();
			ArrayList<IRowObject> listContent = sheet.getContent();
			for (IRowObject hObj : listContent) {

				String filekey = (String) hObj.getValue(title.getIndex(TitleConstants.ActionFactory_ID));// 文件ID
				String number = (String) hObj.getValue(title.getIndex(TitleConstants.ActionFactory_AgileNumber));// 文件ID
				String path = OAFilePath + filekey + "/";
				File file = new File(path);
				File[] files = file.listFiles();
				IDocumentDTO doc = new DocumentDTO(context, sheet.getTitle(), number, files, hObj);
				doc.setmFrame(mFrame);
				list.add(super.create(actionType, doc));

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	public ArrayList<IAction> createTestActions(Integer type) {
		ArrayList<IAction> list = new ArrayList<IAction>();
		try {
			for (int i = 0; i < 2; i++) {
				String path = "d:/test/";
				File file = new File(path);
				File[] files = file.listFiles();
				if (files != null) {
					IDocumentDTO doc = new DocumentDTO("", files);
					list.add(super.create(type, doc));

				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

}
