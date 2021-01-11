package com.path.vo.reporting.designer;

import java.math.BigDecimal;
import java.util.List;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_REP_PROCSC extends GridParamsSC 
{
	private String PROCEDURE_NAME;
	private String PROCEDURE_DESC;
	private BigDecimal REP_ID;
	private BigDecimal PROCEDURE_ID;
	private String PARAM_TYPE;
	private BigDecimal EXEC_BEFORE;
	private List<BigDecimal> REPORTS_ID;
	private Boolean isGrid=true;
	
	public List<BigDecimal> getREPORTS_ID() {
		return REPORTS_ID;
	}

	public void setREPORTS_ID(List<BigDecimal> rEPORTSID) {
		REPORTS_ID = rEPORTSID;
	}

	public BigDecimal getEXEC_BEFORE() {
		return EXEC_BEFORE;
	}

	public void setEXEC_BEFORE(BigDecimal eXECBEFORE) {
		EXEC_BEFORE = eXECBEFORE;
	}

	public Boolean getIsGrid() {
		return isGrid;
	}

	public void setIsGrid(Boolean isGrid) {
		this.isGrid = isGrid;
	}

	public IRP_REP_PROCSC(){
		super.setSearchCols(new String[] {"PROC_NAME","PROC_DESC","REPORT_ID","PROC_ID","PARAM_ORDER","PARAM_NAME","PARAM_TYPE","QRY_PARAM_NAME","PROC_NAME","ARGUMENT_NAME","ARGUMENT_LABEL","ARGUMENT_ID"});
	}

	public String getPARAM_TYPE() {
		return PARAM_TYPE;
	}

	public void setPARAM_TYPE(String pARAMTYPE) {
		PARAM_TYPE = pARAMTYPE;
	}

	public String getPROCEDURE_NAME() {
		return PROCEDURE_NAME;
	}

	public void setPROCEDURE_NAME(String pROCEDURENAME) {
		PROCEDURE_NAME = pROCEDURENAME;
	}

	public String getPROCEDURE_DESC() {
		return PROCEDURE_DESC;
	}

	public void setPROCEDURE_DESC(String pROCEDUREDESC) {
		PROCEDURE_DESC = pROCEDUREDESC;
	}

	public BigDecimal getREP_ID() {
		return REP_ID;
	}

	public void setREP_ID(BigDecimal rEPID) {
		REP_ID = rEPID;
	}

	public BigDecimal getPROCEDURE_ID() {
		return PROCEDURE_ID;
	}

	public void setPROCEDURE_ID(BigDecimal pROCEDUREID) {
		PROCEDURE_ID = pROCEDUREID;
	}
	
}
