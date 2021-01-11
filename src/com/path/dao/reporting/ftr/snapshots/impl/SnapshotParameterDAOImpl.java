package com.path.dao.reporting.ftr.snapshots.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.ftr.snapshots.SnapshotParameterDAO;
import com.path.dbmaps.vo.IRP_SNAPSHOT_SUB_REPORTVO;
import com.path.dbmaps.vo.REP_SITCOM_FILEVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.USRVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
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
 * SnapshotParameterDAOImpl.java used to
 */
public class SnapshotParameterDAOImpl extends BaseDAO implements SnapshotParameterDAO
{
    public ArrayList<REP_SNAPSHOT_PARAMCO> getSnapshotsParametersList(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotParameterMapper.getSnapshotsParametersMap");
	if(snapshotParameterSC.getSortOrder() == null)
	{
	    snapshotParameterSC.setSortOrder(" ORDER BY REP_ID ASC");
	}
	return (ArrayList<REP_SNAPSHOT_PARAMCO>) getSqlMap().queryForList(
		"snapshotParameterMapper.getSnapshotsParametersList", snapshotParameterSC);
    }

    public int getSnapshotsParametersListCount(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotParameterMapper.getSnapshotsParametersMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapshotParameterMapper.getSnapshotsParametersListCount",
		snapshotParameterSC)).intValue();
	return nb;
    }

    public ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO> getModifiedColumnList(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException
    {
	if(snapshotParameterSC.isGrid())
	{
	    DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotParameterMapper.getModifiedColumnMap");
	}
	return (ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>) getSqlMap().queryForList(
		"snapshotParameterMapper.getModifiedColumnList", snapshotParameterSC);
    }

    public int getModifiedColumnListCount(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotParameterMapper.getModifiedColumnMap");
	// int nb = 0;
	Integer nb = ((Integer) getSqlMap().queryForObject("snapshotParameterMapper.getModifiedColumnListCount",
		snapshotParameterSC));
	if(nb == null)
	{
	    return 0;
	}
	return nb.intValue();
    }

    public ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> getDrilldownColumnList(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException
    {
	if(snapshotParameterSC.isGrid())
	{
	    DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotParameterMapper.getDrilldownColumnMap");
	}
	return (ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>) getSqlMap().queryForList(
		"snapshotParameterMapper.getDrilldownColumnList", snapshotParameterSC);
    }

    public int getDrilldownColumnListCount(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotParameterMapper.getDrilldownColumnMap");
	Integer nb = ((Integer) getSqlMap().queryForObject("snapshotParameterMapper.getDrilldownColumnListCount",
		snapshotParameterSC));
	if(nb == null)
	{
	    return 0;
	}
	return nb;
    }

    public void deleteSnapshotParam(SnapshotParameterSC sc) throws DAOException
    {
	getSqlMap().delete("snapshotParameterMapper.deleteDrilColById", sc);
	getSqlMap().delete("snapshotParameterMapper.deleteModColById", sc);
	getSqlMap().delete("snapshotParameterMapper.deleteSnParamById", sc);
    }

    public int deleteModColById(SnapshotParameterSC sc) throws DAOException
    {
	return getSqlMap().delete("snapshotParameterMapper.deleteModColById", sc);
    }

    public int deleteDrilColById(SnapshotParameterSC sc) throws DAOException
    {
	return getSqlMap().delete("snapshotParameterMapper.deleteDrilColById", sc);
    }

    public BigDecimal getRepIdByRefFreq(SnapshotParameterSC sc) throws DAOException
    {
	return (BigDecimal) getSqlMap().queryForObject("snapshotParameterMapper.getRepIdByRefFreq", sc);
    }

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> retSnapshotInformationList(SnapshotParameterSC snapshotParameterSC,
	    String whereQuery) throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotInformationMapper.snapshotInformationMap");
	if(!RepConstantsCommon.EMPTY_STRING.equals(whereQuery))
	{
	    snapshotParameterSC.setWhereQuery(whereQuery);
	}
	return (ArrayList<REP_SNAPSHOT_INFORMATIONCO>) getSqlMap().queryForList(
		"snapshotInformationMapper.retSnapshotInformationList", snapshotParameterSC);
    }

    public int retSnapshotInformationListCount(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotInformationMapper.snapshotInformationMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapshotInformationMapper.retSnapshotInformationListCount",
		snapshotParameterSC)).intValue();
	return nb;
    }

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> selectSnapshotParameterReports(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotInformationMapper.snapshotInformationRepMap");
	return (ArrayList<REP_SNAPSHOT_INFORMATIONCO>) getSqlMap().queryForList(
		"snapshotInformationMapper.selectSnapshotParameterReports", snapshotParameterSC,
		snapshotParameterSC.getRecToskip(), snapshotParameterSC.getNbRec());
    }

    public int selectSnapshotParameterReportsCount(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotInformationMapper.snapshotInformationRepMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapshotInformationMapper.selectSnapshotParameterReportsCount",
		snapshotParameterSC)).intValue();
	return nb;
    }

    public int checkSnInformation(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("snapshotParameterMapper.checkSnInformation", snapshotParameterSC))
		.intValue();
    }

    public int updateRepSnapshotInfo(REP_SNAPSHOT_INFOVO repSnapshotInfoVO) throws DAOException
    {
	return getSqlMap().update("snapshotInformationMapper.updateRepSnapshotInfo", repSnapshotInfoVO);
    }

    @Override
    public HashMap<String, REP_SNAPSHOT_PARAMCO> fillInitMap(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException
    {
	return (HashMap<String, REP_SNAPSHOT_PARAMCO>) getSqlMap().queryForMap("snapshotParameterMapper.fillInitMap",
		snapshotParameterSC, "CONCAT_KEY");
    }

    public HashMap<BigDecimal, REP_SNAPSHOT_INFOVO> fillHashInfoInit(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException
    {
	return (HashMap<BigDecimal, REP_SNAPSHOT_INFOVO>) getSqlMap().queryForMap(
		"snapshotInformationMapper.fillHashInfoInit", snapshotParameterSC, "REP_SNAPSHOT_ID");
    }

    public int checkIncludeFileConditions(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	Integer result = ((Integer) getSqlMap().queryForObject("snapshotInformationMapper.checkIncludeFileConditions",
		snapshotParameterSC));
	//
	if(result == null)
	{
	    return 4;
	}
	return result;
    }

    @Override
    public REP_SNAPSHOT_INFORMATIONCO retFormulaToApply(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	return ((REP_SNAPSHOT_INFORMATIONCO) getSqlMap().queryForObject("snapshotInformationMapper.retFormulaToApply", snapshotParameterSC));
    }

    public ArrayList<REP_SNAPSHOT_PARAMCO> retSnpFrequenciesLst(SnapshotParameterSC snapshotParameterSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "snapshotParameterMapper.getSnapshotsParametersMap");
	return (ArrayList<REP_SNAPSHOT_PARAMCO>) getSqlMap().queryForList(
		"snapshotParameterMapper.retSnpFrequenciesLst", snapshotParameterSC);

    }

    public Date retGvSystemDate(REP_SNAPSHOT_INFORMATIONCO infoCO) throws DAOException
    {
	return (Date) getSqlMap().queryForObject("snapshotParameterMapper.retGvSystemDate", infoCO);
    }

    public BigDecimal checkRepHasEnableSnpParam(SnapshotParameterSC repSnpInfoSC) throws DAOException
    {
	return (BigDecimal) getSqlMap().queryForObject("snapshotParameterMapper.checkRepHasEnableSnpParam",
		repSnpInfoSC);
    }

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> retSnpListByRepIdAndFreq(SnapshotParameterSC repSnpInfoSC)
	    throws DAOException
    {
	return (ArrayList<REP_SNAPSHOT_INFORMATIONCO>) getSqlMap().queryForList(
		"snapshotInformationMapper.retSnpListByRepIdAndFreq", repSnpInfoSC);

    }

    public HashMap<String, REP_SNAPSHOT_MODIFY_COLUMNCO> retModifiedColToEdit(SnapshotParameterSC paramSC)
	    throws DAOException
    {
	return (HashMap) getSqlMap().queryForMap("snapshotParameterMapper.retModifiedColToEdit", paramSC,
		"repSnapshotModifyColumnVO.COLUMN_MODIFY");
    }

    public REP_SNAPSHOT_INFORMATIONCO retFcrSnpFormatDetails(SnapshotParameterSC paramSC) throws DAOException
    {
	return (REP_SNAPSHOT_INFORMATIONCO) getSqlMap().queryForObject(
		"snapshotInformationMapper.retFcrSnpFormatDetails", paramSC);
    }

    public REP_SNAPSHOT_INFORMATIONCO retSnpFormatDetails(SnapshotParameterSC paramSC) throws DAOException
    {
	return (REP_SNAPSHOT_INFORMATIONCO) getSqlMap().queryForObject("snapshotInformationMapper.retSnpFormatDetails",
		paramSC);
    }

    public void deleteSnpSubReportByRepSnpId(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO) throws DAOException
    {
	getSqlMap().insert("snapshotInformationMapper.deleteSnpSubReportByRepSnpId", snpSubRepVO);
    }

    public List<IRP_SNAPSHOT_SUB_REPORTVO> retSnapshotSubReportList(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO)
	    throws DAOException
    {
	return getSqlMap().queryForList("snapshotInformationMapper.retSnapshotSubReportList", snpSubRepVO);
    }

    public ArrayList<USRVO> selectSnapshotUser(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "USR.BaseResultMap");
	return (ArrayList<USRVO>) getSqlMap().queryForList("snapshotInformationMapper.selectSnapshotUser",
		snapshotParameterSC, snapshotParameterSC.getRecToskip(), snapshotParameterSC.getNbRec());
    }

    public int retSnapshotUserCount(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	DAOHelper.fixGridMaps(snapshotParameterSC, getSqlMap(), "USR.BaseResultMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapshotInformationMapper.selectSnapshotUserCount",
		snapshotParameterSC)).intValue();
	return nb;
    }

    @Override
    public void updateRepSnpDrillDownRepId(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	getSqlMap().update("snapshotParameterMapper.updateRepSnpDrillDownRepId", snapshotParameterSC);
	
    }

    @Override
    public void updateRepSnpModColRepId(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	getSqlMap().update("snapshotParameterMapper.updateRepSnpModColRepId", snapshotParameterSC);
	
    }

    @Override
    public void updateRepSnpInfoRepId(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	getSqlMap().update("snapshotInformationMapper.updateRepSnpInfoRepId", snapshotParameterSC);
	
    }

    
    @Override
    public void deleteSnapshotInfByRepId(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	getSqlMap().delete("snapshotInformationMapper.deleteSnapshotInfByRepId", snapshotParameterSC);
	
    }

    @Override
    public ArrayList<BigDecimal> retAdditionalRepIds(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
	return (ArrayList<BigDecimal>) getSqlMap().queryForList("snapshotParameterMapper.retAdditionalRepIds", snapshotParameterSC);
    }
    
    
    @Override
    public ArrayList<IRP_REP_ARGUMENTSCO> loadDynamicParams(String fcrType) throws DAOException
    {
	return (ArrayList<IRP_REP_ARGUMENTSCO>) getSqlMap().queryForList("snapshotParameterMapper.loadDynamicParams",
		fcrType);
    }

    @Override
    public void updateIrpSnpParamMapping(SnapshotParameterSC snapshotParameterSC)throws DAOException
    {
	getSqlMap().update("snapshotParameterMapper.updateIrpSnpParamMapping", snapshotParameterSC);
	
    }
    
    @Override
    public int checkTextFormulaExist(SnapshotParameterSC snapshotParameterSC) throws DAOException
    {
    return ((Integer) getSqlMap().queryForObject("snapshotParameterMapper.checkTextFormulaExist", snapshotParameterSC)).intValue();
    }
    
    @Override
    public REP_SNAPSHOT_INFORMATIONCO retSnapshotMainReportDetails(REP_SNAPSHOT_INFOVO retInfoVO)throws DAOException
    {
	return (REP_SNAPSHOT_INFORMATIONCO) getSqlMap().queryForObject("snapshotParameterMapper.retSnapshotMainReportDetails", retInfoVO);
    }
    
}
