package com.path.dao.reporting.ftr.scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.CIFVO;
import com.path.dbmaps.vo.CIFVOKey;
import com.path.dbmaps.vo.IRP_REPORT_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_LOG_DETAILSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_RECEIVERSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_SESSION_PARAMVO;
import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_SCHED_DETAILSVOKey;
import com.path.lib.common.exception.DAOException;
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

public interface SchedulerDAO
{
    List<IRP_SCHEDULECO> findSchedules(IRP_SCHEDULESC sc) throws DAOException;

    int findSchedulesCount(IRP_SCHEDULESC sc) throws DAOException;

    IRP_SCHEDULECO findSingleSchedule(IRP_SCHEDULESC sc) throws DAOException;

    List<IRP_SCHED_DETAILSCO> findScheduleDetails(IRP_SCHEDULESC sc) throws DAOException;

    void insertScheduleDetail(IRP_SCHED_DETAILSVOKey key) throws DAOException;

    void deleteScheduleDetails(IRP_SCHEDULEVO vo) throws DAOException;

    int retReportSchedulesCount(IRP_REPORT_SCHEDULESC sc) throws DAOException;

    List<IRP_REPORT_SCHEDULECO> findReportSchedules(IRP_REPORT_SCHEDULESC sc) throws DAOException;

    IRP_REPORT_SCHEDULECO findSingleReportSchedule(IRP_REPORT_SCHEDULESC sc) throws DAOException;

    void insertReportSchedule(IRP_REPORT_SCHEDULEVO vo) throws DAOException;

    void deleteReportSchedule(IRP_REPORT_SCHEDULEVO vo) throws DAOException;

    public List<IRP_REPORT_SCHED_PARAMSCO> findReportScheduleParams(IRP_REPORT_SCHED_PARAMSSC sc) throws DAOException;

    List<IRP_REPORT_SCHEDULECO> selectDueReports(IRP_SCHEDULESC sc) throws DAOException;

    // Object retArgument(Object argumentValue, String argumentType);
    int findLogCount(IRP_REPORT_SCHEDULESC sc) throws DAOException;

    void insertLOG(IRP_REPORT_SCHED_LOGCO vo) throws DAOException;

    void deleteLOG(IRP_REPORT_SCHED_LOGVO vo) throws DAOException;

    void updateLOG(IRP_REPORT_SCHED_LOGVO vo) throws DAOException;

    void updateNextRunDate(IRP_SCHEDULESC sc) throws DAOException;

    Date retNextRunningDate(IRP_SCHEDULECO co) throws DAOException;

    List<IRP_REPORT_SCHED_LOGCO> findReportByRunningStatus(IRP_REPORT_SCHED_PARAMSSC sc) throws DAOException;

    int findReportByRunningStatusCount(IRP_REPORT_SCHED_PARAMSSC sc) throws DAOException;

    public void deleteSchedParam(IRP_REPORT_SCHED_PARAMSSC paramSC) throws DAOException;

    public HashMap retSchedParamValues(IRP_REPORT_SCHED_PARAMSSC paramSC) throws DAOException;

    public void deletSchedLogs(IRP_SCHEDULECO co) throws DAOException;

    public int checkSchedIsRunning(IRP_REPORT_SCHEDULESC sc) throws DAOException;

    public int retMailFeGroupByCnt(IRP_REP_SCHED_MAIL_GROUP_BYSC sc) throws DAOException;

    public List<IRP_REP_SCHED_MAIL_GROUP_BYCO> retMailFeGroupByList(IRP_REP_SCHED_MAIL_GROUP_BYSC sc)
	    throws DAOException;

    public void deleteGrpByFeMail(IRP_REP_SCHED_MAIL_GROUP_BYSC sc) throws DAOException;

    public List<IRP_REP_SCHED_MAIL_RECEIVERSVO> retSchedMailUsersList(IRP_REP_SCHED_MAIL_RECEIVERSVO vo)
	    throws DAOException;

    public int retSchedMailUsersCnt(IRP_REP_SCHED_MAIL_RECEIVERSVO vo) throws DAOException;

    public void deleteMailUsersList(IRP_REP_SCHED_MAIL_RECEIVERSVO recVO) throws DAOException;

    public void deleteRepSchedGryByFields(List<BigDecimal> reportsIds) throws DAOException;

    public void deleteRepSchedReceivers(List<BigDecimal> reportsIds) throws DAOException;

    public List<String> retMailUserList(IRP_REP_SCHED_MAIL_RECEIVERSVO recVO) throws DAOException;

    public CIFVO retMailValByCif(CIFVOKey cifVOKey) throws DAOException;

    public void insertRepSchedLogDetails(IRP_REPORT_SCHED_LOG_DETAILSVO logDetVO) throws DAOException;

    public int retRepSchedMailLogDetCnt(IRP_REPORT_SCHEDULESC sc) throws DAOException;

    public List<IRP_REPORT_SCHED_LOG_DETAILSCO> retRepSchedMailLogDetLst(IRP_REPORT_SCHEDULESC sc) throws DAOException;

    public void deleteRepSchedList(List<BigDecimal> reportsIds) throws DAOException;

    public List<BigDecimal> retSchedUsage(List<BigDecimal> reportsId) throws DAOException;
    
    public  HashMap<String ,IRP_REP_ARGUMENTSCO> retRepMainArgsMap(IRP_REPORT_SCHED_PARAMSSC paramSC)throws DAOException;
    
    public void deleteRepSchedArg(IRP_REPORT_SCHED_PARAMSSC sc) throws DAOException;
    public int checkReportHasSched(String progRef) throws DAOException;
    /**
     * Called to get the list of reports of specific schedule
     * 
     * @param IRP_SCHEDULESC containing the schedule search criteria
     * @return List<IRP_REPORT_SCHEDULECO> the list of reports related to this schedule
     * @throws DAOException
     */
    public List<IRP_REPORT_SCHEDULECO> selectSchedReports(IRP_SCHEDULESC sc) throws DAOException;
    
    /**
     * Called to delete the  scheduler parameters by report ids
     * 
     * @param reportsIds 
     * @return
     * @throws DAOException
     */
    public void deleteRepsSchedParams(List<BigDecimal> reportsIds) throws DAOException;
    /**
     * Method that returns records in irp_report_schedule table
     * for several reports passed as parameter
     * @param sc
     * @return
     * @throws DAOException
     */
    public ArrayList<Object> retRepSchedByRepId(IRP_REPORT_SCHEDULESC sc) throws DAOException;
    /**
     * Method that returns records in irp_report_sched_params table
     * for several reports passed as parameter
     * @param sc
     * @return
     * @throws DAOException
     */
    public ArrayList<Object> retRepSchedParamByRepId(IRP_REPORT_SCHEDULESC sc)throws DAOException;
    /**
     * Method that returns records in irp_rep_sched_mail_group_by table
     * for several reports passed as parameter
     * @param sc
     * @return
     * @throws DAOException
     */
    public ArrayList<Object> retRepSchedMailGroupByRepId(IRP_REPORT_SCHEDULESC sc)throws DAOException;
    /**
     * Method that returns records in irp_rep_sched_mail_receivers table
     * for several reports passed as parameter
     * @param sc
     * @return
     * @throws DAOException
     */
    public ArrayList<Object> retRepSchedMailRecvByRepId(IRP_REPORT_SCHEDULESC sc) throws DAOException;
    

    /**
     * Method that returns the list of the session attribute that will be used by the scheduler engine
     * @param 
     * @return List<IRP_REP_SCHED_SESSION_PARAMVO>
     * @throws BaseException
     */
    public HashMap<String,IRP_REP_SCHED_SESSION_PARAMVO> returnSchedSessionParamsVal() throws DAOException;
    
    /**
     * Method that returns the Count of SYS_PARAM_LOV_TRANS
     * @param SC
     * @return int
     * @throws BaseException
     */
    public int retCountAlertEventType(IRP_SCHEDULESC sc) throws DAOException;
    
    /**
     * Method that returns if table exist or not
     * @param sc
     * @return
     * @throws DAOException
     */
    public int findTableByTableName(IRP_SCHEDULESC sc) throws DAOException;

}