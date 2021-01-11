package com.path.vo.reporting.ftr.columnTemplate;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_TEMPLATE_COLUMNSSC extends GridParamsSC
{
	private BigDecimal COMP_CODE;
	private BigDecimal TEMPLATE_CODE;

	public BigDecimal getTEMPLATE_CODE() {
		return TEMPLATE_CODE;
	}
	public void setTEMPLATE_CODE(BigDecimal tEMPLATECODE) {
		TEMPLATE_CODE = tEMPLATECODE;
	}
	public void setCOMP_CODE(BigDecimal cOMPCODE) {
		COMP_CODE = cOMPCODE;
	}
	public IRP_TEMPLATE_COLUMNSSC(){
		super.setSearchCols(new String[] { "COMP_CODE","TEMPLATE_CODE","BRIEF_NAME1","BRIEF_NAME2"});
	}
	public BigDecimal getCOMP_CODE() {
		return COMP_CODE;
	}
}
