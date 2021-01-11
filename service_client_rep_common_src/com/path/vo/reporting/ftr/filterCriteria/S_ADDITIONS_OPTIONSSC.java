package com.path.vo.reporting.ftr.filterCriteria;

import com.path.struts2.lib.common.GridParamsSC;
import java.math.BigDecimal;

public class S_ADDITIONS_OPTIONSSC extends GridParamsSC
{
    private BigDecimal OPTION_CODE;
    private String BRIEF_NAME_ENG;
    private String LONG_NAME_ENG;
    
    
   


    public String getBRIEF_NAME_ENG()
    {
        return BRIEF_NAME_ENG;
    }


    public void setBRIEF_NAME_ENG(String bRIEFNAMEENG)
    {
        BRIEF_NAME_ENG = bRIEFNAMEENG;
    }


    public String getLONG_NAME_ENG()
    {
        return LONG_NAME_ENG;
    }


    public void setLONG_NAME_ENG(String lONGNAMEENG)
    {
        LONG_NAME_ENG = lONGNAMEENG;
    }


    public S_ADDITIONS_OPTIONSSC(){
	super.setSearchCols(new String[] { "OPTION_CODE" , "BRIEF_NAME_ENG","LONG_NAME_ENG"});
}


    public BigDecimal getOPTION_CODE()
    {
        return OPTION_CODE;
    }


    public void setOPTION_CODE(BigDecimal oPTIONCODE)
    {
        OPTION_CODE = oPTIONCODE;
    }
}
