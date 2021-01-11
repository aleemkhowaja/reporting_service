package com.path.vo.reporting.scheduler;

import java.util.List;

import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.lib.vo.BaseVO;

public class IRP_SCHEDULECO extends BaseVO {
    
    	private IRP_SCHEDULEVO SCHED_VO = new IRP_SCHEDULEVO();
	
	private List<IRP_SCHED_DETAILSCO> scheduleDetails;
	
	private String FIRST_RUN_DATE_STR;
	
	private String SCHED_EXPIRY_DATE_STR;
	
	private String NEXT_RUN_DATE_STR;
	
	private int LOG_COUNT;
	private String SCHED_TYPE_STR;
	private String BATCH_BRIEF_NAME;
	private String EVT_BRIEF_NAME;
	
	public String getEVT_BRIEF_NAME() 
	{
		return EVT_BRIEF_NAME;
	}

	public void setEVT_BRIEF_NAME(String eVT_BRIEF_NAME) 
	{
		EVT_BRIEF_NAME = eVT_BRIEF_NAME;
	}

	public String getBATCH_BRIEF_NAME()
	{
	    return BATCH_BRIEF_NAME;
	}

	public void setBATCH_BRIEF_NAME(String bATCH_BRIEF_NAME)
	{
	    BATCH_BRIEF_NAME = bATCH_BRIEF_NAME;
	}

	public String getSCHED_TYPE_STR()
	{
	    return SCHED_TYPE_STR;
	}

	public void setSCHED_TYPE_STR(String sCHEDTYPESTR)
	{
	    SCHED_TYPE_STR = sCHEDTYPESTR;
	}

	public IRP_SCHEDULEVO getSCHED_VO()
	{
	    return SCHED_VO;
	}

	public void setSCHED_VO(IRP_SCHEDULEVO sCHEDVO)
	{
	    SCHED_VO = sCHEDVO;
	}
	public List<IRP_SCHED_DETAILSCO> getScheduleDetails() {
		return scheduleDetails;
	}

	public void setScheduleDetails(List<IRP_SCHED_DETAILSCO> scheduleDetails) {
		this.scheduleDetails = scheduleDetails;
	}

	public String getFIRST_RUN_DATE_STR() {
		return FIRST_RUN_DATE_STR;
	}

	public void setFIRST_RUN_DATE_STR(String fIRSTRUNDATESTR) {
		FIRST_RUN_DATE_STR = fIRSTRUNDATESTR;
	}

	public String getSCHED_EXPIRY_DATE_STR() {
		return SCHED_EXPIRY_DATE_STR;
	}

	public void setSCHED_EXPIRY_DATE_STR(String sCHEDEXPIRYDATESTR) {
		SCHED_EXPIRY_DATE_STR = sCHEDEXPIRYDATESTR;
	}

	public String getNEXT_RUN_DATE_STR() {
		return NEXT_RUN_DATE_STR;
	}

	public void setNEXT_RUN_DATE_STR(String nEXTRUNDATESTR) {
		NEXT_RUN_DATE_STR = nEXTRUNDATESTR;
	}

	public int getLOG_COUNT() {
		return LOG_COUNT;
	}

	public void setLOG_COUNT(int lOGCOUNT) {
		LOG_COUNT = lOGCOUNT;
	}
	
}
