package com.path.vo.reporting.ftr.templateProcess;

import java.math.BigDecimal;

public class TEMPLATE_PROCESSSC extends TEMPLATE_PROCESSCO
{
    private BigDecimal compCode;
    private String userName;
    private String asOfDateStr;
    private String fromDateStr;
    private String toDateStr;
    private BigDecimal periodicDate;
    public BigDecimal getPeriodicDate()
    {
        return periodicDate;
    }

    public void setPeriodicDate(BigDecimal periodicDate)
    {
        this.periodicDate = periodicDate;
    }

    private String periodType;
    
    public String getPeriodType()
    {
        return periodType;
    }

    public void setPeriodType(String periodType)
    {
        this.periodType = periodType;
    }

    private int IsRTL;

    private int olError;
    private String osMessage;

    public int getOlError()
    {
	return olError;
    }

    public void setOlError(int olError)
    {
	this.olError = olError;
    }

    public String getOsMessage()
    {
	return osMessage;
    }

    public void setOsMessage(String osMessage)
    {
	this.osMessage = osMessage;
    }

    public int getIsRTL()
    {
	return IsRTL;
    }

    public void setIsRTL(int isRTL)
    {
	IsRTL = isRTL;
    }

    public String getUserName()
    {
	return userName;
    }

    public void setUserName(String userName)
    {
	this.userName = userName;
    }

    public BigDecimal getCompCode()
    {
	return compCode;
    }

    public void setCompCode(BigDecimal compCode)
    {
	this.compCode = compCode;
    }

    public String getAsOfDateStr()
    {
	return asOfDateStr;
    }

    public void setAsOfDateStr(String asOfDateStr)
    {
	this.asOfDateStr = asOfDateStr;
    }

    public String getFromDateStr()
    {
	return fromDateStr;
    }

    public void setFromDateStr(String fromDateStr)
    {
	this.fromDateStr = fromDateStr;
    }

    public String getToDateStr()
    {
	return toDateStr;
    }

    public void setToDateStr(String toDateStr)
    {
	this.toDateStr = toDateStr;
    }

}
