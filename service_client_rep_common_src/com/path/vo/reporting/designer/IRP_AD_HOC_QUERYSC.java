package com.path.vo.reporting.designer;

import java.math.BigDecimal;
import java.util.List;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_AD_HOC_QUERYSC extends GridParamsSC {
	private BigDecimal QUERY_ID;
	private String QUERY_NAME;
	private List<BigDecimal> qryIdLst;
	
	
	public IRP_AD_HOC_QUERYSC(){
		super.setSearchCols(new String[] {"QUERY_ID","QUERY_NAME"});
	}
	
	
	
	public List<BigDecimal> getQryIdLst()
	{
	    return qryIdLst;
	}

	public void setQryIdLst(List<BigDecimal> qryIdLst)
	{
	    this.qryIdLst = qryIdLst;
	}

	public BigDecimal getQUERY_ID() {
		return QUERY_ID;
	}

	public void setQUERY_ID(BigDecimal qUERYID) {
		QUERY_ID = qUERYID;
	}

	public String getQUERY_NAME() {
		return QUERY_NAME;
	}

	public void setQUERY_NAME(String qUERYNAME) {
		QUERY_NAME = qUERYNAME;
	}
}
