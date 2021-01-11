package com.path.vo.reporting.ftr.cbParam;

import com.path.struts2.lib.common.GridParamsSC;
import java.math.BigDecimal;

public class CbParamSC extends GridParamsSC
{
    
    private BigDecimal COMP_CODE;
    private String pageRef;
    private String entityType;
    private String lang;
    private BigDecimal	lovTypeId;
    
    
    public String getEntityType()
    {
        return entityType;
    }

    public void setEntityType(String entityType)
    {
        this.entityType = entityType;
    }

    public CbParamSC()
    {
	super.setSearchCols(new String[] {"CODE","DESCRIPTION","ENTITY_CB_CODE"});
    }
    
    public BigDecimal getCOMP_CODE()
    {
        return COMP_CODE;
    }

    public void setCOMP_CODE(BigDecimal cOMPCODE)
    {
        COMP_CODE = cOMPCODE;
    }

    public String getPageRef()
    {
        return pageRef;
    }

    public void setPageRef(String pageRef)
    {
        this.pageRef = pageRef;
    }


    public String getLang()
    {
        return lang;
    }

    public void setLang(String lang)
    {
        this.lang = lang;
    }

    public BigDecimal getLovTypeId()
    {
        return lovTypeId;
    }

    public void setLovTypeId(BigDecimal lovTypeId)
    {
        this.lovTypeId = lovTypeId;
    }
    
}
