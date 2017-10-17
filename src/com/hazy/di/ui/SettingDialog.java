package com.hazy.di.ui;


import java.awt.BorderLayout;
import java.awt.GridLayout;

import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

import javax.swing.JPanel;

import javax.swing.JTextField;

import com.hazy.imp.ITaskMan;



public class SettingDialog extends JDialog {

	ITaskMan setting = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField jtf1, jtf2;
	JLabel jlinfo;

	public SettingDialog(JFrame parent) {

		super(parent);

		initInputDialog();

	}

	public void showData(ITaskMan setting) {
		this.setting = setting;
		jtf1.setText(setting.getInputfilepath());
		jtf2.setText(setting.getOutputfilepath());
	
	}

	public void initInputDialog() {

		setTitle("多项输入对话框");

		setModal(true);

		setSize(300, 200);// 对话框的大小

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框

		setLocationRelativeTo(null);

		JLabel jl1 = new JLabel("导入路径:");

		jtf1 = new JTextField(8);

		JLabel jl2 = new JLabel("导出路径:");

		jtf2 = new JTextField(8);

	

		

		JPanel jp = new JPanel(new GridLayout(5, 2));

		jp.add(jl1);

		jp.add(jtf1);

		jp.add(jl2);

		jp.add(jtf2);

		
		JButton jb = new JButton("确认输入");

		jlinfo = new JLabel("信息:", JLabel.CENTER);

		jb.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				String info = "1:" + jtf1.getText() + "\n 2:" + jtf2.getText() ;
				setting.setInputfilepath(jtf1.getText());
				setting.setOutputfilepath(jtf2.getText());
			

				jlinfo.setText(info);

			}

		});

		add(jp);

		add(jlinfo, BorderLayout.NORTH);

		add(jb, BorderLayout.SOUTH);

	}

	public static void main(String[] args) {

		new SettingDialog(new JFrame()).setVisible(true);

	}

}