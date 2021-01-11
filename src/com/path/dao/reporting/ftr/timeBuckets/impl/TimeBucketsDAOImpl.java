package com.path.dao.reporting.ftr.timeBuckets.impl;

import java.util.ArrayList;
import java.util.List;
import com.path.dao.reporting.ftr.timeBuckets.TimeBucketsDAO;
import com.path.dbmaps.vo.FTR_TIME_BUCKETSVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSCO;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSSC;

public class TimeBucketsDAOImpl extends BaseDAO implements TimeBucketsDAO {
	
	public List<FTR_TIME_BUCKETSCO> getTimeBucketsList(FTR_TIME_BUCKETSSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "timeBuckets.timeBucketsMap");
		return (ArrayList<FTR_TIME_BUCKETSCO>) getSqlMap().queryForList("timeBuckets.timeBucketsList", sc,sc.getRecToskip(), sc.getNbRec());
	}
    
	//LBedrane - 19/03/2018 638947 - Currency description field issue 
	    public FTR_TIME_BUCKETSCO retrieveTimeBuckets(FTR_TIME_BUCKETSSC ftrTimeBucketsSC) throws DAOException
	    {
	      return ((FTR_TIME_BUCKETSCO) getSqlMap().queryForObject("timeBuckets.retrieveTimeBuckets",ftrTimeBucketsSC));
	    }
    
    public List<FTR_TIME_BUCKETSCO> retTimeBucketsListDetails(FTR_TIME_BUCKETSSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "timeBuckets.timeBucketsMap");		
		return (ArrayList<FTR_TIME_BUCKETSCO>) getSqlMap().queryForList("timeBuckets.timeBucketsListDetails", sc, sc.getRecToskip(), sc.getNbRec());
	}
    
    public int getTimeBucketsCount(FTR_TIME_BUCKETSSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "timeBuckets.timeBucketsMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("timeBuckets.timeBucketsCount", sc)).intValue();
		return nb;
	}
    
    public int getTimeBucketsDetailsCount(FTR_TIME_BUCKETSSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "timeBuckets.timeBucketsMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("timeBuckets.timeBucketsDetailsCount", sc)).intValue();
		return nb;
	}
    
    public void deleteTimeBuckets(FTR_TIME_BUCKETSVO vo) throws DAOException {
		getSqlMap().delete("timeBuckets.deleteTimeBuckets", vo);
	}
    
    public void deleteTimeBucketsDetails(FTR_TIME_BUCKETSVO vo) throws DAOException {
		getSqlMap().delete("timeBuckets.deleteTimeBucketsDetails", vo);
	}
    
    public void insertTimeBuckets(FTR_TIME_BUCKETSVO vo) throws DAOException {
		getSqlMap().insert("timeBuckets.insertTimeBucketsSql", vo);
	}
    
    public FTR_TIME_BUCKETSCO getRepRef(FTR_TIME_BUCKETSSC sc) throws DAOException{
	return (FTR_TIME_BUCKETSCO) getSqlMap().queryForObject("timeBuckets.retrieveReportRef", sc);
    }

  
}
