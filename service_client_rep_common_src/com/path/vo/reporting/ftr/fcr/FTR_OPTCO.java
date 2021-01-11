package com.path.vo.reporting.ftr.fcr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.path.dbmaps.vo.FTR_OPTVO;
import com.path.lib.vo.BaseVO;

public class FTR_OPTCO extends BaseVO
{
    	private FTR_OPTVO ftrOptVO = new FTR_OPTVO();
	private String PARENT_REF_STR;
	private String TMPLT_CODE_STR;
	private String COLUMN_CODE_STR;
	private String LS_REF;
	private BigDecimal PROG_ORDER ;
	private String LS_PRINT;
	private String LS_SAV;
	private String LS_UPPER_NAME;
	private List<String> PROG_REFS;
	private BigDecimal ORIENTATION;
	private String DISPLAY_VALUES;
	private Date RUNNING_DATE;
	private String APPL_NAME;
	private String CURRENCY_STR;
	private BigDecimal REPORT_ID;
	
	public BigDecimal getREPORT_ID()
	{
	    return REPORT_ID;
	}

	public void setREPORT_ID(BigDecimal rEPORTID)
	{
	    REPORT_ID = rEPORTID;
	}

	public String getCURRENCY_STR()
	{
	    return CURRENCY_STR;
	}

	public void setCURRENCY_STR(String cURRENCYSTR)
	{
	    CURRENCY_STR = cURRENCYSTR;
	}

	public String getAPPL_NAME()
	{
	    return APPL_NAME;
	}

	public void setAPPL_NAME(String aPPLNAME)
	{
	    APPL_NAME = aPPLNAME;
	}

	public FTR_OPTVO getFtrOptVO()
	{
	    return ftrOptVO;
	}

	public void setFtrOptVO(FTR_OPTVO ftrOptVO)
	{
	    this.ftrOptVO = ftrOptVO;
	}

	public boolean getValueFlag()
	{
	    return (BigDecimal.ONE).equals(ftrOptVO.getROW_COLMN())? true : false;
	}

	public void setValueFlag(boolean valueFlag)
	{
	    ftrOptVO.setROW_COLMN(valueFlag? BigDecimal.ONE : BigDecimal.ZERO      );
	}

	public Date getRUNNING_DATE() {
		return RUNNING_DATE;
	}

	public void setRUNNING_DATE(Date rUNNINGDATE) {
		RUNNING_DATE = rUNNINGDATE;
	}

	public String getPARENT_REF_STR() {
		return PARENT_REF_STR;
	}

	public void setPARENT_REF_STR(String pARENTREFSTR) {
		PARENT_REF_STR = pARENTREFSTR;
	}

	public String getTMPLT_CODE_STR() {
		return TMPLT_CODE_STR;
	}

	public void setTMPLT_CODE_STR(String tMPLTCODESTR) {
		TMPLT_CODE_STR = tMPLTCODESTR;
	}

	public String getCOLUMN_CODE_STR() {
		return COLUMN_CODE_STR;
	}

	public void setCOLUMN_CODE_STR(String cOLUMNCODESTR) {
		COLUMN_CODE_STR = cOLUMNCODESTR;
	}

	public String getLS_REF() {
		return LS_REF;
	}

	public void setLS_REF(String lSREF) {
		LS_REF = lSREF;
	}

	public BigDecimal getPROG_ORDER() {
		return PROG_ORDER;
	}

	public void setPROG_ORDER(BigDecimal pROGORDER) {
		PROG_ORDER = pROGORDER;
	}

	public String getLS_PRINT() {
		return LS_PRINT;
	}

	public void setLS_PRINT(String lSPRINT) {
		LS_PRINT = lSPRINT;
	}

	public String getLS_SAV() {
		return LS_SAV;
	}

	public void setLS_SAV(String lSSAV) {
		LS_SAV = lSSAV;
	}

	public String getLS_UPPER_NAME() {
		return LS_UPPER_NAME;
	}

	public void setLS_UPPER_NAME(String lSUPPERNAME) {
		LS_UPPER_NAME = lSUPPERNAME;
	}

	public List<String> getPROG_REFS() {
		return PROG_REFS;
	}

	public void setPROG_REFS(List<String> pROGREFS) {
		PROG_REFS = pROGREFS;
	}

	public BigDecimal getORIENTATION() {
		return ORIENTATION;
	}

	public void setORIENTATION(BigDecimal oRIENTATION) {
		ORIENTATION = oRIENTATION;
	}

	public String getDISPLAY_VALUES() {
		return DISPLAY_VALUES;
	}

	public void setDISPLAY_VALUES(String dISPLAYVALUES) {
		DISPLAY_VALUES = dISPLAYVALUES;
	}
}
