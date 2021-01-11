package com.path.vo.reporting.ftr.filterCriteria;

import java.util.HashMap;

import com.path.dbmaps.vo.GLSTMPLT_CRITERIAVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;

public class GLSTMPLT_CRITERIACO extends BaseVO {
	private String CRITERIA_TYPE_DESCRIPTION;
	private GLSTMPLT_CRITERIAVO glstmpltCriteriaVO = new GLSTMPLT_CRITERIAVO();
	private String SMART_BRIEF_NAME_ENG;
	private String smartInput;
	private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm; 
	private String serializedStrHm;
	
	
	
	public String getSerializedStrHm()
	{
	    return serializedStrHm;
	}

	public void setSerializedStrHm(String serializedStrHm)
	{
	    this.serializedStrHm = serializedStrHm;
	}

	public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getBusinessHm()
	{
	    return businessHm;
	}

	public void setBusinessHm(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm)
	{
	    this.businessHm = businessHm;
	}

	public String getSmartInput()
	{
	    return smartInput;
	}

	public void setSmartInput(String smartInput)
	{
	    this.smartInput = smartInput;
	}

	public String getSMART_BRIEF_NAME_ENG()
	{
	    return SMART_BRIEF_NAME_ENG;
	}

	public void setSMART_BRIEF_NAME_ENG(String sMARTBRIEFNAMEENG)
	{
	    SMART_BRIEF_NAME_ENG = sMARTBRIEFNAMEENG;
	}

	public String getCRITERIA_TYPE_DESCRIPTION() {
		return CRITERIA_TYPE_DESCRIPTION;
	}

	public GLSTMPLT_CRITERIAVO getGlstmpltCriteriaVO()
	{
	    return glstmpltCriteriaVO;
	}

	public void setGlstmpltCriteriaVO(GLSTMPLT_CRITERIAVO glstmpltCriteriaVO)
	{
	    this.glstmpltCriteriaVO = glstmpltCriteriaVO;
	}

	public void setCRITERIA_TYPE_DESCRIPTION(String cRITERIATYPEDESCRIPTION) {
		CRITERIA_TYPE_DESCRIPTION = cRITERIATYPEDESCRIPTION;
	}
}
