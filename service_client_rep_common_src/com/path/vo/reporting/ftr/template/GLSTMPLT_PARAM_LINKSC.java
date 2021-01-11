package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class GLSTMPLT_PARAM_LINKSC extends GridParamsSC
{
    private BigDecimal COMP_CODE;
    private BigDecimal TEMP_CODE;
    private BigDecimal LINE_NO;
    
    
    
    public BigDecimal getCOMP_CODE()
    {
        return COMP_CODE;
    }
    public void setCOMP_CODE(BigDecimal cOMPCODE)
    {
        COMP_CODE = cOMPCODE;
    }
    public BigDecimal getTEMP_CODE()
    {
        return TEMP_CODE;
    }
    public void setTEMP_CODE(BigDecimal tEMPCODE)
    {
        TEMP_CODE = tEMPCODE;
    }
    public BigDecimal getLINE_NO()
    {
        return LINE_NO;
    }
    public void setLINE_NO(BigDecimal lINENO)
    {
        LINE_NO = lINENO;
    }

    

}
