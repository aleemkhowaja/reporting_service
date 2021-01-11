package com.path.vo.reporting.ftr.reportsmismatch;

import java.io.Serializable;
import com.path.dbmaps.vo.REP_MISMATCH_COLUMNVO;
import com.path.lib.vo.BaseVO;

public class REP_MISMATCH_COLUMNCO extends BaseVO implements Serializable
{
    private REP_MISMATCH_COLUMNVO repMismatchColumnVO = new REP_MISMATCH_COLUMNVO();
    private String CRITERIA_COL;
    private String TECH_COL_NAME;

    public String getTECH_COL_NAME()
    {
        return TECH_COL_NAME;
    }

    public void setTECH_COL_NAME(String tECHCOLNAME)
    {
        TECH_COL_NAME = tECHCOLNAME;
    }

    public String getCRITERIA_COL()
    {
        return CRITERIA_COL;
    }

    public void setCRITERIA_COL(String cRITERIACOL)
    {
        CRITERIA_COL = cRITERIACOL;
    }

    public REP_MISMATCH_COLUMNVO getRepMismatchColumnVO()
    {
        return repMismatchColumnVO;
    }

    public void setRepMismatchColumnVO(REP_MISMATCH_COLUMNVO repMismatchColumnVO)
    {
        this.repMismatchColumnVO = repMismatchColumnVO;
    }
    
}
