package com.path.vo.reporting.scheduler;

import java.math.BigDecimal;
import java.util.Date;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_SCHEDULESC extends GridParamsSC
{
	private BigDecimal SCHED_ID;
	private Date NEXT_RUN_DATE;
	private BigDecimal SCHED_TYPE_LOV_TYPE_ID;
	private String LANG_CODE;
	private BigDecimal alertTypeSchedule; 
	private String alertTypeValueCode;
	private String tableName;
	private String tableQuery;
	
	public String getAlertTypeValueCode() 
	{
		return alertTypeValueCode;
	}

	public void setAlertTypeValueCode(String alertTypeValueCode)
	{
		this.alertTypeValueCode = alertTypeValueCode;
	}

	public BigDecimal getAlertTypeSchedule() 
	{
		return alertTypeSchedule;
	}

	public void setAlertTypeSchedule(BigDecimal alertTypeSchedule)
	{
		this.alertTypeSchedule = alertTypeSchedule;
	}

	public Date getNEXT_RUN_DATE() {
		return NEXT_RUN_DATE;
	}

	public void setNEXT_RUN_DATE(Date nEXTRUNDATE) {
		NEXT_RUN_DATE = nEXTRUNDATE;
	}

	public IRP_SCHEDULESC()
	{
		super.setSearchCols(new String[] {"SCHED_VO.SCHED_ID","SCHED_VO.SCHED_NAME","SCHED_EXPIRY_DATE_STR","NEXT_RUN_DATE_STR",
				"EVT_ID", "DESC_ENG"});
	}

	public BigDecimal getSCHED_ID() {
		return SCHED_ID;
	}

	public void setSCHED_ID(BigDecimal sCHEDID) {
		SCHED_ID = sCHEDID;
	}
	public BigDecimal getSCHED_TYPE_LOV_TYPE_ID()
	{
	  return SCHED_TYPE_LOV_TYPE_ID;
	}

	public void setSCHED_TYPE_LOV_TYPE_ID(BigDecimal sCHEDTYPELOVTYPEID)
	{
	 SCHED_TYPE_LOV_TYPE_ID = sCHEDTYPELOVTYPEID;
	 }

	    public String getLANG_CODE()
	    {
		return LANG_CODE;
	    }

	    public void setLANG_CODE(String lANGCODE)
	    {
		LANG_CODE = lANGCODE;
	    }

		public String getTableName() {
			return tableName;
		}

		public void setTableName(String tableName) {
			this.tableName = tableName;
		}

		public String getTableQuery() {
			return tableQuery;
		}

		public void setTableQuery(String tableQuery) {
			this.tableQuery = tableQuery;
		}
	    
	
}
