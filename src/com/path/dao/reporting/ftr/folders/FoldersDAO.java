package com.path.dao.reporting.ftr.folders;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.IRP_FOLDERVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERCO;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;

public interface FoldersDAO
{

    public List<IRP_FOLDERCO> loadFoldersList(IRP_FOLDERSC irpFoldersc) throws DAOException;

    public int retFoldersListCount(IRP_FOLDERSC irpFoldersc) throws DAOException;

    public String checkProgRef(CommonDetailsSC commonDetailsSC) throws DAOException;


    public void insertOpt(IRP_FOLDERCO iRP_FOLDERCO) throws DAOException;
    
    public void updateOpt(IRP_FOLDERCO iRP_FOLDERCO) throws DAOException;
    
    public void deleteOpt(IRP_FOLDERCO iRP_FOLDERCO) throws DAOException;
    
    public BigDecimal retProgOrder(IRP_FOLDERCO iRP_FOLDERCO) throws DAOException;

    public String checkDetails(IRP_FOLDERCO iRPFOLDERCO) throws DAOException;
    public IRP_FOLDERCO retrieveFolder(IRP_FOLDERVO folderVO) throws DAOException;
    public Integer updateFolder(IRP_FOLDERVO vo)throws DAOException;
    
    public List<Object> retAllFoldersLst(IRP_FOLDERVO folderVO,List<Object> folderLstVO) throws DAOException;
}
