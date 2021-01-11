package com.path.dao.reporting.ftr.schedulerdateprocess.impl;

import java.util.List;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.ftr.schedulerdateprocess.SchedulerDateProcessDAO;
import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.dbmaps.vo.schedulerdateprocess.SchedulerDateProcessSC;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_SCHEDULESC;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * SchedulerDateProcessDAOImpl.java used to
 */
public class SchedulerDateProcessDAOImpl extends BaseDAO implements SchedulerDateProcessDAO
{
    public List<IRP_SCHEDULEVO> retSchedList(IRP_SCHEDULESC schedSC) throws BaseException
    {
	if(schedSC.getSortOrder() == null)
	{
	    schedSC.setSortOrder(" ORDER BY SCHED_ID ");
	}
	DAOHelper.fixGridMaps(schedSC, getSqlMap(), "schedulerDateProcessMapper.getSchedLkupMap");
	    return getSqlMap().queryForList("schedulerDateProcessMapper.retSchedList", schedSC,
		    schedSC.getRecToskip(), schedSC.getNbRec());
	

    }

    public int retSchedListCount(IRP_SCHEDULESC schedSC) throws BaseException
    {
	DAOHelper.fixGridMaps(schedSC, getSqlMap(), "schedulerDateProcessMapper.getSchedLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("schedulerDateProcessMapper.retSchedListCount", schedSC)).intValue();
	return nb;
	
    }
    
    public List<IRP_REPORT_SCHEDULECO> findScheduleReports(SchedulerDateProcessSC sc) throws BaseException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "schedulerDateProcessMapper.ScheduleReportsResultMap");
	if(sc.getSortOrder()==null)
	{
		sc.setSortOrder("ORDER BY REPORT_NAME ");
	}
	return getSqlMap().queryForList("schedulerDateProcessMapper.findScheduleReports", sc,sc.getRecToskip(), sc.getNbRec());
	
    }
    public int findScheduleReportsCount(SchedulerDateProcessSC sc) throws BaseException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "schedulerDateProcessMapper.ScheduleReportsResultMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("schedulerDateProcessMapper.findScheduleReportsCount", sc));
	return nb;
    }
    
    public void runProcess(SchedulerDateProcessSC sc) throws BaseException
    {
	
	if(RepConstantsCommon.COL_TEMPLPROC_PERIODIC.equals(sc.getProcType()))
	{   
	    getSqlMap().update("schedulerDateProcessMapper.updatePeriodicDateReportSched", sc);
	}
	else
	{  getSqlMap().update("schedulerDateProcessMapper.updateDateReportSched", sc);
	}
	
    }
    }
