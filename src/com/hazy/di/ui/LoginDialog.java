package com.hazy.di.ui;


import java.awt.FlowLayout;
import java.awt.event.ActionEvent;

import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.JButton;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.hazy.di.model.AgileUser;
import com.hazy.thread.action.ActionUtil;


public class LoginDialog extends JDialog {

	private AgileUser agileUser = null;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	JTextField jtf1;
	JPasswordField jtf2;
	JLabel jlinfo;
    JFrame parent;
	public LoginDialog(JFrame parent) {

		super(parent);
		this.parent=parent;
		initInputDialog();

	}

	public void showData(AgileUser setting) {
		this.agileUser = setting;
		jtf1.setText(setting.getUsername());
		jtf2.setText(setting.getPassword());
	
	}

	public void initInputDialog() {

		setTitle("登陆PLM系统");
       
		setModal(true);

		setSize(260, 150);// 对话框的大小

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);// 关闭后销毁对话框

		setLocationRelativeTo(null);

		JLabel jl1 = new JLabel("用户:");

		jtf1 = new JTextField(12);

		JLabel jl2 = new JLabel("密码:");

		jtf2 = new JPasswordField(12);
		JPanel jp1 = new JPanel(new FlowLayout());
		jp1.add(jl1);
		jp1.add(jtf1);

		JPanel jp2 = new JPanel(new FlowLayout());
		jp2.add(jl2);
		jp2.add(jtf2);
		/*JPanel jp = new JPanel(new GridLayout(5, 2));

		jp.add(jl1);

		jp.add(jtf1);

		jp.add(jl2);

		jp.add(jtf2);*/

		
		JButton jb = new JButton("确认");

		JButton cancel = new JButton("取消");
		//jlinfo = new JLabel("信息:", JLabel.CENTER);
		JPanel jp = new JPanel(new FlowLayout());
		jp.add(jb);
		jp.add(cancel);
		jb.addActionListener(new ActionListener() {

			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {

				//String info = "1:" + jtf1.getText() + "\n 2:" + jtf2.getText() ;
				agileUser.setUsername(jtf1.getText());
				agileUser.setPassword(jtf2.getText());
				System.out.println(agileUser.getUsername()+" "+agileUser.getPassword());
				IAgileSession session=null;
				try {
					session=ActionUtil.getAgileSession(agileUser);
					dispose();
				} catch (APIException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(parent, e1.getMessage(),"ERROR_MESSAGE", 
							JOptionPane.ERROR_MESSAGE);
					
				}finally{
					if(session!=null){
						session.close();
					}
				}
			    

				//jlinfo.setText(info);

			}

		});
		cancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				System.exit(ABORT);

			}

		});
		//add(jp);

		//add(jlinfo, BorderLayout.NORTH);

		//add(jb, BorderLayout.SOUTH);
		
		Box box1, box2,  boxBase;
		boxBase = Box.createHorizontalBox();
		box1 = Box.createVerticalBox();
		box1.add(new JLabel("    "));
		box1.add(Box.createVerticalStrut(8));
		box1.add(new JLabel("    "));
		box1.add(Box.createVerticalStrut(8));
		box1.add(new JLabel("    "));
		box1.add(Box.createVerticalStrut(8));


		box2 = Box.createVerticalBox();
		box2.add(jp1);
		box2.add(Box.createVerticalStrut(8));
		box2.add(jp2);
		box2.add(Box.createVerticalStrut(8));
		box2.add(jp);
		box2.add(Box.createVerticalStrut(8));


	
		boxBase.add(box1);
		boxBase.add(Box.createHorizontalStrut(8));
		boxBase.add(box2);
		boxBase.add(Box.createHorizontalStrut(8));
		setLayout(new FlowLayout());
		add(boxBase);

	}

	public static void main(String[] args) {

		LoginDialog login=new LoginDialog(new JFrame());
		AgileUser setting=new AgileUser();
		login.showData(setting);
		login.setVisible(true);

	}

}