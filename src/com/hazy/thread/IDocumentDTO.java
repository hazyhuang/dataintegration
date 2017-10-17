package com.hazy.thread;

import java.io.File;
import java.util.Map;

import com.hazy.di.model.IRowObject;
import com.hazy.di.model.ITitle;
import com.hazy.di.ui.IMainFrame;

public interface IDocumentDTO  {

	public ITitle getTitle();
	public IRowObject getDataObject();
	public int getStatus();
	public void setStatus(int status);
	public String getNumber();
	public void setNumber(String number);
	public File[] getFiles();
	public Map<Object,Object> getContext();
	
	public IMainFrame getmFrame();

	public void setmFrame(IMainFrame mFrame);

}
