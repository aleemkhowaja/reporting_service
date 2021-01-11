package com.path.dao.reporting.ftr.scheduler.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.ftr.scheduler.SchedulerDAO;
import com.path.dbmaps.vo.CIFVO;
import com.path.dbmaps.vo.CIFVOKey;
import com.path.dbmaps.vo.IRP_REPORT_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOG_DETAILSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_RECEIVERSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_SESSION_PARAMVO;
import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_SCHED_DETAILSVOKey;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
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

public class SchedulerDAOImpl extends BaseDAO implements SchedulerDAO
{

    
    public int retReportSchedulesCount(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "SCHEDULER.ReportScheduleResultMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("SCHEDULER.findReportSchedulesCount", sc));
	return nb;
    }

    
    public int findLogCount(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	int nb;
	nb = ((Integer) getSqlMap().queryForObject("SCHEDULER.findLogCount", sc));
	return nb;
    }

    
    public void insertLOG(IRP_REPORT_SCHED_LOGCO co) throws DAOException
    {
	getSqlMap().insert("SCHEDULER.insertLOG", co);
    }

    
    public void updateLOG(IRP_REPORT_SCHED_LOGVO vo) throws DAOException
    {
	getSqlMap().update("SCHEDULER.updateLOG", vo);
    }

    
    public void updateNextRunDate(IRP_SCHEDULESC sc) throws DAOException
    {
	getSqlMap().update("SCHEDULER.updateNextRunDate", sc);
    }

    
    public Date retNextRunningDate(IRP_SCHEDULECO co) throws DAOException
    {
	try
	{
	    Calendar cal = Calendar.getInstance();
	    Date dt = co.getSCHED_VO().getNEXT_RUN_DATE();
	    cal.setTime(dt);
	    int[] months = new int[12];
	    int[] days = new int[7];
	    if(co.getSCHED_VO().getSCHED_FREQUENCY().equals("Y"))
	    {
		cal.add(Calendar.YEAR, 1);
	    }
	    else if(co.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_ENDOFMONTH) || co.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_MONTH))
	    {
		for(IRP_SCHED_DETAILSCO schedDet : co.getScheduleDetails())
		{
		    if(schedDet.getFREQUENCY_OCCURENCE().equals(BigDecimal.ONE))
		    {
			months[0] = 1;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(2)))
		    {
			months[1] = 2;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(3)))
		    {
			months[2] = 3;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(4)))
		    {
			months[3] = 4;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(5)))
		    {
			months[4] = 5;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(6)))
		    {
			months[5] = 6;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(7)))
		    {
			months[6] = 7;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(8)))
		    {
			months[7] = 8;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(9)))
		    {
			months[8] = 9;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(BigDecimal.TEN))
		    {
			months[9] = 10;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(11)))
		    {
			months[10] = 11;
		    }
		    else if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(12)))
		    {
			months[11] = 12;
		    }
		}

		if(co.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_MONTH))
		{
		    cal.add(Calendar.MONTH, 1);
		}
		int currentMonth = cal.get(Calendar.MONTH) + 1;
		boolean monthFound = false;
		for(int j = 1; j < 12; j++)
		{
		    for(int i = 0; i < 12; i++)
		    {
			if(months[i] == currentMonth)
			{
			    monthFound = true;
			    break;
			}
		    }
		    if(monthFound)
		    {
			break;
		    }

		    cal.add(Calendar.MONTH, 1);
		    currentMonth = cal.get(Calendar.MONTH) + 1;

		}
		if(co.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_ENDOFMONTH) && monthFound)
		{
		    cal.set(Calendar.DATE, 1);
		    cal.add(Calendar.MONTH, 1);
		    cal.add(Calendar.DATE, -1);
		}

	    }// frequency=monthly or end of month
	    else if(co.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_DAILY))
	    {
		for(IRP_SCHED_DETAILSCO schedDet : co.getScheduleDetails())
		{
		    if(schedDet.getFREQUENCY_OCCURENCE().equals(BigDecimal.ONE))
		    {
			days[0] = 1;
		    }
		    if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(2)))
		    {
			days[1] = 2;
		    }
		    if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(3)))
		    {
			days[2] = 3;
		    }
		    if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(4)))
		    {
			days[3] = 4;
		    }
		    if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(5)))
		    {
			days[4] = 5;
		    }
		    if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(6)))
		    {
			days[5] = 6;
		    }
		    if(schedDet.getFREQUENCY_OCCURENCE().equals(new BigDecimal(7)))
		    {
			days[6] = 7;
		    }
		}

		boolean dayFound = false;
		cal.add(Calendar.DATE, 1);
		int currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
		for(int j = 1; j < 7; j++)
		{
		    for(int i = 0; i < 7; i++)
		    {
			if(days[i] == currentDayOfWeek)
			{
			    dayFound = true;
			    break;
			}
		    }
		    if(dayFound)
		    {
			break;
		    }

		    cal.add(Calendar.DATE, 1);
		    currentDayOfWeek = cal.get(Calendar.DAY_OF_WEEK);

		}
	    }
	    else if(co.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_ONCE))
	    {
		cal.setTime(co.getSCHED_VO().getSCHED_EXPIRY_DATE());
	    }
	    else if(co.getSCHED_VO().getSCHED_FREQUENCY().equals(RepConstantsCommon.SCHED_FREQUENCY_HOUR))
	    {	if (co.getSCHED_VO().getFREQUENCY_HOUR() == null)
	    	{
		    co.getSCHED_VO().setFREQUENCY_HOUR(BigDecimal.ZERO);
	    	}
	    	if (co.getSCHED_VO().getFREQUENCY_MINUTE() == null)
	    	{
		    co.getSCHED_VO().setFREQUENCY_MINUTE(BigDecimal.ZERO);
	    	}
		cal.add(Calendar.HOUR ,co.getSCHED_VO().getFREQUENCY_HOUR().intValue());
		cal.add(Calendar.MINUTE,co.getSCHED_VO().getFREQUENCY_MINUTE().intValue());

	    }
	 	
	    return cal.getTime();
	}
	catch(Exception ex)
	{
	    return co.getSCHED_VO().getNEXT_RUN_DATE();
	}
    }

    
    public List<IRP_REPORT_SCHEDULECO> findReportSchedules(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	return getSqlMap().queryForList("SCHEDULER.findReportSchedules", sc,
		sc.getRecToskip(), sc.getNbRec());
    }

    
    public List<IRP_REPORT_SCHED_LOGCO> findReportByRunningStatus(IRP_REPORT_SCHED_PARAMSSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "SCHEDULER.logResultMap");
	if(sc.getSortOrder() == null)
	{
	    sc.setSortOrder("ORDER BY START_DATE DESC ");
	}

	return getSqlMap().queryForList("SCHEDULER.findReportByRunningStatus", sc,
		sc.getRecToskip(), sc.getNbRec());
    }

    
    public int findReportByRunningStatusCount(IRP_REPORT_SCHED_PARAMSSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "SCHEDULER.logResultMap");
	if(sc.getSortOrder() == null)
	{
	    sc.setSortOrder("ORDER BY START_DATE DESC ");
	}
	Object obj = getSqlMap().queryForObject("SCHEDULER.findReportByRunningStatusCount", sc);
	if(obj == null)
	{
	    return 0;
	}
	return ((Integer) obj).intValue();
    }

    
    public List<IRP_REPORT_SCHED_PARAMSCO> findReportScheduleParams(IRP_REPORT_SCHED_PARAMSSC sc) throws DAOException
    {
	return getSqlMap().queryForList("SCHEDULER.selectREPORT_SCHED_PARAMS",
		sc);
    }

    
    public IRP_REPORT_SCHEDULECO findSingleReportSchedule(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	return (IRP_REPORT_SCHEDULECO) getSqlMap().queryForObject("SCHEDULER.findSingleReportSchedule", sc);
    }

    
    public int findSchedulesCount(IRP_SCHEDULESC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "SCHEDULER.ScheduleResultMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("SCHEDULER.findSchedulesCount", sc));
	return nb;
    }

    
    public List<IRP_SCHEDULECO> findSchedules(IRP_SCHEDULESC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "SCHEDULER.ScheduleResultMap");
	if(sc.getSortOrder() == null)
	{
	    sc.setSortOrder("ORDER BY SCHED_ID ");
	}
	return getSqlMap().queryForList("SCHEDULER.findSchedules", sc, sc.getRecToskip(),
		sc.getNbRec());
    }

    
    public IRP_SCHEDULECO findSingleSchedule(IRP_SCHEDULESC sc) throws DAOException
    {
	return (IRP_SCHEDULECO) getSqlMap().queryForObject("SCHEDULER.findSingleSchedule", sc);
    }

    // findScheduleDetails
    
    public List<IRP_SCHED_DETAILSCO> findScheduleDetails(IRP_SCHEDULESC sc) throws DAOException
    {
	return getSqlMap().queryForList("SCHEDULER.findScheduleDetails", sc);
    }

    
    public void insertScheduleDetail(IRP_SCHED_DETAILSVOKey key) throws DAOException
    {
	getSqlMap().insert("IRP_SCHED_DETAILS.insertIRP_SCHED_DETAILS", key);
    }

    
    public void deleteScheduleDetails(IRP_SCHEDULEVO vo) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteScheduleDetails", vo);
    }

    
    public void deleteReportSchedule(IRP_REPORT_SCHEDULEVO vo) throws DAOException
    {
	if(vo.getREPORT_ID() == null)
	{
	    getSqlMap().delete("SCHEDULER.deleteReportSchedule", vo);
	}
	else
	{
	    getSqlMap().delete("IRP_REPORT_SCHEDULE.deleteIRP_REPORT_SCHEDULE", vo);
	}
    }

    public void deletSchedLogs(IRP_SCHEDULECO co) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deletSchedLogs", co);
    }

    
    public void insertReportSchedule(IRP_REPORT_SCHEDULEVO vo) throws DAOException
    {
	getSqlMap().insert("IRP_REPORT_SCHEDULE.insertIRP_REPORT_SCHEDULE", vo);
    }
    
    public List<IRP_REPORT_SCHEDULECO> selectDueReports(IRP_SCHEDULESC sc) throws DAOException
    {
	return getSqlMap().queryForList("SCHEDULEREXT.selectDueReports", sc);
    }
    
    public List<IRP_REPORT_SCHEDULECO> selectSchedReports(IRP_SCHEDULESC sc) throws DAOException
    {
	return getSqlMap().queryForList("SCHEDULER.selectSchedReports", sc);
    }

    // public Object retArgument(Object argumentValue, String argumentType) {
    // if(argumentType.equalsIgnoreCase("string"))
    // {
    // return argumentValue.toString();
    // }
    // if(argumentType.equalsIgnoreCase("numeric") ||
    // argumentType.equalsIgnoreCase("NUMBER"))
    // {
    // return new BigDecimal(argumentValue.toString());
    // }
    // if(argumentType.equalsIgnoreCase("date"))
    // {
    // DateFormat formatter ;
    // formatter = new SimpleDateFormat("dd/MM/yyyy");
    // try {
    // return formatter.parse(argumentValue.toString());
    // } catch (ParseException e) {
    //				
    // e.printStackTrace();
    // }
    // }
    // return null;
    // }

    
    public void deleteLOG(IRP_REPORT_SCHED_LOGVO vo) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteLOG", vo);
    }

    public void deleteSchedParam(IRP_REPORT_SCHED_PARAMSSC paramSC) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteSchedParam", paramSC);
    }

    
    public HashMap retSchedParamValues(IRP_REPORT_SCHED_PARAMSSC paramSC) throws DAOException
    {
	return (HashMap) getSqlMap().queryForMap("SCHEDULER.retSchedParamValues", paramSC, "PARAM_NAME");
    }

    public int checkSchedIsRunning(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("SCHEDULER.checkSchedIsRunning", sc));
    }

    public int retMailFeGroupByCnt(IRP_REP_SCHED_MAIL_GROUP_BYSC sc) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("SCHEDULER.retMailFeGroupByCnt", sc)).intValue();
    }

    public List<IRP_REP_SCHED_MAIL_GROUP_BYCO> retMailFeGroupByList(IRP_REP_SCHED_MAIL_GROUP_BYSC sc)
	    throws DAOException
    {
	return getSqlMap()
		.queryForList("SCHEDULER.retMailFeGroupByList", sc);
    }

    public void deleteGrpByFeMail(IRP_REP_SCHED_MAIL_GROUP_BYSC sc) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteGrpByFeMail", sc);
    }

    public int retSchedMailUsersCnt(IRP_REP_SCHED_MAIL_RECEIVERSVO vo) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("SCHEDULER.retSchedMailUsersCnt", vo)).intValue();
    }

    public List<IRP_REP_SCHED_MAIL_RECEIVERSVO> retSchedMailUsersList(IRP_REP_SCHED_MAIL_RECEIVERSVO vo)
	    throws DAOException
    {
	return getSqlMap().queryForList("SCHEDULER.retSchedMailUsersList",
		vo);
    }

    public void deleteMailUsersList(IRP_REP_SCHED_MAIL_RECEIVERSVO recVO) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteMailUsersList", recVO);
    }

    public void deleteRepSchedGryByFields(List<BigDecimal> reportsIds) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteRepSchedGryByFields", reportsIds);
    }

    public void deleteRepSchedReceivers(List<BigDecimal> reportsIds) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteRepSchedReceivers", reportsIds);
    }

    public List<String> retMailUserList(IRP_REP_SCHED_MAIL_RECEIVERSVO recVO) throws DAOException
    {
	return getSqlMap().queryForList("SCHEDULER.retMailUserList", recVO);
    }

    public CIFVO retMailValByCif(CIFVOKey cifVOKey) throws DAOException
    {
	return (CIFVO) getSqlMap().queryForObject("CIF.selectCIF", cifVOKey);
    }

    public void insertRepSchedLogDetails(IRP_REPORT_SCHED_LOG_DETAILSVO logDetVO) throws DAOException
    {
	getSqlMap().insert("SCHEDULEREXT.insertRepSchedLogDetails", logDetVO);
    }

    public int retRepSchedMailLogDetCnt(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "SCHEDULER.retRepSchedMailLogDetLstMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("SCHEDULER.retRepSchedMailLogDetCnt", sc)).intValue();
	return nb;
    }

    public List<IRP_REPORT_SCHED_LOG_DETAILSCO> retRepSchedMailLogDetLst(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "SCHEDULER.retRepSchedMailLogDetLstMap");
	if(sc.getSortOrder() == null)
	{
	    sc.setSortOrder("ORDER BY LINE_NBR ");
	}
	return getSqlMap().queryForList("SCHEDULER.retRepSchedMailLogDetLst", sc, sc.getRecToskip(), sc.getNbRec());
    }

    public void deleteRepSchedList(List<BigDecimal> reportsIds) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteRepSchedList", reportsIds);
    }

    public List<BigDecimal> retSchedUsage(List<BigDecimal> reportsId) throws DAOException
    {
	return getSqlMap().queryForList("SCHEDULER.retSchedUsage", reportsId);
    }
    
    public  HashMap<String ,IRP_REP_ARGUMENTSCO> retRepMainArgsMap(IRP_REPORT_SCHED_PARAMSSC paramSC)throws DAOException
    {
	return (HashMap<String ,IRP_REP_ARGUMENTSCO>) getSqlMap().queryForMap("SCHEDULER.retRepMainArgsMap", paramSC, "ARGUMENT_NAME");
    }
    
    public void deleteRepSchedArg(IRP_REPORT_SCHED_PARAMSSC sc) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteRepSchedArg", sc);
    }
    
     /**
     * Method that checks if a report has a related scheduler
     */
    public int checkReportHasSched(String progRef) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("SCHEDULER.checkSchedHasReport", progRef)).intValue();
    }
    
    @Override
    public void deleteRepsSchedParams(List<BigDecimal> reportsIds) throws DAOException
    {
	getSqlMap().delete("SCHEDULER.deleteRepsSchedParams", reportsIds);
	
    }
    
    @Override
    public ArrayList<Object> retRepSchedByRepId(IRP_REPORT_SCHEDULESC sc) throws DAOException
    {
	return (ArrayList<Object>) getSqlMap().queryForList("SCHEDULER.retRepSchedByRepId", sc);
    }
    
    @Override
    public ArrayList<Object> retRepSchedParamByRepId(IRP_REPORT_SCHEDULESC sc)
	    throws DAOException
    {
	return (ArrayList<Object>) getSqlMap().queryForList("SCHEDULER.retRepSchedParamByRepId",
		sc);
    }
    
    @Override
    public ArrayList<Object> retRepSchedMailGroupByRepId(IRP_REPORT_SCHEDULESC sc)
	    throws DAOException
    {
	return (ArrayList<Object>) getSqlMap().queryForList(
		"SCHEDULER.retRepSchedMailGroupByRepId", sc);
    }

    @Override
    public ArrayList<Object> retRepSchedMailRecvByRepId(IRP_REPORT_SCHEDULESC sc)
	    throws DAOException
    {
	return (ArrayList<Object>) getSqlMap().queryForList(
		"SCHEDULER.retRepSchedMailRecvByRepId", sc);
    }

    @Override
    public HashMap<String,IRP_REP_SCHED_SESSION_PARAMVO> returnSchedSessionParamsVal() throws DAOException
    {
	return (HashMap) getSqlMap().queryForMap("SCHEDULER.returnSchedSessionParamsVal", null, "SESSION_NAME");
    }


	@Override
	public int retCountAlertEventType(IRP_SCHEDULESC sc) throws DAOException 
	{
		return ((Integer) getSqlMap().queryForObject("SCHEDULER.retCountAlertEventType", sc)).intValue();
	}
	
	@Override
	public int findTableByTableName(IRP_SCHEDULESC sc) throws DAOException
    {
	int nb;
	nb = ((Integer) getSqlMap().queryForObject("SCHEDULER.findTableByTableName", sc));
	return nb;
    }
}
