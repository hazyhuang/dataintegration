package com.hazy.di.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

public class MenuAction implements ActionListener{
	private JMenuItem newMenuItemLogin = new JMenuItem("login", KeyEvent.VK_N);
	private JMenuItem newMenuItemExist = new JMenuItem("exist", KeyEvent.VK_N);
	

	private JMenuItem newMenuItem6 = new JMenuItem("About", KeyEvent.VK_N);
	private JMenuBar menuBar = new JMenuBar();
	private IFrame mFrame;
	private LoginDialog loginDialog=null;
	public MenuAction(IFrame mFrame){
	    this.mFrame=mFrame;
	    loginDialog=new LoginDialog(mFrame.getFrame());
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);

		JMenu pMenu = new JMenu("Help");
		fileMenu.setMnemonic(KeyEvent.VK_I);
		pMenu.add(newMenuItem6);
		
		newMenuItemLogin.addActionListener(this);
		newMenuItemExist.addActionListener(this);
		
		fileMenu.add(newMenuItemLogin);
		fileMenu.add(newMenuItemExist);
		

		newMenuItem6.addActionListener(this);

		pMenu.add(newMenuItem6);
		
		menuBar.add(fileMenu);
		menuBar.add(pMenu);
	}
	
 public	JMenuBar getMenuBar(){
		return this.menuBar;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == this.newMenuItemLogin) {
			System.out.println("login");
			this.loginDialog.showData(mFrame.getAgileUser());
			this.loginDialog.setVisible(true);
			
		} else if (e.getSource() == this.newMenuItemExist) {
			System.exit(0);
		} else if (e.getSource() == this.newMenuItem6) {
			System.out.println("newMenuItem6");
			JOptionPane.showMessageDialog(mFrame.getFrame(),  "数据导入\nVersion 1.0\n50547681@qq.com","版本信息",
					JOptionPane.INFORMATION_MESSAGE);
		} 
	}

}
