package com.path.vo.reporting.scheduler;

import java.math.BigDecimal;

import com.path.dbmaps.vo.IRP_REPORT_SCHED_PARAMSVO;

public class IRP_REPORT_SCHED_PARAMSCO extends IRP_REPORT_SCHED_PARAMSVO
{
    private String SESSION_ATTR_NAME;
    private BigDecimal ARGUMENT_SOURCE;
    private BigDecimal MULTISELECT_YN;
    
    
    
    public BigDecimal getMULTISELECT_YN()
    {
        return MULTISELECT_YN;
    }
    public void setMULTISELECT_YN(BigDecimal mULTISELECTYN)
    {
        MULTISELECT_YN = mULTISELECTYN;
    }
    public String getSESSION_ATTR_NAME()
    {
        return SESSION_ATTR_NAME;
    }
    public void setSESSION_ATTR_NAME(String sESSIONATTRNAME)
    {
        SESSION_ATTR_NAME = sESSIONATTRNAME;
    }
    public BigDecimal getARGUMENT_SOURCE()
    {
        return ARGUMENT_SOURCE;
    }
    public void setARGUMENT_SOURCE(BigDecimal aRGUMENTSOURCE)
    {
        ARGUMENT_SOURCE = aRGUMENTSOURCE;
    }
}
