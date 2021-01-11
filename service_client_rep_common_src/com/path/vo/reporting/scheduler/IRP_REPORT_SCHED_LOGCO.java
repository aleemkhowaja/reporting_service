package com.path.vo.reporting.scheduler;

import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOGVO;

public class IRP_REPORT_SCHED_LOGCO extends IRP_REPORT_SCHED_LOGVO
{
    private String REPORT_NAME;
    private String SCHED_NAME;
    private String START_DATE_STR;
    private String END_DATE_STR;
    private String SCHEDULED_DATE_STR;
    private String REP_FORMAT;
    private String START_DATE_STR1;
    private String SCHED_DATE_STR1;
    private String PROG_REF;
    private String IS_DB;
    private String SAVE_DATA_TYPE_STR;
    private String STATUS_DESC;
    private String FILE_NAME;
    private String REPORT_FORMAT_TRANS;
    private String CONN_ID;
    private String REPORT_REF; // to store the report reference of the dynamic report
    private String PREVIEW; 
    
    public String getPREVIEW() {
		return PREVIEW;
	}

	public void setPREVIEW(String pREVIEW) {
		PREVIEW = pREVIEW;
	}

	public String getREPORT_REF()
    {
        return REPORT_REF;
    }

    public void setREPORT_REF(String rEPORTREF)
    {
        REPORT_REF = rEPORTREF;
    }

    public String getCONN_ID()
    {
        return CONN_ID;
    }

    public void setCONN_ID(String cONNID)
    {
        CONN_ID = cONNID;
    }

    public String getREPORT_FORMAT_TRANS()
    {
        return REPORT_FORMAT_TRANS;
    }

    public void setREPORT_FORMAT_TRANS(String rEPORTFORMATTRANS)
    {
        REPORT_FORMAT_TRANS = rEPORTFORMATTRANS;
    }

    public String getFILE_NAME()
    {
        return FILE_NAME;
    }

    public void setFILE_NAME(String fILENAME)
    {
        FILE_NAME = fILENAME;
    }

    public String getSTATUS_DESC()
    {
        return STATUS_DESC;
    }

    public void setSTATUS_DESC(String sTATUSDESC)
    {
        STATUS_DESC = sTATUSDESC;
    }

    public String getSAVE_DATA_TYPE_STR()
    {
	return SAVE_DATA_TYPE_STR;
    }

    public void setSAVE_DATA_TYPE_STR(String sAVEDATATYPESTR)
    {
	SAVE_DATA_TYPE_STR = sAVEDATATYPESTR;
    }

    public String getIS_DB()
    {
	return IS_DB;
    }

    public void setIS_DB(String iSDB)
    {
	IS_DB = iSDB;
    }

    public String getPROG_REF()
    {
	return PROG_REF;
    }

    public void setPROG_REF(String pROGREF)
    {
	PROG_REF = pROGREF;
    }

    public String getSCHED_DATE_STR1()
    {
	return SCHED_DATE_STR1;
    }

    public void setSCHED_DATE_STR1(String sCHEDDATESTR1)
    {
	SCHED_DATE_STR1 = sCHEDDATESTR1;
    }

    public String getSTART_DATE_STR1()
    {
	return START_DATE_STR1;
    }

    public void setSTART_DATE_STR1(String sTARTDATESTR1)
    {
	START_DATE_STR1 = sTARTDATESTR1;
    }

    public String getREP_FORMAT()
    {
	return REP_FORMAT;
    }

    public void setREP_FORMAT(String rEPFORMAT)
    {
	REP_FORMAT = rEPFORMAT;
    }

    public String getSTART_DATE_STR()
    {
	return START_DATE_STR;
    }

    public void setSTART_DATE_STR(String sTARTDATESTR)
    {
	START_DATE_STR = sTARTDATESTR;
    }

    public String getREPORT_NAME()
    {
	return REPORT_NAME;
    }

    public void setREPORT_NAME(String rEPORTNAME)
    {
	REPORT_NAME = rEPORTNAME;
    }

    public String getSCHED_NAME()
    {
	return SCHED_NAME;
    }

    public void setSCHED_NAME(String sCHEDNAME)
    {
	SCHED_NAME = sCHEDNAME;
    }

    public String getEND_DATE_STR()
    {
	return END_DATE_STR;
    }

    public void setEND_DATE_STR(String eNDDATESTR)
    {
	END_DATE_STR = eNDDATESTR;
    }

    public String getSCHEDULED_DATE_STR()
    {
	return SCHEDULED_DATE_STR;
    }

    public void setSCHEDULED_DATE_STR(String sCHEDULEDDATESTR)
    {
	SCHEDULED_DATE_STR = sCHEDULEDDATESTR;
    }


}
