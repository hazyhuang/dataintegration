package com.hazy.thread;

public interface IAction {
	
	public Integer SUCCESS = 0;
	
	public Integer FAILURE = 1;
	
	public Integer INITIAL=-1;
	
	public Integer ACTION_CREATE=1;
	
	public Integer ACTION_UPLOAD=2;
	
	public Integer ACTION_RELEASE=3;
	
	public Integer ACTION_TEST=4;
	
	public Integer ACTION_VALIDATION=5;
	
	public Integer ACTION_VALIDATION_UPLOAD=6;
	
	public Integer ACTION_VALIDATION_ADD_UPLOAD=7;
	
	public Integer ACTION_ADD_UPLOAD=8;
	
	public Integer doAction();
	
	public IDocumentDTO getDocumentDTO();

}
