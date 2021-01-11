package com.path.bo.reporting.ftr.scheduler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.customization.button.ButtonCustomizationRmiCallerBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.bo.reporting.ftr.scheduler.SchedulerBO;
import com.path.dao.reporting.ftr.scheduler.SchedulerDAO;
import com.path.dbmaps.vo.IRP_REPORT_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_PARAMSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_RECEIVERSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_SESSION_PARAMVO;
import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_SCHED_DETAILSVOKey;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.remote.RmiServiceCaller;
import com.path.proc.bo.procservices.ProcServiceWrapperBO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FCR_REPORTSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULESC;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_LOGCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_LOG_DETAILSCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSCO;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSSC;
import com.path.vo.reporting.scheduler.IRP_REP_SCHED_MAIL_GROUP_BYCO;
import com.path.vo.reporting.scheduler.IRP_REP_SCHED_MAIL_GROUP_BYSC;
import com.path.vo.reporting.scheduler.IRP_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_SCHEDULESC;
import com.path.vo.reporting.scheduler.IRP_SCHED_DETAILSCO;

public class SchedulerBOImpl extends BaseBO implements SchedulerBO
{
    private SchedulerDAO schedulerDAO;
    private FcrBO fcrBO;
    private DesignerBO designerBO;
    private SchedulerUtilitiesImpl schedulerUtilities;

    public void setDesignerBO(DesignerBO designerBO)
    {
        this.designerBO = designerBO;
    }

    public void setFcrBO(FcrBO fcrBO)
    {
        this.fcrBO = fcrBO;
    }

    public void setSchedulerUtilities(SchedulerUtilitiesImpl schedulerUtilities)
    {
	this.schedulerUtilities = schedulerUtilities;
    }

    public SchedulerDAO getSchedulerDAO()
    {
	return schedulerDAO;
    }

    public void setSchedulerDAO(SchedulerDAO schedulerDAO)
    {
	this.schedulerDAO = schedulerDAO;
    }

    @Override
    public int retReportSchedulesCount(IRP_REPORT_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.retReportSchedulesCount(sc);
    }

    @Override
    public List<IRP_REPORT_SCHEDULECO> findReportSchedules(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	return schedulerDAO.findReportSchedules(sc);
    }

    @Override
    public IRP_REPORT_SCHEDULECO findSingleReportSchedule(IRP_REPORT_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.findSingleReportSchedule(sc);
    }

    @Override
    public int findSchedulesCount(IRP_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.findSchedulesCount(sc);
    }

    @Override
    public List<IRP_SCHEDULECO> findSchedules(IRP_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.findSchedules(sc);
    }

    @Override
    public IRP_SCHEDULECO findSingleSchedule(IRP_SCHEDULESC sc) throws BaseException
    {
	
    sc.setSCHED_TYPE_LOV_TYPE_ID(RepConstantsCommon.SCHED_TYPE);
    sc.setAlertTypeValueCode(RepConstantsCommon.ALERT_TYPE_LOV_CODE);
	int count = schedulerDAO.retCountAlertEventType(sc);
    
	sc.setAlertTypeSchedule(new BigDecimal(count));
	
	IRP_SCHEDULECO co;
	
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
	co.setScheduleDetails(schedulerDAO.findScheduleDetails(sc));
	return co;
    }

    @Override
    public List<IRP_REPORT_SCHED_LOGCO> findReportByRunningStatus(IRP_REPORT_SCHED_PARAMSSC sc) throws BaseException
    {
	return schedulerDAO.findReportByRunningStatus(sc);
    }

    @Override
    public int findReportByRunningStatusCount(IRP_REPORT_SCHED_PARAMSSC sc) throws BaseException
    {
	return schedulerDAO.findReportByRunningStatusCount(sc);
    }

    public BigDecimal insertSched(IRP_SCHEDULECO co) throws BaseException
    {
	Integer zId = genericDAO.insert(co.getSCHED_VO());
    	return new BigDecimal(zId);
    }

    public void updateSchedule(IRP_SCHEDULECO co) throws BaseException
    {
	Integer row = genericDAO.update(co.getSCHED_VO());
	if(row == null || row < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
    }

    public void deleteSchedule(IRP_SCHEDULEVO vo) throws BaseException
    {
	genericDAO.delete(vo);
    }

    public void deleteReportSchedule(IRP_REPORT_SCHEDULEVO vo) throws BaseException
    {
	schedulerDAO.deleteReportSchedule(vo);
    }

    public void insertScheduleDetail(IRP_SCHED_DETAILSVOKey key) throws BaseException
    {
	schedulerDAO.insertScheduleDetail(key);
    }

    public void deleteScheduleDetails(IRP_SCHEDULEVO vo) throws BaseException
    {
	schedulerDAO.deleteScheduleDetails(vo);
    }

    @Override
    public void insertReportSchedule(IRP_REPORT_SCHEDULEVO vo,
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList,
	    HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap)
	    throws BaseException
    {
	if(!StringUtil.isNotEmpty(vo.getREPORT_REF()))
	{
	    vo.setREPORT_REF(RepConstantsCommon.ZERO);
	}
	schedulerDAO.insertReportSchedule(vo);
	// insert grpByFeMail
	insertGrpByFeMail(repScheMailGrpByList, vo, false);

	// insert usersList
	insertMailUsersList(repSchedMailUsrsMap, vo, false);

	// apply audit
	auditBO.callAudit(vo, vo, vo.getAuditRefCO());
	auditBO.insertAudit(vo.getAuditRefCO());

    }

    @Override
    public void updateReportSchedule(IRP_REPORT_SCHEDULEVO vo,
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList,
	    HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap)
	    throws BaseException
    {
	if(!StringUtil.isNotEmpty(vo.getREPORT_REF()))
	{
	    vo.setREPORT_REF(RepConstantsCommon.ZERO);
	}
	genericDAO.update(vo);
	// insert grpByFeMail
	insertGrpByFeMail(repScheMailGrpByList, vo, true);

	// insert usersList
	insertMailUsersList(repSchedMailUsrsMap, vo, true);

	// apply audit
	auditBO.callAudit(vo, vo, vo.getAuditRefCO());
	auditBO.insertAudit(vo.getAuditRefCO());
    }

    public void insertMailUsersList(
	    HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap,
	    IRP_REPORT_SCHEDULEVO vo, boolean isUpdate) throws BaseException
    {
	LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> toMap = repSchedMailUsrsMap.get("1");
	LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> ccMap = repSchedMailUsrsMap.get("2");
	LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO> bccMap = repSchedMailUsrsMap.get("3");
	IRP_REP_SCHED_MAIL_RECEIVERSVO recVO;
	// delete
	if(isUpdate)
	{
	    recVO = new IRP_REP_SCHED_MAIL_RECEIVERSVO();
	    recVO.setREPORT_ID(vo.getREPORT_ID());
	    recVO.setSCHED_ID(vo.getSCHED_ID());
	    if(toMap != null)
	    {
		recVO.setRECEIVER_TYPE(BigDecimal.ONE);
		schedulerDAO.deleteMailUsersList(recVO);
	    }
	    if(ccMap != null)
	    {
		recVO.setRECEIVER_TYPE(new BigDecimal(2));
		schedulerDAO.deleteMailUsersList(recVO);
	    }
	    if(bccMap != null)
	    {
		recVO.setRECEIVER_TYPE(new BigDecimal(3));
		schedulerDAO.deleteMailUsersList(recVO);
	    }
	}
	// insert
	Iterator it;
	if(toMap != null)
	{
	    it = toMap.values().iterator();
	    while(it.hasNext())
	    {
		recVO = (IRP_REP_SCHED_MAIL_RECEIVERSVO) it.next();
		if(!StringUtil.isNotEmpty(recVO.getREPORT_REF()))
		{
		    recVO.setREPORT_REF(RepConstantsCommon.ZERO);
		}
		genericDAO.insert(recVO);
	    }
	}
	if(ccMap != null)
	{
	    it = ccMap.values().iterator();
	    while(it.hasNext())
	    {
		recVO = (IRP_REP_SCHED_MAIL_RECEIVERSVO) it.next();
		if(!StringUtil.isNotEmpty(recVO.getREPORT_REF()))
		{
		    recVO.setREPORT_REF(RepConstantsCommon.ZERO);
		}
		genericDAO.insert(recVO);
	    }
	}
	if(bccMap != null)
	{
	    it = bccMap.values().iterator();
	    while(it.hasNext())
	    {
		recVO = (IRP_REP_SCHED_MAIL_RECEIVERSVO) it.next();
		if(!StringUtil.isNotEmpty(recVO.getREPORT_REF()))
		{
		    recVO.setREPORT_REF(RepConstantsCommon.ZERO);
		}
		genericDAO.insert(recVO);
	    }
	}
    }

    public void insertGrpByFeMail(LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList,
	    IRP_REPORT_SCHEDULEVO vo, boolean isUpdate) throws BaseException
    {
	// delete
	if(isUpdate)
	{
	    IRP_REP_SCHED_MAIL_GROUP_BYSC grpSC = new IRP_REP_SCHED_MAIL_GROUP_BYSC();
	    grpSC.setREPORT_ID(vo.getREPORT_ID());
	    grpSC.setSCHED_ID(vo.getSCHED_ID());
	    schedulerDAO.deleteGrpByFeMail(grpSC);
	}
	// insert
	Iterator it = repScheMailGrpByList.values().iterator();
	IRP_REP_SCHED_MAIL_GROUP_BYCO grpCO;
	while(it.hasNext())
	{
	    grpCO = (IRP_REP_SCHED_MAIL_GROUP_BYCO) it.next();
	    if(!StringUtil.isNotEmpty(grpCO.getMailGrpVO().getREPORT_REF()))
	    {
		grpCO.getMailGrpVO().setREPORT_REF(RepConstantsCommon.ZERO);
	    }
	    genericDAO.insert(grpCO.getMailGrpVO());
	}
    }

    @Override
    public List<IRP_REPORT_SCHED_PARAMSCO> findReportScheduleParams(IRP_REPORT_SCHED_PARAMSSC sc) throws BaseException
    {
	return schedulerDAO.findReportScheduleParams(sc);
    }

    @Override
    public List<IRP_REPORT_SCHEDULECO> selectDueReports(IRP_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.selectDueReports(sc);
    }

    // @Override
    // public Object retArgument(Object argumentValue, String argumentType)
    // throws ParseException
    // {
    // return schedulerDAO.retArgument(argumentValue,argumentType);
    //		
    // }
    @Override
    public void stopTimer()
    {
	schedulerUtilities.stopTimer();
    }

    @Override
    public void startTimer(ReportParamsCO repParamsCO)
    {
	schedulerUtilities.startTimer(repParamsCO);
    }

    @Override
    public void updateNextRunDate(IRP_SCHEDULESC sc) throws BaseException
    {
	schedulerDAO.updateNextRunDate(sc);
    }

    @Override
    public Date retNextRunningDate(IRP_SCHEDULECO co) throws BaseException
    {
	return schedulerDAO.retNextRunningDate(co);
    }

    @Override
    public boolean isTimerRunning()
    {
	return schedulerUtilities.isTimerRunning();
    }

    public void insertSchedParam(IRP_REPORT_SCHED_PARAMSVO paramVO) throws BaseException
    {
	genericDAO.insert(paramVO);
    }

    public void deleteSchedParam(IRP_REPORT_SCHED_PARAMSSC paramSC) throws BaseException
    {
	schedulerDAO.deleteSchedParam(paramSC);
    }

    @Override
    public HashMap retSchedParamValues(IRP_REPORT_SCHED_PARAMSSC paramSC) throws BaseException
    {
	return schedulerDAO.retSchedParamValues(paramSC);
    }

    public void deletSchedLogs(IRP_SCHEDULECO co) throws BaseException
    {
	schedulerDAO.deletSchedLogs(co);

    }

    public int checkSchedIsRunning(IRP_REPORT_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.checkSchedIsRunning(sc);
    }

    public void deleteSchedule(BigDecimal schedId, AuditRefCO refCO) throws BaseException
    {
	IRP_SCHEDULEVO vo = new IRP_SCHEDULEVO();
	vo.setSCHED_ID(schedId);
	IRP_SCHEDULECO co = new IRP_SCHEDULECO();
	co.getSCHED_VO().setSCHED_ID(schedId);

	deleteScheduleDetails(co.getSCHED_VO());
	// delete sched.logs
	deletSchedLogs(co);
	// delete schedParam
	IRP_REPORT_SCHED_PARAMSSC paramSC = new IRP_REPORT_SCHED_PARAMSSC();
	paramSC.setSCHED_ID(schedId);
	deleteSchedParam(paramSC);
	// delete reportSechedule mail grp by
	IRP_REP_SCHED_MAIL_GROUP_BYSC grpSC = new IRP_REP_SCHED_MAIL_GROUP_BYSC();
	grpSC.setSCHED_ID(schedId);
	schedulerDAO.deleteGrpByFeMail(grpSC);
	// delete reportSechedule mail usersLst
	IRP_REP_SCHED_MAIL_RECEIVERSVO recVO = new IRP_REP_SCHED_MAIL_RECEIVERSVO();
	recVO.setSCHED_ID(schedId);
	schedulerDAO.deleteMailUsersList(recVO);
	// delete reportSched
	IRP_REPORT_SCHEDULEVO repSchedVO = new IRP_REPORT_SCHEDULEVO();
	repSchedVO.setSCHED_ID(schedId);
	deleteReportSchedule(repSchedVO);

	// delete schedule
	deleteSchedule(vo);

	// apply audit
	auditBO.callAudit(vo, vo, refCO);
	auditBO.insertAudit(refCO);
    }

    public void deleteReportSchedule(IRP_REPORT_SCHEDULECO repSchedCO, AuditRefCO refCO) throws BaseException
    {
	BigDecimal repId=repSchedCO.getREPORT_ID();
	BigDecimal schedId=repSchedCO.getSCHED_ID();
	// delete reportSechedule mail grp by
	IRP_REP_SCHED_MAIL_GROUP_BYSC grpSC = new IRP_REP_SCHED_MAIL_GROUP_BYSC();
	grpSC.setREPORT_ID(repId);
	grpSC.setSCHED_ID(schedId);
	schedulerDAO.deleteGrpByFeMail(grpSC);

	// delete reportSechedule mail usersLst
	IRP_REP_SCHED_MAIL_RECEIVERSVO recVO = new IRP_REP_SCHED_MAIL_RECEIVERSVO();
	recVO.setSCHED_ID(schedId);
	recVO.setREPORT_ID(repId);
	schedulerDAO.deleteMailUsersList(recVO);

	// delete reportSchedParam
	IRP_REPORT_SCHED_PARAMSSC paramSC = new IRP_REPORT_SCHED_PARAMSSC();
	paramSC.setREPORT_ID(repId);
	paramSC.setSCHED_ID(schedId);
	paramSC.setREPORT_REF(repSchedCO.getREPORT_REF());
	deleteSchedParam(paramSC);
	
	IRP_REPORT_SCHEDULEVO vo = new IRP_REPORT_SCHEDULEVO();
	vo.setREPORT_ID(repId);
	vo.setSCHED_ID(schedId);
	vo.setREPORT_REF(repSchedCO.getREPORT_REF());
	deleteReportSchedule(vo);


	// apply audit
	auditBO.callAudit(vo, vo, refCO);
	auditBO.insertAudit(refCO);
    }

    public void updateSchedParams(IRP_REPORT_SCHED_PARAMSSC paramSC, ArrayList<IRP_REPORT_SCHED_PARAMSVO> paramsList) throws BaseException
    {
	    deleteSchedParam(paramSC);
	    // save the new params
	    if(paramsList != null)
	    {
		saveSchedParams(paramsList);
	    }
    }

    public void saveSchedParams(ArrayList<IRP_REPORT_SCHED_PARAMSVO> paramsList) throws BaseException
    {
	    IRP_REPORT_SCHED_PARAMSVO paramVO;
	    for(int i = 0; i < paramsList.size(); i++)
	    {
		paramVO = paramsList.get(i);
		//checking added for fcr
		if(RepConstantsCommon.OL_ERROR_FCR.equals(paramVO.getPARAM_NAME())
			|| RepConstantsCommon.OS_MESSAGE_FCR.equals(paramVO.getPARAM_NAME())
			|| RepConstantsCommon.BASED_ON_REP_CURRENCY_FCR.equals(paramVO.getPARAM_NAME())
			|| !StringUtil.isNotEmpty(paramVO.getPARAM_VALUE()))
		{
		    continue;
		}
		else
		{
		    insertSchedParam(paramVO);
		}
	    }
	}
    
    public void addSchedule(String mode, IRP_SCHEDULECO schedule, IRP_SCHEDULESC schedulerSC, BigDecimal schedId)
	    throws BaseException
    {
	if(RepConstantsCommon.MODE_EDIT.equals(mode) && !schedule.getSCHED_VO().getSCHED_ID().equals(BigDecimal.ZERO ))
	{
	    schedulerSC.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    IRP_SCHEDULECO oldSchedule = findSingleSchedule(schedulerSC);
	    if (!schedule.getSCHED_VO().getSCHED_TYPE().equals(new BigDecimal("4")))
		{
	    Calendar cal = Calendar.getInstance();
	    if((oldSchedule.getSCHED_VO().getFIRST_RUN_DATE().equals(schedule.getSCHED_VO().getFIRST_RUN_DATE()))
		    && (oldSchedule.getSCHED_VO().getSCHED_FREQUENCY().equals(schedule.getSCHED_VO().getSCHED_FREQUENCY())))
	    {
		cal.setTime(oldSchedule.getSCHED_VO().getNEXT_RUN_DATE());
	    }
	    else
	    {
		cal.setTime(schedule.getSCHED_VO().getFIRST_RUN_DATE());
	    }

	    if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_MONTH) || schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_ENDOFMONTH))
	    {
		if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_MONTH))
		{
		    cal.add(Calendar.MONTH, -1);
		}
		schedule.getSCHED_VO().setNEXT_RUN_DATE(cal.getTime());
		schedule.getSCHED_VO().setNEXT_RUN_DATE(retNextRunningDate(schedule));
	    }
	    else if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_DAILY))
	    {// same as for month (check previous)
		cal.add(Calendar.DATE, -1);
		schedule.getSCHED_VO().setNEXT_RUN_DATE(cal.getTime());
		schedule.getSCHED_VO().setNEXT_RUN_DATE(retNextRunningDate(schedule));
	    }
	    else
	    {
		// schedule.setNEXT_RUN_DATE(schedule.getFIRST_RUN_DATE());
		schedule.getSCHED_VO().setNEXT_RUN_DATE(cal.getTime());
	    }
		}
	    updateSchedule(schedule);
	    deleteScheduleDetails(schedule.getSCHED_VO());

	    // apply audit
	    // convert co to vo
	    IRP_SCHEDULEVO schedVO = new IRP_SCHEDULEVO();
	    schedVO.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    schedVO.setEND_OF_MONTH(schedule.getSCHED_VO().getEND_OF_MONTH());
	    schedVO.setSCHED_NAME(schedule.getSCHED_VO().getSCHED_NAME());
	    if (!schedule.getSCHED_VO().getSCHED_TYPE().equals(new BigDecimal("4")))
		{
			schedVO.setFIRST_RUN_DATE(schedule.getSCHED_VO().getFIRST_RUN_DATE());
		}
	    schedVO.setNEXT_RUN_DATE(schedule.getSCHED_VO().getNEXT_RUN_DATE());
	    schedVO.setSCHED_EXPIRY_DATE(schedule.getSCHED_VO().getSCHED_EXPIRY_DATE());
	    schedVO.setSCHED_FREQUENCY(schedule.getSCHED_VO().getSCHED_FREQUENCY());
	    schedVO.setDATE_UPDATED(schedule.getSCHED_VO().getDATE_UPDATED());
	    schedVO.setFREQUENCY_HOUR(schedule.getSCHED_VO().getFREQUENCY_HOUR());
	    schedVO.setFREQUENCY_MINUTE(schedule.getSCHED_VO().getFREQUENCY_MINUTE());
	    auditBO.callAudit(schedule.getAuditRefCO().getAuditObj(), schedVO, schedule.getAuditRefCO());
	    auditBO.insertAudit(schedule.getAuditRefCO());

	}
	else
	{
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(schedule.getSCHED_VO().getFIRST_RUN_DATE());
	    if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_MONTH) || schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_ENDOFMONTH))
	    {
		if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_MONTH))
		{
		    cal.add(Calendar.MONTH, -1);
		}
		schedule.getSCHED_VO().setNEXT_RUN_DATE(cal.getTime());
		schedule.getSCHED_VO().setNEXT_RUN_DATE(retNextRunningDate(schedule));
	    }
	    else if(schedule.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_DAILY))
	    {
		cal.add(Calendar.DATE, -1);
		schedule.getSCHED_VO().setNEXT_RUN_DATE(cal.getTime());
		schedule.getSCHED_VO().setNEXT_RUN_DATE(retNextRunningDate(schedule));
	    }
	    else
	    {
		if (!schedule.getSCHED_VO().getSCHED_TYPE().equals(new BigDecimal("4")))
		{
		schedule.getSCHED_VO().setNEXT_RUN_DATE(schedule.getSCHED_VO().getFIRST_RUN_DATE());
		}
	    }

	    schedule.getSCHED_VO().setSCHED_ID(schedId);
	    insertSched(schedule);
	    // apply Audit
	    AuditRefCO refCO = schedule.getAuditRefCO();
	    auditBO.callAudit(null, schedule.getSCHED_VO(), refCO);

	    //schedule.getSCHED_VO().setSCHED_ID(newID);
	}
	if(schedule.getScheduleDetails() == null)
	{
	    schedule.setScheduleDetails(new ArrayList());
	}
	for(IRP_SCHED_DETAILSCO co : schedule.getScheduleDetails())
	{
	    co.setSCHED_ID(schedule.getSCHED_VO().getSCHED_ID());
	    insertScheduleDetail(co);
	}

    }

    public int retMailFeGroupByCnt(IRP_REP_SCHED_MAIL_GROUP_BYSC sc) throws BaseException
    {
	return schedulerDAO.retMailFeGroupByCnt(sc);
    }

    public List<IRP_REP_SCHED_MAIL_GROUP_BYCO> retMailFeGroupByList(IRP_REP_SCHED_MAIL_GROUP_BYSC sc)
	    throws BaseException
    {
	return schedulerDAO.retMailFeGroupByList(sc);
    }

    public int retSchedMailUsersCnt(IRP_REP_SCHED_MAIL_RECEIVERSVO vo) throws BaseException
    {
	return schedulerDAO.retSchedMailUsersCnt(vo);
    }

    public List<IRP_REP_SCHED_MAIL_RECEIVERSVO> retSchedMailUsersList(IRP_REP_SCHED_MAIL_RECEIVERSVO vo)
	    throws BaseException
    {
	return schedulerDAO.retSchedMailUsersList(vo);
    }

    public void deleteReportsSchedules(List<BigDecimal> reportIds) throws BaseException
    {
	//delete from IRP_REPORT_SCHED_PARAMS
	schedulerDAO.deleteRepsSchedParams(reportIds);
	
	// delete from irp_report_schedule
	schedulerDAO.deleteRepSchedList(reportIds);
	// delete from IRP_REP_SCHED_MAIL_GROUP_BY
	schedulerDAO.deleteRepSchedGryByFields(reportIds);

	// delete from IRP_REP_SCHED_MAIL_RECEIVERS
	schedulerDAO.deleteRepSchedReceivers(reportIds);
    }

    public int retRepSchedMailLogDetCnt(IRP_REPORT_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.retRepSchedMailLogDetCnt(sc);
    }

    public List<IRP_REPORT_SCHED_LOG_DETAILSCO> retRepSchedMailLogDetLst(IRP_REPORT_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.retRepSchedMailLogDetLst(sc);
    }

    public List<BigDecimal> retSchedUsage(List<BigDecimal> reportsId) throws BaseException
    {
	return schedulerDAO.retSchedUsage(reportsId);
    }

    public  HashMap<String ,IRP_REP_ARGUMENTSCO> retRepMainArgsMap(IRP_REPORT_SCHED_PARAMSSC paramSC)throws BaseException
    {
	return schedulerDAO.retRepMainArgsMap(paramSC);
    }
    
    public void deleteRepSchedArg(IRP_REPORT_SCHED_PARAMSSC sc) throws BaseException
    {
	 schedulerDAO.deleteRepSchedArg(sc);
    }
    
    /**
     * Method that checks if a report has a related scheduler
     */
    public int checkReportHasSched(String progRef) throws BaseException
    {
	return schedulerDAO.checkReportHasSched(progRef);
    }

    /**
     * Method that returns the list of fcr dynamic reports along with the
     * report_id retrieved from irp_ad_hoc_report
     */
    public List<FTR_OPTCO> retListDynReports(CommonDetailsSC sc) throws BaseException
    {
	List<FTR_OPTCO> lstDynReports = fcrBO.loadFcrList(sc);
	FTR_OPTCO co;
	IRP_AD_HOC_REPORTSC reportSC = new IRP_AD_HOC_REPORTSC();
	reportSC.setCOMP_CODE(sc.getCOMP_CODE());
	IRP_FCR_REPORTSCO fcrRep;
	for(int i = 0; i < lstDynReports.size(); i++)
	{
	    co = lstDynReports.get(i);
	    reportSC.setPROG_REF(co.getFtrOptVO().getPROG_REF());
	    reportSC.setAPP_NAME(co.getFtrOptVO().getAPP_NAME());
	    // get the jrxml progref
	    fcrRep = fcrBO.retFcrRep(reportSC);
	    if(fcrRep==null)
	    {
		continue;
	    }
	    reportSC.setPROG_REF(fcrRep.getProgRef());
	   
		//return the mainFcrReportId
		 IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
		 repSC.setAPP_NAME(ConstantsCommon.REP_APP_NAME);
		 repSC.setPROG_REF(ConstantsCommon.FCR_MAIN_REPORT_REF);
		 IRP_AD_HOC_REPORTCO repIdCO=designerBO.retRepIdByProgRef(repSC);
		 co.setREPORT_ID(repIdCO.getREPORT_ID());
	    
	}
	return lstDynReports;
    }
    
    /*
     * Method that returns the report object related to the given jrxmlId
     */
    public IRP_AD_HOC_REPORTCO retFcrRepById(IRP_AD_HOC_REPORTSC sc) throws BaseException
    {
    	IRP_AD_HOC_REPORTCO report = null;
	    BigDecimal jrxmlRepId = BigDecimal.valueOf(designerBO.selectReportByRef(sc));
		try {
				report = designerBO.returnReportById(jrxmlRepId);
			} 
		catch (Exception e) 
		{
			log.error(e, "");
		}
	    return report;
	}
    
    @Override
    public ArrayList<Object> retRepSchedByRepId(IRP_REPORT_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.retRepSchedByRepId(sc);
    }
    
    @Override
    public ArrayList<Object> retRepSchedParamByRepId(IRP_REPORT_SCHEDULESC sc)throws BaseException
    {
	return schedulerDAO.retRepSchedParamByRepId(sc);
    }
    
    @Override
    public ArrayList<Object> retRepSchedMailGroupByRepId(IRP_REPORT_SCHEDULESC sc)throws BaseException
    {
	return schedulerDAO.retRepSchedMailGroupByRepId(sc);
    }
    
    @Override
    public ArrayList<Object> retRepSchedMailRecvByRepId(IRP_REPORT_SCHEDULESC sc) throws BaseException
    {
	return schedulerDAO.retRepSchedMailRecvByRepId(sc);
    }
    @Override
    public void startEngine()throws BaseException
    {
	ReportParamsCO repParamsCO;
	try
	{
	    // retrieve the session values from the table IRP_REP_SCHED_SESSION_PARAM
	    HashMap<String,IRP_REP_SCHED_SESSION_PARAMVO> sessionMap = returnSchedSessionParamsVal();
	    repParamsCO =fillSessionVal(sessionMap);
	    startTimer(repParamsCO);
	}
	catch(Exception e)
	{
	    //in case the table is not created at the level of the DB
	    repParamsCO =fillSessionVal(new HashMap<String,IRP_REP_SCHED_SESSION_PARAMVO>());
	    startTimer(repParamsCO);
	    log.error(e,"");
	}
	
    }
    public ReportParamsCO fillSessionVal(HashMap<String,IRP_REP_SCHED_SESSION_PARAMVO> sessionMap)throws BaseException
    {
	ReportParamsCO repParamsCO = new ReportParamsCO();
	try
	{
	    IRP_REP_SCHED_SESSION_PARAMVO sessVO;
	    Class<?> sessType;
	    String sessName;
	    String sessVal;
	    ArrayList<String> SCHED_SESSION_NAME_CONST=RepConstantsCommon.SCHED_SESSION_NAME;
	  //check if not all session parameters exist in the DB
	    for(int i=0;i<SCHED_SESSION_NAME_CONST.size();i++)
	    {
		sessName=SCHED_SESSION_NAME_CONST.get(i);
		sessType = PropertyUtils.getPropertyType(repParamsCO, sessName); 
		sessVO =sessionMap.get(sessName);
		if(sessVO ==null)
		{
		    log.debug(">>>>>>>****>>>>>>>>> The session attribute \""+sessName+"\" does not exist in the table IRP_REP_SCHED_SESSION_PARAM");
		    if(sessType.getCanonicalName().contains(RepConstantsCommon.STRING_STR))
		    {
			PropertyUtils.setProperty(repParamsCO, sessName,"null");
		    }
		    else if(sessType.getCanonicalName().contains(RepConstantsCommon.DATE_STR))
		    {
			PropertyUtils.setProperty(repParamsCO, sessName,DateUtil.parseDate("01/01/1900", "dd/MM/yyyy"));
		    }
		    else if(sessType.getCanonicalName().contains(RepConstantsCommon.INT_STR))
		    {
			PropertyUtils.setProperty(repParamsCO, sessName,0);
		    }
		    else
		    {
			PropertyUtils.setProperty(repParamsCO, sessName,new BigDecimal(-1));
		    } 
		}
		else
		{
		    sessVal = sessVO.getSESSION_VAL();
		    if(sessVal==null)
		    {
			 log.debug(">>>>>>>****>>>>>>>>> The session attribute \""+sessName+"\" is set to null in the table IRP_REP_SCHED_SESSION_PARAM");
		    }
		    if(sessType.getCanonicalName().contains(RepConstantsCommon.STRING_STR))
		    {
			PropertyUtils.setProperty(repParamsCO, sessName, sessVal==null?"null":sessVal);
		    }
		    else if(sessType.getCanonicalName().contains(RepConstantsCommon.DATE_STR))
		    {
			PropertyUtils.setProperty(repParamsCO, sessName, sessVal == null ? DateUtil.parseDate("01/01/1900", "dd/MM/yyyy"): DateUtil.parseDate(sessVal, "dd/MM/yyyy"));
		    }
		    else if(sessType.getCanonicalName().contains(RepConstantsCommon.INT_STR))
		    {
			PropertyUtils.setProperty(repParamsCO, sessName, sessVal == null ? 0 : Integer.parseInt(sessVal));
		    }
		    else
		    {
			PropertyUtils.setProperty(repParamsCO, sessName, sessVal == null ?new BigDecimal(-1) : new BigDecimal(sessVal));
		    }
		}
	    }
	    /*fill the attributes related to the group separators and decimal separators that will be read from the constants common and not from the DB since
	    they will be related to the user that will recieve the report and not the default parametrization of the DB */
	    String[] grpDecSepArr = NumberUtil.returnGroupDecimalSep();
	    HashMap<String, Object> userNbFormats = new HashMap<String, Object>();
	    userNbFormats.put("groupSepa", grpDecSepArr[0]);
	    userNbFormats.put("decimalSepa",grpDecSepArr[1]);
	    repParamsCO.setUserNbFormats(userNbFormats);
	}
	catch(Exception e)
	{
	    log.error(e,"");
	}
	return repParamsCO;
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

    @Override
    public HashMap<String,IRP_REP_SCHED_SESSION_PARAMVO> returnSchedSessionParamsVal() throws BaseException
    {
	return schedulerDAO.returnSchedSessionParamsVal();
    }
    
	@Override
	public List<HashMap<String,Object>> retBatchesList(IRP_AD_HOC_REPORTSC sc) throws BaseException
	{
	    ProcServiceWrapperBO procServiceWrapperBO = returnProcServiceBean();
	    if(procServiceWrapperBO==null)
	    {
		return new ArrayList<HashMap<String,Object>>();
	    }
	    return procServiceWrapperBO.returnBatchList(PathPropertyUtil.convertToMap(sc));
	    
	}

	@Override
	public int retBatchesListCount(IRP_AD_HOC_REPORTSC sc) throws BaseException
	{
	    ProcServiceWrapperBO procServiceWrapperBO = returnProcServiceBean();
	    if(procServiceWrapperBO==null)
	    {
		return 0;
	    }
	    return procServiceWrapperBO.returnBatchListCount(PathPropertyUtil.convertToMap(sc));
	}
    
	@Override
	public List<Map<String, Object>> retEventList(IRP_SCHEDULESC sc) throws BaseException 
	{
		List<Map<String, Object>> values = retAlertEventList(sc);
		if(values == null || values.size() <= 0)
		{
			return new ArrayList<>();
		}
		else
		{
			return values;
		}
	}
	
	@Override
	public int retEventListCount(IRP_SCHEDULESC sc) throws BaseException
	{
		ButtonCustomizationRmiCallerBO buttonCustomizationRmiCallerBO = returnAlertServiceBean();
	    Map<String, Object> resultMap = new HashMap<>();
	    if(buttonCustomizationRmiCallerBO==null)
	    {
	    	return 0;
	    }
	    resultMap = (Map) buttonCustomizationRmiCallerBO.executeBoMethod("eventsServiceBO", "returnIndividualEventListCount", PathPropertyUtil.convertToMap(sc));
	    
	    return (int) resultMap.get("count");
	}
	
	private List<Map<String,Object>> retAlertEventList(IRP_SCHEDULESC reportSC) throws BaseException
	{
		ButtonCustomizationRmiCallerBO buttonCustomizationRmiCallerBO = returnAlertServiceBean();
	    Map<String, Object> resultMap = new HashMap<>();
	    if(buttonCustomizationRmiCallerBO==null)
	    {
	    	return new ArrayList<Map<String,Object>>();
	    }
	    resultMap = (Map) buttonCustomizationRmiCallerBO.executeBoMethod("eventsServiceBO", "returnIndividualEventList", PathPropertyUtil.convertToMap(reportSC));
	    
	    return (List<Map<String, Object>>) resultMap.get("eventList");
	}
	
	private ButtonCustomizationRmiCallerBO returnAlertServiceBean() throws BaseException
    {
    	ButtonCustomizationRmiCallerBO bo = null;
		try
		{
		    String procServiceUrl = PathPropertyUtil.returnPathPropertyFromFile("PathAlertRemoting.properties", "alrt.serviceURL");
		    if(procServiceUrl == null)
			{
			    log.error("Invalid Alert URL");
			    return null;
			}
		    bo = (ButtonCustomizationRmiCallerBO) RmiServiceCaller.returnRmiInterface(
		    		procServiceUrl.concat("buttonCustomizationRmiCallerBOService"), ButtonCustomizationRmiCallerBO.class);
		}
		catch(Exception e)
		{
		    throw new BaseException(e);
		}
		return bo;
    }
}
