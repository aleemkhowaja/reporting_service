package com.path.vo.reporting.scheduler;

import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOG_DETAILSVO;
import com.path.lib.vo.BaseVO;

public class IRP_REPORT_SCHED_LOG_DETAILSCO extends BaseVO
{
    private IRP_REPORT_SCHED_LOG_DETAILSVO logDetVO = new IRP_REPORT_SCHED_LOG_DETAILSVO();
    private String RECEIVER_TYPE_STR;
    private String STATUS_DESC;

    public String getSTATUS_DESC()
    {
	return STATUS_DESC;
    }

    public void setSTATUS_DESC(String sTATUSDESC)
    {
	STATUS_DESC = sTATUSDESC;
    }

    public IRP_REPORT_SCHED_LOG_DETAILSVO getLogDetVO()
    {
	return logDetVO;
    }

    public void setLogDetVO(IRP_REPORT_SCHED_LOG_DETAILSVO logDetVO)
    {
	this.logDetVO = logDetVO;
    }

    public String getRECEIVER_TYPE_STR()
    {
	return RECEIVER_TYPE_STR;
    }

    public void setRECEIVER_TYPE_STR(String rECEIVERTYPESTR)
    {
	RECEIVER_TYPE_STR = rECEIVERTYPESTR;
    }

}
