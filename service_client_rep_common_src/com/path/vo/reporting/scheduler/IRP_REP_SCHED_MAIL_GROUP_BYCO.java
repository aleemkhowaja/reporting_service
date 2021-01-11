package com.path.vo.reporting.scheduler;

import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_GROUP_BYVO;
import com.path.lib.vo.BaseVO;

public class IRP_REP_SCHED_MAIL_GROUP_BYCO extends BaseVO
{

	private IRP_REP_SCHED_MAIL_GROUP_BYVO mailGrpVO=new IRP_REP_SCHED_MAIL_GROUP_BYVO();
	private String FIELD_DESC;
	
	public IRP_REP_SCHED_MAIL_GROUP_BYVO getMailGrpVO() {
		return mailGrpVO;
	}
	public void setMailGrpVO(IRP_REP_SCHED_MAIL_GROUP_BYVO mailGrpVO) {
		this.mailGrpVO = mailGrpVO;
	}
	public String getFIELD_DESC() {
		return FIELD_DESC;
	}
	public void setFIELD_DESC(String fIELDDESC) {
		FIELD_DESC = fIELDDESC;
	}
}
