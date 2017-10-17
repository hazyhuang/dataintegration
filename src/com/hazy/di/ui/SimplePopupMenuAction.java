package com.hazy.di.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Collection;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.tree.DefaultMutableTreeNode;

import com.hazy.imp.ISimpleTaskMan;
import com.hazy.thread.DocumentDTO;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;

public class SimplePopupMenuAction implements IPopupMenuAction,ActionListener {
		private JMenuItem loadAction = new JMenuItem("加载数据");
		private JMenuItem startAction = new JMenuItem("开始导入");
		private JMenuItem stopAction = new JMenuItem("停止导入");

		private JPopupMenu popMenu = new JPopupMenu();
		//private JTree tree = null;
		private IMainFrame mFrame=null;
		
		public SimplePopupMenuAction(IMainFrame frame) {
			this.mFrame = frame;
			this.createMenu();
			
		}
		
		public JPopupMenu getPopupMenu(){
			return popMenu;
		}
	 
		
		
		private JPopupMenu createMenu() {
			loadAction.addActionListener(this);
			startAction.addActionListener(this);

			stopAction.addActionListener(this);

	
			popMenu.add(loadAction);
			popMenu.add(startAction);
			popMenu.add(stopAction);
	
			return popMenu;
		}



		@Override
		public void actionPerformed(ActionEvent e) {
			DefaultMutableTreeNode node = (DefaultMutableTreeNode) mFrame.getTreePanel().getTree().getLastSelectedPathComponent();
			ISimpleTaskMan task = (ISimpleTaskMan) node.getUserObject();

			if (e.getSource() == this.startAction) {
				System.out.println("startAction" + task.toString());
				task.execute();

			} else if (e.getSource() == this.loadAction) {
	            try {
					task.loadActions();
				} catch (IOException e1) {
					e1.printStackTrace();
					JOptionPane.showMessageDialog(mFrame.getFrame(),  e1.getMessage(),"ERROR_MESSAGE", JOptionPane.ERROR_MESSAGE);
					
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

			} 

		}
}