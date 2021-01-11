package com.path.vo.reporting.ftr.controlRecord;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;

public class BTR_CONTROLSC extends GridParamsSC
{
    private BigDecimal COMP_CODE;

    public BigDecimal getCOMP_CODE()
    {
        return COMP_CODE;
    }

    public void setCOMP_CODE(BigDecimal cOMPCODE)
    {
        COMP_CODE = cOMPCODE;
    }
}
