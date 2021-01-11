package com.path.dao.reporting.designer.impl;

import java.math.BigDecimal;
import java.util.List;

import com.path.dao.reporting.designer.HyperlinksDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.lib.common.util.NumberUtil;
import com.path.vo.reporting.DesignerGridSC;
import com.path.vo.reporting.IRP_REP_HYPERLINKSCO;

public class HyperlinksDAOImpl extends BaseDAO implements HyperlinksDAO
{

	public int retHyperlinksCnt(DesignerGridSC sc) throws DAOException 
	{
		DAOHelper.fixGridMaps(sc, getSqlMap(), "hyperlinks.retHyperlinksListMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("hyperlinks.retHyperlinksCnt", sc)).intValue();
		return nb;
	}

	public List<IRP_REP_HYPERLINKSCO> retHyperlinksList(DesignerGridSC sc)throws DAOException 
	{
		DAOHelper.fixGridMaps(sc, getSqlMap(), "hyperlinks.retHyperlinksListMap");
		if(sc.getSortOrder()==null)
		{
			sc.setSortOrder("   ORDER BY REPORT_ID ");
		}
		if(NumberUtil.isEmptyDecimal(sc.getRep_Id()))
		{
		    return  getSqlMap().queryForList("hyperlinks.retHyperlinksList", sc,sc.getRecToskip(),sc.getNbRec());
		}
		else
		{
		    return  getSqlMap().queryForList("hyperlinks.retHyperlinksList",sc);  
		}
	}

	@Override
	public Integer deleteHyperlink(IRP_REP_HYPERLINKSCO hypCO) throws DAOException 
	{
		return getSqlMap().delete("hyperlinks.deleteHyperlink", hypCO);
	}

	public int retHyperlinkParamsCnt(DesignerGridSC sc) throws DAOException 
	{
		DAOHelper.fixGridMaps(sc, getSqlMap(), "hyperlinks.retHyperlinkParamsMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("hyperlinks.retHyperlinkParamsCnt", sc)).intValue();
		return nb;
	}

	public List<IRP_REP_HYPERLINKSCO> retHyperlinkParamsList(DesignerGridSC sc)throws DAOException 
	{
		DAOHelper.fixGridMaps(sc, getSqlMap(), "hyperlinks.retHyperlinkParamsMap");
		if(sc.getSortOrder()==null)
		{
			sc.setSortOrder("   ORDER BY ARGUMENT_ID ");
		}
		return  getSqlMap().queryForList("hyperlinks.retHyperlinkParamsList", sc,sc.getRecToskip(),sc.getNbRec());

	}

	public List<IRP_REP_HYPERLINKSCO> retHyperlinksParams(DesignerGridSC sc)throws DAOException 
	{
		return  getSqlMap().queryForList("hyperlinks.retHyperlinksParams", sc);
	}

	public List<BigDecimal> retHypReportUsage(List<BigDecimal> reprotsId)throws DAOException 
	{
		return  getSqlMap().queryForList("hyperlinks.retHypReportUsage", reprotsId);
	}

	public int retIfRepHasHyp(BigDecimal reportId) throws DAOException 
	{
		return ((Integer)getSqlMap().queryForObject("hyperlinks.retIfRepHasHyp", reportId)).intValue();
	}

}
