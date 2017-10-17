package com.hazy.thread.action;

import com.agile.api.APIException;
import com.agile.api.IAgileSession;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.AgileUser;

public class ActionUtil {
	
	public static IAgileSession getAgileSession(AgileUser agileUser) throws APIException{
		return HazyUtil.getAgileAPIHelper().login(agileUser.getUrl(), agileUser.getUsername(), agileUser.getPassword());
	}

}
