package com.path.vo.reporting.ftr.cbParam;

import com.path.dbmaps.vo.FTR_CB_CODEVO;
import com.path.lib.vo.BaseVO;

public class CbParamCO extends BaseVO {
	private FTR_CB_CODEVO ftr_cb_codeVO;
	private String DESCRIPTION;
	private String DOC_TYPE;
	private String SUB_DESCRIPTION;
	private String concatKey;
	
	public String getConcatKey()
	{
	    return concatKey;
	}
	public void setConcatKey(String concatKey)
	{
	    this.concatKey = concatKey;
	}
	public FTR_CB_CODEVO getFtr_cb_codeVO()
	{
	    return ftr_cb_codeVO;
	}
	public void setFtr_cb_codeVO(FTR_CB_CODEVO ftrCbCodeVO)
	{
	    ftr_cb_codeVO = ftrCbCodeVO;
	}
	public String getDESCRIPTION()
	{
	    return DESCRIPTION;
	}
	public void setDESCRIPTION(String dESCRIPTION)
	{
	    DESCRIPTION = dESCRIPTION;
	}
	public String getSUB_DESCRIPTION()
	{
	    return SUB_DESCRIPTION;
	}
	public void setSUB_DESCRIPTION(String sUBDESCRIPTION)
	{
	    SUB_DESCRIPTION = sUBDESCRIPTION;
	}
	public String getDOC_TYPE()
	{
	    return DOC_TYPE;
	}
	public void setDOC_TYPE(String dOCTYPE)
	{
	    DOC_TYPE = dOCTYPE;
	}
}
