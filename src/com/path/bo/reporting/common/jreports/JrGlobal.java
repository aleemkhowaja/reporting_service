package com.path.bo.reporting.common.jreports;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.BeansException;

import com.path.bo.common.CommonLibBO;
import com.path.bo.common.CommonMethods;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.ReportsCommonBO;
import com.path.bo.reporting.common.CashedConstantsCommonRep;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dbmaps.vo.BRANCHESVO;
import com.path.dbmaps.vo.BRANCHESVOKey;
import com.path.dbmaps.vo.COMPANIESVO;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.dbmaps.vo.IRP_SESSION_ATTRIBUTESVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.ApplicationContextProvider;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.log.Log;
import com.path.struts2.lib.common.PathLocalizedTextUtil;
import com.path.vo.reporting.ReportParamsCO;

public final class JrGlobal
{
    private static Log log = Log.getInstance();
    private JrGlobal() 
    {
      log.debug("This Class Should not be Instantiated");
    }


    /**
     * return the logged in user ID
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_getuserid(ReportParamsCO paramsCO) throws BaseException
    {
	return paramsCO.getUserName();
    }

    /**
     * return the application long description based on the
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_getapplication(ReportParamsCO paramsCO) throws BaseException
    {
	ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext().getBean(
		"reportsCommonBO");
	return repotsCommon.returnF_GetApplication(paramsCO.getCurrentAppName(), paramsCO.getRepLanguage(),paramsCO.getConnCO());
    }

    /**
     * return the report reference
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_ref(ReportParamsCO paramsCO) throws BaseException
    {
	return paramsCO.getProgRef();
    }

    /**
     * return the system running date
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static Date f_get_system_date(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    return paramsCO.getRunningDateRET();
	}
    }

    /**
     * return the company name
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_company_desc(ReportParamsCO paramsCO) throws BaseException
    {
    	if(NumberUtil.isEmptyDecimal(paramsCO.getCompanyCode()))
    	{
    		return "";
    	}
    	else
    	{
    		CommonLibBO commonLibBO = (CommonLibBO) ApplicationContextProvider.getApplicationContext().getBean(
    				"commonLibBO");
    			COMPANIESVO compVO = new COMPANIESVO();
    			compVO.setCOMP_CODE(paramsCO.getCompanyCode());
    			COMPANIESVO retCompVO = commonLibBO.returnCompany(compVO);
    			if(ConstantsCommon.LANGUAGE_ARABIC.equals(paramsCO.getRepLanguage()))
    			{
    			    return retCompVO.getLONG_DESC_ARAB();
    			}
    			else
    			{
    			    return retCompVO.getLONG_DESC_ENG();
    			}
    	}
    }

    /**
     * return the value of the global variable name passed as parameter like
     * currency and company name...
     * 
     * @param field
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_getgv(String field, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    if("BASE_CY_NAME_ARAB".equals(field))
	    {
		ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
			.getBean("reportsCommonBO");
		theVarResult = repotsCommon.returnCurrencyArabName(paramsCO.getCompanyCode(), paramsCO
			.getBaseCurrencyCode(),paramsCO.getConnCO());
	    }
	    else
	    {
		theVarResult = JrReportsCommonMethods.getGlobalVar(field, paramsCO);
	    }
	    return theVarResult;
	}
    }

    /**
     * return the logged in branch code
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_branch(ReportParamsCO paramsCO) throws BaseException // modified
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    return paramsCO.getBranchCode();
	}
    }

    /**
     * return the logged in branch name
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_branch_desc(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
		if(NumberUtil.isEmptyDecimal(paramsCO.getCompanyCode()) || NumberUtil.isEmptyDecimal(paramsCO.getBranchCode()))
		{
			return "";
		}
		else
		{
	    // return the branch desc following the report language
	    CommonLibBO commonLibBO = (CommonLibBO) ApplicationContextProvider.getApplicationContext().getBean(
		    "commonLibBO");
	    BRANCHESVOKey branchKey = new BRANCHESVOKey();
	    branchKey.setCOMP_CODE(paramsCO.getCompanyCode());
	    branchKey.setBRANCH_CODE(paramsCO.getBranchCode());
	    BRANCHESVO branchVO = commonLibBO.returnBranch(branchKey);
	    if(ConstantsCommon.LANGUAGE_ARABIC.equals(paramsCO.getRepLanguage()))
	    {
		if(branchVO.getBRIEF_DESC_ARAB() == null)
		{
		    return "";
		}
		else
		{
		    return branchVO.getBRIEF_DESC_ARAB();
		}
	    }
	    else
	    {
		return branchVO.getBRIEF_DESC_ENG();
	    }
		}
	}
    }
    /**
     * Method that return whether provided language is rTL or not
     * @param paramsCO report paramCO
     * @return 1 if RTL , 0 otherwise
     * @throws BaseException
     */
    public static String f_get_rtl(ReportParamsCO paramsCO) throws BaseException
    {
	ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext().getBean(
		"reportsCommonBO");
	return reportsCommon.returnF_Get_Rtl(paramsCO.getRepLanguage(),paramsCO.getConnCO());
    }

    /**
     * return (0 - if both Arabic and English are mandatory,1 - if only English)
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_language(ReportParamsCO paramsCO) throws BaseException
    {
	ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext().getBean(
		"reportsCommonBO");
	String result = reportsCommon.returnF_Get_Language(paramsCO.getConnCO());
	if(result == null)
	{
	    return BigDecimal.ZERO;  
	}
	else
	{
	    if("2".equals(result) || "0".equals(result))
	    {
		return BigDecimal.ONE;
	    }
	    return BigDecimal.ZERO;
	}
    }

    /**
     * return the text translated based on the logged in language
     * 
     * @param text
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_translate_string(String text, ReportParamsCO paramsCO) throws BaseException
    {
	// To do you need to pass to the application, program reference, and
	// textkey, and language

	// TODO Patricia once translation tables are REdi
	String transText;
	ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext().getBean(
		"reportsCommonBO");
	transText = repotsCommon.returnKeyLabelTrans(paramsCO.getRepLanguage(), paramsCO.getRepAppName(), paramsCO
		.getProgRef(), text,paramsCO.getConnCO());
	return transText;
    }
    
    /**
     * return the text translated based on the provided language
     * 
     * @param text
     * @param paramsCO
     * @param language explicit language that overrides the report language (EN,AR,....)
     * @return
     * @throws BaseException
     */
    public static String f_translate_string(String text, String language, ReportParamsCO paramsCO) throws BaseException
    {
	String transText;
	ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		.getBean("reportsCommonBO");
	transText = repotsCommon.returnKeyLabelTrans(StringUtil.nullEmptyToValue(language, "EN"),
		paramsCO.getRepAppName(), paramsCO.getProgRef(), text, paramsCO.getConnCO());
	return transText;
    }
    
    /**
     * return the format string based on the passed number of decimal points.
     * The format is not specified for negative numbers
     * 
     * @param cyDecimal
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask(BigDecimal cyDecimal, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String lsFormat = "#,##0.";
	    if(cyDecimal == null || NumberUtil.isNumberNegative(cyDecimal) || cyDecimal.intValue() == 0)
	    {
		lsFormat = "#,##0";
	    }

	    lsFormat = lsFormat + CommonMethods.fill("0", cyDecimal) + " " + ";(" + lsFormat
		    + CommonMethods.fill("0", cyDecimal) + ")";

	    return lsFormat;
	}
    }

    /*Added By NOjeil & LBedrane issue appear when retrieving reports that called f_currency_mask  US = 751197 <Start> */
    public static String f_currency_mask_1(BigDecimal cyDecimal, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    return NumberUtil.currencyMask(cyDecimal);
	}
    }
    /*Added By NOjeil & LBedrane issue appear when retrieving reports that called f_currency_mask  US = 751197 <Start> */
    
    /**
     * return the currencies decimal points of the base currency
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_currency_dec(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    return paramsCO.getBaseCurrDecPoint();
	}
    }

    /**
     * return the country amount (balance) for use with the availment report
     * 
     * @param cifNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_country_amt(BigDecimal cifNo, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    // TODO Patricia
	    return reportsCommon.returnF_GET_COUNTRY_AMT(cifNo, paramsCO.getCompanyCode(), paramsCO
		    .getBaseCurrencyCode(),paramsCO.getConnCO());
	}
    }

    /**
     * Returns the format string based on the passed decimal points, and the
     * negative numbers are formatted between parenthesis
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_def(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	   String format = f_currency_mask_1(paramsCO.getBaseCurrDecPoint(), paramsCO);//Updated by NOjeil & LBedrane - US = 751197
	    StringBuffer  sb=new StringBuffer(format);
	    sb.append(";(").append(format).append(")");
	    return sb.toString();
	}
    }

    /**
     * return the summation of the YTD_CV_BAL of all the accounts defined for
     * the CIF No.
     * 
     * @param cifNo
     * @param obligorLimit
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_oneobligor(BigDecimal cifNo, BigDecimal obligorLimit, ReportParamsCO paramsCO)
	    throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    // TODO Patricia, might additional parameters needed to be contacted
	    // with NAthalie and Code Checking
	    return reportsCommon.returnF_GET_ONE_OBLIGOR(cifNo, obligorLimit, paramsCO.getBaseCurrencyCode(), paramsCO
		    .getCompanyCode(), paramsCO.getExposCurCode(), paramsCO.getRunningDateRET(),paramsCO.getConnCO());
	}
    }

    /**
     * return the date converted to GREGORIAN or HIJRI based on the value of the
     * passed parameter type G or H
     * 
     * @param dateToConv
     * @param type
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static Date f_convert_date_grego_hijiri(Date dateToConv, String type, ReportParamsCO paramsCO)
	    throws BaseException
    {
	// get the first character from the type since this is the only needed
	// in conversion to hijri
	String ls_type;
	ls_type = type.substring(0, 1);
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{

	    try
	    {
		ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
			.getBean("reportsCommonBO");
		// TODO Patricia, check Old CSM migration Code might already
		// available
		return reportsCommon.returnF_CONVERT_DATE_GREGO_HIJIRI(dateToConv, ls_type, paramsCO.getCompanyCode(),paramsCO.getConnCO());

	    }
	    catch(BaseException e)
	    {
		Log.getInstance().error(e, "");
		return paramsCO.getRunningDateRET();
	    }
	    catch(Exception e)
	    {
		Log.getInstance().error(e, "");
		return paramsCO.getRunningDateRET();
	    }

	}

    }

    /**
     * return the format based on the passed decimal points. Negative numbers
     * are displayed without the negative sign
     * 
     * @param cyDec
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_nosign(BigDecimal cyDec, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String format = f_currency_mask_1(cyDec, paramsCO);//Updated by NOjeil & LBedrane - US = 751197
	    StringBuffer  sb=new StringBuffer(format);
	    sb.append(";").append(format).append("");
	    return sb.toString();
	}
    }

    /**
     * return the decimal points of the currency code
     * 
     * @param cyCode
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_dec_point(BigDecimal cyCode, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData() || cyCode == null)
	{
	    return null;
	}
	else
	{
	    BigDecimal decPoint = BigDecimal.ZERO;
	    try
	    {
		ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
			.getBean("reportsCommonBO");
		decPoint = reportsCommon.returnF_GET_DEC_POINT(cyCode, paramsCO.getCompanyCode(),paramsCO.getConnCO());
	    }
	    catch(BeansException e)
	    {
		Log.getInstance().error(e, "Error in f_get_dec_point PBClobal");
	    }
	    return decPoint;
	}

    }

    /**
     * return the short over amount based on the passed currency and teller code
     * 
     * @param cyCode
     * @param tellerCode
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_short_over(BigDecimal cyCode, BigDecimal tellerCode, ReportParamsCO paramsCO)
	    throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    // TODO Patricia, check if the method already available in CSM with
	    // teh
	    // team otherwise need to code
	    if(NumberUtil.isEmptyDecimal(tellerCode))// this test added by
	    // h.khoury to prevent
	    // error in rep generation
	    // when qry of rep return
	    // no data
	    {
		return null;
	    }
	    else
	    {
		return reportsCommon.returnF_GET_SHORT_OVER(cyCode, paramsCO.getCompanyCode(),
			paramsCO.getBranchCode(), paramsCO.getRunningDateRET(), tellerCode,paramsCO.getConnCO());
	    }
	}
    }

    /**
     * Checks if the account is Unclaimed
     * 
     * @param compCode
     * @param branchCode
     * @param cyCode
     * @param glCode
     * @param cifNo
     * @param slNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_check_unclaimed(BigDecimal compCode, BigDecimal branchCode, BigDecimal cyCode,
	    BigDecimal glCode, BigDecimal cifNo, BigDecimal slNo, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    // TODO Patricia, check if the method already available in CSM with
	    // the
	    // team otherwise need to code, contact Nathalie
	    return reportsCommon.returnF_CHECK_UNCLAIMED(compCode, branchCode, cyCode, glCode, cifNo, slNo, paramsCO
		    .getRepLanguage(),paramsCO.getConnCO());
	}
    }

    /**
     * return true if the language is Arabic
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static Boolean f_is_arabic(ReportParamsCO paramsCO) throws BaseException
    {
	if(ConstantsCommon.LANGUAGE_ARABIC.equals(paramsCO.getRepLanguage()))
	{
	    return true;
	}
	else
	{
	    return false;
	}
    }

    /**
     * return the base currency info details based on the passed string argument
     * value
     * 
     * @param field
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_base_cy_details(String field, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return reportsCommon.returnF_CY_DETAILS(field, paramsCO.getBaseCurrencyCode(), paramsCO.getCompanyCode(),paramsCO.getConnCO());
	}
    }

    /**
     * return the format string based on the decimal points of the base currency
     * including debit and credit or their translation in Arabic
     * 
     * @param cyDec
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_dr_cr_basecy(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String ls_Format = "#,##0.";
	    StringBuffer  formatSB = new StringBuffer();
	    BigDecimal gy_cy_dec = paramsCO.getBaseCurrDecPoint();
	    
	    if(ConstantsCommon.LANGUAGE_ENGLISH.equals(paramsCO.getRepLanguage().toString()))
	    {
		formatSB = formatSB.append(ls_Format).append(CommonMethods.fill("0", gy_cy_dec)).append(" '").append(f_translate_string("DR", paramsCO)).append("';")
			       .append(ls_Format).append(CommonMethods.fill("0", gy_cy_dec)).append(" '").append(f_translate_string("CR", paramsCO)).append("';")
			       .append(ls_Format).append(CommonMethods.fill("0", gy_cy_dec)).append(" '     '");
	    }
	    else
	    {
		formatSB = formatSB.append(ls_Format).append(CommonMethods.fill("0", gy_cy_dec)).append(" '").append(f_translate_string("DR", paramsCO)).append("';")
			       .append(ls_Format).append(CommonMethods.fill("0", gy_cy_dec)).append(" '").append(f_translate_string("CR", paramsCO)).append("';")
			       .append(ls_Format).append(CommonMethods.fill("0", gy_cy_dec)).append(" '     '");
	    }
	    
	    return formatSB.toString();
	}
    }

    /**
     * return the format string based on the decimal points including Debit and
     * Credit
     * 
     * @param cyDec
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_dr_cr(BigDecimal cyDec, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    if(NumberUtil.isEmptyDecimal(cyDec))
	    {
		cyDec = new BigDecimal(0);
	    }
	    String ls_Format = "#,##0.";
	    
	    StringBuffer  formatSB = new StringBuffer();
	    
	    formatSB = formatSB.append(ls_Format).append(CommonMethods.fill("0", cyDec)).append(" '").append(f_translate_string("DR", paramsCO)).append("';")
		       .append(ls_Format).append(CommonMethods.fill("0", cyDec)).append(" '").append(f_translate_string("CR", paramsCO)).append("';")
		       .append(ls_Format).append(CommonMethods.fill("0", cyDec)).append(" '   '");
	    
	    return formatSB.toString();
	}
    }

    /**
     * return the company name English or Arabic based on the language
     * 
     * @param language
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_company_name(String language, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    if(ConstantsCommon.LANGUAGE_ARABIC.equals(language))
	    {
		return paramsCO.getCompanyArabName();
	    }
	    else
	    {
		return paramsCO.getCompanyName();
	    }
	}
    }
    
    public static String f_get_company_name(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    if(ConstantsCommon.LANGUAGE_ARABIC.equals(paramsCO.getRepLanguage().toString()))
	    {
		return paramsCO.getCompanyArabName();
	    }
	    else
	    {
		return paramsCO.getCompanyName();
	    }
	}
    }

    /**
     * return the company name English or Arabic based on the logged in language
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_company_name_language(ReportParamsCO paramsCO) throws BaseException
    {
	if(ConstantsCommon.LANGUAGE_ARABIC.equals(paramsCO.getRepLanguage()))
	{
	    return paramsCO.getCompanyArabName();
	}
	else
	{
	    return paramsCO.getCompanyName();
	}
    }

    /**
     * Get the decimal points of the passed currency from the currencies table
     * and construct the format based on it. Negative numbers are displayed
     * without the sign.
     * 
     * @param cyCode
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_select(BigDecimal cyCode, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    BigDecimal decPoint = f_get_dec_point(cyCode, paramsCO);
	    return f_currency_mask_nosign(decPoint, paramsCO);
	}
    }

    /**
     * return the format to be used for cheque numbers based on
     * CTSCONTROL.CHEQUE_DIGITS_NUM. The default is 10
     * 
     * @param compCode
     * @param branchCode
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_format_cheque(BigDecimal compCode, BigDecimal branchCode, ReportParamsCO paramsCO)
	    throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    BigDecimal chequeDgtNb = reportsCommon.returnF_RETURN_CHEQUE_DIGITS(compCode, branchCode,paramsCO.getConnCO());
	    return CommonMethods.fill("0", chequeDgtNb) + ";" + CommonMethods.fill("0", chequeDgtNb);
	}
    }

    /**
     * Tifkeet of passed numbers in Arabic
     * 
     * @param numToConvert
     * @param currName
     * @param currNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_num_to_words_arab_no_only(BigDecimal numToConvert, String currName, Number currNo,
	    ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ARAB_NO_ONLY(numToConvert, currName);

	    return theVarResult;
	}
    }
    
    /**
     * 
     * @param numToConvert
     * @param currName
     * @param currNo
     * @param suffix
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_num_to_words_arab_no_only(BigDecimal numToConvert, String currName, Number currNo,
	    String suffix, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ARAB_NO_ONLY(numToConvert, currName, suffix);

	    return theVarResult;
	}
    }

    /**
     * Tifkeet of passed numbers in Arabic
     * 
     * @param numToConvert
     * @param currName
     * @param currNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_num_to_words_arab_no_only1(BigDecimal numToConvert, String currName, BigDecimal currDec,
	    BigDecimal compCode, BigDecimal currNo, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ARAB_NO_ONLY(numToConvert, compCode, currNo, paramsCO
		    .getBaseCurrencyCode(),paramsCO.getConnCO());

	    return theVarResult;
	}
    }
    
    /**
     * 
     * @param numToConvert
     * @param currName
     * @param currDec
     * @param compCode
     * @param currNo
     * @param suffix
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_num_to_words_arab_no_only1(BigDecimal numToConvert, String currName, BigDecimal currDec,
	    BigDecimal compCode, BigDecimal currNo, String suffix, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ARAB_NO_ONLY(numToConvert, compCode, currNo,
		    paramsCO.getBaseCurrencyCode(), suffix, paramsCO.getConnCO());

	    return theVarResult;
	}
    }

    /**
     * return the format string based on the decimal points of the base
     * currency. Negative numbers are displayed without the sign.
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_def_rep(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    return f_currency_mask_nosign(paramsCO.getBaseCurrDecPoint(), paramsCO);
	}
    }

    /**
     * return the format based on the passed number of decimal points. Negative
     * numbers are displayed between parenthesis.
     * 
     * @param cyCode
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_cy(BigDecimal cyCode, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    BigDecimal decPoint = f_get_dec_point(cyCode, paramsCO);
	    String format = f_currency_mask_1(decPoint, paramsCO);//Updated by NOjeil & LBedrane - US = 751197
	    StringBuffer  sb=new StringBuffer(format);
	    sb.append(";(").append(format).append(")");
	    
	    return sb.toString();
	}
    }
    /**
     * return the format based on the passed number of decimal points. 
     * @param decPoint
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    	public static String f_currency_mask_sign_ftr(BigDecimal decPoint, ReportParamsCO paramsCO) throws BaseException
    	{
    	    if (paramsCO.getNoData())
    	{
    	    return "";
    	}
    	else
    	{ 
    	    return  f_currency_mask_1(decPoint,paramsCO);//Updated by NOjeil & LBedrane - US = 751197
    	}
    	
    	}
    	
        /**
         * return the format based on the passed number of decimal points. 
         * @param decPoint
         * @param paramsCO
         * @return
         * @throws BaseException
         */
    	public static String f_currency_mask_sign(BigDecimal decPoint, ReportParamsCO paramsCO) throws BaseException
    	{
    	    if (paramsCO.getNoData())
    	{
    	    return "";
    	}
    	else
    	{
    	    return f_currency_mask_1(decPoint,paramsCO);//Updated by NOjeil & LBedrane - US = 751197
    	}
    	
    	}
    /**
     * return format based on decimal points of base currency. The format
     * includes debit and credit keywords.
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_def_drcr(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    return f_currency_mask_dr_cr_basecy(paramsCO);
	}

    }

    /**
     * Tifkeet of passed numbers in English
     * 
     * @param currName
     * @param currNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_num_to_words_english(BigDecimal numToConvert, String currName, Number currNo,
	    ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ENGLISH(numToConvert, currName);

	    return theVarResult;
	}
    }
    
    /**
     * 
     * @param numToConvert
     * @param currName
     * @param currNo
     * @param suffix
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_num_to_words_english(BigDecimal numToConvert, String currName, Number currNo, String suffix,
	    ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ENGLISH(numToConvert, currName, suffix);

	    return theVarResult;
	}
    }


    /**
     * Tifkeet of passed numbers in English
     * 
     * @param currName
     * @param currDec
     * @param compCode
     * @param currNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */

    public static String f_num_to_words_english_csm(BigDecimal numToConvert, String currName, BigDecimal currDec,
	    BigDecimal compCode, BigDecimal currNo, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    if(StringUtil.nullToEmpty(paramsCO.getRepLanguage()).equals(ConstantsCommon.LANGUAGE_ARABIC))
	    {
		theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ARAB_NO_ONLY(numToConvert, compCode, currNo, paramsCO
			.getBaseCurrencyCode(),paramsCO.getConnCO());
	    }
	    else if(StringUtil.nullToEmpty(paramsCO.getRepLanguage()).equals(ConstantsCommon.LANGUAGE_FRENCH))
	    {
		theVarResult = repotsCommon.returnF_NUM_TO_WORDS_FRENCH(numToConvert, compCode, currNo, paramsCO
			.getBaseCurrencyCode(),paramsCO.getConnCO());
	    }
	    else
	    {
		theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ENGLISH(numToConvert, compCode, currNo, paramsCO
			.getBaseCurrencyCode(),paramsCO.getConnCO());
	    }
	    return theVarResult;
	}
    }
    
    /**
     * 
     * @param numToConvert
     * @param currName
     * @param currDec
     * @param compCode
     * @param currNo
     * @param suffix
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_num_to_words_english_csm(BigDecimal numToConvert, String currName, BigDecimal currDec,
	    BigDecimal compCode, BigDecimal currNo, String suffix, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    if(StringUtil.nullToEmpty(paramsCO.getRepLanguage()).equals(ConstantsCommon.LANGUAGE_ARABIC))
	    {
		theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ARAB_NO_ONLY(numToConvert, compCode, currNo,
			paramsCO.getBaseCurrencyCode(), suffix, paramsCO.getConnCO());
	    }
	    else if(StringUtil.nullToEmpty(paramsCO.getRepLanguage()).equals(ConstantsCommon.LANGUAGE_FRENCH))
	    {
		theVarResult = repotsCommon.returnF_NUM_TO_WORDS_FRENCH(numToConvert, compCode, currNo,
			paramsCO.getBaseCurrencyCode(), suffix, paramsCO.getConnCO());
	    }
	    else
	    {
		theVarResult = repotsCommon.returnF_NUM_TO_WORDS_ENGLISH(numToConvert, compCode, currNo,
			paramsCO.getBaseCurrencyCode(), suffix, paramsCO.getConnCO());
	    }
	    return theVarResult;
	}
    }

    /**
     * return the decimal points of the passed currency as parameter
     * 
     * @param currCode
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_decimal_point_bc(BigDecimal currCode, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    return f_get_dec_point(currCode, paramsCO);
	}
    }

    /**
     * return the format string based on the passed number of decimal points. No
     * format is specified for negative numbers
     * 
     * @param decPoint
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_format(BigDecimal decPoint, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    return NumberUtil.currencyMask(decPoint);
	}
    }

    /**
     * return the CIF short name Arabic or English based on the language
     * 
     * @param cifNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_cif_desc(BigDecimal cifNo, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnCifShortName(paramsCO.getCompanyCode(), paramsCO.getRepLanguage(), cifNo,paramsCO.getConnCO());

	    return theVarResult;
	}
    }

    /**
     * return the CIF Address1 Arabic or English based on the language
     * 
     * @param cifNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_cif_address1(BigDecimal cifNo, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnCifAddress1(paramsCO.getCompanyCode(), paramsCO.getRepLanguage(), cifNo,paramsCO.getConnCO());

	    return theVarResult;
	}
    }

    /**
     * return the CIF Address2 Arabic or English based on the language
     * 
     * @param cifNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_cif_address2(BigDecimal cifNo, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnCifAddress2(paramsCO.getCompanyCode(), paramsCO.getRepLanguage(), cifNo,paramsCO.getConnCO());

	    return theVarResult;
	}
    }

    /**
     * return the CIF Address3 Arabic or English based on the language
     * 
     * @param cifNo
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_cif_address3(BigDecimal cifNo, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnCifAddress3(paramsCO.getCompanyCode(), paramsCO.getRepLanguage(), cifNo,paramsCO.getConnCO());

	    return theVarResult;
	}
    }

    /**
     * return the Currency Description Arabic or English based on the language
     * 
     * @param compCode
     * @param cyCode
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_get_currency_desc(BigDecimal compCode, BigDecimal cyCode, ReportParamsCO paramsCO)
	    throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnCurrencyDesc(compCode, paramsCO.getRepLanguage(), cyCode,paramsCO.getConnCO());
	    return theVarResult;
	}
    }

    /**
     * return the base currency code
     * 
     * @return the base currency code
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_base_cy(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    return paramsCO.getBaseCurrencyCode();
	}

    }

    /**
     * return the format based the decimal points passed as parameter. Negative
     * numbers format do not include the sign
     * 
     * @param cyDec
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_rep(BigDecimal cyDec, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    return f_currency_mask_nosign(cyDec, paramsCO);
	}
    }

    /**
     * returns the difference between the limit defined at the level of CIF and
     * utilized amount of the drawdown
     * 
     * @param cifNo
     * @param decCifLimit
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_get_cif_amt(BigDecimal cifNo, BigDecimal decCifLimit, ReportParamsCO paramsCO)
	    throws BaseException
    {
	// CommonMethods.getCifAmount(new Integer(cifNo.intValue()), new
	// Double(decCifLimit.doubleValue()),
	// paramsCO);
	// TODO Patricia
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    BigDecimal theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnF_GET_CIF_AMT(cifNo, decCifLimit, paramsCO.getCompanyCode(), paramsCO
		    .getExposCurCode(), paramsCO.getBaseCurrencyCode(), paramsCO.getRunningDateRET(),paramsCO.getConnCO());
	    return theVarResult;
	}

	// return BigDecimal.ZERO;

    }

    /**
     * return 1 if the passed currency is the base currency
     * 
     * @param cyCode
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static BigDecimal f_base_cy(Number cyCode, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    if(paramsCO.getBaseCurrencyCode().equals(cyCode))
	    {
		return BigDecimal.ONE;
	    }
	    return BigDecimal.ZERO;
	}

    }

    
    /**
     * return a string which is the format of the amount
     * 
     * param amt_sign
     * param gl_type
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_gl_type_without_dec(Integer amt_sign, String gl_type, ReportParamsCO paramsCO)
	    throws BaseException
    {
	String format = "#,##0";
	StringBuffer sb= new StringBuffer();
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    if(amt_sign.intValue() == -1
		    && (RepConstantsCommon.GL_TYPE_L.equals(gl_type) || RepConstantsCommon.GL_TYPE_R.equals(gl_type) || RepConstantsCommon.GL_TYPE_S
			    .equals(gl_type)))
	    {
		sb = sb.append(format).append(" ;").append(format);
		format=sb.toString();
	    }
	    else if(amt_sign.intValue() == 1
		    && (RepConstantsCommon.GL_TYPE_L.equals(gl_type) || RepConstantsCommon.GL_TYPE_R.equals(gl_type) || RepConstantsCommon.GL_TYPE_S
			    .equals(gl_type)))
	    {
		sb = sb.append("(").append(format).append(")");
		format=sb.toString();
	    }
	    else if(amt_sign.intValue() == -1
		    && (RepConstantsCommon.GL_TYPE_A.equals(gl_type) || RepConstantsCommon.GL_TYPE_E.equals(gl_type)))
	    {
		sb = sb.append(format).append(" ;(").append(format).append(")");
		format=sb.toString();
	    }
	    else
	    {
		sb = sb.append(format).append(" ;").append(format);
		format=sb.toString();
	    }
	}
	return format;
    }
    
    /**
     * return a string which is the format of the amount
     * @param amt_sign
     * @param gl_type
     * @param ac_disp_zeros
     * @param adec_amt
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_gl_type_dash(Integer amt_sign, String gl_type, String ac_disp_zeros,
	    Number adec_amt, ReportParamsCO paramsCO) throws BaseException
    {
	BigDecimal gy_cy_dec = paramsCO.getBaseCurrDecPoint();
	String format = "#,##0.";
	if(paramsCO.getNoData())
	{
	    return "";
	}

	else
	{
	    if(gy_cy_dec.intValue() <= 0)
	    {
		format = "#,##0";
	    }
	    if(amt_sign.intValue() == -1 && (RepConstantsCommon.GL_TYPE_L.equals(gl_type)
		    || RepConstantsCommon.GL_TYPE_R.equals(gl_type) || RepConstantsCommon.GL_TYPE_S.equals(gl_type)))
	    {
		format = format + " ;" + format;
	    }
	    else if(amt_sign.intValue() == 1 && (RepConstantsCommon.GL_TYPE_L.equals(gl_type)
		    || RepConstantsCommon.GL_TYPE_R.equals(gl_type) || RepConstantsCommon.GL_TYPE_S.equals(gl_type)))
	    {
		format = "(" + format + ")";
	    }
	    else if(amt_sign.intValue() == -1
		    && (RepConstantsCommon.GL_TYPE_A.equals(gl_type) || RepConstantsCommon.GL_TYPE_E.equals(gl_type)))
	    {
		format = format + " ;(" + format + ")";
	    }
	    else if("N".equals(ac_disp_zeros) && (adec_amt == null || adec_amt.doubleValue() == 0))
	    {
		format = "-  ";
	    }
	    else
	    {
		format = format + " ;" + format;
	    }
	}
	return format;
    }

    
    /**
     * return a string which is the format of the amount
     * 
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_gl_type(Integer amt_sign, String gl_type, ReportParamsCO paramsCO)
	    throws BaseException
    {
	BigDecimal gy_cy_dec = paramsCO.getBaseCurrDecPoint();
	String format = "#,##0.";
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    if(gy_cy_dec.intValue() <= 0)
	    {
		format = "#,##0";
	    }
	    if(amt_sign.intValue() == -1 && (RepConstantsCommon.GL_TYPE_L.equals(gl_type) || RepConstantsCommon.GL_TYPE_R.equals(gl_type) || RepConstantsCommon.GL_TYPE_S.equals(gl_type)))
	    {
		return format = format + CommonMethods.fill("0", gy_cy_dec) + " ;" + format
			+ CommonMethods.fill("0", gy_cy_dec);
	    }
	    else if(amt_sign.intValue() == 1 && (RepConstantsCommon.GL_TYPE_L.equals(gl_type) || RepConstantsCommon.GL_TYPE_R.equals(gl_type) || RepConstantsCommon.GL_TYPE_S.equals(gl_type)))
	    {
		return format = "(" + format + CommonMethods.fill("0", gy_cy_dec) + ")";
	    }
	    else if(amt_sign.intValue() == -1 && (RepConstantsCommon.GL_TYPE_A.equals(gl_type) || RepConstantsCommon.GL_TYPE_E.equals(gl_type)))
	    {
		return format = format + CommonMethods.fill("0", gy_cy_dec) + " ;(" + format
			+ CommonMethods.fill("0", gy_cy_dec) + ")";
	    }
	    else
	    {
		return format = format + CommonMethods.fill("0", gy_cy_dec) + " ;" + format
			+ CommonMethods.fill("0", gy_cy_dec);
	    }
	}
    }

    /**
     * return a string which is the format of the amount
     * 
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_def_sign(Integer amt_sign, String gl_type, ReportParamsCO paramsCO)
	    throws BaseException
    {
	String format = f_currency_mask_1(paramsCO.getBaseCurrDecPoint(), paramsCO);//Updated by NOjeil & LBedrane - US = 751197
	BigDecimal gy_cy_dec = paramsCO.getBaseCurrDecPoint();
	format = "#,##0.";
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    if(gy_cy_dec.intValue() <= 0)
	    {
		format = "#,##0";
	    }
	    if((amt_sign.intValue() == -1 && "L".equals(gl_type)) || (amt_sign.intValue() == -1 && "S".equals(gl_type))
		    || (amt_sign.intValue() == -1 && "R".equals(gl_type)))
	    {
		return format = format + CommonMethods.fill("0", gy_cy_dec) + " ;(" + format
			+ CommonMethods.fill("0", gy_cy_dec) + ")";
	    }
	    else if((amt_sign.intValue() == 1 && "A".equals(gl_type))
		    || (amt_sign.intValue() == 1 && "E".equals(gl_type)))
	    {
		return format = "(" + format + CommonMethods.fill("0", gy_cy_dec) + ")";
	    }
	    else
	    {
		return format = format + CommonMethods.fill("0", gy_cy_dec) + " ;" + format
			+ CommonMethods.fill("0", gy_cy_dec);
	    }
	}
    }

    /**
     * this function is used whitin special cbk reports where the amounts are
     * multiplied by -1
     * 
     * 
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_def_sign_without_dec(Integer amt_sign, String gl_type, ReportParamsCO paramsCO)
	    throws BaseException
    {
	String format = f_currency_mask_1(paramsCO.getBaseCurrDecPoint(), paramsCO);//Updated by NOjeil & LBedrane - US = 751197
	BigDecimal gy_cy_dec = paramsCO.getBaseCurrDecPoint();
	format = "#,##0";
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    if(gy_cy_dec.intValue() <= 0)
	    {
		format = "#,##0";
	    }
	    if((amt_sign.intValue() == -1 && "L".equals(gl_type)) || (amt_sign.intValue() == -1 && "S".equals(gl_type))
		    || (amt_sign.intValue() == -1 && "R".equals(gl_type)))
	    {
		return format = format + " ;(" + format + ")";
	    }
	    else if((amt_sign.intValue() == 1 && "A".equals(gl_type))
		    || (amt_sign.intValue() == 1 && "E".equals(gl_type)))
	    {
		return format = "(" + format + ")";
	    }
	    else
	    {
		return format = format + " ;" + format;
	    }
	}
    }
    
    /**
     *this function is used within special cbk reports to return a string  which is the format of the amount
     * 
     * @param adec_amt
     * @param ac_disp_zeros
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_currency_mask_def_cbk_without_dec_dash(Number adec_amt, String ac_disp_zeros, ReportParamsCO paramsCO)
	    throws BaseException
    {
	String lv_format="#,##0 ;#,##0";
	
	if("N".equals(ac_disp_zeros) && (adec_amt==null || adec_amt.doubleValue()==0))
	{
	    lv_format = "-  ";
	}
	return lv_format;
    }
    
    /**
     * this method returns the values for the template header to be displayed in fcr / ftr reports
     *
     */

    public static String returnTemplateHeaderVal(String tempHeaderField, BigDecimal tempHeaderCode, ReportParamsCO repParamsCO, int pageNbr)
	    throws BaseException
    {
	if("".equals(StringUtil.nullToEmpty(tempHeaderCode)))
	{
	   return ""; 
	}
	else
	{
	    	CommonLookupBO repotsCommon = (CommonLookupBO) ApplicationContextProvider.getApplicationContext().getBean(
	    		"commonLookupBO");
        	GLSHEADERVO vo = repotsCommon.returnTemplateHeaderVal(tempHeaderCode);
        	String propValue = (String) PathPropertyUtil.returnProperty(vo, tempHeaderField.toUpperCase(Locale.ENGLISH));
        	//return the session attributes' names
        	HashMap<String, String> hm = CashedConstantsCommonRep.sessionAttrHm;
        	if(hm.isEmpty())
        	{
        	    CommonLibBO commonLibBO = (CommonLibBO) ApplicationContextProvider.getApplicationContext().getBean(
        		"commonLibBO");
        	    IRP_SESSION_ATTRIBUTESVO sessionAttrVO = new IRP_SESSION_ATTRIBUTESVO();
        	    List<IRP_SESSION_ATTRIBUTESVO> list = commonLibBO.returnSessionAttrList(sessionAttrVO);
        	    for(IRP_SESSION_ATTRIBUTESVO attr : list)
        	    {
        		hm.put(attr.getTECHNICAL_NAME(), attr.getATTRIBUTE_NAME()+"~"+attr.getATTRIBUTE_TYPE());
        	    }
        	 }
        	//
        	if(hm.containsKey(propValue))
        	{
        	    String[] arr = (hm.get(propValue)).split("~");
        	    Object res = PathPropertyUtil.returnProperty(repParamsCO, arr[0]);
        	    if(arr[1].equals("DATE"))
        	    {
        		return DateUtil.format((Date) res, DateUtil.DEFAULT_DATE_FORMAT);
        	    }
        	    else
        	    {
        		return (String) res;
        	    }
        	}
        	else if("PROG_REF".equals(propValue))
        	{
        	    return (String) PathPropertyUtil.returnProperty(repParamsCO, "progRef");
        	}
        	else if("p_n".equals(propValue))
        	{
        	    return String.valueOf(pageNbr);
        	}
        	else if("today_dt".equals(propValue))
        	{
        	    return DateUtil.format(new Date(), DateUtil.DEFAULT_DATE_FORMAT);
        	}
        	else
        	{
        	    return StringUtil.nullToEmpty(propValue);
        	}
	}
    }

    /**
     * return the application long description (IIS Reports)
     */
    public static String f_getgv_app(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    return paramsCO.getRepAppName();
	}
    }

    /**
     * return the application long description if 'LEND' (IIS Reports)
     */
    public static String f_getgv_app_lend(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String ls_app = f_getgv_app(paramsCO);
	    if((ConstantsCommon.LCOR_APP_NAME).equals(ls_app) || (ConstantsCommon.LRET_APP_NAME).equals(ls_app)
		    || (ConstantsCommon.LEND_APP_NAME).equals(ls_app))
	    {
		return ConstantsCommon.LEND_APP_NAME;
	    }
	    else
	    {
		return ls_app;
	    }
	}

    }

    /**
     * return the long short description (IIS Reports)
     */
    public static String f_long_short_desc_ind(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_LONG_SHORD_DESC_IND(f_getapplication(paramsCO),paramsCO.getConnCO());
	}
    }

    /**
     * return company phone (IIS Reports)
     */
    public static String f_get_comp_phone(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_GET_COMP_PHONE(paramsCO.getCompanyCode(),paramsCO.getConnCO());
	}
    }

    /**
     * return CIF name (IIS Reports)
     */
    public static String f_get_cif_name(BigDecimal ar_company, BigDecimal ar_cif, ReportParamsCO paramsCO)
	    throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnCifShortName(ar_company, paramsCO.getRepLanguage(), ar_cif,paramsCO.getConnCO());
	}
    }

    /**
     * return log details (PCS Reports)
     */
    public static String f_getlogdetails(BigDecimal logNumber, BigDecimal pftLogDet, ReportParamsCO paramsCO)
	    throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    String pftLogDetStr;
	    if(BigDecimal.ZERO.equals(pftLogDet))
	    {
		pftLogDetStr = ConstantsCommon.PFT_LOG_DETAIL_FROM;
	    }
	    else
	    {
		pftLogDetStr = ConstantsCommon.PFT_LOG_DETAIL_TO;
	    }

	    return repotsCommon.returnF_GetLogDetails(paramsCO.getCompanyCode(), logNumber, pftLogDetStr,paramsCO.getConnCO());
	}
    }

    /**
     * return branch description (PCS Reports)
     */
    public static String f_get_branch_desc_pcs(BigDecimal companyCode, BigDecimal branchCode, BigDecimal profitGroup,
	    BigDecimal al_lang_type, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_GET_BRANCH_DESC_PCS(companyCode, branchCode, profitGroup, paramsCO
		    .getIsRTLDir(),paramsCO.getConnCO());
	}
    }

    /**
     * return profit calculation description (PCS Reports)
     */
    public static String f_get_pftcalc_desc(BigDecimal profitGroup, BigDecimal al_lang_type, ReportParamsCO paramsCO)
	    throws BaseException
    {

	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon
		    .returnF_GET_PFTCALC_DESC(paramsCO.getCompanyCode(), profitGroup, paramsCO.getIsRTLDir(),paramsCO.getConnCO());
	}
    }

    /**
     * return PCS Month Day(PCS Reports)
     */
    public static BigDecimal f_m_day(BigDecimal profitGroup, BigDecimal logNumber, ReportParamsCO paramsCO)
	    throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_M_Day(paramsCO.getCompanyCode(), profitGroup, logNumber, paramsCO
		    .getBaseCurrencyCode(),paramsCO.getConnCO());
	}
    }

    /**
     * return FMS CurrencyMaskCy Code(FMS Reports)
     */
    public static String f_currency_mask_cy_code(BigDecimal cyCode, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_CURRENCY_MASK_CY(paramsCO.getCompanyCode(), cyCode,paramsCO.getConnCO());
	}
    }

    /**
     * return convert into Arabic words (AAOIFI Reports)
     */
    public static String f_num_to_words_arab(BigDecimal numToConvert, String currName, BigDecimal currNo,
	    ReportParamsCO paramsCO) throws Exception
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_NUM_TO_WORDS_ARAB_NO_ONLY(numToConvert, paramsCO.getCompanyCode(), currNo,
		    paramsCO.getBaseCurrencyCode(),paramsCO.getConnCO());
	}
    }
    
    /**
     * 
     * @param numToConvert
     * @param currName
     * @param currNo
     * @param suffix
     * @param paramsCO
     * @return
     * @throws Exception
     */
    public static String f_num_to_words_arab(BigDecimal numToConvert, String currName, BigDecimal currNo, String suffix,
	    ReportParamsCO paramsCO) throws Exception
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_NUM_TO_WORDS_ARAB_NO_ONLY(numToConvert, paramsCO.getCompanyCode(), currNo,
		    paramsCO.getBaseCurrencyCode(), suffix, paramsCO.getConnCO());
	}
    }

    /**
     * return convert into Arabic words (AAOIFI Reports)
     */
    public static String f_num_to_words_arab_bb(BigDecimal numToConvert, String currName, BigDecimal currNo,
	    ReportParamsCO paramsCO) throws Exception
    {
	return f_num_to_words_arab(numToConvert, currName, currNo, paramsCO);
    }

    /**
     * return currency mask with bracket (IIS Reports)
     */
    public static String f_currency_mask_with_bracket_long(BigDecimal currCode, ReportParamsCO paramsCO)
	    throws BaseException
    {

	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String format = f_currency_mask_1(f_get_dec_point(currCode, paramsCO), paramsCO);//Updated by NOjeil & LBedrane - US = 751197
	    
	    StringBuffer  sb=new StringBuffer(format);
	    sb.append(";(").append(format).append(")");
	    return sb.toString();
	}
    }
    
    public static String f_set_teller_mask( BigDecimal cardNb, ReportParamsCO paramsCO ) throws BaseException
    {
        if(paramsCO.getNoData())
        {
            return "";   
        }
        else
        {
            ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext().getBean("reportsCommonBO");
            return repotsCommon.returnF_SET_TELLER_MASK( paramsCO.getCompanyCode() ,paramsCO.getBranchCode(), cardNb.toString() ,paramsCO.getUserName(),paramsCO.getConnCO());
        }
    }
    
    public static String lTrim(String s) 
    {
    	    int i = 0;
    	    while (i < s.length() && Character.isWhitespace(s.charAt(i))) 
    	    {
    	        i++;
    	    }
    	    return s.substring(0,i);
    }

    public static String rTrim(String s) 
    {
	    int i = s.length()-1;
	    while (i >= 0 && Character.isWhitespace(s.charAt(i))) 
	    {
	        i--;
	    }
	    return s.substring(i+1);
    }
    
    
    public static String f_translate_string_spaces(String as_text, ReportParamsCO paramsCO ) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String left_trim = lTrim(as_text);
	    String right_trim = rTrim(as_text);
	    String mainStr=as_text.trim();
	    String str_translated= f_translate_string(mainStr ,paramsCO);
	    return left_trim+str_translated+right_trim;
	}
    }
    public static String f_get_formated_base_cy (BigDecimal a_dec_value, BigDecimal a_base_cy, ReportParamsCO paramsCO ) throws BaseException
    {   
	BigDecimal a_dec_val=a_dec_value;
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    String ls_amtformat;
	    String ls_fpo_format;
	    BigDecimal  li_dec_points;					 
    
	    if( NumberUtil.isEmptyDecimal(a_dec_val))
	    {
		a_dec_val = new BigDecimal("0.00");
	    }
           
	    li_dec_points = f_get_decimal_point_bc( a_base_cy, paramsCO) ;
           
	    ls_fpo_format = "#,##0";
           
	    if( !(li_dec_points.equals(BigDecimal.ZERO)) && !(NumberUtil.isEmptyDecimal(li_dec_points )) )
	    {
		ls_fpo_format = "#,##0.";	      
	    }
          	 
	    HashMap<String,Object> theNbFormat=paramsCO.getUserNbFormats();
	    String groupSepa = StringUtil.nullEmptyToValue(theNbFormat.get("groupSepa"), ",");
	    String decimalSepa = StringUtil.nullEmptyToValue(theNbFormat.get("decimalSepa"), ".");
	    ls_amtformat = NumberUtil.format( a_dec_val ,ls_fpo_format +  NumberUtil.fill("0", li_dec_points)  +" "+";(" + ls_fpo_format +  NumberUtil.fill("0", li_dec_points) + ")" ,groupSepa.charAt(0),decimalSepa.charAt(0));
           
	    return ls_amtformat;
	}
    }
    
    public static String f_qty_mask_def (BigDecimal ar_qty, ReportParamsCO paramsCO ) throws BaseException 
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    StringBuffer  lv_format=new StringBuffer("#,##0");
	    if ( (ar_qty.doubleValue()) >  NumberUtil.truncate( ar_qty.doubleValue() , BigDecimal.ZERO.longValue() ))
	    {
		lv_format.append(".0000");
	    }
        	    
	    return lv_format.toString();
	}
    }
    
    public static String  f_currency_mask_price (BigDecimal cyDecimal, ReportParamsCO paramsCO) throws BaseException 
    {
    	if(paramsCO.getNoData())
    	{
    	    return "";
    	}
    	else
    	{
    		return f_currency_mask_1( cyDecimal,paramsCO);//Updated by NOjeil & LBedrane - US = 751197
    	}
    }

    public static String  f_qty_mask ( ReportParamsCO paramsCO) throws BaseException 
    {
    	if(paramsCO.getNoData())
    	{
    	    return "";
    	}
    	else
    	{
    		return f_currency_mask_1( RepConstantsCommon.QTY_DEC_PTS ,paramsCO);//Updated by NOjeil & LBedrane - US = 751197
    	}
    }

    public static String f_islamicvalues(String as_field, ReportParamsCO paramsCO) throws BaseException 
    {
    	String as_fe_upper= as_field.toUpperCase(Locale.ENGLISH);
    	ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
    		    .getBean("reportsCommonBO");
    	String appType = (repotsCommon.returnAPP_TYPE(ConstantsCommon.ACC_APP_NAME,paramsCO.getConnCO())).toUpperCase(Locale.ENGLISH);
    	if((RepConstantsCommon.FIELD_YIELD.toUpperCase(Locale.ENGLISH)).equals(as_fe_upper))  //  ConstantsCommon.YIELD_UPPER  public static final String  YIELD_WORD= "YIELD";
    	{
    		if( (RepConstantsCommon.ISLAMIC_APP_TYPE).equals(appType))
    		{
    			return RepConstantsCommon.FIELD_YIELD;  //  ConstantsCommon.YIELD_WORD  public static final String  YIELD_WORD= "Yield";
    		}
    		else
    		{
    			return RepConstantsCommon.FIELD_INTEREST;
    		}
    	}
    	else if((RepConstantsCommon.FIELD_PROFIT.toUpperCase(Locale.ENGLISH)).equals(as_fe_upper))
    	{
    		if( (RepConstantsCommon.ISLAMIC_APP_TYPE).equals(appType))
    		{
    			return RepConstantsCommon.FIELD_PROFIT;
    		}
    		else
    		{
    			return RepConstantsCommon.FIELD_INTEREST;
    		}
    	}
    	else if((RepConstantsCommon.FIELD_POINTS.toUpperCase(Locale.ENGLISH)).equals(as_fe_upper))
    	{
    		if( (RepConstantsCommon.ISLAMIC_APP_TYPE).equals(appType))
    		{
    			return RepConstantsCommon.FIELD_POINTS;
    		}
    		else
    		{
    			return RepConstantsCommon.FIELD_INTEREST;
    		}
    	}
    	else if((RepConstantsCommon.FIELD_INTEREST.toUpperCase(Locale.ENGLISH)).equals(as_fe_upper))
    	{
    		if( (RepConstantsCommon.ISLAMIC_APP_TYPE).equals(appType))
    		{
    			return RepConstantsCommon.FIELD_PROFIT;
    		}
    		else
    		{
    			return RepConstantsCommon.FIELD_INTEREST;
    		}
    	}
    	else 
    	{
    	    return as_field;
    	}
    }


    public static String f_islamic ( ReportParamsCO paramsCO) throws BaseException
    {
    	if(paramsCO.getNoData())
    	{
    	    return "";
    	}
    	else
    	{
    		ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
    				.getBean("reportsCommonBO");
    		String appType = repotsCommon.returnAPP_TYPE(ConstantsCommon.ACC_APP_NAME,paramsCO.getConnCO()) ;
    		if(appType!=null)
    		{
    		    appType= appType.toUpperCase(Locale.ENGLISH);
    		}
    		return appType;
    	}
    }

    public static String f_get_sys_name (ReportParamsCO paramsCO) throws Exception
    {
    	if(paramsCO.getNoData())
    	{
    	    return "";
    	}
    	else
    	{
    	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
    		    .getBean("reportsCommonBO");
    	    return repotsCommon.returnSYSTEM_NAME(paramsCO.getConnCO());
    	}
    }
    
    public static String f_base_currency_desc(String a_type, ReportParamsCO paramsCO)  throws BaseException
    {
    	if(paramsCO.getNoData())
    	{
    	    return "";
    	}
    	else
    	{
    	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
	    .getBean("reportsCommonBO");

    	    if((RepConstantsCommon.LANG_AR_A).equals(a_type)  || (RepConstantsCommon.LANG_AR_ARAB).equals(a_type) || (RepConstantsCommon.LANG_AR_ARABIC).equals(a_type))
    	    {
    		return repotsCommon.returnCurrencyDesc(paramsCO.getCompanyCode(), ConstantsCommon.LANGUAGE_ARABIC , paramsCO.getBaseCurrencyCode(),paramsCO.getConnCO());
    	    }
    	    else
    	    {
    		return repotsCommon.returnCurrencyDesc(paramsCO.getCompanyCode(), ConstantsCommon.LANGUAGE_ENGLISH, paramsCO.getBaseCurrencyCode(),paramsCO.getConnCO());
    	    }
    	}
    }
    public static String f_get_RepLanguage(ReportParamsCO paramsCO)  throws BaseException
    {
	if(paramsCO.getNoData())
    	{
    	    return "";
    	}
	else
	{
	    return paramsCO.getRepLanguage();
	}
    }
    /**
     * Used by IIS Reports with 3 arguments (IIS Reports)
     * Where running date to be checked by team after testing 
     */
    public static BigDecimal f_get_cross_rate(BigDecimal a_tr_cy, BigDecimal a_pf_cy, Date a_date , ReportParamsCO paramsCO)  throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
	    		    .getBean("reportsCommonBO");
	    return reportsCommon.returnF_GET_CROSS_RATE(paramsCO.getCompanyCode(),a_tr_cy, paramsCO.getBranchCode(), a_pf_cy, paramsCO.getBaseCurrencyCode(), a_date,paramsCO.getConnCO());
	}
    }
    
    /**
     * Used by IIS Reports with 2 arguments (IIS Reports)
     * to get the exchange rate based on passed date and currency
     */
   public static BigDecimal f_get_exch_rate_new( BigDecimal currCode, Date a_date , ReportParamsCO paramsCO)  throws BaseException
   {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
	    		    .getBean("reportsCommonBO");
	    return reportsCommon.returnF_GET_EXCHRATE_NEW(paramsCO.getCompanyCode(),paramsCO.getBaseCurrencyCode(), currCode, a_date,paramsCO.getConnCO());
	}
  }

    public static BigDecimal f_get_mddr_utilized_report_iis(BigDecimal al_cif_no, Date adt_date, ReportParamsCO paramsCO)
	    throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return reportsCommon.returnF_GET_MDDR_UTILIZED_REPORT_IIS(al_cif_no, adt_date, paramsCO.getCompanyCode(),
		    paramsCO.getBranchCode(), paramsCO.getRunningDateRET(), paramsCO.getBaseCurrencyCode(),paramsCO.getConnCO());
	}

    }

    public static Date f_get_next_working_day_currency(BigDecimal al_days, Date a_date, BigDecimal countryCode,
	    ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return reportsCommon.returnF_GET_NEXT_WORKING_DAY_BY_COUNTRY(al_days, a_date, paramsCO.getCompanyCode(),
		    countryCode,paramsCO.getConnCO());
	}
    }

    /**
     * Used by IIS Reports (IIS Reports)
     */
    public static BigDecimal f_convert_to_cv(BigDecimal ar_amount, BigDecimal ar_fromcurr, BigDecimal ar_tocurr,
	    Date ar_date, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return reportsCommon.returnF_CONVERT_TO_CV_IIS(ar_amount, ar_fromcurr, ar_tocurr, ar_date, paramsCO
		    .getCompanyCode(), paramsCO.getBranchCode(), paramsCO.getBaseCurrencyCode(),paramsCO.getConnCO());
	}

    }

    public static BigDecimal f_get_mddr_utilized_iis_for_next(BigDecimal al_cif_no, BigDecimal ai_argument,
	    ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return reportsCommon.returnF_GET_MDDR_UTILIZED_IIS_FOR_NEXT(al_cif_no, paramsCO.getRunningDateRET(),
		    paramsCO.getBranchCode(), paramsCO.getCompanyCode(), paramsCO.getBaseCurrencyCode(),paramsCO.getConnCO());
	}
    }
    
    public static String f_num_to_words_english_bb(BigDecimal a_number_to_convert, String a_currency_name,
	    BigDecimal a_currency_dec, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    return f_num_to_words_english(a_number_to_convert, a_currency_name,a_currency_dec,paramsCO);
	}
    }
    //***************************************************CSM Functions(BB Reports)***************************************************************************
    public static BigDecimal f_calc_total_amount(BigDecimal first_payment, BigDecimal last_payment, BigDecimal amount, BigDecimal num_of_payment
	    ,ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{

	    BigDecimal TotalAmount = null  ;

	    if (((NumberUtil.isEmptyDecimal(first_payment)) && (NumberUtil.isEmptyDecimal(last_payment))) 
		    || ( first_payment == BigDecimal.ZERO && last_payment == BigDecimal.ZERO ) 
		    || ((NumberUtil.isEmptyDecimal(first_payment)) && last_payment == BigDecimal.ZERO )
		    || ((NumberUtil.isEmptyDecimal(last_payment)) && first_payment == BigDecimal.ZERO ) )
	    {
		TotalAmount = amount.multiply(num_of_payment);
	    }
	    else if (((!NumberUtil.isEmptyDecimal(first_payment)) && (NumberUtil.isEmptyDecimal(last_payment))) || last_payment == BigDecimal.ZERO )

	    {
		TotalAmount =  ((amount.multiply((num_of_payment.subtract(BigDecimal.ONE))))).add(first_payment) ;
	    }

	    else if ((NumberUtil.isEmptyDecimal(first_payment)) &&  ((!NumberUtil.isEmptyDecimal(last_payment))) || first_payment == BigDecimal.ZERO ) 

	    {
		TotalAmount = (amount.multiply(( num_of_payment.subtract(BigDecimal.ONE)))).add(last_payment);
	    }

	    else if(((!NumberUtil.isEmptyDecimal(first_payment))) && ((!NumberUtil.isEmptyDecimal(last_payment)) ))
	    {
		TotalAmount = (amount.multiply(num_of_payment.subtract(BigDecimal.valueOf(2)))).add(last_payment).add(first_payment) ;
	    }

	    return TotalAmount ;
	}
    }
    public static String f_get_arab_branch_desc(ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO reportsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
	    .getBean("reportsCommonBO");
	    return reportsCommon.returnF_GET_BRANCH_DESC(paramsCO.getCompanyCode() , paramsCO.getBranchCode() , paramsCO.getLanguage(),  paramsCO.getConnCO());
	}
    }
    public static String f_num_to_words_arab_tii(BigDecimal numToConvert, String currName, BigDecimal currNo,
	    ReportParamsCO paramsCO) throws Exception
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    return f_num_to_words_arab(numToConvert, currName, currNo, paramsCO);
	}
    }
   public static String f_num_to_words_english_tii(BigDecimal a_number_to_convert, String a_currency_name,
	    BigDecimal a_currency_dec, ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    return f_num_to_words_english(a_number_to_convert, a_currency_name,a_currency_dec,paramsCO);
	}
    }
   
   public static String f_get_currency_name(BigDecimal currencyCode, ReportParamsCO paramsCO) throws BaseException
   {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{ 
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    String LS_BRIEF= repotsCommon.returnF_CY_DETAILS("BRIEF_DESC_ENG", currencyCode, paramsCO.getCompanyCode(), paramsCO.getConnCO());
	    String LS_LONG = repotsCommon.returnF_CY_DETAILS("LONG_DESC_ENG", currencyCode, paramsCO.getCompanyCode(), paramsCO.getConnCO());    
	    return LS_BRIEF.trim()+"  "+LS_LONG.trim();
	}
   }
   
   public static String f_get_branch_name(BigDecimal al_branch, String as_language, ReportParamsCO paramsCO) throws BaseException
   {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
	    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_GET_BRANCH_DESC( paramsCO.getCompanyCode(), al_branch, as_language,
		    paramsCO.getConnCO());
	}
   }
   
   public static String f_get_currency_desc_a(BigDecimal al_comp_code, BigDecimal al_cy_code, ReportParamsCO paramsCO) throws BaseException
   {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    String theVarResult;
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    theVarResult = repotsCommon.returnCurrencyDesc(al_comp_code, "AR", al_cy_code,paramsCO.getConnCO());
	    return theVarResult;
	}
   }
   
   public static String f_get_arabic_dayname(String as_english_dayname, ReportParamsCO paramsCO) throws BaseException
   {
       if("Monday".equals(as_english_dayname))
       {
	   return RepConstantsCommon.Monday_AR;
       }
       else if("Tuesday".equals(as_english_dayname))
       {
	   return RepConstantsCommon.Tuesday_AR ;
       }
       else if("Wednesday".equals(as_english_dayname))
       {
	   return RepConstantsCommon.Wednesday_AR;
       }
       else if("Thursday".equals(as_english_dayname))
       {
	   return RepConstantsCommon.Thursday_AR;
       }
       else if("Friday".equals(as_english_dayname))
       {
	   return RepConstantsCommon.Friday_AR;
       }
       else if("Saturday".equals(as_english_dayname))
       {
	   return RepConstantsCommon.Saturday_AR;
       }
       else if("Sunday".equals(as_english_dayname))
       {
	   return RepConstantsCommon.Sunday_AR;
       }
       else
       {
	   return "-1";
       }
   }
   
   /**
    * this method will return the gregorian  name of the month based on the parameter month
    * @param month
    * @param paramsCO
    * @return
    * @throws BaseException
    */
   
    public static String f_get_arabic_month_name(int month, ReportParamsCO paramsCO) throws BaseException
    {
	String monthGreg = "";
	switch (month)
	{
	    case 1:
		monthGreg = RepConstantsCommon.JAN_Gregorian;
		break;
	    case 2:
		monthGreg = RepConstantsCommon.FEB_Gregorian;
		break;
	    case 3:
		monthGreg = RepConstantsCommon.MAR_Gregorian;
		break;
	    case 4:
		monthGreg = RepConstantsCommon.APR_Gregorian;
		break;
	    case 5:
		monthGreg = RepConstantsCommon.MAY_Gregorian;
		break;
	    case 6:
		monthGreg = RepConstantsCommon.JUN_Gregorian;
		break;
	    case 7:
		monthGreg = RepConstantsCommon.JUL_Gregorian;
		break;
	    case 8:
		monthGreg = RepConstantsCommon.AUG_Gregorian;
		break;
	    case 9:
		monthGreg = RepConstantsCommon.SEP_Gregorian;
		break;
	    case 10:
		monthGreg = RepConstantsCommon.OCT_Gregorian;
		break;
	    case 11:
		monthGreg = RepConstantsCommon.NOV_Gregorian;
		break;
	    case 12:
		monthGreg = RepConstantsCommon.DEC_Gregorian;
		break;
	    default:
		break;

	}
	return monthGreg;
    }
   
   public static String f_get_branch_manager_name(String as_language, ReportParamsCO paramsCO) throws BaseException
   {
	if(paramsCO.getNoData())
	{
	    return null;
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_GET_BRANCH_MANAGER_NAME(paramsCO.getCompanyCode(), paramsCO.getBranchCode() , as_language, paramsCO.getConnCO());
	     
	}
   }
   
 //************************************************************************************************************************************************************

   public static String f_currency_mask_dec_cy(BigDecimal ai_dec_points, ReportParamsCO paramsCO) throws BaseException
   {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	String nbFormat = "#,##0.";
	if(ai_dec_points == null || ai_dec_points.intValue() <= 0)
	{
	    nbFormat = "#,##0";
	}
	StringBuffer format = new StringBuffer();
	format.append(nbFormat).append(CommonMethods.fill("0", ai_dec_points)).append(" ;(").append(nbFormat)
		.append(CommonMethods.fill("0", ai_dec_points)).append(")");
	return format.toString();
   }
   
   public static String f_num_to_words_english_noamount(BigDecimal deal_amout, String currency_name,
	    ReportParamsCO paramsCO) throws BaseException
  {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_NUM_TO_WORDS_ENGLISH(deal_amout, currency_name);
	}
  }
   
    /**
     * 
     * @param deal_amout
     * @param currency_name
     * @param suffix
     * @param paramsCO
     * @return
     * @throws BaseException
     */
    public static String f_num_to_words_english_noamount(BigDecimal deal_amout, String currency_name, String suffix,
	    ReportParamsCO paramsCO) throws BaseException
    {
	if(paramsCO.getNoData())
	{
	    return "";
	}
	else
	{
	    ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
		    .getBean("reportsCommonBO");
	    return repotsCommon.returnF_NUM_TO_WORDS_ENGLISH(deal_amout, currency_name, suffix);
	}
    }
   
   /**
    * return the value description based on the value code ,the lov type id and the language 
    * @param paramsCO
    * @param lovTypeId
    * @param lovCode
    * @return value desc
    * @throws BaseException
    */
   public static String returnSingleLOV(BigDecimal lovTypeId, String lovCode, ReportParamsCO paramsCO) throws BaseException
   {
       ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
	    .getBean("reportsCommonBO");
       return repotsCommon.returnSingleLOV(lovTypeId, lovCode, paramsCO.getRepLanguage(), paramsCO.getConnCO());
   } 
   
   /**
    * Method that returns Computed FC Amount for given company , currency and Division/Multiplication Rate
    * @param dRate Division Rate
    * @param cvAmount FC Amount
    * @param cyCode Currency Code
    * @param conn DAtabase Connection
    * @param paramsCO 
    * @throws BaseException Exception if error occurred.
    */
   public static BigDecimal f_compute_fcamt(BigDecimal adRate, BigDecimal acvAmount, BigDecimal aCy, ReportParamsCO paramsCO) throws BaseException
   {
       ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
	    .getBean("reportsCommonBO");
       	 return repotsCommon.returnF_COMPUTE_FCAMT(paramsCO.getCompanyCode(), adRate, acvAmount, aCy, paramsCO.getConnCO());
             	 
   }
   
   /**
    * Method that returns the account restrictions
    * @param alCif Cif Number
    * @param alGl Gl Number
    * @param alBr Branch Code
    * @param alCy Currency Code 
    * @param alSl Sl Number 
    * @param paramsCO 
    * @throws BaseException Exception if error occurred.
    */
   public static Integer f_check_acc_restrictions_new(BigDecimal alCif, BigDecimal alGl, BigDecimal alBr, BigDecimal alCy, BigDecimal alSl,ReportParamsCO paramsCO) throws BaseException
   {
       ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
	    .getBean("reportsCommonBO");
  	 return repotsCommon.returnF_CHECK_ACC_RESTRICTION_NEWS(paramsCO.getCompanyCode(), alBr, alCy,
  		alGl, alCif, alSl, paramsCO.getRepAppName(), paramsCO.getProgRef(),paramsCO.getUserName(), paramsCO.getConnCO()).intValue();

   }
   /**
    * Method that translates Dates having patterns dd-MMM-yy or MMM-yy using
    * the report language
    * 
    * @param date value
    * @param paramsCO
    * @throws BaseException Exception if error occurred.
    */
   public static String f_translate_date(String dt, ReportParamsCO paramsCO) throws BaseException
   {
	ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext().getBean(
		"reportsCommonBO");
	//in case of sybase
	dt=dt.replaceAll("00--", "");
	if("--".equals(dt.trim()))
	{
	    return "";
	}
	else if(StringUtil.isNotEmpty(dt.trim()))
	{
	    String[] dtArr = dt.split(" ");
	    String dtPattern = retFcrDatePattern(dtArr[0]);
	    String isoLangCode = repotsCommon.returnF_Get_ISO_Code(paramsCO.getRepLanguage(), paramsCO.getConnCO());
	    Locale requested_locale = PathLocalizedTextUtil.localeFromString (isoLangCode, null);
	    SimpleDateFormat df = new SimpleDateFormat(dtPattern, requested_locale);
	    String frmtDt;
	    StringBuffer sb = new StringBuffer();
	    for(int i = 0; i < dtArr.length; i++)
	    {
	    	if("--".equals(dtArr[i]))
	    	{
	    		sb.append(" ");
	    	}
	    	else
	    	{
	    		frmtDt = df.format(DateUtil.parseDate(dtArr[i], dtPattern));
	    		sb.append(frmtDt).append(" ");
	    	}
	    }
	    return sb.toString().trim();
	}
	else
	{
	    return dt;
	}
   }
    
    //fares
    /**Added by fares kassab
     * @param adec_amt
     * @param ac_disp_zeros
     * @params amt_sign
     * @params gl_type
     * @throws BaseException Exception if error occurred
     *this function is Created on 19/10/2006 for removing the decimals from CBK reports
     */ 
    public static String f_currency_mask_sign_without_dec_dash(BigDecimal adec_amt, String ac_disp_zeros,Integer amt_sign, String gl_type, ReportParamsCO paramsCO) throws BaseException
    {
    	StringBuffer  lv_format=new StringBuffer("#,##0");
    	StringBuffer  lv_format1=new StringBuffer("#,##0");
    	BigDecimal gy_cy_dec = paramsCO.getBaseCurrDecPoint();
    	
    	if(NumberUtil.isEmptyDecimal(gy_cy_dec) || gy_cy_dec.intValue()<= 0)
    	{
    		lv_format=new StringBuffer("#,##0");
    	}
    	
    	if((amt_sign.intValue() == -1 && RepConstantsCommon.GL_TYPE_L.equals(gl_type)) || (amt_sign.intValue() == -1 && RepConstantsCommon.GL_TYPE_S.equals(gl_type))
    		|| (amt_sign.intValue() == -1 && RepConstantsCommon.GL_TYPE_R.equals(gl_type)))
    	{
    		lv_format = lv_format1.append(" ;(").append(lv_format).append(")");
    	}
    	else if((amt_sign.intValue() == 1 && RepConstantsCommon.GL_TYPE_A.equals(gl_type))
    		    || (amt_sign.intValue() == 1 && RepConstantsCommon.GL_TYPE_E.equals(gl_type)))
	    {
    		lv_format = new StringBuffer("(").append(lv_format).append(")");
	    }
	    else
	    {
	    	lv_format = lv_format1.append(" ;").append(lv_format);
    	}
    	if(ConstantsCommon.NO.equals(ac_disp_zeros) && (adec_amt.intValue() == 0 || NumberUtil.isEmptyDecimal(adec_amt)))
    	{
    		lv_format = new StringBuffer("-  ");
    	}
    	return lv_format.toString();
    }
    
    public static String f_currency_mask_def_without_dec() throws BaseException
    {
    	StringBuffer  lv_format=new StringBuffer("#,##0");
    	StringBuffer  lv_format1=new StringBuffer("#,##0");
    	lv_format = lv_format1.append(" ;(").append(lv_format).append(")");
    	return lv_format.toString();
    }
    
    public static String f_get_country_arabic(BigDecimal companyCode, BigDecimal countryCode, String language,ReportParamsCO paramsCO) throws BaseException
    {
    	ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext()
    	.getBean("reportsCommonBO");

    	return repotsCommon.returnCountryDesc(paramsCO.getCompanyCode(), countryCode,ConstantsCommon.LANGUAGE_ARABIC ,paramsCO.getConnCO());
    }
    
    
    public static String f_currency_mask_def_cbk_without_dec() throws BaseException
    {
    	StringBuffer  lv_format=new StringBuffer("#,##0");
    	StringBuffer  lv_format1=new StringBuffer("#,##0");
    	lv_format = lv_format1.append(" ;").append(lv_format);
    	return lv_format.toString();
    }
    
    /**Added by fares kassab
     * @params amt_sign
     * @params gl_type
     * @throws BaseException Exception if error occurred
     *this function is used whitin special cbk reports where the amounts are multiplied by -1
     */ 
    public static String f_currency_mask_def_liq_without_dec(Integer amt_sign, String gl_type, ReportParamsCO paramsCO) throws BaseException
    {
    	StringBuffer  lv_format=new StringBuffer("#,##0");
    	StringBuffer  lv_format1=new StringBuffer("#,##0");
    	if(amt_sign.intValue() == -1 && RepConstantsCommon.GL_TYPE_L.equals(gl_type))
    	{
    		lv_format = lv_format1.append(" ;").append(lv_format);
    	}
    	else if (amt_sign.intValue() == 1 && RepConstantsCommon.GL_TYPE_L.equals(gl_type))
    	{
    		lv_format = lv_format1.append(" ;(").append(lv_format).append(")");
    	}
    	else if (amt_sign.intValue() == -1 && RepConstantsCommon.GL_TYPE_A.equals(gl_type))
    	{
    		lv_format = lv_format1.append(" ;(").append(lv_format).append(")");
    	}
    	else
    	{
    		lv_format = lv_format1.append(" ;").append(lv_format);
    	}
    	
    	return lv_format.toString();
    }
    
    public static String f_currency_mask_def_sign_with_sixdec(Integer amt_sign, String gl_type, ReportParamsCO paramsCO) throws BaseException
    {
    	StringBuffer  lv_format=new StringBuffer("#,##0.000000");
    	StringBuffer  lv_format1=new StringBuffer("#,##0.000000");
    	BigDecimal gy_cy_dec = paramsCO.getBaseCurrDecPoint();
    	if(NumberUtil.isEmptyDecimal(gy_cy_dec) || gy_cy_dec.intValue()<= 0)
    	{
    		lv_format=new StringBuffer("#,##0.000000");
    	}
    	if((amt_sign.intValue() == -1 && RepConstantsCommon.GL_TYPE_L.equals(gl_type)) || (amt_sign.intValue() == -1 && RepConstantsCommon.GL_TYPE_S.equals(gl_type))
        		|| (amt_sign.intValue() == -1 && RepConstantsCommon.GL_TYPE_R.equals(gl_type)))
    	{
    		lv_format = lv_format1.append(" ;(").append(lv_format).append(")");
    	}
    	else if((amt_sign.intValue() == 1 && RepConstantsCommon.GL_TYPE_A.equals(gl_type))
    		    || (amt_sign.intValue() == 1 && RepConstantsCommon.GL_TYPE_E.equals(gl_type)))
	    {
    		lv_format = new StringBuffer("(").append(lv_format).append(")");
	    }
	    else
	    {
	    	lv_format = lv_format1.append(" ;").append(lv_format);
    	}
    	return lv_format.toString();
    }
    //end fares
    
   /**
    * Method that returns the pattern of the parameter date
    * 
    * @param date value
    * @throws BaseException Exception if error occurred.
    */
   private static String retFcrDatePattern(String date) throws BaseException
   {
	String DTPattern1 = "\\d\\d-\\w\\w\\w-\\d\\d";// Ex: dd-MMM-yy
	String DTPattern2 = "\\w\\w\\w-\\d\\d";// Ex: MMM-yy
	if(date.matches(DTPattern1))
	{
	    return "dd-MMM-yy";
	}
	else if(date.matches(DTPattern2))
	{
	    return "MMM-yy";
	}
	else
	{
	    return "dd-MM-yyyy";
	}
   }
   
 /**
 * Method that returns a fomrated String based on the parameters format ,group separator and decimal separator
 * 
 * @param date value
 * @throws BaseException Exception if error occurred.
 */

public static String string(Object value, String format, ReportParamsCO paramsCO) throws BaseException
{
SimpleDateFormat dateFormat = new SimpleDateFormat();
HashMap<String,Object> theNbFormat=paramsCO.getUserNbFormats();
String groupSepa = StringUtil.nullEmptyToValue(theNbFormat.get("groupSepa"), ",");
String decimalSepa = StringUtil.nullEmptyToValue(theNbFormat.get("decimalSepa"), ".");
    
if((value == null) || (format == null))
{
    if(format.indexOf(";") == -1)
    {
	return "";
    }
    else
    {
	return NumberUtil.multiFormatNumber((Number) value, format,groupSepa, decimalSepa);
    }
}
else
{  
    if(value instanceof String)
    {
	value.toString();
	return String.format(format);
    }
    else if(value instanceof Number)
    {
	String retVal = NumberUtil.multiFormatNumber((Number) value, format,groupSepa, decimalSepa);
	return retVal;
    }
    else if(value instanceof Date)
    {
	dateFormat.applyPattern(format);
	return dateFormat.format(value);
    }
    else
    {
	log.warning("\n\n\n string method unrecognized data type for value argument =" + value
		+ "  returning null \n\n\n");
	return null;
    }
}
}

	/**
	 * returns company description by company parameter not by session
	 * 
	 * @param paramsCO
	 * @param compCode
	 * @return
	 * @throws BaseException
	 */
	public static String f_get_company_desc_by_comp_code(
			ReportParamsCO paramsCO, BigDecimal compCode) throws BaseException {
		if (paramsCO.getNoData() || NumberUtil.isEmptyDecimal(compCode)) {
			return "";
		} else {
			CommonLibBO commonLibBO = (CommonLibBO) ApplicationContextProvider
					.getApplicationContext().getBean("commonLibBO");
			COMPANIESVO compVO = new COMPANIESVO();
			compVO.setCOMP_CODE(compCode);
			COMPANIESVO retCompVO = commonLibBO.returnCompany(compVO);
			if (ConstantsCommon.LANGUAGE_ARABIC.equals(paramsCO
					.getRepLanguage())
					&& !StringUtil.nullToEmpty(retCompVO.getBRIEF_DESC_ARAB())
							.isEmpty()) {
				return retCompVO.getBRIEF_DESC_ARAB();
			} else {
				return retCompVO.getBRIEF_DESC_ENG();
			}
		}
	}

	/**
	 * returns branch description by company/branch parameters not by session
	 * 
	 * @param paramsCO
	 * @param compCode
	 * @param branchCode
	 * @return
	 * @throws BaseException
	 */
	public static String f_get_branch_desc_by_comp_branch_code(
			ReportParamsCO paramsCO, BigDecimal compCode, BigDecimal branchCode)
			throws BaseException {
		if (paramsCO.getNoData() || NumberUtil.isEmptyDecimal(compCode)
				|| NumberUtil.isEmptyDecimal(branchCode)) {
			return "";
		} else {
			// return the branch desc following the report language
			CommonLibBO commonLibBO = (CommonLibBO) ApplicationContextProvider
					.getApplicationContext().getBean("commonLibBO");
			BRANCHESVOKey branchKey = new BRANCHESVOKey();
			branchKey.setCOMP_CODE(compCode);
			branchKey.setBRANCH_CODE(branchCode);
			BRANCHESVO branchVO = commonLibBO.returnBranch(branchKey);
			if (ConstantsCommon.LANGUAGE_ARABIC.equals(paramsCO
					.getRepLanguage())) {
				if ("".equals(StringUtil.nullToEmpty(branchVO
						.getBRIEF_DESC_ARAB()))) {
					return branchVO.getBRIEF_DESC_ENG();
				} else {
					return branchVO.getBRIEF_DESC_ARAB();
				}
			} else {
				return branchVO.getBRIEF_DESC_ENG();
			}
		}
	}
	
    /**
     * 
     * @param date
     * @param paramsCO
     * @return
     */
    public static BigDecimal daynumber(Date date, ReportParamsCO paramsCO)
    {
	Calendar cal = Calendar.getInstance();
	Date dt = date;
	cal.setTime(dt);
	return BigDecimal.valueOf(cal.get(Calendar.DAY_OF_WEEK));
    }


    /**
     * Method used to encrypt or decrypt card number according to provided parameters
     * @param encIndicator ENcryption / Decryption indicator E=Encrypt, D=Decrypt
     * @param cardNo Card No to decrypt or Encrypt
     * @param cifNo cifNo which is card is related to
     * @param maskCardPos The index of char where the masking will start
     * @param maskCardLength The total characters to be masked.
     * @param maskChar The char that will be replaced instead of the characters in card number
     * @return Encrypted / Decrypted value of Card
     * @throws BaseException
     */
	public static String f_encrypt_decrypt_report(String encIndicator,String cardNo, BigDecimal cifNo, BigDecimal maskCardPos,
		    BigDecimal maskCardLength, String maskChar, ReportParamsCO paramsCO) throws BaseException {
		if (paramsCO.getNoData()) {
			return "";
		} else {
			BigDecimal compCode =paramsCO.getCompanyCode();
			ReportsCommonBO repotsCommon = (ReportsCommonBO) ApplicationContextProvider.getApplicationContext().getBean("reportsCommonBO");
			return repotsCommon.returnF_ENCRYPT_DECRYPT_CARD(encIndicator, cardNo, compCode,  cifNo,  maskCardPos, maskCardLength,  maskChar);
		}
	}
}
