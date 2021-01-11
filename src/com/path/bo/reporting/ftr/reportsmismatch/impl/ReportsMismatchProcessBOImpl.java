package com.path.bo.reporting.ftr.reportsmismatch.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.reportsmismatch.ReportsMismatchProcessBO;
import com.path.dao.reporting.ftr.reportsmismatch.ReportsMismatchProcessDAO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;

public class ReportsMismatchProcessBOImpl extends BaseBO implements ReportsMismatchProcessBO
{
    private ReportsMismatchProcessDAO reportsMismatchProcessDAO;

    public ReportsMismatchProcessDAO getReportsMismatchProcessDAO()
    {
	return reportsMismatchProcessDAO;
    }

    public void setReportsMismatchProcessDAO(ReportsMismatchProcessDAO reportsMismatchProcessDAO)
    {
	this.reportsMismatchProcessDAO = reportsMismatchProcessDAO;
    }

    public ArrayList<REP_MISMATCH_PROCESSCO> retCrtProgRefLkpLst(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException
    {
	return reportsMismatchProcessDAO.retCrtProgRefLkpLst(misProcSC);
    }

    public int retCrtProgRefLkpCnt(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException
    {
	return reportsMismatchProcessDAO.retCrtProgRefLkpCnt(misProcSC);
    }

    public int retMisProcGridInterCnt(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException
    {
	return reportsMismatchProcessDAO.retMisProcGridInterCnt(misProcSC);
    }

    public ArrayList<REP_MISMATCH_PROCESSCO> retMisProcGridInterLst(REP_MISMATCH_PROCESSSC misProcSC)
	    throws BaseException
    {
	return reportsMismatchProcessDAO.retMisProcGridInterLst(misProcSC);
    }

    public int retMisProcGridIntraCnt(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException
    {
	return reportsMismatchProcessDAO.retMisProcGridIntraCnt(misProcSC);
    }

    public ArrayList<REP_MISMATCH_PROCESSCO> retMisProcGridIntraLst(REP_MISMATCH_PROCESSSC misProcSC)
	    throws BaseException
    {
	return reportsMismatchProcessDAO.retMisProcGridIntraLst(misProcSC);
    }

    public List<REP_SNAPSHOT_INFORMATIONCO> retMisProcSnpInfoList(REP_MISMATCH_PROCESSSC procSc) throws BaseException
    {
	return reportsMismatchProcessDAO.retMisProcSnpInfoList(procSc);
    }

    public StringBuffer retIntraProcessHtml(List<REP_SNAPSHOT_INFORMATIONCO> snpLst, REP_MISMATCH_PROCESSCO misProcCO)
	    throws Exception
    {
	return null;
    }

    public StringBuffer retProcessHtml(List<REP_SNAPSHOT_INFORMATIONCO> snpLst, REP_MISMATCH_PROCESSCO misProcCO,HashMap<String,String> lblhm)
	    throws Exception
    {

	// get the snp related to the selected period
	REP_SNAPSHOT_INFORMATIONCO co;
	String frequency = misProcCO.getFREQUENCY();
	Date selPeriod = misProcCO.getPERIOD();
	String crtProgRefVal = misProcCO.getCRT_PROGREF();
	BigDecimal misType = misProcCO.getTYPE();
	String intraCrtVal = misProcCO.getCRITERIA();
	boolean hasSnp;
	int maxColNb = 0;
	String crtCol;
	HashMap<String, Object> hmVal;

	String[] crtsProgRefs = misProcCO.getCrtsProgRefs().split(",");
	LinkedHashMap<String, LinkedHashMap<String, BigDecimal>> hm = new LinkedHashMap<String, LinkedHashMap<String, BigDecimal>>();
	/*
	 * fill the hm with key and value where the value is a new hashMap, we
	 * will fill it here in order to maintain the selected order , in case
	 * of inter the key will be progRef~crt and in case of intra the key
	 * will be crt~progRef
	 */
	if(BigDecimal.ZERO.equals(misType))
	{
	    // fill the first element of the hm with the main crt
	    hm.put(intraCrtVal + "~" + crtProgRefVal, null);
	}
	for(int i = 0; i < crtsProgRefs.length; i++)
	{
	    hm.put(crtsProgRefs[i] + "~" + crtProgRefVal,null);
	}

	LinkedHashMap<String, BigDecimal> currMap;

	for(int j = 0; j < snpLst.size(); j++)
	{
	    co = snpLst.get(j);
	    // return the related columns
	    REP_MISMATCH_PROCESSSC sc = new REP_MISMATCH_PROCESSSC();
	    sc.setMISMATCH_TYPE(misType);
	    sc.setMISMATCH_REP_REF(co.getRepSnapshotParamVO().getREP_REFERENCE());
	    if(BigDecimal.ONE.equals(misType))
	    {
		sc.setCRITERIA_VAL(crtProgRefVal);
	    }
	    else
	    {
		sc.setCRITERIA_VAL(intraCrtVal);
	    }
	    List<REP_MISMATCH_COLUMNCO> cols = reportsMismatchProcessDAO.retRelColBySnp(sc);

	    if(cols.size() > maxColNb)
	    {
		maxColNb = cols.size();
	    }
	    // check if the snp is related to the selected period
	    hasSnp = checkIfHasSnpByPeriod(co, frequency, selPeriod);
	    if(hasSnp)
	    {
		// convert the blob to array
		ArrayList<HashMap<String, Object>> ar = (ArrayList<HashMap<String, Object>>) CommonUtil
			.objectFromBytes(co.getRepSnapshotInfoVO().getREP_BLOB());

		// if inter ,loop the array and seach for the criteria column
		// having the
		// selected crt. val and construct the hashmap
		if(BigDecimal.ONE.equals(misType))
		{
		    currMap = hm.get(co.getRepSnapshotParamVO().getREP_REFERENCE() + "~" + crtProgRefVal);
		    if(currMap==null)
		    {
			hm.put(co.getRepSnapshotParamVO().getREP_REFERENCE() + "~" + crtProgRefVal, new LinkedHashMap<String, BigDecimal>());
			currMap=hm.get(co.getRepSnapshotParamVO().getREP_REFERENCE() + "~" + crtProgRefVal);
		    }
		    if(!cols.isEmpty())
		    {
			crtCol = cols.get(0).getCRITERIA_COL();
			for(int i = 0; i < ar.size(); i++)
			{
			    hmVal = ar.get(i);
			    if((hmVal != null && (hmVal.get("-1") == null || !hmVal.get("-1").equals("hasNext")))
				    && (hmVal.get(crtCol) != null && hmVal.get(crtCol).toString().equals(crtProgRefVal)))
			    {
				fillFinalHtmlMap(currMap, cols, hmVal);
			    }

			}
		    }
		}
		// if intra
		else
		{
		    if(!cols.isEmpty())
		    {
			String arCrtVal;
			/* fill the criteria in a linkedHashMap in order to easy
			 manipulate the selected crts value in the array*/
			LinkedHashMap<String, String> intraCrtsMap = new LinkedHashMap<String, String>();
			intraCrtsMap.put(intraCrtVal, intraCrtVal);
			for(int i = 0; i < crtsProgRefs.length; i++)
			{
			    intraCrtsMap.put(crtsProgRefs[i], crtsProgRefs[i]);
			}
			crtCol = cols.get(0).getCRITERIA_COL();
			for(int i = 0; i < ar.size(); i++)
			{
			    hmVal = ar.get(i);
			    if((hmVal != null && (hmVal.get("-1") == null || !hmVal.get("-1").equals("hasNext")))
				    && (hmVal.get(crtCol) != null && intraCrtsMap.get(hmVal.get(crtCol).toString()) != null))
			    {
				    arCrtVal = hmVal.get(crtCol).toString();
				    currMap = hm.get(arCrtVal + "~" + crtProgRefVal);
				    if(currMap==null)
				    {
					hm.put(arCrtVal + "~" + crtProgRefVal,new LinkedHashMap<String, BigDecimal>());
					currMap= hm.get(arCrtVal + "~" + crtProgRefVal);
				    }
				    fillFinalHtmlMap(currMap, cols, hmVal);
				}
			    
			}
		    }
		}

	    }
	}
	
	return constructProcessHtml(hm, maxColNb, misType,misProcCO, lblhm);
    }

    public void fillFinalHtmlMap(LinkedHashMap<String, BigDecimal> currMap, List<REP_MISMATCH_COLUMNCO> cols,
	    HashMap<String, Object> hmVal)
    {
	BigDecimal relColVal;
	BigDecimal arRelColVal;
	REP_MISMATCH_COLUMNCO colCO;
	String relCol;
	for(int k = 0; k < cols.size(); k++)
	{
	    colCO = cols.get(k);
	    relCol = colCO.getRepMismatchColumnVO().getRELATED_COLUMN();
	    relColVal = currMap.get(relCol);
	    arRelColVal = (BigDecimal) hmVal.get(relCol);
	    if(relColVal == null)
	    {
		if(arRelColVal == null)
		{
		    currMap.put(relCol, BigDecimal.ZERO);
		}
		else
		{
		    currMap.put(relCol, arRelColVal);
		}
	    }
	    else
	    {
		// add currentVal to previous val
		int val = arRelColVal.intValue() + relColVal.intValue();
		currMap.put(relCol, new BigDecimal(val));
	    }
	}
    }

    public StringBuffer constructProcessHtml(LinkedHashMap<String, LinkedHashMap<String, BigDecimal>> hm, int maxColNb,
	    BigDecimal misType,REP_MISMATCH_PROCESSCO misProcCO,HashMap<String,String> lblhm)
    {
	StringBuffer sb = new StringBuffer();
	String crtProgRef;
	BigDecimal val;
	LinkedHashMap<String, BigDecimal> hmVal;
	int sum;
	int firstRowSum = 0;
	int emptyTdCnt;
	String trStyle;
	ArrayList<BigDecimal> rowArr;
	int cnt = 0;

	sb.append("<html><body>");
	//check if one or more selected progRef does not have a snapshot
	if(hm.values().contains(null))
	{
	    sb.append("<div style=\"padding-bottom:15px;\"><b><u><font size=\"2\" color=\"red\">"+misProcCO.getTRANS_MSG()+"</font></u></b></div>");
	}
	sb.append("<table width=\"100%\" >");
	// write the columns headers
	sb.append("<tr>");
	//sb.append("<td><b><u>Prog Ref</u></b></td>");
	//sb.append("<td><b><u>Criteria</u></b></td>");
	sb.append("<td><b><u>"+lblhm.get("1")+"</u></b></td>");
	sb.append("<td><b><u>"+lblhm.get("2")+ "</u></b></td>");
		
	for(int i = 0; i < maxColNb; i++)
	{
	    sb.append("<td><b><u>"+lblhm.get("3")+ i + "</u></b></td>");
	 //   sb.append("<td><b><u>Detail " + i + "</u></b></td>");
	}
	sb.append("<td><b><u>"+lblhm.get("4")+ "</u></b></td>");
	//sb.append("<td><b><u>Sum</u></b></td>");
	sb.append("</tr>");

	// write details
	for(Map.Entry<String, LinkedHashMap<String, BigDecimal>> e : hm.entrySet())
	{
	    sum = 0;
	    cnt++;
	    trStyle = "";
	    rowArr = new ArrayList<BigDecimal>();
	    crtProgRef = e.getKey();
	    hmVal = e.getValue();
	    if(hmVal==null)
	    {
		hmVal=new LinkedHashMap<String, BigDecimal>();
	    }
	    /*
	     * fill the hmVal in an arrayList in order to retrive the data by
	     * cursor when constructing the tds and calculate the sum
	     */
	    for(Map.Entry<String, BigDecimal> ee : hmVal.entrySet())
	    {
		val = ee.getValue();
		sum += val.intValue();
		rowArr.add(val);
	    }
	    /*
	     * store the first sum value and compare the other sum to it in
	     * order to highlight the values that are greater than or less than
	     */
	    if(cnt == 1)
	    {
		firstRowSum = sum;
	    }
	    else
	    {
		if(sum < firstRowSum)
		{
		    trStyle = "style=\"color:blue;\"";
		}
		if(sum > firstRowSum)
		{
		    trStyle = "style=\"color:red;\"";
		}
	    }

	    // writing details tr(s)
	    sb.append("<tr " + trStyle + ">");
	    if(BigDecimal.ONE.equals(misType))
	    {
		sb.append("<td>" + crtProgRef.split("~")[0] + "</td>");
		sb.append("<td>" + crtProgRef.split("~")[1] + "</td>");
	    }
	    else
	    {
		sb.append("<td>" + crtProgRef.split("~")[1] + "</td>");
		sb.append("<td>" + crtProgRef.split("~")[0] + "</td>");
	    }

	    emptyTdCnt = maxColNb - rowArr.size();
	    // fill tds having data
	    for(int i = 0; i < rowArr.size(); i++)
	    {
		sb.append("<td>" + rowArr.get(i) + "</td>");
	    }
	    // fill empty td
	    for(int i = 0; i < emptyTdCnt; i++)
	    {
		sb.append("<td></td>");
	    }
	    sb.append("<td>" + sum + "</td>");
	    sb.append("</tr>");
	}
	sb.append("</table></body></html>");
	return sb;
    }

    public boolean checkIfHasSnpByPeriod(REP_SNAPSHOT_INFORMATIONCO co, String frequency, Date selPeriod)
	    throws BaseException
    {
	Date retrieveDate = co.getRepSnapshotInfoVO().getRETREIVE_DATE();
	int retrieveMonth = DateUtil.pathMonth(retrieveDate);
	int retrieveYear = DateUtil.getDayYearMonth(retrieveDate, DateUtil.YEAR);

	 int periodMonth = DateUtil.pathMonth(selPeriod);
	 int periodYear = DateUtil.getDayYearMonth(selPeriod, DateUtil.YEAR);
	if(periodYear == retrieveYear)
	{
	    if(RepConstantsCommon.SNP_FREQUENCY_YEARLY.equals(frequency))
	    {
		return true;
	    }
	    else if(RepConstantsCommon.SNP_FREQUENCY_SEMI_ANNUALLY.equals(frequency))
	    {
		if((periodMonth <= 6 && retrieveMonth <= 6) || (periodMonth > 6 && retrieveMonth > 6))
		{
		    return true;
		}
	    }
	    else if(RepConstantsCommon.SNP_FREQUENCY_QUARTERLY.equals(frequency))
	    {
		if((periodMonth <= 3 && retrieveMonth <= 3)
			|| (periodMonth > 3 && periodMonth <= 6 && retrieveMonth > 3 && retrieveMonth <= 6)
			|| (periodMonth > 6 && periodMonth <= 9 && retrieveMonth > 6 && retrieveMonth <= 9)
			|| (periodMonth > 9 && periodMonth <= 12 && retrieveMonth > 9 && retrieveMonth <= 12))
		{
		    return true;
		}
	    }
	    else if(RepConstantsCommon.SNP_FREQUENCY_MONTHLY.equals(frequency) && periodMonth == retrieveMonth)
	    {
		return true;
	    }
	}
	return false;
    }

}
