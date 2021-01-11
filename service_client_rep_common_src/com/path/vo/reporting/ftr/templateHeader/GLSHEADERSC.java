package com.path.vo.reporting.ftr.templateHeader;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class GLSHEADERSC  extends GridParamsSC
{
    private BigDecimal CODE;   
    private String columnName; 
    //lov_type_id of report_properties stored in sys_param_lov_trans
    private BigDecimal repLovTypeID;
    private BigDecimal templateTypeID;    
   

    public BigDecimal getRepLovTypeID()
    {
        return repLovTypeID;
    }

    public void setRepLovTypeID(BigDecimal repLovTypeID)
    {
        this.repLovTypeID = repLovTypeID;
    }

    public GLSHEADERSC(){
	super.setSearchCols(new String[] {"CODE"});
    }
    
    public BigDecimal getCODE()
    {
        return CODE;
    }


    public void setCODE(BigDecimal cODE)
    {
        CODE = cODE;
    }

    public String getColumnName()
    {
        return columnName;
    }

    public void setColumnName(String columnName)
    {
        this.columnName = columnName;
    }

    public void setTemplateTypeID(BigDecimal templateTypeID)
    {
	this.templateTypeID = templateTypeID;
    }

    public BigDecimal getTemplateTypeID()
    {
	return templateTypeID;
    }
}
