package com.hazy.thread;

import java.util.Observable;

public class ThreadTimer extends Observable{
	
	public static String SUCCESS="SUCCESS";
	public static String FAILURE="FAILURE";
	private long count;//总时间
	private long begin;//开始时间
	private long totalFinished;//已完成任务
	private long total;////总任务数量
	private String name;
	
	public String getName(){
		return name;
	}

	public ThreadTimer(String name,long total) {
		
		this.name=name;
		this.total=total;
		this.totalFinished=0;
		this.count = 0;
		this.begin = System.currentTimeMillis();
	}

	public synchronized long getCount() {
		return this.count;

	}

	public synchronized void setCount(long count) {

		this.count = this.count + count;
		System.out.println(name+" 新加入时间：" + count + " 共计：" + this.count);
		this.totalFinished= this.totalFinished+1;
		long end = (System.currentTimeMillis() - this.begin) / 1000;
		System.out.println(name+" total time:"+end+"s"+" 已完成数量"+totalFinished+"Task, "+end/totalFinished+"s/Task");
		notify();
		setChanged();
		this.notifyObservers();
	}
	
	public String getMessage(){
		return name+" 已完成数量"+totalFinished+"Task, 总"+total+"Task";
		
	}

}
