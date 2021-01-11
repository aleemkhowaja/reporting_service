package com.path.vo.reporting.ftr.snapshots;

import java.io.Serializable;
import java.math.BigDecimal;

import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.lib.vo.BaseVO;

public class REP_SNAPSHOT_PARAMCO extends BaseVO implements Serializable
{

    private Long ID_MS;
    private BigDecimal COLUMN_MODIFY;
    private BigDecimal COLUMN_DRILLDOWN;
    private String modCol;
    private String modDril;
    private String progRefOld;
    private String freqOld;
    private String PROG_DESC;
    private String PARAM_NAME;
    private String CONCAT_KEY;
    private String IS_FTR_FCR;
    private String formula;
    private BigDecimal REP_SNP_ID;
    private String AS_OF_DATE_PARAM_NAME;
    private String appName;

    public String getAS_OF_DATE_PARAM_NAME()
    {
        return AS_OF_DATE_PARAM_NAME;
    }

    public void setAS_OF_DATE_PARAM_NAME(String aSOFDATEPARAMNAME)
    {
        AS_OF_DATE_PARAM_NAME = aSOFDATEPARAMNAME;
    }

    public BigDecimal getREP_SNP_ID()
    {
        return REP_SNP_ID;
    }

    public void setREP_SNP_ID(BigDecimal rEPSNPID)
    {
        REP_SNP_ID = rEPSNPID;
    }

    public String getFormula()
    {
	return formula;
    }

    public void setFormula(String formula)
    {
	this.formula = formula;
    }

    public String getIS_FTR_FCR()
    {
        return IS_FTR_FCR;
    }

    public void setIS_FTR_FCR(String iSFTRFCR)
    {
        IS_FTR_FCR = iSFTRFCR;
    }

    public String getCONCAT_KEY()
    {
        return CONCAT_KEY;
    }

    public void setCONCAT_KEY(String cONCATKEY)
    {
        CONCAT_KEY = cONCATKEY;
    }

    public String getPARAM_NAME()
    {
	return PARAM_NAME;
    }

    public void setPARAM_NAME(String pARAMNAME)
    {
	PARAM_NAME = pARAMNAME;
    }

    public String getPROG_DESC()
    {
	return PROG_DESC;
    }

    public void setPROG_DESC(String pROGDESC)
    {
	PROG_DESC = pROGDESC;
    }

    private REP_SNAPSHOT_PARAMVO repSnapshotParamVO = new REP_SNAPSHOT_PARAMVO();

    public Long getID_MS()
    {
	return ID_MS;
    }

    public REP_SNAPSHOT_PARAMVO getRepSnapshotParamVO()
    {
	return repSnapshotParamVO;
    }

    public void setRepSnapshotParamVO(REP_SNAPSHOT_PARAMVO repSnapshotParamVO)
    {
	this.repSnapshotParamVO = repSnapshotParamVO;
    }

    public String getProgRefOld()
    {
	return progRefOld;
    }

    public void setProgRefOld(String progRefOld)
    {
	this.progRefOld = progRefOld;
    }

    public String getFreqOld()
    {
	return freqOld;
    }

    public void setFreqOld(String freqOld)
    {
	this.freqOld = freqOld;
    }

    public String getModCol()
    {
	return modCol;
    }

    public void setModCol(String modCol)
    {
	this.modCol = modCol;
    }

    public String getModDril()
    {
	return modDril;
    }

    public void setModDril(String modDril)
    {
	this.modDril = modDril;
    }

    public void setID_MS(Long iDMS)
    {
	ID_MS = iDMS;
    }

    public BigDecimal getCOLUMN_MODIFY()
    {
	return COLUMN_MODIFY;
    }

    public void setCOLUMN_MODIFY(BigDecimal cOLUMNMODIFY)
    {
	COLUMN_MODIFY = cOLUMNMODIFY;
    }

    public BigDecimal getCOLUMN_DRILLDOWN()
    {
	return COLUMN_DRILLDOWN;
    }

    public void setCOLUMN_DRILLDOWN(BigDecimal cOLUMNDRILLDOWN)
    {
	COLUMN_DRILLDOWN = cOLUMNDRILLDOWN;
    }

    public String getAppName()
    {
	return appName;
    }

    public void setAppName(String appName)
    {
	this.appName = appName;
    }
 

}
