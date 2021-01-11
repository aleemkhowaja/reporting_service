package com.path.dao.reporting.ftr.snapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_SNAPSHOT_SUB_REPORTVO;
import com.path.dbmaps.vo.REP_SITCOM_FILEVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.USRVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_DRILLDOWN_COLUMNCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_MODIFY_COLUMNCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_PARAMCO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * SnapshotParameterDAO.java used to
 */
public interface SnapshotParameterDAO
{
    public ArrayList<REP_SNAPSHOT_PARAMCO> getSnapshotsParametersList(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException;

    public int getSnapshotsParametersListCount(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    public ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO> getModifiedColumnList(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException;

    public int getModifiedColumnListCount(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    public ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> getDrilldownColumnList(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException;

    public int getDrilldownColumnListCount(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    public void deleteSnapshotParam(SnapshotParameterSC sc) throws DAOException;

    public int deleteModColById(SnapshotParameterSC sc) throws DAOException;

    public int deleteDrilColById(SnapshotParameterSC sc) throws DAOException;

    public BigDecimal getRepIdByRefFreq(SnapshotParameterSC sc) throws DAOException;

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> retSnapshotInformationList(SnapshotParameterSC snapshotParameterSC,String whereQuery)
	    throws DAOException;

    public int retSnapshotInformationListCount(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> selectSnapshotParameterReports(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException;

    public int selectSnapshotParameterReportsCount(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    public int checkSnInformation(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    public int updateRepSnapshotInfo(REP_SNAPSHOT_INFOVO repSnapshotInfoVO) throws DAOException;

    public HashMap<String, REP_SNAPSHOT_PARAMCO> fillInitMap(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException;

    public HashMap<BigDecimal, REP_SNAPSHOT_INFOVO> fillHashInfoInit(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException;

    public ArrayList<REP_SNAPSHOT_PARAMCO> retSnpFrequenciesLst(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException;

    public Date retGvSystemDate(REP_SNAPSHOT_INFORMATIONCO infoCO) throws DAOException;

    public BigDecimal checkRepHasEnableSnpParam(SnapshotParameterSC repSnpInfoSC) throws DAOException;

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> retSnpListByRepIdAndFreq(SnapshotParameterSC repSnpInfoSC)
	    throws DAOException;

    public HashMap<String, REP_SNAPSHOT_MODIFY_COLUMNCO> retModifiedColToEdit(SnapshotParameterSC paramSC)
	    throws DAOException;

    public REP_SNAPSHOT_INFORMATIONCO retSnpFormatDetails(SnapshotParameterSC paramSC) throws DAOException;

    public REP_SNAPSHOT_INFORMATIONCO retFcrSnpFormatDetails(SnapshotParameterSC paramSC) throws DAOException;

    public void deleteSnpSubReportByRepSnpId(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO) throws DAOException;

    public List<IRP_SNAPSHOT_SUB_REPORTVO> retSnapshotSubReportList(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO)
	    throws DAOException;

    public int checkIncludeFileConditions(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    public REP_SNAPSHOT_INFORMATIONCO retFormulaToApply(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    public ArrayList<USRVO> selectSnapshotUser(SnapshotParameterSC snapshotParameterSC) throws DAOException;
    
    public int retSnapshotUserCount(SnapshotParameterSC snapshotParameterSC) throws DAOException;
    
    /**
     * Method that update the repId in the table REP_SNAPSHOT_DRILLDOWN_COLUMN
     * @param snapshotParameterSC
     * @return
     * @throws DAOException
     */
    public void updateRepSnpDrillDownRepId(SnapshotParameterSC snapshotParameterSC) throws DAOException;
    
    
    /**
     * Method that update the repId in the table REP_SNAPSHOT_MODIFY_COLUMN
     * @param snapshotParameterSC
     * @return
     * @throws DAOException
     */
    public void updateRepSnpModColRepId(SnapshotParameterSC snapshotParameterSC) throws DAOException;
    
    /**
     * Method that update the repId in the table REP_SNAPSHOT_INFO
     * @param snapshotParameterSC
     * @return
     * @throws DAOException
     */
    public void updateRepSnpInfoRepId(SnapshotParameterSC snapshotParameterSC) throws DAOException;
    
    /**
     * Method that delete from REP_SNAPSHOT_INFO based on the rep_id
     * @param snapshotParameterSC
     * @return
     * @throws DAOException
     */
    public void deleteSnapshotInfByRepId(SnapshotParameterSC snapshotParameterSC) throws DAOException;
    
    /**
     * Method that return the list of additional rep_id based on the rep_ref and the new repIds
     * @param snapshotParameterSC
     * @return
     * @throws DAOException
     */
    public ArrayList<BigDecimal> retAdditionalRepIds(SnapshotParameterSC snapshotParameterSC) throws DAOException;

    /**
     * Method that loads the arguments related to fcr reports
     * @param fcrType
     * @return
     * @throws DAOException
     */
    public ArrayList<IRP_REP_ARGUMENTSCO> loadDynamicParams(String fcrType) throws DAOException;
    
    /**
     * Method that update the rep_id in the table irp_snapshot_param_mapping in order to map to the new rep_id
     * @param snapshotParameterSC
     * @return
     * @throws DAOException
     */
    public void updateIrpSnpParamMapping(SnapshotParameterSC snapshotParameterSC) throws DAOException;
    
    /**
     * Method to check the existence of a snapshot parameter
     * report
     * @param snapshotParameterSC
     * @return 
     * @throws Exception
     */
    public int checkTextFormulaExist(SnapshotParameterSC snapshotParameterSC) throws DAOException;
    

	/**
	 * Method that returns details related to the main report of the snapshot
	 * report
	 * @param  REP_SNAPSHOT_INFORMATIONCO
	 * @return 
	 * @throws DAOException
	 */	
    public REP_SNAPSHOT_INFORMATIONCO retSnapshotMainReportDetails(REP_SNAPSHOT_INFOVO retInfoVO)throws DAOException;

    
}