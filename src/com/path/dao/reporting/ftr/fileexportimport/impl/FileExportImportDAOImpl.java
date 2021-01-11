package com.path.dao.reporting.ftr.fileexportimport.impl;

import java.util.List;

import com.path.dao.reporting.ftr.fileexportimport.FileExportImportDAO;
import com.path.dbmaps.vo.IRP_FILE_DETVO;
import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINSC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * FileExportImportDAOImpl.java used to
 */
public class FileExportImportDAOImpl extends BaseDAO implements FileExportImportDAO
{
    public List<IRP_FILE_MAINCO> getFileMainList(IRP_FILE_MAINSC sc) throws BaseException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "fileExportImport.fileMainMap");
	return getSqlMap().queryForList("fileExportImport.fileMainList", sc, sc.getRecToskip(), sc.getNbRec());
    }

    public int getFileMainCount(IRP_FILE_MAINSC sc) throws BaseException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "fileExportImport.fileMainMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("fileExportImport.fileMainCount", sc)).intValue();
	return nb;
    }

    public IRP_FILE_MAINVO retrieveMainFiles(IRP_FILE_MAINSC sc) throws DAOException
    {
	return ((IRP_FILE_MAINCO) getSqlMap().queryForObject("fileExportImport.retrieveMainFiles", sc))
		.getIrp_file_mainVO();
    }

    public void deleteMainFile(IRP_FILE_MAINVO vo) throws DAOException
    {   getSqlMap().delete("fileExportImport.deleteDetailByFile", vo);
	getSqlMap().delete("fileExportImport.deleteMainFile", vo);
    }

    public int getDetailFilesCount(IRP_FILE_DETSC sc) throws BaseException
    {
	DAOHelper.fixGridMaps(sc, getSqlMap(), "fileExportImport.fileDetMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("fileExportImport.fileDetailsCount", sc)).intValue();
	return nb;
    }
    

    public List<IRP_FILE_DETCO> retrieveDetailFiles(IRP_FILE_DETSC irpFileDetSC) throws BaseException
    {
	DAOHelper.fixGridMaps(irpFileDetSC, getSqlMap(), "fileExportImport.fileDetMap");
	return getSqlMap().queryForList("fileExportImport.fileDetailsList", irpFileDetSC, irpFileDetSC.getRecToskip(),
		irpFileDetSC.getNbRec());

    }

    public int retRepRefListCount(IRP_FILE_DETSC sc) throws BaseException
    {DAOHelper.fixGridMaps(sc, getSqlMap(), "fileExportImport.repRefMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("fileExportImport.repRefListCount", sc)).intValue();
	return nb;
	
    }

    public List<IRP_FILE_DETCO> retRepRefList(IRP_FILE_DETSC irpFileDetSC) throws BaseException
    {
	DAOHelper.fixGridMaps(irpFileDetSC, getSqlMap(), "fileExportImport.repRefMap");
	return getSqlMap().queryForList("fileExportImport.repRefList", irpFileDetSC, irpFileDetSC.getRecToskip(),
		irpFileDetSC.getNbRec());
    }
    public void insertFileMain(IRP_FILE_MAINVO vo) throws DAOException {
	getSqlMap().insert("fileExportImport.insertFileMainSql", vo);
}
    public void insertDetailFiles(IRP_FILE_DETVO vo) throws DAOException {
	getSqlMap().insert("fileExportImport.insertFileDetSql", vo);
    }
    public void deleteDetailFiles(IRP_FILE_DETVO vo) throws DAOException {
	getSqlMap().delete("fileExportImport.deleteFileDetails", vo);
    }
    public int checkIfFileUploaded(String fileName) throws DAOException
    {	int nb = 0;
	nb = (Integer) getSqlMap().queryForObject("fileExportImport.checkIfFileUploadedCount", fileName);
	return nb;
    }
	

}
