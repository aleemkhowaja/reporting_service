package com.path.vo.reporting.ftr.fileExportImport;

import com.path.dbmaps.vo.IRP_FILE_DETVO;
import com.path.lib.vo.BaseVO;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * FileExportImportCO.java used to
 */
public class IRP_FILE_DETCO extends BaseVO
{
    
    IRP_FILE_DETVO irp_file_detVO = new IRP_FILE_DETVO();
    
    private String PROG_REF_D00;

    /**
     * @return the pROG_REF_D00
     */
    public String getPROG_REF_D00()
    {
        return PROG_REF_D00;
    }

    /**
     * @param pROGREFD00 the pROG_REF_D00 to set
     */
    public void setPROG_REF_D00(String pROGREFD00)
    {
        PROG_REF_D00 = pROGREFD00;
    }

    public IRP_FILE_DETVO getIrp_file_detVO()
    {
        return irp_file_detVO;
    }

    public void setIrp_file_detVO(IRP_FILE_DETVO irpFileDetVO)
    {
        irp_file_detVO = irpFileDetVO;
    }
    
    private String PROG_REF;
    public String getPROG_REF()
    {
        return PROG_REF;
    }

    public void setPROG_REF(String pROGREF)
    {
        PROG_REF = pROGREF;
    }

    private String REPORT_NAME;
    
    private String concatKey;
    
    public String getREPORT_NAME()
    {
        return REPORT_NAME;
    }

    public void setREPORT_NAME(String rEPORTNAME)
    {
	REPORT_NAME = rEPORTNAME;
    }

    public void setConcatKey(String concatKey)
    {
	this.concatKey = concatKey;
    }

    public String getConcatKey()
    {
	return concatKey;
    }
}
