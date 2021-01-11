package com.path.bo.reporting.designer.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.designer.EntitiesBO;
import com.path.dao.reporting.designer.EntitiesDAO;
import com.path.dbmaps.vo.IRP_ENTITIESVO;
import com.path.dbmaps.vo.IRP_FIELDSVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.designer.IRP_COLUMNSCO;
import com.path.vo.reporting.designer.IRP_ENTITIESSC;
import com.path.vo.reporting.designer.IRP_TABLE_VIEWCO;

public class EntitiesBOImpl extends BaseBO implements EntitiesBO 
{

    private EntitiesDAO entitiesDAO;
    
    public EntitiesDAO getEntitiesDAO()
    {
        return entitiesDAO;
    }

    public void setEntitiesDAO(EntitiesDAO entitiesDAO)
    {
        this.entitiesDAO = entitiesDAO;
    }

    @Override
    public List<IRP_ENTITIESVO> loadEntitiesList(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.loadEntitiesList(entitiesSC);
    }

    @Override
    public int retEntitiesListCount(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.retEntitiesListCount(entitiesSC);
    }
    
    @Override
    public List<IRP_FIELDSCO> loadFieldsList(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.loadFieldsList(entitiesSC);
    }

    @Override
    public int retFieldsListCount(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.retFieldsListCount(entitiesSC);
    }

    @Override
    public List<IRP_TABLE_VIEWCO> loadTableViewList(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.loadTableViewList(entitiesSC);
    }

    @Override
    public int retTableViewListCount(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.retTableViewListCount(entitiesSC);
    }

    @Override
    public List<IRP_COLUMNSCO> loadFieldLkpList(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.loadFieldLkpList(entitiesSC);
    }

    @Override
    public int retFieldLkpListCount(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.retFieldLkpListCount(entitiesSC);
    }

    @Override
    public List<IRP_FIELDSVO> loadAllFieldsList(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.loadAllFieldsList(entitiesSC);
    }

    @Override
    public int retAllFieldsListCount(IRP_ENTITIESSC entitiesSC) throws BaseException
    {
	return entitiesDAO.retAllFieldsListCount(entitiesSC);
    }

    @Override
    public void saveEntFields(IRP_ENTITIESSC entitiesSC,AuditRefCO refCO) throws BaseException
    {
	IRP_ENTITIESVO entVO = new IRP_ENTITIESVO();
	entVO.setENTITY_DB_NAME(entitiesSC.getENTITY_DB_NAME());
	entVO.setENTITY_ALIAS(entitiesSC.getENTITY_ALIAS());
	//the entVO is not filled with isSybase ,so will are doing it manually
	if(ConstantsCommon.CURR_DBMS_SYBASE ==1)
	{
		entVO.setIsSybase(1);
	}
	
	
	if (entitiesSC.isNewEntity())
	{
	    genericDAO.insert(entVO);
	    //apply audit
	    refCO.setOperationType(AuditConstant.CREATED);
	    auditBO.callAudit( null,entVO, refCO);
	}
	else
	{
	    IRP_ENTITIESVO oldVO=(IRP_ENTITIESVO) refCO.getAuditObj();
	    entVO.setDATE_UPDATED(oldVO.getDATE_UPDATED());
	    Integer row= entitiesDAO.updateEntity(entVO);
	    if (row == null || row < 1)
		{
		     throw new BOException(MessageCodes.RECORD_CHANGED);
		}
	    //apply audit
	    refCO.setOperationType(AuditConstant.UPDATE);
	    auditBO.callAudit( oldVO,entVO, refCO);
	}
	IRP_FIELDSCO fCO ;
	List<String> delFieldsList = new ArrayList<String>();
	HashMap addedFieldsList = entitiesSC.getAddedFields();
	HashMap updatedFieldsList = entitiesSC.getUpdatedFields();
	HashMap deletedFieldsList = entitiesSC.getDeletedFields();
	HashMap<String,IRP_FIELDSCO> oldFieldsMap=entitiesSC.getOldFieldsMap();
	Iterator it;
	if(deletedFieldsList != null && !deletedFieldsList.isEmpty())
	{
		it=deletedFieldsList.values().iterator();
		while(it.hasNext())
		{
			 fCO=(IRP_FIELDSCO)it.next();
			 delFieldsList.add(fCO.getFIELD_DB_NAME());
		}
		entitiesSC.setDelFieldsList(delFieldsList);
		entitiesDAO.deleteFields(entitiesSC);
		//apply audit
		 refCO.setOperationType(AuditConstant.UPDATE);
		 auditBO.callAudit( entVO,entVO, refCO);
	}
	
	if(addedFieldsList != null && !addedFieldsList.isEmpty())
	{
		it=addedFieldsList.values().iterator();
		while(it.hasNext())
		{
		    fCO = (IRP_FIELDSCO)it.next();
		    entitiesDAO.addFields(fCO);
		}
		//apply audit
		 refCO.setOperationType(AuditConstant.UPDATE);
		 auditBO.callAudit( entVO,entVO, refCO);
	}
	if(updatedFieldsList != null && !updatedFieldsList.isEmpty())
	{
		it=updatedFieldsList.values().iterator();
		while(it.hasNext())
		{
			fCO=(IRP_FIELDSCO)it.next();
			entitiesDAO.updateFields(fCO);
			
			//apply audit
			 refCO.setOperationType(AuditConstant.UPDATE);
			 
			 auditBO.callAudit(convertFeCOToVO(oldFieldsMap.get(fCO.getFIELD_DB_NAME())),convertFeCOToVO(fCO), refCO);
		}
	}


	if (!entitiesSC.isNewEntity()){
		auditBO.insertAudit(refCO);
	}
	
	
    }
    
    public IRP_FIELDSVO convertFeCOToVO(IRP_FIELDSCO feCO)
    {
    	IRP_FIELDSVO feVO=new IRP_FIELDSVO();
    	feVO.setENTITY_DB_NAME(feCO.getENTITY_DB_NAME());
    	feVO.setFIELD_ALIAS(feCO.getFIELD_ALIAS());
    	feVO.setFIELD_DB_NAME(feVO.getFIELD_DB_NAME());
    	return feVO;
    }

	public void deleteEntity(IRP_ENTITIESSC entSC,AuditRefCO refCO) throws BaseException 
	{
		entitiesDAO.deleteFields(entSC);
		entitiesDAO.deleteEntity(entSC);
		
		//apply audit
		auditBO.callAudit(entSC, entSC, refCO);
		auditBO.insertAudit(refCO);
	}

	public IRP_ENTITIESVO retrieveEntity(IRP_ENTITIESVO entVO)throws BaseException 
	{
		return (IRP_ENTITIESVO)genericDAO.selectByPK(entVO);
	}

}
