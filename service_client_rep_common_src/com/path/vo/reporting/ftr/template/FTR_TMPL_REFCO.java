package com.path.vo.reporting.ftr.template;

import java.io.Serializable;

import com.path.dbmaps.vo.FTR_TMPL_REFVO;
import com.path.lib.vo.BaseVO;

public class FTR_TMPL_REFCO extends BaseVO implements Serializable
{
    private FTR_TMPL_REFVO ftrTmplRefVO = new FTR_TMPL_REFVO();
    private String reportName = "";
    private String oldReportRef;
    private String concatKey;

  

    public String getConcatKey()
    {
        return concatKey;
    }

    public void setConcatKey(String concatKey)
    {
        this.concatKey = concatKey;
    }

    public String getReportName()
    {
	return reportName;
    }

    public void setReportName(String reportName)
    {
	this.reportName = reportName;
    }

    public String getOldReportRef()
    {
	return oldReportRef;
    }

    public void setOldReportRef(String oldReportRef)
    {
	this.oldReportRef = oldReportRef;
    }

    public FTR_TMPL_REFVO getFtrTmplRefVO()
    {
	return ftrTmplRefVO;
    }

    public void setFtrTmplRefVO(FTR_TMPL_REFVO ftrTmplRefVO)
    {
	this.ftrTmplRefVO = ftrTmplRefVO;
    }

}
