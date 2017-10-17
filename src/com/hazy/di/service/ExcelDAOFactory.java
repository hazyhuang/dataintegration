package com.hazy.di.service;




import org.apache.log4j.Logger;


import com.hazy.di.poi.IXlsxDAO;
import com.hazy.di.poi.XlsxDAO;

public class ExcelDAOFactory {
	static Logger logger = Logger.getLogger( ExcelDAOFactory.class);

	private static ExcelDAOFactory service = new ExcelDAOFactory();

	public static ExcelDAOFactory getInstance() {
		return service;
	}

    public IXlsxDAO createXlsxDAO(String filepath){
    	return new XlsxDAO(filepath);
    }
    

}
