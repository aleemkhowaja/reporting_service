package com.path.vo.reporting.ftr.reportsmismatch;

import java.math.BigDecimal;
import java.util.Date;

import com.path.lib.vo.BaseVO;

public class REP_MISMATCH_PROCESSCO extends BaseVO 
{
    private String FREQUENCY;
    private BigDecimal TYPE;
    private Date PERIOD;
    private String CRT_PROGREF;
    private String PROG_REF;
    private String CRITERIA;
    private String CRITERIA_COLUMN;
    private BigDecimal REP_MISMATCH_ID;
    private String crtsProgRefs;
    private String TRANS_MSG;
  
    public String getTRANS_MSG()
    {
        return TRANS_MSG;
    }
    public void setTRANS_MSG(String tRANSMSG)
    {
        TRANS_MSG = tRANSMSG;
    }
    public String getCrtsProgRefs()
    {
        return crtsProgRefs;
    }
    public void setCrtsProgRefs(String crtsProgRefs)
    {
        this.crtsProgRefs = crtsProgRefs;
    }
    public BigDecimal getREP_MISMATCH_ID()
    {
        return REP_MISMATCH_ID;
    }
    public void setREP_MISMATCH_ID(BigDecimal rEPMISMATCHID)
    {
        REP_MISMATCH_ID = rEPMISMATCHID;
    }
    public String getCRITERIA_COLUMN()
    {
        return CRITERIA_COLUMN;
    }
    public void setCRITERIA_COLUMN(String cRITERIACOLUMN)
    {
        CRITERIA_COLUMN = cRITERIACOLUMN;
    }
    public String getPROG_REF()
    {
        return PROG_REF;
    }
    public void setPROG_REF(String pROGREF)
    {
        PROG_REF = pROGREF;
    }
    public String getCRITERIA()
    {
        return CRITERIA;
    }
    public void setCRITERIA(String cRITERIA)
    {
        CRITERIA = cRITERIA;
    }
    public String getFREQUENCY()
    {
        return FREQUENCY;
    }
    public void setFREQUENCY(String fREQUENCY)
    {
        FREQUENCY = fREQUENCY;
    }
    public BigDecimal getTYPE()
    {
        return TYPE;
    }
    public void setTYPE(BigDecimal tYPE)
    {
        TYPE = tYPE;
    }
    public Date getPERIOD()
    {
        return PERIOD;
    }
    public void setPERIOD(Date pERIOD)
    {
        PERIOD = pERIOD;
    }
    public String getCRT_PROGREF()
    {
        return CRT_PROGREF;
    }
    public void setCRT_PROGREF(String cRTPROGREF)
    {
        CRT_PROGREF = cRTPROGREF;
    }
  
    
}
