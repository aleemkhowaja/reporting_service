package com.path.dao.reporting.ftr.columntemplate;

import java.util.List;

import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNSVOKey;
import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNS_DETVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.columnTemplate.IRP_TEMPLATE_COLUMNS_DETCO;
import com.path.vo.reporting.ftr.columnTemplate.IRP_TEMPLATE_COLUMNS_DETSC;

public interface ColumnTemplateDetDAO {
	public List<IRP_TEMPLATE_COLUMNS_DETCO> findColumnTemplateDetails(IRP_TEMPLATE_COLUMNS_DETSC sc) throws DAOException;
	public IRP_TEMPLATE_COLUMNS_DETCO findSingleColumnTemplateDet(IRP_TEMPLATE_COLUMNS_DETSC sc) throws DAOException;
	public void insert(IRP_TEMPLATE_COLUMNS_DETVO vo) throws DAOException;
	public void update(IRP_TEMPLATE_COLUMNS_DETVO vo) throws DAOException;
	public void deleteLines(IRP_TEMPLATE_COLUMNSVOKey key) throws DAOException;
	public Integer findColumnTemplateDetailsCount(IRP_TEMPLATE_COLUMNS_DETSC sc) throws DAOException;
}
