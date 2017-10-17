package com.hazy.thread.action;

import org.apache.log4j.Logger;

import com.agile.api.IAgileSession;
import com.hazy.di.model.ITitle;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;
import com.hazy.thread.TitleConstants;

public class ValidationUploadFileAction implements IAction {
	private static Logger logger = Logger.getLogger(ValidationUploadFileAction.class);
	IAgileSession session;
	IDocumentDTO doc;

	public ValidationUploadFileAction(IDocumentDTO doc) {
		this.doc = doc;
	}

	public IDocumentDTO getDocumentDTO() {
		return doc;
	}

	@Override
	public Integer doAction() {
		
		ITitle title = doc.getTitle();
		try {
			if (doc.getNumber() != null && !"".equals(doc.getNumber())) {

				if (doc.getFiles() != null) {
					int i = doc.getFiles().length;
					if(i>0){
					doc.getDataObject().setValue(title.getIndex(TitleConstants.FilePath), "SUCCESS" + i);
					doc.setStatus(SUCCESS);
					doc.getDataObject().setValue(title.getIndex(TitleConstants.FileLoadStatus), "SUCCESS");
					return SUCCESS;}else{
						doc.getDataObject().setValue(title.getIndex(TitleConstants.FilePath), "FAILURE文件夹为空");
						doc.setStatus(FAILURE);
						doc.getDataObject().setValue(title.getIndex(TitleConstants.FileLoadStatus), "FAILURE");
						return FAILURE;
					}
				} else {
					doc.getDataObject().setValue(title.getIndex(TitleConstants.FilePath), "FAILURE文件夹不存在" );
					doc.setStatus(FAILURE);
					doc.getDataObject().setValue(title.getIndex(TitleConstants.FileLoadStatus), "FAILURE");
					return FAILURE;
				}
				
			} else {
				doc.setStatus(FAILURE);
				doc.getDataObject().setValue(title.getIndex(TitleConstants.FileLoadStatus), "FAILURE编号为空");
				return FAILURE;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[seq]" + doc.getNumber() + "[Message]" + e.getMessage());
			doc.setStatus(FAILURE);
			doc.getDataObject().setValue(title.getIndex(TitleConstants.FileLoadStatus), "FAILURE");
			return FAILURE;
		} finally {
			if (session != null) {
				session.close();
			}
		}

	}

}
