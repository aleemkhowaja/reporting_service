package com.path.vo.reporting.ftr.template;

import java.math.BigDecimal;

import com.path.dbmaps.vo.FTR_MISMATCH_REPORTVO;
import com.path.lib.vo.BaseVO;

public class FTR_MISMATCH_REPORTCO extends BaseVO
{
    private FTR_MISMATCH_REPORTVO ftrMissRepVO = new FTR_MISMATCH_REPORTVO();
    private String TMPLT_NAME;
    private String LINE_NAME;
    private BigDecimal concatKey;
    private BigDecimal newLineNumber; //to handle the reorganize button

    
    public BigDecimal getNewLineNumber()
    {
        return newLineNumber;
    }

    public void setNewLineNumber(BigDecimal newLineNumber)
    {
        this.newLineNumber = newLineNumber;
    }

    public BigDecimal getConcatKey()
    {
        return concatKey;
    }

    public void setConcatKey(BigDecimal concatKey)
    {
        this.concatKey = concatKey;
    }

    public String getTMPLT_NAME()
    {
        return TMPLT_NAME;
    }

    public void setTMPLT_NAME(String tMPLTNAME)
    {
        TMPLT_NAME = tMPLTNAME;
    }

    public String getLINE_NAME()
    {
        return LINE_NAME;
    }

    public void setLINE_NAME(String lINENAME)
    {
        LINE_NAME = lINENAME;
    }

    public FTR_MISMATCH_REPORTVO getFtrMissRepVO()
    {
        return ftrMissRepVO;
    }

    public void setFtrMissRepVO(FTR_MISMATCH_REPORTVO ftrMissRepVO)
    {
        this.ftrMissRepVO = ftrMissRepVO;
    }
    
}
