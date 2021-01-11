package com.path.dao.reporting.mailserver.impl;

import java.util.List;

import com.path.dao.reporting.mailserver.MailServerConfigDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGSC;

public class MailServerConfigDAOImpl extends BaseDAO  implements MailServerConfigDAO
{

	public int retMailServerCount(IRP_MAIL_SERVER_CONFIGSC sc) throws DAOException 
	{
		DAOHelper.fixGridMaps(sc, getSqlMap(), "mailServerConfig.retMailServerListMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("mailServerConfig.retMailServerCount", sc)).intValue();
		return nb;
	}

	public List<IRP_MAIL_SERVER_CONFIGCO> retMailServerList(IRP_MAIL_SERVER_CONFIGSC sc)throws DAOException 
	{
		DAOHelper.fixGridMaps(sc, getSqlMap(), "mailServerConfig.retMailServerListMap");
		if(sc.getSortOrder()==null) //to be compatible with sybase since it does not work with a nested order by
		{
			sc.setSortOrder("ORDER BY MAIL_SERVER_CODE ");
		}
		return getSqlMap().queryForList("mailServerConfig.retMailServerList", sc,sc.getRecToskip(), sc.getNbRec());
	}

	public IRP_MAIL_SERVER_CONFIGCO retMailServerById(IRP_MAIL_SERVER_CONFIGSC sc) throws DAOException 
	{
		return (IRP_MAIL_SERVER_CONFIGCO)getSqlMap().queryForObject("mailServerConfig.retMailServerById", sc);
	}
	
	public int retMailServerReportCount(IRP_MAIL_SERVER_CONFIGSC sc)throws DAOException{
		return ((Integer) getSqlMap().queryForObject("mailServerConfig.retMailServerReportCount", sc)).intValue();
	}
	
}
