package com.path.dao.reporting.ftr.filterCriteria.impl;

import java.util.ArrayList;
import java.util.List;

import com.path.dao.reporting.ftr.filterCriteria.FilterCriteriaDAO;
import com.path.dbmaps.vo.GLSTMPLT_CRITERIAVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIACO;
import com.path.vo.reporting.ftr.filterCriteria.GLSTMPLT_CRITERIASC;

public class FilterCriteriaDAOImpl extends BaseDAO implements FilterCriteriaDAO {

	public int getFilterCritCount(GLSTMPLT_CRITERIASC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "filterCriteria.filterCriteriaMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("filterCriteria.filterCriteriaCnt", sc))
				.intValue();
		return nb;
	}

	public List<GLSTMPLT_CRITERIACO> getFilterCritList(GLSTMPLT_CRITERIASC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "filterCriteria.filterCriteriaMap");
		return (ArrayList<GLSTMPLT_CRITERIACO>) getSqlMap().queryForList("filterCriteria.filterCriteriaList", sc,sc.getRecToskip(), sc.getNbRec());
	}
	
	public void deleteFilterCriteria(GLSTMPLT_CRITERIAVO vo) throws DAOException {
		getSqlMap().delete("filterCriteria.delete", vo);
	}
	public int findCriteriaUsageCount(GLSTMPLT_CRITERIAVO vo) throws DAOException
	{
		Integer val = (Integer)getSqlMap().queryForObject("filterCriteria.findCriteriaUsageCount", vo);
		return val.intValue();
	}
	public int findCriteriaCount(GLSTMPLT_CRITERIAVO vo) throws DAOException
	{
		Integer val = (Integer)getSqlMap().queryForObject("filterCriteria.findCriteriaCount", vo);
		return val.intValue();
	}


	
}
