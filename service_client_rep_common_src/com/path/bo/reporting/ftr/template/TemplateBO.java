package com.path.bo.reporting.ftr.template;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.GLSTMPLTVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
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

public interface TemplateBO
{
    public List<GLSTMPLTCO> getAllTemplates(TmplGridSC tmplGridSC) throws BaseException;

    public List<GLSTMPLTCO> getAllTemplateLines(GLSTMPLTSC iRP_TEMPLATE_LINESSCKey) throws BaseException;

    public List<GLSTMPLTCO> getAllTemplateLines(TmplGridSC tmplGridSC) throws BaseException;

    public int getAllTemplateLinesCount(TmplGridSC sc) throws BaseException;

    public List<GLSTMPLTGLSDETCO> getGLsByLine(GLSTMPLTGLSDETSC iRP_TEMPLATE_GLSC) throws BaseException;

    public List<GLSTMPLTCO> getCurrencyList(TmplGridSC tmplGridSC) throws BaseException;

    public GLSTMPLTVO checkTemplCode(GLSTMPLTVO irpTemplVO) throws BaseException;

    public GLSTMPLTCO getCurrencyDependency(GLSTMPLTCO linesVO) throws BaseException;

    public CommonDetailsVO getFromGLDependency(CommonDetailsSC commonDetailsSC) throws BaseException;

    public List<CommonDetailsVO> getFromGLList(TmplGridSC tmplGridS) throws BaseException;

    public List<CommonDetailsVO> getCompaniesList(TmplGridSC tmplGridSC) throws BaseException;

    public int getCompaniesListCount(TmplGridSC tmplGridSC) throws BaseException;

    public List<CommonDetailsVO> getBranchesList(TmplGridSC tmplGridSC) throws BaseException;

    public List<CommonDetailsVO> getDivisonsList(TmplGridSC tmplGridSC) throws BaseException;

    public List<CommonDetailsVO> getDepartmentsList(TmplGridSC tmplGridSC) throws BaseException;

    public List<FTR_TMPLT_EXPRCO> getExprList(GLSTMPLTSC iRP_TEMPLATE_LINESSC) throws BaseException;

    public FTR_TMPLT_EXPRCO getMaxSubLineNb(GLSTMPLTSC linesSC) throws BaseException;

    public void saveTemplate(GLSTMPLTCO irp_templVO) throws BaseException;

    public int getAllTemplCount(TmplGridSC tmplGridSC) throws BaseException;

    public int getCurrencyListCount(TmplGridSC tmplGridSC) throws BaseException;

    public int getFromGLListCount(TmplGridSC tmplGridSC) throws BaseException;

    public GLSTMPLTGLSDETCO getMaxGLSubLineNb(GLSTMPLTSC lineSC) throws BaseException;

    public CommonDetailsVO getMaxCrtSubLineNb(CommonDetailsSC crtSC) throws BaseException;

    public List<GLSTMPLT_PARAM_LINKCO> getCrtList(TmplGridSC tmplGridSC) throws BaseException;

    public int getBranchesListCount(TmplGridSC tmplGridSC) throws BaseException;

    public int getDivisonsListCount(TmplGridSC tmplGridSC) throws BaseException;

    public int getDepartmentsListCount(TmplGridSC tmplGridSC) throws BaseException;

    public List<CommonDetailsVO> getFilterCrtDetList(TmplGridSC tmplGridSC) throws BaseException;

    public int getFilterCrtDetListCount(TmplGridSC tmplGridSC) throws BaseException;

    public List<CommonDetailsVO> getFilterCrtDetLList(TmplGridSC tmplGridSC) throws BaseException;

    public int getFilterCrtDetLListCount(TmplGridSC tmplGridSC) throws BaseException;

    public void deleteTemplWithDetails(GLSTMPLTCO tempVO, AuditRefCO refCO) throws BaseException;

    public void saveTemplWithDetails(GLSTMPLTCO existTemplVO, GLSTMPLTCO irp_templVO, BigDecimal tempCode,
	    String usrName, GLSTMPLTCO allTempl, AuditRefCO refCO, String language) throws BaseException;

    public GLSTMPLTVO retrieveTemplate(GLSTMPLTVO templVO) throws BaseException;

    public int getRelatedReportsListCount(TmplGridSC tmplGridSC) throws BaseException;

    public List<FTR_TMPL_REFCO> getRelatedReportsList(TmplGridSC tmplGridSC) throws BaseException;

    public void deleteRelRep(FTR_TMPL_REFCO ftrTmplRefCO) throws BaseException;

    public void checkRequiredFields(Object obj, String pageRef, BigDecimal compCode, String appName, String lang)
	    throws BaseException;

    public List<FTR_ACCOUNTSCO> getAccountsList(FTR_ACCOUNTSSC ftrAccountsSC) throws BaseException;

    public int getAccountsListCount(FTR_ACCOUNTSSC ftrAccountsSC) throws BaseException;

    public List<FTR_MISMATCH_REPORTCO> getMismatchsList(TmplGridSC sc) throws BaseException;

    public int getMismatchsListCount(TmplGridSC sc) throws BaseException;

    public String getTemplateNameDependency(TmplGridSC sc) throws BaseException;

    public String getLineNameDependency(TmplGridSC sc) throws BaseException;

    public void saveCreateLike(GLSTMPLTSC sc) throws BaseException;
    
    public Integer checkOrderInOtherTemplate(TmplGridSC sc) throws BaseException;
    public int checkTemplateGLRecords(BigDecimal templateCode,BigDecimal compCode) throws BaseException;
    
    /**
     * Returns if the template satisfy the RTV condition
     * @param sc
     * @return one if condition not satisfied and zero if satisfied
     * @throws BaseException
     */
    public int checkRTVcalcType(CommonDetailsSC sc) throws BaseException;
    /**
     * cross check report checking
     * @param sc
     * @return
     * @throws BaseException
     */
    public HashMap<String,Integer> checkCrosscheckReport(CommonDetailsSC sc) throws BaseException;

    /**
     * Csm Additional Field criteria CAF
     * @param hash map,tmplGridSC
     * @return
     * @throws BaseException
     */
    public List<CommonDetailsVO> retCrtAddFieldsDet3Lst(HashMap<String, Object> repSessionCOMap, TmplGridSC tmplGridSC ) throws BaseException;
    /**
     * Csm Additional Field criteria CAF count
     * @param hash map,tmplGridSC
     * @return
     * @throws BaseException
     */
    public int retCrtAddFieldsDet3LstCount(HashMap<String, Object> repSessionCOMap, TmplGridSC tmplGridSC) throws BaseException;
    /**
     * Csm Additional Field criteria CAF
     * @param hash map,tmplGridSC
     * @return
     * @throws BaseException
     */
    public List<CommonDetailsVO> retCrtAddFieldsDet3Dep(HashMap<String, Object> repSessionCOMap, TmplGridSC tmplGridSC ) throws BaseException;
    
    /**
     * Returns if the detail1 of the smart criteria should be mandatory or not
     * @param crtSC
     * @return the OPTION_TYPE =0 in case the detail1 is mandatory otherwise OPTION_TYPE will be >0
     * @throws BaseException
     */
    public S_ADDITIONS_OPTIONSVO checkSmartMandatoryDetails(GLSTMPLT_CRITERIASC crtSC)throws BaseException;

}
