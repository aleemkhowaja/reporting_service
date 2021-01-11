package com.path.dbmaps.vo.bloombergrates;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.dbmaps.vo.FTR_RATE_UPLOADVO;
import com.path.lib.vo.BaseVO;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * BloombergRatesCO.java used to
 */
public class BloombergRatesCO extends BaseVO
{
    FTR_RATE_UPLOADVO ftrRateUploadVO = new FTR_RATE_UPLOADVO();
    private String currencyDesc;
    private BigDecimal oldAdjustRate;

    private List<BloombergRatesCO> blgRtesLst = new ArrayList<BloombergRatesCO>();

    
    
    
    public BigDecimal getOldAdjustRate()
    {
        return oldAdjustRate;
    }

    public void setOldAdjustRate(BigDecimal oldAdjustRate)
    {
        this.oldAdjustRate = oldAdjustRate;
    }

    public FTR_RATE_UPLOADVO getFtrRateUploadVO()
    {
	return ftrRateUploadVO;
    }

    public void setFtrRateUploadVO(FTR_RATE_UPLOADVO ftrRateUploadVO)
    {
	this.ftrRateUploadVO = ftrRateUploadVO;
    }

    public String getCurrencyDesc()
    {
	return currencyDesc;
    }

    public void setCurrencyDesc(String currencyDesc)
    {
	this.currencyDesc = currencyDesc;
    }

    public List<BloombergRatesCO> getBlgRtesLst()
    {
	return blgRtesLst;
    }

    public void setBlgRtesLst(List<BloombergRatesCO> blgRtesLst)
    {
	this.blgRtesLst = blgRtesLst;
    }

}
