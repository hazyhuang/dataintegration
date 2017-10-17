package com.hazy.thread.action;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.IItem;
import com.agile.api.ItemConstants;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.ITitle;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;
import com.hazy.thread.TitleConstants;

public class CreateDocumentAction implements IAction{
	private static Logger logger = Logger.getLogger(CreateDocumentAction.class);
	IAgileSession session;
	IDocumentDTO doc;
	public CreateDocumentAction(IDocumentDTO doc){
		this.doc=doc;
	}
	public IDocumentDTO getDocumentDTO(){
		return doc;
	}
	@Override
	public Integer doAction() {
		String type=null;//文档类型
		
		String subType=null;//文档小类
		String desc=null;//文档描述
		IItem item=null;
		ITitle title=doc.getTitle();
		String agileNumber;
		String valid =null;
		String comment=null;
		try {
			agileNumber=(String)doc.getDataObject().getValue(title.getIndex(TitleConstants.ActionFactory_AgileNumber));//文档类型
			System.out.println(agileNumber);
			if("".equals(agileNumber)){
			session = ActionUtil.getAgileSession(doc.getmFrame().getAgileUser());
			
			type=(String)doc.getDataObject().getValue(title.getIndex(TitleConstants.DocumentClass));//文档类型
			item=HazyUtil.getAgileAPIHelper().createItem(session, type);
			subType=(String)doc.getDataObject().getValue(title.getIndex(TitleConstants.DocumentSubType));//文档小类
			desc=(String)doc.getDataObject().getValue(title.getIndex(TitleConstants.OADescription));//文档描述
			valid=(String)doc.getDataObject().getValue(title.getIndex(TitleConstants.ValidationStatus));//Valid结果
			String history=(String)doc.getDataObject().getValue(title.getIndex(TitleConstants.History));//审批记录
			comment=(String)doc.getDataObject().getValue(title.getIndex(TitleConstants.Comment));//审批记录
			
			if(!"[FAILURE]小类不存在".equals(valid)){
			if(!"无".equals(subType)&&!"".equals(subType)){//设置小类
			     HazyUtil.getAgileAPIHelper().setListValue(item, ItemConstants.ATT_PAGE_THREE_LIST01, subType);
			}
			}
	        item.setValue(ItemConstants.ATT_TITLE_BLOCK_DESCRIPTION, desc);
	        if(history.length()>2000){
	        	
	        	item.setValue(ItemConstants.ATT_PAGE_TWO_MULTITEXT31, history.substring(0, 1900));
	        }else{
	        item.setValue(ItemConstants.ATT_PAGE_TWO_MULTITEXT31, history);
	        }
	        item.setValue(ItemConstants.ATT_PAGE_TWO_NOTES, comment);
			doc.setStatus(SUCCESS);
	        doc.getDataObject().setValue(title.getIndex(TitleConstants.ActionFactory_AgileNumber), item.getName());//Agile编号
			doc.getDataObject().setValue(title.getIndex(TitleConstants.CreateItemStatus), "SUCCESS");//创建Agile对象结果
			return SUCCESS;
			}else{
				doc.setStatus(SUCCESS);
				return SUCCESS;
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[type]"+type+"[SubType]"+subType+"[desc]"+desc);
			logger.error("[seq]"+doc.getNumber()+"[Message]"+e.getMessage());
			if(item!=null){
				try {
					doc.setNumber(item.getName());
					item.delete();
				} catch (APIException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
			doc.setStatus(FAILURE);
			doc.getDataObject().setValue(title.getIndex(TitleConstants.CreateItemStatus), "FAILURE"+"[seq]"+doc.getNumber()+"[Message]"+e.getMessage());//创建Agile对象结果
			return FAILURE;
		}finally{
			if(session!=null){
			session.close();
			}
		}
	
	}
}
