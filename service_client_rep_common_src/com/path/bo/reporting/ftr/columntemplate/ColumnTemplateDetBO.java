package com.path.bo.reporting.ftr.columntemplate;

import java.util.List;

import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNSVOKey;
import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNS_DETVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.columnTemplate.IRP_TEMPLATE_COLUMNS_DETCO;
import com.path.vo.reporting.ftr.columnTemplate.IRP_TEMPLATE_COLUMNS_DETSC;

public interface ColumnTemplateDetBO {
	public List<IRP_TEMPLATE_COLUMNS_DETCO> findColumnTemplateDetails(
			IRP_TEMPLATE_COLUMNS_DETSC sc) throws BaseException;
	public IRP_TEMPLATE_COLUMNS_DETCO findSingleColumnTemplateDet(IRP_TEMPLATE_COLUMNS_DETSC sc) throws BaseException;
	public void insert(IRP_TEMPLATE_COLUMNS_DETVO vo) throws BaseException;
	public void update(IRP_TEMPLATE_COLUMNS_DETVO vo) throws BaseException;
	public void deleteLines(IRP_TEMPLATE_COLUMNSVOKey key) throws BaseException;
	public Integer findColumnTemplateDetailsCount(IRP_TEMPLATE_COLUMNS_DETSC sc) throws BaseException;
}
