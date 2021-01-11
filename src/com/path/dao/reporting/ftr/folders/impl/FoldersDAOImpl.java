package com.path.dao.reporting.ftr.folders.impl;

import java.math.BigDecimal;
import java.util.List;

import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.ftr.folders.FoldersDAO;
import com.path.dbmaps.vo.IRP_FOLDERVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERCO;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;

public class FoldersDAOImpl extends BaseDAO implements FoldersDAO
{

    @Override
    public List<IRP_FOLDERCO> loadFoldersList(IRP_FOLDERSC irpFoldersc) throws DAOException
    {
	DAOHelper.fixGridMaps(irpFoldersc, getSqlMap(), "folders.loadFoldersListMap");
	if(irpFoldersc.getSortOrder() == null)
	{
	    irpFoldersc.setSortOrder(" ORDER BY FOLDER_CODE ");
	}
	return getSqlMap().queryForList("folders.loadFoldersList", irpFoldersc,
		irpFoldersc.getRecToskip(), irpFoldersc.getNbRec());
    }

    @Override
    public int retFoldersListCount(IRP_FOLDERSC irpFoldersc) throws DAOException
    {
	return ((Integer) (getSqlMap().queryForObject("folders.retFoldersListCount", irpFoldersc))).intValue();
    }

    @Override
    public String checkProgRef(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	String result = (String) getSqlMap().queryForObject("folders.checkProgRef", commonDetailsSC);
	if(result == null || ("").equals(result))
	{
	    return "0";
	}
	else
	{
	    return result;
	}
    }

    @Override
    public void insertOpt(IRP_FOLDERCO iRPFOLDERCO) throws DAOException
    {
	getSqlMap().insert("folders.insertOpt", iRPFOLDERCO);
    }

    @Override
    public void updateOpt(IRP_FOLDERCO iRPFOLDERCO) throws DAOException
    {
	getSqlMap().update("folders.updateOpt", iRPFOLDERCO);
    }

    @Override
    public void deleteOpt(IRP_FOLDERCO iRPFOLDERCO) throws DAOException
    {
	getSqlMap().delete("folders.deleteOpt", iRPFOLDERCO);

    }

    @Override
    public BigDecimal retProgOrder(IRP_FOLDERCO iRPFOLDERCO) throws DAOException
    {
	return (BigDecimal) (getSqlMap().queryForObject("folders.retProgOrder", iRPFOLDERCO));
    }

    @Override
    public String checkDetails(IRP_FOLDERCO iRPFOLDERCO) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("folders.checkDetails", iRPFOLDERCO)).toString();
    }

    public IRP_FOLDERCO retrieveFolder(IRP_FOLDERVO folderVO) throws DAOException
    {
	return (IRP_FOLDERCO) getSqlMap().queryForObject("folders.retrieveFolder", folderVO);
    }

    public Integer updateFolder(IRP_FOLDERVO vo) throws DAOException
    {
	return getSqlMap().update("folders.updateFolder", vo);
    }
    
    public List<Object> retAllFoldersLst(IRP_FOLDERVO folderVO,List<Object> folderLstVO) throws DAOException
    {
	List<IRP_FOLDERVO> tempLst = getSqlMap().queryForList("folders.retAllFoldersLst", folderVO);
	if(!tempLst.isEmpty())
	{
	    folderVO.setFOLDER_REF(tempLst.get(0).getPARENT_REF());
	    folderLstVO.add(tempLst.get(0));
	    if(!RepConstantsCommon.ROOT.equals(tempLst.get(0).getPARENT_REF()))
	    {
		tempLst.clear();
		retAllFoldersLst(folderVO, folderLstVO);
	    }
	}

	return folderLstVO;
    }

}
