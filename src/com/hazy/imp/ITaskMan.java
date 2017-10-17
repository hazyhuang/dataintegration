package com.hazy.imp;

import java.io.IOException;

import com.hazy.common.HazyException;
import com.hazy.di.ui.IMainFrame;

public interface ITaskMan extends INode{
	
    public void setType(Integer type);
	public void setInputfilepath(String inputfilepath);
	public void setOutputfilepath(String outputfilepath);
	public void loadActions() throws IOException, HazyException;
	public void shutdown();
	public void execute();
	public void execute(int start,int end);
	public void createSheet() throws IOException;
	public void createErrorSheet() throws IOException;
	public String getInputfilepath();
	public String getOutputfilepath();
	
	public void setmFrame(IMainFrame mFrame);
}
