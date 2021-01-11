package com.path.bo.reporting.common.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;

import org.apache.commons.lang.StringEscapeUtils;

import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRAbstractExporter;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRDatasetParameter;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
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
import net.sf.jasperreports.engine.export.JRPdfExporterParameter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.export.MapHtmlResourceHandler;
import net.sf.jasperreports.engine.export.data.DateTextValue;
import net.sf.jasperreports.engine.export.oasis.JROdsExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporter;
import net.sf.jasperreports.engine.export.ooxml.JRDocxExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRTemplatePrintFrame;
import net.sf.jasperreports.engine.fill.JRTemplatePrintText;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleCsvExporterConfiguration;
import net.sf.jasperreports.export.SimpleDocxReportConfiguration;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleHtmlExporterConfiguration;
import net.sf.jasperreports.export.SimpleHtmlExporterOutput;
import net.sf.jasperreports.export.SimpleHtmlReportConfiguration;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;
import net.sf.jasperreports.export.SimplePdfReportConfiguration;
import net.sf.jasperreports.export.SimplePrintServiceExporterConfiguration;
import net.sf.jasperreports.export.SimpleWriterExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.CommonReportingBO;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.common.util.FileTransformation;
import com.path.bo.reporting.common.util.ReportUtils;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.HyperlinksBO;
import com.path.bo.reporting.designer.ImageBO;
import com.path.bo.reporting.designer.ProceduresBO;
import com.path.bo.reporting.designer.SnapShotBO;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dao.reporting.common.ReportDAO;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.dbmaps.vo.IRP_REPORT_EXEC_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_PAGINATIONVOWithBLOBs;
import com.path.dbmaps.vo.IRP_REP_METADATA_LOGVO;
import com.path.dbmaps.vo.IRP_SNAPSHOT_SUB_REPORTVO;
import com.path.dbmaps.vo.IRP_VERIFIED_REPORTSVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.log.Log;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.struts2.lib.common.BaseObject;
import com.path.struts2.lib.common.ConnectionCO;
import com.path.vo.common.CommonLibSC;
import com.path.vo.common.DBSequenceSC;
import com.path.vo.reporting.DesignerGridSC;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_HYPERLINKSCO;
import com.path.vo.reporting.IRP_REP_PROCCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.ReportOutputCO;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.designer.IRP_REP_PROCSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_MODIFY_COLUMNCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;

public class ReportBOImpl extends BaseBO implements ReportBO
{

    private ReportDAO reportDAO;
    private FcrBO fcrBO;
    private SnapShotBO snapShotBO;
    private CommonRepFuncBO commonRepFuncBO;
    private ProceduresBO procBO;
    private DesignerBO designerBO;
    private String reportingPortalURL;
    private CommonLookupBO commonLookupBO;
    private HyperlinksBO hyperlinksBO;
    private SnapshotParameterBO snapshotParameterBO;
    private String reportingImageURL;
    private ImageBO imageBO;
    private CommonReportingBO commonReportingBO;
    
    public void setCommonReportingBO(CommonReportingBO commonReportingBO)
    {
        this.commonReportingBO = commonReportingBO;
    }

    public void setImageBO(ImageBO imageBO)
    {
        this.imageBO = imageBO;
    }

    
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
    
    public SnapshotParameterBO getSnapshotParameterBO()
    {
        return snapshotParameterBO;
    }

    public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO)
    {
        this.snapshotParameterBO = snapshotParameterBO;
    }

    public void setHyperlinksBO(HyperlinksBO hyperlinksBO)
    {
	this.hyperlinksBO = hyperlinksBO;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public FcrBO getFcrBO()
    {
	return fcrBO;
    }

    public void setFcrBO(FcrBO fcrBO)
    {
	this.fcrBO = fcrBO;
    }

    public SnapShotBO getSnapShotBO()
    {
	return snapShotBO;
    }

    public void setSnapShotBO(SnapShotBO snapShotBO)
    {
	this.snapShotBO = snapShotBO;
    }

    public CommonRepFuncBO getCommonRepFuncBO()
    {
	return commonRepFuncBO;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }

    public ProceduresBO getProcBO()
    {
	return procBO;
    }

    public void setProcBO(ProceduresBO procBO)
    {
	this.procBO = procBO;
    }

    public DesignerBO getDesignerBO()
    {
	return designerBO;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
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

    public ReportDAO getReportDAO()
    {
	return reportDAO;
    }

    public void setReportDAO(ReportDAO reportDAO)
    {
	this.reportDAO = reportDAO;
    }
    
    /**
     * returning user name for informative details
     * @param request
     * @return
     */
    private static String returnUserFromSession(IRP_AD_HOC_REPORTCO reportSession,String usrName,String var_reportName)
    {
    	String userDetails= "UNKNOWN";
    	try
    	{
    		if(reportSession != null)
			{
    			userDetails = DateUtil.format(Calendar.getInstance().getTime(), "dd_MM_yyyy_hh_mm_ss").concat("-")
						.concat(String.valueOf(usrName)).concat("-")
						.concat(String.valueOf(reportSession.getCOMP_CODE())).concat("-")
						.concat(String.valueOf(reportSession.getBRANCH_CODE())).concat("-")
						.concat(String.valueOf(reportSession.getAPP_NAME())).concat("-")
						.concat(String.valueOf(reportSession.getREPORT_ID())).concat("-")
						.concat(String.valueOf(var_reportName)).concat("-")
						.concat(String.valueOf(reportSession.getPROG_REF()));

			}
    		else
    		{
    			userDetails = DateUtil.format(Calendar.getInstance().getTime(), "dd_MM_yyyy_hh_mm_ss").concat("-")
						.concat(String.valueOf(usrName)).concat("-")
						.concat(String.valueOf(var_reportName));
    		}
    	}
    	catch(Exception e)
    	{
    		 log.error(e,"Error in Returning UserName from Session");
    	}
    	return userDetails;
    }

    public ReportOutputCO printReport(String var_reportName, String reportFormat, Map parameters,
	    String saveCopyLocation, String menu, HashMap<String,Object> repSessionCOMap, int decimalPts, String appName,
	    String usrName, String language, String printerName, BigDecimal dbConn, boolean noHeadAndFoot,
	    String csvSepartor, String noData, BigDecimal fromPage, BigDecimal toPage, String origFormat,
	    String fromMenu, HashMap<String,String> transMsgLangMap) throws BaseException, Exception
    {
	IRP_AD_HOC_REPORTCO reportSession = (IRP_AD_HOC_REPORTCO) PathPropertyUtil.convertToObject(repSessionCOMap, IRP_AD_HOC_REPORTCO.class);
    	ReportParamsCO repParamsCO = (ReportParamsCO) (parameters.get(RepConstantsCommon.repParamsCO_arg));
    	//insert into IRP_REPORT_EXEC_LOG table
    	
    	BigDecimal sourceId = null;
    	if(parameters.containsKey(RepConstantsCommon.REP_PARAM_SOURCE_ID))
    	{
    		sourceId = (BigDecimal) parameters.get(RepConstantsCommon.REP_PARAM_SOURCE_ID);
    		parameters.remove(RepConstantsCommon.REP_PARAM_SOURCE_ID);
    	}
    	
    	String retrieveCall = (String) parameters.get("retrieveCall");
    	parameters.remove("retrieveCall");
    	
    	IRP_REPORT_EXEC_LOGVO repExecLogParamsVO = new IRP_REPORT_EXEC_LOGVO();
    	String reportExecLog = PathPropertyUtil.getPathRemotingProp("PathRepRemoting", "reporting.reportExec.log");
    	log.info(">>>>>>>>>>> reporting.reportExec.log = "+reportExecLog);	
    	if(!"true".equals(noData) && BigDecimal.ONE.toPlainString().equals(reportExecLog))
	    {
	    	String executionLogParams="";
	    	repExecLogParamsVO.setUSER_ID(repParamsCO.getUserName());
	    	repExecLogParamsVO.setCOMP_CODE(reportSession.getCOMP_CODE());
	    	repExecLogParamsVO.setBRANCH_CODE(reportSession.getBRANCH_CODE());
	    	repExecLogParamsVO.setREPORT_APP_NAME(reportSession.getAPP_NAME());
	    	repExecLogParamsVO.setAPP_NAME(repParamsCO.getCurrentAppName());
	    	repExecLogParamsVO.setREPORT_PROG_REF(reportSession.getPROG_REF());
	    	repExecLogParamsVO.setHTTP_SESSION(repParamsCO.getHttpSessionIdForLink());
	    	repExecLogParamsVO.setEXEC_START_TIME(Calendar.getInstance().getTime());
	    	
	    	if(!NumberUtil.isEmptyDecimal(sourceId))
	    	{
	    		repExecLogParamsVO.setSOURCE_ID(sourceId);
	    	}
	    	if(!NumberUtil.isEmptyDecimal(reportSession.getREPORT_ID()))
	    	{
	    		repExecLogParamsVO.setREPORT_ID(reportSession.getREPORT_ID());
	    	}
	    	if(!NumberUtil.isEmptyDecimal(dbConn))
	    	{
	    		repExecLogParamsVO.setCONN_ID(dbConn);
	    	}
	    	if(parameters.toString().length() > 1000)
	    	{
	    	    executionLogParams = parameters.toString().substring(1, 1000);
	    	    repExecLogParamsVO.setREPORT_PARAMS(executionLogParams);
	    	}
	    	else
	    	{
	    	    executionLogParams = parameters.toString();
	    	    repExecLogParamsVO.setREPORT_PARAMS(executionLogParams.substring(executionLogParams.indexOf("{")+1,executionLogParams.lastIndexOf("}")));
	    	}
	    	designerBO.insertReportExecLog_NewTrans(repExecLogParamsVO);
	    }
	
    String currentThreadName = StringUtil.nullToEmpty(Thread.currentThread().getName());	
   
    	
	BigDecimal repPrintPdf=(BigDecimal) parameters.get(ConstantsCommon.REP_PRINT_PDF)==null?BigDecimal.ZERO:BigDecimal.ONE;
	parameters.remove(ConstantsCommon.REP_PRINT_PDF);
	BigDecimal p_c_nb=(BigDecimal) parameters.get(ConstantsCommon.REP_NBCOPIES_PRINT)==null?BigDecimal.ONE:(BigDecimal) parameters.get(ConstantsCommon.REP_NBCOPIES_PRINT);
	parameters.remove(ConstantsCommon.REP_NBCOPIES_PRINT);
	String d_p = (String) parameters.get("d_p");
	parameters.remove("d_p");
	String autoPrint= (String) parameters.get("a_p");
	String showPrintPreview= parameters.get("showPrintPreview")==null?ConstantsCommon.NO: (String) parameters.get("showPrintPreview");
	parameters.remove("a_p");
	parameters.remove("showPrintPreview");
	String r_e_cm = (String) parameters.get("r_e_cm");
	String r_e_im = (String) parameters.get("r_e_im");
	String r_e_nm = (String) parameters.get("r_e_nm");
	parameters.remove("r_e_cm");
	parameters.remove("r_e_im");
	parameters.remove("r_e_nm");

	File reportFile = null;
	Connection con = null;
	
	Thread.currentThread().setName(currentThreadName.concat("_").concat(returnUserFromSession(reportSession, usrName, var_reportName)));
	try
	{
	

	if(dbConn.compareTo(BigDecimal.ZERO ) > 0)
	{
		ConnectionCO connectCO = commonLookupBO.returnConnection(dbConn);
		con = DriverManager.getConnection(connectCO.getDbURL(),connectCO.getDbUserName(), connectCO.getDbPassword());
		repParamsCO.setConnCO(connectCO);
	}
	else
	{
	    con = reportDAO.getConnection();
	}
	}
	catch(Exception e)
	{
		Thread.currentThread().setName(currentThreadName);
	    throw new BOException(MessageCodes.DATABASE_CONNECTION_ERROR,new String[]{ reportSession.getPROG_REF(),reportSession.getAPP_NAME(),e.getMessage()},e);
	}
	String repositoryPath= ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	try
	{
	    String report= repositoryPath + "/" + var_reportName;
	    // create a temporarily folder to put all jrxml files
	    FileUtil.makeDirectories(report + "/");
	    con.setAutoCommit(false);
	    // create hash Table for procedures for Sybase
	    if(commonLibBO.returnIsSybase() == 1 && reportSession.getHashTblList().size() > 0)
	    {
		try
		{
		procBO.createHashTbl(con, reportSession.getHashTblList());
		}
		catch(Exception e)
		{
		    throw new BaseException("Error occured when creating table hash for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		}
		
	    }

	    HashMap<String, String> hypParamsValMap = null;
	    if(!menu.endsWith(ConstantsCommon.DESIGNER_PROG_REF))
	    {
		// create jrxml file under repository
		createJRXMLFile(reportSession.getJRXML_FILE(), report);
	    }
	    // fill the hpyerlinks params value
	    if(reportSession.getREPORT_ID() != null)
	    {
		hypParamsValMap = retHypParamsVal(reportSession, repParamsCO);
	    }
	    //

	    // compile .jrxml file into .jasper
	    String wp = (String) parameters.get("w_p");
//	    try
//	    {
		reportFile = compileReport(report, menu, reportFormat, reportSession, language,
			hypParamsValMap, noHeadAndFoot, noData, repParamsCO, null, saveCopyLocation, wp,reportSession.getWHEN_NO_DATA());// add
		// null
		// here
		// main
		// report
//	    }
//	    catch(Exception e)
//	    {
//		log.error(e, "Error in compileReport() method in ReportBOImpl.java");
//		//throw new BOException("report.compile.exceptionMsg._key");
//		throw new BOException(e.getMessage());
//	    }
	    //

	    // call Before procedures
	    if(!"true".equals(noData) && ((ConstantsCommon.TRUE.equalsIgnoreCase(retrieveCall)
		    && "0".equals(reportSession.getEXEC_PRC_ON_RETRIEVE_YN()))
		    || (!ConstantsCommon.TRUE.equalsIgnoreCase(retrieveCall))))
	    {
		HashMap<String, Object> outProcValuesMap = new HashMap<String, Object>();
		outProcValuesMap = callProcedures(BigDecimal.ZERO, parameters, reportSession, menu, con, transMsgLangMap);
		
		/*put the argName of the report arguments as key in a hashMap in order to check later if the outProcParam exist in the argList with @ or without
		 * it is needed to support the reports uploaded from R14.0.1 where the user was obliged to write the argument name with @ in the jrxml to be
		 * the same as the procedure output parameter
		 */
		if(!outProcValuesMap.isEmpty())
		{
			HashMap<String,String> tempArgMap=new HashMap<String,String>();
			LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>  argMap=reportSession.getArgumentsList();
			IRP_REP_ARGUMENTSCO argCO;
			Iterator it =argMap.values().iterator();
			while(it.hasNext())
			{
				
				argCO=(IRP_REP_ARGUMENTSCO) it.next();
				tempArgMap.put(argCO.getARGUMENT_NAME(), argCO.getARGUMENT_NAME());
			}
			
			
			//Sending the procedure output parameter values as values to the input parameters of the procedure 
			String entryKey;
			for (Entry<String, Object> entry: outProcValuesMap.entrySet()) {
			    // Check if the current value is a key in the 2nd map
				entryKey = entry.getKey(); 
				if (commonLibBO.returnIsSybase() == 1 && tempArgMap.get(entryKey.replaceAll("@",""))!=null)
			    {
				entryKey = entry.getKey().replaceAll("@",""); 
			    }
			    parameters.put(entryKey,entry.getValue());
			}
		}
	    }

	    List<IRP_AD_HOC_REPORTCO> subReportCOList = new ArrayList<IRP_AD_HOC_REPORTCO>();

	    if(reportSession.getSubreportsList() != null && reportSession.getSubreportsList().size() > 0)
	    {
		generateSubReports(reportSession.getSubreportsList(), subReportCOList, hypParamsValMap, repParamsCO,
			decimalPts, reportFormat, language, noHeadAndFoot, noData, parameters, con, var_reportName,
			saveCopyLocation, wp);
		parameters.put("SUBREPORT_DIR", repositoryPath + "/" + var_reportName + "/");
	    }
	    JasperReport jasperReport=null;
	    try
	    {
		jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
	    }
	    catch(Exception e)
	    {
		throw throwException(e, "Error in loading the file for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage());
	    }
	    int pagesNbr = 0;
	    
//	    
////		parameters.put("isNew", "N");
//		parameters.put("isNew", "Y");
//		parameters.put("pathCnt", Integer.valueOf(-1));
//		ArrayList<HashMap<String,Object>> ar=new ArrayList<HashMap<String,Object>>();
//		parameters.put("pathArr", ar);
////		
//		
	    JasperPrint jasperPrint = null;
	    JasperPrint jasperPrint2 = null;
	    boolean hasData=false;
	    String repExt = "";
	    StringBuffer sitcomBuffer = new StringBuffer();
	    ReportOutputCO reportOutputCO = new ReportOutputCO();
	    try
	    {
	    if("true".equals(noData))
	    {
		jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource(0));
	    }
	    else
	    {
		//tracing for the executed report
		BaseObject baseObj=new BaseObject();
		baseObj.applyTraceProps(appName,usrName,reportSession.getPROG_REF(),repParamsCO.getHttpSessionIdForLink());
		genericDAO.callSqlSessionTrace(baseObj, "r:"+reportSession.getPROG_REF()+" ra:"+reportSession.getAPP_NAME(), con);
		    if(fromMenu == null && (ConstantsCommon.TXT_FILE.equals(reportFormat)|| ConstantsCommon.DAT_EXT.equals(reportFormat)))
		    {
			parameters.put(RepConstantsCommon.SITCOM_IS_NEW, RepConstantsCommon.Y);
			parameters.put(RepConstantsCommon.SITCOM_PATH_CNT, Integer.valueOf(-1));
			parameters.put(RepConstantsCommon.JASPER_PATH_ARR, new ArrayList<HashMap<String, Object>>());
		    }
		    Map<String, BigDecimal> subConnections = (HashMap<String, BigDecimal>) parameters.get("SUB_CON");
		    ConnectionCO connectCO = null;
		    Connection subcon = null;
		    if(subConnections!=null) {
		    	for (Map.Entry<String, BigDecimal> entry : subConnections.entrySet()) {
		    		connectCO = commonLookupBO.returnConnection(entry.getValue());
		    		subcon = DriverManager.getConnection(connectCO.getDbURL(),connectCO.getDbUserName(), connectCO.getDbPassword());
		    		parameters.put(entry.getKey(), subcon);
		    	}
		    }
		    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
		    HashMap<BigDecimal,BigDecimal> cifMap;
		    if(BigDecimal.ONE.compareTo(NumberUtil.nullToZero(reportSession.getCIF_AUDIT_YN()))==0)
		    {
			cifMap = retCifList(jasperPrint,reportSession);
			reportOutputCO.setCifMap(cifMap);
		    }
		    if(fromMenu == null && (ConstantsCommon.TXT_FILE.equals(reportFormat)|| ConstantsCommon.DAT_EXT.equals(reportFormat)))
		    {
			repExt = reportFormat;
			BigDecimal repSnpId = new BigDecimal(commonRepFuncBO.retMinRepSnapshotRepId(StringUtil
				    .isNotEmpty(reportSession.getFTR_OPT_PROG_REF()) ? reportSession
				    .getFTR_OPT_PROG_REF() : reportSession.getPROG_REF()));
			REP_SNAPSHOT_PARAMVO snpParVO = new REP_SNAPSHOT_PARAMVO();
			snpParVO.setREP_ID(repSnpId);
			String snpFrequency = ((REP_SNAPSHOT_PARAMVO) genericDAO.selectByPK(snpParVO))
				.getSNAPSHOT_FREQUENCY();
			Date retrieveDte = new Date();
			sitcomBuffer = commonRepFuncBO.generateSitcom(BigDecimal.ONE, reportSession.getAPP_NAME() , repSnpId,
				reportSession.getPROG_REF(), reportSession.getCOMP_CODE(), retrieveDte, snpFrequency,
				parameters,null);
			parameters.remove(RepConstantsCommon.SITCOM_IS_NEW);
			parameters.remove(RepConstantsCommon.SITCOM_PATH_CNT);
			parameters.remove(RepConstantsCommon.JASPER_PATH_ARR);
		    }
		    // bbe
		//check if report returns data
		hasData=((Boolean)parameters.get(RepConstantsCommon.REP_HAS_DATA)).booleanValue();
		if("1".equals(wp))
		{
		    jasperPrint2 = FileTransformation.removeWatermarkFromJP(jasperPrint);
		}
		pagesNbr = jasperPrint.getPages().size();
	    }
	    }
	    catch(Exception e)
	    {
		throw new BaseException("Error when filling the report for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
	    }
	 //   System.out.println("@@@@@@@@@@@@@@@@@@ ReportBOImpl @@@@@@@@ DATA @@@@@@    "+parameters.get("pathArr"));
    	    //save the snpashot for ftr and fcr reports
	    
	    if("Y".equals(parameters.get("isNew")))
	    {
		try
		    {
		  REP_SNAPSHOT_INFOVO repSnpInfoVO=new REP_SNAPSHOT_INFOVO();
		  String repSnpIdStr=(String)parameters.get("snp~repSnpId");
		  String snpIdStr=(String)parameters.get("snp~snpId");
		  repSnpInfoVO.setREP_ID(BigDecimal.valueOf(Double.parseDouble(snpIdStr)));
		  repSnpInfoVO.setDESCRIPTION((String)parameters.get("snp~snpDesc"));
		  repSnpInfoVO.setSNAPSHOT_USER(usrName);
		  String aod=(String)parameters.get("snp~aod");
		  SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy",Locale.ENGLISH);
		  repSnpInfoVO.setRETREIVE_DATE(sdfDate.parse(aod));
		  repSnpInfoVO.setJRXML_FILE(reportSession.getJRXML_FILE());
		  
		
		  
		  
		  
		  //convert the arrayList of data to bytes
		  ArrayList<HashMap<String,Object>> pathArr=(ArrayList<HashMap<String,Object>>)parameters.get("pathArr");
		  repSnpInfoVO.setREP_BLOB(CommonUtil.objectToBytes(pathArr));
		  //return the systyem date as PB
		  REP_SNAPSHOT_INFORMATIONCO infoCO=new REP_SNAPSHOT_INFORMATIONCO();
		  infoCO.setCOMPANY_CODE(reportSession.getCOMP_CODE());
		  infoCO.setBRCH_CODE(reportSession.getBRANCH_CODE());
		  infoCO.setAPPL_NAME(appName);
		  Date snpDate=snapshotParameterBO.retGvSystemDate(infoCO);
		  if(snpDate==null)
		  {
		      snpDate=commonLibBO.returnSystemDateNoTime();
		  }
		  repSnpInfoVO.setSNAPSHOT_DATE(snpDate);
		  
		  
//		  parameters.remove("repParamsCO");
		  HashMap pMap=new HashMap();
		  pMap.putAll(parameters);
		  pMap.remove("REPORT_FORMAT_FACTORY");
		  pMap.remove("lastRule");
		  pMap.remove("JASPER_REPORT");
		  pMap.remove("REPORT_TIME_ZONE");
		  pMap.remove("REPORT_CONNECTION");
		  pMap.remove("REPORT_PARAMETERS_MAP");
		  pMap.remove("isNew");
		  pMap.remove("pathCnt");
		  pMap.remove("pathArr");
		  pMap.remove("JASPER_REPORTS_CONTEXT");
		  
		  //saving the parameters of the snapshot
		  repSnpInfoVO.setSNAPSHOT_PARAMS(CommonUtil.objectToBytes(pMap));
		//mode update
		  IRP_SNAPSHOT_SUB_REPORTVO snpSubVO;
		
		if(repSnpIdStr==null || repSnpIdStr.isEmpty() ) 
		{
		    //get the next val of the sequence
			DBSequenceSC dbSeqSC = new DBSequenceSC();
			dbSeqSC.setSequenceName("FTR_SNAP_INFO_SEQ");
			dbSeqSC.setTableName("SNAPSHOT_INFO_IDENTITY");
			BigDecimal repSnpId = commonLibBO.returnTableSequence(dbSeqSC);
		    repSnpInfoVO.setREP_SNAPSHOT_ID(repSnpId);
		    //insert into DB
		    snapshotParameterBO.insertSnpInfo(repSnpInfoVO);
		}
		//mode new
		else
		{
		    repSnpInfoVO.setREP_SNAPSHOT_ID(BigDecimal.valueOf(Double.parseDouble(repSnpIdStr)));
		    repSnpInfoVO.setDECLARED_YN("N");
		    repSnpInfoVO.setDECLARED_DATE(null);
		    snapshotParameterBO.updateSnpInfo(repSnpInfoVO);
		    //delete from irp_snapshot_sub_report
		    snpSubVO = new IRP_SNAPSHOT_SUB_REPORTVO();
		    snpSubVO.setREP_SNAPSHOT_ID(repSnpInfoVO.getREP_SNAPSHOT_ID());
		    snapshotParameterBO.deleteSnpSubReportByRepSnpId(snpSubVO);
		}
		
		
		//insert into irp_snpashot_sub_report
		  /***************************************************/
		   insertSnpSubRep(reportSession,repSnpInfoVO);
		    /***************************************************/
		    }
		    catch(Exception e)
			{
			    throw new BaseException("Error when saving snapshot for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
			}
	    }
	    
	

	    // set the name of the file used to export the report into
	    Date executionDate;
	    try
	    {
	    executionDate= commonLibBO.returnSystemDateWithTime();
	    }
	    catch(Exception e)
	    {
		throw new BaseException("Error when getting system date with time for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
	    }
	    
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    byte[] reportBytes = null;
	    byte[] reportBytes2 = null;
	    String startExecution = DateUtil.format(executionDate, "yyyy-MM-dd HH-mm-ss aa");
	    String zRepName = menu/* reportSession.getPROG_REF() */+ "_" + reportSession.getAPP_NAME() + "_"
		    + usrName.replace(".", "") + "_" + startExecution;

	    String repFolderName = RepConstantsCommon.SNAPSHOT_LOCATION + "/" + zRepName;
	    boolean imgsCreated = false;
	    reportOutputCO.setHasData(hasData);
	    int mainRepPageNbr=jasperPrint.getPages().size();
	    // check which jasper to print (refer to guitta)
	    if(RepConstantsCommon.EMPTY_STRING.equals(printerName) ||(BigDecimal.ONE.compareTo(repPrintPdf) == 0)|| ConstantsCommon.REP_SERVICE_CALL.equals(fromMenu))  
	    {
		// use JRAbstractExporter class to export to pdf, html, excel,
		// doc
		JRAbstractExporter exporter = null;

		if(reportFormat.equalsIgnoreCase(ConstantsCommon.SQL_REP_FORMAT))
		{
		    // create the sql statments;
		    try
		    {
		    LinkedHashMap jasDesMap = reportSession.getJasperDesignFieldsMap();
		    StringBuffer sbVal = new StringBuffer("");
		    if(!jasDesMap.isEmpty())
		    {
			StringBuffer sbInsert = new StringBuffer("");
			sbInsert.append("INSERT INTO " + var_reportName + "(");
			Iterator it = jasDesMap.keySet().iterator();
			while(it.hasNext())
			{
			    sbInsert.append((String) it.next() + ",");
			}

			sbInsert = new StringBuffer(sbInsert.substring(0, sbInsert.length() - 1));

			sbInsert.append(")");
			sbVal = createResultSql(jasperPrint, sbInsert, jasDesMap);
		    }
		    reportBytes = sbVal.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
		    reportOutputCO.setOutputFormat(ConstantsCommon.SQL_REP_FORMAT);
		    repExt=ConstantsCommon.SQL_REP_FORMAT;
		    }
		    catch(Exception e)
		    {
			throw new BaseException("Error when exporting report to SQL format for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		    }
		}
		else if(reportFormat.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT))
		{
		    repExt = ConstantsCommon.PDF_EXT;
		    // JasperExportManager.exportReportToPdfFile(jasperPrint,
		    // report + ".pdf");
		    try
		    {
			exporter = new JRPdfExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
			exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
			SimplePdfReportConfiguration configuration = new SimplePdfReportConfiguration();
			int pages = jasperPrint.getPages().size();
			if(pages > 0)
			{
			    if(!NumberUtil.isEmptyDecimal(fromPage) && fromPage.intValue() > 0
				    && pages >= fromPage.intValue())
			    {
				configuration.setStartPageIndex(fromPage.intValue() - 1);
			    }
			    if(!NumberUtil.isEmptyDecimal(toPage) && toPage.intValue() > 0
				    && pages >= toPage.intValue())
			    {
				configuration.setEndPageIndex(toPage.intValue() - 1);
			    }
			}
			exporter.setConfiguration(configuration);
			
			if(BigDecimal.ONE.compareTo(repPrintPdf)==0)
			{
			    String printJS ="";
			    String finalPrintJS ="";

			    SimplePdfExporterConfiguration exporterConfiguration = new SimplePdfExporterConfiguration();
			    
			    if(autoPrint != null)
			    {
				if(ConstantsCommon.ONE.equals(autoPrint) || ConstantsCommon.TRUE.equals(autoPrint))
				{
				    if(ConstantsCommon.YES.equals(showPrintPreview))
				    {
				    	printJS = "var pp = this.getPrintParams(); pp.pageHandling=pp.constants.handling.fit; pp.NumCopies=" + p_c_nb+" ;var fv = pp.constants.flagValues;pp.flags |= (fv.setPageSize);  this.print(pp);";
				    }
				    else
				    {
					printJS = "var pp = this.getPrintParams(); " + "  pp.NumCopies=" + p_c_nb
						+ "; pp.interactive = pp.constants.interactionLevel.silent" 
						+ "; pp.pageHandling=pp.constants.handling.fit;var fv = pp.constants.flagValues;pp.flags |= (fv.setPageSize); this.print(pp);";
				    }
				}

			    }
			    else
			    {
			    	printJS = "var pp = this.getPrintParams(); pp.pageHandling=pp.constants.handling.fit;   pp.NumCopies=" + p_c_nb+" ;var fv = pp.constants.flagValues;pp.flags |= (fv.setPageSize);this.print(pp);";
			    }
			    
			    if(!StringUtil.isEmptyString(printJS))
			    {

				StringBuffer printJsBuffer = new StringBuffer();
				printJsBuffer.append(
					"var supported= true;  try { this.getPrintParams(); } catch(error) { supported = false};")
					.append("if(supported){").append(printJS).append("} else{ this.print();}");

				printJS = printJsBuffer.toString();
			    }
			    // if the report doesn't have data, check if report empty confirm/info message is send
			    // the priority is for confirm message
			    // if no data and none of the arguments sent, assign it without alert
			    // if there is data, assign it without alert
			    // nIcon: An icon type. Possible values are these:  0 , Error 1,Warning 2, Question 3, Status
			    // nType: A button group type. Possible values are these: 0,OK 1/ OK, Cancel 2 / Yes, No 3 / Yes, No, Cancel
			    if(!hasData && StringUtil.isNotEmpty(r_e_nm) && ConstantsCommon.TRUE.equals(r_e_nm))
			    {
			    	finalPrintJS = "";
			    }
			    else  if(!hasData && (StringUtil.isNotEmpty(r_e_cm) || StringUtil.isNotEmpty(r_e_im)))
			    {
				
				if(StringUtil.isNotEmpty(r_e_cm) && StringUtil.isNotEmpty(printJS))
				{
				    finalPrintJS = "var nButton = app.alert("
					    + "{ cMsg: '"+StringEscapeUtils.escapeJavaScript(r_e_cm)+"',"
					    + "nIcon: 2, nType: 1 });" + "if ( nButton == 1 ) {"+printJS+"}";
				}
				else if (StringUtil.isNotEmpty(r_e_im)) 
				{
				    finalPrintJS = "var nButton = app.alert("
					    + "{ cMsg: '"+StringEscapeUtils.escapeJavaScript(r_e_im)+"',"
					    + "nIcon: 1, nType: 0 });";
				}
			    }
			    else
			    {
				finalPrintJS = printJS;
			    }
			    
			    exporterConfiguration.setPdfJavaScript(finalPrintJS);
			    exporter.setConfiguration(exporterConfiguration);
			}
			exporter.exportReport();
			reportBytes = byteArray.toByteArray();

//			reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
			if("1".equals(wp))
			{
			    reportBytes2 = JasperExportManager.exportReportToPdf(jasperPrint2);
			}
		    }
		    catch(Exception e)
		    {
			throw new BaseException("Error when exporting report to PDF format for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		    }
		    reportOutputCO.setOutputFormat(ConstantsCommon.PDF_REP_FORMAT);
		}
		else if(reportFormat.equalsIgnoreCase(ConstantsCommon.DOC_REP_FORMAT))
		{
		    try
		    {
		    repExt = ConstantsCommon.DOC_EXT;
		    exporter = new JRDocxExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
		    SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
		    configuration.setFlexibleRowHeight(true);
//		    exporter.setParameter(JRDocxExporterParameter.CHARACTER_ENCODING, CommonUtil.ENCODING); ??????
		    exporter.setConfiguration(configuration);
		    exporter.exportReport();
		    reportBytes = byteArray.toByteArray();
		    reportOutputCO.setOutputFormat(ConstantsCommon.DOC_REP_FORMAT);
		    }
		    catch(Exception e)
		    {
			throw new BaseException("Error when exporting report to DOCX format for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		    }
		}
		
		else if(reportFormat.equalsIgnoreCase(ConstantsCommon.XLS_REP_FORMAT))
		{
		    try
		    {
//			repExt = ConstantsCommon.XLS_EXT;
			// check if REPORT_TYPE==1 ->FCR report ->Ext = xls
		    SimpleDateFormat sf = new SimpleDateFormat();
		    log.debug("REP: Date pattern used in SimpleDateFormat is: " + sf.toPattern());
		    // add date format for excel raw data ( The patch delivered to BB was delivered with format dd/MM/yyyy hh:mm , 
		    // but it will cause issues so it is fixed here to dd/MM/yyyy HH:mm)
		    Map<String, String> dateFormats = new HashMap<String, String>();
		    dateFormats.put("M/d/yy h:mm a", "dd/MM/yyyy HH:mm");
		    dateFormats.put("dd/mm/yy hh:mm", "dd/MM/yyyy HH:mm");
		    dateFormats.put("dd/MM/yy hh:mm", "dd/MM/yyyy HH:mm");
		    dateFormats.put("DD/MM/yy hh:mm", "dd/MM/yyyy HH:mm");
		    dateFormats.put("dd/MM/yy HH:mm", "dd/MM/yyyy HH:mm");
		    
		    dateFormats.put("M/d/yy", "dd/MM/yyyy");
		    dateFormats.put("dd/mm/yy", "dd/MM/yyyy");
		    dateFormats.put("dd/MM/yy", "dd/MM/yyyy");
		    dateFormats.put("DD/MM/yy", "dd/MM/yyyy");

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
			configuration.setFormatPatternsMap(dateFormats);
			configuration.setRemoveEmptySpaceBetweenColumns(true);
			exporter.setConfiguration(configuration);
			
			exporter.exportReport();
			reportBytes = byteArray.toByteArray();
			reportOutputCO.setOutputFormat(ConstantsCommon.XLS_REP_FORMAT);
			// central bank
			if(reportSession.getREPORT_TYPE() != null
				&& reportSession.getREPORT_TYPE().equals(RepConstantsCommon.ONE))
			{
			    commonRepFuncBO.writeFileToRepository(reportSession.getPROG_REF(), reportBytes,
				    reportSession, noHeadAndFoot, parameters, con, usrName,origFormat);
			}
			// fcr dynamic
			else if(!ConstantsCommon.YES.equals(parameters.get(ConstantsCommon.FCR_STANDARD_FLAG_YN)) && ConstantsCommon.FCR_MAIN_REPORT_REF.equals(reportSession.getPROG_REF()))
			{
			    commonRepFuncBO.writeFileToRepository(reportSession.getFTR_OPT_PROG_REF(), reportBytes,
				    reportSession, noHeadAndFoot, parameters, con, usrName,origFormat);
			}
		    }
		    catch(Exception e)
		    {
			throw new BaseException("Error when exporting report to XLS format for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		    }
		}
		else if(ConstantsCommon.ODS_REP_FORMAT.equalsIgnoreCase(reportFormat))
		{
			repExt = ConstantsCommon.ODS_REP_FORMAT.toLowerCase();
            exporter = new JROdsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
			exporter.exportReport();
            reportBytes = byteArray.toByteArray();
		    reportOutputCO.setOutputFormat(ConstantsCommon.ODS_REP_FORMAT);
		}
		else if(reportFormat.equalsIgnoreCase(ConstantsCommon.CSV_REP_FORMAT) || reportFormat.equalsIgnoreCase(ConstantsCommon.CSV_EXT_REP_FORMAT))
		{
		    try
		    {
		    if(reportFormat.equalsIgnoreCase(ConstantsCommon.CSV_EXT_REP_FORMAT))
		    {
			repExt = ConstantsCommon.CSV_REP_FORMAT;
		    }
		    else
		    {	
			repExt = ConstantsCommon.TXT_EXT;
		    }
		    exporter = new JRCsvExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		    exporter.setExporterOutput(new SimpleWriterExporterOutput(byteArray));
		    SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
		    configuration.setFieldDelimiter(csvSepartor);
		    exporter.setConfiguration(configuration);
		    exporter.exportReport();
		    reportBytes = byteArray.toByteArray();
		    reportOutputCO.setOutputFormat(ConstantsCommon.CSV_REP_FORMAT);
		    }
		    catch(Exception e)
		    {
			throw new BaseException("Error when exporting report to CSV format for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		    }
		}
		else if(ConstantsCommon.TXT_FILE.equalsIgnoreCase(reportFormat) || ConstantsCommon.DAT_EXT.equals(reportFormat))
		{
		    reportBytes = sitcomBuffer.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
		    reportOutputCO.setOutputFormat(ConstantsCommon.TXT_EXT);
		}
		else
		{
		    try
		    {
			String imgFolderName = RepConstantsCommon.IMAGES_LOCATION;
			String imgDir = repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + zRepName + "/"
				+ imgFolderName;

			/*
			 * create a new directory to save temporarily blob or
			 * chart images
			 */
			File imgDirFile = new PathFileSecure(imgDir);
			if(!imgDirFile.exists())
			{
			    boolean res = imgDirFile.mkdirs();
			    if(!res)
			    {
				Log.getInstance().warning("Directory " + imgDirFile + " creation failed");
			    }
			}
			repExt = ConstantsCommon.HTML_EXT;

			exporter = new HtmlExporter();
			exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		  
		    if("true".equals(fromMenu) || "2".equals(d_p))
		    {
			SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(byteArray);
			String imageUri = this.getReportingPortalURL() + "servlets/image?deleteImg=true&image="+ repFolderName + "/" + imgFolderName + "/";
			HtmlResourceHandler  fileHdl= new FileHtmlResourceHandler(imgDirFile, imageUri + "{0}");
			fileHdl = new MapHtmlResourceHandler(fileHdl,  new HashMap()); //new HashMap for imageMap
			output.setImageHandler(fileHdl);
			exporter.setExporterOutput(output);
		    }
		    else
		    {
			SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(repositoryPath + "/"+ repFolderName + "/" + zRepName + "." + repExt);
			HtmlResourceHandler fileHdl= new FileHtmlResourceHandler(imgDirFile, imgFolderName + "/" + "{0}");
			fileHdl = new MapHtmlResourceHandler(fileHdl,  new HashMap());
			output.setImageHandler(fileHdl);
			exporter.setExporterOutput(output);
		    }
		    
//		    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
//		    exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.TRUE);// true by default


		    if(ConstantsCommon.LANGUAGE_ARABIC.equalsIgnoreCase(language)&& "0".equals(reportSession.getLANG_INDEPENDENT_YN()))
		    {
			 SimpleHtmlExporterConfiguration htmlExpConf = new SimpleHtmlExporterConfiguration();
			 htmlExpConf.setHtmlFooter("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/><style>body {direction:rtl}</style>");
			 htmlExpConf.setBetweenPagesHtml("<P STYLE='page-break-before:always'>");
			 exporter.setConfiguration(htmlExpConf);
			
		    }
		    SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
		    /* setting a zoom ration because jasper is replacing pt by  px in html which is resulting in a decrease in the font size*/
		    configuration.setZoomRatio(1.334F);
//		    // export a range of pages on preview
		    int pages = jasperPrint.getPages().size();
		    if(pages > 0)
		    {
			if(!NumberUtil.isEmptyDecimal(fromPage) && fromPage.intValue() > 0
				&& pages >= fromPage.intValue())
			{
			    configuration.setStartPageIndex(fromPage.intValue() - 1);
			}
			if(!NumberUtil.isEmptyDecimal(toPage) && toPage.intValue() > 0 && pages >= toPage.intValue())
			{
			    configuration.setEndPageIndex(toPage.intValue() - 1);
			}
		    }
		    exporter.setConfiguration(configuration);
		    exporter.exportReport();

		    // check if images are created then zip the folder
		    if((new PathFileSecure(imgDir)).list().length > 0)
		    {
			imgsCreated = true;
		    }

		    reportOutputCO.setOutputFormat(ConstantsCommon.HTML_REP_FORMAT);
		    if("true".equals(fromMenu) || "2".equals(d_p))
		    {
			reportBytes = byteArray.toByteArray();
			if(ConstantsCommon.TRUE.equalsIgnoreCase(retrieveCall))
			{
			    // get the oversize limit defined in IRP_CTRL
			    // table
			    BigDecimal oversizeLimit = reportDAO.returnOversizeLimit();
			    if(!NumberUtil.isEmptyDecimal(oversizeLimit))
			    {
				// get the number of page equivalent to the
				// size defined in IRP_CTRL_TABLE
				Double reportPagesOversizeLimit = (double) Math.round(oversizeLimit.intValue() * pages / (byteArray.size() / 1000));// byteArray.size()/1000 to get the byteArray in KB
				// get the number of count defined that
				// should be use for the number of report
				// pages
				int paginationCount = (int) Math.ceil(pages / reportPagesOversizeLimit);
				int count = 0;
				IRP_AD_HOC_REPORTSC paginationSC = new IRP_AD_HOC_REPORTSC();
				paginationSC.setUSER_ID(usrName);
				paginationSC.setAPP_NAME(appName);
				paginationSC.setPROG_REF(reportSession.getPROG_REF().replace("-", "_"));
				paginationSC.setCurrentPage(new BigDecimal(reportPagesOversizeLimit - 1));// to page
				// delete all the record related to the same
				// app name prog ref and user id
				reportDAO.deletePagination(paginationSC);
				// if the pagination is distributed more
				// than 1 page
				if(paginationCount != 1)
				{
				    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
				    for(int s = 0; s < paginationCount; s++)
				    {
					IRP_REPORT_PAGINATIONVOWithBLOBs irpReportPaginationVO;
					byteArray = new ByteArrayOutputStream();
					SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(byteArray);
					String imageUri = this.getReportingPortalURL() + "servlets/image?deleteImg=true&image="+ repFolderName + "/" + imgFolderName + "/";
					HtmlResourceHandler  fileHdl= new FileHtmlResourceHandler(imgDirFile, imageUri + "{0}");
					fileHdl = new MapHtmlResourceHandler(fileHdl,  new HashMap()); //new HashMap for imageMap
					output.setImageHandler(fileHdl);
					exporter.setExporterOutput(output);
					configuration.setStartPageIndex(count);
					if(pages > reportPagesOversizeLimit.intValue())
					{
					    configuration.setEndPageIndex(count + reportPagesOversizeLimit.intValue() - 1);
					}
					else
					{
					    configuration.setEndPageIndex(jasperPrint.getPages().size() - 1);
					}
					exporter.setConfiguration(configuration);
					exporter.exportReport();
					irpReportPaginationVO = new IRP_REPORT_PAGINATIONVOWithBLOBs();
					irpReportPaginationVO.setPAGE_COUNTER(new BigDecimal(s + 1));
					irpReportPaginationVO.setUSER_ID(usrName);
					irpReportPaginationVO.setAPP_NAME(appName);
					irpReportPaginationVO.setPROG_REF(reportSession.getPROG_REF().replace("-", "_"));
					irpReportPaginationVO.setFROM_PAGE(new BigDecimal(count));
					if(pages > reportPagesOversizeLimit.intValue())
					{
					    irpReportPaginationVO.setTO_PAGE(new BigDecimal(count + reportPagesOversizeLimit.intValue() - 1));
					}
					else
					{
					    irpReportPaginationVO.setTO_PAGE(new BigDecimal(jasperPrint.getPages().size() - 1));
					}
					irpReportPaginationVO.setREPORT_CONTENT(byteArray.toByteArray());
					if(s == 0)
					{
					    reportBytes = byteArray.toByteArray();
					    reportOutputCO.setHasPagination(true);
					    reportOutputCO.setPaginationCount(paginationCount);
					}
					genericDAO.insert(irpReportPaginationVO);
					count = count + reportPagesOversizeLimit.intValue();
					pages = pages - reportPagesOversizeLimit.intValue();
				    }
				}
				else
				{
				    reportOutputCO.setHasPagination(false);
				}
			    }
			}
		    }
		    else
		    {
			if(imgsCreated)
			{
			    // zip the folder and convert it to byte[]
			    reportBytes = FileUtil.generateZipFromFldr(repositoryPath + "/" + repFolderName);
			    reportOutputCO.setOutputFormat(ConstantsCommon.ZIP_EXT);
			}
			else
			{
			    reportBytes = FileUtil.readFileBytes(repositoryPath + "/" + repFolderName + "/" + zRepName
				    + "." + repExt);
			}
		    }
		}
		    catch(Exception e)
		    {
			throw new BaseException("Error when exporting report to HTML format for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		    }
	    }
		if( ConstantsCommon.REP_SERVICE_CALL.equals(fromMenu) && ! printerName.isEmpty())
		{
			try
			{
			// print report on printer
			    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
			    printServiceAttributeSet.add(new PrinterName(printerName, null));
			    JRPrintServiceExporter exporterPrint = new JRPrintServiceExporter();
			    exporterPrint.setExporterInput(new SimpleExporterInput(jasperPrint));
			    SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
			    configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
			    exporterPrint.setConfiguration(configuration);
			    exporterPrint.exportReport();
			    
			}
			catch(Exception e)
			{
			    throw new BaseException("Error when printing report for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
			}
		}
	    }
	    else
	    {
		try
		{
		 // print report on printer
		    PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
		    printServiceAttributeSet.add(new PrinterName(printerName, null));
		    PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
		    printRequestAttributeSet.add(new Copies(p_c_nb.intValue()));
		    JRPrintServiceExporter exporter = new JRPrintServiceExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		    SimplePrintServiceExporterConfiguration configuration = new SimplePrintServiceExporterConfiguration();
		    configuration.setPrintRequestAttributeSet(printRequestAttributeSet);
		    configuration.setPrintServiceAttributeSet(printServiceAttributeSet);
		    exporter.setConfiguration(configuration);
		    exporter.exportReport();
		    
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when printing report for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		}
	    }

	    // call After procedures
	    if(!"true".equals(noData) && ((ConstantsCommon.TRUE.equalsIgnoreCase(retrieveCall)
		    && "0".equals(reportSession.getEXEC_PRC_ON_RETRIEVE_YN()))
		    || (!ConstantsCommon.TRUE.equalsIgnoreCase(retrieveCall))))
	    {
		callProcedures(BigDecimal.ONE, parameters, reportSession, menu, con,transMsgLangMap);
	    }

	    // call subreport After procedures
	    for(IRP_AD_HOC_REPORTCO subRepCO : subReportCOList)
	    {
		if(!"true".equals(noData))
		{
		    callProcedures(BigDecimal.ONE, parameters, subRepCO, subRepCO.getPROG_REF(), con, transMsgLangMap);
		}
	    }
	    if(!NumberUtil.isEmptyDecimal(reportSession.getMETADATA_REPORT_ID()) && !ConstantsCommon.TRUE.equals(noData) && reportOutputCO.isHasData())
	    {
		retrieveMetadataReport(reportSession, parameters, saveCopyLocation, menu, decimalPts, appName, usrName,
			language, printerName, dbConn, noHeadAndFoot, csvSepartor, fromPage, toPage, fromMenu,
			transMsgLangMap, reportBytes, repExt, mainRepPageNbr,con,repParamsCO,origFormat
			,var_reportName,jasperPrint,jasperReport);
	    }
	    if(!RepConstantsCommon.EMPTY_STRING.equals(saveCopyLocation))
	    {
		try
		{
		IRP_SNAPSHOTSCO snpCO = new IRP_SNAPSHOTSCO();
		snpCO.setREPORT_FORMAT(origFormat);
		snpCO.setEXECUTION_DATE(executionDate);
		snpCO.setREPORT_NAME(reportSession.getREPORT_NAME());
		snpCO.setEXECUTED_BY(usrName);
		snpCO.setCOMP_CODE(reportSession.getCOMP_CODE());
		snpCO.setBRANCH_CODE(reportSession.getBRANCH_CODE());

		// get the new counter
		BigDecimal snpId = commonRepFuncBO.retCounterValue("IRP_SNAPSHOTS");
		snpCO.setSNAPSHOT_ID(snpId);

		if(saveCopyLocation.equals(RepConstantsCommon.Repository))
		{
		    if(imgsCreated)
		    {
			byte[] zipBytes = FileUtil.generateZipFromFldr(repositoryPath + "/" + repFolderName);
			FileUtil.saveToRepository(zipBytes, ReportingConstantsCommon.repositoryFolder, "/" + ReportingConstantsCommon.reportingFolder + "/" + repFolderName + ".zip");
		    }
		    else
		    {
			FileUtil.saveToRepository(reportBytes, ReportingConstantsCommon.repositoryFolder, "/" + ReportingConstantsCommon.reportingFolder + "/" + RepConstantsCommon.SNAPSHOT_LOCATION
				+ "/" + zRepName + "." + repExt);
		    }

		    // save into DB all details
		    snpCO.setIS_DB(BigDecimal.ZERO);
		    snpCO.setFILE_NAME(zRepName);
		    snpCO.setSNAPSHOT_REF(BigDecimal.ZERO);
		    snpCO.setAPP_NAME(reportSession.getAPP_NAME());
		    snapShotBO.insertSnapShots(snpCO);
		}
		else if(saveCopyLocation.equals(RepConstantsCommon.DB))
		{
		    // save into DB all details
		    snpCO.setREPORT_CONTENT(reportBytes);
		    snpCO.setIS_DB(BigDecimal.ONE);
		    snpCO.setFILE_NAME(zRepName);
		    snpCO.setSNAPSHOT_REF(BigDecimal.ZERO);
		    snpCO.setAPP_NAME(reportSession.getAPP_NAME());
		    snapShotBO.insertSnapShots(snpCO);
		}
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when saving a snapshot of the report for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		}
	    }

	    // delete the temporarily created report folder
	    if(!"true".equals(fromMenu) || ("true".equals(fromMenu) && !imgsCreated))
	    {
		try
		{
		File tempFile = new PathFileSecure(repositoryPath + "/" + repFolderName);
		//Condition after the || for the case where report having several normal images (not barcode) 
		//under database only then the img folder won't be deleted
		File tempImages  = new PathFileSecure(repositoryPath + "/" + repFolderName+"/images");
		    if(tempFile.exists() && !"2".equals(d_p) || (tempFile.exists() && "2".equals(d_p)
			    && tempImages.exists() && tempImages.list().length == 0))
		    {
			FileUtil.deleteFolder(repositoryPath + "/" + repFolderName);
		    }
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when deleting temporary report folder from repository for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		}
	    }
	    
	    reportOutputCO.setReportBytes(reportBytes);
	    reportOutputCO.setReportBytes2(reportBytes2);
	    reportOutputCO.setPagesNbr(pagesNbr);
	    reportOutputCO.setReportName(var_reportName);
	    if(con != null)
	    {
		con.commit();
	    }
	    if(!"true".equals(noData) && BigDecimal.ONE.toPlainString().equals(reportExecLog))
	    {
	    	if(reportBytes != null)
	    	{
	    		Double reportSize = (double) Math.round(reportBytes.length / 1024.0);//1024 to get the byteArray in KB
	    		repExecLogParamsVO.setREPORT_SIZE(new BigDecimal(reportSize));
		    }
		    else
		    {
		    	repExecLogParamsVO.setREPORT_SIZE(BigDecimal.ZERO);
		    }
	    }
	    return reportOutputCO;
	}
//	catch(JRException jr)
//	{
//	    if(con != null)
//	    {
//		con.rollback();
//	    }
//	    log.error(jr, jr.getMessage());
//	    throw new BOException(MessageCodes.REPORT_COMPILATION_ERROR);
//	}
	catch(Exception e)
	{
		if(repExecLogParamsVO != null)
    	{
			if(e.toString().length() > 1000)
			{
				repExecLogParamsVO.setREPORT_EXCEPTION(e.toString().substring(1, 1000));
	    	}
	    	else
	    	{
	    		repExecLogParamsVO.setREPORT_EXCEPTION(e.toString());
	    	}
    	}
	    if(con != null)
	    {
		con.rollback();
	    }

	    if(e.getMessage()!=null && e.getMessage().indexOf(transMsgLangMap.get("1"))==0)
	    {
		throw throwException(e, e.getMessage().substring(transMsgLangMap.get("1").length()) );
	    }
	    else
	    {
		throw throwException(e, e.getMessage() + transMsgLangMap.get("2")+ reportSession.getPROG_REF() + transMsgLangMap.get("3")+ reportSession.getAPP_NAME());
	    }
	}
	finally
	{
		//update final execution time 
		if(!"true".equals(noData) && BigDecimal.ONE.toPlainString().equals(reportExecLog))
		{
			repExecLogParamsVO.setEXEC_END_TIME(Calendar.getInstance().getTime());
			designerBO.updateReportExecLog_NewTrans(repExecLogParamsVO);
    	}
		try
		{
		    // drop hash Table for imported procedures in case db=Sybase
		    if(commonLibBO.returnIsSybase() == 1 && reportSession.getHashTblList().size() > 0)
		    {
			try
			{
			procBO.dropHashTbl(con, reportSession.getHashTblList());
			}
			catch(Exception e)
			{
			    throw new BaseException("Error when dropping hash table for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
			}
		    }
		}
		finally
		{
		    if(con != null)
		    {
		    	con.close();
		    }
		}
		
	    try{
	    // delete the temporarily folder containing the jrxml files
	    FileUtil.deleteFolder(repositoryPath + "/" + var_reportName + "/");
	    }
	    catch(Exception e)
		{
		    throw new BaseException(e, "Error when deleting temporary report folder from repository for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
		}
	    Thread.currentThread().setName(currentThreadName);
	}
    }
    
    public void insertSnpSubRep(IRP_AD_HOC_REPORTCO reportSession, REP_SNAPSHOT_INFOVO repSnpInfoVO) throws BaseException
    {
	try
	{
	    String subRepExpr;
	    IRP_AD_HOC_REPORTCO subReport;
	    IRP_SNAPSHOT_SUB_REPORTVO snpSubVO;
	    for(IRP_SUB_REPORTCO subrep : reportSession.getSubreportsList())
	    {
		subRepExpr = subrep.getIrpSubReportVO().getSUB_REPORT_EXPRESSION();
		subReport = designerBO.returnReportById(subrep.getIrpSubReportVO().getREPORT_ID());
		snpSubVO = new IRP_SNAPSHOT_SUB_REPORTVO();
		snpSubVO.setJRXML_FILE(subReport.getJRXML_FILE());
		snpSubVO.setSUB_REPORT_EXPRESSION(subRepExpr);
		snpSubVO.setREP_SNAPSHOT_ID(repSnpInfoVO.getREP_SNAPSHOT_ID());
		snapshotParameterBO.insertSnpSubReportByRepSnpId(snpSubVO);
		if(subReport.getSubreportsList() != null && subReport.getSubreportsList().size() > 0)
		{
		    insertSnpSubRep(subReport, repSnpInfoVO);
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	    throw new BaseException("Error occured when inserting snapshtos of sub reports for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
	}
    }

    private void generateSubReports(List<IRP_SUB_REPORTCO> subreportsList, List<IRP_AD_HOC_REPORTCO> subReportCOList,
	    HashMap<String, String> hyprParamsValMap, ReportParamsCO repParamsCO, int decimalPts, String reportFormat,
	    String language, boolean noHeadAndFoot, String noData, Map parameters, Connection con,
	    String var_reportName, String saveCopyLocation, String wp) throws BaseException, Exception
    {
	try
	{
	HashMap<String, String> hypParamsValMap = hyprParamsValMap;
	String subRepExpr;
	IRP_AD_HOC_REPORTCO subReport;

	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);

	for(IRP_SUB_REPORTCO subrep : subreportsList)
	{
	    subRepExpr = subrep.getIrpSubReportVO().getSUB_REPORT_EXPRESSION();
	    try
	    {
	    subReport = designerBO.returnReportById(subrep.getIrpSubReportVO().getREPORT_ID());
	    }
	    catch(Exception e)
	    {
		throw new BaseException("Error occured when retrieving nested report from db  "+e.getMessage(),e);
	    }
	    subReport.setIsSubRep("1");
	    // create subreport jrxml file under repository
	    createJRXMLFile(subReport.getJRXML_FILE(), repositoryPath + "/" + var_reportName + "/" + subRepExpr);

	    // fill the subreport hpyerlinks params value
	    hypParamsValMap = retHypParamsVal(subReport, repParamsCO);
	    //

	    // compile subreport .jrxml file into .jasper
//	    try
//	    {
		compileReport(repositoryPath + "/" + var_reportName + "/" + subRepExpr, subReport.getPROG_REF(), reportFormat, subReport, language, hypParamsValMap, noHeadAndFoot, noData,
			repParamsCO, subrep, saveCopyLocation, wp,RepConstantsCommon.WHEN_NO_DATA_DEFAULT);// passing
		// sub
		// report
		// list to be used in
		// mirror layout to
		// declare sub report
		// width
//	    }
//	    catch(Exception e)
//	    {
//		log.error(e, "Error in compileReport() method in ReportBOImpl.java");
//		throw new BOException(e.getMessage(),"");
//	    }

	    // call subreport Before procedures
	    if(!"true".equals(noData))
	    {
		callProcedures(BigDecimal.ZERO, parameters, subReport, subReport.getPROG_REF(), con);
	    }
	    //

	    // add the subreport to a list
	    subReportCOList.add(subReport);

	    if(subReport.getSubreportsList() != null && subReport.getSubreportsList().size() > 0)
	    {
		generateSubReports(subReport.getSubreportsList(), subReportCOList, hypParamsValMap, repParamsCO,
			decimalPts, reportFormat, language, noHeadAndFoot, noData, parameters, con, var_reportName,
			saveCopyLocation, wp);
	    }
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in generating nested reports: "+e.getMessage(),e);
	}
    }

    private HashMap<String, String> retHypParamsVal(IRP_AD_HOC_REPORTCO reportSession, ReportParamsCO repParamsCO)
	    throws Exception
    {
	HashMap<String, String> hypParamsValMap = new HashMap<String, String>();
	try
	{
	    DesignerGridSC sc = new DesignerGridSC();
	    sc.setRep_Id(reportSession.getREPORT_ID());
	    List<IRP_REP_HYPERLINKSCO> lstHypParams = hyperlinksBO.retHyperlinksParams(sc);

	    // return the parameters value
	    IRP_REP_HYPERLINKSCO hypCO;
	    BigDecimal feIndex;
	    BigDecimal argType;
	    String paramType;
	    String paramVal;
	    Object sessionVal;
	    BigDecimal linkedFeIndex;
	    IRP_AD_HOC_QUERYCO qryCO = reportSession.getQueriesList().get(0);
	    IRP_FIELDSCO feCO;
	    String mainFeAlias;
	    BigDecimal linkedRepId;
	    String lnkProgRef;
	    for(int i = 0; i < lstHypParams.size(); i++)
	    {
		hypCO = lstHypParams.get(i);
		feIndex = hypCO.getHyperlinkVO().getFIELD_INDEX();
		// get the labelAlias from the field index
		feCO = qryCO.getSqlObject().getFields().get(feIndex.longValue());
		if(feCO == null)
		{
		    mainFeAlias = "fe is deleted";
		}
		else
		{
		    mainFeAlias = feCO.getLabelAlias();
		}

		argType = hypCO.getHyperlinkVO().getARGUMENT_TYPE();
		paramType = hypCO.getPARAM_TYPE();
		linkedRepId = hypCO.getHyperlinkVO().getLINKED_REPORT_ID();
		lnkProgRef = hypCO.getLINKED_PROG_REF();
		if(argType.intValue() == 2) // session
		{
		    sessionVal = PathPropertyUtil.returnProperty(repParamsCO, hypCO.getHyperlinkVO().getVALUE_STATIC());
		    if( ConstantsCommon.PARAM_TYPE_NUMBER.equals(paramType))
		    {
			paramVal = sessionVal.toString();
		    }
		    else
		    {
			paramVal = sessionVal.toString();
		    }
		}
		else if(argType.intValue() == 1) // field
		{
		    linkedFeIndex = new BigDecimal(hypCO.getHyperlinkVO().getVALUE_STATIC());

		    feCO = qryCO.getSqlObject().getFields().get(linkedFeIndex.longValue());
		    if(feCO == null)
		    {
			paramVal = "linked field has been deleted";
		    }
		    else
		    {
			paramVal = "\"+$F{" + feCO.getLabelAlias() + "}+\"";
		    }
		}
		else
		// static
		{
		    paramVal = hypCO.getHyperlinkVO().getVALUE_STATIC();
		}
		String paramsVal = hypParamsValMap.get("$F{" + mainFeAlias + "}");
		if(paramsVal == null)
		{
		    hypParamsValMap.put("$F{" + mainFeAlias + "}", lnkProgRef + "~r~" + linkedRepId + "~r~" + paramVal);
		}
		else
		{
		    hypParamsValMap.put("$F{" + mainFeAlias + "}", hypParamsValMap.get("$F{" + mainFeAlias + "}")
			    + URLEncoder.encode("~#~", "UTF-8") + paramVal);
		}
	    }
	}
	catch(Exception e)
	{
	    throw new BaseException("Error occured when getting hyperlinks params for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
	}
	return hypParamsValMap;
    }

    public void createJRXMLFile(byte[] xml, String reportPath) throws BaseException, IOException
    {
	Writer out=null;
	FileOutputStream fos=null;
	try
	{
	String xmlStr = new String(xml, FileUtil.DEFAULT_FILE_ENCODING);
	fos = new FileOutputStream(reportPath + ".jrxml");
	out = new OutputStreamWriter(fos, CommonUtil.ENCODING);
	
	    out.write(xmlStr);
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in creating JRXML file: "+e.getMessage(),e);
	}
	finally
	{
	    if(out!=null)
	    {
		out.close();
	    }
	    if(fos!=null)
	    {
		fos.close();
	    }
	}
    }

    private File compileReport(String report, String menu,  String reportFormat,
	    IRP_AD_HOC_REPORTCO reportSession, String language, HashMap<String, String> hypParamsValMap,
	    boolean noHeadAndFoot, String noData, ReportParamsCO repParamsCO, IRP_SUB_REPORTCO subrep,
	    String saveCopyLocation, String wp,String whenNoData) throws Exception
    {
	JasperDesign jasperDesign;
	try
	{
	jasperDesign = JRXmlLoader.load(report + ".jrxml");
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in loading the report when compiling for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage(),e);
	}
	
	//add whenNoData property at the level of the jasperDesign for the main report
	if(subrep==null && !"".equals(StringUtil.nullToEmpty(whenNoData))
		&& !RepConstantsCommon.WHEN_NO_DATA_DEFAULT.equals(whenNoData))
	{
	    HashMap<String,String> transMap = new HashMap<String, String>(); 
	    if(RepConstantsCommon.WHEN_NO_DATA_NO_DATA_SECTION.equals(whenNoData))
	    {
		CommonLibSC sc = new CommonLibSC();
		sc.setAppName(ConstantsCommon.IMAL_APP_NAME);
		sc.setProgRef(ConstantsCommon.PROGREF_ROOT);
		sc.setLanguage(language);
		sc.setKeyLabelCode("reporting.noDataRetrieved");
		String transVal = commonLibBO.returnKeyLabelTrans(sc);
		transMap.put("reporting.noDataRetrieved", transVal);
	    }

	    ReportUtils.addWhenNoData(jasperDesign, whenNoData,transMap);
	}
	
	// add a property to each field in order to use it when creating the
	// insert statement

	//add property my.dataFe that will be used when generating the report in sql format
	if(ConstantsCommon.SQL_REP_FORMAT.equals(reportFormat))
	{
	    ReportUtils.addFieldProperty(jasperDesign, new HashMap<String, String>());
	}
	// check language setting and if it's arabic, mirror the design to
	// generate right-to-left arabic report
	if(ConstantsCommon.LANGUAGE_ARABIC.equalsIgnoreCase(language) && "0".equals(reportSession.getLANG_INDEPENDENT_YN()))
	{
	    ReportUtils.mirrorLayout(jasperDesign, subrep, reportFormat);
	}

	// update the image path inisde the jrxml
	if(!menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
	{
	    ReportUtils.updateImageLocation(jasperDesign, getReportingImageURL(), saveCopyLocation,reportFormat);
	}

	ReportUtils.addRepGlobalParam(jasperDesign);

	// For specific reports, check if the watermark exists inside a
	// background band
	// if not add it
	if("1".equals(wp))
	{
	    ReportUtils.addWatermark(jasperDesign, subrep);
	}

	if(reportSession != null && !("true").equals(noData))
	{
	    // Groupping
	    if(reportSession.getPrevGrpMap().size() > 0)
	    {
		ReportUtils.addGroupFields(jasperDesign, reportSession);
	    }
	    // sorting
	    if(reportSession.getPrevOrderList().size() > 0)
	    {
		ReportUtils.addSortFields(jasperDesign, reportSession);
	    }

	    // Filtering
	    if(reportSession.getPrevFilterMap().size() > 0)
	    {
		ReportUtils.addFilterFields(jasperDesign, reportSession);
	    }

	    // Add hyperLink if exist
	    if(hypParamsValMap != null && !hypParamsValMap.isEmpty())
	    {
		ReportUtils.addRepHyperLink(jasperDesign, getReportingPortalURL(), reportSession, hypParamsValMap,
			repParamsCO);
	    }
	    
	    //if is Sybase, update report query by replacing the table '' with '' in case of ftr or fcr report
	    if(commonLibBO.returnIsSybase() == 1
		    && (menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION) || "1".equals(reportSession.getREPORT_TYPE())))
	    {
		ReportUtils.updateFtrFcrReportQuery(jasperDesign);
	    }

	}
	// check if we should remvoe the header and footer
	if(noHeadAndFoot)
	{
	    ReportUtils.removeHeaderAndFooter(jasperDesign);
	}
	if("true".equals(noData) && RepConstantsCommon.WHEN_NO_DATA_DEFAULT.equals(whenNoData))
	{
	    ReportUtils.showTblColHeaderWhenNoData(jasperDesign,whenNoData);
	}

	try
	{
    	// compile a .jrxml file
    	JasperCompileManager.compileReportToFile(jasperDesign, report + ".jasper");
	}
	catch(Exception e)
	{
	    throw throwException(e, "Error compiling the report having reference: " + reportSession.getPROG_REF() + " and application: "+ reportSession.getAPP_NAME() + "\n "+e.getMessage());
	}
	File reportFile = new PathFileSecure(report + ".jasper");
	if(!reportFile.exists())
	{
	    throw new JRRuntimeException(report + ".jasper file not found for report reference: " + reportSession.getPROG_REF() + " and report application: "+ reportSession.getAPP_NAME());
	}
	return reportFile;
    }

    public IRP_AD_HOC_REPORTCO returnReportById(BigDecimal reportId) throws BaseException, Exception
    {
	return designerBO.returnReportById(reportId);
    }

    public void callProcedures(BigDecimal order, Map parameters, IRP_AD_HOC_REPORTCO reportSession, String var_menuId,
	    Connection con) throws BaseException
    {  	HashMap<String,String> transMsgLangMap = new HashMap<String,String>();
        transMsgLangMap.put("1","Error when calling procedures:");
        transMsgLangMap.put("2"," for report reference: ");
        transMsgLangMap.put("3"," and report application: ");
        callProcedures( order,  parameters,  reportSession,  var_menuId,con,transMsgLangMap);
    }
    public  HashMap<String, Object> callProcedures(BigDecimal order, Map parameters, IRP_AD_HOC_REPORTCO reportSession, String var_menuId,
	    Connection con, HashMap<String,String> transMsgLangMap) throws BaseException
    {
	HashMap<String, Object> outProcValuesMap = new HashMap<String, Object>();
	try
	{
	    List<IRP_REP_PROCCO> proceduresList = reportSession.getProceduresList();
	    if(proceduresList != null && !proceduresList.isEmpty())
	    {
		List<IRP_REP_PROCCO> procList = new ArrayList<IRP_REP_PROCCO>();
		IRP_REP_PROCCO procCO;
		String procName;
		String paramName;
		Object paramVal;
		List<IRP_REP_PROC_PARAMSCO> procSysParamsList;
		StringBuffer procStr;
		HashMap<String, Object> procValuesMap = new HashMap<String, Object>();
		IRP_REP_PROC_PARAMSCO procParamsCO;
		BigDecimal paramId;
		IRP_REP_ARGUMENTSCO argCO;
		IRP_REP_PROCCO pCO;
		
		IRP_REP_PROCSC procSC;
		BigDecimal repId;
		if(reportSession.getREPORT_ID() == null)
		{
		    repId = new BigDecimal(-1);
		}
		else
		{
		    repId = reportSession.getREPORT_ID();
		}

		// Filter on before and after
		HashMap<BigDecimal, IRP_REP_PROCCO> beforeMap = new HashMap<BigDecimal, IRP_REP_PROCCO>();
		HashMap<BigDecimal, IRP_REP_PROCCO> afterMap = new HashMap<BigDecimal, IRP_REP_PROCCO>();
		for(int j = 0; j < proceduresList.size(); j++)
		{
		    procCO = proceduresList.get(j);
		    if(procCO.getEXEC_BEFORE().intValue() == 0)
		    {
			beforeMap.put(procCO.getPROC_ORDER(), procCO);
		    }
		    else
		    {
			afterMap.put(procCO.getPROC_ORDER(), procCO);
		    }
		}

		if(order.intValue() == 0) // before
		{
		    for(int l = 1; l <= beforeMap.size(); l++)
		    {
			pCO = beforeMap.get(new BigDecimal(l));
			procList.add(pCO);
		    }
		}
		else
		{
		    for(int l = 1; l <= afterMap.size(); l++)
		    {
			procList.add(afterMap.get(new BigDecimal(l)));
		    }
		}
		ReportParamsCO  repParamsCO = (ReportParamsCO) (parameters.get(RepConstantsCommon.repParamsCO_arg));

		String   userId = repParamsCO.getUserName();
		String   appName = repParamsCO.getCurrentAppName();
		String language = repParamsCO.getRepLanguage();
		Date runningDate = repParamsCO.getRunningDateRET();
		for(int i = 0; i < procList.size(); i++)
		{
		    
		    //insert  temp session details (will be called only if the procList is not empty and the parameter
		    // 'l' is sent in the request)
		    String isInsertTmpSessDet = (String) (parameters.get("isInsertTmpSessDet"));
		    if(ConstantsCommon.YES.equals(isInsertTmpSessDet))
		    {
			try
			{
			    genericDAO.insertTempSessionDetails(language, runningDate, userId, appName, con);
			}
			catch(Exception e)
			{
			    log.error(e, "error in insertTempSessionDetails");
			}
		    }
		    
		    procCO = procList.get(i);
		    procName = procCO.getPROC_NAME();
		    procStr = new StringBuffer("");
		    // return the system parameters of each procedure
		    procSC = new IRP_REP_PROCSC();
		    procSC.setIsGrid(false);
		    procSC.setPROCEDURE_NAME(procName);
		    procSC.setREP_ID(repId);
		    //changed from new bigdecimal(-1) to retrieve the procedure output parameter type and success value
		    procSC.setPROCEDURE_ID(procCO.getPROC_ID());
		    procSysParamsList = procBO.getSysProcParamsList(procSC);
		    procStr.append("{call " + procName + "(");
		    for(int j = 0; j < procSysParamsList.size(); j++)
		    {
			procStr.append("?,");
		    }
		    // remove the last ,
		    if(!procSysParamsList.isEmpty())
		    {
			procStr = new StringBuffer(procStr.toString().substring(0, procStr.length() - 1));
		    }
		    procStr.append(")}");
		    log.debug("______________procSTR==" + procStr);
		    // return the parameter values of each procedure
		    ArrayList<IRP_REP_PROC_PARAMSCO> procParamsMap = reportSession.getProcParamsMap().get(procName);

		    if(procParamsMap != null)
		    {
			for(int k = 0; k < procParamsMap.size(); k++)
			{
			    procParamsCO = procParamsMap.get(k);
			    paramId = procParamsCO.getPARAM_ID();
			    if(paramId != null)
			    {
				argCO = retArgCO(reportSession.getArgumentsList(), paramId);
				if(argCO != null)
				{
				    paramName = argCO.getARGUMENT_NAME();
				    paramVal=parameters.get(paramName);
				    procValuesMap.put(procParamsCO.getPARAM_NAME(), paramVal);
				}
			    }
			}
		    }
		    outProcValuesMap= procBO.callProcedure(procStr, procValuesMap, procSysParamsList, con, appName,  reportSession, procName, userId);

		}

	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	    throw new BaseException(transMsgLangMap.get("1")+e.getMessage(),e);
	}
	return outProcValuesMap;
    }

    private IRP_REP_ARGUMENTSCO retArgCO(LinkedHashMap argMap, BigDecimal paramId) throws Exception
    {

	Iterator it = argMap.values().iterator();
	IRP_REP_ARGUMENTSCO argCO;
	Long argIndex;
	while(it.hasNext())
	{
	    argCO = (IRP_REP_ARGUMENTSCO) it.next();
	    argIndex = argCO.getIndex();
	    if(argCO.getARGUMENT_ID() != null && argCO.getARGUMENT_ID().intValue() == paramId.intValue())
	    {
		return argCO;
	    }
	    else if(argIndex != null && argIndex.equals(paramId.longValue()))
	    {
		return argCO;
	    }
	}

	return null;
    }

    /**
     * Method to create a jasperdesign dynamically based on the VO's properties
     */
    public byte[] addTemplateHeader(GLSHEADERVO glsheaderVO) throws BaseException
    {
	try
	{
	    JasperPrint jasperPrint = null;
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    JasperDesign jasperDesign = ReportUtils.previewTemplateHeader(glsheaderVO);
	    // convert jasperDesign to jasperReport
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    String report = repositoryPath + "\\testJasperReport";
	    JasperCompileManager.compileReportToFile(jasperDesign, report + ".jasper");
	    File reportFile = new PathFileSecure(report + ".jasper");
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
	    jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap(), new JREmptyDataSource());
	    HtmlExporter exporter = new HtmlExporter();
	    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	    exporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArray));
	    SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
	    configuration.setZoomRatio(1.334F);
	    configuration.setRemoveEmptySpaceBetweenRows(true);
	    configuration.setWhitePageBackground(true);
	    configuration.setStartPageIndex(0);
	    configuration.setEndPageIndex(0);
	    exporter.setConfiguration(configuration);
//	    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
//	    exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);
//	    exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, CommonUtil.ENCODING);
	    exporter.exportReport();
	    return byteArray.toByteArray();
	}
	catch(Exception e)
	{
	    log.error(e, "An error occured in addTemplateHeader ReportBOImpl");
	    throw new BaseException("Error occured when adding a template header: "+e.getMessage(),e);
	}
    }
    
    @Override
    public Object [] generateReportDesign(IRP_AD_HOC_REPORTCO repCO,HashMap<String, Object> hashLiveSearch,String type,BigDecimal repId,BigDecimal crtColOrRelCol
	    ,Map parameters) throws BaseException
    {
	try
	{
	    JasperPrint jasperPrint = null;
	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    // convert jasperDesign to jasperReport
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    String report = repositoryPath + "\\testJasperReport";
	    JasperDesign jasperDesign = ReportUtils.modifyReportsColumnsSnapshot(repCO, hashLiveSearch, repositoryPath,
		    type, repId,crtColOrRelCol);
	  //display subreports columns
	    IRP_SUB_REPORTCO subRepCO;
	    IRP_AD_HOC_REPORTCO paramRepCO = new IRP_AD_HOC_REPORTCO();
	    for(int i = 0; i < repCO.getSubreportsList().size(); i++)
	    {
		subRepCO = repCO.getSubreportsList().get(i);
		paramRepCO.setPROG_REF(subRepCO.getPROG_REF());
		paramRepCO.setJRXML_FILE(subRepCO.getJRXML_FILE());
		ReportUtils.modifyReportsColumnsSnapshot(paramRepCO, hashLiveSearch, repositoryPath, type, repId,
			crtColOrRelCol);
	    }
	    Object[] obj = new Object[2];
	    byte[] reportBytes;
	    if(repCO.getSubreportsList().isEmpty())
	    {
		JasperCompileManager.compileReportToFile(jasperDesign, report + ".jasper");
	    File reportFile = new PathFileSecure(report + ".jasper");
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
	    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());
	    HtmlExporter exporter = new HtmlExporter();
//	    exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP, imagesMap);
//	    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
	    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
	    exporter.setExporterOutput(new SimpleHtmlExporterOutput(byteArray));
	    SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
	    configuration.setZoomRatio(1.334F);
	    configuration.setRemoveEmptySpaceBetweenRows(true);
	    configuration.setWhitePageBackground(true);
	    configuration.setStartPageIndex(0);
	    configuration.setEndPageIndex(0);
	    exporter.setConfiguration(configuration);
//	    exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR, Boolean.FALSE);
//	    exporter.setParameter(JRHtmlExporterParameter.CHARACTER_ENCODING, CommonUtil.ENCODING);
	    exporter.exportReport();
	    reportBytes = byteArray.toByteArray();
	    obj[0] = reportBytes;
	    }
	    else
	    {
		obj[0] = null;
	    }
	    obj[1] = hashLiveSearch;
	    return obj;
	}
	catch(Exception e)
	{
	    log.error(e, "An error occured in addTemplateHeader ReportBOImpl");
	    throw new BaseException("Error when generating report design: "+e.getMessage(),e);
	}
    }

    public HashMap<String,Object> createDynamicRowDataJrxml(boolean var_noHeadAndFoot, String var_reportName,
	    String var_menuId, IRP_AD_HOC_REPORTCO reportCO) throws BaseException
    {
	 HashMap<String,Object>  retMap=new HashMap<String,Object> ();
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String report = repositoryPath + "/" + var_reportName;
	try
	{

	    JasperDesign jasperDesign;
	    if(!var_menuId.endsWith(ConstantsCommon.DESIGNER_PROG_REF))
	    {
		// save the temporary dynamic jrxml file to disk
		createJRXMLFile(reportCO.getJRXML_FILE(), report);
	    }
	    jasperDesign = JRXmlLoader.load(report + ".jrxml");
	    // get the jasperDesign in order to retrieve the query

	    StringBuffer newJrxml = createRowDataJrxml(jasperDesign, var_noHeadAndFoot, var_reportName, reportCO);
	    byte[] newJrxmlByte=newJrxml.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
	    retMap.put("0", newJrxmlByte);
	    retMap.put("1", reportCO.getJasperDesignFieldsMap());
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	finally
	{
	    /*
	     * delete saved file in case of opening the report from menu
	     * otherwise do not delete it because we will take a copy of it
	     * before replacing it with the new jrxml
	     */
	    if(!var_menuId.endsWith(ConstantsCommon.DESIGNER_PROG_REF))
	    {
		File file;
		try {
			file = new PathFileSecure(report + ".jrxml");
			boolean isDel = file.delete();
			if(!isDel)
			{
			    log.debug("\n\n  >>>>>>>>>>>>  The following file has not been deleted :" + report);
			}
		} catch (Exception e) {
			log.error(e, e.getMessage());
		}
	    }
	}
	return retMap;
    }

    public StringBuffer createRowDataJrxml(JasperDesign jasperDesign, boolean var_noHeadAndFoot, String var_reportName,
	    IRP_AD_HOC_REPORTCO reportCO) throws BaseException
    {
	try
	{
	String sqlString;
	String sqlLang;
	JRField[] feLst;
	List<JRParameter> qryParams;
	JRDatasetParameter[] dsRunParams = null;
	List<JRParameter> repParams = jasperDesign.getParametersList();
	boolean isFreeForm = false;
	if(jasperDesign.getDatasetsList().size() == 0) // free form
	{
	    sqlString = jasperDesign.getQuery().getText();
	    sqlLang=jasperDesign.getQuery().getLanguage();
	    feLst = jasperDesign.getFields();
	    qryParams = repParams;
	    isFreeForm = true;

	}
	else
	{
	    JRDesignDataset ds = (JRDesignDataset) jasperDesign.getDatasetsList().get(0);
	    sqlString = ds.getQuery().getText();
	    sqlLang=ds.getQuery().getLanguage();
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
			+ var_reportName
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
	if(isFreeForm)
	{
	    	// add subDataSet parameters
		newJrxml.append(createRowDataJrxmlParams(repParams));
		// add sqlString
		newJrxml.append("<queryString language=\""+sqlLang+"\"><![CDATA[" + sqlString + "]]></queryString>");
		// add fields
		JRField fe;
		StringBuffer jrxmlCols = new StringBuffer("");
		StringBuffer jrxmlHeadCols = new StringBuffer("");
		int x=0;
		for(int i = 0; i < feLst.length; i++)
		{
		    fe = feLst[i];
		    reportCO.getJasperDesignFieldsMap().put(fe.getName(), fe.getValueClassName());
		    newJrxml.append("<field name=\"" + fe.getName() + "\" class=\"" + fe.getValueClassName() + "\"></field>");
		    if(x==0)
		    {
			x+=1;
		    }
		    else
		    {
			x+=90;
		    }
		    if(!var_noHeadAndFoot)
		    {
			jrxmlHeadCols=retRowDataJrxmlFeHead(fe, jrxmlHeadCols,x);
		    }
		    jrxmlCols = retRowDataJrxmlFe(fe, jrxmlCols,x);
		}
		//add backGroud
		newJrxml.append("<background><band splitType=\"Stretch\"/></background>");
		//add page header 
		if(!var_noHeadAndFoot)
		{
		    newJrxml.append("<pageHeader><band height=\"27\" splitType=\"Stretch\">" +
		    		"<printWhenExpression><![CDATA[$V{PAGE_NUMBER}==1]]></printWhenExpression>");
		    newJrxml.append(jrxmlHeadCols);
		    newJrxml.append("</band></pageHeader>");
		}
		//add details
		newJrxml.append("<detail><band height=\"30\" splitType=\"Stretch\">");
		newJrxml.append(jrxmlCols);
		newJrxml.append("</band></detail></jasperReport>");
	}
	else
	{
	    	// add subDataSet
		newJrxml.append("<subDataset name=\"" + var_reportName + "_0\" >");
		// add subDataSet parameters
		newJrxml.append(createRowDataJrxmlParams(qryParams));

		// add sqlString
		newJrxml.append("<queryString language=\""+sqlLang+"\"><![CDATA[" + sqlString + "]]></queryString>");
		// add fields
		JRField fe;
		StringBuffer jrxmlCols = new StringBuffer("");
		for(int i = 0; i < feLst.length; i++)
		{
		    fe = feLst[i];
		    reportCO.getJasperDesignFieldsMap().put(fe.getName(), fe.getValueClassName());
		    newJrxml.append("<field name=\"" + fe.getName() + "\" class=\"" + fe.getValueClassName() + "\"></field>");
		    jrxmlCols = createRowDataJrxmlColumn(fe, jrxmlCols, var_noHeadAndFoot);
		}
		// close subDataSet tag
		newJrxml.append("</subDataset>");
		// add  parameters
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
				+ "<datasetRun subDataset=\"" + var_reportName + "_0\" >");
		// add dsRunParams
		newJrxml.append(createRowDataJrxmlDsRunParams(repParams, dsRunParams, isFreeForm));
		newJrxml.append("<connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression>"
			+ "</datasetRun>");
		// create the columns
		newJrxml.append(jrxmlCols);

		newJrxml.append("</jr:table>" + "</componentElement>" + "</band>" + "</detail>" + "</jasperReport>");
	}
	
	return newJrxml;
	}
	catch(Exception e)
	{
	    throw new BaseException("Error when creating row data JRXML: "+e.getMessage(),e);
	}
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
		defaultValueExpression = param.getDefaultValueExpression().getText();
	    }
	    if(!param.isSystemDefined() && !param.getValueClass().equals(ReportParamsCO.class)
		    && !("SUBREPORT_DIR".equals(param.getName())))
	    {
		sb.append("<parameter name=\"" + param.getName() + "\" class=\"" + param.getValueClassName() + "\">");
		if(param.getDefaultValueExpression()!=null)
		{
		    sb.append("<defaultValueExpression><![CDATA[" + defaultValueExpression+"]]></defaultValueExpression>");
		}
		sb.append("</parameter>");
	    }
	}
	return sb;

    }

    public StringBuffer createRowDataJrxmlDsRunParams(List<JRParameter> repParams, JRDatasetParameter[] dsRunParams,
	    boolean isFreeForm) throws Exception
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

    public StringBuffer createRowDataJrxmlColumn(JRField fe, StringBuffer jrxmlColumns, boolean var_noHeadAndFoot) throws Exception
    {
	StringBuffer jrxmlCols = jrxmlColumns;
	jrxmlCols.append("<jr:column width=\"165\" >");
	if(!var_noHeadAndFoot)
	{
	    jrxmlCols.append("<jr:columnHeader height=\"37\" rowSpan=\"1\">");
	    jrxmlCols = retRowDataJrxmlFeHead(fe, jrxmlCols,23);
	    jrxmlCols.append("</jr:columnHeader>");

	}

	jrxmlCols.append("<jr:detailCell height=\"37\" rowSpan=\"1\">");
	jrxmlCols = retRowDataJrxmlFe(fe, jrxmlCols,23);
	jrxmlCols.append("</jr:detailCell>" + "</jr:column>");

	return jrxmlCols;
    }

    public StringBuffer retRowDataJrxmlFeHead(JRField fe, StringBuffer jrxmlCols,int x) throws Exception
    {
	jrxmlCols
		.append("<textField isStretchWithOverflow=\"true\">"
			+ "<reportElement  x=\""+x+"\" y=\"9\" width=\"80\" height=\"17\"/>"
			+ "<textElement textAlignment=\"Center\">"
			+ "<font fontName=\"Arial\" pdfFontName=\"arial.ttf\" pdfEncoding=\"Identity-H\" isPdfEmbedded=\"true\"/>"
			+ "</textElement>" + "<textFieldExpression><![CDATA[\"" + fe.getName()
			+ "\"]]></textFieldExpression>" + "</textField>");
	return jrxmlCols;
    }
    
    public StringBuffer retRowDataJrxmlFe(JRField fe, StringBuffer jrxmlCols,int x) throws Exception
    {
	jrxmlCols.append("<textField isStretchWithOverflow=\"false\" isBlankWhenNull=\"true\">"
		+ "<reportElement  x=\""+x+"\" y=\"9\" width=\"80\" height=\"17\">"
		+ "<property name=\"net.sf.jasperreports.export.xls.auto.fit.row\" value=\"true\"/>"
                + "<property name=\"net.sf.jasperreports.print.keep.full.text\" value=\"true\"/>"
                + "</reportElement>"
		+ "<textElement textAlignment=\"Center\">"
		+ "<font fontName=\"Arial\" pdfFontName=\"arial.ttf\" pdfEncoding=\"Identity-H\" isPdfEmbedded=\"true\"/>"
		+ "</textElement>" + "<textFieldExpression><![CDATA[$F{" + fe.getName()
		+ "}]]></textFieldExpression>" + "</textField>");
	return jrxmlCols;
    }

    public StringBuffer createResultSql(JasperPrint jasperPrint, StringBuffer sbInsert,
	    LinkedHashMap<String, String> jasDesMap) throws Exception
    {
	// to store the value statement
	StringBuffer sbVal = new StringBuffer(sbInsert);
	sbVal.append(" VALUES(");
	// to store the final statement
	StringBuffer sbSql = new StringBuffer("");
	// Collection feTypeLst = new ArrayList<String>();
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
					txtVal = txtFe.getFullText();
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
		    else if(elem instanceof JRTemplatePrintText)
		    {
			txtFe = (JRTemplatePrintText) elem;
			txtVal = txtFe.getFullText();
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
	catch(Exception e)
	{
	    log.error(e, "");
	    throw e;
	}
	return sbSql;
    }

    public byte[] loadImage(String fileName, String deleteImg) throws BaseException
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	try
	{
	    byte[] data = ("").getBytes(FileUtil.DEFAULT_FILE_ENCODING);
	    if(!"null".equals(fileName))
	    {
		byte[] imageData = imageBO.retImgObject(fileName);
		if(imageData != null)
		{
		    data = imageData;
		}
		else
		{    
        		String newPath = repositoryPath + "/" + RepConstantsCommon.IMAGES_LOCATION +"/"+fileName;
        		File file = new PathFileSecure(newPath);
        		//For pie charts and blobs images
        		if(!file.exists())
        		{
        		    newPath = repositoryPath + "/" + fileName;
        		    file = new PathFileSecure(newPath);
        		}
        		if(file.exists())
        		{
        		    data = FileUtil.readFileBytes(newPath);
        		    // delete image
        		    if(("true").equals(deleteImg))
        		    {
        			File imgDir = file.getParentFile();
        			boolean isDel = file.delete();
        			if(!isDel)
        			{
        			    log.debug("The following image has not been deleted : " + fileName);
        			}
        			// delete image folder
        			if(imgDir.isDirectory() && imgDir.list().length == 0)
        			{
        			    FileUtil.deleteFolder(imgDir.getParentFile().getPath());
        			}
        		    }
        		}
		}
	    }
	    return data;
	}
	catch(FileNotFoundException e)
	{
	    log.error(e, e.getMessage());
	    throw new BaseException("File not found when loading image: "+e.getMessage(),e);
	}
	catch(Exception e)
	{
	    log.error(e, e.getMessage());
	    throw new BaseException("Error when loading image: "+e.getMessage(),e);
	}
	//return new byte[0];
    }

    
    
    public ReportOutputCO printSnapshot(String var_reportName, Map parameters, String repFormat,
	    REP_SNAPSHOT_INFOVO infoVO, String appName, String userName, boolean isModifySnp, String csvSep,
	    boolean showHeadFoot) throws BaseException
    {
   	 BigDecimal compCode =(BigDecimal)(parameters.get(RepConstantsCommon.COMP_CODE_COL+"~1"));
   	 parameters.remove(RepConstantsCommon.COMP_CODE_COL+"~1");
	 String reportFormat = repFormat;
	 String csvSeparator=csvSep;
	 String repSnpId=infoVO.getREP_SNAPSHOT_ID().toString();
	 byte[]jrxml=infoVO.getJRXML_FILE();
	 String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	 String report = repositoryPath + "/" + var_reportName;
	try
	{
	    IRP_AD_HOC_REPORTCO reportCO=new IRP_AD_HOC_REPORTCO();
	    // create a temporarily folder to put all jrxml files
	    FileUtil.makeDirectories(report + "/");


//	    ReportParamsCO repParamsCO = (ReportParamsCO) (parameters.get("repParamsCO"));
	    File reportFile = null;

	    if(ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(reportFormat)
		    || ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equals(reportFormat)
		    || ConstantsCommon.SQL_REP_FORMAT.equals(reportFormat))
	    {
		reportCO.setJRXML_FILE(jrxml);
		HashMap<String,Object>  retMap= createDynamicRowDataJrxml(showHeadFoot, var_reportName, "", reportCO);
		reportCO.setJRXML_FILE((byte[])retMap.get("0"));
		reportCO.setJasperDesignFieldsMap((LinkedHashMap<String,String>)retMap.get("1"));
		jrxml = reportCO.getJRXML_FILE();
	    }
	    // create jrxml file under repository
	    createJRXMLFile(jrxml, report);
	   //update the formats
	    if(ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equals(reportFormat))
	    {
		reportFormat = ConstantsCommon.XLS_REP_FORMAT;
	    }
	    else if(ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(reportFormat))
	    {
		reportFormat = ConstantsCommon.CSV_REP_FORMAT;
	    }

	    try
	    {
		// compile .jrxml file into .jasper
		reportFile = compileReportSnapshot(report,isModifySnp,infoVO,reportFormat,showHeadFoot);
	    }
	    catch(Exception e)
	    {
		log.error(e, "Error in compileReport() method in ReportBOImpl.java");
		throw new Exception("report.compile.exceptionMsg._key",e);
	    }

	    
	    IRP_SNAPSHOT_SUB_REPORTVO snpSubVO=new IRP_SNAPSHOT_SUB_REPORTVO();
	    snpSubVO.setREP_SNAPSHOT_ID(infoVO.getREP_SNAPSHOT_ID());
	    List<IRP_SNAPSHOT_SUB_REPORTVO> subRepList=snapshotParameterBO.retSnapshotSubReportList(snpSubVO);
	    if(subRepList != null && !subRepList.isEmpty())
	    {
		generateSnpSubReports(subRepList,var_reportName,isModifySnp,reportFormat, showHeadFoot);
		parameters.put("SUBREPORT_DIR", repositoryPath + "/" + var_reportName + "/");
	    }
	    
	    JasperPrint jasperPrint = null;
	    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
	    int pagesNbr = 0;
	    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters);
	    pagesNbr = jasperPrint.getPages().size();

	    ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	    byte[] reportBytes = null;
	    // byte[] reportBytes2 = null;

	    // set the name of the file used to export the report into
	    Date executionDate = commonLibBO.returnSystemDateWithTime();
	    String startExecution = DateUtil.format(executionDate, "yyyy-MM-dd HH-mm-ss aa");
	    String zRepName = repSnpId + "_" + appName + "_" + userName.replace(".", "") + "_" + startExecution;

	    String repFolderName = RepConstantsCommon.SNAPSHOT_LOCATION + "/" + zRepName;
	    boolean imgsCreated = false;
	    JRAbstractExporter exporter = null;
	    ReportOutputCO reportOutputCO = new ReportOutputCO();
	    if(reportFormat.equalsIgnoreCase(ConstantsCommon.SQL_REP_FORMAT))
	    {
		    // create the sql statments;
		    LinkedHashMap jasDesMap = reportCO.getJasperDesignFieldsMap();
		    StringBuffer sbVal = new StringBuffer("");
		    if(!jasDesMap.isEmpty())
		    {
			StringBuffer sbInsert = new StringBuffer("");
			sbInsert.append("INSERT INTO " + var_reportName + "(");
			Iterator it = jasDesMap.keySet().iterator();
			while(it.hasNext())
			{
			    sbInsert.append((String) it.next() + ",");
			}

			sbInsert = new StringBuffer(sbInsert.substring(0, sbInsert.length() - 1));

			sbInsert.append(")");
			sbVal = createResultSql(jasperPrint, sbInsert, jasDesMap);
		    }
		    reportBytes = sbVal.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
		    reportOutputCO.setOutputFormat(ConstantsCommon.SQL_REP_FORMAT);
		}
	   else if(reportFormat.equalsIgnoreCase(ConstantsCommon.PDF_REP_FORMAT))
	    {
		reportBytes = JasperExportManager.exportReportToPdf(jasperPrint);
		reportOutputCO.setOutputFormat(ConstantsCommon.PDF_REP_FORMAT);
	    }
	    else if(reportFormat.equalsIgnoreCase(ConstantsCommon.DOC_REP_FORMAT))
	    {
		
		    exporter = new JRDocxExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
		    SimpleDocxReportConfiguration configuration = new SimpleDocxReportConfiguration();
		    configuration.setFlexibleRowHeight(true);
//		    exporter.setParameter(JRDocxExporterParameter.CHARACTER_ENCODING, CommonUtil.ENCODING);
		    exporter.setConfiguration(configuration);
		    exporter.exportReport();
		reportBytes = byteArray.toByteArray();
		reportOutputCO.setOutputFormat(ConstantsCommon.DOC_REP_FORMAT);
	    }

	    else if(reportFormat.equalsIgnoreCase(ConstantsCommon.XLS_REP_FORMAT))
	    {
		
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
		exporter.exportReport();
		
		reportBytes = byteArray.toByteArray();
		reportOutputCO.setOutputFormat(ConstantsCommon.XLS_REP_FORMAT);

	    }
	    else if(ConstantsCommon.ODS_REP_FORMAT.equalsIgnoreCase(reportFormat))
		{
            exporter = new JROdsExporter();
            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
            exporter.exportReport();
            reportBytes = byteArray.toByteArray();
    		reportOutputCO.setOutputFormat(ConstantsCommon.ODS_REP_FORMAT);
		}
	    else if(reportFormat.equalsIgnoreCase(ConstantsCommon.CSV_REP_FORMAT) || reportFormat.equalsIgnoreCase(ConstantsCommon.CSV_EXT_REP_FORMAT))
	    {
		exporter = new JRCsvExporter();
		exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		exporter.setExporterOutput(new SimpleWriterExporterOutput(byteArray));
		SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
		if("\\t".equals(csvSeparator))
		{
		    csvSeparator="\t";
		}
		configuration.setFieldDelimiter(csvSeparator);
		exporter.setConfiguration(configuration);
		exporter.exportReport();
		reportBytes = byteArray.toByteArray();
		reportOutputCO.setOutputFormat(ConstantsCommon.CSV_REP_FORMAT);
	    }
	    else if (reportFormat.equalsIgnoreCase(ConstantsCommon.TXT_FILE) || ConstantsCommon.DAT_EXT.equals(reportFormat))
	    {
			BigDecimal snpId = infoVO.getREP_ID();
			REP_SNAPSHOT_PARAMVO snpParVO = new REP_SNAPSHOT_PARAMVO();
			snpParVO.setREP_ID(snpId);
			snpParVO =(REP_SNAPSHOT_PARAMVO) genericDAO.selectByPK(snpParVO);
			String snpFrequency = snpParVO.getSNAPSHOT_FREQUENCY();
			StringBuffer sitcomBuffer = commonRepFuncBO.generateSitcom(BigDecimal.ONE, appName, snpId,snpParVO.getREP_REFERENCE(),
					compCode, new Date(), snpFrequency, parameters,null);
			reportBytes = sitcomBuffer.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
			reportOutputCO.setOutputFormat(ConstantsCommon.TXT_EXT);
	    }
	    else
	    {
		String imgFolderName = RepConstantsCommon.IMAGES_LOCATION;
		String imgDir = repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + zRepName + "/"
			+ imgFolderName;
		/*
		 * create a new directory to save temporarily blob or chart
		 * images
		 */
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
		SimpleHtmlExporterOutput output = new SimpleHtmlExporterOutput(byteArray);
		String imageUri = this.getReportingPortalURL() + "servlets/image?deleteImg=true&image=" + repFolderName
			+ "/" + imgFolderName + "/";
		HtmlResourceHandler fileHdl = new FileHtmlResourceHandler(imgDirFile, imageUri + "{0}");
		/* new HashMap for imageMap*/
		fileHdl = new MapHtmlResourceHandler(fileHdl, new HashMap());
		output.setImageHandler(fileHdl);
		exporter.setExporterOutput(output);
//		exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, Boolean.FALSE);
		SimpleHtmlReportConfiguration configuration = new SimpleHtmlReportConfiguration();
		/* setting a zoom ration because jasper is replacing pt by  px in html which is resulting in a decrease in the font size*/
		configuration.setZoomRatio(1.334F);
		exporter.setConfiguration(configuration);
		exporter.exportReport();

		// check if images are created then zip the folder
		if((new PathFileSecure(imgDir)).list().length > 0)
		{
		    imgsCreated = true;
		}
		
		reportOutputCO.setOutputFormat(ConstantsCommon.HTML_REP_FORMAT);
		reportBytes = byteArray.toByteArray();
	    }

	    // delete the temporarily created report folder
	    if(!imgsCreated)
	    {
		File tempFile = new PathFileSecure(repositoryPath + "/" + repFolderName);
		if(tempFile.exists())
		{
		    FileUtil.deleteFolder(repositoryPath + "/" + repFolderName);
		}
	    }

	    reportOutputCO.setReportBytes(reportBytes);
	    reportOutputCO.setPagesNbr(pagesNbr);

	    return reportOutputCO;
	}
	catch(Exception e)
	{
	    log.error(e, e.getMessage());
	    throw new BaseException("Error when printing snapshot"+e.getMessage(),e);
	}
	finally
	{
	    // delete the temporarily folder containing the jrxml files
	    try
	    {
		FileUtil.deleteFolder(repositoryPath + "/" + var_reportName + "/");
	    }
	    catch(Exception e)
	    {
		log.error(e,"");
	    }
	}

    }
    
    private void generateSnpSubReports(List<IRP_SNAPSHOT_SUB_REPORTVO> subRepList, String var_reportName,
	    boolean isModifySnp, String reportFormat, boolean showHeadFoot) throws Exception
    {
	String subRepExpr;
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String jrxmlPath;
	try
	{
	    for(IRP_SNAPSHOT_SUB_REPORTVO subrep : subRepList)
	    {
		subRepExpr = subrep.getSUB_REPORT_EXPRESSION();
		jrxmlPath = repositoryPath + "/" + var_reportName + "/"+subRepExpr;
		createJRXMLFile(subrep.getJRXML_FILE(), jrxmlPath );

		// compile subreport .jrxml file into .jasper
		compileReportSnapshot(jrxmlPath, isModifySnp, null, reportFormat, showHeadFoot);
	    }

	}
	catch(Exception e)
	{
	    log.error(e, "Error in compileReport() method in ReportBOImpl.java");
	    throw e; //new Exception("report.compile.exceptionMsg._key");
	}
    }

    //  /**********************************************************************************/
    
    

    private File compileReportSnapshot(String report,boolean isModifySnp,REP_SNAPSHOT_INFOVO infoVO,String reportFormat,boolean showHeadFoot) throws JRException, FileNotFoundException,BaseException
    {

	JasperDesign jasperDesign = JRXmlLoader.load(report + ".jrxml");
	if(isModifySnp && infoVO!=null)
	{
	    //return the list of modified columns
	    
	    try
	    {
		SnapshotParameterSC paramSC=new SnapshotParameterSC();
		paramSC.setREP_ID(infoVO.getREP_ID());
		HashMap<String ,REP_SNAPSHOT_MODIFY_COLUMNCO>editFeMap = snapshotParameterBO.retModifiedColToEdit(paramSC);
		
		ReportUtils.addFieldProperty(jasperDesign, null ,editFeMap);
	    }
	    catch(BaseException e)
	    {
		log.error(e,"");
	    }
	    
	}

	// if("ar".equalsIgnoreCase(language))
	// {
	// ReportUtils.mirrorLayout(jasperDesign, subrep, reportFormat);
	// }
	// update the image path inisde the jrxml
//	 if(!menu.endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
//	 {
	 try 
	 {
		ReportUtils.updateImageLocation(jasperDesign,getReportingImageURL(), "",reportFormat);
	} 
	catch (Exception e) 
	 {
		log.error(e, e.getMessage());
	}
//	 }
	ReportUtils.addRepGlobalParam(jasperDesign);
	
	if(ConstantsCommon.SQL_REP_FORMAT.equals(reportFormat))
	{
	    ReportUtils.addFieldProperty(jasperDesign, new HashMap<String, String>());
	}
	// check if we should remvoe the header and footer
	if(showHeadFoot)
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
    public void verifyReport(HashMap <String,Object> valuesMap) throws BaseException
    {
	try
	{
	IRP_VERIFIED_REPORTSVO vo = new IRP_VERIFIED_REPORTSVO();
	Iterator<Map.Entry<String, Object>> verRepIt = valuesMap.entrySet().iterator();
	while(verRepIt.hasNext())
	{
	    Entry<String, Object> ent = verRepIt.next();
	    PathPropertyUtil.setProperty(vo,ent.getKey(), ent.getValue());
	}
	BigDecimal id = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_VERIFIED_REPORTS);
	vo.setVERIFICATION_ID(id);
	reportDAO.verifyReport(vo);	
	}
	catch(Exception e)
	{
	    throw new BaseException("Error when verifying the report: "+e.getMessage(),e);
	}
    }
    /**
     * Verify the Report or snapshot by inserting a record in IRP_VERIFIED_REPORT 
     * including info of the user, verification date...
     * 
     * @param IRP_VERIFIED_REPORTSVO
     * @return
     * @throws BaseException
     */
    public void verifyReport(IRP_VERIFIED_REPORTSVO vo) throws BaseException
    {
	try
	{
	
	BigDecimal id = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_VERIFIED_REPORTS);
	vo.setVERIFICATION_ID(id);
	reportDAO.verifyReport(vo);	
	}
	catch(Exception e)
	{
	    throw new BaseException("Error when verifying the report: "+e.getMessage(),e);
	}
    }
    /**
     * Method to throw correct Exception
     * @param message
     * @throws Exception
     */
    private Exception throwException(Exception e, String message) throws Exception
    {
	/*this control has been added in case of jasper exception 
	 * 	To show the error in the log file since the jasper jar is not included into the presentation war of other application 
	 * 	To show the message provided by the exception at client side
	 */
	if(e instanceof BOException)
	{
	    throw e;
	}
	else
	{
	    log.error(e,"");
	    throw new BOException(message);
	}
    }


    @Override
    public JasperPrint fillJasperReport(JasperReport jasperReport, Map parameters, Connection con) throws BaseException
    {
	try
	{
	    return JasperFillManager.fillReport(jasperReport, parameters, con);
	}
	catch(JRException e)
	{
	    throw new BOException(e.getMessage(), e);
	}
    }
    
    @Override
    public void retrieveMetadataReport(IRP_AD_HOC_REPORTCO reportSession, Map parameters, String saveCopyLocation,
	    String menu, int decimalPts, String appName, String usrName, String language, String printerName,
	    BigDecimal dbConn, boolean noHeadAndFoot, String csvSepartor, BigDecimal fromPage, BigDecimal toPage,
	    String fromMenu, HashMap<String, String> transMsgLangMap, byte[] origReportBytes, String origRepExt,
	    int mainPageCnt, Connection conn, ReportParamsCO repParamsCO, String origFormat, String mainReportName,
	    JasperPrint mainReportJasperPrint, JasperReport mainJasperReport) throws Exception
    {
	// if the value in the retrieval screen format value is other then html,
	// generate the main report in
	// the selected format also under repository.
	String llOrigRepExt = origRepExt;
	byte[] mainReportBytes = origReportBytes;
	JRAbstractExporter exporter = null;
	ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
	String repositoryPath= ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	if(ConstantsCommon.TRUE.equals(fromMenu))
	{
	    if(ConstantsCommon.SQL_REP_FORMAT.equalsIgnoreCase(origFormat))
	    {
		llOrigRepExt = ConstantsCommon.SQL_REP_FORMAT;
		// create the sql statments;
		try
		{
		    String report = repositoryPath + "/" + mainReportName;
		    JasperDesign jasperDesign = JRXmlLoader.load(report + ".jrxml");
		
		    HashMap<String,Object>  retMap= createDynamicRowDataJrxml(noHeadAndFoot, mainReportName, "", reportSession);
		    reportSession.setJasperDesignFieldsMap((LinkedHashMap<String,String>)retMap.get("1"));
		    ReportUtils.addFieldProperty(jasperDesign, new HashMap<String, String>());
		    try
		    {
			// compile a .jrxml file
			JasperCompileManager.compileReportToFile(jasperDesign, report + ".jasper");
		    }
		    catch(Exception e)
		    {
			throw throwException(e,
				"Error compiling the report having reference: " + reportSession.getPROG_REF()
					+ " and application: " + reportSession.getAPP_NAME() + "\n " + e.getMessage());
		    }
		    File reportFile = new PathFileSecure(report + ".jasper");
		    if(!reportFile.exists())
		    {
			throw new JRRuntimeException(
				report + ".jasper file not found for report reference: " + reportSession.getPROG_REF()
					+ " and report application: " + reportSession.getAPP_NAME());
		    }

		    JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

		    LinkedHashMap jasDesMap = reportSession.getJasperDesignFieldsMap();
		    StringBuffer sbVal = new StringBuffer("");
		    if(!jasDesMap.isEmpty())
		    {
			StringBuffer sbInsert = new StringBuffer("");
			sbInsert.append("INSERT INTO " + mainReportName + "(");
			Iterator it = jasDesMap.keySet().iterator();
			while(it.hasNext())
			{
			    sbInsert.append((String) it.next() + ",");
			}

			sbInsert = new StringBuffer(sbInsert.substring(0, sbInsert.length() - 1));

			sbInsert.append(")");
			sbVal = createResultSql(jasperPrint, sbInsert, jasDesMap);
		    }
		    mainReportBytes = sbVal.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when exporting report to SQL format for report reference: "
			    + reportSession.getPROG_REF() + " and report application: " + reportSession.getAPP_NAME()
			    + "\n " + e.getMessage(), e);
		}
	    }
	    else if(ConstantsCommon.PDF_REP_FORMAT.equalsIgnoreCase(origFormat))
	    {
		llOrigRepExt = ConstantsCommon.PDF_EXT;
		try
		{
		    exporter = new JRPdfExporter();
		    exporter.setParameter(JRPdfExporterParameter.JASPER_PRINT, mainReportJasperPrint);
		    exporter.setParameter(JRPdfExporterParameter.CHARACTER_ENCODING, CommonUtil.ENCODING);
		    exporter.setParameter(JRPdfExporterParameter.OUTPUT_STREAM, byteArray);
		    exporter.exportReport();
		    mainReportBytes = byteArray.toByteArray();
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when exporting report to PDF format for report reference: "
			    + reportSession.getPROG_REF() + " and report application: " + reportSession.getAPP_NAME()
			    + "\n " + e.getMessage(), e);
		}
	    }
	    else if(origFormat.equalsIgnoreCase(ConstantsCommon.DOC_REP_FORMAT))
	    {
		try
		{
		    llOrigRepExt = ConstantsCommon.DOC_EXT;
		    exporter = new JRDocxExporter();
		    exporter.setParameter(JRDocxExporterParameter.JASPER_PRINT, mainReportJasperPrint);
		    exporter.setParameter(JRDocxExporterParameter.OUTPUT_STREAM, byteArray);
		    exporter.setParameter(JRDocxExporterParameter.FLEXIBLE_ROW_HEIGHT, Boolean.TRUE);
		    exporter.setParameter(JRDocxExporterParameter.CHARACTER_ENCODING, CommonUtil.ENCODING);
		    exporter.exportReport();
		    mainReportBytes = byteArray.toByteArray();
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when exporting report to DOCX format for report reference: "
			    + reportSession.getPROG_REF() + " and report application: " + reportSession.getAPP_NAME()
			    + "\n " + e.getMessage(), e);
		}
	    }
	    else if(ConstantsCommon.XLS_REP_FORMAT.equalsIgnoreCase(origFormat)
		    || ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equalsIgnoreCase(origFormat))
	    {
		try
		{
		    llOrigRepExt = ConstantsCommon.XLSX_EXT;
		    JRXlsxExporter exporter1 = new JRXlsxExporter();
		    exporter1.setParameter(JRXlsExporterParameter.JASPER_PRINT, mainReportJasperPrint);
		    exporter1.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, byteArray);
		    exporter1.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
		    exporter1.setParameter(JRXlsExporterParameter.IS_DETECT_CELL_TYPE, Boolean.TRUE);
		    exporter1.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
		    exporter1.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
		    exporter1.setParameter(JRXlsExporterParameter.IS_COLLAPSE_ROW_SPAN, Boolean.TRUE);
		    exporter1.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
		    exporter1.exportReport();
		    mainReportBytes = byteArray.toByteArray();
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when exporting report to XLS format for report reference: "
			    + reportSession.getPROG_REF() + " and report application: " + reportSession.getAPP_NAME()
			    + "\n " + e.getMessage(), e);
		}
	    }
	    else if(ConstantsCommon.CSV_REP_FORMAT.equalsIgnoreCase(origFormat)
		    || ConstantsCommon.TEXT_ROW_DATA_REP_FORMAT.equals(origFormat)
		    || origFormat.equalsIgnoreCase(ConstantsCommon.CSV_EXT_REP_FORMAT))
	    {
		try
		{
		    llOrigRepExt = ConstantsCommon.TXT_EXT;
		    exporter = new JRCsvExporter();
		    exporter.setParameter(JRCsvExporterParameter.JASPER_PRINT, mainReportJasperPrint);
		    exporter.setParameter(JRCsvExporterParameter.OUTPUT_STREAM, byteArray);
		    exporter.setParameter(JRCsvExporterParameter.FIELD_DELIMITER, csvSepartor);
		    exporter.exportReport();
		    mainReportBytes = byteArray.toByteArray();
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when exporting report to CSV format for report reference: "
			    + reportSession.getPROG_REF() + " and report application: " + reportSession.getAPP_NAME()
			    + "\n " + e.getMessage(), e);
		}
	    }
	    else if(ConstantsCommon.TXT_FILE.equalsIgnoreCase(origFormat)
		    || ConstantsCommon.DAT_EXT.equalsIgnoreCase(origFormat))
	    {
		parameters.put(RepConstantsCommon.SITCOM_IS_NEW, RepConstantsCommon.Y);
		parameters.put(RepConstantsCommon.SITCOM_PATH_CNT, Integer.valueOf(-1));
		parameters.put(RepConstantsCommon.JASPER_PATH_ARR, new ArrayList<HashMap<String, Object>>());
		if(ConstantsCommon.DAT_EXT.equals(origFormat))
		{
		    llOrigRepExt = ConstantsCommon.DAT_EXT.toLowerCase();
		}
		else if(ConstantsCommon.TXT_FILE.equals(origFormat))
		{
		    llOrigRepExt = ConstantsCommon.TXT_EXT;
		}
		JasperFillManager.fillReport(mainJasperReport, parameters, conn);
		SnapshotParameterSC snParamSC = new SnapshotParameterSC();
		snParamSC.setREP_REFERENCE(StringUtil.isNotEmpty(reportSession.getFTR_OPT_PROG_REF())
			? reportSession.getFTR_OPT_PROG_REF() : reportSession.getPROG_REF());
		int checkIfFormulaExists =snapshotParameterBO.checkTextFormulaExist(snParamSC);
		if(checkIfFormulaExists==0)
		{
		    throw new BaseException("No formula defined for the main report : " + snParamSC.getREP_REFERENCE()+"\n ");
		}
		else
		{
		    BigDecimal repSnpId = new BigDecimal(commonRepFuncBO
			    .retMinRepSnapshotRepId(StringUtil.isNotEmpty(reportSession.getFTR_OPT_PROG_REF())
				    ? reportSession.getFTR_OPT_PROG_REF() : reportSession.getPROG_REF()));
		    REP_SNAPSHOT_PARAMVO snpParVO = new REP_SNAPSHOT_PARAMVO();
		    snpParVO.setREP_ID(repSnpId);
		    String snpFrequency = ((REP_SNAPSHOT_PARAMVO) genericDAO.selectByPK(snpParVO))
			    .getSNAPSHOT_FREQUENCY();
		    Date retrieveDte = new Date();
		    StringBuffer sitcomBuffer = commonRepFuncBO.generateSitcom(BigDecimal.ONE, appName, repSnpId,
			    reportSession.getPROG_REF(), reportSession.getCOMP_CODE(), retrieveDte, snpFrequency,
			    parameters, null);
		    parameters.remove(RepConstantsCommon.SITCOM_IS_NEW);
		    parameters.remove(RepConstantsCommon.SITCOM_PATH_CNT);
		    parameters.remove(RepConstantsCommon.JASPER_PATH_ARR);
		    mainReportBytes = sitcomBuffer.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
		}
	    }
	}
	IRP_AD_HOC_REPORTCO metadataReportCO = returnReportById(reportSession.getMETADATA_REPORT_ID());
	File reportFile = null;
	String var_reportName = metadataReportCO.getPROG_REF() + "_" + metadataReportCO.getAPP_NAME() + "_"
		    + usrName.replace(".", "");
	String mdReportFormat = reportSession.getMETADATA_REPORT_EXT().substring(
		reportSession.getMETADATA_REPORT_EXT().lastIndexOf(".")+1,
		reportSession.getMETADATA_REPORT_EXT().length());
	try
	{
	    String report= repositoryPath + "/" + var_reportName;
	    // create a temporarily folder to put all jrxml files
	    FileUtil.makeDirectories(report + "/"); 
	    // create hash Table for procedures for Sybase
	    if(commonLibBO.returnIsSybase() == 1 && metadataReportCO.getHashTblList().size() > 0)
	    {
		try
		{
		procBO.createHashTbl(conn, metadataReportCO.getHashTblList());
		}
		catch(Exception e)
		{
		    throw new BaseException("Error occured when creating table hash for report reference: " + metadataReportCO.getPROG_REF() + " and report application: "+ metadataReportCO.getAPP_NAME() + "\n "+e.getMessage(),e);
		}
		
	    }

	    HashMap<String, String> hypParamsValMap = null;
	    if(!menu.endsWith(ConstantsCommon.DESIGNER_PROG_REF))
	    {
		// create jrxml file under repository
		createJRXMLFile(metadataReportCO.getJRXML_FILE(), report);
	    }
	    // fill the hpyerlinks params value
	    if(metadataReportCO.getREPORT_ID() != null)
	    {
		hypParamsValMap = retHypParamsVal(metadataReportCO, repParamsCO);
	    }

	    String wp = (String) parameters.get("w_p");

		reportFile = compileReport(report, menu, mdReportFormat, metadataReportCO, language,
			hypParamsValMap, noHeadAndFoot, ConstantsCommon.FALSE, repParamsCO, null, saveCopyLocation, wp,metadataReportCO.getWHEN_NO_DATA());// add

	    // call Before procedures
	   
		HashMap<String, Object> outProcValuesMap = new HashMap<String, Object>();
		outProcValuesMap = callProcedures(BigDecimal.ZERO, parameters, metadataReportCO, menu, conn, transMsgLangMap);
		//Sending the procedure output parameter values as values to the input parameters of the procedure 
		String entryKey;
		for (Entry<String, Object> entry: outProcValuesMap.entrySet()) {
		    // Check if the current value is a key in the 2nd map
		    entryKey = entry.getKey();
		    parameters.put(entryKey,entry.getValue());
		}
	    

	    List<IRP_AD_HOC_REPORTCO> subReportCOList = new ArrayList<IRP_AD_HOC_REPORTCO>();

	    if(metadataReportCO.getSubreportsList() != null && metadataReportCO.getSubreportsList().size() > 0)
	    {
		generateSubReports(metadataReportCO.getSubreportsList(), subReportCOList, hypParamsValMap, repParamsCO,
			decimalPts, mdReportFormat, language, noHeadAndFoot, ConstantsCommon.FALSE, parameters, conn, var_reportName,
			saveCopyLocation, wp);
		parameters.put("SUBREPORT_DIR", repositoryPath + "/" + var_reportName + "/");
	    }
	    JasperReport jasperReport=null;
	    try
	    {
		jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
	    }
	    catch(Exception e)
	    {
		throw throwException(e, "Error in loading the file for report reference: " + metadataReportCO.getPROG_REF() + " and report application: "+ metadataReportCO.getAPP_NAME() + "\n "+e.getMessage());
	    }
	    JasperPrint jasperPrint = null;
	    StringBuffer sitcomBuffer = new StringBuffer();
	    try
	    {
	 	//tracing for the executed report
		BaseObject baseObj=new BaseObject();
		baseObj.applyTraceProps(appName,usrName,metadataReportCO.getPROG_REF());
		genericDAO.callSqlSessionTrace(baseObj, "r:"+metadataReportCO.getPROG_REF()+" ra:"+metadataReportCO.getAPP_NAME(), conn);

			parameters.put(RepConstantsCommon.SITCOM_IS_NEW, RepConstantsCommon.Y);
			parameters.put(RepConstantsCommon.SITCOM_PATH_CNT, Integer.valueOf(-1));
			parameters.put(RepConstantsCommon.JASPER_PATH_ARR, new ArrayList<HashMap<String, Object>>());


		    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, conn);

    		    BigDecimal repSnpId;
    		    try
    		    {
    			 repSnpId = new BigDecimal(commonRepFuncBO.retMinRepSnapshotRepId(StringUtil
    				    .isNotEmpty(metadataReportCO.getFTR_OPT_PROG_REF()) ? metadataReportCO
    				    .getFTR_OPT_PROG_REF() : metadataReportCO.getPROG_REF()));
    		    }
    		    catch(Exception e)
    		    {
    			CommonLibSC sc = new CommonLibSC();
    			sc.setAppName("IMAL");
    			sc.setProgRef("ROOT");
    			sc.setLanguage(language);
    			sc.setKeyLabelCode("metadataUndefinedFormula");
    			String metadataUndefinedFormula = commonLibBO.returnKeyLabelTrans(sc);
    			throw new BaseException(metadataUndefinedFormula+"\n ","",null);
    		    }
			REP_SNAPSHOT_PARAMVO snpParVO = new REP_SNAPSHOT_PARAMVO();
			snpParVO.setREP_ID(repSnpId);
			String snpFrequency = ((REP_SNAPSHOT_PARAMVO) genericDAO.selectByPK(snpParVO))
				.getSNAPSHOT_FREQUENCY();
			Date retrieveDte = new Date();
			HashMap<Object,Object> sitcomParamsMap = new HashMap<Object,Object>();
			sitcomParamsMap.put(RepConstantsCommon.MAIN_REP_SIZE, Integer.valueOf(origReportBytes.length));
			sitcomParamsMap.put(RepConstantsCommon.MAIN_PAGE_COUNT, Integer.valueOf(mainPageCnt));
			//used reportSession since compCode set from session in the action
			sitcomBuffer = commonRepFuncBO.generateSitcom(BigDecimal.ONE, metadataReportCO.getAPP_NAME(), repSnpId,
				metadataReportCO.getPROG_REF(), repParamsCO.getCompanyCode(), retrieveDte, snpFrequency,
				parameters,sitcomParamsMap);
			parameters.remove(RepConstantsCommon.SITCOM_IS_NEW);
			parameters.remove(RepConstantsCommon.SITCOM_PATH_CNT);
			parameters.remove(RepConstantsCommon.JASPER_PATH_ARR);

	    	    }
	    catch(Exception e)
	    {
		throw new BaseException("Error when filling the report for report reference: " + metadataReportCO.getPROG_REF() + " and report application: "+ metadataReportCO.getAPP_NAME() + "\n "+e.getMessage(),e);
	    }
	    
	    String metaRepExt = reportSession.getMETADATA_REPORT_EXT().substring(reportSession.getMETADATA_REPORT_EXT().lastIndexOf(".")+1,reportSession.getMETADATA_REPORT_EXT().length());
	    byte[] reportBytes = null;

		    reportBytes = sitcomBuffer.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);

	    // call After procedures
	    callProcedures(BigDecimal.ONE, parameters, metadataReportCO, menu, conn,transMsgLangMap);
	    // call subreport After procedures
	    for(IRP_AD_HOC_REPORTCO subRepCO : subReportCOList)
	    {
		callProcedures(BigDecimal.ONE, parameters, subRepCO, subRepCO.getPROG_REF(), conn, transMsgLangMap);
	    }

	    Date sysDate = commonLibBO.returnSystemDateWithTime();
	    String generatedPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder)
		    + "/" + RepConstantsCommon.IRP_METADATA_ROOT_FOLDER + "/"
		    + reportSession.getMETADATA_GENERATED_LOCATION();
	    File tempFile = new PathFileSecure(generatedPath);
	    if(!tempFile.exists())
	    {
		FileUtil.makeDirectories(tempFile.getPath());
	    }
	    String generatedFileName = commonReportingBO.returnGeneratedFileName(reportSession.getGENERATED_FILE_NAME(),new HashMap());
	    //check if report already generated with same arguments
	    IRP_AD_HOC_REPORTSC metaSC = new IRP_AD_HOC_REPORTSC();
	    metaSC.setREPORT_ID(reportSession.getREPORT_ID());
	    ArrayList<IRP_REP_METADATA_LOGVO> irpRepMetaLogList = designerBO.retAlreadyExportedArgsLogs(metaSC);
	    String[] paramStr;
	    String argName;
	    String argVal;
	    IRP_REP_METADATA_LOGVO metaVO = new IRP_REP_METADATA_LOGVO();
	    boolean newArgsCombination = true;	    
	    List<IRP_REP_ARGUMENTSCO> argumentsList = new ArrayList(reportSession.getArgumentsList().values());
	    if(argumentsList.isEmpty() && !irpRepMetaLogList.isEmpty())
	    {
		newArgsCombination = false;
		metaVO = irpRepMetaLogList.get(0);
	    }
	    else
	    {
	    // looping on all the records in metadata table
	    for(int i = 0; i < irpRepMetaLogList.size(); i++)
	    {
		// if same parameters already used exit and metaVO will contain
		// the related db record.
		if(!newArgsCombination)
		{
		    break;
		}
		metaVO = irpRepMetaLogList.get(i);
		// for each db meta record do the below
		paramStr = StringUtil.toStringArray(metaVO.getREPORT_ARGUMENTS(), "~#~");
		// handling the case where the user adds a new argument or
		// deletes an existing argument in upload/download
		if(argumentsList.size() != paramStr.length)
		{
		    // go to the next metadata log
		    continue;
		}
		// looping on each argument in the record of metadata
		for(int j = 0; j < paramStr.length; j++)
		{
		    argName = paramStr[j].substring(0, paramStr[j].indexOf(":"));
		    argVal = paramStr[j].substring(paramStr[j].indexOf(":") + 1, paramStr[j].length());
		    // if for this meta record, argument saved value different
		    // then screen value go to the next meta db record
		    if((parameters.get(argName) == null && !"null".equals(argVal))
			    				|| 
		       (parameters.get(argName) != null && (!parameters.get(argName).toString().equals(argVal))))
		    {
			// means the current record in db is a different one.
			break;
		    }
		    // end of the db record reached and no break occured=>same
		    // values already saved from a previous retrieve/export
		    if(j == paramStr.length - 1)
		    {
			newArgsCombination = false;
		    }
		}
	    }
	    }
	    if(newArgsCombination)
	    {
		StringBuffer valueParamSb = new StringBuffer("");
		IRP_REP_ARGUMENTSCO argObj;
		for(int i = 0; i < argumentsList.size(); i++)
		{
		    argObj = argumentsList.get(i);
		    argName = argObj.getARGUMENT_NAME();
		    valueParamSb.append(argName + ":" + parameters.get(argName));
		    if(i != argumentsList.size() - 1)
		    {
			valueParamSb.append("~#~");
		    }
		}
		metaVO = new IRP_REP_METADATA_LOGVO();
		BigDecimal logId = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_REP_METADATA_LOG);
		metaVO.setLOG_ID(logId);
		metaVO.setREPORT_ID(reportSession.getREPORT_ID());
		metaVO.setPROG_REF(reportSession.getPROG_REF());
		metaVO.setREPORT_ARGUMENTS(valueParamSb.toString().isEmpty()?null:valueParamSb.toString());
		metaVO.setREP_FILE_NAME(generatedFileName);
		metaVO.setLOG_DATE(sysDate);
		genericDAO.insert(metaVO);
	    }
	    //delete already generated files and replace with newer
	    else
	    {
		File file = new PathFileSecure(generatedPath + "/" + metaVO.getREP_FILE_NAME() + RepConstantsCommon.REP_EXT_OUT);
		boolean isDel = file.delete();
		if(!isDel)
		{
		    log.debug("\n\n  >>>>>>>>>>>>  The following file has not been deleted :" + generatedPath + "/"
			    + metaVO.getREP_FILE_NAME() + RepConstantsCommon.REP_EXT_OUT);
		}
		file = new PathFileSecure(generatedPath + "/" + metaVO.getREP_FILE_NAME() + "." + metaRepExt);
		isDel = file.delete();
		if(!isDel)
		{
		    log.debug("\n\n  >>>>>>>>>>>>  The following file has not been deleted :" + generatedPath + "/"
			    + metaVO.getREP_FILE_NAME() + "." + metaRepExt);
		}
		File folder = new PathFileSecure(generatedPath);
		File fList[] = folder.listFiles();
		//in order to delete the main report in case export to doc then pdf then xlsx
		String fileName;
		for(int i = 0; i < fList.length; i++)
		{
		    fileName = fList[i].getName();
		    if(fileName.lastIndexOf(".")!=-1 && (fileName.equals(metaVO.getREP_FILE_NAME()
			    + fileName.substring(fileName.lastIndexOf("."), fileName.length()))))
		    {
			isDel = fList[i].delete();
			if(!isDel)
			{
			    log.debug("\n\n  >>>>>>>>>>>>  The following file has not been deleted :" + generatedPath
				    + "/" + fileName);
			}
		    }
		}
		metaVO.setREP_FILE_NAME(generatedFileName);
		metaVO.setLOG_DATE(sysDate);
		genericDAO.update(metaVO);
	    }
	    if(ConstantsCommon.DAT_EXT.equals(origRepExt))
	    {
		llOrigRepExt = ConstantsCommon.DAT_EXT.toLowerCase();
	    }
	    else if(ConstantsCommon.TXT_FILE.equals(origRepExt))
	    {
		llOrigRepExt = ConstantsCommon.TXT_EXT;
	    }
	    // write the metadata report to repository location
	    String metadataLocation = "/" + ReportingConstantsCommon.reportingFolder + "/"
		    + RepConstantsCommon.IRP_METADATA_ROOT_FOLDER + "/" + reportSession.getMETADATA_GENERATED_LOCATION()
		    + "/" + generatedFileName;
	    FileUtil.saveToRepository(reportBytes, ReportingConstantsCommon.repositoryFolder,
		    metadataLocation + "." + metaRepExt);
	    FileUtil.saveToRepository(mainReportBytes, ReportingConstantsCommon.repositoryFolder,
		    metadataLocation + "." + llOrigRepExt);
	    FileUtil.saveToRepository(mainReportBytes, ReportingConstantsCommon.repositoryFolder,
		    metadataLocation + RepConstantsCommon.REP_EXT_OUT);
	}
	catch(Exception e)
	{
	    if(e.getMessage()!=null && e.getMessage().indexOf(transMsgLangMap.get("1"))==0)
	    {
		throw throwException(e, e.getMessage().substring(transMsgLangMap.get("1").length()) );
	    }
	    else
	    {
		throw throwException(e, e.getMessage() + transMsgLangMap.get("2")+ metadataReportCO.getPROG_REF() + transMsgLangMap.get("3")+ metadataReportCO.getAPP_NAME());
	    }
	}
	finally
	{
	    	String repositoryMetadataPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
		String path = repositoryMetadataPath + "/" + var_reportName;
		File file;
		file = new PathFileSecure(path + ".jrxml");
		boolean isDel = file.delete();
		if(!isDel)
		{
		    log.debug("The following file has not been deleted : " + path + ".jrxml");
		}
		file = new PathFileSecure(path + ".jasper");
		isDel = file.delete();
		if(!isDel)
		{
		    log.debug("The following file has not been deleted : " + path + ".jasper");
		}
	    // drop hash Table for imported procedures in case db=Sybase
	    if(commonLibBO.returnIsSybase() == 1 && metadataReportCO.getHashTblList().size() > 0)
	    {
		try
		{
		procBO.dropHashTbl(conn, metadataReportCO.getHashTblList());
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when dropping hash table for report reference: " + metadataReportCO.getPROG_REF() + " and report application: "+ metadataReportCO.getAPP_NAME() + "\n "+e.getMessage(),e);
		}
	    }
	    try{
	    // delete the temporarily folder containing the jrxml files
	    FileUtil.deleteFolder(repositoryPath + "/" + var_reportName + "/");
	    }
	    catch(Exception e)
		{
		    throw new BaseException(e, "Error when deleting temporary report folder from repository for report reference: " + metadataReportCO.getPROG_REF() + " and report application: "+ metadataReportCO.getAPP_NAME() + "\n "+e.getMessage(),e);
		}

	}
    }

    /**
     * Method to return array list of CIF in the report
     * @param jasperPrint
     * @param reportCO
     * @return
     */
    private HashMap<BigDecimal, BigDecimal> retCifList(JasperPrint jasperPrint, IRP_AD_HOC_REPORTCO reportCO)
    {
	HashMap<BigDecimal, BigDecimal> cifMap = new HashMap<BigDecimal, BigDecimal>();
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
	LinkedHashMap jasDesMap = reportCO.getJasperDesignFieldsMap();
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
			txtVal = (String) txtFe.getValue();
		    }
		    else
		    {
			txtVal = txtFe.getFullText();
		    }
		    if("1".equals(txtFe.getPropertiesMap().getProperty("fe.cifAudit")))
		    {
			if(!StringUtil.nullToEmpty(txtVal).isEmpty())
			{
			    try
			    {
				cifMap.put(new BigDecimal(txtVal), new BigDecimal(txtVal));
			    }
			    catch(NumberFormatException e)
			    {
				log.debug("Warning: fe.cifAudit related field not a number "+e.getMessage());
			    }
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
					txtVal = (String) txtFe.getValue();
				    }
				    else
				    {
					txtVal = txtFe.getFullText();
				    }
				    if("1".equals(txtFe.getPropertiesMap().getProperty("fe.cifAudit")))
				    {
					if(!StringUtil.nullToEmpty(txtVal).isEmpty())
					{
					    try
					    {
						cifMap.put(new BigDecimal(txtVal), new BigDecimal(txtVal));
					    }
					    catch(NumberFormatException e)
					    {
						log.debug("Warning: fe.cifAudit related field not a number "+e.getMessage());
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
				txtVal = (String) txtFe.getValue();
			    }
			    else
			    {
				txtVal = txtFe.getFullText();
			    }
			    if("1".equals(txtFe.getPropertiesMap().getProperty("fe.cifAudit")))
			    {
				if(!StringUtil.nullToEmpty(txtVal).isEmpty())
				{
				    try
				    {
					cifMap.put(new BigDecimal(txtVal), new BigDecimal(txtVal));
				    }
				    catch(NumberFormatException e)
				    {
					log.debug("Warning: fe.cifAudit related field not a number "+e.getMessage());
				    }
				}
			    }
			}
		    }
		}
	    }
	}
	return cifMap;
    }
    
    public HashMap<String, Object> returnReportByteArrayCurrentPage(HashMap<String, Object> paramsMap)
	    throws BaseException
    {
	IRP_AD_HOC_REPORTSC paginationSC = new IRP_AD_HOC_REPORTSC();
	paginationSC = (IRP_AD_HOC_REPORTSC) PathPropertyUtil.convertToObject(paramsMap, IRP_AD_HOC_REPORTSC.class);
	paginationSC.setRepPaginationBytes(reportDAO.returnReportByteArrayCurrentPage(paginationSC));
	HashMap<String, Object> map = PathPropertyUtil.convertToMap(paginationSC);
	return map;
    }
}
