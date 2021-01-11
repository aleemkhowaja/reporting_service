package com.path.bo.reporting.ftr.scheduler;

import java.math.BigDecimal;
import java.text.ParseException;

import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ReportParamsCO;

public interface TimerScheduleBO 
{
	void startTimer(ReportParamsCO repParamsCO);
	void stopTimer();
	boolean isTimerRunning();
	/**
	* Called to execute a schedule immediately
	* 
	* @param repParamsCO 
	* @param schedId the schedule ID of the schedule to be executed immediately 
	* @return void
	* @throws BaseException, ParseException
	*/
	public void executeImmediate(ReportParamsCO repParamsCO,BigDecimal schedId) throws BaseException, ParseException;

}
