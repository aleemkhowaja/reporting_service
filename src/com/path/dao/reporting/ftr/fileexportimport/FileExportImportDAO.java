package com.path.dao.reporting.ftr.fileexportimport;

import java.util.List;

import com.path.dbmaps.vo.IRP_FILE_DETVO;
import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINSC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * FileExportImportDAO.java used to
 */
public interface FileExportImportDAO
{
    /**
     * Retrieve the list of main files for the main grid
     * 
     * @param irpFileMainSC
     * @return List<IRP_FILE_MAINCO>
     * @throws BaseException
     */
    public List<IRP_FILE_MAINCO> getFileMainList(IRP_FILE_MAINSC sc) throws BaseException;

    /**
     * Get the count of the retrieved main files
     * 
     * @param irpFileMainSC
     * @return int
     * @throws BaseException
     */
    public int getFileMainCount(IRP_FILE_MAINSC sc) throws BaseException;
    /**
     * Called to retrieve the main files record
     * 
     * @param irpFileMainSC
     * @return void
     * @throws BaseException
     */
    public IRP_FILE_MAINVO retrieveMainFiles(IRP_FILE_MAINSC sc) throws DAOException;
    /**
     * Called to delete a main file record
     * 
     * @param irpFileMainVO the record to delete
     * @return void
     * @throws BaseException
     */
    public void deleteMainFile(IRP_FILE_MAINVO vo) throws DAOException;
    /**
     * Get the count of the retrieved detail files
     * 
     * @param irpFileDetSC
     * @return int
     * @throws BaseException
     */
    public int getDetailFilesCount(IRP_FILE_DETSC sc) throws BaseException;
    /**
     * Retrieve the list of detail files related to the selected main file in
     * the main grid
     * 
     * @param irpFileDetSC
     * @return List<IRP_FILE_DETCO>
     * @throws BaseException
     */
    public List<IRP_FILE_DETCO> retrieveDetailFiles(IRP_FILE_DETSC irpFileDetSC) throws BaseException;

    /**
     * Called to return the list of reports prog ref for the detail file
     * reference lookup
     * 
     * @param irpFileDetSC
     * @return void
     * @throws BaseException
     */
    public int retRepRefListCount(IRP_FILE_DETSC sc) throws BaseException;

    /**
     * Called to return the list of reports prog ref for the detail file
     * reference lookup
     * 
     * @param irpFileDetSC
     * @return void
     * @throws BaseException
     */
    public List<IRP_FILE_DETCO> retRepRefList(IRP_FILE_DETSC irpFileDetSC) throws BaseException;

    /**
     * Called to insert a main file record
     * 
     * @param vo the records to add
     * @return void
     * @throws BaseException
     */
    public void insertFileMain(IRP_FILE_MAINVO vo) throws DAOException;

    /**
     * Called to insert detail files records
     * 
     * @param lstAdd List of records added new, fileID
     * @return void
     * @throws BaseException
     */
    public void insertDetailFiles(IRP_FILE_DETVO vo) throws DAOException;
    /**
     * Called to delete details files records
     * 
     * @param lstDel List of records deleted, fileID
     * @return void
     * @throws BaseException
     */
    public void deleteDetailFiles(IRP_FILE_DETVO vo) throws DAOException;
    /**
     * return the count of files that have as a file name the file name passed
     * as a parameter
     * 
     * @param fileName
     * @return integer
     * @throws BaseException
     */
    public int checkIfFileUploaded(String fileName) throws DAOException;
}