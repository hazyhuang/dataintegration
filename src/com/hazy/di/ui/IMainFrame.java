package com.hazy.di.ui;


import javax.swing.JFrame;
import javax.swing.JPanel;

import com.hazy.di.model.AgileUser;


public interface IMainFrame {
	public JPanel getRightPanel();
	public ITreePanel getTreePanel();
	public IRightTopPanel getRightTopPanel();
	public MonitorPanel getMonitorPanel();
	public JFrame getFrame();
	public AgileUser getAgileUser();
}
