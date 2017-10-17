package com.hazy.imp;

import java.util.ArrayList;

import com.hazy.thread.IAction;

public interface INode {
	public void setName(String name);
	public String getName();
	public ArrayList<IAction> getActions();
}
