package com.path.vo.reporting.connection;

import java.util.HashMap;

import com.path.dbmaps.vo.IRP_APP_CONNECTIONVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;

public class IRP_APP_CONNECTIONCO extends BaseVO
{

    private IRP_APP_CONNECTIONVO irpAppConnectionVO = new IRP_APP_CONNECTIONVO();
    private String CONN_DESC;
    private String LONG_DESC;
    private String APP_NAME;
    private String actionType;
    private String langCode;
    
    public void setLangCode(String langCode)
    {
	this.langCode = langCode;
    }
    
    public String getLangCode()
    {
	return langCode;
    }
    
    private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm; 
    
    

    public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getBusinessHm()
    {
        return businessHm;
    }

    public void setBusinessHm(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm)
    {
        this.businessHm = businessHm;
    }

    public String getActionType()
    {
        return actionType;
    }

    public void setActionType(String actionType)
    {
        this.actionType = actionType;
    }

    public String getCONN_DESC()
    {
        return CONN_DESC;
    }

    public void setCONN_DESC(String cONNDESC)
    {
        CONN_DESC = cONNDESC;
    }

    public String getAPP_NAME()
    {
        return APP_NAME;
    }

    public void setAPP_NAME(String aPPNAME)
    {
        APP_NAME = aPPNAME;
    }

    public String getLONG_DESC()
    {
        return LONG_DESC;
    }

    public void setLONG_DESC(String lONGDESC)
    {
        LONG_DESC = lONGDESC;
    }

    public IRP_APP_CONNECTIONVO getIrpAppConnectionVO()
    {
        return irpAppConnectionVO;
    }

    public void setIrpAppConnectionVO(IRP_APP_CONNECTIONVO irpAppConnectionVO)
    {
        this.irpAppConnectionVO = irpAppConnectionVO;
    }
    
}
