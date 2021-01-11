package com.path.dbmaps.vo.schedulerdateprocess;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.path.struts2.lib.common.GridParamsSC;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * SchedulerDateProcessSC.java used to
 */
public class SchedulerDateProcessSC extends GridParamsSC
{
    	private String procType;
    	private BigDecimal periodicDate;
    	private String periodType;
    	private Date asOfDate;
    	private Date fromDate;
    	private Date toDate;
    	private List<String> schedIds;
    	private String dateStr;
    	private String dateType;
   	private ArrayList<SchedulerDateProcessCO> procList;

    	   public SchedulerDateProcessSC()
    	    {
    		super.setSearchCols(new String[] { "SCHED_ID", "SCHED_NAME", "REPORT_NAME", "REPORT_FORMAT_TRANS"});
    	    }
    	   
	 public String getProcType()
	{
	    return procType;
	}
	public void setProcType(String procType)
	{
	    this.procType = procType;
	}
	
	 public BigDecimal getPeriodicDate()
	{
	    return periodicDate;
	}
	public void setPeriodicDate(BigDecimal periodicDate)
	{
	    this.periodicDate = periodicDate;
	}
	
	 public String getPeriodType()
	{
	    return periodType;
	}
	public void setPeriodType(String periodType)
	{
	    this.periodType = periodType;
	}
	
	
	 public Date getAsOfDate()
	{
	    return asOfDate;
	}
	public void setAsOfDate(Date asOfDate)
	{
	    this.asOfDate = asOfDate;
	}
	
	 public Date getFromDate()
	{
	    return fromDate;
	}
	public void setFromDate(Date fromDate)
	{
	    this.fromDate = fromDate;
	}
	
	public Date getToDate()
	{
	    return toDate;
	}
	public void setToDate(Date toDate)
	{
	    this.toDate = toDate;
	}
	
	public List<String> getSchedIds()
	{
	    return schedIds;
	}
	public void setSchedIds(List<String> schedIds)
	{
	    this.schedIds = schedIds;
	}
	
	private String langCode;
	public void setLangCode(String langCode)
	{
	    this.langCode = langCode;
	}
	public String getLangCode()
	{
	    return langCode;
	}
	private BigDecimal lovTypeId;
	public BigDecimal getLovTypeId()
	{
	    return lovTypeId;
	}
	public void setLovTypeId(BigDecimal lovTypeId)
	{
	    this.lovTypeId = lovTypeId;
	}
	
	
	private List<String> dateValue;
	
	public void setDateValue(List<String> dateValue)
	{
	    this.dateValue = dateValue;
	}
	public List<String> getDateValue()
	{
	    return dateValue;
	}

	public void setProcList(ArrayList<SchedulerDateProcessCO> procList)
	{
	    this.procList = procList;
	}

	public ArrayList<SchedulerDateProcessCO> getProcList()
	{
	    return procList;
	}

	public void setDateStr(String dateStr)
	{
	    this.dateStr = dateStr;
	}

	public String getDateStr()
	{
	    return dateStr;
	}

	public void setDateType(String dateType)
	{
	    this.dateType = dateType;
	}

	public String getDateType()
	{
	    return dateType;
	}



	
	
	
}
