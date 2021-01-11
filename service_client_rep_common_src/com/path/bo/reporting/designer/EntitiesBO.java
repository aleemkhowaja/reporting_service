package com.path.bo.reporting.designer;

import java.util.List;

import com.path.dbmaps.vo.IRP_ENTITIESVO;
import com.path.dbmaps.vo.IRP_FIELDSVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.designer.IRP_COLUMNSCO;
import com.path.vo.reporting.designer.IRP_ENTITIESSC;
import com.path.vo.reporting.designer.IRP_TABLE_VIEWCO;

public interface EntitiesBO 
{
    public List<IRP_ENTITIESVO> loadEntitiesList(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public int retEntitiesListCount(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public List<IRP_FIELDSCO> loadFieldsList(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public int retFieldsListCount(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public List<IRP_TABLE_VIEWCO> loadTableViewList(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public int retTableViewListCount(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public List<IRP_COLUMNSCO> loadFieldLkpList(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public int retFieldLkpListCount(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public List<IRP_FIELDSVO> loadAllFieldsList(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public int retAllFieldsListCount(IRP_ENTITIESSC entitiesSC) throws BaseException;
    
    public void saveEntFields(IRP_ENTITIESSC entitiesSC,AuditRefCO refCO) throws BaseException;
    
    public void deleteEntity(IRP_ENTITIESSC entSC,AuditRefCO refCO) throws BaseException;
    
    public IRP_ENTITIESVO retrieveEntity(IRP_ENTITIESVO entVO)throws BaseException;
}
