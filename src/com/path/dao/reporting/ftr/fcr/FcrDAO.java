package com.path.dao.reporting.ftr.fcr;

import java.util.List;
import java.util.Map;

import com.path.dbmaps.vo.AXSVOKey;
import com.path.dbmaps.vo.FTR_OPTVO;
import com.path.dbmaps.vo.IRP_FCR_FIXED_COLSVO;
import com.path.dbmaps.vo.IRP_FCR_REPORTSVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FCR_REPORTSCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.AXSCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;

public interface FcrDAO
{
    public int retFcrListCount(CommonDetailsSC commonDetailsSC) throws DAOException;

    public List<FTR_OPTCO> loadFcrList(CommonDetailsSC commonDetailsSC) throws DAOException;

    public List<CommonDetailsVO> retParentRefList(CommonDetailsSC commonDetailsSC) throws DAOException;

    public int retParentRefListCount(CommonDetailsSC commonDetailsSC) throws DAOException;

    public int checkProgRef(CommonDetailsSC commonDetailsSC) throws DAOException;

    public void saveOpt1(FTR_OPTCO fcrCO) throws DAOException;

    public void saveOpt2(FTR_OPTCO fcrCO) throws DAOException;

    public void saveOpt3(FTR_OPTCO fcrCO) throws DAOException;

    public int retProgOrder(FTR_OPTCO fcrCO) throws DAOException;

    public void updateOpt1(FTR_OPTCO fcrCO) throws DAOException;

    public void updateOpt2(FTR_OPTCO fcrCO) throws DAOException;

    public void updateOpt3(FTR_OPTCO fcrCO) throws DAOException;

    public void deleteOpt(FTR_OPTCO fcrCO) throws DAOException;

    public void deleteOpts(FTR_OPTCO fcrCO) throws DAOException;

    public void deleteAxs(AXSCO axsCO) throws DAOException;

    public FTR_OPTCO returnDynamicReportByRef(FTR_OPTCO optCO) throws DAOException;

    public void deleteOptExtended(FTR_OPTCO fcrCO) throws DAOException;

    public FTR_OPTCO retrieveFcr(CommonDetailsSC sc) throws DAOException;

    public Integer updateFcr(FTR_OPTVO optVO) throws DAOException;

    public List<FTR_OPTCO> retSavedOptsByProgRef(FTR_OPTCO optCO) throws DAOException;
    
    public IRP_FCR_REPORTSCO retFcrRep(IRP_AD_HOC_REPORTSC repSC)throws DAOException;
    
    public String returnFcrProgRef(Map parameters)throws DAOException;
    
    public void deleteAXSByCompBranchApp(AXSVOKey axsVO) throws DAOException;
    
    public List<AXSCO> retAxsByReport(AXSCO axsCO) throws DAOException;
    public String retFcrReportEngDesc(String progRef) throws DAOException;
    
    /**
     * Returns the list of fixed columns bases on the report reference
     * @param progRef
     * @return List<IRP_FCR_FIXED_COLSVO>
     * @throws BaseException
     */
    public List<IRP_FCR_FIXED_COLSVO> retFixedFcrColsByRef(String progRef)throws DAOException;
    /**
     * Returns the list of lines from the column template
     * @param colTmplSC
     * @return List<COLMNTMPLTCO>
     * @throws BaseException
     */
    public List<COLMNTMPLTCO> retDynamicFcrColsFromColTempl(COLMNTMPLTSC colTmplSC)throws DAOException;
    /**
     * Returns the list of lines from the template
     * @param colTmplSC
     * @return List<COLMNTMPLTCO>
     * @throws BaseException
     */
    public List<COLMNTMPLTCO> retDynamicFcrColsFromTempl(GLSTMPLTSC tmplSC)throws DAOException;
    /**
     * Returns the record related to the sent ftrOptProgRef from the table IRP_FCR_REPORTS
     * @param ftrOptProgRef
     * @return IRP_FCR_REPORTSVO
     * @throws BaseException
     */
    public List<IRP_FCR_REPORTSVO>  returnFcrDetByFtrOptRef(IRP_FCR_REPORTSVO vo)throws DAOException;

	    /**
     * Returns the list of categories where is_web_yn=2
     * @param commonDetailsSC
     * @return List<CommonDetailsVO>
     * @throws BaseException
     */
	public List<CommonDetailsVO> retCategoriesLkpList(CommonDetailsSC commonDetailsSC)throws DAOException;

	   /**
     * Returns the count of categories where is_web_yn=2
     * @param commonDetailsSC
     * @return List<CommonDetailsVO>
     * @throws BaseException
     */
	public int retCategoriesLkpCount(CommonDetailsSC commonDetailsSC)throws DAOException;

	  /**
     * method that will delete records from s_role_detail table based on the report reference and application name
     * @param FTR_OPTCO
     * @return 
     * @throws DAOException
     */
	public void deleteSRoleDetail(FTR_OPTCO fcrCO)throws DAOException;

}
