package com.hazy.di.model;

import java.util.ArrayList;

public class SheetFactory {
	
	private static SheetFactory instance=new SheetFactory();
	
	public static SheetFactory getInstance(){
		return instance;
	}
	
	public IRowObject createRow(){
		return new RowObject();
	}
	public ITitle createTitle(){
		return new Title();
	}
	public IColumnProperty createColumnProperty(int i, String title){
		return new ColumnProperty(i,title);
	}
	public ISheet createSheet(ITitle title,ArrayList<IRowObject> content){
	    return new SheetObject(title, content);
	}

}
