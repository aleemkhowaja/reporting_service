package com.path.bo.reporting.ftr.reportsmismatch;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;

import com.path.dbmaps.vo.reportsmismatch.ReportsMismatchSC;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_INTRA_CRITERIACO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PARAMCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_PARAMCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * ReportsMismatchBO.java used to
 */
public interface ReportsMismatchBO
{
    public ArrayList<REP_MISMATCH_PARAMCO> retReportsMismatchList(ReportsMismatchSC reportsMismatchSC)
	    throws BaseException;

    public int retReportsMismatchListCount(ReportsMismatchSC reportsMismatchSC) throws BaseException;

    public ArrayList<REP_SNAPSHOT_PARAMCO> retReportsMismatchLookupList(ReportsMismatchSC reportsMismatchSC)
	    throws BaseException;

    public int retReportsMismatchLookupListCount(ReportsMismatchSC reportsMismatchSC) throws BaseException;

    public ArrayList<REP_MISMATCH_COLUMNCO> retReportsMismatchRelColsList(ReportsMismatchSC reportsMismatchSC)
	    throws BaseException;

    public int retReportsMismatchRelColsListCount(ReportsMismatchSC reportsMismatchSC) throws BaseException;

    public ArrayList<REP_MISMATCH_PARAMCO> retRelatedReportsMismatchList(ReportsMismatchSC reportsMismatchSC)
	    throws BaseException;

    public int retRelatedReportsMismatchListCount(ReportsMismatchSC reportsMismatchSC) throws BaseException;

    public void saveAllInterReportsMismatch(ArrayList<REP_MISMATCH_PARAMCO> lstAdd,
	    ArrayList<REP_MISMATCH_PARAMCO> lstModify, ArrayList<REP_MISMATCH_PARAMCO> lstDelete,
	    HashMap<String, Object> repMisParameterScreenHash, BigDecimal compCode, AuditRefCO refCO) throws Exception;

    public ArrayList<REP_MISMATCH_INTRA_CRITERIACO> retReportsMismatchRelCriteriaList(
	    ReportsMismatchSC reportsMismatchSC) throws BaseException;

    public void saveAllIntraReportsMismatch(ArrayList<REP_MISMATCH_PARAMCO> lstAdd,
	    ArrayList<REP_MISMATCH_PARAMCO> lstModify, ArrayList<REP_MISMATCH_PARAMCO> lstDelete,
	    HashMap<String, Object> repMisParameterScreenHash, BigDecimal compCode, AuditRefCO refCO) throws Exception;

    public HashMap<String, REP_MISMATCH_PARAMCO> fillHashInterIntraInit(ReportsMismatchSC sc) throws BaseException;
    
    public HashMap<String, Object> fillColTechNameMap(IRP_AD_HOC_REPORTSC repSC) throws BaseException, IOException;

}