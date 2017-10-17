package com.hazy.di.model;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.hazy.common.HazyUtil;
import com.hazy.thread.action.ActionUtil;

public class ModelTest {

	public static Logger logger = Logger.getLogger(ModelTest.class);
    IRowObject row =null;


	@Before
	public void setUp() throws Exception {
           this.row=new RowObject();
	}

	@Test
	public void testRowObjectClone() {
		IRowObject row2=row.clone();
		Assert.assertNotEquals(row2, row);
		
	}
	
	@Test
	public void testAgileLogin() {
		HazyUtil.getLogHelper().initLogger();
		AgileUser agileUser=new AgileUser();
		agileUser.setUrl("http://testapp.ramaxel.com:7001/Agile");
		agileUser.setUsername("admin");
		agileUser.setPassword("password");
		try {
			IAgileSession session=ActionUtil.getAgileSession(agileUser);
			System.out.println(session);
		} catch (APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		IRowObject row2=row.clone();
		Assert.assertNotEquals(row2, row);
		
	}
}
