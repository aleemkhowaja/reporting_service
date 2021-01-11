package com.path.dao.reporting.common;

import java.util.ArrayList;
import java.util.List;

import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.AXSSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;

public interface CommonRepFuncDAO 
{
	public void updateCounter(String tableName) throws DAOException;
	public Integer retCounterVal(String tableName)throws DAOException;
	public List<AXSVO> checkUpdateReportAccess(AXSSC axsSC) throws DAOException;
	public ArrayList<IRP_FILE_MAINVO> retRepositoryFolder(String progRef)throws DAOException;
	public String retRepositoryFileName(IRP_FILE_DETSC sc) throws DAOException;
	/**
	 * Method that returns the snapshot parameter config to use
	 * @param progRef
	 * @return
	 * @throws DAOException
	 */
	public int retMinRepSnapshotRepId(String progRef) throws DAOException;
}
