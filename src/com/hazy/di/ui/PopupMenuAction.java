package com.hazy.di.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

import com.hazy.common.HazyException;
import com.hazy.imp.ITaskMan;
import com.hazy.thread.DocumentDTO;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;

public class PopupMenuAction implements IPopupMenuAction, ActionListener {
//	private JMenuItem testAction = new JMenuItem("执行前五条");
	private JMenuItem loadAction = new JMenuItem("加载数据");
	private JMenuItem startAction = new JMenuItem("执行");
	private JMenuItem stopAction = new JMenuItem("中止执行");
	private JMenuItem reportAction = new JMenuItem("生成结果文件");
//	private JMenuItem showAction = new JMenuItem("生成错误记录");
	//private JMenuItem settingAction = new JMenuItem("选择导入文件");
	//private JMenuItem displayAction = new JMenuItem("显示");
	private JPopupMenu popMenu = new JPopupMenu();
	// private JTree tree = null;
	private IMainFrame mFrame;
	//private SettingDialog settingDialog = null;

	public PopupMenuAction(IMainFrame mFrame) {
		// this.tree = tree;
		this.mFrame = mFrame;
		this.createMenu();
		//settingDialog = new SettingDialog(mFrame.getFrame());
	}

	public JPopupMenu getPopupMenu() {
		return popMenu;
	}

	private JPopupMenu createMenu() {
		loadAction.addActionListener(this);
		startAction.addActionListener(this);

		stopAction.addActionListener(this);

		reportAction.addActionListener(this);

		//settingAction.addActionListener(this);
		//showAction.addActionListener(this);
		//displayAction.addActionListener(this);
		//testAction.addActionListener(this);
		popMenu.add(loadAction);
		//popMenu.add(testAction);
		popMenu.add(startAction);
		popMenu.add(stopAction);
		popMenu.add(reportAction);
		//popMenu.add(settingAction);
		//popMenu.add(showAction);
		//popMenu.add(displayAction);
		return popMenu;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DefaultMutableTreeNode node = (DefaultMutableTreeNode) mFrame.getTreePanel().getTree()
				.getLastSelectedPathComponent();
		ITaskMan task = (ITaskMan) node.getUserObject();

		if (e.getSource() == this.startAction) {
			System.out.println("startAction" + task.toString());
			task.execute();

		} else if (e.getSource() == this.loadAction) {
			try {
				task.loadActions();
			} catch (IOException | HazyException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(mFrame.getFrame(),  e1.getMessage(),"ERROR_MESSAGE",
						JOptionPane.ERROR_MESSAGE);

			}
			Collection<IAction> actions =task.getActions();

			this.mFrame.getRightPanel().removeAll();
			for (IAction action : actions) {
				IDocumentDTO doc = action.getDocumentDTO();
				
				PageLabel dp = new PageLabel(doc);
				DocumentDTO dto=(DocumentDTO)doc;
				dto.addObserver(dp);
				this.mFrame.getRightPanel().add(dp);

			}
			this.mFrame.getRightPanel().revalidate();
			this.mFrame.getRightPanel().repaint();
		} else if (e.getSource() == this.stopAction) {
			task.shutdown();
		} /*else if (e.getSource() == this.testAction) {
			System.out.println("执行前五条");
			task.execute(0, 5);

		} */else if (e.getSource() == this.reportAction) {
			try {
				task.createSheet();
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(mFrame.getFrame(), e1.getMessage(),"ERROR_MESSAGE", 
						JOptionPane.ERROR_MESSAGE);

			}
			System.out.println("reportAction");
		}/*  else if (e.getSource() == this.showAction) {
			try {
				task.createErrorSheet();
			} catch (IOException e1) {
				e1.printStackTrace();
				JOptionPane.showMessageDialog(mFrame.getFrame(),  e1.getMessage(),"ERROR_MESSAGE",
						JOptionPane.ERROR_MESSAGE);

			}
			System.out.println("showAction");
		}*/

	}

}
