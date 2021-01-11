package com.path.dbmaps.vo.schedulerdateprocess;

import java.math.BigDecimal;
import java.util.Date;

import com.path.lib.vo.BaseVO;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * SchedulerDateProcessCO.java used to
 */
public class SchedulerDateProcessCO extends BaseVO
{	private BigDecimal schedId;
	private BigDecimal reportId;
	
	 private String procType;
	 public String getProcType()
	{
	    return procType;
	}
	public void setProcType(String procType)
	{
	    this.procType = procType;
	}
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
	private Date asOfDate;
	 public Date getAsOfDate()
	{
	    return asOfDate;
	}
	public void setAsOfDate(Date asOfDate)
	{
	    this.asOfDate = asOfDate;
	}
	private Date fromDate;
	 public Date getFromDate()
	{
	    return fromDate;
	}
	public void setFromDate(Date fromDate)
	{
	    this.fromDate = fromDate;
	}
	private Date toDate;
	public Date getToDate()
	{
	    return toDate;
	}
	public void setToDate(Date toDate)
	{
	    this.toDate = toDate;
	}
	public void setSchedId(BigDecimal schedId)
	{
	    this.schedId = schedId;
	}
	public BigDecimal getSchedId()
	{
	    return schedId;
	}
	public void setReportId(BigDecimal reportId)
	{
	    this.reportId = reportId;
	}
	public BigDecimal getReportId()
	{
	    return reportId;
	}
	
	
}
