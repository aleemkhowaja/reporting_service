package com.path.vo.common;

import java.math.BigDecimal;

import com.path.lib.vo.BaseVO;

public class CURRENCIESCO extends BaseVO {
	private BigDecimal BASE_CURRENCY;
	private BigDecimal DECIMAL_POINTS;
	public BigDecimal getBASE_CURRENCY() {
		return BASE_CURRENCY;
	}
	public void setBASE_CURRENCY(BigDecimal bASECURRENCY) {
		BASE_CURRENCY = bASECURRENCY;
	}
	public BigDecimal getDECIMAL_POINTS() {
		return DECIMAL_POINTS;
	}
	public void setDECIMAL_POINTS(BigDecimal dECIMALPOINTS) {
		DECIMAL_POINTS = dECIMALPOINTS;
	}
}
