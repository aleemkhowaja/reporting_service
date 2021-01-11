package com.path.vo.reporting.ftr.columnTemplate;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_TEMPLATE_COLUMNS_DETSC extends GridParamsSC
{
	private BigDecimal COMP_CODE;
	private BigDecimal TEMPLATE_CODE;
	private BigDecimal TEMPLATE_COL_NO;
	private String LANG_CODE;


	public BigDecimal getTEMPLATE_COL_NO() {
		return TEMPLATE_COL_NO;
	}
	public void setTEMPLATE_COL_NO(BigDecimal tEMPLATECOLNO) {
		TEMPLATE_COL_NO = tEMPLATECOLNO;
	}
	public BigDecimal getTEMPLATE_CODE() {
		return TEMPLATE_CODE;
	}
	public void setTEMPLATE_CODE(BigDecimal tEMPLATECODE) {
		TEMPLATE_CODE = tEMPLATECODE;
	}
	public void setCOMP_CODE(BigDecimal cOMPCODE) {
		COMP_CODE = cOMPCODE;
	}
	public IRP_TEMPLATE_COLUMNS_DETSC(){
		super.setSearchCols(new String[] { "COMP_CODE"});
	}
	public BigDecimal getCOMP_CODE() {
		return COMP_CODE;
	}
	public String getLANG_CODE() {
		return LANG_CODE;
	}
	public void setLANG_CODE(String lANGCODE) {
		LANG_CODE = lANGCODE;
	}
}
