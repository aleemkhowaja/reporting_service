package com.path.bo.reporting.designer.querybuilder;

import java.util.LinkedHashMap;
import java.util.Map.Entry;


import com.path.lib.log.Log;
import com.path.vo.reporting.CONDITION_OBJECT;
import com.path.vo.reporting.IRP_ENTITIESCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.SQL_OBJECT;


public final class QueryBuilder {
	
    private static final String SELECT = "SELECT";
    private static final String FROM   = "FROM";
    private static final String WHERE  = "WHERE";
    private static final String HAVING = "HAVING";
    private static final String GROUP  = "GROUP BY";
    private static final String ORDER  = "ORDER BY";
    
    private static final Log log = Log.getInstance();
    
    private QueryBuilder() 
    {
    	Log.getInstance().error("This Class Should not be Instantiated");
    }
    
    /**
     * create sql syntax from sql object
     * @param sql
     */
    public static StringBuffer buildQuery(SQL_OBJECT sql)
    {	    		
    	if(sql.getEntities() == null)
    	{
    		return new StringBuffer("");
    	}
    	
    	if(sql.getEntities() != null && sql.getEntities().size() == 0)
    	{
    		return new StringBuffer("");
    	}
    	
    	StringBuffer sqlSyntax    = new StringBuffer(SELECT);
    	StringBuffer whereSyntax  = new StringBuffer("\n "+WHERE);
    	StringBuffer groupSyntax  = new StringBuffer("\n "+GROUP);
    	StringBuffer havingSyntax = new StringBuffer("\n "+HAVING);
    	StringBuffer orderSyntax  = new StringBuffer("\n "+ORDER);
    	
    	boolean fieldsExist = false;
    	boolean whereExist = false;
        boolean groupByExist = false;
        boolean havingExist = false;
        boolean orderByExist = false;
        
        String upperStart = "";
        String upperEnd = "";
        String quote = "";

    	LinkedHashMap<Long, IRP_FIELDSCO> fieldsList;
    	IRP_FIELDSCO field;
        if(sql.getFields() != null && sql.getFields().size() > 0)
        {
        	fieldsExist = true;
        	fieldsList = sql.getFields();        	
        	
        	for(Entry<Long, IRP_FIELDSCO> entry : fieldsList.entrySet()){
        		field = entry.getValue();
            	if(field.getType().equals("1")) { // simple field
    	        	sqlSyntax.append(" "+field.getFieldSyntax())
    	        	         .append(" AS " + field.getLabelAlias().replaceAll(" ", "") )
    	        		     .append(",");
    	        	
    	        	if(field.getOrder() != null && !field.getOrder().equals("")) {
    	        		orderByExist = true;
    	        		orderSyntax.append(" "+field.getFieldSyntax()+" "+field.getOrder()).append(",");
    	        	}
    	        	
    	        	//append groupSyntax to sqlSyntax only if there are aggregate fields
    	        	groupSyntax.append(" "+field.getFieldSyntax()).append(",");
            	}
            	else if(field.getType().equals("2")){ // based on a subquery
            		sqlSyntax.append(" (").append(buildQuery(field.getSql()))        		
            		         .append(")").append(" AS ").append(field.getLabelAlias().replaceAll(" ", ""))
            		         .append(",");
            		
            		if(field.getOrder() != null && !field.getOrder().equals("")) {
    	        		orderByExist = true;
    	        		orderSyntax.append(" "+field.getLabelAlias().replaceAll(" ", "")+" "+field.getOrder()).append(",");
    	        	}
            	}        	
            }
        }
    	
    	if(sql.getExpressionFields() != null && sql.getExpressionFields().size() > 0)
    	{
    		fieldsExist = true;
        	fieldsList = sql.getExpressionFields();
        	
        	for(Entry<Long, IRP_FIELDSCO> entry : fieldsList.entrySet()){
        		field = entry.getValue();
            	sqlSyntax.append(" (").append(field.getFieldSyntax())
            			 .append(")").append(" AS ").append(field.getLabelAlias().replaceAll(" ", ""))
            		     .append(",");
            	
            	if(field.getOrder() != null && !field.getOrder().equals("")) {
            		orderByExist = true;
            		orderSyntax.append(" "+field.getLabelAlias().replaceAll(" ", "")+" "+field.getOrder()).append(",");
            	}
        	}
    	}
    	
    	if(sql.getAggregateFields() != null && sql.getAggregateFields().size() > 0)
    	{
    		fieldsExist = true;
    		groupByExist = true;
        	fieldsList = sql.getAggregateFields();
        	
        	for(Entry<Long, IRP_FIELDSCO> entry : fieldsList.entrySet()){ 
        		field = entry.getValue();
            	sqlSyntax.append(" (").append(field.getFieldSyntax())
            			 .append(")").append(" AS ").append(field.getLabelAlias().replaceAll(" ", ""))
            		     .append(",");
            	
            	if(field.getOrder() != null && !field.getOrder().equals("")) {
            		orderByExist = true;
            		orderSyntax.append(" "+field.getLabelAlias().replaceAll(" ", "")+" "+field.getOrder()).append(",");
            	}
        	}
    	}
    	
    	int index = -1;
        index = sqlSyntax.lastIndexOf(",");
        if(index > -1)
        {
        	sqlSyntax = sqlSyntax.deleteCharAt(index);
        }
        
        index = groupSyntax.lastIndexOf(",");
        if(index > -1)
        {
        	groupSyntax = groupSyntax.deleteCharAt(index);
        }
        
        index = orderSyntax.lastIndexOf(",");
        if(index > -1)
        {
        	orderSyntax = orderSyntax.deleteCharAt(index);
        }
        
        if(!fieldsExist)
        {
        	sqlSyntax.append(" *");
        }
        
        // FROM
        sqlSyntax.append("\n ").append(FROM);
        
        LinkedHashMap<Long, IRP_ENTITIESCO> entitiesList = sql.getEntities();
    	IRP_ENTITIESCO entity;
        int cnt = 0;
        for(Entry<Long, IRP_ENTITIESCO> entry : entitiesList.entrySet()){ 
        	entity = entry.getValue();
        	cnt++;
        	if(cnt > 1)
        	{
        		sqlSyntax.append(",");
        	}
        	
        	if(entity.getType().equals("1")) { // simple entity
        		sqlSyntax.append(" "+entity.getENTITY_DB_NAME());
        		sqlSyntax.append(" " +entity.getSyntaxAlias());
        	}
        	else if(entity.getType().equals("2")){ // based on a subquery
        		sqlSyntax.append(" (").append(buildQuery(entity.getSql()))
        		         .append(") ").append(entity.getSyntaxAlias());
        	}        	
        }
        
        LinkedHashMap<Long, CONDITION_OBJECT> conditionsList;
        CONDITION_OBJECT condition;      
        if(sql.getJoinsList() != null && sql.getJoinsList().size() > 0)
        {
        	whereExist = true;
        	conditionsList = sql.getJoinsList();
        	
        	for(Entry<Long, CONDITION_OBJECT> entry : conditionsList.entrySet()){
        		condition = entry.getValue();
        		whereSyntax.append(" "+condition.getEntity1().getSyntaxAlias());
        		whereSyntax.append("."+condition.getField1().getFIELD_DB_NAME()+" ");

        		if(condition.getJoin().equals("left")){
        			if(condition.getEntity1().getIsOracle() == 1)
        			{
        				whereSyntax.append("(+) ");
        			}
        			else if(condition.getEntity1().getIsSybase() == 1)
        			{
        				whereSyntax.append(" *");
        			}
        		}
        			
        		whereSyntax.append("=");
        		
        		if(condition.getJoin().equals("right") && condition.getEntity1().getIsSybase() == 1)
        		{
        			whereSyntax.append("* ");
        		}
        		
        		whereSyntax.append(" "+condition.getEntity2().getSyntaxAlias());
        		whereSyntax.append("."+condition.getField2().getFIELD_DB_NAME()+" ");
        		
        		if(condition.getJoin().equals("right") && condition.getEntity1().getIsOracle() == 1)
        		{
        			whereSyntax.append("(+) ");
        		}
        		
        		whereSyntax.append("AND");
        	}  	
        }

        int pos;
        if(sql.getConditionsList() != null && sql.getConditionsList().size() > 0)
        {
        	whereExist = true;
        	pos = 0;
        	conditionsList = sql.getConditionsList();
        	
        	for(Entry<Long, CONDITION_OBJECT> entry : conditionsList.entrySet()){
        		condition = entry.getValue();
        		pos++;
        		if(pos > 1)
        		{
        			whereSyntax.append(condition.getConjunction() == null?  "": " "+condition.getConjunction()  );
        		}
        		whereSyntax.append(condition.getLparenthesis() == null?  "" : " "+condition.getLparenthesis() );
	        	
	        	if(condition.getField1().getFIELD_DATA_TYPE().equals("java.lang.String") 
	        			&& !condition.getOperator().equals("in")){
	        		upperStart = "upper(";
	                upperEnd = ")";
	                quote = "'"; 
	        	}
	        	else{
	        		upperStart = "";
	                upperEnd = "";
	                quote = ""; 
	        	}
	        		
	        	whereSyntax.append(" "+upperStart);
	        	
	        	if(condition.getEntity1() == null) 
	        	{
	        		if(condition.getField1().getType().equals("2"))
	        		{
	        			whereSyntax.append("("+buildQuery(condition.getField1().getSql())+")");
	        		}
	        		else
	        		{
	        			whereSyntax.append("("+condition.getField1().getFieldSyntax()+")");
	        		}
	        	}
	        	else 
	        	{
		        	whereSyntax.append(condition.getEntity1().getSyntaxAlias()
  			              +"."
  			              +condition.getField1().getFIELD_DB_NAME()
  			           );

	        	}
	        	
	        	whereSyntax.append(upperEnd);
	        	
	        	if(condition.getOperator()!=null && condition.getOperator().equals("bet"))
	        	{
	        		whereSyntax.append(" BETWEEN");
	        	}
	        	else if(condition.getOperator().equals("startsWith") || condition.getOperator().equals("endsWith") || condition.getOperator().equals("contains"))
	        	{
	        		whereSyntax.append(" LIKE");
	        	}
	        	else
	        	{
	        		whereSyntax.append(" "+condition.getOperator());
	        	}
	        	if(condition.getArgument1() == null)
	        	{
		        	if(condition.getValue() != null && !condition.getValue().equals("") && condition.getOperator().equals("startsWith"))
		        	{
		        		whereSyntax.append(" " + upperStart + quote + condition.getValue()+"%" + quote + upperEnd);
		        	}
		        	else if(condition.getValue() != null && !condition.getValue().equals("") && condition.getOperator().equals("endsWith"))
		        	{
		        		whereSyntax.append(" " + upperStart + quote + "%"+condition.getValue() + quote + upperEnd);
		        	}
		        	else if(condition.getValue() != null && !condition.getValue().equals("") && condition.getOperator().equals("contains"))
		        	{
		        		whereSyntax.append(" " + upperStart + quote + "%"+condition.getValue()+"%" + quote + upperEnd);
		        	}
		        	else if(condition.getValue() != null && !condition.getValue().equals(""))
		        	{
		        		whereSyntax.append(" " + upperStart + quote + condition.getValue() + quote + upperEnd);
		        	}
	        	}
	        	else
	        	{
	        	    	if(condition.getOperator().equals("startsWith"))
	        	    	{
	        	    	    //if(condition.getArgument1().getARGUMENT_TYPE().equals("VARCHAR2"))
	        	    	    {
	        	    		whereSyntax.append(" " + upperStart + quote +  "$P!{" + condition.getArgument1().getARGUMENT_NAME() + "}" +"%" + quote + upperEnd);      
	        	    	    }
//	        	    	    else
//	        	    	    {
//	        	    		whereSyntax.append(" " + upperStart + quote +  "$P{" + condition.getArgument1().getARGUMENT_NAME() + "}" +"%" + quote + upperEnd);
//	        	    	    }
	        	    	}
	        	    	else if(condition.getOperator().equals("endsWith"))
	        	    	{
	        	    	    //if(condition.getArgument1().getARGUMENT_TYPE().equals("VARCHAR2"))
	        	    	    {
	        	    		whereSyntax.append(" " + upperStart + quote + "%" + "$P!{" + condition.getArgument1().getARGUMENT_NAME() + "}" + quote + upperEnd);      
	        	    	    }
//	        	    	    else
//	        	    	    {
//	        	    		whereSyntax.append(" " + upperStart + quote + "%" + "$P{" + condition.getArgument1().getARGUMENT_NAME() + "}" + quote + upperEnd);
//	        	    	    }
	        	    	}
	        	    	else if(condition.getOperator().equals("contains"))
	        	    	{
	        	    	    //if(condition.getArgument1().getARGUMENT_TYPE().equals("VARCHAR2"))
	        	    	    {
	        	    		whereSyntax.append(" " + upperStart + quote + "%" + "$P!{" + condition.getArgument1().getARGUMENT_NAME() + "}" +"%" + quote + upperEnd);      
	        	    	    }
//	        	    	    else
//	        	    	    {
//	        	    		whereSyntax.append(" " + upperStart + quote + "%" + "$P{" + condition.getArgument1().getARGUMENT_NAME() + "}" + quote + upperEnd);
//	        	    	    }
	        	    	}
	        	    	else
	        	    	{
	        	    	    //if(condition.getArgument1().getARGUMENT_TYPE().equals("VARCHAR2"))
	        	    	    {
	        	    		whereSyntax.append(" " + upperStart + quote + "$P{" + condition.getArgument1().getARGUMENT_NAME() + "}" + quote + upperEnd);      
	        	    	    }
//	        	    	    else
//	        	    	    {
//	        	    		whereSyntax.append(" " + upperStart + quote + "%" + "$P{" + condition.getArgument1().getARGUMENT_NAME() + "}" + quote + upperEnd);
//	        	    	    }
	        	    	}
	        	    	
	        	}
	        	whereSyntax.append(condition.getArgument2() == null? "": (" and $P{"+condition.getArgument2().getARGUMENT_NAME() + "}") );
	        	
	        	whereSyntax.append(condition.getRparenthesis() == null? "":" "+condition.getRparenthesis() );
	        }
        }
	        
	    if(sql.getHavingList() != null && sql.getHavingList().size() > 0)
	    {
	    	havingExist = true;
	    	conditionsList = sql.getHavingList();
	    	pos = 0;
	    	for(Entry<Long, CONDITION_OBJECT> entry : conditionsList.entrySet()){
        		condition = entry.getValue();
        		pos++;
        		if(pos > 1)
        		{
        			havingSyntax.append(condition.getConjunction() == null? "" : " "+condition.getConjunction() );
        		}
	    		havingSyntax.append(condition.getLparenthesis() == null? "": " "+condition.getLparenthesis());
	    		
	    		havingSyntax.append(" "+condition.getField1().getFieldSyntax());
	    		havingSyntax.append(" "+condition.getOperator()+" ");
	    		
	    		if(condition.getEntity2() == null) 
	    		{
		        	if(condition.getField2() != null) 
		        	{
		        		if(condition.getField2().getType().equals("2"))
		        		{
		        			havingSyntax.append("("+buildQuery(condition.getField2().getSql())+")");
		        		}
		        		else
		        		{
		        			havingSyntax.append("("+condition.getField2().getFieldSyntax()+")");
		        		}
		        	}	    		    
	    		}
	    		else
	    		{
	    			havingSyntax.append(condition.getEntity2().getSyntaxAlias()
		        			              +"."
		        			              +condition.getField2().getFIELD_DB_NAME()
		        			           );
	        	}

	    		
	    		if(condition.getArgument1() == null)
	    		{
	    		    if(condition.getValue() != null && !condition.getValue().equals(""))
	    		    {
		    		havingSyntax.append(" "+condition.getValue());
	    		    }
	    		}
	    		else
	    		{
	    			havingSyntax.append(" $P{"+condition.getArgument1().getARGUMENT_NAME() + "}");
	    		}

	    			
	    		havingSyntax.append(condition.getArgument2() == null? "":" and $P{"+condition.getArgument2().getARGUMENT_NAME() + "}" );
	    		havingSyntax.append(condition.getRparenthesis() == null?  "" : " "+condition.getRparenthesis() );
	    	}
	    }
	   
        if(whereExist){
        	if(whereSyntax.toString().endsWith(" AND"))
        	{
            		whereSyntax = whereSyntax.delete(whereSyntax.lastIndexOf(" AND"), whereSyntax.length());
        	}
        	sqlSyntax.append(" "+whereSyntax);
        }
        	
        if(groupByExist)
        {
        	sqlSyntax.append(" "+groupSyntax);
        }
        if(havingExist)
        {
        	sqlSyntax.append(" "+havingSyntax);
        }
        if(orderByExist)
        {
        	sqlSyntax.append(" "+orderSyntax);
        }
        log.debug("query syntax: "+sqlSyntax.toString());
        return sqlSyntax;        
    }
    
}
