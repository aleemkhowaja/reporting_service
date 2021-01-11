package com.path.vo.reporting.ftr.fileExportImport;

import java.math.BigDecimal;

import com.path.struts2.lib.common.GridParamsSC;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * FileExportImportSC.java used to
 */
public class IRP_FILE_MAINSC extends GridParamsSC
{
	private String FILE_NAME;
	private BigDecimal FILE_ID;
	
	public BigDecimal getFILE_ID()
	{
	    return FILE_ID;
	}

	public void setFILE_ID(BigDecimal fILEID)
	{
	    FILE_ID = fILEID;
	}

	public IRP_FILE_MAINSC(){
		super.setSearchCols(new String[] {"FILE_ID"});
	}

	public String getFILE_NAME() {
		return FILE_NAME;
	}

	public void setFILE_NAME(String fILENAME) {
		FILE_NAME = fILENAME;
	}

	
}
