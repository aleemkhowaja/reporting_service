package com.path.bo.reporting.ftr.scheduler;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.path.dbmaps.vo.IRP_REPORT_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_PARAMSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_RECEIVERSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_SESSION_PARAMVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
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

public interface SchedulerBO
{
    public int findSchedulesCount(IRP_SCHEDULESC sc) throws BaseException;

    public List<IRP_SCHEDULECO> findSchedules(IRP_SCHEDULESC sc) throws BaseException;

    IRP_SCHEDULECO findSingleSchedule(IRP_SCHEDULESC sc) throws BaseException;

    int retReportSchedulesCount(IRP_REPORT_SCHEDULESC sc) throws BaseException;

    List<IRP_REPORT_SCHEDULECO> findReportSchedules(IRP_REPORT_SCHEDULESC sc) throws DAOException;

    IRP_REPORT_SCHEDULECO findSingleReportSchedule(IRP_REPORT_SCHEDULESC sc) throws BaseException;

    void insertReportSchedule(IRP_REPORT_SCHEDULEVO vo,
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList,
	    HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap)
	    throws BaseException;

    void updateReportSchedule(IRP_REPORT_SCHEDULEVO vo,
	    LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList,
	    HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap)
	    throws BaseException;

    Date retNextRunningDate(IRP_SCHEDULECO co) throws BaseException;

    public List<IRP_REPORT_SCHED_PARAMSCO> findReportScheduleParams(IRP_REPORT_SCHED_PARAMSSC sc) throws BaseException;

    List<IRP_REPORT_SCHEDULECO> selectDueReports(IRP_SCHEDULESC sc) throws BaseException;

    void stopTimer();

    void startTimer(ReportParamsCO repParamsCO);

    boolean isTimerRunning();

    void updateNextRunDate(IRP_SCHEDULESC sc) throws BaseException;

    List<IRP_REPORT_SCHED_LOGCO> findReportByRunningStatus(IRP_REPORT_SCHED_PARAMSSC sc) throws BaseException;

    public int findReportByRunningStatusCount(IRP_REPORT_SCHED_PARAMSSC sc) throws BaseException;

    public HashMap retSchedParamValues(IRP_REPORT_SCHED_PARAMSSC paramSC) throws BaseException;

    public int checkSchedIsRunning(IRP_REPORT_SCHEDULESC sc) throws BaseException;

    public void deleteSchedule(BigDecimal schedId, AuditRefCO refCO) throws BaseException;

    public void deleteReportSchedule(IRP_REPORT_SCHEDULECO repSchedCO, AuditRefCO refCO) throws BaseException;

    public void updateSchedParams(IRP_REPORT_SCHED_PARAMSSC paramSC, ArrayList<IRP_REPORT_SCHED_PARAMSVO> paramsList) throws BaseException;

    public void addSchedule(String mode, IRP_SCHEDULECO schedule, IRP_SCHEDULESC schedulerSC, BigDecimal schedId)
	    throws BaseException;

    public void saveSchedParams(ArrayList<IRP_REPORT_SCHED_PARAMSVO> paramsList) throws BaseException;

    public int retMailFeGroupByCnt(IRP_REP_SCHED_MAIL_GROUP_BYSC sc) throws BaseException;

    public List<IRP_REP_SCHED_MAIL_GROUP_BYCO> retMailFeGroupByList(IRP_REP_SCHED_MAIL_GROUP_BYSC sc)
	    throws BaseException;

    public List<IRP_REP_SCHED_MAIL_RECEIVERSVO> retSchedMailUsersList(IRP_REP_SCHED_MAIL_RECEIVERSVO vo)
	    throws BaseException;

    public int retSchedMailUsersCnt(IRP_REP_SCHED_MAIL_RECEIVERSVO vo) throws BaseException;

    public void deleteReportsSchedules(List<BigDecimal> reportIds) throws BaseException;

    public int retRepSchedMailLogDetCnt(IRP_REPORT_SCHEDULESC sc) throws BaseException;

    public List<IRP_REPORT_SCHED_LOG_DETAILSCO> retRepSchedMailLogDetLst(IRP_REPORT_SCHEDULESC sc) throws BaseException;

    public List<BigDecimal> retSchedUsage(List<BigDecimal> reportsId) throws BaseException;
    
    public  HashMap<String ,IRP_REP_ARGUMENTSCO> retRepMainArgsMap(IRP_REPORT_SCHED_PARAMSSC paramSC)throws BaseException;
    
    public void deleteRepSchedArg(IRP_REPORT_SCHED_PARAMSSC sc) throws BaseException;
    
   /* public List<FTR_OPTCO> selectFcrReports(IRP_SCHEDULESC sc) throws BaseException;
    public int selectFcrRepCount(IRP_SCHEDULESC sc) throws BaseException;*/
    public int checkReportHasSched(String progRef) throws BaseException;
    public List<FTR_OPTCO> retListDynReports(CommonDetailsSC sc) throws BaseException;
    public IRP_AD_HOC_REPORTCO retFcrRepById(IRP_AD_HOC_REPORTSC sc) throws BaseException;
    /**
     * Method that returns records in irp_report_schedule table
     * for several reports passed as parameter
     * @param sc
     * @return
     * @throws BaseException
     */
    public ArrayList<Object> retRepSchedByRepId(IRP_REPORT_SCHEDULESC sc) throws BaseException;
    /**
     * Method that returns records in irp_report_sched_params table
     * for several reports passed as parameter
     * @param sc
     * @return
     * @throws BaseException
     */
    public ArrayList<Object> retRepSchedParamByRepId(IRP_REPORT_SCHEDULESC sc)throws BaseException;
    /**
     * Method that returns records in irp_rep_sched_mail_group_by table
     * for several reports passed as parameter
     * @param sc
     * @return
     * @throws BaseException
     */
    public ArrayList<Object> retRepSchedMailGroupByRepId(IRP_REPORT_SCHEDULESC sc)throws BaseException;
    /**
     * Method that returns records in irp_rep_sched_mail_receivers table
     * for several reports passed as parameter
     * @param sc
     * @return
     * @throws BaseException
     */
    public ArrayList<Object> retRepSchedMailRecvByRepId(IRP_REPORT_SCHEDULESC sc) throws BaseException;
    

    /**
     * Method that returns the list of the session attribute that will be used by the scheduler engine
     * @param 
     * @return List<IRP_REP_SCHED_SESSION_PARAMVO>
     * @throws BaseException
     */
    public HashMap<String,IRP_REP_SCHED_SESSION_PARAMVO> returnSchedSessionParamsVal() throws BaseException;
    /**
     * Method that run the scheduler engine
     * @param 
     * @return List<IRP_REP_SCHED_SESSION_PARAMVO>
     * @throws BaseException
     */
    public void startEngine()throws BaseException;
    /**
	 * 
	 * @param sc
	 * @return
	 * @throws BaseException
	 */
	public List<HashMap<String,Object>> retBatchesList(IRP_AD_HOC_REPORTSC sc) throws BaseException;
	/**
	 * 
	 * @param sc
	 * @return
	 * @throws BaseException
	 */
	public int retBatchesListCount(IRP_AD_HOC_REPORTSC sc) throws BaseException;
	
	/**
	 * 
	 * @param sc
	 * @return
	 * @throws BaseException
	 */
	public List<Map<String,Object>> retEventList(IRP_SCHEDULESC reportSC) throws BaseException;
	
	/**
	 * 
	 * @param sc
	 * @return
	 * @throws BaseException
	 */
	public int retEventListCount(IRP_SCHEDULESC sc) throws BaseException;
}
