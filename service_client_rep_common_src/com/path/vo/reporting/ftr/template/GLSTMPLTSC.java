package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.struts2.lib.common.GridParamsSC;

public class GLSTMPLTSC extends GridParamsSC
{

    private BigDecimal COMP_CODE;
    private BigDecimal CODE;
    private BigDecimal LINE_NBR;
    private String LANG_CODE;
    private BigDecimal oldCode;
    private String userName;
    boolean isGrid = true;
    private  String templateType;

    

    public String getTemplateType()
    {
        return templateType;
    }

    public void setTemplateType(String templateType)
    {
        this.templateType = templateType;
    }

    public boolean isGrid()
    {
	return isGrid;
    }

    public void setGrid(boolean isGrid)
    {
	this.isGrid = isGrid;
    }

    public String getUserName()
    {
	return userName;
    }

    public void setUserName(String userName)
    {
	this.userName = userName;
    }

    public BigDecimal getOldCode()
    {
	return oldCode;
    }

    public void setOldCode(BigDecimal oldCode)
    {
	this.oldCode = oldCode;
    }

    public GLSTMPLTSC()
    {
	templateType=RepConstantsCommon.TEMPLATE_TYPE;
	super.setSearchCols(new String[] { "CODE", "glstmpltVO.LINE_NBR", "glstmpltVO.BRIEF_NAME_ENG" });
    }

    public BigDecimal getCOMP_CODE()
    {
	return COMP_CODE;
    }

    public void setCOMP_CODE(BigDecimal cOMPCODE)
    {
	COMP_CODE = cOMPCODE;
    }

    public BigDecimal getCODE()
    {
	return CODE;
    }

    public void setCODE(BigDecimal cODE)
    {
	CODE = cODE;
    }

    public BigDecimal getLINE_NBR()
    {
	return LINE_NBR;
    }

    public void setLINE_NBR(BigDecimal lINENBR)
    {
	LINE_NBR = lINENBR;
    }

    public String getLANG_CODE()
    {
	return LANG_CODE;
    }

    public void setLANG_CODE(String lANGCODE)
    {
	LANG_CODE = lANGCODE;
    }

}