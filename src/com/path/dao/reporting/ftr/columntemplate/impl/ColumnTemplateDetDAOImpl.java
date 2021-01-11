package com.path.dao.reporting.ftr.columntemplate.impl;

import java.util.List;

import com.path.dao.reporting.ftr.columntemplate.ColumnTemplateDetDAO;
import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNSVOKey;
import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNS_DETVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.columnTemplate.IRP_TEMPLATE_COLUMNS_DETCO;
import com.path.vo.reporting.ftr.columnTemplate.IRP_TEMPLATE_COLUMNS_DETSC;


public class ColumnTemplateDetDAOImpl extends BaseDAO implements ColumnTemplateDetDAO
{
	@Override
	public List<IRP_TEMPLATE_COLUMNS_DETCO> findColumnTemplateDetails(
			IRP_TEMPLATE_COLUMNS_DETSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "IRP_TEMPLATE_COL_DET.DetResultMapCO");
		return  getSqlMap().queryForList("IRP_TEMPLATE_COL_DET.select_COLUMNS_DET_template", sc);

	}
	public IRP_TEMPLATE_COLUMNS_DETCO findSingleColumnTemplateDet(IRP_TEMPLATE_COLUMNS_DETSC sc) throws DAOException
	{			
		DAOHelper.fixGridMaps(sc, getSqlMap(), "IRP_TEMPLATE_COL_DET.DetResultMap");
		return (IRP_TEMPLATE_COLUMNS_DETCO) getSqlMap().queryForObject("IRP_TEMPLATE_COL_DET.find_single_COLUMN_DET", sc);
	}
	public void insert(IRP_TEMPLATE_COLUMNS_DETVO vo) throws DAOException
	{
		getSqlMap().insert("IRP_TEMPLATE_COLUMNS_DET.insertIRP_TEMPLATE_COLUMNS_DET", vo);
	}
	public void update(IRP_TEMPLATE_COLUMNS_DETVO vo) throws DAOException
	{
		getSqlMap().update("IRP_TEMPLATE_COLUMNS_DET.updateIRP_TEMPLATE_COLUMNS_DET", vo);
	}
	public void deleteLines(IRP_TEMPLATE_COLUMNSVOKey key) throws DAOException
	{
		getSqlMap().delete("IRP_TEMPLATE_COL_DET.delete_lines", key);
	}
	@Override
	public Integer findColumnTemplateDetailsCount(IRP_TEMPLATE_COLUMNS_DETSC sc)throws DAOException 
	{
		return ((Integer)getSqlMap().queryForObject("IRP_TEMPLATE_COL_DET.get_columnDetailsCount", sc));
	}

}
