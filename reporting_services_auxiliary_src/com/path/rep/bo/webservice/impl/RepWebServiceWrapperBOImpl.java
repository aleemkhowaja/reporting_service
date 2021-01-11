package com.path.rep.bo.webservice.impl;

import java.util.HashMap;

import com.path.lib.common.base.WebServiceBaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.rep.bo.webservice.RepWebServiceWrapperBO;
import com.path.rep.bo.webservice.common.CommonReportingServiceBO;
import com.path.rep.bo.webservice.util.RepServiceUtilBO;
import com.path.vo.rep.report.GenerateReportReq;
import com.path.vo.rep.report.GenerateReportResp;

/**
 * 
 * @author EliasAoun
 *
 */
public class RepWebServiceWrapperBOImpl extends WebServiceBaseBO implements RepWebServiceWrapperBO
{
    private CommonReportingServiceBO commonReportingServiceBO;
    private RepServiceUtilBO repServiceUtilBO;
    
    public RepServiceUtilBO getRepServiceUtilBO()
    {
        return repServiceUtilBO;
    }


    public void setRepServiceUtilBO(RepServiceUtilBO repServiceUtilBO)
    {
        this.repServiceUtilBO = repServiceUtilBO;
    }


    public void setCommonReportingServiceBO(CommonReportingServiceBO commonReportingServiceBO)
    {
        this.commonReportingServiceBO = commonReportingServiceBO;
    }
    
    
    /**
     * Method that will be used temporarily to import dynamic File, before it is
     * moved to integration gateway stand alone module
     * 
     * @param hashIn HashMap with the parameters for which the import to be done
     * @return result of the import operation
     * @throws BaseException
     */
    public HashMap<String, Object> generateReport(HashMap<String, Object> hashIn) throws BaseException
    {
	try
	{
	    // initialize service call
	    initializeServiceCall(hashIn);
	    
	    if(hashIn.get("skipUsrAuth") == null || "false".equals(hashIn.get("skipUsrAuth")))
	    {
		// convert hashin to object
		GenerateReportReq generateReportReq = (GenerateReportReq) PathPropertyUtil.convertToObject(hashIn, GenerateReportReq.class);
		repServiceUtilBO.authenticateUser(generateReportReq);
	    }
	    // return response from service
	    GenerateReportResp returnEventsListResp = commonReportingServiceBO.generateReport(hashIn);

	    // convert from response object to hashmap
	    hashIn = PathPropertyUtil.convertToMap(returnEventsListResp);
	}
	catch(Exception ex)
	{
	    handleServiceException(ex, hashIn);
	}
	return hashIn;
    }


}
