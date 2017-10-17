package com.hazy.di.ui;


import java.awt.Color;
import java.awt.Dimension;
import java.util.Observable;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;


public class PageLabel extends JLabel implements Observer{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private IDocumentDTO page=null;
    public PageLabel(IDocumentDTO page){
    	this.page=page;
    	this.setPreferredSize(new Dimension(10,10));
    	this.setBorder(BorderFactory.createLineBorder(Color.WHITE, 1));
    	this.setOpaque(true);//设置组件JLabel不透明，只有设置为不透明，设置背景色才有效 
    	if(this.page.getStatus()==IAction.SUCCESS){
    	this.setBackground(Color.GREEN);  
    	}else if(this.page.getStatus()==IAction.INITIAL){
    		this.setBackground(Color.GRAY);  
    	}else if(this.page.getStatus()==IAction.FAILURE){
    		this.setBackground(Color.RED);  
    	}
    }
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof IDocumentDTO){
			if(this.page.getStatus()==IAction.SUCCESS){
		    	this.setBackground(Color.GREEN);  
		    	}else if(this.page.getStatus()==IAction.INITIAL){
		    		this.setBackground(Color.GRAY);  
		    	}else if(this.page.getStatus()==IAction.FAILURE){
		    		this.setBackground(Color.RED);  
		    	}
			this.revalidate();
			this.repaint();
		}
		
	}
    
}