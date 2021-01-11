package com.path.bo.reporting.ftr.reportsmismatch.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.util.ReportUtils;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.ftr.reportsmismatch.ReportsMismatchBO;
import com.path.dao.reporting.ftr.reportsmismatch.ReportsMismatchDAO;
import com.path.dbmaps.vo.REP_MISMATCH_COLUMNVO;
import com.path.dbmaps.vo.REP_MISMATCH_INTRA_CRITERIAVO;
import com.path.dbmaps.vo.REP_MISMATCH_PARAMVO;
import com.path.dbmaps.vo.reportsmismatch.ReportsMismatchSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.DBSequenceSC;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
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
 * ReportsMismatchBOImpl.java used to
 */
public class ReportsMismatchBOImpl extends BaseBO implements ReportsMismatchBO
{
    ReportsMismatchDAO reportsMismatchDAO;
    
    DesignerBO designerBO;
    public DesignerBO getDesignerBO()
    {
        return designerBO;
    }
    public void setDesignerBO(DesignerBO designerBO)
    {
        this.designerBO = designerBO;
    }

    public ReportsMismatchDAO getReportsMismatchDAO()
    {
	return reportsMismatchDAO;
    }

    public void setReportsMismatchDAO(ReportsMismatchDAO reportsMismatchDAO)
    {
	this.reportsMismatchDAO = reportsMismatchDAO;
    }

    public ArrayList<REP_MISMATCH_PARAMCO> retReportsMismatchList(ReportsMismatchSC reportsMismatchSC)
	    throws BaseException
    {
	return reportsMismatchDAO.retReportsMismatchList(reportsMismatchSC);
    }

    public int retReportsMismatchListCount(ReportsMismatchSC reportsMismatchSC) throws BaseException
    {
	return reportsMismatchDAO.retReportsMismatchListCount(reportsMismatchSC);
    }

    public ArrayList<REP_SNAPSHOT_PARAMCO> retReportsMismatchLookupList(ReportsMismatchSC reportsMismatchSC)
	    throws BaseException
    {
	return reportsMismatchDAO.retReportsMismatchLookupList(reportsMismatchSC);
    }

    public int retReportsMismatchLookupListCount(ReportsMismatchSC reportsMismatchSC) throws BaseException
    {
	return reportsMismatchDAO.retReportsMismatchLookupListCount(reportsMismatchSC);
    }

    public ArrayList<REP_MISMATCH_COLUMNCO> retReportsMismatchRelColsList(ReportsMismatchSC reportsMismatchSC)
	    throws BaseException
    {
	return reportsMismatchDAO.retReportsMismatchRelColsList(reportsMismatchSC);
    }

    public int retReportsMismatchRelColsListCount(ReportsMismatchSC reportsMismatchSC) throws BaseException
    {
	return reportsMismatchDAO.retReportsMismatchRelColsListCount(reportsMismatchSC);
    }

    public ArrayList<REP_MISMATCH_PARAMCO> retRelatedReportsMismatchList(ReportsMismatchSC reportsMismatchSC)
	    throws BaseException
    {
	return reportsMismatchDAO.retRelatedReportsMismatchList(reportsMismatchSC);
    }

    public int retRelatedReportsMismatchListCount(ReportsMismatchSC reportsMismatchSC) throws BaseException
    {
	return reportsMismatchDAO.retRelatedReportsMismatchListCount(reportsMismatchSC);
    }

    public ArrayList<REP_MISMATCH_INTRA_CRITERIACO> retReportsMismatchRelCriteriaList(
	    ReportsMismatchSC reportsMismatchSC) throws BaseException
    {
	return reportsMismatchDAO.retReportsMismatchRelCriteriaList(reportsMismatchSC);
    }

    public void saveAllInterReportsMismatch(ArrayList<REP_MISMATCH_PARAMCO> lstAdd,
	    ArrayList<REP_MISMATCH_PARAMCO> lstModify, ArrayList<REP_MISMATCH_PARAMCO> lstDelete,
	    HashMap<String, Object> repMisParameterScreenHash, BigDecimal compCode, AuditRefCO refCO) throws Exception
    {

	REP_MISMATCH_PARAMCO co;
	REP_MISMATCH_PARAMCO coo;
	ArrayList<REP_MISMATCH_PARAMCO> listRelRep;
	HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>> relRepHash;
	// below variables used for the cleaning section
	HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>> relRepHashFinal = (HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_REL_REPORTS));
	// will contain listAll
	ArrayList<REP_MISMATCH_PARAMCO> lstAll;
	// will contain listDelete
	ArrayList<REP_MISMATCH_PARAMCO> lstDel;
	/*
	 * added to group mod and delete in one hash map used for audit
	 * checkings.Map added because if we have to insert an audit record for
	 * related reports or related columns in the form of an update done to
	 * the main grid's record,this should be done only if the main grid's
	 * record is not being manipulated (not present in the 3
	 * arraylists).otherwise there will be several audit record for a single
	 * main grid record in one save operation.can't use the initInterIntra
	 * map because it contains all the records from db initially without
	 * specifying which ones will be subject to db modification.When
	 * inserting,delete,update a rel report or a rel column check if it has
	 * its parent in the auditMap.If no=>add it.Should handle the ones being
	 * added to the auditMap with an additional code.when the parent is
	 * inserted,updated or deleted , remove it from the auditMap at the
	 * end,only the non treated main grid records will remain in auditMap
	 * and handle them
	 */
	HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap = new HashMap<BigDecimal, REP_MISMATCH_PARAMCO>();
	for(int i = 0; i < lstModify.size(); i++)
	{
	    auditMap.put(lstModify.get(i).getRepMismatchParamVO().getREP_MISMATCH_ID(), lstModify.get(i));
	}
	
	/************************************************* INSERT ***********************************************************/
	// loop on the list of added records
	for(int i = 0; i < lstAdd.size(); i++)
	{
	    co = lstAdd.get(i);
	    co.getRepMismatchParamVO().setCOMP_CODE(compCode);
	    // insert the new record with its related columns
	    insertRepMismatch(co, repMisParameterScreenHash, BigDecimal.valueOf(1), refCO, auditMap);

	    /*
	     * get the related reports .only listAll is present for this case
	     */

	    relRepHash = ((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_REPORTS))).get(co.getRepMismatchParamVO().getCRITERIA_CODE());
	    if(relRepHash != null)
	    {
		listRelRep = relRepHash.get(RepConstantsCommon.MISMATCH_ALL_REC);
		for(int j = 0; j < listRelRep.size(); j++)
		{
		    coo = listRelRep.get(j);
		    coo.getRepMismatchParamVO().setCOMP_CODE(compCode);
		    insertRepMismatch(coo, repMisParameterScreenHash, BigDecimal.valueOf(1), refCO, auditMap);
		    // after inserting the related report with its related
		    // columns,remove the related report from the session
		    ((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) repMisParameterScreenHash
			    .get(RepConstantsCommon.MISMATCH_REL_REPORTS)).remove(coo.getRepMismatchParamVO()
			    .getCRITERIA_CODE());
		}
	    }

	}
	/************************************************* UPDATE ***********************************************************/
	// check the list of modified records
	for(int i = 0; i < lstModify.size(); i++)
	{
	    co = lstModify.get(i);
	    co.getRepMismatchParamVO().setCOMP_CODE(compCode);
	    /*
	     * get the related reports
	     */
	    relRepHash = ((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_REPORTS))).get(co.getRepMismatchParamVO().getCRITERIA_CODE());
	    if(relRepHash != null)
	    {
		// get the list all ,check if has a rep_mismatch_id.If yes
		// =>update
		listRelRep = relRepHash.get(RepConstantsCommon.MISMATCH_ALL_REC);
		for(int j = 0; j < listRelRep.size(); j++)
		{
		    coo = listRelRep.get(j);
		    // it has a mismatch id then update it
		    if(coo.getRepMismatchParamVO().getREP_MISMATCH_ID().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
		    // the related report does not have a
		    // rep_mismatch_id,then
		    // should be Inserted because it has been added newly to
		    // the
		    // list of related reports and then insert also its
		    // related
		    // columns that are all of them newly added
		    {
			coo.getRepMismatchParamVO().setCOMP_CODE(compCode);
			insertRepMismatch(coo, repMisParameterScreenHash, BigDecimal.valueOf(1), refCO, auditMap);
			// when returning from the insertRepMismatch
			// method,all
			// the related columns are now inserted in db
		    }
		    else
		    {
			updateRepMismatch(coo.getRepMismatchParamVO(), repMisParameterScreenHash,
				BigDecimal.valueOf(1), auditMap, refCO);
			// when update finished remove the ListAll of this
			// related report from
			// the session.its related columns has been removed
			// inside updaterepmismatch

		    }

		}

		/*
		 * now dealing with listDelete of related reports.Will have to
		 * delete the related reports and delete its related columns All
		 * the related reports in the listDel must have a
		 * rep_mismatch_id.if not=> it's a bug in the memory management
		 * of hashs
		 */
		listRelRep = relRepHash.get(RepConstantsCommon.MISMATCH_LST_DEL);
		for(int j = 0; j < listRelRep.size(); j++)
		{
		    coo = listRelRep.get(j);
		    deleteRepMismatch(coo.getRepMismatchParamVO(), repMisParameterScreenHash, BigDecimal.valueOf(1),
			    refCO, false, auditMap);
		}
		relRepHashFinal.remove(co.getRepMismatchParamVO().getCRITERIA_CODE());
	    }
	    // update the record of the main grid with its related columns
	    updateRepMismatch(co.getRepMismatchParamVO(), repMisParameterScreenHash, BigDecimal.valueOf(1), auditMap,
		    refCO);
	}
	/************************************************* DELETE ***********************************************************/
	// now check the list of deleted records
	for(int i = 0; i < lstDelete.size(); i++)
	{
	    co = lstDelete.get(i);
	    co.getRepMismatchParamVO().setCOMP_CODE(compCode);
	    // delete related columns of the main report
	    deleteRepMismatch(co.getRepMismatchParamVO(), repMisParameterScreenHash, BigDecimal.valueOf(1), refCO,
		    true, auditMap);
	    // delete the related columns of the related reports + delete
	    // the
	    // related report
	    relRepHash = ((HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_REPORTS))).get(co.getRepMismatchParamVO().getCRITERIA_CODE());
	    listRelRep = relRepHash.get(RepConstantsCommon.MISMATCH_LST_DEL);
	    for(int j = 0; j < listRelRep.size(); j++)
	    {
		coo = listRelRep.get(j);
		deleteRepMismatch(coo.getRepMismatchParamVO(), repMisParameterScreenHash, BigDecimal.valueOf(1), refCO,
			false, auditMap);
	    }
	    relRepHashFinal.remove(co.getRepMismatchParamVO().getCRITERIA_CODE());
	}
	/************************************************* CLEAN REMAINING SESSION'S DATA *******************************************/
	Iterator<Map.Entry<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>> it = relRepHashFinal.entrySet()
		.iterator();
	ArrayList<String> listKeysToDelete = new ArrayList<String>();
	Entry<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>> entry;
	while(it.hasNext())
	{
	    entry = it.next();
	    lstAll = entry.getValue().get(RepConstantsCommon.MISMATCH_ALL_REC);
	    lstDel = entry.getValue().get(RepConstantsCommon.MISMATCH_LST_DEL);
	    for(int j = 0; j < lstAll.size(); j++)
	    {
		coo = lstAll.get(j);
		// it has a mismatch id then update it
		if(coo.getRepMismatchParamVO().getREP_MISMATCH_ID().equals(ConstantsCommon.EMPTY_BIGDECIMAL_VALUE))
		{
		    coo.getRepMismatchParamVO().setCOMP_CODE(compCode);
		    insertRepMismatch(coo, repMisParameterScreenHash, BigDecimal.valueOf(1), refCO, auditMap);
		}
		else
		{
		    updateRepMismatch(coo.getRepMismatchParamVO(), repMisParameterScreenHash, BigDecimal.valueOf(1),
			    auditMap, refCO);
		}
	    }
	    for(int j = 0; j < lstDel.size(); j++)
	    {
		coo = lstDel.get(j);
		deleteRepMismatch(coo.getRepMismatchParamVO(), repMisParameterScreenHash, BigDecimal.valueOf(1), refCO,
			false, auditMap);
	    }
	    listKeysToDelete.add(entry.getKey());
	}

	for(int i = 0; i < listKeysToDelete.size(); i++)
	{
	    relRepHashFinal.remove(listKeysToDelete.get(i));
	}

	// still have to check the related columns of the main grid records
	// or
	// the related reports related columns
	cleanRelColsRelCrt(repMisParameterScreenHash, 1, auditMap);
	insertAuditMainGridMissing(auditMap, refCO, compCode);
    }

    public void insertAuditMainGridMissing(HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap, AuditRefCO refCO,
	    BigDecimal compCode) throws Exception
    {
	Iterator<Map.Entry<BigDecimal, REP_MISMATCH_PARAMCO>> it = auditMap.entrySet().iterator();
	Entry<BigDecimal, REP_MISMATCH_PARAMCO> entry;
	REP_MISMATCH_PARAMVO oldVO;
	String trxNbr;
	while(it.hasNext())
	{
	    entry = it.next();
	    // added for audit
	    oldVO = new REP_MISMATCH_PARAMVO();
	    oldVO.setREP_MISMATCH_ID(entry.getKey());
	    oldVO.setCOMP_CODE(compCode);
	    refCO.setTrxNbr(null);
	    refCO.setAuditCO(null);
	    trxNbr = auditBO.checkAuditKey(oldVO, refCO);
	    refCO.setTrxNbr(trxNbr);
	    refCO.setOperationType(AuditConstant.UPDATE);
	    auditBO.callAudit(oldVO, oldVO, refCO);
	    if( refCO.getAuditCO()!=null)
	    {
	    refCO.getAuditCO().getAuditVO().setTRX_NBR(trxNbr);
	    auditBO.insertAudit(refCO);
	    }
	    // end added for audit
	}
    }

    // created to avoid redundancy for related columns
    public void cleanRelColsRelCrt(HashMap<String, Object> repMisParameterScreenHash, int mismatchType,
	    HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap) throws Exception
    {
	HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>> relColHashFinal = (HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) (repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_REL_COLS));
	HashMap<String, REP_MISMATCH_PARAMCO> interIntraMap = (HashMap<String, REP_MISMATCH_PARAMCO>) (repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_INTER_INTRA));
	ArrayList<String> listKeysToDelete = new ArrayList<String>();
	ArrayList<REP_MISMATCH_COLUMNCO> listRelCols;
	ReportsMismatchSC sc = new ReportsMismatchSC();
	Iterator<Map.Entry<String, ArrayList<REP_MISMATCH_COLUMNCO>>> itRelCol = relColHashFinal.entrySet().iterator();
	Entry<String, ArrayList<REP_MISMATCH_COLUMNCO>> entry;
	while(itRelCol.hasNext())
	{
	    entry = itRelCol.next();
	    listRelCols = entry.getValue();
	    /*
	     * if the user deleted manually all the related columns of a
	     * record,in the session,there will be an entry with prog ref +
	     * criteria code with corresponding empty arrayList but no mismatch
	     * id.since progRef+criteria are unique delete using them
	     */
	    if(listRelCols.isEmpty())
	    {
		sc.setRepReference(entry.getKey().substring(0, entry.getKey().indexOf("~")));
		sc.setCriteriaCode(entry.getKey().substring(entry.getKey().indexOf("~") + 1, entry.getKey().length()));
		sc.setMisType(BigDecimal.valueOf(mismatchType));
		REP_MISMATCH_PARAMVO lVO = (interIntraMap.get(entry.getKey())).getRepMismatchParamVO();
		sc.setDateUpdated(lVO.getDATE_UPDATED());
		sc.setREP_MISMATCH_ID(lVO.getREP_MISMATCH_ID());
		int nbInitRecords = reportsMismatchDAO.retReportsMismatchRelColsListCount(sc);
		if(reportsMismatchDAO.deleteRelColByProgRefCriteria(sc) == 0 && nbInitRecords > 0)
		{
		    throw new BOException(MessageCodes.RECORD_CHANGED);
		}
		// add control for audit
		auditMap.put(lVO.getREP_MISMATCH_ID(), interIntraMap.get(entry.getKey()));
		// end add control for audit
		listKeysToDelete.add(entry.getKey());
	    }
	    else
	    {
		for(int j = 0; j < listRelCols.size(); j++)
		{
		    updateRepMisRelatedCols(listRelCols, null, interIntraMap, auditMap);
		    listKeysToDelete.add(entry.getKey());
		}
	    }
	}
	HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>> relCrtHashFinal = (HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) (repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_REL_CRITERIA));
	for(int i = 0; i < listKeysToDelete.size(); i++)
	{
	    relColHashFinal.remove(listKeysToDelete.get(i));
	}

	if(mismatchType == 0)
	{
	    ArrayList<REP_MISMATCH_INTRA_CRITERIACO> listRelCrt;
	    // check related criteria of main grid;
	    Iterator<Map.Entry<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>> itRelCrt = relCrtHashFinal.entrySet()
		    .iterator();
	    listKeysToDelete.clear();
	    Entry<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>> entry1;
	    while(itRelCrt.hasNext())
	    {
		entry1 = itRelCrt.next();
		listRelCrt = entry1.getValue();
		/*
		 * if the user deleted manually all the related criteria of a
		 * record,in the session,there will be an entry with prog ref +
		 * criteria code with corresponding empty arrayList but no
		 * mismatch id.since progRef+criteria are unique delete using
		 * them
		 */
		if(listRelCrt.isEmpty())
		{
		    sc.setRepReference(entry1.getKey().substring(0, entry1.getKey().indexOf("~")));
		    sc.setCriteriaCode(entry1.getKey().substring(entry1.getKey().indexOf("~") + 1,
			    entry1.getKey().length()));
		    sc.setMisType(BigDecimal.valueOf(0));

		    REP_MISMATCH_PARAMVO lVO = (interIntraMap.get(entry1.getKey())).getRepMismatchParamVO();
		    sc.setDateUpdated(lVO.getDATE_UPDATED());
		    sc.setREP_MISMATCH_ID(lVO.getREP_MISMATCH_ID());
		    int nbInitRecords = reportsMismatchDAO.retReportsMismatchRelCriteriaListCount(sc);
		    if(reportsMismatchDAO.deleteRelCrtByProgRefCriteria(sc) == 0 && nbInitRecords > 0)
		    {
			throw new BOException(MessageCodes.RECORD_CHANGED);
		    }
		    listKeysToDelete.add(entry1.getKey());
		}
		else
		{
		    for(int j = 0; j < listRelCrt.size(); j++)
		    {
			updateRepMisRelatedCrt(listRelCrt, null, interIntraMap, auditMap);
			listKeysToDelete.add(entry1.getKey());
		    }
		}
	    }

	}
	//
	/*
	 * before removing the key from session,set the parent record in
	 * rep_mismatch_param as been updated by changing the value of
	 * date_updated. relColsListToDelete and relCriteriaListToDelete can
	 * contain entries related to the same id in db.Start by retrieving
	 * distinct mismatch_id from those lists
	 */
	BigDecimal repMismatchId;
	ArrayList<String> relCriteriaListToDelete = (ArrayList<String>) listKeysToDelete.clone();
	ArrayList<String> relColsListToDelete = (ArrayList<String>) listKeysToDelete.clone();
	HashMap<BigDecimal, BigDecimal> listAllIds = new HashMap<BigDecimal, BigDecimal>();
	for(int i = 0; i < relColsListToDelete.size(); i++)
	{
	    repMismatchId = interIntraMap.get(relColsListToDelete.get(i)).getRepMismatchParamVO().getREP_MISMATCH_ID();
	    if(listAllIds.get(repMismatchId) == null)
	    {
		listAllIds.put(repMismatchId, repMismatchId);
	    }
	}
	for(int i = 0; i < relCriteriaListToDelete.size(); i++)
	{
	    repMismatchId = interIntraMap.get(relCriteriaListToDelete.get(i)).getRepMismatchParamVO()
		    .getREP_MISMATCH_ID();
	    if(listAllIds.get(repMismatchId) == null)
	    {
		listAllIds.put(repMismatchId, repMismatchId);
	    }
	}
	Set<BigDecimal> keySet = listAllIds.keySet();
	Iterator<BigDecimal> itKeySet = keySet.iterator();
	BigDecimal theKey;
	Integer row;
	while(itKeySet.hasNext())
	{
	    // theKey is a rep_mismatch_id
	    theKey = itKeySet.next();
	    Iterator<Map.Entry<String, REP_MISMATCH_PARAMCO>> itInterIntra = interIntraMap.entrySet().iterator();
	    while(itInterIntra.hasNext())
	    {
		Entry<String, REP_MISMATCH_PARAMCO> ent = itInterIntra.next();
		if(ent.getValue().getRepMismatchParamVO().getREP_MISMATCH_ID().intValue() == theKey.intValue())
		{
		    // this is the parent id + vo.
		    row = genericDAO.update(ent.getValue().getRepMismatchParamVO());
		    if(row == null || row < 1)
		    {
			throw new BOException(MessageCodes.RECORD_CHANGED);
		    }

		    // auditMap.remove(ent.getValue().getRepMismatchParamVO().getREP_MISMATCH_ID());
		}
	    }
	}
    }

    public void saveAllIntraReportsMismatch(ArrayList<REP_MISMATCH_PARAMCO> lstAdd,
	    ArrayList<REP_MISMATCH_PARAMCO> lstModify, ArrayList<REP_MISMATCH_PARAMCO> lstDelete,
	    HashMap<String, Object> repMisParameterScreenHash, BigDecimal compCode, AuditRefCO refCO) throws Exception
    {
	REP_MISMATCH_PARAMCO co;

	/************************************************* INSERT ***********************************************************/
	// loop on the list of added records
	for(int i = 0; i < lstAdd.size(); i++)
	{
	    co = lstAdd.get(i);
	    co.getRepMismatchParamVO().setCOMP_CODE(compCode);
	    // insert the new record with its related columns
	    insertRepMismatch(co, repMisParameterScreenHash, BigDecimal.valueOf(0), refCO, null);

	}
	/************************************************* UPDATE ***********************************************************/
	HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap = new HashMap<BigDecimal, REP_MISMATCH_PARAMCO>();
	// check the list of modified records
	for(int i = 0; i < lstModify.size(); i++)
	{
	    co = lstModify.get(i);
	    co.getRepMismatchParamVO().setCOMP_CODE(compCode);
	    auditMap.put(co.getRepMismatchParamVO().getREP_MISMATCH_ID(), co);
	    // update the record of the main grid with its related columns
	    updateRepMismatch(co.getRepMismatchParamVO(), repMisParameterScreenHash, BigDecimal.valueOf(0), auditMap,
		    refCO);
	}
	/************************************************* DELETE ***********************************************************/
	// now check the list of deleted records
	for(int i = 0; i < lstDelete.size(); i++)
	{
	    co = lstDelete.get(i);
	    co.getRepMismatchParamVO().setCOMP_CODE(compCode);
	    // delete related columns of the main report
	    deleteRepMismatch(co.getRepMismatchParamVO(), repMisParameterScreenHash, BigDecimal.valueOf(0), refCO,
		    true, auditMap);
	}
	/************************************************* CLEAN REMAINING SESSION'S DATA *******************************************/
	cleanRelColsRelCrt(repMisParameterScreenHash, 0, auditMap);
	insertAuditMainGridMissing(auditMap, refCO, compCode);
    }

    public void deleteRepMismatch(REP_MISMATCH_PARAMVO vo, HashMap<String, Object> repMisParameterScreenHash,
	    BigDecimal misType, AuditRefCO refCO, boolean applyAudit, HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap)
	    throws BaseException
    {
	// first delete the related columns then delete the related report
	ArrayList<REP_MISMATCH_COLUMNCO> listRelCols = ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_REL_COLS)).get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE());
	// 0=>an empty entry exists for those related columns
	// when deleting a related report, an empty entry in the session's
	// hash has been previously added to its related columns
	if(listRelCols != null && listRelCols.isEmpty())
	{
	    // now must delete all the related columns of this related
	    // report
	    ReportsMismatchSC sc = new ReportsMismatchSC();
	    sc.setREP_MISMATCH_ID(vo.getREP_MISMATCH_ID());
	    reportsMismatchDAO.deleteRelColsById(sc);
	    ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_COLS)).remove(vo.getREP_REFERENCE() + "~"
		    + vo.getCRITERIA_CODE());
	}
	if(misType.equals(BigDecimal.valueOf(0)))
	{
	    ReportsMismatchSC sc = new ReportsMismatchSC();
	    sc.setREP_MISMATCH_ID(vo.getREP_MISMATCH_ID());
	    reportsMismatchDAO.deleteRelCrtById(sc);
	    ((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_CRITERIA)).remove(vo.getREP_REFERENCE() + "~"
		    + vo.getCRITERIA_CODE());
	}
	// delete the report
	genericDAO.delete(vo);
	/*
	 * audit must be applied for main grid's records only.Using the last
	 * parameter of the function to have a record with D in audit's table
	 */
	if(applyAudit)
	{
	    // added for audit
	    refCO.setTrxNbr(null);
	    refCO.setAuditCO(null);
	    String trxNbr = auditBO.checkAuditKey(vo, refCO);
	    refCO.setTrxNbr(trxNbr);
	    refCO.setOperationType(AuditConstant.DELETE);
	    auditBO.callAudit(vo, vo, refCO);
	    if(refCO.getAuditCO()!=null)
	    {
	    refCO.getAuditCO().getAuditVO().setTRX_NBR(trxNbr);
	    auditBO.insertAudit(refCO);
	    }
	    // end added for audit
	}
	// put in the auditMap the parent's vo if it doesn't already exist
	else
	{
	    // check if its parent is in the audit map
	    REP_MISMATCH_PARAMCO pMisCO;
	    HashMap<String, REP_MISMATCH_PARAMCO> interIntraMap = (HashMap<String, REP_MISMATCH_PARAMCO>) (repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_INTER_INTRA));
	    REP_MISMATCH_PARAMCO parentCO = interIntraMap.get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE());
	    if(auditMap.get(parentCO.getPARENT_ID()) == null)
	    {
		pMisCO = new REP_MISMATCH_PARAMCO();
		pMisCO.getRepMismatchParamVO().setREP_MISMATCH_ID(parentCO.getPARENT_ID());
		auditMap.put(parentCO.getPARENT_ID(), pMisCO);
	    }
	}
    }

    public void updateRepMismatch(REP_MISMATCH_PARAMVO vo, HashMap<String, Object> repMisParameterScreenHash,
	    BigDecimal misType, HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap, AuditRefCO refCO) throws Exception
    {
	HashMap<String, REP_MISMATCH_PARAMCO> interIntraMap = (HashMap<String, REP_MISMATCH_PARAMCO>) (repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_INTER_INTRA));
	// handling the column type column in the main grid
	String colType = vo.getCOLUMN_TYPE();
	if(RepConstantsCommon.NUMBER_TYPE_JASPER.equalsIgnoreCase(colType))
	{
	    vo.setCOLUMN_TYPE(RepConstantsCommon.N);
	}
	if(RepConstantsCommon.VARCHAR_TYPE_JASPER.equalsIgnoreCase(colType))
	{
	    vo.setCOLUMN_TYPE(RepConstantsCommon.VARCHAR_V);
	}

	/*
	 * get its related columns.All of them have misId setted
	 */
	ArrayList<REP_MISMATCH_COLUMNCO> listRelCols = ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_REL_COLS)).get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE());
	if(listRelCols != null)
	{
	    // pass to it the mismatchId for the case where progref changed
	    updateRepMisRelatedCols(listRelCols, vo, interIntraMap, auditMap);
	    // after inserting the related columns ,remove
	    // the related columns from session
	    ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_COLS)).remove(vo.getREP_REFERENCE() + "~"
		    + vo.getCRITERIA_CODE());
	}
	if(BigDecimal.valueOf(0).equals(misType))
	{
	    ArrayList<REP_MISMATCH_INTRA_CRITERIACO> listRelCrt = ((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_CRITERIA)).get(vo.getREP_REFERENCE() + "~"
		    + vo.getCRITERIA_CODE());
	    if(listRelCrt != null)
	    {
		// pass to it the mismatchId for the case where progref
		// changed
		updateRepMisRelatedCrt(listRelCrt, vo, interIntraMap, auditMap);
		// after inserting the related criteria ,remove
		// the related criteria from session
		((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) repMisParameterScreenHash
			.get(RepConstantsCommon.MISMATCH_REL_CRITERIA)).remove(vo.getREP_REFERENCE() + "~"
			+ vo.getCRITERIA_CODE());
	    }
	}

	// added for date_updated
	Integer row;
	REP_MISMATCH_PARAMCO parentCO = interIntraMap.get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE());
	REP_MISMATCH_PARAMCO oldCO;
	String trxNbr;
	if(BigDecimal.valueOf(1).equals(misType)
		&& !parentCO.getRepMismatchParamVO().getREP_MISMATCH_ID().equals(parentCO.getPARENT_ID()))

	{
	    // use custom update for related reports
	    // parentCO is now same as the VO
	    vo.setDATE_UPDATED(parentCO.getRepMismatchParamVO().getDATE_UPDATED());
	    parentCO.setRepMismatchParamVO(vo);
	    row = reportsMismatchDAO.updateRelatedReport(parentCO);
	    // check if its parent is in the audit map
	    REP_MISMATCH_PARAMCO pMisCO;
	    if(auditMap.get(parentCO.getPARENT_ID()) == null)
	    {
		pMisCO = new REP_MISMATCH_PARAMCO();
		pMisCO.getRepMismatchParamVO().setREP_MISMATCH_ID(parentCO.getPARENT_ID());
		auditMap.put(parentCO.getPARENT_ID(), pMisCO);
	    }
	}
	// usual update for main report
	else
	{
	    vo.setDATE_UPDATED(parentCO.getRepMismatchParamVO().getDATE_UPDATED());
	    row = genericDAO.update(vo);
	    // added for audit
	    oldCO = interIntraMap.get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE());
	    refCO.setTrxNbr(null);
	    refCO.setAuditCO(null);
	    trxNbr = auditBO.checkAuditKey(vo, refCO);
	    refCO.setTrxNbr(trxNbr);
	    refCO.setOperationType(AuditConstant.UPDATE);
	    auditBO.callAudit(oldCO.getRepMismatchParamVO(), vo, refCO);
	    if(refCO.getAuditCO()!=null)
	    {
	    refCO.getAuditCO().getAuditVO().setTRX_NBR(trxNbr);
	    auditBO.insertAudit(refCO);
	    }
	    // end added for audit
	    auditMap.remove(vo.getREP_MISMATCH_ID());
	}

	if(row == null || row < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
    }

    public void updateRepMisRelatedCols(ArrayList<REP_MISMATCH_COLUMNCO> listRelCols, REP_MISMATCH_PARAMVO vo,
	    HashMap<String, REP_MISMATCH_PARAMCO> interIntraMap, HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap)
	    throws Exception
    {
	BigDecimal repMismatchId = null;
	if(vo != null)
	{
	    repMismatchId = vo.getREP_MISMATCH_ID();
	}
	ReportsMismatchSC sc = new ReportsMismatchSC();
	// repMismatchId is the id of the direct parent of the record
	if(repMismatchId == null)
	{
	    // in this case the listRelCols will not be empty in any case
	    sc.setREP_MISMATCH_ID(listRelCols.get(0).getRepMismatchColumnVO().getREP_MISMATCH_ID());
	}
	else
	{
	    sc.setREP_MISMATCH_ID(repMismatchId);
	}

	// vo was being sent null
	if(vo == null)
	{
	    Iterator<Map.Entry<String, REP_MISMATCH_PARAMCO>> itInterIntra = interIntraMap.entrySet().iterator();
	    Entry<String, REP_MISMATCH_PARAMCO> ent;
	    REP_MISMATCH_PARAMCO coBuf;
	    while(itInterIntra.hasNext())
	    {
		ent = itInterIntra.next();
		coBuf = ent.getValue();
		/*
		 * searching for the mismatch_id in the new session hash and
		 * getting the date_updated since it's the same for related
		 * reports and reports
		 */
		if(coBuf.getRepMismatchParamVO().getREP_MISMATCH_ID().equals(sc.getREP_MISMATCH_ID()))
		{
		    sc.setParentId(coBuf.getPARENT_ID());
		    sc.setDateUpdated(coBuf.getRepMismatchParamVO().getDATE_UPDATED());
		    break;
		}
	    }
	}
	/*
	 * below works if there's already at least one record in the table. have
	 * to add a query that checks the number of records for a date_updated
	 * before deleting
	 */
	else
	{
	    sc.setParentId((interIntraMap.get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE())).getPARENT_ID());
	    sc.setDateUpdated((interIntraMap.get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE()))
		    .getRepMismatchParamVO().getDATE_UPDATED());
	}
	int initNbRecords = reportsMismatchDAO.retReportsMismatchRelColsListCount(sc);
	/*
	 * delete returning 0 and there's records for this
	 * mismatch_id=>date_updated has been modified in
	 * rep_mismatch_param=>show alert
	 */
	if(reportsMismatchDAO.deleteRelColsById(sc) == 0 && initNbRecords > 0)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
	insertRepMisRelatedCols(listRelCols, null);
	// check for audit by filling the auditMap with the parent if the parent
	// doesn't exist
	Iterator<Map.Entry<String, REP_MISMATCH_PARAMCO>> itInterIntra = interIntraMap.entrySet().iterator();
	Entry<String, REP_MISMATCH_PARAMCO> ent;
	REP_MISMATCH_PARAMCO coBuf;
	while(itInterIntra.hasNext())
	{
	    ent = itInterIntra.next();
	    coBuf = ent.getValue();
	    /*
	     * searching for the mismatch_id in the new session hash and getting
	     * the date_updated since it's the same for related reports and
	     * reports
	     */
	    if(coBuf.getRepMismatchParamVO().getREP_MISMATCH_ID().equals(sc.getREP_MISMATCH_ID()))
	    {
		auditMap.put(coBuf.getPARENT_ID(), coBuf);
		break;
	    }
	}
    }

    public void updateRepMisRelatedCrt(ArrayList<REP_MISMATCH_INTRA_CRITERIACO> listRelCrt, REP_MISMATCH_PARAMVO vo,
	    HashMap<String, REP_MISMATCH_PARAMCO> interIntraMap, HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap)
	    throws Exception
    {
	BigDecimal repMismatchId = null;
	if(vo != null)
	{
	    repMismatchId = vo.getREP_MISMATCH_ID();
	}
	ReportsMismatchSC sc = new ReportsMismatchSC();
	if(repMismatchId == null)
	{
	    // in this case the listRelCrt will not be empty in any case
	    sc.setREP_MISMATCH_ID(listRelCrt.get(0).getRepMismatchIntraCriteriaVO().getREP_MISMATCH_ID());
	}
	else
	{
	    sc.setREP_MISMATCH_ID(repMismatchId);
	}
	if(vo == null)
	{
	    Iterator<Map.Entry<String, REP_MISMATCH_PARAMCO>> itInterIntra = interIntraMap.entrySet().iterator();
	    Entry<String, REP_MISMATCH_PARAMCO> ent;
	    REP_MISMATCH_PARAMCO coBuf;
	    while(itInterIntra.hasNext())
	    {
		ent = itInterIntra.next();
		coBuf = ent.getValue();
		/*
		 * searching for the mismatch_id in the new session hash and
		 * getting the date_updated since it's the same for related
		 * reports and reports
		 */
		if(coBuf.getRepMismatchParamVO().getREP_MISMATCH_ID().equals(sc.getREP_MISMATCH_ID()))
		{
		    sc.setParentId(coBuf.getPARENT_ID());
		    sc.setDateUpdated(coBuf.getRepMismatchParamVO().getDATE_UPDATED());
		    break;
		}
	    }
	}
	/*
	 * below works if there's already at least one record in the table. have
	 * to add a query that checks the number of records for a date_updated
	 * before deleting
	 */
	else
	{
	    sc.setParentId(interIntraMap.get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE()).getPARENT_ID());
	    sc.setDateUpdated(interIntraMap.get(vo.getREP_REFERENCE() + "~" + vo.getCRITERIA_CODE())
		    .getRepMismatchParamVO().getDATE_UPDATED());
	}

	int initNbRecords = reportsMismatchDAO.retReportsMismatchRelCriteriaListCount(sc);
	/*
	 * delete returning 0 and there's records for this
	 * mismatch_id=>date_updated has been modified in
	 * rep_mismatch_param=>show alert
	 */
	if(reportsMismatchDAO.deleteRelCrtById(sc) == 0 && initNbRecords > 0)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
	insertRepMisRelatedCriteria(listRelCrt, null);
	// check for audit by filling the auditMap with the parent if the parent
	// doesn't exist
	Iterator<Map.Entry<String, REP_MISMATCH_PARAMCO>> itInterIntra = interIntraMap.entrySet().iterator();
	Entry<String, REP_MISMATCH_PARAMCO> ent;
	REP_MISMATCH_PARAMCO coBuf;
	while(itInterIntra.hasNext())
	{
	    ent = itInterIntra.next();
	    coBuf = ent.getValue();
	    /*
	     * searching for the mismatch_id in the new session hash and getting
	     * the date_updated since it's the same for related reports and
	     * reports
	     */
	    if(coBuf.getRepMismatchParamVO().getREP_MISMATCH_ID().equals(sc.getREP_MISMATCH_ID()))
	    {
		auditMap.put(coBuf.getPARENT_ID(), coBuf);
		break;
	    }
	}
    }

    public void insertRepMismatch(REP_MISMATCH_PARAMCO repMisCO, HashMap<String, Object> repMisParameterScreenHash,
	    BigDecimal misType, AuditRefCO refCO, HashMap<BigDecimal, REP_MISMATCH_PARAMCO> auditMap) throws Exception
    {
	HashMap<String, REP_MISMATCH_PARAMCO> interIntraMap = (HashMap<String, REP_MISMATCH_PARAMCO>) (repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_INTER_INTRA));
	REP_MISMATCH_PARAMCO parentCO;
	BigDecimal misId = null;
	REP_MISMATCH_PARAMVO repMisVO = repMisCO.getRepMismatchParamVO();
	String colType = repMisVO.getCOLUMN_TYPE();
	// get the new rep_mismatch_id from the sequence
	DBSequenceSC dbSeqSC = new DBSequenceSC();
	dbSeqSC.setSequenceName("FTR_MISMATCH_PARAM_SEQ");
	dbSeqSC.setTableName("MISMATCH_PARAM_IDENTITY");
	misId = commonLibBO.returnTableSequence(dbSeqSC);
	repMisVO.setREP_MISMATCH_ID(misId);
	// handling the column type column in the main grid
	if(RepConstantsCommon.NUMBER_TYPE_JASPER.equalsIgnoreCase(colType))
	{
	    repMisVO.setCOLUMN_TYPE(RepConstantsCommon.N);
	}
	if(RepConstantsCommon.VARCHAR_TYPE_JASPER.equalsIgnoreCase(colType))
	{
	    repMisVO.setCOLUMN_TYPE(RepConstantsCommon.VARCHAR_V);
	}

	/*
	 * checking for related reports if the date updated of its parent has
	 * changed before inserting it to the db
	 */
	boolean canInsert = true;
	boolean isMainGridRep = true;

	parentCO = null;
	Iterator<Map.Entry<String, REP_MISMATCH_PARAMCO>> itInterIntra = interIntraMap.entrySet().iterator();
	while(itInterIntra.hasNext())
	{
	    Entry<String, REP_MISMATCH_PARAMCO> ent = itInterIntra.next();
	    if(ent.getValue().getRepMismatchParamVO().getCRITERIA_CODE().equals(
		    repMisCO.getRepMismatchParamVO().getCRITERIA_CODE())
		    && ent.getValue().getRepMismatchParamVO().getREP_MISMATCH_ID()
			    .equals(ent.getValue().getPARENT_ID()))
	    {
		parentCO = ent.getValue();
		break;
	    }
	}

	// checking if it's a related report
	if(parentCO != null)
	{
	    repMisCO.getRepMismatchParamVO().setDATE_UPDATED(parentCO.getRepMismatchParamVO().getDATE_UPDATED());
	    repMisCO.setPARENT_ID(parentCO.getPARENT_ID());
	    ReportsMismatchSC sc = new ReportsMismatchSC();
	    sc.setParentId(repMisCO.getPARENT_ID());
	    sc.setDateUpdated(parentCO.getRepMismatchParamVO().getDATE_UPDATED());
	    canInsert = reportsMismatchDAO.checkIfDateUpdatedModified(sc);
	    isMainGridRep = false;
	}
	if(canInsert)
	{
	    genericDAO.insert(repMisVO);
	    if(isMainGridRep)
	    {
		// start audit
		refCO.setTrxNbr(null);
		refCO.setAuditCO(null);
		refCO.setOperationType(AuditConstant.CREATED);
		auditBO.callAudit(repMisVO, repMisVO, refCO);
		// end audit
	    }
	    // add the parent to the audit map if it does not exist
	    else
	    {
		if(auditMap.get(parentCO.getPARENT_ID()) == null)
		{
		    REP_MISMATCH_PARAMCO pMisCO = new REP_MISMATCH_PARAMCO();
		    pMisCO.getRepMismatchParamVO().setREP_MISMATCH_ID(parentCO.getPARENT_ID());
		    auditMap.put(parentCO.getPARENT_ID(), pMisCO);
		}
	    }
	}
	else
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
	/*
	 * get its related columns and set their mismatch id to the new
	 * 
	 * mismatch id
	 */
	ArrayList<REP_MISMATCH_COLUMNCO> listRelCols = ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) repMisParameterScreenHash
		.get(RepConstantsCommon.MISMATCH_REL_COLS)).get(repMisVO.getREP_REFERENCE() + "~"
		+ repMisVO.getCRITERIA_CODE());
	if(listRelCols != null)
	{
	    insertRepMisRelatedCols(listRelCols, misId);
	    // after inserting the new record with its related columns ,
	    // remove
	    // the related columns from session
	    ((HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_COLS)).remove(repMisVO.getREP_REFERENCE() + "~"
		    + repMisVO.getCRITERIA_CODE());
	}
	// For the intra reports, insert the list of related criteria
	if(misType.equals(BigDecimal.valueOf(0)))
	{
	    ArrayList<REP_MISMATCH_INTRA_CRITERIACO> relatedCrtList = ((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_CRITERIA)).get(repMisVO.getREP_REFERENCE() + "~"
		    + repMisVO.getCRITERIA_CODE());
	    insertRepMisRelatedCriteria(relatedCrtList, misId);
	    // after inserting the new record with its related criteria ,
	    // remove the related criteria from session
	    ((HashMap<String, ArrayList<REP_MISMATCH_INTRA_CRITERIACO>>) repMisParameterScreenHash
		    .get(RepConstantsCommon.MISMATCH_REL_CRITERIA)).remove(repMisVO.getREP_REFERENCE() + "~"
		    + repMisVO.getCRITERIA_CODE());
	}
    }

    public void insertRepMisRelatedCols(ArrayList<REP_MISMATCH_COLUMNCO> listCols, BigDecimal misId) throws Exception
    {
	REP_MISMATCH_COLUMNVO vo;
	REP_MISMATCH_COLUMNCO co;
	String colType;
	for(int i = 0; i < listCols.size(); i++)
	{
	    co=listCols.get(i);
	    vo = co.getRepMismatchColumnVO();
	    colType = vo.getCOLUMN_TYPE();
	    // condition to skip the case of the update
	    if(misId != null)
	    {
		vo.setREP_MISMATCH_ID(misId);
	    }
	    if(RepConstantsCommon.NUMBER_TYPE_JASPER.equalsIgnoreCase(colType))
	    {
		vo.setCOLUMN_TYPE(RepConstantsCommon.N);
	    }
	    if(RepConstantsCommon.VARCHAR_TYPE_JASPER.equalsIgnoreCase(colType))
	    {
		vo.setCOLUMN_TYPE(RepConstantsCommon.VARCHAR_V);
	    }
	    vo.setRELATED_COLUMN(co.getTECH_COL_NAME());
	    genericDAO.insert(vo);
	}
    }

    public void insertRepMisRelatedCriteria(ArrayList<REP_MISMATCH_INTRA_CRITERIACO> listRelCrt, BigDecimal misId)
	    throws Exception
    {
	REP_MISMATCH_INTRA_CRITERIAVO vo;
	for(int i = 0; i < listRelCrt.size(); i++)
	{
	    vo = listRelCrt.get(i).getRepMismatchIntraCriteriaVO();
	    // condition to skip the case of the update
	    if(misId != null)
	    {
		vo.setREP_MISMATCH_ID(misId);
	    }

	    genericDAO.insert(vo);
	}
    }

    public HashMap<String, REP_MISMATCH_PARAMCO> fillHashInterIntraInit(ReportsMismatchSC sc) throws DAOException
    {
	if(1 == ConstantsCommon.CURR_DBMS_SQLSERVER)
	{
	    sc.setIsSQLServer(ConstantsCommon.CURR_DBMS_SQLSERVER);
	}
	return reportsMismatchDAO.fillHashInterIntraInit(sc);
    }

    public HashMap<String, Object> fillColTechNameMap(IRP_AD_HOC_REPORTSC repSC) throws BaseException, IOException
    {
	IRP_AD_HOC_REPORTCO repIdCO = designerBO.retRepIdByProgRef(repSC);
	IRP_AD_HOC_REPORTCO repCO=null;
	if(repIdCO == null)
	{
	    repSC.setPROG_REF(ConstantsCommon.FCR_MAIN_REPORT_REF);
	    BigDecimal jrxmlRepId = BigDecimal.valueOf(designerBO.selectReportByRef(repSC));
	    try
	    {
		repCO = designerBO.returnReportById(jrxmlRepId);
	    }
	    catch(Exception e)
	    {
		log.error(e, "");
	    }
	}
	else
	{
	    repCO = designerBO.findReportById(repIdCO.getREPORT_ID());
	}
	 Calendar cal = Calendar.getInstance();
	
	String filePath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder) + "\\" + RepConstantsCommon.MISMATCH_COL_TECH_NAME + "_"+cal.getTimeInMillis()+".jrxml";
	return ReportUtils.fillColTechNameMap(repCO,filePath);
    }

    
    /*
     * public void printContentHash(HashMap<String, Object>
     * repMisParameterScreenHash) {
     * 
     * try { StringBuffer sb = new StringBuffer(); sb.append(
     * "#########################################################################################################################\n\n\n\n"
     * );sb.append(
     * "**********************************RELATED REPORTS**********************************\n\n\n"
     * ); HashMap<String, HashMap<String, ArrayList<REP_MISMATCH_PARAMCO>>>
     * hashRelRep = (HashMap<String, HashMap<String,
     * ArrayList<REP_MISMATCH_PARAMCO>>>) (repMisParameterScreenHash
     * .get(RepConstantsCommon.MISMATCH_REL_REPORTS)); Set keySet =
     * hashRelRep.keySet(); Iterator it = keySet.iterator(); while(it.hasNext())
     * { String key = (String) it.next(); HashMap<String,
     * ArrayList<REP_MISMATCH_PARAMCO>> loc = (HashMap<String,
     * ArrayList<REP_MISMATCH_PARAMCO>>) hashRelRep .get(key); Set
     * keySetOtherSet = loc.keySet(); Iterator itt = keySetOtherSet.iterator();
     * while(itt.hasNext()) { String otherKey = (String) itt.next();
     * ArrayList<REP_MISMATCH_PARAMCO> list = (ArrayList<REP_MISMATCH_PARAMCO>)
     * loc.get(otherKey); for(int i = 0; i < list.size(); i++) {
     * sb.append("key of the hash: " + key + " Internal key: " + otherKey +
     * "   REP REFERENCE: " +
     * (list.get(i)).getRepMismatchParamVO().getREP_REFERENCE() +
     * "    CRITERIA CODE   " +
     * (list.get(i)).getRepMismatchParamVO().getCRITERIA_CODE() + "\n"); } }
     * sb.append("\n\n\n\n\n\n\n\n\n\n\n\n\n\n"); }
     * 
     * sb.append(
     * "**********************************RELATED COLUMNS**********************************\n\n\n"
     * ); HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>> hashRelCols =
     * (HashMap<String, ArrayList<REP_MISMATCH_COLUMNCO>>)
     * (repMisParameterScreenHash .get(RepConstantsCommon.MISMATCH_REL_COLS));
     * Set relSet = hashRelCols.keySet(); Iterator id = relSet.iterator();
     * while(id.hasNext()) { String key = (String) id.next();
     * ArrayList<REP_MISMATCH_COLUMNCO> ll = (ArrayList<REP_MISMATCH_COLUMNCO>)
     * hashRelCols.get(key); if(ll != null) { if(ll.size() == 0) {
     * sb.append("the key : " + key + "      RELATED COLUMN      \n"); } else {
     * for(int i = 0; i < ll.size(); i++) { sb.append("the key: " + key +
     * " RELATED COLUMN: " +
     * (ll.get(i)).getRepMismatchColumnVO().getRELATED_COLUMN() +
     * "  MISMATCH ID: " +
     * (ll.get(i)).getRepMismatchColumnVO().getREP_MISMATCH_ID() + "\n"); } } }
     * } sb.append(
     * "#########################################################################################################################\n\n\n\n"
     * ); File f = new
     * File("C:\\Users\\bassambechara\\Desktop\\scenarios_mismatch.txt");
     * FileOutputStream ff = new FileOutputStream(f, true);
     * ff.write(String.valueOf(sb.toString()).getBytes()); ff.close(); }
     * catch(Exception e) { e.printStackTrace(); } }
     */
}
