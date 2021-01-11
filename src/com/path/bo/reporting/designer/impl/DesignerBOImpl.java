package com.path.bo.reporting.designer.impl;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;

import javax.imageio.ImageIO;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import net.sf.jasperreports.components.table.DesignCell;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExpressionChunk;
import net.sf.jasperreports.engine.JRSubreportParameter;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignSubreport;
import net.sf.jasperreports.engine.design.JRDesignSubreportParameter;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.VersionUtils;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.common.translation.TranslationBO;
import com.path.bo.common.translation.TranslationConstants;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.util.FileTransformation;
import com.path.bo.reporting.common.util.ReportUtils;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.bo.reporting.designer.ImageBO;
import com.path.bo.reporting.designer.ProceduresBO;
import com.path.bo.reporting.designer.QueryBO;
import com.path.bo.reporting.designer.UploadImageBO;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.bo.reporting.ftr.folders.FoldersBO;
import com.path.bo.reporting.ftr.scheduler.SchedulerBO;
import com.path.dao.reporting.designer.DesignerDAO;
import com.path.dao.reporting.designer.ProceduresDAO;
import com.path.dao.reporting.ftr.folders.FoldersDAO;
import com.path.dao.reporting.ftr.snapshots.SnapshotParameterDAO;
import com.path.dbmaps.vo.API_SCR_LOGVO;
import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.FTR_EXP_XLSVO;
import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVO;
import com.path.dbmaps.vo.IRP_AD_HOC_REPORTVOWithBLOBs;
import com.path.dbmaps.vo.IRP_CLIENT_REPORTVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_FOLDERVO;
import com.path.dbmaps.vo.IRP_HASH_TABLEVO;
import com.path.dbmaps.vo.IRP_HASH_TABLE_REPORTVO;
import com.path.dbmaps.vo.IRP_PROCVO;
import com.path.dbmaps.vo.IRP_QUERY_ARG_MAPPINGVO;
import com.path.dbmaps.vo.IRP_REPORT_EXEC_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_QUERYVOKey;
import com.path.dbmaps.vo.IRP_REPORT_SCHEDULEVO;
import com.path.dbmaps.vo.IRP_REPORT_SCHED_PARAMSVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTSVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTS_FILTERSVO;
import com.path.dbmaps.vo.IRP_REP_ARG_CONSTRAINTSVO;
import com.path.dbmaps.vo.IRP_REP_FILTERSVO;
import com.path.dbmaps.vo.IRP_REP_HYPERLINKSVO;
import com.path.dbmaps.vo.IRP_REP_IMAGESVO;
import com.path.dbmaps.vo.IRP_REP_METADATA_LOGVO;
import com.path.dbmaps.vo.IRP_REP_PROCVO;
import com.path.dbmaps.vo.IRP_REP_PROC_PARAMSVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_GROUP_BYVO;
import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_RECEIVERSVO;
import com.path.dbmaps.vo.IRP_SNAPSHOT_PARAM_MAPPINGVO;
import com.path.dbmaps.vo.IRP_SUB_REPORTVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.OPTVOKey;
import com.path.dbmaps.vo.OPT_EXTENDEDVO;
import com.path.dbmaps.vo.OPT_EXTENDEDVOKey;
import com.path.dbmaps.vo.PTH_CLIENTSVO;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.dbmaps.vo.PTH_CTRLVO;
import com.path.dbmaps.vo.REP_CATALOGUEVO;
import com.path.dbmaps.vo.REP_INFOVO;
import com.path.dbmaps.vo.REP_SNAPSHOT_PARAMVO;
import com.path.dbmaps.vo.SYS_PARAM_LANGUAGESVO;
import com.path.dbmaps.vo.S_APPVO;
import com.path.dbmaps.vo.snapshots.SnapshotParameterSC;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.FileZipUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.common.util.UnicodeUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.vo.common.CommonLibSC;
import com.path.vo.common.DBSequenceSC;
import com.path.vo.common.SessionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.common.select.SelectSC;
import com.path.vo.common.translation.TranslationCO;
import com.path.vo.common.translation.TranslationSC;
import com.path.vo.reporting.AUTOMATED_IMPORT_REPORTSCO;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_CLIENT_REPORTCO;
import com.path.vo.reporting.IRP_CLIENT_REPORTSC;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_HASH_TABLECO;
import com.path.vo.reporting.IRP_HASH_TABLESC;
import com.path.vo.reporting.IRP_QUERY_ARG_MAPPINGCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_ARG_CONSTRAINTSCO;
import com.path.vo.reporting.IRP_REP_FILTERSSC;
import com.path.vo.reporting.IRP_REP_PROCCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.IRP_SUB_REPORTSC;
import com.path.vo.reporting.ImageCO;
import com.path.vo.reporting.PTH_CLIENTSCO;
import com.path.vo.reporting.PTH_CLIENTSSC;
import com.path.vo.reporting.USER_TAB_COLSCO;
import com.path.vo.reporting.VARIABLE_OBJECT;
import com.path.vo.reporting.designer.IRP_REP_PROCSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.folders.IRP_FOLDERCO;
import com.path.vo.reporting.ftr.template.AXSCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHEDULESC;
import com.path.vo.reporting.scheduler.IRP_REPORT_SCHED_PARAMSSC;

public class DesignerBOImpl extends BaseBO implements DesignerBO
{
    private DesignerDAO designerDAO;
    private ProceduresDAO procDAO; 
    private ProceduresBO procBO;
    private CommonRepFuncBO commonRepFuncBO;
    private QueryBO queryBO;
    private FcrBO fcrBO;
    private SchedulerBO schedulerBO;
    private FoldersBO foldersBO;
    private FoldersDAO foldersDAO;
    private TranslationBO translationBO;
    private SnapshotParameterDAO snapshotParameterDAO;
    private ImageBO imageBO;
    private UploadImageBO uploadImageBO;

    public void setUploadImageBO(UploadImageBO uploadImageBO)
    {
        this.uploadImageBO = uploadImageBO;
    }

    public void setImageBO(ImageBO imageBO)
    {
        this.imageBO = imageBO;
    }


    public SnapshotParameterDAO getSnapshotParameterDAO()
    {
        return snapshotParameterDAO;
    }

    public void setSnapshotParameterDAO(SnapshotParameterDAO snapshotParameterDAO)
    {
        this.snapshotParameterDAO = snapshotParameterDAO;
    }
    

    public void setTranslationBO(TranslationBO translationBO)
    {
        this.translationBO = translationBO;
    }

    public void setFoldersDAO(FoldersDAO foldersDAO)
    {
        this.foldersDAO = foldersDAO;
    }
    
    public void setFoldersBO(FoldersBO foldersBO)
    {
        this.foldersBO = foldersBO;
    }
    

    public void setSchedulerBO(SchedulerBO schedulerBO)
    {
	this.schedulerBO = schedulerBO;
    }

    public DesignerDAO getDesignerDAO()
    {
	return designerDAO;
    }

    public void setDesignerDAO(DesignerDAO designerDAO)
    {
	this.designerDAO = designerDAO;
    }

    public ProceduresDAO getProcDAO()
    {
	return procDAO;
    }

    public void setProcDAO(ProceduresDAO procDAO)
    {
	this.procDAO = procDAO;
    }

    public QueryBO getQueryBO()
    {
	return queryBO;
    }

    public void setQueryBO(QueryBO queryBO)
    {
	this.queryBO = queryBO;
    }

    public ProceduresBO getProcBO()
    {
	return procBO;
    }

    public void setProcBO(ProceduresBO procBO)
    {
	this.procBO = procBO;
    }

    public CommonRepFuncBO getCommonRepFuncBO()
    {
	return commonRepFuncBO;
    }

    public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO)
    {
	this.commonRepFuncBO = commonRepFuncBO;
    }

    public void setFcrBO(FcrBO fcrBO)
    {
	this.fcrBO = fcrBO;
    }

    @Override
    public String generateJrxml(String xhtml, String xslFileName) throws BaseException
    {
	return FileTransformation.xslTransformation(xhtml, xslFileName);
    }

    public IRP_AD_HOC_REPORTCO retReportFlags(IRP_AD_HOC_REPORTSC repSC) throws BaseException
    {
	return designerDAO.retReportFlags(repSC);
    }

    public void deleteUploadDetails(IRP_AD_HOC_REPORTCO reportCO)
    {
	try
	{
	    List repIdsList = Arrays.asList(reportCO.getREPORT_ID());
	    queryBO.deleteQueries(repIdsList);
	    // delete from irp_rep_proc_params
	    IRP_REP_PROCSC procSC = new IRP_REP_PROCSC();
	    procSC.setREPORTS_ID(repIdsList);
	    procDAO.deleteProcParamsByReportId(procSC);
	    // delete from irp_rep_proc
	    procDAO.deleteProcsByReportId(procSC);
	    // delete hyperlinks
	    designerDAO.deleteReportHyperlinks(repIdsList);

	    // the args will be managed when uploading the report ,so we will
	    // not delete them here
	    // designerDAO.deleteArguments(repIdsList);
	}
	catch(Exception e)
	{
	    log.error(e, "__________ error in deleteUploadDetails() _________");
	}
    }

    @Override
    public IRP_AD_HOC_REPORTCO save(IRP_AD_HOC_REPORTVOWithBLOBs vo, IRP_AD_HOC_REPORTCO reportCO, boolean updateRep,
	    BigDecimal compCode, BigDecimal brchCode, String userName,String pageRef)
	    throws BaseException, IOException
    {
	BigDecimal companyCode=compCode;
	BigDecimal branchCode=brchCode;
	if(ConstantsCommon.SADS_APP_NAME.equals(vo.getAPP_NAME())) 
	{
	    companyCode=BigDecimal.ZERO;
	    branchCode=BigDecimal.ZERO;
	}

	vo.setCOMP_CODE(companyCode);
	vo.setBRANCH_CODE(branchCode);
	vo.setREPORT_TYPE(reportCO.getREPORT_TYPE());
	// check if we are updating a report from upload download section then
	// delete all previous data from DB before proceeding
	
	
	//to save the translation on save as click of the report
	if(reportCO.getOldRepAppName()!=null && reportCO.getOldRepProgRef()!=null)
	{
	    designerDAO.insertRepTranslation(reportCO);
	}
	
	if(reportCO.isFromUpDown() && reportCO.getREPORT_ID() != null && reportCO.isUpdateUpDownRep())
	{
	    deleteUploadDetails(reportCO);
	    reportCO.setUpdateUpDownRep(false);
	}

	/*
	 * if from uploadDownload and skipOpt is true then check if one of the
	 * opts related to the current progRef does not exist in the table opt
	 * then add it
	 */
	if(reportCO.isFromUpDown() && "true".equals(reportCO.getSKIP_OPT_AXS()))
	{
	    addMissingOpts(reportCO, updateRep);
	}
// K.Kazan 11/03/2014- the below code is invalid since the skip opt checkbox will be always checked and disabled in update mode
//	if(updateRep && reportCO.isFromUpDown() && !("true".equals(reportCO.getSKIP_OPT_AXS())))
//	{
//	     update opt tables
//	    FTR_OPTCO fcrCO = new FTR_OPTCO();
//	    fcrCO.getFtrOptVO().setPROG_REF(reportCO.getPROG_REF());
//	    fcrCO.getFtrOptVO().setPARENT_REF(reportCO.getPARENT_REF());
//	    fcrCO.getFtrOptVO().setBRIEF_NAME_ENG(reportCO.getREPORT_NAME());
//
//	    fcrCO.getFtrOptVO().setDISP_ORDER(new BigDecimal(7));
//
//	    fcrBO.updateReportOPTs(fcrCO, vo.getAPP_NAME(), true, optTrans);
//	}

	// check if we still have a pb application
	int pbAppCnt = designerDAO.retPbAppCnt();
	REP_INFOVO infoVO = new REP_INFOVO();
	REP_CATALOGUEVO catVO = new REP_CATALOGUEVO();
	if(pbAppCnt > 0)
	{
	    // get the sequence
	    infoVO.setUSERID(userName);
	    infoVO.setTITLE(vo.getREPORT_NAME());
	    infoVO.setAPPLICATION(vo.getAPP_NAME());
	    infoVO.setREP_TYPE("DWS");
	    infoVO.setCOMP_CODE(companyCode);
	    infoVO.setBRANCH_CODE(branchCode);
	    infoVO.setREP_REFERENCE(vo.getPROG_REF());
	    infoVO.setDW_OBJECT("$$JAVA");
	    catVO.setREP_OBJECT(vo.getJRXML_FILE());
	}
	
	FTR_EXP_XLSVO ftrExpXlsVO = new FTR_EXP_XLSVO();
	ftrExpXlsVO.setCOMP_CODE(companyCode);
	ftrExpXlsVO.setREP_REF(reportCO.getPROG_REF());
	ftrExpXlsVO.setXLS_PATH(reportCO.getXlsName());
	genericDAO.delete(ftrExpXlsVO);
	if(!"".equals(reportCO.getXlsName()))
	{
	    genericDAO.insert(ftrExpXlsVO);
	}

	if(updateRep)
	{
	    if(pbAppCnt > 0)
	    {
		// update REP_INFO
		// return the old_report_id from the report_id
		BigDecimal oldRepId = designerDAO.retOldRepIdFromRepId(vo.getREPORT_ID());
		if(!BigDecimal.ZERO.equals(oldRepId))
		{
		    infoVO.setREP_ID(oldRepId);
		    infoVO.setREP_DATE(vo.getDATE_MODIFIED());
		    genericDAO.update(infoVO);
		    // update REP_CATALOGUE
		    catVO.setREP_ID(oldRepId);
		    genericDAO.update(catVO);
		}

	    }
	    
	    vo.setDATE_UPDATED(reportCO.getDATE_UPDATED());
	    
	    Integer row = designerDAO.updateReport(vo);
	    if(row == null || row < 1)
	    {
		throw new BOException(MessageCodes.RECORD_CHANGED);
	    }
	   
	    //update in opt table
	    if(reportCO.isFromUpDown())
	    {
		OPTVO optVO=new OPTVO();
		optVO.setAPP_NAME(reportCO.getAPP_NAME());
		optVO.setPROG_REF(reportCO.getPROG_REF());
		optVO.setPARENT_REF(reportCO.getPARENT_REF());
		optVO.setMENU_TITLE_ENG(reportCO.getMENU_TITLE_ENG());
		optVO.setPROG_DESC(reportCO.getMENU_TITLE_ENG());
		optVO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,vo.getHttpSessionIdForLink());
		
		// check if the the is_web_yn==2 in the table s_app then set the
		// categ_id of the opt the same as its parent
		S_APPVO sAppVO = new S_APPVO();
		sAppVO.setAPP_NAME(reportCO.getAPP_NAME());
		sAppVO = commonLibBO.returnApplication(sAppVO);
		if(BigDecimal.valueOf(2).equals(sAppVO.getIS_WEB_YN()))
		{
			if(RepConstantsCommon.ROOT.equals(reportCO.getPARENT_REF()))
			{
				optVO.setCATEG_ID(reportCO.getCATEGORY_ID());
			}
			else
			{
				OPTVO zVO = new OPTVO();
			    zVO.setAPP_NAME(reportCO.getAPP_NAME());
			    zVO.setPROG_REF(reportCO.getPARENT_REF());
			    zVO = (OPTVO) genericDAO.selectByPK(zVO);
			    optVO.setCATEG_ID(zVO.getCATEG_ID());
			}
		}
		genericDAO.update(optVO);
	    }
	    
	}
	else
	{
	    if(pbAppCnt > 0)
	    {
		// insert REP_INFO
		infoVO.setREP_DATE(vo.getDATE_CREATED());
		/*
		 * in case of 'replace if exist' the old 'old_rep_id' will be provided
		 * if exist in order to save it and not generating a new one
		*/
	    if(infoVO.getREP_ID() == null)
	    {
	    	DBSequenceSC dbSeqSC = new DBSequenceSC();
	    	dbSeqSC.setSequenceName("REP_ID_SEQ");
	    	dbSeqSC.setTableName("REP_IDENTITY");
	    	infoVO.setREP_ID(commonLibBO.returnTableSequence(dbSeqSC));
	    }
		BigDecimal oldId = designerDAO.insertRepInfo(infoVO);
		// insert REP_CATALOGUE
		catVO.setREP_ID(oldId);
		genericDAO.insert(catVO);
		// set the old_report_id in IRP_AD_HOC_REPORT
		vo.setOLD_REPORT_ID(oldId);

		// Add dummy update
		REP_CATALOGUEVO dummyCatVO = new REP_CATALOGUEVO();
		dummyCatVO.setREP_ID(catVO.getREP_ID());
		dummyCatVO.setREP_OBJECT(catVO.getREP_OBJECT());
		genericDAO.update(dummyCatVO);
		

	    }

	    // BigDecimal newRepId=designerDAO.insert(vo);
	    genericDAO.insert(vo);
	    // add dummy update
	    IRP_AD_HOC_REPORTVOWithBLOBs dummyRepVO = new IRP_AD_HOC_REPORTVOWithBLOBs();
	    dummyRepVO.setREPORT_ID(vo.getREPORT_ID());
	    dummyRepVO.setREPORT_NAME(vo.getREPORT_NAME());
	    genericDAO.update(dummyRepVO);

	}

	IRP_AD_HOC_QUERYVO queryvo;

	for(IRP_AD_HOC_QUERYCO query : reportCO.getQueriesList())
	{
	    queryvo = new IRP_AD_HOC_QUERYVO();
	    queryvo.setQUERY_ID(query.getQUERY_ID());
	    queryvo.setQUERY_NAME(query.getQUERY_NAME());
	    // set the arg rep list to sqlObj args
	    if(!reportCO.isFromUpDown() && ConstantsCommon.OUTPUT_LAYOUT_FREE_FORM.equals(query.getSqlObject().getOutputLayout()))
	    {
		query.getSqlObject().setArgumentsList(reportCO.getArgumentsList());
	    }
	    // set the arg rep list to sqlObj args
	    if(reportCO.isFromUpDown() && ConstantsCommon.OUTPUT_LAYOUT_FREE_FORM.equals(query.getSqlObject().getOutputLayout()))
	    {
		query.getSqlObject().setArgumentsList(reportCO.getArgumentsList());
	    }
	    queryvo.setQUERY_OBJECT(CommonUtil.objectToBytes(query.getSqlObject()));
	    if(query.getUpdate())
	    {
		queryvo.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,vo.getHttpSessionIdForLink());
		queryBO.updateQuery(queryvo);
	    }
	    else
	    {
		queryBO.insertQuery(queryvo);
		//add dummy update
		queryvo = new IRP_AD_HOC_QUERYVO();
		queryvo.setQUERY_ID(query.getQUERY_ID());
		queryvo.setQUERY_NAME(query.getQUERY_NAME());
		genericDAO.update(queryvo);
	    }

	    if(query.getReportQueryVO() == null)
	    {
		IRP_REPORT_QUERYVOKey rqVO = new IRP_REPORT_QUERYVOKey();
		rqVO.setREPORT_ID(vo.getREPORT_ID());
		rqVO.setQUERY_ID(query.getQUERY_ID());
		queryBO.insertReportQry(rqVO);
		query.setReportQueryVO(rqVO);
	    }
	}

	if(!updateRep && ((reportCO.isFromUpDown() && !("true".equals(reportCO.getSKIP_OPT_AXS()))) || (!reportCO.isFromUpDown())))
	{
	    // in upload section ,do not save in axs and opt tables if the 'skip
	    // opt axs' checkbox is checked
	    fcrBO.saveReportOpts(companyCode, branchCode, vo.getAPP_NAME(), userName, reportCO, vo.getDATE_CREATED());
	}

	HashMap<Long, BigDecimal> argIdsMap = new HashMap<Long, BigDecimal>();
	//will be used in case of save as when setting the procParamArgId to the new one
	HashMap<BigDecimal, BigDecimal> oldNewArgIdsMap = new HashMap<BigDecimal, BigDecimal>();
	// update report arguments
//	Collection<IRP_REP_ARGUMENTSCO> params = reportCO.getArgumentsList().values();
	IRP_REP_ARGUMENTSVO argument = new IRP_REP_ARGUMENTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO = new IRP_REP_ARG_CONSTRAINTSVO();
	//deleting the old arguments before adding the new ones 
	Collection<IRP_REP_ARGUMENTSCO> params = reportCO.getArgsDbDelete().values();
	IRP_REP_FILTERSSC filterSC = new IRP_REP_FILTERSSC();
	filterSC.setIsSybase(commonLibBO.returnIsSybase());
	for(IRP_REP_ARGUMENTSCO param : params)
	{
	    // This control is added because when adding an arg. to the
	    // argsDbDelete
	    // we didn't check if it is a new created arg. in order to not add
	    // it to the map
	    if(!NumberUtil.isEmptyDecimal(param.getARGUMENT_ID()))
	    {
		argument.setARGUMENT_ID(param.getARGUMENT_ID());
		argument.setREPORT_ID(param.getREPORT_ID());

		// added by haytham.k to delete link qry args
		IRP_QUERY_ARG_MAPPINGVO irpQryArgMapVO = new IRP_QUERY_ARG_MAPPINGVO();
		irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID());
		irpQryArgMapVO.setREPORT_ID(param.getREPORT_ID());
		irpQryArgMapVO.setREPORT_ARGUMENT_ID(param.getARGUMENT_ID());
		designerDAO.deleteLinkQryArgsMap(irpQryArgMapVO);
		
		IRP_REPORT_SCHED_PARAMSSC schedParamSC=new IRP_REPORT_SCHED_PARAMSSC();
		schedParamSC.setREPORT_ID(param.getREPORT_ID());
		schedParamSC.setPARAM_ID(param.getARGUMENT_ID());
		schedulerBO.deleteRepSchedArg(schedParamSC);
		constrVO.setREPORT_ID(param.getREPORT_ID());
		constrVO.setARGUMENT_ID(param.getARGUMENT_ID());
		designerDAO.deleteConstraints(constrVO);		
		filterSC.setREPORT_ID(param.getREPORT_ID());
		filterSC.setARGUMENT_ID(param.getARGUMENT_ID());
		designerDAO.deleteArgumentsFilters(filterSC);
		genericDAO.delete(argument);
	    }
	}
//	
	//reseting the value of params to the newly inserted arguments
	params = reportCO.getArgumentsList().values();
	
	boolean isReportCifAudit = BigDecimal.ONE.compareTo(NumberUtil.nullToZero(reportCO.getCIF_AUDIT_YN())) == 0 ?true:false;
	for(IRP_REP_ARGUMENTSCO param : params)
	{
	    if(param.getARGUMENT_ID() == null)
	    {
		argument.setREPORT_ID(vo.getREPORT_ID());
		argument.setARGUMENT_NAME(param.getARGUMENT_NAME());
		argument.setARGUMENT_LABEL(param.getARGUMENT_LABEL());
		argument.setARGUMENT_VALUE(param.getARGUMENT_VALUE());
		argument.setARGUMENT_TYPE(param.getARGUMENT_SOURCE().equals(new BigDecimal(10))?ConstantsCommon.CONNECTION:param.getARGUMENT_TYPE());
		argument.setARGUMENT_SOURCE(param.getARGUMENT_SOURCE());
		argument.setSESSION_ATTR_NAME(param.getSESSION_ATTR_NAME());
		argument.setQUERY_ID(param.getQUERY_ID());
		argument.setCOLUMN_NAME(param.getCOLUMN_NAME());
		argument.setARGUMENT_ORDER(param.getARGUMENT_ORDER());
		argument.setDISPLAY_FLAG(param.getDISPLAY_FLAG());
		argument.setENABLE_FLAG(param.getENABLE_FLAG());
		argument.setQUERY_ID_DEFAULT(param.getQUERY_ID_DEFAULT());
		argument.setDEFAULT_VALUE_COL_NAME(param.getDEFAULT_VALUE_COL_NAME());
		argument.setARGUMENT_SRC_DEFAULT(param.getARGUMENT_SRC_DEFAULT());
		argument.setFLAG_VALUE_ON(param.getFLAG_VALUE_ON());
		argument.setFLAG_VALUE_OFF(param.getFLAG_VALUE_OFF());
		argument.setMULTISELECT_YN(param.getMULTISELECT_YN());
		argument.setTO_SAVE_YN(param.getTO_SAVE_YN());
		argument.setCIF_AUDIT_YN(param.getCIF_AUDIT_YN());
		argument.setARGUMENT_DATE_VALUE(param.getARGUMENT_DATE_VALUE());
		argument.setARG_QUERY_OPTIONS(param.getARG_QUERY_OPTIONS());
		argument.setCOLUMN_DESC(param.getCOLUMN_DESC());
		argument.setDEFAULT_VALUE_COL_DESC(param.getDEFAULT_VALUE_COL_DESC());
		BigDecimal argId = designerDAO.insertArgument(argument);
		param.setARGUMENT_ID(argId);
		param.setREPORT_ID(vo.getREPORT_ID());
		//inserting the new constraints
		ArrayList<IRP_REP_ARG_CONSTRAINTSVO> listConstrVO = fillConstrVOs(param);
		for(int i=0;i<listConstrVO.size();i++)
		{
		    genericDAO.insert(listConstrVO.get(i));
		}

		for(Entry<String, List<IRP_QUERY_ARG_MAPPINGCO>> entry : reportCO.getLinkQryArsMap().entrySet())
		{
		    //String[] arrayKey =null;
		    //String key = entry.getKey();
		    String[] arrayKey = entry.getKey().split("~");
		    String key =arrayKey[0];
		    String concatKey = entry.getKey();
		    if(key.equals(param.getARGUMENT_NAME()))
		    {
			List<IRP_QUERY_ARG_MAPPINGCO> qryArgMaplst = new ArrayList<IRP_QUERY_ARG_MAPPINGCO>();
			qryArgMaplst = new ArrayList(reportCO.getLinkQryArsMap().get(concatKey));
			for(int i = 0; i < qryArgMaplst.size(); i++)
			{

			    IRP_QUERY_ARG_MAPPINGVO irpQryArgMapVO = new IRP_QUERY_ARG_MAPPINGVO();
			    irpQryArgMapVO.setREPORT_ID(vo.getREPORT_ID());
			    irpQryArgMapVO.setREPORT_ARGUMENT_ID(argId);
			    if(arrayKey[1].equals("1"))
			    {
				irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID());
			    }
			    else
			    {
				irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID_DEFAULT());
			    }
			    // qryArgMaplst.get(0)
			    // for(int j=0;j<qryArgMaplst.get(i))
			    IRP_QUERY_ARG_MAPPINGCO mapQryArgVO = qryArgMaplst.get(i);
			    irpQryArgMapVO.setQUERY_ARG_NAME(mapQryArgVO.getQRY_ARG_NAME());
			    irpQryArgMapVO.setREPORT_MAPPED_ARG_NAME(mapQryArgVO.getIrpQueryArgsMappingVO()
				    .getREPORT_MAPPED_ARG_NAME());
			    irpQryArgMapVO.setSTATIC_VALUE(mapQryArgVO.getIrpQueryArgsMappingVO().getSTATIC_VALUE());
			    irpQryArgMapVO.setDEFAULT_SOURCE(new BigDecimal(arrayKey[1]));
			    genericDAO.insert(irpQryArgMapVO);
			}

		    }

		}

		argIdsMap.put(param.getIndex(), argument.getARGUMENT_ID());
		oldNewArgIdsMap.put(param.getOLD_ARGUMENT_ID(), argument.getARGUMENT_ID());
	    }
	}

	params = reportCO.getArgsDBUpdate().values();
	for(IRP_REP_ARGUMENTSCO param : params)
	{
	    argument.setARGUMENT_ID(param.getARGUMENT_ID());
	    argument.setREPORT_ID(param.getREPORT_ID());
	    argument.setARGUMENT_NAME(param.getARGUMENT_NAME());
	    argument.setARGUMENT_LABEL(param.getARGUMENT_LABEL());
	    argument.setARGUMENT_VALUE(param.getARGUMENT_VALUE());
	    argument.setARGUMENT_TYPE(param.getARGUMENT_SOURCE().equals(new BigDecimal(10))?ConstantsCommon.CONNECTION:param.getARGUMENT_TYPE());
	    argument.setARGUMENT_SOURCE(param.getARGUMENT_SOURCE());
	    argument.setSESSION_ATTR_NAME(param.getSESSION_ATTR_NAME());
	    argument.setQUERY_ID(param.getQUERY_ID());
	    argument.setCOLUMN_NAME(param.getCOLUMN_NAME());
	    argument.setCOLUMN_DESC(param.getCOLUMN_DESC());
	    argument.setARG_QUERY_OPTIONS(param.getARG_QUERY_OPTIONS());
	    argument.setARGUMENT_ORDER(param.getARGUMENT_ORDER());
	    argument.setDISPLAY_FLAG(param.getDISPLAY_FLAG());
	    argument.setENABLE_FLAG(param.getENABLE_FLAG());
	    
	    argument.setQUERY_ID_DEFAULT(param.getQUERY_ID_DEFAULT());
	    argument.setDEFAULT_VALUE_COL_NAME(param.getDEFAULT_VALUE_COL_NAME());
	    argument.setDEFAULT_VALUE_COL_DESC(param.getDEFAULT_VALUE_COL_DESC());
	    argument.setARGUMENT_SRC_DEFAULT(param.getARGUMENT_SRC_DEFAULT());
	    argument.setFLAG_VALUE_ON(param.getFLAG_VALUE_ON());
	    argument.setFLAG_VALUE_OFF(param.getFLAG_VALUE_OFF());
	    argument.setMULTISELECT_YN(param.getMULTISELECT_YN());
	    argument.setTO_SAVE_YN(param.getTO_SAVE_YN());
	    argument.setCIF_AUDIT_YN(param.getCIF_AUDIT_YN());
	    argument.setARGUMENT_DATE_VALUE(param.getARGUMENT_DATE_VALUE());
	    genericDAO.update(argument);
	    ArrayList<IRP_REP_ARG_CONSTRAINTSVO> listConstraints = fillConstrVOs(param);
	    constrVO.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO.setREPORT_ID(param.getREPORT_ID());
	    designerDAO.deleteConstraints(constrVO);
	    filterSC.setREPORT_ID(param.getREPORT_ID());
	    filterSC.setARGUMENT_ID(param.getARGUMENT_ID());
//	    designerDAO.deleteArgumentsFilters(filterSC);
	    for (int i=0;i<listConstraints.size();i++)
	    {
		genericDAO.insert(listConstraints.get(i));
	    }
	    // to insert link qry for update report args
	    for(Entry<String, List<IRP_QUERY_ARG_MAPPINGCO>> entry : reportCO.getLinkQryArsMap().entrySet())
	    {
		//String key = entry.getKey();
		  String[] arrayKey = entry.getKey().split("~");
		  String key =arrayKey[0];
		  String concatKey = entry.getKey();
		
		if(key.equals(param.getARGUMENT_NAME()))
		{
		    List<IRP_QUERY_ARG_MAPPINGCO> qryArgMaplst = new ArrayList<IRP_QUERY_ARG_MAPPINGCO>();
		    qryArgMaplst = new ArrayList(reportCO.getLinkQryArsMap().get(concatKey));

		    IRP_QUERY_ARG_MAPPINGVO irpQryArgMapForDelVO = new IRP_QUERY_ARG_MAPPINGVO();
		    irpQryArgMapForDelVO.setREPORT_ID(vo.getREPORT_ID());
		    irpQryArgMapForDelVO.setREPORT_ARGUMENT_ID(param.getARGUMENT_ID());
		    
		    if(arrayKey[1].equals("1"))
		    {
			irpQryArgMapForDelVO.setQUERY_ID(param.getQUERY_ID());
		    }
		    else
		    {
			irpQryArgMapForDelVO.setQUERY_ID(param.getQUERY_ID_DEFAULT());
		    }
		    irpQryArgMapForDelVO.setDEFAULT_SOURCE(new BigDecimal(arrayKey[1]));
		    designerDAO.deleteLinkQryArgsMap(irpQryArgMapForDelVO);
		    if(arrayKey[1].equals("1") && (new BigDecimal(3).equals(param.getARGUMENT_SOURCE()) || new BigDecimal(10).equals(param.getARGUMENT_SOURCE())) )
		    {
			for(int i = 0; i < qryArgMaplst.size(); i++)
			{

			    IRP_QUERY_ARG_MAPPINGVO irpQryArgMapVO = new IRP_QUERY_ARG_MAPPINGVO();
			    irpQryArgMapVO.setREPORT_ID(vo.getREPORT_ID());
			    irpQryArgMapVO.setREPORT_ARGUMENT_ID(param.getARGUMENT_ID());
			    irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID());
			    /*
			    if(arrayKey[1].equals("1"))
			    {
				irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID());
			    }
			    else
			    {
				irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID_DEFAULT());
			    }
			    */
			   // irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID());
			    // qryArgMaplst.get(0)
			    // for(int j=0;j<qryArgMaplst.get(i))
			    IRP_QUERY_ARG_MAPPINGCO mapQryArgVO = qryArgMaplst.get(i);
			    irpQryArgMapVO.setQUERY_ARG_NAME(mapQryArgVO.getQRY_ARG_NAME());
			    irpQryArgMapVO.setREPORT_MAPPED_ARG_NAME(mapQryArgVO.getIrpQueryArgsMappingVO()
				    .getREPORT_MAPPED_ARG_NAME());
			    irpQryArgMapVO.setSTATIC_VALUE(mapQryArgVO.getIrpQueryArgsMappingVO().getSTATIC_VALUE());
			    irpQryArgMapVO.setDEFAULT_SOURCE(new BigDecimal(arrayKey[1]));
			    //irpQryArgMapVO.setDEFAULT_SOURCE();
			    // genericDAO.delete(irpQryArgMapVO);
			    genericDAO.insert(irpQryArgMapVO);
			}
		    }
		    else if(!NumberUtil.isEmptyDecimal(param.getQUERY_ID_DEFAULT()))
		    {
			for(int i = 0; i < qryArgMaplst.size(); i++)
			{

			    IRP_QUERY_ARG_MAPPINGVO irpQryArgMapVO = new IRP_QUERY_ARG_MAPPINGVO();
			    irpQryArgMapVO.setREPORT_ID(vo.getREPORT_ID());
			    irpQryArgMapVO.setREPORT_ARGUMENT_ID(param.getARGUMENT_ID());
			    irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID_DEFAULT());
			    /*
			    if(arrayKey[1].equals("1"))
			    {
				irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID());
			    }
			    else
			    {
				irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID_DEFAULT());
			    }
			    */
			   // irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID());
			    // qryArgMaplst.get(0)
			    // for(int j=0;j<qryArgMaplst.get(i))
			    IRP_QUERY_ARG_MAPPINGCO mapQryArgVO = qryArgMaplst.get(i);
			    irpQryArgMapVO.setQUERY_ARG_NAME(mapQryArgVO.getQRY_ARG_NAME());
			    irpQryArgMapVO.setREPORT_MAPPED_ARG_NAME(mapQryArgVO.getIrpQueryArgsMappingVO()
				    .getREPORT_MAPPED_ARG_NAME());
			    irpQryArgMapVO.setSTATIC_VALUE(mapQryArgVO.getIrpQueryArgsMappingVO().getSTATIC_VALUE());
			    irpQryArgMapVO.setDEFAULT_SOURCE(new BigDecimal(arrayKey[1]));
			    //irpQryArgMapVO.setDEFAULT_SOURCE();
			    // genericDAO.delete(irpQryArgMapVO);
			    genericDAO.insert(irpQryArgMapVO);
			}
		    }

		}

	    }

	}
	BigDecimal repId = reportCO.getREPORT_ID();
	//handling for saveas
	if(!isReportCifAudit && repId!=null)
	{
	    designerDAO.updateAllCifAuditParams(reportCO.getREPORT_ID());
	}
	if(repId == null)
	{
	    repId = vo.getREPORT_ID();
	}
	// save procedures and procParams
	List<IRP_REP_PROCCO> procList = reportCO.getProceduresList();
	HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> procParamsMap = reportCO.getProcParamsMap();
	IRP_REP_PROCCO procCO;
	int procUsageCnt;
	IRP_REP_PROCSC repProcSC = null;
	String procName;
	IRP_REP_PROCCO prCO;
	BigDecimal procId;
	IRP_REP_PROCSC sc = new IRP_REP_PROCSC();
	sc.setREP_ID(repId);
	ArrayList<IRP_REP_PROC_PARAMSCO> paramsArr;
	// delete then insert

	// delete from irp_rep_proc_params
	IRP_REP_PROCSC procSC = new IRP_REP_PROCSC();
	List<BigDecimal> reportsId = new ArrayList<BigDecimal>();
	reportsId.add(repId);
	procSC.setREPORTS_ID(reportsId);
	procDAO.deleteProcParamsByReportId(procSC);
	// delete from irp_rep_proc
	procDAO.deleteProcsByReportId(procSC);
	// delete from irp_proc if the proc is not used in other report
	for(int i = 0; i < procList.size(); i++)
	{
	    procCO = procList.get(i);
	    if(procCO.getPROC_ID() != null)
	    {
		procSC = new IRP_REP_PROCSC();
		procSC.setPROCEDURE_ID(procCO.getPROC_ID());
		procUsageCnt = procDAO.checkProcUsageById(procSC);
		if(procUsageCnt == 0)
		{
		    genericDAO.delete(procBO.retProcVOFromRepProcCO(procCO));
		}
	    }
	}
	if(!procList.isEmpty())
	{
	    for(int i = 0; i < procList.size(); i++)
	    {
		procCO = procList.get(i);
		procName = procCO.getPROC_NAME();

		// check if procName exist in irp_proc then do not save it
		procSC = new IRP_REP_PROCSC();
		procSC.setPROCEDURE_NAME(procName);
		prCO = procDAO.retProcIdByProcName(procSC);

		if(prCO == null)
		{
		    procId = commonRepFuncBO.retCounterValue("IRP_PROC");
		    procCO.setPROC_ID(procId);
		    // insert into IRP_PROC
		    genericDAO.insert(procBO.retProcVOFromRepProcCO(procCO));

		}
		else
		{
		    procId = prCO.getPROC_ID();
		    procCO.setPROC_ID(procId);
		}

		// insert into IRP_REP_PROC
		genericDAO.insert(procBO.retRepProcVOFromRepProcCO(procCO, sc));

		// insert into IRP_REP_PROC_PARAMS
		paramsArr = procParamsMap.get(procName);
		
		//in case of 'save as' get the new report arguments ids (from argOrderMap) and set them in the VOs of the paramsArr
		if(reportCO.getOldRepAppName()!=null && reportCO.getOldRepProgRef()!=null)
		{
		    IRP_REP_PROC_PARAMSCO procParamCO;
			BigDecimal procParamId;
			for(int k=0;k<paramsArr.size();k++)
			{
			    procParamCO=paramsArr.get(k);
			    procParamId=procParamCO.getPARAM_ID();			  
			    if(procParamId!=null && argIdsMap.get(procParamId.longValue())==null)
			    {
				procParamCO.setPARAM_ID(oldNewArgIdsMap.get(procParamCO.getPARAM_ID()));
			    }
			}
			  
		}
		
		procBO.saveParams(paramsArr, sc, procId, repProcSC, argIdsMap);
	    }
	}

//	params = reportCO.getArgsDbDelete().values();
//	for(IRP_REP_ARGUMENTSCO param : params)
//	{
//	    // This control is added because when adding an arg. to the
//	    // argsDbDelete
//	    // we didn't check if it is a new created arg. in order to not add
//	    // it to the map
//	    if(!NumberUtil.isEmptyDecimal(param.getARGUMENT_ID()))
//	    {
//		argument.setARGUMENT_ID(param.getARGUMENT_ID());
//		argument.setREPORT_ID(param.getREPORT_ID());
//
//		// added by haytham.k to delete link qry args
//		IRP_QUERY_ARG_MAPPINGVO irpQryArgMapVO = new IRP_QUERY_ARG_MAPPINGVO();
//		irpQryArgMapVO.setQUERY_ID(param.getQUERY_ID());
//		irpQryArgMapVO.setREPORT_ID(param.getREPORT_ID());
//		irpQryArgMapVO.setREPORT_ARGUMENT_ID(param.getARGUMENT_ID());
//		designerDAO.deleteLinkQryArgsMap(irpQryArgMapVO);
//		genericDAO.delete(argument);
//	    }
//	}

	// save sub report
	if(reportCO.getSubreportsList().size() > 0)
	{
	    for(int i = 0; i < reportCO.getSubreportsList().size(); i++)
	    {
		reportCO.getSubreportsList().get(i).setPARENT_REPORT_ID(vo.getREPORT_ID());
	    }
	}
	designerDAO.saveSubRep(reportCO);

	// save client report add if

	    IRP_CLIENT_REPORTVO irpClientReportVO = new IRP_CLIENT_REPORTVO();
	    irpClientReportVO.setAPP_NAME(reportCO.getAPP_NAME());
	    irpClientReportVO.setREPORT_REF(reportCO.getPROG_REF());
	    designerDAO.deleteRepClientLst(irpClientReportVO);
	    for(int i = 0; i < reportCO.getRepClientList().size(); i++)
	    {
		reportCO.getRepClientList().get(i).getIrpClientReportVO().setAPP_NAME(reportCO.getAPP_NAME());
		reportCO.getRepClientList().get(i).getIrpClientReportVO().setREPORT_REF(reportCO.getPROG_REF());
		genericDAO.insert(reportCO.getRepClientList().get(i).getIrpClientReportVO());
	    }

	// save hash Table
	if(reportCO.getHashTblList().size() > 0)
	{
	    for(int i = 0; i < reportCO.getHashTblList().size(); i++)
	    {
		if(!NumberUtil.isEmptyDecimal(reportCO.getHashTblList().get(i).getIrpHashTableVO().getHASH_TABLE_ID()))
		{
		    reportCO.getHashTblList().get(i).setREPORT_ID(vo.getREPORT_ID());
		}
	    }
	}
	if(commonLibBO.returnIsSybase() == 1)
	{
	    if(reportCO.getHashTblList().size()==0) //added by haytham.k to avoid null value for report id when deleting and saving hash-table for new report  
	    {
		reportCO.setREPORT_ID(vo.getREPORT_ID());
	    }
	    designerDAO.saveHashTbl(reportCO);
	    
	}

	// add audit
	AuditRefCO refCO = reportCO.getAuditRefCO();
	if(refCO != null)
	{
	    if(refCO.getOperationType().equals(AuditConstant.CREATED))
	    {
		reportCO.setREPORT_ID(vo.getREPORT_ID());
		auditBO.callAudit(null, reportCO, refCO);
		reportCO.setREPORT_ID(null);
	    }
	    else if(refCO.getOperationType().equals(AuditConstant.UPDATE))
	    {
		if(reportCO.isFromUpDown() && reportCO.getAuditObj() != null)
		{
		    IRP_AD_HOC_REPORTCO oldCO = (IRP_AD_HOC_REPORTCO) reportCO.getAuditObj();
		    IRP_AD_HOC_REPORTVO oldRepVO = new IRP_AD_HOC_REPORTVO();
		    oldRepVO.setREPORT_NAME(oldCO.getREPORT_NAME());
		    oldRepVO.setPROG_REF(oldCO.getPARENT_REF());
		    oldRepVO.setREPORT_ID(oldCO.getREPORT_ID());
		    oldRepVO.setCONN_ID(oldCO.getCONN_ID());
		    oldRepVO.setDEFAULT_FORMAT(oldCO.getDEFAULT_FORMAT());
		    oldRepVO.setSHOW_HEAD_FOOT(oldCO.getSHOW_HEAD_FOOT());
		    oldRepVO.setLANG_INDEPENDENT_YN((oldCO.getLANG_INDEPENDENT_YN()));

		    oldRepVO.setCSV_SEPARATOR(oldCO.getCSV_SEPARATOR());
		    
		    IRP_AD_HOC_REPORTVO currRepVO = new IRP_AD_HOC_REPORTVO();
		    currRepVO.setREPORT_NAME(reportCO.getREPORT_NAME());
		    currRepVO.setPROG_REF(reportCO.getPARENT_REF());
		    currRepVO.setREPORT_ID(reportCO.getREPORT_ID());
		    currRepVO.setCONN_ID(reportCO.getCONN_ID());
		    currRepVO.setDEFAULT_FORMAT(reportCO.getDEFAULT_FORMAT());
		    currRepVO.setSHOW_HEAD_FOOT(reportCO.getSHOW_HEAD_FOOT());
		    currRepVO.setLANG_INDEPENDENT_YN((reportCO.getLANG_INDEPENDENT_YN()));
		    currRepVO.setCIF_AUDIT_YN(reportCO.getCIF_AUDIT_YN());
		    currRepVO.setCSV_SEPARATOR(reportCO.getCSV_SEPARATOR());

		    auditBO.callAudit(oldRepVO, currRepVO, refCO);
		    
		    FTR_EXP_XLSVO oldFtrExpXlsVO = new FTR_EXP_XLSVO();
		    oldFtrExpXlsVO.setXLS_PATH(oldCO.getXlsName());
		    
		    FTR_EXP_XLSVO newFtrExpXlsVO = new FTR_EXP_XLSVO();
		    newFtrExpXlsVO.setXLS_PATH(reportCO.getXlsName());
		    
		    auditBO.callAudit(oldFtrExpXlsVO, newFtrExpXlsVO, refCO);
		}
		else
		{
		    auditBO.callAudit(reportCO, reportCO, refCO);
		}
		auditBO.insertAudit(refCO);
	    }
	}

	// set the new date_updated in the reportCO in case the user will save
	// the report more than one time without reopening it
	IRP_AD_HOC_REPORTCO newRepCO = findReportById(vo.getREPORT_ID());
	reportCO.setDATE_UPDATED(newRepCO.getDATE_UPDATED());
	return reportCO;
    }

    public void addMissingOpts (IRP_AD_HOC_REPORTCO reportCO,boolean updateRep) throws BaseException
    {
	    String pRef = reportCO.getPROG_REF();
	    List<String> allOpts = new ArrayList<String>();
	    addExtensions(allOpts, pRef);
	    FTR_OPTCO optCO = new FTR_OPTCO();
	    optCO.setPROG_REFS(allOpts);
	    optCO.getFtrOptVO().setAPP_NAME(reportCO.getAPP_NAME());
	    optCO.getFtrOptVO().setPROG_REF(reportCO.getPROG_REF());
	    //below function returns all existing opts in db
	    List<FTR_OPTCO> savedOptsCO = fcrBO.retSavedOptsByProgRef(optCO);

	    OPTVO optVO = new OPTVO();
	    optVO.setAPP_NAME(reportCO.getAPP_NAME());
	    optVO.setPROG_REF(reportCO.getPROG_REF());
	    optVO = (OPTVO) genericDAO.selectByPK(optVO);
	    
	    if(optVO!=null && (StringUtil.nullToEmpty(optVO.getPARENT_REF()).isEmpty() || StringUtil.nullToEmpty(optVO.getDISP_ORDER()).isEmpty()) ) {
	    	if(StringUtil.nullToEmpty(optVO.getPARENT_REF()).isEmpty()) {
	    		optVO.setPARENT_REF(reportCO.getPARENT_REF());
	    	}
	    	if(StringUtil.nullToEmpty(optVO.getDISP_ORDER()).isEmpty()) {
	    		optVO.setDISP_ORDER(new BigDecimal(7));
	    	}
	    	genericDAO.update(optVO);
	    }
	    
	    String optDesc = reportCO.getREPORT_NAME();
	    FTR_OPTCO retOptCO;
	    List<String> savedOpts = new ArrayList<String>();
	    for(int i = 0; i < savedOptsCO.size(); i++)
	    {
		retOptCO = savedOptsCO.get(i);
		optDesc = retOptCO.getFtrOptVO().getBRIEF_NAME_ENG();
		savedOpts.add(retOptCO.getFtrOptVO().getPROG_REF());
	    }
	    //saveOpts contains now all prog_refs in db
	    List<String> missedOpts = new ArrayList<String>();
	    String currOpt;
	    for(int i = 0; i < allOpts.size(); i++)
	    {
		currOpt = allOpts.get(i);
		if(!savedOpts.contains(currOpt))
		{
		    missedOpts.add(currOpt);
		}
	    }
	    //missedOpts will contain the missing opts for a given prog_ref
	    if(!missedOpts.isEmpty())
	    {
		fcrBO.saveMissedOpts(reportCO, missedOpts, optDesc);
	    }
  
	    if(!updateRep) // To add opt_extended for reports with existing opt 
	    {
		OPT_EXTENDEDVO optExtendedVO = new OPT_EXTENDEDVO();
		optExtendedVO.setAPP_NAME(reportCO.getAPP_NAME());
		optExtendedVO.setPROG_REF(reportCO.getPROG_REF());
		optExtendedVO.setOPT_URL("REPORT");
		if( ((OPT_EXTENDEDVO)genericDAO.selectByPK(optExtendedVO)) == null )
		{
		    genericDAO.insert(optExtendedVO);
		}
	    }
    }
    
    public ArrayList<IRP_REP_ARG_CONSTRAINTSVO> fillConstrVOs(IRP_REP_ARGUMENTSCO param) throws BaseException
    {
	IRP_REP_ARG_CONSTRAINTSVO constrVO = new IRP_REP_ARG_CONSTRAINTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO1 = new IRP_REP_ARG_CONSTRAINTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO2 = new IRP_REP_ARG_CONSTRAINTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO3 = new IRP_REP_ARG_CONSTRAINTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO4 = new IRP_REP_ARG_CONSTRAINTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO5 = new IRP_REP_ARG_CONSTRAINTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO6 = new IRP_REP_ARG_CONSTRAINTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO7 = new IRP_REP_ARG_CONSTRAINTSVO();
	IRP_REP_ARG_CONSTRAINTSVO constrVO8 = new IRP_REP_ARG_CONSTRAINTSVO();
	ArrayList<IRP_REP_ARG_CONSTRAINTSVO> listConstraints = new ArrayList<IRP_REP_ARG_CONSTRAINTSVO>();
	IRP_REP_ARG_CONSTRAINTSCO constrCO = param.getIrpRepArgConstraintCO();

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getCASE_SENSITIVITY())))
	{
	    constrVO.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO.setREPORT_ID(param.getREPORT_ID());
	    constrVO.setVALUE(constrCO.getCASE_SENSITIVITY());
	    constrVO.setVALUE_CODE("1");
	    listConstraints.add(constrVO);
	}

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getCONDITION())))
	{
	    constrVO1.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO1.setREPORT_ID(param.getREPORT_ID());
	    constrVO1.setVALUE(constrCO.getCONDITION());
	    constrVO1.setVALUE_CODE("6");
	    listConstraints.add(constrVO1);
	}

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getFORMAT())))
	{
	    constrVO2.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO2.setREPORT_ID(param.getREPORT_ID());
	    constrVO2.setVALUE(constrCO.getFORMAT());
	    constrVO2.setVALUE_CODE("2");
	    listConstraints.add(constrVO2);
	}

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getHIDE_EXPR())))
	{
	    constrVO3.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO3.setREPORT_ID(param.getREPORT_ID());
	    constrVO3.setVALUE(constrCO.getHIDE_EXPR());
	    constrVO3.setVALUE_CODE("8");
	    listConstraints.add(constrVO3);
	}

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getMAX_LENGTH())))
	{
	    constrVO4.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO4.setREPORT_ID(param.getREPORT_ID());
	    constrVO4.setVALUE(constrCO.getMAX_LENGTH());
	    constrVO4.setVALUE_CODE("3");
	    listConstraints.add(constrVO4);
	}

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getMAX_VAL())))
	{
	    constrVO5.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO5.setREPORT_ID(param.getREPORT_ID());
	    constrVO5.setVALUE(constrCO.getMAX_VAL());
	    constrVO5.setVALUE_CODE("4");
	    listConstraints.add(constrVO5);
	}

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getMIN_VAL())))
	{
	    constrVO6.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO6.setREPORT_ID(param.getREPORT_ID());
	    constrVO6.setVALUE(constrCO.getMIN_VAL());
	    constrVO6.setVALUE_CODE("5");
	    listConstraints.add(constrVO6);
	}

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getSHOW_EXPR())))
	{
	    constrVO7.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO7.setREPORT_ID(param.getREPORT_ID());
	    constrVO7.setVALUE(constrCO.getSHOW_EXPR());
	    constrVO7.setVALUE_CODE("7");
	    listConstraints.add(constrVO7);
	}

	if(!"".equals(StringUtil.nullToEmpty(constrCO.getBTR_CONTROL_DISP())))
	{
	    constrVO8.setARGUMENT_ID(param.getARGUMENT_ID());
	    constrVO8.setREPORT_ID(param.getREPORT_ID());
	    constrVO8.setVALUE(constrCO.getBTR_CONTROL_DISP());
	    constrVO8.setVALUE_CODE("9");
	    listConstraints.add(constrVO8);
	}
	return listConstraints;
    }
    
    @Override
    public int getReportsCount(IRP_AD_HOC_REPORTSC sc) throws BaseException
    {
	return designerDAO.getReportsCount(sc);
    }

    @Override
    public List<IRP_AD_HOC_REPORTCO> getReportsList(IRP_AD_HOC_REPORTSC sc) throws BaseException
    {
	return designerDAO.getReportsList(sc);
    }

    
    public void deleteReport(List<BigDecimal> reprotsId, AXSCO axsCO, AuditRefCO refCO, List<BigDecimal> oldReportsId,
	    List<String> appsName, List<String> progRefList, boolean useExistingOptAccess,String userName,String pageRef) throws BaseException
    {
	deleteReport(reprotsId, axsCO, refCO, oldReportsId, appsName, progRefList, useExistingOptAccess,userName,pageRef, true);
    }
    
    private void deleteReport(List<BigDecimal> reprotsId, AXSCO axsCO, AuditRefCO refCO, List<BigDecimal> oldReportsId,
	    List<String> appsName, List<String> progRefList,boolean useExistingOptAccess,String userName,String pageRef ,boolean deleteTrans) throws BaseException
    {
	int progRefIndex = 0;
	int appsNameIndex = 0;
	String appName;
	String progRef;
	queryBO.deleteQueries(reprotsId);
	// delete from irp_rep_proc_params
	IRP_REP_PROCSC procSC = new IRP_REP_PROCSC();
	procSC.setREPORTS_ID(reprotsId);
	procDAO.deleteProcParamsByReportId(procSC);
	// delete from irp_rep_proc
	procDAO.deleteProcsByReportId(procSC);

	designerDAO.deleteReportHyperlinks(reprotsId);
	//delete linked qry from irp_query_arg_mapping
	designerDAO.deleteLinkQuery(reprotsId);
	//deleting the arguments constraints
	designerDAO.deleteConstraintsAsList(reprotsId);
	IRP_REP_FILTERSSC filterSC = new IRP_REP_FILTERSSC();
	filterSC.setReportsIdList(reprotsId);
	filterSC.setIsSybase(commonLibBO.returnIsSybase());
	designerDAO.deleteArgumentsFilters(filterSC);
	// delete from schedule
	schedulerBO.deleteReportsSchedules(reprotsId);
	
	//delete arguements
	designerDAO.deleteArguments(reprotsId);
	
	//delete sub report from irp_sub_report
	designerDAO.deleteSubReport(reprotsId);
	//delete report hash table
	if(commonLibBO.returnIsSybase() == 1)
	{
	    designerDAO.deleteRepHashTable(reprotsId);
	}
	//delete report client and translations
	TranslationSC transSC;
	IRP_CLIENT_REPORTVO irpCltRep;
	while(appsNameIndex < progRefList.size() / 8)
	{
	    appName=appsName.get(appsNameIndex);
	    progRef=progRefList.get(progRefIndex);
	    irpCltRep = new IRP_CLIENT_REPORTVO();
	    irpCltRep.setAPP_NAME(appName);
	    irpCltRep.setREPORT_REF(progRef);
	    designerDAO.deleteRepClientLst(irpCltRep);
	    
	    if(deleteTrans)
	    {
		transSC = new TranslationSC();
		transSC.setAppName(appName);
		transSC.setPageRef(progRef);
		translationBO.delByProgRef(transSC);
	    }
	    progRefIndex = progRefIndex + 8;
	    appsNameIndex++;
	}
	designerDAO.deleteReport(reprotsId);

	// delete from REP_INFO and REP_CATALOGUE
	if(oldReportsId != null && !oldReportsId.isEmpty())
	{
	    designerDAO.deleteRepCatalogue(oldReportsId);
	    designerDAO.deleteRepInfo(oldReportsId);
	}
	
	 //delete from FTR_EXP_XLS
	FTR_EXP_XLSVO ftrExpXlsVO = new FTR_EXP_XLSVO();
	ftrExpXlsVO.setCOMP_CODE(axsCO.getCOMP_CODE());
	ftrExpXlsVO.setREP_REF(progRefList.get(0));
	genericDAO.delete(ftrExpXlsVO);	

	if(!useExistingOptAccess)
	{
	    List<String> progRefs;
	    FTR_OPTCO fcrCO;
	    for(int i = 0; i < appsName.size(); i++)
	    {
		appName = appsName.get(i);
		progRefs = new ArrayList<String>();

		progRefs.add(progRefList.get((i * 8) + 0));
		progRefs.add(progRefList.get((i * 8) + 1));
		progRefs.add(progRefList.get((i * 8) + 2));
		progRefs.add(progRefList.get((i * 8) + 3));
		progRefs.add(progRefList.get((i * 8) + 4));
		progRefs.add(progRefList.get((i * 8) + 5));
		progRefs.add(progRefList.get((i * 8) + 6));
		progRefs.add(progRefList.get((i * 8) + 7));
		fcrCO = new FTR_OPTCO();
		// delete from opt extended
		fcrCO.setPROG_REFS(progRefs);
		fcrCO.getFtrOptVO().setAPP_NAME(appName);
		fcrCO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,refCO==null?axsCO.getHttpSessionIdForLink():refCO.getHttpSessionIdForLink());
		fcrBO.deleteOptExtended(fcrCO);
		// delete Axs
		axsCO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,refCO==null?axsCO.getHttpSessionIdForLink():refCO.getHttpSessionIdForLink());
		fcrBO.deleteAxs(fillAxsCOProps(axsCO.getUSER_ID(), appName, progRefs, axsCO.getCOMP_CODE(), axsCO
			.getBRANCH_CODE()));
		//Delete from s_role_detail since we have foreign key to OPT table under specific DBs
		fcrBO.deleteSRoleDetail(fcrCO);
		// delete from opt
		axsCO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,refCO==null?axsCO.getHttpSessionIdForLink():refCO.getHttpSessionIdForLink());
		fcrBO.deleteOpts(fcrCO);

	    }
	}
	// add Audit
	if(refCO != null)
	{
	    IRP_AD_HOC_REPORTVO repVO;
	    for(int i = 0; i < reprotsId.size(); i++)
	    {
		repVO = new IRP_AD_HOC_REPORTVO();
		repVO.setREPORT_ID(reprotsId.get(i));
		auditBO.callAudit(repVO, repVO, refCO);
	    }
	    auditBO.insertAudit(refCO);
	}
    }
    
    @Override
    public boolean[] validateReportInfo(IRP_AD_HOC_REPORTSC reportSC) throws BaseException
    {
	return designerDAO.validateReportInfo(reportSC);
    }
    
    public List<IRP_QUERY_ARG_MAPPINGCO> fillLinkQryArgMapping(IRP_AD_HOC_QUERYCO queryCO,List<IRP_QUERY_ARG_MAPPINGCO> qryArgMapLst, BigDecimal dfltSource)
    {
	LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> arguments;
	List<IRP_QUERY_ARG_MAPPINGCO> qryArgMapLstForGrid = new ArrayList<IRP_QUERY_ARG_MAPPINGCO>();
	arguments = queryCO.getSqlObject().getArgumentsList();
	ArrayList<IRP_REP_ARGUMENTSCO> lst = new ArrayList(arguments.values());
	if(arguments.values().size() > 0)
	{
	    for(int j = 0; j < lst.size(); j++)
	    {
		for(int k = 0; k < qryArgMapLst.size(); k++)
		{
		    if(qryArgMapLst.get(k).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME().equals(
			    lst.get(j).getARGUMENT_NAME()))
		    {

			IRP_QUERY_ARG_MAPPINGCO irpQryMapCO = new IRP_QUERY_ARG_MAPPINGCO();
			irpQryMapCO.setQRY_ARG_NAME(lst.get(j).getARGUMENT_NAME());
			irpQryMapCO.setQRY_ARG_TYPE(lst.get(j).getARGUMENT_TYPE());
			if((qryArgMapLst.get(k).getIrpQueryArgsMappingVO().getSTATIC_VALUE()) == null
				|| ("").equals(qryArgMapLst.get(k).getIrpQueryArgsMappingVO()
					.getSTATIC_VALUE()))
			{
			    irpQryMapCO.setMAP_PARAM_TYPE("2");
			}
			else
			{
			    irpQryMapCO.setMAP_PARAM_TYPE("0");
			}
			irpQryMapCO.setIrpQueryArgsMappingVO(qryArgMapLst.get(k)
				.getIrpQueryArgsMappingVO());
			qryArgMapLstForGrid.add(irpQryMapCO);

		    }
		}
	    }
	}

	// this boucle is to add an arg if the user change the
	// arg in query
	for(int m = 0; m < lst.size(); m++)
	{
	    boolean qryArgExist = false;
	    for(int n = 0; n < qryArgMapLst.size(); n++)
	    {
		if(lst.get(m).getARGUMENT_NAME().equals(
			qryArgMapLst.get(n).getIrpQueryArgsMappingVO().getQUERY_ARG_NAME()))
		{
		    qryArgExist = true;
		    break;
		}
	    }
	    if(!qryArgExist)
	    {
		IRP_QUERY_ARG_MAPPINGCO irpQryMapCO = new IRP_QUERY_ARG_MAPPINGCO();
		irpQryMapCO.setQRY_ARG_NAME(lst.get(m).getARGUMENT_NAME());
		irpQryMapCO.setQRY_ARG_TYPE(lst.get(m).getARGUMENT_TYPE());
		qryArgMapLstForGrid.add(irpQryMapCO);
	    }
	}

	return qryArgMapLstForGrid;
    }

    // return subreports
   // List<IRP_SUB_REPORTCO> subrepList = designerDAO.returnSubreports(reportId);
    public List<IRP_SUB_REPORTCO> returnSubreports(BigDecimal reportId) throws BaseException
    {
	return designerDAO.returnSubreports(reportId);
    }
    
    public IRP_AD_HOC_REPORTCO returnReportById(BigDecimal reportId) throws BaseException, Exception
    {
	try
	{
	    IRP_AD_HOC_REPORTCO report = findReportById(reportId);//designerDAO.findReportById(reportId);
	    List<IRP_AD_HOC_QUERYCO> queriesList = queryBO.queriesByReportId(reportId);
	    // read variables from jrxml and add it to each query
	    byte[] jrxmlFILE = report.getJRXML_FILE();
	    
	    String repositoryPath = "";
	    List<ImageCO> imagesList = new ArrayList<ImageCO>();
	    
	    Document doc = null;
	    DocumentBuilder parser = DocumentBuilderFactory.newInstance().newDocumentBuilder();
	    JasperDesign jasperDesign = null;
	    // apply random and date/time for simultaneous access
	    Random randomGenerator = new Random();
	    int randomNbr = randomGenerator.nextInt(100000);
	    Date dte = new Date();
	    String fileName = "/temp" + "-" + randomNbr + "-" + DateUtil.format(dte, "dd-MM-yyyy-hh-mm-ss") + ".jrxml";

	    if(jrxmlFILE != null && queriesList != null && !queriesList.isEmpty())
	    {
		String xml = new String(jrxmlFILE, CommonUtil.ENCODING);
		repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
		FileOutputStream fos = new FileOutputStream(repositoryPath + fileName);
		Writer out = new OutputStreamWriter(fos,FileUtil.DEFAULT_FILE_ENCODING);
		try
		{
		    out.write(xml);
		}
		finally
		{
		    out.close();
		    fos.close();
		}
		File file = new PathFileSecure(repositoryPath + fileName);
		jasperDesign = JRXmlLoader.load(file);
		doc = parser.parse(file);
		boolean isDel = file.delete();
		if(!isDel)
		{
		    log.debug("The following file has not been deleted  :" + fileName);
		}
		XPath xpath = XPathFactory.newInstance().newXPath();
		String expression = "";
		String variableExpression = "";
		NodeList nodes = null;
		List<VARIABLE_OBJECT> variablesList;
		VARIABLE_OBJECT variable;
		for(int i = 0; i < queriesList.size(); i++)
		{
		    IRP_AD_HOC_QUERYCO query = queriesList.get(i);
		    variablesList = new ArrayList<VARIABLE_OBJECT>();
		    expression = "//subDataset[@name=\"" + query.getQUERY_NAME() + "\"]//variable";
		    nodes = (NodeList) xpath.evaluate(expression, doc, XPathConstants.NODESET);
		    if(nodes != null && nodes.getLength() > 0)
		    {
			for(int j = 0; j < nodes.getLength(); j++)
			{
			    Element element = (Element) nodes.item(j);
			    variable = new VARIABLE_OBJECT();
			    variable.setVarName(element.getAttribute("name"));
			    variable.setVarClass(element.getAttribute("class"));
			    variable.setCalculation(element.getAttribute("calculation"));
			    variableExpression = (element.getElementsByTagName("variableExpression").item(0))
				    .getChildNodes().item(0).getNodeValue();
			    variableExpression = variableExpression.substring(3, variableExpression.length() - 1);
			    variable.setExpression(variableExpression);
			    variable.setQryIndex(i);
			    variablesList.add(variable);
			}

		    }
		    query.setVariablesList(variablesList);
		}
	    }

	    report.setQueriesList(queriesList);

	    // return arguments
	    List<IRP_REP_ARGUMENTSCO> argsList = designerDAO.argsByReportId(reportId);

	    Long index;
	    IRP_REP_ARGUMENTSCO arg;
	    for(int i = 0; i < argsList.size(); i++)
	    {
		arg = argsList.get(i);
		index = generateId() + i;
		arg.setIndex(index);
		// List<IRP_QUERY_ARG_MAPPINGCO> qryArgMaplst=new
		// ArrayList<IRP_QUERY_ARG_MAPPINGCO>();
		if(!NumberUtil.isEmptyDecimal(arg.getQUERY_ID()) || !NumberUtil.isEmptyDecimal(arg.getQUERY_ID_DEFAULT()))
		{
		    IRP_QUERY_ARG_MAPPINGCO irpQryArgMapCO = new IRP_QUERY_ARG_MAPPINGCO();
		    irpQryArgMapCO.getIrpQueryArgsMappingVO().setREPORT_ID(reportId);
		    irpQryArgMapCO.getIrpQueryArgsMappingVO().setREPORT_ARGUMENT_ID(arg.getARGUMENT_ID());
		    irpQryArgMapCO.getIrpQueryArgsMappingVO().setQUERY_ID(arg.getQUERY_ID());
		    List<IRP_QUERY_ARG_MAPPINGCO> qryArgMapLst = designerDAO.returnQryArgMapLst(irpQryArgMapCO);
		    List<IRP_QUERY_ARG_MAPPINGCO> qryArgMapLstForSrc=new ArrayList<IRP_QUERY_ARG_MAPPINGCO>();
		    List<IRP_QUERY_ARG_MAPPINGCO> qryArgMapLstForDflt=new ArrayList<IRP_QUERY_ARG_MAPPINGCO>();
		    IRP_AD_HOC_QUERYCO queryCO =null;
		    IRP_AD_HOC_QUERYCO queryDfltValCO =null;
		    if(!qryArgMapLst.isEmpty())
		    {
			BigDecimal qrySrcId=null;
			BigDecimal qryDfltValId=null;
			for(int q=0;q<qryArgMapLst.size();q++)
			{
			    if(qryArgMapLst.get(q).getIrpQueryArgsMappingVO().getDEFAULT_SOURCE().equals(BigDecimal.ONE))
			    {
				qrySrcId = qryArgMapLst.get(q).getIrpQueryArgsMappingVO().getQUERY_ID();
			    }
			    if(qryArgMapLst.get(q).getIrpQueryArgsMappingVO().getDEFAULT_SOURCE().equals(new BigDecimal(2)))
			    {
				qryDfltValId = qryArgMapLst.get(q).getIrpQueryArgsMappingVO().getQUERY_ID();
			    }
			}
		
			if(!NumberUtil.isEmptyDecimal(qrySrcId))
			{
			    irpQryArgMapCO.getIrpQueryArgsMappingVO().setQUERY_ID(qrySrcId);
			    irpQryArgMapCO.getIrpQueryArgsMappingVO().setDEFAULT_SOURCE(BigDecimal.ONE);
			    qryArgMapLstForSrc = designerDAO.returnQryArgMapLst(irpQryArgMapCO);
			    queryCO = queryBO.returnQueryById(qrySrcId, false);
			    List<IRP_QUERY_ARG_MAPPINGCO> qryArgMapLstForGrid = fillLinkQryArgMapping(queryCO,qryArgMapLstForSrc,BigDecimal.ONE);
			    report.getLinkQryArsMap().put(arg.getARGUMENT_NAME()+"~1", qryArgMapLstForGrid);
			}
			if(!NumberUtil.isEmptyDecimal(qryDfltValId))
			{
			    irpQryArgMapCO.getIrpQueryArgsMappingVO().setQUERY_ID(qryDfltValId);
			    irpQryArgMapCO.getIrpQueryArgsMappingVO().setDEFAULT_SOURCE(new BigDecimal(2));
			    qryArgMapLstForDflt = designerDAO.returnQryArgMapLst(irpQryArgMapCO);
			    queryDfltValCO = queryBO.returnQueryById(qryDfltValId, false);
			    List<IRP_QUERY_ARG_MAPPINGCO> qryArgMapLstForGrid = fillLinkQryArgMapping(queryDfltValCO,qryArgMapLstForDflt,new BigDecimal(2));
			    report.getLinkQryArsMap().put(arg.getARGUMENT_NAME()+"~2", qryArgMapLstForGrid);
			}

		    }
		}
		
		report.getArgumentsList().put(index, arg);
	    }

	    // return subreports
	    List<IRP_SUB_REPORTCO> subrepList = designerDAO.returnSubreports(reportId);
	    report.setSubreportsList(subrepList);
	    
	    // return client report
	    IRP_CLIENT_REPORTSC irpCltRepSC = new IRP_CLIENT_REPORTSC();
	    irpCltRepSC.setREPORT_REF(report.getPROG_REF());
	    irpCltRepSC.setAPP_NAME(report.getAPP_NAME());
	    List<IRP_CLIENT_REPORTCO> clientRep = designerDAO.returnClientReport(irpCltRepSC);
	    report.setRepClientList(clientRep);
	    
	    // subrepList.get(0).getSubRepArgsList().add(e)
	    List<IRP_REP_ARGUMENTSCO> lst = new ArrayList<IRP_REP_ARGUMENTSCO>();
	    // fill the hm of sub rep link param

	    IRP_SUB_REPORTSC subrepSC = new IRP_SUB_REPORTSC();
	    // List<IRP_REP_ARGUMENTSCO> getSubRepParamsList(IRP_SUB_REPORTSC
	    // subrepSC);
	    for(int s = 0; s < subrepList.size(); s++)
	    {
		subrepSC.setSUB_REPORT_ID(subrepList.get(s).getIrpSubReportVO().getREPORT_ID());
		subrepSC.setIsGrid(false);
		lst = designerDAO.getSubRepParamsList(subrepSC);
		subrepList.get(s).getSubRepArgsList().addAll(lst);
	    }
	    // hypParamsMap.put(getSubRepId()+"_"+getSubRepExp(),gu.getLstAllRec());

	    // return hashTable
	    IRP_HASH_TABLESC irpHashTblSC = new IRP_HASH_TABLESC();
	    irpHashTblSC.setREPORT_ID(reportId);
	    if(commonLibBO.returnIsSybase() == 1)
	    {
		List<IRP_HASH_TABLECO> hashTblList = designerDAO.retHashTablGrid(irpHashTblSC);
		report.setHashTblList(hashTblList);
	    }

	    if(jasperDesign != null)
	    {
		JRBand[] allBandList = jasperDesign.getAllBands();
		List childLstBand;
		Object childBand;
		Component comp;
		JRDesignComponentElement compElem;
		StandardTable tbl;
		List columns;
		StandardColumn col;
		DesignCell head;
		DesignCell foot;
		DesignCell det;
		JRElement[] headElems;
		JRElement[] footElems;
		JRElement[] detElems;
		
		for(int b =0;b<allBandList.length;b++)
		{
		    childLstBand = allBandList[b].getChildren();
		    for(int c = 0; c < childLstBand.size(); c++)
		    {
			childBand = childLstBand.get(c);

			if(childBand instanceof JRDesignSubreport)
			{
			    JRDesignSubreport zSubRep;
			    JRDesignSubreportParameter subRepParam;
			    zSubRep = (JRDesignSubreport) childBand;
			    Map<String, JRSubreportParameter> hmSubRepParam = zSubRep.getParametersMap();
			    JRExpressionChunk[] exprList;
			    exprList = zSubRep.getExpression().getChunks();
			    String expr = exprList[1].getText().substring(4, exprList[1].getText().length() - 8);
			    Iterator it = hmSubRepParam.keySet().iterator();
			    String type = "";
			    String colName;
			    for(int l = 0; l < subrepList.size(); l++)
			    {

				if(expr.equals(subrepList.get(l).getIrpSubReportVO().getSUB_REPORT_EXPRESSION()))
				{
				    // adding sub report width to be used in
				    // mirror layout
				    subrepList.get(l).setSubRepWidth(zSubRep.getWidth());
				    subrepList.get(l).setSubRepHeight(zSubRep.getHeight());
				    while(it.hasNext())
				    {
					subRepParam = (JRDesignSubreportParameter) hmSubRepParam.get(it.next());
					String subreportParameterExpression = subRepParam.getExpression().getText();
					String[] subRepNameSplitArr = subreportParameterExpression.split("$");
					String subRepNameSplit = subRepNameSplitArr[0];
					if(subreportParameterExpression.charAt(0)=='$' && subRepNameSplit.length() > 4)
					{
					    type = subRepNameSplit.substring(1, 2);
					    colName = subRepNameSplit.substring(3, subRepNameSplit.length() - 1);
					}
					else {
					    colName = subRepNameSplit;
					}
					
					for(int a = 0; a < subrepList.get(l).getSubRepArgsList().size(); a++)// 0
					// to
					// k
					{
					    if(subRepParam.getName().equals(
						    subrepList.get(l).getSubRepArgsList().get(a).getARGUMENT_NAME()))
					    {

						if(RepConstantsCommon.TYPE_FIELD.equals(type))
						{
						    subrepList.get(l).getSubRepArgsList().get(a).setVALUE_COLUMN(
							    colName);
						    subrepList.get(l).getSubRepArgsList().get(a).setMAP_PARAM_TYPE("1");

						}
						else if(RepConstantsCommon.TYPE_PARAMETER.equals(type))
						{
						    subrepList.get(l).getSubRepArgsList().get(a).setVALUE_ARGUMENT(
							    colName);
						    subrepList.get(l).getSubRepArgsList().get(a).setMAP_PARAM_TYPE("2");

						}
						else
						{
						    
						    if(subreportParameterExpression.contains("new BigDecimal"))
						    {
							String[] strArray = subreportParameterExpression.split("BigDecimal");
							String str = strArray[1];
							colName = str.substring(1, str.length() - 1);
						    }
						    else
						    {
							subreportParameterExpression = subreportParameterExpression.replaceAll("new String\\(\"", "");
							subreportParameterExpression = subreportParameterExpression.replaceAll("new Date\\(\"", "");
							subreportParameterExpression = subreportParameterExpression.replaceAll("\"\\)", "");
							subreportParameterExpression = subreportParameterExpression.replaceAll("\"\\(", "");
							colName=subreportParameterExpression;
						    }
						    if(colName.contains("\""))
						    {
							colName = colName.substring(1, colName.length() - 1);
						    }
						    subrepList.get(l).getSubRepArgsList().get(a).setVALUE_STATIC(colName);
						    subrepList.get(l).getSubRepArgsList().get(a).setMAP_PARAM_TYPE("0");
						}
					    }
					}

				    }
				}
			    }
			}

			if(childBand instanceof JRDesignImage)
			{
			    if(!((JRDesignImage) childBand).getPropertiesMap().containsProperty("blobImg"))
			    {
				// construct an images list from jrxml
				imagesList = retImgListFromJrxml(childBand, imagesList);
			    }

			}

			else if(childBand instanceof JRDesignComponentElement)
			{

			    compElem = (JRDesignComponentElement) childBand;
			    comp = compElem.getComponent();
			    if(comp instanceof StandardTable)
			    {
				tbl = (StandardTable) comp;
				columns = tbl.getColumns();
				for(int k = 0; k < columns.size(); k++)
				{
				    col = (StandardColumn) columns.get(k);
				    head = (DesignCell) col.getColumnHeader();
				    foot = (DesignCell) col.getTableFooter();
				    det = (DesignCell) col.getDetailCell();

				    if(head != null)
				    {
					headElems = head.getElements();
					if(headElems != null)
					{
					    for(int i = 0; i < headElems.length; i++)
					    {
						if(headElems[i] instanceof JRDesignImage && !(headElems[i].getPropertiesMap().containsProperty("blobImg")) )
						{
							// construct an images
							// list from jrxml
							imagesList = retImgListFromJrxml(headElems[i], imagesList);
						}
					    }
					}
				    }

				    if(foot != null)
				    {
					footElems = foot.getElements();
					if(footElems != null)
					{
					    for(int i = 0; i < footElems.length; i++)
					    {
						if(footElems[i] instanceof JRDesignImage && !(footElems[i].getPropertiesMap().containsProperty("blobImg")) )
						{
							// construct an images
							// list from jrxml
							imagesList = retImgListFromJrxml(footElems[i], imagesList);
						}
					    }
					}
				    }
				    detElems = det.getElements();
				    if(detElems != null)
				    {
					for(int i = 0; i < detElems.length; i++)
					{
					    if(detElems[i] instanceof JRDesignImage && !(detElems[i].getPropertiesMap().containsProperty("blobImg")))
					    {
						    // construct an images list
						    // from jrxml
						    imagesList = retImgListFromJrxml(detElems[i], imagesList);
					    }
					}
				    }
				}
			    }

			}

		    }
		}
	

	    }

	    // fill the proc and procParams sessions
	    // procs
	    IRP_REP_PROCSC repProcSC = new IRP_REP_PROCSC();
	    repProcSC.setREP_ID(report.getREPORT_ID());
	    repProcSC.setIsGrid(false);
	    List<IRP_REP_PROCCO> lstProc = procBO.getProceduresList(repProcSC);
	    report.setProceduresList(lstProc);
	    // params
	    IRP_REP_PROCCO procCO;
	    List<IRP_REP_PROC_PARAMSCO> lstParams;
	    HashMap<String, ArrayList<IRP_REP_PROC_PARAMSCO>> procParamsMap = report.getProcParamsMap();
	    for(int i = 0; i < lstProc.size(); i++)
	    {
		procCO = lstProc.get(i);
		repProcSC = new IRP_REP_PROCSC();
		repProcSC.setREP_ID(report.getREPORT_ID());
		repProcSC.setPROCEDURE_NAME(procCO.getPROC_NAME());
		repProcSC.setPROCEDURE_ID(procCO.getPROC_ID());
		repProcSC.setIsGrid(false);
		lstParams = procBO.getProceduresParamsList(repProcSC);
		procParamsMap.put(procCO.getPROC_NAME(), new ArrayList<IRP_REP_PROC_PARAMSCO>(lstParams));
	    }
	    report.setImagesList(imagesList);
	    return report;

	}
//	catch(UnsupportedEncodingException e)
//	{
//	    log.error(e, e.getMessage());
//	    throw new BOException(e.getMessage(),e);
//	}
	catch(FileNotFoundException e)
	{
	    log.error(e, e.getMessage());
	    throw new BOException(e.getMessage(),e);
	}
	catch(IOException e)
	{
	    log.error(e, e.getMessage());
	    throw new BOException(e.getMessage(),e);
	}
	catch(ClassNotFoundException e)
	{
	    log.error(e, e.getMessage());
	    throw new BOException(e.getMessage(),e);
	}
	catch(ParserConfigurationException e)
	{
	    log.error(e, e.getMessage());
	    throw new BOException(e.getMessage(),e);
	}
	catch(JRException e)
	{
	    log.error(e, e.getMessage());
	    throw new BOException(e.getMessage(),e);
	}
	catch(SAXException e)
	{
	    log.error(e, e.getMessage());
	    throw new BOException(e.getMessage(),e);
	}
	catch(XPathExpressionException e)
	{
	    log.error(e, e.getMessage());
	    throw new BOException(e.getMessage(),e);
	}
//	catch(Exception e)
//	{
//	    log.error(e, "Error in returning the report from db");
//	    throw new Exception("Error in returning the report from db");
//	}
    }

    private Long generateId()
    {
	Calendar cal = Calendar.getInstance();
	return cal.getTimeInMillis();
    }

    @Override
    public IRP_AD_HOC_REPORTCO findReportById(BigDecimal reportId) throws BaseException, IOException
    {
	return designerDAO.findReportById(reportId);
    }

    @Override
    public IRP_AD_HOC_REPORTCO returnReportByRef(IRP_AD_HOC_REPORTSC reportSC) throws BaseException
    {
	return designerDAO.returnReportByRef(reportSC);
    }

    public List<IRP_AD_HOC_QUERYVO> retQueriesByReport(IRP_AD_HOC_REPORTSC reportSC) throws BaseException
    {
	return designerDAO.retQueriesByReport(reportSC);
    }

    public IRP_AD_HOC_REPORTCO retProgRefByRepId(IRP_AD_HOC_REPORTSC reportSC) throws BaseException
    {
	return designerDAO.retProgRefByRepId(reportSC);
    }

    @Override
    public List<IRP_REP_ARGUMENTSCO> retArgsByReport(IRP_AD_HOC_REPORTSC reportSC) throws BaseException
    {
	return designerDAO.retArgsByReport(reportSC);
    }

    public IRP_AD_HOC_REPORTCO retRepIdByProgRef(IRP_AD_HOC_REPORTSC reportSC) throws BaseException
    {
	return designerDAO.retRepIdByProgRef(reportSC);
    }

//    public IRP_REP_ARGUMENTSCO retArgCO(LinkedHashMap argMap, BigDecimal paramId, IRP_REP_ARGUMENTSCO arggCO,
//	    Iterator itr, Long argIndx) throws Exception
//    {
//	IRP_REP_ARGUMENTSCO argCO=arggCO;
//	Long argIndex=argIndx;
//	Iterator it = argMap.values().iterator();
//	while(it.hasNext())
//	{
//	    argCO = (IRP_REP_ARGUMENTSCO) it.next();
//	    argIndex = argCO.getIndex();
//	    if(argCO.getARGUMENT_ID() != null && argCO.getARGUMENT_ID().intValue() == paramId.intValue())
//	    {
//		return argCO;
//	    }
//	    else if(argIndex != null && argIndex.equals(paramId.longValue()))
//	    {
//		return argCO;
//	    }
//	}
//
//	return null;
//    }

    public int selectReportByRef(IRP_AD_HOC_REPORTSC sc) throws BaseException
    {
	return designerDAO.selectReportByRef(sc);
    }

    @Override
    public String generateXhtml(byte[] jrxmlFILE, String xslFileName) throws BaseException
    {
	return FileTransformation.transformationToXhtml(jrxmlFILE, xslFileName);
    }

    public BigDecimal retRepIdFromOldRepId(BigDecimal oldRepId) throws BaseException
    {
	return designerDAO.retRepIdFromOldRepId(oldRepId);
    }

    @Override
    public String reorderJRxml(byte[] jrxmlFILE, String xslFileName) throws BaseException
    {
	return FileTransformation.reorderJRxml(jrxmlFILE, xslFileName);
    }

    public IRP_AD_HOC_REPORTCO retrieveReportVO(BigDecimal repId) throws BaseException
    {
	return designerDAO.retrieveReportVO(repId);
    }

    public String checkIfSubRepUploaded(String subRepName) throws BaseException
    {
	return designerDAO.checkIfSubRepUploaded(subRepName);
    }

    /**
     * get the list of sub report
     * 
     * @return IRP_SUB_REPORTVO
     * @throws BaseException
     */
    public List<IRP_SUB_REPORTVO> retSubRepList(IRP_SUB_REPORTSC subrepSC) throws BaseException
    {
	return designerDAO.retSubRepList(subrepSC);
    }

    /**
     * get the list of sub report
     * 
     * @return int
     * @throws BaseException
     */
    public int retSubRepListCount(IRP_SUB_REPORTSC subrepSC) throws BaseException
    {
	return designerDAO.retSubRepListCount(subrepSC);
    }

    /**
     * get the list of sub report param
     * 
     * @return int
     * @throws BaseException
     */
    public List<IRP_REP_ARGUMENTSCO> getSubRepParamsList(IRP_SUB_REPORTSC subrepSC) throws BaseException
    {
	return designerDAO.getSubRepParamsList(subrepSC);
    }

    /**
     * get the list of sub report param
     * 
     * @return int
     * @throws BaseException
     */
    public int getSubRepParamsCount(IRP_SUB_REPORTSC subrepSC) throws BaseException
    {
	return designerDAO.getSubRepParamsCount(subrepSC);
    }

    public List<BigDecimal> retSubReportUsage(List<BigDecimal> reportsId) throws BaseException
    {
	return designerDAO.retSubReportUsage(reportsId);
    }

    public List<BigDecimal> retSchedUsage(List<BigDecimal> reportsId) throws BaseException
    {
	return schedulerBO.retSchedUsage(reportsId);
    }

    /**
     * get the list of query arg mapping
     * 
     * @return list
     * @throws BaseException
     */
    public List<IRP_QUERY_ARG_MAPPINGCO> retQryArgMapping(IRP_AD_HOC_REPORTSC reportSC) throws BaseException
    {
	return designerDAO.retQryArgMapping(reportSC);
    }

    public List<IRP_REP_ARGUMENTSVO> retRepArgDepList(IRP_AD_HOC_REPORTSC reportSC)throws BaseException
    {
	return designerDAO.retRepArgDepList(reportSC);
    }
    
    public OPTVO checkIfSkipOptAxs(OPTVOKey optVOKey) throws BaseException
    {
	return (OPTVO) genericDAO.selectByPK(optVOKey);
    }
    
    public boolean checkIfOptInAdHocReport(OPTVOKey optVOKey) throws BaseException
    {
	return designerDAO.checkIfOptInAdHocReport(optVOKey);
    }

    /**
     * get the connection list count
     * 
     * @return int
     * @throws Exception
     */
    public List<IRP_HASH_TABLEVO> retHashTablList(IRP_HASH_TABLESC irpHashTblSC) throws BaseException
    {
	return designerDAO.retHashTablList(irpHashTblSC);
    }

    /**
     * get the connection list count
     * 
     * @return int
     * @throws Exception
     */
    public int retHashTablListCount(IRP_HASH_TABLESC irpHashTblSC) throws BaseException
    {
	return designerDAO.retHashTablListCount(irpHashTblSC);
    }

    public IRP_AD_HOC_REPORTCO emptyRepPropsForSaveAs(IRP_AD_HOC_REPORTCO currRepCO) throws BaseException
    {
	// set report id to null
	currRepCO.setREPORT_ID(null);
	IRP_AD_HOC_QUERYCO qryCO;
	List<IRP_AD_HOC_QUERYCO> qryList = currRepCO.getQueriesList();
	// set queries ids and reportQueriesVO to null
	for(int i = 0; i < qryList.size(); i++)
	{
	    qryCO = qryList.get(i);
	    qryCO.setQUERY_ID(null);
	    qryCO.setReportQueryVO(null);
	}
	// set all subReportIds to null
	IRP_SUB_REPORTCO subRepCO;
	List<IRP_SUB_REPORTCO> subRepList = currRepCO.getSubreportsList();
	for(int i = 0; i < subRepList.size(); i++)
	{
	    subRepCO = subRepList.get(i);
	    subRepCO.setPARENT_REPORT_ID(null);
	}
	// empty the argsDelete map
	currRepCO.setArgsDbDelete(new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>());

	// fill the argsUpdate in argsList and empty the argsUpdate
	LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> argsDbUpdateMap = currRepCO.getArgsDBUpdate();
	currRepCO.getArgumentsList().putAll(argsDbUpdateMap);
	currRepCO.setArgsDBUpdate(new LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>());
	// set all args ids of the arg. list to null
	IRP_REP_ARGUMENTSCO argCO;
	Iterator it = currRepCO.getArgumentsList().values().iterator();
	while(it.hasNext())
	{
	    argCO = (IRP_REP_ARGUMENTSCO) it.next();
	    argCO.setOLD_ARGUMENT_ID(argCO.getARGUMENT_ID());
	    argCO.setARGUMENT_ID(null);
	}
	return currRepCO;
    }
    
    public ImageCO readImageFromJrxml(Object child) throws BaseException
    {
	return ReportUtils.readImageFromJrxml(child);
    }
    
    public JasperDesign updateImgPath(JasperDesign jasperDesign, ArrayList<ImageCO> imgLst)throws BaseException
    {	
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	return ReportUtils.updateImgPath(jasperDesign,imgLst,repositoryPath);
    }
    
    public List<ImageCO> retImgListFromJrxml(Object childBand,List<ImageCO>imagesList)throws BaseException
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	return retImgListFromJrxml(childBand,imagesList, repositoryPath);
    }
    
    public List<ImageCO> retImgListFromJrxml(Object childBand,List<ImageCO>imagesList,String repositoryPath)throws BaseException
    {
	ImageCO imgCO;
	JRDesignImage img;
	JRDesignExpression expr;
	

	img = (JRDesignImage) childBand;
	imgCO = readImageFromJrxml(childBand);
	String imgName=imgCO.getImgName();
	expr = (JRDesignExpression) img.getExpression();
	expr.setText(repositoryPath + "\\images\\" + imgName + '"');
	imgCO.setImgLocation(repositoryPath + "\\images\\");
	boolean existImage = false;
	for(int p = 0; p < imagesList.size(); p++)
	{
	    if(imgName.equals(imagesList.get(p).getImgName()))
	    {
		existImage = true;
	    }
	}
	if(!existImage)
	{
		// check if the image exist in the DB
		IRP_REP_IMAGESVO repImgVO = new IRP_REP_IMAGESVO();
		repImgVO.setIMAGE_NAME(StringUtil.nullToEmpty(imgName));
		repImgVO = (IRP_REP_IMAGESVO) genericDAO.selectByPK(repImgVO);
		if (repImgVO != null) 
		{
			imgCO.setMappedImgName(imgName);
		}
		// check if the image exist in the repository
		else
		{
			String newPath = repositoryPath + "/images/" + imgName;
			File file;
			try {
				file = new PathFileSecure(newPath);
				if(file.exists())
				{
					imgCO.setMappedImgName(imgName);
				}
			} catch (Exception e) {
				log.error(e, e.getMessage());
			}
		}
		imagesList.add(imgCO);
	}
	return imagesList;
    }
    
    public String returnXslName(IRP_AD_HOC_REPORTSC reptSC) throws BaseException
    {
	return designerDAO.returnXslName(reptSC);
    }
    
    public List<USER_TAB_COLSCO> returnTblColsName(USER_TAB_COLSCO userTabColsCO) throws BaseException
    {
	return designerDAO.returnTblColsName(userTabColsCO);
    }
    
    public List<Object> returnAllOptsLst(OPTVO optVO)throws BaseException
    {
	List<Object> optLstVO = new ArrayList<Object>();
	 optLstVO=  designerDAO.returnAllOptsLst(optVO,optLstVO);
	 return optLstVO;
    }
   
    
    public List<ImageCO> retImgListFromJrxmlToAllBand(JasperDesign jasperDesign,List<ImageCO>imagessList)throws BaseException
    {
	JRBand[] allBandList = jasperDesign.getAllBands();
	List childLstBand;
	Object childBand = null;
	List<ImageCO> imagesList=imagessList;
	for(int b = 0; b < allBandList.length; b++)
	{
	    childLstBand = allBandList[b].getChildren();
	    for(int c = 0; c < childLstBand.size(); c++)
	    {
		childBand = childLstBand.get(c);
		if(childBand instanceof JRDesignImage && !((JRDesignImage) childBand).getPropertiesMap().containsProperty("blobImg"))
		{
			// construct an images list from jrxml
			imagesList = retImgListFromJrxml(childBand, imagesList);
		}
	    }
	}
	return imagesList;
    }
    
    public void copyImages(List<ImageCO>imagesList,String imagePathSrc,String imagePathDest)throws BaseException
    {
	File sourceFile,destFile;
	for(int i = 0; i < imagesList.size(); i++)
	{
		try {
			sourceFile = new PathFileSecure(imagePathSrc + "/" + imagesList.get(i).getImgName());
			destFile = new PathFileSecure(imagePathDest + "/" + imagesList.get(i).getImgName());
			if(sourceFile.exists())
		    {
			try
			{
			    FileUtils.copyFile(sourceFile, destFile);
			}
			catch(IOException e)
			{
			    log.error(e,"error in copyImages - designerBOImpl");
			}
		    }
		} catch (Exception e1) {
			log.error(e1, e1.getMessage());
		}
	}
    }
    
    /***
     * Method that writes a list of hashmap to a csv file
     * @param tblName
     * @param lstMap
     * @param csvFilePath
     * @param userName
     * @param reportName
     * @throws BaseException
     * @throws IOException
     */
    private void exportMapLstToCsv(String tblName, ArrayList<HashMap<String, Object>> lstMap, String csvFilePath)
	    throws BaseException, IOException
    {
	if(lstMap.isEmpty())
	{
	    return;
	}
	String csvFile = (csvFilePath + "/" + tblName + RepConstantsCommon.CSV_EXT);
	FileOutputStream fos = new FileOutputStream(csvFile);
	Writer writer = new OutputStreamWriter(fos, FileUtil.DEFAULT_FILE_ENCODING);

	// writer the headers
	HashMap<String, Object> colsNameMap = lstMap.get(0);
	for(Entry<String, Object> entry : colsNameMap.entrySet())
	{
	    writer.write(entry.getKey() + ",");
	}

	writer.write("\r\n");
	String propertyVal = null;
	byte[] propertyByteVal = null;
	for(int l = 0; l < lstMap.size(); l++)
	{
	    // map is one row in the table
	    HashMap<String,Object> map = lstMap.get(l);
	    for(Entry<String, Object> entry : colsNameMap.entrySet())
	    {
		if(entry.getValue() instanceof byte[] && !entry.getKey().equals(RepConstantsCommon.JRXML_FILE_COL))
		{
		    propertyByteVal = (byte[])map.get(entry.getKey());
		    propertyVal = new String(propertyByteVal, CommonUtil.ENCODING);
		    propertyVal = StringUtil.escapeCharactersInString(propertyVal, StringUtil.CSV_TYPE_ESCAPE_UNESCAPE);
		    propertyVal = propertyVal.replaceAll(ConstantsCommon.NEW_LINE, " ");
		    propertyVal = propertyVal.replaceAll("\t", " ");
		    propertyVal = propertyVal.replaceAll("\r", " ");
		    propertyVal = propertyVal.replaceAll("\r\n", " ");
		}
		else
		{
		    if(map.get(entry.getKey()) == null)
		    {
			propertyVal = "";
		    }
		    else
		    {
			propertyVal = StringUtil.escapeCharactersInString(map.get(entry.getKey()).toString(),
				StringUtil.CSV_TYPE_ESCAPE_UNESCAPE);
			propertyVal = propertyVal.replaceAll(ConstantsCommon.NEW_LINE, " ");
			propertyVal = propertyVal.replaceAll("\t", " ");
			propertyVal = propertyVal.replaceAll("\r", " ");
			propertyVal = propertyVal.replaceAll("\r\n", " ");
		    }
		}
		writer.write(propertyVal + ",");
	    }
	    writer.write("\r\n");
	}
	writer.close();
    }
    
    
    /**
     * used to write the content of reports and its sub
     * @param exportedReportsMap contain the reports that should be exported
     * @param repositoryPath path of teh repository
     * @param errorStr
     * @param translationMap
     * @throws BaseException, Exception,IOException 
     */
    public void writeRepContent(HashMap<BigDecimal, IRP_SUB_REPORTCO> exportedReportsMap, String userName,StringBuffer errorStr,int errorCounter, HashMap<String,String> translationMap,String lang,boolean skipTrans) throws BaseException, Exception,IOException
    {
	String filePath;
	JasperDesign jasperDesign =null;
	List<ImageCO> imagesList =null;
	int errorCtr=errorCounter;//to avoid PMD
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String flError = (repositoryPath + "/" + RepConstantsCommon.EXPORT_REP +"/"+RepConstantsCommon.REP_EXT+userName+"/"+RepConstantsCommon.ERROR_TXT);
	FileOutputStream fosError = new FileOutputStream(flError);
	Writer fileErrorWriter = new OutputStreamWriter(fosError,FileUtil.DEFAULT_FILE_ENCODING);// ,"UTF-8");
	HashMap<BigDecimal,BigDecimal> templateQry =new HashMap<BigDecimal,BigDecimal>();	
	HashMap<BigDecimal,BigDecimal> repConnectionsHM = new HashMap<BigDecimal,BigDecimal>();
	HashMap<BigDecimal,BigDecimal> repProcHM = new HashMap<BigDecimal,BigDecimal>();	
	HashMap<BigDecimal,BigDecimal> repHashTblHM = new HashMap<BigDecimal,BigDecimal>();
	HashMap<String,String> pthClientHM = new HashMap<String, String>();
	
	String csvFilePath;
	String csvFilePathStandalone = "";
	String progRef;
	String appName;
	BigDecimal connId;
	BigDecimal compCode;
	BigDecimal repId;
	String reportType;
	ArrayList<HashMap<String,Object>> dataListMap; 
	IRP_AD_HOC_REPORTSC repSc;
	
	IRP_SUB_REPORTCO subReportCO;
	for(Entry<BigDecimal, IRP_SUB_REPORTCO> entry : exportedReportsMap.entrySet())
	{
	    subReportCO = entry.getValue(); 
	    try{
		repSc = new IRP_AD_HOC_REPORTSC();
		repId =subReportCO.getIrpSubReportVO().getREPORT_ID();
		repSc.setREPORT_ID(repId);
		repSc.setTblName(RepConstantsCommon.IRP_AD_HOC_REPORT);
		dataListMap = designerDAO.returnDataByTable(repSc);
		progRef = (String)dataListMap.get(0).get(RepConstantsCommon.PROG_REF_COL);
		appName = (String)dataListMap.get(0).get(RepConstantsCommon.APP_NAME_COL);
		connId = (BigDecimal)dataListMap.get(0).get(RepConstantsCommon.CONN_ID_COL);
		dataListMap.get(0).put(RepConstantsCommon.XHTML_FILE_COL, (RepConstantsCommon.HTML_TAG).getBytes(FileUtil.DEFAULT_FILE_ENCODING));
		reportType=(String)dataListMap.get(0).get(RepConstantsCommon.REPORT_TYPE_COL);
		compCode=(BigDecimal)dataListMap.get(0).get(RepConstantsCommon.COMP_CODE_COL);
	    }
	    catch(Exception e)
	    {
		errorStr.append(translationMap.get("upDown.unknownEx")).append(":").append( subReportCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(translationMap.get("progRef")).append(":").append(subReportCO.getPROG_REF()).append(" ").append(translationMap.get("upDown.appName_key")).append(":").append(subReportCO.getAPP_NAME()).append("   : ").append(e.getMessage()).append("\r\n");
		errorCtr++;
		continue;
	    }
	    
	    
	    boolean isCreatedRepFolder = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
		    + RepConstantsCommon.REP_EXT + userName + "/" + progRef+"_"+appName).mkdir();
	    boolean isCreatedImages = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
		    + RepConstantsCommon.REP_EXT + userName + "/" + progRef+"_"+appName + "/"
		    + RepConstantsCommon.IMAGES_LOCATION).mkdir();
	    if(!isCreatedRepFolder || !isCreatedImages)
	    {
		log.debug("The directory " + RepConstantsCommon.EXPORT_REP + " has not been created");
	    }
	    
	   try{
	    
	    FileUtil.writeFile(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT
		    + userName + "/" + progRef+"_"+appName + "/" + progRef+"_"+appName + RepConstantsCommon.JRXML_EXT, new String(
		    subReportCO.getJRXML_FILE(), FileUtil.DEFAULT_FILE_ENCODING), ConstantsCommon.FILE_ENCODING);
	    StringBuffer versionData=new StringBuffer("");
		
	    versionData.append(RepConstantsCommon.VERSION_KEY + RepConstantsCommon.EQUAL);
	    versionData.append(subReportCO.getREPORT_VERSION());
	    versionData.append("\r\n");
	    versionData.append(RepConstantsCommon.VERSION_DATE_KEY + RepConstantsCommon.EQUAL);
	    String versionDate = DateUtil.format(subReportCO.getVERSION_DATE(), "dd/MM/yyyy");
	    versionData.append(versionDate);
	    
	    FileUtil.writeFile(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT
	    + userName + "/" + progRef+"_"+appName + "/" + RepConstantsCommon.VERSION_TXT,
		    versionData.toString(), ConstantsCommon.FILE_ENCODING);

	    filePath = repositoryPath+ "/" + RepConstantsCommon.EXPORT_REP
	    + "/" + RepConstantsCommon.REP_EXT + userName + "/" + progRef+"_"+appName  + "/" + progRef+"_"+appName + RepConstantsCommon.JRXML_EXT;
	    
	 
		jasperDesign = JRXmlLoader.load(filePath);
	        csvFilePath = repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT
	        	     + userName + "/" + progRef+"_"+appName;
	        csvFilePathStandalone = repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT+userName;
	    }
	    catch(UnsupportedEncodingException e)
		{
		    errorStr.append(translationMap.get("upDown.encodingEx_key")).append(":").append( subReportCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(translationMap.get("progRef")).append(":").append(subReportCO.getPROG_REF()).append(" ").append(translationMap.get("upDown.appName_key")).append(":").append(subReportCO.getAPP_NAME()).append("\r\n");
		    errorCtr++;
		    continue;
		}
		catch(FileNotFoundException e)
		{
		    errorStr.append(translationMap.get("upDown.fileNotFound")).append( " " ).append( repositoryPath ).append( "/"
		    ).append( RepConstantsCommon.EXPORT_REP ).append( "/" ).append( RepConstantsCommon.REP_EXT ).append( userName ).append( "/"
		    ).append( subReportCO.getSUB_REPORT_NAME()).append( RepConstantsCommon.JRXML_EXT).append(" ").append( translationMap.get("upDown.repId")).append(":"
		    ).append( subReportCO.getIrpSubReportVO().getREPORT_ID() ).append( "\r\n");
		    errorCtr++;
		    continue;
		}

		catch(JRException e)
		{
		   errorStr.append(translationMap.get("upDown.invalid")).append(" ").append( translationMap.get("upDown.repId")).append(" ").append(subReportCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(translationMap.get("progRef")).append(":").append(subReportCO.getPROG_REF()).append( " " ).append(translationMap.get("upDown.appName_key")).append(":").append(subReportCO.getAPP_NAME()).append( "\r\n");
		   File file=new PathFileSecure( repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT + userName
		   + "/" + subReportCO.getSUB_REPORT_NAME() + RepConstantsCommon.JRXML_EXT);
		   boolean isDel = file.delete();
		   if(!isDel)
		   {
		       log.error("The following file has not been deleted :");
		   }
		   errorCtr++;
		   continue;
		}
		catch(SAXException e)
		{
		    errorStr.append(translationMap.get("upDown.invalid")).append(" ").append(translationMap.get("upDown.repId")).append( subReportCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(translationMap.get("progRef")).append(":").append(subReportCO.getPROG_REF()).append(translationMap.get("upDown.appName_key")).append(":").append(subReportCO.getAPP_NAME()).append("\r\n");
		    errorCtr++;
		    continue;
		}
		catch(Exception e)
		{

		    errorStr.append(translationMap.get("upDown.unknownEx")).append(":").append( subReportCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(translationMap.get("progRef")).append(":").append(subReportCO.getPROG_REF()).append(" ").append(translationMap.get("upDown.appName_key")).append(":").append(subReportCO.getAPP_NAME()).append("   : ").append(e.getMessage()).append("\r\n");
		    errorCtr++;
		    continue;
		}

		//get the image list from all jrxml without repetition 
		imagesList = new ArrayList<ImageCO>();//to set empty the imagesList to get again all the image in the jrxml
		imagesList = retImgListFromJrxmlToAllBand(jasperDesign,imagesList);
        	// for each image retrieve it from the database
        	HashMap<String, byte[]> imagesDbMap = new HashMap<String, byte[]>();
        	byte[] data;
		String imagePathSrc = repositoryPath + "/" + RepConstantsCommon.IMAGES_LOCATION;
		ImageCO currImgCO;
		ImageCO foundImgCO;
		String err = null;
		HashMap<String, ImageCO> imageHm = new HashMap<String, ImageCO>();
		
		for(int i=0;i<imagesList.size();i++)
		{
		    data=imageBO.retImgObject(imagesList.get(i).getImgName());
		    if(data!=null)
		    {
			imagesDbMap.put(imagesList.get(i).getImgName(), data);
		    }
		    currImgCO=imagesList.get(i);
        	    foundImgCO=imageHm.get(currImgCO.getImgName());
        	  //add condition image not in database
        	    if(imagesDbMap.get(currImgCO.getImgName())==null)
        	    {
        	    if(foundImgCO==null)
        	    {
        		imageHm.put(currImgCO.getImgName(), currImgCO);
        		 File sourceFile = new PathFileSecure(imagePathSrc + "/" + currImgCO.getImgName());
              	   // File destFile = new PathFileSecure(imagePathDest + "/" + currImgCO.getImgName());
              
              	    if(!sourceFile.exists())
              	    {
              		errorStr.append(translationMap.get("upDown.image_key")).append(":").append(currImgCO.getImgName()).append(" ").append(translationMap.get("upDown.notFound_key")).append(" ").append(translationMap.get("upDown.repId")).append(":").append(subReportCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(translationMap.get("progRef")).append(":").append(subReportCO.getPROG_REF()).append(" ").append(translationMap.get("upDown.appName_key")).append(":").append(subReportCO.getAPP_NAME()).append("\r\n");
              		errorCtr++;
              		currImgCO.setErrorStatus( translationMap.get("upDown.image_key")+":");
              	    }
        	    }
        	    else
        	    {
        		err=foundImgCO.getErrorStatus();
        		if(err!=null && !"".equals(err))
        		{
            			errorStr.append(err+ currImgCO.getImgName()).append(" ").append(translationMap.get("upDown.notFound_key")).append(" ").append(translationMap.get("upDown.repId")).append(":").append(subReportCO.getIrpSubReportVO().getREPORT_ID()).append(" ").append(translationMap.get("progRef")).append(":").append(subReportCO.getPROG_REF()).append(" ").append(translationMap.get("upDown.appName_key")).append(":").append(subReportCO.getAPP_NAME()).append("\r\n");
            			errorCtr++;
        		}
        		
        	    }
        	    }
		}
	    
		    imagePathSrc = repositoryPath + "/" + RepConstantsCommon.IMAGES_LOCATION;
		    //imagesList.clear();//to put in a clear list the imagCO without redundancy
		    imagesList = new ArrayList<ImageCO>(imageHm.values());
		    //copy the images from repository to folder image 
		    String imagePathDest = repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT + userName + "/"
		     + progRef+"_"+appName+ "/" + RepConstantsCommon.IMAGES_LOCATION;
		    copyImages(imagesList,imagePathSrc,imagePathDest);		    
		    BufferedImage bufImg; 
        	    for(Entry<String, byte[]> e : imagesDbMap.entrySet())
        	    {
        
        		bufImg = ImageIO.read(new ByteArrayInputStream(e.getValue()));
        		ImageIO.write(bufImg, e.getKey().substring(e.getKey().lastIndexOf(".")+1, e.getKey().length()), new PathFileSecure(
        			imagePathDest + "/" + e.getKey()));
        	    }
	    
	    String tblName = RepConstantsCommon.IRP_AD_HOC_REPORT;
	    if(connId!=null)
	    {
		repConnectionsHM.put(connId, connId);
	    }
	    exportMapLstToCsv(tblName, dataListMap,csvFilePath);
	    
	    
	    if(RepConstantsCommon.ONE.equals(reportType))
	    {
		String ftrExpXlsTblName = RepConstantsCommon.FTR_EXP_XLS;
		repSc = new IRP_AD_HOC_REPORTSC();
		repSc.setPROG_REF(progRef);
		repSc.setCOMP_CODE(compCode);
		repSc.setTblName(ftrExpXlsTblName);
		dataListMap = designerDAO.returnDataByTable(repSc);
		exportMapLstToCsv(ftrExpXlsTblName, dataListMap,csvFilePath);
	    }
	    
	    ArrayList<BigDecimal> argIdsLst = new ArrayList<BigDecimal>();
	    repSc = new IRP_AD_HOC_REPORTSC();
	    String argsTblName = RepConstantsCommon.IRP_REP_ARGUMENTS;
	    repSc.setREPORT_ID(repId);
	    repSc.setTblName(argsTblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    if(!dataListMap.isEmpty())
	    {
		HashMap<String, Object> hm;
		for(int i = 0; i < dataListMap.size(); i++)
		{
		    hm = dataListMap.get(i);
		    argIdsLst.add((BigDecimal) hm.get(RepConstantsCommon.ARGUMENT_ID_COL));
		    if(hm.get(RepConstantsCommon.QUERY_ID_COL) != null)
		    {
			templateQry.put((BigDecimal) hm.get(RepConstantsCommon.QUERY_ID_COL), (BigDecimal) hm
				.get(RepConstantsCommon.QUERY_ID_COL));
		    }
		    if(hm.get(RepConstantsCommon.QUERY_ID_DEFAULT_COL) != null)
		    {
			templateQry.put((BigDecimal) hm.get(RepConstantsCommon.QUERY_ID_DEFAULT_COL), (BigDecimal) hm
				.get(RepConstantsCommon.QUERY_ID_DEFAULT_COL));
		    }
		}
		exportMapLstToCsv(argsTblName, dataListMap, csvFilePath);
	    }
	    
	    if(!argIdsLst.isEmpty())
	    {
		ArrayList<HashMap<String, Object>> linkQryListMap;
		String linkQryArgMapTblName = RepConstantsCommon.IRP_QUERY_ARG_MAPPING;
		repSc = new IRP_AD_HOC_REPORTSC();
		repSc.setREPORT_ID(repId);
		repSc.setListArgsIds(argIdsLst);
		repSc.setTblName(linkQryArgMapTblName);
		linkQryListMap = designerDAO.returnDataByTable(repSc);
		exportMapLstToCsv(linkQryArgMapTblName, linkQryListMap, csvFilePath);
	    }
	    
	    //exporting the constraints
	    String argConstrTblName = RepConstantsCommon.IRP_REP_ARG_CONSTRAINTS;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setREPORT_ID(repId);
	    repSc.setTblName(argConstrTblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    IRP_REP_ARG_CONSTRAINTSVO vo = new IRP_REP_ARG_CONSTRAINTSVO();
	    IRP_REP_ARG_CONSTRAINTSVO argCnstrVO;
	    for(int i = 0; i < dataListMap.size(); i++)
	    {
		vo.setREPORT_ID(repId);
		vo.setARGUMENT_ID((BigDecimal) dataListMap.get(i).get(RepConstantsCommon.ARGUMENT_ID_COL));
		vo.setVALUE_CODE((String) dataListMap.get(i).get(RepConstantsCommon.VALUE_CODE_COL));
		argCnstrVO = (IRP_REP_ARG_CONSTRAINTSVO) genericDAO.selectByPK(vo);
		dataListMap.get(i).put(RepConstantsCommon.VALUE_COL, argCnstrVO.getVALUE());
	    }
	    exportMapLstToCsv(argConstrTblName, dataListMap, csvFilePath);
	    

	    String qryRepTblName = RepConstantsCommon.IRP_REPORT_QUERY;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setREPORT_ID(repId);
	    repSc.setTblName(qryRepTblName);
	    dataListMap=designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(qryRepTblName, dataListMap, csvFilePath);
	    
	    String qryTblName = RepConstantsCommon.IRP_AD_HOC_QUERY;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setQUERY_ID((BigDecimal)dataListMap.get(0).get(RepConstantsCommon.QUERY_ID_COL));
	    repSc.setTblName(qryTblName);
	    dataListMap=designerDAO.returnDataByTable(repSc);
	    IRP_AD_HOC_QUERYVO queryVO = new IRP_AD_HOC_QUERYVO();
	    queryVO.setQUERY_ID(repSc.getQUERY_ID());
	    IRP_AD_HOC_QUERYVO curVO = (IRP_AD_HOC_QUERYVO)genericDAO.selectByPK(queryVO);
	    dataListMap.get(0).put(RepConstantsCommon.QUERY_OBJECT_COL,curVO.getQUERY_OBJECT());
	    exportMapLstToCsv(qryTblName, dataListMap, csvFilePath);
	  
	    //subreports
	    String subRepTblName = RepConstantsCommon.IRP_SUB_REPORT;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setREPORT_ID(repId);
	    repSc.setTblName(subRepTblName);
	    dataListMap=designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(subRepTblName, dataListMap, csvFilePath);
	    

	    String procRepTblName = RepConstantsCommon.IRP_REP_PROC;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setREPORT_ID(repId);
	    repSc.setTblName(procRepTblName);
	    dataListMap=designerDAO.returnDataByTable(repSc);
	    for(int i = 0; i < dataListMap.size(); i++)
	    {
		repProcHM.put((BigDecimal) dataListMap.get(i).get(RepConstantsCommon.PROC_ID_COL),
			(BigDecimal) dataListMap.get(i).get(RepConstantsCommon.PROC_ID_COL));
	    }
	    exportMapLstToCsv(procRepTblName, dataListMap, csvFilePath);
	   
	    String procParamTblName = RepConstantsCommon.IRP_REP_PROC_PARAMS;
	    repSc.setTblName(procParamTblName);
	    if(!dataListMap.isEmpty())
	    {
		repSc.setListProcIds(new ArrayList<BigDecimal>(repProcHM.values()));
		dataListMap = designerDAO.returnDataByTable(repSc);
		exportMapLstToCsv(procParamTblName, dataListMap, csvFilePath);
	    }
	    

	    // get the hyper links
	    String hyperLinkTblName = RepConstantsCommon.IRP_REP_HYPERLINKS;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setREPORT_ID(repId);
	    repSc.setTblName(hyperLinkTblName);
	    dataListMap=designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(hyperLinkTblName, dataListMap, csvFilePath);
	    

	    String optTblName = RepConstantsCommon.OPT;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setAPP_NAME(appName);
	    repSc.setTblName(optTblName);
	    OPTVO optVO = new OPTVO();
	    optVO.setPROG_REF(progRef);
	    optVO.setAPP_NAME(appName);
	    List<Object> optLst = returnAllOptsLst(optVO);
	    if(!optLst.isEmpty())
	    {
		repSc.setListOpts(new ArrayList<String>());
		for(int i = 0; i < optLst.size(); i++)
		{
		    repSc.getListOpts().add(((OPTVO) optLst.get(i)).getPROG_REF());
		}
		dataListMap = designerDAO.returnDataByTable(repSc);
		exportMapLstToCsv(optTblName, dataListMap, csvFilePath);
	    }
	   
	   String optExTblName = RepConstantsCommon.OPT_EXTENDED;
	   repSc = new IRP_AD_HOC_REPORTSC();
	   repSc.setPROG_REF(progRef);
	   repSc.setAPP_NAME(appName);
	   repSc.setTblName(optExTblName);
	   dataListMap=designerDAO.returnDataByTable(repSc);	   
	   exportMapLstToCsv(optExTblName, dataListMap, csvFilePath);	       

	    // get the folder for a report
	    String folderTblName =RepConstantsCommon.IRP_FOLDER;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    optVO = new OPTVO();
	    optVO.setPROG_REF(progRef);
	    optVO.setAPP_NAME(appName);
	    OPTVO curOptVO = (OPTVO)genericDAO.selectByPK(optVO);
	    repSc.setAPP_NAME(appName);
	    repSc.setTblName(folderTblName);
	    IRP_FOLDERVO folderVO = new IRP_FOLDERVO();
	    folderVO.setFOLDER_REF(curOptVO.getPARENT_REF());
	    folderVO.setAPP_NAME(appName);
	    List<Object> foldersLstVO = foldersBO.retAllFoldersLst(folderVO);
	    ArrayList<String> listFolderRef = new ArrayList<String>();
	    for(int i=0;i<foldersLstVO.size();i++)
	    {
		listFolderRef.add(((IRP_FOLDERVO)foldersLstVO.get(i)).getFOLDER_REF());
	    }
	    if(!listFolderRef.isEmpty())
	    {
		repSc.setListFolderRef(listFolderRef);
		dataListMap = designerDAO.returnDataByTable(repSc);
		exportMapLstToCsv(folderTblName, dataListMap, csvFilePath);
	    }
	    
	    if(commonLibBO.returnIsSybase() == 1)
	    {
		// return hashTable
		String hashTblRepName = RepConstantsCommon.IRP_HASH_TABLE_REPORT;
		repSc = new IRP_AD_HOC_REPORTSC();
		repSc.setREPORT_ID(repId);
		repSc.setTblName(hashTblRepName);
		dataListMap = designerDAO.returnDataByTable(repSc);
		exportMapLstToCsv(hashTblRepName, dataListMap, csvFilePath);
		if(!dataListMap.isEmpty())
		{
		    for(int i = 0; i < dataListMap.size(); i++)
		    {
			repHashTblHM.put((BigDecimal) dataListMap.get(i).get(RepConstantsCommon.HASH_TABLE_ID_COL),
				(BigDecimal) dataListMap.get(i).get(RepConstantsCommon.HASH_TABLE_ID_COL));
		    }
		}
	    }
	       
	    // return hashTable
	    String repClientTblName = RepConstantsCommon.IRP_CLIENT_REPORT;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setREPORT_REF(progRef);
	    repSc.setAPP_NAME(appName);
	    repSc.setTblName(repClientTblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(repClientTblName, dataListMap, csvFilePath);
	    for(int i = 0; i < dataListMap.size(); i++)
	    {
		pthClientHM.put((String) dataListMap.get(i).get(RepConstantsCommon.CLIENT_ACRONYM_COL),
			(String) dataListMap.get(i).get(RepConstantsCommon.CLIENT_ACRONYM_COL));
	    }
	    
	    //return text snapshot parameter data 
	    String repSnpParamTblName = RepConstantsCommon.REP_SNAPSHOT_PARAM;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setREPORT_REF(progRef);
	    repSc.setTblName(repSnpParamTblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(repSnpParamTblName, dataListMap, csvFilePath);
	    
	    //return text snapshot AOD parameter mapping data from IRP_SNAPSHOT_PARAM_MAPPING
	    String irpSnpParamMappingTblName = RepConstantsCommon.IRP_SNAPSHOT_PARAM_MAPPING;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setREPORT_REF(progRef);
	    repSc.setTblName(irpSnpParamMappingTblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(irpSnpParamMappingTblName, dataListMap, csvFilePath);
	    
	    //export the translation
	    if(!skipTrans)
	    {
        	    TranslationSC translationSC = new TranslationSC();
        	    SelectSC sc = new SelectSC();
        	    sc.setLovTypeId(ConstantsCommon.LANGUAGES_LOV_TYPE);
        	    sc.setLanguage(lang);
        	    List<SYS_PARAM_LANGUAGESVO> languageVO = commonLibBO.returnLanguages(sc);
        	    StringBuffer strBfr=new StringBuffer("");
        	    for(int i=0;i<languageVO.size();i++)
        	    {
        		strBfr.append(languageVO.get(i).getLANG_CODE()).append(",");
        	    }
        	    String str = strBfr.toString().substring(0, strBfr.length()-1);
        	    String strLang = str.replace(",", "','");
        	    strLang = ("'".concat(strLang)).concat("'");
        	    
        	    translationSC.setPreferredLanguage(strLang);
        	    translationSC.setAppName(appName);
        	    translationSC.setSelectedApp(TranslationConstants.ALL_APPS_SELECTION);//5
        	    translationSC.setExportType(new BigDecimal(2));
        	    translationSC.setPageRef(progRef);//(report.getPROG_REF());
        	    StringBuffer scriptBuff = translationBO.returnLblExp(translationSC);
        	   byte[] scriptByte = scriptBuff.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
        	    scriptByte = UnicodeUtil.addBOMToBytes(scriptByte, FileUtil.DEFAULT_FILE_ENCODING);
        	    FileUtil.writeFile(csvFilePath + "/" + RepConstantsCommon.TRANSLATION_CSV, new String(scriptByte,FileUtil.DEFAULT_FILE_ENCODING),FileUtil.DEFAULT_FILE_ENCODING);
	    }
	    
    }
	//to export the template queries linked to arguments
	if(!templateQry.isEmpty())
	{
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setListQryIds(new ArrayList<BigDecimal>(templateQry.values()));
	    String tblName = RepConstantsCommon.IRP_AD_HOC_QUERY;
	    repSc.setTblName(tblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    for(int i=0;i<dataListMap.size();i++)
	    {
		IRP_AD_HOC_QUERYVO queryVO = new IRP_AD_HOC_QUERYVO();
		queryVO.setQUERY_ID((BigDecimal)dataListMap.get(i).get(RepConstantsCommon.QUERY_ID_COL));
		IRP_AD_HOC_QUERYVO curVO = (IRP_AD_HOC_QUERYVO)genericDAO.selectByPK(queryVO);
		dataListMap.get(i).put(RepConstantsCommon.QUERY_OBJECT_COL,curVO.getQUERY_OBJECT());
	    }  
	    exportMapLstToCsv(tblName, dataListMap, csvFilePathStandalone);
	}	
	//to export the connection of reports
	if(!repConnectionsHM.isEmpty())
	{
	    String tblName = RepConstantsCommon.IRP_CONNECTIONS;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setListConIds(new ArrayList<BigDecimal>(repConnectionsHM.values()));
	    repSc.setTblName(tblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(tblName, dataListMap, csvFilePathStandalone);
	}
	
	
	
	//to export the procedures of reports
	if(!repProcHM.isEmpty())
	{
	    String tblName = RepConstantsCommon.IRP_PROC;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setListProcIds(new ArrayList<BigDecimal>(repProcHM.values()));
	    repSc.setTblName(tblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(tblName, dataListMap, csvFilePathStandalone);
	}
	//to export the hash table of reports
	if(!repHashTblHM.isEmpty())
	{
	    String irpHshTblName = RepConstantsCommon.IRP_HASH_TABLE;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setListHashIds(new ArrayList<BigDecimal>(repHashTblHM.values()));
	    repSc.setTblName(irpHshTblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    exportMapLstToCsv(irpHshTblName, dataListMap, csvFilePathStandalone);
	}
	if(!pthClientHM.isEmpty())
	{
	    String pthClientTblName = RepConstantsCommon.PTH_CLIENTS;
	    repSc = new IRP_AD_HOC_REPORTSC();
	    repSc.setListClientAcronym(new ArrayList<String>(pthClientHM.values()));
	    repSc.setTblName(pthClientTblName);
	    dataListMap = designerDAO.returnDataByTable(repSc);
	    for(int i = 0; i < dataListMap.size(); i++)
	    {
		dataListMap.get(i).put(RepConstantsCommon.IS_DEFAULT_YN_COL, RepConstantsCommon.N);
	    }
	    exportMapLstToCsv(pthClientTblName, dataListMap, csvFilePathStandalone);
	}
	    errorStr.append(translationMap.get("upDown.totalError")).append(":").append(errorCtr);
	    fileErrorWriter.write(errorStr.toString());
	    fileErrorWriter.close();
    }

    public String retExistingOpts(String progRef,String appName,String lang,String transMsg) throws BaseException
    {
	    FTR_OPTCO ftrCO = new FTR_OPTCO();
	    ArrayList<String> progRefs = new ArrayList<String>();
	    progRefs.add(progRef);
	    progRefs.add(progRef+RepConstantsCommon.OPT_MOD_M);
	    progRefs.add(progRef+RepConstantsCommon.OPT_DEL_D);
	    progRefs.add(progRef+RepConstantsCommon.OPT_SV_SV);
	    progRefs.add(progRef+RepConstantsCommon.OPT_SA_SA);
	    progRefs.add(progRef+RepConstantsCommon.OPT_SM_SM);
	    progRefs.add(progRef+RepConstantsCommon.OPT_SC_SC);
	    progRefs.add(progRef+RepConstantsCommon.OPT_PR_PR);
	    ftrCO.setPROG_REFS(progRefs);
	    ftrCO.getFtrOptVO().setPROG_REF(progRef);
	    ftrCO.setAPPL_NAME(appName);
	    List<OPTVO> lst= designerDAO.retExistingOpts(ftrCO);
	    if(lst.isEmpty())
	    {
		return "0";
	    }
	    else
	    {
		StringBuffer sb=new StringBuffer();
		//sb.append(getText("fcr.checkProgRefAlert"));
		sb.append(transMsg);
		OPTVO vo;
		HashMap<String,String> hm=new HashMap<String,String>();
		for(int i=0;i<lst.size();i++)
		{
		    vo=lst.get(i);
		    	if(hm.get(vo.getPROG_REF())==null)
		    	{
			    if(ConstantsCommon.LANGUAGE_ARABIC.equals(lang))
			    {
			    sb.append(ConstantsCommon.NEW_LINE+vo.getPROG_REF() +" : "+vo.getMENU_TITLE_ARAB());
			    }
			    else if(ConstantsCommon.LANGUAGE_FRENCH.equals(lang))
			    {
				sb.append(ConstantsCommon.NEW_LINE+vo.getPROG_REF() +" : "+vo.getMENU_TITLE_FR());
			    }
			    else
			    {
				sb.append(ConstantsCommon.NEW_LINE+vo.getPROG_REF() +" : "+vo.getMENU_TITLE_ENG());
			    }
			    hm.put(vo.getPROG_REF(), "");
		    	}
		    
		}
		return sb.toString();
	
    }
    }
    
    
    
    public Object returnPropValue(Object obj, String[] headerName, Object[] valueArray,
	    StringBuffer excelAdditionnalCols, StringBuffer dbAdditionnalCols) throws Exception
    {
	Class<?> propType=null;
	//first fill the table's name
	boolean classNameFilled = false;
	String simpleName=obj.getClass().getSimpleName();
	String tblName = simpleName.substring(0,simpleName.lastIndexOf(RepConstantsCommon.CLASS_VO));
	checkIfDbTableHasMoreColumns(tblName,headerName,dbAdditionnalCols);
	for(int i = 0; i < headerName.length - 1; i++)
	{
	    	Object propVal =valueArray[i];
		if(PropertyUtils.isReadable(obj, headerName[i]) && PropertyUtils.isWriteable(obj, headerName[i]))    
		{
		    propType = PropertyUtils.getPropertyType(obj, headerName[i]);
		    // in case of empty values for non string types we
		    // need to put manually null
		    if(!propType.isPrimitive() && !propType.getCanonicalName().contains("String"))
		    {
			if(((String) (propVal)).trim().isEmpty() || ((String) (propVal)).replaceAll(" ", "").isEmpty())
			{
			    // if numeric Field then need to set as EmptyBigDecimal
			    if(propType.getCanonicalName().contains("BigDecimal"))
			    {
				 propVal = ConstantsCommon.EMPTY_BIGDECIMAL_VALUE;
				 continue;
			    }
			    else if(propType.getCanonicalName().contains("Date"))
			    {
				continue;
			    }
			    else
			    {
				propVal=null;
				continue;
			    }
			}
			if(propType.getCanonicalName().contains("byte[]"))
			{
			    byte[] b = propVal.toString().getBytes((FileUtil.DEFAULT_FILE_ENCODING));
			    PropertyUtils.setProperty(obj, headerName[i], b);
			    continue;
			}
			if(propType.getCanonicalName().contains("Date"))
			{
        			continue;
			}
			else
			{
			    try{
				
				propVal = propType.getConstructor(String.class).newInstance((String) propVal);
			    }
			    catch(Exception e)
			    {
				log.error(e,"");
			    }
			}

		    }
		}
	    // VO contains property with same name as excel
	    if(PropertyUtils.isWriteable(obj, headerName[i]))
	    {
		if(propType!= null && ConstantsCommon.STRING_IMPORT.equals(propType.getCanonicalName()))
		{
		    propVal=StringUtil.unEscapeCharactersInString(propVal.toString(), StringUtil.CSV_TYPE_ESCAPE_UNESCAPE);
		}
		PropertyUtils.setProperty(obj, headerName[i], propVal);
	    }
	    // VO doesn't contain property with the name of the excels' column
	    else if(excelAdditionnalCols.indexOf(headerName[i]) == -1 && !RepConstantsCommon.ORIGIN_BR_LST.contains(headerName[i]))
	    {
		classNameFilled = appendTableColumnsNames(excelAdditionnalCols, classNameFilled, headerName[i], tblName);
	    }
	}
	if(classNameFilled)
	{
	    excelAdditionnalCols.append(ConstantsCommon.NEW_LINE);
	}
	return obj;
	
    } 
    
    private void checkIfDbTableHasMoreColumns(String tblName, String[] headerName, StringBuffer dbAdditionnalCols)
	    throws BaseException
    {
	boolean classNameFilled = false;
	USER_TAB_COLSCO userTabColsCO = new USER_TAB_COLSCO();
	userTabColsCO.setTABLE_NAME(tblName);
	boolean missingProp;
	String colName;
	List<USER_TAB_COLSCO> tblColsName = returnTblColsName(userTabColsCO);
	if(tblColsName.size() > headerName.length && dbAdditionnalCols.indexOf(tblName) == -1)
	{
	    for(int i = 0; i < tblColsName.size(); i++)
	    {
		colName=tblColsName.get(i).getCOLUMN_NAME();
		missingProp = true;
		for(int j = 0; j < headerName.length; j++)
		{
		    if(colName.equals(headerName[j]))
		    {
			missingProp = false;
			break;
		    }
		}
		if(missingProp  && !RepConstantsCommon.ORIGIN_BR_LST.contains(colName))
		{
		    classNameFilled = appendTableColumnsNames(dbAdditionnalCols, classNameFilled, tblColsName.get(i)
			    .getCOLUMN_NAME(), tblName);
		}
	    }
	    // to have an extra line for every table encountered
	    if(classNameFilled)
	    {
		dbAdditionnalCols.append(ConstantsCommon.NEW_LINE);
	    }
	}
    }
    
    private boolean appendTableColumnsNames(StringBuffer usedBuf, boolean classNameFilled, String columnName,
	    String tblName) throws BaseException
    {
	boolean tmpClassFilled = classNameFilled;
	// check if the className is added to the message
	if(classNameFilled)
	{
	    usedBuf.append(columnName).append(RepConstantsCommon.COMMA);
	}
	else
	{
	    if(usedBuf.length() > 0)
	    {
		usedBuf.replace(usedBuf.lastIndexOf(RepConstantsCommon.COMMA), usedBuf
			.lastIndexOf(RepConstantsCommon.COMMA) + 1, RepConstantsCommon.EMPTY_STRING);
	    }
	    usedBuf.append(tblName + RepConstantsCommon.COLUMN + columnName).append(RepConstantsCommon.COMMA);
	    tmpClassFilled = true;
	}
	return tmpClassFilled;
    }
    
    /**
     * convert the csv file to ASCII in case the DB character set is CP850 then return the bufferReader of the csv file
     * @param  csvFile
     * @return bfRd
     */
    private BufferedReader transformFileToBfRd(File csvFile) throws IOException
    {
	String encodingType = "";
	String fileEncoding =ConstantsCommon.FILE_ENCODING;
	try
	{
	    byte[] fileBytes = FileUtil.readFileBytes(csvFile.getCanonicalPath());
	    encodingType = PathPropertyUtil.getPathRemotingProp("PathRemoting", "default.database.encoding");
	    if(RepConstantsCommon.ENCODING_TYPE_ASCII.equals(encodingType))
	    {
		fileEncoding = ConstantsCommon.FILE_ENCODING_CP1256;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		OutputStreamWriter writer = new OutputStreamWriter(baos, fileEncoding);
		writer.append(new String(fileBytes, CommonUtil.ENCODING));
		writer.close();
		fileBytes = baos.toByteArray();
	    }

	    InputStream inStr = new ByteArrayInputStream(fileBytes);
	    return new BufferedReader(new InputStreamReader(inStr, fileEncoding));
	}
	catch(Exception e)
	{
	    log.error(e,"");
	    return null;
	}
    }
    
    public void returnMissingCols(String[] headerArry,HashMap<String, List<String>> missingVOPropHM,String tblName) throws BaseException
    {
	USER_TAB_COLSCO userTabColsCO = new USER_TAB_COLSCO();
	userTabColsCO.setTABLE_NAME(tblName);
	List<USER_TAB_COLSCO> tblColsName = returnTblColsName(userTabColsCO);
	boolean missingProp;
	List<String> missingPropLst = new ArrayList<String>();
	
	for(int i=0;i<tblColsName.size();i++)
	{
	    missingProp =true;
	    for(int j=0;j<headerArry.length-1;j++)
	    {
		if(tblColsName.get(i).getCOLUMN_NAME().equals(headerArry[j]))
		{
		    missingProp=false;
		    break;
		}
	    }
	    if(missingProp)
	    {
		missingPropLst.add(tblColsName.get(i).getCOLUMN_NAME());
	    }
	}
	
	if(!missingPropLst.isEmpty())
	{
	    missingVOPropHM.put(tblName+".csv", missingPropLst);
	}
    }
    
    private void fillIrpHashTblRepVO(File csvFile,
	    LinkedHashMap<BigDecimal, List<IRP_HASH_TABLE_REPORTVO>> repHashTblRepHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_HASH_TABLE_REPORTVO irpHashTblRepVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object[] valueArray;
	List<IRP_HASH_TABLE_REPORTVO> hashTblRepList = new ArrayList<IRP_HASH_TABLE_REPORTVO>();
	
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    irpHashTblRepVO = new IRP_HASH_TABLE_REPORTVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    irpHashTblRepVO = (IRP_HASH_TABLE_REPORTVO) returnPropValue(irpHashTblRepVO, headerArry, valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    hashTblRepList.add(irpHashTblRepVO);
	}
	repHashTblRepHM.put(irpHashTblRepVO.getREPORT_ID(), hashTblRepList);
    }
    
    
    private void fillIrpClientRepVO(File csvFile, LinkedHashMap<String, List<IRP_CLIENT_REPORTVO>> irpTempClientRepHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_CLIENT_REPORTVO irpClientReportVO =null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List<IRP_CLIENT_REPORTVO> clientTblRepList = new ArrayList<IRP_CLIENT_REPORTVO>();

	returnMissingCols(headerArry,missingVOPropHM,tblName);
	
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    irpClientReportVO = new IRP_CLIENT_REPORTVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    irpClientReportVO = (IRP_CLIENT_REPORTVO) returnPropValue(irpClientReportVO, headerArry, valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    clientTblRepList.add(irpClientReportVO);
	}
	irpTempClientRepHM.put(irpClientReportVO.getREPORT_REF(), clientTblRepList);
	
    }
    public void fillDefaultClientRepVO( LinkedHashMap<String, List<IRP_CLIENT_REPORTVO>> irpTempClientRepHM,SessionCO sessionCO)
	    throws Exception
    {
	
	  //insert the default client if there is no data in IRP_CLIENT_REPORT
	IRP_CLIENT_REPORTVO irpClientReportVO =null;
	List<IRP_CLIENT_REPORTVO> clientTblRepList = new ArrayList<IRP_CLIENT_REPORTVO>();

	irpClientReportVO = new IRP_CLIENT_REPORTVO();
	try 
	{
        PTH_CLIENTSCO defaultClient = returnDefaultClient();
        irpClientReportVO.setCLIENT_ACRONYM(defaultClient.getPthClientsVO().getCLIENT_ACRONYM());
	irpClientReportVO.setAPP_NAME(RepConstantsCommon.IRP_CLIENT_DEFAULT );
	irpClientReportVO.setREPORT_REF(RepConstantsCommon.IRP_CLIENT_DEFAULT );
	clientTblRepList.add(irpClientReportVO);
	irpTempClientRepHM.put(RepConstantsCommon.IRP_CLIENT_DEFAULT, clientTblRepList);
	}
	catch(Exception e)
	{
		BaseException baseEx = new BaseException(MessageCodes.DEFAULT_CLIENT_NOTFOUND,e);
		String msgTrans[] =commonLibBO.translateErrorMessage(baseEx, sessionCO.getLanguage());
		throw new BOException(msgTrans[0]);
	}
	
    }
    
    
    public void fillPthClientsVO (File csvFile, LinkedHashMap<String, List<PTH_CLIENTSVO>> pthClientsHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
    throws Exception 
 {
	
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	PTH_CLIENTSVO pthClientsVO =null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List<PTH_CLIENTSVO> pthClientsList = new ArrayList<PTH_CLIENTSVO>();

	returnMissingCols(headerArry,missingVOPropHM,tblName);
	
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    pthClientsVO = new PTH_CLIENTSVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    pthClientsVO = (PTH_CLIENTSVO) returnPropValue(pthClientsVO, headerArry, valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    pthClientsList.add(pthClientsVO);
	}
	pthClientsHM.put(pthClientsVO.getCLIENT_ACRONYM(), pthClientsList);
	
    }

    public void fillIrpFolderVO(String repFolderName, File csvFile,
	    LinkedHashMap<BigDecimal, IRP_FOLDERVO> repFolderHM, HashMap<String, List<String>> missingVOPropHM,
	    String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols) throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_FOLDERVO repFolderVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object[] valueArray;
	//List<IRP_FOLDERVO> repFolderList = new ArrayList<IRP_FOLDERVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    repFolderVO = new IRP_FOLDERVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    repFolderVO = (IRP_FOLDERVO) returnPropValue(repFolderVO, headerArry, valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    repFolderHM.put(repFolderVO.getFOLDER_CODE(), repFolderVO);
	}
    }
    
    public void fillRepOptExVO(String repFolderName, File csvFile,
	    LinkedHashMap<String, List<OPT_EXTENDEDVO>> repOptExHM, HashMap<String, List<String>> missingVOPropHM,
	    String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols) throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	OPT_EXTENDEDVO repOptExVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List <OPT_EXTENDEDVO> optRepExList = new ArrayList<OPT_EXTENDEDVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    repOptExVO = new OPT_EXTENDEDVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    repOptExVO = (OPT_EXTENDEDVO) returnPropValue(repOptExVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    optRepExList.add(repOptExVO);
	}
	repOptExHM.put(repFolderName, optRepExList);
    }
    
    
    

    public void fillRepOptVO(String repFolderName, File csvFile, LinkedHashMap<String, OPTVO> repOptHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	OPTVO repOptVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List <OPTVO> optRepList = new ArrayList<OPTVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    repOptVO = new OPTVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    repOptVO = (OPTVO) returnPropValue(repOptVO,headerArry,valueArray,excelAdditionnalCols, dbAdditionnalCols);
	    optRepList.add(repOptVO);
	    repOptHM.put(repOptVO.getPROG_REF()+"_"+repOptVO.getAPP_NAME(), repOptVO);
	}
    }
    
    
    
    public void fillIrpSubRepVO(File csvFile, LinkedHashMap<BigDecimal, List<IRP_SUB_REPORTVO>> irpSubRepHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_SUB_REPORTVO irpSubRepVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List <IRP_SUB_REPORTVO> subRepList = new ArrayList<IRP_SUB_REPORTVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    irpSubRepVO = new IRP_SUB_REPORTVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    irpSubRepVO = (IRP_SUB_REPORTVO) returnPropValue(irpSubRepVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    subRepList.add(irpSubRepVO);
	}
	irpSubRepHM.put(irpSubRepVO.getREPORT_ID(), subRepList);
    }
    
    
    public void fillIrpRepProcParamVO(File csvFile,
	    LinkedHashMap<BigDecimal, List<IRP_REP_PROC_PARAMSVO>> irpRepProcParamHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_REP_PROC_PARAMSVO irpRepProcParamVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List <IRP_REP_PROC_PARAMSVO> repProcParamList = new ArrayList<IRP_REP_PROC_PARAMSVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    irpRepProcParamVO = new IRP_REP_PROC_PARAMSVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    irpRepProcParamVO = (IRP_REP_PROC_PARAMSVO) returnPropValue(irpRepProcParamVO,headerArry,valueArray,excelAdditionnalCols,
		     dbAdditionnalCols);
	    repProcParamList.add(irpRepProcParamVO);
	}
	irpRepProcParamHM.put(irpRepProcParamVO.getREPORT_ID(), repProcParamList);
    }
    
    
    public void fillIrpRepProcVO(File csvFile, LinkedHashMap<BigDecimal, List<IRP_REP_PROCVO>> irpRepProcHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_REP_PROCVO irpRepProcVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes 
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List <IRP_REP_PROCVO> repProcList = new ArrayList<IRP_REP_PROCVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes 
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    irpRepProcVO = new IRP_REP_PROCVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    irpRepProcVO = (IRP_REP_PROCVO) returnPropValue(irpRepProcVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    repProcList.add(irpRepProcVO);
	}
	irpRepProcHM.put(irpRepProcVO.getREPORT_ID(), repProcList);
    }
     
    public void fillIrpQryArgMappingVO(File csvFile,
	    LinkedHashMap<BigDecimal, List<IRP_QUERY_ARG_MAPPINGVO>> irpQryArgMappingHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_QUERY_ARG_MAPPINGVO qryArgMappingVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes 
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List <IRP_QUERY_ARG_MAPPINGVO> qryArgMapList = new ArrayList<IRP_QUERY_ARG_MAPPINGVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes 
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    qryArgMappingVO = new IRP_QUERY_ARG_MAPPINGVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    qryArgMappingVO = (IRP_QUERY_ARG_MAPPINGVO) returnPropValue(qryArgMappingVO,headerArry,valueArray,excelAdditionnalCols
		    ,dbAdditionnalCols);
	    qryArgMapList.add(qryArgMappingVO);
	}
	irpQryArgMappingHM.put(qryArgMappingVO.getREPORT_ID(), qryArgMapList);
    }
    
    
    public void fillIrpRepQryVO(BigDecimal oldRepId, File csvFile,
	    LinkedHashMap<BigDecimal, List<IRP_AD_HOC_QUERYVO>> irpAdHocQueryHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_AD_HOC_QUERYVO qryVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List <IRP_AD_HOC_QUERYVO> qryList = new ArrayList<IRP_AD_HOC_QUERYVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    qryVO = new IRP_AD_HOC_QUERYVO();
	    
	   //annasuccar- 08/09/2014: addition of "?>" to the regexp to solve java.lang.StackOverflowError
	  // valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	   valueArray = strLine.split(",(?=(?>[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	   
	   String [] valueArrayStr = new String[valueArray.length];
	    for(int it = 0; it < headerArry.length-1; it++)
	    {
		if(valueArray[it].toString().startsWith("\"") && valueArray[it].toString().endsWith("\""))
		{
		    valueArrayStr[it] = StringUtil.unEscapeCharactersInString(valueArray[it].toString(),StringUtil.CSV_TYPE_ESCAPE_UNESCAPE);
		}
		else
		{
		    valueArrayStr[it]=(String) valueArray[it];
		}
	    }
	    
	    qryVO = (IRP_AD_HOC_QUERYVO) returnPropValue(qryVO,headerArry,valueArrayStr,excelAdditionnalCols,dbAdditionnalCols);
	    qryList.add(qryVO);
	}
	irpAdHocQueryHM.put(oldRepId, qryList);
    }
    
    public void fillIrpRepArgVO(File csvFile, LinkedHashMap<BigDecimal, List<IRP_REP_ARGUMENTSVO>> irpRepArgHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_REP_ARGUMENTSVO argVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	List <IRP_REP_ARGUMENTSVO> argList = new ArrayList<IRP_REP_ARGUMENTSVO>();
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    argVO = new IRP_REP_ARGUMENTSVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    argVO = (IRP_REP_ARGUMENTSVO) returnPropValue(argVO,headerArry,valueArray,excelAdditionnalCols, dbAdditionnalCols);
	    argList.add(argVO);
	}
	irpRepArgHM.put(argVO.getREPORT_ID(), argList);
    }
    
    public boolean fillIrpAddHocRepVO(File csvFile,
	    LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> irpAddHocRepHM, String jrxmPath,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, String importOption,
	    HashMap<BigDecimal, BigDecimal> repIdOldNewMap,
	    LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> repIdOldNewReplaceMap,
	    StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols) throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_AD_HOC_REPORTVOWithBLOBs repVO;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    repVO = new IRP_AD_HOC_REPORTVOWithBLOBs();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    repVO = (IRP_AD_HOC_REPORTVOWithBLOBs) returnPropValue(repVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    //added the below to check if a report already exists in db so we don't fill the irpAddHocRepHM
	    //in case the user chooses to skip.
	    if(!RepConstantsCommon.STOP.equals(importOption))
	    {
		IRP_AD_HOC_REPORTVOWithBLOBs repFromDbVO = designerDAO.retExistRepInDb(repVO);
		if(repFromDbVO!=null)
		{
		    if(RepConstantsCommon.SKIP.equals(importOption))
		    {
			repIdOldNewMap.put(repVO.getREPORT_ID(),repFromDbVO.getREPORT_ID());
			return true;
		    }
		    else
		    {
			repIdOldNewReplaceMap.put(repVO.getREPORT_ID(),repFromDbVO);
		    }
		}
	    }
	    byte[] jrxml = FileUtil.readFileBytes(jrxmPath);
	    repVO.setJRXML_FILE(jrxml);
	    irpAddHocRepHM.put(repVO.getREPORT_ID(), repVO);
	}
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	return false;
    }

    
    public void fillFtrExpXlsCsv(File csvFile, LinkedHashMap<String, FTR_EXP_XLSVO> ftrExpXlsHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	FTR_EXP_XLSVO ftrExpXlsVO;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    ftrExpXlsVO = new FTR_EXP_XLSVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    ftrExpXlsVO = (FTR_EXP_XLSVO) returnPropValue(ftrExpXlsVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    ftrExpXlsHM.put(ftrExpXlsVO.getREP_REF(), ftrExpXlsVO);
	}
    }
    
    public void fillQueriesVO(File csvFile, HashMap<BigDecimal, IRP_AD_HOC_QUERYVO> irpTempAdHocQueryHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {

	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_AD_HOC_QUERYVO qryVO;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	String [] valueArrayStr = new String[headerArry.length];
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    qryVO = new IRP_AD_HOC_QUERYVO();
	    strLine =  strLine.replaceAll("\"\"", "\"");
	
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    
	    for(int it = 0; it < headerArry.length-1; it++)
	    {
		if(valueArray[it].toString().startsWith("\"") && valueArray[it].toString().endsWith("\""))
		{
		    valueArrayStr[it] = valueArray[it].toString().substring(1, valueArray[it].toString().length()-1);
		    valueArrayStr[it] = StringUtil.unEscapeCharactersInString(valueArrayStr[it],StringUtil.CSV_TYPE_ESCAPE_UNESCAPE);
		}
		else
		{
		    valueArrayStr[it]=(String) valueArray[it];
		}
	    }
	    
	    qryVO = (IRP_AD_HOC_QUERYVO) returnPropValue(qryVO,headerArry,valueArrayStr,excelAdditionnalCols, dbAdditionnalCols);
	    irpTempAdHocQueryHM.put(qryVO.getQUERY_ID(), qryVO);
	}
	
    }

    
    public void fillConnectionVO(File csvFile, HashMap<BigDecimal, IRP_CONNECTIONSVO> irpTempIrpConnHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_CONNECTIONSVO conVO;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    conVO = new IRP_CONNECTIONSVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    conVO = (IRP_CONNECTIONSVO) returnPropValue(conVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    irpTempIrpConnHM.put(conVO.getCONN_ID(), conVO);
	}
    }
  
    /**
     * method that fill the rep_snapshot_paramVOs from the csv file 
     * @author: Nabiha Ojeil
     * @param oldRepId, csvFile,repSnpParamHM, issingVOPropHM,tblName,excelAdditionnalCols,dbAdditionnalCols
     */
    private void fillRepSnpParamVO(BigDecimal oldRepId,File csvFile, HashMap<BigDecimal, List<REP_SNAPSHOT_PARAMVO>> repSnpParamHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
		    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	REP_SNAPSHOT_PARAMVO snpParamVO;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	 List<REP_SNAPSHOT_PARAMVO> lstVO=new ArrayList<REP_SNAPSHOT_PARAMVO>() ;
	//limit the size of the line to be below of 200 MB = 200000000 bytes 
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    snpParamVO = new REP_SNAPSHOT_PARAMVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    snpParamVO = (REP_SNAPSHOT_PARAMVO) returnPropValue(snpParamVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    lstVO.add(snpParamVO);
	}
	repSnpParamHM.put(oldRepId,lstVO);
    }
    
    /**
     * method that fills the IRP_SNAPSHOT_PARAM_MAPPING from the csv file 
     * @author: Nabiha Ojeil
     * @param oldRepId, csvFile,irpSnpParamMappingHM, issingVOPropHM,tblName,excelAdditionnalCols,dbAdditionnalCols
     */
    private void fillIrpSnpParamMappingVO(BigDecimal oldRepId,File csvFile, HashMap<BigDecimal, List<IRP_SNAPSHOT_PARAM_MAPPINGVO>> irpSnpParamMappingHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
		    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_SNAPSHOT_PARAM_MAPPINGVO snpParamMappVO;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	List<IRP_SNAPSHOT_PARAM_MAPPINGVO> lstVO=new ArrayList<IRP_SNAPSHOT_PARAM_MAPPINGVO>() ;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    snpParamMappVO = new IRP_SNAPSHOT_PARAM_MAPPINGVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    snpParamMappVO = (IRP_SNAPSHOT_PARAM_MAPPINGVO) returnPropValue(snpParamMappVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    lstVO.add(snpParamMappVO);
	}
	irpSnpParamMappingHM.put(oldRepId,lstVO);
    }

  
    public void fillIrpProcVO(File csvFile, HashMap<BigDecimal, IRP_PROCVO> irpTempIrpProcHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_PROCVO procVO;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    procVO = new IRP_PROCVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    procVO = (IRP_PROCVO) returnPropValue(procVO,headerArry,valueArray,excelAdditionnalCols,dbAdditionnalCols);
	    irpTempIrpProcHM.put(procVO.getPROC_ID(), procVO);
	}
	
    }
    
    /**
     * Method that exports the contents of hashtable
     * @param csvFile
     * @param irpTempHashTblHM
     * @param missingVOPropHM
     * @param tblName
     * @param excelAdditionnalCols
     * @param dbAdditionnalCols
     * @throws Exception
     */
    private void fillIrpHashTblVO(File csvFile, HashMap<BigDecimal, IRP_HASH_TABLEVO> irpTempHashTblHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,StringBuffer dbAdditionnalCols)
	    throws Exception
    {
	
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_HASH_TABLEVO hashTblVO;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry=strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object [] valueArray ;
	
	returnMissingCols(headerArry,missingVOPropHM,tblName);
	
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    hashTblVO = new IRP_HASH_TABLEVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    String [] valueArrayStr = new String[valueArray.length];
	    for(int it = 0; it < headerArry.length-1; it++)
	    {
		if(headerArry[it].equals(RepConstantsCommon.HASH_TABLE_SCRIPT_COL))
		{
		    valueArrayStr[it] = StringUtil.unEscapeCharactersInString(valueArray[it].toString(),StringUtil.CSV_TYPE_ESCAPE_UNESCAPE);
		}
		else
		{
		    valueArrayStr[it]=(String) valueArray[it];
		}
	    }
	    hashTblVO = (IRP_HASH_TABLEVO) returnPropValue(hashTblVO,headerArry,valueArrayStr,excelAdditionnalCols,dbAdditionnalCols);
	    irpTempHashTblHM.put(hashTblVO.getHASH_TABLE_ID(), hashTblVO);
	}
	
	
    }

    /**
     * Method that reads the content of the constraints excel file
     * @param csvFile
     * @param irpRepArgHM
     * @param missingVOPropHM
     * @param tblName
     * @param excelAdditionnalCols
     * @param dbAdditionnalCols
     * @throws Exception
     */
    private void fillIrpRepArgCnstrVO(File csvFile,
	    LinkedHashMap<BigDecimal, List<IRP_REP_ARG_CONSTRAINTSVO>> argConstrHM,
	    HashMap<String, List<String>> missingVOPropHM, String tblName, StringBuffer excelAdditionnalCols,
	    StringBuffer dbAdditionnalCols) throws Exception
    {
	BufferedReader bfRd = transformFileToBfRd(csvFile);
	String strLine;
	IRP_REP_ARG_CONSTRAINTSVO argCnstrVO = null;
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	strLine = PathFileSecure.readLine(bfRd, 200000000);
	String[] headerArry = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	Object[] valueArray;
	List<IRP_REP_ARG_CONSTRAINTSVO> argCnstrList = new ArrayList<IRP_REP_ARG_CONSTRAINTSVO>();
	returnMissingCols(headerArry, missingVOPropHM, tblName);
	//limit the size of the line to be below of 200 MB = 200000000 bytes
	while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	{
	    argCnstrVO = new IRP_REP_ARG_CONSTRAINTSVO();
	    valueArray = strLine.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)", -1);
	    argCnstrVO = (IRP_REP_ARG_CONSTRAINTSVO) returnPropValue(argCnstrVO, headerArry, valueArray,
		    excelAdditionnalCols, dbAdditionnalCols);
	    argCnstrList.add(argCnstrVO);
	}
	argConstrHM.put(argCnstrVO.getREPORT_ID(), argCnstrList);
    }
    
    @Override
    public HashMap<String, Object> importRepFiles(String user, BigDecimal compCode, BigDecimal branchCode,
	    String importOption, AXSCO axsCO, SessionCO sessionCO, boolean useExistingOptAccess,
	    boolean overwriteTrans, boolean updateVersionIfEqual, String zipPassword, File upload, String fileName,String pageRef
	    ,boolean keepSchedsHyperlinks) throws BaseException
    {
	String repositoryPath;
	String reportFolder;
	//called from the automated screen
	if(upload==null)
	{
	     repositoryPath = "";
	     reportFolder = fileName;
	}
	else
	{
	    repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    reportFolder = repositoryPath + "/" + RepConstantsCommon.IMPORT_REP + "/" + fileName;
	}
	try
	{
	    boolean existZipPass;
	// save file to disk
	 if(upload==null)
	 {
	     existZipPass = FileZipUtil.checkIfZipProtected(fileName,false);
	 }
	 else
	 {
	     FileUtils.copyFile(upload, new PathFileSecure(reportFolder));
		 
	     existZipPass = FileZipUtil.checkIfZipProtected("/" + ReportingConstantsCommon.reportingFolder + "/"
		    + RepConstantsCommon.IMPORT_REP + "/" + fileName);  
	 }
	    if(existZipPass && "".equals(zipPassword))
	    {
		if(upload == null)
		{
		    ArrayList<BaseException> exceptionsLst = new ArrayList<BaseException>();
		    BaseException b = new BaseException(MessageCodes.MISSING_ZIP_CODE, "");
		    exceptionsLst.add(b);
		    throw new BOException(exceptionsLst);
		}
		else
		{
		    throw new BOException(MessageCodes.MISSING_ZIP_CODE);
		}
	    }
	    if(upload == null)
	    {
		FileZipUtil.extractProtectedZipFolder(fileName, zipPassword,false);
	    }
	    else
	    {
		FileZipUtil.extractProtectedZipFolder("/" + ReportingConstantsCommon.reportingFolder + "/"
			+ RepConstantsCommon.IMPORT_REP + "/" + fileName, zipPassword);
	    }
	    new PathFileSecure(reportFolder);
	  
	   
	}
	catch(BOException e)
	{
	    throw e;
	}
	catch(Exception e)
	{
		throw new BOException(e);
	}
	String  unZippedRepFldr = reportFolder.substring(0, reportFolder.length() - 4); 
	File repFolderList = null,stepsFile = null;
	try {
		repFolderList = new PathFileSecure(unZippedRepFldr);
		stepsFile = new PathFileSecure(repFolderList + "/" + RepConstantsCommon.STEPS_TXT);
		if(!stepsFile.exists())
		{
		    throw new BOException(MessageCodes.WRONG_STEPS_FILE);
		}
	} catch (Exception e1) {
		log.error(e1, e1.getMessage());
	}
	// irpTempAdHocQueryHM.put(qryVO.getQUERY_ID(), qryVO);
	HashMap<BigDecimal, IRP_AD_HOC_QUERYVO> irpTempAdHocQueryHM = new HashMap<BigDecimal, IRP_AD_HOC_QUERYVO>();
	// irpTempIrpConnHM.put(conVO.getCONN_ID(), conVO);
	HashMap<BigDecimal, IRP_CONNECTIONSVO> irpTempIrpConnHM = new HashMap<BigDecimal, IRP_CONNECTIONSVO>();
	// irpTempIrpProcHM.put(procVO.getPROC_ID(), procVO);
	HashMap<BigDecimal, IRP_PROCVO> irpTempIrpProcHM = new HashMap<BigDecimal, IRP_PROCVO>();
	// irpTempHashTblHM.put(hashTblVO.getHASH_TABLE_ID(), hashTblVO);
	HashMap<BigDecimal, IRP_HASH_TABLEVO> irpTempHashTblHM = new HashMap<BigDecimal, IRP_HASH_TABLEVO>();
	// report_id.contains reports to be inserted
	LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> irpAddHocRepHM = new LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs>();
	// ftrExpXlsHM.put(ftrExpXlsVO.getREP_REF(), ftrExpXlsVO);
	LinkedHashMap<String, FTR_EXP_XLSVO> ftrExpXlsHM = new LinkedHashMap<String, FTR_EXP_XLSVO>();
	// report_id
	LinkedHashMap<BigDecimal, List<IRP_REP_ARGUMENTSVO>> irpRepArgHM = new LinkedHashMap<BigDecimal, List<IRP_REP_ARGUMENTSVO>>();
	// report_id
	LinkedHashMap<BigDecimal, List<IRP_AD_HOC_QUERYVO>> irpAdHocQueryHM = new LinkedHashMap<BigDecimal, List<IRP_AD_HOC_QUERYVO>>();
	// report_id
	LinkedHashMap<BigDecimal, List<IRP_QUERY_ARG_MAPPINGVO>> irpQryArgMappingHM = new LinkedHashMap<BigDecimal, List<IRP_QUERY_ARG_MAPPINGVO>>();
	// report_id
	LinkedHashMap<BigDecimal, List<IRP_REP_PROCVO>> irpRepProcHM = new LinkedHashMap<BigDecimal, List<IRP_REP_PROCVO>>();
	// report_id
	LinkedHashMap<BigDecimal, List<IRP_REP_PROC_PARAMSVO>> irpRepProcParamHM = new LinkedHashMap<BigDecimal, List<IRP_REP_PROC_PARAMSVO>>();
	// report_id
	LinkedHashMap<BigDecimal, List<IRP_SUB_REPORTVO>> irpSubRepHM = new LinkedHashMap<BigDecimal, List<IRP_SUB_REPORTVO>>();
	// repOptVO.getPROG_REF()+"_"+repOptVO.getAPP_NAME()
	LinkedHashMap<String, OPTVO> repOptHM = new LinkedHashMap<String, OPTVO>();
	// repFolderName
	LinkedHashMap<String, List<OPT_EXTENDEDVO>> repOptExHM = new LinkedHashMap<String, List<OPT_EXTENDEDVO>>();
	// repFolderVO.getFOLDER_CODE()
	LinkedHashMap<BigDecimal, IRP_FOLDERVO> repFolderHM = new LinkedHashMap<BigDecimal, IRP_FOLDERVO>();
	// report_id
	LinkedHashMap<BigDecimal, List<IRP_HASH_TABLE_REPORTVO>> repHashTblRepHM = new LinkedHashMap<BigDecimal, List<IRP_HASH_TABLE_REPORTVO>>();
	LinkedHashMap<String, List<IRP_CLIENT_REPORTVO>> irpTempClientRepHM = new LinkedHashMap<String,List<IRP_CLIENT_REPORTVO>>();
	LinkedHashMap<String, List<PTH_CLIENTSVO>> pthClientsHM = new LinkedHashMap<String,List<PTH_CLIENTSVO>>();
	
	HashMap<String, String> imgSrcDestPathHashMap = new HashMap<String, String>();
	// this hm is used to store the missing prop between db and csv
	HashMap<String, List<String>> missingVOPropHM = new HashMap<String, List<String>>();
	//ArrayList<TranslationCO> lstTrans = new ArrayList<TranslationCO>();
	HashMap<String,Object> transMap = new HashMap<String, Object>();
	// added below map to store old/new id of report already in db to be
	// skipped
	HashMap<BigDecimal, BigDecimal> repIdOldNewMap = new HashMap<BigDecimal, BigDecimal>();
	// contains mapping old id (excel) and dbrepVO of the reports already in
	// db.will be used to delete them prior to insertion
	LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> repIdOldNewReplaceMap = new LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs>();
	LinkedHashMap<BigDecimal,List<IRP_REP_ARG_CONSTRAINTSVO>> argConstrHM = new LinkedHashMap<BigDecimal, List<IRP_REP_ARG_CONSTRAINTSVO>>();
	LinkedHashMap<BigDecimal, List<REP_SNAPSHOT_PARAMVO>> repSnpParamHM = new LinkedHashMap<BigDecimal, List<REP_SNAPSHOT_PARAMVO>>();
	LinkedHashMap<BigDecimal, List<IRP_SNAPSHOT_PARAM_MAPPINGVO>> irpSnpParamMappingHM = new LinkedHashMap<BigDecimal, List<IRP_SNAPSHOT_PARAM_MAPPINGVO>>();
	// temporary hashmap for the already existing opts
	StringBuffer excelAdditionnalCols = new StringBuffer("");
	StringBuffer dbAdditionnalCols = new StringBuffer("");
	
	try
	{
	    BufferedReader bfRd = transformFileToBfRd(stepsFile);
	    String strLine;
	    File pthClientsCsv;
	    //limit the size of the line to be below of 200 MB = 200000000 bytes
	    while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	    {
		String repFolderName = strLine;
		// IRP_AD_HOC_REPORT
		File irpAddHocRepCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_AD_HOC_REPORT_CSV);
		if(!irpAddHocRepCsv.exists())
		{
		    throw new BOException(
			    "IRP_AD_HOC_REPORT.csv file does not exist. Please make sure to import an exported zip file.");
		}

		String jrxml = repFolderList + "/" + repFolderName + "/" + repFolderName + ".jrxml";
		if(!(new PathFileSecure(jrxml).exists()))
		{
		    throw new BOException("jrxml file does not exist. Please make sure to import an exported zip file.");
		}
		Boolean skipReport = fillIrpAddHocRepVO(irpAddHocRepCsv, irpAddHocRepHM, jrxml, missingVOPropHM,
			RepConstantsCommon.IRP_AD_HOC_REPORT, importOption, repIdOldNewMap, repIdOldNewReplaceMap,
			excelAdditionnalCols,dbAdditionnalCols);

		if(skipReport)
		{
		    continue;
		}
		BigDecimal oldRepId = null;
		IRP_AD_HOC_REPORTVOWithBLOBs repVO=new IRP_AD_HOC_REPORTVOWithBLOBs();
		for(Entry<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> entry : irpAddHocRepHM.entrySet())
		{
		    oldRepId = entry.getKey();
		    repVO = entry.getValue();

		    if("1".equals(repVO.getREPORT_TYPE()))
		    {
			// FTR_EXP_XLS
			File ftrExpXlsCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
				+ RepConstantsCommon.FTR_EXP_XLS_CSV);
			if(ftrExpXlsCsv.exists())
			{
			    fillFtrExpXlsCsv(ftrExpXlsCsv, ftrExpXlsHM, missingVOPropHM,
				    RepConstantsCommon.FTR_EXP_XLS, excelAdditionnalCols,dbAdditionnalCols);
			}
		    }
		    // Read the report version
			//File versionFile = new PathFileSecure(repFolderList+"/"+repFolderName+"/"+RepConstantsCommon.VERSION_TXT);
		    String versionFileName = repFolderList + "/" + repFolderName + "/" + RepConstantsCommon.VERSION_TXT;
		    if (FileUtil.existFile(versionFileName)) {
			    Properties prop = null;
			    String propValue = null;
			    prop = new Properties();
			    InputStreamReader isr = new InputStreamReader(new FileInputStream(versionFileName),
				    FileUtil.DEFAULT_FILE_ENCODING);
			    prop.load(isr);
			    isr.close();

			    if(prop == null)
			    {
				repVO.setREPORT_VERSION(ConstantsCommon.ZERO);
				repVO.setREPORT_MODIFIED_YN(ConstantsCommon.ZERO);
			    }
			    else
			    {
				propValue = prop.getProperty(RepConstantsCommon.VERSION_KEY);
				repVO.setREPORT_VERSION(propValue);
				propValue = prop.getProperty(RepConstantsCommon.VERSION_DATE_KEY);
				DateFormat df = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
				if (!propValue.isEmpty())
				{
				    Date versionDate = df.parse(propValue);
				    repVO.setVERSION_DATE(versionDate);
				}
				propValue = prop.getProperty(RepConstantsCommon.VERSION_MODIFIED_KEY);
				repVO.setREPORT_MODIFIED_YN(propValue);
			    }
			    entry.setValue(repVO);
				}
				else 
				{
					BaseException baseEx = new BaseException(MessageCodes.VERSION_FILE_NOT_FOUND,new  String[]{repFolderName + "/" + RepConstantsCommon.VERSION_TXT});
					String msgTrans[] =commonLibBO.translateErrorMessage(baseEx, sessionCO.getLanguage());
					throw new BOException(msgTrans[0]);
				}

		
		}
		
		// IRP_REP_ARGUMENTS
		File irpRepArgCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_REP_ARGUMENTS_CSV);
		if(irpRepArgCsv.exists())
		{
		    fillIrpRepArgVO(irpRepArgCsv, irpRepArgHM, missingVOPropHM, RepConstantsCommon.IRP_REP_ARGUMENTS,
			    excelAdditionnalCols,dbAdditionnalCols);
		}
		// IRP_AD_HOC_QUERY
		File irpRepQryCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_AD_HOC_QUERY_CSV);
		if(irpRepQryCsv.exists())
		{
		    fillIrpRepQryVO(oldRepId, irpRepQryCsv, irpAdHocQueryHM, missingVOPropHM,
			    RepConstantsCommon.IRP_AD_HOC_QUERY, excelAdditionnalCols,dbAdditionnalCols);
		}
		
		//REP_SNAPSHOT_PARAM
		File irpRepSnpParamCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"+ RepConstantsCommon.REP_SNAPSHOT_PARAM_CSV);
		if(irpRepSnpParamCsv.exists())
		{
		    fillRepSnpParamVO(oldRepId,irpRepSnpParamCsv, repSnpParamHM, missingVOPropHM,
			    RepConstantsCommon.REP_SNAPSHOT_PARAM, excelAdditionnalCols, dbAdditionnalCols);
		}

		//IRP_SNAPSHOT_PARAM_MAPPINGVO
		File irpRepSnpParamMappingCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"+ RepConstantsCommon.IRP_SNAPSHOT_PARAM_MAPPING_CSV);
		if(irpRepSnpParamMappingCsv.exists())
		{
		    fillIrpSnpParamMappingVO(oldRepId,irpRepSnpParamMappingCsv, irpSnpParamMappingHM, missingVOPropHM,
			    RepConstantsCommon.IRP_SNAPSHOT_PARAM_MAPPING, excelAdditionnalCols, dbAdditionnalCols);
		}

		// IRP_QUERY_ARG_MAPPING
		File irpQryArgMappingCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_QUERY_ARG_MAPPING_CSV);
		if(irpQryArgMappingCsv.exists())
		{
		    fillIrpQryArgMappingVO(irpQryArgMappingCsv, irpQryArgMappingHM, missingVOPropHM,
			    RepConstantsCommon.IRP_QUERY_ARG_MAPPING, excelAdditionnalCols,dbAdditionnalCols);
		}
		// IRP_REP_PROC
		File irpRepProcCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_REP_PROC_CSV);
		if(irpRepProcCsv.exists())
		{
		    fillIrpRepProcVO(irpRepProcCsv, irpRepProcHM, missingVOPropHM, RepConstantsCommon.IRP_REP_PROC,
			    excelAdditionnalCols,dbAdditionnalCols);
		}
		// IRP_REP_PROC_PARAMS
		File irpRepProcParamCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_REP_PROC_PARAMS_CSV);
		if(irpRepProcParamCsv.exists())
		{
		    fillIrpRepProcParamVO(irpRepProcParamCsv, irpRepProcParamHM, missingVOPropHM,
			    RepConstantsCommon.IRP_REP_PROC_PARAMS, excelAdditionnalCols,dbAdditionnalCols);
		}
		// IRP_SUB_REPORT
		File irpSubRepCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_SUB_REPORT_CSV);
		if(irpSubRepCsv.exists())
		{
		    fillIrpSubRepVO(irpSubRepCsv, irpSubRepHM, missingVOPropHM, RepConstantsCommon.IRP_SUB_REPORT,
			    excelAdditionnalCols,dbAdditionnalCols);
		}
		// OPT
		File repOptCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/" + RepConstantsCommon.OPT_CSV);
		if(!repOptCsv.exists())
		{
		    throw new BOException(
			    "OPT.csv file does not exist. Please make sure to import an exported zip file.");
		}
		fillRepOptVO(repFolderName, repOptCsv, repOptHM, missingVOPropHM, RepConstantsCommon.OPT,
			excelAdditionnalCols,dbAdditionnalCols);
		// OPT_EXTENDED
		File repOptExCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.OPT_EXTENDED_CSV);
		if(!repOptExCsv.exists())
		{
		    throw new BOException(
			    "OPT_EXTENDED.csv file does not exist. Please make sure to import an exported zip file.");
		}
		fillRepOptExVO(repFolderName, repOptExCsv, repOptExHM, missingVOPropHM,
			RepConstantsCommon.OPT_EXTENDED, excelAdditionnalCols,dbAdditionnalCols);
		// IRP_FOLDER
		File irpFolderCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_FOLDER_CSV);
		if(irpFolderCsv.exists())
		{
		    fillIrpFolderVO(repFolderName, irpFolderCsv, repFolderHM, missingVOPropHM,
			    RepConstantsCommon.IRP_FOLDER, excelAdditionnalCols,dbAdditionnalCols);
		}
		// IRP_HASH_TABLE_REPORT
		File irpHashTblRepCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_HASH_TABLE_REPORT_CSV);
		if(irpHashTblRepCsv.exists())
		{
		    fillIrpHashTblRepVO(irpHashTblRepCsv, repHashTblRepHM, missingVOPropHM,
			    RepConstantsCommon.IRP_HASH_TABLE_REPORT, excelAdditionnalCols,dbAdditionnalCols);
		}
		
		// IRP_REP_ARG_CONSTRAINTS
		File irpRepArgConstrCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.IRP_REP_ARG_CONSTRAINTS_CSV);
		if(irpRepArgConstrCsv.exists())
		{
		    fillIrpRepArgCnstrVO(irpRepArgConstrCsv, argConstrHM, missingVOPropHM,
			    RepConstantsCommon.IRP_REP_ARG_CONSTRAINTS, excelAdditionnalCols,dbAdditionnalCols);
		}
		
		//PTH_CLIENT
		pthClientsCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/" + RepConstantsCommon.PTH_CLIENTS_CSV);
		if(pthClientsCsv.exists())
		{
		    fillPthClientsVO(pthClientsCsv, pthClientsHM, missingVOPropHM,
			    RepConstantsCommon.PTH_CLIENTS,excelAdditionnalCols,dbAdditionnalCols);
		}
		
		//IRP_REP_CLIENT
		File irpClientRepCsv = new PathFileSecure(repFolderList + "/" + repFolderName + "/" + RepConstantsCommon.IRP_CLIENT_REPORT_CSV);
		
		
		if(irpClientRepCsv.exists())
		{
		    fillIrpClientRepVO(irpClientRepCsv, irpTempClientRepHM, missingVOPropHM,
			    RepConstantsCommon.IRP_CLIENT_REPORT,excelAdditionnalCols,dbAdditionnalCols);
		}
		else 
		{ try
		  {
		    fillDefaultClientRepVO(irpTempClientRepHM,sessionCO);
		  }
		  catch(Exception e)
		 {
		    throw new BOException(e);
		 }
			
		}	
		
		File imgFolderPath = new PathFileSecure(repFolderList.toString() + "/" + repFolderName + "/images");
		if(imgFolderPath.exists())
		{
		    File[] imgLst = imgFolderPath.listFiles();
		    for(int i = 0; i < imgLst.length; i++)
		    {
			String concatImag = imgLst[i].toString();
			String imgName = concatImag.substring(imgFolderPath.toString().length() + 1, imgLst[i]
				.toString().length());
			imgSrcDestPathHashMap.put(imgName, imgLst[i].toString());
		    }
		}

		// fill the translation CO
		File transFile = new PathFileSecure(repFolderList + "/" + repFolderName + "/"
			+ RepConstantsCommon.TRANSLATION_CSV);
		if(transFile.exists())
		{
			//limit the size of the file to be below of 200 MB = 200000000 bytes
		    byte[] fileBytes = PathFileSecure.readFileToByteArray(transFile,200000000);
		    TranslationCO translationCO = new TranslationCO();
		    translationCO.setImportedBytes(fileBytes);
		    translationCO.setOverwriteGroup(String.valueOf(overwriteTrans));
		    translationCO.setOverwriteLabel(String.valueOf(overwriteTrans));
		    transMap.put(repVO.getPROG_REF()+"_"+repVO.getAPP_NAME(),translationCO);
		    //lstTrans.add(translationCO);
		}
	    }

	    if(!irpAddHocRepHM.isEmpty())
	    {
		File repFolder;
		File irpAddHocQryCsv;
		File irpConnCsv;
		File irpAddHocProcCsv;
		File irpHashTblCsv;
		

		repFolder = new PathFileSecure(repFolderList.toString());
		irpAddHocQryCsv = new PathFileSecure(repFolder + "/" + RepConstantsCommon.IRP_AD_HOC_QUERY_CSV);
		irpConnCsv = new PathFileSecure(repFolder + "/" + RepConstantsCommon.IRP_CONNECTIONS_CSV);
		irpAddHocProcCsv = new PathFileSecure(repFolder + "/" + RepConstantsCommon.IRP_PROC_CSV);
		irpHashTblCsv = new PathFileSecure(repFolder + "/" + RepConstantsCommon.IRP_HASH_TABLE_CSV);
		pthClientsCsv = new PathFileSecure(repFolder + "/"  + RepConstantsCommon.PTH_CLIENTS_CSV);

		if(irpAddHocQryCsv.exists())
		{
		    fillQueriesVO(irpAddHocQryCsv, irpTempAdHocQueryHM, missingVOPropHM,
			    RepConstantsCommon.IRP_AD_HOC_QUERY,excelAdditionnalCols,dbAdditionnalCols);
		}
		if(irpConnCsv.exists())
		{
		    fillConnectionVO(irpConnCsv, irpTempIrpConnHM, missingVOPropHM, RepConstantsCommon.IRP_CONNECTIONS,
			    excelAdditionnalCols,dbAdditionnalCols);
		}
		if(irpAddHocProcCsv.exists())
		{
		    fillIrpProcVO(irpAddHocProcCsv, irpTempIrpProcHM, missingVOPropHM, RepConstantsCommon.IRP_PROC,
			    excelAdditionnalCols,dbAdditionnalCols);
		}
		if(irpHashTblCsv.exists())
		{
		    fillIrpHashTblVO(irpHashTblCsv, irpTempHashTblHM, missingVOPropHM,
			    RepConstantsCommon.IRP_HASH_TABLE,excelAdditionnalCols,dbAdditionnalCols);
		}
		if(pthClientsCsv.exists())
		{
		    fillPthClientsVO(pthClientsCsv,pthClientsHM, missingVOPropHM,
			    RepConstantsCommon.PTH_CLIENTS,excelAdditionnalCols,dbAdditionnalCols);
		}	

	    }
	}
	catch(Exception e)
	{
	    throw new BOException(e);
	}
	HashMap<String, OPTVO> repOptTempHm = new HashMap<String, OPTVO>();
	// check for error during import
	checkingForError(missingVOPropHM, irpAddHocRepHM, irpTempIrpProcHM, repFolderHM, importOption,
		repIdOldNewReplaceMap, sessionCO, useExistingOptAccess, updateVersionIfEqual, repOptTempHm, repOptHM, axsCO,
		excelAdditionnalCols, dbAdditionnalCols, stepsFile,pageRef);
        //below contains id of the sub_report and corresponding list of
        //irpsubreportVOs to be inserted
	HashMap<BigDecimal, ArrayList<IRP_SUB_REPORTCO>> retSubRepId = new HashMap<BigDecimal, ArrayList<IRP_SUB_REPORTCO>>();
	HashMap<String,Object> toBeReInsertMap;
	//below map to save temporarily the schedule and hyperlink data related to a report
	HashMap<String, ArrayList<Object>> schedMap = new HashMap<String, ArrayList<Object>>();
	HashMap<BigDecimal, ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>> filtersArgumentsMap = new HashMap<BigDecimal, ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>>();
	HashMap<BigDecimal, ArrayList<IRP_REP_FILTERSVO>> filtersMap = new HashMap<BigDecimal, ArrayList<IRP_REP_FILTERSVO>>();
	if(!repIdOldNewReplaceMap.isEmpty())
	{
	    if(keepSchedsHyperlinks)
	    {
		schedMap = saveSchedsHyperlinksInMaps(repIdOldNewReplaceMap);
	    }
	    toBeReInsertMap = deleteExistingReports(repIdOldNewReplaceMap, axsCO,useExistingOptAccess,user,pageRef,irpRepArgHM);
	    retSubRepId = (HashMap<BigDecimal, ArrayList<IRP_SUB_REPORTCO>>) toBeReInsertMap
		    .get(RepConstantsCommon.SUB_REP_MAP);
	    filtersMap = (HashMap<BigDecimal, ArrayList<IRP_REP_FILTERSVO>>) toBeReInsertMap
		    .get(RepConstantsCommon.FILTERS_MAP);
	    filtersArgumentsMap = (HashMap<BigDecimal, ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>>) toBeReInsertMap
		    .get(RepConstantsCommon.ARG_FILTERS_MAP);
	}
	// save the imported csv
	// this hm to map the qry ids oldRepQryId,newRepQryId
	HashMap<BigDecimal, BigDecimal> mapRepQryIdHM = new HashMap<BigDecimal, BigDecimal>();
	// this hm to map the connection ids old connection Id,new
	// connection Id
	HashMap<BigDecimal, BigDecimal> mapRepConIdHM = new HashMap<BigDecimal, BigDecimal>();
	// this hm to map the proc ids old proc Id,new proc Id
	HashMap<BigDecimal, BigDecimal> mapRepProcIdHM = new HashMap<BigDecimal, BigDecimal>();
	// this hm to map the hash table ids old hash table Id,new hash
	// table Id
	HashMap<BigDecimal, BigDecimal> mapRepHashTblIdHM = new HashMap<BigDecimal, BigDecimal>();
	if(!irpAddHocRepHM.isEmpty())
	{
	    HashMap<String,Object> paramMaps = new HashMap<String, Object>();
	    paramMaps.put(RepConstantsCommon.IRP_AD_HOC_QUERY_TMP_MAP, irpTempAdHocQueryHM);
	    paramMaps.put(RepConstantsCommon.IRP_CONNECTIONS_MAP, irpTempIrpConnHM);
	    paramMaps.put(RepConstantsCommon.IRP_PROC_TMP_MAP, irpTempIrpProcHM);
	    paramMaps.put(RepConstantsCommon.IRP_HASH_TBL_TMP_MAP, irpTempHashTblHM);
	    paramMaps.put(RepConstantsCommon.IRP_AD_HOC_REPORT_MAP, irpAddHocRepHM);
	    paramMaps.put(RepConstantsCommon.FTR_EXP_XLS_MAP, ftrExpXlsHM);
	    paramMaps.put(RepConstantsCommon.IRP_REP_ARGUMENTS_MAP, irpRepArgHM);
	    paramMaps.put(RepConstantsCommon.IRP_AD_HOC_QUERY_MAP, irpAdHocQueryHM);
	    paramMaps.put(RepConstantsCommon.IRP_QUERY_ARG_MAPPING_MAP, irpQryArgMappingHM);
	    paramMaps.put(RepConstantsCommon.IRP_REP_PROC_MAP, irpRepProcHM);
	    paramMaps.put(RepConstantsCommon.IRP_REP_PROC_PARAMS_MAP, irpRepProcParamHM);
	    paramMaps.put(RepConstantsCommon.IRP_SUB_REPORT_MAP, irpSubRepHM);
	    paramMaps.put(RepConstantsCommon.OPT_MAP, repOptHM);
	    paramMaps.put(RepConstantsCommon.OPT_EXTENDED_MAP, repOptExHM);
	    paramMaps.put(RepConstantsCommon.IRP_FOLDER_MAP, repFolderHM);
	    paramMaps.put(RepConstantsCommon.IRP_HASH_TABLE_REPORT_MAP, repHashTblRepHM);
	    paramMaps.put(RepConstantsCommon.IRP_CLIENT_REPORT_MAP, irpTempClientRepHM);
	    paramMaps.put(RepConstantsCommon.PTH_CLIENTS_MAP, pthClientsHM);
	    paramMaps.put(RepConstantsCommon.NEW_OLD_REP_SKIP_MAP, repIdOldNewMap);
	    paramMaps.put(RepConstantsCommon.PROG_REF_APP_OPT_MAP, repOptTempHm);
	    paramMaps.put(RepConstantsCommon.OLD_NEW_REP_REPLACE_MAP, repIdOldNewReplaceMap);
	    paramMaps.put(RepConstantsCommon.REP_SUB_REP_LIST_MAP, retSubRepId);
	    paramMaps.put(RepConstantsCommon.IRP_REP_ARG_CONSTRAINTS_MAP, argConstrHM);
	    paramMaps.put(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP, mapRepQryIdHM);
	    paramMaps.put(RepConstantsCommon.CONN_ID_MAP, mapRepConIdHM);
	    paramMaps.put(RepConstantsCommon.IRP_PROC_MAP, mapRepProcIdHM);
	    paramMaps.put(RepConstantsCommon.IRP_HASH_TABLE_MAP, mapRepHashTblIdHM);
	    paramMaps.put(RepConstantsCommon.REP_SNAPSHOT_PARAM_MAP, repSnpParamHM);
	    paramMaps.put(RepConstantsCommon.IRP_SNAPSHOT_PARAM_MAPPING_MAP, irpSnpParamMappingHM);

	    saveImportedReports(paramMaps,user,compCode,branchCode);
	}
	// copy the images from image folder to database
	try
	{
	    byte[] data;
	    for(Entry<String, String> entry : imgSrcDestPathHashMap.entrySet())
	    {
		data = FileUtil.readFileBytes(entry.getValue());
		uploadImageBO.saveUploadedImage(data, entry.getKey());
	    }
	}
	catch(Exception e)
	{
	    throw new BOException(e);
	}
	// save the translation CO
//	for(int i = 0; i < lstTrans.size(); i++)
//	{
//	    translationBO.importLabels(lstTrans.get(i));
//	}
	
	ArrayList<BaseException> exceptionsLst = new ArrayList<BaseException>();
	if(excelAdditionnalCols.toString().endsWith(ConstantsCommon.NEW_LINE))
	{
	    // removing the last comma
	    excelAdditionnalCols = new StringBuffer(ConstantsCommon.NEW_LINE+excelAdditionnalCols
		    .substring(0, excelAdditionnalCols.length() - 2));
	    addException(excelAdditionnalCols, MessageCodes.NEW_VERSION_AVAILABLE, exceptionsLst);
	}
	if(dbAdditionnalCols.toString().endsWith(ConstantsCommon.NEW_LINE))
	{
	    // removing the last comma
	    dbAdditionnalCols = new StringBuffer(ConstantsCommon.NEW_LINE+dbAdditionnalCols.substring(0, dbAdditionnalCols.length() - 2));
	    addException(dbAdditionnalCols, MessageCodes.CURRENT_APP_NEWER_VERSION, exceptionsLst);
	}
	if(!retSubRepId.isEmpty())
	{
	    StringBuffer reportsAltered = new StringBuffer("");
	    //Construct the list of altered reports (updated if the report is subreport in more than
	    //one report)
	    for(Entry<BigDecimal,ArrayList<IRP_SUB_REPORTCO>> entry : retSubRepId.entrySet())
	    {
		reportsAltered.append(entry.getKey()+",");
	    }
	    addException(reportsAltered, MessageCodes.REPORT_ALTERED, exceptionsLst);
	}
	
	HashMap<String,Object> resultMap = new HashMap<String, Object>();
	resultMap.put(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP, mapRepQryIdHM);
	resultMap.put(RepConstantsCommon.CONN_ID_MAP, mapRepConIdHM);
	resultMap.put(RepConstantsCommon.IRP_PROC_MAP, mapRepProcIdHM);
	resultMap.put(RepConstantsCommon.IRP_HASH_TABLE_MAP, mapRepHashTblIdHM);
	resultMap.put(RepConstantsCommon.IRP_AD_HOC_REPORT_MAP, irpAddHocRepHM);
	resultMap.put(RepConstantsCommon.OLD_NEW_REP_REPLACE_MAP, repIdOldNewReplaceMap);
	resultMap.put(RepConstantsCommon.IRP_CONNECTIONS_MAP, irpTempIrpConnHM);
	resultMap.put(RepConstantsCommon.NEW_OLD_REP_SKIP_MAP, repIdOldNewMap);
	resultMap.put(RepConstantsCommon.FTR_EXP_XLS_MAP, ftrExpXlsHM);
	resultMap.put(RepConstantsCommon.IRP_REP_ARGUMENTS_MAP, irpRepArgHM);
	resultMap.put(RepConstantsCommon.IRP_REP_ARG_CONSTRAINTS_MAP, argConstrHM);
	resultMap.put(RepConstantsCommon.IRP_REP_PROC_MAP, irpRepProcHM);
	resultMap.put(RepConstantsCommon.IRP_REP_PROC_PARAMS_MAP, irpRepProcParamHM);
	resultMap.put(RepConstantsCommon.IRP_SUB_REPORT_MAP, irpSubRepHM);
	resultMap.put(RepConstantsCommon.IRP_QUERY_ARG_MAPPING_MAP, irpQryArgMappingHM);
	resultMap.put(RepConstantsCommon.PROG_REF_APP_OPT_MAP, repOptTempHm);
	resultMap.put(RepConstantsCommon.OPT_MAP, repOptHM);
	resultMap.put(RepConstantsCommon.OPT_EXTENDED_MAP, repOptExHM);
	resultMap.put(RepConstantsCommon.IRP_AD_HOC_QUERY_MAP, irpAdHocQueryHM);
	resultMap.put(RepConstantsCommon.IRP_HASH_TABLE_REPORT_MAP, repHashTblRepHM);
	resultMap.put(RepConstantsCommon.IRP_CLIENT_REPORT_MAP, irpTempClientRepHM);
	resultMap.put(RepConstantsCommon.PTH_CLIENTS_MAP, pthClientsHM);
	resultMap.put(RepConstantsCommon.REP_SUB_REP_LIST_MAP, retSubRepId);
	resultMap.put(RepConstantsCommon.EXCEPTIONS_LIST, exceptionsLst);
	resultMap.put(RepConstantsCommon.IMPORT_TRANSLATION_MAP, transMap);
	resultMap.put(RepConstantsCommon.IRP_SCHED_HYPER, schedMap);
	resultMap.put(RepConstantsCommon.REP_SNAPSHOT_PARAM_MAP, repSnpParamHM);
	resultMap.put(RepConstantsCommon.IRP_SNAPSHOT_PARAM_MAPPING_MAP, irpSnpParamMappingHM);
	resultMap.put(RepConstantsCommon.FILTERS_MAP, filtersMap);
	resultMap.put(RepConstantsCommon.ARG_FILTERS_MAP, filtersArgumentsMap);
	return resultMap;
    }
    
    /**
     * Method that saves the scheduler information and hyperlink related to a report in 
     * maps
     * @param repIdOldNewReplaceMap
     * @return
     * @throws BaseException
     */
    private HashMap<String, ArrayList<Object>> saveSchedsHyperlinksInMaps(
	    LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> repIdOldNewReplaceMap) throws BaseException
    {
	HashMap<String, ArrayList<Object>> schedMap = new HashMap<String, ArrayList<Object>>();
	IRP_REPORT_SCHEDULESC sc = new IRP_REPORT_SCHEDULESC();
	IRP_AD_HOC_REPORTSC hypSc = new IRP_AD_HOC_REPORTSC();
	hypSc.setListReportsId(new ArrayList<BigDecimal>());
	sc.setListReportsId(new ArrayList<BigDecimal>());
	for(Entry<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> entry : repIdOldNewReplaceMap.entrySet())
	{
	    sc.getListReportsId().add(entry.getValue().getREPORT_ID());
	    hypSc.getListReportsId().add(entry.getValue().getREPORT_ID());
	}
	sc.setTblName(RepConstantsCommon.IRP_REPORT_SCHEDULE);
	schedMap.put(RepConstantsCommon.IRP_REPORT_SCHEDULE, schedulerBO.retRepSchedByRepId(sc));
	sc.setTblName(RepConstantsCommon.IRP_REPORT_SCHED_PARAMS);
	schedMap.put(RepConstantsCommon.IRP_REPORT_SCHED_PARAMS, schedulerBO.retRepSchedParamByRepId(sc));
	sc.setTblName(RepConstantsCommon.IRP_REP_SCHED_MAIL_GROUP_BY);
	schedMap.put(RepConstantsCommon.IRP_REP_SCHED_MAIL_GROUP_BY, schedulerBO.retRepSchedMailGroupByRepId(sc));
	sc.setTblName(RepConstantsCommon.IRP_REP_SCHED_MAIL_RECEIVERS);
	schedMap.put(RepConstantsCommon.IRP_REP_SCHED_MAIL_RECEIVERS, schedulerBO.retRepSchedMailRecvByRepId(sc));
	hypSc.setTblName(RepConstantsCommon.IRP_REP_HYPERLINKS);
	schedMap.put(RepConstantsCommon.IRP_REP_HYPERLINKS, designerDAO.retRepHyperlinksByRepId(hypSc));
	return schedMap;
    }
    
    private void checkingForError(HashMap<String, List<String>> missingVOPropHM,
	    LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> irpAddHocRepHM,
	    HashMap<BigDecimal, IRP_PROCVO> irpTempIrpProcHM, LinkedHashMap<BigDecimal, IRP_FOLDERVO> repFolderHM,
	    String importOption, LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> repIdOldNewReplaceMap,
	    SessionCO sessionCO, boolean useExistingOptAccess, boolean updateVersionIfEqual,  HashMap<String, OPTVO> repOptTempHM,
	    LinkedHashMap<String, OPTVO> repOptHM, AXSCO axsCO, StringBuffer excelAdditionnalCols,
	    StringBuffer dbAdditionnalCols, File stepsFile,String pageRef) throws BaseException
    {
	ArrayList<BaseException> exceptionsLst = new ArrayList<BaseException>();
	StringBuffer missingProcSb = new StringBuffer("");
	StringBuffer usedProgRefSb = new StringBuffer("");
	StringBuffer alreadyExistingOpt = new StringBuffer("");
	StringBuffer usedFolderRefSb = new StringBuffer("");
	StringBuffer missingColsInCsv = new StringBuffer("");
	StringBuffer nonEditableRep = new StringBuffer("");
	StringBuffer nonDeletableRep = new StringBuffer("");
	StringBuffer sameVersProgRefSb = new StringBuffer("");
	StringBuffer greaterVersProgRefSb =  new StringBuffer("");
	IRP_AD_HOC_REPORTVOWithBLOBs repInReplaceVO;
	String hasDelAxs;

	if(!repIdOldNewReplaceMap.isEmpty())
	{
	    for(Entry<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> entry : repIdOldNewReplaceMap.entrySet())
	    {
		repInReplaceVO = entry.getValue();
		// check if report is editable
		if(repInReplaceVO.getEDITABLE_FLAG().equals(BigDecimal.ONE))
		{
		    nonEditableRep = nonEditableRep.append(repInReplaceVO.getPROG_REF()).append(",");
		}
		// checking if report deletable
		hasDelAxs = returnAccessRightByProgRef(repInReplaceVO.getPROG_REF() + "D",
			repInReplaceVO.getAPP_NAME(), sessionCO);
		if(hasDelAxs == null)
		{
		    nonDeletableRep.append(repInReplaceVO.getPROG_REF()).append(",");
		}
	    }
	    if(nonEditableRep.toString().endsWith(","))
	    {
		addException(nonEditableRep, MessageCodes.REP_NOT_EDITABLE, exceptionsLst);
	    }
	    if(nonDeletableRep.toString().endsWith(","))
	    {
		addException(nonDeletableRep, MessageCodes.REP_NOT_DELETABLE, exceptionsLst);
	    }
	}
	// check if exist a missing columns in csv
	if(!missingVOPropHM.isEmpty())
	{
	    for(Entry<String, List<String>> entry : missingVOPropHM.entrySet())
	    {
		missingColsInCsv = missingColsInCsv.append(ConstantsCommon.NEW_LINE);
		List<String> missingColsLst = entry.getValue();
		missingColsInCsv = missingColsInCsv.append(entry.getKey()).append(":");
		for(int i = 0; i < missingColsLst.size(); i++)
		{
		    missingColsInCsv = missingColsInCsv.append(missingColsLst.get(i));
		    if(i < missingColsLst.size() - 1)
		    {
			missingColsInCsv.append(",");
		    }
		}
	    }
	    //addException(missingColsInCsv, MessageCodes.MISSING_COLS_IN_CSV, exceptionsLst);
	}

	// check if prog ref already in use to generate an error
	for(Entry<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> entry : irpAddHocRepHM.entrySet())
	{
	    IRP_AD_HOC_REPORTVOWithBLOBs repVO = entry.getValue();
	    IRP_AD_HOC_REPORTVOWithBLOBs repFromDbVO = designerDAO.retExistRepInDb(repVO);
		if(repFromDbVO != null)
		{   //Checking the imported report version and comparing it to the report version available on the database
        	    if ((StringUtil.isEmptyString(repVO.getREPORT_VERSION()) || repVO.getREPORT_VERSION().trim().isEmpty())|| (!ConstantsCommon.ZERO.equals(repFromDbVO.getREPORT_VERSION()) && VersionUtils.compareVersions(repFromDbVO.getREPORT_VERSION(), repVO.getREPORT_VERSION())>0))
        	    {
        		greaterVersProgRefSb = greaterVersProgRefSb.append(repFromDbVO.getPROG_REF() + "_" + repFromDbVO.getAPP_NAME())
        		    .append(",");
        	    }
        	    if (!ConstantsCommon.ZERO.equals(repFromDbVO.getREPORT_VERSION()) && VersionUtils.compareVersions(repFromDbVO.getREPORT_VERSION(), repVO.getREPORT_VERSION())==0)
        	    {
        		sameVersProgRefSb = sameVersProgRefSb.append(repFromDbVO.getPROG_REF() + "_" + repFromDbVO.getAPP_NAME())
        		    .append(",");
        	    }
		}
	    if(RepConstantsCommon.STOP.equals(importOption) && repFromDbVO != null)
	    {
		    usedProgRefSb = usedProgRefSb.append(repFromDbVO.getPROG_REF() + "_" + repFromDbVO.getAPP_NAME())
			    .append(",");
		    continue;
	    }
	    // check only the new reports that don't exist to be replaced
		// opt will be deleted for the repIdOldNewReplaceMap
		OPTVOKey voExists = new OPTVOKey();
		voExists.setAPP_NAME(repVO.getAPP_NAME());
		voExists.setPROG_REF(repVO.getPROG_REF());
		OPTVO optVO = checkIfAnyOptInDb(voExists);
		if(optVO != null)
		{
		    if(useExistingOptAccess)
		    {
			// for the case the function returned only an opt object
			// with prog_ref and app_name filled
			// because only parent_ref in addition to prog_Ref and
			// app_name is being read later
			if(optVO.getPARENT_REF() == null)
			{
			    optVO.setPARENT_REF((repOptHM.get(repVO.getPROG_REF() + "_" + repVO.getAPP_NAME()))
				    .getPARENT_REF());
			}
			repOptTempHM.put(repVO.getPROG_REF() + "_" + repVO.getAPP_NAME(), optVO);
		    }
		    else
		    {
			/*
			 * modif denisk:if replace if exist and use
			 * existing opt access unchecked =>delete all the opts +
			 * all the opt_extended that exists.
			 */
			if(RepConstantsCommon.REPLACE.equals(importOption))
			{
			    /*
			     * delete all opt and opt_extended entries related
			     * to the reports not in repIdOldNewReplaceMap
			     * because those related to repIdOldNewReplaceMap
			     * will be deleted using deleteexistingreports
			     */
			    deleteAllOpts(axsCO, optVO,sessionCO.getUserName(),pageRef);
			}
			else
			{
			    alreadyExistingOpt.append(repVO.getPROG_REF() + "_" + repVO.getAPP_NAME()).append(",");
			}
		    }
		}
	}
	if(alreadyExistingOpt.toString().endsWith(","))
	{
	    addException(alreadyExistingOpt, MessageCodes.ALREADY_EXISTING_OPTS, exceptionsLst);
	}
	if(usedProgRefSb.toString().endsWith(","))
	{
	    addException(usedProgRefSb, MessageCodes.USED_REPORT_PROG_REF, exceptionsLst);
	}
	if (greaterVersProgRefSb.toString().endsWith(","))
	{
	    addException(greaterVersProgRefSb, MessageCodes.GREATER_VERSION_AVAILABLE, exceptionsLst);
	}
	if (sameVersProgRefSb.toString().endsWith(",") && !updateVersionIfEqual)
	{
	    addException(sameVersProgRefSb, MessageCodes.SAME_VERSION_AVAILABLE, exceptionsLst);
	}
	
	// check if proc exist in the package
//	List<String> procNameLst = new ArrayList<String>();
//	List<IRP_PROCVO> procFromCsvLst = new ArrayList<IRP_PROCVO>();
//	List<IRP_PROCVO> existProclst = new ArrayList<IRP_PROCVO>();
//	for(Entry<BigDecimal, IRP_PROCVO> entry : irpTempIrpProcHM.entrySet())
//	{
//	    IRP_PROCVO procVO = entry.getValue();
//	    procFromCsvLst.add(procVO);
//	    procNameLst.add(procVO.getPROC_NAME());
//	}
//	if(!procNameLst.isEmpty())
//	{
//	    existProclst = designerDAO.retProcLstFromPackage(procNameLst);
//	}
//	if(existProclst.size() != procFromCsvLst.size())
//	{
//	    for(int i = 0; i < procFromCsvLst.size(); i++)
//	    {
//		String procName = procFromCsvLst.get(i).getPROC_NAME();
//		boolean existProc = false;
//		for(int j = 0; j < existProclst.size(); j++)
//		{
//		    if(existProclst.get(j).getPROC_NAME().equals(procName))
//		    {
//			existProc = true;
//			break;
//		    }
//		}
//		if(!existProc)
//		{
//		    missingProcSb.append(procName).append(",");
//		}
//	    }
//	}
//	if(missingProcSb.toString().endsWith(","))
//	{
//	    addException(missingProcSb, MessageCodes.MISSING_PROCEDURE_NAME, exceptionsLst);
//	}
	for(Entry<BigDecimal, IRP_FOLDERVO> entry : repFolderHM.entrySet())
	{
	    IRP_FOLDERVO folderVO = entry.getValue();
	    // this function get the folder list from db
	    IRP_FOLDERVO folderFromDbVO = designerDAO.retExistFolderInDb(folderVO);
	    if(folderFromDbVO != null)
	    {
		usedFolderRefSb = usedFolderRefSb.append(folderFromDbVO.getFOLDER_REF()).append(",");
	    }
	}
	if(usedFolderRefSb.toString().endsWith(","))
	{
	    addException(usedFolderRefSb, MessageCodes.USED_FOLDER_REF_WITH_DIFF_PARENT, exceptionsLst);
	}
	//checking for possible conflicts between progRefExt inside the zip file
	StringBuffer progRefConflict = checkConflictsProgRefExt(stepsFile);
	if(!progRefConflict.toString().equals(RepConstantsCommon.EMPTY_STRING))
	{
	    addException(progRefConflict, MessageCodes.PROG_REF_CONFLICTS, exceptionsLst);
	}
	if(!exceptionsLst.isEmpty())
	{
	    StringBuffer tmpSb;
	    // below message will appear along with other messages
	    if(excelAdditionnalCols.toString().endsWith(ConstantsCommon.NEW_LINE))
	    {
		tmpSb = new StringBuffer(excelAdditionnalCols.substring(0, excelAdditionnalCols.length() - 2));
		addException(tmpSb, MessageCodes.NEW_VERSION_AVAILABLE, exceptionsLst);
	    }
	    // below message will appear along with other messages
	    if(dbAdditionnalCols.toString().endsWith(ConstantsCommon.NEW_LINE))
	    {
		tmpSb = new StringBuffer(dbAdditionnalCols.substring(0, dbAdditionnalCols.length() - 2));
		addException(tmpSb, MessageCodes.CURRENT_APP_NEWER_VERSION, exceptionsLst);
	    }

	    throw new BOException(exceptionsLst);
	}
    }
    
    public ArrayList<BaseException> addException(StringBuffer usedSb, Integer messageCode, ArrayList<BaseException> exceptionsLst)
    {
	String[] usedArray = new String[] {""};
	String strUsed = usedSb.substring(0, usedSb.toString().length() - 1);
	usedArray[0] = strUsed;
	BaseException baseEx = new BaseException(messageCode, usedArray);
	exceptionsLst.add(baseEx);
	return exceptionsLst;
    }

    /**
     * Method that apply the first part of the saving of the reports
     * @param paramMaps
     * @param user
     * @param compCode
     * @param branchCode
     * @throws BaseException
     */
    private void saveImportedReports(HashMap<String,Object> paramMaps,String user, BigDecimal compCode,
	    BigDecimal branchCode)
	    throws BaseException
    {
	HashMap<BigDecimal, IRP_AD_HOC_QUERYVO> irpTempAdHocQueryHM = (HashMap<BigDecimal, IRP_AD_HOC_QUERYVO>) paramMaps
		.get(RepConstantsCommon.IRP_AD_HOC_QUERY_TMP_MAP);
	HashMap<BigDecimal, IRP_CONNECTIONSVO> irpTempIrpConnHM = (HashMap<BigDecimal, IRP_CONNECTIONSVO>) paramMaps
		.get(RepConstantsCommon.IRP_CONNECTIONS_MAP);
	HashMap<BigDecimal, IRP_PROCVO> irpTempIrpProcHM = (HashMap<BigDecimal, IRP_PROCVO>) paramMaps
		.get(RepConstantsCommon.IRP_PROC_TMP_MAP);
	HashMap<BigDecimal, IRP_HASH_TABLEVO> irpTempHashTblHM = (HashMap<BigDecimal, IRP_HASH_TABLEVO>) paramMaps
		.get(RepConstantsCommon.IRP_HASH_TBL_TMP_MAP);
	LinkedHashMap<String, OPTVO> repOptHM = (LinkedHashMap<String, OPTVO>) paramMaps
		.get(RepConstantsCommon.OPT_MAP);
	LinkedHashMap<BigDecimal, IRP_FOLDERVO> repFolderHM = (LinkedHashMap<BigDecimal, IRP_FOLDERVO>) paramMaps
		.get(RepConstantsCommon.IRP_FOLDER_MAP);
	HashMap<BigDecimal, BigDecimal> mapRepQryIdHM = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP);
	HashMap<BigDecimal, BigDecimal> mapRepConIdHM = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.CONN_ID_MAP);
	HashMap<BigDecimal, BigDecimal> mapRepProcIdHM = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.IRP_PROC_MAP);
	HashMap<BigDecimal, BigDecimal> mapRepHashTblIdHM = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.IRP_HASH_TABLE_MAP);

	// this hm to map the sub rep ids oldSubRepId,newSubRepId
	// HashMap<BigDecimal, BigDecimal> mapSubRepIdHM = new
	// HashMap<BigDecimal, BigDecimal>();
	// this hm to map the qry ids oldRepQryId,newRepQryId
	// = new HashMap<BigDecimal, BigDecimal>();
	// this hm to map the connection ids old connection Id,new connection Id
	// = new HashMap<BigDecimal, BigDecimal>();
	// this hm to map the proc ids old proc Id,new proc Id
	// = new HashMap<BigDecimal, BigDecimal>();
	// this hm to map the hash table ids old hash table Id,new hash table Id
	// = new HashMap<BigDecimal, BigDecimal>();

	Date date = new Date();
	// save the connection
	for(Entry<BigDecimal, IRP_CONNECTIONSVO> entryCon : irpTempIrpConnHM.entrySet())
	{
	    IRP_CONNECTIONSVO conVO = entryCon.getValue();
	    // check if the connection already exist
	    IRP_CONNECTIONSVO currentConVO = designerDAO.retConnection(conVO);
	    if(currentConVO == null)
	    {
		BigDecimal connId = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_CONNECTIONS);
		mapRepConIdHM.put(conVO.getCONN_ID(), connId);
		conVO.setCONN_ID(connId);
		genericDAO.insert(conVO);
	    }
	    else
	    {
		mapRepConIdHM.put(conVO.getCONN_ID(), currentConVO.getCONN_ID());
	    }
	}

	// save the template qry link to argument
	for(Entry<BigDecimal, IRP_AD_HOC_QUERYVO> entry : irpTempAdHocQueryHM.entrySet())
	{
	    IRP_AD_HOC_QUERYVO qryVO = entry.getValue();
	    // check if the template qry already exist
	    IRP_AD_HOC_QUERYVO currentQryVO = designerDAO.retTemplateQry(qryVO);
	    if(currentQryVO == null)
	    {
		BigDecimal qryId = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_AD_HOC_QUERY);
		mapRepQryIdHM.put(qryVO.getQUERY_ID(), qryId);
		qryVO.setQUERY_ID(qryId);
		genericDAO.insert(qryVO);
	    }
	    else
	    {
		mapRepQryIdHM.put(qryVO.getQUERY_ID(), currentQryVO.getQUERY_ID());
	    }
	}
	
	
	// save the procedure
	for(Entry<BigDecimal, IRP_PROCVO> entry : irpTempIrpProcHM.entrySet())
	{
	    IRP_PROCVO procVO = entry.getValue();
	    // check if the procedure already exist
	    IRP_PROCVO currentProcVO = designerDAO.retProc(procVO);
	    if(currentProcVO == null)
	    {
		BigDecimal procId = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_PROC);
		mapRepProcIdHM.put(procVO.getPROC_ID(), procId);
		procVO.setPROC_ID(procId);
		genericDAO.insert(procVO);
	    }
	    else
	    {
		mapRepProcIdHM.put(procVO.getPROC_ID(), currentProcVO.getPROC_ID());
	    }
	}

	// save the hash table
	if(commonLibBO.returnIsSybase() == 1)
	{
	    for(Entry<BigDecimal, IRP_HASH_TABLEVO> entry : irpTempHashTblHM.entrySet())
	    {
		IRP_HASH_TABLEVO hashTblVO = entry.getValue();
		BigDecimal oldHashTblId = hashTblVO.getHASH_TABLE_ID();
		// check if the hash table already exist
		IRP_HASH_TABLEVO currentHashTblVO = designerDAO.retHashTbl(hashTblVO);
		if(currentHashTblVO == null)
		{
		    BigDecimal hashTblId = designerDAO.insertHashTable(hashTblVO);
		    mapRepHashTblIdHM.put(oldHashTblId, hashTblId);
		}
		else
		{
		    currentHashTblVO.setHASH_TABLE_SCRIPT(hashTblVO.getHASH_TABLE_SCRIPT());
		    designerDAO.updateHashTable(currentHashTblVO);
		    mapRepHashTblIdHM.put(oldHashTblId, currentHashTblVO.getHASH_TABLE_ID());
		}
	    }
	}
	// insert in IRP_FOLDERS
	for(Entry<BigDecimal, IRP_FOLDERVO> entry : repFolderHM.entrySet())
	{
	    IRP_FOLDERVO foldersVO = entry.getValue();

	    // check if the folder already exist
	    CommonDetailsSC cdSC = new CommonDetailsSC();
	    cdSC.setAppName(foldersVO.getAPP_NAME());
	    cdSC.setPROG_REF(foldersVO.getFOLDER_REF());
	    String checkIfExist = designerDAO.checkOptProgRef(cdSC);

	    if(("0").equals(checkIfExist))
	    {
		

		// Setting VO values from the Getters of the optVO
		OPTVO optVO = repOptHM.get(foldersVO.getFOLDER_REF() + "_" + foldersVO.getAPP_NAME());
		if(optVO != null)
		{
		    IRP_FOLDERCO foldersCO = new IRP_FOLDERCO();
		    foldersCO.setBRIEF_NAME_ENG(optVO.getMENU_TITLE_ENG());
		    foldersCO.setBRIEF_NAME_ARAB(optVO.getMENU_TITLE_ARAB());
		    foldersCO.setFOLDER_REF(optVO.getPROG_REF());
		    foldersCO.setPARENT_REF(optVO.getPARENT_REF());
		    foldersCO.setDISP_ORDER(optVO.getDISP_ORDER());
		    foldersCO.setDATE_UPDATED(date);
		    foldersCO.setAPP_NAME(optVO.getAPP_NAME());
		    foldersDAO.insertOpt(foldersCO);
		}
		// insert in IRP_FOLDERS
		BigDecimal folderCode = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_FOLDER);
		foldersVO.setFOLDER_CODE(folderCode);
		genericDAO.insert(foldersVO);
	    }

	    // Give Access Privileges : save details in AXS table
	    // insert in AXS
	    addAxsToOPT( compCode,  branchCode, foldersVO.getAPP_NAME(), foldersVO.getFOLDER_REF(), user, date);
	}	
    }
    
    /**
     * To add access
     * @param compCode
     * @param branchCode
     * @param appName
     * @param progRef
     * @param user
     * @param date
     * @throws BaseException
     */
    private void addAxsToOPT(BigDecimal compCode, BigDecimal branchCode,String appName,String progRef,String user,Date date)throws BaseException
    {
	 AXSVO axsVO = new AXSVO();
	    axsVO.setCOMP_CODE(compCode);
	    axsVO.setBRANCH_CODE(branchCode);
	    axsVO.setAPP_NAME(appName);
	    axsVO.setPROG_REF(progRef);
	    axsVO.setUSER_ID(user);
	    axsVO.setCREATED_BY(user);
	    axsVO.setAUTHORIZED_BY(user);
	    axsVO.setSTATUS("P");
	    axsVO.setDIRECT_ACCESS(user);
	    axsVO.setDATE_CREATED(date);
	    axsVO.setDATE_AUTHORIZED(date);
	    axsVO.setTO_BE_DELETED(RepConstantsCommon.N);
	    AXSVO currentAxsVO = (AXSVO) genericDAO.selectByPK(axsVO);
	    // to check if the user have axs on the folder
	    if(currentAxsVO == null)
	    {
		genericDAO.insert(axsVO);
	    }
    }
    
    /**
     * Method that saves the reports one by one when called from action
     */
    public HashMap<String, Object> saveImportedReport(HashMap<String, Object> paramMaps, BigDecimal excelReportId,
	    IRP_AD_HOC_REPORTVOWithBLOBs adHocRepVO, String user, BigDecimal compCode, BigDecimal branchCode,
	    SessionCO sessionCO, boolean useExistingOptAccess,String pageRef) throws BaseException
    {
	HashMap<BigDecimal, IRP_CONNECTIONSVO> irpTempIrpConnHM = (HashMap<BigDecimal, IRP_CONNECTIONSVO>) paramMaps
		.get(RepConstantsCommon.IRP_CONNECTIONS_MAP);
	LinkedHashMap<String, FTR_EXP_XLSVO> ftrExpXlsHM = (LinkedHashMap<String, FTR_EXP_XLSVO>) paramMaps
		.get(RepConstantsCommon.FTR_EXP_XLS_MAP);
	LinkedHashMap<BigDecimal, List<IRP_REP_ARGUMENTSVO>> irpRepArgHM = (LinkedHashMap<BigDecimal, List<IRP_REP_ARGUMENTSVO>>) paramMaps
		.get(RepConstantsCommon.IRP_REP_ARGUMENTS_MAP);
	LinkedHashMap<BigDecimal, List<IRP_AD_HOC_QUERYVO>> irpAdHocQueryHM = (LinkedHashMap<BigDecimal, List<IRP_AD_HOC_QUERYVO>>) paramMaps
		.get(RepConstantsCommon.IRP_AD_HOC_QUERY_MAP);
	LinkedHashMap<BigDecimal, List<IRP_QUERY_ARG_MAPPINGVO>> irpQryArgMappingHM = (LinkedHashMap<BigDecimal, List<IRP_QUERY_ARG_MAPPINGVO>>) paramMaps
		.get(RepConstantsCommon.IRP_QUERY_ARG_MAPPING_MAP);
	LinkedHashMap<BigDecimal, List<IRP_REP_PROCVO>> irpRepProcHM = (LinkedHashMap<BigDecimal, List<IRP_REP_PROCVO>>) paramMaps
		.get(RepConstantsCommon.IRP_REP_PROC_MAP);
	LinkedHashMap<BigDecimal, List<IRP_REP_PROC_PARAMSVO>> irpRepProcParamHM = (LinkedHashMap<BigDecimal, List<IRP_REP_PROC_PARAMSVO>>) paramMaps
		.get(RepConstantsCommon.IRP_REP_PROC_PARAMS_MAP);
	LinkedHashMap<BigDecimal, List<IRP_SUB_REPORTVO>> irpSubRepHM = (LinkedHashMap<BigDecimal, List<IRP_SUB_REPORTVO>>) paramMaps
		.get(RepConstantsCommon.IRP_SUB_REPORT_MAP);
	LinkedHashMap<String, OPTVO> repOptHM = (LinkedHashMap<String, OPTVO>) paramMaps
		.get(RepConstantsCommon.OPT_MAP);
	LinkedHashMap<String, List<OPT_EXTENDEDVO>> repOptExHM = (LinkedHashMap<String, List<OPT_EXTENDEDVO>>) paramMaps
		.get(RepConstantsCommon.OPT_EXTENDED_MAP);
	LinkedHashMap<BigDecimal, List<IRP_HASH_TABLE_REPORTVO>> repHashTblRepHM = (LinkedHashMap<BigDecimal, List<IRP_HASH_TABLE_REPORTVO>>) paramMaps
		.get(RepConstantsCommon.IRP_HASH_TABLE_REPORT_MAP);
	LinkedHashMap<String, List<IRP_CLIENT_REPORTVO>> irpTempClientRepHM = (LinkedHashMap<String, List<IRP_CLIENT_REPORTVO>>) paramMaps
		.get(RepConstantsCommon.IRP_CLIENT_REPORT_MAP);
	LinkedHashMap<String, List<PTH_CLIENTSVO>> pthClientsHM = (LinkedHashMap<String, List<PTH_CLIENTSVO>>) paramMaps
		.get(RepConstantsCommon.PTH_CLIENTS_MAP);
	HashMap<BigDecimal, BigDecimal> repIdOldNewMap = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.NEW_OLD_REP_SKIP_MAP);
	HashMap<String, OPTVO> repOptTempHM = (HashMap<String, OPTVO>) paramMaps
		.get(RepConstantsCommon.PROG_REF_APP_OPT_MAP);
	LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> repIdOldNewReplaceMap = (LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs>) paramMaps
		.get(RepConstantsCommon.OLD_NEW_REP_REPLACE_MAP);
	HashMap<BigDecimal, ArrayList<IRP_SUB_REPORTCO>> retSubRepId = (HashMap<BigDecimal, ArrayList<IRP_SUB_REPORTCO>>) paramMaps
		.get(RepConstantsCommon.REP_SUB_REP_LIST_MAP);
	LinkedHashMap<BigDecimal, List<IRP_REP_ARG_CONSTRAINTSVO>> argConstrHM = (LinkedHashMap<BigDecimal, List<IRP_REP_ARG_CONSTRAINTSVO>>) paramMaps
		.get(RepConstantsCommon.IRP_REP_ARG_CONSTRAINTS_MAP);
	HashMap<BigDecimal, BigDecimal> mapRepQryIdHM = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP);
	HashMap<BigDecimal, BigDecimal> mapRepConIdHM = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.CONN_ID_MAP);
	HashMap<BigDecimal, BigDecimal> mapRepProcIdHM = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.IRP_PROC_MAP);
	HashMap<BigDecimal, BigDecimal> mapRepHashTblIdHM = (HashMap<BigDecimal, BigDecimal>) paramMaps
		.get(RepConstantsCommon.IRP_HASH_TABLE_MAP);
	HashMap<BigDecimal, ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>> filtersArgumentsMap = (HashMap<BigDecimal, ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>>) paramMaps
		.get(RepConstantsCommon.ARG_FILTERS_MAP);
	HashMap<BigDecimal, ArrayList<IRP_REP_FILTERSVO>> filtersMap = (HashMap<BigDecimal, ArrayList<IRP_REP_FILTERSVO>>) paramMaps
		.get(RepConstantsCommon.FILTERS_MAP);
	HashMap<String,Object> transMap = (HashMap<String, Object>) paramMaps.get(RepConstantsCommon.IMPORT_TRANSLATION_MAP);
	HashMap<BigDecimal,List<REP_SNAPSHOT_PARAMVO>>repSnpParamMap=(HashMap<BigDecimal, List<REP_SNAPSHOT_PARAMVO>>) paramMaps.get(RepConstantsCommon.REP_SNAPSHOT_PARAM_MAP);
	HashMap<BigDecimal,List<IRP_SNAPSHOT_PARAM_MAPPINGVO>>irpSnpParamMappingMap=(HashMap<BigDecimal, List<IRP_SNAPSHOT_PARAM_MAPPINGVO>>) paramMaps.get(RepConstantsCommon.IRP_SNAPSHOT_PARAM_MAPPING_MAP);
	HashMap<String,Object> resultMap = new HashMap<String, Object>();
	String    progRef = adHocRepVO.getPROG_REF();

	boolean failedSubReport = false;
	try
	{
	Date date = (Date) paramMaps.get(RepConstantsCommon.IMPORT_REPORTS_DATE);
	String    repAppName = adHocRepVO.getAPP_NAME();
	String    repName = adHocRepVO.getREPORT_NAME();
	
	   
	    BigDecimal newRepId = null;
	    BigDecimal repInfoRepId=null;
	    boolean repBeingReplaced=false;
	    // insert in IRP_AD_HOC_REPORT
	    /*
	     * first check if there is value for the key (excel_id) of
	     * irpAddHocRepHM inside the replace map.If yes, get the report_id
	     * from the VO in the replaceMap since it's the id of the db report
	     * that has been deleted previously and use it when inserting to
	     * preserve data integrity (if a nested report already has a parent
	     * report that doesn't exist in the zip file,mapping in
	     * irp_sub_report will be preserved) repIdOldNewReplaceMap=>
	     * (excel_id,db_rep).irpAddHocRepHM=>(excel_id,excel_vo) NB:for all
	     * reports that will be replaced,the preexisting id in db will be
	     * used
	     */
	    if(repIdOldNewReplaceMap.isEmpty() || repIdOldNewReplaceMap.get(excelReportId) == null)
	    {
		newRepId = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_AD_HOC_REPORT);
	    }
	    else
	    {
		newRepId = (repIdOldNewReplaceMap.get(excelReportId)).getREPORT_ID();
		repInfoRepId=(repIdOldNewReplaceMap.get(excelReportId)).getOLD_REPORT_ID();
		repBeingReplaced = true;
	    }

	    IRP_AD_HOC_REPORTVOWithBLOBs irpAdHocRepVO = adHocRepVO;
	    BigDecimal oldRepId = irpAdHocRepVO.getREPORT_ID();
	    irpAdHocRepVO.setREPORT_ID(newRepId);
	    irpAdHocRepVO.setCREATED_BY(user);
	    irpAdHocRepVO.setMODIFIED_BY(user);

	    //both conditions will work since in steps.txt always the metadata added before the main
	    if(repBeingReplaced && !NumberUtil.isEmptyDecimal(irpAdHocRepVO.getMETADATA_REPORT_ID()))
	    {
		irpAdHocRepVO.setMETADATA_REPORT_ID(
			repIdOldNewReplaceMap.get(irpAdHocRepVO.getMETADATA_REPORT_ID()).getREPORT_ID());
	    }
	    else if(!NumberUtil.isEmptyDecimal(irpAdHocRepVO.getMETADATA_REPORT_ID()))
	    {
		irpAdHocRepVO.setMETADATA_REPORT_ID(repIdOldNewMap.get(irpAdHocRepVO.getMETADATA_REPORT_ID()));
	    }

	    irpAdHocRepVO.setDATE_CREATED(date);
	    irpAdHocRepVO.setDATE_MODIFIED(date);
	    irpAdHocRepVO.setDATE_UPDATED(date);
	    irpAdHocRepVO.setCOMP_CODE(compCode);
	    irpAdHocRepVO.setBRANCH_CODE(branchCode);

	    if(irpTempIrpConnHM.containsKey(irpAdHocRepVO.getCONN_ID()))
	    {
		irpAdHocRepVO.setCONN_ID(mapRepConIdHM.get(irpAdHocRepVO.getCONN_ID()));
	    }



	    // check if we still have a pb application
	    int pbAppCnt = designerDAO.retPbAppCnt();
	    REP_INFOVO infoVO = new REP_INFOVO();
	    REP_CATALOGUEVO catVO = new REP_CATALOGUEVO();
	    if(pbAppCnt > 0)
	    {
		// get the sequence
		infoVO.setUSERID(user);
		infoVO.setTITLE(repName);
		infoVO.setAPPLICATION(repAppName);
		infoVO.setREP_TYPE("DWS");
		infoVO.setCOMP_CODE(compCode);
		infoVO.setBRANCH_CODE(branchCode);
		infoVO.setREP_REFERENCE(progRef);
		infoVO.setDW_OBJECT("$$JAVA");
		catVO.setREP_OBJECT(irpAdHocRepVO.getJRXML_FILE());

		// insert REP_INFO
		infoVO.setREP_DATE(irpAdHocRepVO.getDATE_CREATED());
		if(repInfoRepId!=null)
		{
		    infoVO.setREP_ID(repInfoRepId);   
		}
		/*
		 * in case of 'replace if exist' the old 'old_rep_id' will be provided
		 * if exist in order to save it and not generating a new one
		*/
	    if(infoVO.getREP_ID() == null)
	    {
	    	DBSequenceSC dbSeqSC = new DBSequenceSC();
	    	dbSeqSC.setSequenceName("REP_ID_SEQ");
	    	dbSeqSC.setTableName("REP_IDENTITY");
	    	infoVO.setREP_ID(commonLibBO.returnTableSequence(dbSeqSC));
	    }
		BigDecimal oldId = designerDAO.insertRepInfo(infoVO);
		// insert REP_CATALOGUE
		catVO.setREP_ID(oldId);
		genericDAO.insert(catVO);
		// set the old_report_id in IRP_AD_HOC_REPORT
		irpAdHocRepVO.setOLD_REPORT_ID(oldId);

		// Add dummy update
		REP_CATALOGUEVO dummyCatVO = new REP_CATALOGUEVO();
		dummyCatVO.setREP_ID(catVO.getREP_ID());
		dummyCatVO.setREP_OBJECT(catVO.getREP_OBJECT());
		genericDAO.update(dummyCatVO);

	    }
	    else
	    {
		irpAdHocRepVO.setOLD_REPORT_ID(null);
	    }
	    repIdOldNewMap.put(oldRepId, newRepId);
	    genericDAO.insert(irpAdHocRepVO);
	    
	    // insert the report version history
	    API_SCR_LOGVO apiScrLogVO=  new API_SCR_LOGVO(); 
	    apiScrLogVO.setAPP_NAME(irpAdHocRepVO.getAPP_NAME());
	    int entityNamelength = irpAdHocRepVO.getREPORT_NAME().length();
	    if (entityNamelength >= 40)
	    {
		apiScrLogVO.setENTITY_NAME(irpAdHocRepVO.getREPORT_NAME().substring(0, 39));
	    }
	    else
	    {
		apiScrLogVO.setENTITY_NAME(irpAdHocRepVO.getREPORT_NAME());
	    }
	    apiScrLogVO.setENTITY_TYPE(RepConstantsCommon.REPORT);
	    apiScrLogVO.setSERVER_DATE(date);
	    apiScrLogVO.setVERSION( irpAdHocRepVO.getREPORT_VERSION());
	    designerDAO.insertApiScrLog(apiScrLogVO);
	    
	          
	    // insert in FTR_EXP_XLS
	    if(ftrExpXlsHM.containsKey(progRef))
	    {
		for(Entry<String, FTR_EXP_XLSVO> entryFtr : ftrExpXlsHM.entrySet())
		{
		    FTR_EXP_XLSVO ftrExpXlsVO = entryFtr.getValue();
		    ftrExpXlsVO.setCOMP_CODE(compCode);
		    genericDAO.insert(ftrExpXlsVO);
		}
	    }
	  //insert in REP_SNAPSHOT_PARAM
	    if(repSnpParamMap.containsKey(oldRepId))
	    {
		List<REP_SNAPSHOT_PARAMVO> snpParamMapLst=repSnpParamMap.get(oldRepId);
		REP_SNAPSHOT_PARAMVO snpParamVO;
		BigDecimal repId;
		BigDecimal repIdCsv;
		List<BigDecimal> newRepIdLst=new ArrayList<BigDecimal>();
		String snpRef="";
		 SnapshotParameterSC snpSC;
		DBSequenceSC dbSeqSC = new DBSequenceSC();
		dbSeqSC.setSequenceName("FTR_SNAP_PARAM_SEQ");
		dbSeqSC.setTableName("SNAPSHOT_PARAM_IDENTITY");
		for(int i=0; i<snpParamMapLst.size();i++)
		{
		    snpParamVO=snpParamMapLst.get(i);
		    repIdCsv=snpParamVO.getREP_ID();
		    snpRef=snpParamVO.getREP_REFERENCE();
		    //return the old repId if exist based on the prog_ref and the frequency
		    snpSC= new SnapshotParameterSC();
		    snpSC.setREP_REFERENCE(snpParamVO.getREP_REFERENCE());
		    snpSC.setSNAPSHOT_FREQUENCY(snpParamVO.getSNAPSHOT_FREQUENCY());
		    BigDecimal oldRepSnpParamId=snapshotParameterDAO.getRepIdByRefFreq(snpSC);
		    //insert the new record under REP_SNPASHOT_PARAMETER
			repId = commonLibBO.returnTableSequence(dbSeqSC);
		    snpParamVO.setREP_ID(repId);
		    snpParamVO.setCREATED_BY(user);
		    snpParamVO.setCOMP_CODE(compCode);
		    newRepIdLst.add(repId);
		    genericDAO.insert(snpParamVO);
		    
		    // insert in IRP_SNAPSHOT_PARAM_MAPPING
		    if(irpSnpParamMappingMap.containsKey(oldRepId))
		    {
			List<IRP_SNAPSHOT_PARAM_MAPPINGVO> snpParamMappingLst = irpSnpParamMappingMap.get(oldRepId);
			IRP_SNAPSHOT_PARAM_MAPPINGVO snpParamMappingVO;
			for(int j = 0; j < snpParamMappingLst.size(); j++)
			{
			    snpParamMappingVO = snpParamMappingLst.get(j);
			    if(snpParamMappingVO.getREP_ID().equals(repIdCsv))
			    {
				snpParamMappingVO.setREP_ID(repId);
				genericDAO.insert(snpParamMappingVO);
			    }
			}
		    }
		    
		    //if the report aleady has a record in rep_snpashot_param
		    if(!BigDecimal.ZERO.equals(NumberUtil.nullToZero(oldRepSnpParamId)))
		    {
			snpSC= new SnapshotParameterSC();
			snpSC.setREP_ID(repId);
			snpSC.setREP_SNAPSHOT_ID(oldRepSnpParamId);
			//update the table REP_SNAPSHOT_DRILLDOWN_COLUMN with the new repId
			snapshotParameterDAO.updateRepSnpDrillDownRepId(snpSC);
			
			//update the table REP_SNAPSHOT_MODIFY_COLUMN with the new repId
			snapshotParameterDAO.updateRepSnpModColRepId(snpSC);
			
			//update the table REP_SNAPSHOT_INFO with the new repId
			snapshotParameterDAO.updateRepSnpInfoRepId(snpSC);
			
			// delete from the table IRP_SNAPSHOT_PARAM_MAPPING
			IRP_SNAPSHOT_PARAM_MAPPINGVO snpMapVO = new IRP_SNAPSHOT_PARAM_MAPPINGVO();
			snpMapVO.setMAPPING_CODE(BigDecimal.ONE);
			snpMapVO.setREP_ID(oldRepSnpParamId);
			genericDAO.delete(snpMapVO);
			
			//delete from REP_SNPASHOT_PARAMETER the old rep id
			REP_SNAPSHOT_PARAMVO oldRepSnpParamVO= new REP_SNAPSHOT_PARAMVO();
			oldRepSnpParamVO.setREP_ID(oldRepSnpParamId);
			genericDAO.delete(oldRepSnpParamVO);
		    }
		}
		//delete the addidtional records that already exist in the DB and not exist in the csv
		snpSC= new SnapshotParameterSC();
		snpSC.setREP_REFERENCE(snpRef);
		snpSC.setREP_ID_LST(newRepIdLst);
		ArrayList<BigDecimal>oldRepIdLst=snapshotParameterDAO.retAdditionalRepIds(snpSC);
		BigDecimal repIdToDel;
		for(int i=0;i<oldRepIdLst.size();i++)
		{
		    repIdToDel = oldRepIdLst.get(i);
		    snpSC= new SnapshotParameterSC();
		    snpSC.setREP_ID(repIdToDel);
		    // delete from the table REP_SNAPSHOT_DRILLDOWN_COLUMN
		    snapshotParameterDAO.deleteDrilColById(snpSC);
		   
		    // delete from the table REP_SNAPSHOT_MODIFY_COLUMN
		    snapshotParameterDAO.deleteModColById(snpSC);
		   
		    // delete from the table REP_SNAPSHOT_INFO
		    snapshotParameterDAO.deleteSnapshotInfByRepId(snpSC);
		    
		    // delete from REP_SNPASHOT_PARAMETER 
		    REP_SNAPSHOT_PARAMVO snpParamDelVO=new REP_SNAPSHOT_PARAMVO(); 
		    snpParamDelVO.setREP_ID(repIdToDel);
		    genericDAO.delete(snpParamDelVO);
		}
	    }
	    
	    // insert in IRP_REP_ARGUMENTS
	    if(irpRepArgHM.containsKey(oldRepId))
	    {
		List<IRP_REP_ARGUMENTSVO> repArgList = irpRepArgHM.get(oldRepId);
		for(int i = 0; i < repArgList.size(); i++)
		{
		    IRP_REP_ARGUMENTSVO repArgVO = repArgList.get(i);
		    repArgVO.setREPORT_ID(newRepId);
		    if(repArgVO.getQUERY_ID() != null)
		    {
			repArgVO.setQUERY_ID(mapRepQryIdHM.get(repArgVO.getQUERY_ID()));
		    }
		    if(repArgVO.getQUERY_ID_DEFAULT() != null)
		    {
			repArgVO.setQUERY_ID_DEFAULT(mapRepQryIdHM.get(repArgVO.getQUERY_ID_DEFAULT()));
		    }
		    // mapRepQryIdHM
		    genericDAO.insert(repArgVO);
		}

	    }
	    
	    //insert in IRP_REP_ARG_CONSTRAINTS
	    if(argConstrHM.containsKey(oldRepId))
	    {
		IRP_REP_ARG_CONSTRAINTSVO repArgCnstrVO;
		List<IRP_REP_ARG_CONSTRAINTSVO> repArgCnstrList = argConstrHM.get(oldRepId);
		for(int i = 0; i < repArgCnstrList.size(); i++)
		{
		    repArgCnstrVO = repArgCnstrList.get(i);
		    repArgCnstrVO.setREPORT_ID(newRepId);
		    genericDAO.insert(repArgCnstrVO);
		}

	    }

	    // insert in IRP_REP_PROC
	    if(irpRepProcHM.containsKey(oldRepId))
	    {
		List<IRP_REP_PROCVO> repProcList = irpRepProcHM.get(oldRepId);
		for(int i = 0; i < repProcList.size(); i++)
		{
		    IRP_REP_PROCVO repProcVO = repProcList.get(i);
		    repProcVO.setREPORT_ID(newRepId);
		    // to set the new proc_id
		    repProcVO.setPROC_ID(mapRepProcIdHM.get(repProcVO.getPROC_ID()));
		    genericDAO.insert(repProcVO);
		}

	    }

	    // insert in IRP_REP_PROC_PARAMS
	    if(irpRepProcParamHM.containsKey(oldRepId))
	    {
		List<IRP_REP_PROC_PARAMSVO> repProcParmList = irpRepProcParamHM.get(oldRepId);
		for(int i = 0; i < repProcParmList.size(); i++)
		{
		    IRP_REP_PROC_PARAMSVO repProcParamVO = repProcParmList.get(i);
		    repProcParamVO.setREPORT_ID(newRepId);
		    repProcParamVO.setPROC_ID(mapRepProcIdHM.get(repProcParamVO.getPROC_ID()));
		    genericDAO.insert(repProcParamVO);
		}

	    }

	    // insert in IRP_SUB_REPORT
	    if(irpSubRepHM.containsKey(oldRepId))
	    {
		List<IRP_SUB_REPORTVO> subRepList = irpSubRepHM.get(oldRepId);
		for(int i = 0; i < subRepList.size(); i++)
		{
		    IRP_SUB_REPORTVO subRepVO = subRepList.get(i);
		    subRepVO.setREPORT_ID(newRepId);
		    // set the new subRepId saved in mapSubRepIdHM
		    subRepVO.setSUB_REPORT_ID(repIdOldNewMap.get(subRepVO.getSUB_REPORT_ID()));
		    //preventing null sub_report_id being inserted in case of previous
		    //failure of the report
		    if(subRepVO.getSUB_REPORT_ID()==null)
		    {
			failedSubReport=true;
			throw new BOException();
		    }
		    genericDAO.insert(subRepVO);
		}

	    }

	    // insert in IRP_QUERY_ARG_MAPPING
	    if(irpQryArgMappingHM.containsKey(oldRepId))
	    {
		List<IRP_QUERY_ARG_MAPPINGVO> repQryArgMappingList = irpQryArgMappingHM.get(oldRepId);
		for(int i = 0; i < repQryArgMappingList.size(); i++)
		{
		    IRP_QUERY_ARG_MAPPINGVO repQryArgMapVO = repQryArgMappingList.get(i);
		    repQryArgMapVO.setREPORT_ID(newRepId);
		    repQryArgMapVO.setQUERY_ID(mapRepQryIdHM.get(repQryArgMapVO.getQUERY_ID()));
		    genericDAO.insert(repQryArgMapVO);
		}
	    }

	    IRP_AD_HOC_REPORTSC paramSC = new IRP_AD_HOC_REPORTSC();
	    paramSC.setAPP_NAME(repAppName);
	    paramSC.setPROG_REF(progRef);
	    OPTVO curOptVO;
	    ArrayList<OPTVO> parentsList;
	    // insert in OPT
	    //repOptTempHM used as indicator for any existing records in opt table
	    if(repOptTempHM.get(progRef + "_" + repAppName) == null)
	    {
		OPTVO optVO = repOptHM.get(progRef + "_" + repAppName);
		if(optVO != null)
		{
		    fcrBO.saveAllReportOpts(optVO);
		}
	    }
	    else
	    {
		IRP_AD_HOC_REPORTCO repCO = new IRP_AD_HOC_REPORTCO();
		repCO.setPROG_REF(progRef);
		repCO.setAPP_NAME(repAppName);
		repCO.setREPORT_NAME(repName);
		repCO.setPARENT_REF((repOptTempHM.get(progRef + "_" + repAppName)).getPARENT_REF());
		addMissingOpts(repCO, true);
	    }
	    // getting the parents opts
	      parentsList = saveParentReferences(repOptHM, paramSC, new ArrayList<OPTVO>());
	    //adding the parents opts + access
	    for(int i = 0; i < parentsList.size(); i++)
	    {
		curOptVO = parentsList.get(i);
		if(genericDAO.selectByPK(curOptVO) == null)
		{
		    genericDAO.insert(curOptVO);
		}
		//add axs privilege to the parent opt
		addAxsToOPT( compCode,  branchCode, curOptVO.getAPP_NAME(), curOptVO.getPROG_REF(), user, date);
	    }
	    
	    String hasAxs = "";
	    PTH_CTRL1VO pthCtrl1VO = commonLibBO.returnPthCtrl1();
	    // insert in AXS
	    if(pthCtrl1VO.getREP_ALLOW_AXS_YN().equals(BigDecimal.ONE))
	    {
		if(repOptTempHM.get(progRef + "_" + repAppName) != null)
		{
		    hasAxs = returnAccessRightByProgRef(progRef, repAppName, sessionCO);
		}
		// To add a control on the access flag in pthctrl table added by
		// nathalie
		if(repOptTempHM.get(progRef + "_" + repAppName) == null
			|| (repOptTempHM.get(progRef + "_" + repAppName) != null && hasAxs == null))
		{
		    AXSVO axsVO = fcrBO.fillAxsVOProps(compCode, branchCode, repAppName, user, date, progRef,
			    RepConstantsCommon.AXS_STATUS_P, RepConstantsCommon.AXS_TO_BE_DELETED_N);
		    // this method will check the already existing access
		    // records in db and will insert the missing ones
		    addMissingAxs(axsVO, useExistingOptAccess,user,pageRef);
		}
	    }
	    String progRefRepAppName = progRef + "_" + repAppName;
	    // insert in OPT_EXTENDED
	    if(repOptExHM.containsKey(progRefRepAppName))
	    {
		List<OPT_EXTENDEDVO> optExList = repOptExHM.get(progRefRepAppName);
		OPT_EXTENDEDVOKey vo;
		for(int i = 0; i < optExList.size(); i++)
		{
		    OPT_EXTENDEDVO optExVO = optExList.get(i);
		    vo = new OPT_EXTENDEDVOKey();
		    vo.setPROG_REF(optExVO.getPROG_REF());
		    vo.setAPP_NAME(optExVO.getAPP_NAME());
		    if(genericDAO.selectByPK(vo) != null)
		    {
			if(useExistingOptAccess)
			{
			    continue;
			}
			else
			{
			    vo.applyTraceProps(ConstantsCommon.REP_APP_NAME, sessionCO.getUserName(), pageRef,adHocRepVO.getHttpSessionIdForLink());
			    genericDAO.delete(vo);
			}
		    }
		    genericDAO.insert(optExVO);
		}

	    }
	    // insert in IRP_AD_HOC_QUERY
	    if(irpAdHocQueryHM.containsKey(oldRepId))
	    {
		BigDecimal newQryId;
		List<IRP_AD_HOC_QUERYVO> qryList = irpAdHocQueryHM.get(oldRepId);
		IRP_AD_HOC_QUERYVO queryvo;
		for(int i = 0; i < qryList.size(); i++)
		{
		    IRP_AD_HOC_QUERYVO qryVO = qryList.get(i);
		    newQryId = commonRepFuncBO.retCounterValue(RepConstantsCommon.IRP_AD_HOC_QUERY);
		    mapRepQryIdHM.put(qryVO.getQUERY_ID(), newQryId);
		    qryVO.setQUERY_ID(newQryId);
		    genericDAO.insert(qryVO);
		  //add dummy update
		    queryvo = new IRP_AD_HOC_QUERYVO();
		    queryvo.setQUERY_ID(qryVO.getQUERY_ID());
		    queryvo.setQUERY_NAME(qryVO.getQUERY_NAME());
		    genericDAO.update(queryvo);
		    // insert in IRP_REPORT_QUERY
		    IRP_REPORT_QUERYVOKey irpRepQryVO = new IRP_REPORT_QUERYVOKey();
		    irpRepQryVO.setQUERY_ID(newQryId);
		    irpRepQryVO.setREPORT_ID(newRepId);
		    genericDAO.insert(irpRepQryVO);

		}

	    }
	    // insert in IRP_HASH_TABLE_REPORT
	    if(commonLibBO.returnIsSybase() == 1 && repHashTblRepHM.containsKey(oldRepId))
	    {
		    List<IRP_HASH_TABLE_REPORTVO> hashTblRepList = repHashTblRepHM.get(oldRepId);
		    for(int i = 0; i < hashTblRepList.size(); i++)
		    {
			IRP_HASH_TABLE_REPORTVO hashTblRepVO = hashTblRepList.get(i);
			hashTblRepVO.setREPORT_ID(newRepId);
			hashTblRepVO.setHASH_TABLE_ID(mapRepHashTblIdHM.get(hashTblRepVO.getHASH_TABLE_ID()));
			genericDAO.insert(hashTblRepVO);
		    }
	    }
	    
	    //insert in IRP_CLIENT_REPORT 
	    if(irpTempClientRepHM.containsKey(progRef))
	    { 	    PTH_CLIENTSSC pthClientSC ;
	    	   IRP_CLIENT_REPORTVO irpClientReportVO;
	    	   List<PTH_CLIENTSVO> pthClientsList;
	    	   PTH_CLIENTSVO pthClientsVO;
		     List<IRP_CLIENT_REPORTVO> clientRepList = irpTempClientRepHM.get(progRef);
		    for(int i = 0; i < clientRepList.size(); i++)
		    {
			pthClientSC = new PTH_CLIENTSSC();
			irpClientReportVO = clientRepList.get(i);
			pthClientSC.setCLIENT_ACRONYM(irpClientReportVO.getCLIENT_ACRONYM());
			PTH_CLIENTSCO pthClientsCO =  returnClient(pthClientSC); 
			
			if (pthClientsCO == null)
			{  
			     pthClientsList = pthClientsHM.get(irpClientReportVO.getCLIENT_ACRONYM());
			    for(int j = 0; j < pthClientsList.size(); j++)
			    {    
				pthClientsVO =pthClientsList.get(j);
				genericDAO.insert(pthClientsVO);
			    }
			}
			genericDAO.insert(irpClientReportVO);
			
		    }
	    }
	    
	    else if (irpTempClientRepHM.containsKey(RepConstantsCommon.IRP_CLIENT_DEFAULT))
	    {
		List<IRP_CLIENT_REPORTVO> clientRepList = irpTempClientRepHM.get(RepConstantsCommon.IRP_CLIENT_DEFAULT);
		IRP_CLIENT_REPORTVO irpClientReportVO;
		for(int i = 0; i < clientRepList.size(); i++)
		    {
		    	irpClientReportVO = clientRepList.get(i);
    			irpClientReportVO.setAPP_NAME(repAppName);
    			irpClientReportVO.setREPORT_REF(progRef);
    			genericDAO.insert(irpClientReportVO);
		    }
	    }
	 
	    
	    //insert the records deleted because of other parents existing in
	    //db (when report is subreport for more than 1 report)
	    if(retSubRepId.get(newRepId) != null)
	    {
		ArrayList<IRP_SUB_REPORTCO> listSubs = retSubRepId.get(newRepId);
		for(int i = 0; i < listSubs.size(); i++)
		{
		    genericDAO.insert(listSubs.get(i).getIrpSubReportVO());
		}
	    }
	    
	    // add the previously existing filters
	    if(filtersMap.get(newRepId) != null)
	    {
		ArrayList<IRP_REP_FILTERSVO> filtersList = filtersMap.get(newRepId);
		for(int i = 0; i < filtersList.size(); i++)
		{
			genericDAO.insert(filtersList.get(i));
		}
	    }
	    if(filtersArgumentsMap.get(newRepId) != null)
	    {
		ArrayList<IRP_REP_ARGUMENTS_FILTERSVO> argFiltersList = filtersArgumentsMap.get(newRepId);
		for(int i = 0; i < argFiltersList.size(); i++)
		{
		    genericDAO.insert(argFiltersList.get(i));
		}
	    } 
	    
	    if(transMap.get(progRefRepAppName) != null)
	    {
		translationBO.importLabels((TranslationCO) transMap.get(progRefRepAppName));
	    }
	    //below maps being altered in the function,send them as return values
	    resultMap.put(RepConstantsCommon.NEW_OLD_REP_SKIP_MAP, repIdOldNewMap);
	    resultMap.put(RepConstantsCommon.OLD_NEW_REP_QUERY_MAP, mapRepQryIdHM);	
	}
	catch(Exception e)
	{
	    BOException error = new BOException(RepConstantsCommon.IMPORT_FAILED_REPORTS, e);
	    if(failedSubReport)
	    {
		error.setMsgIdent(RepConstantsCommon.IMPORT_FAILED_MAIN_SUB_REPORTS);
	    }
	    else
	    {
		error.setMsgIdent(RepConstantsCommon.IMPORT_FAILED_REPORTS);
	    }
	    throw error;
	}
	return resultMap;
    }
    
    @Override
    public void saveSchedulersHyperlinksToDb(HashMap<String, ArrayList<Object>> schedMap) throws BaseException
    {
	ArrayList<Object> irpRepSchedHyperLists = schedMap.get(RepConstantsCommon.IRP_REPORT_SCHEDULE);
	IRP_REPORT_SCHEDULEVO repSchedVO;
	IRP_REPORT_SCHED_PARAMSVO repSchedParamsVO;
	IRP_REP_SCHED_MAIL_GROUP_BYVO repSchedMailGroupByVO;
	IRP_REP_SCHED_MAIL_RECEIVERSVO repSchedMailRecvVO;
	IRP_REP_HYPERLINKSVO hypVO;

	for(int i = 0; i < irpRepSchedHyperLists.size(); i++)
	{
	    repSchedVO = (IRP_REPORT_SCHEDULEVO) irpRepSchedHyperLists.get(i);
	    genericDAO.insert(repSchedVO);
	}
	irpRepSchedHyperLists = schedMap.get(RepConstantsCommon.IRP_REPORT_SCHED_PARAMS);
	for(int i = 0; i < irpRepSchedHyperLists.size(); i++)
	{
	    repSchedParamsVO = (IRP_REPORT_SCHED_PARAMSVO) irpRepSchedHyperLists.get(i);
	    genericDAO.insert(repSchedParamsVO);
	}
	irpRepSchedHyperLists = schedMap.get(RepConstantsCommon.IRP_REP_SCHED_MAIL_GROUP_BY);
	for(int i = 0; i < irpRepSchedHyperLists.size(); i++)
	{
	    repSchedMailGroupByVO = (IRP_REP_SCHED_MAIL_GROUP_BYVO) irpRepSchedHyperLists.get(i);
	    genericDAO.insert(repSchedMailGroupByVO);
	}
	irpRepSchedHyperLists = schedMap.get(RepConstantsCommon.IRP_REP_SCHED_MAIL_RECEIVERS);
	for(int i = 0; i < irpRepSchedHyperLists.size(); i++)
	{
	    repSchedMailRecvVO = (IRP_REP_SCHED_MAIL_RECEIVERSVO) irpRepSchedHyperLists.get(i);
	    genericDAO.insert(repSchedMailRecvVO);
	}
	irpRepSchedHyperLists = schedMap.get(RepConstantsCommon.IRP_REP_HYPERLINKS);
	for(int i = 0; i < irpRepSchedHyperLists.size(); i++)
	{
	    hypVO = (IRP_REP_HYPERLINKSVO) irpRepSchedHyperLists.get(i);
	    genericDAO.insert(hypVO);
	}
    }
   
    /**
     * Method that checks for potential conflict between some opts in the zip
     * file
     * 
     * @param stepsFile
     * @return
     * @throws BOException
     */
    private StringBuffer checkConflictsProgRefExt(File stepsFile) throws BOException
    {
	StringBuffer progRefProb = new StringBuffer();
	try
	{
	    ArrayList<String> progRefsList = new ArrayList<String>();
	    HashMap<String, String> existenceMap = new HashMap<String, String>();
	    String originalOpt = "";
	    BufferedReader bfRd = transformFileToBfRd(stepsFile);
	    String strLine;
	    String curProgRef;
	    //limit the size of the line to be below of 200 MB = 200000000 bytes
	    while((strLine = PathFileSecure.readLine(bfRd, 200000000)) != null)
	    {
		curProgRef = strLine.substring(0, strLine.lastIndexOf(RepConstantsCommon.UNDERSCORE));
		addExtensions(progRefsList, curProgRef);
	    }
	    // progRefsList contains each opt with its extensions
	    for(int i = 0; i < progRefsList.size(); i++)
	    {
		if(i % 8 == 0)
		{
		    originalOpt = progRefsList.get(i);
		}
		curProgRef = progRefsList.get(i);
		if(existenceMap.get(curProgRef) == null)
		{
		    existenceMap.put(curProgRef, originalOpt);
		}
		else
		{
		    progRefProb.append(originalOpt + RepConstantsCommon.COMMA + existenceMap.get(curProgRef)
			    + RepConstantsCommon.COMMA);
		}
	    }
	}
	catch(IOException e)
	{
	    throw new BOException(e);
	}
	catch(Exception e)
	{
		throw new BOException(e);
	}
	return progRefProb;
    }

    public HashMap<String, Object> deleteExistingReports(
	    LinkedHashMap<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> repIdOldNewReplaceMap, AXSCO axsCO,
	    boolean useExistingOptAccess, String userName, String pageRef,
	    LinkedHashMap<BigDecimal, List<IRP_REP_ARGUMENTSVO>> irpRepArgHM) throws BaseException
    {
	ArrayList<BigDecimal> reportsId = new ArrayList<BigDecimal>();
	ArrayList<BigDecimal> oldReportsId = new ArrayList<BigDecimal>();
	ArrayList<String> appsName = new ArrayList<String>();
	StringBuffer progRefBuf = new StringBuffer("");
	IRP_AD_HOC_REPORTVOWithBLOBs bufVO;
	BigDecimal dbReportId;
	BigDecimal csvReportId;
	String bufProgRef;

	// save in hashmap existing filters
	// key is report id currently in database
	HashMap<BigDecimal, ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>> filtersArgumentsMap = new HashMap<BigDecimal, ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>>();
	HashMap<BigDecimal, ArrayList<IRP_REP_FILTERSVO>> filtersMap = new HashMap<BigDecimal, ArrayList<IRP_REP_FILTERSVO>>();
	IRP_AD_HOC_REPORTSC sc;
	// list of arguments in csv for given report
	List<IRP_REP_ARGUMENTSVO> currentArgsInCSVList;
	IRP_REP_ARGUMENTSVO curArgVO;
	// list of arg filters in the database
	ArrayList<IRP_REP_ARGUMENTS_FILTERSVO> listArgsFilters;
	// list of filters in database
	ArrayList<IRP_REP_FILTERSVO> filtersList;
	// list of arguments ids to be retrieved later from database from
	// irp_rep_arguments_filters table
	ArrayList<BigDecimal> listArgsIds;
	// argument id in db
	BigDecimal dbArgId;
	// csv argId,db argId. mapping for the arguments having same name and
	// type in db as in csv
	HashMap<BigDecimal, BigDecimal> argCsvDBMappingMap;
	// db filter id, db filter id (contains the filters to be saved into
	// filtersMap
	HashMap<BigDecimal, BigDecimal> filtersToSaveMap;
	IRP_REP_FILTERSVO filterVO = new IRP_REP_FILTERSVO();
	for(Entry<BigDecimal, IRP_AD_HOC_REPORTVOWithBLOBs> entry : repIdOldNewReplaceMap.entrySet())
	{
	    sc = new IRP_AD_HOC_REPORTSC();
	    argCsvDBMappingMap = new HashMap<BigDecimal, BigDecimal>();
	    listArgsFilters = new ArrayList<IRP_REP_ARGUMENTS_FILTERSVO>();
	    filtersToSaveMap = new HashMap<BigDecimal, BigDecimal>();
	    csvReportId = entry.getKey();
	    listArgsIds = new ArrayList<BigDecimal>();
	    bufVO = entry.getValue();
	    dbReportId = bufVO.getREPORT_ID();
	    reportsId.add(dbReportId);
	    //check if any filters for this report
	    sc.setREPORT_ID(dbReportId);
	    if(designerDAO.checkIfAnyFilterForCurrentReport(sc) > 0)
	    {
		// check which arguments still have same name and type for the
		// current report
		currentArgsInCSVList = irpRepArgHM.get(csvReportId);
		// loop on the current arguments (in csv) of the report in db
		for(int i = 0; i < currentArgsInCSVList.size(); i++)
		{
		    curArgVO = currentArgsInCSVList.get(i);
		    //encountered null value in to_save_yn when importing old template report
		    if(NumberUtil.emptyDecimalToZero(curArgVO.getTO_SAVE_YN()).intValue() == 1)
		    {
			sc.setReportArgumentName(curArgVO.getARGUMENT_NAME());
			sc.setArgumentType(curArgVO.getARGUMENT_TYPE());
			/*
			 * check if arg same name and type.Have to retrieve all
			 * data for a report_id in filterArgs table. should make
			 * mapping between arg_id on db and arg_id in excel
			 * based on arg name
			 */
			dbArgId = designerDAO.checkArgSameNameAndType(sc);
			if(dbArgId != null && dbArgId.compareTo(BigDecimal.ZERO) == 1)
//			if(NumberUtil.nullToZero(dbArgId).intValue()>0)
			{
			    /*
			     * above method will return the argument_id on db
			     * for the current argument with condition that
			     * argument exists on db with same name and same
			     * type
			     */
			    argCsvDBMappingMap.put(dbArgId,curArgVO.getARGUMENT_ID());
			    listArgsIds.add(dbArgId);
			}
		    }
		}
		/*
		 * Added below condition to avoid the case where several
		 * arguments were already saved in rep_arguments_filters and
		 * none of the argument of the current imported report have the
		 * save flag checked
		 */
		if(!listArgsIds.isEmpty())
		{
		    sc.setListArgsIds(listArgsIds);
		    /*
		     * returning the irp_rep_arg_filter records for arguments
		     * having name and type unchanged
		     */
		    listArgsFilters = designerDAO.retArgumentsFiltersByReport(sc);
		}
		/*
		 * handling if the user deletes several arguments from the
		 * report on other db and then reinsert the arguments in
		 * different orders and saves the report.The argument will be
		 * the same if same type but with different argument id.So when
		 * inserting in arguments filters table, must insert the new
		 * argument id and not the already existing one.
		 */
		for(int i = 0; i < listArgsFilters.size(); i++)
		{
		    dbArgId = listArgsFilters.get(i).getARGUMENT_ID();
		    /*
		     * replacing the db argument id in listArgsFilters with the
		     * argument id of the argument in csv
		     */
		    listArgsFilters.get(i).setARGUMENT_ID(argCsvDBMappingMap.get(dbArgId));
		    filtersToSaveMap.put(listArgsFilters.get(i).getFILTER_ID(), listArgsFilters.get(i).getFILTER_ID());
		}
		/*
		 * listArgsFilters contains records from rep_argument_filters
		 * table with argument id from csv. NB: In the import process,
		 * when inserting the arguments they will have always same
		 * argument_id as the one in their csv file.
		 */
		filtersArgumentsMap.put(dbReportId, listArgsFilters);
		// filling the hashmap of filters to be reinserted later in the
		// process
		filtersList = new ArrayList<IRP_REP_FILTERSVO>();
		for(Entry<BigDecimal, BigDecimal> entryFilters : filtersToSaveMap.entrySet())
		{
		    filterVO.setFILTER_ID(entryFilters.getKey());
		    filtersList.add((IRP_REP_FILTERSVO) genericDAO.selectByPK(filterVO));
		}
		filtersMap.put(dbReportId, filtersList);
		// end
	    }
	    //added if to avoid having arraylist containing nulls.issue on sybase
	    if(bufVO.getOLD_REPORT_ID()!=null)
	    {
		oldReportsId.add(bufVO.getOLD_REPORT_ID());
	    }
	    appsName.add(bufVO.getAPP_NAME());
	    bufProgRef = bufVO.getPROG_REF();
	    progRefBuf.append(bufProgRef + "," + bufProgRef + "D," + bufProgRef + "M," + bufProgRef + "SV,"
		    + bufProgRef + "SA," + bufProgRef + "SM," + bufProgRef + "SC," + bufProgRef + "PR,");
	}
	progRefBuf = new StringBuffer(progRefBuf.toString().substring(0, progRefBuf.toString().length() - 1));
	String[] tab = progRefBuf.toString().split(",");
	List<String> progRefList = Arrays.asList(tab);
	// new modif
	// contains id of the sub_report and corresponding list of
	// irpsubreportVOs to be inserted(when a report is used as subreport in more than one report)
	HashMap<BigDecimal, ArrayList<IRP_SUB_REPORTCO>> retSubRepId = new HashMap<BigDecimal, ArrayList<IRP_SUB_REPORTCO>>();
	ArrayList<IRP_SUB_REPORTCO> toBeReinserted;
	IRP_SUB_REPORTCO retCO = new IRP_SUB_REPORTCO();
	retCO.setREPORTS_ID(reportsId);
	// reportsId are Ids of the reports in db to be deleted
	for(int i = 0; i < reportsId.size(); i++)
	{
	    retCO.getIrpSubReportVO().setREPORT_ID(reportsId.get(i));
	    // toBeReinserted contains records to be reinserted for a given
	    // sub_report_id
	    toBeReinserted = designerDAO.retRepsBySubRepId(retCO);
	    for(int j = 0; j < toBeReinserted.size(); j++)
	    {
		genericDAO.delete(toBeReinserted.get(j).getIrpSubReportVO());
	    }
	    if(!toBeReinserted.isEmpty())
	    {
		retSubRepId.put(reportsId.get(i), toBeReinserted);
	    }
	}
	deleteReport(reportsId, axsCO, null, oldReportsId, appsName, progRefList, useExistingOptAccess,userName,pageRef,false);
	HashMap<String, Object> returnHm = new HashMap<String, Object>();
	returnHm.put(RepConstantsCommon.FILTERS_MAP, filtersMap);
	returnHm.put(RepConstantsCommon.ARG_FILTERS_MAP, filtersArgumentsMap);
	returnHm.put(RepConstantsCommon.SUB_REP_MAP,retSubRepId);
	return returnHm;
    }
    
    public ArrayList<IRP_AD_HOC_REPORTVO> retParentReports(BigDecimal reportId) throws BaseException
    {
	return designerDAO.retParentReports(reportId);
    }    
    
    public List<PTH_CLIENTSCO> retRepClientLst(PTH_CLIENTSSC pthClientSC) throws BaseException
    {
	return designerDAO.retRepClientLst(pthClientSC);
    }

    public int retRepClientLstCount(PTH_CLIENTSSC pthClientSC) throws BaseException
    {
	return designerDAO.retRepClientLstCount(pthClientSC);
    }

    /**
     * return access right for given ProgRef and Application Name, usefull when
     * called between applications
     * @param progRef prog reference
     * @param theAppName application Name
     * @return
     */
    public String returnAccessRightByProgRef(String progRef, String theAppName, SessionCO sesCO) throws BaseException
    {
	String allowAccess = null;
	HashMap axsHm = sesCO.getUserAxsMap() == null ? new HashMap() : sesCO.getUserAxsMap();
	HashMap<String, String> axsPerUserHm = axsHm.get(sesCO.getUserName()) == null ? new HashMap<String, String>()
		: (HashMap<String, String>) axsHm.get(sesCO.getUserName());
	if(axsPerUserHm.containsKey(progRef + "_" + theAppName))
	{
	    allowAccess = axsPerUserHm.get(progRef + "_" + theAppName);
	}
	else
	{
	    CommonLibSC criteria = new CommonLibSC();
	    criteria.setCompCode(sesCO.getCompanyCode());
	    criteria.setBranchCode(sesCO.getBranchCode());
	    criteria.setAppName(theAppName);
	    criteria.setUserId(sesCO.getUserName());
	    criteria.setProgRef(progRef);
	    
	    PTH_CTRLVO pthCtrl = commonLibBO.returnPthCtrl();
	    criteria.setProfType(NumberUtil.nullToZero(pthCtrl.getPOP_PROF_MOD_ACCESS()));
	    
	    allowAccess = commonLibBO.checkUserPrivileges(criteria);
	    axsPerUserHm.put(progRef + "_" + theAppName, allowAccess);
	    axsHm.put(sesCO.getUserName(), axsPerUserHm);
	    sesCO.setUserAxsMap(axsHm);
	}
	return allowAccess;
    }
    
    public HashMap<Long, IRP_FIELDSCO> returnVariablesMapByReportId(BigDecimal reportId,String userName)throws BaseException
    {
	HashMap<Long, IRP_FIELDSCO> varMap = new HashMap<Long, IRP_FIELDSCO>();
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	Calendar cal = Calendar.getInstance();
	String reportPath =repositoryPath+"/mail_"+userName+"_"+cal.getTimeInMillis()+".jrxml";
	try
	{
	    IRP_AD_HOC_REPORTCO repCO = returnReportById(reportId);
	    JasperDesign jasperDesign;
	   
	    FileUtil.writeFile(reportPath, new String(repCO.getJRXML_FILE(),ConstantsCommon.FILE_ENCODING),ConstantsCommon.FILE_ENCODING);
	    jasperDesign = JRXmlLoader.load(reportPath);
	    
	    varMap=ReportUtils.returnRepVariablesMap(jasperDesign);
	   
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	finally
	{
		try {
			File file = new PathFileSecure(reportPath);
			boolean isDel = file.delete();
		    if(!isDel)
		    {
			log.debug("The following file has not been deleted :" + file);
		    }
		} catch (Exception e) {
			log.error(e, e.getMessage());
		}
	}
	return varMap;
    }
    
    /**
     * method that returns an opt object with all related data if it's the first
     * opt record (without the extensions).if the first opt not found , the
     * method returns an opt object with prog_Ref and app_name being set in it only.
     * @param voExists
     * @return
     * @throws BaseException
     */
    private OPTVO checkIfAnyOptInDb(OPTVOKey voExists) throws BaseException
    {
	// below old method used to check existence of opt without other
	// extensions
	// in case null then check existence of other extensions in the if
	// clause
	OPTVO result = checkIfSkipOptAxs(voExists);
	if(result == null)
	{
	    String pRef = voExists.getPROG_REF();
	    List<String> allOpts = new ArrayList<String>();
	    addExtensions(allOpts, pRef);
	    FTR_OPTCO optCO = new FTR_OPTCO();
	    optCO.setPROG_REFS(allOpts);
	    optCO.getFtrOptVO().setAPP_NAME(voExists.getAPP_NAME());
	    optCO.getFtrOptVO().setPROG_REF(voExists.getPROG_REF());
	    List<FTR_OPTCO> savedOptsCO = fcrBO.retSavedOptsByProgRef(optCO);
	    if(!savedOptsCO.isEmpty())
	    {
		result = new OPTVO();
		result.setPROG_REF(voExists.getPROG_REF());
		result.setAPP_NAME(voExists.getAPP_NAME());
	    }
	    return result;
	}
	return result;
    }
    

    /**
     * method that deletes all opts,opt_extended,s_role_detail and axs related to a prog_ref
     * @param axsCO
     * @param optVO
     * @throws BaseException
     */
    private void deleteAllOpts(AXSCO axsCO, OPTVO optVO,String userName,String pageRef) throws BaseException
    {
	FTR_OPTCO fcrCO = new FTR_OPTCO();
	// delete from opt extended
	String pRef = optVO.getPROG_REF();
	List<String> allOpts = new ArrayList<String>();
	addExtensions(allOpts, pRef);
	fcrCO.setPROG_REFS(allOpts);
	fcrCO.getFtrOptVO().setAPP_NAME(optVO.getAPP_NAME());
	fcrCO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,axsCO.getHttpSessionIdForLink());
	fcrBO.deleteOptExtended(fcrCO);
	// delete Axs
	AXSCO zAxsCO=fillAxsCOProps(axsCO.getUSER_ID(), optVO.getAPP_NAME(), allOpts,axsCO.getCOMP_CODE(),axsCO.getBRANCH_CODE());
	zAxsCO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,axsCO.getHttpSessionIdForLink());
	fcrBO.deleteAxs(zAxsCO);
	//added to delete also from this table since foreign key to opt 
	IRP_CLIENT_REPORTVO irpCltRep = new IRP_CLIENT_REPORTVO();
	irpCltRep.setAPP_NAME(optVO.getAPP_NAME());
	irpCltRep.setREPORT_REF(pRef);
	designerDAO.deleteRepClientLst(irpCltRep);
	//Delete from s_role_detail since we have foreign key to OPT table under specific DBs
	fcrBO.deleteSRoleDetail(fcrCO);
	fcrCO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,axsCO.getHttpSessionIdForLink());
	fcrBO.deleteOpts(fcrCO);
    }
    
    public void addExtensions(List<String> list,String pRef)
    {
	list.add(pRef);
	list.add(pRef + RepConstantsCommon.OPT_DEL_D);
	list.add(pRef + RepConstantsCommon.OPT_MOD_M);
	list.add(pRef + RepConstantsCommon.OPT_SV_SV);
	list.add(pRef + RepConstantsCommon.OPT_SA_SA);
	list.add(pRef + RepConstantsCommon.OPT_SM_SM);
	list.add(pRef + RepConstantsCommon.OPT_SC_SC);
	list.add(pRef + RepConstantsCommon.OPT_PR_PR);
    }
    
    /**
     * method that will add missing access records
     * @AXSVO axsVO
     * @throws BaseException
     */    
    public void addMissingAxs(AXSVO axsVO,boolean useExistingOptAccess,String userName,String pageRef) throws BaseException
    {
	String pRef = axsVO.getPROG_REF();
	List<String> allAxs = new ArrayList<String>();
	List<String> missedAxs = new ArrayList<String>();
	List<String> savedAxs = new ArrayList<String>();
	String currAxs;
	AXSCO retAxsCO;
	addExtensions(allAxs,pRef);
	AXSCO axsCO = new AXSCO();
	axsCO.setPROG_REFS(allAxs);
	axsCO.setAPP_NAME(axsVO.getAPP_NAME());
	axsCO.setPROG_REF(axsVO.getPROG_REF());
	axsCO.setUSER_ID(axsVO.getUSER_ID());
	axsCO.setCOMP_CODE(axsVO.getCOMP_CODE());
	axsCO.setBRANCH_CODE(axsVO.getBRANCH_CODE());
	List<AXSCO> savedAxsCO;
	if(useExistingOptAccess)
	{
	    // below function returns all existing axs in db
	    savedAxsCO = fcrBO.retAxsByReport(axsCO);
	}
	else
	{
	    // delete all previous Axs
	    AXSCO zAxsCO=fillAxsCOProps(axsCO.getUSER_ID(), axsCO.getAPP_NAME(), allAxs, axsCO.getCOMP_CODE(), axsCO
		    .getBRANCH_CODE());
	    zAxsCO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,axsVO.getHttpSessionIdForLink());
	    fcrBO.deleteAxs(zAxsCO);
	    savedAxsCO = new ArrayList<AXSCO>();
	}
	for(int i = 0; i < savedAxsCO.size(); i++)
	{
	    retAxsCO = savedAxsCO.get(i);
	    savedAxs.add(retAxsCO.getPROG_REF());
	}
	// savedAxs contains now all prog_refs in db
	for(int i = 0; i < allAxs.size(); i++)
	{
	    currAxs = allAxs.get(i);
	    if(!savedAxs.contains(currAxs))
	    {
		missedAxs.add(currAxs);
	    }
	}
	// missedAxs will contain the missing Axs for a given prog_ref
	if(!missedAxs.isEmpty())
	{
	    fcrBO.saveMissedAxs(axsVO, missedAxs);
	}
    }
    
    /**
     * Method that fills the parentsList with the parent OPTs of the report
     * @param repOptHM
     * @param paramSC
     * @param parentsList
     * @param reportRef
     * @return
     * @throws BaseException
     */

    public ArrayList<OPTVO> saveParentReferences(HashMap<String, OPTVO> repOptHM, IRP_AD_HOC_REPORTSC paramSC,
	    ArrayList<OPTVO> parentsList) throws BaseException
    {
	OPTVO optVO;
	String parentRef;
	ArrayList<OPTVO> curParentsList = parentsList;
	IRP_AD_HOC_REPORTSC curParamSC = paramSC;
	optVO = repOptHM.get(paramSC.getPROG_REF() + "_" + paramSC.getAPP_NAME());
	parentRef = optVO.getPARENT_REF();
	for(Entry<String, OPTVO> entry : repOptHM.entrySet())
	{
	    optVO = entry.getValue();
	    if(optVO.getPROG_REF().equals(parentRef))
	    {
		if(!ConstantsCommon.PROGREF_ROOT.equals(parentRef))
		{
		    curParentsList.add(optVO);
		    curParamSC.setPROG_REF(parentRef);
		    curParentsList = saveParentReferences(repOptHM, curParamSC, curParentsList);
		}
		break;
	    }
	}
	return curParentsList;
    }
    

    
    /**
     * method that returns an AXSCO object filled with the values passed as
     * parameters
     * @param userId
     * @param appName
     * @param progRefs
     * @param compCode
     * @param branchCode
     * @return
     */
    public AXSCO fillAxsCOProps(String userId, String appName, List<String> progRefs, BigDecimal compCode,
	    BigDecimal branchCode)
    {
	AXSCO axsCO = new AXSCO();
	axsCO.setUSER_ID(userId);
	axsCO.setAPP_NAME(appName);
	axsCO.setPROG_REFS(progRefs);
	if(ConstantsCommon.SADS_APP_NAME.equals(appName))
	{
	    axsCO.setCOMP_CODE(BigDecimal.ZERO);
	    axsCO.setBRANCH_CODE(BigDecimal.ZERO);

	}
	else
	{
	    axsCO.setCOMP_CODE(compCode);
	    axsCO.setBRANCH_CODE(branchCode);
	}
	return axsCO;
    }
    public IRP_AD_HOC_REPORTCO writeUploadedReportfile(IRP_AD_HOC_REPORTCO repCO,ArrayList<ImageCO> imgLst ) throws BaseException
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	try
	{
	    FileUtil.writeFile(repositoryPath + "/updateJasperImgReport.jrxml", new String(repCO.getJRXML_FILE(),
		    FileUtil.DEFAULT_FILE_ENCODING), ConstantsCommon.FILE_ENCODING);
	    JasperDesign jasperDesign = JRXmlLoader.load(repositoryPath + "/updateJasperImgReport.jrxml");

	    jasperDesign = ReportUtils.updateImgPath(jasperDesign, imgLst, repositoryPath);

	    byte[] jrxml = FileUtil.readFileBytes(repositoryPath + "/updateJasperImgReport.jrxml");
	    repCO.setJRXML_FILE(jrxml);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	return repCO;

    }
    
    /**
     * Write the full export related files and zipping its follder to the repository
     * @param orderedRepIdsBuf
     * @param userName
     * @param zipPassword
     * @return Byte[] (the zipped file bytes)
     */
    public byte[] writeReportExportFile(StringBuffer orderedRepIdsBuf,String userName, String zipPassword) throws BaseException
    {       
	byte[] zipBytes = null;
	 
	 String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);   
	try {
	    String flStep = (repositoryPath + "/" + RepConstantsCommon.EXPORT_REP +"/"+RepConstantsCommon.REP_EXT+userName+"/"+RepConstantsCommon.STEPS_TXT);
	    FileOutputStream fosStep = new FileOutputStream(flStep);
	    Writer fileStepsWriter = new OutputStreamWriter(fosStep,FileUtil.DEFAULT_FILE_ENCODING);
	     
	    fileStepsWriter.write(orderedRepIdsBuf.toString());
	    fileStepsWriter.close();
	    
	    
	    FileZipUtil.generateProtectedZipFolder(ReportingConstantsCommon.reportingFolder + "/"+ RepConstantsCommon.EXPORT_REP + "/"+ RepConstantsCommon.REP_EXT + userName, zipPassword,true,false);
	    
	    zipBytes = FileUtil.readFileBytes(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
		    + RepConstantsCommon.REP_EXT + userName+  "." + ConstantsCommon.ZIP_EXT);
	}

	catch(Exception e)
	{
	    log.error(e, "");
	}
	
	return zipBytes;
	   
    }
    /**
     * Creating the files and folder needed in export 
     * @param userName
     * @param fullBasicExp (a flag specifying if it is a full or basic export
     * @return
     */
    public void createReportExportFile(String userName,  String fullBasicExp ) throws BaseException
   
    {
	   try 
	   {
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    FileUtil.makeDirectories(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP);
	    boolean isCreatedImages = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
			+ RepConstantsCommon.REP_EXT + userName + "/" + RepConstantsCommon.IMAGES_LOCATION).mkdir();
	
	  
	    File flSub = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
		    + RepConstantsCommon.REP_EXT + userName);
	    // to delete the folder
	    if(flSub.exists())
	    {
		FileUtil.deleteFolder(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
			+ RepConstantsCommon.REP_EXT + userName);
	    }
	    File flZip = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
		    + RepConstantsCommon.REP_EXT + userName + "." + ConstantsCommon.ZIP_EXT);

	    if(flZip.exists())
	    {
		boolean isDel = flZip.delete();
		if(!isDel)
		{
		    log.debug("The following file has not been deleted :" + userName);
		}
	    }
	    boolean isCreatedSub = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
		    + RepConstantsCommon.REP_EXT + userName).mkdir();
	    if(!isCreatedSub)
	    {
		log.debug("The directory " + RepConstantsCommon.EXPORT_REP + " has not been created");
	    }
	   
	    if(("1").equals(fullBasicExp) && (!isCreatedSub || !isCreatedImages))
	    {
		log.debug("The directory " + RepConstantsCommon.EXPORT_REP + " has not been created");
	    }
	}
	   catch(Exception e)
		{
		    log.error(e, "");
		}
    }

    /**
     * Start the Basic Export Process 
     * @param userName
     * @param subRepCO
     * @param jasperDesign
     * @return
     */
    public JasperDesign startBasicExportFile(String userName,  IRP_SUB_REPORTCO subRepCO , JasperDesign jasperDesign) throws BaseException
    
    {
	JasperDesign jasperDes= jasperDesign;
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	String filePath;
	try
	{
	    FileUtil.writeFile(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT
		    + userName + "/" + subRepCO.getPROG_REF() + "_" + subRepCO.getAPP_NAME() + "."+ConstantsCommon.JRXML_EXT, new String(
		    subRepCO.getJRXML_FILE(), FileUtil.DEFAULT_FILE_ENCODING), ConstantsCommon.FILE_ENCODING);

	    filePath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder) + "/"
		    + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT + userName + "/"
		    + subRepCO.getPROG_REF() + "_" + subRepCO.getAPP_NAME() + "."+ConstantsCommon.JRXML_EXT;

	    jasperDes = JRXmlLoader.load(filePath);
	}
	catch(UnsupportedEncodingException e)
	{
	  throw new BOException("UnsupportedEncoding",e); 
	}
	catch(FileNotFoundException e)
	{
	    throw new BOException("FileNotFound",e); 
	}

	catch(JRException e)
	{
		try {
			File file = new PathFileSecure( repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT + userName
				   + "/" + subRepCO.getSUB_REPORT_NAME() + ".jrxml");
			boolean isDel = file.delete();
			   if(!isDel)
			   {
			       log.error("The following file has not been deleted :");
			   }
	    throw new BOException("JRException",e); 
		} catch (Exception e1) {
			log.error(e1, e1.getMessage());
		}
	}
	catch(SAXException e)
	{
	    throw new BOException("SAXException",e); 
	}
	catch(Exception e)
	{
	    throw new BOException("Exception",e); 
	}
	return jasperDes;
    }
    
    /**
     * Check if the passed image exists on the repository
     * @param imgName
     * @return true if the image exists and false otherwise
     */
 public boolean checkImgExist(String imgName) throws BaseException
    
    { String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
      String imagePathSrc = repositoryPath + "/" + RepConstantsCommon.IMAGES_LOCATION;  
      File sourceFile = null;
	try {
		sourceFile = new PathFileSecure(imagePathSrc + "/" +imgName);
	} catch (Exception e) {
		log.error(e, e.getMessage());
	}
      return (sourceFile.exists());
      
    }
 
 /**
  * Write the full export related files and zipping its follder to the repository
  * @param userName
  * @param imagesList (images list found in the report
  * @param zipPassword
  * @param errorStr (string buffer containing the export errors so to write them to the error file on the repository
  * @return Byte[] (the zipped file bytes)
  */
 public byte[] writeBasicExportFile(String userName,List<ImageCO> imagesList, String zipPassword,  StringBuffer errorStr )  throws BaseException
 
 {  	byte[] zipBytes = null;
 	
            String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	  
	
		String flError = (repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
			+ RepConstantsCommon.REP_EXT + userName + "/" + "error.txt");
		 try
		    {
		     FileOutputStream fos = new FileOutputStream(flError);
			Writer writer = new OutputStreamWriter(fos, FileUtil.DEFAULT_FILE_ENCODING);// ,"UTF-8");
			 writer.write(errorStr.toString());
			    writer.close();
		    }
		    catch(Exception e)
		    {
			 log.error(e, "");
		    }  
		
    	        String imagePathSrc = repositoryPath + "/" + RepConstantsCommon.IMAGES_LOCATION;
    	   String imagePathDest = repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT + userName + "/"
    	   + RepConstantsCommon.IMAGES_LOCATION;
    	   copyImages(imagesList,imagePathSrc,imagePathDest);

	    try
	    {
		FileZipUtil.generateProtectedZipFolder(ReportingConstantsCommon.reportingFolder + "/"+ RepConstantsCommon.EXPORT_REP + "/"+ RepConstantsCommon.REP_EXT + userName, zipPassword,true,false);
		 zipBytes = FileUtil.readFileBytes(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
			    + RepConstantsCommon.REP_EXT + userName+  "." + ConstantsCommon.ZIP_EXT);

	    }
	    catch(Exception e)
	    {
		 log.error(e, "");
	    }  
	   return zipBytes;
	    
 } 
 /**
  * Delete the files created on repository in the basic export process
  * @param userName
  * @return
  */

    public void deleteBasicExportFile(String userName) throws BaseException
    {
	File flZip = null;
	File flImg = null;
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	try
	{
	    FileUtil.deleteFolder(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
		    + RepConstantsCommon.REP_EXT + userName); // to delete the
							      // folder
	    flZip = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT
		    + userName + "." + ConstantsCommon.ZIP_EXT);
	    flImg = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/" + RepConstantsCommon.REP_EXT
		    + userName + "/" + RepConstantsCommon.IMAGES_LOCATION);

	    if(flZip.exists())
	    {
		boolean isDel = flZip.delete();
		if(!isDel)
		{
		    log.debug("The following file has not been deleted :" + userName);
		}
	    }
	    if(flImg.exists())
	    {
		FileUtil.deleteFolder(repositoryPath + "/" + RepConstantsCommon.EXPORT_REP + "/"
			+ RepConstantsCommon.REP_EXT + userName + "/" + RepConstantsCommon.IMAGES_LOCATION);
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}

    } 
 
 /**
  * method that deletes the file passed as parameter after checking if it exists in the repository
  * @param fileName
  * @return
  */
 public void deleteFileFromRepository(String fileName) throws BaseException 
 {
	 // delete saved file
         String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);   
		try {
			File file = new PathFileSecure(repositoryPath + "/" + fileName);
			boolean isDel = file.delete();
		    if(!isDel)
		    {
			log.debug("The following file has not been deleted :" + file);
		    }
		} catch (Exception e) {
			log.error(e, e.getMessage());
		}
	    
 }

 
 /**
  *  method to write file to the services repository
  */
 public void writeFileToRepository(String fileName,String fileContent,String ext)throws BaseException 
 {
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    try
	    {
		FileUtil.writeFile(repositoryPath + "/" + fileName+ "."+ext, fileContent);
	    }
	    catch(Exception e)
	    {
		throw new BOException(e.getMessage(),e);
	    }
	
 }
 /**
  * Delete the zip folder in finally statement 
  * @author: Nabiha Ojeil
  * @param fileZipLocation:path of the zip file ; unzippedRepFolderLocation:location of the unzipped report folder
  * @return
  */
    public void deleteZipFolder(String fileZipLocation , String unzippedRepFolderLocation) throws BaseException
    {
	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	try
	{
	    File flZip = new PathFileSecure(repositoryPath + "/" + fileZipLocation);
	    if(flZip.exists())
	    {
		boolean isDel = flZip.delete();
		if(!isDel)
		{
		    log.debug("The following file has not been deleted :" + repositoryPath + "/" + fileZipLocation);
		}
	    }

	    FileUtil.deleteFolder(repositoryPath + "/" +unzippedRepFolderLocation);
	}

	catch(Exception e)
	{
	    log.error(e, ">>>>>>>>>>>>>> error deleting the folder " + repositoryPath + "/" + unzippedRepFolderLocation);
	}
    }

    /**
     * Returns the default client that has default_yn flag set to Y
     **/
    public PTH_CLIENTSCO returnDefaultClient() throws DAOException
    {
	return designerDAO.returnDefaultClient();
    }

    /**
     * Returns the client name based on the client acronym
     **/
    public PTH_CLIENTSCO returnClient(PTH_CLIENTSSC pthClientSC) throws DAOException
    {
	return designerDAO.returnClient(pthClientSC);
    }
    
    public ArrayList<AUTOMATED_IMPORT_REPORTSCO> retAutoImpRepGridRecords(String path) throws BaseException
    {
	File automatedReportsDir = null;
	try {
		automatedReportsDir = new PathFileSecure(path);
	} catch (Exception e1) {
		log.error(e1, e1.getMessage());
	}
	if(!automatedReportsDir.exists() || !automatedReportsDir.isDirectory())
	{
	    BOException e = new BOException();
	    e.setMsgIdent(RepConstantsCommon.INVALID_PATH);
	    throw e;
	}
	ArrayList<AUTOMATED_IMPORT_REPORTSCO> reportsList = new ArrayList<AUTOMATED_IMPORT_REPORTSCO>();
	String[] zipFilesList = automatedReportsDir.list();
	for(int i = 0; i < zipFilesList.length; i++)
	{
	    int idx = zipFilesList[i].lastIndexOf(".");
	    if(idx != -1)
	    {
		String ext = zipFilesList[i].substring(idx + 1);
		if(ConstantsCommon.ZIP_EXT.equalsIgnoreCase(ext))
		{
		    AUTOMATED_IMPORT_REPORTSCO autoImpRepCO = new AUTOMATED_IMPORT_REPORTSCO();
		    autoImpRepCO.setZIP_FILE_NAME(zipFilesList[i]);
		    autoImpRepCO.setACTION(RepConstantsCommon.REPLACE);
		    // set by default the UseExisting OPT Checked, Based on Yamen Kiwan Request on Site at BBS 
		    autoImpRepCO.setUSE_EXISTING_OPT(true);
		    autoImpRepCO.setUPDATE_VERSION_IF_EQUAL(true);
		    reportsList.add(autoImpRepCO);
		}
	    }
	}
	return reportsList;
   }
    
    @Override
    public IRP_AD_HOC_REPORTCO returnFullReportByRef(IRP_AD_HOC_REPORTSC reportSC) throws Exception
    {
	IRP_AD_HOC_REPORTCO repIdCO=retRepIdByProgRef(reportSC);
	return returnReportById(repIdCO.getREPORT_ID());
	
    }
    
    @Override
    public IRP_AD_HOC_REPORTVOWithBLOBs retExistRepInDb(IRP_AD_HOC_REPORTVOWithBLOBs repVO) throws DAOException
    {
    	//case where the report id is not sent but the reporot reference and the application name
    	if(repVO.getREPORT_ID()==null || repVO.getREPORT_ID().equals(new BigDecimal(-1)))
    	{
    	    return designerDAO.retExistRepInDb(repVO);
    	}
    	else
    	{
    	    return  designerDAO.retRepDetailsByRepId(repVO);
    	    
    	}
    }
    
    
    @Override
    public ArrayList<BigDecimal> retMetaDataReports(ArrayList<BigDecimal> repIdList) throws BaseException
    {
	return designerDAO.retMetaDataReports(repIdList);
    }
    
    @Override
    public ArrayList<IRP_AD_HOC_REPORTVO> retReportsBeingMetadata(List<BigDecimal> repIdList) throws BaseException
    {
	return designerDAO.retReportsBeingMetadata(repIdList);
    }
    
    @Override
    public ArrayList<IRP_REP_METADATA_LOGVO>retAlreadyExportedArgsLogs (IRP_AD_HOC_REPORTSC sc) throws BaseException
    {
	return designerDAO.retAlreadyExportedArgsLogs(sc);
    }
    
    @Override
    public void insertReportExecLog_NewTrans(IRP_REPORT_EXEC_LOGVO repExecLogParamsVO) throws BaseException
    {
    	designerDAO.insertReportExecLog_NewTrans(repExecLogParamsVO);
    }
    
    @Override
    public void updateReportExecLog_NewTrans(IRP_REPORT_EXEC_LOGVO repExecLogParamsVO) throws BaseException
    {
    	genericDAO.update(repExecLogParamsVO);
    }

}
