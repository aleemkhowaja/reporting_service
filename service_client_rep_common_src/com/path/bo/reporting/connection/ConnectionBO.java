package com.path.bo.reporting.connection;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import com.path.dbmaps.vo.IRP_APP_CONNECTIONVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.IRP_CONNECTIONSCO;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONCO;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONSC;

public interface ConnectionBO
{
	public List<IRP_CONNECTIONSVO> retConnectionList(IRP_CONNECTIONSSC irpConSC)throws BaseException;
	public int retConnectionListCount(IRP_CONNECTIONSSC irpConSC)throws BaseException;
	
	public IRP_CONNECTIONSVO retrieveConnData(IRP_CONNECTIONSVO icVO) throws BaseException;
	public void insertConDetail(IRP_CONNECTIONSCO icCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
	public void updateConDetail(IRP_CONNECTIONSCO icCO,IRP_CONNECTIONSVO oldConnVO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
	public void deleteConDetail(IRP_CONNECTIONSVO icVO) throws BaseException;
	public void conTest(IRP_CONNECTIONSCO icCO,String pageRef,BigDecimal compCode,String appName,String lang)throws BaseException, SQLException, ClassNotFoundException;
	public int checkIfHaveAppCon(IRP_CONNECTIONSVO icVO) throws BaseException;
	
	public List<IRP_APP_CONNECTIONCO> retAppConnectionList(IRP_APP_CONNECTIONSC irpAppConSC)throws BaseException;
	public int retAppConnectionListCount(IRP_APP_CONNECTIONSC irpAppConSC)throws BaseException;
	
	public List<IRP_APP_CONNECTIONCO> retAppNameList(IRP_APP_CONNECTIONSC irpAppConSC)throws BaseException;
	public int retAppNameListCount(IRP_APP_CONNECTIONSC irpAppConSC)throws BaseException;
	
	public IRP_APP_CONNECTIONCO retrieveAppConnData(IRP_APP_CONNECTIONCO icAppCO) throws BaseException;
	public void insertAppCon(IRP_APP_CONNECTIONCO icAppCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
	public void updateAppConDetail(IRP_APP_CONNECTIONCO icAppCO,IRP_APP_CONNECTIONVO oldAppConnVO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
	public void deleteAppConnection(IRP_APP_CONNECTIONVO icAppVO) throws BaseException;
	public IRP_APP_CONNECTIONCO applyAppConDependency(IRP_APP_CONNECTIONVO icAppVO) throws BaseException;
	public IRP_APP_CONNECTIONCO applyAppNameDependency(IRP_APP_CONNECTIONVO icAppVO) throws BaseException;
	
}
