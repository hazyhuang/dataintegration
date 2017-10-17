package com.hazy.thread;

import org.apache.log4j.Logger;

import com.hazy.common.HazyUtil;

public class ThreadAction implements Runnable {
	static Logger logger = Logger.getLogger(ThreadAction.class);
	private ThreadTimer successTimer;
	private ThreadTimer failureTimer;
	private int seq;
	private IAction action;

	public ThreadAction(IAction action2, int seq2, ThreadTimer succtimer, ThreadTimer failtimer) {
		this.seq = seq2;
		this.successTimer = succtimer;
		this.failureTimer = failtimer;
		this.action = action2;
	}

	@Override
	public void run() {
		double begin = HazyUtil.getTimeHelper().getBeginTime();
		logger.debug("start doAction" + seq + action.getClass());
		int ret = action.doAction();
		logger.debug("ThreadAction" + seq);
		double duration = HazyUtil.getTimeHelper().getTotalTimeSecond(begin);
		if (ret == IAction.SUCCESS) {
			successTimer.setCount((long)duration);
		} else {
			failureTimer.setCount((long)duration);
		}
	}

}
