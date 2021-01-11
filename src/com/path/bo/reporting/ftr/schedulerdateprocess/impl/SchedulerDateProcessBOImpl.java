package com.path.bo.reporting.ftr.schedulerdateprocess.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.schedulerdateprocess.SchedulerDateProcessBO;
import com.path.dao.reporting.ftr.schedulerdateprocess.SchedulerDateProcessDAO;
import com.path.dbmaps.vo.IRP_SCHEDULEVO;
import com.path.dbmaps.vo.schedulerdateprocess.SchedulerDateProcessCO;
import com.path.dbmaps.vo.schedulerdateprocess.SchedulerDateProcessSC;
import com.path.expression.lib.util.DateUtil;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULECO;
import com.path.vo.reporting.scheduler.IRP_SCHEDULESC;
/**
 * 
 * Copyright 2013, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * SchedulerDateProcessBOImpl.java used to
 */
public class SchedulerDateProcessBOImpl extends BaseBO implements SchedulerDateProcessBO
{
	SchedulerDateProcessDAO schedulerDateProcessDAO;
	public SchedulerDateProcessDAO getSchedulerDateProcessDAO()
	{
		return schedulerDateProcessDAO;
	}
	public void setSchedulerDateProcessDAO(SchedulerDateProcessDAO schedulerDateProcessDAO)
	{
		this.schedulerDateProcessDAO = schedulerDateProcessDAO;
	}
	    public List<IRP_SCHEDULEVO> retSchedList(IRP_SCHEDULESC schedSC) throws BaseException
	    {
		return schedulerDateProcessDAO.retSchedList(schedSC);
	    }

	    public int retSchedListCount(IRP_SCHEDULESC schedSC) throws BaseException
	    {
		return schedulerDateProcessDAO.retSchedListCount(schedSC);
	    }
	    public List<IRP_REPORT_SCHEDULECO> findScheduleReports(SchedulerDateProcessSC sc) throws BaseException
	    {
		return schedulerDateProcessDAO.findScheduleReports(sc);
	    }
	    public int findScheduleReportsCount(SchedulerDateProcessSC sc) throws BaseException
	    {
		return schedulerDateProcessDAO.findScheduleReportsCount(sc);
	    }

	    public void runProcess(Object[] reportsArr,SchedulerDateProcessCO schedulerDateProcessCO ) throws BaseException
	    {
		 try
		    {
		     SchedulerDateProcessSC sc = new SchedulerDateProcessSC();
		     ArrayList<SchedulerDateProcessCO> procList = new ArrayList<SchedulerDateProcessCO>();
		     
		   for(int i = 0; i < reportsArr.length; i++)
		    {
		     Object report = reportsArr[i];
		     SchedulerDateProcessCO schedProcCO = new SchedulerDateProcessCO();
		    	String reportId = (String) PropertyUtils.getProperty(report, "REPORT_ID");
		    	String schedId =  (String) PropertyUtils.getProperty(report, "SCHED_ID");
		    	schedProcCO.setSchedId(new BigDecimal(schedId));
		    	schedProcCO.setReportId(new BigDecimal(reportId));
		    	procList.add(schedProcCO);
		       
		    }
			sc.setProcType(schedulerDateProcessCO.getProcType());
			sc.setProcList(procList);
			if(RepConstantsCommon.COL_TEMPLPROC_ASOF.equals(schedulerDateProcessCO.getProcType()))
			{  	
			   sc.setDateStr(DateUtil.format(schedulerDateProcessCO.getAsOfDate(), "dd/MM/yyyy"));
			   sc.setDateType(RepConstantsCommon.DATE_VALUE_ASOF);
			   schedulerDateProcessDAO.runProcess(sc);
			}
			else if(RepConstantsCommon.COL_TEMPLPROC_FROMTO.equals(schedulerDateProcessCO.getProcType()))
			{   
			    sc.setDateStr(DateUtil.format(schedulerDateProcessCO.getFromDate(), "dd/MM/yyyy"));
			    sc.setDateType(RepConstantsCommon.DATE_VALUE_FROM);
			    schedulerDateProcessDAO.runProcess(sc);
			    
			    sc.setDateStr(DateUtil.format(schedulerDateProcessCO.getToDate(), "dd/MM/yyyy"));
			    sc.setDateType(RepConstantsCommon.DATE_VALUE_TO);
			    schedulerDateProcessDAO.runProcess(sc);
			
			}
			else if(RepConstantsCommon.COL_TEMPLPROC_BOTH.equals(schedulerDateProcessCO.getProcType()))
			{ 
			    sc.setDateStr(DateUtil.format(schedulerDateProcessCO.getAsOfDate(), "dd/MM/yyyy"));
			    sc.setDateType(RepConstantsCommon.DATE_VALUE_ASOF);
			    schedulerDateProcessDAO.runProcess(sc);
				   
			    sc.setDateStr(DateUtil.format(schedulerDateProcessCO.getFromDate(), "dd/MM/yyyy"));
			    sc.setDateType(RepConstantsCommon.DATE_VALUE_FROM);
			    schedulerDateProcessDAO.runProcess(sc);
			    
			    sc.setDateStr(DateUtil.format(schedulerDateProcessCO.getToDate(), "dd/MM/yyyy"));
			    sc.setDateType(RepConstantsCommon.DATE_VALUE_TO);
			    schedulerDateProcessDAO.runProcess(sc);
				
			}
			else if(RepConstantsCommon.COL_TEMPLPROC_PERIODIC.equals(schedulerDateProcessCO.getProcType()))
			{   sc.setPeriodicDate(schedulerDateProcessCO.getPeriodicDate());
			    sc.setPeriodType(schedulerDateProcessCO.getPeriodType());
			    schedulerDateProcessDAO.runProcess(sc);
			}
			

		    }
		   
		    catch(Exception e)
		    {
			throw new BOException(e.getMessage(),e);
			
		    }
		  		     
			
		  
		
	    }
}
