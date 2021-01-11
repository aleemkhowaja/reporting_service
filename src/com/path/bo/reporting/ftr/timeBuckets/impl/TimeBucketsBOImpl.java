package com.path.bo.reporting.ftr.timeBuckets.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.bo.reporting.ftr.timeBuckets.TimeBucketsBO;
import com.path.dao.reporting.ftr.timeBuckets.TimeBucketsDAO;
import com.path.dbmaps.vo.FTR_TIME_BUCKETSVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSCO;
import com.path.vo.reporting.ftr.timeBuckets.FTR_TIME_BUCKETSSC;

public class TimeBucketsBOImpl extends BaseBO implements TimeBucketsBO {
	
	private TimeBucketsDAO timeBucketsDAO;

	public TimeBucketsDAO getTimeBucketsDAO() {
		return timeBucketsDAO;
	}

	public void setTimeBucketsDAO(TimeBucketsDAO timeBucketsDAO) {
		this.timeBucketsDAO = timeBucketsDAO;
	}
	
	public List<FTR_TIME_BUCKETSCO> getTimeBucketsList(FTR_TIME_BUCKETSSC sc) throws BaseException {
		return timeBucketsDAO.getTimeBucketsList(sc);
	}
	
	public	FTR_TIME_BUCKETSCO  retrieveTimeBuckets(FTR_TIME_BUCKETSSC ftrTimeBucketsSC)	throws BaseException {//LBedrane - 19/03/2018 638947 - Currency description field issue 
		return timeBucketsDAO.retrieveTimeBuckets(ftrTimeBucketsSC);
	}

	public List<FTR_TIME_BUCKETSCO> retTimeBucketsListDetails(FTR_TIME_BUCKETSSC sc) throws BaseException {
		return timeBucketsDAO.retTimeBucketsListDetails(sc);
	}
	
	public int getTimeBucketsCount(FTR_TIME_BUCKETSSC sc) throws BaseException {
		return timeBucketsDAO.getTimeBucketsCount(sc);
	}
	
	public int getTimeBucketsDetailsCount(FTR_TIME_BUCKETSSC sc) throws BaseException {
		return timeBucketsDAO.getTimeBucketsDetailsCount(sc);
	}
	
	public void deleteTimeBuckets(FTR_TIME_BUCKETSVO ftrTimeBucketsVO) throws BaseException {
		timeBucketsDAO.deleteTimeBuckets(ftrTimeBucketsVO);
		//auditBO.callAudit(ftrTimeBucketsVO, ftrTimeBucketsVO, ftrTimeBucketsVO.getAuditRefCO());
		//auditBO.insertAudit(ftrTimeBucketsVO.getAuditRefCO());
	}
	
	public void deleteTimeBucketsDetails(ArrayList lstDel,BigDecimal companyCode) throws BaseException {

		FTR_TIME_BUCKETSCO timeBucketsCO;
		FTR_TIME_BUCKETSVO timeBucketsVO;
		
		for(int i=0;i<lstDel.size();i++)
		{
			timeBucketsCO = (FTR_TIME_BUCKETSCO)lstDel.get(i);
			timeBucketsVO = timeBucketsCO.getFtrtimebucketsVO();
			timeBucketsVO.setCOMP_CODE(companyCode);
			timeBucketsDAO.deleteTimeBucketsDetails(timeBucketsVO);
			
			//apply audit
			//auditBO.callAudit(timeBucketsVO, timeBucketsVO, refCO);
			//auditBO.insertAudit(refCO);	
			}	
		
	}
	
	public void insertTimeBuckets(ArrayList lstAdd, BigDecimal compCode) throws BaseException {
		
		FTR_TIME_BUCKETSCO timeBucketsCO;
		FTR_TIME_BUCKETSVO timeBucketsVO;
		for(int i=0;i<lstAdd.size();i++)
		{
			timeBucketsCO = (FTR_TIME_BUCKETSCO)lstAdd.get(i);
			
			CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
			checkRequiredCO.setObj_value(lstAdd);
			checkRequiredCO.setOpt("TB00MT");
			checkRequiredCO.setApp("REP");
			
			//check for required fields
			commonLibBO.checkRequired(checkRequiredCO);
			
			timeBucketsVO = timeBucketsCO.getFtrtimebucketsVO();
			timeBucketsVO.setCOMP_CODE(compCode);
			timeBucketsDAO.insertTimeBuckets(timeBucketsVO);
			
			//apply audit
			//auditBO.callAudit(null, timeBucketsCO.getFtrtimebucketsVO(), refCO);
		}
	}
	
	public void updateTimeBuckets(ArrayList lstMod, BigDecimal compCode) throws BaseException {
		try
		{
			FTR_TIME_BUCKETSCO timeBucketsCO;
			FTR_TIME_BUCKETSVO timeBucketsVO;
			//FTR_TIME_BUCKETSVO timeBucketsOldVO;
			StringBuffer  concatKey;
			
			for(int i=0;i<lstMod.size();i++)
			{
				timeBucketsCO = (FTR_TIME_BUCKETSCO)lstMod.get(i); 
				
				timeBucketsVO = timeBucketsCO.getFtrtimebucketsVO();
				timeBucketsVO.setCOMP_CODE(compCode);
				
				genericDAO.update(timeBucketsVO);
				
				//Concatenate the key of the timeBucketsCO in order to use it when comparing with the old object
				//from the list oldAuditRefCOList
				concatKey=new StringBuffer(timeBucketsVO.getCOMP_CODE().toString());
				
	    		concatKey.append('_' ).append( timeBucketsVO.getREP_REF());
	    		concatKey.append('_' ).append( timeBucketsVO.getCURRENCY_CODE().toString());
	    		concatKey.append('_' ).append( timeBucketsVO.getLINE_NO().toString());
	    		
	    		//timeBucketsOldVO = (FTR_TIME_BUCKETSVO)oldAuditRefVOList.get(concatKey.toString());
	    		
				//Apply Audit
			//refCO.setKeyRef(AuditConstant.TIME_BUCKETS_LINE_KEY_REF);
				//refCO.setOperationType(AuditConstant.UPDATE);
				
				//auditBO.callAudit(timeBucketsOldVO, timeBucketsVO, refCO);
				//auditBO.insertAudit(refCO);
			}
		}
		catch(Exception e)
		{
			throw new BaseException(e);
		}
	}
	
	public FTR_TIME_BUCKETSCO getRepRef(FTR_TIME_BUCKETSSC sc)throws BaseException {
		return timeBucketsDAO.getRepRef(sc);
	}
}
