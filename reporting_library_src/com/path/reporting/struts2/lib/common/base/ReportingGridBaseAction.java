package com.path.reporting.struts2.lib.common.base;

import java.util.HashMap;

import com.path.bo.common.ConstantsCommon;
import com.path.struts2.lib.common.base.GridBaseAction;
import com.path.vo.common.ReportingSessionCO;
import com.path.vo.common.SessionCO;

public class ReportingGridBaseAction extends GridBaseAction
{
	public ReportingSessionCO returnReportingSessionObject()
	{
		return returnReportingSessionObject("RD00R");
	}
	public ReportingSessionCO returnReportingSessionObject(String ref)
    {		
	    	String pageRef= ref;
		if(pageRef==null)
		{
			pageRef="RD00R";
		}
		SessionCO sessionCO= (SessionCO)session.get(ConstantsCommon.SESSION_VO_ATTR);
		HashMap<String, ReportingSessionCO> sessionMap=(HashMap<String, ReportingSessionCO>)sessionCO.getReportingAppDet();
		if(sessionMap==null)
		{
			sessionCO.setReportingAppDet(new HashMap<String, ReportingSessionCO>());
		}
		sessionMap=(HashMap<String, ReportingSessionCO>)sessionCO.getReportingAppDet();
		if(sessionMap.get(pageRef)==null)
		{
			sessionMap.put(pageRef, new ReportingSessionCO());
		}
		return sessionMap.get(pageRef);
    }
}


