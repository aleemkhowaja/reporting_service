package com.path.dao.reporting.common.impl;

import java.util.ArrayList;
import java.util.List;

import com.path.dao.reporting.common.CommonRepFuncDAO;
import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.AXSSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;

public class CommonRepFuncDAOImpl extends BaseDAO implements CommonRepFuncDAO
{

	public Integer retCounterVal(String tableName) throws DAOException 
	{
		return (Integer) getSqlMap().queryForObject("commonRepFunc.retCounterVal", tableName); 
	}

	public void updateCounter(String tableName) throws DAOException 
	{
		getSqlMap().update("commonRepFunc.updateCounter", tableName);
	}

	public List<AXSVO> checkUpdateReportAccess(AXSSC axsSC) throws DAOException 
	{
		return getSqlMap().queryForList("commonRepFunc.checkUpdateReportAccess",axsSC);
	}
	

    /**
     * method that returns the folder that should contain the file	
     */
    public ArrayList<IRP_FILE_MAINVO> retRepositoryFolder(String progRef) throws DAOException
    {
	return (ArrayList<IRP_FILE_MAINVO>) getSqlMap().queryForList("commonRepFunc.retRepositoryFolder", progRef);
    }

    /**
     * Method that returns the file name on the repository
     * @param progRef
     * @return
     * @throws DAOException
     */
    public String retRepositoryFileName(IRP_FILE_DETSC sc) throws DAOException
    {
	return (String) getSqlMap().queryForObject("commonRepFunc.retRepositoryFileName", sc);
    }
	
    
    @Override
    public int retMinRepSnapshotRepId(String progRef) throws DAOException
    {
	return ((Integer)getSqlMap().queryForObject("commonRepFunc.retMinRepSnapshotRepId", progRef)).intValue();
    }

}
