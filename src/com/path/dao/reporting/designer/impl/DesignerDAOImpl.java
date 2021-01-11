package com.path.dao.reporting.designer.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.designer.DesignerDAO;
import com.path.dbmaps.vo.API_SCR_LOGVO;
import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVOWithBLOBs;
import com.path.dbmaps.vo.IRP_CLIENT_REPORTVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_FOLDERVO;
import com.path.dbmaps.vo.IRP_HASH_TABLEVO;
import com.path.dbmaps.vo.IRP_PROCVO;
import com.path.dbmaps.vo.IRP_QUERY_ARG_MAPPINGVO;
import com.path.dbmaps.vo.IRP_REPORT_EXEC_LOGVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTSVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTS_FILTERSVO;
import com.path.dbmaps.vo.IRP_REP_ARG_CONSTRAINTSVO;
import com.path.dbmaps.vo.IRP_REP_IMAGESVO;
import com.path.dbmaps.vo.IRP_REP_METADATA_LOGVO;
import com.path.dbmaps.vo.IRP_SUB_REPORTVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.OPTVOKey;
import com.path.dbmaps.vo.REP_INFOVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.lib.common.util.NumberUtil;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_CLIENT_REPORTCO;
import com.path.vo.reporting.IRP_CLIENT_REPORTSC;
import com.path.vo.reporting.IRP_HASH_TABLECO;
import com.path.vo.reporting.IRP_HASH_TABLESC;
import com.path.vo.reporting.IRP_QUERY_ARG_MAPPINGCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_FILTERSSC;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.IRP_SUB_REPORTSC;
import com.path.vo.reporting.PTH_CLIENTSCO;
import com.path.vo.reporting.PTH_CLIENTSSC;
import com.path.vo.reporting.USER_TAB_COLSCO;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;

public class DesignerDAOImpl extends BaseDAO implements DesignerDAO {

	@Override
	public BigDecimal insert(IRP_AD_HOC_REPORTVOWithBLOBs vo) throws DAOException {
		getSqlMap().insert("designer.insertReport", vo);
		return vo.getREPORT_ID();
	}

	@Override
	public int getReportsCount(IRP_AD_HOC_REPORTSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "designer.reportResultMap");
		int nb = 0;
		if(sc.getProfType()==null)
		{
			nb = ((Integer) getSqlMap().queryForObject("designer.allReportsCnt", sc))
			.intValue();
		}
		else
		{
			nb = ((Integer) getSqlMap().queryForObject("designer.reportsCnt", sc))
					.intValue();
		}
		return nb;
	}

	@Override
	public List<IRP_AD_HOC_REPORTCO> getReportsList(IRP_AD_HOC_REPORTSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "designer.reportResultMap");
		if(sc.getSortOrder()==null) //to be compatible with sybase since it does not work with a nested order by
		{
			sc.setSortOrder("ORDER BY REPORT_ID");
		}
		if(sc.getProfType()==null)
		{
		    if(sc.isIS_DEPENDENCY())
		    {
			return getSqlMap().queryForList("designer.selectAllReports", sc);
		    }
		    else
		    {
			return getSqlMap().queryForList("designer.selectAllReports", sc,sc.getRecToskip(), sc.getNbRec());
		    }
		}
		else
		{ 
		    if(sc.isIS_DEPENDENCY())
		    {
			return getSqlMap().queryForList("designer.selectReports", sc);
		    }
		    	else
		    {
		    	return getSqlMap().queryForList("designer.selectReports", sc,sc.getRecToskip(), sc.getNbRec());
	    	    }
		}
	}

	@Override
	public void deleteReport(List<BigDecimal> reprotsId) throws DAOException {
	    
		getSqlMap().delete("designer.deleteReport", reprotsId);	
	}
	
	@Override
	public void deleteSubReport(List<BigDecimal> reprotsId) throws DAOException {
	    
	    	getSqlMap().delete("designer.deleteSubReport", reprotsId);	
	}
	
	@Override
	public void deleteLinkQuery(List<BigDecimal> reprotsId) throws DAOException {
	    
	    	getSqlMap().delete("designer.deleteLinkQuery", reprotsId);	
	}
	
	@Override
	public boolean[] validateReportInfo(IRP_AD_HOC_REPORTSC reportSC) throws DAOException {		
	    	//30-06-2014 AS and BBE: As per Joseph Merhej, remove the checking on report name 
		boolean repNameExists = false;//(getSqlMap().queryForObject("designer.selectReportByName", reportSC) == null)? false : true;
		boolean refExists = (getSqlMap().queryForObject("designer.selectReportByRef", reportSC) == null)? false : true;
		return new boolean[]{repNameExists,refExists};
	}

	public IRP_AD_HOC_REPORTCO returnReportById(BigDecimal reportId) throws DAOException {
		return (IRP_AD_HOC_REPORTCO) getSqlMap().queryForObject("designer.selectReportById", reportId);
	}
	@Override
	public IRP_AD_HOC_REPORTCO findReportById(BigDecimal reportId) throws DAOException {
		return (IRP_AD_HOC_REPORTCO) getSqlMap().queryForObject("designer.selectReportById", reportId);
	}

	@Override
	public IRP_AD_HOC_REPORTCO returnReportByRef(IRP_AD_HOC_REPORTSC reportSC)	throws DAOException {
		return (IRP_AD_HOC_REPORTCO) (getSqlMap().queryForObject("designer.returnReportXMLByRef", reportSC));
	}

	public List<IRP_AD_HOC_QUERYVO> retQueriesByReport(IRP_AD_HOC_REPORTSC reportSC) throws DAOException 
	{
		return getSqlMap().queryForList("designer.retQueriesByReport",reportSC);
	}

	public IRP_AD_HOC_REPORTCO retProgRefByRepId(IRP_AD_HOC_REPORTSC reportSC)throws DAOException 
	{
		return (IRP_AD_HOC_REPORTCO) (getSqlMap().queryForObject("designer.retProgRefByRepId", reportSC));
	}

	@Override
	public List<IRP_REP_ARGUMENTSCO> argsByReportId(BigDecimal reportId)
			throws DAOException {
		IRP_AD_HOC_REPORTSC sc = new IRP_AD_HOC_REPORTSC();
        sc.setREPORT_ID(reportId);
        if(ConstantsCommon.CURR_DBMS_SYBASE == 1)
        {
            sc.setIsOracle(0);
        }
        else
        {
            sc.setIsOracle(1);
        }

		return getSqlMap().queryForList("designer.selectArgsByReportId", sc);
	}

	@Override
	public List<IRP_QUERY_ARG_MAPPINGCO> returnQryArgMapLst(IRP_QUERY_ARG_MAPPINGCO irpQryArgMapCO)
			throws DAOException {
		return getSqlMap().queryForList("designer.selectQryArgMapLst", irpQryArgMapCO);
	}
	
	
	@Override
	public void deleteArguments(List<BigDecimal> reprotsId) throws DAOException {
		getSqlMap().delete("designer.deleteArguments", reprotsId);
	}
	
	@Override
	public void deleteLinkQryArgsMap(IRP_QUERY_ARG_MAPPINGVO irpQryArgMapVO) throws DAOException {
		getSqlMap().delete("designer.deleteLinkQryArgsMap", irpQryArgMapVO);
	}

	@Override
	public List<IRP_REP_ARGUMENTSCO> retArgsByReport(IRP_AD_HOC_REPORTSC reportSC) throws DAOException {
		return getSqlMap().queryForList("designer.retArgsByReport",reportSC);
	}

	@Override
	public BigDecimal insertArgument(IRP_REP_ARGUMENTSVO argument) throws DAOException {
		getSqlMap().insert("designer.insertArgument", argument);
		return argument.getARGUMENT_ID();
	}

	public IRP_AD_HOC_REPORTCO retRepIdByProgRef(IRP_AD_HOC_REPORTSC reportSC)	throws DAOException 
	{
		return (IRP_AD_HOC_REPORTCO)(getSqlMap().queryForObject("designer.retRepIdByProgRef", reportSC));
	}

	public int selectReportByRef(IRP_AD_HOC_REPORTSC  sc) throws DAOException 
	{
		Integer retVal= (Integer)getSqlMap().queryForObject("designer.selectReportByRef", sc);
		if(retVal==null)
		{
			return 0;
		}
		else
		{
			return retVal.intValue();
		}
	}

	public IRP_AD_HOC_REPORTCO retReportFlags(IRP_AD_HOC_REPORTSC repSC)throws DAOException 
	{
		return  (IRP_AD_HOC_REPORTCO)(getSqlMap().queryForObject("designer.retReportFlags", repSC));
	}

	public int retPbAppCnt() throws DAOException 
	{
		return ((Integer) getSqlMap().queryForObject("designer.retPbAppCnt", null)).intValue();
	}

	public void deleteRepCatalogue(List<BigDecimal> reprotsId)throws DAOException 
	{
		getSqlMap().delete("designer.deleteRepCatalogue", reprotsId);	
	}

	public void deleteRepInfo(List<BigDecimal> reprotsId) throws DAOException
	{
		getSqlMap().delete("designer.deleteRepInfo", reprotsId);	
	}

	public BigDecimal retOldRepIdFromRepId(BigDecimal repId)  throws DAOException
	{
		return ((BigDecimal) getSqlMap().queryForObject("designer.retOldRepIdFromRepId", repId));
	}

        public BigDecimal insertRepInfo(REP_INFOVO infoVO) throws DAOException
        {
    	insert(infoVO);
    
    	return infoVO.getREP_ID();
    
        }

	public BigDecimal retRepIdFromOldRepId(BigDecimal oldRepId)throws DAOException 
	{
		return (BigDecimal)getSqlMap().queryForObject("designer.retRepIdFromOldRepId", oldRepId) ;
	}

	public Integer updateReport(IRP_AD_HOC_REPORTVOWithBLOBs vo) throws DAOException 
	{
	    return getSqlMap().update("designer.updateReport", vo);
	}

	public IRP_AD_HOC_REPORTCO retrieveReportVO(BigDecimal repId)throws DAOException 
	{
		return (IRP_AD_HOC_REPORTCO)getSqlMap().queryForObject("designer.retrieveReportVO", repId) ;
	}

	@Override
	public List<IRP_SUB_REPORTCO> returnSubreports(BigDecimal reportId) throws DAOException
	{
	    return getSqlMap().queryForList("designer.selectSubreports", reportId);
	}
	
	public String checkIfSubRepUploaded(String subRepName) throws DAOException
	{
	    return (String) getSqlMap().queryForObject("designer.checkIfSubRepUploaded", subRepName);
	}
	
	
	public List<IRP_SUB_REPORTVO> retSubRepList(IRP_SUB_REPORTSC subrepSC)throws DAOException 
	{
		
		DAOHelper.fixGridMaps(subrepSC, getSqlMap(), "connection.retSubRepListMap");
		return  getSqlMap().queryForList("designer.retSubRepList", subrepSC,subrepSC.getRecToskip(),subrepSC.getNbRec());

	}

	public int retSubRepListCount(IRP_SUB_REPORTSC subrepSC)throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("designer.retSubRepListCount", subrepSC)).intValue();
		return nb;
	}
	public void saveSubRep(IRP_AD_HOC_REPORTCO reportCO) throws DAOException 
	{
	    BigDecimal parentRepId= reportCO.getREPORT_ID();
	    if(parentRepId ==null && reportCO.getSubreportsList().size()>0)
	    {
		 parentRepId = reportCO.getSubreportsList().get(0).getPARENT_REPORT_ID();
	    }
	    if(parentRepId !=(null))
	    {
		getSqlMap().delete("designer.deleteSubRep", parentRepId);
		for(int i=0;i< reportCO.getSubreportsList().size();i++)
		{
		   // if(reportCO.getSubreportsList().get(i).getIrpSubReportVO().getREPORT_ID()!=null)
		    {
			IRP_SUB_REPORTVO theVO = reportCO.getSubreportsList().get(i).getIrpSubReportVO();
			IRP_SUB_REPORTCO theCO = new IRP_SUB_REPORTCO();
			theCO.setPARENT_REPORT_ID(parentRepId);
			theCO.setIrpSubReportVO(theVO);
			//theCO.setSUB_REPORT_EXPRESSION(reportCO.getSubreportsList().get(i).getSUB_REPORT_EXPRESSION());
			//theCO.setSUB_REPORT_EXPRESSION(reportCO.getSubreportsList().get(i).getIrpSubReportVO().getSUB_REPORT_EXPRESSION());
			getSqlMap().insert("designer.insertSubReport", theCO);
		    }
		}
	    }
	}

	public void deleteReportHyperlinks(List<BigDecimal> reprotsId) throws DAOException 
	{
		getSqlMap().delete("designer.deleteReportHyperlinks", reprotsId);	
	}
	
	
	public List<IRP_REP_ARGUMENTSCO> getSubRepParamsList(IRP_SUB_REPORTSC subrepSC)throws DAOException 
	{
		if(subrepSC.getIsGrid())
		{
		    DAOHelper.fixGridMaps(subrepSC, getSqlMap(), "designer.getSubRepParamsMap");
		    return  getSqlMap().queryForList("designer.getSubRepParamsList", subrepSC,subrepSC.getRecToskip(),subrepSC.getNbRec());
		}
		else
		{
		    return  getSqlMap().queryForList("designer.getSubRepParamsList", subrepSC);
		}

	}

	public int getSubRepParamsCount(IRP_SUB_REPORTSC subrepSC)throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("designer.getSubRepParamsCount", subrepSC)).intValue();
		return nb;
	}

	public List<BigDecimal> retSubReportUsage(List<BigDecimal> reportsId)throws DAOException 
	{
		return  getSqlMap().queryForList("designer.retSubReportUsage", reportsId);
	}
	
	public List<IRP_QUERY_ARG_MAPPINGCO> retQryArgMapping(IRP_AD_HOC_REPORTSC reportSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(reportSC, getSqlMap(), "designer.retQryArgMappingListMap");
		return  getSqlMap().queryForList("designer.retQryArgMappingList", reportSC);
	}
	
	public List<IRP_REP_ARGUMENTSVO> retRepArgDepList(IRP_AD_HOC_REPORTSC reportSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(reportSC, getSqlMap(), "designer.retRepArgDepListMap");
		return  getSqlMap().queryForList("designer.retRepArgDepList", reportSC);
	}
	
	
	public List<IRP_HASH_TABLEVO> retHashTablList(IRP_HASH_TABLESC irpHashTblSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(irpHashTblSC, getSqlMap(), "designer.retHashTablListMap");
		return getSqlMap().queryForList("designer.retHashTablList", irpHashTblSC,irpHashTblSC.getRecToskip(),irpHashTblSC.getNbRec());
	}

	public int retHashTablListCount(IRP_HASH_TABLESC irpHashTblSC)throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("designer.retHashTablListCount", irpHashTblSC));
		return nb;
	}

	public void saveHashTbl(IRP_AD_HOC_REPORTCO reportCO) throws DAOException 
	{
	    
	    if(!NumberUtil.isEmptyDecimal(reportCO.getREPORT_ID()))
	    {
		getSqlMap().delete("designer.deleteHashTbl", reportCO.getREPORT_ID());
	    }
		for(int i=0;i< reportCO.getHashTblList().size();i++)
		{
		    if(!NumberUtil.isEmptyDecimal(reportCO.getHashTblList().get(i).getIrpHashTableVO().getHASH_TABLE_ID()))
		    {
			IRP_HASH_TABLECO theCO = reportCO.getHashTblList().get(i);
			getSqlMap().insert("designer.insertHashTbl", theCO);
		    }
		}
	}
	
	public List<IRP_HASH_TABLECO> retHashTablGrid(IRP_HASH_TABLESC irpHashTblSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(irpHashTblSC, getSqlMap(), "designer.retHashTablGridMap");
		return getSqlMap().queryForList("designer.retHashTablGrid", irpHashTblSC);
		//ArrayList<IRP_HASH_TABLECO> lst = (ArrayList<IRP_HASH_TABLECO>) getSqlMap().queryForList("designer.selectSubreports", irpHashTblSC);

	}
	
	public void insertRepTranslation(IRP_AD_HOC_REPORTCO reportCO)throws DAOException
	{
	    getSqlMap().insert("designer.insertSysParamKey", reportCO);
	    getSqlMap().insert("designer.insertSysParamKeyTrans", reportCO);
	}
	
	public String returnXslName(IRP_AD_HOC_REPORTSC reptSC) throws DAOException
	{
	    return (String) getSqlMap().queryForObject("designer.returnXslName", reptSC);
	}
	
	public List<USER_TAB_COLSCO> returnTblColsName(USER_TAB_COLSCO userTabColsCO) throws DAOException
	{
	    return getSqlMap().queryForList("designer.returnTblColsName",userTabColsCO);
	}
	
	public List<Object> returnAllOptsLst(OPTVO optVO,List<Object> optLstVO) throws DAOException
	{
	     List<OPTVO> tempLst = getSqlMap().queryForList("designer.returnAllOptsLst", optVO);
	     if(!tempLst.isEmpty())
	     {
		 optVO.setPROG_REF(tempLst.get(0).getPARENT_REF());
		 optLstVO.add(tempLst.get(0));
		 if(!RepConstantsCommon.ROOT.equals(tempLst.get(0).getPARENT_REF()))
		 {
		     tempLst.clear();
		     returnAllOptsLst(optVO,optLstVO);
		 }
	     }
	     return optLstVO;
	}

	public List<OPTVO> retExistingOpts(FTR_OPTCO co) throws DAOException
	{
	    return getSqlMap().queryForList("designer.retExistingOpts", co);
	}
	
	public boolean checkIfOptInAdHocReport(OPTVOKey optVOKey) throws DAOException
	{
	    return (((Integer)getSqlMap().queryForObject("designer.checkIfOptInAdHocReport", optVOKey)).intValue() > 0);
	}
	
	public void deleteRepHashTable(List<BigDecimal> reprotsId) throws DAOException
	{
		getSqlMap().delete("designer.deleteRepHashTable", reprotsId);	
	}
	public IRP_CONNECTIONSVO retConnection(IRP_CONNECTIONSVO conVO) throws DAOException
	{
		return ((IRP_CONNECTIONSVO) getSqlMap().queryForObject("designer.retConnection", conVO));
	}
	
	public IRP_PROCVO retProc(IRP_PROCVO procVO) throws DAOException
	{
		return ((IRP_PROCVO) getSqlMap().queryForObject("designer.retProc", procVO));
	}
	
	public IRP_HASH_TABLEVO retHashTbl(IRP_HASH_TABLEVO hashTblVO) throws DAOException
	{
		return ((IRP_HASH_TABLEVO) getSqlMap().queryForObject("designer.retHashTbl", hashTblVO));
	}
	//insert the hash table and return his key
	public BigDecimal insertHashTable(IRP_HASH_TABLEVO hashTblVO) throws DAOException {
		getSqlMap().insert("designer.insertHashTable", hashTblVO);
		return hashTblVO.getHASH_TABLE_ID();
	}
	
	public List<IRP_PROCVO>retProcLstFromPackage(List<String> procNameLst)throws DAOException{
	    
	if(ConstantsCommon.CURR_DBMS_SYBASE == 1)
	{
	    return getSqlMap().queryForList("designer.retProcLstFromSybPackage", procNameLst);
	}
	else
	{
	    return getSqlMap().queryForList("designer.retProcLstFromOraPackage", procNameLst);
	}
	    
	   
	}
	
	public IRP_AD_HOC_QUERYVO retTemplateQry(IRP_AD_HOC_QUERYVO qryVO) throws DAOException
	{
		return ((IRP_AD_HOC_QUERYVO) getSqlMap().queryForObject("designer.retTemplateQry", qryVO));
	}
	
	public IRP_AD_HOC_REPORTVOWithBLOBs retExistRepInDb(IRP_AD_HOC_REPORTVOWithBLOBs repVO) throws DAOException {
	    
	    	return (IRP_AD_HOC_REPORTVOWithBLOBs) getSqlMap().queryForObject("designer.retExistRepInDb",repVO);
	}
	
	public IRP_FOLDERVO retExistFolderInDb(IRP_FOLDERVO folderVO) throws DAOException {
	    
		return (IRP_FOLDERVO) getSqlMap().queryForObject("designer.retExistFolderInDb", folderVO);
	}
	
	public String checkOptProgRef(CommonDetailsSC commonDetailsSC) throws DAOException
	{
	    String result = (String) getSqlMap().queryForObject("designer.checkOptProgRef", commonDetailsSC);
	    if(result == null || ("").equals(result))
	    {
		return "0";
	    }
	    else
	    {
		return result;
	    }
	}
	
	public List<PTH_CLIENTSCO> retRepClientLst(PTH_CLIENTSSC pthClientSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(pthClientSC, getSqlMap(), "designer.retRepClientLstMap");
		return getSqlMap().queryForList("designer.retRepClientLst", pthClientSC,pthClientSC.getRecToskip(),pthClientSC.getNbRec());
	}

	public int retRepClientLstCount(PTH_CLIENTSSC pthClientSC)throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("designer.retRepClientLstCount", pthClientSC));
		return nb;
	}
	
	public List<IRP_CLIENT_REPORTCO> returnClientReport(IRP_CLIENT_REPORTSC irpCltRepSC )throws DAOException 
	{
		 return getSqlMap().queryForList("designer.returnClientReport", irpCltRepSC);
	}
	
	public void deleteRepClientLst(IRP_CLIENT_REPORTVO irpClientReportVO) throws DAOException {
	    
		getSqlMap().delete("designer.deleteAllClientReport", irpClientReportVO);	
	}
	
	public ArrayList <IRP_AD_HOC_REPORTVO>retParentReports(BigDecimal reportId)  throws DAOException
	{
	    return (ArrayList<IRP_AD_HOC_REPORTVO>)getSqlMap().queryForList("designer.retParentReports", reportId);
	}
	
	public ArrayList<IRP_SUB_REPORTCO> retRepsBySubRepId(IRP_SUB_REPORTCO retCO) throws DAOException
	{
	    return (ArrayList<IRP_SUB_REPORTCO>)getSqlMap().queryForList("designer.selectRepsBySubRepId", retCO);
	}
	
	/**
	 * Delete constraints by report id and argument id
	**/
	public void deleteConstraints(IRP_REP_ARG_CONSTRAINTSVO constrVO) throws DAOException
	{
		getSqlMap().delete("designer.deleteConstraints", constrVO);	
	}
	
	
	/**
	 * Delete constraints by report id 
	**/
	public void deleteConstraintsAsList(List<BigDecimal> reprotsId) throws DAOException
	{
	    getSqlMap().delete("designer.deleteConstraintsAsList", reprotsId);
	}
	/**
	 * Returns the default client that has default_yn flag set to Y 
	**/
	public PTH_CLIENTSCO returnDefaultClient()throws DAOException 
	{
		return (PTH_CLIENTSCO) getSqlMap().queryForObject("designer.returnDefaultClient",null);
	}
	/**
	 * Returns the client name based on the client acronym 
	**/
	public PTH_CLIENTSCO returnClient(PTH_CLIENTSSC pthClientSC)throws DAOException
	{
		return (PTH_CLIENTSCO) getSqlMap().queryForObject("designer.returnClient",pthClientSC);
	}
	
	/**
	 * Method that reads the data from report tables and returns it in
	 * an array list of hashmaps
	 */
    public ArrayList<HashMap<String, Object>> returnDataByTable(IRP_AD_HOC_REPORTSC sc) throws DAOException
    {
	return (ArrayList<HashMap<String, Object>>) getSqlMap().queryForList("designer.returnDataByTable", sc);
    }
    
    public void insertApiScrLog(API_SCR_LOGVO apiScrLogVO)throws DAOException
    {
	  getSqlMap().insert("designer.insertScrApiLogSql", apiScrLogVO);
    }
    
    @Override
    public ArrayList<Object> retRepHyperlinksByRepId(IRP_AD_HOC_REPORTSC sc) throws DAOException
    {
	return (ArrayList<Object>) getSqlMap().queryForList("designer.retRepHyperlinksByRepId", sc);
    }
    
    @Override
    public int updateHashTable(IRP_HASH_TABLEVO irpHashTableVO) throws DAOException
    {
	return getSqlMap().update("designer.updateHashTable", irpHashTableVO);
    }
    
    @Override
    public void deleteArgumentsFilters(IRP_REP_FILTERSSC sc) throws DAOException
    {
	if(sc.getReportsIdList() == null)
	{
	    getSqlMap().delete("designer.deleteArgumentFromFilters", sc);
	}
	else
	{
	    getSqlMap().delete("designer.deleteFilterOfReport", sc);
	}
    }
    
    @Override
    public List<IRP_REP_IMAGESVO> retImagesGridsRecords(IRP_AD_HOC_REPORTSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "designer.irpRepImagesMap");
	return getSqlMap().queryForList("designer.retImagesGridsRecords", sc,sc.getRecToskip(),sc.getNbRec());
    }

    @Override
    public int retImagesGridRecordsCount(IRP_AD_HOC_REPORTSC sc) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("designer.retImagesGridRecordsCount", sc));
    }
    

    @Override
    public ArrayList<IRP_REP_ARGUMENTS_FILTERSVO> retArgumentsFiltersByReport(IRP_AD_HOC_REPORTSC sc)
	    throws DAOException
    {

	return (ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>) getSqlMap().queryForList("designer.retArgumentsFiltersByReport",
		sc);
    }

    @Override
    public BigDecimal checkArgSameNameAndType(IRP_AD_HOC_REPORTSC sc) throws DAOException
    {
	return ((BigDecimal) getSqlMap().queryForObject("designer.checkArgSameNameAndType", sc));
    }
    
    @Override
    public int checkIfAnyFilterForCurrentReport(IRP_AD_HOC_REPORTSC sc)throws DAOException
    {
	return  ((Integer) getSqlMap().queryForObject("designer.checkIfAnyFilterForCurrentReport", sc)).intValue();
    }
    

    @Override
    public IRP_AD_HOC_REPORTVOWithBLOBs retRepDetailsByRepId(IRP_AD_HOC_REPORTVOWithBLOBs repVO) throws DAOException
    {
	return (IRP_AD_HOC_REPORTVOWithBLOBs) getSqlMap().queryForObject("designer.retRepDetailsByRepId",repVO);
    }
    
    @Override
    public ArrayList<BigDecimal> retMetaDataReports(ArrayList<BigDecimal> repIdList) throws DAOException
    {
	return  (ArrayList<BigDecimal>) getSqlMap().queryForList("designer.retMetaDataReports", repIdList);
    }
    
    @Override
    public ArrayList<IRP_AD_HOC_REPORTVO> retReportsBeingMetadata(List<BigDecimal> repIdList) throws DAOException
    {
	return  (ArrayList<IRP_AD_HOC_REPORTVO>) getSqlMap().queryForList("designer.retReportsBeingMetadata", repIdList);
    }
    
    public ArrayList<IRP_REP_METADATA_LOGVO>retAlreadyExportedArgsLogs (IRP_AD_HOC_REPORTSC sc) throws DAOException
    {
	return (ArrayList<IRP_REP_METADATA_LOGVO>) getSqlMap().queryForList("designer.retAlreadyExportedArgsLogs", sc);
    }
    
    @Override
    public void updateAllCifAuditParams(BigDecimal reportId) throws DAOException
    {
	getSqlMap().update("designer.updateAllCifAuditParams", reportId);
    }
    
    @Override
    public void insertReportExecLog_NewTrans(IRP_REPORT_EXEC_LOGVO repExecLogParamsVO) throws DAOException
    {
	getSqlMap().insert("designer.insertReportExecLog_NewTrans", repExecLogParamsVO);
    }
    

}
