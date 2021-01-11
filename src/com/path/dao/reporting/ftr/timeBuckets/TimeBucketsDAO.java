package com.path.dao.reporting.ftr.timeBuckets;

import java.util.List;
import com.path.dbmaps.vo.FTR_TIME_BUCKETSVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSCO;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSSC;

public interface TimeBucketsDAO {
	
	public List<FTR_TIME_BUCKETSCO> getTimeBucketsList(FTR_TIME_BUCKETSSC sc) throws DAOException;
	public FTR_TIME_BUCKETSCO retrieveTimeBuckets(FTR_TIME_BUCKETSSC ftrTimeBucketsSC)throws DAOException;//LBedrane - 19/03/2018 638947 - Currency description field issue 
	public List<FTR_TIME_BUCKETSCO> retTimeBucketsListDetails(FTR_TIME_BUCKETSSC sc) throws DAOException;
	public int getTimeBucketsCount(FTR_TIME_BUCKETSSC sc) throws DAOException;
	public int getTimeBucketsDetailsCount(FTR_TIME_BUCKETSSC sc) throws DAOException;
	public void deleteTimeBuckets(FTR_TIME_BUCKETSVO vo) throws DAOException;
	public void deleteTimeBucketsDetails(FTR_TIME_BUCKETSVO vo) throws DAOException;
	public void insertTimeBuckets(FTR_TIME_BUCKETSVO vo) throws DAOException;
	public FTR_TIME_BUCKETSCO getRepRef(FTR_TIME_BUCKETSSC sc) throws DAOException;
}