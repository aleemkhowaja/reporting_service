package com.path.dao.reporting.ftr.template.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.ftr.template.TemplateDAO;
import com.path.dbmaps.vo.CTS_ADD_FIELDSVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
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

public class TemplateDAOImpl extends BaseDAO implements TemplateDAO
{
    public List<GLSTMPLTCO> getAllTemplates(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "template.getAllTemplatesMap");
	if(tmplGridSC.getSortOrder() == null)
	{
	    tmplGridSC.setSortOrder("   ORDER BY CODE ");
	}
	return getSqlMap().queryForList("template.getAllTemplates", tmplGridSC, tmplGridSC.getRecToskip(),
		tmplGridSC.getNbRec());
    }

    public List<GLSTMPLTCO> getAllTemplateLines(GLSTMPLTSC iRPTEMPLATELINESSC) throws DAOException
    {
	if(iRPTEMPLATELINESSC.getSortOrder() == null)
	{
	    iRPTEMPLATELINESSC.setSortOrder(" ORDER BY LINE_NBR ");
	}
	return getSqlMap().queryForList("templateLines.getAllTemplateLines", iRPTEMPLATELINESSC);
    }

    public List<GLSTMPLTCO> getAllTemplateLines(TmplGridSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "templateLines.getAllTemplateLinesMap");
	List<GLSTMPLTCO> l = getSqlMap().queryForList("templateLines.getAllTemplateLinesTmplsc", sc, sc.getRecToskip(),
		sc.getNbRec());
	return l;
    }

    public int getAllTemplateLinesCount(TmplGridSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "templateLines.getAllTemplateLinesMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateLines.getAllTemplateLinesCount", sc)).intValue();
	return nb;
    }

    public List<GLSTMPLTGLSDETCO> getGLsByLine(GLSTMPLTGLSDETSC iRPTEMPLATEGLSC) throws DAOException
    {
	if(iRPTEMPLATEGLSC.getSortOrder() == null)
	{
	    iRPTEMPLATEGLSC.setSortOrder("  ORDER BY LINE_NBR_DET  ");
	}

	ArrayList<GLSTMPLTGLSDETCO> l = (ArrayList<GLSTMPLTGLSDETCO>) getSqlMap().queryForList(
		"templateGL.getGLsByLine", iRPTEMPLATEGLSC);
	return l;
    }

    public List<GLSTMPLTCO> getCurrencyList(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateLines.getCurrencyListMap");
	return getSqlMap().queryForList("templateLines.getCurrencyList", tmplGridSC, tmplGridSC.getRecToskip(),
		tmplGridSC.getNbRec());
    }

    public void deleteLinesByTemplate(GLSTMPLTCO irpTempVO) throws DAOException
    {
	// added by bbe
	Set set = irpTempVO.getDbLinesMap().keySet();
	Iterator it = set.iterator();
	while(it.hasNext())
	{
	    GLSTMPLTCO lGlstmpltCO = irpTempVO.getDbLinesMap().get(it.next());
	    lGlstmpltCO.getGlstmpltVO().setTEMPLATE_TYPE("P");
	    getSqlMap().delete("templateLines.deleteLinesByTemplate", lGlstmpltCO.getGlstmpltVO());
	}
	// end added bbe
	// stopped by bbe
	// getSqlMap().delete("templateLines.deleteLinesByTemplate",
	// irpTempVO.getGlstmpltVO());

    }

    
    public GLSTMPLTCO getCurrencyDependency(GLSTMPLTCO irpTempVO) throws DAOException
    {
	return (GLSTMPLTCO) getSqlMap()
		.queryForObject("templateLines.getCurrencyDependency", irpTempVO.getGlstmpltVO());
    }

    
    public List<CommonDetailsVO> getFromGLList(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	if(tmplGridSC.getSortOrder() == null)
	{
	    tmplGridSC.setSortOrder(" ORDER BY TBL.CODE ");
	}
	return getSqlMap().queryForList("templateGL.getFromGLList", tmplGridSC, tmplGridSC.getRecToskip(),
		tmplGridSC.getNbRec());
    }

    public int getFromGLListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateGL.getFromGLListCount", tmplGridSC)).intValue();
	return nb;
    }

    
    public CommonDetailsVO getFromGLDependency(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	return (CommonDetailsVO) getSqlMap().queryForObject("templateGL.getFromGLDependency", commonDetailsSC);
    }

    
    public List<CommonDetailsVO> getCompaniesList(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	if(tmplGridSC.getSortOrder() == null)
	{
	    tmplGridSC.setSortOrder(" ORDER BY TBL.CODE  ");
	}
	return getSqlMap().queryForList("templateGL.getCompaniesList", tmplGridSC, tmplGridSC.getRecToskip(),
		tmplGridSC.getNbRec());
    }

    
    public int getCompaniesListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateGL.getCompaniesListCount", tmplGridSC)).intValue();
	return nb;
    }

    
    public List<CommonDetailsVO> getBranchesList(TmplGridSC tmplGridSC) throws DAOException
    {
	if(tmplGridSC.getSortOrder() == null)
	{
	    tmplGridSC.setSortOrder(" ORDER BY TBL.CODE ");
	}
	if(tmplGridSC.isGrid())
	{
	    DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	    return getSqlMap().queryForList("templateGL.getBranchesList", tmplGridSC, tmplGridSC.getRecToskip(),
		    tmplGridSC.getNbRec());
	}
	else
	{
	    return getSqlMap().queryForList("templateGL.getBranchesList", tmplGridSC);
	}
    }

    public int getBranchesListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateGL.getBranchesListCount", tmplGridSC)).intValue();
	return nb;
    }

    
    public List<CommonDetailsVO> getDivisonsList(TmplGridSC tmplGridSC) throws DAOException
    {
	if(tmplGridSC.getSortOrder() == null)
	{
	    tmplGridSC.setSortOrder(" ORDER BY TBL.CODE ");
	}
	if(tmplGridSC.isGrid())
	{
	    DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	    return getSqlMap().queryForList("templateGL.getDivisonsList", tmplGridSC, tmplGridSC.getRecToskip(),
		    tmplGridSC.getNbRec());
	}
	else
	{
	    return getSqlMap().queryForList("templateGL.getDivisonsList", tmplGridSC);
	}
    }

    public int getDivisonsListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateGL.getDivisonsListCount", tmplGridSC)).intValue();
	return nb;
    }

    public List<CommonDetailsVO> getDepartmentsList(TmplGridSC tmplGridSC) throws DAOException
    {
	if(tmplGridSC.getSortOrder() == null)
	{
	    tmplGridSC.setSortOrder(" ORDER BY TBL.CODE ");
	}
	if(tmplGridSC.isGrid())
	{
	    DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	    return getSqlMap().queryForList("templateGL.getDepartmentsList", tmplGridSC, tmplGridSC.getRecToskip(),
		    tmplGridSC.getNbRec());
	}
	else
	{
	    return getSqlMap().queryForList("templateGL.getDepartmentsList", tmplGridSC);
	}
    }

    public int getDepartmentsListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateGL.getGLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateGL.getDepartmentsListCount", tmplGridSC)).intValue();
	return nb;
    }

    public List<CommonDetailsVO> getAccountsList(GLSTMPLTGLSDETSC iRP_TEMPLATE_GLSSC) throws DAOException
    {
	return getSqlMap().queryForList("templateGL.getAccountsList", iRP_TEMPLATE_GLSSC);
    }

    
    public List<FTR_TMPLT_EXPRCO> getExprList(GLSTMPLTSC GLSTMPLTSC) throws DAOException
    {
	return getSqlMap().queryForList("templateExpr.getExprList", GLSTMPLTSC);
    }

    
    public HashMap getExprMap(GLSTMPLTSC iRPTEMPLATELINESSC) throws DAOException
    {
	return (HashMap) getSqlMap().queryForMap("templateExpr.getExprList", iRPTEMPLATELINESSC, "concatKey");
    }

    
    public FTR_TMPLT_EXPRCO getMaxSubLineNb(GLSTMPLTSC linesSC) throws DAOException
    {
	return (FTR_TMPLT_EXPRCO) getSqlMap().queryForObject("templateExpr.getMaxSubLineNb", linesSC);
    }

    public void deleteExprByTempl(GLSTMPLTCO tempVO) throws DAOException
    {
	getSqlMap().delete("templateExpr.deleteExprByTempl", tempVO.getGlstmpltVO());
    }

    public void deleteGLsByTempl(GLSTMPLTCO tempVO) throws DAOException
    {
	getSqlMap().delete("templateGL.deleteGLsByTempl", tempVO.getGlstmpltVO());
    }

    public int getAllTemplCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "template.getAllTemplatesMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("template.getAllTemplCount", tmplGridSC)).intValue();
	return nb;
    }

    public int getCurrencyListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateLines.getCurrencyListMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateLines.getCurrencyListCount", tmplGridSC)).intValue();
	return nb;
    }

    
    public GLSTMPLTGLSDETCO getMaxGLSubLineNb(GLSTMPLTSC lineSC) throws DAOException
    {
	return (GLSTMPLTGLSDETCO) getSqlMap().queryForObject("templateGL.getMaxGLSubLineNb", lineSC);
    }

    
    public List<GLSTMPLT_PARAM_LINKCO> getCrtList(TmplGridSC tmplGridSC) throws DAOException
    {
	ArrayList ll = (ArrayList<GLSTMPLT_PARAM_LINKCO>) getSqlMap()
		.queryForList("templateCrt.getCrtList", tmplGridSC);
	return ll;
    }

    
    public CommonDetailsVO getMaxCrtSubLineNb(CommonDetailsSC crtSC) throws DAOException
    {
	return (CommonDetailsVO) getSqlMap().queryForObject("templateCrt.getMaxCrtSubLineNb", crtSC);
    }

    public List<CommonDetailsVO> getFilterCrtDetList(TmplGridSC tmplGridSC) throws DAOException
    {
	if(tmplGridSC.isGrid())
	{
	    if(tmplGridSC.getTABLE_NAME().equals("V_IRP_TRS_PROVISION_CATEGORY"))
	    {
		tmplGridSC.setCOMP_CODE(null);
	    }
	    DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateCrt.getFilterCrtDetMap");
	    return getSqlMap().queryForList("templateCrt.getFilterCrtDetList", tmplGridSC, tmplGridSC.getRecToskip(),
		    tmplGridSC.getNbRec());
	}
	else
	{
	    if(tmplGridSC.getTABLE_NAME().equals("V_IRP_TRS_PROVISION_CATEGORY"))
	    {
		tmplGridSC.setCOMP_CODE(null);
	    }
	    return getSqlMap().queryForList("templateCrt.getFilterCrtDetList", tmplGridSC);
	}
    }

    public int getFilterCrtDetListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateCrt.getFilterCrtDetMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateCrt.getFilterCrtDetListCount", tmplGridSC)).intValue();
	return nb;
    }

    public void deleteCrtsByTemplate(GLSTMPLTCO tempVO) throws DAOException
    {
	getSqlMap().delete("templateCrt.deleteCrtsByTemplate", tempVO.getGlstmpltVO());
    }

    public void saveExpr(FTR_TMPLT_EXPRVO exprVO) throws DAOException
    {
	getSqlMap().insert("templateExpr.saveExpr", exprVO);
    }

    public List<CommonDetailsVO> getFilterCrtDetLList(TmplGridSC tmplGridSC) throws DAOException
    {
	if(tmplGridSC.getTABLE_NAME().equals("V_IRP_TRS_PROVISION_CATEGORY")
		|| tmplGridSC.getTABLE_NAME().equals("V_IRP_REGIONS"))
	{
	    tmplGridSC.setCOMP_CODE(null);
	}
	if(tmplGridSC.isGrid())
	{
	    DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateCrt.getFilterCrtDetMap");
	    if(tmplGridSC.getTABLE_NAME().equals("V_IRP_SUB_ECO_SECTORS"))
	    {
		return getSqlMap().queryForList("templateCrt.getFilterCrtDetLListSubEco", tmplGridSC,
			tmplGridSC.getRecToskip(), tmplGridSC.getNbRec());
	    }
	    return getSqlMap().queryForList("templateCrt.getFilterTmpltCrtDetLList", tmplGridSC, tmplGridSC.getRecToskip(),
		    tmplGridSC.getNbRec());
	}
	else
	{
	    if(tmplGridSC.getTABLE_NAME().equals("V_IRP_SUB_ECO_SECTORS"))
	    {
		return getSqlMap().queryForList("templateCrt.getFilterCrtDetLListSubEco", tmplGridSC);
	    }
	    return getSqlMap().queryForList("templateCrt.getFilterTmpltCrtDetLList", tmplGridSC);
	}
    }

    public int getFilterCrtDetLListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "templateCrt.getFilterCrtDetMap");
	int nb = 0;
	if(tmplGridSC.getTABLE_NAME().equals("V_IRP_SUB_ECO_SECTORS"))
	{
	    nb = ((Integer) getSqlMap().queryForObject("templateCrt.getFilterCrtDetLListCountSubEco", tmplGridSC))
		    .intValue() + 1;
	    return nb;
	}
	nb = ((Integer) getSqlMap().queryForObject("templateCrt.getFilterCrtDetLListCount", tmplGridSC)).intValue();
	return nb;
    }

    public void deleteGlsByLine(GLSTMPLTGLSDETSC glSC) throws DAOException
    {
	getSqlMap().delete("glMapper.deleteGlsByLine", glSC);
    }

    public Integer updateTemplate(GLSTMPLTCO irpTemplVO) throws DAOException
    {
	try
	{
	    return getSqlMap().update("template.updateTemplate", irpTemplVO);
	}
	catch(Exception e)
	{
	    e.printStackTrace();
	    return null;
	}
    }

    public int getRelatedReportsListCount(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "relatedReports.getRelatedReportsListMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("relatedReports.getRelatedReportsCount", tmplGridSC)).intValue();
	return nb;
    }

    public List<FTR_TMPL_REFCO> getRelatedReportsList(TmplGridSC tmplGridSC) throws DAOException
    {
	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "relatedReports.getRelatedReportsListMap");
	return getSqlMap().queryForList("relatedReports.getRelatedReportsList", tmplGridSC, tmplGridSC.getRecToskip(),
		tmplGridSC.getNbRec());
    }

    public int updateRelatedReports(FTR_TMPL_REFCO ftrTmplRefCO) throws DAOException
    {
	return getSqlMap().update("relatedReports.updateFTR_TMPL_REF", ftrTmplRefCO);
    }

    public void deleteAllLines(int templateCode) throws DAOException
    {
	GLSTMPLTSC glstmpltSC = new GLSTMPLTSC();
	glstmpltSC.setCODE(new BigDecimal(templateCode));
	getSqlMap().delete("template.deleteAllLines", glstmpltSC);
    }

    public List<FTR_ACCOUNTSCO> getAccountsList(FTR_ACCOUNTSSC ftrAccountsSC) throws DAOException
    {
	DAOHelper.fixGridMaps(ftrAccountsSC, getSqlMap(), "templateGL.getAccountsMap");
	ArrayList ll = (ArrayList<FTR_ACCOUNTSCO>) getSqlMap()
		.queryForList("templateGL.getAccountsList", ftrAccountsSC);
	return ll;
    }

    public int getAccountsListCount(FTR_ACCOUNTSSC ftrAccountsSC) throws DAOException
    {
	DAOHelper.fixGridMaps(ftrAccountsSC, getSqlMap(), "templateGL.getAccountsMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateGL.getAccountsListCount", ftrAccountsSC)).intValue();
	return nb;
    }

    public List<FTR_MISMATCH_REPORTCO> getMismatchsList(TmplGridSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "templateMismatch.getMismatchListMap");
	ArrayList ll = (ArrayList<FTR_MISMATCH_REPORTCO>) getSqlMap().queryForList("templateMismatch.getMismatchList",
		sc);
	return ll;
    }

    public int getMismatchsListCount(TmplGridSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "templateMismatch.getMismatchListMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateMismatch.getMismatchListCount", sc)).intValue();
	return nb;
    }

    public String getTemplateNameDependency(TmplGridSC sc) throws DAOException
    {
	return ((String) getSqlMap().queryForObject("templateMismatch.getTemplateNameDependency", sc));
    }

    public String getLineNameDependency(TmplGridSC sc) throws DAOException
    {
	return ((String) getSqlMap().queryForObject("templateMismatch.getLineNameDependency", sc));
    }

    public void saveCreateLike(GLSTMPLTSC sc) throws DAOException
    {
	getSqlMap().insert("template.createLike", sc);
    }

    public List<FTR_MISMATCH_REPORTCO> getMismatchesByLine(TmplGridSC sc) throws DAOException
    {
	return getSqlMap().queryForList("templateMismatch.getMismatchList", sc);
    }

    public void deleteRelatedReportsByTemplate(GLSTMPLTCO glstmpltCO) throws DAOException
    {
	getSqlMap().delete("relatedReports.deleteRelatedReportsByTemplate", glstmpltCO.getGlstmpltVO());
    }

    public void deleteMismatchesByTemplate(GLSTMPLTCO glstmpltCO) throws DAOException
    {
	getSqlMap().delete("templateMismatch.deleteMismatchesByTemplate", glstmpltCO.getGlstmpltVO());
    }

    public void deleteAllTemplateLines(GLSTMPLTSC glstmpltSC) throws DAOException
    {
	getSqlMap().delete("template.deleteAllTemplateLines", glstmpltSC);
    }

    public Integer checkOrderInOtherTemplate(TmplGridSC sc) throws DAOException
    {
	return (Integer)getSqlMap().queryForObject("relatedReports.checkOrderInOtherTemplate", sc);
    }
    /**
     * Method that checks if hdg calc type in template section
     */
    public int checkTemplateGLRecords(BigDecimal templateCode, BigDecimal compCode) throws DAOException
    {
	GLSTMPLTGLSDETSC sc = new GLSTMPLTGLSDETSC();
	sc.setCOMP_CODE(compCode);
	sc.setCODE(templateCode);
	sc.setCALC_TYPE(RepConstantsCommon.CALC_TYPE_HDG);
	return (Integer) getSqlMap().queryForObject("templateGL.checkTemplateGLRecords", sc);
	
    }
    
    @Override
    public int checkRTVcalcType(CommonDetailsSC sc) throws DAOException
    {
	return (Integer) getSqlMap().queryForObject("templateLines.checkRTVcalcType", sc);
    }
    
    @Override
    public HashMap<String,Integer> checkCrosscheckReport(CommonDetailsSC sc) throws DAOException
    {
	HashMap<String,Integer> resultHm = new HashMap<String, Integer>();
	sc.setPROG_REF(RepConstantsCommon.REP_BOB23);
	resultHm.put(RepConstantsCommon.REP_BOB23,(Integer) getSqlMap().queryForObject("template.checkCrosscheckReport", sc));
	sc.setPROG_REF(RepConstantsCommon.REP_BOB45);
	resultHm.put(RepConstantsCommon.REP_BOB45,(Integer) getSqlMap().queryForObject("template.checkCrosscheckReport", sc));
	return resultHm;
    }
    
    @Override
    public S_ADDITIONS_OPTIONSVO checkSmartMandatoryDetails(GLSTMPLT_CRITERIASC crtSC) throws DAOException
    {
	return (S_ADDITIONS_OPTIONSVO) getSqlMap().queryForObject("template.checkSmartMandatoryDetails", crtSC);
    }

    @Override
    public CTS_ADD_FIELDSVO retCrtAddFieldsLst(TmplGridSC tmplGridSC)throws DAOException
    {
    return (CTS_ADD_FIELDSVO) getSqlMap().queryForObject("template.retCrtAddFieldsLst", tmplGridSC);
    }
    
    @Override
    public List<CommonDetailsVO> retCrtAddFieldsDet3Lst(TmplGridSC tmplGridSC)throws DAOException
    {
    	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "template.returnCrtAddFieldsDet3Value");
    	if(tmplGridSC.getSortOrder()==null) //to be compatible with sybase since it does not work with a nested order by
		{
    		tmplGridSC.setSortOrder("ORDER BY CODE_STR ");
		}
	    if(tmplGridSC.getNbRec() == -1)
	    {
	    	return (List<CommonDetailsVO>) getSqlMap().queryForList("template.retCrtAddFieldsDet3Lst", tmplGridSC);
	    }
	    else
	    {
	    	return (List<CommonDetailsVO>)getSqlMap().queryForList("template.retCrtAddFieldsDet3Lst", tmplGridSC,tmplGridSC.getRecToskip(), tmplGridSC.getNbRec());
	    }
    }
    
    @Override
    public int retCrtAddFieldsDet3LstCount(TmplGridSC tmplGridSC) throws DAOException
    {
    	DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(), "template.returnCrtAddFieldsDet3Value");
		int nb = 0;
		nb= ((Integer) getSqlMap().queryForObject("template.retCrtAddFieldsDet3LstCount", tmplGridSC)).intValue();
		return nb;
    }
    
    @Override
    public List<CommonDetailsVO> retCrtAddFieldsDet3Dep(TmplGridSC tmplGridSC) throws DAOException
    {
    	return (List<CommonDetailsVO>) getSqlMap().queryForList("template.retCrtAddFieldsDet3Dep", tmplGridSC);
    }
    

}
