package com.path.bo.reporting.common.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailSendException;

import com.path.bo.common.CommonLibBO;
import com.path.bo.common.CommonMethods;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.translation.TranslationBO;
import com.path.bo.reporting.CommonReportingBO;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.QueryBO;
import com.path.bo.reporting.ftr.columntemplate.ColumnTemplateBO;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.bo.reporting.mailserver.MailServerConfigBO;
import com.path.dao.reporting.common.CommonReportingDAO;
import com.path.dbmaps.vo.GLSTMPLTVO;
import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVOWithBLOBs;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_FCR_FIXED_COLSVO;
import com.path.dbmaps.vo.IRP_FCR_REPORTSVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTSVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTS_FILTERSVO;
import com.path.dbmaps.vo.IRP_REP_FILTERSVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.SecurityUtils;
import com.path.lib.common.util.StringUtil;
import com.path.lib.reporting.exception.ReportException;
import com.path.vo.common.SessionCO;
import com.path.vo.common.email.MailCO;
import com.path.vo.common.email.MailServerCO;
import com.path.vo.common.translation.TranslationCO;
import com.path.vo.common.translation.TranslationSC;
import com.path.vo.reporting.DynLookupSC;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FCR_REPORTSCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_QUERY_ARG_MAPPINGCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_FILTERSSC;
import com.path.vo.reporting.QRY_GRIDSC;
import com.path.vo.reporting.ReportOutputCO;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.designer.IRP_AD_HOC_QUERYSC;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGCO;
import com.path.vo.reporting.mailServer.IRP_MAIL_SERVER_CONFIGSC;

import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignField;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import java.util.Iterator;
import com.path.dbmaps.vo.IRP_REP_SORTVO;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * @author: annasuccar
 * 
 *          CommonReportingBOImpl.java used as common for all iMAL applications
 *          and therefore the content should not be application specific.
 */
public class CommonReportingBOImpl extends BaseBO implements CommonReportingBO
{
    private DesignerBO designerBO;
    private QueryBO queryBO;
    private CommonLookupBO commonLookupBO;
    private ReportBO reportBO;
    private FcrBO fcrBO;
    private CommonLibBO commonLibBO;
    private MailServerConfigBO mailServerConfigBO;
    private TemplateBO templateBO;
    private ColumnTemplateBO columnTemplateBO;
    private TranslationBO translationBO;
    private CommonReportingDAO commonReportingDAO;
    private SnapshotParameterBO snapshotParameterBO;
	public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO) {
		this.snapshotParameterBO = snapshotParameterBO;
	}

    public void setTranslationBO(TranslationBO translationBO)
    {
        this.translationBO = translationBO;
    }
    
    

    public void setCommonReportingDAO(CommonReportingDAO commonReportingDAO)
    {
        this.commonReportingDAO = commonReportingDAO;
    }
    public void setColumnTemplateBO(ColumnTemplateBO columnTemplateBO)
    {
        this.columnTemplateBO = columnTemplateBO;
    }
    public void setTemplateBO(TemplateBO templateBO)
    {
        this.templateBO = templateBO;
    }
    public void setMailServerConfigBO(MailServerConfigBO mailServerConfigBO)
    {
	this.mailServerConfigBO = mailServerConfigBO;
    }
    public void setCommonLibBO(CommonLibBO commonLibBO)
    {
        this.commonLibBO = commonLibBO;
    }

    
    
    public void setFcrBO(FcrBO fcrBO)
    {
        this.fcrBO = fcrBO;
    }

    public void setDesignerBO(DesignerBO designerBO)
    {
	this.designerBO = designerBO;
    }

    public void setQueryBO(QueryBO queryBO)
    {
	this.queryBO = queryBO;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
	this.commonLookupBO = commonLookupBO;
    }

    public void setReportBO(ReportBO reportBO)
    {
	this.reportBO = reportBO;
    }

    @Override
    public BigDecimal returnConnectionId(String appName) throws ReportException
    {
	try
	{
	    return commonLookupBO.returnConnectionId(appName);
	}
	catch(BaseException e)
	{
	    throw retRepExceptionFromBaseException(e);
	}
	catch(Exception e)
	{
	    throw retRepExceptionFromException(e);
	}
    }

    public HashMap<String,Object> returnReportById(BigDecimal reportId) throws Exception
    {
	IRP_AD_HOC_REPORTCO repCO= designerBO.returnReportById(reportId);
	return PathPropertyUtil.convertToMap(repCO);
    }

    public List<HashMap<String,Object>> getLookupListMap(HashMap<String,Object> sysParamLovMap) throws ReportException
    {
	List<HashMap<String,Object>> retLst=new ArrayList<HashMap<String,Object>>();
	try
	{
    	SYS_PARAM_LOV_TRANSVO sysParamLovTransVO=new SYS_PARAM_LOV_TRANSVO();
    	String[] propsArr=ConstantsCommon.getLookupListMap_PROPS.toArray(new String[ConstantsCommon.getLookupListMap_PROPS.size()]);
    	PathPropertyUtil.copyProperties(sysParamLovMap,sysParamLovTransVO,false,propsArr);
    	List<Object> lstVOs= commonLookupBO.getLookupList(sysParamLovTransVO);
    	
    	
    	SYS_PARAM_LOV_TRANSVO sysVO;
    	HashMap<String,Object> sysVOMap;
    	propsArr=ConstantsCommon.getLookupListMapRet_PROPS.toArray(new String[ConstantsCommon.getLookupListMapRet_PROPS.size()]);
    	for(int i=0;i<lstVOs.size();i++)
    	{
    	    sysVO=(SYS_PARAM_LOV_TRANSVO) lstVOs.get(i)   ;
    	    sysVOMap=new HashMap<String,Object>();
    	    PathPropertyUtil.copyProperties(sysVO, sysVOMap,false,propsArr);
    	    retLst.add(sysVOMap);
    	}
	}
	catch(BaseException e)
	{
	    throw retRepExceptionFromBaseException(e);
	}
	catch(Exception e)
	{
	    throw retRepExceptionFromException(e);
	}
	return retLst;
    }
    
    public List<HashMap<String,Object>> retDBFields(String field, String entityDBNAME) throws Exception
    {
	QRY_GRIDSC qryGridSC = new QRY_GRIDSC();
	qryGridSC.setFIELD_NAME(field);
	qryGridSC.setENTITY_DB_NAME(entityDBNAME);
	qryGridSC.setPARENT_ID(Long.valueOf("0"));
	List<IRP_FIELDSCO> feCOLst= queryBO.retDBFields(qryGridSC);
	List<HashMap<String,Object>> retLst=new ArrayList<HashMap<String,Object>>();
	IRP_FIELDSCO feCO;
	HashMap<String,Object> hm;
	String[] propsArr=ConstantsCommon.retDBFieldsRet_PROPS.toArray(new String[ConstantsCommon.retDBFieldsRet_PROPS.size()]);
	for(int i=0;i<feCOLst.size();i++)
	{
	    feCO=feCOLst.get(i);
	    hm=new HashMap<String, Object>();
	    PathPropertyUtil.copyProperties(feCO, hm,false,propsArr);
	    retLst.add(hm);
	}
	return retLst;
    }

    @Override
    public List<String[]> returnColsList(String qryId) throws Exception, ClassNotFoundException, IOException,
	    SQLException
    {
	return commonLookupBO.returnColsList(qryId);
    }

    
    public Integer returnQryResultCnt(HashMap<String,Object> dynLookupSCMap) throws Exception, ClassNotFoundException,
	    IOException, SQLException
    {
	DynLookupSC dynLookupSC=new DynLookupSC();
	String[] propsArr=ConstantsCommon.returnQryResult_PROPS.toArray(new String[ConstantsCommon.returnQryResult_PROPS.size()]);
	PathPropertyUtil.copyProperties(dynLookupSCMap,dynLookupSC,false,propsArr);
	return commonLookupBO.returnQryResultCnt(dynLookupSC);
    }

    
    public ArrayList<LinkedHashMap> returnQryResult(HashMap<String,Object> dynLookupSCMap) throws Exception,
	    ClassNotFoundException, IOException, SQLException
    {
	DynLookupSC dynLookupSC=new DynLookupSC();
	String[] propsArr=ConstantsCommon.returnQryResult_PROPS.toArray(new String[ConstantsCommon.returnQryResult_PROPS.size()]);
	PathPropertyUtil.copyProperties(dynLookupSCMap,dynLookupSC,false,propsArr);
	return commonLookupBO.returnQryResult(dynLookupSC);
    }

    public BigDecimal retRepIdFromOldRepId(BigDecimal oldRepId) throws Exception
    {
	return designerBO.retRepIdFromOldRepId(oldRepId);
    }

    public HashMap<String,Object> retRepIdByProgRef(HashMap<String,Object> repSCMap) throws Exception
    {
	IRP_AD_HOC_REPORTSC reportSC=new IRP_AD_HOC_REPORTSC();
	String[] propsArr=ConstantsCommon.retRepIdByProgRef_PROPS.toArray(new String[ConstantsCommon.retRepIdByProgRef_PROPS.size()]);
	PathPropertyUtil.copyProperties(repSCMap,reportSC,false,propsArr);
	IRP_AD_HOC_REPORTCO retCO= retRepIdByProgRef(reportSC);
	HashMap<String,Object> retHm=new HashMap<String, Object>();
	if(retCO!=null)
	{
	    retHm= PathPropertyUtil.convertToMap(retCO);
	}
	return retHm;
    }
    
    
    public HashMap<String, Object> retProgRefByRepId(HashMap<String, Object> repSCMap) throws Exception
    {
	IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
	String[] propsArr = ConstantsCommon.retQryArgMapping_PROPS.toArray(new String[ConstantsCommon.retQryArgMapping_PROPS.size()]);
	PathPropertyUtil.copyProperties(repSCMap, reportSC, false, propsArr);
	IRP_AD_HOC_REPORTCO retCO = designerBO.retProgRefByRepId(reportSC);
	HashMap<String, Object> retHm = new HashMap<String, Object>();
	if(retCO != null)
	{
	    retHm = PathPropertyUtil.convertToMap(retCO);
	}
	return retHm;
    }
    
    public IRP_AD_HOC_REPORTCO retRepIdByProgRef(IRP_AD_HOC_REPORTSC reportSC) throws Exception
    {
	String optDetLst[]=commonLibBO.returnOptDetailsList(reportSC.getAPP_NAME(), reportSC.getPROG_REF());
	String mainRepAppName=optDetLst[8];
	String mainProgRef=optDetLst[4];
	log.debug(">>>>>>>>commonReportingBO.retRepIdByProgRef>>>>>>>>>>current AppName=="+reportSC.getAPP_NAME()+"           mainAppName=="+mainRepAppName+"         mainProgRef=="+mainProgRef+".");
	if(!StringUtil.nullToEmpty(mainRepAppName).isEmpty())
	{
	    reportSC.setAPP_NAME(mainRepAppName);
	}
	if(!StringUtil.nullToEmpty(mainProgRef).isEmpty())
	{
	    reportSC.setPROG_REF(mainProgRef);
	}
	return designerBO.retRepIdByProgRef(reportSC);
    }

    public HashMap<String,Object> printReport(String var_reportName, String reportFormat, Map parameters, String saveCopyLocation,
	    String menu, HashMap<String,Object> repSessionCOMap, int decimalPts, String appName, String usrName,
	    String language, String printerName, BigDecimal dbConn, boolean noHeadAndFoot, String csvSepartor,
	    String noData, BigDecimal fromPage, BigDecimal toPage,String origFormat, String fromMenu, HashMap<String,String> TransMsgLangMap) throws Exception
    {   
		String retrieveCall = (String) parameters.get("retrieveCall");
		ReportOutputCO repOutCO= reportBO.printReport(var_reportName, reportFormat, parameters, saveCopyLocation, menu, repSessionCOMap,
		decimalPts, appName, usrName, language, printerName, dbConn, noHeadAndFoot, csvSepartor, noData,
		fromPage, toPage, origFormat, fromMenu, TransMsgLangMap);
	
		HashMap<String,Object> retMap=new HashMap<String,Object>();
		String[] propsArr=ConstantsCommon.printReportRet_PROPS.toArray(new String[ConstantsCommon.printReportRet_PROPS.size()]);
		if(!ConstantsCommon.TRUE.equalsIgnoreCase(retrieveCall))
		{
			List<String> newList = new ArrayList<>(ConstantsCommon.printReportRet_PROPS);
			newList.remove("hasPagination");
			newList.remove("paginationCount");
			propsArr=newList.toArray(new String[newList.size()]);
		}
		PathPropertyUtil.copyProperties(repOutCO, retMap,false,propsArr);
		return retMap;
	
    }

    /**
     * delete jrxml and jasper files
     */
    @Override
    public void deleteTempFiles(String filename) throws Exception
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String path = repositoryPath + "/" + filename;
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
    }

    /**
     * get the list of query arg mapping
     * 
     * @return list
     * @throws Exception
     */
    public List<HashMap<String,Object>> retQryArgMapping(HashMap<String,Object> repSCMap) throws Exception
    {
	IRP_AD_HOC_REPORTSC reportSC=new IRP_AD_HOC_REPORTSC();
	String[] propsArr=ConstantsCommon.retQryArgMapping_PROPS.toArray(new String[ConstantsCommon.retQryArgMapping_PROPS.size()]);
	PathPropertyUtil.copyProperties(repSCMap,reportSC,false,propsArr);
	List<IRP_QUERY_ARG_MAPPINGCO> lstCOs= designerBO.retQryArgMapping(reportSC);
	
	List<HashMap<String,Object>> retLst=new ArrayList<HashMap<String,Object>>();
	IRP_QUERY_ARG_MAPPINGCO argMapCO;
	HashMap<String,Object> hm;
	propsArr=ConstantsCommon.retQryArgMappingRet_PROPS.toArray(new String[ConstantsCommon.retQryArgMappingRet_PROPS.size()]);
	for(int i=0;i<lstCOs.size();i++)
	{
	    argMapCO=lstCOs.get(i);
	    hm=new HashMap<String, Object>();
	    PathPropertyUtil.copyProperties(argMapCO,  hm,false,propsArr);
	    retLst.add(hm);
	}
	return retLst;
    }

    public HashMap<String,Object> returnQueryById(BigDecimal queryId, boolean generateSyntax) throws ReportException,
	    IOException, ClassNotFoundException
    {
	try
	{
	    IRP_AD_HOC_QUERYCO qryCO= queryBO.returnQueryById(queryId, generateSyntax);
	    return PathPropertyUtil.convertToMap(qryCO);
	}
	catch(BaseException e)
	{
	    throw retRepExceptionFromBaseException(e);
	}
	catch(Exception e)
	{
	    throw retRepExceptionFromException(e);
	}

    }

    public void createJRXMLFile(byte[] xml, String reportPath) throws Exception, IOException
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	reportBO.createJRXMLFile(xml, repositoryPath + "/" +reportPath);
    }

    public  HashMap<String,Object> createDynamicRowDataJrxml(boolean var_noHeadAndFoot,String var_reportName,String var_menuId,HashMap<String,Object>repCOMap)throws Exception
    {
	IRP_AD_HOC_REPORTCO reportCO=new IRP_AD_HOC_REPORTCO();
	String[] propsArr=ConstantsCommon.createDynamicRowDataJrxml_PROPS.toArray(new String[ConstantsCommon.createDynamicRowDataJrxml_PROPS.size()]);
	PathPropertyUtil.copyProperties(repCOMap,  reportCO,false,propsArr);
	return reportBO.createDynamicRowDataJrxml(var_noHeadAndFoot, var_reportName, var_menuId, reportCO);
    }
    
    public byte[] loadImage(String fileName, String deleteImg) throws Exception
    {
	return reportBO.loadImage(fileName, deleteImg);
    }
    
   public IRP_AD_HOC_REPORTCO returnNewFcrReport(Map parameters) throws Exception
   {
       String repProgRef = fcrBO.returnFcrProgRef(parameters);
       IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	    repSC.setCOMP_CODE((BigDecimal) (parameters.get(ConstantsCommon.FCR_COMP_CODE)));// RA_COMP
	    repSC.setPROG_REF((String) parameters.get(ConstantsCommon.FCR_APP));// APP_REF
	    repSC.setTemplateCode((BigDecimal) (parameters.get(ConstantsCommon.FCR_TEMPL_CODE)));
	    String xlsName = designerBO.returnXslName(repSC);
	    IRP_AD_HOC_REPORTCO reportCO = new IRP_AD_HOC_REPORTCO();
	    reportCO.setFTR_OPT_PROG_REF(repProgRef);
	    reportCO.setXlsName(xlsName);
	    return reportCO;
   }
   
   public String returnXslName(HashMap<String,Object> reptSCMap) throws Exception
   {
        IRP_AD_HOC_REPORTSC reptSC=new IRP_AD_HOC_REPORTSC();
        String[] propsArr=ConstantsCommon.returnXslName_PROPS.toArray(new String[ConstantsCommon.returnXslName_PROPS.size()]);
        PathPropertyUtil.copyProperties(reptSCMap,reptSC,false,propsArr);
	return designerBO.returnXslName(reptSC);
   }
   
   public List<HashMap<String,Object>> returnConnectionsList() throws Exception {
       List<IRP_CONNECTIONSVO> conVOLst= commonLookupBO.returnConnectionsList();
       List<HashMap<String,Object>> retLst=new ArrayList<HashMap<String,Object>>();
       HashMap<String,Object> hm;
       IRP_CONNECTIONSVO conVO;
       String[] propsArr=ConstantsCommon.returnConnectionsListRet_PROPS.toArray(new String[ConstantsCommon.returnConnectionsListRet_PROPS.size()]);
       for(int i=-0;i<conVOLst.size();i++)
       {
	   conVO=conVOLst.get(i);
	   hm= new HashMap<String, Object>();
	   PathPropertyUtil.copyProperties(conVO,hm,false,propsArr);
	   retLst.add(hm);
       }
       
       return retLst;
   }
   
   public void verifyReport(HashMap <String,Object> valuesMap) throws Exception
   {
       reportBO.verifyReport(valuesMap);
   }
   
   public List<HashMap<String,Object>> retRepArgDepList(HashMap<String,Object> reportSCMap)throws ReportException
   {
       try
       {
        IRP_AD_HOC_REPORTSC reportSC=new IRP_AD_HOC_REPORTSC();
        String[] propsArr=ConstantsCommon.retRepArgDepList_PROPS.toArray(new String[ConstantsCommon.retRepArgDepList_PROPS.size()]);
	PathPropertyUtil.copyProperties(reportSCMap,reportSC,false,propsArr);
	List<IRP_REP_ARGUMENTSVO> lst= designerBO.retRepArgDepList(reportSC);
	List<HashMap<String,Object>> retLst= new ArrayList<HashMap<String,Object>>();
	IRP_REP_ARGUMENTSVO argVO;
	HashMap<String,Object> hm;
	propsArr=ConstantsCommon.retRepArgDepListRet_PROPS.toArray(new String[ConstantsCommon.retRepArgDepListRet_PROPS.size()]);
	for(int i=0;i<lst.size();i++)
	{
	    argVO=lst.get(i);
	    hm=new HashMap<String, Object>();
	    PathPropertyUtil.copyProperties(argVO,hm,false,propsArr);
	    retLst.add(hm);
	}
	return retLst;
       }
       catch(BaseException e)
	{
	    throw retRepExceptionFromBaseException(e);
	}
	catch(Exception e)
	{
	    throw retRepExceptionFromException(e);
	}
   }
   
   public String retSysDateParamLovVal(String lang) throws ReportException
   {
	try
	{
	    return commonLookupBO.retSysDateParamLovVal(lang);
	}
	catch(BaseException e)
	{
	    throw retRepExceptionFromBaseException(e);
	}
	catch(Exception e)
	{
	    throw retRepExceptionFromException(e);
	}
   }

   public void sendReportsByMail(HashMap<String,Object> mailCOMap ,BigDecimal msId) throws ReportException
   {
	try
	{
	    MailCO mailCO=(MailCO)PathPropertyUtil.convertToObject(mailCOMap,MailCO.class);
	    List<MailCO> mailList = new ArrayList<MailCO>();
	    mailList.add(mailCO);
	    
	    // return the mail server details from the DB
	    IRP_MAIL_SERVER_CONFIGSC msSC = new IRP_MAIL_SERVER_CONFIGSC();
	    msSC.setMsCode(msId);
	    IRP_MAIL_SERVER_CONFIGCO msCO = mailServerConfigBO.retMailServerById(msSC);
	    mailCO.setFrom(msCO.getFROM());
	    MailServerCO mServerCO = new MailServerCO();
	    mServerCO.setHost(msCO.getMailServerVO().getHOST());
	    mServerCO.setPort(msCO.getMailServerVO().getPORT().intValue());
	    mServerCO.setUserName(msCO.getMailServerVO().getSERVER_USER_NAME());
	    mServerCO.setPassword(SecurityUtils.decodeB64(msCO.getMailServerVO().getSERVER_PASS()));
	    mServerCO.setProtocol("smtp");
	    commonLibBO.sendEmail(mailList, mServerCO);
	}
	catch(MailAuthenticationException e)
	{
	    throw retRepExceptionFromBOException(new BOException("authentication error",e));
	}
	catch(MailSendException e)
	{
	    throw retRepExceptionFromBOException(new BOException("Mail Server Connection Failed",e));
	}
	catch(BOException e)
	{
	    if(e.getErrorCode().equals(MessageCodes.INVALID_MISSING_COMMON_MSG))
	    {
		 throw retRepExceptionFromBOException(new BOException(e));
	    }
	    else
	    {
		 throw retRepExceptionFromBOException(new BOException("Error occured when sending the mail",e));
	    }
	}
	catch(Exception e)
	{
	    throw retRepExceptionFromBOException(new BOException("Error occured when sending the mail",e));
	}
	
   }
   /**
    *  method to read the jrxml file from the services repository
    */

    public byte[] readFileFromRepository(String fileName,String ext) throws Exception
    {
    	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
    	try
    	{
    	    return FileUtil.readFileBytes(repositoryPath + "/" + fileName + "."+ext);
    	}
    	catch(Exception e)
    	{
    	    throw new BOException(e.getMessage() ,e);
    	}
    }
    
    /**
     *  method to write file to the services repository
     */
    public void writeFileToRepository(String fileName,String fileContent,String ext)throws Exception 
    {
	designerBO.writeFileToRepository(fileName, fileContent,ext);
    }
    
    /**
     * Method that checks if hdg calc type in template section
     */
    public int checkTemplateGLRecords(BigDecimal templateCode, BigDecimal compCode) throws Exception
    {
	return templateBO.checkTemplateGLRecords(templateCode,compCode);
    }
    
    /**
     * Method that checks if col type ABD in column template
     */
    public int checkColTempCalcType(BigDecimal colCode, BigDecimal compCode) throws Exception
    {
	return columnTemplateBO.checkColTempCalcType(colCode, compCode);
    }
    
    
    @Override
    public String retTemplateDispVal(Map parameters,BigDecimal compCode) throws Exception
    {
	String retVal=RepConstantsCommon.FCR_DEFAULT_RA_FORMAT;
	if(parameters.get(ConstantsCommon.FCR_TEMPL_CODE)!=null)
	{
	   	GLSTMPLTVO vo=new GLSTMPLTVO();
		vo.setCODE((BigDecimal) (parameters.get(ConstantsCommon.FCR_TEMPL_CODE)));
		vo.setTEMPLATE_TYPE(RepConstantsCommon.FCR_TEMPLATE_TYPE);
		vo.setLINE_NBR(BigDecimal.ZERO);
		vo.setCOMP_CODE(compCode);
		GLSTMPLTVO retVO= (GLSTMPLTVO)genericDAO.selectByPK(vo);
		if(retVO!=null)
		{
		    retVal=retVO.getDISPLAY_VALUES();
		}
	}
	return retVal;
    }
    
    /**
     * Method that fills the parameters map with a collection for the case of
     * multi livesearch selections
     * 
     * @param itemName
     * @param itemValue
     * @param parameters
     */
    public Map fillParametersWithCollection(String itemName, String itemValue, Map parameters, String argType, int from)
	    throws Exception
    {
	// {"root":[{"CURRENCY_CODE":"1"},{"CURRENCY_CODE":"12"},{"CURRENCY_CODE":"2"}]}
	JSONObject jsonFilter = (JSONObject) JSONSerializer.toJSON(itemValue);
	JSONArray jsonModel = jsonFilter.getJSONArray(ConstantsCommon.REP_ROOT);
	Object[] modelArr = jsonModel.toArray();
	JSONObject obj;
	HashMap<String, Object> hm;
	if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(argType))
	{
	    ArrayList<BigDecimal> lstValues = new ArrayList<BigDecimal>();
	    for(int i = 0; i < modelArr.length; i++)
	    {
		obj = (JSONObject) modelArr[i];
		hm = (HashMap) JSONObject.toBean(obj, HashMap.class);
		lstValues.add(new BigDecimal((String) hm.values().toArray()[0]));
	    }
	    parameters.put(itemName, lstValues);
	}
	else if(ConstantsCommon.PARAM_TYPE_DATE.equals(argType))
	{
	    if(from == 0)
	    {
		ArrayList<Date> lstValues = new ArrayList<Date>();
		for(int i = 0; i < modelArr.length; i++)
		{
		    obj = (JSONObject) modelArr[i];
		    hm = (HashMap) JSONObject.toBean(obj, HashMap.class);
		    lstValues.add(DateUtil.parseDate((String) (hm.values().toArray()[0]),
			    RepConstantsCommon.SCHED_DATE_FRMT));
		}
		parameters.put(itemName, lstValues);
	    }
	    else
	    {
		ArrayList<String> lstValues = new ArrayList<String>();
		for(int i = 0; i < modelArr.length; i++)
		{
		    obj = (JSONObject) modelArr[i];
		    hm = (HashMap) JSONObject.toBean(obj, HashMap.class);
		    lstValues.add((String) (hm.values().toArray()[0]));
		}
		parameters.put(itemName, lstValues);
	    }
	}
	else
	{
	    ArrayList<String> lstValues = new ArrayList<String>();
	    for(int i = 0; i < modelArr.length; i++)
	    {
		obj = (JSONObject) modelArr[i];
		hm = (HashMap) JSONObject.toBean(obj, HashMap.class);
		lstValues.add((String) (hm.values().toArray()[0]));
	    }
	    parameters.put(itemName, lstValues);
	}
	return parameters;
    }
    
    @Override
    public IRP_FCR_REPORTSCO retFcrRep(IRP_AD_HOC_REPORTSC repSC) throws Exception 
    {
	return fcrBO.retFcrRep(repSC);
    }
    
    @Override
    public HashMap<String,String> returnDynamicReportByRef(String progRef) throws Exception
    {
	FTR_OPTCO ftrOptCO = new FTR_OPTCO();
	ftrOptCO.getFtrOptVO().setPROG_REF(progRef);
	ftrOptCO.getFtrOptVO().setAPP_NAME(ConstantsCommon.REP_APP_NAME);
	ftrOptCO = fcrBO.returnDynamicReportByRef(ftrOptCO);
	HashMap<String, String> retHm = new HashMap<String, String>();
	retHm.put(ConstantsCommon.FCR_TEMPL_CODE, ftrOptCO.getFtrOptVO().getTMPLT_CODE().toString());
	retHm.put(ConstantsCommon.FCR_COL_TMPLT_CODE, ftrOptCO.getFtrOptVO().getCOLUMN_CODE().toString());
	retHm.put(ConstantsCommon.FCR_REP_TYPE, ftrOptCO.getFtrOptVO().getREP_TYPE());
	retHm.put(ConstantsCommon.ARG_RA_TYPE, ftrOptCO.getFtrOptVO().getGROUP_BY());
	retHm.put(ConstantsCommon.FCR_ROW_TO_COL, ftrOptCO.getFtrOptVO().getROW_COLMN().toString());
	retHm.put(ConstantsCommon.FCR_CURRENCY, ftrOptCO.getFtrOptVO().getREP_CURRENCY().toString());
	return retHm;
    }
    
    
    /**
     * Returns the ReportException from the BaseException
     * @param BaseException
     * @return ReportException
     * 
     */
    private ReportException retRepExceptionFromBaseException(BaseException e)
    {
	ReportException re = new ReportException();
	re.setMsgType(Integer.valueOf(1));
	re.setMessage(e.getMessage());
	re.setErrorCode(e.getErrorCode());
	re.setParams(e.getParams());
	return re;
    }
    
    /**
     * Returns the ReportException from the BOException
     * @param BOException
     * @return ReportException
     * 
     */
    private ReportException retRepExceptionFromBOException(BOException e)
    {
	ReportException re = new ReportException();
	re.setMsgType(Integer.valueOf(2));
	re.setMessage(e.getMessage());
	re.setErrorCode(e.getErrorCode());
	re.setParams(e.getParams());
	return re;
    }
    
    /**
     * Returns the ReportException from the Exception
     * @param Exception
     * @return ReportException
     * 
     */
    private ReportException retRepExceptionFromException(Exception e)
    {
	ReportException re = new ReportException();
	re.setMsgType(Integer.valueOf(4));
	re.setMessage(e.getMessage());
	return re;
    }
    
    @Override
    public HashMap<String,Object> returnReportBytes(Map repParameters, HashMap<String, Object> sessionMap,
	    HashMap<String, Object> repDetailsMap,boolean isReturnOutput) throws ReportException, Exception
    {
	//retrieve report details
	
	String progRef = StringUtil.nullToEmpty((String) repDetailsMap.get(ConstantsCommon.MENU_ID_PARAM));
	String appName = StringUtil.nullToEmpty((String) repDetailsMap.get(ConstantsCommon.APP_NAME_PARAM));
	String repName = StringUtil.nullToEmpty((String) repDetailsMap.get(ConstantsCommon.REPORT_NAME_PARAM));
	if(repName.isEmpty())
	{
		IRP_AD_HOC_REPORTSC reportSC=new IRP_AD_HOC_REPORTSC();
		reportSC.setAPP_NAME(appName);
		reportSC.setPROG_REF(progRef);
		IRP_AD_HOC_REPORTCO repCO=designerBO.retRepIdByProgRef(reportSC);
		repName=repCO.getREPORT_NAME();
	}
	
	String language = StringUtil.nullToEmpty((String) repDetailsMap.get(ConstantsCommon.LANG_PARAM));

	// fill the repParamsCO
	SessionCO sessionCO = (SessionCO) PathPropertyUtil.convertToObject(sessionMap, SessionCO.class);
	String[] properties = new String[] { "baseCurrDecPoint", "baseCurrencyCode", "baseCurrencyName", "branchCode",
		"clientType", "companyArabName", "companyCode", "companyName", "currentAppName", "exposCurCode",
		"exposCurName", "fiscalYear", "runningDateRET", "language", "userName", "branchName", "isRTLDir" };
	ReportParamsCO repParamsCO = new ReportParamsCO();
	PathPropertyUtil.copyProperties(sessionCO, repParamsCO, properties);
	
	// fill the translations map
	HashMap<String, String> transMsgLangMap = new HashMap<String, String>();
	BigDecimal nbCopies=(BigDecimal) repDetailsMap.get(ConstantsCommon.REP_NBCOPIES_PRINT)==null?BigDecimal.ONE:(BigDecimal) repDetailsMap.get(ConstantsCommon.REP_NBCOPIES_PRINT);
	repParameters.put(ConstantsCommon.REP_NBCOPIES_PRINT,nbCopies);
	TranslationSC translationSC = new TranslationSC();
	translationSC.setPreferredLanguage(language);
	translationSC.setAppName(ConstantsCommon.IMAL_APP_NAME);
	translationSC.setPageRef(ConstantsCommon.PROGREF_ROOT);

	translationSC.setKeyLabelCode("reporting.excMsgProcError");
	TranslationCO transCO = translationBO.returnLabelTrans(translationSC);
	transMsgLangMap.put("1", transCO == null ?"reporting.excMsgProcError": transCO.getLblValueTrans());

	translationSC.setKeyLabelCode("reporting.excMsgPartOne");
	transCO = translationBO.returnLabelTrans(translationSC);
	transMsgLangMap.put("2", transCO == null ?"reporting.excMsgPartOne": transCO.getLblValueTrans());

	translationSC.setKeyLabelCode("reporting.excMsgPartTwo");
	transCO = translationBO.returnLabelTrans(translationSC);
	transMsgLangMap.put("3", transCO == null ? "reporting.excMsgPartTwo": transCO.getLblValueTrans());

	// return the report id from the report reference
	IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	repSC.setPROG_REF(progRef);
	repSC.setAPP_NAME(appName);
	HashMap<String, Object> repSCMap = new HashMap<String, Object>();
	String[] propsArr = (String[]) ConstantsCommon.retRepIdByProgRef_PROPS.toArray(new String[ConstantsCommon.retRepIdByProgRef_PROPS.size()]);
	PathPropertyUtil.copyProperties(repSC, repSCMap, false, propsArr);
	HashMap<String, Object> retHm = retRepIdByProgRef(repSCMap);
	IRP_AD_HOC_REPORTCO repIdCO;
	if(retHm == null || retHm.isEmpty())
	{
	    ReportException re = new ReportException();
	    re.setMsgType(Integer.valueOf(4));
	    translationSC.setKeyLabelCode("report.retrieve.exceptionMsg._key");
	    transCO = translationBO.returnLabelTrans(translationSC);
	    re.setMessage(transCO == null ? "report.retrieve.exceptionMsg._key":transCO.getLblValueTrans());
	    throw re;
	}
	else
	{
	    repIdCO = (IRP_AD_HOC_REPORTCO) PathPropertyUtil.convertToObject(retHm, IRP_AD_HOC_REPORTCO.class);
	}
	
	// fill the report object based on the report id
	BigDecimal repId = repIdCO.getREPORT_ID();
	HashMap<String, Object> retMap = returnReportById(repId);
	IRP_AD_HOC_REPORTCO reportCO = (IRP_AD_HOC_REPORTCO) PathPropertyUtil.convertToObject(retMap,IRP_AD_HOC_REPORTCO.class);

	reportCO.setCOMP_CODE(sessionCO.getCompanyCode());
	reportCO.setBRANCH_CODE(sessionCO.getBranchCode());
	HashMap<String, Object> repSessionCOMap = PathPropertyUtil.convertToMap(reportCO);
	
	
	//fill the following properties in repParamsCO ProgRef,RepAppName, RepLanguage and NoData
	repParamsCO.setProgRef(reportCO.getPROG_REF());
	repParamsCO.setRepAppName(reportCO.getAPP_NAME());
	repParamsCO.setRepLanguage(language);
	repParamsCO.setNoData(false);
	
	// Add the repParamsCO to the parameters map
	repParameters.put(RepConstantsCommon.repParamsCO_arg, repParamsCO);
	
	String reportFormat = StringUtil.nullToEmpty((String) repDetailsMap.get(ConstantsCommon.FORMAT_PARAM));
	String repPath= StringUtil.nullToEmpty((String) repDetailsMap.get(ConstantsCommon.REP_PATH));
	BigDecimal dbConn = (BigDecimal) repDetailsMap.get(ConstantsCommon.DB_PARAM);
	String printerName=StringUtil.nullToEmpty((String)repDetailsMap.get(ConstantsCommon.PEP_PRINTER_NAME));
	boolean noHeadAndFoot = ((Boolean) repDetailsMap.get(ConstantsCommon.HEAD_FOOT_PARAM)).booleanValue();
	String csvSepartor = StringUtil.nullToEmpty((String) repDetailsMap.get(ConstantsCommon.CSV_SEPARTOR_PARAM));
	
	if(repDetailsMap.containsKey(RepConstantsCommon.REP_PARAM_SOURCE_ID))
	{
		BigDecimal sourceId = (BigDecimal) repDetailsMap.get(RepConstantsCommon.REP_PARAM_SOURCE_ID);
		repParameters.put(RepConstantsCommon.REP_PARAM_SOURCE_ID, sourceId);
	}
	
	//886696 - in case decimalPoint is not sent then set as zero to retain backward compatibility
	BigDecimal decimalPoint = NumberUtil.nullToZero((BigDecimal) repDetailsMap.get(RepConstantsCommon.REP_PARAM_DECIMAL_POINT));
	
	
	//retrieve report
	ReportOutputCO repOutputCO = reportBO.printReport(repName, reportFormat, repParameters, "", progRef,
		repSessionCOMap, decimalPoint.intValue(), appName, repParamsCO.getUserName(), language, printerName, dbConn, noHeadAndFoot,
		csvSepartor, RepConstantsCommon.FALSE, null, null, reportFormat, ConstantsCommon.REP_SERVICE_CALL, transMsgLangMap);
	
	//save the generated report under the rquested location
	if(!repPath.isEmpty())
	{
		 String repositoryPath= ReportingConstantsCommon.reportingFolder+"/"+ConstantsCommon.REP_PROC_FOLDER+"/"+ repPath;
		 FileUtil.makeDirectories(FileUtil.getFileURLByName(ReportingConstantsCommon.repositoryFolder) +"/"+repositoryPath);
		 FileUtil.saveToRepository(repOutputCO.getReportBytes(), ReportingConstantsCommon.repositoryFolder, "/" + repositoryPath+"/" + repName + "."+reportFormat);
	}
	
	//delete temporary files
	repName = repName.replaceAll(" ", "%20");
	deleteTempFiles(repName);
	
	if(isReturnOutput)
	{
		return PathPropertyUtil.convertToMap(repOutputCO);
	}
	else
	{
		return null;
	}
    }

    
    @Override
    public byte[] returnDynamicFcrJrxml(IRP_AD_HOC_REPORTCO reportCO,String ftrOptProgRef,Map parameters)throws UnsupportedEncodingException,Exception
    {
	String ftrOptPRef=ftrOptProgRef;
	//case of dynamicReports
	if(ftrOptPRef == null)
	{
	    BigDecimal compCode = (BigDecimal) parameters.get(ConstantsCommon.FCR_COMP_CODE);
	    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	    repSC.setPROG_REF(reportCO.getFTR_OPT_PROG_REF());
	    repSC.setCOMP_CODE(compCode);
	    repSC.setAPP_NAME(ConstantsCommon.REP_APP_NAME);
	    IRP_FCR_REPORTSCO irpFcrRepCO = fcrBO.retFcrRep(repSC);
	    ftrOptPRef=irpFcrRepCO.getProgRef();
	}
	
	//return the related query from ftr_opt_prog_ref
	IRP_FCR_REPORTSVO vo=new IRP_FCR_REPORTSVO();
	vo.setREPORT_PROG_REF(ftrOptPRef);
	//vo.setGROUP_BY((String)parameters.get(ConstantsCommon.ARG_RA_TYPE));
	List<IRP_FCR_REPORTSVO>  fcrRepVOLst=fcrBO.returnFcrDetByFtrOptRef(vo);
	IRP_FCR_REPORTSVO fcrRepVO = fcrRepVOLst.get(0);
	return constructDynamicFcrJrxml(parameters,fcrRepVO,reportCO,ftrOptPRef);
    }
    
    /**
     * Method that will create the dynamic jrxml of the fcr reports based on the fixed columns and the dynamic columns
     * @return
     */
    private byte[] constructDynamicFcrJrxml(Map parameters ,IRP_FCR_REPORTSVO fcrRepVO,IRP_AD_HOC_REPORTCO reportCO,String ftrOptProgRef) throws BaseException, UnsupportedEncodingException
    {
	String reportName=reportCO.getREPORT_NAME();
	//add the first column to the fixed columns
	List<IRP_FCR_FIXED_COLSVO> fixedCols = new ArrayList<IRP_FCR_FIXED_COLSVO>();
	IRP_FCR_FIXED_COLSVO firstFixedCol= new IRP_FCR_FIXED_COLSVO();
	firstFixedCol.setCOL_NAME(RepConstantsCommon.TMPLT_DESC_COL);
	firstFixedCol.setCOL_HEADER("");
	firstFixedCol.setCOL_TYPE(RepConstantsCommon.VARCHAR_TYPE_JASPER);
	firstFixedCol.setCOL_SHOW_HIDE_YN(BigDecimal.ONE);
	fixedCols.add(firstFixedCol);
	// retrieve the fixed cols from the DB
	List<IRP_FCR_FIXED_COLSVO> fixedColsDB = fcrBO.retFixedFcrColsByRef(ftrOptProgRef);
	fixedCols.addAll(fixedColsDB);
	boolean isRowToColChecked = "1".equals(parameters.get(RepConstantsCommon.ROW_TO_COL_ARG))?true:false;
	BigDecimal  templCode=((BigDecimal) (parameters.get(ConstantsCommon.FCR_TEMPL_CODE)));
	BigDecimal  colTemplCode=((BigDecimal) (parameters.get(ConstantsCommon.FCR_COL_TEMPL_CODE)));
	BigDecimal compCode =(BigDecimal) parameters.get(ConstantsCommon.FCR_COMP_CODE);
	
	List<COLMNTMPLTCO> dynCols;
	COLMNTMPLTCO colCO;
	boolean isDailyMonthlyColType=false;
	// retrieve the dynamic cols
	if(isRowToColChecked)
	{
	    GLSTMPLTSC tmplSC = new GLSTMPLTSC();
	    tmplSC.setCODE(templCode);
	    tmplSC.setCOMP_CODE(compCode);
	    dynCols = fcrBO.retDynamicFcrColsFromTempl(tmplSC);
	}
	else
	{
	    COLMNTMPLTSC colTmplSC = new COLMNTMPLTSC();
	    colTmplSC.setCODE(colTemplCode);
	    colTmplSC.setCOMP_CODE(compCode);
	    colTmplSC.setLANG_CODE((String)parameters.get(ConstantsCommon.RA_REP_LANG));
	    dynCols = fcrBO.retDynamicFcrColsFromColTempl(colTmplSC);
	    if(dynCols.size()==1)
	    {
		List<COLMNTMPLTCO> dynColsDaily = new ArrayList<COLMNTMPLTCO>();
		COLMNTMPLTCO dynColDaily;
		colCO=dynCols.get(0);
		String colType=colCO.getColmnTmpltVO().getCOL_TYPE();
		//check if the calculation type is daily than prepare the list of days to be constructed as header
		if(RepConstantsCommon.CALC_ACT_DAY_BAL_CV.equals(colType) || RepConstantsCommon.CALC_ACT_DAY_BAL_FC.equals(colType))
		{
		    isDailyMonthlyColType=true;
		    Date frmDate = colCO.getColmnTmpltVO().getFROM_DATE();
		    Date toDate = colCO.getColmnTmpltVO().getTO_DATE();
		    int specifyDay = new BigDecimal(colCO.getColmnTmpltVO().getSPECIFY_DAY()).intValue();
		    Date nextDate;
		    // add first date
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(frmDate);
		    if((specifyDay != 0 && cal.get(Calendar.DAY_OF_WEEK) == specifyDay) || specifyDay == 0)
		    {
			dynColDaily = new COLMNTMPLTCO();
			dynColDaily.getColmnTmpltVO().setBRIEF_NAME_ENG(colCO.getColmnTmpltVO().getBRIEF_NAME_ENG());
			dynColDaily.setFROM_DATE_STR(DateUtil.format(frmDate, "dd-MMM-yy"));
			dynColDaily.setCOL_TYPE_STR(colCO.getCOL_TYPE_STR()); 
			dynColDaily.getColmnTmpltVO().setLINE_NBR(colCO.getColmnTmpltVO().getLINE_NBR());
			dynColsDaily.add(dynColDaily);
		    }

		    //get the next day date
		    cal.add(Calendar.DATE, 1);
		    nextDate = cal.getTime();
		    while(nextDate.compareTo(toDate) < 0)
		    {
			if((specifyDay != 0 && cal.get(Calendar.DAY_OF_WEEK) == specifyDay) || specifyDay == 0)
			{
			    dynColDaily = new COLMNTMPLTCO();
			    dynColDaily.getColmnTmpltVO().setBRIEF_NAME_ENG(colCO.getColmnTmpltVO().getBRIEF_NAME_ENG());
			    dynColDaily.setFROM_DATE_STR(DateUtil.format(nextDate, "dd-MMM-yy"));
			    dynColDaily.setCOL_TYPE_STR(colCO.getCOL_TYPE_STR());
			    dynColDaily.getColmnTmpltVO().setLINE_NBR(colCO.getColmnTmpltVO().getLINE_NBR());
			    dynColsDaily.add(dynColDaily);
			}
			cal.setTime(nextDate);
			cal.add(Calendar.DATE, 1);
			nextDate = cal.getTime();

		    }

		    // add last date
		    if((specifyDay != 0 && cal.get(Calendar.DAY_OF_WEEK) == specifyDay) || specifyDay == 0)
		    {
			dynColDaily = new COLMNTMPLTCO();
			dynColDaily.getColmnTmpltVO().setBRIEF_NAME_ENG(colCO.getColmnTmpltVO().getBRIEF_NAME_ENG());
			dynColDaily.setFROM_DATE_STR(DateUtil.format(toDate, "dd-MMM-yy"));
			dynColDaily.setCOL_TYPE_STR(colCO.getCOL_TYPE_STR()); 
			dynColDaily.getColmnTmpltVO().setLINE_NBR(colCO.getColmnTmpltVO().getLINE_NBR());
			dynColsDaily.add(dynColDaily);
		    }
		    dynCols = dynColsDaily;
		}
		//check if the calculation type is monthly than prepare the list of months to be constructed as header
		else if(RepConstantsCommon.CALC_ACT_MON_BAL_CV.equals(colType) || RepConstantsCommon.CALC_ACT_MON_BAL_FC.equals(colType))
		{
		    isDailyMonthlyColType=true;
		    
		    Date frmDate = colCO.getColmnTmpltVO().getFROM_DATE();
		    Date toDate = colCO.getColmnTmpltVO().getTO_DATE();
		    int specifyMonth = new BigDecimal(colCO.getColmnTmpltVO().getSPECIFY_MONTH()).intValue();
		    Date nextDate;
		    // add first date
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(frmDate);
		    if((specifyMonth != 0 && cal.get(Calendar.MONTH) == specifyMonth) || specifyMonth == 0)
		    {
			dynColDaily = new COLMNTMPLTCO();
			dynColDaily.getColmnTmpltVO().setBRIEF_NAME_ENG(colCO.getColmnTmpltVO().getBRIEF_NAME_ENG());
			dynColDaily.setFROM_DATE_STR(DateUtil.format(frmDate, "MMM-yy"));
			dynColDaily.setCOL_TYPE_STR(colCO.getCOL_TYPE_STR()); 
			dynColDaily.getColmnTmpltVO().setLINE_NBR(colCO.getColmnTmpltVO().getLINE_NBR());
			dynColsDaily.add(dynColDaily);
		    }

		    //get the next day date
		    cal.add(Calendar.MONTH, 1);
		    nextDate = cal.getTime();
		    while(nextDate.compareTo(toDate) < 0)
		    {
			if((specifyMonth != 0 && cal.get(Calendar.MONTH) == specifyMonth-1) || specifyMonth == 0)
			{
			    dynColDaily = new COLMNTMPLTCO();
			    dynColDaily.getColmnTmpltVO().setBRIEF_NAME_ENG(colCO.getColmnTmpltVO().getBRIEF_NAME_ENG());
			    dynColDaily.setFROM_DATE_STR(DateUtil.format(nextDate, "MMM-yy"));
			    dynColDaily.setCOL_TYPE_STR(colCO.getCOL_TYPE_STR());
			    dynColDaily.getColmnTmpltVO().setLINE_NBR(colCO.getColmnTmpltVO().getLINE_NBR());
			    dynColsDaily.add(dynColDaily);
			}
			cal.setTime(nextDate);
			cal.add(Calendar.MONTH, 1);
			nextDate = cal.getTime();
		    }
		    
		    // add last date
		  if(((specifyMonth != 0 && cal.get(Calendar.MONTH) == specifyMonth) || specifyMonth == 0) && DateUtil.format(toDate, "dd").compareTo(DateUtil.format(frmDate, "dd")) < 0)
		    {
			dynColDaily = new COLMNTMPLTCO();
			dynColDaily.getColmnTmpltVO().setBRIEF_NAME_ENG(colCO.getColmnTmpltVO().getBRIEF_NAME_ENG());
			dynColDaily.setFROM_DATE_STR(DateUtil.format(toDate, "MMM-yy"));
			dynColDaily.setCOL_TYPE_STR(colCO.getCOL_TYPE_STR());
			dynColDaily.getColmnTmpltVO().setLINE_NBR(colCO.getColmnTmpltVO().getLINE_NBR());
			dynColsDaily.add(dynColDaily);
		    }
		    dynCols = dynColsDaily;
		    
		}
	    }
	}
	
	byte[] jrxml = reportCO.getJRXML_FILE();
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String filePath = repositoryPath + "/dynFCR_"+ftrOptProgRef+"_"+new Date().getTime()+".jrxml";
	JasperDesign jasperDesign; 
	try
	{
	    //convert jrxml to jasperDesign
	    ByteArrayInputStream is = new ByteArrayInputStream(jrxml);
	    jasperDesign = JRXmlLoader.load(is);

	    // update query by adding the dynamic cols 
	    String sqlString = fcrRepVO.getFCR_REPORT_QUERY();
	    String[] sqlSplit = sqlString.split("(F|f)(R|r)(O|o)(M|m)(\\s)*(G|g)(L|l)(S|s)(T|t)(M|m)(P|p)(L|l)(T|t)");
	    String qry1 = sqlSplit[0];
	    String qry2 = sqlSplit[1];
	    StringBuffer qryAdd = new StringBuffer();
	    int lineNb;
	    int isSybase=commonLibBO.returnIsSybase();
	    for(int i = 0; i < dynCols.size(); i++)
	    {
		colCO=dynCols.get(i);
		lineNb=colCO.getColmnTmpltVO().getLINE_NBR().intValue();
		if(isDailyMonthlyColType) //and unchecked in this case
		{
		    qryAdd.append("SUM(case when  FTR_REP.ADD_NUMBER1 = "+(i+1)+" then \n");
		}
		else if(isRowToColChecked)
		{
		    qryAdd.append("SUM(case when FTR_REP.TMPLT_LINE_NBR = "+lineNb+" then \n");
		}
		else
		{
		    qryAdd.append("SUM(case when FTR_REP.TMPLT_LINE_NBR2 = "+lineNb+" then \n");
		}
		
		if("FCRDCKP".equals(ftrOptProgRef) || "FCRDCOP".equals(ftrOptProgRef) || "FCRDCRP".equals(ftrOptProgRef)
			|| "FCRDCYP".equals(ftrOptProgRef) || "FCRDUAP".equals(ftrOptProgRef) || "FCRDUKP".equals(ftrOptProgRef)
			|| "FCRDUOP".equals(ftrOptProgRef) || "FCRDURP".equals(ftrOptProgRef)|| "FCRDUYP".equals(ftrOptProgRef)
			|| "FCRSUMCP".equals(ftrOptProgRef) || "FCRSUMUP".equals(ftrOptProgRef))
		{
		    qryAdd.append( "    CASE WHEN COLMNTMPLT.COL_TYPE IN ('COA','COC','RTV','ICC') ");
		}
		else
		{
		    qryAdd.append( "    CASE WHEN COLMNTMPLT.COL_TYPE IN ('COA', 'COC','ICC') ");
			if(!"FCRDCAP".equals(ftrOptProgRef))
			{
			    qryAdd.append("  OR FTR_REP.CALC_TYPE IN ('COC','BRC','RTV','ICC') " );
			} 
		}
		
		qryAdd.append(	 "  THEN\n"+
			 "      (CASE  $P{RA_FORMAT}\n"+
			 "          WHEN 'D' THEN\n"+
			 "              (FTR_REP.CV_AMOUNT) \n"+
			 "          WHEN 'W' THEN\n"+
			 "              (FTR_REP.CV_AMOUNT) \n"+
			 "          WHEN 'T' THEN\n"+
			 "              (FTR_REP.CV_AMOUNT) \n"+
			 "          WHEN 'M' THEN\n"+
			 "              (FTR_REP.CV_AMOUNT) \n"+
			 "        END)\n"+
			 "	ELSE \n");
		if("FCRDCAP".equals(ftrOptProgRef) || "FCRDCKP".equals(ftrOptProgRef) || "FCRDCOP".equals(ftrOptProgRef)
			|| "FCRDCRP".equals(ftrOptProgRef) || "FCRDCYP".equals(ftrOptProgRef) || "FCRDUAP".equals(ftrOptProgRef)
			|| "FCRDUKP".equals(ftrOptProgRef) || "FCRDUOP".equals(ftrOptProgRef) || "FCRDURP".equals(ftrOptProgRef)
			|| "FCRDUYP".equals(ftrOptProgRef) || "FCRSUMCP".equals(ftrOptProgRef) || "FCRSUMUP".equals(ftrOptProgRef))
		{
		    qryAdd.append("  CASE WHEN COLMNTMPLT.COL_TYPE IN ('AB','AM','BB','BM','VB','X','AGC', 'MB', 'GM','CD','PER') ");
		}
		else 
		{
		    qryAdd.append("  CASE WHEN COLMNTMPLT.COL_TYPE IN ('AB','AM','BB','BM','VB','X','AGC','CD','PER') ");//,'PER'
		}
		if( "FCRDUKNP".equals(ftrOptProgRef) || "FCRDURNP".equals(ftrOptProgRef)|| "FCRDCRNP".equals(ftrOptProgRef) ||
			"FCRDCYNP".equals(ftrOptProgRef)|| "FCRDUYNP".equals(ftrOptProgRef) || "FCRDUONP".equals(ftrOptProgRef)
			|| "FCRDUANP".equals(ftrOptProgRef) || "FCRDCONP".equals(ftrOptProgRef) 
			 || "FCRSUMCNP".equals(ftrOptProgRef)  || "FCRSUMUNP".equals(ftrOptProgRef) )
		{
		    qryAdd.append("  OR FTR_REP.CALC_TYPE IN('CD') ");
		}
		qryAdd.append( " THEN\n"+
			 "        (CASE  $P{RA_FORMAT}\n"+
			 "              WHEN 'D' THEN\n"+
			 "                  (FTR_REP.CV_AMOUNT) \n"+
			 "              WHEN 'W' THEN\n"+" " +
			 "                 (FTR_REP.CV_AMOUNT) \n"+
			 "              WHEN 'T' THEN\n"+
			 "                  (FTR_REP.CV_AMOUNT) *1/1000\n"+
			 "              WHEN 'M' THEN\n"+
			 "                  (FTR_REP.CV_AMOUNT) *1/1000000\n"+
			 "           END)\n"+
			 "   ELSE\n"+
			 "     (CASE  $P{RA_FORMAT}\n"+
			 "              WHEN 'D' THEN\n"+
			 "                  (FTR_REP.FC_AMOUNT) \n"+
			 "              WHEN 'W' THEN\n"+
			 "                  (FTR_REP.FC_AMOUNT) \n"+
			 "              WHEN 'T' THEN\n"+
			 "                  (FTR_REP.FC_AMOUNT) *1/1000\n"+
			 "              WHEN 'M' THEN\n"+
			 "                  (FTR_REP.FC_AMOUNT) *1/1000000\n"+
			 "           END)"+"    END"+"  END else 0 end ) AS "+RepConstantsCommon.DYNCOL_FIELD +i+"\n");
		
		//adding the column date related to each dynamic column
		if(isSybase==1)
		{
			qryAdd.append(",  MIN(CASE WHEN FTR_REP.TMPLT_LINE_NBR2 = "+lineNb+" \n"+
					"THEN  (CONVERT(VARCHAR(10),ISNULL(COLMNTMPLT.TO_DATE,COLMNTMPLT.FROM_DATE),103)) \n"+
					  "ELSE NULL END) AS "+RepConstantsCommon.DYNCOL_FIELD+ConstantsCommon.PARAM_TYPE_DATE+i+"\n");
		}
		else
		{
		qryAdd.append(",  MIN(CASE WHEN FTR_REP.TMPLT_LINE_NBR2 = "+lineNb+" \n"+
				"THEN TO_CHAR(NVL(COLMNTMPLT.TO_DATE,COLMNTMPLT.FROM_DATE),'dd/MM/yyyy') \n"+
				  "ELSE NULL END) AS "+RepConstantsCommon.DYNCOL_FIELD+ConstantsCommon.PARAM_TYPE_DATE+i+"\n");
		}

		if(i < dynCols.size() - 1)
		{
		    qryAdd.append(",");
		}
	    }

	    
	    StringBuffer finalQrySb = new StringBuffer();
	    String commaSep="";
	    if(!qryAdd.toString().isEmpty())
	    {
		commaSep=" , ";
	    }
	    finalQrySb = finalQrySb.append(qry1).append(commaSep).append(qryAdd).append(" FROM GLSTMPLT").append(qry2);
	    String finalQry = finalQrySb.toString();

	    JRDesignDataset dataSet=(JRDesignDataset) jasperDesign.getDatasetsList().get(0);
	    
	    dataSet.setName(reportName+ "_0");
	    // create dataset query
	    JRDesignQuery jrDsQry = new JRDesignQuery();
	    jrDsQry.setText(finalQry); 
	    dataSet.setQuery(jrDsQry);
	    
	   
	    JRDesignField fe;
	    
	    //add GL_TYPE only to the fields section since it will be used in the 'pattern Exp.' of the dynamic columns
	    fe = new JRDesignField();
	    fe.setName(RepConstantsCommon.GL_TYPE_FIELD);
	    fe.setValueClassName(RepConstantsCommon.VARCHAR_TYPE_JASPER);
	    dataSet.addField(fe);
	    
    
	    //add fixed cols
	    IRP_FCR_FIXED_COLSVO tblCol;
	    COLMNTMPLTCO tblDynCol;
	    String headerName;
	    for(int i = 0; i < fixedCols.size(); i++)
	    {
		tblCol = fixedCols.get(i);
		if(tblCol.getCOL_SHOW_HIDE_YN().intValue() == 1)
		{
		    fe = new JRDesignField();
		    fe.setName(tblCol.getCOL_NAME());
		    fe.setValueClassName(tblCol.getCOL_TYPE());
		    dataSet.addField(fe);
		    // create the col header field dynamically since it does not
		    // exist in the fixed cols
		    headerName = tblCol.getCOL_HEADER();
		    if(headerName.indexOf(RepConstantsCommon.FIELD_JASPER) == 0)
		    {
			fe = new JRDesignField();
			fe.setName(headerName.substring(3, headerName.length() - 1));
			fe.setValueClassName(tblCol.getCOL_TYPE());
			dataSet.addField(fe);
		    }
	}
	    }
	   
	    // add dynamic fields
	    for(int i = 0; i < dynCols.size(); i++)
	    {
		fe = new JRDesignField();
		fe.setName(RepConstantsCommon.DYNCOL_FIELD + i);
		fe.setValueClassName(RepConstantsCommon.NUMBER_TYPE_JASPER);
		dataSet.addField(fe);
		
		//add related date for each dynamic field
		fe = new JRDesignField();
		fe.setName(RepConstantsCommon.DYNCOL_FIELD+ConstantsCommon.PARAM_TYPE_DATE+i);
		fe.setValueClassName(RepConstantsCommon.VARCHAR_TYPE_JASPER);
		dataSet.addField(fe);

	    }
	    
	   
	    JRXmlWriter.writeReport(jasperDesign, filePath, ConstantsCommon.ENCODING_TYPE_UTF);

	    // convert jasperDesing to string in order to add detail band
	    byte[] fileBytes = FileUtil.readFileBytes(filePath);
	    String modifiedJrxml = new String(fileBytes, ConstantsCommon.FILE_ENCODING);
	    
	    String[] jrxmlSplit=modifiedJrxml.split("<detail>(\\s)*<band height=\"165\" splitType=\"Stretch\"/>(\\s)*</detail>");
	    
	    StringBuffer detSb=new StringBuffer();
	    // create detail section
	    detSb
		    .append("<detail>"
			    + "<band height=\"165\" splitType=\"Stretch\">"
			    + "<componentElement>"
			    + "<reportElement  key=\"table 2\" x=\"0\" y=\"0\" width=\"500\" height=\"112\"/>"
			    + "<jr:table xmlns:jr=\"http://jasperreports.sourceforge.net/jasperreports/components\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports/components http://jasperreports.sourceforge.net/xsd/components.xsd\">"
			    + "<datasetRun subDataset=\"" + reportName + "_0\" >");
	    // add dsRunParams
	    JRParameter[] paramsArr = jasperDesign.getParameters();
	    JRParameter param;
	    for(int i = 0; i < paramsArr.length; i++)
	    {
		param = paramsArr[i];
		if(!param.isSystemDefined())
		{
		    detSb.append("<datasetParameter name=\"" + param.getName() + "\">"
			    + "<datasetParameterExpression><![CDATA[$P{" + param.getName()
			    + "}]]></datasetParameterExpression>" + "</datasetParameter>");
		}
	    }
	    detSb.append("<connectionExpression><![CDATA[$P{REPORT_CONNECTION}]]></connectionExpression>");
	    detSb.append("</datasetRun>");
	    // create fixed columns
	    
	  
	    String headerColor= " mode=\"Opaque\"  backcolor=\"#ECF0F8\" ";
	    String cellColor;
	    for(int i = 0; i < fixedCols.size(); i++)
	    {
		tblCol = fixedCols.get(i);
		if(tblCol.getCOL_SHOW_HIDE_YN().intValue() == 1)
		{
		    headerName = tblCol.getCOL_HEADER();
		    cellColor = "";
		    if(i == 0)
		    {
			headerName = "";
			cellColor = headerColor;
		    }
		    detSb.append("<jr:column width=\"105\" >");
		    detSb.append("<jr:columnHeader height=\"25\" rowSpan=\"1\">");
		    if(headerName.indexOf("$F") == 0)
		    {
			retRowDataJrxmlFeHead("JrGlobal.f_translate_string(" + headerName + ", $P{repParamsCO})",
				detSb, 5);
		    }
		    else if(headerName.indexOf("JrGlobal.f_translate_string") == -1)
		    {
		    	retRowDataJrxmlFeHead("JrGlobal.f_translate_string(\"" + headerName + "\", $P{repParamsCO})",
						detSb, 5);
		    }
		    else
		    {
		    	retRowDataJrxmlFeHead(headerName, detSb, 5);
		    }
		    detSb.append("</jr:columnHeader>");

		    detSb.append("<jr:detailCell height=\"25\" rowSpan=\"1\">");
		    detSb = retRowDataJrxmlFe(tblCol.getCOL_NAME(), detSb, 5, cellColor);
		    detSb.append("</jr:detailCell>" + "</jr:column>");
		}
	    }
	    //
	    // create dynamic columns
	  String frmDteTrans;
	    for(int i = 0; i < dynCols.size(); i++)
	    {
		tblDynCol = dynCols.get(i);
		detSb.append("<jr:column width=\"105\" >");
		detSb.append("<jr:columnHeader height=\"25\" rowSpan=\"1\">");
		if(isRowToColChecked)
		{
		    retRowDataJrxmlFeHead("JrGlobal.f_translate_string(\""+tblDynCol.getColmnTmpltVO().getBRIEF_NAME_ENG()+"\", $P{repParamsCO})", detSb, 5,headerColor);
		}
		else
		{
			frmDteTrans="";
			if("".equals(StringUtil.nullToEmpty(tblDynCol.getFROM_DATE_STR())))
			{
				frmDteTrans="+\"\\n\\n\"+"+
					 	 "\"\"" +
					 	 "+\"\\n\\n\"+"+
					 	 "\"\"" ;
			}
			else
			{
				frmDteTrans="+\"\\n\\n\"+"+
						 	 "\""+tblDynCol.getCOL_TYPE_STR()+"\"" +
						 	 "+\"\\n\\n\"+"+
						 	 "JrGlobal.f_translate_date(\""+ tblDynCol.getFROM_DATE_STR()+"\",$P{repParamsCO})";
			}
		    retRowDataJrxmlFeHead("JrGlobal.f_translate_string(\"" + tblDynCol.getColmnTmpltVO().getBRIEF_NAME_ENG()+ "\", $P{repParamsCO})"+
				frmDteTrans,
				detSb, 5, headerColor);
		}
		
		detSb.append("</jr:columnHeader>");

		detSb.append("<jr:detailCell height=\"25\" rowSpan=\"1\">");
		detSb = retRowDataJrxmlFe(RepConstantsCommon.DYNCOL_FIELD +i, detSb, 5);
		detSb.append("</jr:detailCell>" + "</jr:column>");

	    }
		detSb.append("</jr:table>" + "</componentElement>" + "</band>" + "</detail>");
	    
		// construct the final jrxml
		 StringBuffer finalJrxmlSb=new StringBuffer();
		 finalJrxmlSb.append(jrxmlSplit[0]).append(detSb).append(jrxmlSplit[1]);
		 //FileUtil.writeFile(repositoryPath + "/FINALLLLLLLLL.jrxml", finalJrxmlSb.toString());
		 return finalJrxmlSb.toString().getBytes(ConstantsCommon.FILE_ENCODING);
	}
	catch(Exception e)
	{
	    throw new BaseException("Error creating the jrxml of the report reference: "
		    + ftrOptProgRef + "\n " + e.getMessage(), e);
	}
	finally
	{
	    File file;
		try {
			file = new PathFileSecure(filePath);
			boolean isDel = file.delete();
		    if(!isDel)
		    {
			log.debug("The following jrxml has not been deleted : " + filePath);
		    }
		} catch (Exception e) {
			log.error(e, e.getMessage());
		} 
	}
    }
    
    private StringBuffer retRowDataJrxmlFeHead(String feName, StringBuffer jrxmlCols,int x) throws Exception
    {
	return retRowDataJrxmlFeHead( feName,  jrxmlCols, x, "") ;
    }
    
    /**
     * Method that will return the columns headears of the dynamic jrxml of the fcr reports
     * @return
     */
    private StringBuffer retRowDataJrxmlFeHead(String feName, StringBuffer jrxmlCols,int x,String headerColor) throws Exception
    {
	jrxmlCols
		.append("<textField isStretchWithOverflow=\"true\">"
			+ "<reportElement  x=\""+x+"\" y=\"2\" width=\"100\" height=\"17\" "+headerColor+"/>"
			+ "<textElement textAlignment=\"Center\">"
			+ "<font fontName=\"Arial\" pdfFontName=\"arial.ttf\" pdfEncoding=\"Identity-H\" isPdfEmbedded=\"true\"/>"
			+ "</textElement>" + "<textFieldExpression><![CDATA[" + feName
			+ "]]></textFieldExpression>" + "</textField>");
	return jrxmlCols;
    }
    
    private StringBuffer retRowDataJrxmlFe(String feName, StringBuffer jrxmlCols,int x) throws Exception
    {
	return retRowDataJrxmlFe( feName,  jrxmlCols, x,"");
    }
    
    /**
     * Method that will return the columns  of the dynamic jrxml of the fcr reports
     * @return
     */
    private StringBuffer retRowDataJrxmlFe(String feName, StringBuffer jrxmlCols,int x,String cellColor) throws Exception
    {
	String feVal="$F{" + feName+ "}" ;
	if("DATE_CRITERIA".equals(feName) || "TRANS_DATE".equals(feName))
	{
	    feVal="JrGlobal.f_translate_date($F{"+feName+"},$P{repParamsCO})";
	}
	jrxmlCols.append("<textField isStretchWithOverflow=\"true\" isBlankWhenNull=\"true\">"
		+ "<reportElement  x=\""+x+"\" y=\"2\" width=\"100\" height=\"17\" "+cellColor+"/>"
		+ "<textElement textAlignment=\"Center\">"
		+ "<font fontName=\"Arial\" pdfFontName=\"arial.ttf\" pdfEncoding=\"Identity-H\" isPdfEmbedded=\"true\"/>"
		+ "</textElement>" + "<textFieldExpression><![CDATA[" +
		feVal +
		"]]></textFieldExpression>" );
	if(feName.indexOf(RepConstantsCommon.DYNCOL_FIELD)==0)
	{
	    jrxmlCols
		    .append("<patternExpression><![CDATA[("
			    + "($P{RA_FORMAT}.equals(\"D\"))?JrGlobal.f_currency_mask_gl_type(JrFunc.sign($F{"+feName+"}),$F{GL_TYPE},$P{repParamsCO}) :"
			    + "($P{RA_FORMAT}.equals(\"W\"))?JrGlobal.f_currency_mask_gl_type_without_dec(JrFunc.sign($F{"+feName+"}),$F{GL_TYPE},$P{repParamsCO}) :"
			    + "($P{RA_FORMAT}.equals(\"T\"))?JrGlobal.f_currency_mask_gl_type_without_dec(JrFunc.sign($F{"+feName+"}),$F{GL_TYPE},$P{repParamsCO}) :"
			    + "($P{RA_FORMAT}.equals(\"M\"))?JrGlobal.f_currency_mask_gl_type_without_dec(JrFunc.sign($F{"+feName+"}),$F{GL_TYPE},$P{repParamsCO}) : \"\""
			    + ")]]></patternExpression>");
	}
	jrxmlCols.append("</textField>");
	return jrxmlCols;
    }   

    
    @Override
    public List<HashMap<String, Object>> retFiltersList(HashMap<String, Object> fitlersSCMap) throws Exception
    {
	IRP_REP_FILTERSSC sc=(IRP_REP_FILTERSSC)PathPropertyUtil.convertToObject(fitlersSCMap,IRP_REP_FILTERSSC.class);
	List<IRP_REP_FILTERSVO> filtersVOList =  commonReportingDAO.retFiltersList(sc);
	String[] propsArr = (String[]) ConstantsCommon.retFiltersListMap_PROPS.toArray(new String[ConstantsCommon.retFiltersListMap_PROPS.size()]);
	IRP_REP_FILTERSVO filtersVO;
	List<HashMap<String,Object>> retLst = new ArrayList<HashMap<String,Object>>();
	HashMap<String, Object> hm;
	for(int i=0;i<filtersVOList.size();i++)
	{
	    filtersVO=filtersVOList.get(i);
	    hm=new HashMap<String, Object>();
	    PathPropertyUtil.copyProperties(filtersVO,hm,false,propsArr);
	    retLst.add(hm);
	}
	return retLst;
    }
    
    @Override
    public int retFiltersListCount(HashMap<String, Object> fitlersSCMap) throws Exception
    {
	IRP_REP_FILTERSSC sc=(IRP_REP_FILTERSSC)PathPropertyUtil.convertToObject(fitlersSCMap,IRP_REP_FILTERSSC.class);
	return commonReportingDAO.retFiltersListCount(sc);
    }
    
    @Override
    public BigDecimal saveFilter(HashMap<String, String> nameValueMap, HashMap<String, Object> nameObjMap,
	    HashMap<String, Object> filtersVOMap, String mode, boolean overrideArguments) throws Exception
    {
	IRP_REP_FILTERSVO filterVO = (IRP_REP_FILTERSVO) PathPropertyUtil.convertToObject(filtersVOMap,
		IRP_REP_FILTERSVO.class);
	BigDecimal filterId = null;
	// updating
	if(ConstantsCommon.UPDATE_MODE.equals(mode) && overrideArguments)
	{
	    commonReportingDAO.deleteFilterArgumentsById(filterVO.getFILTER_ID());
	    genericDAO.delete(filterVO);
	}
	// insert
	Date creationDate = new Date();
	IRP_REP_FILTERSVO newFilterVO = new IRP_REP_FILTERSVO();
	newFilterVO.setFILTER_NAME(filterVO.getFILTER_NAME());
	newFilterVO.setPUBLIC_YN(filterVO.getPUBLIC_YN());
	newFilterVO.setDEFAULT_YN(filterVO.getDEFAULT_YN());
	filterId = filterVO.getFILTER_ID();
	newFilterVO.setCREATED_BY(filterVO.getCREATED_BY());
	newFilterVO.setCREATED_DATE(creationDate);
	newFilterVO.setFILTER_ID(filterVO.getFILTER_ID());
	if(ConstantsCommon.CREATE_MODE.equals(mode) || overrideArguments)
	{
	    filterId = commonReportingDAO.selectMaxFilterId();
	    newFilterVO.setFILTER_ID(filterId);
	    genericDAO.insert(newFilterVO);
	}
	else
	{
	    genericDAO.update(newFilterVO);
	}
	if(overrideArguments)
	{
	    IRP_REP_ARGUMENTS_FILTERSVO argFilterVO;
	    IRP_REP_ARGUMENTSCO repArgCO;
	    for(Entry<String, Object> entryObj : nameObjMap.entrySet())
	    {
		repArgCO = (IRP_REP_ARGUMENTSCO) PathPropertyUtil.convertToObject(
			(HashMap<String, Object>) entryObj.getValue(), IRP_REP_ARGUMENTSCO.class);
		argFilterVO = new IRP_REP_ARGUMENTS_FILTERSVO();
		argFilterVO.setFILTER_ID(filterId);
		argFilterVO.setREPORT_ID(repArgCO.getREPORT_ID());
		argFilterVO.setARGUMENT_ID(repArgCO.getARGUMENT_ID());
		argFilterVO.setCREATED_DATE(creationDate);
		argFilterVO.setCREATED_BY(filterVO.getCREATED_BY());
		argFilterVO.setARGUMENT_VALUE(nameValueMap.get(repArgCO.getARGUMENT_NAME()));
		genericDAO.insert(argFilterVO);
	    }
	}
	return filterId;
    }
    
    @Override
    public List<HashMap<String, Object>> retFilterArgumentsValues(HashMap<String, Object> filtersSCMap)
	    throws Exception
    {
	IRP_REP_FILTERSSC sc = (IRP_REP_FILTERSSC) PathPropertyUtil.convertToObject(filtersSCMap,
		IRP_REP_FILTERSSC.class);
	List<HashMap<String, Object>> filterArgsList = commonReportingDAO.retFilterArgumentsValues(sc);
	HashMap<String, Object> tmpMap;
	IRP_REP_ARGUMENTS_FILTERSVO tmpVO;
	for(int i = 0; i < filterArgsList.size(); i++)
	{
	    tmpMap = filterArgsList.get(i);
	    tmpVO = new IRP_REP_ARGUMENTS_FILTERSVO();
	    tmpVO.setREPORT_ID((BigDecimal) tmpMap.get(ConstantsCommon.REPORT_ID_COL));
	    tmpVO.setFILTER_ID((BigDecimal) tmpMap.get(ConstantsCommon.FILTER_ID_COL));
	    tmpVO.setARGUMENT_ID((BigDecimal) tmpMap.get(ConstantsCommon.ARGUMENT_ID_COL));
	    tmpVO = (IRP_REP_ARGUMENTS_FILTERSVO) genericDAO.selectByPK(tmpVO);
	    tmpMap.put(ConstantsCommon.ARGUMENT_VALUE_COL, tmpVO.getARGUMENT_VALUE());
	}
	return filterArgsList;
    }
    @Override
    public void deleteFilterById(BigDecimal filterId) throws Exception
    {
	commonReportingDAO.deleteFilterArgumentsById(filterId);	
	IRP_REP_FILTERSVO repFilterVO = new IRP_REP_FILTERSVO();
	repFilterVO.setFILTER_ID(filterId);
	genericDAO.delete(repFilterVO);
    }
    
    @Override
    public boolean checkFilterNameUnique(HashMap<String, Object> filtersSCMap) throws Exception
    {
	IRP_REP_FILTERSSC sc = (IRP_REP_FILTERSSC) PathPropertyUtil.convertToObject(filtersSCMap,
		IRP_REP_FILTERSSC.class);
	return commonReportingDAO.checkFilterNameUnique(sc);
    }
    
    @Override
    public BigDecimal selectDefaultFilter(HashMap<String, Object> filtersSCMap) throws Exception
    {
	IRP_REP_FILTERSSC sc = (IRP_REP_FILTERSSC) PathPropertyUtil.convertToObject(filtersSCMap,
		IRP_REP_FILTERSSC.class);
	return commonReportingDAO.selectDefaultFilter(sc);
    }
    
    @Override
    public int checkTextFormulaExist(HashMap<String,Object> repSCMap) throws Exception
    {
    	IRP_AD_HOC_REPORTSC sc = (IRP_AD_HOC_REPORTSC) PathPropertyUtil.convertToObject(repSCMap,
    			IRP_AD_HOC_REPORTSC.class);
    	
    	SnapshotParameterSC snpSC=new SnapshotParameterSC();
    	snpSC.setREP_REFERENCE(sc.getPROG_REF());
    	return snapshotParameterBO.checkTextFormulaExist(snpSC);
    }
    
    @Override
    public String returnGeneratedFileName(String generatedFileName, Map parameters) throws Exception
    {
	String fileName = generatedFileName;

	Date sysDate = commonLibBO.returnSystemDateWithTime();
	Calendar cal = Calendar.getInstance();
	cal.setTime(sysDate);

	String year=String.valueOf(cal.get(Calendar.YEAR));
	String yy=year.substring(2,4);
	
	String month = String.valueOf(cal.get(Calendar.MONTH) + 1);
	month = CommonMethods.fill("0", new BigDecimal(2 - month.length())) + month;

	String day = String.valueOf(cal.get(Calendar.DAY_OF_MONTH));
	day = CommonMethods.fill("0", new BigDecimal(2 - day.length())) + day;

	String hrAmPM = String.valueOf(cal.get(Calendar.HOUR));
	hrAmPM = CommonMethods.fill("0", new BigDecimal(2 - hrAmPM.length())) + hrAmPM;

	String hr = String.valueOf(cal.get(Calendar.HOUR_OF_DAY));
	hr = CommonMethods.fill("0", new BigDecimal(2 - hr.length())) + hr;

	String min = String.valueOf(cal.get(Calendar.MINUTE));
	min = CommonMethods.fill("0", new BigDecimal(2 - min.length())) + min;

	String sec = String.valueOf(cal.get(Calendar.SECOND));
	sec = CommonMethods.fill("0", new BigDecimal(2 - sec.length())) + sec;
	
	String amPm = cal.get(Calendar.AM_PM) == 0 ? "AM" : "PM";

	fileName = fileName.replaceAll("\\[(YY|AA)\\]", yy);
	fileName = fileName.replaceAll("\\[(YYYY|AAAA)\\]", year);
	fileName = fileName.replaceAll("\\[(DD|JJ)\\]", day);
	fileName = fileName.replaceAll("\\[MM\\]", month);
	fileName = fileName.replaceAll("\\[HH\\]", hr);
	fileName = fileName.replaceAll("\\[hh\\]", hrAmPM);
	fileName = fileName.replaceAll("\\[mm\\]", min);
	fileName = fileName.replaceAll("\\[ss\\]", sec);
	fileName = fileName.replaceAll("\\[(A|P)M\\]", amPm);
	fileName = fileName.replace("[DT_MS]", String.valueOf(sysDate.getTime()));

	// Replacing arguments values
	Pattern pattern = Pattern.compile("\\[PARAM_(.*?)\\]");
	Matcher matcher = pattern.matcher(fileName);
	String argMatcher;
	String argName;
	Object argValueObj;
	String argValue;
	while(matcher.find())
	{
	    argMatcher = matcher.group();
	    argName = argMatcher.substring(7, argMatcher.length() - 1);
	    argValueObj = parameters.get(argName);
	    if(argValueObj instanceof java.util.Date)
	    {
		argValue = DateUtil.format((Date) argValueObj, "dd_MM_yyyy");
	    }
	    else
	    {
		argValue = StringUtil.nullToEmpty(argValueObj);
	    }
	    fileName = fileName.replaceAll("\\[PARAM_" + argName + "\\]", argValue);
	}
	return fileName;
    }
    
    @Override
	public ArrayList<HashMap<String, Object>> returnArgListByQryId(HashMap<String,Object> hm)
			throws BaseException, IOException, ClassNotFoundException 
{
	
	IRP_AD_HOC_QUERYCO queryCO = new IRP_AD_HOC_QUERYCO();
	BigDecimal queryId = (BigDecimal) hm.get("queryId");
	queryCO = commonReportingDAO.returnQryResult(queryId);
	queryCO.setGrpByQryName(RepConstantsCommon.CALLED_FROM_COMMON);
	queryBO.fillSqlObj(queryCO);
	LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments;
	arguments = queryCO.getSqlObject().getArgumentsList();
	ArrayList<HashMap<String,Object>> qryArgMaplst = new ArrayList<HashMap<String, Object>>();
	//list of the query's arguments
		ArrayList<IRP_REP_ARGUMENTSCO> lst = new ArrayList(arguments.values());
		IRP_REP_ARGUMENTSVO queryArgVO;
		HashMap<String, Object> qryArgHM;
		for (int i = 0; i < lst.size(); i++)
		{
			qryArgMaplst.add(PathPropertyUtil.convertToMap(lst.get(i)));
		}
	return qryArgMaplst;
}

    @Override
    public StringBuffer returnQuerySyntaxWithReplacedArgs(BigDecimal queryId,
	    ArrayList<HashMap<String, Object>> argsMapList) throws BaseException, IOException, ClassNotFoundException
    {
	IRP_AD_HOC_QUERYCO queryCO;
	HashMap<String, String> hmQryParam;
	queryCO = commonReportingDAO.returnQryResult(queryId);
	queryCO.setGrpByQryName(RepConstantsCommon.CALLED_FROM_COMMON);
	queryBO.fillSqlObj(queryCO);
	String sqlStr = queryCO.getSqlObject().getSqbSyntax().toString();
	String theKey;
	HashMap<String, Object> hm;
	IRP_REP_ARGUMENTSVO queryArgVO;
	DynLookupSC dynLookupSC = new DynLookupSC();
	dynLookupSC.setIsSybase(queryCO.getIsSybase());
	dynLookupSC.setSqlStr(sqlStr);
	hmQryParam = new HashMap<String, String>();
	for(int i = 0; i < argsMapList.size(); i++)
	{
	    hm = argsMapList.get(i);
	    queryArgVO = new IRP_REP_ARGUMENTSVO();
	    queryArgVO = (IRP_REP_ARGUMENTSVO) PathPropertyUtil.convertToObject(hm, IRP_REP_ARGUMENTSVO.class);
	    if(ConstantsCommon.PARAM_TYPE_VARCHAR2.equals(queryArgVO.getARGUMENT_TYPE()))
	    {
		hmQryParam.put(queryArgVO.getARGUMENT_NAME(), "'" + queryArgVO.getARGUMENT_VALUE() + "'");
	    }
	    else if(ConstantsCommon.PARAM_TYPE_DATE.equals(queryArgVO.getARGUMENT_TYPE()))
	    {
		queryArgVO.setARGUMENT_VALUE(queryArgVO.getARGUMENT_VALUE() + "-DATE");
		hmQryParam.put(queryArgVO.getARGUMENT_NAME(), queryArgVO.getARGUMENT_VALUE());
	    }
	    else
	    {
		hmQryParam.put(queryArgVO.getARGUMENT_NAME(), queryArgVO.getARGUMENT_VALUE());
	    }
	}
	dynLookupSC.setHmQryParam(hmQryParam);
	sqlStr = commonLookupBO.replaceQryArgByVal(dynLookupSC);

	return new StringBuffer(sqlStr);
    }
    
    @Override
    public String retConnectionDescById(BigDecimal conId) throws BaseException
    {
	IRP_CONNECTIONSVO con = new IRP_CONNECTIONSVO();
	con.setCONN_ID(conId);
	return ((IRP_CONNECTIONSVO)genericDAO.selectByPK(con)).getCONN_DESC();
    }
    
	
    @Override
    public HashMap<String, Object> returnReportDetailsByRef(HashMap<String, Object> paramsMap) throws Exception
    {
	IRP_AD_HOC_REPORTVOWithBLOBs vo = (IRP_AD_HOC_REPORTVOWithBLOBs) PathPropertyUtil.convertToObject(paramsMap,
		IRP_AD_HOC_REPORTVOWithBLOBs.class);
	IRP_AD_HOC_REPORTVOWithBLOBs repFromDbVO = designerBO.retExistRepInDb(vo);
	if(repFromDbVO == null)
	{
	    repFromDbVO = new IRP_AD_HOC_REPORTVOWithBLOBs();
	}
	return PathPropertyUtil.convertToMap(repFromDbVO);

    }
    
    @Override
    public void renameBatchFolder(HashMap<String, Object> paramsMap) throws Exception
    {
	// will receive the name of the folder directly under MTS folder
	String oldBatchName = (String) paramsMap.get("oldBatchName");
	String newBatchName = (String) paramsMap.get("newBatchName");
	String repositoryProcPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder)
		+ "/" + ConstantsCommon.REP_PROC_FOLDER;
	File oldBatchDirectory = new PathFileSecure(repositoryProcPath + "/" + oldBatchName);
	if(!oldBatchDirectory.isDirectory())
	{
	    log.error("There is no directory " + oldBatchName + " @ given path");
	}
	else
	{
	    File newBatchDirectory = new PathFileSecure(oldBatchDirectory.getParent() + "/" + newBatchName);
	    oldBatchDirectory.renameTo(newBatchDirectory);
	}
    }
    
    @Override
    public HashMap<String,Object> returnReportByteArrayCurrentPage(HashMap<String,Object> paramsMap)throws Exception
    {
    	return ((HashMap<String,Object>)reportBO.returnReportByteArrayCurrentPage(paramsMap));
    }

	@Override
	public HashMap<String, Object> returnQueriesList(HashMap<String, Object> paramsMap) throws Exception 
	{
		IRP_AD_HOC_QUERYSC sc = (IRP_AD_HOC_QUERYSC) PathPropertyUtil.convertToObject(paramsMap, IRP_AD_HOC_QUERYSC.class);
		List<IRP_AD_HOC_QUERYVO> irp_AD_HOC_QUERYVOs = queryBO.getQueriesList(sc);
		List<HashMap<String, Object>> result = new ArrayList<HashMap<String, Object>>();
		for(IRP_AD_HOC_QUERYVO irp_AD_HOC_QUERYVO : irp_AD_HOC_QUERYVOs)
		{
			 HashMap<String, Object> map = PathPropertyUtil.convertToMap(irp_AD_HOC_QUERYVO);
			 result.add(map);
		}
		HashMap<String, Object> hashOut = new HashMap<String, Object>();
		hashOut.put("queriesList", result);
		return hashOut;
	}
	
	@Override
	public HashMap<String, Object> returnQueriesListCount(HashMap<String, Object> paramsMap) throws Exception 
	{
		IRP_AD_HOC_QUERYSC sc = (IRP_AD_HOC_QUERYSC) PathPropertyUtil.convertToObject(paramsMap, IRP_AD_HOC_QUERYSC.class);
		int queryCount = queryBO.getQueriesCount(sc);
		HashMap<String, Object> hashMap = new  HashMap<String, Object>();
		hashMap.put("queriesListCount", queryCount);
		return hashMap;
	}
	
    @Override
    public void saveSorting(HashMap<String, Object> lstSortingAllMap) throws Exception
    {
	IRP_FIELDSCO irpFieldCO;
	IRP_REP_SORTVO lVO;
	//delete all the records related to this prog ref
	IRP_AD_HOC_REPORTSC irpAdHocRepSC = new IRP_AD_HOC_REPORTSC();
	irpAdHocRepSC.setREPORT_ID((BigDecimal)lstSortingAllMap.get("reportId"));
	irpAdHocRepSC.setUSER_ID((String)lstSortingAllMap.get("userId"));
	commonReportingDAO.deleteSorting(irpAdHocRepSC);
	
	ArrayList<HashMap<String, Object>> SortMapList=new ArrayList<HashMap<String, Object>>();
	HashMap<String, Object> SortMap;
	SortMapList = (ArrayList<HashMap<String, Object>>) lstSortingAllMap.get("lstSorting");
	Iterator<HashMap<String, Object>> itSort = SortMapList.iterator();
	while(itSort.hasNext())
	{
	    SortMap = new HashMap<String, Object>();
	    SortMap = itSort.next();
	    irpFieldCO = new IRP_FIELDSCO();
	    irpFieldCO = (IRP_FIELDSCO) PathPropertyUtil.convertToObject(SortMap, IRP_FIELDSCO.class);
	    if(!irpFieldCO.getOrder().isEmpty())
	    {
		lVO = new IRP_REP_SORTVO();
		lVO.setREPORT_ID((BigDecimal) lstSortingAllMap.get("reportId"));
		lVO.setFIELD_DESC(irpFieldCO.getFIELD_ALIAS());
		lVO.setFIELD_ORDER(irpFieldCO.getOrder());
		lVO.setUSER_ID((String) lstSortingAllMap.get("userId"));
		genericDAO.insert(lVO);
	    }
	}
    }
    
    @Override
    public HashMap<String, Object> retSortingListFromIrpRepSort(HashMap<String, Object> lstSortingMap) throws Exception
    {
	IRP_FIELDSCO irpFieldCO;
	IRP_AD_HOC_REPORTSC irpAdHocRepSC = new IRP_AD_HOC_REPORTSC();
	irpAdHocRepSC.setREPORT_ID((BigDecimal)lstSortingMap.get("reportId"));
	irpAdHocRepSC.setUSER_ID((String)lstSortingMap.get("userId"));
	ArrayList<IRP_FIELDSCO> irpFieldsCOList = commonReportingDAO.retSortingListFromIrpRepSort(irpAdHocRepSC);
//	HashMap<String, Object> SortMap;
	ArrayList<HashMap<String, Object>> SortMapList = new ArrayList<HashMap<String, Object>>();
	for(int i = 0; i < irpFieldsCOList.size(); i++)
	{
	    irpFieldCO = irpFieldsCOList.get(i);
//	    SortMap = new HashMap<String, Object>();
//	    SortMap.put("fieldAlias", irpFieldCO.getFIELD_ALIAS());
//	    SortMap.put("fieldOrder", irpFieldCO.getOrder());
	    SortMapList.add(PathPropertyUtil.convertToMap(irpFieldCO));
	}
	lstSortingMap.put("irpFieldsCOList", SortMapList);
	return lstSortingMap;
    }

}
