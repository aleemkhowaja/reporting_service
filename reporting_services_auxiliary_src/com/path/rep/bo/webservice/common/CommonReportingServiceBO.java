package com.path.rep.bo.webservice.common;

import java.util.HashMap;

import com.path.vo.rep.report.GenerateReportResp;

public interface CommonReportingServiceBO
{

    GenerateReportResp generateReport(HashMap<String, Object> hashIn) throws Exception;

}
