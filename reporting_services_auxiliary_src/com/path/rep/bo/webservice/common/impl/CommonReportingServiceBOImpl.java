package com.path.rep.bo.webservice.common.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ArrayUtils;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.login.LoginBO;
import com.path.bo.reporting.CommonReportingBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.DesignerBO;
import com.path.dbmaps.vo.BRANCHESVO;
import com.path.dbmaps.vo.BRANCHESVOKey;
import com.path.dbmaps.vo.COMPANIESVO;
import com.path.dbmaps.vo.CURRENCIESVO;
import com.path.dbmaps.vo.CURRENCIESVOKey;
import com.path.dbmaps.vo.PTH_CTRLVO;
import com.path.dbmaps.vo.RIFCRTVO;
import com.path.dbmaps.vo.SYS_PARAM_LANGUAGESVO;
import com.path.dbmaps.vo.S_APPVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.SecurityUtils;
import com.path.lib.common.util.StringUtil;
import com.path.lib.reporting.exception.ReportException;
import com.path.lib.util.ServiceUtil;
import com.path.rep.bo.webservice.common.CommonReportingServiceBO;
import com.path.rep.bo.webservice.util.RepServiceUtilBO;
import com.path.struts2.lib.common.base.GridBaseAction;
import com.path.vo.admin.user.UsrCO;
import com.path.vo.common.SessionCO;
import com.path.vo.common.select.SelectCO;
import com.path.vo.common.select.SelectSC;
import com.path.vo.rep.report.GenerateReportReq;
import com.path.vo.rep.report.GenerateReportResp;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.ReportParamsCO;

public class CommonReportingServiceBOImpl extends BaseBO implements CommonReportingServiceBO
{
    RepServiceUtilBO repServiceUtilBO;
    CommonReportingBO commonReportingBO;
    DesignerBO designerBO;
    LoginBO loginBO;


    public void setRepServiceUtilBO(RepServiceUtilBO repServiceUtilBO)
    {
	this.repServiceUtilBO = repServiceUtilBO;
    }
    public void setCommonReportingBO(CommonReportingBO commonReportingBO)
    {
	this.commonReportingBO = commonReportingBO;
    }
    public void setLoginBO(LoginBO loginBO)
    {
	this.loginBO = loginBO;
    }
    public void setDesignerBO(DesignerBO designerBO)
    {
        this.designerBO = designerBO;
    }
    
    
    
    
    @Override
    /**
     * main method
     */
    public GenerateReportResp generateReport(HashMap<String, Object> hashIn) throws Exception
    {
	// convert hashin to request object
	GenerateReportReq generateReportReq = (GenerateReportReq) PathPropertyUtil.convertToObject(hashIn, GenerateReportReq.class);
	GenerateReportResp generateReportResp = new GenerateReportResp();
	
	//copy all request input to the response
	PathPropertyUtil.copyProperties(generateReportReq, generateReportResp);
	PathPropertyUtil.copyProperties(generateReportReq.getServiceContext(),generateReportResp.getResponseServiceContext());
	
	//simulate the session object that is loaded from action container and validate main attributes
	HashMap<String, Object> hm = loadSessionVariables(generateReportReq);
    
	// validation
	validateReport(generateReportReq);
	validateCsvSeparator(generateReportReq);
	validateReportFormat(generateReportReq);
	// validation
	//load the value of SessionCO into hm in order to be used when filling the parameters later
	loadSessionCOIntoHm(hm);
	
	String reportParams = (String) hashIn.get("reportParams");
	hm.put("reportParams", reportParams);
	//fill(convert) the values of the parameters based on report configuration
	HashMap<String, Object> reportParametersHm = fillParamValuesIntoHm(hm, generateReportResp);
	hm.put("reportParametersHm", reportParametersHm);
	
	//call this function to generate the report
	this.returnReportBytes(hm, generateReportResp);
	
	return generateReportResp;
    }

    
    private void validateCsvSeparator(GenerateReportReq generateReportReq) throws BaseException
    {
	String separator = generateReportReq.getReportDetail().getCsvSepartor();
	if(StringUtil.isNotEmpty(separator))
	{
	    String[] arr = new String[] {",","\t"};
	    if(!ArrayUtils.contains( arr, separator))
	    {
		throw new BOException(MessageCodes.PARAM1_IS_MISSING_INVALID, new String[] { "reporting.csvSeparator" }); 
	    }
	}
    }
    
    
    /**
     * simulate the same way the system is filling the parameters in ReportAction.generateReport() 
     * @param hm
     * @param generateReportResp
     * @return
     * @throws BaseException
     */
    private HashMap<String, Object> fillParamValuesIntoHm(HashMap<String, Object> hm,
	    GenerateReportResp generateReportResp) throws BaseException
    {
	HashMap<String, Object> reportParametersHm = new HashMap<String, Object>();

	SessionCO sessionCO = (SessionCO) PathPropertyUtil.convertToObject(hm, SessionCO.class);
	
	
	HashMap<String, Object> retMap = null;
	try
	{
	    retMap = commonReportingBO.returnReportById(generateReportResp.getReportDetail().getReportId());
	}
	catch(Exception e)
	{
	    BaseException baseException = new BaseException(e.getMessage(), e);
	    throw baseException;
	}
	IRP_AD_HOC_REPORTCO reportCO = (IRP_AD_HOC_REPORTCO) PathPropertyUtil.convertToObject(retMap, IRP_AD_HOC_REPORTCO.class);
	if(generateReportResp.getReportDetail().getFormat() == null)
	{
	    String defaultFormat = (String) retMap.get("default_FORMAT");
	    generateReportResp.getReportDetail().setFormat(defaultFormat);	    
	}
	
	LinkedHashMap<Long, IRP_REP_ARGUMENTSCO> argsMap = reportCO.getArgumentsList();
	ArrayList<IRP_REP_ARGUMENTSCO> argsList = new ArrayList<IRP_REP_ARGUMENTSCO>(argsMap.values());
	String argumentName;
	String parameterValue;
	String argumentType;
	String sessionParamName;
	BigDecimal argumnetSource;
	Object paramSessionVal = "";
	Date dt;

	String reportParams = (String) hm.get("reportParams");
	if(StringUtil.isNotEmpty(reportParams))
	{
	    fillAdviceParameters(generateReportResp, argsList, reportParams);
	}
	
	for(IRP_REP_ARGUMENTSCO argObj : argsList)
	{
	    argumentName = argObj.getARGUMENT_NAME();
	    argumentType = argObj.getARGUMENT_TYPE();
	    argumnetSource = argObj.getARGUMENT_SOURCE();
	    parameterValue = generateReportResp.getReportParametersList().get(argumentName);
	    paramSessionVal = "";
	    
	    //if the value is not sent from webservice then check if it should be loaded froms ession values
	    //if the value is sent from webservice then override even if the argument is set as session variable
	    if(parameterValue == null)
	    {
		if(argumnetSource.equals(ConstantsCommon.SESSION_ARG_SOURCE)
			|| argumnetSource.equals(ConstantsCommon.TRANS_SESSION_ARG_SOURCE))
		{
		    sessionParamName = argObj.getSESSION_ATTR_NAME();
		    paramSessionVal = (Object) retSessionVal(sessionParamName, sessionCO, argumnetSource,
			    generateReportResp.getRequesterContext().getLangId());
		}
		if(ConstantsCommon.PARAM_TYPE_DATE.equals(argumentType))
		{
		    if(paramSessionVal != null && !paramSessionVal.equals(""))
		    {
			parameterValue = DateUtil.format((Date) paramSessionVal, "dd/MM/yyyy");
		    }
		}
		else if(ConstantsCommon.datetime.equals(argumentType))
		{
		    if(paramSessionVal != null && !paramSessionVal.equals(""))
		    {
			parameterValue = DateUtil.format((Date) paramSessionVal, "dd/MM/yyyy hh:mm:ss");
		    }
		}
		else
		{
		    parameterValue = paramSessionVal.toString();
		}
	    }
	    
	    if(!(StringUtil.nullToEmpty(parameterValue)).isEmpty())
	    {
		if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(argumentType))
		{
		    if( NumberUtil.isNumber(parameterValue))
		    {
			reportParametersHm.put(argumentName, new BigDecimal(parameterValue));
		    }
		    else
		    {
			throw new BOException(MessageCodes.INVALID_MISSING_REPORT_FORMAT, new String[] { argumentName });
		    }
		}
		else if(ConstantsCommon.PARAM_TYPE_DATE.equals(argumentType))
		{
		    final String pattern = "yyyy-MM-dd";
		    if(DateUtil.isValidDate(parameterValue, pattern))
		    {
			dt = DateUtil.parseDate(parameterValue, pattern);
			reportParametersHm.put(argumentName, DateUtil.parseDate(parameterValue, pattern));
		    }
		    else
		    {
			throw new BOException(MessageCodes.INVALID_MISSING_REPORT_FORMAT, new String[] { argumentName });
		    }
		}
		else if(ConstantsCommon.datetime.equals(argumentType))
		{
		    final String pattern = "yyyy-MM-dd'T'HH:mm:ss";
		    if(DateUtil.isValidDate(parameterValue, pattern))
		    {
			dt = DateUtil.parseDate(parameterValue, pattern);
			reportParametersHm.put(argumentName, new java.sql.Timestamp(dt.getTime()));
		    }
		    else
		    {
			throw new BOException(MessageCodes.INVALID_MISSING_REPORT_FORMAT, new String[] { argumentName });
		    }
		}
		else
		{
		    reportParametersHm.put(argumentName, parameterValue);
		}
	    }
	}
	
	return reportParametersHm;
    }
    
    
    /**
     * this method simulates how the system is splitting the value of r_a_p (~#~) in ReportAction.generateReport()
     * in openPreviewAdvice(CommonFuncExtension.js) the parameters are sent by separator
     */
    private void fillAdviceParameters(GenerateReportResp generateReportResp, ArrayList<IRP_REP_ARGUMENTSCO> argsList,
	    String reportParams)
    {
	if(StringUtil.isNotEmpty(reportParams))
	{
	    HashMap<String, String> reportParametersList = new  HashMap<String, String>();
	    String[] reportParamsArr = reportParams.split(ConstantsCommon.REPORT_ARGUMENT_SEPARATOR);
	    for(int index = 0; index < reportParamsArr.length; index++)
	    {
		String argumentValue = reportParamsArr[index];
		IRP_REP_ARGUMENTSCO argumentCO = argsList.get(index);
		if(argumentCO != null)
		{
		   String argumentName = argumentCO.getARGUMENT_NAME();
		   reportParametersList.put(argumentName, argumentValue);
		}
	    }
	    generateReportResp.setReportParametersList(reportParametersList);
	}	
    }
    
    
    /**
     * 
     * @param hm
     */
    private void loadSessionCOIntoHm(HashMap<String, Object> hm)
    {
	SessionCO sessionCO = new SessionCO();
	sessionCO.setBaseCurrDecPoint((BigDecimal) hm.get("baseCurrDecPoint"));
	sessionCO.setBaseCurrencyCode((BigDecimal) hm.get("baseCurrencyCode"));
	sessionCO.setBaseCurrencyName((String) hm.get("baseCurrencyName"));
	sessionCO.setBranchCode((BigDecimal) hm.get("branchCode"));
	sessionCO.setClientType((String) hm.get("clientType"));
	sessionCO.setCompanyArabName((String) hm.get("companyArabName"));
	sessionCO.setCompanyCode((BigDecimal) hm.get("companyCode"));
	sessionCO.setCompanyName((String) hm.get("companyName"));
	sessionCO.setCurrentAppName((String) hm.get("currentAppName"));
	sessionCO.setExposCurCode((BigDecimal) hm.get("exposCurCode"));
	sessionCO.setExposCurName((String) hm.get("exposCurName"));
	sessionCO.setFiscalYear((BigDecimal) hm.get("fiscalYear"));
	sessionCO.setRunningDateRET((Date) hm.get("runningDateRET"));
	sessionCO.setLanguage((String) hm.get("language"));
	sessionCO.setUserName((String) hm.get("userName"));
	sessionCO.setBranchName((String) hm.get("branchName"));
	sessionCO.setIsRTLDir((Integer) hm.get("isRTLDir"));
	hm.put("sessionCO", sessionCO);
    }
    
    
    /**
     * this method to validate the report by criteria, and fill the relative reportId
     * @param generateReportReq
     * @throws Exception 
     */
    private void validateReport(GenerateReportReq generateReportReq) throws BaseException
    {
	//double checking, if ReportDetail is not filled the service will not work properly 
	if(generateReportReq.getReportDetail() == null)
	{
	    String labelDesc = repServiceUtilBO.returnKeyLabelTrans(generateReportReq.getReportDetail().getLanguage(), "report.generate.exceptionMsg._key");
	    throw new BOException(MessageCodes.PARAM1_IS_MISSING_INVALID, new String[] { labelDesc });
	}
	else
	{

	    IRP_AD_HOC_REPORTSC criteria = new IRP_AD_HOC_REPORTSC();
	    PTH_CTRLVO pthCtrl = commonLibBO.returnPthCtrl();
	    // fill basic parameters for the grid
	    criteria.setProfType(NumberUtil.nullToZero(pthCtrl.getPOP_PROF_MOD_ACCESS()));
	    criteria.setAPP_NAME(generateReportReq.getReportDetail().getReportApplicationName());
	    criteria.setBRANCH_CODE(generateReportReq.getBranchCode());
	    criteria.setCOMP_CODE(generateReportReq.getCompanyCode());
	    criteria.setUSER_ID(generateReportReq.getRequesterContext().getUserID());

	    // set the input parameters into the hashmap
	    HashMap<String, Object> filtersMap = new HashMap<String, Object>();

	    filtersMap.put("REPORT_ID", generateReportReq.getReportDetail().getReportId());
	    filtersMap.put("PROG_REF", generateReportReq.getReportDetail().getProgRef());
	    filtersMap.put("APP_NAME", generateReportReq.getReportDetail().getReportApplicationName());
	    filtersMap.put("OLD_REPORT_ID", generateReportReq.getReportDetail().getOldReportId());

	    String[] searchCols = { "REPORT_ID", "REPORT_NAME", "PROG_REF", "APP_NAME", "OLD_REPORT_ID",
		    "eodBatchMasterVO.BATCH_CODE", "eodBatchMasterVO.BATCH_BRIEF_NAME" };
	    Map<String, Object> paramMap = ServiceUtil.fillFilterMap(searchCols, filtersMap);

	    GridBaseAction baseAction = new GridBaseAction();
	    HashMap<String, Integer> addionalParams = new HashMap<>();
	    // we send the flag isSybase because in this case we do not have an
	    // instance of baseServices
	    addionalParams.put("isSybase", commonLibBO.returnIsSybase());

	    // function to validate operator and return search query
	    criteria.setSearchCols(searchCols);
	    criteria.setIS_DEPENDENCY(true);
//	    if(RepConstantsCommon.UPLOAD_DOWNLOAD_PROG_REF.equals())
	    {
	    criteria.setFILTER_DESIGNER_REP("1");
	    }
	    baseAction.copysearchgridproperties(criteria, paramMap, addionalParams);

	    // call the main method to retrieve data
	    List<IRP_AD_HOC_REPORTCO> gridModel = designerBO.getReportsList(criteria);

	    if(gridModel == null || gridModel.isEmpty() || gridModel.size() > 1)
	    {
		throw new BOException(MessageCodes.NO_RECORD_FOUND);
	    }
	    else
	    {
		//hold the first ( and only row) that should be returned
		IRP_AD_HOC_REPORTCO recordCO = gridModel.get(0);
		generateReportReq.getReportDetail().setReportId(recordCO.getREPORT_ID());
		generateReportReq.getReportDetail().setProgRef(recordCO.getPROG_REF());
		generateReportReq.getReportDetail().setReportApplicationName(recordCO.getAPP_NAME());
	    }
	}
    }
    

    private SYS_PARAM_LANGUAGESVO returnParamLanguage(String language) throws BaseException
    {
	SYS_PARAM_LANGUAGESVO sc = new SYS_PARAM_LANGUAGESVO();
	sc.setISO_CODE(language.toLowerCase());
	return commonLibBO.returnDirection(sc);
    }

    private CURRENCIESVO returnCurrency(BigDecimal compCode) throws BaseException
    {
	RIFCRTVO rifCrtVO = new RIFCRTVO();
	rifCrtVO.setCOMP_CODE(compCode);
	rifCrtVO = commonLibBO.returnRIFCRT(rifCrtVO);
	CURRENCIESVO expCurVO = null;
	if(rifCrtVO != null && rifCrtVO.getCURRENCY_CODE() != null)
	{
	    BigDecimal exposureCurrCode = rifCrtVO.getCURRENCY_CODE();
	    CURRENCIESVOKey curVOKey = new CURRENCIESVOKey();
	    curVOKey.setCOMP_CODE(compCode);
	    curVOKey.setCURRENCY_CODE(exposureCurrCode);
	    expCurVO = commonLibBO.returnCurrency(curVOKey);
	}
	return expCurVO;
    }
    
    private BRANCHESVO returnBranch(BigDecimal companyCode, BigDecimal branchCode) throws BaseException
    {
	BRANCHESVOKey branchKey = new BRANCHESVOKey();
	branchKey.setBRANCH_CODE(branchCode);
	branchKey.setCOMP_CODE(companyCode);
	BRANCHESVO branchesVO = commonLibBO.returnBranch(branchKey);
	// TP 450108 check if Valid Branch Code provided
	if(branchesVO == null)
	{
	    throw new BOException(MessageCodes.INVALID_BRANCH_CODE);
	}
	return branchesVO;
    }
    
    
    private void validateReportFormat(GenerateReportReq generateReportReq) throws BaseException
    {
	if(generateReportReq.getReportDetail().getFormat() != null)
	{

	    SelectSC selSC = new SelectSC(ConstantsCommon.FILE_FORMAT_LOV_TYPE, ConstantsCommon.LANGUAGE_ENGLISH);
	    selSC.setLovCodesInlude("'" + generateReportReq.getReportDetail().getFormat() + "'");
	    List<SelectCO> lst = commonLibBO.returnLOV(selSC);
	    if(lst == null || lst.isEmpty())
	    {
		throw new BOException(MessageCodes.INVALID_MISSING_REPORT_FORMAT, new String[] { "format" });
	    }
	}
    }
    
    /**
     * this method fills the session variables as if we are logging in from web application
     */
    private HashMap<String, Object> loadSessionVariables(GenerateReportReq generateReportReq) throws BaseException
    {
	String userId = generateReportReq.getRequesterContext().getUserID();
	BigDecimal companyCode = generateReportReq.getCompanyCode();
	BigDecimal branchCode = generateReportReq.getBranchCode();
	String currentAppName = generateReportReq.getApplicationName();//TODO
	Date runningDate = null;

	if(NumberUtil.isEmptyDecimal(companyCode))
	{
	    //the company is missing
	    throw new BOException( MessageCodes.INVALID_MISSING_COMPANY_CODE);
	}
	COMPANIESVO compVO = new COMPANIESVO();
	compVO.setCOMP_CODE(companyCode);
	compVO = commonLibBO.returnCompany(compVO);
	if(compVO == null)
	{
	    throw new BOException(MessageCodes.INVALID_COMPANY_CODE);
	}

	if(NumberUtil.isEmptyDecimal(compVO.getBASE_CURRENCY()))
	{
	    //the base currency is missing
	    throw new BOException( MessageCodes.INVALID_MISSING_CURRENCY);
	}
	CURRENCIESVO currVO = new CURRENCIESVO();
	currVO.setCOMP_CODE(companyCode);
	currVO.setCURRENCY_CODE(compVO.getBASE_CURRENCY());
	currVO = commonLibBO.returnCurrency(currVO);
	if(currVO == null)
	{
	    throw new BOException(MessageCodes.INVALID_MISSING_CURRENCY);
	}
	
	//running date is read same as the application running date
	runningDate = repServiceUtilBO.returnRunningDate(companyCode, branchCode, currentAppName);
	if(runningDate == null)
	{
	    throw new BOException(MessageCodes.CHECK_WHETHER_RUNNING_DATE_IS_DEFINED);
	}

	S_APPVO appVO = new S_APPVO();
	appVO.setAPP_NAME(currentAppName);//
	appVO = super.commonLibBO.returnApplication(appVO);
	String appType = ConstantsCommon.CUSTOMER_TYPE_A;
	if(appVO != null)
	{
	    appType = StringUtil.nullEmptyToValue(appVO.getTYPE(), appType);
	}
	String clientType = appType;
	
	BigDecimal exposureCurrCode = BigDecimal.ZERO;
	String exposureCurName = null;
	if(!ConstantsCommon.IBIS_APP_NAME.equals(currentAppName))
	{
	    // throws exception if invalid exposure currency
	    CURRENCIESVO exposureCurrVo = returnCurrency(companyCode);
	    if(exposureCurrVo != null)
	    {
		exposureCurrCode = exposureCurrVo.getCURRENCY_CODE();
		exposureCurName = exposureCurrVo.getBRIEF_DESC_ENG();
	    }
	}

	BigDecimal fiscalYear = null;
	// fiscal year not needed for FATCA, SADS module
	if(!ConstantsCommon.ITR_APP_NAME.equals(currentAppName) && !ConstantsCommon.SADS_APP_NAME.equals(currentAppName)
		&& !ConstantsCommon.UPG_APP_NAME.equals(currentAppName)
		&& !ConstantsCommon.OADM_APP_NAME.equals(currentAppName))
	{
	    // check if Fiscal Year Defined
	    BigDecimal[] fiscalMonthYear = repServiceUtilBO.returnFiscalYearMonth(companyCode, branchCode, runningDate);
	    if(fiscalMonthYear != null)
	    {
		// setting fiscal year and month into a session
		fiscalYear = fiscalMonthYear[0];
	    }
	}


	BRANCHESVO branchesVO = returnBranch(companyCode, branchCode);
	SYS_PARAM_LANGUAGESVO sc = returnParamLanguage(generateReportReq.getRequesterContext().getLangId());
	if(sc == null)
	{
	    throw new BOException("System language is not defined");
	}
	String rtlDir = StringUtil.nullEmptyToValue(sc.getIS_RIGHT_LEFT_YN(), "0");
	Integer isRtl = Integer.valueOf(rtlDir);
	
	String branchName = branchesVO.getBRIEF_DESC_ENG();
	if(isRtl == 1)
	{
	    branchName = StringUtil.nullEmptyToValue(branchesVO.getBRIEF_DESC_ARAB(), branchName);
	}
	
	UsrCO uinfo = loginBO.userLoginDet(generateReportReq.getRequesterContext().getUserID());
	String showPrintPreview = StringUtil.nullEmptyToValue(uinfo.getSHOW_PRINT_PREVIEW_YN(), ConstantsCommon.NO).trim();

	HashMap<String, Object> hm = new HashMap<>();
	hm.put("companyCode", companyCode);
	hm.put("branchCode", branchCode);
	hm.put("baseCurrDecPoint", currVO.getDECIMAL_POINTS());
	hm.put("baseCurrencyCode", compVO.getBASE_CURRENCY());
	hm.put("baseCurrencyName", currVO.getBRIEF_DESC_ENG());
	hm.put("clientType", clientType);
	hm.put("companyArabName", compVO.getBRIEF_DESC_ARAB());
	hm.put("companyName", compVO.getBRIEF_DESC_ENG());
	hm.put("currentAppName", currentAppName);
	hm.put("exposCurCode", exposureCurrCode);
	hm.put("exposCurName", exposureCurName);
	hm.put("fiscalYear", fiscalYear);
	hm.put("runningDateRET", runningDate);
	hm.put("language", generateReportReq.getReportDetail().getLanguage());
	hm.put("userName", userId);
	hm.put("branchName", branchName);
	hm.put("isRTLDir", isRtl);
	//TP317013 setting the flag for show print preview dialog upon report printing
	hm.put("showPrintPreview", showPrintPreview);
	hm.put("retrieveCall", "true");
	
	return hm;
    }

    
    
    /**
     * ReportAction.retSessionVal()
     * @param sessionParamName
     * @param sessionCO
     * @param argSource
     * @param l
     * @return
     */
    private Object retSessionVal(String sessionParamName, SessionCO sessionCO, BigDecimal argSource, String l)
    {
	Object paramSessionVal = "";
	CURRENCIESVOKey currVOKey;
	CURRENCIESVO curVO = null;
	BRANCHESVOKey brVOKey;
	BRANCHESVO brVO = null;
	try
	{
	    if((argSource.equals(new BigDecimal(2)) && ConstantsCommon.LANGUAGE_ARABIC.equals(sessionCO.getLanguage()))
		    || (argSource.equals(new BigDecimal(8)) /*&& l != null*/))
	    {
		if(ConstantsCommon.COMP_NAME_EXP_VAR.equals(sessionParamName))
		{
		    if(argSource.equals(new BigDecimal(8)) && ConstantsCommon.LANGUAGE_ENGLISH.equals(l))
		    {
			paramSessionVal = PathPropertyUtil.returnProperty(sessionCO, ConstantsCommon.COMP_NAME_EXP_VAR);
		    }
		    else
		    {
			paramSessionVal = PathPropertyUtil.returnProperty(sessionCO,
				ConstantsCommon.COMP_AR_NAME_SESSION);
		    }
		}
		else if(ConstantsCommon.BASE_CURR_NAME_EXP_VAR.equals(sessionParamName))
		{
		    currVOKey = new CURRENCIESVOKey();
		    currVOKey.setCOMP_CODE(sessionCO.getCompanyCode());
		    currVOKey.setCURRENCY_CODE(sessionCO.getBaseCurrencyCode());
		    curVO = super.commonLibBO.returnCurrency(currVOKey);
		    if(argSource.equals(new BigDecimal(8)) && ConstantsCommon.LANGUAGE_ENGLISH.equals(l))
		    {
			paramSessionVal = curVO.getBRIEF_DESC_ENG();
		    }
		    else
		    {
			paramSessionVal = curVO.getBRIEF_DESC_ARAB();
		    }
		}
		else if(ConstantsCommon.BRANCH_NAME_EXP_VAR.equals(sessionParamName))
		{
		    brVOKey = new BRANCHESVOKey();
		    brVOKey.setCOMP_CODE(sessionCO.getCompanyCode());
		    brVOKey.setBRANCH_CODE(sessionCO.getBranchCode());
		    brVO = super.commonLibBO.returnBranch(brVOKey);
		    if(argSource.equals(new BigDecimal(8)) && ConstantsCommon.LANGUAGE_ENGLISH.equals(l))
		    {
			paramSessionVal = brVO.getBRIEF_DESC_ENG();
		    }
		    else
		    {
			paramSessionVal = brVO.getBRIEF_DESC_ARAB();
		    }
		}
		else
		{
		    paramSessionVal = PathPropertyUtil.returnProperty(sessionCO, sessionParamName);
		}
	    }
	    else
	    {
		paramSessionVal = PathPropertyUtil.returnProperty(sessionCO, sessionParamName);
	    }

	}
	catch(Exception e)
	{
	    log.error(e, " error in reportAction.retSessionVal");
	}
	return paramSessionVal;
    }
    
    
    /**
     * 
     * @param sessionHm
     * @param generateReportResp
     * @throws Exception 
     */
    private void returnReportBytes(HashMap<String, Object> sessionHm, GenerateReportResp generateReportResp) throws Exception
    {
	try
	{
	    HashMap<String, Object> repParameters = new HashMap<String, Object>();
	    HashMap<String, Object> sessionMap = new HashMap<String, Object>();
	    HashMap<String, Object> reportParametersHm = (HashMap<String, Object>) sessionHm.get("reportParametersHm");
	    HashMap<String, Object> repDetailsMap = new HashMap<String, Object>();
	    boolean isReturnOutput = true;
	    
	    SessionCO sessionCO = (SessionCO) PathPropertyUtil.convertToObject(sessionHm, SessionCO.class);
	    
	    ReportParamsCO repParamsCO = new ReportParamsCO();

	    String[] properties = new String[] { "baseCurrDecPoint", "baseCurrencyCode", "baseCurrencyName",
		    "branchCode", "clientType", "companyArabName", "companyCode", "companyName", "currentAppName",
		    "exposCurCode", "exposCurName", "fiscalYear", "runningDateRET", "language", "userName",
		    "branchName", "isRTLDir", "employeeId", "userNbFormats" };

	    PathPropertyUtil.copyProperties(sessionCO, repParamsCO, properties);

	    BigDecimal dbCon = NumberUtil.nullToZero( generateReportResp.getReportDetail().getDatabaseConn());
	    repDetailsMap.put(ConstantsCommon.MENU_ID_PARAM, generateReportResp.getReportDetail().getProgRef());
	    repDetailsMap.put(ConstantsCommon.APP_NAME_PARAM, generateReportResp.getApplicationName());
	    repDetailsMap.put(ConstantsCommon.LANG_PARAM, generateReportResp.getRequesterContext().getLangId());
	    repDetailsMap.put(ConstantsCommon.DB_PARAM, dbCon);
	    repDetailsMap.put(ConstantsCommon.HEAD_FOOT_PARAM, false);
	    
	    
	    //fill "repParamsCO"
	    repParameters.put(RepConstantsCommon.repParamsCO_arg, repParamsCO);
	    
	    String reportName = generateReportResp.getReportDetail().getReportName();
	    if(StringUtil.isEmptyString(reportName))
	    {
		reportName = generateReportResp.getReportDetail().getProgRef().concat("_")
			.concat(generateReportResp.getApplicationName().concat("_")
				.concat(generateReportResp.getRequesterContext().getUserID()).replace(".", ""));
		//default the value in case it is not sent
		generateReportResp.getReportDetail().setReportName(reportName);
	    }
	    repDetailsMap.put(ConstantsCommon.REPORT_NAME_PARAM, reportName);
	    repDetailsMap.put(ConstantsCommon.FORMAT_PARAM, generateReportResp.getReportDetail().getFormat());
	    
	    BigDecimal decimalPts = BigDecimal.ZERO;
	    if(ConstantsCommon.REP_APP_NAME.equals(generateReportResp.getApplicationName()))
	    {
		decimalPts = (BigDecimal) sessionHm.get("baseCurrDecPoint");
	    }
	    repDetailsMap.put(RepConstantsCommon.REP_PARAM_DECIMAL_POINT, decimalPts);
	    repDetailsMap.put(ConstantsCommon.PEP_PRINTER_NAME, generateReportResp.getReportDetail().getPrinterName());
	    
	    //add the allocated parameters of the report to the hashmap
	    repParameters.putAll(reportParametersHm);
	    
	    sessionMap = PathPropertyUtil.convertToMap(sessionCO);

	    HashMap<String, Object> map = commonReportingBO.returnReportBytes(repParameters, sessionMap, repDetailsMap, isReturnOutput);
	    byte[] reportBytes = (byte[]) map.get("reportBytes");
	    String reportBytesEncoded = SecurityUtils.encodeB64(reportBytes);
	    generateReportResp.setReport(reportBytesEncoded);
	    generateReportResp.setReportSize(BigDecimal.valueOf(reportBytes.length));
	    
	}
	catch(Exception ex)
	{
	    //ReportException
	    if(ex instanceof ReportException)
	    {
		ReportException reportException = (ReportException) ex;
//		    BaseException baseException = new BaseException(reportException, reportException.getErrorCode(), reportException.getParams(), true);
		BaseException baseException = new BaseException(reportException.getMessage(), ex);
		baseException.setErrorCode(reportException.getErrorCode());
		throw baseException;
	    }
	    else
	    {
		// BaseException, BOException
		throw ex;
	    }
	    
	}
    }

}
