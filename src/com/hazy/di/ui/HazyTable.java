package com.hazy.di.ui;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.IColumnProperty;
import com.hazy.di.model.IRowObject;
import com.hazy.di.model.ISheet;
import com.hazy.di.model.ITitle;
import com.hazy.di.poi.IXlsxDAO;
import com.hazy.di.service.ExcelDAOFactory;

public class HazyTable {

	
	private JTable jTable;

	public HazyTable() {
		
			
		try {
			initTable();
		} catch (IOException | HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public JTable getTable() {
		return jTable;
	}

	void initTable() throws IOException, HazyException {
		String fullPath = HazyUtil.getLinuxUtil().getLocalFullPath("conf.properties");
		Properties prop = HazyUtil.getInstance().loadPropertiesUTF8(fullPath);
		String filepath1 = prop.getProperty("filepath1");
		IXlsxDAO dao1=ExcelDAOFactory.getInstance().createXlsxDAO(filepath1);
		
		ISheet sheet = dao1.loadHazyObject();
		ITitle title = sheet.getTitle();
		ArrayList<IColumnProperty> columns = title.getProperties();
		Object[] headers = new Object[columns.size()];
        
		for (IColumnProperty column : columns) {
			int index = column.getIndex();

		    headers[index] = column.getValue();
		}

		Object[][] cellData = null;
		DefaultTableModel dataModel = new DefaultTableModel(cellData, headers) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		this.jTable = new JTable();
		jTable.setModel(dataModel);
		DefaultTableModel tableModel = (DefaultTableModel)jTable.getModel();
		
		ArrayList<IRowObject> list = sheet.getContent();
		
		for (IRowObject hObj : list) {
		
			Object[] values = new Object[columns.size()];
            
			for (IColumnProperty column : columns) {
				//String key = column.getValue();
				int index = column.getIndex();

				values[index] = hObj.getValue(index);
			}
			tableModel.addRow(values);
		}
		jTable.revalidate();
		jTable.repaint();
		
	}

}
