package com.path.bo.reporting.designer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.DesignerGridSC;
import com.path.vo.reporting.IRP_REP_HYPERLINKSCO;

public interface HyperlinksBO 
{
	public List<IRP_REP_HYPERLINKSCO> retHyperlinksList(DesignerGridSC sc) throws BaseException;
	public int retHyperlinksCnt(DesignerGridSC sc) throws BaseException;
	public List<IRP_REP_HYPERLINKSCO> retHyperlinkParamsList(DesignerGridSC sc) throws BaseException;
	public int retHyperlinkParamsCnt(DesignerGridSC sc) throws BaseException;
	public Integer deleteHyperlink(IRP_REP_HYPERLINKSCO  hypCO)throws BaseException;
	public void saveHyperlinks(HashMap<String,List<IRP_REP_HYPERLINKSCO>> hypParamsMap,ArrayList<IRP_REP_HYPERLINKSCO> lstAdd,ArrayList<IRP_REP_HYPERLINKSCO> lstMod,AuditRefCO refCO,HashMap<String, List<IRP_REP_HYPERLINKSCO>> oldHypParamsMap)throws BaseException;
	public List<IRP_REP_HYPERLINKSCO> retHyperlinksParams(DesignerGridSC sc) throws BaseException;
	public List<BigDecimal> retHypReportUsage(List<BigDecimal> reprotsId) throws BaseException;
	public int retIfRepHasHyp(BigDecimal reportId)throws BaseException;
}
