package com.hazy.thread;


import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

public class ThreadExecutor {
	
	static Logger logger = Logger.getLogger(ThreadExecutor.class);
	private ThreadPoolExecutor executor = new ThreadPoolExecutor(30, 30, 200, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(10000));// 线程池大小
	


	public void execute(ArrayList<IAction> list,ThreadTimer successTimer,ThreadTimer failureTimer){
	
		 
		int i=0;
		for(IAction action:list){
			ThreadAction a=new ThreadAction(action,i+1,successTimer,failureTimer);
			executor.execute(a);
			logger.debug("[Action]"+i);
			i++;
		}
	}
	public void execute(ArrayList<IAction> list){
		int size=list.size();
		ThreadTimer   successTimer=new ThreadTimer(ThreadTimer.SUCCESS,size);
		ThreadTimer	 failureTimer=new ThreadTimer(ThreadTimer.FAILURE,size);
		 
		int i=0;
		for(IAction action:list){
			ThreadAction a=new ThreadAction(action,i+1,successTimer,failureTimer);
			executor.execute(a);
			logger.debug("[Action]"+i);
			i++;
		}
	}
	
	public void shutdown(){
		executor.shutdown();
	}
}
