package com.path.dbmaps.vo.csvitems;

import com.path.dbmaps.vo.CBK_RPT_LINEVO;
import com.path.lib.vo.BaseVO;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * CsvItemsCO.java used to
 */
public class CsvItemsCO extends BaseVO
{
    
    CBK_RPT_LINEVO cbkRptLineVO = new CBK_RPT_LINEVO();
    private String reportName;
    private String oldCsvItemId;
    
    
    
    
    
    
    
    public String getOldCsvItemId()
    {
        return oldCsvItemId;
    }

    public void setOldCsvItemId(String oldCsvItemId)
    {
        this.oldCsvItemId = oldCsvItemId;
    }

    public String getReportName()
    {
        return reportName;
    }

    public void setReportName(String reportName)
    {
        this.reportName = reportName;
    }

    public CBK_RPT_LINEVO getCbkRptLineVO()
    {
        return cbkRptLineVO;
    }

    public void setCbkRptLineVO(CBK_RPT_LINEVO cbkRptLineVO)
    {
        this.cbkRptLineVO = cbkRptLineVO;
    }
    
    
}
