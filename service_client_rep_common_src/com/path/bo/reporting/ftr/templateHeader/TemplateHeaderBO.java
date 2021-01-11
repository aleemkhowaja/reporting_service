package com.path.bo.reporting.ftr.templateHeader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERCO;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERSC;
public interface TemplateHeaderBO
{
    public int getTemplateHeaderCount(GLSHEADERSC sc) throws BaseException;
    public List<GLSHEADERCO> getTemplateHeaderList(GLSHEADERSC sc) throws BaseException;
    public void insertTemplateHeader(GLSHEADERCO glsheaderCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
    public void deleteTemplateHeader(GLSHEADERCO glsheaderCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
    public void updateTemplateHeader(GLSHEADERCO glsheaderCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
    public ArrayList<String> retTextFieldsAutoComplete(GLSHEADERSC glsheaderSC)throws BaseException;
    public int checkIdAvailability(GLSHEADERVO glsheaderVO) throws BaseException;
    public GLSHEADERVO retrieveTemplateHeader(GLSHEADERSC glsheaderSC)throws BaseException;
}
