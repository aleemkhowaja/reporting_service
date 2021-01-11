package com.path.bo.reporting.ftr.filterCriteria;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.GLSTMPLT_CRITERIAVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIACO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIASC;

public interface FilterCriteriaBO {
	public int getFilterCritCount(GLSTMPLT_CRITERIASC sc) throws BaseException;
	public List<GLSTMPLT_CRITERIACO> getFilterCritList(GLSTMPLT_CRITERIASC sc) throws BaseException;
	public void insertFilterCriteria(GLSTMPLT_CRITERIACO glstmpltCritCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
	public void deleteFilterCriteria(GLSTMPLT_CRITERIACO glstmpltCritCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
	public GLSTMPLT_CRITERIAVO getFilterCritById(GLSTMPLT_CRITERIAVO glstmpltCritVO) throws BaseException;
	public void updateFilterCriteria(GLSTMPLT_CRITERIACO glstmpltCritCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
	public int findCriteriaUsageCount(GLSTMPLT_CRITERIAVO glstmpltCritVO) throws BaseException;
	public int findCriteriaCount(GLSTMPLT_CRITERIAVO glstmpltCritVO) throws BaseException;
	public GLSTMPLT_CRITERIAVO retrieveFitlCrt(GLSTMPLT_CRITERIAVO glstmpltCritVO) throws BaseException;
}
