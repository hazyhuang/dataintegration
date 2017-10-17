package com.hazy.di.model;

import java.util.ArrayList;

public class SheetObject implements ISheet{
	
	private ITitle title;
	
	private ArrayList<IRowObject> content;
	
	public SheetObject(ITitle title,ArrayList<IRowObject> content){
		this.title=title;
		this.content=content;
	}
	
	public ITitle getTitle() {
		return title;
	}

	public ArrayList<IRowObject> getContent() {
		return content;
	}

	public void print(){
		for(IRowObject ho:content ){
			ArrayList<IColumnProperty> list=title.getProperties();
			for(IColumnProperty prop:list){
				System.out.print(ho.getValue(prop.getValue()));
				System.out.print("  ");
			}
			System.out.println();
		}
		
	}
	

}
