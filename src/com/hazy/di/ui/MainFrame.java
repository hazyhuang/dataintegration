package com.hazy.di.ui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.util.Properties;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;

import org.apache.log4j.Logger;

import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.AgileUser;

public class MainFrame implements IFrame,IMainFrame{

	static Logger logger = Logger.getLogger(MainFrame.class);
	private JFrame frame = new JFrame("数据导入");
	
	private JPanel rightPanel = new JPanel();
	
	
	private ITreePanel treePanel = new TreePanel(this);
	
	private TopPanel topPanel;
	
	private IRightTopPanel rtp=new RightTopPanel();
	
	private AgileUser agileUser=new AgileUser();
	
	private MonitorPanel monitorPanel=new MonitorPanel();
	
	public MonitorPanel getMonitorPanel(){
		return monitorPanel;
	}
	
	public IRightTopPanel getRightTopPanel() {
		return rtp;
	}
	
	public ITreePanel getTreePanel() {
		return treePanel;
	}
	public JFrame getFrame() {
		return frame;
	}
	public JPanel getRightPanel() {
		return rightPanel;
	}

	public MainFrame() {
		init();
	}


	void init() {
		this.loadConfig();
		topPanel=new TopPanel(agileUser);
		agileUser.addObserver(topPanel);
		frame.setLayout(new BorderLayout());
		
		Container contentPane = frame.getContentPane();
		JScrollPane jScrollPane1 = new JScrollPane();
		jScrollPane1.getViewport().add(treePanel.getTree(), null);
		JScrollPane jScrollPaneRight = new JScrollPane();//右工作面板

		rightPanel.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		JPanel mPanel=new JPanel();
		
		mPanel.setLayout(new BorderLayout());
		mPanel.add(jScrollPaneRight,BorderLayout.CENTER);
		JPanel buttomPanel=monitorPanel.getPanel();
		
		JPanel rightTopPanel=rtp.getPanel();
		mPanel.add(buttomPanel, BorderLayout.SOUTH);
		JSplitPane splitPaneRight = new JSplitPane(JSplitPane.VERTICAL_SPLIT, false, rightTopPanel, mPanel);
		
		jScrollPaneRight.getViewport().add(rightPanel, null);
		// jScrollPane2.getViewport().add(table.getTable(), null);
		rightPanel.setPreferredSize(new Dimension(500, 9000));// 这是关键的2句
		jScrollPaneRight.setPreferredSize(new Dimension(500, 600));
		JSplitPane splitPane1 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, false, jScrollPane1,splitPaneRight);
		contentPane.add(this.topPanel.getPanel(),BorderLayout.NORTH);
		contentPane.add(splitPane1,BorderLayout.CENTER);
		frame.setJMenuBar(createMenuBar());
		frame.setVisible(true);
		int w = 800, h = 600;
		frame.setSize(w, h);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setSize(w, h);
		frame.setLocation(screenSize.width / 2 - w / 2, screenSize.height / 2 - h / 2);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// panel.revalidate();
		// panel.repaint();
	}

	private JMenuBar createMenuBar() {

		MenuAction menuListener = new MenuAction(this);

		return menuListener.getMenuBar();
	}
	private LoginDialog loginDialog=null;
	private void loadConfig(){
		loginDialog=new LoginDialog(this.getFrame());
		String fullPath = HazyUtil.getLinuxUtil().getUserPath("/conf.properties");
		logger.debug("[Config Path]"+fullPath);
		try {
			Properties prop = HazyUtil.getInstance().loadPropertiesUTF8(fullPath);
			String user=prop.getProperty("agileuser");
			String password=prop.getProperty("agilepwd");
			String url=prop.getProperty("agileurl");
		    agileUser.setUrl(url);
		    agileUser.setUsername(user);
		    agileUser.setPassword(password);
		    //loginDialog.showData(agileUser);
		    //loginDialog.setVisible(true);
		    
		} catch (HazyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(getFrame(),  e.getMessage(),"ERROR_MESSAGE",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		String fullpath = HazyUtil.getLinuxUtil().getUserPath("/log.xml");

		HazyUtil.getLogHelper().initXMLLogger(fullpath);
		//HazyUtil.getLogHelper().initLogger();
		logger.debug("main");
		new MainFrame();
	}

	@Override
	public AgileUser getAgileUser() {
		return agileUser;
	}
}
