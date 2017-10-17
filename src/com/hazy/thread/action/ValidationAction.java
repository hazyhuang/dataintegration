package com.hazy.thread.action;

import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.IAgileClass;
import com.agile.api.IAgileList;
import com.agile.api.IAgileSession;
import com.agile.api.IAttribute;
import com.agile.api.ItemConstants;
import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.ITitle;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;
import com.hazy.thread.TitleConstants;

public class ValidationAction implements IAction {
	static Logger logger = Logger.getLogger(ValidationAction.class);

	private IDocumentDTO doc;
	private IAgileSession session;

	public ValidationAction(IDocumentDTO doc) {
		this.doc = doc;
	}

	@Override
	public Integer doAction() {
		String type = null;// 文档类型
		String subType = null;// 文档小类

		String desc = null;
		String rev = null;
		ITitle title = doc.getTitle();
		StringBuffer resultStr = new StringBuffer();
		try {
			boolean result=false;
			type = (String) doc.getDataObject().getValue(title.getIndex(TitleConstants.DocumentClass));// 文档类型
			subType = (String) doc.getDataObject().getValue(title.getIndex(TitleConstants.DocumentSubType));// 文档小类
			desc = (String) doc.getDataObject().getValue(title.getIndex(TitleConstants.OADescription));// 描述
			rev = (String) doc.getDataObject().getValue(title.getIndex(TitleConstants.DocumentRev));// 版本

			if (desc.length() > 240) {
				resultStr.append("[FAILURE]描述 的长度超过240");
			}
			if ("".equals(rev)) {
				resultStr.append("[FAILURE]版本为空");
			}
			@SuppressWarnings("unchecked")
			Map<Object, Object> context = (Map<Object, Object>) doc.getContext().get(type);
			
				if (context != null) {
					if (!"无".equals(subType)&&!"".equals(subType)) {
					result = contains(context, subType);
					}else{
						result=true;
					}
				} else {
					session = ActionUtil.getAgileSession(doc.getmFrame().getAgileUser());
					Map<Object, Object> map = getSingleList(session, type, ItemConstants.ATT_PAGE_THREE_LIST01);
					doc.getContext().put(type, map);
					if (!"无".equals(subType)&&!"".equals(subType)) {
					result = contains(map, subType);
					}else{
						result=true;
					}
				}
				if (!result) {
					resultStr.append("[FAILURE]小类不存在");
				}
			

		} catch (Throwable e) {
			e.printStackTrace();
			logger.error("[type]" + type + "[SubType]" + subType);
			logger.error("[Message]" + e.getMessage());
			resultStr.append("[FAILURE]" + e.getMessage());// 
		} finally {
			if (session != null) {
				session.close();
			}
		}
		if (resultStr.length() == 0) {
			doc.setStatus(SUCCESS);
			doc.getDataObject().setValue(title.getIndex(TitleConstants.ValidationStatus), "SUCCESS");// Validation结果
		} else {
			doc.setStatus(FAILURE);
			doc.getDataObject().setValue(title.getIndex(TitleConstants.ValidationStatus), resultStr.toString());// Validation结果
		}
		return doc.getStatus();
	}

	@Override
	public IDocumentDTO getDocumentDTO() {
		return doc;
	}

	public static boolean contains(Map<Object, Object> map, String key) {
		Object value = map.get(key);
		if (value == null) {
			return false;
		} else {
			return true;
		}
	}

	public static Map<Object, Object> getSingleList(IAgileSession session, String subClass, Integer attrid)
			throws APIException, HazyException {
		Map<Object, Object> map = new HashMap<Object, Object>();
		IAgileClass agileClazz = session.getAdminInstance().getAgileClass(subClass);
		if (agileClazz == null) {
			throw HazyUtil.getInstance().createException("文档类不存在");
		}
		IAttribute attr = agileClazz.getAttribute(attrid);
		IAgileList list = attr.getAvailableValues();
		@SuppressWarnings("unchecked")
		Collection<IAgileList> coll = list.getChildNodes();
		if(coll==null){
			throw HazyUtil.getInstance().createException("小类不存在");
		}
		Iterator<IAgileList> iter = coll.iterator();
		while (iter.hasNext()) {
			IAgileList obj = iter.next();
			String value = (String) obj.getValue();
			map.put(value, value);
		}
		return map;
	}

	public static void main(String args[]) throws HazyException, APIException {
               System.out.println("小类不存在".substring(0, 3)+"小类不存在".length());
	}

}
