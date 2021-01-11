package com.path.dao.reporting.ftr.bloombergrates.impl;

import java.util.List;

import com.path.dao.reporting.ftr.bloombergrates.BloombergRatesDAO;
import com.path.dbmaps.vo.bloombergrates.BloombergRatesCO;
import com.path.dbmaps.vo.bloombergrates.BloombergRatesSC;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * BloombergRatesDAOImpl.java used to
 */
public class BloombergRatesDAOImpl extends BaseDAO implements BloombergRatesDAO
{
    
    
    public List<BloombergRatesCO> retBloombergRatesList(BloombergRatesSC bloombergRatesSC) throws DAOException
    {

	DAOHelper.fixGridMaps(bloombergRatesSC, getSqlMap(), "bloombergRatesMapper.retBloombergRatesListMap");
	return getSqlMap().queryForList("bloombergRatesMapper.retBloombergRatesList", bloombergRatesSC,bloombergRatesSC.getRecToskip(), bloombergRatesSC.getNbRec());

    }

    public int retBloombergRatesCount(BloombergRatesSC bloombergRatesSC) throws DAOException
    {
	DAOHelper.fixGridMaps(bloombergRatesSC, getSqlMap(), "bloombergRatesMapper.retBloombergRatesListMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("bloombergRatesMapper.retBloombergRatesCount", bloombergRatesSC)).intValue();
	return nb;
    }
    
    public void saveUploadBloombergRates(List<BloombergRatesCO> bloombergList) throws DAOException
    {
	BloombergRatesCO blgCO = new BloombergRatesCO();
	blgCO.setBlgRtesLst(bloombergList);
	getSqlMap().delete("bloombergRatesMapper.deleteBloombergRates", "");
	getSqlMap().insert("bloombergRatesMapper.insertBloombergRates", blgCO);
    }
    
    
}
