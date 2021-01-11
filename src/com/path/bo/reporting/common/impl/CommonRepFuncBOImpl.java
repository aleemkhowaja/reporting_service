package com.path.bo.reporting.common.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

import com.path.bo.common.CommonLibBO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.ReportBO;
import com.path.bo.reporting.common.util.ReportUtils;
import com.path.bo.reporting.ftr.controlRecord.ControlRecordBO;
import com.path.bo.reporting.ftr.reportsmismatch.ReportsMismatchBO;
import com.path.bo.reporting.ftr.snapshots.SnapshotParameterBO;
import com.path.dao.reporting.common.CommonRepFuncDAO;
import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.IRP_FILE_MAINVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_INFOVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.StringUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.vo.common.AXSSC;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLSC;
import com.path.vo.reporting.ftr.fileExportImport.IRP_FILE_DETSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;

public class CommonRepFuncBOImpl extends BaseBO implements CommonRepFuncBO
{
	private CommonRepFuncDAO commonRepFuncDAO;
	private ReportBO reportBO;
	private CommonLibBO commonLibBO;
	private ControlRecordBO controlRecordBO;
	private ReportsMismatchBO reportsMismatchBO;
	private SnapshotParameterBO snapshotParameterBO;
	
	

	public void setSnapshotParameterBO(SnapshotParameterBO snapshotParameterBO)
	{
	    this.snapshotParameterBO = snapshotParameterBO;
	}

	public void setControlRecordBO(ControlRecordBO controlRecordBO)
	{
	    this.controlRecordBO = controlRecordBO;
	}

	public void setReportsMismatchBO(ReportsMismatchBO reportsMismatchBO)
	{
	    this.reportsMismatchBO = reportsMismatchBO;
	}

	public void setCommonLibBO(CommonLibBO commonLibBO)
	{
	    this.commonLibBO = commonLibBO;
	}

	public void setReportBO(ReportBO reportBO)
	{
	    this.reportBO = reportBO;
	}

	public CommonRepFuncDAO getCommonRepFuncDAO() {
		return commonRepFuncDAO;
	}

	public void setCommonRepFuncDAO(CommonRepFuncDAO commonRepFuncDAO) {
		this.commonRepFuncDAO = commonRepFuncDAO;
	}

	public BigDecimal retCounterValue(String tableName) throws BaseException 
	{
		//update the table reporting_counter
		commonRepFuncDAO.updateCounter(tableName);
		//get the counter value
		Integer id=commonRepFuncDAO.retCounterVal(tableName);
		return (new BigDecimal(id));
	}

	public boolean checkReportAccess(AXSVO axsVO)throws BaseException 
	{
		boolean access=false;
		AXSVO retAxsVO=(AXSVO)genericDAO.selectByPK(axsVO);
		if(retAxsVO!=null)
		{
			access=true;
		}
		return access;
	}

	//on save , we should check if access modify or access save exist then the user will have access to save a report
	public boolean checkUpdateReportAccess(AXSSC axsSC) throws BaseException
	{
		boolean access=false;
		List<AXSVO> axsLst=commonRepFuncDAO.checkUpdateReportAccess(axsSC);
		if(axsLst!=null && !axsLst.isEmpty())
		{
			access=true;
		}
		return access;
	}
	
	 @Override
	    public void writeFileToRepository(String progRef,  byte[] reportBytes,
		    IRP_AD_HOC_REPORTCO reportCO, boolean var_noHeadAndFoot, Map parameters,
		    Connection con,String usrName,String origFormat) throws BaseException
	    {
		try
		{
		    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
		    String folder;
		    IRP_FILE_DETSC sc = new IRP_FILE_DETSC();
		    // get the folders names from irp_file_main,irp_file_det tables
		    ArrayList<IRP_FILE_MAINVO> foldersList = commonRepFuncDAO.retRepositoryFolder(progRef);
		    String folderUrl;
		    for(int i = 0; i < foldersList.size(); i++)
		    {
			folder = foldersList.get(i).getFILE_NAME();
			sc.setFILE_ID(foldersList.get(i).getFILE_ID());
			sc.setPROG_REF(progRef);
			String excelFileName = commonRepFuncDAO.retRepositoryFileName(sc);
			FileUtil.makeDirectories(repositoryPath + "/" + RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + "/"
				+ folder.substring(0, folder.indexOf(".")));
			folderUrl="/" + ReportingConstantsCommon.reportingFolder + "/"
			+ RepConstantsCommon.IMPORT_EXPORT_FILES_LOCATION + "/"+ folder.substring(0, folder.indexOf(".")) + "/";
			String fileUrl = folderUrl + excelFileName + "."
				+ ConstantsCommon.XLSX_EXT;
			FileUtil.saveToRepository(reportBytes, ReportingConstantsCommon.repositoryFolder, fileUrl);
			
			//in case of excel raw data , save the sub reports under the same location of the main excel 
			if(ConstantsCommon.EXCEL_ROW_DATA_REP_FORMAT.equals(origFormat))
			{
			    writeRawDataSubReport(reportCO, var_noHeadAndFoot, parameters, con, folderUrl,usrName);
			}
		    }
		}
		catch(Exception e)
		{
		    log.error(e, "");
		}
	    }
	
    /**
     * Method that write the subReports in raw data format in the same location
     * as the main excel
     * 
     * @param reportCO
     * @param var_noHeadAndFoot
     * @param parameters
     * @param con
     * @param usrName
     * @throws Exception
     */
    private void writeRawDataSubReport(IRP_AD_HOC_REPORTCO reportCO, boolean var_noHeadAndFoot, Map parameters,
	    Connection con, String mainExcelFolder, String usrName) throws Exception
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String subRepFolder = repositoryPath + "/" + RepConstantsCommon.SUB_REP_RAW_DATA_FOLDER + "/"
		+ reportCO.getPROG_REF() + "_" + usrName;
	try
	{
	    List<IRP_SUB_REPORTCO> subRepLst = reportCO.getSubreportsList();
	    IRP_SUB_REPORTCO subRepCO;
	    String subRepExpr;
	    JasperDesign jasperDesign;
	    JasperReport jasperReport;
	    JasperPrint jasperPrint;
	    String repExt;
	    String repUrl;
	    ByteArrayOutputStream byteArray;
	    HashMap<String, Object> retMap;
	    byte[] newJrxml;
	    String subRepProgRef;
	    byte[] reportBytes;
	    //create a directory to store the temporary .jrxml and .jasper file created from the dynamic jrxml
	    FileUtil.makeDirectories(subRepFolder);
	    for(int i = 0; i < subRepLst.size(); i++)
	    {
		subRepCO = subRepLst.get(i);
		subRepExpr = subRepCO.getIrpSubReportVO().getSUB_REPORT_EXPRESSION();
		subRepProgRef = subRepCO.getPROG_REF();
		repUrl = subRepFolder + "/" + subRepExpr;
		IRP_AD_HOC_REPORTCO tempCO = new IRP_AD_HOC_REPORTCO();
		tempCO.setJRXML_FILE(subRepCO.getJRXML_FILE());
		//create the dynamicRawDataJrxml
		retMap = reportBO.createDynamicRowDataJrxml(var_noHeadAndFoot, subRepExpr, subRepCO.getPROG_REF(),
			tempCO);
		newJrxml = (byte[]) retMap.get("0");
		//save the jrxml under the repository
		reportBO.createJRXMLFile(newJrxml, repUrl);
		jasperDesign = JRXmlLoader.load(repUrl + "." + ConstantsCommon.JRXML_EXT);
		ReportUtils.addRepGlobalParam(jasperDesign);
		//save the jasper file under the repository
		JasperCompileManager.compileReportToFile(jasperDesign, repUrl + "." + RepConstantsCommon.JASPER_EXT);
		File reportFile = new PathFileSecure(repUrl + "." + RepConstantsCommon.JASPER_EXT);
		if(!reportFile.exists())
		{
		    throw new JRRuntimeException(repUrl + ".jasper file not found for report reference: "
			    + subRepProgRef + " and report application: " + subRepCO.getAPP_NAME());
		}
		try
		{
		    jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
		}
		catch(Exception e)
		{
		    throw new BaseException("Error in loading the file for report reference: " + subRepProgRef
			    + " and report application: " + subRepCO.getAPP_NAME() + "\n " + e.getMessage(), e);
		}

		try
		{
		    jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, con);
		}

		catch(Exception e)
		{
		    throw new BaseException("Error when filling the report for report reference: " + subRepProgRef
			    + " and report application: " + subRepCO.getAPP_NAME() + "\n " + e.getMessage(), e);
		}

		try
		{
		    byteArray = new ByteArrayOutputStream();
		    repExt = ConstantsCommon.XLSX_EXT;

		    JRXlsxExporter exporter = new JRXlsxExporter();
		    exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
		    exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(byteArray));
		    SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
		    configuration.setOnePagePerSheet(false);
		    configuration.setDetectCellType(true);
		    configuration.setWhitePageBackground(false);
		    configuration.setRemoveEmptySpaceBetweenRows(true);
		    configuration.setCollapseRowSpan(true);
		    configuration.setRemoveEmptySpaceBetweenColumns(true);
		    exporter.setConfiguration(configuration);
		    exporter.exportReport();
		    reportBytes = byteArray.toByteArray();
		    FileUtil.saveToRepository(reportBytes, ReportingConstantsCommon.repositoryFolder, mainExcelFolder
			    + "/" + subRepExpr + "." + repExt);
		}
		catch(Exception e)
		{
		    throw new BaseException("Error when exporting report to CSV format for report reference: "
			    + subRepProgRef + " and report application: " + subRepCO.getAPP_NAME() + "\n "
			    + e.getMessage(), e);
		}
	    }
	}
	catch(Exception e)
	{
	    throw new BaseException("error in CommonRepFuncBOImpl.writeRawDataSubReport  ", e);
	}
	finally
	{
	    //delete the temporary created folder 
	    FileUtil.deleteFolder(subRepFolder);
	}

    }
    
    @Override
    public StringBuffer generateSitcom(BigDecimal snapshotId, String appName, BigDecimal repId, String repRef,
	    BigDecimal companyCode, Date retrieveDate, String snpFreq, Map parameters, HashMap<Object,Object> sitcomParamsMap) throws BaseException
    {
	StringBuffer sb = new StringBuffer();
	ArrayList<HashMap<String, Object>> ar;
	try
	{
	    Integer mainRepSize = null;
	    Integer mainPageCount = null;
	    if(sitcomParamsMap != null)
	    {
		mainRepSize = (Integer) sitcomParamsMap.get(RepConstantsCommon.MAIN_REP_SIZE);
		mainPageCount = (Integer) sitcomParamsMap.get(RepConstantsCommon.MAIN_PAGE_COUNT);
	    }
	    if(parameters == null)
	    {
		REP_SNAPSHOT_INFOVO infoVO = new REP_SNAPSHOT_INFOVO();
		infoVO.setREP_SNAPSHOT_ID(snapshotId);
		REP_SNAPSHOT_INFOVO retInfoVO = snapshotParameterBO.retSnpInfoVOByRepSnpId(infoVO);
		ar = (ArrayList<HashMap<String, Object>>) CommonUtil.objectFromBytes(retInfoVO.getREP_BLOB());
	    }
	    else
	    {
		ar = (ArrayList<HashMap<String, Object>>) parameters.get(RepConstantsCommon.JASPER_PATH_ARR);
	    }
	    SnapshotParameterSC sc = new SnapshotParameterSC();
	    sc.setREP_ID(repId);
	    REP_SNAPSHOT_INFORMATIONCO formulas = snapshotParameterBO.retFormulaToApply(sc);
	    if(formulas == null)
	    {
		return sb;
	    }
	    String frm = formulas.getRepSnapshotParamVO().getSITCOM_FORMULA();
	    String headerFrm = formulas.getRepSnapshotParamVO().getSITCOM_FORMULA_HEADER();
	    String footerFrm = formulas.getRepSnapshotParamVO().getSITCOM_FORMULA_FOOTER();
	    if(frm == null && headerFrm == null && footerFrm == null)
	    {
		return sb;
	    }
	    /*
	     * fill a hashMap with colTechName,lblAlias in order to use it when
	     * sending the rowList to the expression in case the colTechName and
	     * the lblAlias are not the same
	     */
	    IRP_AD_HOC_REPORTSC repSC = new IRP_AD_HOC_REPORTSC();
	    repSC.setPROG_REF(repRef);
	    repSC.setAPP_NAME(appName);
	    HashMap<String, Object> colTechMap = reportsMismatchBO.fillColTechNameMap(repSC);
	    // Parser Code
	    List<Map<String, Object>> rowData = new ArrayList<Map<String, Object>>();
	    HashMap<String, Object> hhm;
	    Map<String, Object> row;
	    Iterator<Map.Entry<String, Object>> itHhm;
	    Entry<String, Object> entry;
	    String colAlias;
	    for(int i = 0; i < ar.size(); i++)
	    {
		hhm = ar.get(i);
		if(hhm != null)
		{
		    row = new LinkedHashMap<String, Object>();
		    itHhm = hhm.entrySet().iterator();
		    while(itHhm.hasNext())
		    {
			entry = itHhm.next();
			if((entry.getKey().equals("-1")
					&& RepConstantsCommon.JASPER_PATH_HASNEXT.equals(entry.getValue()))
					|| ("1".equals(entry.getKey()) && entry.getValue()!=null && "1".equals(entry.getValue().toString())))
			{
			    break;
			}
			else
			{
			    colAlias = (String) colTechMap.get(entry.getKey());
			    if(colAlias == null)
			    {
				colAlias = entry.getKey();
			    }
			    row.put(colAlias, entry.getValue());
			}
		    }
		    if(!row.isEmpty())
		    {
			rowData.add(row);
		    }
		}
	    }
	    BTR_CONTROLSC btSC = new BTR_CONTROLSC();
	    btSC.setCOMP_CODE(companyCode);
	    BTR_CONTROLCO co = controlRecordBO.retrieveControlRecordData(btSC);
	    String[] formulasArr = new String[3];
	    formulasArr[0] = headerFrm;
	    formulasArr[1] = frm;
	    formulasArr[2] = footerFrm;
	    if(parameters != null)
	    {
		formulasArr = replaceArgsByValues(formulasArr, parameters);
	    }
	    // for every section fill control record data
	    SimpleDateFormat sdf = new SimpleDateFormat(RepConstantsCommon.SCHED_DATE_FRMT,Locale.ENGLISH);
	    for(int i = 0; i < formulasArr.length; i++)
	    {
		if(StringUtil.isNotEmpty(formulasArr[i]))
		{
		    formulasArr[i] = StringUtil.replaceInString(formulasArr[i], "'"
			    + RepConstantsCommon.SNAPSHOT_RETREIVAL_DATE + "'", "'" + sdf.format(retrieveDate) + "'");
		    formulasArr[i] = StringUtil.replaceInString(formulasArr[i], "'"
			    + RepConstantsCommon.SNAPSHOT_BANK_REF + "'",
			    "'" + String.valueOf(co.getBtrControlVO().getBANK_ID()) + "'");
		    formulasArr[i] = StringUtil.replaceInString(formulasArr[i], "'"
			    + RepConstantsCommon.SNAPSHOT_COUNTRY_CODE + "'", "'"
			    + co.getBtrControlVO().getCB_COUNTRY_CODE() + "'");
		    formulasArr[i] = StringUtil.replaceInString(formulasArr[i], "'"
			    + RepConstantsCommon.SNAPSHOT_REP_REMITTANCE + "'", "'" + snpFreq + "'");
		    formulasArr[i] = StringUtil.replaceInString(formulasArr[i],
				"'" + RepConstantsCommon.SNAPSHOT_REP_SIZE + "'", "'" + NumberUtil.nullToZero(mainRepSize) + "'");
			formulasArr[i] = StringUtil.replaceInString(formulasArr[i],
				"'" + RepConstantsCommon.SNAPSHOT_PAGE_COUNT + "'", "'" + NumberUtil.nullToZero(mainPageCount) + "'");
		}
	    }
	    headerFrm = formulasArr[0];
	    frm = formulasArr[1];
	    footerFrm = formulasArr[2];
	    StringBuffer countLinesBuf = new StringBuffer();
	    Integer nbLinesBody;
	    String exprResult;
	    boolean toClear = false;
	    if(StringUtil.isNotEmpty(headerFrm))
	    {
		if(rowData.isEmpty())
		{
		    rowData.add(colTechMap);
		    toClear = true;
		}
		exprResult = String.valueOf(commonLibBO.executeExpression(headerFrm, 0, rowData));
		if(toClear)
		{
		    rowData.clear();
		}
		if(StringUtil.isNotEmpty(exprResult))
		{
		    sb.append(exprResult + "\r\n");
		}
	    }
	    if(!rowData.isEmpty() && StringUtil.isNotEmpty(frm))
	    {
		boolean singleReport = true;
		Map<String, Object> tmpHash = new HashMap<String, Object>();
		for(int i = 0; i < rowData.size(); i++)
		{
		    tmpHash = rowData.get(i);
		    if(tmpHash.containsKey(RepConstantsCommon.SNP_REPORT_ORDER))
		    {
			singleReport = false;
			break;
		    }
		}
		if(singleReport)
		{
		    for(int i = 0; i < rowData.size(); i++)
		    {
			exprResult = String.valueOf(commonLibBO.executeExpression(frm, i, rowData));
			if(StringUtil.isNotEmpty(exprResult))
			{
			    countLinesBuf.append(exprResult + "\n");
			    sb.append(exprResult + "\r\n");
			}
		    }
		}
		else
		{
		    // get the different values of report orders
		    HashMap<BigDecimal, BigDecimal> repOrderValMap = new HashMap<BigDecimal, BigDecimal>();
		    List<Map<String, Object>> tmpMap = new ArrayList<Map<String, Object>>();
		    for(int i = 0; i < rowData.size(); i++)
		    {
			repOrderValMap.put((BigDecimal) rowData.get(i).get(RepConstantsCommon.SNP_REPORT_ORDER),
				(BigDecimal) rowData.get(i).get(RepConstantsCommon.SNP_REPORT_ORDER));
		    }
		    repOrderValMap.remove(null);
		    List<BigDecimal> mapKeys = new ArrayList<BigDecimal>(repOrderValMap.keySet());
		    Collections.sort(mapKeys);
		    Iterator<BigDecimal> keyIt = mapKeys.iterator();
		    BigDecimal repOrder;
		    while(keyIt.hasNext())
		    {
			repOrder = keyIt.next();
			for(int i = 1; i < rowData.size(); i++)
			{
			    if(((BigDecimal) rowData.get(i).get(RepConstantsCommon.SNP_REPORT_ORDER))
				    .compareTo(repOrder) == 0)
			    {
				tmpMap.add(rowData.get(i));
			    }
			}
			for(int i = 0; i < tmpMap.size(); i++)
			{
			    exprResult = String.valueOf(commonLibBO.executeExpression(frm, i, tmpMap));
			    if(StringUtil.isNotEmpty(exprResult))
			    {
				countLinesBuf.append(exprResult + "\n");
				sb.append(exprResult + "\r\n");
			    }
			}
			tmpMap.clear();
		    }
		}
	    }
	    nbLinesBody = countLinesBuf.toString().split("[\n|\r]").length;
	    if(StringUtil.isNotEmpty(footerFrm))
	    {
		if(footerFrm.indexOf(RepConstantsCommon.TXT_FILE_NBLINES_FUNC) >= 0)
		{
		    footerFrm = StringUtil.replaceInString(footerFrm, RepConstantsCommon.TXT_FILE_NBLINES_FUNC, "'"
			    + nbLinesBody.toString() + "'");
		}
		if(rowData.isEmpty())
		{
		    rowData.add(colTechMap);
		}
		exprResult = String.valueOf(commonLibBO.executeExpression(footerFrm, 0, rowData));
		if(StringUtil.isNotEmpty(exprResult))
		{
		    sb.append(exprResult + "\r\n");
		}
	    }
	}
	catch(IOException e)
	{
	    throw new BaseException(e.getMessage(), e);
	}
	catch(ClassNotFoundException e)
	{
	    throw new BaseException(e.getMessage(), e);
	}
	return sb;
    }
    
    /**
     * Method that returns the list of formulas filled with params values
     * @param values
     * @param parameters
     * @return
     */
    private String[] replaceArgsByValues(String[] values, Map parameters)
    {
	String paramVal = "";
	String paramName;
	String[] valuesArr = values;
	Object value;
	SimpleDateFormat sdf = new SimpleDateFormat(RepConstantsCommon.SCHED_DATE_FRMT,Locale.ENGLISH);
	for(int i = 0; i < valuesArr.length; i++)
	{
	    if(StringUtil.isNotEmpty(valuesArr[i]))
	    {
		while(valuesArr[i].indexOf("[") >= 0)
		{
		    paramVal="";
		    paramName="";
		    paramName = valuesArr[i].substring(valuesArr[i].indexOf("[") + 1, valuesArr[i].indexOf("]"));
		    value = parameters.get(paramName);
		    if(value instanceof BigDecimal)
		    {
			paramVal = value.toString();
		    }
		    else if(value instanceof String)
		    {
			paramVal = (String) value;
		    }
		    else if(value instanceof Date)
		    {
			paramVal = sdf.format((Date) value);
		    }
		    valuesArr[i] = StringUtil.replaceInString(valuesArr[i], "[" + paramName + "]", "'"+paramVal+"'");
		}
	    }
	}
	return valuesArr;
    }
    
    @Override
    public int retMinRepSnapshotRepId(String progRef) throws BaseException
    {
	return commonRepFuncDAO.retMinRepSnapshotRepId(progRef);
    }
}
