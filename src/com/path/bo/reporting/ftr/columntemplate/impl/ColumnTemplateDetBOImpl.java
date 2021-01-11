package com.path.bo.reporting.ftr.columntemplate.impl;

import java.util.List;

import com.path.bo.reporting.ftr.columntemplate.ColumnTemplateDetBO;
import com.path.dao.reporting.ftr.columntemplate.ColumnTemplateDetDAO;
import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNSVOKey;
import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNS_DETVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.columnTemplate.IRP_TEMPLATE_COLUMNS_DETCO;
import com.path.vo.reporting.ftr.columnTemplate.IRP_TEMPLATE_COLUMNS_DETSC;

public class ColumnTemplateDetBOImpl  extends BaseBO implements ColumnTemplateDetBO{
	private ColumnTemplateDetDAO columnTemplateDetDAO;
	public ColumnTemplateDetDAO getColumnTemplateDetDAO() {
		return columnTemplateDetDAO;
	}
	public void setColumnTemplateDetDAO(ColumnTemplateDetDAO columnTemplateDetDAO) {
		this.columnTemplateDetDAO = columnTemplateDetDAO;
	}
	@Override
	public List<IRP_TEMPLATE_COLUMNS_DETCO> findColumnTemplateDetails(IRP_TEMPLATE_COLUMNS_DETSC sc) throws BaseException 
	{
		return columnTemplateDetDAO.findColumnTemplateDetails(sc);
	}
	@Override
	public IRP_TEMPLATE_COLUMNS_DETCO findSingleColumnTemplateDet(
			IRP_TEMPLATE_COLUMNS_DETSC sc) throws BaseException {
		
		return columnTemplateDetDAO.findSingleColumnTemplateDet(sc);
	}
	@Override
	public void deleteLines(IRP_TEMPLATE_COLUMNSVOKey key) throws BaseException
	{
		columnTemplateDetDAO.deleteLines(key);
	}
	public void insert(IRP_TEMPLATE_COLUMNS_DETVO vo) throws BaseException
	{
		columnTemplateDetDAO.insert(vo);
	}
	public void update(IRP_TEMPLATE_COLUMNS_DETVO vo) throws BaseException
	{
		columnTemplateDetDAO.update(vo);
	}
	@Override
	public Integer findColumnTemplateDetailsCount(IRP_TEMPLATE_COLUMNS_DETSC sc)
			throws BaseException {
		return columnTemplateDetDAO.findColumnTemplateDetailsCount(sc);
	}
}
