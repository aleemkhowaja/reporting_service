package com.path.bo.reporting.ftr.csvitems.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.ftr.csvitems.CsvItemsBO;
import com.path.dao.reporting.ftr.csvitems.CsvItemsDAO;
import com.path.dbmaps.vo.CBK_RPT_LINEVO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.common.audit.AuditRefCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * CsvItemsBOImpl.java used to
 */
public class CsvItemsBOImpl extends BaseBO implements CsvItemsBO
{
    CsvItemsDAO csvItemsDAO;

    public CsvItemsDAO getCsvItemsDAO()
    {
	return csvItemsDAO;
    }

    public void setCsvItemsDAO(CsvItemsDAO csvItemsDAO)
    {
	this.csvItemsDAO = csvItemsDAO;
    }

    /**
     * get the csv items list count
     * 
     * @return list
     * @throws Exception
     */

    public List<CsvItemsCO> retCsvItemsList(CsvItemsSC csvItemsSC) throws BaseException
    {
	return csvItemsDAO.retCsvItemsList(csvItemsSC);
    }

    /**
     * get the csv items list count
     * 
     * @return int
     * @throws Exception
     */
    public int retCsvItemsCount(CsvItemsSC csvItemsSC) throws BaseException
    {
	return csvItemsDAO.retCsvItemsCount(csvItemsSC);
    }

    public CsvItemsCO applyDependencyByRepRef(CsvItemsSC csvItemsSC) throws BaseException
    {
	return csvItemsDAO.applyDependencyByRepRef(csvItemsSC);
    }

    /**
     * get the csv items list count
     * 
     * @return list
     * @throws Exception
     */

    public List<CsvItemsCO> retCsvItemsByRepList(CsvItemsSC csvItemsSC) throws BaseException
    {
	return csvItemsDAO.retCsvItemsByRepList(csvItemsSC);
    }

    /**
     * get the csv items list count
     * 
     * @return int
     * @throws Exception
     */
    public int retCsvItemsByRepCount(CsvItemsSC csvItemsSC) throws BaseException
    {
	return csvItemsDAO.retCsvItemsByRepCount(csvItemsSC);
    }
    
    
    public CsvItemsCO retrieveRepName(CsvItemsSC csvItemsSC) throws BaseException
    {
	return csvItemsDAO.retrieveRepName(csvItemsSC);
    }
    
    public void saveCsvItems(List<CsvItemsCO> lstCsvItems,List<CsvItemsCO> lstCsvItemsDeleted,AuditRefCO refCO,String pageRef,String repRef,BigDecimal compCode,Date dateUpdated,String appName,String lang) throws BaseException
    {
	if(!lstCsvItems.isEmpty())//!NumberUtil.isEmptyDecimal(lstCsvItems.get(0).getCbkRptLineVO().getLINE_NBR()))
	{
	    CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	    lstCsvItems.get(0).getCbkRptLineVO().setREP_REF(repRef);
	    checkRequiredCO.setObj_value(lstCsvItems.get(0));
	    checkRequiredCO.setOpt(pageRef);
	    checkRequiredCO.setCompCode(compCode);
	    checkRequiredCO.setLanguage(lang);
	    checkRequiredCO.setApp(appName);
	    
	    commonLibBO.checkRequired(checkRequiredCO); 
	}
	
	Integer row = csvItemsDAO.saveCsvItems(lstCsvItems,repRef,compCode,dateUpdated);
	if(row==null)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
	
	boolean callInsertAudit =false;
	if(!NumberUtil.isEmptyDecimal(lstCsvItems.get(0).getCbkRptLineVO().getLINE_NBR()))
	{
        	for(int i=0;i<lstCsvItems.size();i++)
        	{
        		
        	    CBK_RPT_LINEVO newCbkRptLineVO = new CBK_RPT_LINEVO();
        	    CBK_RPT_LINEVO oldCbkRptLineVO = new CBK_RPT_LINEVO();
        	    newCbkRptLineVO.setCSV_ITEM_ID(lstCsvItems.get(i).getCbkRptLineVO().getCSV_ITEM_ID());
        	    newCbkRptLineVO.setLINE_NBR(lstCsvItems.get(i).getCbkRptLineVO().getLINE_NBR());
        	    newCbkRptLineVO.setREP_REF(lstCsvItems.get(i).getCbkRptLineVO().getREP_REF());
        	    newCbkRptLineVO.setCOMP_CODE(compCode);
        	    
        	    oldCbkRptLineVO.setCSV_ITEM_ID(lstCsvItems.get(i).getOldCsvItemId());
        	    oldCbkRptLineVO.setLINE_NBR(lstCsvItems.get(i).getCbkRptLineVO().getLINE_NBR());
        	    oldCbkRptLineVO.setREP_REF(lstCsvItems.get(i).getCbkRptLineVO().getREP_REF());
        	    oldCbkRptLineVO.setCOMP_CODE(compCode);
        	    if(!lstCsvItems.get(i).getOldCsvItemId().equals(lstCsvItems.get(i).getCbkRptLineVO().getCSV_ITEM_ID()))
        	    {
        		callInsertAudit=true;
        		refCO.setAuditRefCO(null);
        		refCO.setOperationType(AuditConstant.UPDATE);
        		auditBO.callAudit( oldCbkRptLineVO, newCbkRptLineVO ,refCO);//lstCsvItems.get(i).getAuditRefCO()); 
        	    }
        	    
        	}
        	
	}
	
	for(int i=0;i<lstCsvItemsDeleted.size();i++)
	{
	    CBK_RPT_LINEVO newCbkRptLineVO = new CBK_RPT_LINEVO();
	    CBK_RPT_LINEVO oldCbkRptLineVO = new CBK_RPT_LINEVO();
	    newCbkRptLineVO.setCSV_ITEM_ID("");
	    newCbkRptLineVO.setREP_REF(lstCsvItemsDeleted.get(i).getCbkRptLineVO().getREP_REF());
	    newCbkRptLineVO.setCOMP_CODE(compCode);
	    
	    oldCbkRptLineVO.setCSV_ITEM_ID(lstCsvItemsDeleted.get(i).getOldCsvItemId());
	    oldCbkRptLineVO.setLINE_NBR(lstCsvItemsDeleted.get(i).getCbkRptLineVO().getLINE_NBR());
	    oldCbkRptLineVO.setREP_REF(lstCsvItemsDeleted.get(i).getCbkRptLineVO().getREP_REF());
	    oldCbkRptLineVO.setCOMP_CODE(compCode);
	    
	    //refCO.setAuditCO(null);
	    refCO.setOperationType(AuditConstant.UPDATE);
	    auditBO.callAudit( oldCbkRptLineVO, newCbkRptLineVO ,refCO);//lstCsvItemsDeleted.get(i).getAuditRefCO()); 
	}
	if(!lstCsvItemsDeleted.isEmpty())
	{
	    callInsertAudit =true;
	}
	
	if(callInsertAudit)
	{
	    auditBO.insertAudit(refCO);//lstCsvItems.get(0).getAuditRefCO()); 
	}
	
	
    }
    
    public void deleteAllCsvItemByRep(String repRef,BigDecimal compCode) throws BaseException
    {
	 csvItemsDAO.deleteAllCsvItemByRep(repRef,compCode);
    }
}
