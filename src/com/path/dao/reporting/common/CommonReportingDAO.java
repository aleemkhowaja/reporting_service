package com.path.dao.reporting.common;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_REP_FILTERSVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_REP_FILTERSSC;
import java.util.ArrayList;
import com.path.vo.reporting.IRP_FIELDSCO;

/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: annasuccar
 *
 * CommonReportingDAO.java used as common for all iMAL applications  
 * and therefore the content should not be application specific.
 */
public interface CommonReportingDAO
{
    public List<IRP_AD_HOC_QUERYVO> queriesByReport(IRP_AD_HOC_REPORTSC reportSC) throws DAOException;
    /**
     * Method to get the list of filters
     * @param sc
     * @return
     * @throws DAOException
     */
    public List<IRP_REP_FILTERSVO> retFiltersList(IRP_REP_FILTERSSC sc) throws DAOException;
    /**
     * Method to return the count of filters list
     * @param sc
     * @return
     * @throws Exception
     */
    public int retFiltersListCount(IRP_REP_FILTERSSC sc) throws DAOException;
    /**
     * Method to select max filter id
     * @return
     * @throws DAOException
     */
    public BigDecimal selectMaxFilterId() throws DAOException;
    /**
     * Method to get all saved values for arguments for a specific filter
     * @param sc
     * @return
     * @throws DAOException
     */
    public List<HashMap<String, Object>> retFilterArgumentsValues(IRP_REP_FILTERSSC sc) throws DAOException;
    /**
     * Method to delete filters and arguments 
     * @param filterId
     * @throws DAOException
     */
    public void deleteFilterArgumentsById(BigDecimal filterId) throws DAOException;
    /**
     * Method to check the uniqueness of a filter name
     * @param sc
     * @return
     * @throws DAOException
     */
    public boolean checkFilterNameUnique(IRP_REP_FILTERSSC sc) throws DAOException;
    /**
     * Method to select default filter
     * @return
     * @throws DAOException
     */
    public BigDecimal selectDefaultFilter(IRP_REP_FILTERSSC sc) throws DAOException;

    /**
     * Method to select Query object from irp_ad_hoc_query
     * @return
     * @throws DAOException
     */
    public IRP_AD_HOC_QUERYCO returnQryResult(BigDecimal QueryId) throws DAOException;
    
    /**
     * delete Sorting from IRP_REP_SORT table
     * @param irpAdHocRepSC
     * @throws DAOException
     */
    public void deleteSorting(IRP_AD_HOC_REPORTSC irpAdHocRepSC) throws DAOException;
    
    /**
     * return Sorting List From Irp_Rep_Sort table
     * @param irpAdHocRepSC
     * @return
     * @throws DAOException
     */
    public ArrayList<IRP_FIELDSCO> retSortingListFromIrpRepSort(IRP_AD_HOC_REPORTSC irpAdHocRepSC) throws DAOException;
    
}
