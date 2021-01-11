package com.path.dbmaps.vo.csvitems;

import com.path.struts2.lib.common.GridParamsSC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * CsvItemsSC.java used to
 */
public class CsvItemsSC extends GridParamsSC
{

    private String REP_REF;
    private String appName;
    private String FROM_SNP;
    
    
    

    public String getFROM_SNP()
    {
        return FROM_SNP;
    }

    public void setFROM_SNP(String fROM_SNP)
    {
        FROM_SNP = fROM_SNP;
    }

    
    

    public String getAppName()
    {
        return appName;
    }

    public void setAppName(String appName)
    {
        this.appName = appName;
    }

    public String getREP_REF()
    {
	return REP_REF;
    }

    public void setREP_REF(String rEPREF)
    {
	REP_REF = rEPREF;
    }

    public CsvItemsSC()
    {
	super.setSearchCols(new String[] {"REP_REF","reportName","PROG_REF","REPORT_NAME"});
	  
    }
}
