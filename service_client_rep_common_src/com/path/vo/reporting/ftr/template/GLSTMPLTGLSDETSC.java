package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class GLSTMPLTGLSDETSC extends GridParamsSC
{
    private BigDecimal FROM_GL;

    private BigDecimal TO_GL;

    private BigDecimal GL_COMP;

    private BigDecimal BRANCH_CODE;

    private BigDecimal DIV_CODE;

    private BigDecimal DEPT_CODE;

    private BigDecimal PERCENTAGE;

    private String CALC_TYPE;

    private String EXCLUDE_CLOSING_ENTRY;

    private BigDecimal COMP_CODE;

    private BigDecimal LINE_NBR_DET;

    private BigDecimal CODE;

    private BigDecimal LINE_NBR;

    private String LANG_CODE;

    public BigDecimal getFROM_GL()
    {
	return FROM_GL;
    }

    public BigDecimal getGL_COMP()
    {
	return GL_COMP;
    }

    public void setGL_COMP(BigDecimal gLCOMP)
    {
	GL_COMP = gLCOMP;
    }

    public BigDecimal getLINE_NBR_DET()
    {
	return LINE_NBR_DET;
    }

    public void setLINE_NBR_DET(BigDecimal lINENBRDET)
    {
	LINE_NBR_DET = lINENBRDET;
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

    public void setFROM_GL(BigDecimal FROM_GL)
    {
	this.FROM_GL = FROM_GL;
    }

    public BigDecimal getTO_GL()
    {
	return TO_GL;
    }

    public void setTO_GL(BigDecimal TO_GL)
    {
	this.TO_GL = TO_GL;
    }

    public BigDecimal getBRANCH_CODE()
    {
	return BRANCH_CODE;
    }

    public void setBRANCH_CODE(BigDecimal BRANCH_CODE)
    {
	this.BRANCH_CODE = BRANCH_CODE;
    }

    public BigDecimal getDIV_CODE()
    {
	return DIV_CODE;
    }

    public void setDIV_CODE(BigDecimal DIV_CODE)
    {
	this.DIV_CODE = DIV_CODE;
    }

    public BigDecimal getDEPT_CODE()
    {
	return DEPT_CODE;
    }

    public void setDEPT_CODE(BigDecimal DEPT_CODE)
    {
	this.DEPT_CODE = DEPT_CODE;
    }

    public BigDecimal getPERCENTAGE()
    {
	return PERCENTAGE;
    }

    public void setPERCENTAGE(BigDecimal PERCENTAGE)
    {
	this.PERCENTAGE = PERCENTAGE;
    }

    public String getCALC_TYPE()
    {
	return CALC_TYPE;
    }

    public void setCALC_TYPE(String CALC_TYPE)
    {
	this.CALC_TYPE = CALC_TYPE == null ? null : CALC_TYPE.trim();
    }

    public String getEXCLUDE_CLOSING_ENTRY()
    {
	return EXCLUDE_CLOSING_ENTRY;
    }

    public void setEXCLUDE_CLOSING_ENTRY(String EXCLUDE_CLOSING_ENTRY)
    {
	this.EXCLUDE_CLOSING_ENTRY = EXCLUDE_CLOSING_ENTRY == null ? null : EXCLUDE_CLOSING_ENTRY.trim();
    }

    public BigDecimal getCOMP_CODE()
    {
	return COMP_CODE;
    }

    public void setCOMP_CODE(BigDecimal COMP_CODE)
    {
	this.COMP_CODE = COMP_CODE;
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
