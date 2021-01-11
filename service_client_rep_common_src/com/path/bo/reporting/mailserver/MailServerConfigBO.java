package com.path.bo.reporting.mailserver;

import java.util.List;

import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGSC;

public interface MailServerConfigBO 
{
	public int retMailServerCount(IRP_MAIL_SERVER_CONFIGSC sc) throws BaseException;
	public List<IRP_MAIL_SERVER_CONFIGCO> retMailServerList(IRP_MAIL_SERVER_CONFIGSC sc) throws BaseException;
	public IRP_MAIL_SERVER_CONFIGCO retMailServerById(IRP_MAIL_SERVER_CONFIGSC sc)throws BaseException;
	public void saveMailServer(IRP_MAIL_SERVER_CONFIGCO mailServerCO)throws BaseException;
	public void deleteMailServer(IRP_MAIL_SERVER_CONFIGCO mailServerCO)throws BaseException;
	public int retMailServerReportCount(IRP_MAIL_SERVER_CONFIGSC sc) throws BaseException;
}
