package com.path.bo.reporting.designer.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import net.sf.jasperreports.engine.JRException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.path.bo.common.CommonLibBO;
import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.common.util.ReportUtils;
import com.path.bo.reporting.designer.QueryBO;
import com.path.bo.reporting.designer.querybuilder.QueryBuilder;
import com.path.dao.reporting.common.ReportDAO;
import com.path.dao.reporting.designer.QueryDAO;
import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_QUERY_EXEC_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_QUERYVOKey;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.StringUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.struts2.lib.common.ConnectionCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.CONDITION_OBJECT;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_ENTITIESCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.QRY_GRIDSC;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.designer.IRP_AD_HOC_QUERYSC;

public class QueryBOImpl extends BaseBO implements QueryBO 
{
	private QueryDAO queryDAO;
	private ReportDAO reportDAO;
	private CommonLookupBO commonLookupBO;
	
	
    public void setCommonLookupBO(CommonLookupBO commonLookupBO)
	{
	    this.commonLookupBO = commonLookupBO;
	}

    private CommonLibBO commonLibBO;
    
    public void setReportDAO(ReportDAO reportDAO)
    {
        this.reportDAO = reportDAO;
    }

    public void setCommonLibBO(CommonLibBO commonLibBO) 
    {
	this.commonLibBO = commonLibBO;
	}

	public QueryDAO getQueryDAO() {
		return queryDAO;
	}

	public void setQueryDAO(QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

	public List<IRP_ENTITIESCO> retDBEntities(QRY_GRIDSC qryGridSC) throws BaseException 
	{
		return queryDAO.retDBEntities(qryGridSC);
	}

	public int retDBEntitiesCount(QRY_GRIDSC qryGridSC) throws BaseException
	{
		return queryDAO.retDBEntitiesCount(qryGridSC);
	}

	public List<IRP_FIELDSCO> retDBFields(QRY_GRIDSC qryGridSC)throws BaseException 
	{
		return queryDAO.retDBFields(qryGridSC);
	}

	public int retDBFieldsCount(QRY_GRIDSC qryGridSC) throws BaseException 
	{
		return queryDAO.retDBFieldsCount(qryGridSC);
	}

	public List<IRP_AD_HOC_QUERYCO> queriesByReportId(BigDecimal reportId) throws BaseException, IOException, ClassNotFoundException {
		
		List<IRP_AD_HOC_QUERYCO> queries = queryDAO.queriesByReportId(reportId);		
		for(IRP_AD_HOC_QUERYCO query : queries)
		{
			//check if it is an old query , than open it as object than it will be transformed to xml on saving
			fillSqlObj(query);
			//check if sqb then get the sql syntax from the sqlObj
			if(query.getSqlObject().getEntities().size()==0)
			{
				query.setSqlSyntax(query.getSqlObject().getSqbSyntax());
			}
			else
			{
				query.setSqlSyntax(generateSql(query.getSqlObject()));
			}
		}
		return queries;
	}

	public void deleteQuery(List<BigDecimal> queriesId,AuditRefCO refCO) throws BaseException {
	    
	    	int isQryArg = queryDAO.checkIfIsQryArg(queriesId);
	    	if(isQryArg>0)
	    	{
	    	    throw new BOException("qryToNotDel");
	    	}
	    	else
	    	{
	    	    queryDAO.deleteQuery(queriesId);
	    	}
		//add Audit
		if(refCO!=null)
		{
			IRP_AD_HOC_QUERYVO qryVO;
			for(int i=0;i<queriesId.size();i++)
			{
				qryVO=new IRP_AD_HOC_QUERYVO();
				qryVO.setQUERY_ID(queriesId.get(i));
				auditBO.callAudit(qryVO, qryVO, refCO);
			}
			auditBO.insertAudit(refCO);
		}
	}

	public int getQueriesCount(IRP_AD_HOC_QUERYSC sc) throws BaseException {
		return queryDAO.getQueriesCount(sc);
	}

	public List<IRP_AD_HOC_QUERYVO> getQueriesList(IRP_AD_HOC_QUERYSC sc) throws BaseException {
		return queryDAO.getQueriesList(sc);
	}
	
	@Override
	public void fillSqlObj(IRP_AD_HOC_QUERYCO query) throws ClassNotFoundException, IOException
	{
		byte[]sqlObj=query.getQUERY_OBJECT();
		StringBuffer xml;
		String xmlStr=new String(sqlObj,CommonUtil.ENCODING);
		xml=new StringBuffer(xmlStr);
		//if the sqlObj is saved as object in the db (old version)
		if(xml.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")==-1)
		{
			query.setSqlObject((SQL_OBJECT) CommonUtil.objectFromBytes(query.getQUERY_OBJECT()));
		}
		//if the sqlObj is saved as xml 
		else
		{
		    if(RepConstantsCommon.CALLED_FROM_COMMON.equals(query.getGrpByQryName()))
        	    {
        		query.setSqlObject(retXmlToSqlObject(query.getQUERY_OBJECT()));
        	    }
        	    else
        	    {
        		query.setSqlObject(xmlToSqlObject(query.getQUERY_OBJECT()));
        	    }
		}
	}
	
	public IRP_AD_HOC_QUERYCO returnQueryById(BigDecimal queryId, boolean generateSyntax) throws BaseException, IOException, ClassNotFoundException {
		IRP_AD_HOC_QUERYCO query = queryDAO.returnQueryById(queryId);
		if(query.getQUERY_OBJECT()!=null)
		{
        		fillSqlObj(query);
        		
        		if(generateSyntax)
        		{
        			StringBuffer sqlSyntax = QueryBuilder.buildQuery(query.getSqlObject());
        			query.setSqlSyntax(sqlSyntax);
        		}
		}
		return query;
	}	

	public String parseExpression(String expString,SQL_OBJECT sqlObj) throws BaseException 
	{	
	    	String expressionString= expString;
		String fieldFromFont = null;
		String fontString 	= "";
		   
	   //for mozilla
	   expressionString=expressionString.replaceAll("<font", "<FONT");
	   expressionString=expressionString.replaceAll("</font", "</FONT");
	   expressionString=expressionString.replaceAll("&lt;", "<");
	   expressionString=expressionString.replaceAll("&quot;", "\"");
	   expressionString=expressionString.replaceAll("&gt;", ">");
	   expressionString=expressionString.replaceAll("<br>", "<BR>");
	   expressionString=expressionString.replaceAll("<br/>", "<BR/>");
	   try
	   {
	     while(expressionString.indexOf("<FONT") != -1)
	     {
	    		 fontString = expressionString.substring(expressionString.indexOf("<FONT"),expressionString.indexOf("</FONT>") + 7);
    			 fieldFromFont 		= getFieldFromFont(fontString,sqlObj);
    			 expressionString 	= expressionString.replace(fontString,fieldFromFont);
    			 
	      }
	     
	     while(expressionString.indexOf("<p>") != -1 || expressionString.indexOf("<P>") != -1 || expressionString.indexOf("</p>") != -1 ||expressionString.indexOf("</P>") != -1)
	     {
		      expressionString = expressionString.replaceAll("<p>","");
		      expressionString = expressionString.replaceAll("<P>","");
		      expressionString = expressionString.replaceAll("</p>","");
		      expressionString = expressionString.replaceAll("</P>","");
	     }
	     expressionString = expressionString.replaceAll("<BR>"," ");
	     expressionString = expressionString.replaceAll("<BR/>"," ");
	     
	     expressionString = expressionString.replaceAll("&nbsp;"," ");
	     expressionString = expressionString.replaceAll("<br/>"," ");
	     expressionString =expressionString.replaceAll("<br>"," ");
	     expressionString = expressionString.replaceAll("&lt;","<");
	     expressionString = expressionString.replaceAll("&gt;",">");
	     expressionString = expressionString.replaceAll("&amp;","&");
	     expressionString = expressionString.replaceAll("&apos;","'");
	   }
	   catch(Exception ex)
	   {
	     log.error(ex, ex.getMessage());
	   }

	   return expressionString;
	}
	public String getFieldFromFont(String exprStr,SQL_OBJECT sqlObj)
	 {
	   String retVal = "";
	   try
	   {
	      String[] breakFontString 	= exprStr.split("#VAR#");
 	      String fontContent 		= breakFontString[1];
	     
	      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	      DocumentBuilder builder = factory.newDocumentBuilder();
	      InputSource is = new InputSource( new StringReader( fontContent) );
	      Document fieldDocument = builder.parse( is );
	      Node fieldNode 			= fieldDocument.getDocumentElement().cloneNode(true);
	     
	      if(sqlObj==null) //construct the test case for check Validity
	      {
	    	  String fieldType=null;
		      if(fieldNode.getAttributes().getNamedItem("fieldType")!=null)
		      {
		    	  fieldType=fieldNode.getAttributes().getNamedItem("fieldType").getFirstChild().getNodeValue();
		    	  if(RepConstantsCommon.NUMBER_TYPE_JASPER.equals(fieldType))
		    	  {
		    		  retVal="1";
		    	  }
		    	  else if(RepConstantsCommon.DATE_TYPE_JASPER.equals(fieldType))
		    	  {
		    		  retVal="sysdate";
		    	  }
		    	  else
		    	  {
		    		  retVal="'a'";
		    	  }
		      }
		      
	      }
	      else  //construct the expr.Sql for the sqlString
	      {
		      String fieldId=null;
		      String parentId=null;
		      if(fieldNode.getAttributes().getNamedItem("id")!=null)
		      {
		    	 fieldId=fieldNode.getAttributes().getNamedItem("id").getFirstChild().getNodeValue();
		    	 parentId=fieldNode.getAttributes().getNamedItem("pId").getFirstChild().getNodeValue();
		      }
		     
		      if(parentId==null || parentId.equals("")) //aggr. inside expr;
		      {
		    	  HashMap<Long,IRP_FIELDSCO> aggrMap=sqlObj.getAggregateFields();
		    	  IRP_FIELDSCO aggrFe=aggrMap.get(Long.valueOf(fieldId));
		    	  retVal=aggrFe.getFieldSyntax();
		    	  
		      }
		      else
		      {
		    	  HashMap<Long,IRP_FIELDSCO> feMap=sqlObj.getFields();
			      IRP_FIELDSCO feCO=feMap.get(Long.valueOf(fieldId));
			      if(feCO!=null)
			      {
			    	 Long pId=feCO.getPARENT_INDEX();
			    	 HashMap<Long,IRP_ENTITIESCO> entMap=sqlObj.getEntities();
			    	 IRP_ENTITIESCO entCO=entMap.get(pId);
			    	 retVal=entCO.getSyntaxAlias()+"."+feCO.getFIELD_DB_NAME();
			      }
		      }
	    	  
	      }
	     
	   }
	   catch(Exception ex)
	   {
	     log.error(ex, ex.getMessage());
	   }

	   return retVal;

	 }

	
	public String checkExpressionValidity(String exprHtml) throws BaseException 
	{
		 String retSqlVal;
		 String res="";
		 String sql ="";
		 try
	     {
		    sql = parseExpression(exprHtml,null);
		    log.debug("\n\n\n"+sql+"\n\n");
		    //check if the expression contains an aggr.function then return false
		    String reMax="(.)*(\\s)*(m|M)(a|A)(x|X)(\\s)*\\((\\s)*(.)*\\)(\\s)*(.)*";
		    String reMin="(.)*(\\s)*(m|M)(i|I)(n|N)(\\s)*\\((\\s)*(.)*\\)(\\s)*(.)*";
		    String reAvg="(.)*(\\s)*(a|A)(v|V)(g|G)(\\s)*\\((\\s)*(.)*\\)(\\s)*(.)*";
		    String reSum="(.)*(\\s)*(s|S)(u|U)(m|M)(\\s)*\\((\\s)*(.)*\\)(\\s)*(.)*";
		    String reCount="(.)*(\\s)*(c|C)(o|O)(u|U)(n|N)(t|T)(\\s)*\\((\\s)*(.)*\\)(\\s)*(.)*";
            if(Pattern.matches(reMax,sql) || Pattern.matches(reMin,sql)|| Pattern.matches(reAvg,sql)|| Pattern.matches(reSum,sql)|| Pattern.matches(reCount,sql))
            {
            	retSqlVal="aggr";
            }
            else
            {
        	 res= queryDAO.testQuery(sql);
        	 //do not remove the line below since it is used to check if the return type  of the expression is number or not
                 Integer.parseInt(res);
                 retSqlVal=RepConstantsCommon.NUMBER_TYPE_JASPER;
            }
	   }
	  catch(NumberFormatException nbEx)
    	  {
    	    throw checkIfDateOrDouble(res, nbEx);
    	  }
	     catch (Exception ex)
	     {
	        throw new BOException("wrong",ex);
	     }
	     return retSqlVal;
	}

	private BOException checkIfDateOrDouble(String res, NumberFormatException nbEx)
	{
	    try
	     {
	         SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss",Locale.ENGLISH);
	         dateFormat.parse(res); 
	         return new BOException("java.util.date",nbEx);
	     }
	     catch(ParseException dateEx)
	     {
	    	 try
	    	 {
	    		Double.parseDouble(res);
	    		return new BOException("java.lang.Double",dateEx);
	    	 }
	    	 catch(Exception e)
	    	 {
	    	     return new BOException(RepConstantsCommon.VARCHAR_TYPE_JASPER,e);
	    	 }
	     }
	}

	public StringBuffer generateSql(SQL_OBJECT sqlObj) throws BaseException 
	{
	    return QueryBuilder.buildQuery(sqlObj);
	}

	public void validateSqbQuery(String sqlStr) throws BaseException 
	{
	    String sqlString= sqlStr;
		try
		{
			//if sybase ,remove the order by from the end of the query since it the query validation will not work 
			if(commonLibBO.returnIsSybase()==1)
			{
			    if(sqlString.indexOf("order by ")== -1 )
			    {
				if(sqlString.indexOf("ORDER BY ")!=-1)
				{
				    sqlString=sqlString.substring(0,sqlString.indexOf("ORDER BY"));
				}
			    }
			    else
			    {
				sqlString=sqlString.substring(0,sqlString.indexOf("order by"));
			    }
			}
			sqlString=StringUtil.replaceInString(sqlString,"\t"," ");
			sqlString=StringUtil.replaceInString(sqlString,"\n"," ");
			queryDAO.testSqbQuery(sqlString);
		}
		  catch (Exception ex)
	     {
	        throw new BaseException(ex.getMessage(),ex);
	     }
		
	}
	
	@Override
	public void validateSqbQueryDesigner(StringBuffer sqlStr, ArrayList<IRP_REP_ARGUMENTSCO> args, BigDecimal conId) throws BaseException 
	{
	    StringBuffer sqlString= sqlStr;
	    Connection con;
		try
		{
		    
		    	if(conId==null)
		    	{
		    	    con = reportDAO.getConnection();
		    	}
		    	else
		    	{
			    ConnectionCO connectCO = commonLookupBO.returnConnection(conId);
			    con = DriverManager.getConnection(connectCO.getDbURL(),connectCO.getDbUserName(), connectCO.getDbPassword());
			}
		    	  
			//if sybase ,remove the order by from the end of the query since it the query validation will not work 
			if(commonLibBO.returnIsSybase()==1)
			{
			    if(sqlString.indexOf("order by ")== -1 )
			    {
				if(sqlString.indexOf("ORDER BY ")!=-1)
				{
				    sqlString= new StringBuffer(sqlString.substring(0,sqlString.indexOf("ORDER BY")));
				}
			    }
			    else
			    {
				sqlString=new StringBuffer(sqlString.substring(0,sqlString.indexOf("order by")));
			    }
			}
			try
			{
			    //call function that will generate a sample jrxml and test it with the given query
			    ReportUtils.testQryExecution(sqlString, args,con);
			}
			catch(JRException ex)
			{
			    log.error(ex,ex.getMessage());
			    if(ex.getCause()==null)
			    {
				throw new BaseException(ex.getMessage(),ex);
			    }
			    else
			    {
				throw new BaseException(ex.getCause().getMessage(),ex);
			    }
			}
			finally
			{
			    if(con != null)
			    {
			    	con.close();
			    }
			}
			
		}
	     catch (Exception ex)
	     {
	        throw new BaseException(ex.getMessage(),ex);
	     }
		
	}
	
	@Override
	public LinkedHashMap fillSqlFields(String sqlString,BigDecimal conId) throws BaseException 
	{
		try
		{
        	    Connection con = null;
        	    if(conId != null)
        	    {
        		ConnectionCO connectCO = commonLookupBO.returnConnection(conId);
        		con = DriverManager.getConnection(connectCO.getDbURL(), connectCO.getDbUserName(), connectCO
        			.getDbPassword());
        	    }
		    return queryDAO.fillSqlFields(sqlString,con);
		}
		catch (Exception ex)
		{
		    throw new BaseException(ex.getMessage(),ex);
		}
	}

	public String checkQryName(String qryName) throws BaseException 
	{
		return queryDAO.checkQryName(qryName);
	}

	public void deleteQueries(List<BigDecimal> reprotsId) throws BaseException {
		queryDAO.deleteQueries(reprotsId);		
	}

	@Override
	public void updateQuery(IRP_AD_HOC_QUERYVO queryvo) throws BaseException {	
		Integer row=queryDAO.update(queryvo);
		if (row == null || row < 1)
		 {
		     throw new BOException(MessageCodes.RECORD_CHANGED);
		 }
		
		//save audit
		if(queryvo.getAuditRefCO()!=null) //called from the queries section
		{
			auditBO.callAudit( queryvo,queryvo, queryvo.getAuditRefCO());
			auditBO.insertAudit(queryvo.getAuditRefCO());
		}
	}

	@Override
	public void insertQuery(IRP_AD_HOC_QUERYVO queryvo) throws BaseException {
		queryDAO.saveQuery(queryvo);
		
		//save audit
		if(queryvo.getAuditRefCO()!=null) //called from the queries section
		{
			auditBO.callAudit(null, queryvo, queryvo.getAuditRefCO());
		}
	}

	@Override
	public void insertReportQry(IRP_REPORT_QUERYVOKey rqVO)
			throws BaseException {
		queryDAO.insertReportQry(rqVO);		
	}

	@Override
	public HashMap<String,String> returnQryColumns(String syntax)	throws BaseException {
		return queryDAO.returnQryColumns(syntax);
	}
	
	public SQL_OBJECT xmlToSqlObject(byte[] qryObj)
	{
		SQL_OBJECT sqlObj =new SQL_OBJECT();
		try 
		{
//			/* To be removed before commit*/
//			String xmlStr;
//			StringBuffer xml;
//			xmlStr=new String(qryObj);
//			xml=new StringBuffer(xmlStr);
//			FileUtil.writeFile(ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder)+"/openQry.xml", xml.toString());
//			/* End to be removed before commit*/
			
			
			   DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			   DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			   Document doc = dBuilder.parse(new ByteArrayInputStream(qryObj));

			   NodeList qryLst = doc.getElementsByTagName("qry");
			   
			   NodeList parentNodeLst;
			   NodeList childNodeLst;
			   NodeList ch1NodeLst;
			   NodeList ch2NodeLst;
			   NodeList ch3NodeLst;
			   
			   Node qry;
			   Node childNode;
			   Node parentNode;
			   Node argLblNode;
			   Node argValNode;
			   
			   Element parentElem;
			   Element qryElem;
			   Element childElem;
			   
			   NamedNodeMap attrs ;
			   
			   String str;
			   String str1;
			   String str3;
			   int j;
			   
			   IRP_ENTITIESCO entCO;
			   IRP_FIELDSCO feCO;
			   IRP_REP_ARGUMENTSCO argCO;
			   CONDITION_OBJECT condCO;
			   QRY_GRIDSC qryGridSC;
			   
			   for(int i = 0; i < qryLst.getLength(); i++)
			   {
			       qry = qryLst.item(i);
			       
			       //fill qryName and outputLayout
	               attrs = qry.getAttributes();
		      if(attrs.getNamedItem("name")==null)
		      {
			  qryElem = (Element) qry;
			  childNodeLst = qryElem.getElementsByTagName("name");
			  childNode=childNodeLst.item(0);
			  sqlObj.setQUERY_NAME(childNode.getChildNodes().item(0).getNodeValue()); 
				  
		      }
		      else
		      {

			  sqlObj.setQUERY_NAME(attrs.getNamedItem("name").getNodeValue());
		      }
	               sqlObj.setOutputLayout(attrs.getNamedItem("outputLayout").getNodeValue());
	               str=attrs.getNamedItem("type").getNodeValue();
	               
	               //if designer query
	               if("0".equals(str)) 
	               {
	                   if(qry.getNodeType() == Node.ELEMENT_NODE)
				       {
		            	   qryElem = (Element) qry;
		            	   
		            	   //fill entities
		            	   parentNodeLst = qryElem.getElementsByTagName("entities");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("entity");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				        		   
				        		   entCO=new IRP_ENTITIESCO();
				        		   entCO.setIndex(Long.valueOf(attrs.getNamedItem("id").getNodeValue()));
				        		   entCO.setType(attrs.getNamedItem("type").getNodeValue());
				        		   entCO.setSyntaxAlias(attrs.getNamedItem("syntAlias").getNodeValue());
				        		   entCO.setENTITY_DB_NAME(attrs.getNamedItem("dbName").getNodeValue());
				        		   
				        		   if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("alias");
				        			   ch2NodeLst =childElem.getElementsByTagName("aliasWithCnt");
				        			   entCO.setENTITY_ALIAS(ch1NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   entCO.setEntityAliasWithCount(ch2NodeLst.item(0).getChildNodes().item(0).getNodeValue());
						           }
				        		   sqlObj.getEntities().put(entCO.getIndex(),entCO );
				        	   }
				           }
				           
				           //fill fields
				           parentNodeLst = qryElem.getElementsByTagName("fields");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("field");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				        		   
				        		   feCO=new IRP_FIELDSCO();
				        		   feCO.setIndex(Long.valueOf(attrs.getNamedItem("id").getNodeValue()));
				        		   feCO.setFIELD_DB_NAME(attrs.getNamedItem("dbName").getNodeValue());
				        		   feCO.setFIELD_DATA_TYPE(attrs.getNamedItem("feType").getNodeValue());
				        		   feCO.setPARENT_INDEX(Long.valueOf(attrs.getNamedItem("pId").getNodeValue()));
				        		   feCO.setType(attrs.getNamedItem("type").getNodeValue());
				        		   feCO.setLabelAlias(attrs.getNamedItem("lblAlias").getNodeValue());
				        		   feCO.setOrder(attrs.getNamedItem("order").getNodeValue());
				        		   if(attrs.getNamedItem("hasBusName")!=null && ("0").equals(attrs.getNamedItem("hasBusName").getNodeValue()))
				        		   {
				        			   feCO.setHasBusinessName(0);
				        		   }
				        		   entCO=sqlObj.getEntities().get(feCO.getPARENT_INDEX());
				        		   feCO.setENTITY_DB_NAME(entCO.getENTITY_DB_NAME());
				        		   feCO.setENTITY_ALIAS(entCO.getENTITY_ALIAS());
				        		   feCO.setEntityAliasWithCount(entCO.getEntityAliasWithCount());
				        		   feCO.setFieldSyntax(entCO.getSyntaxAlias()+"."+feCO.getFIELD_DB_NAME());
				        		   feCO.setParent((feCO.getPARENT_INDEX()).toString());
				        		   feCO.setIsLeaf("true");
				        		   feCO.setLevel("3");
				        		   
				        		   if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("alias");
				        			   ch2NodeLst =childElem.getElementsByTagName("grp");
				        			   feCO.setFIELD_ALIAS(ch1NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   feCO.setFeName(entCO.getENTITY_ALIAS()+"."+feCO.getFIELD_ALIAS());
				        			   if(ch2NodeLst.getLength()>0)
				        			   {
				        				   feCO.setGroupName(ch2NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   }
						           }
				        		   
				        		   sqlObj.getFields().put(feCO.getIndex(), feCO);
				        		   entCO.getSelectedFields().put(feCO.getIndex(), feCO);
				        		   if("1".equals(attrs.getNamedItem("sel").getNodeValue()))
				        		   {
				        			   sqlObj.getDisplayFields().put(feCO.getIndex(), feCO);
				        		   }
				        		   
			        		   }
				           }
				           
				           //fill expression
				           parentNodeLst = qryElem.getElementsByTagName("exps");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("exp");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				        		   
				        		   feCO=new IRP_FIELDSCO();
				        		   feCO.setFIELD_DATA_TYPE(attrs.getNamedItem("feType").getNodeValue());
				        		   feCO.setIndex(Long.valueOf(attrs.getNamedItem("id").getNodeValue()));
				        		   feCO.setType(attrs.getNamedItem("type").getNodeValue());
				        		   feCO.setParent("2");
				        		   feCO.setIsLeaf("true");
				        		   feCO.setLevel("2");
				        		   if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("feSynt");
				        			   ch2NodeLst =childElem.getElementsByTagName("lblAlias");
				        			   ch3NodeLst =childElem.getElementsByTagName("html");
				        			   
				        			   feCO.setFieldSyntax(ch1NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   feCO.setLabelAlias(ch2NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   feCO.setFeName(feCO.getLabelAlias());
				        			   feCO.setHtml(ch3NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   
						           }
				        		   sqlObj.getExpressionFields().put(feCO.getIndex(), feCO);
				        	   }
				           }
				           
				           //fill aggregates
				           parentNodeLst = qryElem.getElementsByTagName("aggrs");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("aggr");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		    childNode=childNodeLst.item(j);
				        		    attrs = childNode.getAttributes();
				        		    
									feCO=new IRP_FIELDSCO();
									feCO.setIndex(Long.valueOf(attrs.getNamedItem("id").getNodeValue()));
									feCO.setParent("3");
									feCO.setLevel("2");
									feCO.setIsLeaf("true");
									feCO.setAggregate(attrs.getNamedItem("aggrFn").getNodeValue());
									feCO.setFIELD_ALIAS(attrs.getNamedItem("feAlias").getNodeValue());
									feCO.setFIELD_DB_NAME(attrs.getNamedItem("feDb").getNodeValue());
									feCO.setType(attrs.getNamedItem("type").getNodeValue());
									feCO.setPARENT_INDEX(Long.valueOf(attrs.getNamedItem("pId").getNodeValue()));
									feCO.setFIELD_DATA_TYPE(attrs.getNamedItem("feType").getNodeValue());
									//get entityCO from the parent_index
									entCO=sqlObj.getEntities().get(feCO.getPARENT_INDEX());
									feCO.setEntityAliasWithCount(entCO.getEntityAliasWithCount());
									feCO.setENTITY_ALIAS(entCO.getENTITY_ALIAS());
									feCO.setENTITY_DB_NAME(entCO.getENTITY_DB_NAME());
									
									
									if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("lblAlias");
				        			   ch2NodeLst =childElem.getElementsByTagName("feSynt");
				        			   feCO.setLabelAlias(ch1NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   feCO.setFieldSyntax(ch2NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   feCO.setFeName(feCO.getLabelAlias());
						           }

				        		   
				        		   sqlObj.getAggregateFields().put(feCO.getIndex(), feCO);
				        	   }
				           }
				           
				           //fill arguments
				           parentNodeLst = qryElem.getElementsByTagName("args");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("arg");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				       			
				        		   argCO=new IRP_REP_ARGUMENTSCO();
				        		   argCO.setIndex(Long.parseLong(attrs.getNamedItem("id").getNodeValue()));
				        		   argCO.setARGUMENT_NAME(attrs.getNamedItem("name").getNodeValue());
				        		   argCO.setARGUMENT_TYPE(attrs.getNamedItem("type").getNodeValue());
				        		   argCO.setARGUMENT_SOURCE(new BigDecimal(attrs.getNamedItem("src").getNodeValue()));
				        		   argCO.setSESSION_ATTR_NAME(attrs.getNamedItem("sess").getNodeValue());
				        		   if(attrs.getNamedItem("qryId")!=null  && !"null".equals(attrs.getNamedItem("qryId").getNodeValue()))
				        		   {
				        			   argCO.setQUERY_ID(new BigDecimal(attrs.getNamedItem("qryId").getNodeValue()));
				        		   }
				        		   argCO.setQUERY_NAME(attrs.getNamedItem("qryName").getNodeValue());
				        		   if(attrs.getNamedItem("linkRepQryArg")!=null)
				        		   {
				        			   argCO.setLINK_REP_QRY_ARG(attrs.getNamedItem("linkRepQryArg").getNodeValue());
				        		   }
				        		   if("null".equals(sqlObj.getOutputLayout()))//to link the qry arg to rep arg on creation of a new rep from designer
				        		   {
				        		       argCO.setLINK_REP_QRY_ARG(argCO.getARGUMENT_NAME());
				        		   }
				        		   str=attrs.getNamedItem("col").getNodeValue();
				        		   if("null".equalsIgnoreCase(str))
				        		   {
				        			   str=null;
				        		   }
				        		   argCO.setCOLUMN_NAME(str);
				        		   argCO.setValueFlag(Boolean.valueOf(attrs.getNamedItem("valFlg").getNodeValue()));
				        		   argCO.setARGUMENT_ORDER(new BigDecimal(attrs.getNamedItem("order").getNodeValue()));
				        		   argCO.setDISPLAY_FLAG(attrs.getNamedItem("displ").getNodeValue());
				        		   argCO.setENABLE_FLAG(attrs.getNamedItem("enable").getNodeValue());
				        		   
				        		   
				        		   if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("lbl");
				        			   ch2NodeLst =childElem.getElementsByTagName("val");
				        			   String argLabel="";
				        			   String argValue="";
				        			   argLblNode=ch1NodeLst.item(0).getFirstChild();
				        			   argValNode=ch2NodeLst.item(0).getFirstChild();
                                				    if(argLblNode != null)
                                				    {
                                					argLabel = argLblNode.getNodeValue();
                                				    }
                                				    if(argValNode != null)
                                				    {
                                					argValue = argValNode.getNodeValue();
                                				    }
				        			  argCO.setARGUMENT_LABEL(argLabel);
				        			  argCO.setARGUMENT_VALUE(argValue);
						           }
				        		  sqlObj.getArgumentsList().put(argCO.getIndex(), argCO); 
				        	   }
				        	   
				        	   
				           }
				           
				           //fill joins
				           parentNodeLst = qryElem.getElementsByTagName("joins");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("join");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				       			
				        		   condCO=new CONDITION_OBJECT();
				        		   condCO.setIndex((Long.parseLong(attrs.getNamedItem("id").getNodeValue())));
				        		   str=attrs.getNamedItem("entId1").getNodeValue();
				        		   str1=attrs.getNamedItem("entId2").getNodeValue();
				        		   //get the ent1 and ent2 from the entId1 and entId2
				        		   condCO.setEntity1(sqlObj.getEntities().get(Long.valueOf(str)));
				        		   condCO.setEntity2(sqlObj.getEntities().get(Long.valueOf(str1)));
				        		   condCO.setJoin(attrs.getNamedItem("join").getNodeValue());
				        		   
				        		   str=attrs.getNamedItem("feDb1").getNodeValue();
				        		   str1=attrs.getNamedItem("feDb2").getNodeValue();

					        		qryGridSC=new QRY_GRIDSC();
					        		qryGridSC.setENTITY_DB_NAME(condCO.getEntity1().getENTITY_DB_NAME());
					       			qryGridSC.setFIELD_NAME(str);
					       			List<IRP_FIELDSCO> field1 = retDBFields(qryGridSC);
					       			addObjsIndex(field1);
					       			if(field1==null || field1.isEmpty())
					       			{
					       			    condCO.setField1(null);
					       			}
					       			else
					       			{
					       			    condCO.setField1(field1.get(0));
					       			}
					       			
					       			qryGridSC=new QRY_GRIDSC();
					        		qryGridSC.setENTITY_DB_NAME(condCO.getEntity2().getENTITY_DB_NAME());
					       			qryGridSC.setFIELD_NAME(str1);
					       			List<IRP_FIELDSCO> field2 = retDBFields(qryGridSC);
					       			addObjsIndex(field2);
					       			if(field2==null || field2.isEmpty())
					       			{
					       			    condCO.setField2(null);
					       			}
					       			else
					       			{
					       			    condCO.setField2(field2.get(0));
					       			}
					        		   
				        		   sqlObj.getJoinsList().put(condCO.getIndex(), condCO);
					        		   
				        	   }
				        	   
				           }
				           
				           //fill conditions
				           parentNodeLst = qryElem.getElementsByTagName("conds");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("cond");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				        		   
				        		   condCO=new CONDITION_OBJECT();
				        		   condCO.setIndex((Long.parseLong(attrs.getNamedItem("id").getNodeValue())));
				        		   str3=attrs.getNamedItem("conj").getNodeValue();
				        		   if("null".equals(str3))
				        		   {
				        			   str3="";
				        		   }
				        		   condCO.setConjunction(str3);
				        		   
				        		   str=attrs.getNamedItem("entId").getNodeValue();
				        		   //get the entityCO from the entCO
				        		   entCO=sqlObj.getEntities().get(Long.valueOf(str));
				        		   condCO.setEntity1(entCO);
				        		   
				        		   //return the feCO from the feDbName and the entDbName
				        		   str1=attrs.getNamedItem("feDb").getNodeValue();
				        		   qryGridSC = new QRY_GRIDSC();
				        		   qryGridSC.setFIELD_NAME(str1);
				        		   qryGridSC.setENTITY_DB_NAME(entCO.getENTITY_DB_NAME());
				        		   qryGridSC.setPARENT_ID(Long.valueOf("0"));
				        		   List<IRP_FIELDSCO> field1 = retDBFields(qryGridSC);
					       		   if(field1==null || field1.isEmpty())
					       			{
					       		       		condCO.setField1(null);
					       			}
					       			else
					       			{
					       				condCO.setField1(field1.get(0));
					       			}
				        		   
					       		   condCO.setLparenthesis(attrs.getNamedItem("lP").getNodeValue());
					       		   condCO.setRparenthesis(attrs.getNamedItem("rP").getNodeValue());
					       		   
					       		   //check if the argId exist , then get the argCO from the argId 
					       		   if(attrs.getNamedItem("arg1")!=null)
				       			   {
					       			   str=attrs.getNamedItem("arg1").getNodeValue();
					       			   condCO.setArgument1(sqlObj.getArgumentsList().get(Long.valueOf(str)));
				       			   }
					       		   if(attrs.getNamedItem("arg2")!=null)
					       		   {
					       			   str1=attrs.getNamedItem("arg2").getNodeValue();
					       			   condCO.setArgument2(sqlObj.getArgumentsList().get(Long.valueOf(str1)));
					       		   }
					       		   
					       		   if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("val");
				        			   ch2NodeLst =childElem.getElementsByTagName("oper");
				        			   ch3NodeLst =childElem.getElementsByTagName("operName");
				        			   condCO.setValue(ch1NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   condCO.setOperator(ch2NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   condCO.setOperatorName(ch3NodeLst.item(0).getChildNodes().item(0).getNodeValue());
						           }
				        		   sqlObj.getConditionsList().put(condCO.getIndex(), condCO);
				        	   }
				           }
				           
				           //fill having
				           parentNodeLst = qryElem.getElementsByTagName("havings");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("having");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				        		   
				        		   condCO=new CONDITION_OBJECT();
				        		   condCO.setIndex((Long.parseLong(attrs.getNamedItem("id").getNodeValue())));
				        		   str3=attrs.getNamedItem("conj").getNodeValue();
				        		   if("null".equals(str3))
				        		   {
				        			   str3="";
				        		   }
				        		   condCO.setConjunction(str3);
					       		   condCO.setLparenthesis(attrs.getNamedItem("lP").getNodeValue());
					       		   condCO.setRparenthesis(attrs.getNamedItem("rP").getNodeValue());
					       		   
					       		   str=attrs.getNamedItem("feAggrId").getNodeValue();
					       		   condCO.setField1(sqlObj.getAggregateFields().get(Long.valueOf(str)));
					       		   
					       		   
					       		   //return the argCO from the argId
				        		   if(attrs.getNamedItem("arg1")!=null)
				       			   {
					       			   str=attrs.getNamedItem("arg1").getNodeValue();
					       			   condCO.setArgument1(sqlObj.getArgumentsList().get(Long.valueOf(str)));
				       			   }
					       		   if(attrs.getNamedItem("arg2")!=null)
					       		   {
					       			   str=attrs.getNamedItem("arg2").getNodeValue();
					       			   condCO.setArgument2(sqlObj.getArgumentsList().get(Long.valueOf(str)));
					       		   }
					       		   if(attrs.getNamedItem("entId2")!=null)
					       		   {
					       			   str=attrs.getNamedItem("entId2").getNodeValue();
					       			   str1=attrs.getNamedItem("feDb2").getNodeValue();
					       			   //get entCO from the entId
					       			   condCO.setEntity2(sqlObj.getEntities().get(Long.valueOf(str)));
					       			   
					       			   //return the fieldCO from the entDbName and the the feDbName
					       			   qryGridSC = new QRY_GRIDSC();
					        		   qryGridSC.setFIELD_NAME(str1);
					        		   qryGridSC.setENTITY_DB_NAME(condCO.getEntity2().getENTITY_DB_NAME());
					        		   qryGridSC.setPARENT_ID(Long.valueOf("0"));
					        		   List<IRP_FIELDSCO> field = retDBFields(qryGridSC);
						       		   if(field==null || field.isEmpty())
						       			{
						       		       		condCO.setField2(null);
						       			}
						       			else
						       			{
						       				condCO.setField2(field.get(0));
						       			}
					       		   }
					       		   
					       		 if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("val");
				        			   ch2NodeLst =childElem.getElementsByTagName("oper");
				        			   ch3NodeLst =childElem.getElementsByTagName("operName");
				        			   condCO.setValue(ch1NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   condCO.setOperator(ch2NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   condCO.setOperatorName(ch3NodeLst.item(0).getChildNodes().item(0).getNodeValue());
						           }
				        		   
				        		   sqlObj.getHavingList().put(condCO.getIndex(), condCO);
				        	   }
				           }
				           
				           //fill group by
				           parentNodeLst = qryElem.getElementsByTagName("grpsBy");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("grpBy");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				        		   str=attrs.getNamedItem("id").getNodeValue();
				        		   //fill the grpByHm from the fields map following the feId
				        		   sqlObj.getGroupByHM().put(Long.valueOf(str), sqlObj.getFields().get(Long.valueOf(str)));
				        	   }
				           }
				       }
	               }
	               //if syntax query
	               else 
	               {
	            	   if(qry.getNodeType() == Node.ELEMENT_NODE)
				       {
		            	   qryElem = (Element) qry;
		            	   //fill fields
				           parentNodeLst = qryElem.getElementsByTagName("fields");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("field");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				        		   
				        		   feCO=new IRP_FIELDSCO();
				        		   feCO.setIndex(Long.valueOf(attrs.getNamedItem("id").getNodeValue()));
				        		   feCO.setFIELD_DB_NAME(attrs.getNamedItem("dbName").getNodeValue());
				        		   feCO.setFIELD_DATA_TYPE(attrs.getNamedItem("feType").getNodeValue());
				        		   feCO.setType(attrs.getNamedItem("type").getNodeValue());
				        		   feCO.setLabelAlias(attrs.getNamedItem("lblAlias").getNodeValue());
				        		   feCO.setIsLeaf("true");
				        		   feCO.setLevel("3");
				        		   if(attrs.getNamedItem("hasBusName")!=null && ("0").equals(attrs.getNamedItem("hasBusName").getNodeValue()))
				        		   {
				        			   feCO.setHasBusinessName(0);
				        		   }
				        		   
				        		   if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("alias");
				        			   feCO.setFIELD_ALIAS(ch1NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   feCO.setFeName(feCO.getFIELD_ALIAS());
				        			   ch2NodeLst =childElem.getElementsByTagName("grp");
				        			   if(ch2NodeLst.getLength()>0)
				        			   {
				        				   feCO.setGroupName(ch2NodeLst.item(0).getChildNodes().item(0).getNodeValue());
				        			   }
						           }
				        		   
				        		   sqlObj.getFields().put(feCO.getIndex(), feCO);
				        		   if("1".equals(attrs.getNamedItem("sel").getNodeValue()))
				        		   {
				        			   sqlObj.getDisplayFields().put(feCO.getIndex(), feCO);
				        		   }
				        		   
			        		   }
				           }
				           
				           //fill arguments
				           parentNodeLst = qryElem.getElementsByTagName("args");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("arg");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				       			
				        		   argCO=new IRP_REP_ARGUMENTSCO();
				        		   argCO.setIndex(Long.parseLong(attrs.getNamedItem("id").getNodeValue()));
				        		   argCO.setARGUMENT_NAME(attrs.getNamedItem("name").getNodeValue());
				        		   argCO.setARGUMENT_TYPE(attrs.getNamedItem("type").getNodeValue());
				        		   argCO.setARGUMENT_SOURCE(new BigDecimal(attrs.getNamedItem("src").getNodeValue()));
				        		   argCO.setSESSION_ATTR_NAME(attrs.getNamedItem("sess").getNodeValue());
				        		   if(attrs.getNamedItem("qryId")!=null  && !"null".equals(attrs.getNamedItem("qryId").getNodeValue()))
				        		   {
				        			   argCO.setQUERY_ID(new BigDecimal(attrs.getNamedItem("qryId").getNodeValue()));
				        		   }
				        		   if(attrs.getNamedItem("linkRepQryArg")!=null)// && !"null".equals(attrs.getNamedItem("linkRepQryArg").getNodeValue()))
				        		   {
				        		       argCO.setLINK_REP_QRY_ARG(attrs.getNamedItem("linkRepQryArg").getNodeValue());
				        		   }
//				        		   else
//				        		   {
//				        		       argCO.setLINK_REP_QRY_ARG(""); 
//				        		   }
				        		   argCO.getARGUMENT_ID();
				        		   if("null".equals(sqlObj.getOutputLayout()))//to link the qry arg to rep arg on creation of a new rep from designer
				        		   {
				        		       argCO.setLINK_REP_QRY_ARG(argCO.getARGUMENT_NAME());
				        		   }
				        		   argCO.setQUERY_NAME(attrs.getNamedItem("qryName").getNodeValue());
				        		   str=attrs.getNamedItem("col").getNodeValue();
				        		   if("null".equalsIgnoreCase(str))
				        		   {
				        			   str=null;
				        		   }
				        		   argCO.setCOLUMN_NAME(str);
				        		   argCO.setValueFlag(Boolean.valueOf(attrs.getNamedItem("valFlg").getNodeValue()));
				        		   argCO.setARGUMENT_ORDER(new BigDecimal(attrs.getNamedItem("order").getNodeValue()));
				        		   argCO.setDISPLAY_FLAG(attrs.getNamedItem("displ").getNodeValue());
				        		   argCO.setENABLE_FLAG(attrs.getNamedItem("enable").getNodeValue());
				        		   if(attrs.getNamedItem(RepConstantsCommon.REP_MULTISEL)!=null)
				        		   {
				        		       argCO.setMULTISELECT_YN(new BigDecimal(attrs.getNamedItem(RepConstantsCommon.REP_MULTISEL).getNodeValue()));
				        		   }
				        		   if(childNode.getNodeType()==Node.ELEMENT_NODE)
						           {
				        			   childElem=(Element)childNode;
				        			   ch1NodeLst =childElem.getElementsByTagName("lbl");
				        			   ch2NodeLst =childElem.getElementsByTagName("val");
				        			   String argLabel="";
				        			   String argValue="";
				        			   argLblNode=ch1NodeLst.item(0).getFirstChild();
				        			   argValNode=ch2NodeLst.item(0).getFirstChild();
                                				    if(argLblNode != null)
                                				    {
                                					argLabel = argLblNode.getNodeValue();
                                				    }
                                				    if(argValNode != null)
                                				    {
                                					argValue = argValNode.getNodeValue();
                                				    }
				        			  argCO.setARGUMENT_LABEL(argLabel);
				        			  argCO.setARGUMENT_VALUE(argValue);
						           }
				        		  sqlObj.getArgumentsList().put(argCO.getIndex(), argCO); 
				        	   }
				           }
				           
				           //fill syntax
				           parentNodeLst = qryElem.getElementsByTagName("syntax");
		            	   parentNode=parentNodeLst.item(0);
				           if(parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("val");
			        		   childNode=childNodeLst.item(0);
			        		   sqlObj.setSqbSyntax(new StringBuffer(childNode.getChildNodes().item(0).getNodeValue()));
				           }
				            
				           //fill group by
				           parentNodeLst = qryElem.getElementsByTagName("grpsBy");
				           parentNode=parentNodeLst.item(0);
				           if(parentNode!=null && parentNode.getNodeType()==Node.ELEMENT_NODE)
				           {
				        	   parentElem=(Element)parentNode;
				        	   childNodeLst=parentElem.getElementsByTagName("grpBy");
				        	   for(j=0;j<childNodeLst.getLength();j++)
				        	   {
				        		   childNode=childNodeLst.item(j);
				        		   attrs = childNode.getAttributes();
				        		   str=attrs.getNamedItem("id").getNodeValue();
				        		   //fill the grpByHm from the fields map following the feId
				        		   sqlObj.getGroupByHM().put(Long.valueOf(str), sqlObj.getFields().get(Long.valueOf(str)));
				        	   }
				           }
		               }
	               }
	               
	               
	    
	               
	               
			   }
			
		} catch (Exception e) 
		{
			log.error(e, e.getMessage());
		}
	
		return sqlObj;
	}
	
	public void addObjsIndex(List<IRP_FIELDSCO> lst)
    {
		//add index to each obj since sybase does not have a conversion of rownum
		IRP_FIELDSCO feCO;
		for(int i=0;i<lst.size();i++)
		{
		    feCO=lst.get(i);
		    feCO.setIndex(Long.valueOf(i));
		}
    }
	
	/**
	 * Method that convert (list of argument and query syntax)from xml to sql 
	 * @param qryObj
	 * @return
	 */
	  private SQL_OBJECT retXmlToSqlObject(byte[] qryObj)
	    {
		SQL_OBJECT sqlObj = new SQL_OBJECT();
		try
		{
		    DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		    Document doc = dBuilder.parse(new ByteArrayInputStream(qryObj));

		    NodeList qryLst = doc.getElementsByTagName("qry");

		    NodeList parentNodeLst;
		    NodeList childNodeLst;
		    NodeList ch1NodeLst;
		    NodeList ch2NodeLst;
		    
		    Node qry;
		    Node childNode;
		    Node parentNode;
		    Node argLblNode;
		    Node argValNode;
			   
		    Element parentElem;
		    Element qryElem;
		    Element childElem;

		    NamedNodeMap attrs;

		    String str;
		    IRP_REP_ARGUMENTSCO argCO;
		    int j;

		    for(int i = 0; i < qryLst.getLength(); i++)
		    {
			qry = qryLst.item(i);

			// fill qryName and outputLayout
			attrs = qry.getAttributes();
			if(attrs.getNamedItem("name") == null)
			{
			    qryElem = (Element) qry;
			    childNodeLst = qryElem.getElementsByTagName("name");
			    childNode = childNodeLst.item(0);
			    sqlObj.setQUERY_NAME(childNode.getChildNodes().item(0).getNodeValue());
			}
			else
			{
			    sqlObj.setQUERY_NAME(attrs.getNamedItem("name").getNodeValue());
			}
			sqlObj.setOutputLayout(attrs.getNamedItem("outputLayout").getNodeValue());
			str = attrs.getNamedItem("type").getNodeValue();

			if(qry.getNodeType() == Node.ELEMENT_NODE)
			{
			    qryElem = (Element) qry;

			  //fill arguments
			  parentNodeLst = qryElem.getElementsByTagName("args");
			  parentNode=parentNodeLst.item(0);
			  if(parentNode.getNodeType()==Node.ELEMENT_NODE)
			    {
				parentElem = (Element) parentNode;
				childNodeLst = parentElem.getElementsByTagName("arg");
				for(j = 0; j < childNodeLst.getLength(); j++)
				{
				    childNode = childNodeLst.item(j);
				    attrs = childNode.getAttributes();

				    argCO = new IRP_REP_ARGUMENTSCO();
				    argCO.setIndex(Long.parseLong(attrs.getNamedItem("id").getNodeValue()));
				    argCO.setARGUMENT_NAME(attrs.getNamedItem("name").getNodeValue());
				    argCO.setARGUMENT_TYPE(attrs.getNamedItem("type").getNodeValue());
				    argCO.setARGUMENT_SOURCE(new BigDecimal(attrs.getNamedItem("src").getNodeValue()));
				    argCO.setSESSION_ATTR_NAME(attrs.getNamedItem("sess").getNodeValue());
				    if(attrs.getNamedItem("qryId") != null
					    && !"null".equals(attrs.getNamedItem("qryId").getNodeValue()))
				    {
					argCO.setQUERY_ID(new BigDecimal(attrs.getNamedItem("qryId").getNodeValue()));
				    }
				    if(attrs.getNamedItem("linkRepQryArg") != null)
				    {
					argCO.setLINK_REP_QRY_ARG(attrs.getNamedItem("linkRepQryArg").getNodeValue());
				    }
				    argCO.getARGUMENT_ID();
				    if("null".equals(sqlObj.getOutputLayout()))// to
								/*
								 * link  the  qry  arg to  rep  arg on 
								 * creation  of a  new  rep  from 
								 * designer
								 */
				    {
					argCO.setLINK_REP_QRY_ARG(argCO.getARGUMENT_NAME());
				    }
				    argCO.setQUERY_NAME(attrs.getNamedItem("qryName").getNodeValue());
				    str = attrs.getNamedItem("col").getNodeValue();
				    if("null".equalsIgnoreCase(str))
				    {
					str = null;
				    }
				    argCO.setCOLUMN_NAME(str);
				    argCO.setValueFlag(Boolean.valueOf(attrs.getNamedItem("valFlg").getNodeValue()));
				    argCO.setARGUMENT_ORDER(new BigDecimal(attrs.getNamedItem("order").getNodeValue()));
				    argCO.setDISPLAY_FLAG(attrs.getNamedItem("displ").getNodeValue());
				    argCO.setENABLE_FLAG(attrs.getNamedItem("enable").getNodeValue());
				    if(attrs.getNamedItem("multiSel") != null)
				    {
					argCO.setMULTISELECT_YN(new BigDecimal(
						attrs.getNamedItem("multiSel").getNodeValue()));
				    }
				    if(childNode.getNodeType() == Node.ELEMENT_NODE)
				    {
					childElem = (Element) childNode;
					ch1NodeLst = childElem.getElementsByTagName("lbl");
					ch2NodeLst = childElem.getElementsByTagName("val");
					String argLabel = "";
					String argValue = "";
					argLblNode = ch1NodeLst.item(0).getFirstChild();
					argValNode = ch2NodeLst.item(0).getFirstChild();
					if(argLblNode != null)
					{
					    argLabel = argLblNode.getNodeValue();
					}
					if(argValNode != null)
					{
					    argValue = argValNode.getNodeValue();
					}
					argCO.setARGUMENT_LABEL(argLabel);
					argCO.setARGUMENT_VALUE(argValue);
				    }
				    sqlObj.getArgumentsList().put(argCO.getIndex(), argCO);
				}
			    }
			  	// fill syntax
			    parentNodeLst = qryElem.getElementsByTagName("syntax");
			    parentNode = parentNodeLst.item(0);
			    if(parentNode.getNodeType() == Node.ELEMENT_NODE)
			    {
				parentElem = (Element) parentNode;
				childNodeLst = parentElem.getElementsByTagName("val");
				childNode = childNodeLst.item(0);
				sqlObj.setSqbSyntax(new StringBuffer(childNode.getChildNodes().item(0).getNodeValue()));
			    }
			}
		    }
		}
		catch(Exception e)
		{
		    log.error(e, e.getMessage());
		}

		return sqlObj;
	    }

	@Override
	public void insertQueryExecLog_NewTrans(IRP_QUERY_EXEC_LOGVO exec_LOGVO) throws BaseException 
	{
		queryDAO.insertQueryExecLog(exec_LOGVO);
	}

	@Override
	public void updateQueryExecLog(IRP_QUERY_EXEC_LOGVO exec_LOGVO) throws BaseException 
	{
		genericDAO.update(exec_LOGVO);
	}
}
