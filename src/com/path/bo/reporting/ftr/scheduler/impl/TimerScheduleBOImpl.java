package com.path.bo.reporting.ftr.scheduler.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.springframework.remoting.RemoteInvocationFailureException;
import org.springframework.remoting.RemoteLookupFailureException;

import com.path.alert.vo.notification.AlertNotificationCO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.WebServiceCommonBO;
import com.path.bo.reporting.CommonReportingBO;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.designer.ProceduresBO;
import com.path.bo.reporting.designer.SnapShotBO;
import com.path.bo.reporting.ftr.scheduler.TimerScheduleBO;
import com.path.bo.reporting.mailserver.MailServerConfigBO;
import com.path.dao.reporting.common.ReportDAO;
import com.path.dao.reporting.ftr.scheduler.SchedulerDAO;
import com.path.dbmaps.vo.BRANCHESVO;
import com.path.dbmaps.vo.BRANCHESVOKey;
import com.path.dbmaps.vo.CURRENCIESVO;
import com.path.dbmaps.vo.CURRENCIESVOKey;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOGVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.remote.RmiServiceCaller;
import com.path.proc.bo.procservices.ProcServiceWrapperBO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSSC;
import com.path.vo.reporting.scheduler.IRP_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_SCHEDULESC;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class TimerScheduleBOImpl extends BaseBO implements TimerScheduleBO
{
    private SchedulerDAO schedulerDAO;
    private ReportDAO reportDAO;
    private ReportBO reportBO;
    private String reportingPortalURL;
    private ProceduresBO procBO;
    private SnapShotBO snapShotBO;
    private CommonRepFuncBO commonRepFuncBO;
    private MailServerConfigBO mailServerConfigBO;
    private CommonLookupBO commonLookupBO;
    private CommonReportingBO commonReportingBO;
    private WebServiceCommonBO webServiceCommonBO;
    
    public void setWebServiceCommonBO(WebServiceCommonBO webServiceCommonBO) 
    {
		this.webServiceCommonBO = webServiceCommonBO;
	}

	public void setCommonReportingBO(CommonReportingBO commonReportingBO)
    {
        this.commonReportingBO = commonReportingBO;
    }

    public CommonLookupBO getCommonLookupBO()
    {
        return commonLookupBO;
    }

    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
    {
        this.commonLookupBO = commonLookupBO;
    }

    public MailServerConfigBO getMailServerConfigBO()
    {
	return mailServerConfigBO;
    }

    public void setMailServerConfigBO(MailServerConfigBO mailServerConfigBO)
    {
	this.mailServerConfigBO = mailServerConfigBO;
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

    public SchedulerDAO getSchedulerDAO()
    {
	return schedulerDAO;
    }

    public void setSchedulerDAO(SchedulerDAO schedulerDAO)
    {
	this.schedulerDAO = schedulerDAO;
    }

    public ReportBO getReportBO()
    {
	return reportBO;
    }

    public void setReportBO(ReportBO reportBO)
    {
	this.reportBO = reportBO;
    }

    Timer timer;

//    public TimerScheduleBOImpl()
//    {
//    }

    public void startTimer(ReportParamsCO repParamsCO)
    {
	if(timer == null)
	{
	    timer = new Timer();
	}
	timer.schedule(new ReportGenerator(repParamsCO), 0, 1000 * 60 * 1);// schedule
									   // a
									   // task
									   // to
									   // be
									   // run
									   // every
									   // 1
									   // min
    }

    public void stopTimer()
    {
	if(timer != null)
	{
	    timer.cancel();
	    timer = null;
	}
    }

    /**
     * Called to execute the schedule on Demand
     * 
     * @param repParamsCO 
     * @param schedID
     * @return void
     * @throws BaseException, ParseException
     */
    public void executeImmediate(ReportParamsCO repParamsCO,BigDecimal schedId) throws BaseException, ParseException
    {
	IRP_SCHEDULESC sched = new IRP_SCHEDULESC();
	    sched.setSCHED_ID(schedId);
	    List<IRP_REPORT_SCHEDULECO> reps = schedulerDAO.selectSchedReports(sched);

	    for(IRP_REPORT_SCHEDULECO report : reps)
	    {
		report.setNEXT_RUN_DATE(commonLibBO.returnSystemDateWithTime());
		report.setCompCode(repParamsCO.getCompanyCode());
	    }
	    ReportGenerator myGenerator = new ReportGenerator(repParamsCO);
	    myGenerator.runReports(reps);
    }

    public class ReportGenerator extends TimerTask
    {
	ReportParamsCO repParamsCO;

	public ReportGenerator(ReportParamsCO repParamsCO)
	{
	    this.repParamsCO = repParamsCO;
	}

	public void run()
	{
	    try
	    {
		if(commonLibBO.clusterOperationControl(RepConstantsCommon.SCHED_CLUSTER_OPER_NAME, false))
		{
		    generateReports();
		}
	    }
	    catch(BaseException e)
	    {
		e.printStackTrace();
	    }
	    catch(ParseException e)
	    {
		e.printStackTrace();
	    }
	}

	private void generateReports() throws BaseException, ParseException
	{
	    IRP_SCHEDULESC sched = new IRP_SCHEDULESC();
	    sched.setNEXT_RUN_DATE(Calendar.getInstance().getTime());
	    List<IRP_REPORT_SCHEDULECO> reps = schedulerDAO.selectDueReports(sched);
	    reps.stream().forEach(co -> co.setCompCode(repParamsCO.getCompanyCode()));
	    runReports(reps);
	}
	
	private ProcServiceWrapperBO returnProcServiceBean() throws BaseException
	{
	    ProcServiceWrapperBO procServiceWrapperBO;
	    try
	    {
		String procServiceUrl = PathPropertyUtil.returnPathPropertyFromFile("PathRemoting.properties",
			"proc.serviceURL");
		if(procServiceUrl == null)
		{
		    procServiceUrl = PathPropertyUtil.returnPathPropertyFromFile("PathProcRemoting.properties",
			    "proc.serviceURL");
		    if(procServiceUrl == null)
		    {
			log.error("Invalid PROC URL");
			return null;
		    }		    
		}
		procServiceWrapperBO = (ProcServiceWrapperBO) RmiServiceCaller.returnRmiInterface(
			procServiceUrl.concat("ProcServiceWrapperBOService"), ProcServiceWrapperBO.class);
	    }
	    catch(Exception e)
	    {
		throw new BaseException(e);
	    }
	    return procServiceWrapperBO;
	}
	
	private void runReports( List<IRP_REPORT_SCHEDULECO> reps) throws BaseException, ParseException
	{
	  //  IRP_SCHEDULESC sched = new IRP_SCHEDULESC();
	//    sched.setNEXT_RUN_DATE(Calendar.getInstance().getTime());
	 //   List<IRP_REPORT_SCHEDULECO> reps = schedulerDAO.selectDueReports(sched);
	    Map<String, Object> parameters = null;
	    IRP_REPORT_SCHED_PARAMSSC paramSC = null;
	    List<IRP_REPORT_SCHED_PARAMSCO> params = null;
	    SchedulerThread thrd = null;
	    Map paramValMap = null;
	    String paramType;
	    String paramVal;
	    String paramName;
	    BigDecimal isMultiSel;
	    String dateFrmt;
	    Date dt,runningDate;
	    String sessionAttrName;
	    Object  argSessionValue=null ;
	    boolean isDateSession;
            String schedDateTrans=commonLookupBO.retSchedDateParamLovVal(repParamsCO.getLanguage());
            String sysDateTrans=commonLookupBO.retSysDateParamLovVal(repParamsCO.getLanguage());
            int isSybase=commonLibBO.returnIsSybase();
            HashMap<String,Object> hmStrObj = new HashMap<String,Object>();
            boolean proceedReportBatch=false;
            ProcServiceWrapperBO procServiceWrapperBO;
            HashMap<BigDecimal,String> cachedBatchStatusMap = new HashMap<BigDecimal,String>();
            HashMap<BigDecimal,String> nextDateUpdatedMap = new HashMap<BigDecimal,String>();
	    for(IRP_REPORT_SCHEDULECO report : reps)
	    {
		if(!NumberUtil.isEmptyDecimal(report.getBATCH_CODE())
			&& cachedBatchStatusMap.get(report.getBATCH_CODE()) == null)
		{
		    hmStrObj.put("BATCH_CODE", report.getBATCH_CODE());
		    hmStrObj.put("RUNNING_DATE", report.getNEXT_RUN_DATE());
		    try
		    {
			procServiceWrapperBO = returnProcServiceBean();
			if(procServiceWrapperBO != null)
			{
			    proceedReportBatch = procServiceWrapperBO.returnBatchInstanceStatus(hmStrObj);
			}
		    }
		    catch(RemoteLookupFailureException e)
		    {
			log.error("MTS server is down");
		    }
		    catch(RemoteInvocationFailureException e)
		    {
			log.error("the method returnBatchInstanceStatus is not available , new MTS build is needed");
		    }
		    if(proceedReportBatch)
		    {
			cachedBatchStatusMap.put(report.getBATCH_CODE(), ConstantsCommon.TRUE);
		    }
		    else
		    {
			continue;
		    }
		}
		// Code for Alert Events
		else if(!NumberUtil.isEmptyDecimal(report.getEVT_ID()))
		{
			HashMap<String, Object> hmObj = new HashMap<String,Object>();

			IRP_REPORT_SCHED_LOGVO logVo = new IRP_REPORT_SCHED_LOGVO();
		    logVo.setREPORT_ID(new BigDecimal(-1));
			logVo.setSCHED_ID(report.getSCHED_ID());
			logVo.setREMARKS("");
			logVo.setSTART_DATE(commonLibBO.returnSystemDateWithTime());
			logVo.setEND_DATE(commonLibBO.returnSystemDateWithTime());
			logVo.setSCHEDULED_DATE(report.getNEXT_RUN_DATE());
			logVo.setSTATUS("r");
			logVo.setSAVE_DATA_TYPE(RepConstantsCommon.SAVE_TYPE_ALERT);
			logVo.setREPORT_FORMAT("");
			genericDAO.insert(logVo);
			
			try 
			{
				AlertNotificationCO notificationCO = new AlertNotificationCO();				
				notificationCO.setEventId(report.getEVT_ID());
				hmObj = PathPropertyUtil.convertToMap(notificationCO);
				hmObj.put("companyCode", report.getCompCode());
				Map<String, Object> resultMap = webServiceCommonBO.callPWS(new BigDecimal(4), hmObj);

				logVo.setSTATUS("s");
				
				/**
				 * @note this block look like the code in SchedulerBO findSingleSchedule
				 * which should be checked later if it's a duplication
				 */
				Date nextDate = null;	
				IRP_SCHEDULESC sc = new IRP_SCHEDULESC();
				sc.setSCHED_TYPE_LOV_TYPE_ID(RepConstantsCommon.SCHED_TYPE);
			    sc.setAlertTypeValueCode(RepConstantsCommon.ALERT_TYPE_LOV_CODE);
			    sc.setPreferredLanguage(ConstantsCommon.LANGUAGE_ENGLISH);
				sc.setSCHED_ID(report.getSCHED_ID());
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
				IRP_SCHEDULECO co = schedulerDAO.findSingleSchedule(sc);
				co.setScheduleDetails(schedulerDAO.findScheduleDetails(sc));
				Date nxtRunDte=co.getSCHED_VO().getNEXT_RUN_DATE();
				if(nxtRunDte!=null)
				{
					nextDate = schedulerDAO.retNextRunningDate( co );
					sc.setNEXT_RUN_DATE(nextDate);
					schedulerDAO.updateNextRunDate(sc);
				}
				genericDAO.update(logVo);
			} 
			catch(RemoteLookupFailureException e)
		    {
				logVo.setREMARKS("Cannot connect to Alert service");
				logVo.setSTATUS("f");
				genericDAO.update(logVo);
		    	log.error(e.getMessage());
		    }
		    catch(RemoteInvocationFailureException e)
		    {
		    	logVo.setREMARKS("Cannot connect to Alert service");
		    	logVo.setSTATUS("f");
		    	genericDAO.update(logVo);
		    	log.error(e.getMessage());
		    }
			catch (Exception e) 
			{
				logVo.setREMARKS(e.getMessage());
		    	logVo.setSTATUS("f");
		    	schedulerDAO.updateLOG(logVo);
		    	log.error(e.getMessage());
			}
		    continue;
		}
		parameters = new HashMap<String, Object>();
		paramValMap = new HashMap();
		paramSC = new IRP_REPORT_SCHED_PARAMSSC();
		paramSC.setREPORT_ID(report.getREPORT_ID());
		paramSC.setSCHED_ID(report.getSCHED_ID());
		paramSC.setREPORT_REF(report.getREPORT_REF());
		params = schedulerDAO.findReportScheduleParams(paramSC);
		// fill repParamsCO with additional parameters
		repParamsCO.setRepAppName(report.getREPORT_APP_NAME());
		repParamsCO.setRepLanguage(report.getREPORT_LANGUAGE());
		repParamsCO.setProgRef(report.getPROG_REF());
		for(IRP_REPORT_SCHED_PARAMSCO param : params)// /for(i=0;i<params.length;i++){param=params[i]}
		{
			
		    isDateSession=false;
		    paramType = param.getPARAM_TYPE();
		    paramVal = param.getPARAM_VALUE();
		    isMultiSel = param.getMULTISELECT_YN();
		    //if session parameter
		    if(ConstantsCommon.SESSION_ARG_SOURCE.equals(param.getARGUMENT_SOURCE()) || ConstantsCommon.TRANS_SESSION_ARG_SOURCE.equals(param.getARGUMENT_SOURCE()))
		    {
			sessionAttrName=param.getSESSION_ATTR_NAME();
			argSessionValue=retSessionVal( sessionAttrName ,  repParamsCO, param.getARGUMENT_SOURCE());
			if (("DATE").equals(param.getPARAM_TYPE()) && DateUtil.isValidDate((String) argSessionValue,"E MMM dd HH:mm:ss Z yyyy"))
			{
				DateFormat formatter = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
				runningDate = (Date)formatter.parse((String)argSessionValue);
				argSessionValue = runningDate;
			}
			if(argSessionValue!=null)
			{
			    paramVal=argSessionValue.toString();
			}
			if(ConstantsCommon.PARAM_TYPE_DATE.equals(paramType) || ConstantsCommon.datetime.equals(paramType))
			{
			    isDateSession=true;
			}
		    }
		    if(ConstantsCommon.REP_LANG_ARG_SOURCE.equals(param.getARGUMENT_SOURCE()))
		    {
			paramVal = report.getREPORT_LANGUAGE();
		    }
		    paramName = param.getPARAM_NAME();
		    if(isDateSession)
		    {
			parameters.put(paramName,paramVal!=null?new SimpleDateFormat(RepConstantsCommon.SCHED_SESS_DATE_FORMAT).parse(paramVal):null);
		    }
		    else
		    {
			parameters.put(paramName, retArgument(paramVal, paramType,schedDateTrans, sysDateTrans,report,isMultiSel));
		    }

		    if(paramVal != null && !paramVal.isEmpty() && !BigDecimal.ONE.equals(isMultiSel))
		    {
			if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(paramType))
			{
			    paramValMap.put(paramName, new BigDecimal(paramVal));
			}
			else if(ConstantsCommon.PARAM_TYPE_DATE.equals(paramType))
			{
			    if(isDateSession)
			    {
				paramValMap.put(paramName,new SimpleDateFormat(RepConstantsCommon.SCHED_SESS_DATE_FORMAT).parse(paramVal));
			    }
			    else
			    {
				paramValMap.put(paramName, DateUtil.parseDate(paramVal, "dd/MM/yyyy"));
			    }
			}
			else if( ConstantsCommon.datetime.equals(paramType))
			{
			    if(isDateSession)
			    {
				paramValMap.put(paramName,argSessionValue);
			    }
			     else if(sysDateTrans!=null && sysDateTrans.equals(paramVal))
			     {
				 dt = commonLibBO.returnSystemDateWithTime();
			     }
			     else if(schedDateTrans!=null && schedDateTrans.equals(paramVal))
			     {
				 dt = report.getNEXT_RUN_DATE();
			     }
			    else
			    {
				dateFrmt = DateUtil.getDatePattern(paramVal);
				dt=DateUtil.parseDate(paramVal, dateFrmt);
				paramValMap.put(paramName,new java.sql.Timestamp(dt.getTime()));
			    }
			}
			else
			{
			    paramValMap.put(paramName, paramVal);
			}
		    }
		}

		// String realPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);

		// send ReportParamsCO object as parameter to jasperReport api



		parameters.put("repParamsCO", repParamsCO);

		if(nextDateUpdatedMap.get(report.getSCHED_ID()) != null)
		{
		    nextDateUpdatedMap.put(report.getSCHED_ID(), ConstantsCommon.FALSE);
		}
		else
		{
		    nextDateUpdatedMap.put(report.getSCHED_ID(), ConstantsCommon.TRUE);
		}
		thrd = new SchedulerThread();
		thrd.setReportName(report.getPROG_REF() + "_" + report.getREPORT_APP_NAME());
		thrd.setReportDAO(reportDAO);
		thrd.setSchedulerDAO(schedulerDAO);
		thrd.setReportBO(reportBO);
		thrd.setCommonLibBO(commonLibBO);
		thrd.setProcBO(procBO);
		thrd.setFormat(report.getREPORT_FORMAT());
		thrd.setCsvSep(report.getCSV_SEPARATOR());
		thrd.setSnapShotBO(snapShotBO);
		thrd.setCommonRepFuncBO(commonRepFuncBO);
		thrd.setUserName(repParamsCO.getUserName());
		thrd.setSaveDataType(report.getSAVE_DATA_TYPE());
		thrd.setCurrentReport(report);
		thrd.setMailServerConfigBO(mailServerConfigBO);
		thrd.setCommonLookupBO(commonLookupBO);
		thrd.setGenericDAO(genericDAO);
		thrd.setNextDateUpdatedMap(nextDateUpdatedMap);
		if(BigDecimal.ONE.toString().equals(report.getSHOW_HEAD_FOOT()))
		{
		    thrd.setShowHeadFoot(true);
		}
		else
		{
		    thrd.setShowHeadFoot(false);
		}
		// thrd.setFileName(report.getSCHED_ID().toString() + "_" +
		// report.getREPORT_ID());
		
		// thrd.setRealPath(realPath);
		thrd.setParameters(parameters);
		thrd.setREPORT_ID(report.getREPORT_ID());
		thrd.setSCHED_ID(report.getSCHED_ID());
		thrd.setSCHEDULED_DATE(report.getNEXT_RUN_DATE());
		thrd.setReportContent(report.getJRXML_FILE());
		if(isSybase!=1 && BigDecimal.ONE.equals(report.getIS_FCR_YN()))
		{
		    try
		    {
			//construct the jrxml dynamically
			IRP_AD_HOC_REPORTCO tempRepCO=new IRP_AD_HOC_REPORTCO();
			tempRepCO.setREPORT_NAME(report.getREPORT_NAME());
			tempRepCO.setJRXML_FILE(report.getJRXML_FILE());
			tempRepCO.setFTR_OPT_PROG_REF(report.getREPORT_REF());
			thrd.setReportContent(commonReportingBO.returnDynamicFcrJrxml(tempRepCO, null, parameters));
			String raFormat = commonReportingBO.retTemplateDispVal(parameters, repParamsCO.getCompanyCode());
			parameters.put(ConstantsCommon.ARG_RA_FORMAT, raFormat);
		    }
		    catch(UnsupportedEncodingException e)
		    {
			log.error(e,"");
		    }
		    catch(Exception e)
		    {
			log.error(e,"");
		    }
		  
		}

		thrd.setPrinterName(report.getPRINTER_NAME());
		thrd.setParamValMap(paramValMap);
		thrd.setReportingPortalURL(getReportingPortalURL());
		thrd.setLanguage(repParamsCO.getRepLanguage());
		if(report.getREPORT_REF()!=null)
		{
		    thrd.setFTR_PROG_REF(report.getREPORT_REF());
		}
		thrd.run();
	    }
	}
    }

    @Override
    public boolean isTimerRunning()
    {
	return timer != null;
    }

    /**
     * Method that returns the argument value
     * @param argumentValue
     * @param argumentType
     * @param schedDateTrans
     * @param sysDateTrans
     * @param report
     * @param isMultiSel
     * @return
     */
    private Object retArgument(Object argumentValue, String argumentType, String schedDateTrans, String sysDateTrans,
	    IRP_REPORT_SCHEDULECO report, BigDecimal isMultiSel) throws BaseException
    {
	if(BigDecimal.ONE.equals(isMultiSel))
	{
	    StringBuffer sb = new StringBuffer();
	    if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(argumentType))
	    {
		ArrayList<BigDecimal> lst = new ArrayList<BigDecimal>();
		String[] tab = ((String) argumentValue).split(",");
		for(int i = 0; i < tab.length; i++)
		{
		    sb.append(tab[i].trim());
		    lst.add(new BigDecimal(sb.toString()));
		    sb = new StringBuffer();
		}
		return lst;
	    }
	    else if(ConstantsCommon.PARAM_TYPE_VARCHAR2.equals(argumentType))
	    {
		String[] tab = ((String) argumentValue).split(",");
		for(int i = 0; i < tab.length; i++)
		{
		    // removing starting " and ending "
		    // replacing "" with "
		    sb.append(tab[i].trim().substring(1, tab[i].length() - 1).replace("\"\"", "\""));
		    tab[i] = sb.toString();
		    sb = new StringBuffer();
		}
		return Arrays.asList(tab);
	    }
	    else if(ConstantsCommon.PARAM_TYPE_DATE.equals(argumentType))
	    {
		ArrayList<Date> lst = new ArrayList<Date>();
		String[] tab = ((String) argumentValue).split(",");
		for(int i = 0; i < tab.length; i++)
		{
		    sb.append(tab[i].trim().replace("\"", "")); 
		    Date date = DateUtil.parseDate(sb.toString(), RepConstantsCommon.SCHED_DATE_FRMT);
		    lst.add(date);
		    sb=new StringBuffer();
		}
		return lst;
	    }
	}
	else
	{
	    if(RepConstantsCommon.SCHED_STRING.equalsIgnoreCase(argumentType)
		    || ConstantsCommon.PARAM_TYPE_VARCHAR2.equalsIgnoreCase(argumentType))
	    {
		return argumentValue.toString();
	    }
	    else if(RepConstantsCommon.SCHED_NUMERIC.equalsIgnoreCase(argumentType) || ConstantsCommon.PARAM_TYPE_NUMBER.equalsIgnoreCase(argumentType))
	    {
		return new BigDecimal(argumentValue.toString());
	    }
	    else if(ConstantsCommon.COLUMN_DATE_TYPE.equalsIgnoreCase(argumentType))
	    {
		try
		{
		    return DateUtil.parseDate(argumentValue.toString(), RepConstantsCommon.SCHED_DATE_FRMT);
		}
		catch(BaseException e)
		{
		    log.error(e, e.getMessage());
		}

	    }
	    else if(ConstantsCommon.datetime.equals(argumentType))
	    {
		try
		{
		    Date dt;
		    if(sysDateTrans != null && sysDateTrans.equals(argumentValue.toString()))
		    {
			dt = commonLibBO.returnSystemDateWithTime();
		    }
		    else if(schedDateTrans != null && schedDateTrans.equals(argumentValue.toString()))
		    {
			dt = report.getNEXT_RUN_DATE();
		    }
		    else
		    {
			String dateFrmt = DateUtil.getDatePattern(argumentValue.toString());
			dt = DateUtil.parseDate(argumentValue.toString(), dateFrmt);
		    }
		    return new java.sql.Timestamp(dt.getTime());
		}
		catch(BaseException e)
		{
		    log.error(e, "");
		}
	    }
	}
	return null;
    }

    /*This method will return the session value based on the report language or the session langhuage*/
    private String retSessionVal(String sessionParamName, ReportParamsCO repParamsCO, BigDecimal argSource)
    {
	String paramSessionVal = "";
	CURRENCIESVOKey currVOKey;
	CURRENCIESVO curVO = null;
	BRANCHESVOKey brVOKey;
	BRANCHESVO brVO = null;
	try
	{
	    if((argSource.equals(ConstantsCommon.SESSION_ARG_SOURCE) && ConstantsCommon.LANGUAGE_ARABIC
		    .equals(repParamsCO.getLanguage()))
		    || (argSource.equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE)))
	    {
		if(ConstantsCommon.COMP_NAME_EXP_VAR.equals(sessionParamName))
		{
		    if(argSource.equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE)
			    && ConstantsCommon.LANGUAGE_ENGLISH.equals(repParamsCO.getRepLanguage()))
		    {
			paramSessionVal = (String) PathPropertyUtil.returnProperty(repParamsCO,
				ConstantsCommon.COMP_NAME_EXP_VAR);
		    }
		    else
		    {
			paramSessionVal = (String) PathPropertyUtil.returnProperty(repParamsCO,
				ConstantsCommon.COMP_AR_NAME_SESSION);
		    }
		}
		else if(ConstantsCommon.BASE_CURR_NAME_EXP_VAR.equals(sessionParamName))
		{
		    currVOKey = new CURRENCIESVOKey();
		    currVOKey.setCOMP_CODE(repParamsCO.getCompanyCode());
		    currVOKey.setCURRENCY_CODE(repParamsCO.getBaseCurrencyCode());
		    curVO = commonLibBO.returnCurrency(currVOKey);
		    if(argSource.equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE)
			    && ConstantsCommon.LANGUAGE_ENGLISH.equals(repParamsCO.getRepLanguage()))
		    {
			paramSessionVal = curVO.getBRIEF_DESC_ENG();
		    }
		    else
		    {
			paramSessionVal = curVO.getBRIEF_DESC_ARAB();
		    }
		}
		else if(ConstantsCommon.BRANCH_NAME_EXP_VAR.equals(sessionParamName))
		{
		    brVOKey = new BRANCHESVOKey();
		    brVOKey.setCOMP_CODE(repParamsCO.getCompanyCode());
		    brVOKey.setBRANCH_CODE(repParamsCO.getBranchCode());
		    brVO = commonLibBO.returnBranch(brVOKey);
		    if(argSource.equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE)
			    && ConstantsCommon.LANGUAGE_ENGLISH.equals(repParamsCO.getRepLanguage()))
		    {
			paramSessionVal = brVO.getBRIEF_DESC_ENG();
		    }
		    else
		    {
			paramSessionVal = brVO.getBRIEF_DESC_ARAB();
		    }
		}
		else
		{
		    paramSessionVal = (String) PathPropertyUtil.returnProperty(repParamsCO, sessionParamName);
		}
	    }
	    else
	    {
		paramSessionVal = (String.valueOf(PathPropertyUtil.returnProperty(repParamsCO, sessionParamName)));
	    }

	}
	catch(Exception e)
	{
	    log.error(e, " error in timerScheduleBO.retSessionVal");
	}
	return paramSessionVal;
    }
    
}
