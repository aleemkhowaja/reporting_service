package com.path.bo.reporting.ftr.timeBuckets;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.dbmaps.vo.FTR_TIME_BUCKETSVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSCO;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSSC;

public interface TimeBucketsBO {

	public List<FTR_TIME_BUCKETSCO> getTimeBucketsList(FTR_TIME_BUCKETSSC sc) throws BaseException;
	public FTR_TIME_BUCKETSCO retrieveTimeBuckets(FTR_TIME_BUCKETSSC sc) throws BaseException;//LBedrane - 19/03/2018 638947 - Currency description field issue 
	public List<FTR_TIME_BUCKETSCO> retTimeBucketsListDetails(FTR_TIME_BUCKETSSC sc) throws BaseException;
	public int getTimeBucketsCount(FTR_TIME_BUCKETSSC sc) throws BaseException;
	public int getTimeBucketsDetailsCount(FTR_TIME_BUCKETSSC sc) throws BaseException;
	public void deleteTimeBuckets(FTR_TIME_BUCKETSVO ftrTimeBucketsVO) throws BaseException;
	public void insertTimeBuckets(ArrayList timeBucketsListCO, BigDecimal compCode) throws BaseException;
	public void updateTimeBuckets(ArrayList timeBucketsListCO, BigDecimal compCode) throws BaseException;
	public FTR_TIME_BUCKETSCO getRepRef(FTR_TIME_BUCKETSSC sc) throws BaseException;
	public void deleteTimeBucketsDetails(ArrayList lstDel,BigDecimal companyCode) throws BaseException;
}