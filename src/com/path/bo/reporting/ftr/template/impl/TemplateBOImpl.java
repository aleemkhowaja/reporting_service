package com.path.bo.reporting.ftr.template.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.template.TemplateBO;
import com.path.dao.reporting.ftr.template.TemplateDAO;
import com.path.dbmaps.vo.CTS_ADD_FIELDSVO;
import com.path.dbmaps.vo.FTR_MISMATCH_REPORTVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.dbmaps.vo.GLSTMPLTGLSDETVO;
import com.path.dbmaps.vo.GLSTMPLTVO;
import com.path.dbmaps.vo.GLSTMPLT_PARAM_LINKVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.common.smart.SmartSC;
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

public class TemplateBOImpl extends BaseBO implements TemplateBO
{
    private TemplateDAO templateDAO;
    private CommonRepFuncBO commonRepFuncBO;

    public CommonRepFuncBO getCommonRepFuncBO()
    {
	return commonRepFuncBO;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }

    public TemplateDAO getTemplateDAO()
    {
	return templateDAO;
    }

    public void setTemplateDAO(TemplateDAO templateDAO)
    {
	this.templateDAO = templateDAO;
    }

    /**
     * method that retrieves all templates
     */
    public List<GLSTMPLTCO> getAllTemplates(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getAllTemplates(tmplGridSC);
    }

    /**
     * method that checks a template by pk
     */
    public GLSTMPLTVO checkTemplCode(GLSTMPLTVO irpTemplVO) throws BaseException
    {
	return (GLSTMPLTVO) genericDAO.selectByPK(irpTemplVO);
    }

    /**
     * method that gets all the lines of a template
     */
    public List<GLSTMPLTCO> getAllTemplateLines(GLSTMPLTSC iRPTEMPLATELINESSCKey) throws BaseException
    {
	return templateDAO.getAllTemplateLines(iRPTEMPLATELINESSCKey);
    }
    
    public List<GLSTMPLTCO> getAllTemplateLines(TmplGridSC sc) throws BaseException
    {
	return templateDAO.getAllTemplateLines(sc);
    }

    public int getAllTemplateLinesCount(TmplGridSC sc) throws BaseException
    {
	return templateDAO.getAllTemplateLinesCount(sc);
    }

    /**
     * method that gets all the gls by line
     */
    public List<GLSTMPLTGLSDETCO> getGLsByLine(GLSTMPLTGLSDETSC iRPTEMPLATEGLSC) throws BaseException
    {
	return templateDAO.getGLsByLine(iRPTEMPLATEGLSC);
    }

    /**
     * method that gets mismatches by line
     * 
     * @param sc
     * @return
     * @throws BaseException
     */
    public List<FTR_MISMATCH_REPORTCO> getMismatchesByLine(TmplGridSC sc) throws BaseException
    {
	return templateDAO.getMismatchesByLine(sc);
    }

    /**
     * method that gets the currency list
     */
    public List<GLSTMPLTCO> getCurrencyList(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getCurrencyList(tmplGridSC);
    }

    /**
     *method called on currency's dependency
     */
    public GLSTMPLTCO getCurrencyDependency(GLSTMPLTCO linesVO) throws BaseException
    {
	return templateDAO.getCurrencyDependency(linesVO);
    }

    public List<CommonDetailsVO> getFromGLList(TmplGridSC tmplGridS) throws BaseException
    {
	return templateDAO.getFromGLList(tmplGridS);
    }

    public CommonDetailsVO getFromGLDependency(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return templateDAO.getFromGLDependency(commonDetailsSC);
    }

    public List<CommonDetailsVO> getCompaniesList(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getCompaniesList(tmplGridSC);
    }

    public int getCompaniesListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getCompaniesListCount(tmplGridSC);
    }

    public List<CommonDetailsVO> getBranchesList(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getBranchesList(tmplGridSC);
    }

    public List<CommonDetailsVO> getDepartmentsList(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getDepartmentsList(tmplGridSC);
    }

    public List<CommonDetailsVO> getDivisonsList(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getDivisonsList(tmplGridSC);
    }

    public List<FTR_TMPLT_EXPRCO> getExprList(GLSTMPLTSC GLSTMPLTSC) throws BaseException
    {
	return templateDAO.getExprList(GLSTMPLTSC);
    }

    public FTR_TMPLT_EXPRCO getMaxSubLineNb(GLSTMPLTSC linesSC) throws BaseException
    {
	return templateDAO.getMaxSubLineNb(linesSC);
    }

    public void saveTemplate(GLSTMPLTCO irpTemplVO) throws BaseException
    {
	genericDAO.insert(irpTemplVO.getGlstmpltVO());
    }

    public void saveLine(GLSTMPLTCO lineCO) throws BaseException
    {
	genericDAO.insert(lineCO.getGlstmpltVO());
    }

    public void updateLine(GLSTMPLTCO lineCO) throws BaseException
    {

	if(lineCO.getNewLineNumber() != null)
	{
	    BigDecimal newLineNumber = lineCO.getNewLineNumber();
	    lineCO.getGlstmpltVO().setLINE_NBR(newLineNumber);
	}
	genericDAO.insert(lineCO.getGlstmpltVO());
    }

    public void deleteLine(GLSTMPLTCO irpLineVO, AuditRefCO refCO) throws BaseException
    {
	genericDAO.delete(irpLineVO.getGlstmpltVO());
    }

    public void updateTemplate(GLSTMPLTCO glstmpltCO) throws BaseException
    {
	Integer row = templateDAO.updateTemplate(glstmpltCO);
	if(row == null || row < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
    }

    public void saveGL(GLSTMPLTGLSDETVO glVO) throws BaseException
    {
	glVO.setTEMPLATE_TYPE("P");
	// no need to delete and reinsert already existing gls because already
	// deleted by the template function
	genericDAO.insert(glVO);
    }

    public void deleteGL(GLSTMPLTGLSDETVO glVO) throws BaseException
    {
	genericDAO.delete(glVO);
    }

    public void saveExpr(FTR_TMPLT_EXPRVO exprVO) throws BaseException
    {
	templateDAO.saveExpr(exprVO);
    }

    public int getAllTemplCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getAllTemplCount(tmplGridSC);
    }

    public GLSTMPLTGLSDETCO getMaxGLSubLineNb(GLSTMPLTSC lineSC) throws BaseException
    {
	return templateDAO.getMaxGLSubLineNb(lineSC);
    }

    public int getCurrencyListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getCurrencyListCount(tmplGridSC);
    }

    public int getFromGLListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getFromGLListCount(tmplGridSC);
    }

    @Override
    public List<GLSTMPLT_PARAM_LINKCO> getCrtList(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getCrtList(tmplGridSC);
    }

    @Override
    public CommonDetailsVO getMaxCrtSubLineNb(CommonDetailsSC crtSC) throws BaseException
    {
	return templateDAO.getMaxCrtSubLineNb(crtSC);
    }

    public void saveCrt(GLSTMPLT_PARAM_LINKVO crtVO) throws BaseException
    {
	genericDAO.insert(crtVO);
    }

    public void deleteCrt(GLSTMPLT_PARAM_LINKVO crtVO) throws BaseException
    {
	genericDAO.delete(crtVO);
    }

    public void updateCrt(GLSTMPLT_PARAM_LINKVO crtVO) throws BaseException
    {
	genericDAO.update(crtVO);
    }

    public void saveMis(FTR_MISMATCH_REPORTVO misVO) throws BaseException
    {
	genericDAO.insert(misVO);
    }

    public void deleteMis(FTR_MISMATCH_REPORTVO misVO) throws BaseException
    {
	genericDAO.delete(misVO);
    }

    public void updateMis(FTR_MISMATCH_REPORTVO misVO) throws BaseException
    {
	genericDAO.update(misVO);
    }

    public int getBranchesListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getBranchesListCount(tmplGridSC);
    }

    public int getDivisonsListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getDivisonsListCount(tmplGridSC);
    }

    public int getDepartmentsListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getDepartmentsListCount(tmplGridSC);
    }

    public List<CommonDetailsVO> getFilterCrtDetList(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getFilterCrtDetList(tmplGridSC);
    }

    public int getFilterCrtDetListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getFilterCrtDetListCount(tmplGridSC);
    }

    public List<CommonDetailsVO> getFilterCrtDetLList(TmplGridSC tmplGridSC) throws BaseException
    {
	List<CommonDetailsVO> retLst;
	String tblName = tmplGridSC.getTABLE_NAME();
	BigDecimal detCode = tmplGridSC.getCODE();
	if(tblName != null && tblName.toUpperCase(Locale.ENGLISH).equals("V_IRP_SUB_ECO_SECTORS") && detCode != null
		&& detCode.intValue() == 0)
	{
	    retLst = new ArrayList<CommonDetailsVO>();
	    CommonDetailsVO detVO = new CommonDetailsVO();
	    detVO.setBRIEF_DESC_ENG("All");
	    detVO.setCODE_STR("0");
	    retLst.add(detVO);
	    return retLst;
	}
	else
	{
	    if(commonLibBO.returnIsSybase() == 1)
	    {
		tmplGridSC.setIsSybase(Integer.parseInt(RepConstantsCommon.ONE));     
	    }
	    List<CommonDetailsVO> retLst1 = templateDAO.getFilterCrtDetLList(tmplGridSC);
	    retLst = new ArrayList<CommonDetailsVO>();
	    if(tblName != null && tblName.toUpperCase(Locale.ENGLISH).equals("V_IRP_SUB_ECO_SECTORS")
		    && detCode == null)
	    {
		CommonDetailsVO detVO = new CommonDetailsVO();
		detVO.setBRIEF_DESC_ENG("All");
		detVO.setCODE_STR("0");
		retLst.add(detVO);
	    }
	    retLst.addAll(retLst1);
	    return retLst;
	}
    }

    public int getFilterCrtDetLListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getFilterCrtDetLListCount(tmplGridSC);
    }

    public void deleteTemplWithDetails(GLSTMPLTCO glstmpltCO, AuditRefCO refCO) throws BaseException
    {
	// delete expressions
	templateDAO.deleteExprByTempl(glstmpltCO);
	// delete gls
	templateDAO.deleteGLsByTempl(glstmpltCO);

	// delete criterias
	templateDAO.deleteCrtsByTemplate(glstmpltCO);

	// delete related reports
	templateDAO.deleteRelatedReportsByTemplate(glstmpltCO);

	// delete mismatches
	templateDAO.deleteMismatchesByTemplate(glstmpltCO);

	// delete lines if exists
	templateDAO.deleteLinesByTemplate(glstmpltCO);

	// delete template
	// genericDAO.delete(glstmpltCO.getGlstmpltVO());
	GLSTMPLTSC glstmpltSC = new GLSTMPLTSC();
	glstmpltSC.setCODE(glstmpltCO.getGlstmpltVO().getCODE());
	templateDAO.deleteAllTemplateLines(glstmpltSC);
	// //apply audit
	auditBO.callAudit(glstmpltCO.getGlstmpltVO(), glstmpltCO.getGlstmpltVO(), refCO);
	auditBO.insertAudit(refCO);

    }

    public boolean checkIfAnyModification(Iterator<GLSTMPLTCO> itAddLines, Iterator<GLSTMPLTCO> itModifLines,
	    Iterator<GLSTMPLTCO> itDelLines, Iterator<GLSTMPLTCO> itDbLines,
	    HashMap<BigDecimal, GLSTMPLTCO> modifLinesMap, GLSTMPLTCO allTempl)
    {
	if(allTempl.getAddedReportsList().size() > 0 || allTempl.getUpdatedReportsList().size() > 0
		|| allTempl.getDeletedReportsList().size() > 0)
	{
	    return true;
	}
	while(itAddLines.hasNext())
	{
	    GLSTMPLTCO lGl = itAddLines.next();
	    if(!(((lGl.getAddGLMap() == null || lGl.getAddGLMap().size() == 0)
		    && (lGl.getModifGLMap() == null || lGl.getModifGLMap().size() == 0) && (lGl.getDelGLMap() == null || lGl
		    .getDelGLMap().size() == 0))
		    && ((lGl.getAddExprMap() == null || lGl.getAddExprMap().size() == 0)
			    && (lGl.getModifExprMap() == null || lGl.getModifExprMap().size() == 0) && (lGl
			    .getDelExprMap() == null || lGl.getDelExprMap().size() == 0)) && ((lGl.getAddCrtMap() == null || lGl
		    .getAddCrtMap().size() == 0)
		    && (lGl.getModifCrtMap() == null || lGl.getModifCrtMap().size() == 0)
		    && (lGl.getDelCrtMap() == null || lGl.getDelCrtMap().size() == 0) && ((lGl.getAddMismatchMap() == null || lGl
		    .getAddMismatchMap().size() == 0) && (lGl.getDelMismatchMap() == null || lGl.getDelMismatchMap()
		    .size() == 0)))))
	    {
		return true;
	    }
	}

	while(itModifLines.hasNext())
	{
	    GLSTMPLTCO lGl = itModifLines.next();
	    if(!(((lGl.getAddGLMap() == null || lGl.getAddGLMap().size() == 0)
		    && (lGl.getModifGLMap() == null || lGl.getModifGLMap().size() == 0) && (lGl.getDelGLMap() == null || lGl
		    .getDelGLMap().size() == 0))
		    && ((lGl.getAddExprMap() == null || lGl.getAddExprMap().size() == 0)
			    && (lGl.getModifExprMap() == null || lGl.getModifExprMap().size() == 0) && (lGl
			    .getDelExprMap() == null || lGl.getDelExprMap().size() == 0))
		    && ((lGl.getAddCrtMap() == null || lGl.getAddCrtMap().size() == 0)
			    && (lGl.getModifCrtMap() == null || lGl.getModifCrtMap().size() == 0) && (lGl
			    .getDelCrtMap() == null || lGl.getDelCrtMap().size() == 0)) && ((lGl.getAddMismatchMap() == null || lGl
		    .getAddMismatchMap().size() == 0) && (lGl.getDelMismatchMap() == null || lGl.getDelMismatchMap()
		    .size() == 0))))
	    {
		return true;
	    }
	}

	while(itDelLines.hasNext())
	{
	    GLSTMPLTCO lGl = itDelLines.next();
	    if(!(((lGl.getAddGLMap() == null || lGl.getAddGLMap().size() == 0)
		    && (lGl.getModifGLMap() == null || lGl.getModifGLMap().size() == 0) && (lGl.getDelGLMap() == null || lGl
		    .getDelGLMap().size() == 0))
		    && ((lGl.getAddExprMap() == null || lGl.getAddExprMap().size() == 0)
			    && (lGl.getModifExprMap() == null || lGl.getModifExprMap().size() == 0) && (lGl
			    .getDelExprMap() == null || lGl.getDelExprMap().size() == 0))
		    && ((lGl.getAddCrtMap() == null || lGl.getAddCrtMap().size() == 0)
			    && (lGl.getModifCrtMap() == null || lGl.getModifCrtMap().size() == 0) && (lGl
			    .getDelCrtMap() == null || lGl.getDelCrtMap().size() == 0)) && ((lGl.getAddMismatchMap() == null || lGl
		    .getAddMismatchMap().size() == 0) && (lGl.getDelMismatchMap() == null || lGl.getDelMismatchMap()
		    .size() == 0))))
	    {
		return true;
	    }
	}

	while(itDbLines.hasNext())
	{
	    GLSTMPLTCO lGl = itDbLines.next();
	    if(!(((lGl.getAddGLMap() == null || lGl.getAddGLMap().size() == 0)
		    && (lGl.getModifGLMap() == null || lGl.getModifGLMap().size() == 0) && (lGl.getDelGLMap() == null || lGl
		    .getDelGLMap().size() == 0))
		    && ((lGl.getAddExprMap() == null || lGl.getAddExprMap().size() == 0)
			    && (lGl.getModifExprMap() == null || lGl.getModifExprMap().size() == 0) && (lGl
			    .getDelExprMap() == null || lGl.getDelExprMap().size() == 0))
		    && ((lGl.getAddCrtMap() == null || lGl.getAddCrtMap().size() == 0)
			    && (lGl.getModifCrtMap() == null || lGl.getModifCrtMap().size() == 0) && (lGl
			    .getDelCrtMap() == null || lGl.getDelCrtMap().size() == 0)) && ((lGl.getAddMismatchMap() == null || lGl
		    .getAddMismatchMap().size() == 0) && (lGl.getDelMismatchMap() == null || lGl.getDelMismatchMap()
		    .size() == 0))))
	    {
		return true;
	    }
	}
	return false;
    }

    // public static void printHashContents(LinkedHashMap<BigDecimal, Object>
    // map, String hashName)
    // {
    // System.out.println(hashName + ":\n\n\n");
    //
    // for(Map.Entry<BigDecimal, Object> e : map.entrySet())
    // {
    // BigDecimal theKey = e.getKey();
    // GLSTMPLTCO gg = (GLSTMPLTCO) e.getValue();
    // System.out.println("key hash:" + theKey + "     key: " +
    // gg.getConcatKey() + "       new Line: "
    // + gg.getNewLineNumber() + "             true line: " +
    // gg.getGlstmpltVO().getLINE_NBR()
    // + "             description: " + gg.getGlstmpltVO().getBRIEF_NAME_ENG() +
    // "\n");
    // }
    // }

    public void saveTemplWithDetails(GLSTMPLTCO exstTemplVO, GLSTMPLTCO glstmpltCO, BigDecimal tempCode,
	    String usrName, GLSTMPLTCO allTempl, AuditRefCO refCO, String language) throws BaseException
    {
	GLSTMPLTVO existTemplVO = exstTemplVO.getGlstmpltVO();
	if(existTemplVO == null)
	{
	    // save templ
	    glstmpltCO.getGlstmpltVO().setCREATED_BY(usrName);
	    glstmpltCO.getGlstmpltVO().setBRIEF_NAME_ENG(allTempl.getTemplateTitle());
	    saveTemplate(glstmpltCO);

	    // add audit
	    refCO.setOperationType(AuditConstant.CREATED);
	    refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
	    refCO.setAuditObj(glstmpltCO.getGlstmpltVO());
	    auditBO.callAudit(null, glstmpltCO.getGlstmpltVO(), refCO);
	    // auditBO.insertAudit(refCO);
	    // if new do not update details in audit
	}
	else
	{
	    // stopped previously
	    GLSTMPLTVO oldTmpl = (GLSTMPLTVO) refCO.getAuditObj();
	    // update existing templateBO
	    glstmpltCO.getGlstmpltVO().setMODIFIED_BY(usrName);
	    glstmpltCO.getGlstmpltVO().setBRIEF_NAME_ENG(allTempl.getTemplateTitle());
	    // previously stopped
	    if(oldTmpl != null)
	    {
		glstmpltCO.getGlstmpltVO().setDATE_UPDATED(oldTmpl.getDATE_UPDATED());
	    }
	    updateTemplate(glstmpltCO);

	    // apply audit
	    glstmpltCO.getGlstmpltVO().setAuditRefCO(refCO);
	    if(oldTmpl != null)
	    {
		oldTmpl.setMODIFIED_BY(glstmpltCO.getGlstmpltVO().getMODIFIED_BY());
		oldTmpl.setDATE_MODIFIED(glstmpltCO.getGlstmpltVO().getDATE_MODIFIED());
		oldTmpl.setDATE_CREATED(glstmpltCO.getGlstmpltVO().getDATE_CREATED());
		oldTmpl.setDATE_UPDATED(glstmpltCO.getGlstmpltVO().getDATE_UPDATED());
		oldTmpl.setCREATED_BY(glstmpltCO.getGlstmpltVO().getCREATED_BY());
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(oldTmpl, glstmpltCO.getGlstmpltVO(), refCO);
		auditBO.insertAudit(refCO);
	    }
	    // previously stopped
	}

	// SAVE LINES
	LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap = allTempl.getAddLinesMap();
	LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
	HashMap<BigDecimal, GLSTMPLTCO> modifLinesMap = allTempl.getModifLinesMap();
	HashMap<BigDecimal, GLSTMPLTCO> delLinesMap = allTempl.getDelLinesMap();

	Iterator<GLSTMPLTCO> itAddLines = addLinesMap.values().iterator();
	Iterator<GLSTMPLTCO> itDelLines = delLinesMap.values().iterator();
	Iterator<GLSTMPLTCO> itDbLines = dbLinesMap.values().iterator();

	boolean glExprChanges = checkIfAnyModification(itAddLines, modifLinesMap.values().iterator(), itDelLines,
		itDbLines, modifLinesMap, allTempl);

	itAddLines = addLinesMap.values().iterator();
	itDelLines = delLinesMap.values().iterator();
	itDbLines = dbLinesMap.values().iterator();

	// if nothing in the screen have been done.
	if((addLinesMap == null || addLinesMap.isEmpty()) && (modifLinesMap == null || modifLinesMap.isEmpty())
		&& (delLinesMap == null || delLinesMap.isEmpty()) && !glExprChanges && !allTempl.isAnyModif())
	{
	    return;
	}
	/*
	 * added related reports
	 */
	LinkedHashMap<String, FTR_TMPL_REFCO> addedReportsList = allTempl.getAddedReportsList();
	/*
	 * deleted related reports
	 */
	LinkedHashMap<String, FTR_TMPL_REFCO> deletedReportsList = allTempl.getDeletedReportsList();
	/*
	 * updated related reports
	 */
	LinkedHashMap<String, FTR_TMPL_REFCO> updatedReportsList = allTempl.getUpdatedReportsList();
	/*
	 * before deleting all the lines, fill the
	 * dbglmap,dbcrtmap,dbexprmap,dbmismatchmap of the dblinesmap to handle
	 * later the lines that has not been modified or added in order to
	 * reinsert their previously existing information
	 */
	fillDbMapsOfDbLines(allTempl, language);
	templateDAO.deleteAllLines(tempCode.intValue());
	GLSTMPLTCO irpLineVO;
	HashMap<BigDecimal, GLSTMPLTCO> oldLinesMap = allTempl.getOldLinesMap();
	Iterator<GLSTMPLTGLSDETCO> itGL;
	Iterator<FTR_TMPLT_EXPRCO> itExpr;
	Iterator<GLSTMPLT_PARAM_LINKCO> itCrt;
	Iterator<FTR_TMPL_REFCO> itAddedReportsList = addedReportsList.values().iterator();
	Iterator<FTR_TMPL_REFCO> itDeletedReportsList = deletedReportsList.values().iterator();
	Iterator<FTR_TMPL_REFCO> itUpdatedReportsList = updatedReportsList.values().iterator();
	GLSTMPLTGLSDETCO myGLCO = null;
	FTR_TMPLT_EXPRCO exprCO;
	FTR_MISMATCH_REPORTCO misCO = null;
	GLSTMPLT_PARAM_LINKCO myCrtCO;

	// delete everything before starting (lines,gls...)
	// line numbers have been already adjusted so reinsert the lines
	// followed by the gls
	while(itDbLines.hasNext())
	// inserting the lines that has not been modified and already existing
	// in the database previously
	{
	    // enter loop if line not in modif map , delete map, insert map
	    irpLineVO = itDbLines.next();
	    boolean insert = true;
	    // checking if the line number appears in the addMap
	    while(itAddLines.hasNext())
	    {
		GLSTMPLTCO lGl = itAddLines.next();
		if(lGl.getGlstmpltVO().getLINE_NBR().equals(irpLineVO.getGlstmpltVO().getLINE_NBR()))
		{
		    insert = false;
		}
	    }
	    // checking if the line number appears in the modifMap
	    for(Map.Entry<BigDecimal, GLSTMPLTCO> e : modifLinesMap.entrySet())
	    {
		GLSTMPLTCO lGl = e.getValue();
		if(lGl.getGlstmpltVO().getLINE_NBR().equals(irpLineVO.getGlstmpltVO().getLINE_NBR()))
		{
		    insert = false;
		}
	    }
	    // the line will be reinserted because it's not an added or modified
	    // one
	    if(insert)
	    {
		irpLineVO.getGlstmpltVO().setCODE(tempCode);
		if(irpLineVO.getNewLineNumber() != irpLineVO.getGlstmpltVO().getLINE_NBR())
		{
		    irpLineVO.getGlstmpltVO().setTEMPLATE_TYPE("P");
		    if(irpLineVO.getNewLineNumber() != null)
		    {
			BigDecimal lineNbr = irpLineVO.getNewLineNumber();
			irpLineVO.getGlstmpltVO().setLINE_NBR(lineNbr);
		    }
		}
		BigDecimal concatKey = irpLineVO.getConcatKey();
		saveLine(irpLineVO, "update", refCO, oldLinesMap.get(concatKey));
		saveGlsExprCrtRelatedToLine(irpLineVO, existTemplVO, tempCode, refCO);
	    }
	    itAddLines = addLinesMap.values().iterator();
	}
	itAddLines = addLinesMap.values().iterator();
	itDbLines = dbLinesMap.values().iterator();
	// save new lines
	while(itAddLines.hasNext())
	{
	    irpLineVO = itAddLines.next();
	    irpLineVO.getGlstmpltVO().setCODE(tempCode);
	    if(irpLineVO.getNewLineNumber() != irpLineVO.getGlstmpltVO().getLINE_NBR())
	    {
		irpLineVO.getGlstmpltVO().setTEMPLATE_TYPE("P");
		if(irpLineVO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = irpLineVO.getNewLineNumber();
		    irpLineVO.getGlstmpltVO().setLINE_NBR(lineNbr);
		}
	    }
	    if(existTemplVO == null) // if it is a new template
	    {
		saveLine(irpLineVO, "add", null, null);
	    }
	    else
	    {
		saveLine(irpLineVO, "add", refCO, null);
	    }

	    // SAVE RELATED GLS
	    // only check the addGLMap cs for new line we don't have to update
	    // or delete existing GLs
	    // now we have reinsert the already existing GLs also
	    itGL = irpLineVO.getAddGLMap().values().iterator();
	    while(itGL.hasNext())
	    {
		myGLCO = itGL.next();
		myGLCO.getGlstmpltGlsDetVO().setCODE(tempCode);
		if(myGLCO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = myGLCO.getNewLineNumber();
		    myGLCO.getGlstmpltGlsDetVO().setLINE_NBR((lineNbr));
		}
		if(existTemplVO == null) // if it is a new template
		{
		    saveGL(myGLCO, "add", null, null);
		}
		else
		{
		    saveGL(myGLCO, "add", refCO, null);
		}
	    }

	    // update the GLs hashMaps after saving in DB
	    irpLineVO.getDbGLMap().putAll(irpLineVO.getAddGLMap());
	    irpLineVO.setAddGLMap(new LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO>());
	    irpLineVO.setModifGLMap(new HashMap<BigDecimal, GLSTMPLTGLSDETCO>());
	    irpLineVO.setDelGLMap(new HashMap<BigDecimal, GLSTMPLTGLSDETCO>());

	    // Save related Expr
	    itExpr = irpLineVO.getAddExprMap().values().iterator();
	    while(itExpr.hasNext())
	    {
		exprCO = itExpr.next();
		exprCO.getFtrTmpltExprVO().setCODE(tempCode);
		if(existTemplVO == null) // if it is a new template
		{
		    saveExpr(exprCO, "add", null, null);
		}
		else
		{
		    saveExpr(exprCO, "add", refCO, null);
		}
	    }
	    // Update the Expr. hashMaps after saving in DB
	    irpLineVO.getDbExprMap().putAll(irpLineVO.getAddExprMap());
	    irpLineVO.setAddExprMap(new LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO>());
	    irpLineVO.setModifExprMap(new HashMap<BigDecimal, FTR_TMPLT_EXPRCO>());
	    irpLineVO.setDelExprMap(new HashMap<BigDecimal, FTR_TMPLT_EXPRCO>());

	    // Save related Criterias
	    itCrt = irpLineVO.getAddCrtMap().values().iterator();
	    while(itCrt.hasNext())
	    {
		myCrtCO = itCrt.next();
		if(existTemplVO == null) // if it is a new template
		{
		    saveCrt(myCrtCO, "add", null, null);
		}
		else
		{
		    saveCrt(myCrtCO, "add", refCO, null);
		}
	    }
	    // //Update the Crt. hashMaps after saving in DB
	    irpLineVO.getDbCrtMap().putAll(irpLineVO.getAddCrtMap());
	    irpLineVO.setAddCrtMap(new LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>());
	    irpLineVO.setModifCrtMap(new HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>());
	    irpLineVO.setDelCrtMap(new HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>());

	    // for the added glb
	    Iterator<FTR_MISMATCH_REPORTCO> addMis = irpLineVO.getAddMismatchMap().values().iterator();
	    while(addMis.hasNext())
	    {
		misCO = addMis.next();
		if(misCO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = misCO.getNewLineNumber();
		    misCO.getFtrMissRepVO().setLINE_NO((lineNbr));
		}
		saveMis(misCO, "add", null, null);
	    }
	    // end for the mismatch
	}

	// update existing lines
	for(Map.Entry<BigDecimal, GLSTMPLTCO> e : modifLinesMap.entrySet())
	{
	    BigDecimal concatKey = e.getKey();
	    irpLineVO = e.getValue();
	    irpLineVO.getGlstmpltVO().setCODE(tempCode);
	    if(irpLineVO.getNewLineNumber() != irpLineVO.getGlstmpltVO().getLINE_NBR())
	    {
		irpLineVO.getGlstmpltVO().setTEMPLATE_TYPE("P");
	    }

	    saveLine(irpLineVO, "update", refCO, oldLinesMap.get(concatKey));

	    // after saving line add the save for the gls
	    itGL = irpLineVO.getDbGLMap().values().iterator();
	    boolean saveDBGL = true;
	    while(itGL.hasNext())
	    {
		myGLCO = itGL.next();
		Iterator<GLSTMPLTGLSDETCO> itgl = irpLineVO.getModifGLMap().values().iterator();
		while(itgl.hasNext())
		{
		    GLSTMPLTGLSDETCO glCO = itgl.next();
		    if(myGLCO.getGlstmpltGlsDetVO().getLINE_NBR().equals(glCO.getGlstmpltGlsDetVO().getLINE_NBR()))
		    {
			saveDBGL = false;
		    }
		}

		if(saveDBGL)
		{
		    myGLCO.getGlstmpltGlsDetVO().setCODE(tempCode);
		    if(myGLCO.getNewLineNumber() != null)
		    {
			BigDecimal lineNbr = myGLCO.getNewLineNumber();
			myGLCO.getGlstmpltGlsDetVO().setLINE_NBR((lineNbr));
		    }
		    if(existTemplVO == null) // if it is a new template
		    {
			saveGL(myGLCO, "add", null, null);
		    }
		    else
		    {
			saveGL(myGLCO, "add", refCO, null);
		    }
		}

	    }

	    // for the modified gls
	    Iterator<GLSTMPLTGLSDETCO> modGL = irpLineVO.getModifGLMap().values().iterator();
	    while(modGL.hasNext())
	    {
		myGLCO = modGL.next();
		myGLCO.getGlstmpltGlsDetVO().setCODE(tempCode);
		if(myGLCO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = myGLCO.getNewLineNumber();
		    myGLCO.getGlstmpltGlsDetVO().setLINE_NBR((lineNbr));
		}
		if(existTemplVO == null) // if it is a new template
		{
		    saveGL(myGLCO, "add", null, null);
		}
		else
		{
		    saveGL(myGLCO, "add", refCO, null);
		}
	    }

	    // for the added glb
	    Iterator<GLSTMPLTGLSDETCO> addGL = irpLineVO.getAddGLMap().values().iterator();
	    while(addGL.hasNext())
	    {
		myGLCO = addGL.next();
		myGLCO.getGlstmpltGlsDetVO().setCODE(tempCode);
		if(myGLCO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = myGLCO.getNewLineNumber();
		    myGLCO.getGlstmpltGlsDetVO().setLINE_NBR((lineNbr));
		}
		if(existTemplVO == null) // if it is a new template
		{
		    saveGL(myGLCO, "add", null, null);
		}
		else
		{
		    saveGL(myGLCO, "add", refCO, null);
		}
	    }

	    // for the mismatch
	    // for the modified (db) mismaches
	    Iterator<FTR_MISMATCH_REPORTCO> modMis = irpLineVO.getDbMismatchMap().values().iterator();
	    while(modMis.hasNext())
	    {
		misCO = modMis.next();
		if(misCO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = misCO.getNewLineNumber();
		    misCO.getFtrMissRepVO().setLINE_NO((lineNbr));
		}
		saveMis(misCO, "add", null, null);
	    }

	    // for the added glb
	    Iterator<FTR_MISMATCH_REPORTCO> addMis = irpLineVO.getAddMismatchMap().values().iterator();
	    while(addMis.hasNext())
	    {
		misCO = addMis.next();
		if(misCO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = misCO.getNewLineNumber();
		    misCO.getFtrMissRepVO().setLINE_NO((lineNbr));
		}
		saveMis(misCO, "add", null, null);
	    }
	    // end for the mismatch

	    // for the expressions
	    // for the db expressions
	    Iterator<FTR_TMPLT_EXPRCO> dbExpr = irpLineVO.getDbExprMap().values().iterator();
	    boolean saveDBEXPR = true;
	    while(dbExpr.hasNext())
	    {
		exprCO = dbExpr.next();
		Iterator<GLSTMPLTGLSDETCO> itgl = irpLineVO.getModifGLMap().values().iterator();
		while(itgl.hasNext())
		{
		    FTR_TMPLT_EXPRCO myExprCO = dbExpr.next();
		    if(exprCO.getFtrTmpltExprVO().getTMPLT_LINE_NO().equals(
			    myExprCO.getFtrTmpltExprVO().getTMPLT_LINE_NO()))
		    {
			saveDBEXPR = false;
		    }
		}

		if(saveDBEXPR)
		{
		    if(exprCO.getNewLineNumber() != null)
		    {
			BigDecimal lineNbr = exprCO.getNewLineNumber();
			exprCO.getFtrTmpltExprVO().setTMPLT_LINE_NO((lineNbr));
		    }
		    saveExpr(exprCO, "add", null, null);
		}

	    }
	    // for the modified expressions
	    Iterator<FTR_TMPLT_EXPRCO> modExpr = irpLineVO.getModifExprMap().values().iterator();
	    while(modExpr.hasNext())
	    {
		exprCO = modExpr.next();
		if(!NumberUtil.isEmptyDecimal(exprCO.getNewLineNumber()))
		{
		    BigDecimal lineNbr = exprCO.getNewLineNumber();
		    exprCO.getFtrTmpltExprVO().setTMPLT_LINE_NO((lineNbr));
		}
		saveExpr(exprCO, "add", null, null);
	    }

	    // for the added expressions
	    Iterator<FTR_TMPLT_EXPRCO> addExpr = irpLineVO.getAddExprMap().values().iterator();
	    while(addExpr.hasNext())
	    {
		exprCO = addExpr.next();		
	    if(!NumberUtil.isEmptyDecimal(exprCO.getNewLineNumber()))
		{
		    BigDecimal lineNbr = exprCO.getNewLineNumber();
		    exprCO.getFtrTmpltExprVO().setTMPLT_LINE_NO((lineNbr));
		}
		saveExpr(exprCO, "add", null, null);
	    }
	    // end for the expressions

	    // for the criteria
	    // for the db criteria
	    Iterator<GLSTMPLT_PARAM_LINKCO> dbCrt = irpLineVO.getDbCrtMap().values().iterator();
	    boolean saveDBCRT = true;
	    while(dbCrt.hasNext())
	    {
		myCrtCO = dbCrt.next();
		Iterator<GLSTMPLT_PARAM_LINKCO> itCriteria = irpLineVO.getModifCrtMap().values().iterator();
		while(itCriteria.hasNext())
		{
		    GLSTMPLT_PARAM_LINKCO lCrtCO = itCriteria.next();
		    if(myCrtCO.getGlstmpltParamLinkVO().getLINE_NO().equals(
			    lCrtCO.getGlstmpltParamLinkVO().getLINE_NO()))
		    {
			saveDBCRT = false;
		    }
		}

		if(saveDBCRT)
		{
		    if(myCrtCO.getNewLineNumber() != null)
		    {
			BigDecimal lineNbr = myCrtCO.getNewLineNumber();
			myCrtCO.getGlstmpltParamLinkVO().setLINE_NO((lineNbr));
		    }
		    saveCrt(myCrtCO, "add", null, null);
		}

	    }
	    // for the modified criteria
	    Iterator<GLSTMPLT_PARAM_LINKCO> modCrt = irpLineVO.getModifCrtMap().values().iterator();
	    while(modCrt.hasNext())
	    {
		myCrtCO = modCrt.next();
		if(myCrtCO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = myCrtCO.getNewLineNumber();
		    myCrtCO.getGlstmpltParamLinkVO().setLINE_NO((lineNbr));
		}
		saveCrt(myCrtCO, "add", null, null);
	    }

	    // for the added criteria
	    Iterator<GLSTMPLT_PARAM_LINKCO> addCrt = irpLineVO.getAddCrtMap().values().iterator();
	    while(addCrt.hasNext())
	    {
		myCrtCO = addCrt.next();
		if(myCrtCO.getNewLineNumber() != null)
		{
		    BigDecimal lineNbr = myCrtCO.getNewLineNumber();
		    myCrtCO.getGlstmpltParamLinkVO().setLINE_NO((lineNbr));
		}
		saveCrt(myCrtCO, "add", null, null);
	    }
	    // end for the criteria

	}

	// apply all the GLs operations on DB lines

	// lines
	dbLinesMap.putAll(addLinesMap);
	allTempl.setAddLinesMap(new LinkedHashMap<BigDecimal, GLSTMPLTCO>());
	allTempl.setModifLinesMap(new HashMap<BigDecimal, GLSTMPLTCO>());
	allTempl.setDelLinesMap(new HashMap<BigDecimal, GLSTMPLTCO>());

	// saving related reports
	while(itAddedReportsList.hasNext())
	{
	    FTR_TMPL_REFCO ftrTmplRefCO = itAddedReportsList.next();
	    saveRelatedRepDB(ftrTmplRefCO, "insert");
	}
	while(itDeletedReportsList.hasNext())
	{
	    FTR_TMPL_REFCO ftrTmplRefCO = itDeletedReportsList.next();
	    saveRelatedRepDB(ftrTmplRefCO, "delete");
	}
	while(itUpdatedReportsList.hasNext())
	{
	    FTR_TMPL_REFCO ftrTmplRefCO = itUpdatedReportsList.next();
	    saveRelatedRepDB(ftrTmplRefCO, "update");
	}

	/**
	 * Smart Management
	 */

	SmartSC smartSC = new SmartSC();
	smartSC.setAppName(refCO.getAppName());
	smartSC.setCompCode(glstmpltCO.getGlstmpltVO().getCOMP_CODE());
	smartSC.setBranchCode(BigDecimal.ONE);
	smartSC.setRunningDate(refCO.getRunningDate());
	smartSC.setUserId(refCO.getUserID());
	smartSC.setProgRef(refCO.getProgRef());
	smartBO.validateAndUpdateSmartDetails(glstmpltCO.getSmartCOList(), smartSC, refCO, glstmpltCO);

    }

    /**
     * 
     * @param irpLineVO
     * @param existTemplVO
     * @param tempCode
     * @param glVO
     * @param myGLCO
     * @param refCO
     * @param oldGlVO
     * @param exprVO
     * @param oldExprVO
     * @param crtVO
     * @param oldCrtVO
     * @throws BOException function used to save gls,expr,criteria,mismatch
     *             related to a db line
     */
    public void saveGlsExprCrtRelatedToLine(GLSTMPLTCO irpLineVO, GLSTMPLTVO existTemplVO, BigDecimal tempCode,
	    AuditRefCO refCO) throws BOException
    {
	// SAVE RELATED GLS
	// Save new GLs
	GLSTMPLTGLSDETCO myGLCO;
	Iterator<GLSTMPLTGLSDETCO> itGL = irpLineVO.getAddGLMap().values().iterator();
	while(itGL.hasNext())
	{
	    myGLCO = itGL.next();
	    myGLCO.getGlstmpltGlsDetVO().setCODE(tempCode);

	    if(existTemplVO == null) // if it is a new template
	    {
		saveGL(myGLCO, "add", null, null);
	    }
	    else
	    {
		saveGL(myGLCO, "add", refCO, null);
	    }
	}
	Iterator<Map.Entry<BigDecimal, GLSTMPLTGLSDETCO>> itFmap = irpLineVO.getDbGLMap().entrySet().iterator();
	Entry<BigDecimal, GLSTMPLTGLSDETCO> entry;
	while(itFmap.hasNext())
	{
	    entry = itFmap.next();
	    if(irpLineVO.getModifGLMap().get(entry.getKey()) == null)
	    {
		myGLCO = entry.getValue();
		myGLCO.getGlstmpltGlsDetVO().setCODE(tempCode);
		if(existTemplVO == null) // if it is a new template
		{
		    saveGL(myGLCO, "add", null, null);
		}
		else
		{
		    saveGL(myGLCO, "add", refCO, null);
		}
	    }
	}

	// update existing GLs
	Iterator<BigDecimal> ittGL = irpLineVO.getModifGLMap().keySet().iterator();
	while(ittGL.hasNext())
	{
	    BigDecimal glKey = ittGL.next();
	    myGLCO = irpLineVO.getModifGLMap().get(glKey);
	    myGLCO.getGlstmpltGlsDetVO().setCODE(tempCode);
	    saveGL(myGLCO, "update", refCO, irpLineVO.getOldGLMap().get(glKey));
	}

	// SAVE RELATED EXPRESSIONS
	Iterator<FTR_TMPLT_EXPRCO> itExpr = irpLineVO.getDbExprMap().values().iterator();
	while(itExpr.hasNext())
	{
	    FTR_TMPLT_EXPRCO exprCO = itExpr.next();
	    exprCO.getFtrTmpltExprVO().setCODE(tempCode);
	    if(existTemplVO == null) // if it is a new template
	    {
		saveExpr(exprCO, "add", null, null);
	    }
	    else
	    {
		saveExpr(exprCO, "add", refCO, null);
	    }
	}
	// Save new expr
	itExpr = irpLineVO.getAddExprMap().values().iterator();
	while(itExpr.hasNext())
	{
	    FTR_TMPLT_EXPRCO exprCO = itExpr.next();
	    exprCO.getFtrTmpltExprVO().setCODE(tempCode);
	    if(existTemplVO == null) // if it is a new template
	    {
		saveExpr(exprCO, "add", null, null);
	    }
	    else
	    {
		saveExpr(exprCO, "add", refCO, null);
	    }
	}

	// Update Existing Expr
	Iterator<BigDecimal> ittExpr = irpLineVO.getModifExprMap().keySet().iterator();
	while(ittExpr.hasNext())
	{
	    BigDecimal exprKey = ittExpr.next();
	    FTR_TMPLT_EXPRCO exprCO = irpLineVO.getModifExprMap().get(exprKey);
	    exprCO.getFtrTmpltExprVO().setCODE(tempCode);
	    saveExpr(exprCO, "update", refCO, irpLineVO.getOldExprMap().get(exprKey));
	}

	// SAVE REALTED CRITERIAS
	Iterator<GLSTMPLT_PARAM_LINKCO> itCrt = irpLineVO.getDbCrtMap().values().iterator();
	while(itCrt.hasNext())
	{
	    GLSTMPLT_PARAM_LINKCO myCrtCO = itCrt.next();
	    if(existTemplVO == null) // if it is a new template
	    {
		saveCrt(myCrtCO, "add", null, null);
	    }
	    else
	    {
		saveCrt(myCrtCO, "add", refCO, null);
	    }
	}

	// Save new crts
	itCrt = irpLineVO.getAddCrtMap().values().iterator();
	while(itCrt.hasNext())
	{
	    GLSTMPLT_PARAM_LINKCO myCrtCO = itCrt.next();
	    if(existTemplVO == null) // if it is a new template
	    {
		saveCrt(myCrtCO, "add", null, null);
	    }
	    else
	    {
		saveCrt(myCrtCO, "add", refCO, null);
	    }
	}

	// Update existing crts
	Iterator<BigDecimal> ittCrt = irpLineVO.getModifCrtMap().keySet().iterator();
	while(ittCrt.hasNext())
	{
	    BigDecimal crtKey = ittCrt.next();
	    GLSTMPLT_PARAM_LINKCO myCrtCO = irpLineVO.getModifCrtMap().get(crtKey);
	    saveCrt(myCrtCO, "update", refCO, irpLineVO.getOldCrtMap().get(crtKey));
	}

	// mismatch section
	// Save new mismatchs

	Iterator<FTR_MISMATCH_REPORTCO> itMis = irpLineVO.getDbMismatchMap().values().iterator();
	while(itMis.hasNext())
	{
	    FTR_MISMATCH_REPORTCO myMisCO = itMis.next();
	    if(existTemplVO == null) // if it is a new template
	    {
		saveMis(myMisCO, "add", null, null);
	    }
	    else
	    {
		saveMis(myMisCO, "add", refCO, null);
	    }
	}

	itMis = irpLineVO.getAddMismatchMap().values().iterator();
	while(itMis.hasNext())
	{
	    FTR_MISMATCH_REPORTCO myMisCO = itMis.next();
	    if(existTemplVO == null) // if it is a new template
	    {
		saveMis(myMisCO, "add", null, null);
	    }
	    else
	    {
		saveMis(myMisCO, "add", refCO, null);
	    }
	}
    }

    public void saveRelatedRepDB(FTR_TMPL_REFCO ftrTmplRefCO, String stmt)
    {
	try
	{

	    if(RepConstantsCommon.SQL_TYPE_INSERT.equals(stmt))
	    {
		genericDAO.insert(ftrTmplRefCO.getFtrTmplRefVO());
	    }
	    if(RepConstantsCommon.SQL_TYPE_DELETE.equals(stmt))
	    {
		genericDAO.delete(ftrTmplRefCO.getFtrTmplRefVO());
	    }
	    if(RepConstantsCommon.SQL_TYPE_UPDATE.equals(stmt))
	    {
		templateDAO.updateRelatedReports(ftrTmplRefCO);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public void saveLine(GLSTMPLTCO irpLineVO, String mode, AuditRefCO refCO, GLSTMPLTCO oldLineVO)
    {
	try
	{
	    //for procedure behavior
	    if(NumberUtil.isEmptyDecimal(irpLineVO.getGlstmpltVO().getCURRENCY()))
	    {
		irpLineVO.getGlstmpltVO().setCURRENCY(BigDecimal.ZERO);
	    }
	    String isBold = irpLineVO.getGlstmpltVO().getBOLD().equals(RepConstantsCommon.YES_CAP) ? RepConstantsCommon.Y : RepConstantsCommon.N;
	    irpLineVO.getGlstmpltVO().setBOLD(isBold);

	    String displZero = irpLineVO.getGlstmpltVO().getDISPLAY_ZEROS().equals(RepConstantsCommon.YES_CAP) ? RepConstantsCommon.Y : RepConstantsCommon.N;
	    irpLineVO.getGlstmpltVO().setDISPLAY_ZEROS(displZero);

	    String saveData = irpLineVO.getGlstmpltVO().getSAVE_DATA().equals(RepConstantsCommon.YES_CAP) ? RepConstantsCommon.Y : RepConstantsCommon.N;
	    irpLineVO.getGlstmpltVO().setSAVE_DATA(saveData);
	    
	    String displTotZero = irpLineVO.getGlstmpltVO().getDISP_LINE_TOT_ZERO().equals(RepConstantsCommon.YES_CAP) ? RepConstantsCommon.Y : RepConstantsCommon.N;
	    irpLineVO.getGlstmpltVO().setDISP_LINE_TOT_ZERO(displTotZero);

	    String isRound = RepConstantsCommon.N;
	    if(StringUtil.isNotEmpty(irpLineVO.getGlstmpltVO().getFOR_ROUND()))
	    {
		if(RepConstantsCommon.Y.equals(irpLineVO.getGlstmpltVO().getFOR_ROUND()))
		{
		    isRound = RepConstantsCommon.Y;
		}
		else
		{
		    isRound = irpLineVO.getGlstmpltVO().getFOR_ROUND().equals(RepConstantsCommon.YES_CAP) ? RepConstantsCommon.Y
			    : RepConstantsCommon.N;
		}
	    }

	    irpLineVO.getGlstmpltVO().setFOR_ROUND(isRound);

	    if(RepConstantsCommon.MODE_ADD.equals(mode))
	    {
		saveLine(irpLineVO);
	    }
	    if(RepConstantsCommon.MODE_UPDATE.equals(mode))
	    {
		updateLine(irpLineVO);
	    }

	    isBold = irpLineVO.getGlstmpltVO().getBOLD().equals(RepConstantsCommon.Y) ? RepConstantsCommon.YES_CAP : "NO";
	    irpLineVO.getGlstmpltVO().setBOLD(isBold);

	    displZero = irpLineVO.getGlstmpltVO().getDISPLAY_ZEROS().equals(RepConstantsCommon.Y) ? RepConstantsCommon.YES_CAP : "NO";
	    irpLineVO.getGlstmpltVO().setDISPLAY_ZEROS(displZero);
	    
	    saveData = irpLineVO.getGlstmpltVO().getSAVE_DATA().equals(RepConstantsCommon.Y) ? RepConstantsCommon.YES_CAP : RepConstantsCommon.N;
	    irpLineVO.getGlstmpltVO().setSAVE_DATA(saveData);

	    displTotZero = irpLineVO.getGlstmpltVO().getDISP_LINE_TOT_ZERO().equals(RepConstantsCommon.Y) ? RepConstantsCommon.YES_CAP : "NO";
	    irpLineVO.getGlstmpltVO().setDISP_LINE_TOT_ZERO(displTotZero);

	    isRound = irpLineVO.getGlstmpltVO().getFOR_ROUND().equals(RepConstantsCommon.Y) ? RepConstantsCommon.YES_CAP : "NO";
	    irpLineVO.getGlstmpltVO().setFOR_ROUND(isRound);
	    // reverse the YES and Y for display purpose

	    // apply audit
	    // previously stopped
	    if(RepConstantsCommon.MODE_ADD.equals(mode) && refCO != null)
	    {
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(irpLineVO.getGlstmpltVO(), irpLineVO.getGlstmpltVO(), refCO);
	    }
	    if(RepConstantsCommon.MODE_UPDATE.equals(mode) && oldLineVO != null) // condition
	    // on
	    // oldLineVO because
	    // the line should
	    // not be audited.it
	    // has been
	    // only reinserted.
	    {
		// call audit
		oldLineVO.setBOTTOM_BORDERStr(irpLineVO.getBOTTOM_BORDERStr());
		oldLineVO.setCURRENCYStr(irpLineVO.getCURRENCYStr());
		oldLineVO.setPRINTEDStr(irpLineVO.getPRINTEDStr());
		oldLineVO.getGlstmpltVO().setFOR_ROUND(irpLineVO.getGlstmpltVO().getFOR_ROUND());
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(oldLineVO.getGlstmpltVO(), irpLineVO.getGlstmpltVO(), refCO);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public void saveSelectedAccounts(GLSTMPLTGLSDETCO myGLCO)
    {
	try
	{
	    Iterator<FTR_ACCOUNTSCO> itt = myGLCO.getAddSelectAccListMap().values().iterator();
	    while(itt.hasNext())
	    {
		FTR_ACCOUNTSCO ftrAccountsCO = itt.next();

		if(ftrAccountsCO.getIncl() != null && ftrAccountsCO.getIncl().equals("true"))
		{
		    ftrAccountsCO.getFtrAccountVO().setINCLUDE(RepConstantsCommon.Y);
		}
		try
		{
		    ftrAccountsCO.getFtrAccountVO().setTEMP_CODE(myGLCO.getGlstmpltGlsDetVO().getCODE());
		    ftrAccountsCO.getFtrAccountVO().setTMPLT_LINE_NBR(myGLCO.getGlstmpltGlsDetVO().getLINE_NBR());
		    ftrAccountsCO.getFtrAccountVO().setSUB_LINE_NBR(myGLCO.getGlstmpltGlsDetVO().getLINE_NBR_DET());
		    genericDAO.delete(ftrAccountsCO.getFtrAccountVO());
		    if(ConstantsCommon.TRUE.equals(ftrAccountsCO.getIncl()))
		    {
			genericDAO.insert(ftrAccountsCO.getFtrAccountVO());
		    }
		}
		catch(Exception e)
		{
		    log.error(e, "");
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public void saveGL(GLSTMPLTGLSDETCO myGLCO, String mode, AuditRefCO refCO, GLSTMPLTGLSDETCO oldGLCO)
    {
	GLSTMPLTGLSDETVO glVO;
	try
	{
	    glVO = convertGlCOToVO(myGLCO);
	    myGLCO.setGlstmpltGlsDetVO(glVO);

	    if(RepConstantsCommon.MODE_ADD.equals(mode))
	    {
		saveGL(glVO);
		saveSelectedAccounts(myGLCO);
		// apply audit
		if(refCO != null)
		{
		    refCO.setOperationType(AuditConstant.UPDATE);
		    refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		    auditBO.callAudit(glVO, glVO, refCO);
		}

	    }
	    if(RepConstantsCommon.MODE_UPDATE.equals(mode))
	    {
		saveGL(glVO);
		saveSelectedAccounts(myGLCO);
		// apply audit
		GLSTMPLTGLSDETVO oldGLVO = convertGlCOToVO(oldGLCO);
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(oldGLVO, glVO, refCO);
	    }
	    if(RepConstantsCommon.MODE_DELETE.equals(mode))
	    {
		deleteGL(glVO);
		saveSelectedAccounts(myGLCO);
		// apply audit
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(glVO, glVO, refCO);

	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }

    public void saveCrt(GLSTMPLT_PARAM_LINKCO myCrtCO, String mode, AuditRefCO refCO, GLSTMPLT_PARAM_LINKCO oldCrtCO)
    {

	GLSTMPLT_PARAM_LINKVO crtVO;
	try
	{
	    crtVO = convertCrtCOToVO(myCrtCO);
	    if(RepConstantsCommon.MODE_ADD.equals(mode))
	    {
		saveCrt(crtVO);

		// apply audit
		if(refCO != null)
		{
		    refCO.setOperationType(AuditConstant.UPDATE);
		    refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		    auditBO.callAudit(crtVO, crtVO, refCO);
		}
	    }
	    else if(RepConstantsCommon.MODE_UPDATE.equals(mode))
	    {
		updateCrt(crtVO);

		// apply audit
		GLSTMPLT_PARAM_LINKVO oldCrtVO = convertCrtCOToVO(oldCrtCO);

		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(oldCrtVO, crtVO, refCO);
	    }
	    else if(RepConstantsCommon.MODE_DELETE.equals(mode))
	    {
		deleteCrt(crtVO);

		// apply audit
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(crtVO, crtVO, refCO);
	    }
	}
	catch(BaseException e)
	{
	    log.error(e, "");
	}
    }

    public void saveExpr(FTR_TMPLT_EXPRCO exprCO, String mode, AuditRefCO refCO, FTR_TMPLT_EXPRCO oldExprCO)
    {
	try
	{
	    FTR_TMPLT_EXPRVO exprVO = convertExprCOToVO(exprCO);
	    if(RepConstantsCommon.MODE_ADD.equals(mode))
	    {
		saveExpr(exprVO);

		// apply audit
		if(refCO != null)
		{
		    refCO.setOperationType(AuditConstant.UPDATE);
		    refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		    auditBO.callAudit(exprVO, exprVO, refCO);
		}
	    }
	    if(RepConstantsCommon.MODE_UPDATE.equals(mode))
	    {
		// apply audit
		FTR_TMPLT_EXPRVO oldExprVO = convertExprCOToVO(oldExprCO);
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(oldExprVO, exprVO, refCO);
	    }
	    if(RepConstantsCommon.MODE_DELETE.equals(mode))
	    {
		// apply audit
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(exprVO, exprVO, refCO);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}

    }

    public void saveMis(FTR_MISMATCH_REPORTCO myMisCO, String mode, AuditRefCO refCO, FTR_MISMATCH_REPORTCO oldMisCO)
    {
	try
	{
	    FTR_MISMATCH_REPORTVO misVO = convertMisCOToVO(myMisCO);
	    if(RepConstantsCommon.MODE_ADD.equals(mode))
	    {
		saveMis(misVO);

		// apply audit
		if(refCO != null)
		{
		    refCO.setOperationType(AuditConstant.UPDATE);
		    refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		    auditBO.callAudit(misVO, misVO, refCO);
		}
	    }
	    else if(RepConstantsCommon.MODE_UPDATE.equals(mode))
	    {
		updateMis(misVO);

		// apply audit
		FTR_MISMATCH_REPORTVO oldMisVO = convertMisCOToVO(oldMisCO);

		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(oldMisVO, misVO, refCO);
	    }
	    else if(RepConstantsCommon.MODE_DELETE.equals(mode))
	    {
		deleteMis(misVO);

		// apply audit
		refCO.setOperationType(AuditConstant.UPDATE);
		refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
		auditBO.callAudit(misVO, misVO, refCO);
	    }
	}
	catch(BaseException e)
	{
	    log.error(e, "");
	}
    }

    public FTR_TMPLT_EXPRVO convertExprCOToVO(FTR_TMPLT_EXPRCO exprCO)
    {
	FTR_TMPLT_EXPRVO exprVO = new FTR_TMPLT_EXPRVO();
	exprVO.setCOMP_CODE(exprCO.getFtrTmpltExprVO().getCOMP_CODE());
	exprVO.setEXP_TYPE(exprCO.getFtrTmpltExprVO().getEXP_TYPE());
	exprVO.setLEFT_OPERATOR(exprCO.getFtrTmpltExprVO().getLEFT_OPERATOR());
	exprVO.setOPERATOR(exprCO.getFtrTmpltExprVO().getOPERATOR());
	exprVO.setRIGHT_OPERATOR(exprCO.getFtrTmpltExprVO().getRIGHT_OPERATOR());
	exprVO.setLINE_NO(exprCO.getFtrTmpltExprVO().getLINE_NO());
	exprVO.setCODE(exprCO.getFtrTmpltExprVO().getCODE());
	exprVO.setTMPLT_LINE_NO(exprCO.getFtrTmpltExprVO().getTMPLT_LINE_NO());

	if(exprCO.getFtrTmpltExprVO().getEXP_TYPE().equals("L"))
	{
	    exprVO.setEXP_LINE(exprCO.getFtrTmpltExprVO().getEXP_VALUE());
	    exprVO.setEXP_VALUE(null);
	}
	else
	{
	    exprVO.setEXP_VALUE(exprCO.getFtrTmpltExprVO().getEXP_VALUE());
	    exprVO.setEXP_LINE(null);
	}
	return exprVO;

    }

    public FTR_MISMATCH_REPORTVO convertMisCOToVO(FTR_MISMATCH_REPORTCO misCO)
    {
	FTR_MISMATCH_REPORTVO misVO = new FTR_MISMATCH_REPORTVO();
	misVO.setCOMP_CODE(misCO.getFtrMissRepVO().getCOMP_CODE());
	misVO.setTMPLT_CODE(misCO.getFtrMissRepVO().getTMPLT_CODE());
	misVO.setTMPLT_CODE1(misCO.getFtrMissRepVO().getTMPLT_CODE1());
	misVO.setLINE_NO(misCO.getFtrMissRepVO().getLINE_NO());
	misVO.setSUB_LINE_NO(misCO.getFtrMissRepVO().getSUB_LINE_NO());

	return misVO;

    }

    public GLSTMPLT_PARAM_LINKVO convertCrtCOToVO(GLSTMPLT_PARAM_LINKCO crtCO)
    {
	GLSTMPLT_PARAM_LINKVO crtVO = new GLSTMPLT_PARAM_LINKVO();
	try
	{
	    crtVO.setCODE1(new BigDecimal(crtCO.getCODE1()));
	}
	catch(Exception e)
	{
	    crtVO.setCODE1(crtCO.getGlstmpltParamLinkVO().getCODE1());
	}

	crtVO.setCODE2(crtCO.getGlstmpltParamLinkVO().getCODE2());
	crtVO.setCOMP_CODE(crtCO.getGlstmpltParamLinkVO().getCOMP_CODE());
	crtVO.setCRITERIA_CODE(crtCO.getGlstmpltParamLinkVO().getCRITERIA_CODE());
	String include = crtCO.getGlstmpltParamLinkVO().getINCLUDE().equalsIgnoreCase(RepConstantsCommon.YES) ? RepConstantsCommon.Y : RepConstantsCommon.N;
	crtVO.setINCLUDE(include);
	crtVO.setOPERATOR(crtCO.getGlstmpltParamLinkVO().getOPERATOR());
	crtVO.setSUB_LINE_NO(crtCO.getGlstmpltParamLinkVO().getSUB_LINE_NO());
	crtVO.setTEMP_CODE((crtCO.getGlstmpltParamLinkVO().getTEMP_CODE()));
	crtVO.setLINE_NO((crtCO.getGlstmpltParamLinkVO().getLINE_NO()));

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT) 
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMT_BY_GL))
	{
	    crtVO.setOPERATOR(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}
	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MAT_DATE))
	{
	    crtVO.setMAT_DATE(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_ASSET_TRANS_TYPE)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_FOREX_DEAL_TYPE))
	{
	    crtVO.setTRS_ASSET_TYPE(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_TENURE)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_PERIOD)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MBK))
	{
	    crtVO.setOPERATOR1(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT) || crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMT_BY_GL))
	{
	    // crtVO.setOPERATOR(crtCO.getVALUE1()==null?null:crtCO.getVALUE1());
	    crtVO.setOPERATOR(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_PER)
		&& (crtCO.getCRI_LINE_GL() != null && crtCO.getCRI_LINE_GL().equals("L")))
	{
	    crtVO.setPERCENTAGE(new BigDecimal(crtCO.getVALUE1() == null ? null : crtCO.getVALUE1()));
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_CAT_TYPE))
	{
	    crtVO.setTYPE1(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}
	if(RepConstantsCommon.CRITERIA_GENDER.equals(crtCO.getCRITERIA_TYPE_CODE()))
	{
	    crtVO.setGENDER(crtCO.getGlstmpltParamLinkVO().getGENDER());
	}
	if(RepConstantsCommon.CRITERIA_SEC_CLASSIFICATION.equals(crtCO.getCRITERIA_TYPE_CODE()))
	{
	    crtVO.setSECURITY_CLASS(crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_RESIDENT)
//		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_TOTAL_DC)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_ASSET_TRANS_TYPE)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_FOREX_DEAL_TYPE)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CATEG_REGION)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MAT_DATE)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_PERIOD)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CBK_CIF_TYPE)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_GL_TERM)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_SEC_CATEG)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_TENURE)
		|| crtCO.getCRITERIA_TYPE_CODE().equals("WOD")
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMT_BY_GL))
	{
	    crtVO.setCODE1(crtCO.getVALUE1() == null ? null : new BigDecimal(crtCO.getVALUE1()));
	}
	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT))
	{
	    //905788 - code1 is set as zero in case of amount criteria, same behavior as power builder 
	    crtVO.setCODE1(BigDecimal.ZERO);
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_VAL_SEC))
	{
	    crtVO.setSECURITY_CODE1(crtCO.getCODE1() == null ? null : new BigDecimal(crtCO.getCODE1()));
	    crtVO.setSECURITY_CODE2(crtCO.getGlstmpltParamLinkVO().getCODE2() == null ? null : crtCO
		    .getGlstmpltParamLinkVO().getCODE2());
	}
	
	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CAF))
	{
	    crtVO.setSECURITY_CODE1(crtCO.getCODE3() == null ? null : new BigDecimal(crtCO.getCODE3()));
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_COLLATERALIZED_TYPE))
	{
	    crtVO.setCOLL_PER_TYPE(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEBIT_CREDIT))
	{
	    crtVO.setDEBIT_CREDIT_IND(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_TOTAL_DC))
	{
	    crtVO.setTOTAL_AMT_SIGN(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if((crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_TENURE) || crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_PERIOD))
		&& (null != crtCO.getVALUE1()))
	{
	    crtVO.setDEAL_TENURE(new BigDecimal(crtCO.getVALUE1()));
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_SOURCE_USAGES))
	{
	    crtVO.setSOURCE_USAGES(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CBK_CIF_TYPE))
	{
	    crtVO.setCBK_CIF_TYPE(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_SEC_CATEG))
	{
	    crtVO.setSECURITY_LISTED(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CATEG_REGION) && null != crtCO.getCODE1())
	{
	    crtVO.setCATEGORY(new BigDecimal(crtCO.getCODE1()));
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_RESIDENT))
	{
	    crtVO.setRESIDENT(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MBK)
		|| RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY.equals(crtCO.getCRITERIA_TYPE_CODE()))
	{
	    crtVO.setDATE_TYPE(crtCO.getGlstmpltParamLinkVO().getDATE_TYPE() == null ? null : crtCO
		    .getGlstmpltParamLinkVO().getDATE_TYPE());
	}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_GL_TERM))
	{
	    crtVO.setGL_TERM(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
	}

	// VALUE2

	if(crtCO.getGlstmpltParamLinkVO().getOPERATOR() != null
		&& (!crtCO.getGlstmpltParamLinkVO().getOPERATOR().equals(">")
			&& !crtCO.getGlstmpltParamLinkVO().getOPERATOR().equals("<")
			&& !crtCO.getGlstmpltParamLinkVO().getOPERATOR().equals(">=")
			&& !crtCO.getGlstmpltParamLinkVO().getOPERATOR().equals("<=") && (crtCO.getCRITERIA_TYPE_CODE()
			.equals(RepConstantsCommon.CRITERIA_AMOUNT) || RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY.equals(crtCO
			.getCRITERIA_TYPE_CODE()))))
	{
	    if(null != crtCO.getVALUE2())
	    {
		crtVO.setAMOUNT2(new BigDecimal(crtCO.getVALUE2()));
	    }
	    if(null != crtCO.getVALUE1())
	    {
		crtVO.setAMOUNT(new BigDecimal(crtCO.getVALUE1()));
	    }
	}
	else if (crtCO.getGlstmpltParamLinkVO().getOPERATOR() != null
			&& (crtCO.getGlstmpltParamLinkVO().getOPERATOR().equals(">")
				|| crtCO.getGlstmpltParamLinkVO().getOPERATOR().equals("<")
				|| crtCO.getGlstmpltParamLinkVO().getOPERATOR().equals(">=")
				|| crtCO.getGlstmpltParamLinkVO().getOPERATOR().equals("<=") && (crtCO.getCRITERIA_TYPE_CODE()
				.equals(RepConstantsCommon.CRITERIA_AMOUNT))))
		{
		    if(crtCO.getVALUE2()!=null)
		    {
			crtVO.setAMOUNT2(new BigDecimal(crtCO.getVALUE2()));
		    }
		    if(crtCO.getVALUE1()!=null)
		    {
			crtVO.setAMOUNT(new BigDecimal(crtCO.getVALUE1()));
		    }
		}

	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MBK))
	{
	    crtVO.setAMOUNT(crtCO.getVALUE1() == null ? null : new BigDecimal(crtCO.getVALUE1()));
	    crtVO.setAMOUNT2(crtCO.getVALUE2() == null ? null : new BigDecimal(crtCO.getVALUE2()));
	}
	if(RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY.equals(crtCO.getCRITERIA_TYPE_CODE()))
	{
	    crtVO.setOPERATOR1(crtCO.getCODE1());
	}
	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_ECONOMIC_SEC) || crtCO.getCRITERIA_TYPE_CODE().equals("CNT")
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MAIN_ECO_SEC) || crtCO.getCRITERIA_TYPE_CODE().equals("GDS")
		|| crtCO.getCRITERIA_TYPE_CODE().equals("DES") || crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MBK)
		|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMT_BY_GL))
	{
	    crtVO.setCODE2(crtCO.getGlstmpltParamLinkVO().getCODE2() == null ? null : crtCO.getGlstmpltParamLinkVO()
		    .getCODE2());
	}

	return crtVO;
    }

    public GLSTMPLTGLSDETVO convertGlCOToVO(GLSTMPLTGLSDETCO glCO)
    {
	GLSTMPLTGLSDETVO glVO = new GLSTMPLTGLSDETVO();
	glVO.setBRANCH_CODE(glCO.getGlstmpltGlsDetVO().getBRANCH_CODE());
	glVO.setCALC_TYPE(glCO.getGlstmpltGlsDetVO().getCALC_TYPE());
	glVO.setCOMP_CODE(glCO.getGlstmpltGlsDetVO().getCOMP_CODE());
	glVO.setDEPT_CODE(glCO.getGlstmpltGlsDetVO().getDEPT_CODE());
	glVO.setDIV_CODE(glCO.getGlstmpltGlsDetVO().getDIV_CODE());

	String exclClosEntry = glCO.getGlstmpltGlsDetVO().getEXCLUDE_CLOSING_ENTRY().equals(RepConstantsCommon.YES_CAP) ? RepConstantsCommon.Y
		: RepConstantsCommon.N;
	glVO.setEXCLUDE_CLOSING_ENTRY(exclClosEntry);
	glVO.setFROM_GL(glCO.getGlstmpltGlsDetVO().getFROM_GL());
	glVO.setGL_COMP(glCO.getGlstmpltGlsDetVO().getGL_COMP());
	glVO.setPERCENTAGE(glCO.getGlstmpltGlsDetVO().getPERCENTAGE());
	glVO.setLINE_NBR_DET(glCO.getGlstmpltGlsDetVO().getLINE_NBR_DET());
	glVO.setCODE(glCO.getGlstmpltGlsDetVO().getCODE());
	glVO.setLINE_NBR(glCO.getGlstmpltGlsDetVO().getLINE_NBR());
	glVO.setTO_GL(glCO.getGlstmpltGlsDetVO().getTO_GL());
	return glVO;

    }

    public GLSTMPLTVO retrieveTemplate(GLSTMPLTVO templVO) throws BaseException
    {
	return (GLSTMPLTVO) genericDAO.selectByPK(templVO);
    }

    public int getRelatedReportsListCount(TmplGridSC tmplGridSC) throws BaseException
    {
	return templateDAO.getRelatedReportsListCount(tmplGridSC);
    }

    public List<FTR_TMPL_REFCO> getRelatedReportsList(TmplGridSC tmplGridSC) throws BaseException
    {
	if(1 == ConstantsCommon.CURR_DBMS_SQLSERVER)
	{
	    tmplGridSC.setIsSQLServer(ConstantsCommon.CURR_DBMS_SQLSERVER);
	    if(tmplGridSC.getRelatedRepCodeParam().isEmpty())
	    {
		tmplGridSC.setRelatedRepCodeParam("-1");
	    }
	}
	return templateDAO.getRelatedReportsList(tmplGridSC);
    }

    public void deleteRelRep(FTR_TMPL_REFCO ftrTmplRefCO) throws BaseException
    {
	genericDAO.delete(ftrTmplRefCO.getFtrTmplRefVO());
    }

    /**
     * 
     * @param allTempl
     * @param language
     * @throws BaseException
     * 
     *             Before deleting all the lines, fill the
     *             dbglmap,dbcrtmap,dbexprmap,dbmismatchmap of the DBLINESMAP to
     *             handle later the lines that has not been modified or added in
     *             order to reinsert their previously existing information
     */
    public void fillDbMapsOfDbLines(GLSTMPLTCO allTempl, String language) throws BaseException
    {
	LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = allTempl.getDbLinesMap();
	Iterator<GLSTMPLTCO> itDbLines = dbLinesMap.values().iterator();
	GLSTMPLTCO line;
	GLSTMPLTGLSDETSC sc = new GLSTMPLTGLSDETSC();
	TmplGridSC crtSC = new TmplGridSC();
	GLSTMPLTSC lineSC = new GLSTMPLTSC();
	while(itDbLines.hasNext())
	{
	    line = itDbLines.next();
	    // fill dbGlMap
	    sc.setCOMP_CODE(line.getGlstmpltVO().getCOMP_CODE());
	    sc.setCODE(line.getGlstmpltVO().getCODE());
	    sc.setLINE_NBR(line.getGlstmpltVO().getLINE_NBR());
	    sc.setLANG_CODE(language);
	    List<GLSTMPLTGLSDETCO> glsList = templateDAO.getGLsByLine(sc);
	    // the 3rd & is used in order to not fill a dbglmap which all lines
	    // has been deleted by the user in the screen
	    if(line.getDbGLMap().isEmpty() && !glsList.isEmpty() && line.getDelGLMap().isEmpty())
	    {
		for(int i = 0; i < glsList.size(); i++)
		{
		    GLSTMPLTGLSDETCO glCOO = glsList.get(i);
		    line.getDbGLMap().put(glCOO.getConcatKey(), glCOO);
		}
	    }
	    // fill dbcrtmap
	    crtSC.setCOMP_CODE(line.getGlstmpltVO().getCOMP_CODE());
	    crtSC.setCODE(line.getGlstmpltVO().getCODE());
	    crtSC.setLINE_NBR(line.getGlstmpltVO().getLINE_NBR());
	    crtSC.setLANG_CODE(language);
	    List<GLSTMPLT_PARAM_LINKCO> dbCrtLst = templateDAO.getCrtList(crtSC);
	    // the 3rd & is used in order to not fill a dbglmap which all lines
	    // has been deleted by the user in the screen
	    if(line.getDbCrtMap().isEmpty() && !dbCrtLst.isEmpty() && line.getDelCrtMap().isEmpty())
	    {
		for(int i = 0; i < dbCrtLst.size(); i++)
		{
		    GLSTMPLT_PARAM_LINKCO crtCO = dbCrtLst.get(i);
		    line.getDbCrtMap().put(crtCO.getConcatKey(), crtCO);
		}
	    }
	    // fill the expresionMap
	    lineSC.setCODE(line.getGlstmpltVO().getCODE());
	    lineSC.setCOMP_CODE(line.getGlstmpltVO().getCOMP_CODE());
	    lineSC.setLINE_NBR(line.getGlstmpltVO().getLINE_NBR());
	    List<FTR_TMPLT_EXPRCO> dbExprList = templateDAO.getExprList(lineSC);
	    // the 3rd & is used in order to not fill a dbglmap which all lines
	    // has been deleted by the user in the screen
	    if(line.getDbExprMap().isEmpty() && !dbExprList.isEmpty() && line.getDelExprMap().isEmpty())
	    {
		for(int i = 0; i < dbExprList.size(); i++)
		{
		    FTR_TMPLT_EXPRCO exprCO = dbExprList.get(i);
		    if(exprCO.getFtrTmpltExprVO().getEXP_TYPE().equals(RepConstantsCommon.TMPLT_EXPR_LINE))
		    {
			exprCO.getFtrTmpltExprVO().setEXP_VALUE(exprCO.getFtrTmpltExprVO().getEXP_LINE());
			exprCO.getFtrTmpltExprVO().setEXP_LINE(null);
		    }	    
		    line.getDbExprMap().put(exprCO.getConcatKey(), exprCO);
		}
	    }
	    // fill the mismatchmap
	    List<FTR_MISMATCH_REPORTCO> misList = templateDAO.getMismatchsList(crtSC);
	    // the 3rd & is used in order to not fill a dbglmap which all lines
	    // has been deleted by the user in the screen
	    if(line.getDbMismatchMap().isEmpty() && !misList.isEmpty() && line.getDelMismatchMap().isEmpty())
	    {
		for(int i = 0; i < misList.size(); i++)
		{
		    FTR_MISMATCH_REPORTCO misCO = misList.get(i);
		    line.getDbMismatchMap().put(misCO.getConcatKey(), misCO);
		}
	    }
	}
    }

    public void checkRequiredFields(Object myCO, String pageRef, BigDecimal compCode, String appName, String lang)
	    throws BaseException
    {
	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);

	if(myCO instanceof GLSTMPLTCO)
	{
	    GLSTMPLTCO glstmpltCO = (GLSTMPLTCO) myCO;
	    checkRequiredCO.setObj_value(glstmpltCO);
	    commonLibBO.checkRequired(glstmpltCO.getBusinessHm(), checkRequiredCO);
	}
	else if(myCO instanceof GLSTMPLTGLSDETCO)
	{
	    GLSTMPLTGLSDETCO glstmpltGlsDetCO = (GLSTMPLTGLSDETCO) myCO;
	    checkRequiredCO.setObj_value(glstmpltGlsDetCO);
	    commonLibBO.checkRequired(checkRequiredCO);
	}
	else if(myCO instanceof FTR_MISMATCH_REPORTCO)
	{
	    FTR_MISMATCH_REPORTCO ftrMissRepCO = (FTR_MISMATCH_REPORTCO) myCO;
	    checkRequiredCO.setObj_value(ftrMissRepCO);
	    commonLibBO.checkRequired(checkRequiredCO);
	}

	else if(myCO instanceof FTR_TMPLT_EXPRCO)
	{
	    FTR_TMPLT_EXPRCO exprCO = (FTR_TMPLT_EXPRCO) myCO;
	    checkRequiredCO.setObj_value(exprCO.getFtrTmpltExprVO());
	    commonLibBO.checkRequired(checkRequiredCO);
	}
	else if(myCO instanceof BigDecimal)
	{
	    GLSTMPLTCO gg = new GLSTMPLTCO();
	    if(((BigDecimal) myCO).intValue() < 0)
	    {
		gg.getGlstmpltVO().setCODE(null);
		gg.getGlstmpltVO().setBRIEF_NAME_ENG("ff");
		gg.getGlstmpltVO().setLINE_NBR(new BigDecimal(2));
	    }
	    else
	    {
		gg.getGlstmpltVO().setCODE((BigDecimal) myCO);
		gg.getGlstmpltVO().setBRIEF_NAME_ENG("ff");
		gg.getGlstmpltVO().setLINE_NBR(new BigDecimal(2));
	    }
	    checkRequiredCO.setObj_value(gg);
	    commonLibBO.checkRequired(checkRequiredCO);
	}

	else if(myCO instanceof GLSTMPLT_PARAM_LINKCO)
	{
	    GLSTMPLT_PARAM_LINKCO crtCO = (GLSTMPLT_PARAM_LINKCO) myCO;
	    checkRequiredCO.setObj_value(crtCO);
	    commonLibBO.checkRequired(crtCO.getBusinessHm(), checkRequiredCO);
	}
    }

    public List<FTR_ACCOUNTSCO> getAccountsList(FTR_ACCOUNTSSC ftrAccountsSC) throws BaseException
    {
	return templateDAO.getAccountsList(ftrAccountsSC);
    }

    public int getAccountsListCount(FTR_ACCOUNTSSC ftrAccountsSC) throws BaseException
    {
	return templateDAO.getAccountsListCount(ftrAccountsSC);
    }

    public List<FTR_MISMATCH_REPORTCO> getMismatchsList(TmplGridSC sc) throws BaseException
    {
	return templateDAO.getMismatchsList(sc);
    }

    public int getMismatchsListCount(TmplGridSC sc) throws BaseException
    {
	return templateDAO.getMismatchsListCount(sc);
    }

    public String getTemplateNameDependency(TmplGridSC sc) throws BaseException
    {
	return templateDAO.getTemplateNameDependency(sc);
    }

    public String getLineNameDependency(TmplGridSC sc) throws BaseException
    {
	return templateDAO.getLineNameDependency(sc);
    }

    public void saveCreateLike(GLSTMPLTSC sc) throws BaseException
    {
	templateDAO.saveCreateLike(sc);
    }
    
    public Integer checkOrderInOtherTemplate(TmplGridSC sc) throws BaseException
    {
	return templateDAO.checkOrderInOtherTemplate(sc);
    }
    /**
     * Method that checks if hdg calc type in template section
     */
    public int checkTemplateGLRecords(BigDecimal templateCode,BigDecimal compCode) throws BaseException
    {
	return templateDAO.checkTemplateGLRecords(templateCode,compCode);
    }
    
    @Override
    public int checkRTVcalcType(CommonDetailsSC sc) throws BaseException
    {
	return templateDAO.checkRTVcalcType(sc);
    }
    
    @Override
    public HashMap<String,Integer> checkCrosscheckReport(CommonDetailsSC sc) throws BaseException
    {
	return templateDAO.checkCrosscheckReport(sc);
    }

    @Override
    public S_ADDITIONS_OPTIONSVO checkSmartMandatoryDetails(GLSTMPLT_CRITERIASC crtSC) throws BaseException
    {
    	return templateDAO.checkSmartMandatoryDetails(crtSC);
    }
    
    
    @Override
    public List<CommonDetailsVO> retCrtAddFieldsDet3Lst(HashMap<String, Object> repSessionCOMap, TmplGridSC tmplGridSC ) throws BaseException
    {
    	SessionCO reportSession = (SessionCO) PathPropertyUtil.convertToObject(repSessionCOMap, SessionCO.class);
    	CTS_ADD_FIELDSVO cafVO = templateDAO.retCrtAddFieldsLst(tmplGridSC);
    	
    	List<CommonDetailsVO> retList =new ArrayList<CommonDetailsVO>();
    	if(!StringUtil.nullToEmpty(cafVO.getTABLE_NAME()).isEmpty() )
    	{
    		StringBuffer sqlQueryBuffer = buildDynamicSqlQuery(cafVO,reportSession);
	    	tmplGridSC.setCAF_SQL_QUERY(sqlQueryBuffer.toString());
	    	retList= templateDAO.retCrtAddFieldsDet3Lst(tmplGridSC);
    	}
    	return retList;
    }
    
    @Override
    public int retCrtAddFieldsDet3LstCount(HashMap<String, Object> repSessionCOMap, TmplGridSC tmplGridSC) throws BaseException
    {
		SessionCO reportSession = (SessionCO) PathPropertyUtil.convertToObject(repSessionCOMap, SessionCO.class);
    	CTS_ADD_FIELDSVO cafVO = templateDAO.retCrtAddFieldsLst(tmplGridSC);
    	int cnt=0;
    	if(!StringUtil.nullToEmpty(cafVO.getTABLE_NAME()).isEmpty() )
    	{
    		StringBuffer sqlQueryBuffer = buildDynamicSqlQuery(cafVO,reportSession);
    		String sqlQuery = sqlQueryBuffer.toString();
	    	tmplGridSC.setCAF_SQL_QUERY(sqlQuery);
	    	cnt= templateDAO.retCrtAddFieldsDet3LstCount(tmplGridSC);
    	}
    	return cnt;
    }
    
    public List<CommonDetailsVO> retCrtAddFieldsDet3Dep(HashMap<String, Object> repSessionCOMap, TmplGridSC tmplGridSC ) throws BaseException
    {
    	SessionCO reportSession = (SessionCO) PathPropertyUtil.convertToObject(repSessionCOMap, SessionCO.class);
    	CTS_ADD_FIELDSVO cafVO = templateDAO.retCrtAddFieldsLst(tmplGridSC);
    	List<CommonDetailsVO> retList =new ArrayList<CommonDetailsVO>();
    	if(!StringUtil.nullToEmpty(cafVO.getTABLE_NAME()).isEmpty() )
    	{
    		StringBuffer sqlQueryBuffer = buildDynamicSqlQuery(cafVO,reportSession);
    		sqlQueryBuffer.append(" AND " + cafVO.getDATA_ENTRY() + " = " + tmplGridSC.getCODE());
    		String sqlQuery = sqlQueryBuffer.toString();
	    	tmplGridSC.setCAF_SQL_QUERY(sqlQuery);
	    	retList= templateDAO.retCrtAddFieldsDet3Dep(tmplGridSC);
    	}
    	return retList;
    }
    private String retParamVal(String param,SessionCO sessionCO)throws BaseException
    {
    	if(RepConstantsCommon.gv_company_code.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getCompanyCode());
    	}
    	else if(RepConstantsCommon.gv_branch_code.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getBranchCode());
    	}
    	else if(RepConstantsCommon.gv_currency.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getBaseCurrencyCode());
    	}
    	else if(RepConstantsCommon.gv_fiscal_year.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getFiscalYear());
    	}
    	else if(RepConstantsCommon.gv_cy_dec.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getBaseCurrDecPoint());
    	}
    	else if(RepConstantsCommon.gv_exposure_cy.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getExposCurCode());
    	}
    	else if(RepConstantsCommon.gi_applicationversion.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getAppId());/////bashaaaaa
    	}
    	else if(RepConstantsCommon.gv_rep_id.equals(param))
    	{
    		return "0";
    	}
    	else if(RepConstantsCommon.gv_company_name.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getCompanyName());
    	}
    	else if(RepConstantsCommon.gv_company_name_arabic.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getCompanyArabName());
    	}
    	else if(RepConstantsCommon.gv_userid.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getUserName());
    	}
    	else if(RepConstantsCommon.gv_window_reference.equals(param))
    	{
    		param = "";
    	}
    	else if(RepConstantsCommon.gc_cli_type.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getClientType());
    	}
    	else if(RepConstantsCommon.gs_base_cy_name_eng.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getBaseCurrencyName());
    	}
    	else if(RepConstantsCommon.gv_exposure_cy_name_eng.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getExposCurName());
    	}
    	else if(RepConstantsCommon.gv_application.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getCurrentAppName());
    	}
    	else if(RepConstantsCommon.f_get_computer_name.equals(param))
    	{
    		param = "";
    	}
    	else if(RepConstantsCommon.gv_system_date.equals(param))
    	{
    		param = StringUtil.nullToEmpty(sessionCO.getRunningDateRET());
    	}
    	
    		return param;
    }
    
    private StringBuffer buildDynamicSqlQuery(CTS_ADD_FIELDSVO cafVO,SessionCO reportSession)throws BaseException
    {
    	StringBuffer dynQuery = new StringBuffer();
    	dynQuery.append("SELECT "+ cafVO.getDATA_ENTRY() + " AS CODE_STR,"+cafVO.getDESC_OUTPUT() + " AS BRIEF_DESC_ENG FROM " + cafVO.getTABLE_NAME()+" WHERE 1 = 1 ");
    	
    	if(!StringUtil.nullToEmpty(cafVO.getPARAM1()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_TYPE1()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_VALUE1()).isEmpty())
    	{
    		dynQuery.append(" AND "+cafVO.getPARAM1()+" = " + retParamVal(cafVO.getPARAM_VALUE1(),reportSession));
    	}
    	if(!StringUtil.nullToEmpty(cafVO.getPARAM2()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_TYPE2()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_VALUE2()).isEmpty())
    	{
    		dynQuery.append(" AND "+cafVO.getPARAM2()+" = " + retParamVal(cafVO.getPARAM_VALUE2(),reportSession));
    	}
    	if(!StringUtil.nullToEmpty(cafVO.getPARAM3()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_TYPE3()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_VALUE3()).isEmpty())
    	{
    		dynQuery.append(" AND "+cafVO.getPARAM3()+" = " + retParamVal(cafVO.getPARAM_VALUE3(),reportSession));
    	}
    	if(!StringUtil.nullToEmpty(cafVO.getPARAM4()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_TYPE4()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_VALUE4()).isEmpty())
    	{
    		dynQuery.append(" AND "+cafVO.getPARAM4()+" = " + retParamVal(cafVO.getPARAM_VALUE4(),reportSession));
    	}
    	if(!StringUtil.nullToEmpty(cafVO.getPARAM5()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_TYPE5()).isEmpty() &&!StringUtil.nullToEmpty(cafVO.getPARAM_VALUE5()).isEmpty())
    	{
    		dynQuery.append(" AND "+cafVO.getPARAM5()+" = " + retParamVal(cafVO.getPARAM_VALUE5(),reportSession));
    	}
    	return dynQuery;
    }

}
