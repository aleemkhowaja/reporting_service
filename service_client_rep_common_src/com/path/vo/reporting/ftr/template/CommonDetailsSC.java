package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class CommonDetailsSC extends GridParamsSC
{
	private BigDecimal CODE;
	private String BRIEF_DESC_ENG;
	private String BRIEF_DESC_ARAB;
	private String LONG_DESC_ENG;
	private String LONG_DESC_ARAB;
	private BigDecimal COMP_CODE;
	private BigDecimal GL_CODE;
	private BigDecimal DIV_CODE;
	private String appName;
	boolean isGrid=true;
	
	//added by zahi
	private BigDecimal TO_DIV_CODE;
	private BigDecimal DEPT_CODE;
	private BigDecimal UNIT_CODE;
	//end added by zahi


	
	private BigDecimal TEMPLATE_CODE;
	private BigDecimal TEMPLATE_LINE_NO;

	private String PROG_REF;
        private String profType;
        private String AS_OF_DATE;
        private String TRADE_DATE_VAL;
        private String CORE_IMAL_YN;
        
        public String getCORE_IMAL_YN()
	{
	    return CORE_IMAL_YN;
	}

	public void setCORE_IMAL_YN(String cORE_IMAL_YN)
	{
	    CORE_IMAL_YN = cORE_IMAL_YN;
	}

        
        
        
    
        public String getTRADE_DATE_VAL()
	{
	    return TRADE_DATE_VAL;
	}

	public void setTRADE_DATE_VAL(String tRADEDATEVAL)
	{
	    TRADE_DATE_VAL = tRADEDATEVAL;
	}

	public String getAS_OF_DATE()
	{
	    return AS_OF_DATE;
	}

	public void setAS_OF_DATE(String aSOFDATE)
	{
	    AS_OF_DATE = aSOFDATE;
	}

        /**
         * @return the profType
         */
        public String getProfType()
        {
    	return profType;
        }
    
        /**
         * @param profType the profType to set
         */
        public void setProfType(String profType)
        {
    	this.profType = profType;
        }
        
	public CommonDetailsSC()
	{
		super.setSearchCols(new String[] {"CODE","BRIEF_DESC_ENG","BRIEF_DESC_ARAB","LONG_DESC_ENG","LONG_DESC_ARAB","PROG_REF"
			,"PROG_DESC","MENU_TITLE_ENG","BRIEF_NAME_ENG","BRIEF_NAME_ARAB","DISP_ORDER","PARENT_REF_STR","TMPLT_CODE_STR"
			,"COLUMN_CODE_STR","FOLDER_REF","ftrOptVO.BRIEF_NAME_ENG","ftrOptVO.BRIEF_NAME_ARAB","ftrOptVO.PROG_REF"
			,"ftrOptVO.DISP_ORDER"});
	}

	
	public BigDecimal getCOMP_CODE() {
		return COMP_CODE;
	}


	public void setCOMP_CODE(BigDecimal cOMPCODE) {
		COMP_CODE = cOMPCODE;
	}


	public BigDecimal getGL_CODE() {
		return GL_CODE;
	}


	public void setGL_CODE(BigDecimal gLCODE) {
		GL_CODE = gLCODE;
	}


	public BigDecimal getCODE() {
		return CODE;
	}
	public void setCODE(BigDecimal cODE) {
		CODE = cODE;
	}
	public String getBRIEF_DESC_ENG() {
		return BRIEF_DESC_ENG;
	}
	public void setBRIEF_DESC_ENG(String bRIEFDESCENG) {
		BRIEF_DESC_ENG = bRIEFDESCENG;
	}
	public String getBRIEF_DESC_ARAB() {
		return BRIEF_DESC_ARAB;
	}
	public void setBRIEF_DESC_ARAB(String bRIEFDESCARAB) {
		BRIEF_DESC_ARAB = bRIEFDESCARAB;
	}
	public String getLONG_DESC_ENG() {
		return LONG_DESC_ENG;
	}
	public void setLONG_DESC_ENG(String lONGDESCENG) {
		LONG_DESC_ENG = lONGDESCENG;
	}
	public String getLONG_DESC_ARAB() {
		return LONG_DESC_ARAB;
	}
	public void setLONG_DESC_ARAB(String lONGDESCARAB) {
		LONG_DESC_ARAB = lONGDESCARAB;
	}


	public BigDecimal getDIV_CODE() {
		return DIV_CODE;
	}


	public void setDIV_CODE(BigDecimal dIVCODE) {
		DIV_CODE = dIVCODE;
	}


	public BigDecimal getTEMPLATE_CODE() {
		return TEMPLATE_CODE;
	}


	public void setTEMPLATE_CODE(BigDecimal tEMPLATECODE) {
		TEMPLATE_CODE = tEMPLATECODE;
	}


	public BigDecimal getTEMPLATE_LINE_NO() {
		return TEMPLATE_LINE_NO;
	}


	public void setTEMPLATE_LINE_NO(BigDecimal tEMPLATELINENO) {
		TEMPLATE_LINE_NO = tEMPLATELINENO;
	}

	public BigDecimal getUNIT_CODE() {
		return UNIT_CODE;
	}

	public void setUNIT_CODE(BigDecimal uNITCODE) {
		UNIT_CODE = uNITCODE;
	}

	public BigDecimal getDEPT_CODE() {
		return DEPT_CODE;
	}

	public void setDEPT_CODE(BigDecimal dEPTCODE) {
		DEPT_CODE = dEPTCODE;
	}

	public BigDecimal getTO_DIV_CODE() {
		return TO_DIV_CODE;
	}

	public void setTO_DIV_CODE(BigDecimal tODIVCODE) {
		TO_DIV_CODE = tODIVCODE;
	}


	public boolean isGrid() {
		return isGrid;
	}


	public void setGrid(boolean isGrid) {
		this.isGrid = isGrid;
	}


	public String getPROG_REF() {
		return PROG_REF;
	}


	public void setPROG_REF(String pROGREF) {
		PROG_REF = pROGREF;
	}


	public String getAppName() {
		return appName;
	}


	public void setAppName(String appName) {
		this.appName = appName;
	}
	
}
