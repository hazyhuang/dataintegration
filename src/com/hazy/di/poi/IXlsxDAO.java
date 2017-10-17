package com.hazy.di.poi;

import java.io.IOException;

import com.hazy.di.model.ISheet;

public interface IXlsxDAO {
	public ISheet loadHazyObject() throws IOException ;
	
	public void createHazyObject(ISheet disheet) throws IOException;
	
	public void setOutputfilepath(String outputfilepath);
}
