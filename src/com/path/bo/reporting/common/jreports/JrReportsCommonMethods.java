package com.path.bo.reporting.common.jreports;

//import java.math.BigDecimal;
//import java.net.URLEncoder;
//import java.util.Calendar;
//import java.util.Date;
//import java.util.HashMap;

//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;

//import com.path.lib.log.Log;
//import com.path.vo.common.path.reports.common.ConstantsCommon;
//import com.path.vo.common.path.reports.common.bo.defualt.dictionary.DictionaryMgrBO;
//import com.path.vo.common.path.reports.common.common.bo.AuditBO;
//import com.path.vo.common.path.reports.common.common.vo.AuditVO;

//import com.path.vo.common.path.reports.common.csm.bo.common.CommonBO;
//import com.path.vo.common.path.reports.common.csm.bo.common.UoFindInfoObject;
//import com.path.lib.common.exception.BOException;
import java.util.Locale;

import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.log.Log;
import com.path.vo.reporting.ReportParamsCO;

//import com.path.vo.common.path.reports.common.lib.common.exception.PathBOException;
//import com.path.vo.common.path.reports.common.lib.common.exception.PathException;
//import com.path.vo.common.path.reports.common.lib.util.DateUtil;
//import com.path.vo.common.path.reports.common.lib.util.NumberUtil;
//import com.path.vo.common.path.reports.common.lib.util.StringUtil;

//import com.path.vo.common.path.reports.common.reports.vo.searchcriteria.ReportSC;
//import com.path.vo.common.path.reports.common.vo.common.CurrencyVO;
//import com.path.vo.common.path.reports.common.vo.common.UsrLogInInfoVO;
//import com.path.vo.common.path.reports.common.vo.common.searchcriteria.CurrencySC;


public final class JrReportsCommonMethods
{
// private static com.path.lib.log.Log log = Log.getInstance();
    static Log log = Log.getInstance();
    private JrReportsCommonMethods() 
    {
	log.debug("This Class Should not be Instantiated");
    }
/**
 * @todo NAthalie
 * @param field
 * @param sessCO
 * @return
 * @throws BaseException
 */
public static String getGlobalVar(String ffield, ReportParamsCO sessCO)throws BaseException
{
	  String value = "";
	  String field = ffield.toUpperCase(Locale.ENGLISH);
	  if ("BASE_CY_NAME_ENG".equals(field))
	  {
		  value = sessCO.getBaseCurrencyName(); //getBaseCyNameEng(); //gs_base_cy_name_eng Modified by Nathalie
	  }
	  if ("COMPANY_NAME_ENG".equals(field))
	  {
		    value = sessCO.getCompanyName(); //Modified by Nathalie
	  }
	  if ("COMPANY_NAME_ARAB".equals(field))
	  {
		    value = sessCO.getCompanyArabName(); //Modified by Nathalie
	  }
	  if ("USER_ID".equals(field))
	  {
		    value = sessCO.getUserName(); //getUsrId(); Modified by Nathalie 
	  }
	  if ("COMPANY_CODE".equals(field))
	  {
		  value =  sessCO.getCompanyCode().toString(); //Modified by Nathalie
	  }
	  if ("BRANCH_CODE".equals(field))
	  {
		  value =  sessCO.getBranchCode().toString();
	  }
	  if ("RUNNING_DATE".equals(field))
	  {
		  value =  DateUtil.format(sessCO.getRunningDateRET(),"dd/MM/yyyy");   // getSystemDate(),"dd/MM/yyyy"); Modified by Nathalie 
	  }
	   
	  return StringUtil.nullToEmpty(value);
}
}
/**
 * method that returns 1 if the current database has the same type as the passed parameter
 * @param dbParam: parameter to check whether current database is correct (oracle, sybase)
 * @return :1,0
 * @throws BaseException
 */

//commnented by Nathalie 
/*  public static int getDBMSType(String dbParam)
{
  if (dbParam.equals("oracle"))
  {
    return Constants.CURR_DBMS_ORACLE;
  }
  else if (dbParam.equals("sybase"))
  {
    return Constants.CURR_DBMS_SYBASE;
  }
  else
  {
    return 0;
  }
}*/
//Commented by Nathalie as it is not needed in the reports
/*
public static String getRequestCompCode(HttpServletRequest request)
  throws BaseException
{
  return "" + request.getSession().getAttribute("compCode");
}

public static String getRequestBraCode(HttpServletRequest request)
  throws BaseException
{
  return "" + request.getSession().getAttribute("branchCode");
}

public static SessionCO getUsrLoginInfo(HttpServletRequest request)
  throws BaseException
{
  return (SessionCO)request.getSession().getAttribute("loginInfo");
}

public static SessionCO getUsrLoginInfo(HttpSession session)
{
  return (SessionCO)session.getAttribute("loginInfo");
} */

//Commented by Nathalie 
/* public static void prepareTopBar(HttpServletRequest request, String actMethod)
{
  prepareTopBar(request, actMethod, null);
}

public static void prepareTopBar(HttpServletRequest request, String actMethod, ScreenParams screenParams)
{
	  String actionType      = "display";
	  String displayMsg      = "Display";
	  String cssStyle        = "topBar_disp";
	  String cssTextStyle    = "topBar_dispText";
	  String displayImg      = "dispSqr.gif";
	  String displayBarBgImg = "dispBarBg.gif";
	  if (actMethod != null)
	  {
		  if (actMethod.contains("add") || actMethod.equals("saveNew"))
		  {
			  actionType      = "saveNew";
			  displayMsg      = "Save";
			  cssStyle        = "topBar_add";
			  cssTextStyle    = "topBar_addText";
			  displayImg      = "saveSqr.gif";
			  displayBarBgImg = "saveBarBg.gif";
		  }
		  else if (actMethod.contains("mod") || actMethod.equals("update") || actMethod.equals("edit"))
		  {
			  actionType      = "update";
			  displayMsg      = "Update";
			  cssStyle        = "topBar_upd";
			  cssTextStyle    = "topBar_updText";
			  displayImg      = "updSqr.gif";
			  displayBarBgImg = "updBarBg.gif";
		  }
	  }

	  StringBuffer topBarSb = new StringBuffer();
	  topBarSb.append("<td class=\"" + cssStyle + "\" id=\"topBar\">")
	  .append("<img class=\"" + cssStyle + "Bg\" src=\"/" + Constants.currCntxtRoot + "/path/images/" + displayBarBgImg + "\" />")
	  .append("<div style=\"width:100%\" >").append("<table border=\"0\">").append("<tr>")
	  .append("<td nowrap=\"nowrap\" class=\"" + cssTextStyle + "\">&nbsp;&nbsp;")
	  .append("<img src=\"/" + Constants.currCntxtRoot + "/path/images/" + displayImg + "\" />&nbsp;&nbsp;").append(displayMsg)
	  .append("</td><td style=\"width:100%\"></td>");
	  
	  
	  if(screenParams != null)
	  {
		  AuditBO auditBO = (AuditBO) ContextReference.getBean("auditBO");
		  AuditVO auditVO = new AuditVO();
		  auditVO.setOptReference(screenParams.getOptReference());
		  try
		  {
			  String trxNo = null,
			  		 progRef = null,
			  		 url = null;
			  UsrLogInInfoVO usrLogInInfoVO = CommonMethods.getUsrLoginInfo(request);
			  int enableAudit = auditBO.auditControls(auditVO, usrLogInInfoVO);	
			  if(enableAudit == 1)
			  {
				  trxNo = auditBO.buildAuditKey(screenParams.getAuditVO(), usrLogInInfoVO);
				  progRef = screenParams.getOptReference();
			  }
			  
			  if(actMethod != null && (!actMethod.contains("add") || !actMethod.equals("saveNew")) && ConstantsCommon.IS_AUDIT_ENABLED.equals("1"))
			  {
				  url = URLEncoder.encode("/csm/path/conf/common/auditreport/AuditReportMaintAction.do?actionType=initialize&trxNo="+trxNo+"&progRef="+progRef, "UTF-8");
				  topBarSb.append("<td align=\"right\" style=\"cursor:pointer\"><img src=\"/" + Constants.currCntxtRoot + "/path/images/audit_img.jpg\" onclick=\"openAuditReport('"+url+"')\"></td>");
			  }
			  
			  if(PbFunc.mid(trxNo, 9).equals(""))
			  {
				  throw new BaseException(297);
			  }
			  else
			  {
				  String gvReference = request.getParameter("gv_reference");				  
				  url = URLEncoder.encode("/csm/path/conf/common/smart/SmartMaintAction.do?actionType=initialize&trxNo="+trxNo+gvReference+"&progRef="+gvReference, "UTF-8");
				  topBarSb.append("<td align=\"right\" style=\"cursor:pointer\"><img src=\"/" + Constants.currCntxtRoot + "/path/images/smart_img.jpg\" onclick=\"openSmart('"+url+"')\"></td>");
			  }
		  }
		  catch(Exception ex)
		  {
			  log.debug("Error in prepareTopBar while managing AuditReport and Smart");
		  }
	  }
	  
	  topBarSb.append("<td align=\"right\"><img src=\"/" + Constants.currCntxtRoot + "/path/images/help.gif\"></td>").append("</tr></table></div></td>");
	  request.setAttribute("actionType", actionType);
	  request.setAttribute("topBar", topBarSb.toString());
	  try
	  {
		  request.setAttribute("hideArabic", (CommonMethods.getUsrLoginInfo(request).getLanguage().equals("1")) ? "true" : "false");
	  }
	  catch (BaseException e)
	  {
		  log.error(e, "[Error]-[App:CSM]-[Action: CommonMethods]-[Function: prepareTopBar]");
	  }
}*/

/**
 * method used to return the status for grid filtering
 * @param iv_crud parameter to check which menu is selected
 * R = Maintenance, Q= Query, S=Suspend,A= Re-instantiate,P= Approve
 * @return the correct Status: X= anything, A = Active, I=Initiated,S=Suspended
 */
//Commented by Nathalie as it is not needed in the reports
/*
public static String getFilterStatus(String iv_crud)
{
  String theStatus = "A";
  if (iv_crud != null)
  {
    if (iv_crud.equals("R") || iv_crud.equals("Q"))
    {
      theStatus = "X";
    }
    else if (iv_crud.equals("P"))
    {
      theStatus = "I";
    }
    else if (iv_crud.equals("S"))
    {
      theStatus = "A";
    }
    else if (iv_crud.equals("A"))
    {
      theStatus = "S";
    }
  }

  return theStatus;
}*/

/**
 * return the system date set in the session if not null else return the current date.
 * @param request
 * @return
 */
//Commented by Nathalie as it is not needed in the reports
/*
public static Date getSystemDate(HttpServletRequest request)
  throws BaseException
{
  Date currSystemDate = getUsrLoginInfo(request).getRunningDateRET(); //.getSystemDate(); Modified by Nathalie on 02/01/2013
  if (currSystemDate == null)
  {
    currSystemDate = new Date();
  }

  return currSystemDate;
}*/

/**
 * returns the loged in user code (login name)
 * @param request
 * @return
 */
//Commented by Nathalie as it is not needed in the reports
/*
public static String getRequestUsrCode(HttpServletRequest request)
{
  return "" + request.getSession().getAttribute("userCode");
}*/

//Commented by Nathalie as it is not needed in the reports
/*  public static void setMandatoryLanguages(HttpServletRequest request)
{
  boolean engMandatory = false;
  try
  {
    engMandatory = getUsrLoginInfo(request).getEngMandatory();
  }
  catch (BaseException e)
  {
    e.printStackTrace();
    log.error(e, "[Error]-[Action: CommonMethods]-[Function: setMandatoryLanguages]");
  }

  request.setAttribute("engMandatory", engMandatory);

  //int lang = Integer.parseInt(CommonMethods.getUsrLoginInfo(request).getLanguage());
  int lang             = 1;
  boolean arbMandatory = (lang != 1) ? true : false;
  request.setAttribute("arbMandatory", arbMandatory);
}*/

/**
  *
  * @param status
  * @param request
  */
//Commented by Nathalie 
/*
public static void setCsmStatusStyle(String status, HttpServletRequest request)
{
  if (status == null)
  {
    request.setAttribute("csmStyle", "csmStatus_I");
    request.setAttribute("csmStatus", Constants.INACTIVE_STS);
  }
  else
  {
    if (status.equals(Constants.ACTIVE_STS)) //Active
    {
      request.setAttribute("csmStyle", "csmStatus_A");
    }
    else if (status.equals(Constants.INACTIVE_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_I");
    }
    else if (status.equals(Constants.SUSPENDED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_S");
    }
    else if (status.equals(Constants.DELETED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_D");
    }
    else if (status.equals(Constants.APPROVED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_P");
    }
    else if (status.equals(Constants.RESET))
    {
      request.setAttribute("csmStyle", "csmStatus_E");
    }
    else if (status.equals(Constants.TO_BE_SUSPENDED_LINK_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_T");
    }
    else if (status.equals(Constants.TO_BE_REACTIVATED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_R");
    }
    else if (status.equals(Constants.TO_BE_CANCELLED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_N");
    }
    else if (status.equals(Constants.CANCELLED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_C");
    }
    else if (status.equals(Constants.REACTIVATED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_P");
    }
    else if (status.equals(Constants.TO_BE_SUSPENDED_LINK_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_T");
    }

    request.setAttribute("csmStatus", status);
  }
}
*/

//Commented by Nathalie 
/*public static void setCsmMemoDetStatusStyle(String status, HttpServletRequest request)
{
  if (status == null)
  {
    request.setAttribute("csmStyle", "csmStatus_A");
    request.setAttribute("csmStatus", Constants.ACTIVE_STS);
  }
  else
  {
    if (status.equals(Constants.ACTIVE_STS)) //Active
    {
      request.setAttribute("csmStyle", "csmStatus_A");
    }
    else if (status.equals(Constants.APPROVED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_P");
    }
    else if (status.equals(Constants.CANCELLED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_C");
    }
    else if (status.equals(Constants.DELETED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_D");
    }
    else if (status.equals(Constants.SUSPENDED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_S");
    }
    else if (status.equals(Constants.TO_BE_CANCELLED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_N");
    }
    else if (status.equals(Constants.TO_BE_REACTIVATED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_R");
    }
    else if (status.equals(Constants.TO_BE_SUSPENDED_STS))
    {
      request.setAttribute("csmStyle", "csmStatus_U");
    }

    request.setAttribute("csmStatus", status);
  }
}
*/
//Commented by Nathalie 
/*
public static void setCsmPinStatusStyle(String pinStatus, HttpServletRequest request)
{
  if (pinStatus == null)
  {
    request.setAttribute("csmPinStyle", "csmPinStatus_A");
    request.setAttribute("csmPinStatus", Constants.ACTIVE_STS);
  }
  else
  {
    if (pinStatus.equals(Constants.ACTIVE_STS))
    {
      request.setAttribute("csmPinStyle", "csmPinStatus_A");
    }
    else if (pinStatus.equals(Constants.SUSPENDED_STS))
    {
      request.setAttribute("csmPinStyle", "csmPinStatus_S");
    }
    else if (pinStatus.equals(Constants.LOGGED_STS))
    {
      request.setAttribute("csmPinStyle", "csmPinStatus_L");
    }

    request.setAttribute("csmPinStatus", pinStatus);
  }
}
*/

//Commented by Nathalie

/*
public static String setStatusDescription(String status)
{
  String statusDesc = "";
  if (status.equals(Constants.OPEN_STS))
    statusDesc = "Open";
  else if (status.equals(Constants.OCCUP_STS))
    statusDesc = "Occupied";
  else if (status.equals(Constants.CANCEL_STS))
    statusDesc = "Cancelled";
  else if (status.equals(Constants.DELETED_STS))
    statusDesc = "Deleted";
  else if (status.equals(Constants.EXPIRED_STS))
    statusDesc = "Expired";
  else if (status.equals(Constants.TO_BE_BLOCKED_STS))
    statusDesc = "To  Be Blocked";
  else if (status.equals(Constants.BLOCKED_STS))
    statusDesc = "Blocked";
  else if (status.equals(Constants.TO_BE_ABANDONED_STS))
    statusDesc = "To Be Abandoned";
  else if (status.equals(Constants.ABANDONED_STS))
    statusDesc = "Abandoned";
  else if (status.equals(Constants.TO_BE_CLOSED_STS))
    statusDesc = "To Be Closed";
  else if (status.equals(Constants.CLOSED_STS))
    statusDesc = "Closed";
  else if (status.equals(Constants.CANCEL_STS))
    statusDesc = "Amended";
  else if (status.equals(Constants.TO_BE_CANCEL_STS))
    statusDesc = "To Be Canceled";
  else if (status.equals(Constants.TO_BE_RENEWED_STS))
    statusDesc = "To Be Renewed";

  return statusDesc;
}
*/

/**
 * equivalent to PB f_soundex, it returns the soundex of a string according to a predefined set of rules to match the Arabic names.
 * ex: remove AL, EL from the beginning of the names; Remove the double consonants
 * @param as_value : The name that we want to get its soundex
 * @return: the Soundex string
 */

//Commented by Nathalie as it is not needed in the reports
/*
public static String getSoundex(String as_value) 
	{
	 	  // This function gets the equivalent of a string by checking a list of criteria
	 	  // It is used in the black list search
	 	  String ls_char,ls_char3,ls_string,ls_result;
	 	  char charToCopy ;
	 	  int i;

	 	  ls_result = ""; 
	 	  ls_string = as_value.trim(); 
	 	  // Remove spaces from the beginning and the end
	 	  ls_string = ls_string.toUpperCase(); 
	 	  // Transforms it to Upper case
	 	  ls_string = ls_string.replaceAll("MINOR", ""); 
	 	  // Remove the word MINOR if exists
	 	  ls_char = ls_string.substring(0, 1) ; 
	 	  // test the first character
	 	   if (ls_char.equals("A") || ls_char.equals("E") )  
	 	   {
	 	        ls_char3 = ls_string.substring( 0  , 3   ); 
	 	        // Removes "AL ", "EL " "AL-", "EL-" from the beginning of the world
	 	         if (ls_char3.equals("AL ") || ls_char3.equals("EL ") || ls_char3.equals("AL-") || ls_char3.equals("EL-") )  
	 	         {
	 	              ls_string = ls_string.substring( 3  ,ls_string.length()   );  
	 	         }
	 	       
	 	   }

	 	   if ( ls_string ==null)  
	 	   {
	 	        return  ""  ; 
	 	   }

	 	  ls_string = ls_string.replaceAll(" AL-", "1" ); 
	 	  ls_string = ls_string.replaceAll(" EL-"  , "1"   ); 
	 	  ls_string = ls_string.replaceAll(" AL "  , "1"   ); 
	 	  ls_string = ls_string.replaceAll(" EL "  , "1"   ); 
	 	  ls_string = ls_string.replaceAll("SH"  , "0"   ); 
	 	  ls_string = ls_string.replaceAll( "CH"  , "0"   ); 
	 	  ls_string = ls_string.replaceAll("PH"  , "F"   ); 
	 	  ls_string = ls_string.replaceAll("GH"  , "G"   ); 
	 	  // Marwan
	 	  ls_string = ls_string.replaceAll("K"  , "C"   ); 
	 	  ls_string = ls_string.replaceAll("Q"  , "C"   ); 
	 	  ls_string = ls_string.replaceAll("J"  , "G"   ); 
	 	  ls_string = ls_string.replaceAll("T"  , "D"   ); 
	 	  ls_string = ls_string.replaceAll("V"  , "F"   ); 
	 	  // Remove the double consonance
	 	  ls_string = ls_string.replaceAll("BB"  , "B"   ); 
	 	  ls_string = ls_string.replaceAll("CC"  , "C"   ); 
	 	  ls_string = ls_string.replaceAll("DD"  , "D"   ); 
	 	  ls_string = ls_string.replaceAll("FF"  , "F"   ); 
	 	  ls_string = ls_string.replaceAll("GG"  , "G"   ); 
	 	  ls_string = ls_string.replaceAll("HH"  , "H"   ); 
	 	  ls_string = ls_string.replaceAll("JJ"  , "J"   ); 
	 	  ls_string = ls_string.replaceAll("KK"  , "K"   ); 
	 	  ls_string = ls_string.replaceAll("LL"  , "L"   ); 
	 	  ls_string = ls_string.replaceAll("MM"  , "M"   ); 
	 	  ls_string = ls_string.replaceAll("NN"  , "N"   ); 
	 	  ls_string = ls_string.replaceAll("PP"  , "P"   ); 
	 	  ls_string = ls_string.replaceAll("QQ"  , "Q"   ); 
	 	  ls_string = ls_string.replaceAll("RR"  , "R"   ); 
	 	  ls_string = ls_string.replaceAll("SS"  , "S"   ); 
	 	  ls_string = ls_string.replaceAll("TT"  , "T"   ); 
	 	  ls_string = ls_string.replaceAll("VV"  , "V"   ); 
	 	  ls_string = ls_string.replaceAll("XX"  , "X"   ); 
	 	  ls_string = ls_string.replaceAll("ZZ"  , "Z"   ); 
	 	
	 	  for(i= 0  ; i< ls_string.length()  ; i++)
	 	  {
	 		     charToCopy = ls_string.charAt(i);//ls_string.substring( i  , 1   ); 
	 	         if ( charToCopy == 'B' || charToCopy == 'C' || charToCopy == 'D' || charToCopy == 'F' || charToCopy == 'G' || charToCopy == 'H' || charToCopy == 'L' || charToCopy == 'M' || charToCopy == 'N' || charToCopy == 'P' || charToCopy == 'R' || charToCopy == 'S' || charToCopy == 'W' || charToCopy == 'X'
	 	        	 	|| charToCopy == 'Z' || charToCopy == '0' )  
	 	         {
	 	              ls_result += charToCopy;  
	 	         }
	 	       
	 	  }
	 	  return  ( ls_result  )  ; 
	}
	*/
/**
 * corresponds to BP function f_currency_mask_dr_cr_basecy but returns the formated value directly and not the format
 * @param valueToFormat the value to be formated
 * @param cyDec number of digits after decimal
 * @param dict dictionary for translation
 * @return
 */
//Commented by Nathalie as it is not needed in the reports
/*
public static String formatCurMaskDrCrBaseCy(Number valueToFormat,Integer cyDec, DictionaryMgrBO dict)throws BaseException
{
	  String theFormat = "#,##0.";
	  String nbDecimalZeros = PbFunc.fill("0",cyDec);
	  theFormat += nbDecimalZeros;
	  String dr = "DR" , cr = "CR";
	  if(dict != null)
	  {
		  dr = dict.translate("DR", "psSuite"); 
		  cr = dict.translate("CR", "psSuite"); 
	  }
	  return PbFunc.string(valueToFormat, theFormat+" "+dr+";"+theFormat+" "+cr+";"+theFormat);
}
*/


/**
 * corresponds to BP function f_currency_mask_dr_cr or f_currency_mask_dr_cr_basecy 
 * which returns the Format to be applied according to currency or the base currency
 * @param cyDec
 * @param dict
 * @return
 * @throws BaseException
 */
/* public static String formatCurMaskDrCr(Number cyDec, DictionaryMgrBO dict)throws BaseException
{
	  String theFormat = "#,##0.";
	  String nbDecimalZeros = PbFunc.fill("0",new Integer (cyDec.intValue()));
	  theFormat += nbDecimalZeros;
	  String dr = "DR" , cr = "CR";
	  if(dict != null)
	  {
		  dr = dict.translate("DR", "psSuite"); 
		  cr = dict.translate("CR", "psSuite"); 
	  }
	  theFormat = theFormat+" "+dr+";"+theFormat+" "+cr+";"+theFormat;
	  return theFormat;
}
*/
/**
 * corresponds to BP function f_currency_mask_select which returns the Format to be applied on the selected currency
 * @param cyDec
 * @param dict
 * @return
 * @throws BaseException
 */
/*public static String formatCurMaskSelect(Number cyCode, Integer compCode, DictionaryMgrBO dict)throws BaseException
{
	  CommonBO commonBO = (CommonBO)ContextReference.getBean("csmCommonBO");
	  CurrencySC currencySC = new CurrencySC();
	  currencySC.setCurrency_code(cyCode.toString());
	  currencySC.setCompCode(compCode.toString());
	  CurrencyVO theVO = commonBO.getCurrencyDecPoints(currencySC);
	  String format = "#,##0";
	  
	  if (theVO != null && theVO.getDecPoint().intValue() > 0)
			 format += "." + PbFunc.fill("0",theVO.getDecPoint());
	  
	  return format + ";" + format; 
}*/

/**
 * corresponds to BP function f_currency_mask but returns the formated value directly and not the format
 * @param valueToFormat the value to be formated
 * @param cyDec number of digits after decimal
 * @param dict dictionary for translation
 * @return
 */

//Commented by Nathalie as it is not needed in the reports
/*
public static String formatCurMask(Number valueToFormat,Integer cyDec)throws BaseException
{
	  String theFormat = "#,##0.";
	  String nbDecimalZeros = PbFunc.fill("0",cyDec);
	  theFormat += nbDecimalZeros;
	  return PbFunc.string(valueToFormat, theFormat);
}*/

/**
 * corresponds to BP function f_currency_mask_def returns the format according to gv_cy_dec
 * @param cyDec number of digits after decimal
 * @return
 */
/* public static String formatCurMaskDef(Integer cyDec)throws BaseException
{
	  String theFormat = "#,##0";
	  String numOfZerosToFill = "";
	  if (cyDec != null && cyDec > 0)
		  numOfZerosToFill = PbFunc.fill("0",cyDec) ;
	  if (!numOfZerosToFill.isEmpty())
		  theFormat += ".";
	  theFormat += numOfZerosToFill + " ;(" + theFormat + numOfZerosToFill  + ")";
	  return theFormat;
}*/
/**
 * corresponds to BP function f_currency_mask_def_rep returns the format according to gv_cy_dec
 * @param cyDec number of digits after decimal
 * @return
 */
/* public static String formatCurMaskDefRep(Integer cyDec)throws BaseException
{
	  String theFormat = "#,##0";
	  String numOfZerosToFill = "";
	  if (cyDec != null && cyDec > 0)
		  numOfZerosToFill = PbFunc.fill("0",cyDec) ;
	  if (!numOfZerosToFill.isEmpty())
		  theFormat += ".";
	  theFormat += numOfZerosToFill + " ;" + theFormat + numOfZerosToFill  ;
	  return theFormat;
}*/
/**
 * corresponds to BP function f_currency_mask, returns the format 
 * @param valueToFormat the value to be formated
 * @param cyDec number of digits after decimal
 * @param dict dictionary for translation
 * @return
 */
/*public static String formatCurrencyMask(Number cyDec, DictionaryMgrBO dict)throws BaseException
{
	  String theFormat = "#,##0";
	  if(cyDec != null && cyDec.intValue() > 0)
		  theFormat += "." + PbFunc.fill("0",cyDec.intValue());
	  return theFormat ;
}*/
/**
 * corresponds to the methods f_currency_mask_nosign and f_currency_mask_rep
 * @param cyDec
 * @param dict
 * @return
 * @throws BaseException
 */
/* public static String formatCurrencyMaskRep(Number cyDec, DictionaryMgrBO dict)throws BaseException
{
	  String theFormat = "#,##0";
	  if(cyDec != null && cyDec.intValue() > 0)
		  theFormat += "." + PbFunc.fill("0",cyDec.intValue());
	  return theFormat + ";" + theFormat ;
}
*/
/**
 * corresponds to the PB function f_base_cy_details that retrieves information of the base currency
 * @param cyDec
 * @param dict
 * @return
 * @throws BaseException
 */
/*public static String getBaseCurrencyDetails(String field, SessionCO sessCO)throws BaseException
{
	  String value = "";
	  field = field.toUpperCase();
	  if ("CODE".equals(field))
	  {
		  value =  PbFunc.string(sessCO.getBaseCurrencyCode()); //getCurrency()); Modified by Nathalie
	  }
	  if ("BRIEF_DESC_ENG".equals(field))
	  {
		    value = sessCO.getBaseCurrencyName(); //getBaseCyNameEng(); Modified by Nathalie 
	  }
	  if ("BRIEF_DESC_ARAB".equals(field))
	  {
		    value =sessCO.getBaseCurrencyName(); //getBaseCyNameEng(); //;gs_cy_name_arab Modified by Nathalie
	  }
	  if ("LONG_DESC_ARAB".equals(field))
	  {
		    value = sessCO.getBaseCurrencyName(); // getBaseCyNameEng();// gs_cy_name_arab Modified by Nathalie
	  }
	  if ("DECIMALS".equals(field))
	  {
		    value = PbFunc.string(sessCO.getBaseCurrDecPoint()); //.getCyDec()); Modified by Nathalie 
	  }
	  if ("UNIT".equals(field))
	  {
		    value = PbFunc.string(sessCO.getCyUnit()); 
	  }
	   
	  return StringUtil.nullToEmpty(value);
}
*/
/**
 * f_numbering_text
 * returns the string separated as a list of numbered string
 * @param text
 * @param separator
 * @return
 * @throws BaseException
 */

//Commented by Nathalie as it is not needed in the reports
/*
public static String numberingText(String text,String separator)throws BaseException
{
	  String result = "";
	  if(text == null || "".equals(text))
		  return "";
	  String[] textArr = text.split(separator);
	  for(int i=1; i<=textArr.length; i++)
	  {
		  if(i > 1)
			  result += " ";
		  result += i + "-" + textArr[i-1];
	  }
	  return result;
}*/
/**
 * equivalent to PowerBuilder w_maintain_parent.wf_checkstatus
 * it checks if the new status assigned to a record is acceptable in regards to its previous status
 * @param actionType :the action that is changing the status of the record delete, approve, reverse
 * @param status: the original status of the record
 * @param menuReference: the screen reference in case special conditions should be added for this screen
 * @throws BaseException the 
 */
//Commented by Nathalie 
/*
public static void wf_checkStatus(String actionType, String status, String menuReference) throws BaseException
{
	  if (actionType != null && status != null)
	  {
		  actionType = actionType.toUpperCase();
		  if (actionType.equals("DELETE"))
		  {
				if (!status.equals(Constants.ACTIVE_STS)) 
				{
		  						
		  			throw new PathBOException (185); 
				}
		  }
		  else if (actionType.equals("APPROVE"))
		  {
				if (!status.equals(Constants.ACTIVE_STS)) 
				{
					throw new PathBOException (186); 
				}
		  }
		  else if (actionType.equals("REVERSE"))
		  {
	  			if (!status.equals(Constants.APPROVED_STS)) 
	  			{
	  				throw new PathBOException (187); 
	  			}
	       }
	  }	  
}
*/

/**
 * corresponds to the method f_format_cheque, returns format for cheques
 * @param uInfoVO
 * @return
 */
/* public static String formatCheque(Integer compCode, Integer branchCode, SessionCO sessCO)throws BaseException
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  String format = "";
	  HashMap<String,Object> h = new HashMap<String,Object>(); //Modified by Nathalie added <String,Object>
	  HashMap<String,Object> hm = new HashMap<String,Object>();//Modified by Nathalie added <String,Object>
	  h.put("comp_code", new Integer(compCode.intValue()));
	  h.put("branch_code", new Integer(branchCode.intValue()));
	  hm = (HashMap)commonBO.getCtsControl(h);
	  BigDecimal bb = (BigDecimal )hm.get("cheque_digits_num");
	  Integer digNum = new Integer(bb.intValue());
	  if (digNum == null)
		  digNum = new Integer(10);
	  
	  format = PbFunc.fill("0", digNum ) +";" + PbFunc.fill("0", digNum );
	  return format ;
}*/


/**
 * corresponds to the PB function f_get_country_amt , gets the country amount (balance) for use with availment report
 * @param cifNo
 * @param usrLoginInfo
 * @return
 */
/*public static double getCountryAmount(Integer cifNo, SessionCO sessCO)throws BaseException
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  double couAmt = 0;
	  ReportSC reportSC = new ReportSC();
	  reportSC.setComp_code(sessCO.getCompanyCode());
	  reportSC.setCurrency(sessCO.getBaseCurrencyCode());
	  reportSC.setCif_no(cifNo);
	  return commonBO.getCountryAmount(reportSC);
}
*/
/**
 * corresponds to the method f_get_currency
 * @param currency
 * @param usrLoginInfo
 * @return
 * @throws BaseException
 */
/*  public static CurrencyVO getCurrency(BigDecimal currency, SessionCO sessCO)throws BaseException //Modified by Nathalie the type from integer to BigDecimal
{
	  com.path.bo.common.CommonBO commonBO = (com.path.bo.common.CommonBO) ContextReference.getBean("commonBO");
	  SessionCO uoFindInfoObject = new SessionCO(null, commonBO);
	  CurrencyVO currencyVO = uoFindInfoObject.uf_find_currency(sessCO.getCompanyCode().toString(), currency.toString());
	  return currencyVO ;
}
*/
/**
 * corresponds to the method f_get_cif_amt
 * @param cifNo
 * @param decCifLimit
 * @param usrLoginInfo
 * @return
 * @throws BaseException
 */
/* public static double getCifAmount(Integer cifNo, Double decCifLimit ,SessionCO sessCO)throws BaseException
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  ReportSC reportSC = new ReportSC();
	  reportSC.setComp_code(sessCO.getCompanyCode()); //Modified by Nathalie
	  reportSC.setCif_no(new Integer(cifNo.intValue()));
	  double decUtilized = commonBO.getCifAmount(reportSC);
	  CurrencyVO cyVO = getCurrency(sessCO.getBaseCurrencyCode().intValue(),sessCO);//getCurrency(), sessCO); Modified by Nathalie
	  double exchgRate = getExchangeRateNew(sessCO.getExposCurCode().intValue(), sessCO.getRunningDateRET());//.getSystemDate()); Modified by Nathalie
	  double decLimit = accountGetAmount(decCifLimit, exchgRate, cyVO.getUnit() ,cyVO.getDecPoint()  , cyVO.getMult_div_ind()  );
	  double cifAmt = Math.abs( Math.abs(decLimit) - Math.abs(decUtilized)); 
	  return cifAmt;
}
*/
/**
 * corresponds to the method f_get_exchange_rate_new that gets the exchange rate according to the maxDate relative to sysdate of exchange 
 * @param exposureCy
 * @param sysdate
 * @return
 * @throws BaseException
 */
/*  public static double getExchangeRateNew(Integer  exposureCy, Date sysdate)throws BaseException
{
	  CommonBO commonBO = (CommonBO)ContextReference.getBean("csmCommonBO");
	  ReportSC reportSC = new ReportSC();
	  reportSC.setCurrency(new Integer (exposureCy.intValue()));
	  reportSC.setDate_rate(sysdate);
	  return commonBO.getExchangeRateNew(reportSC);
}
*/
/**
 * corresponds to the method f_account_get_amt	
 * @param fcAmt
 * @param exchgRate
 * @param cyUnit
 * @param cyDec
 * @param multiDiv
 * @return
 * @throws BaseException
 */
/*public static double accountGetAmount(Double fcAmt, Double exchgRate, Integer cyUnit, Integer cyDec, String multiDiv)throws BaseException
{
	  double cvamt = 0;
	  if (exchgRate.doubleValue()== 0)
		  exchgRate = new Double(1); 
	  if ("M".equals(multiDiv)) 
		  cvamt = PbFunc.round(( fcAmt * exchgRate ) / cyUnit, cyDec); 
	  else 
		  cvamt  = PbFunc.round(( fcAmt * cyUnit ) / exchgRate , cyDec); 
	return cvamt;
}
*/
/**
 * corresponds to the PB function f_get_dec_point
 * @param cyCode currency code
 * @param uinfoVO
 * @return
 * @throws BaseException
 */
/*public static int getDecPoint(Integer cyCode, SessionCO sessCO)throws BaseException
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  CurrencySC currencySC = new CurrencySC();
	  currencySC.setCurrency_code(cyCode.toString());
	  currencySC.setCompCode(sessCO.getCompanyCode().toString()); //Modified by Nathalie
	  CurrencyVO theVO = commonBO.getCurrencyDecPoints(currencySC);
	  if (theVO != null )
		  return theVO.getDecPoint();
	  
	  return sessCO.getBaseCurrDecPoint().intValue(); //Modified by Nathalie
}
*/
/*
public static double getShortOver(Integer cyCode, Long tellerCode, SessionCO sessCO) throws BaseException
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  ReportSC reportSC = new ReportSC();
	  reportSC.setCurrency(cyCode);
	  reportSC.setTeller_code(new BigDecimal(tellerCode));
	  reportSC.setComp_code(sessCO.getCompanyCode()); //Modified by Nathalie
	  reportSC.setBranch_code(sessCO.getBranchCode());
	  reportSC.setSystem_date(sessCO.getRunningDateRET());//.getSystemDate()); //Modified by Nathalie
	  return commonBO.getShortOver(reportSC);
	   
}*/
/*
public static String translate(String key,String menuVar, String theLang)throws BaseException
{
	  DictionaryMgrBO dictionaryMgrBO = (DictionaryMgrBO)ContextReference.getBean("dictMgrSubReportBO");
	  return dictionaryMgrBO.translateToLang(key, menuVar, theLang);
}
*/
/* 
public static Date convertDateGregoHijiri(Date date, String type, BigDecimal compCode)throws BaseException //The type of the argument is changed from integer to BigDecimal by Nathalie 
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  Date convertedDate = commonBO.convertDateGregoHijiri(date, type, compCode);
	  return convertedDate;
}
*/  
//Commented by Nathalie as it is not needed in the reports
/*

public static String getCompPhone(BigDecimal compCode)throws BaseException  //The type of the argument is changed from integer to BigDecimal by Nathalie 
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  ReportSC reportSC = new ReportSC();
	  reportSC.setComp_code(compCode);
	  return commonBO.getCompPhone(reportSC);
}
*/

/* public static String getCurrencyDesc(BigDecimal compCode, Integer cyCode, SessionCO sessCO)throws BaseException
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  ReportSC reportSC = new ReportSC();
	  if (NumberUtil.nullToZero(compCode.intValue()) == 0) //Modified by Nathalie added IntValue
		  compCode = sessCO.getCompanyCode(); //Modified by Nathalie  
	  reportSC.setComp_code(compCode);
	  reportSC.setCurrency(cyCode);
	  reportSC.setPref_lang(sessCO.getLanguage()); //Modified by Nathalie
	  return commonBO.getCurrencyDesc(reportSC);
}
*/
/*
public static Integer baseCy(Integer cyCode, SessionCO sessCO)throws BaseException
{
  cyCode = NumberUtil.nullToZero(cyCode); 
  if(cyCode == 0 || !cyCode.equals(sessCO.getCyUnit()))
	  return 0;
  return 1;
}
*/
//Commented by Nathalie as it is not needed in the reports
/*
	//f_company_base_cy
public static Integer getCompanyBaseCy(Integer compCode, Integer cy, SessionCO sessCO)throws BaseException
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  ReportSC reportSC = new ReportSC();
	  reportSC.setComp_code(compCode);
	  reportSC.setCurrency(cy);
	  return commonBO.getCompanyBaseCy(reportSC);
}
*/
//f_currency_mask_company
//Commented by Nathalie as it is not needed in the reports
/*
public static String getCurrencyMaskCompany(Integer compCode, SessionCO sessCO)throws BaseException
{
	  CommonBO commonBO = (CommonBO) ContextReference.getBean("csmCommonBO");
	  ReportSC reportSC = new ReportSC();
	  reportSC.setComp_code(compCode);
	  String format = "#,##0";
	  int cyDec = commonBO.getBaseCyDecByComp(reportSC).intValue();
	  if(cyDec > 0)
	  {
		  format = format + ".";
		  format = format + PbFunc.fill("0",cyDec) + " " + ";(" + format + PbFunc.fill("0",cyDec) + ")" ;  		  
	  }
	  return format;
}
*/

//Commented by Nathalie as it is not needed in the reports
/*
//f_currency_mask_dr_cr_global
public static String getCurrencyMaskDrCrGlobal( SessionCO sessCO)throws BaseException
{
	  String format = "#,##0.";
	  format = format + PbFunc.fill("0",sessCO.getBaseCurrDecPoint().intValue()) + " 'DR';" + //Modified by Nathalie added sessCO.getBaseCurrDecPoint().intValue()
	  		   format + PbFunc.fill("0",sessCO.getBaseCurrDecPoint().intValue()) + " 'CR';" +
	  		   format + PbFunc.fill("0",sessCO.getBaseCurrDecPoint().intValue()) + " '  '";
	  return format;
}
*/
//f_date

//Commented by Nathalie as it is not needed in the reports
/*
public static Date getRelativeEomDate(Date fromDate)
{
		Calendar cal = Calendar.getInstance();
		cal.setTime(fromDate);
		int eom = cal.getActualMaximum(cal.DAY_OF_MONTH);
		cal.set(cal.DAY_OF_MONTH, eom);
		return cal.getTime();
}
}
*/