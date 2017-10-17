package com.hazy.di.ui;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import com.hazy.common.HazyException;
import com.hazy.imp.INode;
import com.hazy.imp.ISimpleTaskMan;
import com.hazy.imp.ITaskMan;

public class TreePanel implements ITreePanel,MouseListener {

	private JTree tree = null;
	private IMainFrame mFrame = null;

	private IPopupMenuAction popupMenuAction;
	private IPopupMenuAction simplePopupMenuAction;

	public TreePanel(IMainFrame mFrame) {
		this.mFrame = mFrame;
		this.tree = this.createTree();
		
		this.popupMenuAction = new PopupMenuAction(mFrame);
		this.simplePopupMenuAction = new SimplePopupMenuAction(mFrame);
		this.tree.addMouseListener(this);
	}

	public JTree getTree() {
		return tree;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		/* 当鼠标双击事件被触发时，调用alert方法显示树节点的userObject值。 */
		int row = tree.getRowForLocation(e.getX(), e.getY());
		if (row == -1)
			return;
		if (e.getClickCount() == 1)
			return;
		DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
		if (treeNode.isLeaf()) {
			doLeaf(treeNode);
		}
	}

	private void doLeaf(DefaultMutableTreeNode treeNode) {
		INode parentitem = (INode) treeNode.getUserObject();
		
		if(parentitem instanceof ITaskMan){
			ITaskMan task=(ITaskMan)parentitem;
			this.mFrame.getRightTopPanel().showData(task);
		}
		

	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		TreePath path = tree.getPathForLocation(e.getX(), e.getY()); // 关键是这个方法的使用
		if (path == null) {
			return;
		}
		tree.setSelectionPath(path);

		if (e.getButton() == MouseEvent.BUTTON3) { // 右键
			DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();

			INode parentitem = (INode) treeNode.getUserObject();
			if (parentitem instanceof ITaskMan) {
				popupMenuAction.getPopupMenu().show(tree, e.getX(), e.getY());
			} else if ((parentitem instanceof ISimpleTaskMan)) {
				simplePopupMenuAction.getPopupMenu().show(tree, e.getX(), e.getY());
			}
		}

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

	private JTree createTree() {
		JTree tree = null;
		try {
			tree = new JTree(TreeNodeFactory.getInstance().create(mFrame));
		} catch (HazyException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			JOptionPane.showMessageDialog(tree, "ERROR_MESSAGE", e.getMessage(), JOptionPane.ERROR_MESSAGE);
		}
		return tree;
	}

}
