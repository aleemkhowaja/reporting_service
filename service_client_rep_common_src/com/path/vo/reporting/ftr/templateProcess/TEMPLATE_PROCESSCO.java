package com.path.vo.reporting.ftr.templateProcess;

import java.math.BigDecimal;
import java.util.Date;

import com.path.lib.vo.BaseVO;

public class TEMPLATE_PROCESSCO extends BaseVO 
{
	 private BigDecimal fromTempl;
	 private BigDecimal toTempl;
	 private String fromTemplStr;
	 private String toTemplStr;
	 private String procType;
	 private BigDecimal periodicDate;
	 private String periodType;
	 private Date asOfDate;
	 private Date fromDate;
	 private Date toDate;
	public BigDecimal getFromTempl() {
		return fromTempl;
	}
	public void setFromTempl(BigDecimal fromTempl) {
		this.fromTempl = fromTempl;
	}
	public BigDecimal getToTempl() {
		return toTempl;
	}
	public void setToTempl(BigDecimal toTempl) {
		this.toTempl = toTempl;
	}
	public String getFromTemplStr() {
		return fromTemplStr;
	}
	public void setFromTemplStr(String fromTemplStr) {
		this.fromTemplStr = fromTemplStr;
	}
	public String getToTemplStr() {
		return toTemplStr;
	}
	public void setToTemplStr(String toTemplStr) {
		this.toTemplStr = toTemplStr;
	}
	public String getProcType() {
		return procType;
	}
	public void setProcType(String procType) {
		this.procType = procType;
	}
	public Date getAsOfDate() {
		return asOfDate;
	}
	public void setAsOfDate(Date asOfDate) {
		this.asOfDate = asOfDate;
	}
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
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
}
