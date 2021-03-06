package com.path.vo.rep.report;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.path.vo.common.ImBaseRequest;
import com.path.vo.common.header.ServiceContextAllVO;

/**
 * @AutoGenerated Code
 * @description class GenerateReportReq extends ImBaseRequest
 */
@XmlType(propOrder={})
@XmlRootElement(name="generateReportReq")
public class GenerateReportReq extends ImBaseRequest
{
	private ServiceContextAllVO serviceContext = new ServiceContextAllVO();
	private BigDecimal branchCode;
	private BigDecimal companyCode;
	private String applicationName;
	private ReportDetails reportDetail;
	private HashMap<String,String> reportParametersList = new HashMap<String,String>();

	public void setServiceContext(ServiceContextAllVO serviceContext)
	{
	   this.serviceContext = serviceContext;
	}
	public ServiceContextAllVO getServiceContext()
	{
	   return serviceContext;
	}
	public void setBranchCode(BigDecimal branchCode)
	{
	   this.branchCode = branchCode;
	}
	@XmlElement(required = true)
	public BigDecimal getBranchCode()
	{
	   return branchCode;
	}
	public void setCompanyCode(BigDecimal companyCode)
	{
	   this.companyCode = companyCode;
	}
	@XmlElement(required = true)
	public BigDecimal getCompanyCode()
	{
	   return companyCode;
	}
	public void setApplicationName(String applicationName)
	{
	   this.applicationName = applicationName;
	}
	@XmlElement(required = true)
	public String getApplicationName()
	{
	   return applicationName;
	}
	public void setReportDetail(ReportDetails reportDetail)
	{
	   this.reportDetail = reportDetail;
	}
	@XmlElement(required = true)
	public ReportDetails getReportDetail()
	{
	   return reportDetail;
	}
	public HashMap<String, String> getReportParametersList()
	{
	    return reportParametersList;
	}
	public void setReportParametersList(HashMap<String, String> reportParametersList)
	{
	    this.reportParametersList = reportParametersList;
	}
	
}
