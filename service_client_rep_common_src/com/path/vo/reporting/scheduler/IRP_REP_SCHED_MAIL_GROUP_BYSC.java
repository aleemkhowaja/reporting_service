package com.path.vo.reporting.scheduler;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_REP_SCHED_MAIL_GROUP_BYSC extends GridParamsSC
{
    private BigDecimal SCHED_ID;
    private BigDecimal REPORT_ID;
	public BigDecimal getSCHED_ID() {
		return SCHED_ID;
	}
	public void setSCHED_ID(BigDecimal sCHEDID) {
		SCHED_ID = sCHEDID;
	}
	public BigDecimal getREPORT_ID() {
		return REPORT_ID;
	}
	public void setREPORT_ID(BigDecimal rEPORTID) {
		REPORT_ID = rEPORTID;
	}
    
	public IRP_REP_SCHED_MAIL_GROUP_BYSC()
    {
		super.setSearchCols(new String[] {"USER_ID"});
    }
}
