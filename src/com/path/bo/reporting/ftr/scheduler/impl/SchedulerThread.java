package com.path.bo.reporting.ftr.scheduler.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;

import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDatasetParameter;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JRPrintElement;
import net.sf.jasperreports.engine.JRPrintPage;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.FileHtmlResourceHandler;
import net.sf.jasperreports.engine.export.HtmlExporter;
import net.sf.jasperreports.engine.export.HtmlResourceHandler;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.MapHtmlResourceHandler;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRTemplatePrintFrame;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleCsvReportConfiguration;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.export.SimpleTextReportConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import org.apache.commons.io.FileUtils;

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.common.util.ReportUtils;
import com.path.bo.reporting.designer.ProceduresBO;
import com.path.bo.reporting.designer.SnapShotBO;
import com.path.bo.reporting.mailserver.MailServerConfigBO;
import com.path.dao.reporting.common.ReportDAO;
import com.path.dao.reporting.ftr.scheduler.SchedulerDAO;
import com.path.dbmaps.vo.CIFVO;
import com.path.dbmaps.vo.CIFVOKey;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOG_DETAILSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_RECEIVERSVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.SecurityUtils;
import com.path.lib.common.util.StringUtil;
import com.path.lib.log.Log;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.struts2.lib.common.BaseObject;
import com.path.vo.common.CommonLibSC;
import com.path.vo.common.email.MailCO;
import com.path.vo.common.email.MailServerCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGSC;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_LOGCO;
import com.path.vo.reporting.scheduler.IRP_REP_SCHED_MAIL_GROUP_BYCO;
import com.path.vo.reporting.scheduler.IRP_REP_SCHED_MAIL_GROUP_BYSC;
import com.path.vo.reporting.scheduler.IRP_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_SCHEDULESC;

public class SchedulerThread extends BaseBO implements Runnable
{
    private String format;
    private String reportName;
    private Map parameters;
    private String realPath;
    // private String fileName;
    private byte[] reportContent;
    ArrayList<ReportFormat> reportFormatsList = new ArrayList<ReportFormat>();
    private ReportDAO reportDAO;
    private SchedulerDAO schedulerDAO;
    private Date SCHEDULED_DATE;
    private BigDecimal SCHED_ID;
    private BigDecimal REPORT_ID;
    private String printerName;
    private ReportBO reportBO;
    private Map paramValMap;
    private String reportingPortalURL;
    private String language;
    private boolean showHeadFoot;
    private String csvSep;
    private ProceduresBO procBO;
    private BigDecimal saveDataType;
    private SnapShotBO snapShotBO;
    private CommonRepFuncBO commonRepFuncBO;
    private MailServerConfigBO mailServerConfigBO;
    private String userName;
    private IRP_REPORT_SCHEDULECO currentReport;
    private CommonLookupBO commonLookupBO;
    private String FTR_PROG_REF;
    private String reportingImageURL;
    private HashMap<BigDecimal,String> nextDateUpdatedMap;
    
    public String getReportingImageURL() throws Exception
    {
    	reportingImageURL=PathPropertyUtil.returnPathPropertyFromFile("PathRepRemoting", "app.REP.image.url");
    	if(StringUtil.nullToEmpty(reportingImageURL).isEmpty())
    	{
    		reportingImageURL=getReportingPortalURL();
    	}
    	else if(reportingImageURL.lastIndexOf("/")< reportingImageURL.length()-1)
		{
		    reportingImageURL=reportingImageURL+"/"  ;
		}
    	return reportingImageURL;
    }

    public void setReportingImageURL(String reportingImageURL)
    {
        this.reportingImageURL = reportingImageURL;
    }

    public String getFTR_PROG_REF()
    {
        return FTR_PROG_REF;
    }

    public void setFTR_PROG_REF(String fTRPROGREF)
    {
        FTR_PROG_REF = fTRPROGREF;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
        this.commonLookupBO = commonLookupBO;
    }

    public void setMailServerConfigBO(MailServerConfigBO mailServerConfigBO)
    {
	this.mailServerConfigBO = mailServerConfigBO;
    }

    public IRP_REPORT_SCHEDULECO getCurrentReport()
    {
	return currentReport;
    }

    public void setCurrentReport(IRP_REPORT_SCHEDULECO currentReport)
    {
	this.currentReport = currentReport;
    }

    public String getUserName()
    {
	return userName;
    }

    public void setUserName(String userName)
    {
	this.userName = userName;
    }

    public void setSnapShotBO(SnapShotBO snapShotBO)
    {
	this.snapShotBO = snapShotBO;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }

    public BigDecimal getSaveDataType()
    {
	return saveDataType;
    }

    public void setSaveDataType(BigDecimal saveDataType)
    {
	this.saveDataType = saveDataType;
    }

    public void setProcBO(ProceduresBO procBO)
    {
	this.procBO = procBO;
    }

    public boolean isShowHeadFoot()
    {
	return showHeadFoot;
    }

    public void setShowHeadFoot(boolean showHeadFoot)
    {
	this.showHeadFoot = showHeadFoot;
    }

    public String getCsvSep()
    {
	return csvSep;
    }

    public void setCsvSep(String csvSep)
    {
	this.csvSep = csvSep;
    }

    public String getLanguage()
    {
	return language;
    }

    public void setLanguage(String language)
    {
	this.language = language;
    }

    public String getReportingPortalURL()
    {
	if(reportingPortalURL.lastIndexOf("/")< reportingPortalURL.length()-1)
	{
	    reportingPortalURL=reportingPortalURL+"/"  ;
	}
	return reportingPortalURL;
    }

    public void setReportingPortalURL(String reportingPortalURL)
    {
	this.reportingPortalURL = reportingPortalURL;
    }

    public Map getParamValMap()
    {
	return paramValMap;
    }

    public void setParamValMap(Map paramValMap)
    {
	this.paramValMap = paramValMap;
    }

    public void setReportBO(ReportBO reportBO)
    {
	this.reportBO = reportBO;
    }

    public void setSCHEDULED_DATE(Date sCHEDULEDDATE)
    {
	SCHEDULED_DATE = sCHEDULEDDATE;
    }

    public void setSCHED_ID(BigDecimal sCHEDID)
    {
	SCHED_ID = sCHEDID;
    }

    public void setREPORT_ID(BigDecimal rEPORTID)
    {
	REPORT_ID = rEPORTID;
    }

    public void setSchedulerDAO(SchedulerDAO schedulerDAO)
    {
	this.schedulerDAO = schedulerDAO;
    }

    public byte[] getReportContent()
    {
	return reportContent;
    }

    public void setReportContent(byte[] reportContent)
    {
	this.reportContent = reportContent;
    }

    public ReportDAO getReportDAO()
    {
	return reportDAO;
    }

    public HashMap<BigDecimal, String> getNextDateUpdatedMap()
    {
        return nextDateUpdatedMap;
    }

    public void setNextDateUpdatedMap(HashMap<BigDecimal, String> nextDateUpdatedMap)
    {
        this.nextDateUpdatedMap = nextDateUpdatedMap;
    }


    public static class ReportFormat
    {
	private final  String description;
	private final  String code;

	public ReportFormat(String description, String code)
	{
	    this.description = description;
	    this.code = code;
	}

	public String getDescription()
	{
	    return description;
	}

	public String getCode()
	{
	    return code;
	}
    }

    public String getRealPath()
    {
	return realPath;
    }

    public void setRealPath(String realPath)
    {
	this.realPath = realPath;
    }

    public Map getParameters()
    {
	return parameters;
    }

    public void setParameters(Map parameters)
    {
	this.parameters = parameters;
    }

    public String getFormat()
    {
	return format;
    }

    public void setFormat(String format)
    {
	this.format = format;
    }

    public String getReportName()
    {
	return reportName;
    }

    public void setReportName(String reportName)
    {
	this.reportName = reportName;
    }

    public ArrayList<ReportFormat> getReportFormatsList()
    {
	return reportFormatsList;
    }

    public void setReportFormatsList(ArrayList<ReportFormat> reportFormatsList)
    {
	this.reportFormatsList = reportFormatsList;
    }

    public void setReportDAO(ReportDAO reportDAO)
    {
	this.reportDAO = reportDAO;
    }

    public String getPrinterName()
    {
	return printerName;
    }

    public void setPrinterName(String printerName)
    {
	this.printerName = printerName;
    }

 /*   public void createThread()
    {

    }*/

    private void generateSubReports(List<IRP_SUB_REPORTCO> subreportsList, List<IRP_AD_HOC_REPORTCO> subReportCOList,
	    Connection con) throws BaseException, Exception
    {

	String subRepExpr;
	IRP_AD_HOC_REPORTCO subReport = new IRP_AD_HOC_REPORTCO();
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);

	for(IRP_SUB_REPORTCO subrep : subreportsList)
	{
	    subRepExpr = subrep.getIrpSubReportVO().getSUB_REPORT_EXPRESSION();
	    
	    boolean connectFail = true;
		while (connectFail)
		{	
			try {
			    	subReport = reportBO.returnReportById(subrep.getIrpSubReportVO().getREPORT_ID());
				connectFail= false;
			}
			catch (Exception e)
			{
				connectFail = sqlConnectionFailed(e,"System returnReportById retrieval exception");
			
			}
		}
	    // create subreport jrxml file under repository
	    createJRXMLFile(subReport.getJRXML_FILE(), repositoryPath + "/" + subRepExpr);

	    // compile subreport .jrxml file into .jasper
	    try
	    {
		compileReport(repositoryPath + "/" + subRepExpr, subrep,format);
	    }
	    catch(Exception e)
	    {
		log.error(e, "Error in compileReport() method in ReportBOImpl.java");
		throw new BOException("report.compile.exceptionMsg._key",e);
	    }

	    // call subreport Before procedures
	    
	    connectFail = true;
		while (connectFail)
		{	
			try {
			    	reportBO.callProcedures(BigDecimal.ZERO, paramValMap, subReport, null, con);
				connectFail= false;
			}
			catch (Exception e)
			{
				connectFail = sqlConnectionFailed(e,"System call Procedures retrieval exception");

			}
		}
	    //

	    // add the subreport to a list
	    subReportCOList.add(subReport);

	    if(subReport.getSubreportsList() != null && subReport.getSubreportsList().size() > 0)
	    {
		generateSubReports(subReport.getSubreportsList(), subReportCOList, con);
	    }
	}

    }
    
    public void run()
    {
	try
	{
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    Date dt;
	    Date dbDate = null;
	    boolean connectFail = true;
		while (connectFail)
		{	
			try {
				dbDate = commonLibBO.returnSystemDateWithTime();
				connectFail= false;
			}
			catch (Exception e)
			{ 	
				connectFail = sqlConnectionFailed(e,"System date retrieval exception");
					
			}
		}
	    ReportParamsCO repParamsCO = (ReportParamsCO) parameters.get("repParamsCO");
	    paramValMap.put("repParamsCO", repParamsCO);
	    repParamsCO.setNoData(false);
	    if("0".equals(currentReport.getDATE_TYPE())) // get DB date
	    {
		dt = dbDate;
	    }
	    else
	    // get DB system date
	    {
		dt = currentReport.getNEXT_RUN_DATE();
	    }

	    // set the name of the file used to export the report into
	    String strtExecDate = DateUtil.format(dt, "yyyy-MM-dd HH-mm-ss aa");
	    String fileName = currentReport.getPROG_REF() + "_" + currentReport.getREPORT_APP_NAME() + "_"
		    + repParamsCO.getUserName().replace(".", "") + "_" + strtExecDate;

	    IRP_REPORT_SCHED_LOGVO vo = new IRP_REPORT_SCHED_LOGVO();
	    IRP_REPORT_SCHED_LOGCO logCo = new IRP_REPORT_SCHED_LOGCO();
	    Connection con = null;
	    IRP_AD_HOC_REPORTCO reportCO =null;
	    try
	    {
		// add record to the log table saying that the schedule is
		// started
		vo.setREPORT_ID(REPORT_ID);
		vo.setSCHED_ID(SCHED_ID);
		vo.setREMARKS("");
		vo.setSTART_DATE(dbDate);
		vo.setSCHEDULED_DATE(SCHEDULED_DATE);
		vo.setSTATUS("r");
		vo.setSAVE_DATA_TYPE(saveDataType);
		
		logCo.setREPORT_ID(REPORT_ID);
		logCo.setSCHED_ID(SCHED_ID);
		logCo.setREMARKS("");
		logCo.setSTART_DATE(dbDate);
		logCo.setSCHEDULED_DATE(SCHEDULED_DATE);
		logCo.setSTATUS("r");
		logCo.setSAVE_DATA_TYPE(saveDataType);
		logCo.setREPORT_REF(currentReport.getREPORT_REF());
		// schedulerDAO.deleteLOG(vo);
	    connectFail = true;
		while (connectFail)
		{	
			try {
				schedulerDAO.insertLOG(logCo);
				connectFail= false;
			}
			catch (Exception e)
			{
				connectFail = sqlConnectionFailed(e,"Log insertion exception");				
			}
		}
		
		BigDecimal dbConn = currentReport.getCONN_ID();
		if(dbConn!=null && dbConn.compareTo(BigDecimal.ZERO) > 0)
		{
		    IRP_CONNECTIONSVO conVO = null;
		    connectFail = true;
			while (connectFail)
			{	
				try {
					conVO = commonLookupBO.returnConnById(dbConn);
				    connectFail= false;
				}
				
				catch (Exception e)
					{
					connectFail = sqlConnectionFailed(e,"Connection return exception");
					
				}
			}
			
		    
		    if(conVO != null)
		    {
			String decodedDbPass = SecurityUtils.decodeB64(conVO.getDB_PASS());
			Class.forName(conVO.getDBMS());
			con = DriverManager.getConnection(conVO.getURL(), conVO.getUSER_ID(), decodedDbPass);
		    }
		    
		    connectFail= true;
		    while (connectFail)
			{	
				try {
				    repParamsCO.setConnCO(commonLookupBO.returnConnection(dbConn));
				    connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"Connection return exception");			

				}
			}
		}
		else
		{
			connectFail= true;
		    while (connectFail)
			{	
				try {
				    con = reportDAO.getConnection();
				    connectFail= false;
				}
				catch (Exception e)
					{
					connectFail = sqlConnectionFailed(e,"Connection return exception");   	    	
					
				}
			}
		}

		File reportFile = null;
		String reportPath = repositoryPath + "/JrxmlFile";

		// create jrxml file under repository
		createJRXMLFile(reportContent, reportPath);

		// return the report and its content

		connectFail = true;
		while (connectFail)
		{	
			try {
				reportCO = reportBO.returnReportById(REPORT_ID);
				connectFail= false;
			}
			catch (Exception e)
				{
				connectFail = sqlConnectionFailed(e,"System Report ID retrieval exception");

			}
		}
		 
		String repFormat =format;	
		if(ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(format)
			|| ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equals(format)
			|| ConstantsCommon.SQL_REP_FORMAT.equals(format))
		{
		    createDynamicRowDataJrxml(reportPath, reportCO);
		    if(ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equals(format))
		    {
			repFormat = ConstantsCommon.XLS_REP_FORMAT;
		    }
		    else if(ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(format))
		    {
			repFormat = ConstantsCommon.CSV_REP_FORMAT;
		    }
		}

		// compile .jrxml file into .jasper
	
			try {
			    reportFile = compileReport(reportPath, null,repFormat);
			  
			}
			catch (Exception e)
				{
				   	 
				   	    log.error(e, "Error in compileReport() method in SchedulerThread.java");
					    throw new BOException("report.compile.exceptionMsg._key",e);				
			}
		
	
		//
		con.setAutoCommit(false);
		// create hash Table for procedures for Sybase
		int IsSybase = 0;
		connectFail = true;
			while (connectFail)
			{	
				try {
				    	IsSybase = commonLibBO.returnIsSybase();
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System Is Sybase retrieval exception");
				}
			}
		if( IsSybase == 1 && reportCO.getHashTblList().size() > 0)
		{
		    try
		    {
			connectFail = true;
			while (connectFail)
			{	
				try {
				    	procBO.createHashTbl(con, reportCO.getHashTblList());
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System Create Hash Table retrieval exception");					
				}
			}
			
		    }
		    catch(Exception e)
		    {
			throw new BaseException("Error occured when creating table hash: "+e.getMessage(),e);
		    }
		}

		// call procedures before
		connectFail = true;
		while (connectFail)
		{	
			try {
			    	reportBO.callProcedures(BigDecimal.ZERO, paramValMap, reportCO, null, con);
				connectFail= false;
			}
			catch (Exception e)
			{	
				connectFail = sqlConnectionFailed(e,"System call Procedures retrieval exception");
				
			}
		}
		
		String origFormat =format;
		format=repFormat;
		List<IRP_AD_HOC_REPORTCO> subReportCOList = new ArrayList<IRP_AD_HOC_REPORTCO>();

		if(reportCO.getSubreportsList() != null && reportCO.getSubreportsList().size() > 0)
		{
		    generateSubReports(reportCO.getSubreportsList(),subReportCOList,con);
		    parameters.put("SUBREPORT_DIR", repositoryPath + "/");
		}

		JasperPrint jasperPrint = null;

		// load up the report
		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);

		//Retry to reconnect and retrieve the report in case the connection to the database fails 
	    connectFail = true;
	    long startTime = Calendar.getInstance().getTimeInMillis();
	    String repoLogFileDir = ReportingFileUtil.getReportingRepoPath(
		    ReportingConstantsCommon.repositoryFolder) +"/"+ RepConstantsCommon.REPORT_LOGGING_DIR;
	    long executionTime;
		while (connectFail)
		{	try 
			{
		    	//tracing for the executed report
			BaseObject baseObj=new BaseObject();
			baseObj.applyTraceProps(ConstantsCommon.REP_APP_NAME,userName,currentReport.getPROG_REF(),repParamsCO.getHttpSessionIdForLink());
			genericDAO.callSqlSessionTrace(baseObj, "sch r:"+currentReport.getPROG_REF()+" ra:"+currentReport.getREPORT_APP_NAME(), con);
			/*
			 * Added the call to jasper method in reportBO since
			 * it's causing slowness issue when used in BOImpl class
			 * as it is here in scheduler thread
			 */
			    // start modif logging to repLogs.log
			    File curFile = new PathFileSecure(repoLogFileDir +"/"+ RepConstantsCommon.REPORT_LOGGING_FILE);
			    if(BigDecimal.ONE.toPlainString().equals(
				    PathPropertyUtil.getPathRemotingProp("PathRepRemoting", "reporting.reportExec.log")))
			    {
				
				StringBuffer repExecLogContent = new StringBuffer("\n\n\n[DEBUG]: USER NAME: "
					+ repParamsCO.getUserName() + ", COMPANY CODE: " + repParamsCO.getCompanyCode()
					+ ", BRANCH CODE: " + repParamsCO.getBranchCode() + " , Report APP NAME: "
					+ repParamsCO.getRepAppName() + " , APP NAME: " + repParamsCO.getCurrentAppName()
					+ " ,PROG REF: " + repParamsCO.getProgRef() + " ,ARGUMENTS: " + parameters);
				FileUtil.makeDirectories(repoLogFileDir);
				int sizeLimit = Integer.valueOf(
					PathPropertyUtil.getPathRemotingProp("PathRepRemoting", "reporting.reportExec.size"))
					.intValue();
				if(curFile.exists() && ((curFile.length() / 1000000) >= sizeLimit))
				{
				    curFile.delete();
				}
				FileUtil.writeFile(repoLogFileDir +"/"+ RepConstantsCommon.REPORT_LOGGING_FILE,
					repExecLogContent.toString().getBytes(), false);
			    }
			    else if(curFile.exists())
			    {
				curFile.delete();
			    }
			    jasperPrint = reportBO.fillJasperReport(jasperReport, parameters, con);
			    executionTime = (Calendar.getInstance().getTimeInMillis() - startTime);
			    if(BigDecimal.ONE.toPlainString().equals(
				    PathPropertyUtil.getPathRemotingProp("PathRepRemoting", "reporting.reportExec.log")))
			    {
				FileUtil.writeFile(repoLogFileDir + "/" + RepConstantsCommon.REPORT_LOGGING_FILE,
					("\n-------------------------------------------------------------------------------"
						+ executionTime + "ms").getBytes(),
					false);
			    }
			    // end modif log
			connectFail = false;
			}
			catch (Exception e)
			{
				connectFail = sqlConnectionFailed(e,"Filling Report Exception");

			}
		    
		}
	
		// retrieve the file name if it parametrized
		//HashMap<String, String> propsMap = new HashMap<String, String>();
		boolean isRsEmpty =!((Boolean)parameters.get(RepConstantsCommon.REP_HAS_DATA)).booleanValue();
		HashMap<String, String> propsMap = retJasperPropsVal(jasperPrint);
		if((BigDecimal.ONE.equals(saveDataType) || new BigDecimal(2).equals(saveDataType))
			&& !"0".equals(currentReport.getATTACH_FILE_NAME()))
		{
		    if(propsMap.get("my.attachFileFe") == null)
		    {
			fileName = "WrongFileName";
		    }
		    else
		    {
			fileName = propsMap.get("my.attachFileFe");
		    }
		}
		
		// get the groupping fields from the DB
		IRP_REP_SCHED_MAIL_GROUP_BYSC grpSc = new IRP_REP_SCHED_MAIL_GROUP_BYSC();
		grpSc.setREPORT_ID(REPORT_ID);
		grpSc.setSCHED_ID(SCHED_ID);
		List<IRP_REP_SCHED_MAIL_GROUP_BYCO> grpLst = null;
		connectFail = true;
		while(connectFail)
		{
		    try
		    {
			grpLst = schedulerDAO.retMailFeGroupByList(grpSc);
			connectFail = false;
		    }
		    catch(Exception e)
		    {
			connectFail = sqlConnectionFailed(e, "groupping fields exception");
		    }
		}
		

		JRAbstractExporter exporter = null;
		byte[] reportBytes = null;
		HashMap<String,Object> sendMailRetMap;
		String repExt = "";
		ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
		String mailSentMsg = "";
		StringBuffer sitcomBuffer = new StringBuffer("");
		if(format.equalsIgnoreCase(ConstantsCommon.SQL_REP_FORMAT))
		{
		    repExt = ConstantsCommon.SQL_EXT;
		    // create the sql statments;
		    LinkedHashMap jasDesMap = reportCO.getJasperDesignFieldsMap();
		    StringBuffer sbVal = new StringBuffer("");
		    StringBuffer sbInsert = new StringBuffer("");
		    if(!jasDesMap.isEmpty())
		    {
			sbInsert.append("INSERT INTO " + fileName + "(");
			Iterator it = jasDesMap.keySet().iterator();
			while(it.hasNext())
			{
			    sbInsert.append((String) it.next() + ",");
			}

			sbInsert = new StringBuffer(sbInsert.substring(0, sbInsert.length() - 1));

			sbInsert.append(")");
		    }

		    // save under repository
		    if(new BigDecimal(2).equals(saveDataType) && grpLst.isEmpty())
		    {
			sbVal = createResultSql(jasperPrint, sbInsert, jasDesMap);
			FileUtil.writeFile(repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName + "." + repExt, sbVal
				.toString());
			if(!NumberUtil.isEmptyDecimal(reportCO.getMETADATA_REPORT_ID()))
			{
			    reportBytes = sbVal.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
			}
		    }
		    else if(BigDecimal.ONE.equals(saveDataType))
		    {
			sbVal = createResultSql(jasperPrint, sbInsert, jasDesMap);
			reportBytes = sbVal.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
		    }
		    else if((new BigDecimal(2).equals(saveDataType) && !grpLst.isEmpty()) ||(new BigDecimal(3).equals(saveDataType)))
		    {
			sendMailRetMap = sendSchedMail(jasperPrint, exporter, byteArray, reportBytes, repositoryPath,
				repExt, vo, fileName, sbInsert, jasDesMap);
			mailSentMsg = sendMailRetMap.get("errorStr")!=null?(String) sendMailRetMap.get("errorStr"):null;
			reportBytes = sendMailRetMap.get("reportBytes")!=null?(byte[]) sendMailRetMap.get("reportBytes"):null;
		    }
		}
		else if(format.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT) )
		{
		    repExt = ConstantsCommon.PDF_EXT;
		    // save under repository
		    if(new BigDecimal(2).equals(saveDataType) && grpLst.isEmpty())
		    {
			JasperExportManager.exportReportToPdfFile(jasperPrint, repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/"
				+ fileName + "." + repExt);
			if(!NumberUtil.isEmptyDecimal(reportCO.getMETADATA_REPORT_ID()))
			{
			    reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
			}
		    }
		    // save under the DB
		    else if(BigDecimal.ONE.equals(saveDataType))
		    {
			reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		    }
		    // sending mail
		    else if((new BigDecimal(2).equals(saveDataType) && !grpLst.isEmpty()) || (new BigDecimal(3).equals(saveDataType)))
		    {
			
			exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			if((isRsEmpty && "0".equals(currentReport.getSEND_IF_NO_DATA_YN())) || (!isRsEmpty))
			{
			    log.info(">>>>>>>>>>>>>>before sendSchedMail line 953");
			    sendMailRetMap = sendSchedMail(jasperPrint, exporter, byteArray, reportBytes, repositoryPath,
				    repExt, vo, fileName, new StringBuffer(""), new LinkedHashMap<String, String>());
			    log.info(">>>>>>>>>>>>>>after sendSchedMail line 956");
			    mailSentMsg = sendMailRetMap.get("errorStr")!=null?(String) sendMailRetMap.get("errorStr"):null;
			    reportBytes = sendMailRetMap.get("reportBytes")!=null?(byte[]) sendMailRetMap.get("reportBytes"):null;
			}
		    }
		}
		else if(format.equalsIgnoreCase(ConstantsCommon.DOC_REP_FORMAT))
		{
		    repExt = ConstantsCommon.DOC_EXT;
		    exporter = new JRDocxExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		    SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
		    configuration.setFlexibleRowHeight(true);
//		    exporter.setParameter(JRDocxExporterParameter.CHARACTER_ENCODING, CommonUtil.ENCODING);
		    exporter.setConfiguration(configuration);
		    // save under repository
		    if(new BigDecimal(2).equals(saveDataType) && grpLst.isEmpty())
		    {
			 exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(repositoryPath
					+ "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName + "." + repExt));
			exporter.exportReport();
			if(!NumberUtil.isEmptyDecimal(reportCO.getMETADATA_REPORT_ID()))
			{
			    exporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, byteArray);
			    exporter.exportReport();
			    reportBytes = byteArray.toByteArray();
			}
		    }
		    // save under the DB
		    else if(BigDecimal.ONE.equals(saveDataType))
		    {
			 exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
			exporter.exportReport();
			reportBytes = byteArray.toByteArray();
		    }
		    // sending mail
		    else if((new BigDecimal(2).equals(saveDataType) && !grpLst.isEmpty())||(new BigDecimal(3).equals(saveDataType)   && ((isRsEmpty && "0".equals(currentReport.getSEND_IF_NO_DATA_YN())) || (!isRsEmpty)) ))
		    {
			sendMailRetMap = sendSchedMail(jasperPrint, exporter, byteArray, reportBytes, repositoryPath,
				repExt, vo, fileName, new StringBuffer(""), new LinkedHashMap<String, String>());
			    mailSentMsg = sendMailRetMap.get("errorStr")!=null?(String) sendMailRetMap.get("errorStr"):null;
			    reportBytes = sendMailRetMap.get("reportBytes")!=null?(byte[]) sendMailRetMap.get("reportBytes"):null;

		    }
		}
		else if(format.equalsIgnoreCase(ConstantsCommon.XLS_REP_FORMAT) || format.equalsIgnoreCase(ConstantsCommon.ODS_REP_FORMAT))
		{
			if(format.equalsIgnoreCase(ConstantsCommon.XLS_REP_FORMAT))
			{
				 repExt = ConstantsCommon.XLSX_EXT;
				    
				    exporter = new JRXlsxExporter();
					exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
					SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
					configuration.setOnePagePerSheet(false);
					configuration.setDetectCellType(true);
					configuration.setWhitePageBackground(false);
					configuration.setRemoveEmptySpaceBetweenRows(true);
					configuration.setCollapseRowSpan(true);
					configuration.setRemoveEmptySpaceBetweenColumns(true);
					exporter.setConfiguration(configuration);
			}
			else
			{
				repExt = ConstantsCommon.ODS_REP_FORMAT.toLowerCase();
				exporter = new JROdsExporter();
	            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
			}
		   
			    // save under repository
		    if(new BigDecimal(2).equals(saveDataType) && grpLst.isEmpty())
		    {
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(repositoryPath
				+ "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName + "." + repExt));
			exporter.exportReport();
			if(!NumberUtil.isEmptyDecimal(reportCO.getMETADATA_REPORT_ID()))
			{
			    exporter = new JRXlsxExporter();
			    exporter.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
			    exporter.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
			    exporter.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
			    exporter.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
			    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
			    exporter.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
			    exporter.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
			    exporter.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArray);
			    exporter.exportReport();
			    reportBytes = byteArray.toByteArray();
			}
		    }
		    // save under the DB
		    else if(BigDecimal.ONE.equals(saveDataType))
		    {
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
			exporter.exportReport();
			reportBytes = byteArray.toByteArray();
		    }
		    // sending mail
		    else if((new BigDecimal(2).equals(saveDataType) && !grpLst.isEmpty()) || (new BigDecimal(3).equals(saveDataType)  && ((isRsEmpty && "0".equals(currentReport.getSEND_IF_NO_DATA_YN())) || (!isRsEmpty)) ))
		    {
			sendMailRetMap = sendSchedMail(jasperPrint, exporter, byteArray, reportBytes, repositoryPath,
			repExt, vo, fileName, new StringBuffer(""), new LinkedHashMap<String, String>());
			mailSentMsg = sendMailRetMap.get("errorStr")!=null?(String) sendMailRetMap.get("errorStr"):null;
			reportBytes = sendMailRetMap.get("reportBytes")!=null?(byte[]) sendMailRetMap.get("reportBytes"):null;
		    }
		    // central bank
		    if(reportCO.getREPORT_TYPE() != null && reportCO.getREPORT_TYPE().equals(RepConstantsCommon.ONE))
		    {
			commonRepFuncBO.writeFileToRepository(reportCO.getPROG_REF(), reportBytes,reportCO,!showHeadFoot,parameters,con,userName,origFormat);
		    }
		    // fcr dynamic
		    else if(ConstantsCommon.FCR_MAIN_REPORT_REF.equals(reportCO.getPROG_REF()))
		    {
			commonRepFuncBO.writeFileToRepository(FTR_PROG_REF, reportBytes,reportCO,!showHeadFoot,parameters,con,userName,origFormat);
		    }
		}
		else if(format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT))
		{
			if(fileName.indexOf("/")>=0 && new BigDecimal(2).equals(saveDataType) && !grpLst.isEmpty())
		    {
				fileName=fileName.substring(fileName.lastIndexOf("/")+1);
		    }
			
		    String repFolderName = RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName;
		    String imgFolderName = RepConstantsCommon.IMAGES_LOCATION;
		    String imgDir = repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName + "/" + imgFolderName;
		    // create the directory where the generated report will be saved
		    repExt = ConstantsCommon.HTML_EXT;
		    if((!new BigDecimal(2).equals(saveDataType) && !new BigDecimal(3).equals(saveDataType) ) || 
		    		(new BigDecimal(2).equals(saveDataType) && grpLst.isEmpty()))
		    {
		    	 File imgDirFile = new PathFileSecure(imgDir);
				    if(!imgDirFile.exists())
				    {
					boolean res = imgDirFile.mkdirs();
					if(!res)
					{
					    Log.getInstance().warning("Directory " + imgDirFile + " creation failed");
					}
				    }
		   
		    exporter = new HtmlExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));

		    SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(repositoryPath + "/" + repFolderName
			    + "/" + fileName + "." + repExt);
		    HtmlResourceHandler fileHdl = new FileHtmlResourceHandler(imgDirFile, imgFolderName + "/" + "{0}");
		    fileHdl = new MapHtmlResourceHandler(fileHdl, new HashMap());
		    output.setImageHandler(fileHdl);
		    exporter.setExporterOutput(output);
		    if(ConstantsCommon.LANGUAGE_ARABIC.equalsIgnoreCase(language) && "0".equals(currentReport.getLANG_INDEPENDENT_YN()))
		    {
			 SimpleHtmlExporterConfiguration htmlExpConf = new SimpleHtmlExporterConfiguration();
			 htmlExpConf.setHtmlFooter("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><style>body {direction:rtl}</style>");
			 htmlExpConf.setBetweenPagesHtml("<P STYLE='page-break-before:always'>");
			 exporter.setConfiguration(htmlExpConf);
		    }
		    }
		    // save under repository
		    if(new BigDecimal(2).equals(saveDataType) && grpLst.isEmpty())
		    {
			exporter.exportReport();

			// check if images are created then zip the folder else move the html file and delete the folder
			if((new PathFileSecure(imgDir)).list().length == 0)
			{
			    String srcFile = repositoryPath + "/" + repFolderName + "/" + fileName
			    	+ "." + repExt;
			    String destDir = repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION;
			    FileUtils.copyFileToDirectory(new PathFileSecure(srcFile), new PathFileSecure(destDir));
			}
			else
			{
			    byte[] zipBytes = FileUtil.generateZipFromFldr(repositoryPath + "/" + repFolderName);
			    FileUtil.saveToRepository(zipBytes, ReportingConstantsCommon.repositoryFolder, "/" + ReportingConstantsCommon.reportingFolder + "/" + repFolderName + ".zip");
			}
			if(!NumberUtil.isEmptyDecimal(reportCO.getMETADATA_REPORT_ID()) && reportBytes==null)
			{
			    reportBytes = FileUtil.readFileBytes(repositoryPath + "/" + repFolderName + "/" + fileName
				    + "." + repExt);
			}
		    }
		    // save under the DB
		    else if(BigDecimal.ONE.equals(saveDataType))
		    {
			exporter.exportReport();

			// check if images are created then zip the folder and convert it to bytes
			if((new PathFileSecure(imgDir)).list().length > 0)
			{
			    // zip the folder and convert it to byte[]
			    reportBytes = FileUtil.generateZipFromFldr(repositoryPath + "/" + repFolderName);
			}
			else
			{
			    reportBytes = FileUtil.readFileBytes(repositoryPath + "/" + repFolderName + "/" + fileName
				    + "." + repExt);
			}
		    }
		    // sending mail
		    else if((new BigDecimal(2).equals(saveDataType) && !grpLst.isEmpty()) || (new BigDecimal(3).equals(saveDataType)  && ((isRsEmpty && "0".equals(currentReport.getSEND_IF_NO_DATA_YN())) || (!isRsEmpty)) ))
		    {
			sendMailRetMap = sendSchedMail(jasperPrint, exporter, byteArray, reportBytes, repositoryPath,
			repExt, vo, fileName, new StringBuffer(""), new LinkedHashMap<String, String>());
			mailSentMsg = sendMailRetMap.get("errorStr")!=null?(String) sendMailRetMap.get("errorStr"):null;
			reportBytes = sendMailRetMap.get("reportBytes")!=null?(byte[]) sendMailRetMap.get("reportBytes"):null;
		    }
		    // delete the temporarily created report folder
		    FileUtil.deleteFolder(repositoryPath + "/" + repFolderName);
		}
		else if(format.equalsIgnoreCase(ConstantsCommon.CSV_REP_FORMAT) || format.equalsIgnoreCase(ConstantsCommon.CSV_EXT_REP_FORMAT))
		{
		    if(format.equalsIgnoreCase(ConstantsCommon.CSV_EXT_REP_FORMAT))
		    {
			repExt = ConstantsCommon.CSV_REP_FORMAT.toLowerCase();
		    }
		    else
		    {
			repExt = ConstantsCommon.TXT_EXT;
		    }
		    exporter = new JRCsvExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		    SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
		    if("\\t".equals(csvSep))
		    {
			 configuration.setFieldDelimiter("\t");
		    }
		    else
		    {
			 configuration.setFieldDelimiter(csvSep);
		    }
		   
		    exporter.setConfiguration(configuration);
		    // save under repository
		    if(new BigDecimal(2).equals(saveDataType) && grpLst.isEmpty())
		    {
			exporter.setExporterOutput(new SimpleWriterExporterOutput(repositoryPath
					+ "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName + "." + repExt));
			exporter.exportReport();
			if(!NumberUtil.isEmptyDecimal(reportCO.getMETADATA_REPORT_ID()))
			{
			    exporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, byteArray);
			    exporter.exportReport();
			    reportBytes = byteArray.toByteArray();
			}
		    }
		    // save under the DB
		    else if(BigDecimal.ONE.equals(saveDataType))
		    {
			exporter.setExporterOutput(new SimpleWriterExporterOutput(byteArray));
			exporter.exportReport();
			reportBytes = byteArray.toByteArray();
		    }
		    // sending mail
		    else if((new BigDecimal(2).equals(saveDataType) && !grpLst.isEmpty()) || (new BigDecimal(3).equals(saveDataType) && ((isRsEmpty && "0".equals(currentReport.getSEND_IF_NO_DATA_YN())) || (!isRsEmpty)) ))
		    {
			sendMailRetMap = sendSchedMail(jasperPrint, exporter, byteArray, reportBytes, repositoryPath,
			repExt, vo, fileName, new StringBuffer(""), new LinkedHashMap<String, String>());
			mailSentMsg = sendMailRetMap.get("errorStr")!=null?(String) sendMailRetMap.get("errorStr"):null;
			reportBytes = sendMailRetMap.get("reportBytes")!=null?(byte[]) sendMailRetMap.get("reportBytes"):null;
		    }

		}
		else if(ConstantsCommon.TXT_FILE.equals(format) || ConstantsCommon.DAT_EXT.equals(format))
		{
		    repExt = format;
		    parameters.put(RepConstantsCommon.SITCOM_IS_NEW, RepConstantsCommon.Y);
		    parameters.put(RepConstantsCommon.SITCOM_PATH_CNT, Integer.valueOf(-1));
		    parameters.put(RepConstantsCommon.JASPER_PATH_ARR, new ArrayList<HashMap<String, Object>>());
		    BigDecimal repSnpId;
		    try
		    {
		     repSnpId = new BigDecimal(
			    commonRepFuncBO.retMinRepSnapshotRepId(StringUtil.isNotEmpty(reportCO.getFTR_OPT_PROG_REF())
				    ? reportCO.getFTR_OPT_PROG_REF() : reportCO.getPROG_REF()));
		    }
		    catch (Exception e)
		    {
			throw new BOException("No formula defined for the main report : " + reportCO.getPROG_REF());
		    }
		    REP_SNAPSHOT_PARAMVO snpParVO = new REP_SNAPSHOT_PARAMVO();
		    snpParVO.setREP_ID(repSnpId);
		    String snpFrequency = ((REP_SNAPSHOT_PARAMVO) genericDAO.selectByPK(snpParVO))
			    .getSNAPSHOT_FREQUENCY();
		    Date retrieveDte = new Date();
		    sitcomBuffer = commonRepFuncBO.generateSitcom(BigDecimal.ONE, ConstantsCommon.REP_APP_NAME,
			    repSnpId, reportCO.getPROG_REF(), repParamsCO.getCompanyCode(), retrieveDte, snpFrequency,
			    parameters, null);
		    parameters.remove(RepConstantsCommon.SITCOM_IS_NEW);
		    parameters.remove(RepConstantsCommon.SITCOM_PATH_CNT);
		    parameters.remove(RepConstantsCommon.JASPER_PATH_ARR);
		    reportBytes = sitcomBuffer.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
		    // sending mail
		    if((new BigDecimal(2).equals(saveDataType) && !grpLst.isEmpty()) || (new BigDecimal(3)
			    .equals(saveDataType)
			    && ((isRsEmpty && "0".equals(currentReport.getSEND_IF_NO_DATA_YN())) || (!isRsEmpty))))
		    {
			exporter = new JRTextExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			SimpleTextReportConfiguration textReportConfiguration = new SimpleTextReportConfiguration();
			textReportConfiguration.setCharHeight(new Float(5));
			textReportConfiguration.setCharWidth(new Float(5));
			textReportConfiguration.setOverrideHints(false);
			exporter.setConfiguration(textReportConfiguration);
			sendMailRetMap = sendSchedMail(jasperPrint, exporter, byteArray, reportBytes, repositoryPath,
				repExt, vo, fileName, new StringBuffer(""), new LinkedHashMap<String, String>());
			mailSentMsg = sendMailRetMap.get("errorStr") != null ? (String) sendMailRetMap.get("errorStr")
				: null;
		    }
		}
		if(!NumberUtil.isEmptyDecimal(reportCO.getMETADATA_REPORT_ID()))
		{
		    HashMap<String, String> transMsgLangMap = new HashMap<String, String>();
		    transMsgLangMap.put("1", "Error when calling procedures:");
		    transMsgLangMap.put("2", " for report reference: ");
		    transMsgLangMap.put("3", " and report application: ");
		    reportBO.retrieveMetadataReport(reportCO, parameters, "", reportCO.getPROG_REF(), 0,
			    ConstantsCommon.REP_APP_NAME, userName, origFormat, StringUtil.nullToEmpty(printerName),dbConn==null?BigDecimal.ZERO:dbConn, showHeadFoot,
			    csvSep, BigDecimal.ZERO, BigDecimal.ZERO, "false", transMsgLangMap, reportBytes, repExt,
			    jasperPrint.getPages().size(),con,repParamsCO,origFormat,fileName,jasperPrint,jasperReport);
		}
		log.info(">>>>>>>>>>>>>>before condition line 1279");
		if(new BigDecimal(3).equals(saveDataType) && !"".equals(mailSentMsg))
		{
		    log.info(">>>>>>>>>>>>>>in condition line 1282");
		    // failed:not consolidated and at least one mail is failed
		    // to be sent
		    if("0".equals(currentReport.getEMAIL_CONSOLIDATED_YN()) && "f".equals(mailSentMsg))
		    {
			log.info(">>>>>>>>>>>>>>in condition line 1287");
			vo.setREMARKS("");
			vo.setSTATUS("f");
		    }
		    else if(mailSentMsg.indexOf(":") == -1)
		    {
			log.info(">>>>>>>>>>>>>>in condition line 1293");
			vo.setREMARKS(mailSentMsg);
			vo.setSTATUS("p");// suspected
		    }
		    else
		    {
			log.info(">>>>>>>>>>>>>>in condition line 1299");
			mailSentMsg = mailSentMsg.substring(mailSentMsg.lastIndexOf(":"), mailSentMsg.length());
			vo.setREMARKS(mailSentMsg);
			vo.setSTATUS("p");// suspected
		    }
		    
		    connectFail=true;
		    while(connectFail){
		    	 try
				    {
		    	     		log.info(">>>>>>>>>>>>>>before schedulerDAO.updateLOG(vo); line 1309");
					schedulerDAO.updateLOG(vo);
					log.info(">>>>>>>>>>>>>>after schedulerDAO.updateLOG(vo); line 1311");
					connectFail=false;
				    }
    		    	catch (Exception e)
    			{
    		    		connectFail = sqlConnectionFailed(e,"Error in SchedulerThread.");    				
    			}				   
				   
		    }
		   
		}
		else
		{
		    // print report on printer
		    if( ((isRsEmpty && "0".equals(currentReport.getPRINT_IF_NO_DATA_YN())) || (!isRsEmpty)) && (printerName != null && !printerName.equals("")) )
		    {
			    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
			    printServiceAttributeSet.add(new PrinterName(printerName, null));
			    JRPrintServiceExporter printExporter = new JRPrintServiceExporter();
			    printExporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			    SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
			    configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
			    printExporter.setConfiguration(configuration);
			    printExporter.exportReport();
		    }

		    //

		    vo.setSTATUS("s");
		    Date endDate = null;
		    connectFail = true;
			while (connectFail)
			{	
				try {
				    	endDate = commonLibBO.returnSystemDateWithTime();
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System end date retrieval exception");					
				}
			}
		    vo.setEND_DATE(endDate);

		    /*
		     * check if the report will be saved under repositiory then
		     * add record to the table irp_snapshots and save the
		     * referenced snapshot_id in the table irp_report_sched_log
		     */
		    // save unde the DB
		    if(BigDecimal.ONE.equals(saveDataType) || new BigDecimal(2).equals(saveDataType))
		    {
			// Date executionDate =
			// Calendar.getInstance().getTime();
			// String startExecution =
			// DateUtil.format(executionDate,
			// "dd-MM-yyyy hh-mm-ss aaa");
			IRP_SNAPSHOTSCO snpCO = new IRP_SNAPSHOTSCO();
			snpCO.setREPORT_FORMAT(repExt);
			snpCO.setEXECUTION_DATE(dbDate);
			snpCO.setREPORT_NAME(reportName);
			snpCO.setEXECUTED_BY(userName);

			snpCO.setCOMP_CODE(repParamsCO.getCompanyCode());
			snpCO.setBRANCH_CODE(repParamsCO.getBranchCode());
			// get the new counter
			BigDecimal snpId = BigDecimal.ZERO;
			connectFail = true;
				while (connectFail)
				{	
					try {
					    	snpId = commonRepFuncBO.retCounterValue("IRP_SNAPSHOTS");
						connectFail= false;
					}
					catch (Exception e)
					{
						connectFail = sqlConnectionFailed(e,"System snapshots retrieval exception");						
					}
				}
			snpCO.setSNAPSHOT_ID(snpId);
			// save into DB all details
			snpCO.setREPORT_CONTENT(reportBytes);
			snpCO.setFILE_NAME(fileName);
			if(BigDecimal.ONE.equals(saveDataType)) // if DB
			{
			    snpCO.setIS_DB(BigDecimal.ONE);
			}
			else
			// if repository
			{
			    snpCO.setIS_DB(BigDecimal.ZERO);
			}
			// sent from sched
			snpCO.setSNAPSHOT_REF(BigDecimal.ONE);
			connectFail = true;
				while (connectFail)
				{	
					try {
						snpCO.setAPP_NAME(ConstantsCommon.REP_APP_NAME);
						snapShotBO.insertSnapShots(snpCO);
						connectFail= false;
					}
					catch (Exception e)
					{
						connectFail = sqlConnectionFailed(e,"System insert snapshot retrieval exception");
					}
				}
			
			vo.setSNAPSHOT_ID(snpId);
		    }

		    // update the log record by setting the status to s:success
		    // and
		    // the endDate to now
		    connectFail = true;
			while (connectFail)
			{	
				try {
				    	schedulerDAO.updateLOG(vo);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System update LOG retrieval exception");					
				}
			}
		    // call procedures after
		    connectFail = true;
			while (connectFail)
			{	
				try {
				    	reportBO.callProcedures(BigDecimal.ONE, paramValMap, reportCO, null, con);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System call procedures retrieval exception");					
				}
			}
		    // call subreport After procedures
		    for(IRP_AD_HOC_REPORTCO subRepCO : subReportCOList)
		    {
			connectFail = true;
				while (connectFail)
				{	
					try {
					    	reportBO.callProcedures(BigDecimal.ONE, paramValMap, subRepCO, null, con);
						connectFail= false;
					}
					catch (Exception e)
					{
						connectFail = sqlConnectionFailed(e,"System call procedures retrieval exception");
					}
				}
		    }

		}
		log.info(">>>>>>>>>>>>>>con : "+con);
		if(con != null)
		{
		    con.commit();
		}
		IRP_SCHEDULESC sc = new IRP_SCHEDULESC();
		sc.setSCHED_ID(SCHED_ID);
		IRP_SCHEDULECO co = new IRP_SCHEDULECO();
		connectFail = true;
			while (connectFail)
			{	
				try {
				    	log.info(">>>>>>>>>>>>>>check if table=ALRT_EVT table exist or not");
				    	//check if table=ALRT_EVT table exist or not
					sc.setTableName("ALRT_EVT");
					int tableCount = schedulerDAO.findTableByTableName(sc);
					
					String tableQuery = "null";
					//check if table eixst then append the query
					if(tableCount  > 0)
					{
					    tableQuery = "(SELECT EVT.DESC_ENG FROM ALRT_EVT EVT WHERE EVT.EVT_ID = IRP_SCHEDULE.EVT_ID)";
					}
					sc.setTableQuery(tableQuery);
				    	co = schedulerDAO.findSingleSchedule(sc);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System find scheduler retrieval exception");					
				}
			}
			
			connectFail = true;
			while (connectFail)
			{	
				try {
					co.setScheduleDetails(schedulerDAO.findScheduleDetails(sc));
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System find scheduler details retrieval exception");
				}
			}
		Date nextDate = null;
		//if not on demand
		Date nxtRunDte=co.getSCHED_VO().getNEXT_RUN_DATE();
		if(nxtRunDte!=null)
		{
		connectFail = true;
			while (connectFail)
			{	
				try {

					nextDate = schedulerDAO.retNextRunningDate(co);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System Runnig date retrieval exception");
				}
			}
		sc.setNEXT_RUN_DATE(nextDate);
		connectFail = true;
			while (connectFail)
			{	
				try {
				    	if(nextDateUpdatedMap.get(sc.getSCHED_ID()).equals(ConstantsCommon.TRUE))
				    	{
				    	    schedulerDAO.updateNextRunDate(sc);
				    	}
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System next run date retrieval exception");
					
				}
			}
		}
	    }
	    catch(Exception ex)
	    {
		if(con != null)
		{
		    con.rollback();
		}
		vo.setREMARKS(ex.toString());
		vo.setSTATUS("f");
		ex.printStackTrace();
		  
		    connectFail = true;
			while (connectFail)
			{	
				try {
				    	schedulerDAO.updateLOG(vo);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System update scheduler retrieval exception");
					
				}
			}
		
	    }
	    finally
	    {
		log.info(">>>>>>>>>>>>>>in finaly Line : 1577");
	    	int IsSybase = 0;
			connectFail = true;
				while (connectFail)
				{	
					try {
					    	IsSybase = commonLibBO.returnIsSybase();
						connectFail= false;
					}
					catch (Exception e)
					{
						connectFail = sqlConnectionFailed(e,"System Is Sybase retrieval exception");
					}
				}
		 // drop hash Table for imported procedures in case db=Sybase
		    if(IsSybase== 1 && reportCO!=null && !reportCO.getHashTblList().isEmpty())
		    {
			
			connectFail = true;
				while (connectFail)
				{	
					try {
					    	procBO.dropHashTbl(con, reportCO.getHashTblList());
						connectFail= false;
					}
					catch (Exception e)
					{
						connectFail = sqlConnectionFailed(e,"System drop Hash Table retrieval exception");
					
					}
				}
		    }
		    log.info(">>>>>>>>>>>>>>con Line : 1609 "+con);
		    if(con != null)
		    {
			log.info(">>>>>>>>>>>>>>con.close() Line : 1612 "+con);
			con.close();
		    }
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}

    }

    private HashMap<String, String> retJasperPropsVal(JasperPrint jasperPrint)
    {
	HashMap<String, String> propsMap = new HashMap<String, String>();
	List<JRPrintElement> lstElems;
	String txtVal;
	JRPrintElement elem;
	JRTemplatePrintText txtFe;
	JRPrintPage page;
	Object obj;
	JRTemplatePrintFrame frame;
	JRTemplatePrintFrame frame2;
	Object frame2Obj;
	List frame2Elem;
	List<JRPrintElement> lstTblElems;
	try
	{
	    int pg = jasperPrint.getPages().size();
	    for(int i = 0; i < pg; i++)
	    {
		page = jasperPrint.getPages().get(i);
		lstElems = page.getElements();
		for(int j = 0; j < lstElems.size(); j++)
		{
		    if(propsMap.get("my.attachFileFe") == null)
		    {
			elem = lstElems.get(j);
			// free form
			if(elem instanceof JRTemplatePrintText)
			{
			    txtFe = (JRTemplatePrintText) elem;
			    if(txtFe.getValue() instanceof java.lang.String)
			    {
				txtVal = (String)txtFe.getValue();
			    }
			    else
			    {
				txtVal = txtFe.getFullText();
			    }
			    retrievePropVal(txtFe, txtVal, propsMap);
			}
			// tab
			else if(elem instanceof JRTemplatePrintFrame)
			{
			    frame = (JRTemplatePrintFrame) elem;
			    lstTblElems = frame.getElements();
			    for(int k = 0; k < lstTblElems.size(); k++)
			    {
				obj = lstTblElems.get(k);
				if(obj instanceof JRTemplatePrintFrame)
				{
				    frame2 = (JRTemplatePrintFrame) obj;
				    frame2Elem = frame2.getElements();
				    for(int l = 0; l < frame2Elem.size(); l++)
				    {
					frame2Obj = frame2Elem.get(l);
					if(frame2Obj instanceof JRTemplatePrintText)
					{
					    txtFe = (JRTemplatePrintText) frame2Obj;
					    if(txtFe.getValue() instanceof java.lang.String)
					    {
						txtVal = (String)txtFe.getValue();
					    }
					    else
					    {
						txtVal = txtFe.getFullText();
					    }
					    retrievePropVal(txtFe, txtVal, propsMap);
					}
				    }
				}
				else if(obj instanceof JRTemplatePrintText)
				{
				    txtFe = (JRTemplatePrintText) obj;
				    if(txtFe.getValue() instanceof java.lang.String)
				    {
					txtVal = (String)txtFe.getValue();
				    }
				    else
				    {
					txtVal = txtFe.getFullText();
				    }
				    retrievePropVal(txtFe, txtVal, propsMap);
				}
			    }
			}
		    }
		}
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}

	return propsMap;
    }

    public JRAbstractExporter  createHtmlExporter(JasperPrint jasperPrint,String repositoryPath,String fileName,String ext,String imgDir,String repFolderName,String imgFolderName)
    {
    	JRAbstractExporter  exporter =null;
    	try
    	{
    		
    		File imgDirFile = new PathFileSecure(imgDir);
		    if(!imgDirFile.exists())
		    {
			boolean res = imgDirFile.mkdirs();
			if(!res)
			{
			    Log.getInstance().warning("Directory " + imgDirFile + " creation failed");
			}
		    }
    		
    		exporter = new HtmlExporter();
    		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	
    		SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(repositoryPath + "/" + repFolderName + "/" + fileName + "." + ext);
    		HtmlResourceHandler fileHdl = new FileHtmlResourceHandler(imgDirFile, imgFolderName + "/" + "{0}");
    		fileHdl = new MapHtmlResourceHandler(fileHdl, new HashMap());
    		output.setImageHandler(fileHdl);
    		exporter.setExporterOutput(output);

		
		   if(ConstantsCommon.LANGUAGE_ARABIC.equalsIgnoreCase(language) && "0".equals(currentReport.getLANG_INDEPENDENT_YN()))
		    {
			 SimpleHtmlExporterConfiguration htmlExpConf = new SimpleHtmlExporterConfiguration();
			 htmlExpConf.setHtmlFooter("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><style>body {direction:rtl}</style>");
			 htmlExpConf.setBetweenPagesHtml("<P STYLE='page-break-before:always'>");
			 exporter.setConfiguration(htmlExpConf);
		    }
    	}
    	catch(Exception e)
    	{
    		log.error(e,"");
    	}
		   return exporter;
    }
    
    private  HashMap<String,Object> sendSchedMail(JasperPrint jasperPrint, JRAbstractExporter exporterr, ByteArrayOutputStream byteArray,
	    byte[] repBytes, String repositoryPath, String extt, IRP_REPORT_SCHED_LOGVO vo, String fileName,
	    StringBuffer sbInsert, LinkedHashMap<String, String> jasDesMap)
    {
	HashMap<String,Object> retMap = new HashMap<String,Object>();
	JRAbstractExporter exporter=exporterr;
	byte[] reportBytes=repBytes;
	String ext=extt;
	String repFolderName = RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName;
	String imgFolderName = RepConstantsCommon.IMAGES_LOCATION;
	String destination=repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName + "." + ext; 
	String imgDir = repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + fileName + "/" + imgFolderName;
	String pwd ="";
	List<MailCO> mailList = new ArrayList<MailCO>();
	try
	{
	    log.info("begin send schedMail function");
	    int pg = jasperPrint.getPages().size();
	    JRPrintPage page;
	    List<JRPrintElement> lstElems;
	    List<JRPrintElement> lstTblElems;
	    JRPrintElement elem;
	    JRTemplatePrintText txtFe;
	    JRTemplatePrintFrame frame;
	    JRTemplatePrintFrame frame2;
	    Object frame2Obj;
	    List frame2Elem;
	    Object obj;
	    String txtVal;
	    String prevGrp = "";

	    String feType = "";
	    // to store the final statement
	    // StringBuffer sbSql=new StringBuffer("");
	    StringBuffer sbVal = new StringBuffer("");
	    int cnt = 0;
	    //Collection feTypeLst = new ArrayList<String>();
	    Collection feTypeLst = jasDesMap.values();
	    int feSize = feTypeLst.size();

	    // ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    int fromPg = 0;
	    int toPg;
	    HashMap<String, String> propsMap = new HashMap<String, String>();
	    // loop the pages
	    for(int i = 0; i < pg; i++)
	    {
		page = jasperPrint.getPages().get(i);
		lstElems = page.getElements();
		for(int j = 0; j < lstElems.size(); j++)
		{
		    elem = lstElems.get(j);
		    // free form
		    if(elem instanceof JRTemplatePrintText)
		    {
			txtFe = (JRTemplatePrintText) elem;
			 if(txtFe.getValue() instanceof java.lang.String)
			    {
				txtVal = (String)txtFe.getValue();
			    }
			    else
			    {
				txtVal = txtFe.getFullText();
			    }

			// store the dynamic value of each property set in the
			// reportUtil when compiling the report
			retrievePropVal(txtFe, txtVal, propsMap);
			if ("1".equals(txtFe.getPropertiesMap().getProperty("my.pwdFe")))
			{
			    pwd = txtVal;
			    if (    StringUtil.nullToEmpty(pwd).isEmpty())
			    {
				pwd= "";
			    }
			}
			if(txtVal != null && txtVal.indexOf(RepConstantsCommon.MAIL_SPLIT_VAR) == 0)
			{
			    lstElems.remove(elem);
			    if("".equals(prevGrp))
			    {
				prevGrp = txtVal;
			    }
			    if(!prevGrp.equals(txtVal)) // new Grp switched
			    {

				if( format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT) )
				{
				    exporter=createHtmlExporter( jasperPrint, repositoryPath, fileName, ext, imgDir, repFolderName, imgFolderName);
				}
				else
				{
					byteArray=new ByteArrayOutputStream();
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
				}
				
				toPg = i - 1;
				setExporterConfiguration( exporter,  fromPg ,  toPg);
				exporter.exportReport();
				
				if( format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT) )
				{
        				// check if the report contains images
        				if((new PathFileSecure(imgDir)).list().length > 0)
        				{
        				    // zip the folder and convert it to byte[]
        				    reportBytes = FileUtil.generateZipFromFldr(repositoryPath + "/" + repFolderName);
        				    ext = "zip";
        				}
        				else
        				{
        				    reportBytes = FileUtil.readFileBytes(repositoryPath + "/" + repFolderName + "/"
        					    + fileName + "." + ext);
        				}
        				// File file = new PathFileSecure(repositoryPath + "/" + repFolderName + "/" + fileName + "." + ext);
        				// file.delete(); 
				}
				else
				{
				    reportBytes = byteArray.toByteArray();
				    //encrypt pdf
				    if( format.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT) && currentReport.getSECURED_FILE_YN().equals("1") )
				    {
					reportBytes =encryptPdfFromBytes(reportBytes,pwd);
				    }
				}
				// FileUtil.writeFile(repositoryPath+"/"+fileName+i+"."+ext,
				// new String(reportBytes));

				prevGrp = txtVal;
				fromPg = i;

				 	if(new BigDecimal(2).equals(saveDataType))
				    {
				 		writeSplitedRepUnderRepoistory(propsMap,reportBytes,fileName,repositoryPath,ext);
				    }
				    else
				    {
				    	// ****prepare mail
				    	prepareMailBeforeSend(propsMap, ext, reportBytes, mailList, fileName);
				    }

					if(format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT))
				    {
				 		FileUtil.deleteFolder(repositoryPath+"/"+repFolderName);
				    }
				 	
				// empty the byteArray in order to not append
				// the data of the prev.page with the current
				// one
				//byteArray = new ByteArrayOutputStream();
				// re-initilaize the propsMap in order to be
				// filled with the values of the new grp
				propsMap = new HashMap<String, String>();
			    }
			}
		    }
		    // tabular
		    else if(elem instanceof JRTemplatePrintFrame)
		    {
			frame = (JRTemplatePrintFrame) elem;
			lstTblElems = frame.getElements();
			for(int k = 0; k < lstTblElems.size(); k++)
			{
			    obj = lstTblElems.get(k);
			    if(obj instanceof JRTemplatePrintFrame)
			    {
				frame2 = (JRTemplatePrintFrame) obj;
				frame2Elem = frame2.getElements();
				for(int l = 0; l < frame2Elem.size(); l++)
				{
				    frame2Obj = frame2Elem.get(l);
				    if(frame2Obj instanceof JRTemplatePrintText)
				    {
					txtFe = (JRTemplatePrintText) frame2Obj;
					 if(txtFe.getValue() instanceof java.lang.String)
					    {
						txtVal = (String)txtFe.getValue();
					    }
					    else
					    {
						txtVal = txtFe.getFullText();
					    }

					// store the dynamic value of each
					// property set in the reportUtil when
					// compiling the report
					retrievePropVal(txtFe, txtVal, propsMap);
					// if sql construct the insert sql
					// statement
					if(ConstantsCommon.SQL_EXT.equals(ext) && (txtFe.getPropertiesMap().getProperty("my.dataFe") != null) )
					{
					    // note that the below code is
					    // repeated in this class since some
					    // parameters as 'cnt' is not
					    // changed by reference so we
					    // couldn't create a separate
					    // function to be called once
						if(cnt == 0)
						{
						    sbVal = sbVal.append(sbInsert);
						    sbVal.append(" VALUES(");
						}
						if(cnt < feSize)
						{
						    feType = (String) jasDesMap.values().toArray()[cnt];
						    if(RepConstantsCommon.VARCHAR_TYPE_JASPER.equals(feType)
							    || RepConstantsCommon.DATE_TYPE_JASPER.equals(feType))
						    {
							sbVal.append("'");
							sbVal.append(txtVal);
							sbVal.append("'");
							sbVal.append(",");
						    }
						    else
						    {
							sbVal.append(txtVal);
							sbVal.append(",");
						    }

						}
						cnt++;
						if(cnt == feSize)
						{
						    cnt = 0;

						    sbVal = new StringBuffer(sbVal.substring(0, sbVal.length() - 1));
						    sbVal.append(");\n");

						}
					}
					if(txtVal != null && txtVal.indexOf(RepConstantsCommon.MAIL_SPLIT_VAR) == 0)
					{
					    // remove the added line that we are
					    // only use to split the report data
					    frame2Elem.remove(frame2Obj);
					    if("".equals(prevGrp))
					    {
						prevGrp = txtVal;
					    }
					    if(!prevGrp.equals(txtVal)) // new
					    // Grp
					    // switched
					    {
						if(ConstantsCommon.SQL_EXT.equals(ext))
						{
						    // if sql

						    reportBytes = sbVal.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
						    if(new BigDecimal(2).equals(saveDataType))
						    {
						    	writeSplitedRepUnderRepoistory(propsMap,reportBytes,fileName,repositoryPath,ext);
						    }
						    else
						    {
						    	prepareMailBeforeSend(propsMap, ext, reportBytes, mailList,
							    fileName);
						    }
						    // re-initilaize the
						    // propsMap in order to be
						    // filled with the values of
						    // the new grp
						    propsMap = new HashMap<String, String>();
						    // re -initialize the sbSql
						    // in order to create a new
						    // insert statements for the
						    // new grp
						    sbVal = new StringBuffer("");
						    cnt = 0;
						}
						else
						{
						    toPg = i - 1;
						    setExporterConfiguration( exporter,  fromPg ,  toPg);
						    
						    if(ConstantsCommon.XLSX_EXT.equals(ext))
						    {
							exporter = new JRXlsxExporter();
							exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
						    }
						
						    
						    if( format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT) )
						    {
							exporter=createHtmlExporter( jasperPrint, repositoryPath, fileName, ext, imgDir, repFolderName, imgFolderName);
						    }
						    else
						    {
						    	byteArray=new ByteArrayOutputStream();
						    	exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
						    }
						  //  SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
						    // print pages from fromPg
						    // to
						    // i-1
						   

						    exporter.exportReport();
						    
						    if( format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT) )
						    {
							// check if the report contains images
							if((new PathFileSecure(imgDir)).list().length > 0)
							{
							    // zip the folder and convert it to byte[]
							    reportBytes = FileUtil.generateZipFromFldr(repositoryPath + "/" + repFolderName);
							    ext = "zip";
							}
							else
							{
							    reportBytes = FileUtil.readFileBytes(repositoryPath + "/" + repFolderName + "/"
								    + fileName + "." + ext);
							}
						    }
						    else
						    {
							reportBytes = byteArray.toByteArray();
							if( format.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT) && currentReport.getSECURED_FILE_YN().equals("1") )
							{
							    reportBytes =encryptPdfFromBytes(reportBytes,pwd);
							}
						    }
						    // FileUtil.writeFile(repositoryPath+"/"+fileName+i+"."+ext,
						    // new String(reportBytes));

						    prevGrp = txtVal;
						    fromPg = i;
						    
						    if(new BigDecimal(2).equals(saveDataType))
						    {
						    	writeSplitedRepUnderRepoistory(propsMap,reportBytes,fileName,repositoryPath,ext);
						    }
						    else
						    {
						    // ****prepare mail
						    	prepareMailBeforeSend(propsMap, ext, reportBytes, mailList,
							    fileName);
						    }
						    if(format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT))
						    {
						 		FileUtil.deleteFolder(repositoryPath+"/"+repFolderName);
						    }
						    // re-initilaize the
						    // propsMap in
						    // order to be filled with
						    // the
						    // values of the new grp
						    propsMap = new HashMap<String, String>();
						    // empty the byteArray in
						    // order
						    // to not append the data of
						    // the
						    // prev.page with the
						    // current
						    // one
						    //byteArray = new ByteArrayOutputStream();
						}
					    }
					}
				    }
				}
			    }
			    else if(obj instanceof JRTemplatePrintText)
			    {
				txtFe = (JRTemplatePrintText) obj;
				 if(txtFe.getValue() instanceof java.lang.String)
				    {
					txtVal = (String)txtFe.getValue();
				    }
				    else
				    {
					txtVal = txtFe.getFullText();
				    }
				retrievePropVal(txtFe, txtVal, propsMap);
			    }

			}
		    }
		}
	    }
	    // write the last page
	    if(ConstantsCommon.SQL_EXT.equals(ext))
	    {
		reportBytes = sbVal.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
	    }
	    else
	    {
		toPg = pg - 1;
		if(ConstantsCommon.XLSX_EXT.equals(ext))
		{
		    exporter = new JRXlsxExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		}
		
		if( format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT) )
		{
			exporter=createHtmlExporter( jasperPrint, repositoryPath, fileName, ext, imgDir, repFolderName, imgFolderName);
		}
		else if (ConstantsCommon.TXT_EXT.equals(ext))
		{
		    byteArray=new ByteArrayOutputStream();
		    exporter.setExporterOutput(new SimpleWriterExporterOutput(byteArray));
		}
		else if(ConstantsCommon.TXT_FILE.equals(format) || ConstantsCommon.DAT_EXT.equals(format))
		{
		    ext = ConstantsCommon.TXT_FILE.equals(format)?ConstantsCommon.TXT_EXT:ConstantsCommon.DAT_EXT;
		    byteArray = new ByteArrayOutputStream();
		    exporter.setExporterOutput(new SimpleWriterExporterOutput(byteArray));
		}
		else if (format.equalsIgnoreCase(ConstantsCommon.CSV_REP_FORMAT) || format.equalsIgnoreCase(ConstantsCommon.CSV_EXT_REP_FORMAT))
		{
		    byteArray=new ByteArrayOutputStream();
		    exporter.setExporterOutput(new SimpleWriterExporterOutput(byteArray));
		}
		else
		{
			byteArray=new ByteArrayOutputStream();
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
		}
//		
		setExporterConfiguration( exporter,  fromPg ,  toPg);
		exporter.exportReport();
		
		if( format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT) )
		{
        		// check if the report contains images
        		if((new PathFileSecure(imgDir)).list().length > 0)
        		{
        		    // zip the folder and convert it to byte[]
        		    reportBytes = FileUtil.generateZipFromFldr(repositoryPath + "/" + repFolderName);
        		    ext = "zip";
        		}
        		else
        		{
        		    reportBytes = FileUtil.readFileBytes(repositoryPath + "/" + repFolderName + "/"
        			    + fileName + "." + ext);
        		}
		}
		else
		{
		    reportBytes = byteArray.toByteArray();
		    if( format.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT) && currentReport.getSECURED_FILE_YN().equals("1") )
		    {
			reportBytes =encryptPdfFromBytes(reportBytes,pwd);
		    }
		}
		// FileUtil.writeFile(repositoryPath+"/"+fileName+"_Final."+ext,
		// new
		// String(reportBytes));
	    }

	    // prepare mail before send
	    if(new BigDecimal(2).equals(saveDataType))
	    {
	    	writeSplitedRepUnderRepoistory(propsMap,reportBytes,fileName,repositoryPath,ext);
	    }
	    else
	    {
	    	prepareMailBeforeSend(propsMap, ext, reportBytes, mailList, fileName);
	    }
	    if(format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT))
	    {
	 		FileUtil.deleteFolder(repositoryPath+"/"+repFolderName);
	    }
	    // send the mail

	    MailServerCO mServerCO =null;
	    boolean connectFail = true;
	    // return the mail server details from the DB
	    log.info(">>>>>>>>>>>>>>currentReport.getMAIL_SERVER_CODE() : "+currentReport.getMAIL_SERVER_CODE());
	    if(currentReport.getMAIL_SERVER_CODE()!=null)
	    {
		 IRP_MAIL_SERVER_CONFIGSC msSC = new IRP_MAIL_SERVER_CONFIGSC();
		    msSC.setMsCode(currentReport.getMAIL_SERVER_CODE());
		    IRP_MAIL_SERVER_CONFIGCO msCO= null;
		  
			while (connectFail)
			{	
				try {
				    msCO = mailServerConfigBO.retMailServerById(msSC);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"retMailServerById return exception");
					
				}
			}
		    

		    mServerCO = new MailServerCO();
		    mServerCO.setHost(msCO.getMailServerVO().getHOST());
		    mServerCO.setPort(msCO.getMailServerVO().getPORT().intValue());
		    mServerCO.setUserName(msCO.getMailServerVO().getSERVER_USER_NAME());
		    mServerCO.setPassword(msCO.getMailServerVO().getSERVER_PASS());
		    mServerCO
        		.setSslEnabled(BigDecimal.ONE.equals(msCO.getMailServerVO().getSSL_ENABLE_YN()) ? ConstantsCommon.TRUE
        			: ConstantsCommon.FALSE);
		    if(!NumberUtil.isEmptyDecimal(msCO.getMailServerVO().getSSL_PORT_NBR()))
		    {
			mServerCO.setSslSocketPort(msCO.getMailServerVO().getSSL_PORT_NBR().intValue());
		    }
		    mServerCO.setProtocol("smtp");

	    }
	   
	    boolean detLogError = false;
	    Date startDate;
	    //email not consolidated then send mail one by one
	    log.info(">>>>>>>>>>>>>>currentReport.getEMAIL_CONSOLIDATED_YN() : "+currentReport.getEMAIL_CONSOLIDATED_YN());
	    if("0".equals(currentReport.getEMAIL_CONSOLIDATED_YN()))
	    {
		MailCO mailCO;
		IRP_REPORT_SCHED_LOG_DETAILSVO logDetVO;
		for(int i = 0; i < mailList.size(); i++)
		{
		    startDate = commonLibBO.returnSystemDateWithTime();
		    mailCO = mailList.get(i);
		    logDetVO = new IRP_REPORT_SCHED_LOG_DETAILSVO();
		    logDetVO.setREPORT_ID(vo.getREPORT_ID());
		    logDetVO.setSCHED_ID(vo.getSCHED_ID());
		    logDetVO.setSTART_DATE(vo.getSTART_DATE());
		    logDetVO.setSCHEDULED_DATE(vo.getSCHEDULED_DATE());   

		   //Retry to send the report mail in case there is a mail server exception 
		    try
		    {
			log.info(">>>>>>>>>>>>>>before commonLibBO.sendEmail line 2278");
			commonLibBO.sendEmail(mailCO, mServerCO);
			log.info(">>>>>>>>>>>>>>after commonLibBO.sendEmail line 2280");
			// save in IRP_REPORT_SCHED_LOG_DETAIL
			logDetVO.setSTATUS("s");
			logDetVO.setREMARKS("");
			insertRepSchedLogDetails(mailCO, logDetVO);
			if(format.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT)
				&& currentReport.getSECURED_FILE_YN().equals("1"))
			{
			    File file = new PathFileSecure(destination);
			    boolean isDel = file.delete();
			    if(!isDel)
			    {
				log.debug("The temporary encrypted exported pdf file has not been deleted from the repository:"+ file);
			    }
			}
		    }
		    catch(Exception e)
		    {
			log.error(e, e.getMessage());
			detLogError = true;
			// save in IRP_REPORT_SCHED_LOG_DETAILS
			logDetVO.setSTATUS("f");
			String errorStr = e.toString();
			if(errorStr.length() > 500)
			{
			    errorStr = errorStr.substring(0, 499);
			}
			logDetVO.setREMARKS(errorStr);
			insertRepSchedLogDetails(mailCO, logDetVO);
		    }
		}
	    }
	    else
	    // email consolidated in one mail
	    {
		try
		{
		    log.info(">>>>>>>>>>>>>>before commonLibBO.sendEmail line 2317");
		    commonLibBO.sendEmail(mailList, mServerCO);
		    log.info(">>>>>>>>>>>>>>after commonLibBO.sendEmail line 2317");
		    // save in IRP_REPORT_SCHED_LOG_DETAILS
		    log.info(">>>>>>>>>>>>>>before insertConsolidatedMailLogDetails line 2321");
		    insertConsolidatedMailLogDetails(mailList, vo, true);
		    log.info(">>>>>>>>>>>>>>after insertConsolidatedMailLogDetails line 2323");
		}
		catch(Exception e)
		{
		    log.error(e, e.getMessage());
		    insertConsolidatedMailLogDetails(mailList, vo, false);

		}
	    }
	    log.info(">>>>>>>>>>>>>>detLogError line 2332 : "+detLogError);
	    if(detLogError)
	    {
		retMap.put("errorStr","f");
		retMap.put("reportBytes",reportBytes);
		return retMap;
	    }
	    else
	    {
		retMap.put("errorStr","");
		retMap.put("reportBytes",reportBytes);
		return retMap;
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "");
	    // save in IRP_REPORT_SCHED_LOG_DETAILS
	    String errorStr = e.toString();
	    if(errorStr.length() > 500)
	    {
		errorStr = errorStr.substring(0, 499);
	    }
	    insertConsolidatedMailLogDetails(mailList, vo, false);
	    retMap.put("errorStr", errorStr);
	    retMap.put("reportBytes",reportBytes);
	    return retMap;
	}
    }

    public void insertConsolidatedMailLogDetails(List<MailCO> mailList, IRP_REPORT_SCHED_LOGVO vo, boolean isSuccess)
    {
	try
	{
    	MailCO mailCO;
    	IRP_REPORT_SCHED_LOG_DETAILSVO logDetVO;
    	Date startDate;
    	for(int i = 0; i < mailList.size(); i++)
    	{
    	    startDate = commonLibBO.returnSystemDateWithTime();
    	    mailCO = mailList.get(i);
    	    logDetVO = new IRP_REPORT_SCHED_LOG_DETAILSVO();
    	    logDetVO.setREPORT_ID(vo.getREPORT_ID());
    	    logDetVO.setSCHED_ID(vo.getSCHED_ID());
    	    logDetVO.setSTART_DATE(vo.getSTART_DATE());
    	    logDetVO.setREMARKS("");
    	    logDetVO.setSCHEDULED_DATE(vo.getSCHEDULED_DATE());
    	    if(isSuccess)
    	    {
    		logDetVO.setSTATUS("s");
    	    }
    	    else
    	    {
    		logDetVO.setSTATUS("p");
    	    }
    
    	    insertRepSchedLogDetails(mailCO, logDetVO);
    
    	}
	}
	catch(Exception e)
	{
	    log.error(e,null);
	}

    }

    public void insertRepSchedLogDetails(MailCO mailCO, IRP_REPORT_SCHED_LOG_DETAILSVO logDetVO)
    {
	try
	{
	    String[] toArr = mailCO.getTo() == null ?   new String[0]: mailCO.getTo();
	    String[] ccArr = mailCO.getCc() == null ?   new String[0]: mailCO.getCc();
	    String[] bccArr = mailCO.getBcc() == null ? new String[0]: mailCO.getBcc();
	    String fromMailVal = mailCO.getFrom();

	    logDetVO.setFROM_EMAIL_VAL(fromMailVal);
	    String emailSubject = StringUtil.nullToEmpty(mailCO.getSubject());
	    if(emailSubject.indexOf("~#~") == -1)
	    {
		logDetVO.setEMAIL_SUBJECT(emailSubject);
	    }
	    else
	    {
		logDetVO.setEMAIL_SUBJECT(emailSubject.substring(0, emailSubject.indexOf("~#~")));
		logDetVO.setCIF_NO(
			new BigDecimal(emailSubject.substring(emailSubject.indexOf("~#~")+3, emailSubject.length())));
	    }
	    String receiverMailVal;

	    // insert the To log details
	    for(int i = 0; i < toArr.length; i++)
	    {
		receiverMailVal = toArr[i];
		if(receiverMailVal != null && !"".equals(receiverMailVal))
		{
		    logDetVO.setRECEIVER_TYPE(BigDecimal.ONE);
		    logDetVO.setRECEIVER_EMAIL_VAL(receiverMailVal);
		    
		    boolean connectFail = true;
			while (connectFail)
			{	
				try {
				    	schedulerDAO.insertRepSchedLogDetails(logDetVO);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System insert Rep Sched Log Details retrieval exception");
					
				}
			}

		}
	    }

	    // insert the cc log details
	    for(int i = 0; i < ccArr.length; i++)
	    {
		receiverMailVal = ccArr[i];
		if(receiverMailVal != null && !"".equals(receiverMailVal))
		{
		    logDetVO.setRECEIVER_TYPE(new BigDecimal(2));
		    logDetVO.setRECEIVER_EMAIL_VAL(receiverMailVal);
		    boolean connectFail = true;
			while (connectFail)
			{	
				try {
				    	schedulerDAO.insertRepSchedLogDetails(logDetVO);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System insert Rep Sched Log Details retrieval exception");
					
				}
			}
		}
	    }

	    // insert the bcc log details
	    for(int i = 0; i < bccArr.length; i++)
	    {
		receiverMailVal = bccArr[i];
		if(receiverMailVal != null && !"".equals(receiverMailVal))
		{
		    logDetVO.setRECEIVER_TYPE(new BigDecimal(3));
		    logDetVO.setRECEIVER_EMAIL_VAL(receiverMailVal);
		    boolean connectFail = true;
			while (connectFail)
			{	
				try {
				    	schedulerDAO.insertRepSchedLogDetails(logDetVO);
					connectFail= false;
				}
				catch (Exception e)
				{	
					connectFail = sqlConnectionFailed(e,"System insert Rep Sched Log Details retrieval exception");
						
				}
			}
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(" error in SchedulerThread.insertRepSchedLogDetails");
	}
    }

    private void prepareMailBeforeSend(HashMap<String, String> propsMap, String ext, byte[] reportBytes,
	    List<MailCO> mailList, String fileName)throws Exception
    {
	try
	{
	    log.info(">>>>>>>>>>>>>>prepareMailBeforeSend");
	    IRP_REP_SCHED_MAIL_RECEIVERSVO recVO = new IRP_REP_SCHED_MAIL_RECEIVERSVO();
	    recVO.setSCHED_ID(SCHED_ID);
	    recVO.setREPORT_ID(REPORT_ID);
	    List<String> usrLst= null;
	    String[] usrArrTo;
	    String[] usrArrCc;
	    String[] usrArrBcc;
	    String[] receivVal;
	    MailCO mailCO = new MailCO();
	    // set from email
	    log.info(">>>>>>>>>>>>>>currentReport.getFROM_EMAIL_TYPE()) : "+currentReport.getFROM_EMAIL_TYPE());
	    if(new BigDecimal("3").equals(currentReport.getFROM_EMAIL_TYPE()))// if
	    // field
	    {
		log.info(">>>>>>>>>>>>>>my.fromFe : "+propsMap.get("my.fromFe"));
		mailCO.setFrom(propsMap.get("my.fromFe"));
	    }
	    else
	    {
		mailCO.setFrom(currentReport.getFROM_EMAIL_VAL()); // static
	    }

	    // set to
	    String to = "";
	    BigDecimal cifNo=null;
	    if(new BigDecimal("2").equals(currentReport.getTO_EMAIL_TYPE())) // if
	    // field
	    {
		to = propsMap.get("my.toFe");
	    }
	    else if(new BigDecimal("3").equals(currentReport.getTO_EMAIL_TYPE()) && propsMap.get("my.toCif")!=null) // if
	    // cif
	    {
		cifNo = new BigDecimal(propsMap.get("my.toCif"));
		ReportParamsCO repParamsCO = (ReportParamsCO) parameters.get("repParamsCO");
		repParamsCO.setNoData(false);
		BigDecimal compCode = repParamsCO.getCompanyCode();
		CIFVOKey cifVOKey = new CIFVOKey();
		cifVOKey.setCIF_NO(cifNo);
		cifVOKey.setCOMP_CODE(compCode);
		CIFVO cifVO = null;		
		  
	    boolean connectFail = true;
		while (connectFail)
		{	
			try {
				cifVO = schedulerDAO.retMailValByCif(cifVOKey);    			
				connectFail= false;
			}
			catch (Exception e)
			{	connectFail = sqlConnectionFailed(e," System retMailValByCif retrieval exception");
				
			}
		}		
		
		
		if(cifVO != null)
		{
		    to = cifVO.getEMAIL_ID();
		}
	    }
	    //if compute
	    else if(new BigDecimal("4").equals(currentReport.getTO_EMAIL_TYPE()))
	    {
		to = propsMap.get("my.toFeComp");
	    }
	    else
	    {
		to = currentReport.getTO_EMAIL_VAL(); // static
	    }
	    // to usersList
	    recVO.setRECEIVER_TYPE(BigDecimal.ONE);
	    boolean connectFail = true;
		while (connectFail)
		{	
			try {
			    usrLst = schedulerDAO.retMailUserList(recVO);
				connectFail= false;
			}
			catch (Exception e)
			{	
				connectFail = sqlConnectionFailed(e,"System retMailUserList retrieval exception");
			}
		}	   
	    if(!"null".equalsIgnoreCase(to) &&to != null && !"".equals(to))
	    {
		// check if to contains ; then split on the ;
		receivVal = to.split(";");
		for(int j = 0; j < receivVal.length; j++)
		{
		    usrLst.add(receivVal[j]);
		}
	    }
	    List<String> newUsrLst = new ArrayList<String>();
	    String newUsr;
	    // check if we have null objects in the usrLst
	    for(int i = 0; i < usrLst.size(); i++)
	    {
		newUsr = usrLst.get(i);
		if(newUsr != null)
		{
		    newUsrLst.add(newUsr);
		}
	    }
	    usrArrTo = new String[newUsrLst.size()];

	    for(int i = 0; i < newUsrLst.size(); i++)
	    {
		usrArrTo[i] = newUsrLst.get(i);
	    }

	    if(usrArrTo.length > 0)
	    {
		mailCO.setTo(usrArrTo);
	    }

	    // set cc
	    String cc = "";
	    if(new BigDecimal("2").equals(currentReport.getCC_EMAIL_TYPE())) // if
	    // field
	    {
		cc = propsMap.get("my.ccFe");
	    }
	    //compute
	    else if(new BigDecimal("3").equals(currentReport.getCC_EMAIL_TYPE()))
	    {
		cc = propsMap.get("my.ccFeComp");
	    }
	    else
	    {
		cc = currentReport.getCC_EMAIL_VAL(); // static
	    }
	    // cc usersList
	    recVO.setRECEIVER_TYPE(new BigDecimal(2));
	    
        connectFail = true;
		while (connectFail)
		{	
			try {
			    	usrLst = schedulerDAO.retMailUserList(recVO);
				connectFail= false;
			}
			catch (Exception e)
			{	
				connectFail = sqlConnectionFailed(e,"System Mail User List retrieval exception");
			}
		}

	    if(!"null".equalsIgnoreCase(cc) && cc != null && !"".equals(cc))
	    {
		// check if cc contains ; then split on the ;
		receivVal = cc.split(";");
		for(int j = 0; j < receivVal.length; j++)
		{
		    usrLst.add(receivVal[j]);
		}
	    }

	    // check if we have null objects in the usrLst
	    newUsrLst = new ArrayList<String>();
	    for(int i = 0; i < usrLst.size(); i++)
	    {
		newUsr = usrLst.get(i);
		if(newUsr != null)
		{
		    newUsrLst.add(newUsr);
		}
	    }

	    usrArrCc = new String[newUsrLst.size()];
	    for(int i = 0; i < newUsrLst.size(); i++)
	    {
		usrArrCc[i] = newUsrLst.get(i);
	    }
	    if(usrArrCc.length > 0)
	    {
		mailCO.setCc(usrArrCc);
	    }

	    // set bcc
	    String bcc = "";
	    if(new BigDecimal("2").equals(currentReport.getBCC_EMAIL_TYPE())) // if
	    // field
	    {
		bcc = propsMap.get("my.bccFe");
	    }
	    //compute
	    else if(new BigDecimal("3").equals(currentReport.getBCC_EMAIL_TYPE()))
	    {
		bcc = propsMap.get("my.bccFeComp");
	    }
	    else
	    {
		bcc = currentReport.getBCC_EMAIL_VAL(); // static
	    }
	    // bcc usersList
	    recVO.setRECEIVER_TYPE(new BigDecimal(3));
	    connectFail = true;
		while (connectFail)
		{	
			try {
			    	usrLst = schedulerDAO.retMailUserList(recVO);
				connectFail= false;
			}
			catch (Exception e)
			{
				connectFail = sqlConnectionFailed(e,"System Mail User List retrieval exception");				
			}
		}

	    if(!"null".equalsIgnoreCase(bcc) &&  bcc != null && !"".equals(bcc))
	    {
		// check if bcc contains ; then split on the ;
		receivVal = bcc.split(";");
		for(int j = 0; j < receivVal.length; j++)
		{
		    usrLst.add(receivVal[j]);
		}
	    }

	    // check if we have null objects in the usrLst
	    newUsrLst = new ArrayList<String>();
	    for(int i = 0; i < usrLst.size(); i++)
	    {
		newUsr = usrLst.get(i);
		if(newUsr != null)
		{
		    newUsrLst.add(newUsr);
		}
	    }

	    usrArrBcc = new String[newUsrLst.size()];
	    for(int i = 0; i < newUsrLst.size(); i++)
	    {
		usrArrBcc[i] = newUsrLst.get(i);
	    }
	    if(usrArrBcc.length > 0)
	    {
		mailCO.setBcc(usrArrBcc);
	    }

	    // set email subject
	    if(cifNo==null)
	    {
		mailCO.setSubject(currentReport.getEMAIL_SUBJECT());
	    }
	    else
	    {
		mailCO.setSubject(currentReport.getEMAIL_SUBJECT()+"~#~"+cifNo.toString());
	    }

	    // set email body
	    log.info("set email body");
	    StringBuffer sb = new StringBuffer();
	    currentReport.setEMAIL_BODY(StringUtil.nullToEmpty(currentReport.getEMAIL_BODY()).replaceAll("\r\n","<BR>"));
	    String emailBody=currentReport.getEMAIL_BODY();
	    Pattern pattern = Pattern.compile("\\[(.*?)\\]");
	    Matcher matcher = pattern.matcher(emailBody);
	    String argMatcher;
	    String feName;
	    String feNameVal;
	    while(matcher.find())
	    {
		   argMatcher = matcher.group();
		   feName = argMatcher.substring(1, argMatcher.length() - 1);
		   feNameVal=propsMap.get(feName);
		   if(feNameVal!=null)
		   {
		       emailBody = emailBody.replaceAll("\\[" + feName + "\\]", feNameVal);
		   }
	    }
	    log.info(">>>>>>>>>>>>>>emailBody : "+emailBody);
	    sb.append(emailBody);
	    log.info(">>>>>>>>>>>>>>sb : "+sb);
	    mailCO.setBodyMail(sb);

	    List<byte[]> listAtt = new ArrayList<byte[]>();
	    StringBuffer filesNamesSB = new StringBuffer("");
	    if("0".equals(currentReport.getATTACH_FILE_NAME())) // default
	    {
		filesNamesSB.append(fileName).append(".").append(ext);
	    }
	    else
	    {
		filesNamesSB.append(propsMap.get("my.attachFileFe")).append(".").append(ext);
	    }
	    filesNamesSB.append(";");
	    listAtt.add(reportBytes);
	    mailCO.setAttachmentList(listAtt);
	    mailCO.setFilesNames(filesNamesSB.toString().split(";"));

	    mailList.add(mailCO);

	}
	catch(Exception e)
	{
	    log.error(e, "");
	    throw new Exception(e);
	}

    }

    private void createJRXMLFile(byte[] xml, String reportPath) throws IOException
    {
	String xmlStr = new String(xml, CommonUtil.ENCODING);
	try
	{   File file = new PathFileSecure(reportPath + ".jrxml");
	    boolean isDel =file.delete();
	    if(!isDel)
	     {
		log.debug("The temporary scheduler jrxml file has not been deleted from the repository:" + file);
	    }
	    FileUtil.writeFile(reportPath + ".jrxml", xmlStr);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    private File compileReport(String report, IRP_SUB_REPORTCO subrep,String repFormat) throws JRException, BaseException, InterruptedException
    {
	JasperDesign jasperDesign = null;
	try {
		jasperDesign = JRXmlLoader.load(new PathFileSecure(report + ".jrxml"));
	} catch (Exception e1) {
		log.error(e1, e1.getMessage());
	}

	if(subrep == null)
	{
	    try
	    {
		LinkedHashMap<Long, IRP_FIELDSCO> dynGrpMap = new LinkedHashMap<Long, IRP_FIELDSCO>();
		HashMap<String, String> fePropsMap = new HashMap<String, String>();
		if(new BigDecimal(3).equals(saveDataType)  || new BigDecimal(2).equals(saveDataType))
		{
		    // get the groupping fields from the DB
		    IRP_REP_SCHED_MAIL_GROUP_BYSC sc = new IRP_REP_SCHED_MAIL_GROUP_BYSC();
		    sc.setREPORT_ID(REPORT_ID);
		    sc.setSCHED_ID(SCHED_ID);
		    List<IRP_REP_SCHED_MAIL_GROUP_BYCO> grpLst =null;
		    boolean connectFail=true;
		    while (connectFail)
			{	
				try {
				     grpLst = schedulerDAO.retMailFeGroupByList(sc);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System date retrieval exception");						
				}
			}
		    IRP_REP_SCHED_MAIL_GROUP_BYCO grpCO;
		    IRP_FIELDSCO feCO;
		    for(int i = 0; i < grpLst.size(); i++)
		    {
			grpCO = grpLst.get(i);
			feCO = new IRP_FIELDSCO();
			feCO.setLabelAlias(grpCO.getMailGrpVO().getFIELD_ALIAS());
			feCO.setGroupName("grp_" + i);
			dynGrpMap.put(generateId()+i, feCO);
		    }
		    //if the save data type is Mail
		    if(new BigDecimal(3).equals(saveDataType))
		    {
			/*
			 * create a hashMap containing the fields
			 * 'fromFe','toFe','toCif' ,'cc','bcc' and 'fieldName'
			 * in order to add a property for it in the jasperprint
			 */
			if(new BigDecimal(3).equals(currentReport.getFROM_EMAIL_TYPE()))
			{
			    fePropsMap.put("fromFe", currentReport.getFROM_EMAIL_VAL());
			}
			if(new BigDecimal(2).equals(currentReport.getTO_EMAIL_TYPE()))
			{
			    fePropsMap.put("toFe", currentReport.getTO_EMAIL_VAL());
			}
			if(new BigDecimal(3).equals(currentReport.getTO_EMAIL_TYPE()))
			{
			    fePropsMap.put("toCif", currentReport.getTO_EMAIL_VAL());
			}
			if(new BigDecimal(4).equals(currentReport.getTO_EMAIL_TYPE()))
			{
			    fePropsMap.put("toFeComp", currentReport.getTO_EMAIL_VAL());
			}
			if(new BigDecimal(2).equals(currentReport.getCC_EMAIL_TYPE()))
			{
			    fePropsMap.put("ccFe", currentReport.getCC_EMAIL_VAL());
			}
			if(new BigDecimal(3).equals(currentReport.getCC_EMAIL_TYPE()))
			{
			    fePropsMap.put("ccFeComp", currentReport.getCC_EMAIL_VAL());
			}
			if(new BigDecimal(2).equals(currentReport.getBCC_EMAIL_TYPE()))
			{
			    fePropsMap.put("bccFe", currentReport.getBCC_EMAIL_VAL());
			}
			if(new BigDecimal(3).equals(currentReport.getBCC_EMAIL_TYPE()))
			{
			    fePropsMap.put("bccFeComp", currentReport.getBCC_EMAIL_VAL());
			}
			//check the body of the email in case it contains fields
			String emailBody=currentReport.getEMAIL_BODY();
			Pattern pattern = Pattern.compile("\\[(.*?)\\]");
			Matcher matcher = pattern.matcher(emailBody);
			String argMatcher;
			String feName;
			while(matcher.find())
			{
			    argMatcher = matcher.group();
			    feName = argMatcher.substring(1, argMatcher.length() - 1);
			    fePropsMap.put("$F{"+feName+ "}", feName);
			}
		    }
		}

		if(!"0".equals(currentReport.getATTACH_FILE_NAME()))
		{
		    fePropsMap.put("attachFileFe", currentReport.getATTACH_FILE_NAME());
		}		
		if("1".equals(currentReport.getSECURED_FILE_YN()))
		{
		    fePropsMap.put("pwdFe", currentReport.getSECURED_PWD_FIELD_NAME());
		}
		
		//add whenNoData property at the level of the jasperDesign 
		String whenNoData=currentReport.getWHEN_NO_DATA();
		if(!"".equals(StringUtil.nullToEmpty(whenNoData))
			&& !RepConstantsCommon.WHEN_NO_DATA_DEFAULT.equals(whenNoData))
		{
		    
		    HashMap<String,String> transMap=new HashMap<String,String>();
		    if(RepConstantsCommon.WHEN_NO_DATA_NO_DATA_SECTION.equals(whenNoData))
		    {
			CommonLibSC sc = new CommonLibSC();
			sc.setAppName(ConstantsCommon.IMAL_APP_NAME);
			sc.setProgRef(ConstantsCommon.PROGREF_ROOT);
			sc.setLanguage(currentReport.getREPORT_LANGUAGE());
			sc.setKeyLabelCode("reporting.noDataRetrieved");
			boolean connectFail= true;
			String transVal = null;
			while (connectFail)
			{	
				try {
					transVal = commonLibBO.returnKeyLabelTrans(sc);
					connectFail= false;
				}
				catch (Exception e)
				{
					connectFail = sqlConnectionFailed(e,"System returnKeyLabelTrans retrieval exception");
				
				}
			}
			transMap.put("reporting.noDataRetrieved", transVal);
		    }

		    ReportUtils.addWhenNoData(jasperDesign, whenNoData,transMap);
		}
		//condition removed in order to addFieldProperty on 'my.dataFe' if the bccFe,toFe,...are empty
//		if(!fePropsMap.isEmpty())
//		{
		    ReportUtils.addGroupFields(jasperDesign, dynGrpMap, true, fePropsMap);
//		}
	    }
	    catch(DAOException e)
	    {
		log.error(e, "");
	    }
	}

	// add a property to each field in order to use it when creating the
	// insert statement
	if(ConstantsCommon.SQL_REP_FORMAT.equals(repFormat))
	{
	    ReportUtils.addFieldProperty(jasperDesign, new HashMap<String, String>());
	}
	// check language setting and if it's arabic, mirror the design to
	// generate right-to-left arabic report
	if(ConstantsCommon.LANGUAGE_ARABIC.equalsIgnoreCase(language) && "0".equals(currentReport.getLANG_INDEPENDENT_YN()))
	{
	    ReportUtils.mirrorLayout(jasperDesign, subrep, repFormat);// //to check
	// joseph
	}

	//update the image expression in jrxml
	try {
		
		ReportUtils.updateImageLocation(jasperDesign, getReportingImageURL(), "false",repFormat);
	}
	catch (Exception e)
	{
		log.error(e, e.getMessage());
	}

	// add report global param if it doesn't exist
	ReportUtils.addRepGlobalParam(jasperDesign);

	// check if we should remvoe the header and footer
	if(!showHeadFoot)
	{
	    ReportUtils.removeHeaderAndFooter(jasperDesign);
	}

	// compile a .jrxml file
	JasperCompileManager.compileReportToFile(jasperDesign, report + ".jasper");

	File reportFile = null;
	try {
		reportFile = new PathFileSecure(report + ".jasper");
		if(!reportFile.exists())
		{
		    throw new JRRuntimeException(report + ".jasper file not found.");
		}
	} catch (Exception e) {
		log.error(e, e.getMessage());
	}

	return reportFile;
    }

    private void retrievePropVal(JRTemplatePrintText txtFe, String txtVal, HashMap<String, String> propsMap)
    {
	if(txtFe.getPropertiesMap().getProperty("my.fromFe") != null && propsMap.get("my.fromFe") == null)
	{
	    propsMap.put("my.fromFe", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.toFe") != null && propsMap.get("my.toFe") == null)
	{
	    propsMap.put("my.toFe", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.toFeComp") != null && propsMap.get("my.toFeComp") == null)
	{
	    propsMap.put("my.toFeComp", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.toCif") != null && propsMap.get("my.toCif") == null)
	{
	    propsMap.put("my.toCif", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.ccFe") != null && propsMap.get("my.ccFe") == null)
	{
	    propsMap.put("my.ccFe", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.ccFeComp") != null && propsMap.get("my.ccFeComp") == null)
	{
	    propsMap.put("my.ccFeComp", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.bccFe") != null && propsMap.get("my.bccFe") == null)
	{
	    propsMap.put("my.bccFe", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.bccFeComp") != null && propsMap.get("my.bccFeComp") == null)
	{
	    propsMap.put("my.bccFeComp", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.attachFileFe") != null && propsMap.get("my.attachFileFe") == null)
	{
	    propsMap.put("my.attachFileFe", txtVal);
	}
	if(txtFe.getPropertiesMap().getProperty("my.dataFe") != null && propsMap.get("my.dataFe") == null)
	{
	    propsMap.put("my.dataFe", "1");
	}
	if(txtFe.getPropertiesMap().getProperty("my.pwdFe") != null && propsMap.get("my.pwdFe") == null)
	{
	    propsMap.put("my.pwdFe", "1");
	}
	
	String feName=txtFe.getPropertiesMap().getProperty("my.feName");
	if(feName != null && propsMap.get(feName) == null)
	{
	    propsMap.put(feName, txtVal);
	}
    }
    
    public Long generateId()
    {
	Calendar cal = Calendar.getInstance();
	return cal.getTimeInMillis();
    }

    public void createDynamicRowDataJrxml(String report, IRP_AD_HOC_REPORTCO reportCO)
    {
	try
	{

	    JasperDesign jasperDesign = JRXmlLoader.load(report + ".jrxml");
	    StringBuffer newJrxml = createRowDataJrxml(jasperDesign, reportCO);
	    createJRXMLFile(newJrxml.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING), report);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public StringBuffer createRowDataJrxml(JasperDesign jasperDesign, IRP_AD_HOC_REPORTCO reportCO)
    {
	String sqlString;
	JRField[] feLst;
	List<JRParameter> qryParams;
	JRDatasetParameter[] dsRunParams = null;
	List<JRParameter> repParams = jasperDesign.getParametersList();
	boolean isFreeForm = false;
	if(jasperDesign.getDatasetsList().size() == 0) // free form
	{
	    sqlString = jasperDesign.getQuery().getText();
	    feLst = jasperDesign.getFields();
	    qryParams = repParams;
	    isFreeForm = true;

	}
	else
	{
	    JRDesignDataset ds = (JRDesignDataset) jasperDesign.getDatasetsList().get(0);
	    sqlString = ds.getQuery().getText();
	    feLst = ds.getFields();
	    qryParams = ds.getParametersList();

	    // return the dataSetRun parameters
	    JRSection detailsBand = jasperDesign.getDetailSection();
	    JRElement[] elements = null;
	    JRElement element;
	    JRDesignComponentElement compElem;
	    Component comp;
	    StandardTable tbl;
	    if(detailsBand != null)
	    {
		JRBand[] details = detailsBand.getBands();
		for(int i = 0; i < details.length; i++)
		{
		    elements = details[i].getElements();
		    for(int j = 0; j < elements.length; j++)
		    {
			element = elements[j];
			if(element instanceof JRDesignComponentElement)
			{
			    compElem = (JRDesignComponentElement) element;
			    comp = compElem.getComponent();
			    if(comp instanceof StandardTable)
			    {
				tbl = (StandardTable) comp;
				dsRunParams = tbl.getDatasetRun().getParameters();
			    }
			}
		    }
		}
	    }

	    /* end */
	}

	StringBuffer newJrxml = new StringBuffer("");

	// add imports
	newJrxml
		.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
			+ "<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" "
			+ "xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" "
			+ "xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" "
			+ "name=\""
			+ reportName
			+ "\" pageWidth=\"595\" pageHeight=\"842\" "
			+ "whenNoDataType=\"AllSectionsNoDetail\" columnWidth=\"555\" leftMargin=\"20\" rightMargin=\"20\" topMargin=\"20\" bottomMargin=\"20\" "
			+ ">" + "<property name=\"ireport.zoom\" value=\"1.0\"/>"
			+ "<property name=\"ireport.x\" value=\"0\"/>" + "<property name=\"ireport.y\" value=\"0\"/>"
			+ "<import value=\"com.path.vo.reporting.ReportParamsCO\"/>"
			+ "<import value=\"com.path.bo.reporting.common.jreports.JrDataFunc\"/>"
			+ "<import value=\"com.path.lib.common.util.DateUtil\"/>"
			+ "<import value=\"com.path.lib.common.util.NumberUtil\"/>"
			+ "<import value=\"com.path.bo.reporting.common.jreports.JrFunc\"/>"
			+ "<import value=\"com.path.lib.common.util.StringUtil\"/>"
			+ "<import value=\"com.path.bo.reporting.common.jreports.JrGlobal\"/>");
	// add subDataSet
	newJrxml.append("<subDataset name=\"" + reportName + "_0\" >");
	// add subDataSet parameters
	newJrxml.append(createRowDataJrxmlParams(qryParams));

	// add sqlString
	newJrxml.append("<queryString><![CDATA[" + sqlString + "]]></queryString>");
	// add fields
	JRField fe;
	StringBuffer jrxmlCols = new StringBuffer("");
	for(int i = 0; i < feLst.length; i++)
	{
	    fe = feLst[i];
	    reportCO.getJasperDesignFieldsMap().put(fe.getName(), fe.getValueClassName());
	    newJrxml.append("<field name=\"" + fe.getName() + "\" class=\"" + fe.getValueClassName() + "\"></field>");
	    jrxmlCols = createRowDataJrxmlColumn(fe, jrxmlCols);
	}
	// close subDataSet tag
	newJrxml.append("</subDataset>");
	// add subDataSet parameters
	newJrxml.append(createRowDataJrxmlParams(repParams));
	// add queryString
	newJrxml.append("<queryString language=\"SQL\"><![CDATA[SELECT 1 FROM DUAL]]></queryString>");
	// add backGround
	newJrxml.append("<background><band splitType=\"Stretch\"/></background>");
	// add detail band
	newJrxml
		.append("<detail>"
			+ "<band height=\"165\" splitType=\"Stretch\">"
			+ "<componentElement>"
			+ "<reportElement  key=\"table 2\" x=\"0\" y=\"0\" width=\"500\" height=\"112\"/>"
			+ "<jr:table xmlns:jr=\"http://jasperreports.sourceforge.net/jasperreports/components\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd\" whenNoDataType=\"AllSectionsNoDetail\">"
			+ "<datasetRun subDataset=\"" + reportName + "_0\" >");
	// add dsRunParams
	newJrxml.append(createRowDataJrxmlDsRunParams(repParams, dsRunParams, isFreeForm));
	newJrxml.append("<connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression>"
		+ "</datasetRun>");
	// create the columns
	newJrxml.append(jrxmlCols);

	newJrxml.append("</jr:table>" + "</componentElement>" + "</band>" + "</detail>" + "</jasperReport>");
	return newJrxml;
    }

    public StringBuffer createRowDataJrxmlParams(List<JRParameter> paramsLst)
    {
	StringBuffer sb = new StringBuffer("");
	JRParameter param;
	String defaultValueExpression;
	for(int i = 0; i < paramsLst.size(); i++)
	{
	    param = paramsLst.get(i);
	    defaultValueExpression="";
	    if(param.getDefaultValueExpression()!=null)
	    {
		defaultValueExpression=param.getDefaultValueExpression().getText();
	    }
	    if(!param.isSystemDefined() && !param.getValueClass().equals(ReportParamsCO.class)
		    && !("SUBREPORT_DIR".equals(param.getName())))
	    {
		sb.append("<parameter name=\"" + param.getName() + "\" class=\"" + param.getValueClassName() + "\">"
			+ "<defaultValueExpression><![CDATA[" + defaultValueExpression
			+ "]]></defaultValueExpression>" + "</parameter>");
	    }
	}
	return sb;

    }

    public StringBuffer createRowDataJrxmlDsRunParams(List<JRParameter> repParams, JRDatasetParameter[] dsRunParams,
	    boolean isFreeForm)
    {
	StringBuffer sb = new StringBuffer("");
	// the dsRunParams will be the repParams
	if(isFreeForm)
	{
	    JRParameter param;
	    for(int i = 0; i < repParams.size(); i++)
	    {
		param = repParams.get(i);
		if(!param.isSystemDefined() && !param.getValueClass().equals(ReportParamsCO.class)
			&& !("SUBREPORT_DIR".equals(param.getName())))
		{
		    sb.append("<datasetParameter name=\"" + param.getName() + "\">"
			    + "<datasetParameterExpression><![CDATA[$P{" + param.getName()
			    + "}]]></datasetParameterExpression>" + "</datasetParameter>");
		}
	    }
	}
	else
	{
	    if(dsRunParams != null)
	    {
		JRDatasetParameter dsParam;
		for(int i = 0; i < dsRunParams.length; i++)
		{
		    dsParam = dsRunParams[i];
		    if(!RepConstantsCommon.repParamsCO_arg.equals(dsParam.getName()))
		    {
			sb.append("<datasetParameter name=\"" + dsParam.getName() + "\">"
				+ "<datasetParameterExpression><![CDATA[" + dsParam.getExpression().getText()
				+ "]]></datasetParameterExpression>" + "</datasetParameter>");
		    }
		}
	    }

	}
	return sb;
    }

    public StringBuffer createRowDataJrxmlColumn(JRField fe, StringBuffer jrxmlCols)
    {
	jrxmlCols.append("<jr:column width=\"165\" >");
	if(showHeadFoot)
	{
	    jrxmlCols
		    .append("<jr:columnHeader height=\"37\" rowSpan=\"1\">"
			    + "<textField isStretchWithOverflow=\"true\">"
			    + "<reportElement  x=\"23\" y=\"9\" width=\"116\" height=\"17\"/>"
			    + "<textElement textAlignment=\"Center\">"
			    + "<font fontName=\"Arial\" pdfFontName=\"arial.ttf\" pdfEncoding=\"Identity-H\" isPdfEmbedded=\"true\"/>"
			    + "</textElement>" + "<textFieldExpression><![CDATA[\"" + fe.getName()
			    + "\"]]></textFieldExpression>" + "</textField>" + "</jr:columnHeader>");
	}

	jrxmlCols
		.append("<jr:detailCell height=\"37\" rowSpan=\"1\">"
			+ "<textField isStretchWithOverflow=\"true\" isBlankWhenNull=\"true\">"
			+ "<reportElement  x=\"23\" y=\"9\" width=\"116\" height=\"17\"/>"
			+ "<textElement textAlignment=\"Center\">"
			+ "<font fontName=\"Arial\" pdfFontName=\"arial.ttf\" pdfEncoding=\"Identity-H\" isPdfEmbedded=\"true\"/>"
			+ "</textElement>" + "<textFieldExpression><![CDATA[$F{" + fe.getName()
			+ "}]]></textFieldExpression>" + "</textField>" + "</jr:detailCell>" + "</jr:column>");
	return jrxmlCols;
    }

    public StringBuffer createResultSql(JasperPrint jasperPrint, StringBuffer sbInsert,
	    LinkedHashMap<String, String> jasDesMap) throws BaseException
    {
	// to store the value statement
	StringBuffer sbVal = new StringBuffer(sbInsert);
	sbVal.append(" VALUES(");
	// to store the final statement
	StringBuffer sbSql = new StringBuffer("");
	//Collection feTypeLst = new ArrayList<String>();
	Collection feTypeLst = jasDesMap.values();
	int feSize = feTypeLst.size();
	int cnt = 0;
	String feType;

	List<JRPrintElement> lstElems;
	String txtVal;
	JRPrintElement elem;
	JRTemplatePrintText txtFe;
	JRPrintPage page;
	Object obj;
	JRTemplatePrintFrame frame;
	JRTemplatePrintFrame frame2;
	Object frame2Obj;
	List frame2Elem;
	List<JRPrintElement> lstTblElems;
	try
	{
	    int pg = jasperPrint.getPages().size();
	    for(int i = 0; i < pg; i++)
	    {
		page = jasperPrint.getPages().get(i);
		lstElems = page.getElements();
		for(int j = 0; j < lstElems.size(); j++)
		{
		    elem = lstElems.get(j);
		    if(elem instanceof JRTemplatePrintFrame)
		    {
			frame = (JRTemplatePrintFrame) elem;
			lstTblElems = frame.getElements();
			for(int k = 0; k < lstTblElems.size(); k++)
			{
			    obj = lstTblElems.get(k);
			    if(obj instanceof JRTemplatePrintFrame)
			    {
				frame2 = (JRTemplatePrintFrame) obj;
				frame2Elem = frame2.getElements();
				for(int l = 0; l < frame2Elem.size(); l++)
				{
				    frame2Obj = frame2Elem.get(l);
				    if(frame2Obj instanceof JRTemplatePrintText)
				    {
					txtFe = (JRTemplatePrintText) frame2Obj;
					 if(txtFe.getValue() instanceof java.lang.String)
					    {
						txtVal = (String)txtFe.getValue();
					    }
					    else
					    {
						txtVal = txtFe.getFullText();
					    }
					// note that the below code is repeated
					// in this class since some parameters
					// as 'cnt' is not changed by reference
					// so we couldn't create a separate
					// function to be called once
					if(txtFe.getPropertiesMap().getProperty("my.dataFe") != null)
					{
					    if(cnt < feSize)
					    {
						feType = (String) jasDesMap.values().toArray()[cnt];
						if(RepConstantsCommon.VARCHAR_TYPE_JASPER.equals(feType)
							|| RepConstantsCommon.DATE_TYPE_JASPER.equals(feType))
						{
						    sbVal.append("'");
						    sbVal.append(txtVal);
						    sbVal.append("'");
						    sbVal.append(",");
						}
						else
						{
						    sbVal.append(txtVal);
						    sbVal.append(",");
						}

					    }
					    cnt++;
					    if(cnt == feSize)
					    {
						cnt = 0;

						sbVal = new StringBuffer(sbVal.substring(0, sbVal.length() - 1));
						sbVal.append(");\n");
						sbSql.append(sbVal);

						// re-initialize the sbVal
						sbVal = new StringBuffer(sbInsert);
						sbVal.append(" VALUES(");

					    }
					}
				    }
				}
			    }
			}
		    }
		}
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return sbSql;
    }

    // checks if the DB is down
	private boolean sqlConnectionFailed(Exception e, String msg)throws InterruptedException 
	{

		if (e instanceof SQLException) 
		{
			int errorCode = ((SQLException) e).getErrorCode();			
			if (errorCode == 0 || errorCode == 17002)
			{
				Thread.sleep(2000);
				log.error(e, "Failed Database Connection.");
				return true;
			} 
			else 
			{
				log.error(e, msg);
				return false;
			}
		}
		else 
		{
			log.error(e, msg);
			return false;
		}

}   
	 /**
	     * Manipulates a PDF file src with the file dest as result
	     * @param src the original PDF
	     * @param dest the resulting PDF
	     * @throws IOException
	     * @throws DocumentException
	     */
	    public void encryptPdf(String src, String dest, String pwd) throws Exception {
	    	PdfReader reader = new PdfReader(src);
	        FileOutputStream fs = new FileOutputStream(dest, true);
	        PdfStamper stamper = new PdfStamper(reader, fs);
	        byte[] USER = pwd.getBytes(FileUtil. DEFAULT_FILE_ENCODING);
	        /** Owner password. */
	        byte[] OWNER = pwd.getBytes(FileUtil. DEFAULT_FILE_ENCODING);
	     
	        stamper.setEncryption(USER, OWNER,
	        PdfWriter.ALLOW_COPY, PdfWriter.ENCRYPTION_AES_128);        
	        stamper.close();
	        reader.close();
	    }
	    
    /**
     * this method will set the configuration of the exporter based on the
     * report format
     * 
     * @param exporter the report exporter
     * @throws Exception
     */
    public void setExporterConfiguration(JRAbstractExporter exporter, int fromPg, int toPg) throws Exception
    {
	if(format.equalsIgnoreCase(ConstantsCommon.DOC_REP_FORMAT))
	{
	    SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
	    configuration.setFlexibleRowHeight(true);
	    configuration.setStartPageIndex(fromPg);
	    configuration.setEndPageIndex(toPg);
	    exporter.setConfiguration(configuration);
	}
	else if(format.equalsIgnoreCase(ConstantsCommon.XLS_REP_FORMAT))
	{

	    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
	    configuration.setOnePagePerSheet(false);
	    configuration.setDetectCellType(true);
	    configuration.setWhitePageBackground(false);
	    configuration.setRemoveEmptySpaceBetweenRows(true);
	    configuration.setCollapseRowSpan(true);
	    configuration.setRemoveEmptySpaceBetweenColumns(true);
	    configuration.setStartPageIndex(fromPg);
	    configuration.setEndPageIndex(toPg);
	    exporter.setConfiguration(configuration);
	}

	else if(format.equalsIgnoreCase(ConstantsCommon.HTML_REP_FORMAT))
	{
	    if(ConstantsCommon.LANGUAGE_ARABIC.equalsIgnoreCase(language) && "0".equals(currentReport.getLANG_INDEPENDENT_YN()))
	    {
		SimpleHtmlExporterConfiguration htmlExpConf = new SimpleHtmlExporterConfiguration();
		htmlExpConf
			.setHtmlFooter("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><style>body {direction:rtl}</style>");
		exporter.setConfiguration(htmlExpConf);
	    }

	    SimpleHtmlReportConfiguration config = new SimpleHtmlReportConfiguration();
	    config.setStartPageIndex(fromPg);
	    config.setEndPageIndex(toPg);
	    exporter.setConfiguration(config);
	}

	else if(format.equalsIgnoreCase(ConstantsCommon.CSV_REP_FORMAT))
	{
	    SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
	    if("\\t".equals(csvSep))
	    {
		configuration.setFieldDelimiter("\t");
	    }
	    else
	    {
		configuration.setFieldDelimiter(csvSep);
	    }
	    exporter.setConfiguration(configuration);

	    SimpleCsvReportConfiguration config = new SimpleCsvReportConfiguration();
	    config.setStartPageIndex(fromPg);
	    config.setEndPageIndex(toPg);
	    exporter.setConfiguration(config);
	}
	else if(format.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT))
	{
		 SimplePdfReportConfiguration configuration = new SimplePdfReportConfiguration();
		 configuration.setStartPageIndex(fromPg);
		 configuration.setEndPageIndex(toPg);
		 exporter.setConfiguration(configuration);
	}
    }
    
    /**
     * Adding a password to the generated report in pdf format
     * @param src which is the report bytes
     * @param pwd
     * @return encrypted report
     * @throws Exception
     */
    public byte[] encryptPdfFromBytes(byte[] src,String pwd) throws Exception {
	PdfReader reader = new PdfReader(src);
	ByteArrayOutputStream baos = new ByteArrayOutputStream();
	PdfStamper stamper = new PdfStamper(reader, baos);
	byte[] USER = pwd.getBytes(FileUtil.DEFAULT_FILE_ENCODING);
	byte[] OWNER = pwd.getBytes(FileUtil.DEFAULT_FILE_ENCODING);
	stamper.setEncryption(USER, OWNER, PdfWriter.ALLOW_COPY, PdfWriter.ENCRYPTION_AES_128);
	stamper.close();
	reader.close();
	return baos.toByteArray();
    }
    
    
    
    /**
     * method to save the splitted report data under the repository based on the location specified in the 'fileName' input
     * the location could be a folder hierarchy '/' separated
     * @param propsMap to retrieve the fileName
     * @param reportBytes
     * @param fileName :the original file name
     * @param repository location
     * @param ext : file extension
     * @return 
     * @throws Exception
     */
    public void writeSplitedRepUnderRepoistory(HashMap<String, String> propsMap, byte[] reportBytes,String fileName, String repositoryPath,String ext)throws Exception
    {
	String attachFileFe=propsMap.get("my.attachFileFe");
	String fileLocation = (attachFileFe == null  || attachFileFe.indexOf("/")==-1 ? RepConstantsCommon.SNAPSHOT_LOCATION
		:attachFileFe);
	String zFileName=attachFileFe==null?fileName:attachFileFe;
	if (!RepConstantsCommon.SNAPSHOT_LOCATION.equals(fileLocation))
	{
	    zFileName=fileLocation.substring(fileLocation.lastIndexOf("/")+1);
	    fileLocation=RepConstantsCommon.SCHED_SPLIT_LOCATION + "/" +fileLocation.substring(0,fileLocation.lastIndexOf("/"));
	    FileUtil.makeDirectories(repositoryPath + "/" +fileLocation);
	}
	FileOutputStream fos = new FileOutputStream(repositoryPath+ "/" + fileLocation + "/" + zFileName + "." + ext);
	fos.write(reportBytes);
	fos.close();
    }
}
