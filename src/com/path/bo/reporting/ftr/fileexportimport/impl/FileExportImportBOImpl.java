package com.path.bo.reporting.ftr.fileexportimport.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.fileexportimport.FileExportImportBO;
import com.path.dao.reporting.ftr.fileexportimport.FileExportImportDAO;
import com.path.dbmaps.vo.IRP_FILE_DETVO;
import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINCO;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_MAINSC;

/**
 * 
 * Copyright 2013, Path Solutions Path Solutions retains all ownership rights to
 * this source code
 * 
 * FileExportImportBOImpl.java used to
 */
public class FileExportImportBOImpl extends BaseBO implements FileExportImportBO
{
    FileExportImportDAO fileExportImportDAO;

    public FileExportImportDAO getFileExportImportDAO()
    {
	return fileExportImportDAO;
    }

    public void setFileExportImportDAO(FileExportImportDAO fileExportImportDAO)
    {
	this.fileExportImportDAO = fileExportImportDAO;
    }

    public List<IRP_FILE_MAINCO> getFileMainList(IRP_FILE_MAINSC sc) throws BaseException
    {
	return fileExportImportDAO.getFileMainList(sc);
    }

    public int getFileMainCount(IRP_FILE_MAINSC sc) throws BaseException
    {
	return fileExportImportDAO.getFileMainCount(sc);
    }

    /**
     * Called to retrieve the main files record
     * 
     * @param irpFileMainSC
     * @return void
     * @throws BaseException
     */
    public IRP_FILE_MAINVO retrieveMainFiles(IRP_FILE_MAINSC irpFileMainSC) throws BaseException
    {
	return fileExportImportDAO.retrieveMainFiles(irpFileMainSC);
    }

    /**
     * Called to delete a main file record
     * 
     * @param irpFileMainVO the record to delete
     * @return void
     * @throws BaseException
     */
    public void deleteMainFile(IRP_FILE_MAINVO irpFileMainVO) throws BaseException
    {
	String fileName = irpFileMainVO.getFILE_NAME();
	int extIndex = fileName.lastIndexOf(".");
	String folderName = fileName.substring(0, extIndex);
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String repository = repositoryPath + "/" + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + "/" + folderName;
	try
	{
	    FileUtil.deleteFolder(repository);
	}
	catch(Exception e)
	{
	    throw new BaseException(e);
	}
	fileExportImportDAO.deleteMainFile(irpFileMainVO);
	auditBO.callAudit( irpFileMainVO, irpFileMainVO, irpFileMainVO.getAuditRefCO());
	auditBO.insertAudit(irpFileMainVO.getAuditRefCO());
    }

    public int getDetailFilesCount(IRP_FILE_DETSC sc) throws BaseException
    {
	return fileExportImportDAO.getDetailFilesCount(sc);
    }

    public List<IRP_FILE_DETCO> retrieveDetailFiles(IRP_FILE_DETSC irpFileDetSC) throws BaseException
    {
	return fileExportImportDAO.retrieveDetailFiles(irpFileDetSC);
    }

    public int retRepRefListCount(IRP_FILE_DETSC sc) throws BaseException
    {
	return fileExportImportDAO.retRepRefListCount(sc);
    }

    /**
     * Called to return the list of reports prog ref for the detail file
     * reference lookup
     * 
     * @param irpFileDetSC
     * @return void
     * @throws BaseException
     */
    public List<IRP_FILE_DETCO> retRepRefList(IRP_FILE_DETSC irpFileDetSC) throws BaseException
    {
	return fileExportImportDAO.retRepRefList(irpFileDetSC);
    }

    /**
     * Called to insert detail files records
     * 
     * @param lstAdd List of records added new, fileID
     * @return void
     * @throws BaseException
     */

    public void insertDetailFiles(ArrayList lstAdd, BigDecimal fileId,IRP_FILE_MAINVO irp_file_mainVO, BigDecimal compCode) throws BaseException
    {

	IRP_FILE_DETCO irp_file_detCO;
	IRP_FILE_DETVO irp_file_detVO;
	for(int i = 0; i < lstAdd.size(); i++)
	{
	    irp_file_detCO = (IRP_FILE_DETCO) lstAdd.get(i);

	    CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	    checkRequiredCO.setObj_value(lstAdd);
	    checkRequiredCO.setOpt("FEI00MT");
	    checkRequiredCO.setApp("REP");
	    checkRequiredCO.setCompCode(compCode);

	    // check for required fields
	    commonLibBO.checkRequired(checkRequiredCO);

	    irp_file_detVO = irp_file_detCO.getIrp_file_detVO();
	    irp_file_detVO.setFILE_ID(fileId);
	    irp_file_detVO.setAPP_NAME("REP");
	    irp_file_detVO.setPROG_REF(irp_file_detCO.getPROG_REF());
	    fileExportImportDAO.insertDetailFiles(irp_file_detVO);

	    // apply audit
	     auditBO.callAudit(null, irp_file_detCO.getIrp_file_detVO(),
	     irp_file_mainVO.getAuditRefCO());
	}
	auditBO.insertAudit(irp_file_mainVO.getAuditRefCO());

    }

    /**
     * Called to insert a main file record
     * 
     * @param vo the records to add
     * @return void
     * @throws BaseException
     */

    public void insertFileMain(IRP_FILE_MAINVO vo) throws BaseException
    {
	fileExportImportDAO.insertFileMain(vo);
	auditBO.callAudit(null, vo, vo.getAuditRefCO());
    }

    /**
     * Called to delete details files records
     * 
     * @param lstDel List of records deleted, fileID
     * @return void
     * @throws BaseException
     */

    public void deleteDetailFiles(ArrayList lstDel, BigDecimal fileId,IRP_FILE_MAINVO irpFileMainVO) throws BaseException
    {
	IRP_FILE_DETCO irp_file_detCO;
	IRP_FILE_DETVO irp_file_detVO;

	for(int i = 0; i < lstDel.size(); i++)
	{
	    irp_file_detCO = (IRP_FILE_DETCO) lstDel.get(i);
	    irp_file_detVO = irp_file_detCO.getIrp_file_detVO();
	    irp_file_detVO.setFILE_ID(fileId);
	    fileExportImportDAO.deleteDetailFiles(irp_file_detVO);
	    auditBO.callAudit( irp_file_detVO, irp_file_detVO, irpFileMainVO.getAuditRefCO());
		
	}
	auditBO.insertAudit(irpFileMainVO.getAuditRefCO());
	    
 
    }

    /**
     * Called to update details files records
     * 
     * @param lsUPd List of records updated, fileID
     * @return void
     * @throws BaseException
     */

    public void updateDetailFiles(ArrayList lstUpd, BigDecimal fileId,IRP_FILE_MAINVO irp_file_mainVO,HashMap oldAuditRefVOList) throws BaseException
    {
	try
	{
	    IRP_FILE_DETCO irp_file_detCO;
	    IRP_FILE_DETVO irp_file_detVO;
	    StringBuffer  concatKey;
	    IRP_FILE_DETVO fileDetailsOldVO;
	    for(int i = 0; i < lstUpd.size(); i++)
	    {
		irp_file_detCO = (IRP_FILE_DETCO) lstUpd.get(i);
		irp_file_detVO = irp_file_detCO.getIrp_file_detVO();
		irp_file_detVO.setFILE_ID(fileId);
		irp_file_detVO.setPROG_REF(irp_file_detCO.getPROG_REF());
		genericDAO.update(irp_file_detVO);
		//Concatenate the key of the irp_file_detVO in order to use it when comparing with the old object
		//from the list oldAuditRefCOList
		concatKey=new StringBuffer(irp_file_detVO.getFILE_ID().toString());
		
		concatKey.append('_' ).append( irp_file_detVO.getLINE_NO());
		
		fileDetailsOldVO = (IRP_FILE_DETVO)oldAuditRefVOList.get(concatKey.toString());
		
		//Apply Audit
		auditBO.callAudit( fileDetailsOldVO, irp_file_detVO, irp_file_mainVO.getAuditRefCO());
		
	    }
	    auditBO.insertAudit(irp_file_mainVO.getAuditRefCO());

	}
	catch(Exception e)
	{
	    throw new BaseException(e);
	}
    }

    public void updateMainFile(IRP_FILE_MAINVO vo, IRP_FILE_MAINVO oldVo) throws BaseException
    {
	genericDAO.update(vo);
	auditBO.callAudit( oldVo, vo, vo.getAuditRefCO());
	auditBO.insertAudit(vo.getAuditRefCO());
    }

    /**
     * Create the main file folder on the repository
     * 
     * @param folderName
     * @return void
     * @throws BaseException
     */

    public void createMainFolder(String folderName) throws BaseException
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	try {
		boolean isCreatedDirectory = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION)
			.mkdir();
		if(!isCreatedDirectory)
		{
		    log.debug("The directory " + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + " has not been created");
		}

		boolean isCreatedFolder = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + "/"
			+ folderName).mkdir();
		if(!isCreatedFolder)
		{
		    log.debug("The folder " + folderName + " has not been created");
		}
	} catch (Exception e) {
		log.error(e, e.getMessage());
	}
    }

    /**
     * read the sub file bytes from the repository
     * 
     * @param folderName, subFilenName
     * @return byte[]
     * @throws BaseException
     */

    public byte[] readFileBytes(String folderName, String subFileName) throws BaseException
    {
	byte[] fileBytes = null;
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String repository = repositoryPath + "/" + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + "/" + folderName;
	
	    try
	    {
		if (FileUtil.existFile(repository + "/" + subFileName +"."+ConstantsCommon.XLSX_EXT)) {
		fileBytes = FileUtil.readFileBytes(repository + "/" + subFileName +"."+ ConstantsCommon.XLSX_EXT);
		}
		else 
		{
		throw new BOException(MessageCodes.REP_IMPORT_EXPORT_FILE_EXISTS);
		}
	    }
	    catch(Exception e)
	    {
		throw new BaseException(e);
	    }
	    
	return fileBytes;
    }

    /**
     * Write the folder to be exported to the repository and the bytes of the
     * zip folder
     * 
     * @param foldername, filename, filebytes
     * @return byte[]
     * @throws BaseException
     */
    public byte[] writeFileBytes(String folderName, String fileName, byte[] fileBytes) throws BaseException
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String repository = repositoryPath + "/" + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + "/" + folderName;
	byte[] zipBytes = null;
	try
	{
	    FileOutputStream file = new FileOutputStream(repository + "/" + fileName);
	    file.write(fileBytes);
	    zipBytes = FileUtil.generateZipFromFldr(repository);
	    file.close();
	}
	catch(Exception e)
	{
	    throw new BaseException(e);
	}

	return zipBytes;

    }

    /**
     * return the count of files that have as a file name the file name passed
     * as a parameter
     * 
     * @param fileName
     * @return integer
     * @throws BaseException
     */
    public int checkIfFileUploaded(String fileName) throws BaseException
    {
	return fileExportImportDAO.checkIfFileUploaded(fileName);
    }
}
