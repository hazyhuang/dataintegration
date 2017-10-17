package com.hazy.thread.action;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.ItemConstants;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.ITitle;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;
import com.hazy.thread.TitleConstants;

public class ValidAddUploadFileAction implements IAction {
	private static Logger logger = Logger.getLogger(ValidAddUploadFileAction.class);
	private IAgileSession session;
	private IDocumentDTO doc;

	public ValidAddUploadFileAction(IDocumentDTO doc) {
		this.doc = doc;
	}

	public IDocumentDTO getDocumentDTO() {
		return doc;
	}

	@Override
	public Integer doAction() {
		//根据Excel中的ID加载文件夹里文件列表
		//用此文件列表与Agile系统中的文件列表对比
		//如果有新增的文件。。。将此文件列出，预备上传
		IItem item = null;
		ITitle title = doc.getTitle();
		StringBuffer resultStr = new StringBuffer();
		try {
			if (doc.getNumber() != null && !"".equals(doc.getNumber())) {
				session = ActionUtil.getAgileSession(doc.getmFrame().getAgileUser());
				item = (IItem) HazyUtil.getAgileAPIHelper().loadItem(session, doc.getNumber());
				ITable table=item.getAttachments();
				@SuppressWarnings("rawtypes")
				Iterator iter = table.iterator();
				Map<String,String> attachments=new HashMap<String,String>();
				while(iter.hasNext()){
					IRow row=(IRow)iter.next();
					String filename=row.getValue(ItemConstants.ATT_ATTACHMENTS_FILE_NAME).toString();
					attachments.put(filename, filename);
					
				}
				if (doc.getFiles() != null) {
					File[] files=doc.getFiles();
					if(files.length==0){
						resultStr.append("文件夹为空");
					}
					for(int i=0;i<files.length;i++){
						String filename=files[i].getName();
						String value=attachments.get(filename);
						if(value==null){//不存在
							System.out.println(filename+"没有上传");
							resultStr.append(filename+"没有上传");
						}
					}
		
				}else{
					resultStr.append("文件夹不存在");
				}
				
			} 
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[seq]" + doc.getNumber() + "[Message]" + e.getMessage());
			doc.setStatus(FAILURE);
			doc.getDataObject().setValue(title.getIndex(TitleConstants.ValidAddFileStatus), "FAILURE");
			return FAILURE;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		
		if (resultStr.length() == 0) {
			doc.setStatus(SUCCESS);
			doc.getDataObject().setValue(title.getIndex(TitleConstants.ValidAddFileStatus), "SUCCESS");// Validation结果
		} else {
			doc.setStatus(FAILURE);
			doc.getDataObject().setValue(title.getIndex(TitleConstants.ValidAddFileStatus), resultStr.toString());// Validation结果
		}
		return doc.getStatus();

	}

}
