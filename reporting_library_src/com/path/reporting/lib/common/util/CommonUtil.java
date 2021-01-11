package com.path.reporting.lib.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.lib.common.util.FileUtil;
import com.path.lib.log.Log;
import com.path.vo.reporting.CONDITION_OBJECT;
import com.path.vo.reporting.IRP_ENTITIESCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.SQL_OBJECT;

public final class CommonUtil 
{
	public final static String ENCODING = "UTF-8";//Cp1256";
	private CommonUtil() 
	{
	    Log.getInstance().error("This Class Should not be Instantiated");
	}
    public static Object getCopyObject(Object orig) throws Exception
    {
       Object obj = null;
       try 
       {
    	   ByteArrayOutputStream bos = new ByteArrayOutputStream();
    	   ObjectOutputStream oos = new ObjectOutputStream(bos);
    	   oos.writeObject(orig);
    	   oos.flush();
    	   oos.close();
    	   bos.close();
    	   byte [] byteData = bos.toByteArray();

    	   ByteArrayInputStream bais = new ByteArrayInputStream(byteData);
    	   obj = new ObjectInputStream(bais).readObject();
       }
       catch(IOException e) 
       {
           e.printStackTrace();
       }
       catch(ClassNotFoundException cnfe) 
       {
           cnfe.printStackTrace();
       }
       return obj;
   }
	
    public static byte[] objectToBytes(Serializable obj) throws IOException 
    {
        byte[] bytes;
        ByteArrayOutputStream baos = new ByteArrayOutputStream(512);
        ObjectOutputStream out = new ObjectOutputStream(baos);
        out.writeObject(obj);
        out.close();
        bytes = baos.toByteArray();
        return bytes;
    }
    
    public static Serializable objectFromBytes(byte[] bytes) throws IOException, ClassNotFoundException 
    {
        ObjectInputStream in = new ObjectInputStream(new ByteArrayInputStream(bytes));
        Serializable obj = (Serializable) in.readObject();
        in.close();
        return obj;
    }
    
	public static byte[] objectToBytes(SQL_OBJECT sqlObj)
	{
		StringBuffer xml=new StringBuffer("");
		try
		{
			xml.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			xml.append("<query>");
			xml.append("<qry outputLayout=\""+sqlObj.getOutputLayout()+"\"");
			//check if it is a designer (type=0) or syntax query (type=1) ;
			if(sqlObj.getEntities()!=null && sqlObj.getEntities().size()>0)
			{
				xml.append(" type=\"0\" >");
				xml.append("<name><![CDATA["+sqlObj.getQUERY_NAME()+"]]></name>");
				//fill entities
				entitiesToXML(xml,sqlObj.getEntities());
				//fill fields
				fieldsToXML(xml,sqlObj);
				//fill expressions
				expressionsToXML(xml,sqlObj.getExpressionFields());
				//fill aggregates
				aggregatesToXML(xml,sqlObj.getAggregateFields());
				//fill arguments
				argumentsToXML(xml,sqlObj.getArgumentsList());
				//fill joins
				joinsToXML(xml,sqlObj.getJoinsList());
				//fill conditions
				conditionsToXML(xml,sqlObj.getConditionsList());
				//fill havings
				havingsToXML(xml,sqlObj.getHavingList());
				//fill groupBy
				groupByToXML(xml,sqlObj.getGroupByHM());
			}
			else
			{
				xml.append(" type=\"1\" >");
				xml.append("<name><![CDATA["+sqlObj.getQUERY_NAME()+"]]></name>");
				//fill fields
				fieldsToXML(xml,sqlObj);
				//fill arguments
				argumentsToXML(xml,sqlObj.getArgumentsList());
				//fill groupBy
				groupByToXML(xml,sqlObj.getGroupByHM());
				//fill query syntax
				syntaxToXML(xml,sqlObj.getSqbSyntax());
			}
			
			xml.append("</qry>");
			xml.append("</query>");

		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		try
		{
		    return xml.toString().getBytes(FileUtil.DEFAULT_FILE_ENCODING);
		}
		catch(UnsupportedEncodingException e)
		{
		   return null;
		}
	}
	
	public static void entitiesToXML(StringBuffer xml,LinkedHashMap<Long, IRP_ENTITIESCO> entitiesMap)
	{
		xml.append("<entities>");
		Long key;
		IRP_ENTITIESCO entCO;
		for(Map.Entry<Long, IRP_ENTITIESCO> e : entitiesMap.entrySet())
		{
		   	key=e.getKey();
		    	entCO=e.getValue();
			xml.append("<entity id=\""+key+"\" type=\""+entCO.getType()+"\" dbName=\""+entCO.getENTITY_DB_NAME()+"\" syntAlias=\""+entCO.getSyntaxAlias()+"\">");
				xml.append("<alias><![CDATA["+entCO.getENTITY_ALIAS()+"]]></alias>");
				xml.append("<aliasWithCnt><![CDATA["+entCO.getEntityAliasWithCount()+"]]></aliasWithCnt>");
			xml.append("</entity>");
		}
		xml.append("</entities>");
	}
	
	public static void fieldsToXML(StringBuffer xml , SQL_OBJECT sqlObj)	
	{
		LinkedHashMap<Long, IRP_FIELDSCO> fieldsMap=sqlObj.getFields();
		LinkedHashMap<Long, IRP_FIELDSCO> selFieldsMap=sqlObj.getDisplayFields();
		xml.append("<fields>");
		Long key;
		IRP_FIELDSCO feCO;
		String order;
		for(Map.Entry<Long, IRP_FIELDSCO> e: fieldsMap.entrySet())
		{
		   	key=e.getKey();
			feCO=e.getValue();
			order=feCO.getOrder();
			if(order==null)
			{
				order="";
			}
			xml.append("<field id=\""+feCO.getIndex()+"\" dbName=\""+feCO.getFIELD_DB_NAME()+"\" feType=\""+feCO.getFIELD_DATA_TYPE()
					+"\" pId=\""+feCO.getPARENT_INDEX()+"\" type=\""+feCO.getType()+"\" lblAlias=\""+feCO.getLabelAlias()
					+"\" order=\""+order+"\" hasBusName=\""+feCO.getHasBusinessName()+"\"");
			if(selFieldsMap.get(key)!=null || selFieldsMap.isEmpty()) //selFieldsMap.size()==0 means that it is an uploaded report				
			{
				xml.append(" sel=\"1\"");
			}
			else
			{
				xml.append(" sel=\"0\"");
			}
			
			xml.append(">");
			xml.append("<alias><![CDATA["+feCO.getFIELD_ALIAS()+"]]></alias>");
			if(feCO.getGroupName()!=null)
			{
				xml.append("<grp><![CDATA["+feCO.getGroupName()+"]]></grp>");
			}
			xml.append("</field>");
		}
		xml.append("</fields>");
	}
	
	public static void expressionsToXML(StringBuffer xml,LinkedHashMap<Long,IRP_FIELDSCO> exprMap)
	{
		xml.append("<exps>");
		IRP_FIELDSCO expCO;
		Iterator it= exprMap.values().iterator();
		while(it.hasNext())
		{
			expCO=(IRP_FIELDSCO) it.next();
			xml.append("<exp id=\""+expCO.getIndex()+"\" feType=\""+expCO.getFIELD_DATA_TYPE()+"\" type=\""+expCO.getType()+"\">");
			xml.append("<feSynt><![CDATA["+expCO.getFieldSyntax()+"]]></feSynt>");
			xml.append("<lblAlias><![CDATA["+expCO.getLabelAlias()+"]]></lblAlias>");
			xml.append("<html><![CDATA["+expCO.getHtml()+"]]></html>");
			xml.append("</exp>");
		}
		xml.append("</exps>");
	}
	
	public static void aggregatesToXML(StringBuffer xml ,LinkedHashMap<Long, IRP_FIELDSCO> aggrMap)
	{
		xml.append("<aggrs>");
		IRP_FIELDSCO aggrCO;
		Iterator it=aggrMap.values().iterator();
		while(it.hasNext())
		{
			aggrCO=(IRP_FIELDSCO) it.next();
			xml.append("<aggr id=\""+aggrCO.getIndex()+"\" aggrFn=\""+aggrCO.getAggregate()+"\" feAlias=\""+aggrCO.getFIELD_ALIAS()
					+"\"  feDb=\""+aggrCO.getFIELD_DB_NAME()+"\" feType=\""+aggrCO.getFIELD_DATA_TYPE()+
					"\" type=\""+aggrCO.getType()+"\"  pId=\""+aggrCO.getPARENT_INDEX()+"\">");
			xml.append("<lblAlias><![CDATA["+aggrCO.getLabelAlias()+"]]></lblAlias>");
			xml.append("<feSynt><![CDATA["+aggrCO.getFieldSyntax()+"]]></feSynt>");
			xml.append("</aggr>");
		}
		xml.append("</aggrs>");
	}
	
	public static void argumentsToXML(StringBuffer xml, LinkedHashMap<Long, IRP_REP_ARGUMENTSCO>  argsMap)
	{
		xml.append("<args>");
		String colName;
		String sessName;
		BigDecimal qryId;
		String qryName;
		String defVal;
		
		IRP_REP_ARGUMENTSCO argCO;
		Iterator it =argsMap.values().iterator();
		while(it.hasNext())
		{
			argCO=(IRP_REP_ARGUMENTSCO) it.next();
			colName=argCO.getCOLUMN_NAME();
			sessName=argCO.getSESSION_ATTR_NAME();
			qryName=argCO.getQUERY_NAME();
			qryId=argCO.getQUERY_ID();
			defVal=argCO.getARGUMENT_VALUE();
			if(qryId==null)
			{
				qryId=ConstantsCommon.EMPTY_BIGDECIMAL_VALUE;
			}
			if(colName==null)
			{
				colName="";
			}
			if(sessName==null)
			{
				sessName="";
			}
			if(qryName==null)
			{
				qryName="";
			}
			if(defVal==null)
			{
				defVal="";
			}
			if(argCO.getMULTISELECT_YN()==null)
			{
			    argCO.setMULTISELECT_YN(BigDecimal.ZERO);
			}
			xml.append("<arg id=\""+argCO.getIndex()+"\" name=\""+argCO.getARGUMENT_NAME()+"\" linkRepQryArg=\""+argCO.getLINK_REP_QRY_ARG()+"\" type=\""+argCO.getARGUMENT_TYPE()
					+"\" src=\""+argCO.getARGUMENT_SOURCE()+"\" sess=\""+sessName+"\" qryId=\""+qryId+
					"\" qryName=\""+qryName+"\" col=\""+colName+"\" valFlg=\""+argCO.isValueFlag()+
					"\" order=\""+argCO.getARGUMENT_ORDER()+"\" displ=\""+argCO.getDISPLAY_FLAG()+"\" enable=\""+argCO.getENABLE_FLAG()+"\" multiSel=\""+argCO.getMULTISELECT_YN()+"\">");
			xml.append("<lbl><![CDATA["+argCO.getARGUMENT_LABEL()+"]]></lbl>");
			xml.append("<val><![CDATA["+defVal+"]]></val>");
			xml.append("</arg>");
		}
		xml.append("</args>");
	}
	
	public static void joinsToXML(StringBuffer xml ,LinkedHashMap<Long, CONDITION_OBJECT> joinsMap)
	{
		xml.append("<joins>");
		CONDITION_OBJECT joinCO;
		Iterator it=joinsMap.values().iterator();
		while(it.hasNext())
		{
			joinCO=(CONDITION_OBJECT) it.next();
			xml.append("<join id=\""+joinCO.getIndex()+"\" entId1=\""+joinCO.getEntity1().getIndex()+"\" entId2=\""+joinCO.getEntity2().getIndex()+
					"\" feDb1=\""+joinCO.getField1().getFIELD_DB_NAME()+"\" feDb2=\""+joinCO.getField2().getFIELD_DB_NAME()+"\" join=\""+joinCO.getJoin()+"\">");
			xml.append("</join>");
		}
		xml.append("</joins>");
	}
	
	public static void conditionsToXML(StringBuffer xml ,LinkedHashMap<Long, CONDITION_OBJECT> condsMap)
	{
		xml.append("<conds>");
		CONDITION_OBJECT condCO;
		Iterator it=condsMap.values().iterator();
		while(it.hasNext())
		{
			condCO=(CONDITION_OBJECT) it.next();
			xml.append("<cond id=\""+condCO.getIndex()+"\" conj=\""+condCO.getConjunction()+"\" entId=\""+condCO.getEntity1().getIndex()+
			   "\" feDb=\""+condCO.getField1().getFIELD_DB_NAME()+"\" lP=\""+condCO.getLparenthesis()+"\" rP=\""+condCO.getRparenthesis()+"\"");
			if(condCO.getArgument1()!=null)
			{
				xml.append(" arg1=\""+condCO.getArgument1().getIndex()+"\"");
			}
			if(condCO.getArgument2()!=null)
			{
				xml.append(" arg2=\""+condCO.getArgument2().getIndex()+"\"");
			}
			xml.append(">");
			xml.append("<val><![CDATA["+condCO.getValue()+"]]></val>");
			xml.append("<oper><![CDATA["+condCO.getOperator()+"]]></oper>");
			xml.append("<operName><![CDATA["+condCO.getOperatorName()+"]]></operName>");
			xml.append("</cond>");
		}
		xml.append("</conds>");
	}
	
	public static void havingsToXML(StringBuffer xml, LinkedHashMap<Long, CONDITION_OBJECT> havingsMap)
	{
		xml.append("<havings>");
		CONDITION_OBJECT havCO;
		Iterator it=havingsMap.values().iterator();
		while(it.hasNext())
		{
			havCO=(CONDITION_OBJECT) it.next();
			xml.append("<having id=\""+havCO.getIndex()+"\" conj=\""+havCO.getConjunction()+"\" lP=\""+havCO.getLparenthesis()+
					"\" rP=\""+havCO.getRparenthesis()+"\" feAggrId=\""+havCO.getField1().getIndex()+"\"");
			if(havCO.getArgument1()!=null)
			{
				xml.append(" arg1=\""+havCO.getArgument1().getIndex()+"\"");
			}
			if(havCO.getArgument2()!=null)
			{
				xml.append(" arg2=\""+havCO.getArgument2().getIndex()+"\"");
			}
			if(havCO.getEntity2()!=null)
			{
				xml.append(" entId2=\""+havCO.getEntity2().getIndex()+"\" feDb2=\""+havCO.getField2().getFIELD_DB_NAME()+"\"");
			}
			xml.append(">");
			xml.append("<val><![CDATA["+havCO.getValue()+"]]></val>");
			xml.append("<oper><![CDATA["+havCO.getOperator()+"]]></oper>");
			xml.append("<operName><![CDATA["+havCO.getOperatorName()+"]]></operName>");
			xml.append("</having>");
		}
		xml.append("</havings>");
	}
	
	public static void groupByToXML(StringBuffer xml, LinkedHashMap<Long, IRP_FIELDSCO> grpByMap)
	{
		xml.append("<grpsBy>");
		IRP_FIELDSCO grpCO;
		Iterator it=grpByMap.values().iterator();
		while(it.hasNext())
		{
			grpCO=(IRP_FIELDSCO) it.next();
			xml.append("<grpBy id=\""+grpCO.getIndex()+"\">");
			xml.append("</grpBy>");
		}
		xml.append("</grpsBy>");
	}
	
	public  static void syntaxToXML(StringBuffer xml,StringBuffer syntax)
	{
		xml.append("<syntax>");
		xml.append("<val><![CDATA["+syntax+"]]></val>");
		xml.append("</syntax>");
	}
	
	public static String returnJavaFieldType(String FieldDataType)
	{
	    String javaFieldType="";
	    if( RepConstantsCommon.NUMBER_TYPE_JASPER.equals(FieldDataType))
	    {
		javaFieldType=ConstantsCommon.PARAM_TYPE_NUMBER;
	    }
	    else if( RepConstantsCommon.DATE_TYPE_JASPER.equals(FieldDataType)    )
	    {
		javaFieldType=ConstantsCommon.PARAM_TYPE_DATE;
	    }
//	    else if(FieldDataType.equals("java.lang.String"))
//	    {
//		javaFieldType="VARCHAR2";
//	    }
	    else
	    {
		javaFieldType= ConstantsCommon.PARAM_TYPE_VARCHAR2;
	    }
	    return javaFieldType;
	    
	}
	
	/**
	     * Method handling multivalue params in JRXML by replacing the $X expression
	     * with sql related values
	     * 
	     * @param sqlString
	     * @param argObj
	     * @param argType
	     */
	    public static String handleMultiValParam(String sqlString, IRP_REP_ARGUMENTSCO argObj, String argType,int dbType)
	    {
		String equalStr;
		String lSqlString = sqlString;
		if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(argType))
		{
		    equalStr = "="+RepConstantsCommon.DB_COMPARISON_NUMBER;
		}
		else if(ConstantsCommon.PARAM_TYPE_DATE.equals(argType))
		{
		    if(dbType==1)
		    {
			equalStr = "="+RepConstantsCommon.SYB_GET_DATE;
		    }
		    else
		    {
			equalStr = "="+RepConstantsCommon.ORA_SYSDATE;
		    }
		}
		else
		{
		    equalStr = "="+RepConstantsCommon.DB_COMPARISON_STRING;
		}
		int indexFrom=0;
		while(lSqlString.indexOf("$X{",indexFrom) != -1 && lSqlString.indexOf(argObj.getARGUMENT_NAME()) != -1)
		{
		    String curParamExpr = lSqlString.substring(lSqlString.indexOf("$X{",indexFrom), lSqlString.length());
		    //curParamExpr contains expression similar to $X{IN, COMP_CODE,multiarg}
		    curParamExpr = curParamExpr.substring(0, curParamExpr.indexOf("}") + 1);
		    if(curParamExpr.indexOf(argObj.getARGUMENT_NAME())==-1)
		    {
			//to start the search from different index when more than one multi parameter
			//in the expression and the found $X expression is not related to the current multi parameter
			indexFrom=lSqlString.indexOf("$X{",indexFrom);
			indexFrom+="$X{".length();
			continue;
		    }
		    String pattern = "\\$X\\{(I)(N)(\\s)*,(\\s)*[a-zA-Z0-9_#.\\$]*(\\s)*," + argObj.getARGUMENT_NAME() + "\\}";
		    //COMP_CODE
		    String sqlExpr = curParamExpr.substring(curParamExpr.indexOf(",") + 1, curParamExpr.indexOf(",",
			    curParamExpr.indexOf(",") + 1));
		    //$X{IN,COUNTRIES_1.COMP_CODE,compCode}
		    if(Pattern.matches(pattern, curParamExpr))
		    {
			lSqlString = lSqlString.replace(curParamExpr, sqlExpr + equalStr);
		    }
		    else
		    {
			lSqlString = lSqlString.replace(argObj.getARGUMENT_NAME(), "")+RepConstantsCommon.VALID_ERROR;
		    }
		    indexFrom=0;
		}
		return lSqlString;
	    }
    
}
