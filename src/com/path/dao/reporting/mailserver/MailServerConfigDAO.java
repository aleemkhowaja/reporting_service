package com.path.dao.reporting.mailserver;

import java.util.List;

import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGSC;

public interface MailServerConfigDAO 
{
	public int retMailServerCount(IRP_MAIL_SERVER_CONFIGSC sc) throws DAOException;
	public List<IRP_MAIL_SERVER_CONFIGCO> retMailServerList(IRP_MAIL_SERVER_CONFIGSC sc) throws DAOException;
	public IRP_MAIL_SERVER_CONFIGCO retMailServerById(IRP_MAIL_SERVER_CONFIGSC sc) throws DAOException;
	public int retMailServerReportCount(IRP_MAIL_SERVER_CONFIGSC sc)throws DAOException;
}
