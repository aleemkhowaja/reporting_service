package com.path.bo.reporting.mailserver;

import java.math.BigDecimal;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.dao.reporting.mailserver.MailServerConfigDAO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.SecurityUtils;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGSC;

public class MailServerConfigBOImpl extends BaseBO implements MailServerConfigBO
{
	private MailServerConfigDAO mailServerConfigDAO;
	private CommonRepFuncBO commonRepFuncBO;
	
	public CommonRepFuncBO getCommonRepFuncBO() {
		return commonRepFuncBO;
	}

	public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO) {
		this.commonRepFuncBO = commonRepFuncBO;
	}

	public MailServerConfigDAO getMailServerConfigDAO() {
		return mailServerConfigDAO;
	}

	public void setMailServerConfigDAO(MailServerConfigDAO mailServerConfigDAO) {
		this.mailServerConfigDAO = mailServerConfigDAO;
	}

	public int retMailServerCount(IRP_MAIL_SERVER_CONFIGSC sc) throws BaseException
	{
		return mailServerConfigDAO.retMailServerCount(sc);
	}

	public List<IRP_MAIL_SERVER_CONFIGCO> retMailServerList(IRP_MAIL_SERVER_CONFIGSC sc)throws BaseException 
	{
		return mailServerConfigDAO.retMailServerList(sc);
	}

	public IRP_MAIL_SERVER_CONFIGCO retMailServerById(IRP_MAIL_SERVER_CONFIGSC sc) throws BaseException 
	{
		
		IRP_MAIL_SERVER_CONFIGCO mailServerCO = mailServerConfigDAO.retMailServerById(sc);
		
		mailServerCO.getMailServerVO().setSERVER_PASS(SecurityUtils.decodeB64(mailServerCO.getMailServerVO().getSERVER_PASS()));
		mailServerCO.setSERVER_CONF_PASS("");
		mailServerCO.setSERVER_OLD_PASS(SecurityUtils.decodeB64(mailServerCO.getSERVER_OLD_PASS()));
		return mailServerCO;
	}

	public void saveMailServer(IRP_MAIL_SERVER_CONFIGCO mailServerCO)throws BaseException 
	{
		
		CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
		checkRequiredCO.setObj_value(mailServerCO);
		checkRequiredCO.setOpt("MSC00MT");
		checkRequiredCO.setApp("REP");
		checkRequiredCO.setCompCode(BigDecimal.ZERO);

		//check for required fields
		commonLibBO.checkRequired(checkRequiredCO); 
		
		//encrypt the password in add mode or in update mode where the password is changed 
		mailServerCO.getMailServerVO().setSERVER_PASS(SecurityUtils.encodeB64(mailServerCO.getMailServerVO().getSERVER_PASS()));
		
		AuditRefCO refCO=mailServerCO.getAuditRefCO();
		//update
		if((ConstantsCommon.EMPTY_BIGDECIMAL_VALUE).equals(mailServerCO.getMailServerVO().getMAIL_SERVER_CODE()))
		{
			//set mailServerId
			BigDecimal msId=commonRepFuncBO.retCounterValue("IRP_MAIL_SERVER_CONFIG");
			mailServerCO.getMailServerVO().setMAIL_SERVER_CODE(msId);
			genericDAO.insert(mailServerCO.getMailServerVO());
			//apply audit
			refCO.setOperationType(AuditConstant.CREATED);
			auditBO.callAudit(null, mailServerCO.getMailServerVO(), refCO);
		}
		//add
		else
		{
			
			Integer row=genericDAO.update(mailServerCO.getMailServerVO());
			if (row == null || row < 1)
			 {
			     throw new BOException(MessageCodes.RECORD_CHANGED);
			 }
			
			//apply audit
			 refCO.setOperationType(AuditConstant.UPDATE);
			 auditBO.callAudit( mailServerCO.getAuditObj(), mailServerCO.getMailServerVO() , refCO);
			 auditBO.insertAudit(refCO);
		}
		
	}

	public void deleteMailServer(IRP_MAIL_SERVER_CONFIGCO mailServerCO)throws BaseException 
	{
		genericDAO.delete(mailServerCO.getMailServerVO());
		//apply audit
		auditBO.callAudit( mailServerCO.getMailServerVO(), mailServerCO.getMailServerVO(), mailServerCO.getAuditRefCO());
		auditBO.insertAudit(mailServerCO.getAuditRefCO());
	}
	

	public int retMailServerReportCount(IRP_MAIL_SERVER_CONFIGSC sc) throws BaseException
	{
		return mailServerConfigDAO.retMailServerReportCount(sc);
	}
	
}
