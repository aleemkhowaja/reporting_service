package com.path.vo.reporting.reports;

import com.path.dbmaps.vo.IRP_SNAPSHOTSVO;

public class IRP_SNAPSHOTSCO extends IRP_SNAPSHOTSVO
{
	private String EXECUTION_DATE_STR;
	private String status;
	private String REPORT_FORMAT_TRANS; 
	
	public String getREPORT_FORMAT_TRANS()
	{
	    return REPORT_FORMAT_TRANS;
	}
	public void setREPORT_FORMAT_TRANS(String rEPORTFORMATTRANS)
	{
	    REPORT_FORMAT_TRANS = rEPORTFORMATTRANS;
	}
	public String getStatus()
	{
	    return status;
	}
	public void setStatus(String status)
	{
	    this.status = status;
	}
	public String getEXECUTION_DATE_STR() {
		return EXECUTION_DATE_STR;
	}
	public void setEXECUTION_DATE_STR(String eXECUTIONDATESTR) {
		EXECUTION_DATE_STR = eXECUTIONDATESTR;
	}
	
}
