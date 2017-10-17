package com.hazy.thread;

import java.io.File;
import java.util.Map;
import java.util.Observable;

import com.hazy.di.model.IRowObject;
import com.hazy.di.model.ITitle;
import com.hazy.di.ui.IMainFrame;

public class DocumentDTO extends Observable implements IDocumentDTO {

	private String number;
	private int status = IAction.INITIAL;// 0: success 1: failure
	private File files[];
	private IRowObject dataObject;
	private ITitle title;
	private Map<Object,Object> context;
	private IMainFrame mFrame;

	public IMainFrame getmFrame() {
		return mFrame;
	}

	public void setmFrame(IMainFrame mFrame) {
		this.mFrame = mFrame;
	}

	public DocumentDTO(String number, File[] files) {
		super();
		this.number = number;
		this.files = files;
	}
	
	public Map<Object,Object> getContext(){
		return context;
	}

	public DocumentDTO(Map<Object,Object> map,ITitle title,String number, File[] files, IRowObject dataObject) {
		super();
		this.context=map;
		this.title=title;
		this.number = number;
		this.files = files;
		this.dataObject = dataObject;
	}

	public IRowObject getDataObject() {
		return dataObject;
	}

	public void setDataObject(IRowObject dataObject) {
		this.dataObject = dataObject;
	}
	public ITitle getTitle() {
		return this.title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
		setChanged();
		this.notifyObservers();
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}
}
