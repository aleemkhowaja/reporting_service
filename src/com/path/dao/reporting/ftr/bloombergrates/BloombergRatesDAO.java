package com.path.dao.reporting.ftr.bloombergrates;

import java.util.List;

import com.path.dbmaps.vo.bloombergrates.BloombergRatesCO;
import com.path.dbmaps.vo.bloombergrates.BloombergRatesSC;
import com.path.lib.common.exception.BaseException;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * BloombergRatesDAO.java used to
 */
public interface BloombergRatesDAO 
{
    
    public void saveUploadBloombergRates(List<BloombergRatesCO> bloombergList)throws BaseException;
    
    public List<BloombergRatesCO> retBloombergRatesList(BloombergRatesSC bloombergRatesSC) throws BaseException;

    public int retBloombergRatesCount(BloombergRatesSC bloombergRatesSC) throws BaseException;
}