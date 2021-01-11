package com.path.vo.reporting.ftr.folders;

import java.math.BigDecimal;
import java.util.HashMap;

import com.path.dbmaps.vo.IRP_FOLDERVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;

public class IRP_FOLDERCO extends IRP_FOLDERVO
{

    private String 	PARENT_REF_STR;
    private BigDecimal  PROG_ORDER;
    private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm; // for required input
    private BigDecimal  CATEG_ID;

    public BigDecimal getCATEG_ID()
    {
	return CATEG_ID;
    }

    public void setCATEG_ID(BigDecimal cATEGID)
    {
	CATEG_ID = cATEGID;
    }
    
    
    
    
    
    public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getBusinessHm()
    {
        return businessHm;
    }
    public void setBusinessHm(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm)
    {
        this.businessHm = businessHm;
    }
    public String getPARENT_REF_STR()
    {
        return PARENT_REF_STR;
    }
    public void setPARENT_REF_STR(String pARENTREFSTR)
    {
        PARENT_REF_STR = pARENTREFSTR;
    }
    public BigDecimal getPROG_ORDER()
    {
        return PROG_ORDER;
    }
    public void setPROG_ORDER(BigDecimal pROGORDER)
    {
        PROG_ORDER = pROGORDER;
    }
    
}
