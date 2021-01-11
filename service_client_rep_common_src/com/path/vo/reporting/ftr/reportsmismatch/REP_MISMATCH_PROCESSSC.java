package com.path.vo.reporting.ftr.reportsmismatch;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class REP_MISMATCH_PROCESSSC  extends GridParamsSC
{
    
    private BigDecimal MISMATCH_TYPE ;
    private BigDecimal REP_MIS_ID;
    private String CRITERIA_VAL;
    private String[] progRefs;
    private String MISMATCH_FREQ;
    private String MISMATCH_REP_REF;
    
    public String getMISMATCH_REP_REF()
    {
        return MISMATCH_REP_REF;
    }
    public void setMISMATCH_REP_REF(String mISMATCHREPREF)
    {
        MISMATCH_REP_REF = mISMATCHREPREF;
    }
    public String getMISMATCH_FREQ()
    {
        return MISMATCH_FREQ;
    }
    public void setMISMATCH_FREQ(String mISMATCHFREQ)
    {
        MISMATCH_FREQ = mISMATCHFREQ;
    }
    public String[] getProgRefs()
    {
        return progRefs;
    }
    public void setProgRefs(String... progRefs)
    {
        this.progRefs = progRefs;
    }
    public String getCRITERIA_VAL()
    {
        return CRITERIA_VAL;
    }


    public void setCRITERIA_VAL(String cRITERIAVAL)
    {
        CRITERIA_VAL = cRITERIAVAL;
    }


    public BigDecimal getREP_MIS_ID()
    {
        return REP_MIS_ID;
    }


    public void setREP_MIS_ID(BigDecimal rEPMISID)
    {
        REP_MIS_ID = rEPMISID;
    }


    public BigDecimal getMISMATCH_TYPE()
    {
        return MISMATCH_TYPE;
    }


    public void setMISMATCH_TYPE(BigDecimal mISMATCHTYPE)
    {
        MISMATCH_TYPE = mISMATCHTYPE;
    }


    public REP_MISMATCH_PROCESSSC()
    {
	super.setSearchCols(new String[] { "CRT_PROGREF", "PROG_REF", "CRITERIA"});
    }

}
