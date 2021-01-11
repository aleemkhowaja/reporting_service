package com.path.vo.reporting.mailServer;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_MAIL_SERVER_CONFIGSC extends GridParamsSC
{
	private BigDecimal msCode;

	public BigDecimal getMsCode() {
		return msCode;
	}



	public void setMsCode(BigDecimal msCode) {
		this.msCode = msCode;
	}



	public IRP_MAIL_SERVER_CONFIGSC()
    {
		super.setSearchCols(new String[] {"HOST","PORT","SERVER_USER_NAME","MAIL_FROM"});
    }
}
