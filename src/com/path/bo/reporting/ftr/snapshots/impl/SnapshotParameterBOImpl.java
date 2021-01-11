package com.path.bo.reporting.ftr.snapshots.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dao.reporting.ftr.snapshots.SnapshotParameterDAO;
import com.path.dbmaps.vo.IRP_SNAPSHOT_PARAM_MAPPINGVO;
import com.path.dbmaps.vo.IRP_SNAPSHOT_SUB_REPORTVO;
import com.path.dbmaps.vo.REP_SITCOM_FILEVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_DRILLDOWN_COLUMNVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_MODIFY_COLUMNVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.USRVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.vo.common.DBSequenceSC;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
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
 * SnapshotParameterBOImpl.java used to
 */
public class SnapshotParameterBOImpl extends BaseBO implements SnapshotParameterBO
{
    SnapshotParameterDAO snapshotParameterDAO;
    DesignerBO designerBO;

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public SnapshotParameterDAO getSnapshotParameterDAO()
    {
	return snapshotParameterDAO;
    }

    public void setSnapshotParameterDAO(SnapshotParameterDAO snapshotParameterDAO)
    {
	this.snapshotParameterDAO = snapshotParameterDAO;
    }

    public ArrayList<REP_SNAPSHOT_PARAMCO> retSnapshotsParametersList(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException
    {
	return snapshotParameterDAO.getSnapshotsParametersList(snapshotParameterSC);
    }

    public int retSnapshotsParametersListCount(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.getSnapshotsParametersListCount(snapshotParameterSC);
    }

    public ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO> retModifiedColumnList(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException
    {
	return snapshotParameterDAO.getModifiedColumnList(snapshotParameterSC);
    }

    public int retModifiedColumnListCount(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.getModifiedColumnListCount(snapshotParameterSC);
    }

    public ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> retDrilldownColumnList(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException
    {
	return snapshotParameterDAO.getDrilldownColumnList(snapshotParameterSC);
    }

    public int retDrilldownColumnListCount(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.getDrilldownColumnListCount(snapshotParameterSC);
    }

    public void saveAllSnDetails(ArrayList lstAdd, ArrayList lstMod, ArrayList lstDel,
	    HashMap<String, Object> snParameterScreenHash, BTR_CONTROLCO btrControlCO, AuditRefCO refCO,
	    SessionCO sessionCO) throws BaseException
    {
	String appName = sessionCO.getCurrentAppName();
	/*
	 * 1-save in btr_control table 2-save in rep_snapshot_param 3-save in
	 * drill down and modify columns tables
	 */
	// btr_control table
	if(ConstantsCommon.TRUE.equals(btrControlCO.getSitcomEnableYn()))
	{
	    btrControlCO.getBtrControlVO().setSITCOM_ENABLE_YN(RepConstantsCommon.Y);
	}
	else
	{
	    btrControlCO.getBtrControlVO().setSITCOM_ENABLE_YN(RepConstantsCommon.N);
	}
	genericDAO.update(btrControlCO.getBtrControlVO());
	BigDecimal COMP_CODE = sessionCO.getCompanyCode();
	// get the modify and drilldown from session
	HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>> hashModifyColumnCO = (HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>) snParameterScreenHash
		.get(RepConstantsCommon.SNAPSHOT_MODIFIED);
	HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>> hashDrilldownColumnCO = (HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) snParameterScreenHash
		.get(RepConstantsCommon.SNAPSHOT_DRILLDOWN);
	HashMap<String, REP_SNAPSHOT_PARAMCO> initMap = (HashMap<String, REP_SNAPSHOT_PARAMCO>) snParameterScreenHash
		.get(RepConstantsCommon.SNAPSHOT_INIT_MAP);
	ArrayList<REP_SNAPSHOT_PARAMVO> listVOToBeUpdated = new ArrayList<REP_SNAPSHOT_PARAMVO>();

	// SAVE GRID
	// start with newly added records
	Iterator<REP_SNAPSHOT_PARAMCO> itAdd = lstAdd.iterator();
	REP_SNAPSHOT_PARAMCO repCO;
	int count;
	REP_SNAPSHOT_PARAMVO repVO;
	IRP_SNAPSHOT_PARAM_MAPPINGVO repSnapshotParamMappingVO = new IRP_SNAPSHOT_PARAM_MAPPINGVO();
	BigDecimal repId = null;
	SnapshotParameterSC sc = new SnapshotParameterSC();
	REP_SNAPSHOT_PARAMVO lVO;
	DBSequenceSC dbSeqSC = new DBSequenceSC();
	dbSeqSC.setSequenceName("FTR_SNAP_PARAM_SEQ");
	dbSeqSC.setTableName("SNAPSHOT_PARAM_IDENTITY");
	while(itAdd.hasNext())
	{
	    repCO = itAdd.next();
	    lVO = repCO.getRepSnapshotParamVO();
		repId = commonLibBO.returnTableSequence(dbSeqSC);
	    changeRepIdaddedHash(repId, lVO.getREP_REFERENCE(), lVO.getSNAPSHOT_FREQUENCY(), snParameterScreenHash);
	    lVO.setREP_ID(repId);
	    adjustCheckBoxValues(lVO);
	    // for sybase lVO.setREP_ID(null);
	    genericDAO.insert(lVO);
	    // put the value of param name instead of the label
	    changeMappingToLabel(repSnapshotParamMappingVO, repCO, appName);
	    // end
	    repSnapshotParamMappingVO.setREP_ID(repId);
	    repSnapshotParamMappingVO.setMAPPING_CODE(BigDecimal.valueOf(1));
	    genericDAO.insert(repSnapshotParamMappingVO);
	    // start audit
	    refCO.setTrxNbr(null);
	    refCO.setAuditCO(null);
	    refCO.setOperationType(AuditConstant.CREATED);
	    auditBO.callAudit(lVO, lVO, refCO);
	    // end audit
	}

	// update records grid
	Iterator<REP_SNAPSHOT_PARAMCO> itMod = lstMod.iterator();
	// below map contains new VOs for the mapping parameter
	HashMap<BigDecimal, IRP_SNAPSHOT_PARAM_MAPPINGVO> auditMap = new HashMap<BigDecimal, IRP_SNAPSHOT_PARAM_MAPPINGVO>();
	while(itMod.hasNext())
	{
	    repCO = itMod.next();
	    lVO = repCO.getRepSnapshotParamVO();
	    adjustCheckBoxValues(lVO);
	    lVO.setDATE_UPDATED(initMap.get(lVO.getREP_REFERENCE() + "~" + lVO.getSNAPSHOT_FREQUENCY())
		    .getRepSnapshotParamVO().getDATE_UPDATED());
	    listVOToBeUpdated.add(lVO);
	    changeMappingToLabel(repSnapshotParamMappingVO, repCO, appName);
	    repSnapshotParamMappingVO.setREP_ID(lVO.getREP_ID());
	    repSnapshotParamMappingVO.setMAPPING_CODE(BigDecimal.valueOf(1));
	    // case where the user choosed a report that cannot have a param
	    // mapping
	    if(repSnapshotParamMappingVO.getPARAM_NAME() == null || repSnapshotParamMappingVO.getMAPPING_CODE() == null)
	    {
		if(repSnapshotParamMappingVO.getREP_ID() == null)
		{
		    break;
		}
		// to avoid seing a param appearing in the grid for an fcr
		// report (bug)
		else
		{
		    genericDAO.delete(repSnapshotParamMappingVO);
		    break;
		}
	    }
	    count = genericDAO.update(repSnapshotParamMappingVO);
	    if(count > 0)
	    {
		auditMap.put(repSnapshotParamMappingVO.getREP_ID(), repSnapshotParamMappingVO);
	    }
	    if(count == 0 && !"".equals(repCO.getPARAM_NAME().trim()))
	    {
		genericDAO.insert(repSnapshotParamMappingVO);
		auditMap.put(repSnapshotParamMappingVO.getREP_ID(), repSnapshotParamMappingVO);
	    }
	}

	// delete records grid
	Iterator<REP_SNAPSHOT_PARAMCO> itDel = lstDel.iterator();
	String trxNbr;
	while(itDel.hasNext())
	{
	    repCO = itDel.next();
	    repVO = repCO.getRepSnapshotParamVO();
	    repId = repVO.getREP_ID();
	    sc.setREP_ID(repId);
	    // first delete from table irp_snapshot_param_mapping
	    repSnapshotParamMappingVO.setPARAM_NAME(repCO.getPARAM_NAME());
	    repSnapshotParamMappingVO.setREP_ID(repId);
	    repSnapshotParamMappingVO.setMAPPING_CODE(BigDecimal.valueOf(1));
	    genericDAO.delete(repSnapshotParamMappingVO);
	    snapshotParameterDAO.deleteSnapshotParam(sc);
	    // added for audit
	    refCO.setTrxNbr(null);
	    refCO.setAuditCO(null);
	    trxNbr = auditBO.checkAuditKey(repVO, refCO);
	    refCO.setTrxNbr(trxNbr);
	    refCO.setOperationType(AuditConstant.DELETE);
	    auditBO.callAudit(repVO, repVO, refCO);
	    /*
	     * added the below checking for the case where the audit columns are
	     * set to 0 in opt table
	     */
	    if(refCO.getAuditCO() != null)
	    {
		refCO.getAuditCO().getAuditVO().setTRX_NBR(trxNbr);
		auditBO.insertAudit(refCO);
		// end added for audit
	    }
	    // now remove the deleted repId from the 2 session hash (modify
	    // and
	    // drilldown)
	    if(hashModifyColumnCO != null)
	    {
		hashModifyColumnCO.remove(repVO.getREP_REFERENCE() + "~" + repVO.getSNAPSHOT_FREQUENCY());
	    }
	    if(hashDrilldownColumnCO != null)
	    {
		hashDrilldownColumnCO.remove(repVO.getREP_REFERENCE() + "~" + repVO.getSNAPSHOT_FREQUENCY());
	    }
	}

	// testing nullity for the case the session hash is empty
	if(hashModifyColumnCO != null)
	{
	    // insert modify column (will be a delete insert)
	    // first delete all records related to repid
	    Iterator<Map.Entry<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>> itFmap = hashModifyColumnCO.entrySet()
		    .iterator();
	    String theKey;
	    String frequency;
	    String reference;
	    while(itFmap.hasNext())
	    {
		Map.Entry<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>> theList = itFmap.next();
		ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO> list = theList.getValue();
		theKey = theList.getKey();
		frequency = theKey.substring(theKey.indexOf("~") + 1, theKey.length());
		reference = theKey.substring(0, theKey.indexOf("~"));
		sc.setREP_REFERENCE(reference);
		sc.setSNAPSHOT_FREQUENCY(frequency);
		if(!list.isEmpty())
		{
		    repId = list.get(0).getRepSnapshotModifyColumnVO().getREP_ID();
		}
		// null repid => record is newly being created in
		// rep_snapshot_param
		//stopped 29 SEP because repId should never be null and should not check for date_updated
		//if it's a record newly created
//		if(repId == null)
//		{
//		    repId = snapshotParameterDAO.getRepIdByRefFreq(sc);
//		}
//		else
//		{
		//stopped 29 SEP
		//the check on initMap is to be sure it's not a newly created record
		if(repId!=null && initMap.get(sc.getREP_REFERENCE() + "~" + sc.getSNAPSHOT_FREQUENCY())!=null)
		{
		    sc.setREP_ID(repId);
		    /*
		     * In this case the record is not being newly created.check
		     * the date updated.check first the number of records
		     * related to the repId.then delete using the check of
		     * dateUpdated for the main record in the grid
		     */
		    int nbInitRecords = snapshotParameterDAO.getModifiedColumnListCount(sc);
		    sc.setDateUpdated(initMap.get(sc.getREP_REFERENCE() + "~" + sc.getSNAPSHOT_FREQUENCY())
			    .getRepSnapshotParamVO().getDATE_UPDATED());
		    if(snapshotParameterDAO.deleteModColById(sc) == 0 && nbInitRecords > 0)
		    {
			throw new BOException(MessageCodes.RECORD_CHANGED);
		    }
		}

//		}
	    }

	    // now insert
	    Iterator<ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>> hashModIt = hashModifyColumnCO.values().iterator();
	    Iterator<REP_SNAPSHOT_MODIFY_COLUMNCO> it;
	    ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO> listCO;
	    REP_SNAPSHOT_MODIFY_COLUMNCO modColCO;
	    REP_SNAPSHOT_MODIFY_COLUMNVO vo;
	    REP_SNAPSHOT_PARAMVO reppVO;
	    while(hashModIt.hasNext())
	    {
		listCO = hashModIt.next();
		it = listCO.iterator();
		while(it.hasNext())
		{
		    modColCO=it.next();
		    vo = modColCO.getRepSnapshotModifyColumnVO();
		    if(RepConstantsCommon.NUMBER_TYPE_JASPER.equalsIgnoreCase(vo.getCOLUMN_TYPE()))
		    {
			vo.setCOLUMN_TYPE(RepConstantsCommon.N);
		    }
		    else if(RepConstantsCommon.VARCHAR_TYPE_JASPER.equalsIgnoreCase(vo.getCOLUMN_TYPE()))
		    {
			vo.setCOLUMN_TYPE(RepConstantsCommon.VARCHAR_V);
		    }
		    else
		    {
			vo.setCOLUMN_TYPE(RepConstantsCommon.N);
		    }
		    if(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(vo.getREP_ID()))
		    {
			continue;
		    }
		    else
		    {
			vo.setCOLUMN_MODIFY(modColCO.getColTechName());
			genericDAO.insert(vo);
			/*
			 * checking if the main grid's record is already in the
			 * list else add it in order to show in the audit as
			 * modified but without details
			 */
			boolean notExisting = true;
			for(int i = 0; i < listVOToBeUpdated.size(); i++)
			{
			    if(listVOToBeUpdated.get(i).getREP_ID().equals(vo.getREP_ID()))
			    {
				notExisting = false;
			    }
			}
			if(notExisting)
			{
			    reppVO = new REP_SNAPSHOT_PARAMVO();
			    reppVO.setREP_ID(vo.getREP_ID());
			    reppVO.setCOMP_CODE(COMP_CODE);
			    listVOToBeUpdated.add(reppVO);
			}
		    }
		}
	    }
	}

	// testing nullity for the case the session hash is empty
	if(hashDrilldownColumnCO != null)
	{
	    // insert drill down column (will be a delete insert)
	    // first delete all records related to repid
	    Iterator<Map.Entry<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>> itFmap = hashDrilldownColumnCO
		    .entrySet().iterator();
	    while(itFmap.hasNext())
	    {
		Map.Entry<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>> theList = itFmap.next();
		ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> list = theList.getValue();
		String theKey = theList.getKey();
		String frequency = theKey.substring(theKey.indexOf("~") + 1, theKey.length());
		String reference = theKey.substring(0, theKey.indexOf("~"));
		sc.setREP_REFERENCE(reference);
		sc.setSNAPSHOT_FREQUENCY(frequency);
		if(!list.isEmpty())
		{
		    repId = list.get(0).getRepSnapshotDrilColVO().getREP_ID();
		}
		//stopped 29 SEP because if a new record no need to pass by date_updated
//		if(repId == null)
//		{
//		    repId = snapshotParameterDAO.getRepIdByRefFreq(sc);
//		}
//		else
//		{
		//the check on initMap is to be sure it's not a newly created record
		if(repId!=null && initMap.get(sc.getREP_REFERENCE() + "~" + sc.getSNAPSHOT_FREQUENCY())!=null)
		{
		    sc.setREP_ID(repId);
		    /*
		     * In this case the record is not being newly created.check
		     * the date updated.check first the number of records
		     * related to the repId.then delete using the check of
		     * dateUpdated for the main record in the grid
		     */
		    int nbInitRecords = snapshotParameterDAO.getDrilldownColumnListCount(sc);
		    sc.setDateUpdated(initMap.get(sc.getREP_REFERENCE() + "~" + sc.getSNAPSHOT_FREQUENCY())
			    .getRepSnapshotParamVO().getDATE_UPDATED());
		    if(snapshotParameterDAO.deleteDrilColById(sc) == 0 && nbInitRecords > 0)
		    {
			throw new BOException(MessageCodes.RECORD_CHANGED);
		    }
		}
		//}
	    }

	    Iterator<ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>> hashDrilIt = hashDrilldownColumnCO.values().iterator();
	    ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> listCO;
	    REP_SNAPSHOT_DRILLDOWN_COLUMNVO vo;
	    REP_SNAPSHOT_DRILLDOWN_COLUMNCO co;
	    REP_SNAPSHOT_PARAMVO reppVO;
	    while(hashDrilIt.hasNext())
	    {
		listCO = hashDrilIt.next();
		Iterator<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> it = listCO.iterator();
		while(it.hasNext())
		{
		    co=it.next();
		    vo = co.getRepSnapshotDrilColVO();
		    if(RepConstantsCommon.NUMBER_TYPE_JASPER.equalsIgnoreCase(vo.getCOLUMN_TYPE()))
		    {
			vo.setCOLUMN_TYPE(RepConstantsCommon.N);
		    }
		    else if(RepConstantsCommon.VARCHAR_TYPE_JASPER.equalsIgnoreCase(vo.getCOLUMN_TYPE()))
		    {
			vo.setCOLUMN_TYPE(RepConstantsCommon.VARCHAR_V);
		    }
		    else
		    {
			vo.setCOLUMN_TYPE(RepConstantsCommon.N);
		    }
		    if(vo.getREP_ID() == ConstantsCommon.EMPTY_BIGDECIMAL_VALUE)
		    {
			continue;
		    }
		    else
		    {
			vo.setCOLUMN_DRILLDOWN(co.getTECH_COL_NAME());
			genericDAO.insert(vo);
			// checking if the main grid's record is already in the
			// list
			// else add it in order to show in the audit as modified
			// but without details
			boolean notExisting = true;
			for(int i = 0; i < listVOToBeUpdated.size(); i++)
			{
			    if(listVOToBeUpdated.get(i).getREP_ID().equals(vo.getREP_ID()))
			    {
				notExisting = false;
			    }
			}
			if(notExisting)
			{
			    reppVO = new REP_SNAPSHOT_PARAMVO();
			    reppVO.setREP_ID(vo.getREP_ID());
			    reppVO.setCOMP_CODE(COMP_CODE);
			    // I will use the null rep_reference and frequency
			    // inside it for checking in applyAudiSn
			    listVOToBeUpdated.add(reppVO);
			}
		    }
		}
	    }
	}
	/*
	 * now update the main grid's record.They was added to a list instead of
	 * directly calling generic update because the deletion in drilldown and
	 * modified columns will throw an incorrect exception due to the
	 * premature update of date_updated
	 */
	Integer row;
	REP_SNAPSHOT_PARAMVO newVO;
	boolean fromModDril = false;
	for(int i = 0; i < listVOToBeUpdated.size(); i++)
	{
	    newVO = listVOToBeUpdated.get(i);
	    if(newVO.getREP_REFERENCE() == null && newVO.getSNAPSHOT_FREQUENCY() == null)
	    {
		fromModDril = true;
	    }
	    else
	    {
		row = genericDAO.update(newVO);
		if(row == null || row < 1)
		{
		    throw new BOException(MessageCodes.RECORD_CHANGED);
		}
		fromModDril = false;
	    }
	    applyAuditSn(newVO, initMap, refCO, auditMap, fromModDril);
	}
    }

    /*
     * below function called for normal grid update or for the case where the
     * user only opens the modify or the drilldown and changes in the lookups
     */
    public void applyAuditSn(REP_SNAPSHOT_PARAMVO newVO, HashMap<String, REP_SNAPSHOT_PARAMCO> initMap,
	    AuditRefCO refCO, HashMap<BigDecimal, IRP_SNAPSHOT_PARAM_MAPPINGVO> auditMap, boolean fromModDril)
	    throws BaseException
    {
	// added for audit
	IRP_SNAPSHOT_PARAM_MAPPINGVO oldMapping = new IRP_SNAPSHOT_PARAM_MAPPINGVO();
	IRP_SNAPSHOT_PARAM_MAPPINGVO newMapping;
	REP_SNAPSHOT_PARAMVO oldVO;
	if(fromModDril)
	{
	    oldVO = newVO;
	}
	else
	{
	    oldVO = initMap.get(newVO.getREP_REFERENCE() + "~" + newVO.getSNAPSHOT_FREQUENCY()).getRepSnapshotParamVO();
	}
	refCO.setTrxNbr(null);
	refCO.setAuditCO(null);
	String trxNbr = auditBO.checkAuditKey(newVO, refCO);
	refCO.setTrxNbr(trxNbr);
	refCO.setOperationType(AuditConstant.UPDATE);
	// for the old gridVO
	// set invisible fields to the same values so they do not appear in the
	// audit screen
	newVO.setCOMP_CODE(oldVO.getCOMP_CODE());
	newVO.setCREATED_BY(oldVO.getCREATED_BY());
	newVO.setCREATED_DATE(oldVO.getCREATED_DATE());
	newVO.setDATE_UPDATED(oldVO.getDATE_UPDATED());
	newVO.setREP_ID(oldVO.getREP_ID());
	newVO.setREP_TYPE(oldVO.getREP_TYPE());
	auditBO.callAudit(oldVO, newVO, refCO);
	/*
	 * added the below checking for the case where the audit columns are set
	 * to 0 in opt table
	 */
	if(refCO.getAuditCO() != null)
	{
	    /* checking if it's a normal case without cleaning */
	    if(!fromModDril && auditMap.get(newVO.getREP_ID()) != null)
	    {
		newMapping = auditMap.get(newVO.getREP_ID());
		oldMapping.setREP_ID(newVO.getREP_ID());
		oldMapping.setPARAM_NAME(initMap.get(newVO.getREP_REFERENCE() + "~" + newVO.getSNAPSHOT_FREQUENCY())
			.getPARAM_NAME());
		oldMapping.setMAPPING_CODE(BigDecimal.valueOf(1));
		// for the mapping VO
		auditBO.callAudit(oldMapping, newMapping, refCO);
		auditMap.remove(newVO.getREP_ID());
	    }
	    refCO.getAuditCO().getAuditVO().setTRX_NBR(trxNbr);
	    auditBO.insertAudit(refCO);
	    // end added for audit
	}
    }

    public void adjustCheckBoxValues(Object obj)
    {
	try
	{
	    if(obj instanceof REP_SNAPSHOT_PARAMVO)
	    {
		REP_SNAPSHOT_PARAMVO lVO = (REP_SNAPSHOT_PARAMVO) obj;
		if(ConstantsCommon.TRUE.equalsIgnoreCase(lVO.getSITCOM_ENABLE_YN()))
		{
		    lVO.setSITCOM_ENABLE_YN(RepConstantsCommon.Y);
		}
		else
		{
		    lVO.setSITCOM_ENABLE_YN(RepConstantsCommon.N);
		}
		if(ConstantsCommon.TRUE.equalsIgnoreCase(lVO.getSAVE_REP_YN()))
		{
		    lVO.setSAVE_REP_YN(RepConstantsCommon.Y);
		}
		else
		{
		    lVO.setSAVE_REP_YN(RepConstantsCommon.N);
		}
		if(ConstantsCommon.TRUE.equalsIgnoreCase(lVO.getSAVE_REP_DETAILS_YN()))
		{
		    lVO.setSAVE_REP_DETAILS_YN(RepConstantsCommon.Y);
		}
		else
		{
		    lVO.setSAVE_REP_DETAILS_YN(RepConstantsCommon.N);
		}
		if(ConstantsCommon.TRUE.equalsIgnoreCase(lVO.getREP_MODIFY_ENABLE_YN()))
		{
		    lVO.setREP_MODIFY_ENABLE_YN(RepConstantsCommon.Y);
		}
		else
		{
		    lVO.setREP_MODIFY_ENABLE_YN(RepConstantsCommon.N);
		}
		if(ConstantsCommon.TRUE.equalsIgnoreCase(lVO.getREP_AUDIT_YN()))
		{
		    lVO.setREP_AUDIT_YN(RepConstantsCommon.Y);
		}
		else
		{
		    lVO.setREP_AUDIT_YN(RepConstantsCommon.N);
		}
		if(ConstantsCommon.TRUE.equalsIgnoreCase(lVO.getREP_COMMENTS_YN()))
		{
		    lVO.setREP_COMMENTS_YN(RepConstantsCommon.Y);
		}
		else
		{
		    lVO.setREP_COMMENTS_YN(RepConstantsCommon.N);
		}
	    }
	    else if(obj instanceof REP_SNAPSHOT_INFOVO)
	    {
		REP_SNAPSHOT_INFOVO vo = (REP_SNAPSHOT_INFOVO) obj;
		if(ConstantsCommon.TRUE.equalsIgnoreCase(vo.getDECLARED_YN()))
		{
		    vo.setDECLARED_YN(RepConstantsCommon.Y);
		}
		else
		{
		    vo.setDECLARED_YN(RepConstantsCommon.N);
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public void changeRepIdaddedHash(BigDecimal repId, String repReference, String frequency,
	    HashMap<String, Object> snParameterScreenHash) throws BOException
    {
	try
	{
	    REP_SNAPSHOT_MODIFY_COLUMNCO repSnModColCO;
	    REP_SNAPSHOT_DRILLDOWN_COLUMNCO repSnDrlColCO;
	    if((HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>) snParameterScreenHash
		    .get(RepConstantsCommon.SNAPSHOT_MODIFIED) != null
		    && (((HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>) (snParameterScreenHash
			    .get(RepConstantsCommon.SNAPSHOT_MODIFIED))).get(repReference + "~" + frequency)) != null)
	    {
		Iterator<REP_SNAPSHOT_MODIFY_COLUMNCO> itMod = (((HashMap<String, ArrayList<REP_SNAPSHOT_MODIFY_COLUMNCO>>) (snParameterScreenHash
			.get(RepConstantsCommon.SNAPSHOT_MODIFIED))).get(repReference + "~" + frequency)).iterator();
		while(itMod.hasNext())
		{
		    repSnModColCO = itMod.next();
		    repSnModColCO.getRepSnapshotModifyColumnVO().setREP_ID(repId);
		}
	    }

	    if((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) snParameterScreenHash
		    .get(RepConstantsCommon.SNAPSHOT_DRILLDOWN) != null
		    && (((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) (snParameterScreenHash
			    .get(RepConstantsCommon.SNAPSHOT_DRILLDOWN))).get(repReference + "~" + frequency)) != null)
	    {
		Iterator<REP_SNAPSHOT_DRILLDOWN_COLUMNCO> itDrl = (((HashMap<String, ArrayList<REP_SNAPSHOT_DRILLDOWN_COLUMNCO>>) (snParameterScreenHash
			.get(RepConstantsCommon.SNAPSHOT_DRILLDOWN))).get(repReference + "~" + frequency)).iterator();
		while(itDrl.hasNext())
		{
		    repSnDrlColCO = itDrl.next();
		    repSnDrlColCO.getRepSnapshotDrilColVO().setREP_ID(repId);
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> retSnapshotInformationList(SnapshotParameterSC snapshotParameterSC,
	    REP_SNAPSHOT_INFORMATIONCO repSnapshotInformationCO) throws BaseException
    {
	StringBuffer colSearchQuery = new StringBuffer();
	if(null != repSnapshotInformationCO)
	{
	    String date;
	    String sqlSearch;
	    snapshotParameterSC.setWhereQuery(null);
	    int isSybase = ConstantsCommon.CURR_DBMS_SYBASE;
	    if(!"".equals(repSnapshotInformationCO.getRepSnapshotParamVO().getREP_REFERENCE()))
	    {
		sqlSearch = " REP_REFERENCE='" + repSnapshotInformationCO.getRepSnapshotParamVO().getREP_REFERENCE()
			+ "' ";
		colSearchQuery.append(sqlSearch);
	    }
	    if(!"".equals(repSnapshotInformationCO.getRepSnapshotInfoVO().getSNAPSHOT_USER()))
	    {
		sqlSearch = " SNAPSHOT_USER='" + repSnapshotInformationCO.getRepSnapshotInfoVO().getSNAPSHOT_USER()
			+ "' ";
		colSearchQuery.append(colSearchQuery.length() == 0 ? sqlSearch : " AND" + sqlSearch);
	    }
	    if(!"".equals(repSnapshotInformationCO.getRepSnapshotInfoVO().getDECLARED_BY()))
	    {
		sqlSearch = " DECLARED_BY='" + repSnapshotInformationCO.getRepSnapshotInfoVO().getDECLARED_BY() + "' ";
		colSearchQuery.append(colSearchQuery.length() == 0 ? sqlSearch : " AND" + sqlSearch);
	    }
	    if(!"".equals(repSnapshotInformationCO.getRepSnapshotParamVO().getADDITIONAL_REFERENCE()))
	    {
		sqlSearch = " ADDITIONAL_REFERENCE='"
			+ repSnapshotInformationCO.getRepSnapshotParamVO().getADDITIONAL_REFERENCE() + "' ";
		colSearchQuery.append(colSearchQuery.length() == 0 ? sqlSearch : " AND" + sqlSearch);
	    }
	    if(null != repSnapshotInformationCO.getRepSnapshotInfoVO().getSNAPSHOT_DATE())
	    {

		date = DateUtil
			.format(repSnapshotInformationCO.getRepSnapshotInfoVO().getSNAPSHOT_DATE(), "dd/MM/yyyy");
		if(isSybase == 1)
		{
		    sqlSearch = " SNAPSHOT_DATE=CONVERT(DATETIME,'" + date + "',103)";
		}
		else
		{
		    sqlSearch = " SNAPSHOT_DATE=to_date('" + date + "','dd/mm/yyyy')";
		}
		colSearchQuery.append(colSearchQuery.length() == 0 ? sqlSearch : " AND" + sqlSearch);
	    }
	    if(null != repSnapshotInformationCO.getRepSnapshotInfoVO().getDECLARED_DATE())
	    {
		date = DateUtil
			.format(repSnapshotInformationCO.getRepSnapshotInfoVO().getDECLARED_DATE(), "dd/MM/yyyy");
		if(isSybase == 1)
		{
		    sqlSearch = " DECLARED_DATE=CONVERT(DATETIME,'" + date + "',103)";
		}
		else
		{
		    sqlSearch = " DECLARED_DATE=to_date('" + date + "','dd/mm/yyyy')";
		}
		colSearchQuery.append(colSearchQuery.length() == 0 ? sqlSearch : " AND" + sqlSearch);
	    }
	    if(null != repSnapshotInformationCO.getRepSnapshotInfoVO().getRETREIVE_DATE())
	    {
		date = DateUtil
			.format(repSnapshotInformationCO.getRepSnapshotInfoVO().getRETREIVE_DATE(), "dd/MM/yyyy");
		if(isSybase == 1)
		{
		    sqlSearch = " RETREIVE_DATE=CONVERT(DATETIME,'" + date + "',103)";
		}
		else
		{
		    sqlSearch = " RETREIVE_DATE=to_date('" + date + "','dd/mm/yyyy')";
		}
		colSearchQuery.append(colSearchQuery.length() == 0 ? sqlSearch : " AND" + sqlSearch);
	    }
	    if(!"".equals(repSnapshotInformationCO.getRepSnapshotInfoVO().getDECLARED_YN()))
	    {
		String declYN = repSnapshotInformationCO.getRepSnapshotInfoVO().getDECLARED_YN();
		if(!"B".equals(declYN))
		{
		    sqlSearch = " DECLARED_YN='" + declYN + "'";
		    colSearchQuery.append(colSearchQuery.length() == 0 ? sqlSearch : " AND" + sqlSearch);
		}
	    }
	    if(!"".equals(repSnapshotInformationCO.getRepSnapshotParamVO().getSNAPSHOT_FREQUENCY()))
	    {
		sqlSearch = " SNP_FRQ='" + repSnapshotInformationCO.getRepSnapshotParamVO().getSNAPSHOT_FREQUENCY()
			+ "'";
		colSearchQuery.append(colSearchQuery.length() == 0 ? sqlSearch : " AND" + sqlSearch);
	    }
	    snapshotParameterSC.setWhereQuery(colSearchQuery.toString());
	}
	return snapshotParameterDAO.retSnapshotInformationList(snapshotParameterSC,colSearchQuery.toString());
    }

    public int retSnapshotInformationListCount(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.retSnapshotInformationListCount(snapshotParameterSC);
    }

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> selectSnapshotParameterReports(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException
    {
	return snapshotParameterDAO.selectSnapshotParameterReports(snapshotParameterSC);
    }

    public int selectSnapshotParameterReportsCount(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.selectSnapshotParameterReportsCount(snapshotParameterSC);
    }

    public void saveAllSnInfoDetails(ArrayList lstMod, ArrayList lstDel, HashMap<String, Object> snInfoScreenHash,
	    AuditRefCO refCO) throws BaseException
    {
	REP_SNAPSHOT_INFOVO infoVO;

	Iterator<REP_SNAPSHOT_INFORMATIONCO> itMod = lstMod.iterator();
	Integer row;
	HashMap<BigDecimal, REP_SNAPSHOT_INFOVO> infoMap = (HashMap<BigDecimal, REP_SNAPSHOT_INFOVO>) snInfoScreenHash
		.get(RepConstantsCommon.SNAPSHOT_INFO_INIT_MAP);
	REP_SNAPSHOT_INFOVO oldVO;
	String trxNbr;
	while(itMod.hasNext())
	{
	    infoVO = itMod.next().getRepSnapshotInfoVO();
	    adjustCheckBoxValues(infoVO);
	    // set date_updated for the vo
	    infoVO.setDATE_UPDATED(((((HashMap<BigDecimal, REP_SNAPSHOT_INFOVO>) snInfoScreenHash
		    .get(RepConstantsCommon.SNAPSHOT_INFO_INIT_MAP)).get(infoVO.getREP_SNAPSHOT_ID())))
		    .getDATE_UPDATED());
	    row = snapshotParameterDAO.updateRepSnapshotInfo(infoVO);
	    // added for audit
	    oldVO = infoMap.get(infoVO.getREP_SNAPSHOT_ID());
	    refCO.setTrxNbr(null);
	    refCO.setAuditCO(null);
	    trxNbr = auditBO.checkAuditKey(infoVO, refCO);
	    refCO.setTrxNbr(trxNbr);
	    refCO.setOperationType(AuditConstant.UPDATE);
	    auditBO.callAudit(oldVO, infoVO, refCO);
	    /*
	     * added the below checking for the case where the audit columns are
	     * set to 0 in opt table
	     */
	    if(refCO.getAuditCO() != null)
	    {
		refCO.getAuditCO().getAuditVO().setTRX_NBR(trxNbr);
		auditBO.insertAudit(refCO);
		// end added for audit
	    }
	    if(row == null || row < 1)
	    {
		throw new BOException(MessageCodes.RECORD_CHANGED);
	    }

	}

	Iterator<REP_SNAPSHOT_INFORMATIONCO> itDel = lstDel.iterator();
	while(itDel.hasNext())
	{
	    infoVO = itDel.next().getRepSnapshotInfoVO();
	    genericDAO.delete(infoVO);
	    // added for audit
	    refCO.setTrxNbr(null);
	    refCO.setAuditCO(null);
	    trxNbr = auditBO.checkAuditKey(infoVO, refCO);
	    refCO.setTrxNbr(trxNbr);
	    refCO.setOperationType(AuditConstant.DELETE);
	    auditBO.callAudit(infoVO, infoVO, refCO);
	    /*
	     * added the below checking for the case where the audit columns are
	     * set to 0 in opt table
	     */
	    if(refCO.getAuditCO() != null)
	    {
		refCO.getAuditCO().getAuditVO().setTRX_NBR(trxNbr);
		auditBO.insertAudit(refCO);
		// end added for audit
	    }
	}

    }

    public int checkSnInformation(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.checkSnInformation(snapshotParameterSC);
    }

    public void changeMappingToLabel(IRP_SNAPSHOT_PARAM_MAPPINGVO repSnapshotParamMappingVO,
	    REP_SNAPSHOT_PARAMCO repCO, String appName)
    {
	try
	{
	    // put the value of param name instead of the label
	    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	    repSC.setPROG_REF(repCO.getRepSnapshotParamVO().getREP_REFERENCE());
	    repSC.setAPP_NAME(appName);
	    IRP_AD_HOC_REPORTCO repIdCO = designerBO.retRepIdByProgRef(repSC);
	    if(repIdCO != null)
	    {
		IRP_AD_HOC_REPORTCO lRepCO = designerBO.returnReportById(repIdCO.getREPORT_ID());
		Iterator<Map.Entry<Long, IRP_REP_ARGUMENTSCO>> itFmap = lRepCO.getArgumentsList().entrySet().iterator();
		while(itFmap.hasNext())
		{
		    Entry<Long, IRP_REP_ARGUMENTSCO> entry = itFmap.next();
		    if(entry.getValue().getARGUMENT_TYPE().equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_DATE)
			    && entry.getValue().getARGUMENT_LABEL().equals(repCO.getPARAM_NAME()))
		    {
			repSnapshotParamMappingVO.setPARAM_NAME(entry.getValue().getARGUMENT_NAME());
		    }
		}
	    }
	    // end
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public HashMap<String, REP_SNAPSHOT_PARAMCO> fillInitMap(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException
    {
	if(1 == ConstantsCommon.CURR_DBMS_SQLSERVER)
	{
	    snapshotParameterSC.setIsSQLServer(ConstantsCommon.CURR_DBMS_SQLSERVER);
	}
	return snapshotParameterDAO.fillInitMap(snapshotParameterSC);
    }

    public HashMap<BigDecimal, REP_SNAPSHOT_INFOVO> fillHashInfoInit(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException
    {
	return snapshotParameterDAO.fillHashInfoInit(snapshotParameterSC);
    }
    public ArrayList<REP_SNAPSHOT_PARAMCO> retSnpFrequenciesLst(SnapshotParameterSC snapshotParameterSC)
	    throws BaseException
    {
	return snapshotParameterDAO.retSnpFrequenciesLst(snapshotParameterSC);
    }

    public Date retGvSystemDate(REP_SNAPSHOT_INFORMATIONCO infoCO) throws BaseException
    {
	return snapshotParameterDAO.retGvSystemDate(infoCO);
    }

    public void insertSnpInfo(REP_SNAPSHOT_INFOVO repSnpInfoVO) throws BaseException
    {
	genericDAO.insert(repSnpInfoVO);
    }

    public void updateSnpInfo(REP_SNAPSHOT_INFOVO repSnpInfoVO) throws BaseException
    {
	genericDAO.update(repSnpInfoVO);
    }

    public BigDecimal checkRepHasEnableSnpParam(SnapshotParameterSC repSnpInfoSC) throws BaseException
    {
	return snapshotParameterDAO.checkRepHasEnableSnpParam(repSnpInfoSC);
    }

    public ArrayList<REP_SNAPSHOT_INFORMATIONCO> retSnpListByRepIdAndFreq(SnapshotParameterSC repSnpInfoSC)
	    throws BaseException
    {
	return snapshotParameterDAO.retSnpListByRepIdAndFreq(repSnpInfoSC);
    }

    public REP_SNAPSHOT_INFOVO retSnpInfoVOByRepSnpId(REP_SNAPSHOT_INFOVO repSnpInfoVO) throws BaseException
    {
	return (REP_SNAPSHOT_INFOVO) genericDAO.selectByPK(repSnpInfoVO);
    }

    public HashMap<String, REP_SNAPSHOT_MODIFY_COLUMNCO> retModifiedColToEdit(SnapshotParameterSC paramSC)
	    throws BaseException
    {
	return snapshotParameterDAO.retModifiedColToEdit(paramSC);
    }

    public REP_SNAPSHOT_INFORMATIONCO retSnpFormatDetails(SnapshotParameterSC paramSC) throws BaseException
    {
	if("1".equals(paramSC.getSNP_REPORT_TYPE()))
	{
	    return snapshotParameterDAO.retFcrSnpFormatDetails(paramSC);
	}
	else
	{
	    return snapshotParameterDAO.retSnpFormatDetails(paramSC);
	}
    }

    public void deleteSnpSubReportByRepSnpId(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO) throws BaseException
    {
	snapshotParameterDAO.deleteSnpSubReportByRepSnpId(snpSubRepVO);
    }

    public void insertSnpSubReportByRepSnpId(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO) throws BaseException
    {
	genericDAO.insert(snpSubRepVO);
    }

    public List<IRP_SNAPSHOT_SUB_REPORTVO> retSnapshotSubReportList(IRP_SNAPSHOT_SUB_REPORTVO snpSubRepVO)
	    throws BaseException
    {
	return snapshotParameterDAO.retSnapshotSubReportList(snpSubRepVO);
    }

    public int checkIncludeFileConditions(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.checkIncludeFileConditions(snapshotParameterSC);
    }

    public REP_SITCOM_FILEVO retSitcomFile(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	REP_SITCOM_FILEVO vo = new REP_SITCOM_FILEVO();
	vo.setSITCOM_FILE_ID(snapshotParameterSC.getSITCOM_FILE_ID());
	vo = (REP_SITCOM_FILEVO) genericDAO.selectByPK(vo);
	return vo;
    }

    @Override
    public REP_SNAPSHOT_INFORMATIONCO retFormulaToApply(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.retFormulaToApply(snapshotParameterSC);
    }

    public void insertSitcomFile(REP_SITCOM_FILEVO repSitcomFileVO,
	    ArrayList<REP_SNAPSHOT_INFORMATIONCO> coToGenerateList) throws BaseException
    {
    DBSequenceSC dbSeqSC = new DBSequenceSC();
    dbSeqSC.setSequenceName("FTR_SITCOM_FILE_SEQ");
    dbSeqSC.setTableName("SITCOM_FILE_IDENTITY");
    BigDecimal sitcomFileId = commonLibBO.returnTableSequence(dbSeqSC);
	repSitcomFileVO.setSITCOM_FILE_ID(sitcomFileId);
	genericDAO.insert(repSitcomFileVO);
	updateSnpInfoSitId(coToGenerateList, sitcomFileId);
    }

    public void updateSitcomFile(REP_SITCOM_FILEVO repSitcomFileVO,
	    ArrayList<REP_SNAPSHOT_INFORMATIONCO> coToGenerateList) throws BaseException
    {
	genericDAO.update(repSitcomFileVO);
	BigDecimal sitcomFileId = repSitcomFileVO.getSITCOM_FILE_ID();
	updateSnpInfoSitId(coToGenerateList, sitcomFileId);
    }

    public void updateSnpInfoSitId(ArrayList<REP_SNAPSHOT_INFORMATIONCO> coToGenerateList, BigDecimal sitcomFileId)
	    throws BaseException
    {
	REP_SNAPSHOT_INFOVO infoVO;
	for(int i = 0; i < coToGenerateList.size(); i++)
	{
	    infoVO = coToGenerateList.get(i).getRepSnapshotInfoVO();
	    infoVO.setSITCOM_FILE_ID(sitcomFileId);
	    adjustCheckBoxValues(infoVO);
	    genericDAO.update(infoVO);
	}
    }

    public ArrayList<USRVO> selectSnapshotUser(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.selectSnapshotUser(snapshotParameterSC);
    }

    public int retSnapshotUserCount(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
	return snapshotParameterDAO.retSnapshotUserCount(snapshotParameterSC);
    }

    @Override
    public ArrayList<IRP_REP_ARGUMENTSCO> loadDynamicParams(String fcrType) throws BaseException
    {
	return snapshotParameterDAO.loadDynamicParams(fcrType);
    }
    
    @Override
    public int checkTextFormulaExist(SnapshotParameterSC snapshotParameterSC) throws BaseException
    {
    return snapshotParameterDAO.checkTextFormulaExist(snapshotParameterSC);
    }

    @Override
    public REP_SNAPSHOT_INFORMATIONCO retSnapshotMainReportDetails(REP_SNAPSHOT_INFOVO retInfoVO) throws BaseException
    {
	return snapshotParameterDAO.retSnapshotMainReportDetails(retInfoVO);
	
    }
    
    

}
