package com.path.bo.reporting.ftr.csvitems;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * CsvItemsBO.java used to
 */
public interface CsvItemsBO
{

    public List<CsvItemsCO> retCsvItemsList(CsvItemsSC csvItemsSC) throws BaseException;

    public int retCsvItemsCount(CsvItemsSC csvItemsSC) throws BaseException;
    
    public CsvItemsCO applyDependencyByRepRef(CsvItemsSC csvItemsSC) throws BaseException;
    
    public List<CsvItemsCO> retCsvItemsByRepList(CsvItemsSC csvItemsSC) throws BaseException;

    public int retCsvItemsByRepCount(CsvItemsSC csvItemsSC) throws BaseException;
    
    public CsvItemsCO retrieveRepName(CsvItemsSC csvItemsSC) throws BaseException;
    
    public void saveCsvItems(List<CsvItemsCO> lstCsvItems,List<CsvItemsCO> lstCsvItemsDeleted,AuditRefCO refCO,String pageRef,String repRef,BigDecimal compCode,Date dateUpdated,String appName,String lang)throws BaseException;
    
    public void deleteAllCsvItemByRep(String repRef,BigDecimal compCode)throws BaseException;

}