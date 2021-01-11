package com.path.vo.reporting.ftr.fileExportImport;

import java.math.BigDecimal;
import java.util.Date;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_FILE_DETSC extends GridParamsSC
{
    /**
     * This field corresponds to the database column IRP_FILE_MAIN.FILE_ID
     */
    private String APP_NAME;
    private BigDecimal FILE_ID;
    private String PROG_REF;

    public String getPROG_REF()
    {
        return PROG_REF;
    }

    public void setPROG_REF(String pROGREF)
    {
        PROG_REF = pROGREF;
    }

    public BigDecimal getFILE_ID()
    {
        return FILE_ID;
    }

    public void setFILE_ID(BigDecimal fILEID)
    {
        FILE_ID = fILEID;
    }

    /**
     * This field corresponds to the database column IRP_FILE_MAIN.FILE_NAME
     */
    private String SUB_FILE_NAME;

    public String getSUB_FILE_NAME()
    {
        return SUB_FILE_NAME;
    }

    public void setSUB_FILE_NAME(String sUBFILENAME)
    {
	SUB_FILE_NAME = sUBFILENAME;
    }

    /**
     * This field corresponds to the database column IRP_FILE_MAIN.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    public Date getDATE_UPDATED()
    {
        return DATE_UPDATED;
    }

    public void setDATE_UPDATED(Date dATEUPDATED)
    {
        DATE_UPDATED = dATEUPDATED;
    }
    
	public IRP_FILE_DETSC(){
		super.setSearchCols(new String[] {"FILE_ID","SUB_FILE_NAME","DATE_UPDATED","PROG_REF","REPORT_NAME"});
}

	public void setAPP_NAME(String aPP_NAME)
	{
	    APP_NAME = aPP_NAME;
	}

	public String getAPP_NAME()
	{
	    return APP_NAME;
	}
}
