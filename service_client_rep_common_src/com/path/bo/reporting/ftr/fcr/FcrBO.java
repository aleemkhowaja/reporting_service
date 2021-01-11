package com.path.bo.reporting.ftr.fcr;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.IRP_FCR_FIXED_COLSVO;
import com.path.dbmaps.vo.IRP_FCR_REPORTSVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FCR_REPORTSCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.AXSCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;


public interface FcrBO
{
    public List<FTR_OPTCO> loadFcrList(CommonDetailsSC commonDetailsSC) throws BaseException;

    public int retFcrListCount(CommonDetailsSC commonDetailsSC) throws BaseException;

    public List<CommonDetailsVO> retParentRefList(CommonDetailsSC commonDetailsSC) throws BaseException;

    public int retParentRefListCount(CommonDetailsSC commonDetailsSC) throws BaseException;

    public int checkProgRef(CommonDetailsSC commonDetailsSC) throws BaseException;

    public void deleteOpts(FTR_OPTCO fcrCO) throws BaseException;

    public void deleteAxs(AXSCO axsCO) throws BaseException;

    public FTR_OPTCO returnDynamicReportByRef(FTR_OPTCO optCO) throws BaseException;

    public void saveFCR(FTR_OPTCO fcrCO, BigDecimal compCode, BigDecimal branchCode, String appName, String userName,
	    HashMap<String, String> optTrans, String mode, String pageRef, String lang) throws BaseException;

    public void deleteFCR(BigDecimal compCode, BigDecimal branchCode, String appNam, String userName, FTR_OPTCO fcrCO,String pageRef)
	    throws BaseException;

    public void saveReportOpts(BigDecimal compCode, BigDecimal branchCode, String appName, String userName,
	    IRP_AD_HOC_REPORTCO reportCO, Date runningDate) throws BaseException;

    public void deleteOptExtended(FTR_OPTCO fcrCO) throws BaseException;

    public void updateOPTs(FTR_OPTCO fcrCO, String appName, boolean fromUpDown) throws BaseException;

    public FTR_OPTCO retrieveFcr(CommonDetailsSC sc) throws BaseException;

    public List<FTR_OPTCO> retSavedOptsByProgRef(FTR_OPTCO optCO) throws BaseException;

    public void saveMissedOpts(IRP_AD_HOC_REPORTCO reportCO, List<String> missedOpts,String optDesc) throws BaseException;
    
    public IRP_FCR_REPORTSCO retFcrRep(IRP_AD_HOC_REPORTSC repSC)throws BaseException;
    
    public String returnFcrProgRef(Map parameters) throws BaseException;
    
    public void saveAllReportAxs(AXSVO axsVO) throws BaseException;
    
    public void saveAllReportOpts(OPTVO optVO) throws BaseException;
    
    public List<AXSCO> retAxsByReport(AXSCO axsCO) throws BaseException;
    
    public void saveMissedAxs(AXSVO axsVO, List<String> missedAxs) throws BaseException;
    
    public AXSVO fillAxsVOProps(BigDecimal compCode, BigDecimal branchCode, String appName, String userName,
	    Date runningDate, String progRef, String status, String toBeDeleted) throws BaseException;
    public String retFcrReportEngDesc(String progRef) throws BaseException;
    
    /**
     * Returns the list of fixed columns bases on the report reference
     * @param progRef
     * @return List<IRP_FCR_FIXED_COLSVO>
     * @throws BaseException
     */
    public List<IRP_FCR_FIXED_COLSVO> retFixedFcrColsByRef(String progRef)throws BaseException;
    /**
     * Returns the list of lines from the column template
     * @param colTmplSC
     * @return List<COLMNTMPLTCO>
     * @throws BaseException
     */
    public List<COLMNTMPLTCO> retDynamicFcrColsFromColTempl(COLMNTMPLTSC colTmplSC)throws BaseException;
    /**
     * Returns the list of lines from the template
     * @param colTmplSC
     * @return List<COLMNTMPLTCO>
     * @throws BaseException
     */
    public List<COLMNTMPLTCO> retDynamicFcrColsFromTempl(GLSTMPLTSC tmplSC)throws BaseException;
    
    /**
     * Returns the query related to the sent ftrOptProgRef from the table IRP_FCR_REPORTS
     * @param vo
     * @return List<IRP_FCR_REPORTSVO>
     * @throws BaseException
     */
    public List<IRP_FCR_REPORTSVO>  returnFcrDetByFtrOptRef(IRP_FCR_REPORTSVO vo)throws BaseException;

    /**
     * Returns the list of categories where is_web_yn=2
     * @param commonDetailsSC
     * @return List<CommonDetailsVO>
     * @throws BaseException
     */
	public List<CommonDetailsVO> retCategoriesLkpList(CommonDetailsSC commonDetailsSC)throws BaseException;

	   /**
     * Returns the count of categories where is_web_yn=2
     * @param commonDetailsSC
     * @return List<CommonDetailsVO>
     * @throws BaseException
     */
	public int retCategoriesLkpCount(CommonDetailsSC commonDetailsSC)throws BaseException;

	  /**
     * method that will delete records from s_role_detail table based on the report reference and application name
     * @param FTR_OPTCO
     * @return 
     * @throws BaseException
     */
	public void deleteSRoleDetail(FTR_OPTCO fcrCO)throws BaseException;

}
