package com.path.bo.reporting.ftr.scheduler.impl;

import com.path.bo.reporting.ftr.scheduler.SchedulerUtilities;
import com.path.lib.common.base.BaseBO;
import com.path.vo.reporting.ReportParamsCO;

public class SchedulerUtilitiesImpl  extends BaseBO implements SchedulerUtilities
{
private TimerScheduleBOImpl timerScheduleBO;
	
	public void setTimerScheduleBO(TimerScheduleBOImpl timerScheduleBO) {
		this.timerScheduleBO = timerScheduleBO;
	}

	public TimerScheduleBOImpl getTimerScheduleBO() {
		return timerScheduleBO;
	}

	@Override
	public void startTimer(ReportParamsCO repParamsCO) {
		timerScheduleBO.startTimer(repParamsCO);
		
	}

	@Override
	public void stopTimer() {
		timerScheduleBO.stopTimer();
		
	}
	@Override
	public boolean isTimerRunning()
	{
		return timerScheduleBO.isTimerRunning();
	}
}
