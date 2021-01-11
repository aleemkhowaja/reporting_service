package com.path.vo.reporting.connection;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_APP_CONNECTIONSC extends GridParamsSC
{
    
    private String APP_NAME;
    private String CON_ID;
    private String langCode;
    
    public void setLangCode(String langCode)
    {
	this.langCode = langCode;
    }
    
    public String getLangCode()
    {
	return langCode;
    }
    
    public IRP_APP_CONNECTIONSC()
    {
	super.setSearchCols(new String[] {"LONG_DESC","CON_ID","APP_NAME"});
    }
    
    public String getCON_ID()
    {
        return CON_ID;
    }

    public void setCON_ID(String cONID)
    {
        CON_ID = cONID;
    }

    public String getAPP_NAME()
    {
        return APP_NAME;
    }

    public void setAPP_NAME(String aPPNAME)
    {
        APP_NAME = aPPNAME;
    }
    
}
