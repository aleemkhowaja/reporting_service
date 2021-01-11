package com.path.dao.reporting.ftr.template;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.CTS_ADD_FIELDSVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIASC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.FTR_ACCOUNTSCO;
import com.path.vo.reporting.ftr.template.FTR_ACCOUNTSSC;
import com.path.vo.reporting.ftr.template.FTR_MISMATCH_REPORTCO;
import com.path.vo.reporting.ftr.template.FTR_TMPLT_EXPRCO;
import com.path.vo.reporting.ftr.template.FTR_TMPL_REFCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTGLSDETCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTGLSDETSC;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;
import com.path.vo.reporting.ftr.template.GLSTMPLT_PARAM_LINKCO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public interface TemplateDAO
{
    public List<GLSTMPLTCO> getAllTemplates(TmplGridSC tmplGridSC) throws DAOException;

    public List<GLSTMPLTCO> getAllTemplateLines(GLSTMPLTSC GLSTMPLTSC) throws DAOException;
    
    public List<GLSTMPLTCO> getAllTemplateLines(TmplGridSC sc) throws DAOException;

    public int getAllTemplateLinesCount(TmplGridSC sc) throws DAOException;

    public List<GLSTMPLTGLSDETCO> getGLsByLine(GLSTMPLTGLSDETSC iRP_TEMPLATE_GLSC) throws DAOException;

    public List<GLSTMPLTCO> getCurrencyList(TmplGridSC tmplGridSC) throws DAOException;

    public void deleteLinesByTemplate(GLSTMPLTCO irpTempVO) throws DAOException;

    public GLSTMPLTCO getCurrencyDependency(GLSTMPLTCO irpTempVO) throws DAOException;

    public List<CommonDetailsVO> getFromGLList(TmplGridSC tmplGridS) throws DAOException;

    public CommonDetailsVO getFromGLDependency(CommonDetailsSC commonDetailsSC) throws DAOException;

    public List<CommonDetailsVO> getCompaniesList(TmplGridSC tmplGridSC) throws DAOException;

    public int getCompaniesListCount(TmplGridSC tmplGridSC) throws DAOException;

    public List<CommonDetailsVO> getBranchesList(TmplGridSC tmplGridSC) throws DAOException;

    public List<CommonDetailsVO> getDivisonsList(TmplGridSC tmplGridS) throws DAOException;

    public List<CommonDetailsVO> getDepartmentsList(TmplGridSC tmplGridSC) throws DAOException;

    public List<CommonDetailsVO> getAccountsList(GLSTMPLTGLSDETSC iRP_TEMPLATE_GLSSC) throws DAOException;

    public List<FTR_TMPLT_EXPRCO> getExprList(GLSTMPLTSC GLSTMPLTSC) throws DAOException;

    public HashMap getExprMap(GLSTMPLTSC GLSTMPLTSC) throws DAOException;

    public FTR_TMPLT_EXPRCO getMaxSubLineNb(GLSTMPLTSC linesSC) throws DAOException;

    public void deleteGLsByTempl(GLSTMPLTCO tempVO) throws DAOException;

    public void deleteExprByTempl(GLSTMPLTCO tempVO) throws DAOException;

    public void deleteCrtsByTemplate(GLSTMPLTCO tempVO) throws DAOException;

    public int getAllTemplCount(TmplGridSC tmplGridSC) throws DAOException;

    public int getCurrencyListCount(TmplGridSC tmplGridSC) throws DAOException;

    public int getFromGLListCount(TmplGridSC tmplGridSC) throws DAOException;

    public GLSTMPLTGLSDETCO getMaxGLSubLineNb(GLSTMPLTSC lineSC) throws DAOException;

    public CommonDetailsVO getMaxCrtSubLineNb(CommonDetailsSC crtSC) throws DAOException;

    public List<GLSTMPLT_PARAM_LINKCO> getCrtList(TmplGridSC tmplGridSC) throws DAOException;

    public int getBranchesListCount(TmplGridSC tmplGridSC) throws DAOException;

    public int getDivisonsListCount(TmplGridSC tmplGridSC) throws DAOException;

    public int getDepartmentsListCount(TmplGridSC tmplGridSC) throws DAOException;

    public List<CommonDetailsVO> getFilterCrtDetList(TmplGridSC tmplGridSC) throws DAOException;

    public int getFilterCrtDetListCount(TmplGridSC tmplGridSC) throws DAOException;

    public void saveExpr(FTR_TMPLT_EXPRVO exprVO) throws DAOException;

    public List<CommonDetailsVO> getFilterCrtDetLList(TmplGridSC tmplGridSC) throws DAOException;

    public int getFilterCrtDetLListCount(TmplGridSC tmplGridSC) throws DAOException;

    public void deleteGlsByLine(GLSTMPLTGLSDETSC glSC) throws DAOException;

    public Integer updateTemplate(GLSTMPLTCO irpTemplVO) throws DAOException;

    public int getRelatedReportsListCount(TmplGridSC tmplGridSC) throws DAOException;

    public List<FTR_TMPL_REFCO> getRelatedReportsList(TmplGridSC tmplGridSC) throws DAOException;

    public int updateRelatedReports(FTR_TMPL_REFCO ftrTmplRefCO) throws DAOException;

    public void deleteAllLines(int templateCode) throws DAOException;

    public List<FTR_ACCOUNTSCO> getAccountsList(FTR_ACCOUNTSSC ftrAccountsSC) throws DAOException;

    public int getAccountsListCount(FTR_ACCOUNTSSC ftrAccountsSC) throws DAOException;

    public List<FTR_MISMATCH_REPORTCO> getMismatchsList(TmplGridSC sc) throws DAOException;

    public int getMismatchsListCount(TmplGridSC sc) throws DAOException;

    public String getTemplateNameDependency(TmplGridSC sc) throws DAOException;

    public String getLineNameDependency(TmplGridSC sc) throws DAOException;

    public void saveCreateLike(GLSTMPLTSC sc) throws DAOException;

    public List<FTR_MISMATCH_REPORTCO> getMismatchesByLine(TmplGridSC sc) throws DAOException;

    public void deleteRelatedReportsByTemplate(GLSTMPLTCO glstmpltCO) throws DAOException;

    public void deleteMismatchesByTemplate(GLSTMPLTCO glstmpltCO) throws DAOException;

    public void deleteAllTemplateLines(GLSTMPLTSC glstmpltSC) throws DAOException;
    
    public Integer checkOrderInOtherTemplate(TmplGridSC sc) throws DAOException;
    public int checkTemplateGLRecords(BigDecimal templateCode,BigDecimal compCode) throws DAOException;
    
    /**
     * Returns if the template satisfy the RTV condition
     * @param sc
     * @return one if condition not satisfied and zero if satisfied
     * @throws BaseException
     */
    public int checkRTVcalcType(CommonDetailsSC sc) throws DAOException;
    /**
     * Cross check report
     * @param sc
     * @return
     * @throws DAOException
     */
    public HashMap<String,Integer> checkCrosscheckReport(CommonDetailsSC sc) throws DAOException;

    /**
     * Returns if the detail1 of the smart criteria should be mandatory or not
     * @param crtSC
     * @return the OPTION_TYPE =0 in case the detail1 is mandatory otherwise OPTION_TYPE will be >0
     * @throws DAOException
     */
    public S_ADDITIONS_OPTIONSVO checkSmartMandatoryDetails(GLSTMPLT_CRITERIASC crtSC)throws DAOException;
    /**
     * Csm Additional Field criteria CAF
     * @param sc
     * @return
     * @throws DAOException
     */
    public CTS_ADD_FIELDSVO retCrtAddFieldsLst(TmplGridSC tmplGridSC)throws DAOException;
    /**
     * Csm Additional Field criteria CAF
     * @param sc
     * @return
     * @throws DAOException
     */
    public List<CommonDetailsVO> retCrtAddFieldsDet3Lst(TmplGridSC tmplGridSC)throws DAOException;
    /**
     * Csm Additional Field criteria CAF count
     * @param sc
     * @return
     * @throws DAOException
     */
    public int retCrtAddFieldsDet3LstCount(TmplGridSC tmplGridSC) throws DAOException;
    /**
     * Csm Additional Field criteria CAF count
     * @param sc
     * @return
     * @throws DAOException
     */
    public List<CommonDetailsVO> retCrtAddFieldsDet3Dep(TmplGridSC tmplGridSC) throws DAOException;

}
