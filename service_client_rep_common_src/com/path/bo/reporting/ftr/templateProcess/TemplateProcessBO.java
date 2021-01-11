package com.path.bo.reporting.ftr.templateProcess;

import java.util.HashMap;
import java.util.List;

import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.templateProcess.TEMPLATE_PROCESSSC;

public interface TemplateProcessBO
{
    public List<CommonDetailsVO> retTemplatesList(CommonDetailsSC commonDetailsSC) throws BaseException;

    public int retTemplatesListCount(CommonDetailsSC commonDetailsSC) throws BaseException;

    public List<CommonDetailsVO> retColTemplatesList(CommonDetailsSC commonDetailsSC) throws BaseException;

    public int retColTemplatesListCount(CommonDetailsSC commonDetailsSC) throws BaseException;

    public List<GLSTMPLTCO> runTemplProcess(TEMPLATE_PROCESSSC tmplProcSC, HashMap<String, String> procStatTransMap)
	    throws BaseException;

    /**
     * Method executed on save of column template and on launch of column template process screen
     * @param tmplProcSC
     * @param procStatTransMap
     * @param isFromProcess
     * @return
     * @throws BaseException
     */
    public List<COLMNTMPLTCO> runColTemplProcess(TEMPLATE_PROCESSSC tmplProcSC, HashMap<String, String> procStatTransMap,boolean isFromProcess)
	    throws BaseException;
}