package com.hazy.imp;

import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hazy.thread.AbstractActionFactory;
import com.hazy.thread.IAction;
import com.hazy.thread.ThreadExecutor;

public class SimpleTaskMan implements ISimpleTaskMan {
	private ThreadExecutor exec = new ThreadExecutor();
	private ArrayList<IAction> actions = new ArrayList<IAction>();
	static Logger logger = Logger.getLogger(SimpleTaskMan.class);

	private String name = null;
	public String getName() {
		return this.name ;
	}
	public void setName(String name) {
		this.name = name;
	}

	public void execute() {
		this.exec.execute(actions);
	}

	public void loadActions() throws IOException {
		this.actions = AbstractActionFactory.getInstance().createTestActions(IAction.ACTION_TEST);
	}

	public ArrayList<IAction> getActions() {
		return actions;
	}

	@Override
	public String toString() {
		return name;
	}

}
