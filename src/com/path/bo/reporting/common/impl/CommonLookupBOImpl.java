package com.path.bo.reporting.common.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.CashedConstantsCommonRep;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.QueryBO;
import com.path.dao.reporting.common.CommonLookupDAO;
import com.path.dbmaps.vo.FTR_OPTVO;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_QUERY_EXEC_LOGVO;
import com.path.dbmaps.vo.SYS_PARAM_LOV_TRANSVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.SecurityUtils;
import com.path.lib.common.util.StringUtil;
import com.path.struts2.lib.common.ConnectionCO;
import com.path.vo.common.COUNTRYSC;
import com.path.vo.common.CURRENCIESCO;
import com.path.vo.reporting.DynLookupSC;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.filterCriteria.S_ADDITIONS_OPTIONSSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public class CommonLookupBOImpl extends BaseBO implements CommonLookupBO {
	private CommonLookupDAO commonLookupDAO;
	private QueryBO queryBO;

	public CommonLookupDAO getCommonLookupDAO() {
		return commonLookupDAO;
	}

	public void setCommonLookupDAO(CommonLookupDAO commonLookupDAO) {
		this.commonLookupDAO = commonLookupDAO;
	}

	public List<Object> getLookupList(Object obj) throws BaseException {
		return commonLookupDAO.getLookupList(obj);
	}
	
	public void setQueryBO(QueryBO queryBO) {
		this.queryBO = queryBO;
	}

	@Override
	public List<CommonDetailsVO> getFilterCrtList(TmplGridSC tmplGridS)
			throws BaseException {
		return commonLookupDAO.getFilterCrtList(tmplGridS);
	}

	@Override
	public int getFilterCrtListCount(TmplGridSC tmplGridS) throws BaseException {
		return commonLookupDAO.getFilterCrtListCount(tmplGridS);
	}

	@Override
	public CommonDetailsVO getFilterCrtDependency(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.getFilterCrtDependency(tmplGridSC);
	}

	public CommonDetailsVO getCompDependency(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.getCompDependency(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxBranch(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMaxBranch(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinBranch(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMinBranch(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxDivision(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMaxDivision(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinDivision(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMinDivision(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxDepartment(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMaxDepartment(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinDepartment(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMinDepartment(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxUnit(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMaxUnit(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinUnit(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMinUnit(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxCurrency(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMaxCurrency(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinCurrency(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMinCurrency(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxRegion(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMaxRegion(tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinRegion(TmplGridSC tmplGridSC)
			throws BaseException {
		return commonLookupDAO.findMinRegion(tmplGridSC);
	}

	@Override
	public CURRENCIESCO returnCompCurrencyDet(BigDecimal companyCode)
			throws BaseException {
		return commonLookupDAO.returnCompCurrencyDet(companyCode);
	}

	@Override
	public BigDecimal returnConnectionId(String appName) throws BaseException {
		return commonLookupDAO.returnConnectionId(appName);
	}

	@Override
	public List<IRP_CONNECTIONSVO> returnConnectionsList() throws BaseException {
		return commonLookupDAO.returnConnectionsList();
	}

	@Override
	public IRP_CONNECTIONSVO returnConnById(BigDecimal dbConn)
			throws BaseException {
		return commonLookupDAO.returnConnById(dbConn);
	}


	public ConnectionCO returnConnection(BigDecimal connId)throws BaseException {
		ConnectionCO connCO = new ConnectionCO();
		try 
		{
			IRP_CONNECTIONSVO irpConVO = returnConnById(connId);
			connCO.setDbUserName(irpConVO.getUSER_ID());
			connCO.setDbName(irpConVO.getUSER_ID());
			connCO.setDbPassword(SecurityUtils.decodeB64(irpConVO.getDB_PASS()));
			connCO.setDbURL(irpConVO.getURL());
			Class.forName(irpConVO.getDBMS());
		}
		catch (ClassNotFoundException e)
		{
			throw new BOException(e);
		}
    		return connCO;
	}
	@Override
	public ArrayList<LinkedHashMap> returnQryResult(DynLookupSC dynLookupSC)
			throws BaseException, ClassNotFoundException, IOException,
			SQLException 
	{
		IRP_AD_HOC_QUERYCO query = queryBO.returnQueryById(new BigDecimal(dynLookupSC.getQryId()), true);
		IRP_QUERY_EXEC_LOGVO queryExecLogVO = null;
    	if(dynLookupSC.getConnId()!=null)
    	{
    	    dynLookupSC.setConnCO(returnConnection(dynLookupSC.getConnId()));
    	}
    	else
    	{
    		if(!NumberUtil.isEmptyDecimal(query.getCONN_ID()))
    		{
    			dynLookupSC.setConnCO(returnConnection(query.getCONN_ID()));
    		}
    	}
    	
    	ArrayList<LinkedHashMap> resultList = new ArrayList<>();
    	boolean queryLog = false;
		try 
    	{
			if(BigDecimal.ONE.toPlainString().equals(PathPropertyUtil.getPathRemotingProp("PathRepRemoting", "reporting.queryExec.log")))
		    {
				queryLog = true;
				queryExecLogVO = constructQueryLogVOFromSC(dynLookupSC);
				queryExecLogVO.setQUERY_ID(query.getQUERY_ID());
				queryExecLogVO.setCOUNT_LIST_YN(ConstantsCommon.ONE);
	    		queryExecLogVO.setEXEC_START_TIME(Calendar.getInstance().getTime());
	    		if(!NumberUtil.isEmptyDecimal(query.getCONN_ID()))
	    		{
	    			queryExecLogVO.setCONN_ID(query.getCONN_ID());
	    		}
	    		queryBO.insertQueryExecLog_NewTrans(queryExecLogVO);
		    }
    		resultList = commonLookupDAO.returnQryResult(dynLookupSC);
		} catch (Exception e) 
    	{
			if(queryLog)
		    {
				if(e.toString().length() > 1000)
			    {
					queryExecLogVO.setQUERY_EXCEPTION(e.toString().substring(1, 1000));
			    }
			    else
			    {
			    	queryExecLogVO.setQUERY_EXCEPTION(e.toString());
			    }
		    }
			throw new BaseException(e.getMessage() , e);
		}
    	finally 
    	{
    		if(queryLog)
		    {
				queryExecLogVO.setEXEC_END_TIME(Calendar.getInstance().getTime());
				queryBO.updateQueryExecLog(queryExecLogVO);
		    }
		}
		return resultList;
	}
	
	public IRP_QUERY_EXEC_LOGVO constructQueryLogVOFromSC(DynLookupSC dynLookupSC)
	{
		IRP_QUERY_EXEC_LOGVO queryExecLogVO = new IRP_QUERY_EXEC_LOGVO();
		queryExecLogVO.setCOMP_CODE(NumberUtil.emptyDecimalToNull(dynLookupSC.getCompCode()));
		queryExecLogVO.setBRANCH_CODE(NumberUtil.emptyDecimalToNull(dynLookupSC.getBranchCode()));
		queryExecLogVO.setAPP_NAME(StringUtil.nullToEmpty(dynLookupSC.getCurrAppName()));
		queryExecLogVO.setUSER_ID(StringUtil.nullToEmpty(dynLookupSC.getUserId()));
		queryExecLogVO.setSOURCE_ID(dynLookupSC.getSourceId());
		return queryExecLogVO;
	}

	@Override
	public Integer returnQryResultCnt(DynLookupSC dynLookupSC)
			throws BaseException, ClassNotFoundException, IOException,
			SQLException 
	{
		IRP_AD_HOC_QUERYCO query = queryBO.returnQueryById(new BigDecimal(dynLookupSC.getQryId()), true);
		IRP_QUERY_EXEC_LOGVO queryExecLogVO = null;
    	if(dynLookupSC.getConnId()!=null)
    	{
    	    dynLookupSC.setConnCO(returnConnection(dynLookupSC.getConnId()));
    	}
    	else
    	{
    		if(!NumberUtil.isEmptyDecimal(query.getCONN_ID()))
    		{
    			dynLookupSC.setConnCO(returnConnection(query.getCONN_ID()));
    		}
    	}
    	
    	int count = 0;
    	boolean queryLog = false;
    	try 
    	{
    		if(BigDecimal.ONE.toPlainString().equals(PathPropertyUtil.getPathRemotingProp("PathRepRemoting", "reporting.queryExec.log")))
		    {
    			queryLog = true;
    			queryExecLogVO = constructQueryLogVOFromSC(dynLookupSC);
    			queryExecLogVO.setQUERY_ID(query.getQUERY_ID());
    			queryExecLogVO.setEXEC_START_TIME(Calendar.getInstance().getTime());
    			queryExecLogVO.setCOUNT_LIST_YN(ConstantsCommon.ZERO);
    			if(!NumberUtil.isEmptyDecimal(query.getCONN_ID()))
	    		{
	    			queryExecLogVO.setCONN_ID(query.getCONN_ID());
	    		}
    			queryBO.insertQueryExecLog_NewTrans(queryExecLogVO);
		    }
			count = commonLookupDAO.returnQryResultCnt(dynLookupSC);
		} catch (Exception e) 
    	{
			if(queryLog)
		    {
				if(e.toString().length() > 1000)
			    {
					queryExecLogVO.setQUERY_EXCEPTION(e.toString().substring(1, 1000));
			    }
			    else
			    {
			    	queryExecLogVO.setQUERY_EXCEPTION(e.toString());
			    }
		    }
			throw new BaseException(e.getMessage() , e);
		}
    	finally 
    	{
    		if(queryLog)
		    {
				queryExecLogVO.setEXEC_END_TIME(Calendar.getInstance().getTime());
				queryBO.updateQueryExecLog(queryExecLogVO);
		    }
		}
		return count;
	}
	
	@Override
	public List<String[]> returnColsList(String qryId) throws BaseException,
			ClassNotFoundException, IOException, SQLException {
		return commonLookupDAO.returnColsList(qryId);
	}

	public S_ADDITIONS_OPTIONSVO getAdditionsOptionsDependency(
			S_ADDITIONS_OPTIONSVO sAdditionsOptionsVO) throws BaseException {
		return commonLookupDAO
				.getAdditionsOptionsDependency(sAdditionsOptionsVO);
	}

	public List<S_ADDITIONS_OPTIONSVO> getAdditionsOptionsList(
			S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws BaseException {
		return commonLookupDAO.getAdditionsOptionsList(sAdditionsOptionsSC);
	}

	public int getAdditionsOptionsListCount(
			S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws BaseException {
		return commonLookupDAO
				.getAdditionsOptionsListCount(sAdditionsOptionsSC);
	}

	public String getSmartText(S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC)
			throws BaseException {
		return commonLookupDAO.getSmartText(sAdditionsOptionsSC);
	}

	public String getCountryDesc(COUNTRYSC countrySC) throws BaseException {
		return commonLookupDAO.getCountryDesc(countrySC);
	}

	public GLSHEADERVO returnTemplateHeaderVal(BigDecimal tempHeaderCode) throws BaseException
	{
	 if(!CashedConstantsCommonRep.tempHeaderHm.containsKey(tempHeaderCode))
	 {
	     List<GLSHEADERVO> lst =commonLookupDAO.returnTemplateHeaderVal(tempHeaderCode);
	     for(int i=0;i<lst.size();i++)
	     {
		 CashedConstantsCommonRep.tempHeaderHm.put(lst.get(i).getCODE(),lst.get(i));
		 
	     }
	 }
	 
	 return CashedConstantsCommonRep.tempHeaderHm.get(tempHeaderCode);
	}
	
	
	public int getReportsCount(CsvItemsSC csvItemsSC) throws BaseException
	{
	    return commonLookupDAO.getReportsCount(csvItemsSC);
	}

	public List<CsvItemsCO> getReportsList(CsvItemsSC csvItemsSC) throws BaseException
	{
	    return commonLookupDAO.getReportsList(csvItemsSC);
	}

    public boolean retIfIsFcrReport(String progRef,String repAppName) throws BaseException
    {
	boolean retVal = false;
	if(progRef!=null && progRef.endsWith(ConstantsCommon.OPT_FCR_EXTENSION))
	{
	    String pRef =progRef.substring(0, progRef.length() - 3);
	    FTR_OPTCO ftrOptCO=new FTR_OPTCO();
	    ftrOptCO.getFtrOptVO().setPROG_REF(pRef);
	    ftrOptCO.getFtrOptVO().setAPP_NAME(repAppName);
	    FTR_OPTVO vo = commonLookupDAO.retIfIsFcrReport(ftrOptCO);
	    if(vo != null)
	    {
		retVal = true;
	    }
	}
	return retVal;
    }
    
	public List<IRP_CONNECTIONSVO> returnConnectionsListByApp(IRP_CONNECTIONSSC conSC) throws BaseException {
		return commonLookupDAO.returnConnectionsListByApp(conSC);
	}

	public String retSchedDateParamLovVal(String lang) throws BaseException
	{
	    SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setLOV_TYPE_ID(RepConstantsCommon.REP_SCHEDULE_DATE_PARAM);
	    sysVO.setLANG_CODE(lang);
	    ArrayList<SYS_PARAM_LOV_TRANSVO> lstSchedDateParamLst=(ArrayList) getLookupList(sysVO);
	    String retVal="";
	    if(!lstSchedDateParamLst.isEmpty())
	    {
		retVal=lstSchedDateParamLst.get(0).getVALUE_DESC();
	    }
	    return retVal;
	}

	public String retSysDateParamLovVal(String lang) throws BaseException
	{
	    SYS_PARAM_LOV_TRANSVO sysVO = new SYS_PARAM_LOV_TRANSVO();
	    sysVO.setLOV_TYPE_ID(RepConstantsCommon.REP_SYSTEM_DATE_PARAM);
	    sysVO.setLANG_CODE(lang);
	    ArrayList<SYS_PARAM_LOV_TRANSVO> lstSysDateParamLst=(ArrayList) getLookupList(sysVO);
	    String retVal="";
	    if(!lstSysDateParamLst.isEmpty())
	    {
		retVal=lstSysDateParamLst.get(0).getVALUE_DESC();
	    }
	    return retVal;
	}
	
	public List<CommonDetailsVO> getRegionList(CommonDetailsSC commDet) throws BaseException
	{
	    return commonLookupDAO.getRegionList(commDet);
	}

	public int getRegionListCount(CommonDetailsSC commDet) throws BaseException
	{
	    return commonLookupDAO.getRegionListCount(commDet);
	}
	
	public String replaceQryArgByVal(DynLookupSC dynLookupSC) throws BaseException
	{
		return commonLookupDAO.replaceQryArgByVal(dynLookupSC);
	}
}
