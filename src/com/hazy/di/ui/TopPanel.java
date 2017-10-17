package com.hazy.di.ui;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JPanel;


import com.hazy.di.model.AgileUser;

public class TopPanel implements Observer{
	
	
	private JPanel panel=new JPanel();

	JLabel serverLabel=new JLabel();
	JLabel userLabel=new JLabel();
	public TopPanel(AgileUser agileUser){

		
		panel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		panel.setPreferredSize(new Dimension(500, 50));
		panel.add(serverLabel);
		panel.add(userLabel);
		serverLabel.setText("Server:"+agileUser.getUrl());
		userLabel.setText("User:"+agileUser.getUsername());
	}
	
	public JPanel getPanel(){
		return panel;
	}

	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof AgileUser){
			AgileUser user=(AgileUser)o;
			userLabel.setText("User:"+user.getUsername());
			userLabel.revalidate();
			userLabel.repaint();
		}
		
	}
	

}
