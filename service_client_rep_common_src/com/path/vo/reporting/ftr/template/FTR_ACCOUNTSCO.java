package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.dbmaps.vo.FTR_ACCOUNTSVO;
import com.path.lib.vo.BaseVO;

public class FTR_ACCOUNTSCO extends BaseVO 
{
	
    private FTR_ACCOUNTSVO ftrAccountVO =  new FTR_ACCOUNTSVO();
    private BigDecimal concatKey;
    private String incl;

   
    public String getIncl()
    {
	if(( this.getFtrAccountVO().getINCLUDE().equals("Y") && (this.incl==null||this.incl.equals("true")||this.incl.equals("Y")
		||this.incl.equals("Yes"))) 
	     || 
	     	(this.incl!=null&&(this.incl.equals("Y") || this.incl.equals("true"))))
	{
	    return "true";
	}
	//for the case where nothing in ftr_accounts for this template/GL and the user checks manually in the screen
	else if ( this.getFtrAccountVO().getINCLUDE().equals("N") && (this.incl!=null && (this.incl.equals("true")||this.incl.equals("Y")
		||this.incl.equals("Yes"))))
	{
	    return "true";
	}
	else
	{
	    return "false";
	}
    }

    public void setIncl(String incl)
    {
        this.incl = incl;
    }

    public BigDecimal getConcatKey()
    {
        return concatKey;
    }

    public void setConcatKey(BigDecimal concatKey)
    {
        this.concatKey = concatKey;
    }

    public FTR_ACCOUNTSVO getFtrAccountVO()
    {
        return ftrAccountVO;
    }

    public void setFtrAccountVO(FTR_ACCOUNTSVO ftrAccountVO)
    {
        this.ftrAccountVO = ftrAccountVO;
    }
    
	
}
