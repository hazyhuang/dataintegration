package com.hazy.di.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Title implements ITitle{
	
	private ArrayList<IColumnProperty> list=new ArrayList<IColumnProperty>();
	private Map<Object,Integer> map = new HashMap<Object,Integer>();

	public void add(IColumnProperty prop){
		list.add(prop);
		map.put(prop.getValue(), prop.getIndex());
	}
	public ArrayList<IColumnProperty> getProperties(){
		return list;
	}
	
	public Integer getIndex(String key){
		return map.get(key);
	}
	
	public int size(){
		return list.size();
	}
}
