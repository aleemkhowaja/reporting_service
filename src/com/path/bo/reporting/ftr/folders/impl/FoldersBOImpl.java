package com.path.bo.reporting.ftr.folders.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.ftr.folders.FoldersBO;
import com.path.dao.reporting.ftr.folders.FoldersDAO;
import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.AXSVOKey;
import com.path.dbmaps.vo.IRP_FOLDERVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.S_APPVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERCO;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;

public class FoldersBOImpl extends BaseBO implements FoldersBO
{
    private FoldersDAO foldersDAO;

    public FoldersDAO getFoldersDAO()
    {
	return foldersDAO;
    }

    public void setFoldersDAO(FoldersDAO foldersDAO)
    {
	this.foldersDAO = foldersDAO;
    }

    @Override
    public List<IRP_FOLDERCO> loadFoldersList(IRP_FOLDERSC irpFoldersc) throws BaseException
    {
	log.debug("Inside FoldersBOImpl.loadFoldersList");
	return foldersDAO.loadFoldersList(irpFoldersc);
    }

    @Override
    public int retFoldersListCount(IRP_FOLDERSC irpFoldersc) throws BaseException
    {
	log.debug("Inside FoldersBOImpl.retFoldersListCount");
	return foldersDAO.retFoldersListCount(irpFoldersc);
    }
    
    @Override
    public String checkProgRef(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return foldersDAO.checkProgRef(commonDetailsSC);
    }

    @Override
    public void newFolder(IRP_FOLDERVO iRP_FOLDERVO,AXSVO axsVO ,IRP_FOLDERCO foldersCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException
    {
	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setObj_value(foldersCO);
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);
	commonLibBO.checkRequired(checkRequiredCO); 
	giveAccess(axsVO);
	
	//check if the the is_web_yn==2 in the table s_app then set the categ_id of the opt the same as its parent 
	S_APPVO sAppVO = new S_APPVO();
	sAppVO.setAPP_NAME(foldersCO.getAPP_NAME());
	sAppVO = commonLibBO.returnApplication(sAppVO);
	if(BigDecimal.valueOf(2).equals(sAppVO.getIS_WEB_YN()))
	{
	    OPTVO zVO=new OPTVO();
	    zVO.setAPP_NAME(foldersCO.getAPP_NAME());
	    zVO.setPROG_REF(foldersCO.getPARENT_REF());
	    zVO=(OPTVO) genericDAO.selectByPK(zVO);
	    foldersCO.setCATEG_ID(zVO.getCATEG_ID());
	}

	//Insert New OPT in OPT table
	insertOpt(foldersCO);
		log.debug("Inside FoldersBOImpl.newFolder");
		iRP_FOLDERVO.setFOLDER_REF(iRP_FOLDERVO.getFOLDER_REF().toUpperCase());
		genericDAO.insert(iRP_FOLDERVO);
	 

		
		//apply Audit
	    AuditRefCO refCO = iRP_FOLDERVO.getAuditRefCO();	    
	    auditBO.callAudit(null, iRP_FOLDERVO, refCO);
    }
    @Override
    public void insertOpt(IRP_FOLDERCO iRPFOLDERCO) throws BaseException
    {
	log.debug("Inside FoldersBOImpl.insertOpt");
	foldersDAO.insertOpt(iRPFOLDERCO);
    }
    @Override
    public void giveAccess(AXSVO axsVO) throws BaseException
    {
	log.debug("Inside FoldersBOImpl.giveAccess");
	axsVO.setPROG_REF(axsVO.getPROG_REF().toUpperCase());
	genericDAO.insert(axsVO);
    }

    @Override
    public void updateFolder(IRP_FOLDERVO iRPFOLDERVO,IRP_FOLDERCO foldersCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException
    {
	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setObj_value(foldersCO);
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);

	commonLibBO.checkRequired(checkRequiredCO); 
	
		 Integer row=foldersDAO.updateFolder(iRPFOLDERVO);
		 if (row == null || row < 1)
		 {
		     throw new BOException(MessageCodes.RECORD_CHANGED);
		 }
	
		//Update OPT in OPT Table
		BigDecimal progOrder = retProgOrder(foldersCO);
		foldersCO.setPROG_ORDER(progOrder);

		//check if the the is_web_yn==2 in the table s_app then set the categ_id of the opt the same as its parent 
		S_APPVO sAppVO = new S_APPVO();
		sAppVO.setAPP_NAME(foldersCO.getAPP_NAME());
		sAppVO = commonLibBO.returnApplication(sAppVO);
		if(BigDecimal.valueOf(2).equals(sAppVO.getIS_WEB_YN()))
		{
		    OPTVO zVO=new OPTVO();
		    zVO.setAPP_NAME(foldersCO.getAPP_NAME());
		    zVO.setPROG_REF(foldersCO.getPARENT_REF());
		    zVO=(OPTVO) genericDAO.selectByPK(zVO);
		    foldersCO.setCATEG_ID(zVO.getCATEG_ID());
		}
		
		updateOpt(foldersCO);
		
		//apply audit
		auditBO.callAudit( iRPFOLDERVO.getAuditObj(), iRPFOLDERVO , iRPFOLDERVO.getAuditRefCO());
		auditBO.insertAudit(iRPFOLDERVO.getAuditRefCO());
    }
    @Override
    public void updateOpt(IRP_FOLDERCO iRPFOLDERCO) throws BaseException
    {
	foldersDAO.updateOpt(iRPFOLDERCO);
    }

    @Override
    public void deleteFolder(IRP_FOLDERVO iRPFOLDERVO,AXSVOKey axsVO ,IRP_FOLDERCO foldersCO) throws BaseException
    {
	   genericDAO.delete(iRPFOLDERVO);
		
	   //Delete Access
	    deleteAccess(axsVO);
	    //Delete OPT
	    deleteOpt(foldersCO);
	    
		//apply audit
	    auditBO.callAudit( iRPFOLDERVO, iRPFOLDERVO, iRPFOLDERVO.getAuditRefCO());
	    auditBO.insertAudit(iRPFOLDERVO.getAuditRefCO());
    }

    @Override
    public void deleteOpt(IRP_FOLDERCO iRPFOLDERCO) throws BaseException
    {
	foldersDAO.deleteOpt(iRPFOLDERCO);
    }

    @Override
    public void deleteAccess(AXSVOKey axsVO) throws BaseException
    {
	genericDAO.delete(axsVO);
    }

    @Override
    public BigDecimal retProgOrder(IRP_FOLDERCO iRPFOLDERCO) throws BaseException
    {
	return foldersDAO.retProgOrder(iRPFOLDERCO);
    }

    @Override
    public String checkDetails(IRP_FOLDERCO iRPFOLDERCO) throws BaseException
    {
	return foldersDAO.checkDetails(iRPFOLDERCO);
    }

    public IRP_FOLDERCO retrieveFolder(IRP_FOLDERVO folderVO) throws BaseException 
    {
	return foldersDAO.retrieveFolder(folderVO);
    }
	
    public List<Object> retAllFoldersLst(IRP_FOLDERVO folderVO)throws BaseException 
    {
	List<Object> folderLstVO = new ArrayList<Object>();
	 return foldersDAO.retAllFoldersLst(folderVO,folderLstVO);
    }
}
