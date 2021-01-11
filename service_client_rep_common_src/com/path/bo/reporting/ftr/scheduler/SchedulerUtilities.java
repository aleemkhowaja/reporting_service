package com.path.bo.reporting.ftr.scheduler;

import com.path.vo.reporting.ReportParamsCO;

public interface SchedulerUtilities {
	void startTimer(ReportParamsCO repParamsCO);
	void stopTimer();
	boolean isTimerRunning();
}
