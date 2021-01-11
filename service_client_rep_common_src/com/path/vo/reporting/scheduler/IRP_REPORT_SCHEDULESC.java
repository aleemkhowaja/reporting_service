package com.path.vo.reporting.scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_REPORT_SCHEDULESC extends GridParamsSC
{
    private String REPORT_NAME;
    private BigDecimal SCHED_ID;
    private BigDecimal REPORT_ID;
    private Date START_DATE;
    private Date SCHEDULED_DATE;
    private String LANG_CODE;
    private String STATUS_DESC;
    private BigDecimal SCHED_STATUS_LOV_TYPE_ID;
    private BigDecimal SCHED_MAIL_RECEIVER_TYPE_ID;
    private String REPORT_REF;
    private ArrayList<BigDecimal> listReportsId;
    private String tblName;
    
    

    public ArrayList<BigDecimal> getListReportsId()
    {
        return listReportsId;
    }

    public void setListReportsId(ArrayList<BigDecimal> listReportsId)
    {
        this.listReportsId = listReportsId;
    }

    public String getTblName()
    {
        return tblName;
    }

    public void setTblName(String tblName)
    {
        this.tblName = tblName;
    }

    public String getREPORT_REF()
    {
        return REPORT_REF;
    }

    public void setREPORT_REF(String rEPORTREF)
    {
        REPORT_REF = rEPORTREF;
    }


    public IRP_REPORT_SCHEDULESC()
    {
	super.setSearchCols(new String[] { "FROM_EMAIL_VAL", "RECEIVER_TYPE_STR", "RECEIVER_EMAIL_VAL", "STATUS_DESC",
		"REMARKS" });
    }

    public BigDecimal getSCHED_STATUS_LOV_TYPE_ID()
    {
	return SCHED_STATUS_LOV_TYPE_ID;
    }

    public void setSCHED_STATUS_LOV_TYPE_ID(BigDecimal sCHEDSTATUSLOVTYPEID)
    {
	SCHED_STATUS_LOV_TYPE_ID = sCHEDSTATUSLOVTYPEID;
    }

    public BigDecimal getSCHED_MAIL_RECEIVER_TYPE_ID()
    {
	return SCHED_MAIL_RECEIVER_TYPE_ID;
    }

    public void setSCHED_MAIL_RECEIVER_TYPE_ID(BigDecimal sCHEDMAILRECEIVERTYPEID)
    {
	SCHED_MAIL_RECEIVER_TYPE_ID = sCHEDMAILRECEIVERTYPEID;
    }

    public String getSTATUS_DESC()
    {
	return STATUS_DESC;
    }

    public void setSTATUS_DESC(String sTATUSDESC)
    {
	STATUS_DESC = sTATUSDESC;
    }

    public String getLANG_CODE()
    {
	return LANG_CODE;
    }

    public void setLANG_CODE(String lANGCODE)
    {
	LANG_CODE = lANGCODE;
    }

    public Date getSTART_DATE()
    {
	return START_DATE;
    }

    public void setSTART_DATE(Date sTARTDATE)
    {
	START_DATE = sTARTDATE;
    }

    public Date getSCHEDULED_DATE()
    {
	return SCHEDULED_DATE;
    }

    public void setSCHEDULED_DATE(Date sCHEDULEDDATE)
    {
	SCHEDULED_DATE = sCHEDULEDDATE;
    }

    public BigDecimal getSCHED_ID()
    {
	return SCHED_ID;
    }

    public void setSCHED_ID(BigDecimal sCHEDID)
    {
	SCHED_ID = sCHEDID;
    }

    public String getREPORT_NAME()
    {
	return REPORT_NAME;
    }

    public void setREPORT_NAME(String rEPORTNAME)
    {
	REPORT_NAME = rEPORTNAME;
    }

    public BigDecimal getREPORT_ID()
    {
	return REPORT_ID;
    }

    public void setREPORT_ID(BigDecimal rEPORTID)
    {
	REPORT_ID = rEPORTID;
    }

}
