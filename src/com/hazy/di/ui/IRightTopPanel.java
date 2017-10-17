package com.hazy.di.ui;

import javax.swing.JPanel;

import com.hazy.imp.ITaskMan;

public interface IRightTopPanel {
	public JPanel getPanel();
	public void showData(ITaskMan task);
}
