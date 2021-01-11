package com.path.vo.reporting.scheduler;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_REPORT_SCHED_PARAMSSC extends GridParamsSC
{
    private BigDecimal PARAM_ID;
    private BigDecimal REPORT_ID;
    private BigDecimal SCHED_ID;
    private String STATUS;
    private String PARAM_NAME;
    private String PARAM_VALUE;
    private String PARAM_TYPE;
    private String LANG_CODE;
    private BigDecimal LOV_DATA_TYPE;
    private BigDecimal SCHED_STATUS_LOV_TYPE_ID;
    private BigDecimal FILE_FORMAT_LOV_TYPE_ID;
    private String REPORT_REF;
    
    public String getREPORT_REF()
    {
        return REPORT_REF;
    }

    public void setREPORT_REF(String rEPORTREF)
    {
        REPORT_REF = rEPORTREF;
    }

    public BigDecimal getFILE_FORMAT_LOV_TYPE_ID()
    {
        return FILE_FORMAT_LOV_TYPE_ID;
    }

    public void setFILE_FORMAT_LOV_TYPE_ID(BigDecimal fILEFORMATLOVTYPEID)
    {
        FILE_FORMAT_LOV_TYPE_ID = fILEFORMATLOVTYPEID;
    }

    public IRP_REPORT_SCHED_PARAMSSC()
    {
	super.setSearchCols(new String[] { "SCHED_NAME", "REPORT_NAME", "START_DATE_STR", "STATUS_DESC", "REMARKS",
		"REP_FORMAT", "SAVE_DATA_TYPE_STR" });
    }

    public BigDecimal getSCHED_STATUS_LOV_TYPE_ID()
    {
	return SCHED_STATUS_LOV_TYPE_ID;
    }

    public void setSCHED_STATUS_LOV_TYPE_ID(BigDecimal sCHEDSTATUSLOVTYPEID)
    {
	SCHED_STATUS_LOV_TYPE_ID = sCHEDSTATUSLOVTYPEID;
    }

    public String getLANG_CODE()
    {
	return LANG_CODE;
    }

    public void setLANG_CODE(String lANGCODE)
    {
	LANG_CODE = lANGCODE;
    }

    public BigDecimal getLOV_DATA_TYPE()
    {
	return LOV_DATA_TYPE;
    }

    public void setLOV_DATA_TYPE(BigDecimal lOVDATATYPE)
    {
	LOV_DATA_TYPE = lOVDATATYPE;
    }

    public String getPARAM_NAME()
    {
	return PARAM_NAME;
    }

    public void setPARAM_NAME(String pARAMNAME)
    {
	PARAM_NAME = pARAMNAME;
    }

    public String getPARAM_VALUE()
    {
	return PARAM_VALUE;
    }

    public void setPARAM_VALUE(String pARAMVALUE)
    {
	PARAM_VALUE = pARAMVALUE;
    }

    public String getPARAM_TYPE()
    {
	return PARAM_TYPE;
    }

    public void setPARAM_TYPE(String pARAMTYPE)
    {
	PARAM_TYPE = pARAMTYPE;
    }

    public String getSTATUS()
    {
	return STATUS;
    }

    public void setSTATUS(String sTATUS)
    {
	STATUS = sTATUS;
    }

    public BigDecimal getPARAM_ID()
    {
	return PARAM_ID;
    }

    public void setPARAM_ID(BigDecimal pARAMID)
    {
	PARAM_ID = pARAMID;
    }

    public BigDecimal getREPORT_ID()
    {
	return REPORT_ID;
    }

    public void setREPORT_ID(BigDecimal rEPORTID)
    {
	REPORT_ID = rEPORTID;
    }

    public BigDecimal getSCHED_ID()
    {
	return SCHED_ID;
    }

    public void setSCHED_ID(BigDecimal sCHEDID)
    {
	SCHED_ID = sCHEDID;
    }
}
