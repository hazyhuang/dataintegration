package com.hazy.di.model;

public class ColumnProperty implements IColumnProperty{
	int index;
	String value;
	public ColumnProperty(int index,String value){
		this.index=index;
		this.value=value;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}


}
