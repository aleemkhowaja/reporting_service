package com.path.dao.reporting.ftr.templateHeader;

import java.util.ArrayList;
import java.util.List;

import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERCO;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERSC;

public interface TemplateHeaderDAO
{
    public int getTemplateHeaderCount(GLSHEADERSC sc) throws DAOException;
    public List<GLSHEADERCO> getTemplateHeaderList(GLSHEADERSC sc) throws DAOException;
    public ArrayList<String> retTextFieldsAutoComplete(GLSHEADERSC glsheaderSC) throws DAOException;
    public int checkIdAvailability(GLSHEADERVO glsheaderVO) throws DAOException;
    public GLSHEADERVO retrieveTemplateHeader(GLSHEADERSC glsheaderSC)throws DAOException;
}
