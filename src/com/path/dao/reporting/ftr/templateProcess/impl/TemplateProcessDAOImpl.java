package com.path.dao.reporting.ftr.templateProcess.impl;

import java.util.HashMap;
import java.util.List;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.ftr.templateProcess.TemplateProcessDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.templateProcess.TEMPLATE_PROCESSSC;

public class TemplateProcessDAOImpl extends BaseDAO implements TemplateProcessDAO
{

    public List<CommonDetailsVO> retTemplatesList(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	if(commonDetailsSC.getSortOrder() == null)
	{
	    commonDetailsSC.setSortOrder(" ORDER BY TBL.CODE  ");
	}
	if(commonDetailsSC.isGrid())
	{
	    DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "templProc.getTemplProcLkupMap");
	    return getSqlMap().queryForList("templProc.retTemplatesList", commonDetailsSC,
		    commonDetailsSC.getRecToskip(), commonDetailsSC.getNbRec());
	}
	else
	{
	    return getSqlMap().queryForList("templProc.retTemplatesList", commonDetailsSC);
	}
    }

    public int retTemplatesListCount(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "templProc.getTemplProcLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templProc.retTemplatesListCount", commonDetailsSC)).intValue();
	return nb;
    }

    public List<CommonDetailsVO> retColTemplatesList(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	if(commonDetailsSC.getSortOrder() == null)
	{
	    commonDetailsSC.setSortOrder("  ORDER BY TBL.CODE ");
	}
	if(commonDetailsSC.isGrid())
	{
	    DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "templProc.getTemplProcLkupMap");
	    return getSqlMap().queryForList("templProc.retColTemplatesList",
		    commonDetailsSC, commonDetailsSC.getRecToskip(), commonDetailsSC.getNbRec());
	}
	else
	{
	    return getSqlMap().queryForList("templProc.retColTemplatesList",
		    commonDetailsSC);
	}
    }

    public int retColTemplatesListCount(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "templProc.getTemplProcLkupMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templProc.retColTemplatesListCount", commonDetailsSC)).intValue();
	return nb;
    }

    public GLSTMPLTCO runTemplProcess(TEMPLATE_PROCESSSC tmplProcSC, GLSTMPLTCO glTmplCO,
	    HashMap<String, String> procStatTransMap) throws DAOException
    {
	try
	{
	    getSqlMap().update("templProc.runTemplProcess", tmplProcSC);
	    int olError = tmplProcSC.getOlError();

	    if(olError == -1)
	    {
		rollBackTransaction();
		glTmplCO.setProcOsMessage(tmplProcSC.getOsMessage());
		glTmplCO.setStatus(procStatTransMap.get("f"));
	    }
	    else
	    {
		commitTransaction();
		glTmplCO.setProcOsMessage("");
		glTmplCO.setStatus(procStatTransMap.get("s"));
	    }
	}
	catch(Exception e)
	{
	    if(procStatTransMap.isEmpty())// called from the template section
	    {
		log.error(e, "Error in runTemplProcess ");
		throw new DAOException("Error in runTemplProcess ",e);
	    }
	    else// called from template process section
	    {
		rollBackTransaction();
		glTmplCO.setProcOsMessage(e.getMessage());
		glTmplCO.setStatus(procStatTransMap.get("f"));
	    }
	}
	return glTmplCO;

    }

    public COLMNTMPLTCO runColTemplProcess(TEMPLATE_PROCESSSC tmplProcSC, COLMNTMPLTCO colmnTmpltCO,
	    HashMap<String, String> procStatTransMap) throws DAOException
    {
	try
	{
	    getSqlMap().update("templProc.runColTemplProcess", tmplProcSC);
	    int olError = tmplProcSC.getOlError();
	    if(olError == -1)
	    {
		rollBackTransaction();
		colmnTmpltCO.setProcOsMessage(tmplProcSC.getOsMessage());
		colmnTmpltCO.setStatus(procStatTransMap.get("f"));
	    }
	    else
	    {
		commitTransaction();
		colmnTmpltCO.setProcOsMessage("");
		colmnTmpltCO.setStatus(procStatTransMap.get("s"));
	    }
	}
	catch(Exception e)
	{
	    if(procStatTransMap.isEmpty())  // called from column template section
	    {
		log.error(e, "Error in runColTemplProcess ");
		throw new DAOException("Error in runColTemplProcess ",e);
	    }
	    else// called from column template
	    // process section
	    {
		rollBackTransaction();
		colmnTmpltCO.setProcOsMessage(e.getMessage());
		colmnTmpltCO.setStatus(procStatTransMap.get("f"));
	    }
	  
	}
	return colmnTmpltCO;
    }

    public List<GLSTMPLTCO> retFromToTemplates(TEMPLATE_PROCESSSC tmplProcSC) throws DAOException
    {
	return getSqlMap().queryForList("templProc.retFromToTemplates", tmplProcSC);
    }

    public List<COLMNTMPLTCO> retFromToColumnTemplates(TEMPLATE_PROCESSSC tmplProcSC) throws DAOException
    {
	return getSqlMap().queryForList("templProc.retFromToColumnTemplates", tmplProcSC);
    }
    
    @Override
    public void updateAllDateFromColTempl(TEMPLATE_PROCESSSC tmplProcSC) throws DAOException
    {
	if(RepConstantsCommon.COL_TEMPLPROC_ASOF.equals(tmplProcSC.getProcType()))
	{
	    getSqlMap().update("templProc.updateAllDateFromColTempl", tmplProcSC);
	}
	else if(RepConstantsCommon.COL_TEMPLPROC_FROMTO.equals(tmplProcSC.getProcType()))
	{
	    getSqlMap().update("templProc.updateAllFromToDateColTempl", tmplProcSC);
	}
	else if(RepConstantsCommon.COL_TEMPLPROC_BOTH.equals(tmplProcSC.getProcType()))
	{
	    getSqlMap().update("templProc.updateAllDateFromColTempl", tmplProcSC); 
	    getSqlMap().update("templProc.updateAllFromToDateColTempl", tmplProcSC);
	}
	else if(RepConstantsCommon.COL_TEMPLPROC_PERIODIC.equals(tmplProcSC.getProcType()))
	{
	    getSqlMap().update("templProc.updatePeriodicFromDateColTempl", tmplProcSC);
	    getSqlMap().update("templProc.updatePeriodicAllDateColTempl", tmplProcSC);

	}
    }   
}
