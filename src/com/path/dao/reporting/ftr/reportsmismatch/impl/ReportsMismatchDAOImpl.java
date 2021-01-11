package com.path.dao.reporting.ftr.reportsmismatch.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.path.bo.common.ConstantsCommon;
import com.path.dao.reporting.ftr.reportsmismatch.ReportsMismatchDAO;
import com.path.dbmaps.vo.REP_MISMATCH_PARAMVO;
import com.path.dbmaps.vo.reportsmismatch.ReportsMismatchSC;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_INTRA_CRITERIACO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PARAMCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_PARAMCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * ReportsMismatchDAOImpl.java used to
 */
public class ReportsMismatchDAOImpl extends BaseDAO implements ReportsMismatchDAO
{
    public ArrayList<REP_MISMATCH_PARAMCO> retReportsMismatchList(ReportsMismatchSC reportsMismatchSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.getReportsMismatchMap");
	if(reportsMismatchSC.getSortOrder() == null)
	{
	    reportsMismatchSC.setSortOrder(" ORDER BY REP_MISMATCH_ID");
	}
	return (ArrayList<REP_MISMATCH_PARAMCO>) getSqlMap().queryForList(
		"reportsMismatchMapper.getReportsMismatchList", reportsMismatchSC);
    }

    public int retReportsMismatchListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.getReportsMismatchMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("reportsMismatchMapper.getReportsMismatchListCount",
		reportsMismatchSC)).intValue();
	return nb;
    }

    public ArrayList<REP_SNAPSHOT_PARAMCO> retReportsMismatchLookupList(ReportsMismatchSC reportsMismatchSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.getReportsMismatchLookupMap");
	if(reportsMismatchSC.getSortOrder() == null)
	{
	    reportsMismatchSC.setSortOrder(" ORDER BY REP_REFERENCE");
	}
	return (ArrayList<REP_SNAPSHOT_PARAMCO>) getSqlMap().queryForList(
		"reportsMismatchMapper.getReportsMismatchLookupList", reportsMismatchSC,
		reportsMismatchSC.getRecToskip(), reportsMismatchSC.getNbRec());
    }

    public int retReportsMismatchLookupListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.getReportsMismatchLookupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("reportsMismatchMapper.getReportsMismatchLookupListCount",
		reportsMismatchSC)).intValue();
	return nb;
    }

    public ArrayList<REP_MISMATCH_COLUMNCO> retReportsMismatchRelColsList(ReportsMismatchSC reportsMismatchSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.getReportsMismatchRelColsMap");
	return (ArrayList<REP_MISMATCH_COLUMNCO>) getSqlMap().queryForList(
		"reportsMismatchMapper.getReportsMismatchRelColsList", reportsMismatchSC);
    }

    public int retReportsMismatchRelColsListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.getReportsMismatchRelColsMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("reportsMismatchMapper.getReportsMismatchRelColsListCount",
		reportsMismatchSC)).intValue();
	return nb;
    }

    public ArrayList<REP_MISMATCH_PARAMCO> retRelatedReportsMismatchList(ReportsMismatchSC reportsMismatchSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.getReportsMismatchMap");
	if(reportsMismatchSC.getSortOrder() == null)
	{
	    reportsMismatchSC.setSortOrder(" ORDER BY REP_MISMATCH_ID");
	}
	reportsMismatchSC.setMisType(BigDecimal.valueOf(1));
	return (ArrayList<REP_MISMATCH_PARAMCO>) getSqlMap().queryForList(
		"reportsMismatchMapper.getRelatedReportsMismatchList", reportsMismatchSC);
    }

    public int retRelatedReportsMismatchListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.getReportsMismatchMap");
	int nb = 0;
	reportsMismatchSC.setMisType(BigDecimal.valueOf(1));
	nb = ((Integer) getSqlMap().queryForObject("reportsMismatchMapper.getRelatedReportsMismatchListCount",
		reportsMismatchSC)).intValue();
	return nb;
    }

    public int deleteRelColsById(ReportsMismatchSC sc) throws DAOException
    {
	return getSqlMap().delete("reportsMismatchMapper.deleteRelColsByRelRep", sc);
    }

    public int deleteRelColByProgRefCriteria(ReportsMismatchSC sc) throws DAOException
    {
	return getSqlMap().delete("reportsMismatchMapper.deleteRelColByProgRefCriteria", sc);
    }

    public ArrayList<REP_MISMATCH_INTRA_CRITERIACO> retReportsMismatchRelCriteriaList(
	    ReportsMismatchSC reportsMismatchSC) throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.retReportsMismatchRelCriteriaMap");
	if(reportsMismatchSC.getSortOrder() == null)
	{
	    reportsMismatchSC.setSortOrder(" ORDER BY REP_MISMATCH_ID");
	}
	return (ArrayList<REP_MISMATCH_INTRA_CRITERIACO>) getSqlMap().queryForList(
		"reportsMismatchMapper.retReportsMismatchRelCriteriaList", reportsMismatchSC);
    }

    public int retReportsMismatchRelCriteriaListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException
    {
	DAOHelper.fixGridMaps(reportsMismatchSC, getSqlMap(), "reportsMismatchMapper.retReportsMismatchRelCriteriaMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("reportsMismatchMapper.retReportsMismatchRelCriteriaListCount",
		reportsMismatchSC)).intValue();
	return nb;
    }

    public int deleteRelCrtById(ReportsMismatchSC sc) throws DAOException
    {
	return getSqlMap().delete("reportsMismatchMapper.deleteRelCrtById", sc);
    }

    public int deleteRelCrtByProgRefCriteria(ReportsMismatchSC sc) throws DAOException
    {
	return getSqlMap().delete("reportsMismatchMapper.deleteRelCrtByProgRefCriteria", sc);
    }

    public void insertRelatedReports(REP_MISMATCH_PARAMVO relatedReportVO) throws DAOException
    {
	getSqlMap().insert("reportsMismatchMapper.insertRelatedReports", relatedReportVO);
    }

    public int updateRelatedReport(REP_MISMATCH_PARAMCO relatedReportCO) throws DAOException
    {
	return getSqlMap().update("reportsMismatchMapper.updateRelatedReport", relatedReportCO);
    }

    public boolean checkIfDateUpdatedModified(ReportsMismatchSC sc) throws DAOException
    {
	sc.setMisType(BigDecimal.valueOf(1));
	int nb = ((Integer) getSqlMap().queryForObject("reportsMismatchMapper.checkIfDateUpdatedModified", sc))
		.intValue();
	if(nb == 1)
	{
	    return true;
	}
	return false;
    }

    public HashMap<String, REP_MISMATCH_PARAMCO> fillHashInterIntraInit(ReportsMismatchSC sc) throws DAOException
    {
	return (HashMap<String, REP_MISMATCH_PARAMCO>) getSqlMap().queryForMap(
		"reportsMismatchMapper.fillHashInterIntraInit", sc, "CONCAT_KEY");
    }

    public int updateMainRecordDateUpdated(ReportsMismatchSC sc) throws DAOException
    {
	sc.setMisType(BigDecimal.valueOf(1));
	return getSqlMap().update("reportsMismatchMapper.updateMainRecordDateUpdated", sc);
    }

}
