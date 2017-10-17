package com.hazy.imp;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class TaskManFactory {
	private static TaskManFactory instance =new TaskManFactory();
	
	public static TaskManFactory getInstance(){
		return instance;
	}
	
	public ITaskMan create(String name,int actionType){
		  //容器
        ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-config.xml");

       ITaskMan taskMan=(ITaskMan)ctx.getBean("taskMan");
       //taskMan.setInputfilepath(inputFilePath);
       //taskMan.setOutputfilepath(outputFilePath);
       taskMan.setName(name);
       taskMan.setType(actionType);
      
		return taskMan;
	}
	
	public ISimpleTaskMan createSimpleTask(String name,int actionType){
		  //容器
      ApplicationContext ctx=new ClassPathXmlApplicationContext("spring-config.xml");

     ISimpleTaskMan taskMan=(ISimpleTaskMan)ctx.getBean("simpleTaskMan");

     taskMan.setName(name);
  
		return taskMan;
	}
}
