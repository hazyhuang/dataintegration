package com.hazy.di.ui;

import javax.swing.JTable;

public class TableFactory {
	private static TableFactory instance =new TableFactory();
	
	public static TableFactory getInstance(){
		return instance;
	}
	
	JTable create(){
		HazyTable table=new HazyTable();
		return table.getTable();
		
	}
}
