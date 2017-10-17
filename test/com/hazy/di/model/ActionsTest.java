package com.hazy.di.model;

import static org.junit.Assert.*;

import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.agile.api.ItemConstants;
import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.thread.action.ValidationAction;



public class ActionsTest {
	
	
	public static Logger logger = Logger.getLogger(ActionsTest.class);



	@Before
	public void setUp() throws Exception {
           HazyUtil.getLogHelper().initLogger();
	}

	@Test
	public void testSingleListAndContains() throws HazyException, APIException {
		ValidationAction vAction=new ValidationAction(null);
		String key = "测试";
		IAgileSession session = HazyUtil.getLinuxUtil().getLocalSession();
		Map<Object, Object> map = vAction.getSingleList(session, "M_操作指令", ItemConstants.ATT_PAGE_THREE_LIST01);
		boolean b = vAction.contains(map, key);
		Assert.assertFalse(b);
		boolean c = vAction.contains(map, "");
		Assert.assertFalse(c);
		
	}

}
