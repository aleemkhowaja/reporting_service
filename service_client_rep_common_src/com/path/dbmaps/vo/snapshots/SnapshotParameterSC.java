package com.path.dbmaps.vo.snapshots;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.struts2.lib.common.GridParamsSC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * SnapshotParameterSC.java used to
 */
public class SnapshotParameterSC extends GridParamsSC
{
    private BigDecimal COMP_CODE;
    private BigDecimal REP_ID;
    private String REP_REFERENCE;
    private String DESCRIPTION;
    private String SNAPSHOT_FREQUENCY;
    private boolean isGrid = true;
    private String languageCode;
    private String PARAM_NAME;
    private Date dateUpdated;
    private BigDecimal REP_SNAPSHOT_ID;
    private BigDecimal SITCOM_FILE_ID;
    private String SAVE_REP_YN;
    private BigDecimal AOD_TYPE;
    //The property PROG_REF added since we are calling the mapper fcrRep.retFcrRepSql in snpahsotParameterSC where we need the PROG_REF
    private String PROG_REF;
    private String SNP_REPORT_TYPE;
    private List<BigDecimal> REP_ID_LST;

    public List<BigDecimal> getREP_ID_LST()
    {
        return REP_ID_LST;
    }

    public void setREP_ID_LST(List<BigDecimal> rEP_ID_LST)
    {
        REP_ID_LST = rEP_ID_LST;
    }
    public BigDecimal getSITCOM_FILE_ID()
    {
	return SITCOM_FILE_ID;
    }

    public void setSITCOM_FILE_ID(BigDecimal sITCOMFILEID)
    {
	SITCOM_FILE_ID = sITCOMFILEID;
    }

    public BigDecimal getREP_SNAPSHOT_ID()
    {
	return REP_SNAPSHOT_ID;
    }

    public void setREP_SNAPSHOT_ID(BigDecimal rEPSNAPSHOTID)
    {
	REP_SNAPSHOT_ID = rEPSNAPSHOTID;
    }
    public String getSNP_REPORT_TYPE()
    {
        return SNP_REPORT_TYPE;
    }

    public void setSNP_REPORT_TYPE(String sNPREPORTTYPE)
    {
        SNP_REPORT_TYPE = sNPREPORTTYPE;
    }

    public String getPROG_REF()
    {
        return PROG_REF;
    }

    public void setPROG_REF(String pROGREF)
    {
        PROG_REF = pROGREF;
    }

    public BigDecimal getAOD_TYPE()
    {
        return AOD_TYPE;
    }

    public void setAOD_TYPE(BigDecimal aODTYPE)
    {
        AOD_TYPE = aODTYPE;
    }

    public String getSAVE_REP_YN()
    {
	return SAVE_REP_YN;
    }

    public void setSAVE_REP_YN(String sAVEREPYN)
    {
	SAVE_REP_YN = sAVEREPYN;
    }

    

    public Date getDateUpdated()
    {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated)
    {
        this.dateUpdated = dateUpdated;
    }

    public String getPARAM_NAME()
    {
	return PARAM_NAME;
    }

    public void setPARAM_NAME(String pARAMNAME)
    {
	PARAM_NAME = pARAMNAME;
    }

    public String getDESCRIPTION()
    {
	return DESCRIPTION;
    }

    public void setDESCRIPTION(String dESCRIPTION)
    {
	DESCRIPTION = dESCRIPTION;
    }

    public String getLanguageCode()
    {
	return languageCode;
    }

    public void setLanguageCode(String languageCode)
    {
	this.languageCode = languageCode;
    }

    public boolean isGrid()
    {
	return isGrid;
    }

    public void setGrid(boolean isGrid)
    {
	this.isGrid = isGrid;
    }

    public String getREP_REFERENCE()
    {
	return REP_REFERENCE;
    }

    public void setREP_REFERENCE(String rEPREFERENCE)
    {
	REP_REFERENCE = rEPREFERENCE;
    }

    public String getSNAPSHOT_FREQUENCY()
    {
	return SNAPSHOT_FREQUENCY;
    }

    public void setSNAPSHOT_FREQUENCY(String sNAPSHOTFREQUENCY)
    {
	SNAPSHOT_FREQUENCY = sNAPSHOTFREQUENCY;
    }

    public BigDecimal getREP_ID()
    {
	return REP_ID;
    }

    public void setREP_ID(BigDecimal rEPID)
    {
	REP_ID = rEPID;
    }

    public BigDecimal getCOMP_CODE()
    {
	return COMP_CODE;
    }

    public void setCOMP_CODE(BigDecimal cOMPCODE)
    {
	COMP_CODE = cOMPCODE;
    }

    public SnapshotParameterSC()
    {
	super.setSearchCols(new String[] { "REP_REFERENCE", "repSnapshotParamVO.REP_REFERENCE",
		"repSnapshotInfoVO.DESCRIPTION", "DESCRIPTION", "PARAM_NAME", "USER_ID","USER_GRP_ID","USER_VALID_DT","USER_GRP_DESC","USER_STS","RESIGNED"});
	HashMap hm=new HashMap();
	hm.put("USER_VALID_DT", "USER_VALID_DT");
	super.setDateSearchCols(hm);
    }

}
