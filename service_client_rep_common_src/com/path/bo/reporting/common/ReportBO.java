package com.path.bo.reporting.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.dbmaps.vo.IRP_VERIFIED_REPORTSVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.ReportOutputCO;
import com.path.vo.reporting.ReportParamsCO;

import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

public interface ReportBO
{
    public ReportOutputCO printReport(String var_reportName, String reportFormat, Map parameters, String saveCopyLocation,
	    String menu, HashMap<String,Object> repSessionCOMap, int decimalPts, String appName, String usrName,
	    String language, String printerName, BigDecimal dbConn, boolean noHeadAndFoot, String csvSepartor,
	    String noData, BigDecimal fromPage, BigDecimal toPage, String origFormat, String fromMenu,HashMap<String,String> transMsgLangMap) throws BaseException, Exception;

    public void callProcedures(BigDecimal order, Map parameters, IRP_AD_HOC_REPORTCO reportSession, String var_menuId,
	    Connection con) throws BaseException;
    public HashMap<String, Object> callProcedures(BigDecimal order, Map parameters, IRP_AD_HOC_REPORTCO reportSession, String var_menuId,
	    Connection con,HashMap<String,String> TransMsgLangMap ) throws BaseException;


    public IRP_AD_HOC_REPORTCO returnReportById(BigDecimal reportId) throws BaseException, Exception;

    public byte[] addTemplateHeader(GLSHEADERVO glsheaderVO) throws BaseException;

    public void createJRXMLFile(byte[] xml, String reportPath) throws BaseException, IOException;

    public  HashMap<String,Object> createDynamicRowDataJrxml(boolean var_noHeadAndFoot, String var_reportName,
	    String var_menuId, IRP_AD_HOC_REPORTCO reportCO) throws BaseException;
    
    public byte[] loadImage(String fileName, String deleteImg) throws BaseException;
    public Object[] generateReportDesign(IRP_AD_HOC_REPORTCO repCO,
	    HashMap<String, Object> hashLiveSearch,String type,BigDecimal repId,BigDecimal crtColOrRelCol,Map parameters) throws BaseException;
    
    public ReportOutputCO printSnapshot(String var_reportName, Map parameters, String reportFormat,REP_SNAPSHOT_INFOVO infoVO, String appName, String userName,boolean isModifySnp,String csvSeparator,boolean showHeadFoot) throws BaseException;
    public void verifyReport(HashMap <String,Object> valuesMap) throws BaseException;
    public void verifyReport(IRP_VERIFIED_REPORTSVO vo) throws BaseException;

    /**
     * Method for filling the report
     * @param jasperReport
     * @param parameters
     * @param con
     * @return
     * @throws BaseException
     */
    public JasperPrint fillJasperReport(JasperReport jasperReport, Map parameters, Connection con) throws BaseException;
    public void retrieveMetadataReport(IRP_AD_HOC_REPORTCO reportSession, Map parameters, String saveCopyLocation,
	    String menu, int decimalPts, String appName, String usrName, String language, String printerName,
	    BigDecimal dbConn, boolean noHeadAndFoot, String csvSepartor, BigDecimal fromPage, BigDecimal toPage,
	    String fromMenu, HashMap<String, String> transMsgLangMap, byte[] origReportBytes, String origRepExt,
	    int mainPageCnt, Connection con, ReportParamsCO repParamsCO, String origFormat, String mainReportName,
	    JasperPrint mainReportJasperPrint, JasperReport mainJasperReport) throws Exception;
    
    /**
     * return Report ByteArray related to CurrentPage number
     * @param paramsMap
     * @return
     * @throws BaseException
     */
    public HashMap<String,Object> returnReportByteArrayCurrentPage(HashMap<String,Object> paramsMap)throws BaseException;
}


