package com.path.vo.reporting.ftr.fcr;

import java.math.BigDecimal;

import com.path.lib.vo.BaseVO;

public class IRP_FCRSC extends BaseVO {
	private BigDecimal compCode;
	private String userName;
	private String progRef;
	private String valueOrTrade;
	
	public BigDecimal getCompCode() {
		return compCode;
	}
	public void setCompCode(BigDecimal compCode) {
		this.compCode = compCode;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProgRef() {
		return progRef;
	}
	public void setProgRef(String progRef) {
		this.progRef = progRef;
	}
	public String getValueOrTrade() {
		return valueOrTrade;
	}
	public void setValueOrTrade(String valueOrTrade) {
		this.valueOrTrade = valueOrTrade;
	}

	
}
