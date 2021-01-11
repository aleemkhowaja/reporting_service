package com.path.dao.reporting.ftr.templateHeader.impl;

import java.util.ArrayList;
import java.util.List;

import com.path.dao.reporting.ftr.templateHeader.TemplateHeaderDAO;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERCO;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERSC;

public class TemplateHeaderDAOImpl extends BaseDAO implements TemplateHeaderDAO
{

    @Override
    public int getTemplateHeaderCount(GLSHEADERSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "templateHeader.templateHeaderMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("templateHeader.templateHeaderCount", sc)).intValue();
	return nb;
    }

    @Override
    public List<GLSHEADERCO> getTemplateHeaderList(GLSHEADERSC sc) throws DAOException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "templateHeader.templateHeaderMap");
	return (ArrayList<GLSHEADERCO>) getSqlMap().queryForList("templateHeader.templateHeaderList", sc,sc.getRecToskip(), sc.getNbRec());
    }
    
    public ArrayList<String> retTextFieldsAutoComplete(GLSHEADERSC glsheaderSC)throws DAOException
    {
	return (ArrayList<String>) getSqlMap().queryForList("templateHeader.getTextFieldsAutoComplete",glsheaderSC);
    }
    
    public int checkIdAvailability(GLSHEADERVO glsheaderVO) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("templateHeader.checkIdAvailability", glsheaderVO)).intValue();
    }
    
    public GLSHEADERVO retrieveTemplateHeader(GLSHEADERSC glsheaderSC)throws DAOException
    {
	return ((GLSHEADERCO) getSqlMap().queryForObject("templateHeader.retrieveTemplateHeader",glsheaderSC)).getGlsheaderVO();
    }

}
