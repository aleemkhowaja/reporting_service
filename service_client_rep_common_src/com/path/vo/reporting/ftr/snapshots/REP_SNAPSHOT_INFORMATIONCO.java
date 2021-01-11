package com.path.vo.reporting.ftr.snapshots;

import java.io.Serializable;
import java.math.BigDecimal;

import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.lib.vo.BaseVO;

public class REP_SNAPSHOT_INFORMATIONCO extends BaseVO implements Serializable
{
    private REP_SNAPSHOT_PARAMVO repSnapshotParamVO = new REP_SNAPSHOT_PARAMVO();
    private REP_SNAPSHOT_INFOVO repSnapshotInfoVO = new REP_SNAPSHOT_INFOVO();
    private String SNP_FRQ;
    private String ENABLE_SITCOM_YN;

    private BigDecimal COMPANY_CODE;
    private BigDecimal BRCH_CODE;
    private String APPL_NAME;
    private String SNP_FORMAT;
    private String CSV_SEPARATOR;
    private String SHOW_HEAD_FOOT;
    private String REPORT_TYPE;

    public String getENABLE_SITCOM_YN()
    {
	return ENABLE_SITCOM_YN;
    }

    public void setENABLE_SITCOM_YN(String eNABLESITCOMYN)
    {
	ENABLE_SITCOM_YN = eNABLESITCOMYN;
    }
    public String getREPORT_TYPE()
    {
        return REPORT_TYPE;
    }

    public void setREPORT_TYPE(String rEPORTTYPE)
    {
        REPORT_TYPE = rEPORTTYPE;
    }
    public String getSHOW_HEAD_FOOT()
    {
        return SHOW_HEAD_FOOT;
    }

    public void setSHOW_HEAD_FOOT(String sHOWHEADFOOT)
    {
        SHOW_HEAD_FOOT = sHOWHEADFOOT;
    }

    public String getCSV_SEPARATOR()
    {
        return CSV_SEPARATOR;
    }

    public void setCSV_SEPARATOR(String cSVSEPARATOR)
    {
        CSV_SEPARATOR = cSVSEPARATOR;
    }

    public String getSNP_FORMAT()
    {
        return SNP_FORMAT;
    }

    public void setSNP_FORMAT(String sNPFORMAT)
    {
        SNP_FORMAT = sNPFORMAT;
    }

    public BigDecimal getCOMPANY_CODE()
    {
        return COMPANY_CODE;
    }

    public void setCOMPANY_CODE(BigDecimal cOMPANYCODE)
    {
        COMPANY_CODE = cOMPANYCODE;
    }

    public BigDecimal getBRCH_CODE()
    {
        return BRCH_CODE;
    }

    public void setBRCH_CODE(BigDecimal bRCHCODE)
    {
        BRCH_CODE = bRCHCODE;
    }

    public String getAPPL_NAME()
    {
        return APPL_NAME;
    }

    public void setAPPL_NAME(String aPPLNAME)
    {
        APPL_NAME = aPPLNAME;
    }

    public String getSNP_FRQ()
    {
	return SNP_FRQ;
    }

    public void setSNP_FRQ(String sNPFRQ)
    {
	SNP_FRQ = sNPFRQ;
    }

    public REP_SNAPSHOT_PARAMVO getRepSnapshotParamVO()
    {
	return repSnapshotParamVO;
    }

    public void setRepSnapshotParamVO(REP_SNAPSHOT_PARAMVO repSnapshotParamVO)
    {
	this.repSnapshotParamVO = repSnapshotParamVO;
    }

    public REP_SNAPSHOT_INFOVO getRepSnapshotInfoVO()
    {
	return repSnapshotInfoVO;
    }

    public void setRepSnapshotInfoVO(REP_SNAPSHOT_INFOVO repSnapshotInfoVO)
    {
	this.repSnapshotInfoVO = repSnapshotInfoVO;
    }

}