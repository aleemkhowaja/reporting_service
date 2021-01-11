package com.path.vo.reporting.ftr.template;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.path.dbmaps.vo.GLSTMPLTGLSDETVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;

public class GLSTMPLTGLSDETCO extends BaseVO implements Serializable
{
    private GLSTMPLTGLSDETVO glstmpltGlsDetVO = new GLSTMPLTGLSDETVO();
    
    private BigDecimal newLineNumber; //to handle the reorganize button
    
    private BigDecimal concatKey;

    private String FROM_GLStr;

    private String TO_GLStr;

    private boolean EXCLUDE_CLOSING_ENTRY_CHK;

    private String GL_COMP_NAME;

    private String BRANCH_NAME;

    private String DIV_NAME;

    private String DEPT_NAME;

    private String CALC_TYPE_NAME;

    private BigDecimal ACC_CODE;

    private String CIF; // used for selected acc.

    private String SL;// used for selected acc.

    private String currency;// used for selected acc.

    private String include;// used for selected acc.

    private HashMap accountsMap = new HashMap();
    
    private LinkedHashMap<BigDecimal,FTR_ACCOUNTSCO> addSelectAccListMap = new LinkedHashMap<BigDecimal,FTR_ACCOUNTSCO>();
    
    private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

   

   

    public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getBusinessHm()
    {
        return businessHm;
    }

    public void setBusinessHm(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm)
    {
        this.businessHm = businessHm;
    }


    public LinkedHashMap<BigDecimal,FTR_ACCOUNTSCO> getAddSelectAccListMap()
    {
        return addSelectAccListMap;
    }

    public void setAddSelectAccListMap(LinkedHashMap<BigDecimal,FTR_ACCOUNTSCO> addSelectAccListMap)
    {
        this.addSelectAccListMap = addSelectAccListMap;
    }

    public BigDecimal getNewLineNumber()
    {
        return newLineNumber;
    }

    public void setNewLineNumber(BigDecimal newLineNumber)
    {
        this.newLineNumber = newLineNumber;
    }

    public GLSTMPLTGLSDETVO getGlstmpltGlsDetVO()
    {
        return glstmpltGlsDetVO;
    }

    public void setGlstmpltGlsDetVO(GLSTMPLTGLSDETVO glstmpltGlsDetVO)
    {
        this.glstmpltGlsDetVO = glstmpltGlsDetVO;
    }

    public BigDecimal getConcatKey()
    {
	return concatKey;
    }

    public void setConcatKey(BigDecimal concatKey)
    {
	this.concatKey = concatKey;
    }

    public String getFROM_GLStr()
    {
	return FROM_GLStr;
    }

    public void setFROM_GLStr(String fROMGLStr)
    {
	FROM_GLStr = fROMGLStr;
    }

    public String getTO_GLStr()
    {
	return TO_GLStr;
    }

    public void setTO_GLStr(String tOGLStr)
    {
	TO_GLStr = tOGLStr;
    }

    public boolean isEXCLUDE_CLOSING_ENTRY_CHK()
    {
	return EXCLUDE_CLOSING_ENTRY_CHK;
    }

    public void setEXCLUDE_CLOSING_ENTRY_CHK(boolean eXCLUDECLOSINGENTRYCHK)
    {
	EXCLUDE_CLOSING_ENTRY_CHK = eXCLUDECLOSINGENTRYCHK;
    }

    public String getGL_COMP_NAME()
    {
	return GL_COMP_NAME;
    }

    public void setGL_COMP_NAME(String gLCOMPNAME)
    {
	GL_COMP_NAME = gLCOMPNAME;
    }

    public String getBRANCH_NAME()
    {
	return BRANCH_NAME;
    }

    public void setBRANCH_NAME(String bRANCHNAME)
    {
	BRANCH_NAME = bRANCHNAME;
    }

    public String getDIV_NAME()
    {
	return DIV_NAME;
    }

    public void setDIV_NAME(String dIVNAME)
    {
	DIV_NAME = dIVNAME;
    }

    public String getDEPT_NAME()
    {
	return DEPT_NAME;
    }

    public void setDEPT_NAME(String dEPTNAME)
    {
	DEPT_NAME = dEPTNAME;
    }

    public String getCALC_TYPE_NAME()
    {
	return CALC_TYPE_NAME;
    }

    public void setCALC_TYPE_NAME(String cALCTYPENAME)
    {
	CALC_TYPE_NAME = cALCTYPENAME;
    }

    public BigDecimal getACC_CODE()
    {
	return ACC_CODE;
    }

    public void setACC_CODE(BigDecimal aCCCODE)
    {
	ACC_CODE = aCCCODE;
    }

    public String getCIF()
    {
	return CIF;
    }

    public void setCIF(String cIF)
    {
	CIF = cIF;
    }

    public String getSL()
    {
	return SL;
    }

    public void setSL(String sL)
    {
	SL = sL;
    }

    public String getCurrency()
    {
	return currency;
    }

    public void setCurrency(String currency)
    {
	this.currency = currency;
    }

    public String getInclude()
    {
	return include;
    }

    public void setInclude(String include)
    {
	this.include = include;
    }

    public HashMap getAccountsMap()
    {
	return accountsMap;
    }

    public void setAccountsMap(HashMap accountsMap)
    {
	this.accountsMap = accountsMap;
    }

}
