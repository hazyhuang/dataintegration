package com.hazy.di.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JLabel;

public class UIUtil {
	public static String getFilePath(){
		JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
		        jfc.showDialog(new JLabel(), "选择导入的文件");  
		       File file=jfc.getSelectedFile(); 
		       String filepath=null;
		      if(file.isDirectory()){  
		         filepath=file.getAbsolutePath();  
		        }else if(file.isFile()){  
		          filepath=file.getAbsolutePath();  
		        }  
		        System.out.println(jfc.getSelectedFile().getName());  
         return filepath;
	}
	
	public static String getPath(){
		JFileChooser jfc=new JFileChooser();  
		        jfc.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES );  
		        jfc.showDialog(new JLabel(), "选择文件夹");  
		       File file=jfc.getSelectedFile(); 
		       String filepath=null;
		      if(file.isDirectory()){  
		         filepath=file.getAbsolutePath();  
		        }
		        System.out.println(jfc.getSelectedFile().getName());  
         return filepath;
	}
}
