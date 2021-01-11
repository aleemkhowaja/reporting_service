package com.path.bo.reporting.designer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.sf.jasperreports.engine.design.JasperDesign;

import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVOWithBLOBs;
import com.path.dbmaps.vo.IRP_HASH_TABLEVO;
import com.path.dbmaps.vo.IRP_REPORT_EXEC_LOGVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTSVO;
import com.path.dbmaps.vo.IRP_REP_METADATA_LOGVO;
import com.path.dbmaps.vo.IRP_SUB_REPORTVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.OPTVOKey;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.AUTOMATED_IMPORT_REPORTSCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_HASH_TABLESC;
import com.path.vo.reporting.IRP_QUERY_ARG_MAPPINGCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.IRP_SUB_REPORTSC;
import com.path.vo.reporting.ImageCO;
import com.path.vo.reporting.PTH_CLIENTSCO;
import com.path.vo.reporting.PTH_CLIENTSSC;
import com.path.vo.reporting.USER_TAB_COLSCO;
import com.path.vo.reporting.ftr.template.AXSCO;

public interface DesignerBO
{
    public String generateJrxml(String xhtml, String xslFileName) throws BaseException;

    public int getReportsCount(IRP_AD_HOC_REPORTSC sc) throws BaseException;

    public List<IRP_AD_HOC_REPORTCO> getReportsList(IRP_AD_HOC_REPORTSC sc) throws BaseException;

    public void deleteReport(List<BigDecimal> reprotsId, AXSCO axsCO, AuditRefCO refCO, List<BigDecimal> oldReprotsId,
	    List<String> appsName, List<String> progRefList,boolean useExistingOptAccess,String userName,String pageRef) throws BaseException;

    public boolean[] validateReportInfo(IRP_AD_HOC_REPORTSC reportSC) throws BaseException;

    public IRP_AD_HOC_REPORTCO returnReportById(BigDecimal reportId) throws BaseException, Exception;

    public IRP_AD_HOC_REPORTCO findReportById(BigDecimal reportId) throws BaseException, IOException;

    public IRP_AD_HOC_REPORTCO returnReportByRef(IRP_AD_HOC_REPORTSC reportSC) throws BaseException;

    public List<IRP_AD_HOC_QUERYVO> retQueriesByReport(IRP_AD_HOC_REPORTSC reportSC) throws BaseException;

    public IRP_AD_HOC_REPORTCO retProgRefByRepId(IRP_AD_HOC_REPORTSC reportSC) throws BaseException;

    public IRP_AD_HOC_REPORTCO save(IRP_AD_HOC_REPORTVOWithBLOBs vo, IRP_AD_HOC_REPORTCO reportCO, boolean updateRep,
	    BigDecimal companyCode, BigDecimal branchCode, String userName,String pageRef)
	    throws BaseException, IOException;

    public List<IRP_REP_ARGUMENTSCO> retArgsByReport(IRP_AD_HOC_REPORTSC reportSC) throws BaseException;

    public IRP_AD_HOC_REPORTCO retRepIdByProgRef(IRP_AD_HOC_REPORTSC reportSC) throws BaseException;

    public int selectReportByRef(IRP_AD_HOC_REPORTSC sc) throws BaseException;

    public IRP_AD_HOC_REPORTCO retReportFlags(IRP_AD_HOC_REPORTSC repSC) throws BaseException;

    public String generateXhtml(byte[] jrxmlFILE, String xslFileName) throws BaseException;

    public BigDecimal retRepIdFromOldRepId(BigDecimal oldRepId) throws BaseException;

    public String reorderJRxml(byte[] jrxmlFILE, String xslFileName) throws BaseException;

    public IRP_AD_HOC_REPORTCO retrieveReportVO(BigDecimal repId) throws BaseException;

    public String checkIfSubRepUploaded(String subRepName) throws BaseException;

    public List<IRP_SUB_REPORTVO> retSubRepList(IRP_SUB_REPORTSC subrepSC) throws BaseException;

    public int retSubRepListCount(IRP_SUB_REPORTSC subrepSC) throws BaseException;

    public int getSubRepParamsCount(IRP_SUB_REPORTSC subrepSC) throws BaseException;

    public List<IRP_REP_ARGUMENTSCO> getSubRepParamsList(IRP_SUB_REPORTSC subrepSC) throws BaseException;

    public List<BigDecimal> retSubReportUsage(List<BigDecimal> reportsId) throws BaseException;

    public List<IRP_QUERY_ARG_MAPPINGCO> retQryArgMapping(IRP_AD_HOC_REPORTSC reportSC) throws BaseException;
    
    public List<IRP_REP_ARGUMENTSVO> retRepArgDepList(IRP_AD_HOC_REPORTSC reportSC)throws BaseException;

    public OPTVO checkIfSkipOptAxs(OPTVOKey optVOKey) throws BaseException;
    
    public boolean checkIfOptInAdHocReport(OPTVOKey optVOKey) throws BaseException;

    public List<IRP_HASH_TABLEVO> retHashTablList(IRP_HASH_TABLESC irpHashTblSC) throws BaseException;

    public int retHashTablListCount(IRP_HASH_TABLESC irpHashTblSC) throws BaseException;

    public List<BigDecimal> retSchedUsage(List<BigDecimal> reportsId) throws BaseException;

    public IRP_AD_HOC_REPORTCO emptyRepPropsForSaveAs(IRP_AD_HOC_REPORTCO currRepCO)throws BaseException;
    
    public ImageCO readImageFromJrxml(Object child)throws BaseException;

    public JasperDesign updateImgPath(JasperDesign jasperDesign, ArrayList<ImageCO> imgLst) throws BaseException;
    
    public List<ImageCO> retImgListFromJrxml(Object childBand,List<ImageCO>imagesList) throws BaseException;
    
    public String returnXslName(IRP_AD_HOC_REPORTSC reptSC)throws BaseException;
    
    public List<IRP_SUB_REPORTCO> returnSubreports(BigDecimal reportId) throws BaseException;
    
    public List<USER_TAB_COLSCO> returnTblColsName(USER_TAB_COLSCO userTabColsCO) throws BaseException;
    
    public List<Object> returnAllOptsLst(OPTVO optVO) throws BaseException;
    
    
    public void writeRepContent(HashMap<BigDecimal, IRP_SUB_REPORTCO> exportedReportsMap,String userName,StringBuffer errorStr,int errorCtr, HashMap<String, String> translationMap,String lang,boolean skipTrans) throws BaseException, FileNotFoundException, UnsupportedEncodingException, IOException, Exception;
    
    public List<ImageCO> retImgListFromJrxmlToAllBand(JasperDesign jasperDesign,List<ImageCO>imagessList)throws BaseException;
    
    public void copyImages(List<ImageCO>imagesList,String imagePathSrc, String imagePathDest)  throws BaseException;
    
    /**
     * Method that imports the reports to the database
     * @param userId
     * @param compCode
     * @param branchCode
     * @param importOptions
     * @param axsCO
     * @param sessionCO
     * @param useExistingOptAccess
     * @param overwriteTrans
     * @param updateVersionIfEqual
     * @param zipPassword
     * @param upload
     * @param fileName
     * @param pageRef
     * @param keepSchedsHyperlinks
     * @return
     * @throws BaseException
     */
    public HashMap<String,Object> importRepFiles( String userId, BigDecimal compCode, BigDecimal branchCode,
	    String importOptions, AXSCO axsCO, SessionCO sessionCO, boolean useExistingOptAccess,
	    boolean overwriteTrans, boolean updateVersionIfEqual,String zipPassword,File upload,String fileName,String pageRef,
	    boolean keepSchedsHyperlinks) throws BaseException;


    public String retExistingOpts(String progRef,String appName,String lang,String transMsg)throws BaseException;
    
    public List<PTH_CLIENTSCO> retRepClientLst(PTH_CLIENTSSC pthClientSC) throws BaseException;;
    
    public int retRepClientLstCount(PTH_CLIENTSSC pthClientSC) throws BaseException;;
    
    public HashMap<Long, IRP_FIELDSCO> returnVariablesMapByReportId(BigDecimal reportId,String userName) throws BaseException;
    public IRP_AD_HOC_REPORTCO writeUploadedReportfile(IRP_AD_HOC_REPORTCO repCO,ArrayList<ImageCO> imgLst ) throws BaseException;
    public void deleteZipFolder(String fileZipLocation , String unzippedRepFolderLocation) throws BaseException;
    public void writeFileToRepository(String fileName,String fileContent,String ext)throws BaseException ;
    public void deleteFileFromRepository(String fileName) throws BaseException ;
    public byte[] writeReportExportFile(StringBuffer orderedRepIdsBuf,String userName, String zipPassword) throws BaseException;
    public void createReportExportFile(String userName,  String fullBasicExp ) throws BaseException;
    public JasperDesign startBasicExportFile(String userName,  IRP_SUB_REPORTCO subRepCO , JasperDesign jasperDesign) throws BaseException;
    public boolean checkImgExist(String imgName) throws BaseException;
    public byte[] writeBasicExportFile(String userName,List<ImageCO> imagesList, String zipPassword,  StringBuffer errorStr )  throws BaseException;
    public void deleteBasicExportFile(String userName)  throws BaseException;
    public PTH_CLIENTSCO returnDefaultClient()throws DAOException;
    public PTH_CLIENTSCO returnClient(PTH_CLIENTSSC pthClientSC)throws DAOException;
    public ArrayList<BaseException> addException(StringBuffer usedSb, Integer messageCode, ArrayList<BaseException> exceptionsLst) throws BaseException;    
    public HashMap<String, Object> saveImportedReport(HashMap<String, Object> paramMaps, BigDecimal excelReportId,
	    IRP_AD_HOC_REPORTVOWithBLOBs adHocRepVO, String user, BigDecimal compCode, BigDecimal branchCode,
	    SessionCO sessionCO, boolean useExistingOptAccess,String pageRef) throws BaseException;
    /**
     * Method that returns the list of available reports on path specified by the user
     * @param path
     * @return
     * @throws BaseException
     */
    public ArrayList<AUTOMATED_IMPORT_REPORTSCO>retAutoImpRepGridRecords(String path) throws BaseException;
    /**
     * Method that saves scheduler and hyperlink data into db when choosing
     * to preserve the data on import (replace if exist)
     * @param schedMap
     * @throws BaseException
     */
    public void saveSchedulersHyperlinksToDb(HashMap<String, ArrayList<Object>> schedMap) throws BaseException;
    /**
     * Method that returns full report by reference
      * @param reportSC
     * @throws BaseException
     */
    public IRP_AD_HOC_REPORTCO returnFullReportByRef(IRP_AD_HOC_REPORTSC reportSC) throws Exception;
    /**
     * Method that will return the report details based on the progRef and application name
      * @param reportSC
     * @throws BaseException
     */
    public IRP_AD_HOC_REPORTVOWithBLOBs retExistRepInDb( IRP_AD_HOC_REPORTVOWithBLOBs repVO) throws DAOException;
    /**
     * Method to return metadata reports
     * @param repIdList
     * @return
     * @throws BaseException
     */
    public ArrayList<BigDecimal> retMetaDataReports(ArrayList<BigDecimal> repIdList) throws BaseException;
    public ArrayList<IRP_AD_HOC_REPORTVO> retReportsBeingMetadata(List<BigDecimal> repIdList) throws BaseException;
    public ArrayList<IRP_REP_METADATA_LOGVO>retAlreadyExportedArgsLogs (IRP_AD_HOC_REPORTSC sc) throws BaseException;
    
    /**
     * insert Report Execution Log New Transaction
     * @param repExecLogParamsVO
     * @throws DAOException
     */
    public void insertReportExecLog_NewTrans(IRP_REPORT_EXEC_LOGVO repExecLogParamsVO) throws BaseException;
    
    /**
     * Update Report Execution Log New Transaction
     * @param repExecLogParamsVO
     * @throws BaseException
     */
    public void updateReportExecLog_NewTrans(IRP_REPORT_EXEC_LOGVO repExecLogParamsVO) throws BaseException;
   
}
