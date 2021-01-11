package com.path.bo.reporting.ftr.filterCriteria.impl;

import java.math.BigDecimal;
import java.util.List;

import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.filterCriteria.FilterCriteriaBO;
import com.path.dao.reporting.ftr.filterCriteria.FilterCriteriaDAO;
import com.path.dbmaps.vo.GLSTMPLT_CRITERIAVO;
import com.path.dbmaps.vo.GLSTMPLT_CRITERIAVOKey;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIACO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIASC;

public class FilterCriteriaBOImpl extends BaseBO implements FilterCriteriaBO
{
    private FilterCriteriaDAO filterCriteriaDAO;

    public FilterCriteriaDAO getFilterCriteriaDAO()
    {
	return filterCriteriaDAO;
    }

    public void setFilterCriteriaDAO(FilterCriteriaDAO filterCriteriaDAO)
    {
	this.filterCriteriaDAO = filterCriteriaDAO;
    }

    public int getFilterCritCount(GLSTMPLT_CRITERIASC sc) throws BaseException
    {
	return filterCriteriaDAO.getFilterCritCount(sc);
    }

    public List<GLSTMPLT_CRITERIACO> getFilterCritList(GLSTMPLT_CRITERIASC sc) throws BaseException
    {
	return filterCriteriaDAO.getFilterCritList(sc);
    }

    public void insertFilterCriteria(GLSTMPLT_CRITERIACO filterCritCO, String pageRef, BigDecimal compCode,
	    String appName, String lang) throws BaseException
    {
	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setObj_value(filterCritCO);
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);
	commonLibBO.checkRequired(filterCritCO.getBusinessHm(), checkRequiredCO);
	genericDAO.insert(filterCritCO.getGlstmpltCriteriaVO());
	// appy audit
	auditBO.callAudit(null, filterCritCO.getGlstmpltCriteriaVO(), filterCritCO.getGlstmpltCriteriaVO()
		.getAuditRefCO());
    }

    public void deleteFilterCriteria(GLSTMPLT_CRITERIACO CO, String pageRef, BigDecimal compCode, String appName,
	    String lang) throws BaseException
    {
	filterCriteriaDAO.deleteFilterCriteria(CO.getGlstmpltCriteriaVO());
	auditBO.callAudit(CO.getGlstmpltCriteriaVO(), CO.getGlstmpltCriteriaVO(), CO.getGlstmpltCriteriaVO()
		.getAuditRefCO());
	auditBO.insertAudit(CO.getGlstmpltCriteriaVO().getAuditRefCO());
    }

    public int findCriteriaUsageCount(GLSTMPLT_CRITERIAVO vo) throws BaseException
    {
	return filterCriteriaDAO.findCriteriaUsageCount(vo);
    }

    public int findCriteriaCount(GLSTMPLT_CRITERIAVO vo) throws BaseException
    {
	return filterCriteriaDAO.findCriteriaCount(vo);
    }

    public GLSTMPLT_CRITERIAVO getFilterCritById(GLSTMPLT_CRITERIAVO filterCritVO) throws BaseException
    {
	GLSTMPLT_CRITERIAVOKey voKey = filterCritVO;
	return (GLSTMPLT_CRITERIAVO) genericDAO.selectByPK(voKey);
    }

    public void updateFilterCriteria(GLSTMPLT_CRITERIACO filterCritCO, String pageRef, BigDecimal compCode,
	    String appName, String lang) throws BaseException
    {

	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setObj_value(filterCritCO);
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);

	commonLibBO.checkRequired(filterCritCO.getBusinessHm(), checkRequiredCO);
	Integer row = genericDAO.update(filterCritCO.getGlstmpltCriteriaVO());
	if(row == null || row < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
	// apply audit
	AuditRefCO refCO = filterCritCO.getGlstmpltCriteriaVO().getAuditRefCO();
	if(!RepConstantsCommon.CRITERIA_SMART.equals(filterCritCO.getGlstmpltCriteriaVO().getCRI_TYPE()))
	{
	    filterCritCO.getGlstmpltCriteriaVO().setSMART_CODE(null);
	}
	filterCritCO.getGlstmpltCriteriaVO().setLINKED_IND("");
	filterCritCO.getGlstmpltCriteriaVO().setLINKED_CODE(null);
	auditBO.callAudit(refCO.getAuditObj(), filterCritCO.getGlstmpltCriteriaVO(), refCO);
	auditBO.insertAudit(filterCritCO.getGlstmpltCriteriaVO().getAuditRefCO());

    }

    public GLSTMPLT_CRITERIAVO retrieveFitlCrt(GLSTMPLT_CRITERIAVO vo) throws BaseException
    {
	return (GLSTMPLT_CRITERIAVO) genericDAO.selectByPK(vo);
    }
}
