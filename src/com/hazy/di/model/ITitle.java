package com.hazy.di.model;

import java.util.ArrayList;

public interface ITitle {
	
	public void add(IColumnProperty prop);
	
	public ArrayList<IColumnProperty> getProperties();
	
	public int size();
	
	public Integer getIndex(String key);
}
