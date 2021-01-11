package com.path.dao.reporting.designer;

import java.util.List;

import com.path.dbmaps.vo.IRP_ENTITIESVO;
import com.path.dbmaps.vo.IRP_FIELDSVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.designer.IRP_COLUMNSCO;
import com.path.vo.reporting.designer.IRP_ENTITIESSC;
import com.path.vo.reporting.designer.IRP_TABLE_VIEWCO;

public interface EntitiesDAO
{
    public List<IRP_ENTITIESVO> loadEntitiesList(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public int retEntitiesListCount(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public List<IRP_FIELDSCO> loadFieldsList(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public int retFieldsListCount(IRP_ENTITIESSC entitiesSC) throws DAOException;
 
    public List<IRP_TABLE_VIEWCO> loadTableViewList(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public int retTableViewListCount(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public List<IRP_COLUMNSCO> loadFieldLkpList(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public int retFieldLkpListCount(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public List<IRP_FIELDSVO> loadAllFieldsList(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public int retAllFieldsListCount(IRP_ENTITIESSC entitiesSC) throws DAOException;

    public Integer updateEntity(IRP_ENTITIESVO iRPENTITIESVO) throws DAOException;
    
    public void deleteFields(IRP_ENTITIESSC entitiesSC) throws DAOException;
    
    public void addFields(IRP_FIELDSCO feCO) throws DAOException;
    
    public void updateFields(IRP_FIELDSCO feCO) throws DAOException;
    
    public void deleteEntity(IRP_ENTITIESSC entSC) throws DAOException;
}
