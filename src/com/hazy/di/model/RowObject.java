package com.hazy.di.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class RowObject implements IRowObject,Cloneable{
	
	private Map<Object,Object> map=new HashMap<Object,Object>();
	
	public Object getValue(Object key){
		return map.get(key);
	}
	
	public void setValue(Object key,Object value){
		map.put(key,value);
	}
	
	public Set<Object> keySet(){
		return map.keySet();
	}
	
	public void putAll(Map<Object,Object> m){
		map.putAll(m);
	}
	
	public IRowObject clone(){
		RowObject newObj=new RowObject();
		Set<Object> set=map.keySet();
		Iterator<Object> iter=set.iterator();
		while(iter.hasNext()){
			Object key=iter.next();
			Object value=map.get(key);
			newObj.setValue(key, value);
			
		}
		return newObj;
	}
}
