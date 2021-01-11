package com.path.dao.reporting.designer.impl;

import java.util.ArrayList;
import java.util.List;

import com.path.dao.reporting.designer.EntitiesDAO;
import com.path.dbmaps.vo.IRP_ENTITIESVO;
import com.path.dbmaps.vo.IRP_FIELDSVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.designer.IRP_COLUMNSCO;
import com.path.vo.reporting.designer.IRP_ENTITIESSC;
import com.path.vo.reporting.designer.IRP_TABLE_VIEWCO;

public class EntitiesDAOImpl extends BaseDAO implements EntitiesDAO
{

    @Override
    public List<IRP_ENTITIESVO> loadEntitiesList(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	DAOHelper.fixGridMaps(entitiesSC, getSqlMap(), "entities.loadEntitiesListMap");
	if(entitiesSC.getSortOrder() == null)
	{
	    entitiesSC.setSortOrder(" ORDER BY ENTITY_DB_NAME ");
	}
	return (ArrayList<IRP_ENTITIESVO>) getSqlMap().queryForList("entities.loadEntitiesList", entitiesSC,
		entitiesSC.getRecToskip(), entitiesSC.getNbRec());

    }

    @Override
    public int retEntitiesListCount(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	return ((Integer) (getSqlMap().queryForObject("entities.retEntitiesListCount", entitiesSC))).intValue();
    }

    @Override
    public List<IRP_FIELDSCO> loadFieldsList(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	DAOHelper.fixGridMaps(entitiesSC, getSqlMap(), "entities.loadFieldsListMap");
	if(entitiesSC.getSortOrder() == null)
	{
	    entitiesSC.setSortOrder(" ORDER BY FIELD_DB_NAME ");
	}
	return getSqlMap().queryForList("entities.loadFieldsList", entitiesSC);
	
    }

    @Override
    public int retFieldsListCount(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("entities.retFieldsListCount", entitiesSC)).intValue();
    }

    @Override
    public List<IRP_TABLE_VIEWCO> loadTableViewList(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	DAOHelper.fixGridMaps(entitiesSC, getSqlMap(), "entities.loadTableViewListMap");
	if(entitiesSC.getSortOrder() == null)
	{
	    entitiesSC.setSortOrder(" ORDER BY OBJECT_DB_NAME ");
	}
	
	return (ArrayList<IRP_TABLE_VIEWCO>) getSqlMap().queryForList("entities.loadTableViewList", entitiesSC,
		entitiesSC.getRecToskip(), entitiesSC.getNbRec());
	
	
    }

    @Override
    public int retTableViewListCount(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("entities.retTableViewListCount", entitiesSC)).intValue();
    }

    @Override
    public List<IRP_COLUMNSCO> loadFieldLkpList(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	DAOHelper.fixGridMaps(entitiesSC, getSqlMap(), "entities.loadFieldLkpListMap");
	if(entitiesSC.getSortOrder() == null)
	{
	    entitiesSC.setSortOrder(" ORDER BY COLUMN_NAME ");
	}
	List<IRP_FIELDSCO> addFieldsList = new ArrayList<IRP_FIELDSCO>();
	for (Object o:entitiesSC.getAddedFields().values()){
	    IRP_FIELDSCO fCO = (IRP_FIELDSCO)o;
	    addFieldsList.add(fCO);
	}	
	
	entitiesSC.setAddFieldsList(addFieldsList);

	return getSqlMap().queryForList("entities.loadFieldLkpList", entitiesSC);
	
    }

    @Override
    public int retFieldLkpListCount(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("entities.retFieldLkpListCount", entitiesSC)).intValue();
    }

    @Override
    public List<IRP_FIELDSVO> loadAllFieldsList(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
    	if(entitiesSC.isGrid())
    	{
    		DAOHelper.fixGridMaps(entitiesSC, getSqlMap(), "entities.loadAllFieldsListMap");
    		if(entitiesSC.getSortOrder() == null)
    		{
    		    entitiesSC.setSortOrder(" ORDER BY FIELD_DB_NAME ");
    		}
    		return (ArrayList<IRP_FIELDSVO>) getSqlMap().queryForList("entities.loadAllFieldsList", entitiesSC,
    			entitiesSC.getRecToskip(), entitiesSC.getNbRec());
    	}
    	else
    	{
    		return (ArrayList<IRP_FIELDSVO>) getSqlMap().queryForList("entities.loadAllFieldsList", entitiesSC);
    	}
    }

    @Override
    public int retAllFieldsListCount(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("entities.retAllFieldsListCount", entitiesSC)).intValue();
    
    }


    @Override
    public Integer updateEntity(IRP_ENTITIESVO iRPENTITIESVO) throws DAOException
    {
	return getSqlMap().update("entities.updateEntity", iRPENTITIESVO);
    }
    
    @Override
    public void deleteFields(IRP_ENTITIESSC entitiesSC) throws DAOException
    {
    	getSqlMap().delete("entities.deleteFields", entitiesSC);
    }
    
    @Override
    public void addFields(IRP_FIELDSCO feCO) throws DAOException
    {
    	getSqlMap().insert("entities.insertFields", feCO);
    }

    @Override
    public void updateFields(IRP_FIELDSCO feCO) throws DAOException
    {
    	getSqlMap().update("entities.updateField", feCO);
    }

	public void deleteEntity(IRP_ENTITIESSC entSC) throws DAOException 
	{
		getSqlMap().delete("entities.deleteEntity", entSC);
	}
    
    

}
