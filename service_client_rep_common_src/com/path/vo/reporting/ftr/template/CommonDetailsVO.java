package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.lib.vo.BaseVO;

public class CommonDetailsVO extends BaseVO 
{
	private BigDecimal CODE;
	private String CODE_STR;
	private String BRIEF_DESC_ENG;
	private String BRIEF_DESC_ARAB;
	private String LONG_DESC_ENG;
	private String LONG_DESC_ARAB;
	private BigDecimal SECTOR_CODE;
	
	private String ADDITIONAL_REFERENCE;
	private String BAS_EXPRESSION_CLASS_CODE;
	private String BAS_EXPRESSION_CLASS_DESC;
	private String BRIEF_DESC_AR;
	private String CATEGORY;
	private String CONTINENT;
	private String FROM_BRANCH;
	private String FROM_CIF;
	private String FROM_CY;
	private String FROM_GL;
	private String FROM_SL;
	private String SECURITY_TYPE;
	private String SERIAL;
	private String STATUS; 
	private String TRADING_COMPANY;
	private String USEFUL_LIFE;
	private String TO_BRANCH;
	private String TO_CIF;
	private String TO_CY;
	private String TO_GL;
	private String TYPE;

	 private String CRITERIA_DESCRIPTION;
	 private String COMPONENT;
	 private String TABLE_NAME1;
	 private String TABLE_NAME2;
	 private String CRITERIA_TYPE_CODE;
	 private BigDecimal COMP_CODE;
	 private BigDecimal CRITERIA_CODE;
	 
	 private BigDecimal SUB_LINE_NO;
	 
	private String PROG_REF;
	private String PROG_DESC;
	private String MENU_TITLE_ENG;
	private String APPL_NAME;
	private String REMARK;
	private BigDecimal BASE_CURRENCY;
	private BigDecimal COMPANY_CODE;
	private String COMPANY_NAME;
	private BigDecimal DIVISION_CODE;
	private String DIVISION_NAME;
	private String GL_TYPE;
	private String GL_CATEGORY;
	private String DEPRECIATION_METHOD;
	
	
	public String getDEPRECIATION_METHOD()
	{
	    return DEPRECIATION_METHOD;
	}
	public void setDEPRECIATION_METHOD(String dEPRECIATIONMETHOD)
	{
	    DEPRECIATION_METHOD = dEPRECIATIONMETHOD;
	}
	public String getADDITIONAL_REFERENCE()
	{
	    return ADDITIONAL_REFERENCE;
	}
	public void setADDITIONAL_REFERENCE(String aDDITIONALREFERENCE)
	{
	    ADDITIONAL_REFERENCE = aDDITIONALREFERENCE;
	}
	public String getBAS_EXPRESSION_CLASS_CODE()
	{
	    return BAS_EXPRESSION_CLASS_CODE;
	}
	public void setBAS_EXPRESSION_CLASS_CODE(String bASEXPRESSIONCLASSCODE)
	{
	    BAS_EXPRESSION_CLASS_CODE = bASEXPRESSIONCLASSCODE;
	}
	public String getBAS_EXPRESSION_CLASS_DESC()
	{
	    return BAS_EXPRESSION_CLASS_DESC;
	}
	public void setBAS_EXPRESSION_CLASS_DESC(String bASEXPRESSIONCLASSDESC)
	{
	    BAS_EXPRESSION_CLASS_DESC = bASEXPRESSIONCLASSDESC;
	}
	public String getBRIEF_DESC_AR()
	{
	    return BRIEF_DESC_AR;
	}
	public void setBRIEF_DESC_AR(String bRIEFDESCAR)
	{
	    BRIEF_DESC_AR = bRIEFDESCAR;
	}
	public String getCATEGORY()
	{
	    return CATEGORY;
	}
	public void setCATEGORY(String cATEGORY)
	{
	    CATEGORY = cATEGORY;
	}
	public String getCONTINENT()
	{
	    return CONTINENT;
	}
	public void setCONTINENT(String cONTINENT)
	{
	    CONTINENT = cONTINENT;
	}
	public String getFROM_BRANCH()
	{
	    return FROM_BRANCH;
	}
	public void setFROM_BRANCH(String fROMBRANCH)
	{
	    FROM_BRANCH = fROMBRANCH;
	}
	public String getFROM_CIF()
	{
	    return FROM_CIF;
	}
	public void setFROM_CIF(String fROMCIF)
	{
	    FROM_CIF = fROMCIF;
	}
	public String getFROM_CY()
	{
	    return FROM_CY;
	}
	public void setFROM_CY(String fROMCY)
	{
	    FROM_CY = fROMCY;
	}
	public String getFROM_GL()
	{
	    return FROM_GL;
	}
	public void setFROM_GL(String fROMGL)
	{
	    FROM_GL = fROMGL;
	}
	public String getFROM_SL()
	{
	    return FROM_SL;
	}
	public void setFROM_SL(String fROMSL)
	{
	    FROM_SL = fROMSL;
	}
	public String getSECURITY_TYPE()
	{
	    return SECURITY_TYPE;
	}
	public void setSECURITY_TYPE(String sECURITYTYPE)
	{
	    SECURITY_TYPE = sECURITYTYPE;
	}
	public String getSERIAL()
	{
	    return SERIAL;
	}
	public void setSERIAL(String sERIAL)
	{
	    SERIAL = sERIAL;
	}
	public String getSTATUS()
	{
	    return STATUS;
	}
	public void setSTATUS(String sTATUS)
	{
	    STATUS = sTATUS;
	}
	public String getTRADING_COMPANY()
	{
	    return TRADING_COMPANY;
	}
	public void setTRADING_COMPANY(String tRADINGCOMPANY)
	{
	    TRADING_COMPANY = tRADINGCOMPANY;
	}
	public String getUSEFUL_LIFE()
	{
	    return USEFUL_LIFE;
	}
	public void setUSEFUL_LIFE(String uSEFULLIFE)
	{
	    USEFUL_LIFE = uSEFULLIFE;
	}
	public String getTO_BRANCH()
	{
	    return TO_BRANCH;
	}
	public void setTO_BRANCH(String tOBRANCH)
	{
	    TO_BRANCH = tOBRANCH;
	}
	public String getTO_CIF()
	{
	    return TO_CIF;
	}
	public void setTO_CIF(String tOCIF)
	{
	    TO_CIF = tOCIF;
	}
	public String getTO_CY()
	{
	    return TO_CY;
	}
	public void setTO_CY(String tOCY)
	{
	    TO_CY = tOCY;
	}
	public String getTO_GL()
	{
	    return TO_GL;
	}
	public void setTO_GL(String tOGL)
	{
	    TO_GL = tOGL;
	}
	public String getTYPE()
	{
	    return TYPE;
	}
	public void setTYPE(String tYPE)
	{
	    TYPE = tYPE;
	}
	public String getGL_TYPE()
	{
	    return GL_TYPE;
	}
	public void setGL_TYPE(String gLTYPE)
	{
	    GL_TYPE = gLTYPE;
	}
	public String getGL_CATEGORY()
	{
	    return GL_CATEGORY;
	}
	public void setGL_CATEGORY(String gLCATEGORY)
	{
	    GL_CATEGORY = gLCATEGORY;
	}
	public BigDecimal getCOMPANY_CODE()
	{
	    return COMPANY_CODE;
	}
	public void setCOMPANY_CODE(BigDecimal cOMPANYCODE)
	{
	    COMPANY_CODE = cOMPANYCODE;
	}
	public String getCOMPANY_NAME()
	{
	    return COMPANY_NAME;
	}
	public void setCOMPANY_NAME(String cOMPANYNAME)
	{
	    COMPANY_NAME = cOMPANYNAME;
	}
	public BigDecimal getDIVISION_CODE()
	{
	    return DIVISION_CODE;
	}
	public void setDIVISION_CODE(BigDecimal dIVISIONCODE)
	{
	    DIVISION_CODE = dIVISIONCODE;
	}
	public String getDIVISION_NAME()
	{
	    return DIVISION_NAME;
	}
	public void setDIVISION_NAME(String dIVISIONNAME)
	{
	    DIVISION_NAME = dIVISIONNAME;
	}

	public String getREMARK()
	{
	    return REMARK;
	}
	public void setREMARK(String rEMARK)
	{
	    REMARK = rEMARK;
	}
	public BigDecimal getBASE_CURRENCY()
	{
	    return BASE_CURRENCY;
	}
	public void setBASE_CURRENCY(BigDecimal bASECURRENCY)
	{
	    BASE_CURRENCY = bASECURRENCY;
	}
	public String getAPPL_NAME() {
		return APPL_NAME;
	}
	public void setAPPL_NAME(String aPPLNAME) {
		APPL_NAME = aPPLNAME;
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
	public String getCRITERIA_DESCRIPTION() {
		return CRITERIA_DESCRIPTION;
	}
	public void setCRITERIA_DESCRIPTION(String cRITERIADESCRIPTION) {
		CRITERIA_DESCRIPTION = cRITERIADESCRIPTION;
	}
	public String getCRITERIA_TYPE_CODE() {
		return CRITERIA_TYPE_CODE;
	}
	public void setCRITERIA_TYPE_CODE(String cRITERIATYPECODE) {
		CRITERIA_TYPE_CODE = cRITERIATYPECODE;
	}
	public BigDecimal getCOMP_CODE() {
		return COMP_CODE;
	}
	public void setCOMP_CODE(BigDecimal cOMPCODE) {
		COMP_CODE = cOMPCODE;
	}
	public BigDecimal getCRITERIA_CODE() {
		return CRITERIA_CODE;
	}
	public void setCRITERIA_CODE(BigDecimal cRITERIACODE) {
		CRITERIA_CODE = cRITERIACODE;
	}
	public BigDecimal getSUB_LINE_NO() {
		return SUB_LINE_NO;
	}
	public void setSUB_LINE_NO(BigDecimal sUBLINENO) {
		SUB_LINE_NO = sUBLINENO;
	}
	public String getCOMPONENT() {
		return COMPONENT;
	}
	public void setCOMPONENT(String cOMPONENT) {
		COMPONENT = cOMPONENT;
	}
	public String getTABLE_NAME1() {
		return TABLE_NAME1;
	}
	public void setTABLE_NAME1(String tABLENAME1) {
		TABLE_NAME1 = tABLENAME1;
	}
	public String getTABLE_NAME2() {
		return TABLE_NAME2;
	}
	public void setTABLE_NAME2(String tABLENAME2) {
		TABLE_NAME2 = tABLENAME2;
	}
	public String getCODE_STR() {
		return CODE_STR;
	}
	public void setCODE_STR(String cODESTR) {
		CODE_STR = cODESTR;
	}
	public String getPROG_REF() {
		return PROG_REF;
	}
	public void setPROG_REF(String pROGREF) {
		PROG_REF = pROGREF;
	}
	public String getPROG_DESC() {
		return PROG_DESC;
	}
	public void setPROG_DESC(String pROGDESC) {
		PROG_DESC = pROGDESC;
	}
	public String getMENU_TITLE_ENG() {
		return MENU_TITLE_ENG;
	}
	public void setMENU_TITLE_ENG(String mENUTITLEENG) {
		MENU_TITLE_ENG = mENUTITLEENG;
	}
	public BigDecimal getSECTOR_CODE() {
		return SECTOR_CODE;
	}
	public void setSECTOR_CODE(BigDecimal sECTORCODE) {
		SECTOR_CODE = sECTORCODE;
	}

	
	
}
