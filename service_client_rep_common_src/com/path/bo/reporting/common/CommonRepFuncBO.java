package com.path.bo.reporting.common;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.path.dbmaps.vo.AXSVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.AXSSC;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;

public interface CommonRepFuncBO 
{
    public BigDecimal retCounterValue(String tableName) throws BaseException;

    public boolean checkReportAccess(AXSVO axsVO) throws BaseException;

    public boolean checkUpdateReportAccess(AXSSC axsSC) throws BaseException;

    /**
     * Method that gets the repository location of the excel file that will be written
     * @param progRef
     * @param repositoryPath
     * @param reportBytes
     * * @param reportCO
     * * @param var_noHeadAndFoot
     * * @param parameters
     * * @param usrName
     * * @param con
     * @throws BaseException
     */
    public void writeFileToRepository(String progRef, byte[] reportBytes, IRP_AD_HOC_REPORTCO reportCO,
	    boolean var_noHeadAndFoot, Map parameters, Connection con,String usrName,String origFormat) throws BaseException;

    /**
     * Common function to generate sitcom file from snapshot information section and from retrieval screen
     * @param snapshotId
     * @param appName
     * @param repId
     * @param repRef
     * @param companyCode
     * @param retrieveDate
     * @param snpFreq
     * @param parameters
     * @return
     * @throws BaseException
     */
    public StringBuffer generateSitcom(BigDecimal snapshotId, String appName, BigDecimal repId, String repRef,
	    BigDecimal companyCode, Date retrieveDate, String snpFreq, Map parameters,HashMap<Object,Object> sitcomParamsMap) throws BaseException;
    /**
     * Method that returns the snapshot parameter config to use
     * @param progRef
     * @return
     * @throws BaseException
     */
    public int retMinRepSnapshotRepId(String progRef) throws BaseException;
}
