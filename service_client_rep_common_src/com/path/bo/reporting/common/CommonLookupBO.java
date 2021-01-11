package com.path.bo.reporting.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.exception.BaseException;
import com.path.struts2.lib.common.ConnectionCO;
import com.path.vo.common.COUNTRYSC;
import com.path.vo.common.CURRENCIESCO;
import com.path.vo.reporting.DynLookupSC;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.ftr.filterCriteria.S_ADDITIONS_OPTIONSSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public interface CommonLookupBO {
	public List<Object> getLookupList(Object obj) throws BaseException;

	public List<CommonDetailsVO> getFilterCrtList(TmplGridSC tmplGridS)
			throws BaseException;

	public int getFilterCrtListCount(TmplGridSC tmplGridS) throws BaseException;

	public CommonDetailsVO getFilterCrtDependency(TmplGridSC tmplGridSC)
			throws BaseException;

	public CommonDetailsVO getCompDependency(TmplGridSC tmplGridSC)
			throws BaseException;

	CommonDetailsVO findMaxBranch(TmplGridSC tmplGridSC) throws BaseException;

	CommonDetailsVO findMinBranch(TmplGridSC tmplGridSC) throws BaseException;

	CommonDetailsVO findMaxDivision(TmplGridSC tmplGridSC) throws BaseException;

	CommonDetailsVO findMinDivision(TmplGridSC tmplGridSC) throws BaseException;

	CommonDetailsVO findMaxDepartment(TmplGridSC tmplGridSC)
			throws BaseException;

	CommonDetailsVO findMinDepartment(TmplGridSC tmplGridSC)
			throws BaseException;

	CommonDetailsVO findMaxUnit(TmplGridSC tmplGridSC) throws BaseException;

	CommonDetailsVO findMinUnit(TmplGridSC tmplGridSC) throws BaseException;

	CommonDetailsVO findMaxCurrency(TmplGridSC tmplGridSC) throws BaseException;

	CommonDetailsVO findMinCurrency(TmplGridSC tmplGridSC) throws BaseException;

	// Ali Jaffal
	// <Start>
	CommonDetailsVO findMaxRegion(TmplGridSC tmplGridSC) throws BaseException;

	CommonDetailsVO findMinRegion(TmplGridSC tmplGridSC) throws BaseException;

	// Ali Jaffal
	// <End>
	public CURRENCIESCO returnCompCurrencyDet(BigDecimal companyCode)
			throws BaseException;

	public BigDecimal returnConnectionId(String appName) throws BaseException;

	public List<IRP_CONNECTIONSVO> returnConnectionsList() throws BaseException;

	public IRP_CONNECTIONSVO returnConnById(BigDecimal dbConn)
			throws BaseException;

	public Integer returnQryResultCnt(DynLookupSC dynLookupSC)
			throws BaseException, ClassNotFoundException, IOException,
			SQLException;

	public ArrayList<LinkedHashMap> returnQryResult(DynLookupSC dynLookupSC)
			throws BaseException, ClassNotFoundException, IOException,
			SQLException;

	public List<String[]> returnColsList(String qryId) throws BaseException,
			ClassNotFoundException, IOException, SQLException;

	public S_ADDITIONS_OPTIONSVO getAdditionsOptionsDependency(
			S_ADDITIONS_OPTIONSVO sAdditionsOptionsVO) throws BaseException;

	public List<S_ADDITIONS_OPTIONSVO> getAdditionsOptionsList(
			S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws BaseException;

	public int getAdditionsOptionsListCount(
			S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws BaseException;

	public String getSmartText(S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC)
			throws BaseException;

	public String getCountryDesc(COUNTRYSC countrySC) throws BaseException;

	public GLSHEADERVO returnTemplateHeaderVal(BigDecimal tempHeaderCode) throws BaseException;
	
	public int getReportsCount(CsvItemsSC csvItemsSC) throws BaseException;

	public List<CsvItemsCO> getReportsList(CsvItemsSC csvItemsSC) throws BaseException;
	
	public boolean retIfIsFcrReport(String progRef,String repAppName) throws BaseException;
	
	public ConnectionCO returnConnection(BigDecimal connId)throws BaseException;
	
	public List<IRP_CONNECTIONSVO> returnConnectionsListByApp(IRP_CONNECTIONSSC conSC) throws BaseException;
	
	public String retSysDateParamLovVal(String lang) throws BaseException;
	public String retSchedDateParamLovVal(String lang) throws BaseException;
	public List<CommonDetailsVO> getRegionList(CommonDetailsSC commDet) throws BaseException;

	public int getRegionListCount(CommonDetailsSC commDet) throws BaseException;
	    
	/**
	 * Methode to replace Argument of the requested query
	 * 
	 * @param dynLookupSC
	 * @return
	 * @throws BaseException
	 */
	public String replaceQryArgByVal(DynLookupSC dynLookupSC) throws BaseException;
}
