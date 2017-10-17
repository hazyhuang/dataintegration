package com.hazy.di.ui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class InputPanel  {

	//private ITaskMan task = null;
	//private JLabel taskLabel = new JLabel("名称：");
	//private JLabel taskNameLabel = new JLabel("请选择[任务节点]");
	private JTextField taskName = new JTextField(40);
	private JLabel inputLabel = new JLabel("保存文件夹");
	private JLabel outputLabel = new JLabel("文档名称");
	private JTextField input = new JTextField(40);
	private JTextField output = new JTextField(40);
	private JButton inputBT = new JButton("选择...");
	private JButton saveBT = new JButton("查询   ");
	
	private JPanel panel = new JPanel();

	public InputPanel() {
        taskName.setText("请选择任务节点");
        taskName.setEditable(false);
		panel.setLayout(new FlowLayout());
		Box box1, box2, box3, boxBase;
		boxBase = Box.createHorizontalBox();
		box1 = Box.createVerticalBox();
		//box1.add(taskLabel);
		//box1.add(Box.createVerticalStrut(8));
		box1.add(inputLabel);
		box1.add(Box.createVerticalStrut(8));
		box1.add(outputLabel);
		box1.add(Box.createVerticalStrut(8));
		box1.add(new JLabel("    "));
		box1.add(Box.createVerticalStrut(8));

		box2 = Box.createVerticalBox();
		//box2.add(taskName);
		//box2.add(Box.createVerticalStrut(8));
		box2.add(input);
		box2.add(Box.createVerticalStrut(8));
		box2.add(output);
		box2.add(Box.createVerticalStrut(8));
		box2.add(new JLabel("    "));
		box2.add(Box.createVerticalStrut(8));

		box3 = Box.createVerticalBox();
		//box3.add(new JLabel("    "));
		//box3.add(Box.createVerticalStrut(8));
		box3.add(inputBT);
		box3.add(Box.createVerticalStrut(8));

		box3.add(saveBT);
		box3.add(Box.createVerticalStrut(8));
		box3.add(new JLabel("    "));
		box3.add(Box.createVerticalStrut(8));
		boxBase.add(box1);
		boxBase.add(Box.createHorizontalStrut(8));
		boxBase.add(box2);
		boxBase.add(Box.createHorizontalStrut(8));
		boxBase.add(box3);
		boxBase.add(Box.createHorizontalStrut(8));
		panel.add(boxBase);
		panel.setPreferredSize(new Dimension(500, 200));

		saveBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Save...");
				//task.setInputfilepath(input.getText());
				//task.setOutputfilepath(output.getText());
				JOptionPane.showMessageDialog(panel, "已更新", "信息", JOptionPane.INFORMATION_MESSAGE);
			}
		});

		inputBT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Select...");
				String str = UIUtil.getPath();
				input.setText(str);
				//task.setInputfilepath(str);
			}
		});
	}

	public JPanel getPanel() {
		return panel;
	}



}
