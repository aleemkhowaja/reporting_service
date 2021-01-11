package com.path.vo.reporting.ftr.reportsmismatch;

import java.io.Serializable;
import java.math.BigDecimal;
import com.path.dbmaps.vo.REP_MISMATCH_PARAMVO;
import com.path.lib.vo.BaseVO;

public class REP_MISMATCH_PARAMCO extends BaseVO implements Serializable
{
    private REP_MISMATCH_PARAMVO repMismatchParamVO = new REP_MISMATCH_PARAMVO();
    private String oldCrt;
    private String oldProgRef;
    private String relRep;
    private String relCols;
    private String relCrt;
    private String progDesc;
    private BigDecimal MIS_TYPE;
    private String CRT_COL;
    private BigDecimal PARENT_ID;
    private String CONCAT_KEY;

    public String getCONCAT_KEY()
    {
        return CONCAT_KEY;
    }

    public void setCONCAT_KEY(String cONCATKEY)
    {
        CONCAT_KEY = cONCATKEY;
    }

    public BigDecimal getPARENT_ID()
    {
        return PARENT_ID;
    }

    public void setPARENT_ID(BigDecimal pARENTID)
    {
        PARENT_ID = pARENTID;
    }

    public String getCRT_COL()
    {
        return CRT_COL;
    }

    public void setCRT_COL(String cRTCOL)
    {
        CRT_COL = cRTCOL;
    }

    public BigDecimal getMIS_TYPE()
    {
        return MIS_TYPE;
    }

    public void setMIS_TYPE(BigDecimal mISTYPE)
    {
        MIS_TYPE = mISTYPE;
    }

    public String getProgDesc()
    {
        return progDesc;
    }

    public void setProgDesc(String progDesc)
    {
        this.progDesc = progDesc;
    }

    public REP_MISMATCH_PARAMVO getRepMismatchParamVO()
    {
	return repMismatchParamVO;
    }

    public void setRepMismatchParamVO(REP_MISMATCH_PARAMVO repMismatchParamVO)
    {
	this.repMismatchParamVO = repMismatchParamVO;
    }

    public String getOldCrt()
    {
	return oldCrt;
    }

    public void setOldCrt(String oldCrt)
    {
	this.oldCrt = oldCrt;
    }

    public String getOldProgRef()
    {
	return oldProgRef;
    }

    public void setOldProgRef(String oldProgRef)
    {
	this.oldProgRef = oldProgRef;
    }

    public String getRelRep()
    {
	return relRep;
    }

    public void setRelRep(String relRep)
    {
	this.relRep = relRep;
    }

    public String getRelCols()
    {
	return relCols;
    }

    public void setRelCols(String relCols)
    {
	this.relCols = relCols;
    }

    public String getRelCrt()
    {
	return relCrt;
    }

    public void setRelCrt(String relCrt)
    {
	this.relCrt = relCrt;
    }

}
