package com.hazy.thread.action;

import org.apache.log4j.Logger;

import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.ITitle;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;
import com.hazy.thread.TitleConstants;

public class UploadFileAction implements IAction {
	private static Logger logger = Logger.getLogger(UploadFileAction.class);
	IAgileSession session;
	IDocumentDTO doc;

	public UploadFileAction(IDocumentDTO doc) {
		this.doc = doc;
	}

	public IDocumentDTO getDocumentDTO() {
		return doc;
	}

	@Override
	public Integer doAction() {
		IItem item = null;
		ITitle title = doc.getTitle();
		try {
			if (doc.getNumber() != null && !"".equals(doc.getNumber())) {
				session = ActionUtil.getAgileSession(doc.getmFrame().getAgileUser());
				item = (IItem) HazyUtil.getAgileAPIHelper().loadItem(session, doc.getNumber());
				HazyUtil.getAgileAPIHelper().uploadFile(session, item, doc.getFiles());
				if (doc.getFiles() != null) {
					int i = doc.getFiles().length;
					doc.getDataObject().setValue(title.getIndex(TitleConstants.FilePath), "SUCCESS" + i);
					doc.setStatus(SUCCESS);
					doc.getDataObject().setValue(title.getIndex(TitleConstants.FileLoadStatus), "SUCCESS");
					return SUCCESS;
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
