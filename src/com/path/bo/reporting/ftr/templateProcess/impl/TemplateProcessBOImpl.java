package com.path.bo.reporting.ftr.templateProcess.impl;

import java.util.HashMap;
import java.util.List;

import com.path.bo.reporting.ftr.templateProcess.TemplateProcessBO;
import com.path.dao.reporting.ftr.templateProcess.TemplateProcessDAO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.templateProcess.TEMPLATE_PROCESSSC;

public class TemplateProcessBOImpl extends BaseBO implements TemplateProcessBO
{
    private TemplateProcessDAO templateProcDAO;

    public TemplateProcessDAO getTemplateProcDAO()
    {
	return templateProcDAO;
    }

    public void setTemplateProcDAO(TemplateProcessDAO templateProcDAO)
    {
	this.templateProcDAO = templateProcDAO;
    }

    public List<CommonDetailsVO> retTemplatesList(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return templateProcDAO.retTemplatesList(commonDetailsSC);
    }

    public int retTemplatesListCount(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return templateProcDAO.retTemplatesListCount(commonDetailsSC);
    }

    public List<CommonDetailsVO> retColTemplatesList(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return templateProcDAO.retColTemplatesList(commonDetailsSC);
    }

    public int retColTemplatesListCount(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return templateProcDAO.retColTemplatesListCount(commonDetailsSC);
    }

    public List<GLSTMPLTCO> runTemplProcess(TEMPLATE_PROCESSSC tmplProcSC, HashMap<String, String> procStatTransMap)
	    throws BaseException
    {
	List<GLSTMPLTCO> allTempl = templateProcDAO.retFromToTemplates(tmplProcSC);// new
	// list
	// to
	// save
	// all
	// the
	// data
	// needed
	GLSTMPLTCO glTmplCO;
	for(int i = 0; i < allTempl.size(); i++)// for loop to pass by all
	// templates and call the
	// procedure P_FTR_FTR_TMP
	{
	    glTmplCO = allTempl.get(i);// Save the data in CO
	    tmplProcSC.setFromTempl(glTmplCO.getGlstmpltVO().getCODE());
	    tmplProcSC.setToTempl(glTmplCO.getGlstmpltVO().getCODE());
	    glTmplCO = templateProcDAO.runTemplProcess(tmplProcSC, glTmplCO, procStatTransMap);
	}
	return allTempl;
    }

    @Override
    public List<COLMNTMPLTCO> runColTemplProcess(TEMPLATE_PROCESSSC tmplProcSC, HashMap<String, String> procStatTransMap,
	    boolean isFromProcess)
	    throws BaseException
    {
	List<COLMNTMPLTCO> allTempl = templateProcDAO.retFromToColumnTemplates(tmplProcSC);// new
	// list
	// to
	// save
	// all
	// the
	// data
	// needed
	tmplProcSC.setIsSybase(commonLibBO.returnIsSybase());
	if(isFromProcess)
	{
	    templateProcDAO.updateAllDateFromColTempl(tmplProcSC);
	}
	COLMNTMPLTCO colmnTmplCO;
	for(int i = 0; i < allTempl.size(); i++)// for loop to pass by all
	// Column templates and call the
	// procedure P_FTR_FTR_TMP
	{
	    colmnTmplCO = allTempl.get(i);// Save the data in CO
	    tmplProcSC.setFromTempl(colmnTmplCO.getColmnTmpltVO().getCODE());
	    tmplProcSC.setToTempl(colmnTmplCO.getColmnTmpltVO().getCODE());
	    templateProcDAO.runColTemplProcess(tmplProcSC, colmnTmplCO, procStatTransMap);
	}
	return allTempl;
    }
}
