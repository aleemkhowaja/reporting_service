package com.path.dao.reporting.designer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
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

public interface DesignerDAO {
	public BigDecimal insert(IRP_AD_HOC_REPORTVOWithBLOBs vo) throws DAOException;
	public int getReportsCount(IRP_AD_HOC_REPORTSC sc) throws DAOException;
	public List<IRP_AD_HOC_REPORTCO> getReportsList(IRP_AD_HOC_REPORTSC sc) throws DAOException;
	public void deleteReport(List<BigDecimal> reprotsId) throws DAOException;
	public boolean[] validateReportInfo(IRP_AD_HOC_REPORTSC reportSC) throws DAOException;
	public IRP_AD_HOC_REPORTCO returnReportById(BigDecimal reportId) throws DAOException;
	public IRP_AD_HOC_REPORTCO findReportById(BigDecimal reportId) throws DAOException;
	public IRP_AD_HOC_REPORTCO returnReportByRef(IRP_AD_HOC_REPORTSC reportSC) throws DAOException;
	public List<IRP_AD_HOC_QUERYVO> retQueriesByReport(IRP_AD_HOC_REPORTSC reportSC) throws DAOException;
	public IRP_AD_HOC_REPORTCO retProgRefByRepId(IRP_AD_HOC_REPORTSC reportSC) throws DAOException;
	public List<IRP_REP_ARGUMENTSCO> argsByReportId(BigDecimal reportId) throws DAOException;
	public void deleteArguments(List<BigDecimal> reprotsId) throws DAOException;
	public List<IRP_REP_ARGUMENTSCO> retArgsByReport(IRP_AD_HOC_REPORTSC reportSC) throws DAOException;
	public BigDecimal insertArgument(IRP_REP_ARGUMENTSVO argument) throws DAOException;
	public IRP_AD_HOC_REPORTCO retRepIdByProgRef(IRP_AD_HOC_REPORTSC reportSC)throws DAOException;
	public int selectReportByRef(IRP_AD_HOC_REPORTSC sc)throws DAOException;
	public IRP_AD_HOC_REPORTCO retReportFlags(IRP_AD_HOC_REPORTSC repSC)throws DAOException;
	public int retPbAppCnt() throws DAOException;
	public void deleteRepInfo(List<BigDecimal> reprotsId) throws DAOException;
	public void deleteRepCatalogue(List<BigDecimal> reprotsId) throws DAOException;
	public BigDecimal retOldRepIdFromRepId(BigDecimal repId) throws DAOException;
	public BigDecimal insertRepInfo(REP_INFOVO infoVO)throws DAOException;
	public BigDecimal retRepIdFromOldRepId(BigDecimal oldRepId) throws DAOException;
	public Integer updateReport(IRP_AD_HOC_REPORTVOWithBLOBs vo)throws DAOException;
	public IRP_AD_HOC_REPORTCO retrieveReportVO(BigDecimal repId)throws DAOException;
	public List<IRP_SUB_REPORTCO> returnSubreports(BigDecimal reportId) throws DAOException;
	public String checkIfSubRepUploaded(String subRepName)throws DAOException;
	public List<IRP_SUB_REPORTVO> retSubRepList(IRP_SUB_REPORTSC subrepSC)throws DAOException;
	public int retSubRepListCount(IRP_SUB_REPORTSC subrepSC)throws DAOException;
	public void saveSubRep(IRP_AD_HOC_REPORTCO reportCO) throws DAOException;
	public void deleteReportHyperlinks(List<BigDecimal> reprotsId) throws DAOException;
	public List<IRP_REP_ARGUMENTSCO> getSubRepParamsList(IRP_SUB_REPORTSC subrepSC)throws DAOException;
	public int getSubRepParamsCount(IRP_SUB_REPORTSC subrepSC)throws DAOException;
	public List<BigDecimal> retSubReportUsage(List<BigDecimal> reportsId)throws DAOException;
	public List<IRP_QUERY_ARG_MAPPINGCO> returnQryArgMapLst(IRP_QUERY_ARG_MAPPINGCO irpQryArgMapCO)throws DAOException;
	public void deleteLinkQryArgsMap(IRP_QUERY_ARG_MAPPINGVO irpQryArgMapVO) throws DAOException;
	public List<IRP_QUERY_ARG_MAPPINGCO> retQryArgMapping(IRP_AD_HOC_REPORTSC reportSC) throws DAOException;
	public List<IRP_REP_ARGUMENTSVO> retRepArgDepList(IRP_AD_HOC_REPORTSC reportSC)throws DAOException;
	public List<IRP_HASH_TABLEVO> retHashTablList(IRP_HASH_TABLESC irpHashTblSC)throws BaseException;
	public int retHashTablListCount(IRP_HASH_TABLESC irpHashTblSC)throws BaseException;
	public void saveHashTbl(IRP_AD_HOC_REPORTCO reportCO) throws DAOException;
	public List<IRP_HASH_TABLECO> retHashTablGrid(IRP_HASH_TABLESC irpHashTblSC)throws BaseException;
	public void deleteSubReport(List<BigDecimal> reprotsId) throws DAOException;
	public void deleteLinkQuery(List<BigDecimal> reprotsId) throws DAOException;
	public void insertRepTranslation(IRP_AD_HOC_REPORTCO reportCO)throws DAOException;
	public String returnXslName(IRP_AD_HOC_REPORTSC reptSC)throws DAOException;
	public List<USER_TAB_COLSCO> returnTblColsName(USER_TAB_COLSCO userTabColsCO) throws DAOException;
	public List<Object> returnAllOptsLst(OPTVO optVO,List<Object> optLstVO) throws DAOException;
	public List<OPTVO>retExistingOpts(FTR_OPTCO co)throws DAOException;
	public boolean checkIfOptInAdHocReport(OPTVOKey optVOKey) throws DAOException;
	public IRP_CONNECTIONSVO retConnection(IRP_CONNECTIONSVO conVO) throws DAOException;
	public IRP_PROCVO retProc(IRP_PROCVO procVO) throws DAOException;
	public IRP_HASH_TABLEVO retHashTbl(IRP_HASH_TABLEVO hashTblVO) throws DAOException;
	public BigDecimal insertHashTable(IRP_HASH_TABLEVO hashTblVO)throws DAOException;
	public void deleteRepHashTable(List<BigDecimal> reprotsId) throws DAOException;
	public List<IRP_PROCVO>retProcLstFromPackage(List<String> procNameLst)throws DAOException;
	public IRP_AD_HOC_QUERYVO retTemplateQry(IRP_AD_HOC_QUERYVO qryVO) throws DAOException;
	public IRP_AD_HOC_REPORTVOWithBLOBs retExistRepInDb( IRP_AD_HOC_REPORTVOWithBLOBs repVO) throws DAOException;
	public IRP_FOLDERVO retExistFolderInDb(IRP_FOLDERVO folderVO) throws DAOException;
	public String checkOptProgRef(CommonDetailsSC commonDetailsSC) throws DAOException;
	public List<PTH_CLIENTSCO> retRepClientLst(PTH_CLIENTSSC pthClientSC)throws DAOException;
	public int retRepClientLstCount(PTH_CLIENTSSC pthClientSC)throws DAOException;
	public List<IRP_CLIENT_REPORTCO> returnClientReport(IRP_CLIENT_REPORTSC irpCltRepSC) throws DAOException;
	public void deleteRepClientLst(IRP_CLIENT_REPORTVO irpClientReportVO)throws DAOException;
	public ArrayList <IRP_AD_HOC_REPORTVO>retParentReports(BigDecimal reportId)  throws DAOException;
	public ArrayList<IRP_SUB_REPORTCO> retRepsBySubRepId(IRP_SUB_REPORTCO retCO) throws DAOException;
	public void deleteConstraints(IRP_REP_ARG_CONSTRAINTSVO constrVO) throws DAOException;
	public void deleteConstraintsAsList(List<BigDecimal> reprotsId) throws DAOException;
	public PTH_CLIENTSCO returnDefaultClient()throws DAOException;
	public PTH_CLIENTSCO returnClient(PTH_CLIENTSSC pthClientSC)throws DAOException;
	public ArrayList<HashMap<String,Object>> returnDataByTable(IRP_AD_HOC_REPORTSC sc) throws DAOException;
	public void insertApiScrLog(API_SCR_LOGVO apiScrLogVO)throws DAOException;
	/**
	 * Method that returns the hyperlinks related to several reports
	 * @param sc
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<Object> retRepHyperlinksByRepId(IRP_AD_HOC_REPORTSC sc) throws DAOException;
	/**
	 * Method that updates a hash table
	 * @param irpHashTableVO
	 * @return
	 * @throws DAOException
	 */
	public int updateHashTable(IRP_HASH_TABLEVO irpHashTableVO) throws DAOException;
	/**
	 * Method to delete arguments and filters based on the param properties
	 * @param sc
	 * @throws DAOException
	 */
	public void deleteArgumentsFilters(IRP_REP_FILTERSSC sc) throws DAOException;
	/**
	 * Method to retrieve images from db
	 * @return
	 * @throws BaseException
	 */
	 public List<IRP_REP_IMAGESVO> retImagesGridsRecords(IRP_AD_HOC_REPORTSC sc)throws DAOException;
	 /**
	  * Method counting number of images retrieved from db
	  * @return
	  * @throws BaseException
	  */
	 public int retImagesGridRecordsCount(IRP_AD_HOC_REPORTSC sc) throws DAOException;
	 /**
	  * Method to return records in irp_rep_arguments_filters for a given report and given argument ids
	  * @param sc
	  * @return
	 * @throws DAOException 
	  */
	public ArrayList<IRP_REP_ARGUMENTS_FILTERSVO> retArgumentsFiltersByReport(IRP_AD_HOC_REPORTSC sc) throws DAOException;
	/**
	 * Method that returns the argument id on database of the passed argument-report
	 * @param sc
	 * @return
	 * @throws DAOException
	 */
	public BigDecimal checkArgSameNameAndType(IRP_AD_HOC_REPORTSC sc)throws DAOException;
	/**
	 * Method that checks if any filter exists on db for a given report
	 * @param sc
	 * @return
	 * @throws DAOException
	 */
	public int checkIfAnyFilterForCurrentReport(IRP_AD_HOC_REPORTSC sc)throws DAOException;
	 
	 /**
	  * Method to return the report details based on the report id 
	  * @return IRP_AD_HOC_REPORTVOWithBLOBs
	  * @throws BaseException
	  */
	public IRP_AD_HOC_REPORTVOWithBLOBs retRepDetailsByRepId(IRP_AD_HOC_REPORTVOWithBLOBs repVO) throws DAOException;
	/**
	 * Method to return metadata reports
	 * @param repIdList
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<BigDecimal> retMetaDataReports(ArrayList<BigDecimal> repIdList) throws DAOException;
	/**
	 * 
	 * @param repIdList
	 * @return
	 * @throws DAOException
	 */
	public ArrayList<IRP_AD_HOC_REPORTVO> retReportsBeingMetadata(List<BigDecimal> repIdList) throws DAOException;
	
	public ArrayList<IRP_REP_METADATA_LOGVO>retAlreadyExportedArgsLogs (IRP_AD_HOC_REPORTSC sc) throws DAOException;
	
	/**
	 * Method to set all cif_audit_yn values to zero
	 * @param reportId
	 * @throws DAOException
	 */
	public void updateAllCifAuditParams(BigDecimal reportId) throws DAOException;
	
	/**
	 * insert Report Execution Log New Transaction
	 * @param repExecLogParamsVO
	 * @throws DAOException
	 */
	public void insertReportExecLog_NewTrans(IRP_REPORT_EXEC_LOGVO repExecLogParamsVO) throws DAOException;

}
