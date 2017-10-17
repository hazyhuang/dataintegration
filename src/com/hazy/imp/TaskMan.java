package com.hazy.imp;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import com.hazy.common.HazyException;
import com.hazy.di.model.IRowObject;
import com.hazy.di.model.ISheet;
import com.hazy.di.model.ITitle;
import com.hazy.di.model.SheetFactory;
import com.hazy.di.poi.IXlsxDAO;
import com.hazy.di.service.ExcelDAOFactory;
import com.hazy.di.ui.IMainFrame;
import com.hazy.thread.AbstractActionFactory;
import com.hazy.thread.ThreadExecutor;
import com.hazy.thread.ThreadTimer;
import com.hazy.thread.TitleConstants;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;

public class TaskMan implements ITaskMan {

	static Logger logger = Logger.getLogger(TaskMan.class);
	private String name = null;
	private ThreadExecutor exec = new ThreadExecutor();
	private ArrayList<IAction> actions = new ArrayList<IAction>();
	private ISheet sheet;
	private IXlsxDAO xlsxDAO;
	private Integer type;
	private String inputfilepath;
	private String outputfilepath;
	private IMainFrame mFrame;

	public TaskMan() {

	}

	public IMainFrame getmFrame() {
		return mFrame;
	}

	public void setmFrame(IMainFrame mFrame) {
		this.mFrame = mFrame;
	}

	
	public String getInputfilepath() {
		return inputfilepath;
	}

	public String getOutputfilepath() {
		return outputfilepath;
	}

	public void setName(String name) {
		this.name = name;
	}
	public String getName() {
		return this.name ;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setInputfilepath(String inputfilepath) {
		this.inputfilepath = inputfilepath;
	}

	public void setOutputfilepath(String outputfilepath) {
		this.outputfilepath = outputfilepath;
	}

	public void loadActions() throws IOException, HazyException {
		this.xlsxDAO = ExcelDAOFactory.getInstance().createXlsxDAO(inputfilepath);
		this.sheet = this.xlsxDAO.loadHazyObject();

		this.actions = AbstractActionFactory.getInstance().create(mFrame,sheet, type);
	}

	public void createSheet() throws IOException {
        xlsxDAO.setOutputfilepath(outputfilepath);
		xlsxDAO.createHazyObject(sheet);

	}

	public void createErrorSheet() throws IOException {
       ITitle title= this.sheet.getTitle();
		ArrayList<IRowObject> list = new ArrayList<IRowObject>();
		for (IAction action : actions) {
			IDocumentDTO doc = action.getDocumentDTO();
			if (doc.getStatus() == IAction.FAILURE) {
				IRowObject oldObj = doc.getDataObject();
				File[] files = doc.getFiles();
				if(files!=null){
				for (int i = 0; i < files.length; i++) {
					IRowObject newObj = oldObj.clone();
					newObj.setValue(title.getIndex(TitleConstants.FilePath), files[i].getAbsolutePath());
					list.add(newObj);
				}
				}
			}
		}
		ISheet newSheet = SheetFactory.getInstance().createSheet(sheet.getTitle(), list);
		xlsxDAO.setOutputfilepath(outputfilepath);
		xlsxDAO.createHazyObject(newSheet);

	}

	public void execute() {
		int size=actions.size();
		ThreadTimer   successTimer=new ThreadTimer(ThreadTimer.SUCCESS,size);
		ThreadTimer	 failureTimer=new ThreadTimer(ThreadTimer.FAILURE,size);
		logger.debug(this.mFrame);
		successTimer.addObserver(this.mFrame.getMonitorPanel());
		failureTimer.addObserver(this.mFrame.getMonitorPanel());
		this.exec.execute(actions,successTimer,failureTimer);
	}
	
	public void shutdown() {
		this.exec.shutdown();
	}
	
	public void execute(int start,int end) {
		ArrayList<IAction> exelist=new ArrayList<IAction>();
		int length=end-start;
		for(int i=0;i<length;i++){
			IAction action=actions.get(start+i);
			exelist.add(action);
		}
		int size=length;
		ThreadTimer   successTimer=new ThreadTimer(ThreadTimer.SUCCESS,size);
		ThreadTimer	 failureTimer=new ThreadTimer(ThreadTimer.FAILURE,size);
		successTimer.addObserver(this.mFrame.getMonitorPanel());
		failureTimer.addObserver(this.mFrame.getMonitorPanel());
		this.exec.execute(exelist,successTimer,failureTimer);
	}

	public ArrayList<IAction> getActions() {
		return actions;
	}

	@Override
	public String toString() {
		return name;
	}

}
