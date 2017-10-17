package com.hazy.thread;

import java.util.ArrayList;

import com.hazy.common.HazyException;
import com.hazy.di.model.ISheet;
import com.hazy.di.ui.IMainFrame;
import com.hazy.thread.action.AddUploadFileAction;
import com.hazy.thread.action.CreateDocumentAction;
import com.hazy.thread.action.ReleaseDocumentAction;
import com.hazy.thread.action.UploadFileAction;
import com.hazy.thread.action.UploadFileNewItemAction;
import com.hazy.thread.action.ValidAddUploadFileAction;
import com.hazy.thread.action.ValidationAction;
import com.hazy.thread.action.ValidationUploadFileAction;

public abstract class AbstractActionFactory {
	
	private static AbstractActionFactory instance=new ActionFactory();
	
	public static AbstractActionFactory getInstance(){
	
		return instance;
	}
	public abstract ArrayList<IAction> create(IMainFrame mFrame,ISheet sheet,Integer type) throws HazyException;
	public abstract ArrayList<IAction> createTestActions(Integer type);
	public IAction create(Integer type,IDocumentDTO doc){
		if(IAction.ACTION_RELEASE.equals(type)){
			return new ReleaseDocumentAction(doc);
		}else if(IAction.ACTION_UPLOAD.equals(type)){
			return new UploadFileAction(doc);
		}else if(IAction.ACTION_CREATE.equals(type)){
			return new CreateDocumentAction(doc);
		}else if(IAction.ACTION_TEST.equals(type)){
			return new UploadFileNewItemAction(doc);
		}else if(IAction.ACTION_VALIDATION.equals(type)){
			return new ValidationAction(doc);
		}else if(IAction.ACTION_VALIDATION_UPLOAD.equals(type)){
			return new ValidationUploadFileAction(doc);
		}else if(IAction.ACTION_VALIDATION_ADD_UPLOAD.equals(type)){
			return new ValidAddUploadFileAction(doc);
		}else if(IAction.ACTION_ADD_UPLOAD.equals(type)){
			return new AddUploadFileAction(doc);
		}
		return null;
	}

}
