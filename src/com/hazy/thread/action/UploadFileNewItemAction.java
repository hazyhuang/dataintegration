package com.hazy.thread.action;

import org.apache.log4j.Logger;

import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.hazy.common.HazyUtil;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;

public class UploadFileNewItemAction implements IAction{
	
	private static Logger logger = Logger.getLogger(UploadFileNewItemAction.class);

	private IDocumentDTO doc;
	
	public UploadFileNewItemAction(IDocumentDTO doc){
		this.doc=doc;
	}
	
	public IDocumentDTO getDocumentDTO(){
		return doc;
	}
	
	@Override
	public Integer doAction() {
		IAgileSession	session=null;
		try {
			logger.debug("[session]");
			session = HazyUtil.getLinuxUtil().getLocalSession();
		
			logger.debug("[session]"+session);
			IItem item = (IItem) HazyUtil.getAgileAPIHelper().createObject(session, "PRD");
			logger.debug("[item]"+item);
			
	        HazyUtil.getAgileAPIHelper().uploadFile(session, item, doc.getFiles());
	        doc.setStatus(SUCCESS);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[seq]"+"[Message]"+e.getMessage());
			doc.setStatus(FAILURE);
			return FAILURE;
		}finally{
			if(session!=null){
			session.close();
			}
		}
		return SUCCESS;
	}
	

	public static void main(String args[]) {
		HazyUtil.getLogHelper().initLogger();
		UploadFileNewItemAction action = new UploadFileNewItemAction(null);
		action.doAction();
	

	}

}
