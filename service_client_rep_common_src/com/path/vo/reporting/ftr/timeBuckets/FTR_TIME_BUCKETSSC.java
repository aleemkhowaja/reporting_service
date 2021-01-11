package com.path.vo.reporting.ftr.timeBuckets;

import java.math.BigDecimal;
import com.path.struts2.lib.common.GridParamsSC;

public class FTR_TIME_BUCKETSSC extends GridParamsSC {

	private BigDecimal COMP_CODE;
	private String REP_REF;
	private BigDecimal CURRENCY_CODE;
	private BigDecimal LINE_NO;	
	private String APP_NAME;
	
	public FTR_TIME_BUCKETSSC(){
		super.setSearchCols(new String[] {"ftrtimebucketsVO.CURRENCY_CODE","ftrtimebucketsVO.REP_REF"});
    }

	public BigDecimal getCOMP_CODE() {
		return COMP_CODE;
	}

	public void setCOMP_CODE(BigDecimal cOMPCODE) {
		COMP_CODE = cOMPCODE;
	}

	public String getREP_REF() {
		return REP_REF;
	}

	public void setREP_REF(String rEPREF) {
		REP_REF = rEPREF;
	}
	
	public BigDecimal getCURRENCY_CODE() {
		return CURRENCY_CODE;
	}

	public void setCURRENCY_CODE(BigDecimal cURRENCYCODE) {
		CURRENCY_CODE = cURRENCYCODE;
	}

	public BigDecimal getLINE_NO() {
		return LINE_NO;
	}

	public void setLINE_NO(BigDecimal lINENO) {
		LINE_NO = lINENO;
	}

	public void setAPP_NAME(String aPP_NAME)
	{
	    APP_NAME = aPP_NAME;
	}

	public String getAPP_NAME()
	{
	    return APP_NAME;
	}
    
}