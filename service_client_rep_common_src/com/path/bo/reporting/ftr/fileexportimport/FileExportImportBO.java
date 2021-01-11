package com.path.bo.reporting.ftr.fileexportimport;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINSC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * FileExportImportBO.java used to
 */
public interface FileExportImportBO
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
    public IRP_FILE_MAINVO retrieveMainFiles(IRP_FILE_MAINSC irpFileMainSC) throws BaseException;

    /**
     * Called to delete a main file record
     * 
     * @param irpFileMainVO the record to delete
     * @return void
     * @throws BaseException
     */
    public void deleteMainFile(IRP_FILE_MAINVO irpFileMainVO) throws BaseException;

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
    public void insertFileMain(IRP_FILE_MAINVO vo) throws BaseException;

    /**
     * Called to insert detail files records
     * 
     * @param lstAdd List of records added new, fileID
     * @param fileId
     * @param irp_file_mainVO
     * @throws BaseException
     */
    public void insertDetailFiles(ArrayList lstAdd, BigDecimal fileId, IRP_FILE_MAINVO irp_file_mainVO, BigDecimal compCode) throws BaseException;

    /**
     * Called to delete details files records
     * 
     * @param lstDel List of records deleted, fileID
     * @param fileId
     * @param irpFileMainVO
     * @throws BaseException
     */
    public void deleteDetailFiles(ArrayList lstDel, BigDecimal fileId,IRP_FILE_MAINVO irpFileMainVO) throws BaseException;

    /**
     * Called to update details files records
     * 
     * @param lsUPd List of records updated, fileID
     * @return void
     * @throws BaseException
     */
    public void updateDetailFiles(ArrayList lstUpd, BigDecimal fileId,IRP_FILE_MAINVO irp_file_mainVO, HashMap oldAuditRefVOList) throws BaseException;

    /**
     * Called to update the main file record
     * 
     * @param vo the record to update
     * @return void
     * @throws BaseException
     */
    public void updateMainFile(IRP_FILE_MAINVO vo, IRP_FILE_MAINVO oldVo) throws BaseException;

    /**
     * Create the main file folder on the repository
     * 
     * @param folderName
     * @return void
     * @throws BaseException
     */
    public void createMainFolder(String folderName) throws BaseException;

    /**
     * read the sub file bytes from the repository
     * 
     * @param folderName, subFilenName
     * @return byte[]
     * @throws BaseException
     */
    public byte[] readFileBytes(String folderName, String subFileName) throws BaseException;

    /**
     * Write the folder to be exported to the repository and the bytes of the
     * zip folder
     * 
     * @param foldername, filename, filebytes
     * @return byte[]
     * @throws BaseException
     */
    public byte[] writeFileBytes(String folderName, String subFileName, byte[] fileBytes) throws BaseException;

    /**
     * return the count of files that have as a file name the file name passed
     * as a parameter
     * 
     * @param fileName
     * @return integer
     * @throws BaseException
     */
    public int checkIfFileUploaded(String fileName) throws BaseException;

}