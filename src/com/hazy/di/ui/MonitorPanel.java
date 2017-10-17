package com.hazy.di.ui;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.hazy.thread.ThreadTimer;

public class MonitorPanel implements Observer{
	
	JPanel panel=new JPanel();
	JLabel label = new JLabel("任务状态");
	JLabel fLabel=new JLabel();
	JLabel sLabel=new JLabel();
	public MonitorPanel(){
		panel.add(label);
		panel.add(fLabel);
		panel.add(sLabel);
		
	}
	public JPanel getPanel(){
		return panel;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof ThreadTimer){
			ThreadTimer timer=(ThreadTimer)o;
			String name=timer.getName();
			if(ThreadTimer.SUCCESS.equals(name)){
				sLabel.setText(timer.getMessage());
			}else if(ThreadTimer.FAILURE.equals(name)){
				fLabel.setText(timer.getMessage());
			}
		}
		
	}
	
	

}
