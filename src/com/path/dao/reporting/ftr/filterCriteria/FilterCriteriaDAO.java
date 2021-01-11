package com.path.dao.reporting.ftr.filterCriteria;

import java.util.List;

import com.path.dbmaps.vo.GLSTMPLT_CRITERIAVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIACO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIASC;

public interface FilterCriteriaDAO {
	public int getFilterCritCount(GLSTMPLT_CRITERIASC sc) throws DAOException;
	public List<GLSTMPLT_CRITERIACO> getFilterCritList(GLSTMPLT_CRITERIASC sc) throws DAOException;
	public void deleteFilterCriteria(GLSTMPLT_CRITERIAVO vo) throws DAOException;
	public int findCriteriaUsageCount(GLSTMPLT_CRITERIAVO vo) throws DAOException;
	public int findCriteriaCount(GLSTMPLT_CRITERIAVO vo) throws DAOException;
}
