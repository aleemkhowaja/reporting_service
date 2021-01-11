package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class FTR_ACCOUNTSSC extends GridParamsSC 
{
	
    private BigDecimal tmpCode;
    private BigDecimal tmpltLineNbr;
    private BigDecimal subLineNbr;
    private BigDecimal branch;
    private BigDecimal div;
    private BigDecimal dpt;
    private BigDecimal fromGL;
    private BigDecimal toGL;
    
    public BigDecimal getTmpCode()
    {
        return tmpCode;
    }

    public void setTmpCode(BigDecimal tmpCode)
    {
        this.tmpCode = tmpCode;
    }

    public BigDecimal getTmpltLineNbr()
    {
        return tmpltLineNbr;
    }

    public void setTmpltLineNbr(BigDecimal tmpltLineNbr)
    {
        this.tmpltLineNbr = tmpltLineNbr;
    }

    public BigDecimal getSubLineNbr()
    {
        return subLineNbr;
    }

    public void setSubLineNbr(BigDecimal subLineNbr)
    {
        this.subLineNbr = subLineNbr;
    }

    public BigDecimal getBranch()
    {
        return branch;
    }

    public void setBranch(BigDecimal branch)
    {
        this.branch = branch;
    }

    public BigDecimal getDiv()
    {
        return div;
    }

    public void setDiv(BigDecimal div)
    {
        this.div = div;
    }

    public BigDecimal getDpt()
    {
        return dpt;
    }

    public void setDpt(BigDecimal dpt)
    {
        this.dpt = dpt;
    }

  

    public BigDecimal getFromGL()
    {
        return fromGL;
    }

    public void setFromGL(BigDecimal fromGL)
    {
        this.fromGL = fromGL;
    }

    public BigDecimal getToGL()
    {
        return toGL;
    }

    public void setToGL(BigDecimal toGL)
    {
        this.toGL = toGL;
    }

    public FTR_ACCOUNTSSC(){
	super.setSearchCols(new String[] {"CODE"});
    }
    
    
  
    
    
}
