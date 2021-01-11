package com.path.bo.reporting.designer.jasperrules;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import com.path.lib.common.util.StringUtil;
import com.path.lib.log.Log;
public final class XslInput
{
    private static ResourceBundle resourceBundleJrxml;
    private static ResourceBundle resourceBundleXhtml;
    
    private static final Log log = Log.getInstance();
    
    private XslInput() 
    {
	Log.getInstance().error("This Class Should not be Instantiated");
    }
    static
    {
	try
	{
	    // Read properties files containing the mapping of the css style
	    // between xhtml and jrxml.
	    resourceBundleJrxml = ResourceBundle.getBundle("com.path.JrxmlStyles");
	    resourceBundleXhtml = ResourceBundle.getBundle("com.path.XhtmlStyles");
	}
	catch(MissingResourceException e)
	{
	    log.debug("Error loading Style properties files.");
	}
    }

    @SuppressWarnings("finally")
    public static String returnJrxmlStyleName(String style)
    {
	String jrName = null;
	try
	{
	    jrName = resourceBundleJrxml.getString(style.split(":")[0].trim());
	}
	catch(Exception e)
	{
	    if(jrName == null)
	    {
		jrName = style.split(":")[0];
	    }
	}
	return StringUtil.nullToEmpty(jrName).trim();

    }

    @SuppressWarnings("finally")
    public static String returnJrxmlStyleValue(String style)
    {
	String jrValue = null;
	try
	{
	    jrValue = resourceBundleJrxml.getString(style.split(":")[1].trim());
	    if(jrValue.indexOf(",") > -1)
	    {
		jrValue = jrValue.split(",")[0];
	    }
	}
	catch(Exception e)
	{
	    if(jrValue == null)
	    {
		jrValue = style.split(":")[1];
	    }
	}
	return jrValue.trim();
    }

    public static String returnJrxmlColorName(String colorName)
    {
	String jrName = resourceBundleJrxml.getString(colorName);
	return jrName.trim();
    }

    public static String returnJrxmlTextValue(String text)
    {
	return "<![CDATA[" + text + "]]>";
    }

    public static String returnJrxmlStaticValue(String text)
    {
	String textVal = text.replaceAll("\"", "");
	return "<![CDATA[\"" + textVal + "\"]]>";
    }

    public static String returnJrxmlImagePath(String imgPath)
    {
	// String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	// repositoryPath=StringUtil.replaceInString(repositoryPath, "\\", "/");
	int a = 8;
	String imageName = imgPath.substring(imgPath.indexOf("updates=") + a, imgPath.length());
	imageName = StringUtil.replaceInString(imageName, "\\", "/");
	imageName = StringUtil.replaceInString(imageName, "%28", "(");
	imageName = StringUtil.replaceInString(imageName, "%29", ")");
	// annasuccar- 30/12/2013: set the image expression in jrxml to be
	// independant from the server repository path
	// return "new File(\""+repositoryPath+"\", \"images/"+imageName+"\")";
	return "\"images/" + imageName + "\"";
    }

    public static String returnXhtmlImagePath(String imgPath)
    {
	String xhtmlPath = "/imal_reporting_portal/path/designer/image_loadImage.action?updates="
		+ imgPath.substring(imgPath.lastIndexOf("/") + 1, imgPath.length() - 1);
	return xhtmlPath;
    }
    public static String returnXhtmlImageExpression(String imgPath)
    {
	return imgPath;
    }
    @SuppressWarnings("finally")
    public static String returnJrxmlPdfFont(String style)
    {
	String jrValue = null;
	try
	{
	    jrValue = resourceBundleJrxml.getString(style.split(":")[1].trim());
	    jrValue = jrValue.split(",")[1];
	}
	catch(Exception e)
	{
	    if(jrValue == null)
	    {
		jrValue = "arial.ttf";// default if font name not listed in
				      // properties file
	    }
	}
	return jrValue.trim();

    }

    public static String returnXhtmlStyleName(String style)
    {
	String htmlName = null;
	try
	{
	    htmlName = resourceBundleXhtml.getString(style);
	}
	catch(Exception e)
	{
	    if(htmlName == null)
	    {
		return null;
	    }
	}
	return htmlName.trim();
    }

    public static String returnXhtmlStyleValue(String value)
    {
	String hmlValue = null;
	try
	{
	    hmlValue = resourceBundleXhtml.getString(value.trim());
	}
	catch(Exception e)
	{
	    if(hmlValue == null)
	    {
		return null;
	    }
	}
	return hmlValue.trim();
    }

    public static String returnXhtmlAttName(String style)
    {
	String xhtmlName = null;
	try
	{
	    xhtmlName = resourceBundleXhtml.getString(style.split(":")[0].trim());
	}
	catch(Exception e)
	{
	    if(xhtmlName == null)
	    {
		return null;
	    }
	}
	return xhtmlName.trim();
    }

    public static String returnXhtmlAttValue(String style)
    {
	String xhtmlValue = null;
	try
	{
	    xhtmlValue = resourceBundleXhtml.getString(style.split(":")[1].trim());
	}
	catch(Exception e)
	{
	    if(xhtmlValue == null)
	    {
		return null;
	    }
	}
	return xhtmlValue.trim();
    }

    public static String returnFieldType(String expression)
    {
	String type = "";

	if(("\"Page \"+$V{PAGE_NUMBER}+\" of\"").equalsIgnoreCase(expression))
	{
	    type = "pageNbr";
	}
	else if(("\" \" + $V{PAGE_NUMBER}").equalsIgnoreCase(expression))
	{
	    type = "pageCnt";
	}
	else if(expression.contains("$F{"))
	{
	    type = "field";
	}
	else if(expression.contains("$P{"))
	{
	    type = "param";
	}
	else
	{
	    type = "label";
	}
	return type;
    }

    public static String returnInnerHTML()
    {
	return " ";
    }
}
