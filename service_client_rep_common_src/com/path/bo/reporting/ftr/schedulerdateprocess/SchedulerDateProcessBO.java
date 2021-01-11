package com.path.bo.reporting.ftr.schedulerdateprocess;

import java.util.List;

import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.dbmaps.vo.schedulerdateprocess.SchedulerDateProcessCO;
import com.path.dbmaps.vo.schedulerdateprocess.SchedulerDateProcessSC;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_SCHEDULESC;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * SchedulerDateProcessBO.java used to
 */
public interface SchedulerDateProcessBO 
{  
    /**
     * Called to return the list of schedules 
     * 
     * @param schedSC containing the schedules search criteria
     * @return List of IRP_SCHEDULEVO
     * @throws BaseException
     */
    public List<IRP_SCHEDULEVO> retSchedList(IRP_SCHEDULESC schedSC) throws BaseException;
    /**
     * Called to return the list of schedules count
     * 
     * @param schedSC containing the schedules search criteria
     * @return int the count of records
     * @throws BaseException
     */
    public int retSchedListCount(IRP_SCHEDULESC schedSC) throws BaseException;
    /**
     * Called to return the list of reports related to the selected schedules list
     * 
     * @param schedSC containing the list of selected schedules 
     * @return  List of IRP_REPORT_SCHEDULECO
     * @throws BaseException
     */
    public List<IRP_REPORT_SCHEDULECO> findScheduleReports(SchedulerDateProcessSC sc) throws BaseException;
    /**
     * Called to return the count of reports related to the selected schedules list
     * 
     * @param schedSC containing the list of selected schedules 
     * @return int the count of records
     * @throws BaseException
     */
    public int findScheduleReportsCount(SchedulerDateProcessSC sc) throws BaseException;
    /**
     * Called when the run button is clicked to run the process of updating the report schedules parameters dates
     * 
     * @param reportsArr the array of reports checked and  SchedulerDateProcessCO containing the parameterization of the process
     * @return void
     * @throws BaseException
     */
    public void runProcess(Object[] reportsArr,SchedulerDateProcessCO schedulerDateProcessCO ) throws BaseException;
 
}