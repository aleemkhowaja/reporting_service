package com.path.dbmaps.vo.reportsmismatch;

import java.math.BigDecimal;
import java.util.Date;

import com.path.struts2.lib.common.GridParamsSC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * ReportsMismatchSC.java used to
 */
public class ReportsMismatchSC extends GridParamsSC
{
    private BigDecimal compCode;
    private BigDecimal misType;
    private String appName;
    private BigDecimal REP_MISMATCH_ID;
    private boolean isGrid = true;
    private String repReference;
    private String criteriaCode;
    private Date dateUpdated;
    private BigDecimal parentId;
    
    
    public BigDecimal getParentId()
    {
        return parentId;
    }

    public void setParentId(BigDecimal parentId)
    {
        this.parentId = parentId;
    }

    public Date getDateUpdated()
    {
	return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated)
    {
	this.dateUpdated = dateUpdated;
    }

    public String getCriteriaCode()
    {
	return criteriaCode;
    }

    public void setCriteriaCode(String criteriaCode)
    {
	this.criteriaCode = criteriaCode;
    }

    public String getRepReference()
    {
	return repReference;
    }

    public void setRepReference(String repReference)
    {
	this.repReference = repReference;
    }

    public boolean isGrid()
    {
	return isGrid;
    }

    public void setGrid(boolean isGrid)
    {
	this.isGrid = isGrid;
    }

    public BigDecimal getREP_MISMATCH_ID()
    {
	return REP_MISMATCH_ID;
    }

    public void setREP_MISMATCH_ID(BigDecimal rEPMISMATCHID)
    {
	REP_MISMATCH_ID = rEPMISMATCHID;
    }

    public ReportsMismatchSC()
    {
	super.setSearchCols(new String[] { "REP_REFERENCE", "PROG_DESC", "RELATED_COLUMN",
		"repMismatchParamVO.CRITERIA_CODE", "repMismatchParamVO.REP_REFERENCE" });
    }

    public String getAppName()
    {
	return appName;
    }

    public void setAppName(String appName)
    {
	this.appName = appName;
    }

    public BigDecimal getCompCode()
    {
	return compCode;
    }

    public void setCompCode(BigDecimal compCode)
    {
	this.compCode = compCode;
    }

    public BigDecimal getMisType()
    {
	return misType;
    }

    public void setMisType(BigDecimal misType)
    {
	this.misType = misType;
    }

}
