package com.path.vo.reporting.ftr.timeBuckets;


import com.path.dbmaps.vo.FTR_TIME_BUCKETSVO;
import com.path.lib.vo.BaseVO;

public class FTR_TIME_BUCKETSCO extends BaseVO {
	
	private String concatKey;
	private String reportName;
	private String BRIEF_DESC_ENG;//LBedrane - 19/03/2018 638947 - Currency description field issue

//LBedrane - 19/03/2018 638947 - Currency description field issue <start>
    public String getBRIEF_DESC_ENG()
	{
	    return BRIEF_DESC_ENG;
	}

	public void setBRIEF_DESC_ENG(String bRIEF_DESC_ENG)
	{
	    BRIEF_DESC_ENG = bRIEF_DESC_ENG;
	}
//LBedrane - 19/03/2018 638947 - Currency description field issue <end>
    public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

	public String getConcatKey() {
		return concatKey;
	}
    
	public void setConcatKey(String concatKey) {
		this.concatKey = concatKey;
	}
	
	private FTR_TIME_BUCKETSVO ftrtimebucketsVO = new FTR_TIME_BUCKETSVO();

	public FTR_TIME_BUCKETSVO getFtrtimebucketsVO() {
		return ftrtimebucketsVO;
	}

	public void setFtrtimebucketsVO(FTR_TIME_BUCKETSVO ftrtimebucketsVO) {
		this.ftrtimebucketsVO = ftrtimebucketsVO;
	}
}
