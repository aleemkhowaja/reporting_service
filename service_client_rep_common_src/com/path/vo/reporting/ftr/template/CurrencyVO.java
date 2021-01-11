package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;
import java.sql.Date;

import com.path.lib.vo.BaseVO;

public class CurrencyVO extends BaseVO
{
	private BigDecimal CURRENCY_CODE;
	private String ADDITIONAL_REFERENCE;
	private String BRIEF_DESC_ENG;
	private Date LONG_DESC_ENG;
	private String BRIEF_DESC_ARAB;
	private String LONG_DESC_ARAB;
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

	
}
