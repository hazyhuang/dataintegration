package com.hazy.thread.action;

import org.apache.log4j.Logger;

import com.agile.api.APIException;
import com.agile.api.ChangeConstants;
import com.agile.api.IAgileSession;
import com.agile.api.IChange;
import com.agile.api.IItem;
import com.agile.api.IRow;
import com.agile.api.ITable;
import com.agile.api.IWorkflow;
import com.hazy.common.HazyException;
import com.hazy.common.HazyUtil;
import com.hazy.di.model.ITitle;
import com.hazy.thread.IAction;
import com.hazy.thread.IDocumentDTO;
import com.hazy.thread.TitleConstants;

public class ReleaseDocumentAction implements IAction {
	private static Logger logger = Logger.getLogger(CreateDocumentAction.class);
	IAgileSession session;
	IDocumentDTO doc;

	public ReleaseDocumentAction(IDocumentDTO doc) {
		this.doc = doc;
	}
	public IDocumentDTO getDocumentDTO(){
		return doc;
	}
	@Override
	public Integer doAction() {
		String number = null;// 文档编码
		String rev = null;// 文档编码
        ITitle title=doc.getTitle();
		try {
			number = (String) doc.getDataObject().getValue(title.getIndex(TitleConstants.ActionFactory_AgileNumber));// 文档编号
			rev = (String) doc.getDataObject().getValue(title.getIndex(TitleConstants.DocumentRev));// 文档版本
			
			process(number,rev);
			doc.setStatus(SUCCESS);

			doc.getDataObject().setValue(title.getIndex(TitleConstants.ReleaseStatus), "SUCCESS");// 发布Agile对象结果

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("[seq]" + number + "[Message]" + e.getMessage());
			doc.setStatus(FAILURE);
			doc.getDataObject().setValue(title.getIndex(TitleConstants.ReleaseStatus), "FAILURE" + "[seq]" + doc.getNumber() + "[Message]" + e.getMessage());// 创建Agile对象结果
			return FAILURE;
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return SUCCESS;
	}

	void process(String number,String rev) throws HazyException, APIException {
		session = ActionUtil.getAgileSession(doc.getmFrame().getAgileUser());
		//session.disableAllWarnings();
		IChange chg = (IChange) HazyUtil.getAgileAPIHelper().createObject(session, "ECO");
		IWorkflow[] wf = chg.getWorkflows();
		chg.setWorkflow(wf[0]);
		if (number != null & !"".equals(number)) {
			IItem item = HazyUtil.getAgileAPIHelper().loadItem(session, number);

			ITable table = chg.getTable(ChangeConstants.TABLE_AFFECTEDITEMS);
			IRow row = table.createRow(item);
			row.setValue(ChangeConstants.ATT_AFFECTED_ITEMS_NEW_REV, rev);
			row.setValue(ChangeConstants.ATT_AFFECTED_ITEMS_LIFECYCLE_PHASE, "发布");
		}
		chg.changeStatus(chg.getDefaultNextStatus(), false, "", false, false, null, null, null, null,
				false);
		//session.enableAllWarnings();
	}

	public static void main(String args[]) {
		HazyUtil.getLogHelper().initLogger();
		ReleaseDocumentAction action = new ReleaseDocumentAction(null);

		try {
			action.process("DAF0000611","A");
		} catch (HazyException | APIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
