package com.hazy.di.model;

public interface IRowObject {
	
	public Object getValue(Object key);
	
	public void setValue(Object key,Object value);
	
	public IRowObject clone();
}
