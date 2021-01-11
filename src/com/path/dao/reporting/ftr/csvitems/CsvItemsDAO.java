package com.path.dao.reporting.ftr.csvitems;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.exception.BaseException;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * CsvItemsDAO.java used to
 */
public interface CsvItemsDAO
{

    public List<CsvItemsCO> retCsvItemsList(CsvItemsSC csvItemsSC) throws BaseException;

    public int retCsvItemsCount(CsvItemsSC csvItemsSC) throws BaseException;

    public CsvItemsCO applyDependencyByRepRef(CsvItemsSC csvItemsSC) throws BaseException;
    
    public List<CsvItemsCO> retCsvItemsByRepList(CsvItemsSC csvItemsSC) throws BaseException;

    public int retCsvItemsByRepCount(CsvItemsSC csvItemsSC) throws BaseException;
    
    public CsvItemsCO retrieveRepName(CsvItemsSC csvItemsSC) throws BaseException;
    
    public Integer saveCsvItems(List<CsvItemsCO> lstCsvItems,String repRef,BigDecimal compCode,Date dateUpdated)throws BaseException;
    
    public void deleteAllCsvItemByRep(String repRef,BigDecimal compCode)throws BaseException;
    
}