package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;
import java.sql.Date;

import com.path.struts2.lib.common.GridParamsSC;

public class TmplGridSC  extends GridParamsSC
{
	private BigDecimal CURRENCY_CODE;
	private String ADDITIONAL_REFERENCE;
	private String BRIEF_DESC_ENG;
	private Date LONG_DESC_ENG;
	private String BRIEF_DESC_ARAB;
	private String LONG_DESC_ARAB;
	private BigDecimal COMP_CODE;
        private BigDecimal SUB_LINE_NO;
        private BigDecimal TEMPLATE_CODE;
        private BigDecimal TEMPLATE_LINE_NO;
        private BigDecimal LINE_NBR;
        private BigDecimal CRITERIA_CODE;
        boolean isGrid=true;
        private BigDecimal BRANCH_CODE;
        private BigDecimal DIV_CODE;
        private BigDecimal DEPT_CODE;
        private String TABLE_NAME;
        private String CODE_STR;
        private String CRITERIA_TYPE_CODE;
        private BigDecimal CODE;
	private BigDecimal SECTOR_CODE;//added by zahi 3 Feb 2012 because in filter criteria if you select an eco sector the sub eco sectors are not selected based on the eco sector
	private String LANG_CODE;
	private String relatedRepCodeParam="";
	private String selectAccParam="";
	private BigDecimal CODE1;
	private BigDecimal OPTION_CODE;
	private String REPORT_REF;
	private BigDecimal TEMPLATE_ORDER;
	private String FROM_COL;
	private String CB_NO;
	private BigDecimal BAS_EXPRESSION_CLASS_CODE;
	private BigDecimal CODE_CAF;
	private BigDecimal FIELD_SEQ;
	private String CAF_SQL_QUERY;
	
	
	
		

	public BigDecimal getCODE_CAF()
	{
	    return CODE_CAF;
	}

	public void setCODE_CAF(BigDecimal cODECAF)
	{
	    CODE_CAF = cODECAF;
	}

	public BigDecimal getFIELD_SEQ()
	{
	    return FIELD_SEQ;
	}

	public void setFIELD_SEQ(BigDecimal fIELDSEQ)
	{
	    FIELD_SEQ = fIELDSEQ;
	}

	public BigDecimal getBAS_EXPRESSION_CLASS_CODE()
	{
	    return BAS_EXPRESSION_CLASS_CODE;
	}

	public void setBAS_EXPRESSION_CLASS_CODE(BigDecimal bASEXPRESSIONCLASSCODE)
	{
	    BAS_EXPRESSION_CLASS_CODE = bASEXPRESSIONCLASSCODE;
	}

	public String getCB_NO()
	{
	    return CB_NO;
	}

	public void setCB_NO(String cBNO)
	{
	    CB_NO = cBNO;
	}

	public String getFROM_COL()
	{
	    return FROM_COL;
	}

	public void setFROM_COL(String fROMCOL)
	{
	    FROM_COL = fROMCOL;
	}

	public BigDecimal getTEMPLATE_ORDER()
	{
	    return TEMPLATE_ORDER;
	}

	public void setTEMPLATE_ORDER(BigDecimal tEMPLATEORDER)
	{
	    TEMPLATE_ORDER = tEMPLATEORDER;
	}

	public String getREPORT_REF()
	{
	    return REPORT_REF;
	}

	public void setREPORT_REF(String rEPORTREF)
	{
	    REPORT_REF = rEPORTREF;
	}

	public BigDecimal getOPTION_CODE()
	{
	    return OPTION_CODE;
	}







	public void setOPTION_CODE(BigDecimal oPTIONCODE)
	{
	    OPTION_CODE = oPTIONCODE;
	}







	public BigDecimal getCODE1()
	{
	    return CODE1;
	}







	public void setCODE1(BigDecimal cODE1)
	{
	    CODE1 = cODE1;
	}







	public String getSelectAccParam()
	{
	    return selectAccParam;
	}







	public void setSelectAccParam(String selectAccParam)
	{
	    this.selectAccParam = selectAccParam;
	}







	public TmplGridSC()
	{
	    super.setSearchCols(new String[] {"glstmpltVO.CODE","glstmpltVO.CURRENCY","glstmpltVO.ADD_DESC","glstmpltVO.ADD_DESC1","CODE", 
		    "BRIEF_DESC_ENG", "BRIEF_DESC_ARAB", "LONG_DESC_ENG", "LONG_DESC_ARAB","glstmpltVO.BRIEF_NAME_ENG"
		    ,"CRITERIA_DESCRIPTION","CRITERIA_CODE","CODE_STR", "BRIEF_DESC_ENG", "LONG_DESC_ARAB", "SECTOR_CODE"
		    ,"glstmpltVO.LINE_NBR","COMP_CODE"});
	}



	











	public String getRelatedRepCodeParam()
	{
	    return relatedRepCodeParam;
	}







	public void setRelatedRepCodeParam(String relatedRepCodeParam)
	{
	    this.relatedRepCodeParam = relatedRepCodeParam;
	}







	public BigDecimal getLINE_NBR()
	{
	    return LINE_NBR;
	}



	public void setLINE_NBR(BigDecimal lINENBR)
	{
	    LINE_NBR = lINENBR;
	}



	public BigDecimal getSECTOR_CODE() {
		return SECTOR_CODE;
	}



	public void setSECTOR_CODE(BigDecimal sECTORCODE) {
		SECTOR_CODE = sECTORCODE;
	}



	public BigDecimal getCURRENCY_CODE() {
		return CURRENCY_CODE;
	}



	public void setCURRENCY_CODE(BigDecimal cURRENCYCODE) {
		CURRENCY_CODE = cURRENCYCODE;
	}



	public String getADDITIONAL_REFERENCE() {
		return ADDITIONAL_REFERENCE;
	}



	public void setADDITIONAL_REFERENCE(String aDDITIONALREFERENCE) {
		ADDITIONAL_REFERENCE = aDDITIONALREFERENCE;
	}



	public String getBRIEF_DESC_ENG() {
		return BRIEF_DESC_ENG;
	}



	public void setBRIEF_DESC_ENG(String bRIEFDESCENG) {
		BRIEF_DESC_ENG = bRIEFDESCENG;
	}



	public Date getLONG_DESC_ENG() {
		return LONG_DESC_ENG;
	}



	public void setLONG_DESC_ENG(Date lONGDESCENG) {
		LONG_DESC_ENG = lONGDESCENG;
	}



	public String getBRIEF_DESC_ARAB() {
		return BRIEF_DESC_ARAB;
	}



	public void setBRIEF_DESC_ARAB(String bRIEFDESCARAB) {
		BRIEF_DESC_ARAB = bRIEFDESCARAB;
	}



	public String getLONG_DESC_ARAB() {
		return LONG_DESC_ARAB;
	}



	public void setLONG_DESC_ARAB(String lONGDESCARAB) {
		LONG_DESC_ARAB = lONGDESCARAB;
	}



	public BigDecimal getCOMP_CODE() {
		return COMP_CODE;
	}



	public void setCOMP_CODE(BigDecimal cOMPCODE) {
		COMP_CODE = cOMPCODE;
	}



	public BigDecimal getSUB_LINE_NO() {
		return SUB_LINE_NO;
	}



	public void setSUB_LINE_NO(BigDecimal sUBLINENO) {
		SUB_LINE_NO = sUBLINENO;
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



	public boolean isGrid() {
		return isGrid;
	}



	public void setGrid(boolean isGrid) {
		this.isGrid = isGrid;
	}



	public BigDecimal getCRITERIA_CODE() {
		return CRITERIA_CODE;
	}



	public void setCRITERIA_CODE(BigDecimal cRITERIACODE) {
		CRITERIA_CODE = cRITERIACODE;
	}



	public BigDecimal getBRANCH_CODE() {
		return BRANCH_CODE;
	}



	public void setBRANCH_CODE(BigDecimal bRANCHCODE) {
		BRANCH_CODE = bRANCHCODE;
	}



	public BigDecimal getDIV_CODE() {
		return DIV_CODE;
	}



	public void setDIV_CODE(BigDecimal dIVCODE) {
		DIV_CODE = dIVCODE;
	}



	public BigDecimal getDEPT_CODE() {
		return DEPT_CODE;
	}



	public void setDEPT_CODE(BigDecimal dEPTCODE) {
		DEPT_CODE = dEPTCODE;
	}



	public String getTABLE_NAME() {
		return TABLE_NAME;
	}



	public void setTABLE_NAME(String tABLENAME) {
		TABLE_NAME = tABLENAME;
	}



	public String getCODE_STR() {
		return CODE_STR;
	}



	public void setCODE_STR(String cODESTR) {
		CODE_STR = cODESTR;
	}



	public String getCRITERIA_TYPE_CODE() {
		return CRITERIA_TYPE_CODE;
	}



	public void setCRITERIA_TYPE_CODE(String cRITERIATYPECODE) {
		CRITERIA_TYPE_CODE = cRITERIATYPECODE;
	}



	public BigDecimal getCODE() {
		return CODE;
	}



	public void setCODE(BigDecimal cODE) {
		CODE = cODE;
	}
	public String getLANG_CODE() {
		return LANG_CODE;
	}

	public void setLANG_CODE(String lANGCODE) {
		LANG_CODE = lANGCODE;
	}
	
	public String getCAF_SQL_QUERY() {
		return CAF_SQL_QUERY;
	}

	public void setCAF_SQL_QUERY(String cAF_SQL_QUERY) {
		CAF_SQL_QUERY = cAF_SQL_QUERY;
	}
}
