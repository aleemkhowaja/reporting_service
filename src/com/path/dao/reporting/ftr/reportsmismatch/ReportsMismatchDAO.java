package com.path.dao.reporting.ftr.reportsmismatch;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.path.dbmaps.vo.REP_MISMATCH_PARAMVO;
import com.path.dbmaps.vo.reportsmismatch.ReportsMismatchSC;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_INTRA_CRITERIACO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PARAMCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_PARAMCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * ReportsMismatchDAO.java used to
 */
public interface ReportsMismatchDAO
{
    public ArrayList<REP_MISMATCH_PARAMCO> retReportsMismatchList(ReportsMismatchSC reportsMismatchSC)
	    throws DAOException;

    public int retReportsMismatchListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException;

    public ArrayList<REP_SNAPSHOT_PARAMCO> retReportsMismatchLookupList(ReportsMismatchSC reportsMismatchSC)
	    throws DAOException;

    public int retReportsMismatchLookupListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException;

    public ArrayList<REP_MISMATCH_COLUMNCO> retReportsMismatchRelColsList(ReportsMismatchSC reportsMismatchSC)
	    throws DAOException;

    public int retReportsMismatchRelColsListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException;

    public ArrayList<REP_MISMATCH_PARAMCO> retRelatedReportsMismatchList(ReportsMismatchSC reportsMismatchSC)
	    throws DAOException;

    public int retRelatedReportsMismatchListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException;

    public int deleteRelColsById(ReportsMismatchSC sc) throws DAOException;

    public int deleteRelColByProgRefCriteria(ReportsMismatchSC sc) throws DAOException;

    public ArrayList<REP_MISMATCH_INTRA_CRITERIACO> retReportsMismatchRelCriteriaList(
	    ReportsMismatchSC reportsMismatchSC) throws DAOException;

    public int deleteRelCrtById(ReportsMismatchSC sc) throws DAOException;

    public int deleteRelCrtByProgRefCriteria(ReportsMismatchSC sc) throws DAOException;

    public int retReportsMismatchRelCriteriaListCount(ReportsMismatchSC reportsMismatchSC) throws DAOException;

    public void insertRelatedReports(REP_MISMATCH_PARAMVO relatedReportVO) throws DAOException;

    public int updateRelatedReport(REP_MISMATCH_PARAMCO relatedReportCO) throws DAOException;

    public boolean checkIfDateUpdatedModified(ReportsMismatchSC sc) throws DAOException;

    public HashMap<String, REP_MISMATCH_PARAMCO> fillHashInterIntraInit(ReportsMismatchSC sc) throws DAOException;

    public int updateMainRecordDateUpdated(ReportsMismatchSC sc) throws DAOException;

}