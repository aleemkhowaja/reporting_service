package com.path.dao.reporting.common.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.path.dao.reporting.common.CommonReportingDAO;
import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_REP_FILTERSVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
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
 * CommonReportingDAOImpl.java used as common for all iMAL applications  
 * and therefore the content should not be application specific.
 */
public class CommonReportingDAOImpl extends BaseDAO implements CommonReportingDAO
{

    @Override
    public List<IRP_AD_HOC_QUERYVO> queriesByReport(IRP_AD_HOC_REPORTSC reportSC) throws DAOException
    {
	return getSqlMap().queryForList("commonReports.selectQueriesByReport", reportSC);
    }
    
    @Override
    public int retFiltersListCount(IRP_REP_FILTERSSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "commonReports.filtersListMap");
	return ((Integer) getSqlMap().queryForObject("commonReports.retFiltersListCount", sc))
		.intValue();
    }
    
    @Override
    public List<IRP_REP_FILTERSVO> retFiltersList(IRP_REP_FILTERSSC sc) throws DAOException
    {
	if(sc.isGrid())
	{
	    DAOHelper.fixGridMaps(sc, getSqlMap(), "commonReports.filtersListMap");
	}
	else
	{
	    return getSqlMap().queryForList("commonReports.retFiltersList", sc);
	}
	return getSqlMap().queryForList("commonReports.retFiltersList", sc, sc.getRecToskip(), sc.getNbRec());
    }
    
    @Override
    public BigDecimal selectMaxFilterId() throws DAOException
    {
	return (BigDecimal)getSqlMap().queryForObject("commonReports.selectMaxFilterId",null);
    }
    
    @Override
    public List<HashMap<String, Object>> retFilterArgumentsValues(IRP_REP_FILTERSSC sc) throws DAOException
    {
	return getSqlMap().queryForList("commonReports.retFilterArgumentsValues", sc);
    }

    @Override
    public void deleteFilterArgumentsById(BigDecimal filterId) throws DAOException
    {
	getSqlMap().delete("commonReports.deleteFilterArgumentsById", filterId);		
    }
    
    @Override
    public boolean checkFilterNameUnique(IRP_REP_FILTERSSC sc) throws DAOException
    {
	return getSqlMap().queryForList("commonReports.checkFilterNameUnique", sc).isEmpty()?true:false;
    }
    
    @Override
    public BigDecimal selectDefaultFilter(IRP_REP_FILTERSSC sc) throws DAOException
    {
	return (BigDecimal)getSqlMap().queryForObject("commonReports.retDefaultFilter",sc);
    }
    
    @Override
    public IRP_AD_HOC_QUERYCO returnQryResult(BigDecimal QueryId) throws DAOException
    {
    	return (IRP_AD_HOC_QUERYCO) getSqlMap().queryForObject("query.selectQueryById",QueryId);
    }
    
    @Override
    public void deleteSorting(IRP_AD_HOC_REPORTSC irpAdHocRepSC) throws DAOException
    {
	getSqlMap().delete("commonReports.deleteSorting", irpAdHocRepSC);
    }
    
    @Override
    public ArrayList<IRP_FIELDSCO> retSortingListFromIrpRepSort(IRP_AD_HOC_REPORTSC irpAdHocRepSC) throws DAOException
    {
	return (ArrayList<IRP_FIELDSCO>) getSqlMap().queryForList("commonReports.retSortingListFromIrpRepSort", irpAdHocRepSC);
    }
}
