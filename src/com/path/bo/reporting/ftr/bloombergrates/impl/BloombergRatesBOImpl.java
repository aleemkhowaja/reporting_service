package com.path.bo.reporting.ftr.bloombergrates.impl;

import java.math.BigDecimal;
import java.util.List;

import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.ftr.bloombergrates.BloombergRatesBO;
import com.path.dao.reporting.ftr.bloombergrates.BloombergRatesDAO;
import com.path.dbmaps.vo.FTR_RATE_UPLOADVO;
import com.path.dbmaps.vo.bloombergrates.BloombergRatesCO;
import com.path.dbmaps.vo.bloombergrates.BloombergRatesSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * BloombergRatesBOImpl.java used to
 */
public class BloombergRatesBOImpl extends BaseBO implements BloombergRatesBO
{
    BloombergRatesDAO bloombergRatesDAO;

    public BloombergRatesDAO getBloombergRatesDAO()
    {
	return bloombergRatesDAO;
    }

    public void setBloombergRatesDAO(BloombergRatesDAO bloombergRatesDAO)
    {
	this.bloombergRatesDAO = bloombergRatesDAO;
    }

    public void saveUploadBloombergRates(List<BloombergRatesCO> bloombergList) throws BaseException
    {
	 bloombergRatesDAO.saveUploadBloombergRates(bloombergList);
    }
    

    public List<BloombergRatesCO> retBloombergRatesList(BloombergRatesSC bloombergRatesSC) throws BaseException
    {
	return bloombergRatesDAO.retBloombergRatesList(bloombergRatesSC);
    }


    public int retBloombergRatesCount(BloombergRatesSC bloombergRatesSC) throws BaseException
    {
	return bloombergRatesDAO.retBloombergRatesCount(bloombergRatesSC);
    }
    
    
    public void saveBlgRates(List<BloombergRatesCO> bloombergList,AuditRefCO refCO,String pageRef,BigDecimal compCode,String appName,String lang)throws BaseException
    {
	Integer row = null;
	for(int i=0;i<bloombergList.size();i++)
	{
	     row = genericDAO.update(bloombergList.get(i).getFtrRateUploadVO());
	     
	     if(row==null || row < 1)
	     {
		 throw new BOException(MessageCodes.RECORD_CHANGED);
	     }
	     refCO.setOperationType(AuditConstant.UPDATE);
	     FTR_RATE_UPLOADVO newVO = bloombergList.get(i).getFtrRateUploadVO();
	     FTR_RATE_UPLOADVO oldVO = new FTR_RATE_UPLOADVO();
	     oldVO.setDATE_UPDATED(newVO.getDATE_UPDATED());
	     oldVO.setCOMP_CODE(newVO.getCOMP_CODE());
	     oldVO.setCURRENCY_CODE(newVO.getCURRENCY_CODE());
	     oldVO.setDISC_FACTOR(newVO.getDISC_FACTOR());
	     oldVO.setNET_RATE(newVO.getNET_RATE());
	     oldVO.setRATE(newVO.getRATE());
	     oldVO.setVALUE_DATE(newVO.getVALUE_DATE());
	     BigDecimal rate = bloombergList.get(i).getOldAdjustRate();
	     oldVO.setADJUST_RATE(rate);
	     
	     refCO.setTrxNbr(null);
	     refCO.setAuditCO(null);
	     String trxNbr = auditBO.checkAuditKey(newVO, refCO);
	     refCO.setTrxNbr(trxNbr);
	     auditBO.callAudit( oldVO, newVO ,refCO);
	     if (refCO.getAuditCO() != null) 
	     {
	     refCO.getAuditCO().getAuditVO().setTRX_NBR(trxNbr);
	     auditBO.insertAudit(refCO);
	     }
	}
	
    }

}
