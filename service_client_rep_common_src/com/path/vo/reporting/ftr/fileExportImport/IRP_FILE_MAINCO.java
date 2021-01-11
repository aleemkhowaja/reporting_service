package com.path.vo.reporting.ftr.fileExportImport;

import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.vo.BaseVO;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * FileExportImportCO.java used to
 */
public class IRP_FILE_MAINCO extends BaseVO
{    IRP_FILE_MAINVO irp_file_mainVO = new IRP_FILE_MAINVO();

public IRP_FILE_MAINVO getIrp_file_mainVO()
{
    return irp_file_mainVO;
}
public void setIrp_file_mainVO(IRP_FILE_MAINVO irpFileMainVO)
{
    irp_file_mainVO = irpFileMainVO;
}

}

