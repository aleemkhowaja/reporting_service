package com.path.vo.reporting.reports;

import java.math.BigDecimal;
import java.sql.Connection;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_SNAPSHOTSSC extends GridParamsSC
{
	 private String EXECUTED_BY;
	 private BigDecimal SNAPSHOT_ID;
	 private BigDecimal IS_DB;
	 private BigDecimal CONN_ID;
	 private Connection con; 
	 private BigDecimal eod;
	 private String LANG_CODE;
	 private BigDecimal FILE_FORMAT_LOV_TYPE_ID;
	 
	public BigDecimal getFILE_FORMAT_LOV_TYPE_ID()
	{
	    return FILE_FORMAT_LOV_TYPE_ID;
	}
	public void setFILE_FORMAT_LOV_TYPE_ID(BigDecimal fILEFORMATLOVTYPEID)
	{
	    FILE_FORMAT_LOV_TYPE_ID = fILEFORMATLOVTYPEID;
	}
	public String getLANG_CODE()
	{
	    return LANG_CODE;
	}
	public void setLANG_CODE(String lANGCODE)
	{
	    LANG_CODE = lANGCODE;
	}
	public BigDecimal getEod()
	{
	    return eod;
	}
	public void setEod(BigDecimal eod)
	{
	    this.eod = eod;
	}
	public Connection getCon()
	{
	    return con;
	}
	public void setCon(Connection con)
	{
	    this.con = con;
	}
	public BigDecimal getCONN_ID()
	{
	    return CONN_ID;
	}
	public void setCONN_ID(BigDecimal cONNID)
	{
	    CONN_ID = cONNID;
	}
	public BigDecimal getIS_DB()
	{
	    return IS_DB;
	}
	public void setIS_DB(BigDecimal iSDB)
	{
	    IS_DB = iSDB;
	}
	public IRP_SNAPSHOTSSC()
	{
	    super.setSearchCols(new String[] {"REPORT_NAME","APP_NAME","REPORT_FORMAT","EXECUTION_DATE_STR","BRANCH_CODE","REPORT_FORMAT_TRANS"});
	}
	public BigDecimal getSNAPSHOT_ID() {
		return SNAPSHOT_ID;
	}

	public void setSNAPSHOT_ID(BigDecimal sNAPSHOTID) {
		SNAPSHOT_ID = sNAPSHOTID;
	}

	public String getEXECUTED_BY() {
		return EXECUTED_BY;
	}

	public void setEXECUTED_BY(String eXECUTEDBY) {
		EXECUTED_BY = eXECUTEDBY;
	}
	 
	 
}
