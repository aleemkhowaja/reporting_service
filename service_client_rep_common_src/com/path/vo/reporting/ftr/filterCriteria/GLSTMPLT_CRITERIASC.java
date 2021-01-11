package com.path.vo.reporting.ftr.filterCriteria;

import java.math.BigDecimal;
import com.path.struts2.lib.common.GridParamsSC;

public class GLSTMPLT_CRITERIASC extends GridParamsSC {
	
	private BigDecimal CODE;
	private BigDecimal COMP_CODE;
	private String LANG_CODE;

	public GLSTMPLT_CRITERIASC(){
		super.setSearchCols(new String[] { "COMP_CODE" , "glstmpltCriteriaVO.CODE","CRITERIA_TYPE_DESCRIPTION","glstmpltCriteriaVO.BRIEF_DESC_ENG","glstmpltCriteriaVO.LONG_DESC_ENG","glstmpltCriteriaVO.BRIEF_DESC_ARAB","glstmpltCriteriaVO.LONG_DESC_ARAB","glstmpltCriteriaVO.CRI_TYPE"});
	}

	public BigDecimal getCOMP_CODE() {
		return COMP_CODE;
	}

	public void setCOMP_CODE(BigDecimal cOMPCODE) {
		COMP_CODE = cOMPCODE;
	}

	public BigDecimal getCODE() {
		return CODE;
	}

	public void setCODE(BigDecimal CODE) {
	    this.CODE = CODE;
	}

	public String getLANG_CODE()
	{
	    return LANG_CODE;
	}

	public void setLANG_CODE(String lANGCODE)
	{
	    LANG_CODE = lANGCODE;
	}	
	
}
