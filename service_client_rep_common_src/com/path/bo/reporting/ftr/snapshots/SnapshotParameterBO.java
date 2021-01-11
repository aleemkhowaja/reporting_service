package com.path.bo.reporting.ftr.snapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_SNAPSHOT_SUB_REPORTVO;
import com.path.dbmaps.vo.REP_SITCOM_FILEVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.USRVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_DRILLDOWN_COLUMNCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_MODIFY_COLUMNCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_PARAMCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * SnapshotParameterBO.java used to
 */
public interface SnapshotParameterBO
{
    public ArrayList<REP_SNAPSHOT_PARAMCO> retSnapshotsParametersList(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException;

    public int retSnapshotsParametersListCount(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    public ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO> retModifiedColumnList(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException;

    public int retModifiedColumnListCount(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    public ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> retDrilldownColumnList(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException;

    public int retDrilldownColumnListCount(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    public void saveAllSnDetails(ArrayList lstAdd, ArrayList lstMod, ArrayList lstDel,
	    HashMap<String, Object> snParameterScreenHash, BTR_CONTROLCO btrControlCO, AuditRefCO refCO,
	    SessionCO sessionCO) throws Exception;

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> retSnapshotInformationList(SnapshotParameterSC snapshotParameterSC,
	    REP_SNAPSHOT_INFORMATIONCO repSnapshotInformationCO) throws BaseException;

    public int retSnapshotInformationListCount(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> selectSnapshotParameterReports(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException;

    public int selectSnapshotParameterReportsCount(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    public void saveAllSnInfoDetails(ArrayList lstMod, ArrayList lstDel, HashMap<String, Object> snInfoScreenHash,
	    AuditRefCO refCO) throws BaseException;

    public int checkSnInformation(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    public HashMap<String, REP_SNAPSHOT_PARAMCO> fillInitMap(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException;

    public HashMap<BigDecimal, REP_SNAPSHOT_INFOVO> fillHashInfoInit(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException;

    public ArrayList<REP_SNAPSHOT_PARAMCO> retSnpFrequenciesLst(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException;

    public Date retGvSystemDate(REP_SNAPSHOT_INFORMATIONCO infoCO) throws BaseException;

    public void updateSnpInfo(REP_SNAPSHOT_INFOVO repSnpInfoVO) throws BaseException;

    public void insertSnpInfo(REP_SNAPSHOT_INFOVO repSnpInfoVO) throws BaseException;

    public BigDecimal checkRepHasEnableSnpParam(SnapshotParameterSC repSnpInfoSC) throws BaseException;

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> retSnpListByRepIdAndFreq(SnapshotParameterSC repSnpInfoSC)
	    throws BaseException;

    public REP_SNAPSHOT_INFOVO retSnpInfoVOByRepSnpId(REP_SNAPSHOT_INFOVO repSnpInfoVO) throws BaseException;

    public HashMap<String, REP_SNAPSHOT_MODIFY_COLUMNCO> retModifiedColToEdit(SnapshotParameterSC paramSC)
	    throws BaseException;

    public REP_SNAPSHOT_INFORMATIONCO retSnpFormatDetails(SnapshotParameterSC paramSC) throws BaseException;

    public void deleteSnpSubReportByRepSnpId(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO) throws BaseException;

    public void insertSnpSubReportByRepSnpId(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO) throws BaseException;

    public List<IRP_SNAPSHOT_SUB_REPORTVO> retSnapshotSubReportList(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO)
	    throws BaseException;

    public int checkIncludeFileConditions(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    public REP_SITCOM_FILEVO retSitcomFile(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    /**
     * Method that returns the formula to be applied
     * @param snapshotParameterSC
     * @return
     * @throws BaseException
     */
    public REP_SNAPSHOT_INFORMATIONCO retFormulaToApply(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    public void insertSitcomFile(REP_SITCOM_FILEVO repSitcomFileVO,
	    ArrayList<REP_SNAPSHOT_INFORMATIONCO> coToGenerateList) throws BaseException;

    public void updateSitcomFile(REP_SITCOM_FILEVO repSitcomFileVO,
	    ArrayList<REP_SNAPSHOT_INFORMATIONCO> coToGenerateList) throws BaseException;

    public ArrayList<USRVO> selectSnapshotUser(SnapshotParameterSC snapshotParameterSC)throws BaseException;
    
    public int retSnapshotUserCount(SnapshotParameterSC snapshotParameterSC) throws BaseException;

    /**
     * Method that loads the arguments related to fcr reports
     * @param fcrType
     * @return
     * @throws BaseException
     */
    public ArrayList<IRP_REP_ARGUMENTSCO> loadDynamicParams(String fcrType) throws BaseException;
    
    /**
	 * Method to check the existence of a snapshot parameter
	 * report
	 * @param snapshotParameterSC
	 * @return 
	 * @throws BaseException
	 */
	public int checkTextFormulaExist(SnapshotParameterSC snapshotParameterSC) throws BaseException;

	/**
	 * Method that returns details related to the main report of the snapshot
	 * report
	 * @param  retInfoVO
	 * @return REP_SNAPSHOT_INFORMATIONCO
	 * @throws BaseException
	 */
	public REP_SNAPSHOT_INFORMATIONCO retSnapshotMainReportDetails(REP_SNAPSHOT_INFOVO retInfoVO)throws BaseException;

}