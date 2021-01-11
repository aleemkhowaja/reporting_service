package com.path.bo.reporting.ftr.folders;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.AXSVOKey;
import com.path.dbmaps.vo.IRP_FOLDERVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERCO;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;

public interface FoldersBO
{
    public List<IRP_FOLDERCO> loadFoldersList(IRP_FOLDERSC irpFoldersc) throws BaseException;

    public int retFoldersListCount(IRP_FOLDERSC irpFoldersc) throws BaseException;

    public String checkProgRef(CommonDetailsSC commonDetailsSC) throws BaseException;
    
    
    public void newFolder(IRP_FOLDERVO iRP_FOLDERVO,AXSVO axsVO ,IRP_FOLDERCO foldersCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;
    
    public void insertOpt(IRP_FOLDERCO iRP_FOLDERCO) throws BaseException;
    
    public void giveAccess(AXSVO axsVO) throws BaseException;
    

    public void updateFolder(IRP_FOLDERVO iRP_FOLDERVO,IRP_FOLDERCO foldersCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException;

    public void updateOpt(IRP_FOLDERCO iRP_FOLDERCO) throws BaseException;
    
    public BigDecimal retProgOrder(IRP_FOLDERCO iRP_FOLDERCO) throws BaseException;
    
    
    public void deleteFolder(IRP_FOLDERVO iRP_FOLDERVO,AXSVOKey axsVO ,IRP_FOLDERCO foldersCO) throws BaseException;
    
    public void deleteOpt(IRP_FOLDERCO iRP_FOLDERCO) throws BaseException;
    
    public void deleteAccess(AXSVOKey axsVO) throws BaseException;
    
    public String checkDetails(IRP_FOLDERCO iRP_FOLDERCO) throws BaseException;

    public IRP_FOLDERCO retrieveFolder(IRP_FOLDERVO folderVO) throws BaseException;
    
    public List<Object> retAllFoldersLst(IRP_FOLDERVO folderVO) throws BaseException;
}
