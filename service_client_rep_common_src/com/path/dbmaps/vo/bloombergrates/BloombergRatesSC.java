package com.path.dbmaps.vo.bloombergrates;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * BloombergRatesSC.java used to
 */
public class BloombergRatesSC extends GridParamsSC
{
    
    private BigDecimal COMP_CODE;


    
    public BigDecimal getCOMP_CODE()
    {
        return COMP_CODE;
    }

    public void setCOMP_CODE(BigDecimal cOMPCODE)
    {
        COMP_CODE = cOMPCODE;
    }
    
    
    public BloombergRatesSC()
    {
	super.setSearchCols(new String[] {"COMP_CODE","CURRENCY_CODE","VALUE_DATE","DISC_FACTOR","RATE","ADJUST_RATE","NET_RATE"});
	  
    }
}
