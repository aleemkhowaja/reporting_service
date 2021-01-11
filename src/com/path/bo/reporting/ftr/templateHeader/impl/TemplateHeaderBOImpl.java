package com.path.bo.reporting.ftr.templateHeader.impl;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.common.CashedConstantsCommonRep;
import com.path.bo.reporting.ftr.templateHeader.TemplateHeaderBO;
import com.path.dao.reporting.ftr.templateHeader.TemplateHeaderDAO;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERCO;
import com.path.vo.reporting.ftr.templateHeader.GLSHEADERSC;

public class TemplateHeaderBOImpl extends BaseBO implements TemplateHeaderBO
{
    private TemplateHeaderDAO templateHeaderDAO;

    public void setTemplateHeaderDAO(TemplateHeaderDAO templateHeaderDAO)
    {
        this.templateHeaderDAO = templateHeaderDAO;
    }

    @Override
    public int getTemplateHeaderCount(GLSHEADERSC sc) throws BaseException
    {
	return templateHeaderDAO.getTemplateHeaderCount(sc);
    }

    @Override
    public List<GLSHEADERCO> getTemplateHeaderList(GLSHEADERSC sc) throws BaseException
    {
	return templateHeaderDAO.getTemplateHeaderList(sc);
    }
    
    public void insertTemplateHeader(GLSHEADERCO glsheaderCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException 
	{
		CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
		checkRequiredCO.setObj_value(glsheaderCO);
		checkRequiredCO.setOpt(pageRef);
		checkRequiredCO.setCompCode(compCode);
		checkRequiredCO.setLanguage(lang);
		checkRequiredCO.setApp(appName);
		commonLibBO.checkRequired(checkRequiredCO);
		genericDAO.insert(glsheaderCO.getGlsheaderVO());
		auditBO.callAudit(null, glsheaderCO.getGlsheaderVO()	, glsheaderCO.getGlsheaderVO().getAuditRefCO());
	}

	public void deleteTemplateHeader(GLSHEADERCO glsheaderCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException {
		genericDAO.delete(glsheaderCO.getGlsheaderVO());
		CashedConstantsCommonRep.tempHeaderHm.clear();
		auditBO.callAudit( glsheaderCO.getGlsheaderVO(), glsheaderCO.getGlsheaderVO(), glsheaderCO.getGlsheaderVO().getAuditRefCO());
		auditBO.insertAudit(glsheaderCO.getGlsheaderVO().getAuditRefCO());
	}
	
	public void updateTemplateHeader(GLSHEADERCO glsheaderCO,String pageRef,BigDecimal compCode,String appName,String lang)	throws BaseException {
	    
	    	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
		checkRequiredCO.setObj_value(glsheaderCO);
		checkRequiredCO.setOpt(pageRef);
		checkRequiredCO.setCompCode(compCode);
		checkRequiredCO.setLanguage(lang);
		checkRequiredCO.setApp(appName);

		commonLibBO.checkRequired(checkRequiredCO);
		CashedConstantsCommonRep.tempHeaderHm.clear();
		Integer row = genericDAO.update(glsheaderCO.getGlsheaderVO());
		 if (row == null || row < 1)
		 {
		     throw new BOException(MessageCodes.RECORD_CHANGED);
		 }
		AuditRefCO refCO=glsheaderCO.getGlsheaderVO().getAuditRefCO();
		auditBO.callAudit( refCO.getAuditObj(), glsheaderCO.getGlsheaderVO() ,refCO);
		auditBO.insertAudit(glsheaderCO.getGlsheaderVO().getAuditRefCO());
		
	}
	
	    public ArrayList<String> retTextFieldsAutoComplete(GLSHEADERSC glsheaderSC) throws BaseException
	    {
		return templateHeaderDAO.retTextFieldsAutoComplete(glsheaderSC);
	    }
	    
	    public int checkIdAvailability(GLSHEADERVO glsheaderVO) throws BaseException
	    {
		return templateHeaderDAO.checkIdAvailability(glsheaderVO);
	    }
	    
	    public GLSHEADERVO retrieveTemplateHeader(GLSHEADERSC glsheaderSC)throws BaseException
	    {
		return templateHeaderDAO.retrieveTemplateHeader(glsheaderSC);
	    }

}
