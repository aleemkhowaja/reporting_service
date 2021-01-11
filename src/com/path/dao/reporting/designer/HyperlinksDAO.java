package com.path.dao.reporting.designer;

import java.math.BigDecimal;
import java.util.List;

import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.DesignerGridSC;
import com.path.vo.reporting.IRP_REP_HYPERLINKSCO;

public interface HyperlinksDAO 
{
	public List<IRP_REP_HYPERLINKSCO> retHyperlinksList(DesignerGridSC sc) throws DAOException;
	public int retHyperlinksCnt(DesignerGridSC sc) throws DAOException;
	public Integer deleteHyperlink(IRP_REP_HYPERLINKSCO  hypCO)throws DAOException;
	public List<IRP_REP_HYPERLINKSCO> retHyperlinkParamsList(DesignerGridSC sc) throws DAOException;
	public int retHyperlinkParamsCnt(DesignerGridSC sc) throws DAOException;
	public List<IRP_REP_HYPERLINKSCO> retHyperlinksParams(DesignerGridSC sc) throws DAOException;
	public List<BigDecimal> retHypReportUsage(List<BigDecimal> reprotsId) throws DAOException;
	public int retIfRepHasHyp(BigDecimal reportId)throws DAOException;
}
