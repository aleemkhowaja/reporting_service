package com.path.vo.reporting.mailServer;

import com.path.dbmaps.vo.IRP_MAIL_SERVER_CONFIGVO;
import com.path.lib.vo.BaseVO;

public class IRP_MAIL_SERVER_CONFIGCO extends BaseVO
{
	private IRP_MAIL_SERVER_CONFIGVO mailServerVO=new IRP_MAIL_SERVER_CONFIGVO();
	private String SERVER_OLD_PASS;
	private String SERVER_CONF_PASS;
	private String SUBJECT;
	private String TO;
	private String BODY;
	private String FROM;
	
	public String getFROM() {
		return FROM;
	}

	public void setFROM(String fROM) {
		FROM = fROM;
	}

	public String getSUBJECT() {
		return SUBJECT;
	}

	public void setSUBJECT(String sUBJECT) {
		SUBJECT = sUBJECT;
	}

	public String getTO() {
		return TO;
	}

	public void setTO(String tO) {
		TO = tO;
	}

	public String getBODY() {
		return BODY;
	}

	public void setBODY(String bODY) {
		BODY = bODY;
	}

	public String getSERVER_CONF_PASS() {
		return SERVER_CONF_PASS;
	}

	public void setSERVER_CONF_PASS(String sERVERCONFPASS) {
		SERVER_CONF_PASS = sERVERCONFPASS;
	}

	public String getSERVER_OLD_PASS() {
		return SERVER_OLD_PASS;
	}

	public void setSERVER_OLD_PASS(String sERVEROLDPASS) {
		SERVER_OLD_PASS = sERVEROLDPASS;
	}

	public IRP_MAIL_SERVER_CONFIGVO getMailServerVO() {
		return mailServerVO;
	}

	public void setMailServerVO(IRP_MAIL_SERVER_CONFIGVO mailServerVO) {
		this.mailServerVO = mailServerVO;
	}
	
}
