package com.path.dao.reporting.connection.impl;

import java.util.List;

import com.path.dao.reporting.connection.ConnectionDAO;
import com.path.dbmaps.vo.IRP_APP_CONNECTIONVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONCO;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONSC;

public class ConnectionDAOImpl extends BaseDAO implements ConnectionDAO
{
    
	@SuppressWarnings("unchecked")
	public List<IRP_CONNECTIONSVO> retConnectionList(IRP_CONNECTIONSSC irpConSC)throws DAOException 
	{
		
		DAOHelper.fixGridMaps(irpConSC, getSqlMap(), "connection.retConnectionListMap");
		return  getSqlMap().queryForList("connection.retConnectionList", irpConSC,irpConSC.getRecToskip(),irpConSC.getNbRec());

	}

	public int retConnectionListCount(IRP_CONNECTIONSSC irpConSC)throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("connection.retConnectionListCount", irpConSC)).intValue();
		return nb;
	}
	
	public Integer updateConDetail(IRP_CONNECTIONSVO icVO) throws DAOException
	{
	    return getSqlMap().update("connection.updateConDetail", icVO);
	}
	
	public int checkIfHaveAppCon (IRP_CONNECTIONSSC sc)throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("connection.checkIfHaveAppConCount", sc)).intValue();
		return nb;
	}
	
	
	public List<IRP_APP_CONNECTIONCO> retAppConnectionList(IRP_APP_CONNECTIONSC irpAppConSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(irpAppConSC, getSqlMap(), "connection.retAppConnectionListMap");
		return  getSqlMap().queryForList("connection.retAppConnectionList", irpAppConSC,irpAppConSC.getRecToskip(),irpAppConSC.getNbRec());
	}

	public int retAppConnectionListCount(IRP_APP_CONNECTIONSC irpAppConSC)throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("connection.retAppConnectionListCount", irpAppConSC)).intValue();
		return nb;
	}
	
	
	public List<IRP_APP_CONNECTIONCO> retAppNameList(IRP_APP_CONNECTIONSC irpAppConSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(irpAppConSC, getSqlMap(), "connection.retAppNameListMap");
		return  getSqlMap().queryForList("connection.retAppNameList", irpAppConSC,irpAppConSC.getRecToskip(),irpAppConSC.getNbRec());
	}

	public int retAppNameListCount(IRP_APP_CONNECTIONSC irpAppConSC)throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("connection.retAppNameListCount", irpAppConSC)).intValue();
		return nb;
	}
	
	public IRP_APP_CONNECTIONCO retrieveAppConnData(IRP_APP_CONNECTIONCO icAppCO) throws DAOException 
	{
	    return (IRP_APP_CONNECTIONCO) (getSqlMap().queryForObject("connection.retrieveAppConnData", icAppCO));
		
	}
	
	public Integer updateAppConDetail(IRP_APP_CONNECTIONVO icAppVO) throws DAOException
	{
	    return getSqlMap().update("connection.updateAppConDetail", icAppVO);
	}
	
	public IRP_APP_CONNECTIONCO applyAppConDependency(IRP_APP_CONNECTIONVO icAppVO) throws DAOException 
	{
	    return (IRP_APP_CONNECTIONCO) (getSqlMap().queryForObject("connection.applyAppConDependency", icAppVO));
	}
	
	public IRP_APP_CONNECTIONCO applyAppNameDependency(IRP_APP_CONNECTIONVO icAppVO) throws DAOException 
	{
	    return (IRP_APP_CONNECTIONCO) (getSqlMap().queryForObject("connection.applyAppNameDependency", icAppVO));
	}
}
