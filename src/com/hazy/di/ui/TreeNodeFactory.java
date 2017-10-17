package com.hazy.di.ui;

import java.io.IOException;
import javax.swing.tree.DefaultMutableTreeNode;

import com.hazy.common.HazyException;
import com.hazy.imp.ISimpleTaskMan;
import com.hazy.imp.ITaskMan;
import com.hazy.imp.TaskManFactory;
import com.hazy.thread.IAction;

public class TreeNodeFactory {
	private static TreeNodeFactory instance =new  TreeNodeFactory();
	
	public static TreeNodeFactory getInstance(){
		return instance;
	}
	public DefaultMutableTreeNode create(IMainFrame mFrame) throws HazyException, IOException{
	
		DefaultMutableTreeNode top = new DefaultMutableTreeNode("批量导入");
	
		//String fullPath = HazyUtil.getLinuxUtil().getLocalFullPath("conf.properties");
		//Properties prop = HazyUtil.getInstance().loadPropertiesUTF8(fullPath);
		//String inputValid = prop.getProperty("inputValid");
		//String outputValid = prop.getProperty("outputValid");
	
		ITaskMan valid=TaskManFactory.getInstance().create("文档创建校验",IAction.ACTION_VALIDATION);
		valid.setmFrame(mFrame);
		top.add(new DefaultMutableTreeNode(valid));
		
		
		//String inputfilepath1 = prop.getProperty("inputCreate");
		//String outputfilepath1 = prop.getProperty("outputCreate");
	
		ITaskMan docImp=TaskManFactory.getInstance().create("文档创建",IAction.ACTION_CREATE);
		docImp.setmFrame(mFrame);
		top.add(new DefaultMutableTreeNode(docImp));
		
		
		//String inputfilepath2 = prop.getProperty("inputFileload");
		//String outputfilepath2 = prop.getProperty("outputFileload");
	
		ITaskMan upload=TaskManFactory.getInstance().create("上传附件",IAction.ACTION_UPLOAD);
		upload.setmFrame(mFrame);
		top.add(new DefaultMutableTreeNode(upload));
		
		//String inputfilepath3= prop.getProperty("inputRelease");
		//String outputfilepath3 = prop.getProperty("outputRelease");
		ITaskMan docsRelease=TaskManFactory.getInstance().create("文档发布",IAction.ACTION_RELEASE);
		docsRelease.setmFrame(mFrame);
		top.add(new DefaultMutableTreeNode(docsRelease));
		
		ISimpleTaskMan test=TaskManFactory.getInstance().createSimpleTask("文档创建测试",IAction.ACTION_TEST);
		top.add(new DefaultMutableTreeNode(test));
		
		//String inputfilepathValid = prop.getProperty("inputFileloadValid");
		//String outputfilepathValid = prop.getProperty("outputFileloadValid");
	
		ITaskMan uploadValid=TaskManFactory.getInstance().create("附件校验",IAction.ACTION_VALIDATION_UPLOAD);
		uploadValid.setmFrame(mFrame);
		top.add(new DefaultMutableTreeNode(uploadValid));
		
		
	
		ITaskMan validAddUpload=TaskManFactory.getInstance().create("添加附件校验",IAction.ACTION_VALIDATION_ADD_UPLOAD);
		validAddUpload.setmFrame(mFrame);
		top.add(new DefaultMutableTreeNode(validAddUpload));
		
		ITaskMan addUpload=TaskManFactory.getInstance().create("添加附件",IAction.ACTION_ADD_UPLOAD);
		addUpload.setmFrame(mFrame);
		top.add(new DefaultMutableTreeNode(addUpload));
		
		return top;
	}

}
