package com.path.dao.reporting.connection;

import java.util.List;

import com.path.dbmaps.vo.IRP_APP_CONNECTIONVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONCO;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONSC;

public interface ConnectionDAO
{
	public List<IRP_CONNECTIONSVO> retConnectionList(IRP_CONNECTIONSSC irpConSC)throws DAOException;
	public int retConnectionListCount(IRP_CONNECTIONSSC irpConSC)throws DAOException;
	public Integer updateConDetail(IRP_CONNECTIONSVO icVO) throws BaseException;
	public int checkIfHaveAppCon (IRP_CONNECTIONSSC sc)throws DAOException;
	
	public List<IRP_APP_CONNECTIONCO> retAppConnectionList(IRP_APP_CONNECTIONSC irpAppConSC)throws DAOException;
	public int retAppConnectionListCount(IRP_APP_CONNECTIONSC irpAppConSC)throws DAOException;
	
	public List<IRP_APP_CONNECTIONCO> retAppNameList(IRP_APP_CONNECTIONSC irpAppConSC)throws DAOException;
	public int retAppNameListCount(IRP_APP_CONNECTIONSC irpAppConSC)throws DAOException;
	
	public IRP_APP_CONNECTIONCO retrieveAppConnData(IRP_APP_CONNECTIONCO icAppCO) throws DAOException;
	public Integer updateAppConDetail(IRP_APP_CONNECTIONVO icAppVO) throws BaseException;
	public IRP_APP_CONNECTIONCO applyAppConDependency(IRP_APP_CONNECTIONVO icAppVO) throws DAOException;
	public IRP_APP_CONNECTIONCO applyAppNameDependency(IRP_APP_CONNECTIONVO icAppVO) throws DAOException;
	
}
