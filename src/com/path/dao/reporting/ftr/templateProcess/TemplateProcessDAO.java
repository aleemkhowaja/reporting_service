package com.path.dao.reporting.ftr.templateProcess;

import java.util.HashMap;
import java.util.List;

import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.ftr.templateProcess.TEMPLATE_PROCESSSC;

public interface TemplateProcessDAO
{
    public List<CommonDetailsVO> retTemplatesList(CommonDetailsSC commonDetailsSC) throws DAOException;

    public int retTemplatesListCount(CommonDetailsSC commonDetailsSC) throws DAOException;

    public List<CommonDetailsVO> retColTemplatesList(CommonDetailsSC commonDetailsSC) throws DAOException;

    public int retColTemplatesListCount(CommonDetailsSC commonDetailsSC) throws DAOException;

    public GLSTMPLTCO runTemplProcess(TEMPLATE_PROCESSSC tmplProcSC, GLSTMPLTCO glTmplCO,
	    HashMap<String, String> procStatTransMap) throws DAOException;

    public COLMNTMPLTCO runColTemplProcess(TEMPLATE_PROCESSSC tmplProcSC, COLMNTMPLTCO colmnTmpltCO,
	    HashMap<String, String> procStatTransMapSC) throws DAOException;

    public List<GLSTMPLTCO> retFromToTemplates(TEMPLATE_PROCESSSC tmplProcSC) throws DAOException;

    public List<COLMNTMPLTCO> retFromToColumnTemplates(TEMPLATE_PROCESSSC tmplProcSC) throws DAOException;
    /**
     * Updates dates fields in colmntmplt table
     * @param tmplProcSC
     * @throws DAOException
     */
    public void updateAllDateFromColTempl(TEMPLATE_PROCESSSC tmplProcSC) throws DAOException;
}
