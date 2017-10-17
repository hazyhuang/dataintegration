package com.hazy.imp;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hazy.di.ui.IMainFrame;
import com.hazy.thread.IAction;

public class TestTaskMan implements ITaskMan {
	
	private Integer type;
	static Logger logger = Logger.getLogger(TestTaskMan.class);
	private String name = null;
	private ArrayList<IAction> actions = new ArrayList<IAction>();
	String inputfilepath;
	String outputfilepath;
	
	private IMainFrame mFrame;

	public TestTaskMan() {

	}
	
	public IMainFrame getmFrame() {
		return mFrame;
	}

	public void setmFrame(IMainFrame mFrame) {
		this.mFrame = mFrame;
	}
	public String getName() {
		return this.name ;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void setType(Integer type) {
		this.type = type;
	}
	public String getInputfilepath() {
		return inputfilepath;
	}

	public String getOutputfilepath() {
		return outputfilepath;
	}
	public void setInputfilepath(String inputfilepath) {
		this.inputfilepath = inputfilepath;
	}

	public void setOutputfilepath(String outputfilepath) {
		this.outputfilepath = outputfilepath;
	}

	@Override
	public void loadActions() throws IOException {
		System.out.println("loadActions");

	}

	@Override
	public void execute() {
		System.out.println("execute");

	}

	@Override
	public void createSheet() throws IOException {
		System.out.println("createSheet");

	}

	@Override
	public void createErrorSheet() throws IOException {
		System.out.println("createErrorSheet");

	}

	@Override
	public ArrayList<IAction> getActions() {
		System.out.println("getActions");
		return this.actions;
	}

	@Override
	public String toString() {
		return name;
	}
	@Override
	public void execute(int start, int end) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void shutdown() {
		// TODO Auto-generated method stub
		
	}

}
