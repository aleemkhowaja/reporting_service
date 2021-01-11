package com.path.vo.common;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class COUNTRYSC extends GridParamsSC
{
    private BigDecimal COMP_CODE;
    private BigDecimal COUNTRY_CODE;
    
    public BigDecimal getCOMP_CODE()
    {
        return COMP_CODE;
    }
    public void setCOMP_CODE(BigDecimal cOMPCODE)
    {
        COMP_CODE = cOMPCODE;
    }
    public BigDecimal getCOUNTRY_CODE()
    {
        return COUNTRY_CODE;
    }
    public void setCOUNTRY_CODE(BigDecimal cOUNTRYCODE)
    {
        COUNTRY_CODE = cOUNTRYCODE;
    }
    public COUNTRYSC()
    {
	super.setSearchCols(new String[] { "COUNTRY_CODE", "BRIEF_DESC_ENG" });
    }
}
