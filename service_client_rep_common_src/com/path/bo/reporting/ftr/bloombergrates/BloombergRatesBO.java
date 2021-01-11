package com.path.bo.reporting.ftr.bloombergrates;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.bloombergrates.BloombergRatesCO;
import com.path.dbmaps.vo.bloombergrates.BloombergRatesSC;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * BloombergRatesBO.java used to
 */
public interface BloombergRatesBO 
{
    
    public void saveUploadBloombergRates(List<BloombergRatesCO> bloombergList)throws BaseException;
    
    public List<BloombergRatesCO> retBloombergRatesList(BloombergRatesSC bloombergRatesSC) throws BaseException;

    public int retBloombergRatesCount(BloombergRatesSC bloombergRatesSC) throws BaseException;
    
    public void saveBlgRates(List<BloombergRatesCO> bloombergList,AuditRefCO refCO,String pageRef,BigDecimal compCode,String appName,String lang)throws BaseException;
}