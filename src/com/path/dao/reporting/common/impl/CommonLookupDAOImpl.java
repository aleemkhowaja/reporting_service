package com.path.dao.reporting.common.impl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.designer.querybuilder.QueryBuilder;
import com.path.dao.reporting.common.CommonLookupDAO;
import com.path.dao.reporting.designer.QueryDAO;
import com.path.dbmaps.vo.FTR_OPTVO;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_QUERY_ARG_MAPPINGVO;
import com.path.dbmaps.vo.IRP_REP_ARGUMENTSVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.lib.common.util.StringUtil;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.vo.common.COUNTRYSC;
import com.path.vo.common.CURRENCIESCO;
import com.path.vo.reporting.CONDITION_OBJECT;
import com.path.vo.reporting.DynLookupSC;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.IRP_ENTITIESCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.QRY_GRIDSC;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.filterCriteria.S_ADDITIONS_OPTIONSSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public class CommonLookupDAOImpl extends BaseDAO implements CommonLookupDAO {
	private QueryDAO queryDAO;

	public QueryDAO getQueryDAO() {
		return queryDAO;
	}

	public void setQueryDAO(QueryDAO queryDAO) {
		this.queryDAO = queryDAO;
	}

	public List<Object> getLookupList(Object obj) throws DAOException {
		return getSqlMap().queryForList(getQueryName(obj), obj);
	}

	private String getQueryName(Object obj) throws DAOException {
		String className = obj.getClass().getSimpleName();
		String selectName;
		if (className.lastIndexOf("VO") < 0) {
			throw new DAOException("Class Name should be VO Object");
		} else {
			selectName = className.substring(0, className.lastIndexOf("VO"));
		}
		String queryName = "Lookups.select" + selectName;

		return queryName;
	}

	@Override
	public List<CommonDetailsVO> getFilterCrtList(TmplGridSC tmplGridSC)
			throws DAOException {
		if (tmplGridSC.getSortOrder() == null) {
			tmplGridSC.setSortOrder(" ORDER BY  TBL.CRITERIA_CODE ");
		}
		DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(),
				"Lookups.getFilterCrtListMap");
		return getSqlMap().queryForList(
				"Lookups.getFilterCrtList", tmplGridSC,
				tmplGridSC.getRecToskip(), tmplGridSC.getNbRec());
	}

	@Override
	public int getFilterCrtListCount(TmplGridSC tmplGridSC) throws DAOException {
		DAOHelper.fixGridMaps(tmplGridSC, getSqlMap(),
				"Lookups.getFilterCrtListMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject(
				"Lookups.getFilterCrtListCount", tmplGridSC)).intValue();
		return nb;
	}

	@Override
	public CommonDetailsVO getFilterCrtDependency(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"Lookups.getFilterCrtDependency", tmplGridSC);
	}

	public CommonDetailsVO getCompDependency(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"Lookups.getCompDependency", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxBranch(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMaxBranch", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinBranch(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMinBranch", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxDivision(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMaxDivision", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinDivision(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMinDivision", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxDepartment(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMaxDepartment", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinDepartment(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMinDepartment", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxUnit(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMaxUnit", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinUnit(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMinUnit", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMaxCurrency(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMaxCurrency", tmplGridSC);
	}

	@Override
	public CommonDetailsVO findMinCurrency(TmplGridSC tmplGridSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"templateGL.findMinCurrency", tmplGridSC);
	}

	@Override
	public CURRENCIESCO returnCompCurrencyDet(BigDecimal companyCode)
			throws DAOException {
		return (CURRENCIESCO) getSqlMap().queryForObject(
				"Lookups.returnCompCurrencyDet", companyCode);
	}

	@Override
	public BigDecimal returnConnectionId(String appName) throws DAOException {
		return (BigDecimal) getSqlMap().queryForObject(
				"Lookups.selectConnectionId", appName);
	}

	@Override
	public List<IRP_CONNECTIONSVO> returnConnectionsList() throws DAOException {
		return getSqlMap().queryForList(
				"Lookups.selectAllConnections", null);
	}

	@Override
	public IRP_CONNECTIONSVO returnConnById(BigDecimal dbConn)
			throws DAOException {
		return (IRP_CONNECTIONSVO) getSqlMap().queryForObject(
				"Lookups.selectConnById", dbConn);
	}

	
	@Override
	public ArrayList<LinkedHashMap> returnQryResult(DynLookupSC dynLookupSC)
			throws DAOException, ClassNotFoundException, IOException,
			SQLException {
		IRP_AD_HOC_QUERYCO query = (IRP_AD_HOC_QUERYCO) getSqlMap()
				.queryForObject("query.selectQueryById",
						new BigDecimal(dynLookupSC.getQryId()));
		SQL_OBJECT sqlObj = fillSqlObj(query);
		StringBuffer sql = QueryBuilder.buildQuery(sqlObj);
		String sqlStr = new String(sql);
		if (sqlStr == null || sqlStr.equals("")) {
			sqlStr = new String(sqlObj.getSqbSyntax());
		}
		dynLookupSC.setSqlStr(sqlStr);
		
		ArrayList<LinkedHashMap> nbRecList = new ArrayList<LinkedHashMap>();
		Connection con = null;
		Statement statement = null;
		ResultSet resultSet = null;
		ResultSetMetaData md = null;
		int nbRec = 1;
		//HashMap<String, String> hmQryParam = dynLookupSC.getHmQryParam();
		sqlStr = replaceQryArgByVal(dynLookupSC);
		
		//sqlStr is set to empty by replaceQryArgByVal() call if one argument's value used inside the query is empty
		if("".equals(sqlStr))
		{
		    return new ArrayList<LinkedHashMap>();
		}
		else if(dynLookupSC.getArgId() == null)
        	{
    	    String colSrchQry = dynLookupSC.getColSearchQuery();
    	    String srchQry =dynLookupSC.getSearchQuery();
    	    String whereQry=dynLookupSC.getWhereQuery();
    	    String sortOrder=dynLookupSC.getSortOrder();
    	    String[] srchCols = dynLookupSC.getSearchCols();
    	    String col;
    	    if(null != srchCols){
    		for(int i = 0; i < srchCols.length; i++)
        	    {
        		col = srchCols[i];
        		if(sqlStr.indexOf("\"" + col + "\"") != -1)
        		{
        		    if(colSrchQry != null)
        		    {
        			colSrchQry = colSrchQry.replaceAll(col, "\"" + col + "\"");
        		    }
        		    if(srchQry!=null)
        		    {
        			srchQry = srchQry.replaceAll(col, "\"" + col + "\"");
        		    }
        		    if(whereQry!=null)
        		    {
        			whereQry = whereQry.replaceAll(col, "\"" + col + "\"");
        		    }
        		    if(sortOrder!=null)
        		    {
        			sortOrder = sortOrder.replaceAll(col, "\"" + col + "\"");
        		    }
        		}
        	    }
    		    dynLookupSC.setColSearchQuery(colSrchQry);
    		    dynLookupSC.setSearchQuery(srchQry);
    		    dynLookupSC.setWhereQuery(whereQry);
    		    dynLookupSC.setSortOrder(sortOrder);
    	    }
    	    
    	    

		    dynLookupSC.setSqlStr(sqlStr);
		    if(dynLookupSC.getColSearchQuery() != null)
		    {
			dynLookupSC.setWhereQuery(dynLookupSC.getColSearchQuery());
		    }
		    if(dynLookupSC.getSearchQuery() != null)
		    {
			  if(dynLookupSC.getWhereQuery() == null || dynLookupSC.getWhereQuery().isEmpty())
			  {		      
			     dynLookupSC.setWhereQuery(dynLookupSC.getSearchQuery());
			  }
			  else
			  {		      
			      dynLookupSC.setWhereQuery(dynLookupSC.getWhereQuery() + " AND ("  + dynLookupSC.getSearchQuery() + ") ");
			  }
		    }
		    
		    if(dynLookupSC.getConnCO() != null)
			{
		    	ArrayList<LinkedHashMap> list = new ArrayList<LinkedHashMap>();
		    	LinkedHashMap object = null;
				StringBuilder queryBuilder = new StringBuilder();
				queryBuilder.append("SELECT * FROM (");
				queryBuilder.append(dynLookupSC.getSqlStr());
				queryBuilder.append(") TBL ");
				if(StringUtil.isNotEmpty(dynLookupSC.getSortOrder()))
				{
					queryBuilder.append(dynLookupSC.getSortOrder());
				}
				if(StringUtil.isNotEmpty(dynLookupSC.getColSearchQuery()))
				{
					queryBuilder.append(" WHERE "+dynLookupSC.getColSearchQuery());
				}
				try
				{
					con = DriverManager.getConnection(dynLookupSC.getConnCO().getDbURL(), dynLookupSC.getConnCO().getDbUserName(), 
							dynLookupSC.getConnCO().getDbPassword());
					statement = con.createStatement();
					resultSet = statement.executeQuery(queryBuilder.toString());
					md = resultSet.getMetaData();
					int columns = md.getColumnCount();
					while (resultSet.next()) 
					{
						object = new LinkedHashMap(columns);
						for(int i=1; i<=columns; ++i){           
							object.put(md.getColumnName(i), resultSet.getObject(i));
						}
						list.add(object);
					}
					nbRec = dynLookupSC.getNbRec();
					int recToSkip = dynLookupSC.getRecToskip();
					int maxRec = nbRec + recToSkip;
					if(list.size() < maxRec)
					{
						maxRec = list.size();
					}
					for(int i = recToSkip; i< maxRec; i++)
					{
						nbRecList.add(list.get(i));
					}
				}
				catch (Exception e) 
				{
					log.error(e,"");
				}
				finally 
				{
					if(statement != null){ statement.close(); }
					if(resultSet != null){ resultSet.close(); }
					if(con != null){ con.close(); }
				}
				return nbRecList;
			}
		    return (ArrayList<LinkedHashMap>) getSqlMap().queryForList(
				"Lookups.returnQryResult", dynLookupSC,
				dynLookupSC.getRecToskip(), dynLookupSC.getNbRec());
        	}
		else
		{
        	    try
        	    {
        		IRP_REP_ARGUMENTSVO argVO = new IRP_REP_ARGUMENTSVO();
        		argVO.setARGUMENT_ID(dynLookupSC.getArgId());
        		argVO.setREPORT_ID(dynLookupSC.getReportId());
        		argVO = (IRP_REP_ARGUMENTSVO) selectByPK(argVO);
        		String argSymb = "$P{";
        		String qryArgName;
        		IRP_QUERY_ARG_MAPPINGVO queryArgMappVO = new IRP_QUERY_ARG_MAPPINGVO();
    		    	IRP_QUERY_ARG_MAPPINGVO resQueryArgMapVO;
        		if(!sqlStr.contains(argSymb))
        		{
        		    argSymb = "$P!{";
        		}
        		String firstPart;
        		while(sqlStr.contains(argSymb))
        		{        		    
        		    queryArgMappVO.setREPORT_ID(dynLookupSC.getReportId());
        		    queryArgMappVO.setREPORT_ARGUMENT_ID(dynLookupSC.getArgId());
        		    queryArgMappVO.setQUERY_ID(new BigDecimal(dynLookupSC.getQryId()));
        		    firstPart = sqlStr.substring(sqlStr.indexOf(argSymb)+argSymb.length(),sqlStr.length());
        		    qryArgName = firstPart.substring(0,firstPart.indexOf("}"));
        		    queryArgMappVO.setQUERY_ARG_NAME(qryArgName);
        		    queryArgMappVO.setDEFAULT_SOURCE(BigDecimal.ONE);
        		    resQueryArgMapVO = (IRP_QUERY_ARG_MAPPINGVO) selectByPK(queryArgMappVO);
        		    if(resQueryArgMapVO == null)
        		    {
        			queryArgMappVO.setDEFAULT_SOURCE(new BigDecimal(2));
        			resQueryArgMapVO = (IRP_QUERY_ARG_MAPPINGVO) selectByPK(queryArgMappVO);
        		    }
        		    sqlStr = StringUtil.replaceInString(sqlStr, argSymb + qryArgName + "}", resQueryArgMapVO
        			    .getREPORT_MAPPED_ARG_NAME() == null ? resQueryArgMapVO.getSTATIC_VALUE() : dynLookupSC
        			    .getHmQryParam().get(resQueryArgMapVO.getREPORT_MAPPED_ARG_NAME()));
        		    if(!sqlStr.contains(argSymb))
        		    {
        			argSymb = "$P!{";
        		    }
        		}
        		String argVal = dynLookupSC.getArgVal();
        		if(StringUtil.isNotEmpty(argVal))
        		{
        		String compareExp;
        		 String colNameComp = argVO.getCOLUMN_NAME();
 		        if(sqlStr.indexOf("\"" + argVO.getCOLUMN_NAME() + "\"") != -1)
 		        {
 		            colNameComp = "\"" + argVO.getCOLUMN_NAME() + "\"";
 		        }
         		if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(argVO.getARGUMENT_TYPE()))
         		{
         		    compareExp = colNameComp + " = " + dynLookupSC.getArgVal();
         		}
         		else
         		{
         		    compareExp = colNameComp + RepConstantsCommon.REP_DEPENDENCY_LIKE
         			    + dynLookupSC.getArgVal() + "')";
         		}
        		StringBuffer sb = new StringBuffer();
        		sb.append(RepConstantsCommon.REP_DEPENDENCY_SELECTALL + sqlStr + RepConstantsCommon.REP_WHERE_CLAUSE
        			+ compareExp);
        		sqlStr = sb.toString();
        		}
        		if(dynLookupSC.getIsSybase() == 1)
        
        		{
        		    int indexOfOrder = 0;
        		    String sqlUpper = sqlStr.toUpperCase(Locale.ENGLISH);
        		    if(sqlUpper.indexOf(" " + RepConstantsCommon.REP_ORDER_CLAUSE + " ") >= 0)
        		    {
        			indexOfOrder = sqlUpper.indexOf(" " + RepConstantsCommon.REP_ORDER_CLAUSE + " ");
        		    }
        		    else if(sqlUpper.indexOf("\n" + RepConstantsCommon.REP_ORDER_CLAUSE + " ") >= 0)
        		    {
        			indexOfOrder = sqlUpper.indexOf("\n" + RepConstantsCommon.REP_ORDER_CLAUSE + " ");
        		    }
        		    else if(sqlUpper.indexOf("\n" + RepConstantsCommon.REP_ORDER_CLAUSE + "\n") >= 0)
        		    {
        			indexOfOrder = sqlUpper.indexOf("\n" + RepConstantsCommon.REP_ORDER_CLAUSE + "\n");
        		    }
        		    if(indexOfOrder > 0)
        		    {
        			sqlStr = sqlStr.substring(0, indexOfOrder)
        				+ sqlStr.substring(sqlStr.lastIndexOf(")"), sqlStr.length());
        		    }
        		}
        		dynLookupSC.setSqlStr(sqlStr);
        		nbRec = 1;
            		if(ConstantsCommon.ARG_QRY_COMBO.equals(argVO.getARG_QUERY_OPTIONS()))
            		{
            		    nbRec = (Integer) getSqlMap().queryForObject("Lookups.returnQryResultCnt", dynLookupSC);
            		}
        		dynLookupSC.setNbRec(nbRec);
        		dynLookupSC.setRecToskip(0);
        		
        		if(dynLookupSC.getConnCO() != null)
    			{
    		    	ArrayList<LinkedHashMap> list = new ArrayList<LinkedHashMap>();
    		    	LinkedHashMap object = null;
    				StringBuilder queryBuilder = new StringBuilder();
    				queryBuilder.append("SELECT * FROM (");
    				queryBuilder.append(dynLookupSC.getSqlStr());
    				queryBuilder.append(") TBL ");
    				if(StringUtil.isNotEmpty(dynLookupSC.getSortOrder()))
    				{
    					queryBuilder.append(dynLookupSC.getSortOrder());
    				}
    				if(StringUtil.isNotEmpty(dynLookupSC.getColSearchQuery()))
    				{
    					queryBuilder.append(" WHERE "+dynLookupSC.getColSearchQuery());
    				}
    				try
    				{
	    				con = DriverManager.getConnection(dynLookupSC.getConnCO().getDbURL(), dynLookupSC.getConnCO().getDbUserName(), 
	    						dynLookupSC.getConnCO().getDbPassword());
	    				statement = con.createStatement();
	    				resultSet = statement.executeQuery(queryBuilder.toString());
	    				md = resultSet.getMetaData();
	    				int columns = md.getColumnCount();
	    				while (resultSet.next()) 
	    				{
	    					object = new LinkedHashMap(columns);
	    					for(int i=1; i<=columns; ++i)
	    					{           
	    						object.put(md.getColumnName(i), resultSet.getObject(i));
	    					}
	    					list.add(object);
	    				}
	    				
	    				nbRec = dynLookupSC.getNbRec();
	    				int recToSkip = dynLookupSC.getRecToskip();
	    				int maxRec = nbRec + recToSkip;
	    				if(list.size() < maxRec)
	    				{
	    					maxRec = list.size();
	    				}
	    				for(int i = recToSkip; i< maxRec; i++)
	    				{
	    					nbRecList.add(list.get(i));
	    				}
    				}
    				catch (Exception e) 
    				{
    					log.error(e,"");
					}
    				finally 
    				{
    					if(statement != null){ statement.close(); }
    					if(resultSet != null){ resultSet.close(); }
    					if(con != null){ con.close(); }
					}
    				return nbRecList;
    			}
        		
        		return (ArrayList<LinkedHashMap>) getSqlMap().queryForList("Lookups.returnQryResult", dynLookupSC,
        			dynLookupSC.getRecToskip(), dynLookupSC.getNbRec());
        	    }
        	    catch(Exception e)
        	    {
        		log.error(e,"");
        		return new ArrayList<LinkedHashMap>();
        	    }  
		}
	}

	@Override
	public Integer returnQryResultCnt(DynLookupSC dynLookupSC)
			throws DAOException, ClassNotFoundException, IOException,
			SQLException {
		IRP_AD_HOC_QUERYCO query = (IRP_AD_HOC_QUERYCO) getSqlMap()
				.queryForObject("query.selectQueryById",
						new BigDecimal(dynLookupSC.getQryId()));
		SQL_OBJECT sqlObj = fillSqlObj(query);
		StringBuffer sql = QueryBuilder.buildQuery(sqlObj);
		String sqlStr = new String(sql);
		if (sqlStr == null || sqlStr.equals("")) {
			sqlStr = new String(sqlObj.getSqbSyntax());
		}

		//HashMap<String, String> hmQryParam = dynLookupSC.getHmQryParam();
		dynLookupSC.setSqlStr(sqlStr);
		sqlStr = replaceQryArgByVal(dynLookupSC);// to replace the param name by
													// his value
		dynLookupSC.setSqlStr(sqlStr);
		
		if(dynLookupSC.getConnCO() != null && StringUtil.isNotEmpty(sqlStr))
		{
			StringBuilder queryBuilder = new StringBuilder();
			queryBuilder.append("SELECT COUNT(1) AS COUNT_ROW FROM (SELECT * FROM (");
			queryBuilder.append(dynLookupSC.getSqlStr());
			queryBuilder.append(") TBL ) OUT_TABLE");
			if(StringUtil.isNotEmpty(dynLookupSC.getColSearchQuery()))
			{
				queryBuilder.append(" WHERE "+dynLookupSC.getColSearchQuery());
			}
			Connection con = null;
			Statement statement = null;
			ResultSet resultSet = null;
			int count = 0;
			try
			{
				con = DriverManager.getConnection(dynLookupSC.getConnCO().getDbURL(), dynLookupSC.getConnCO().getDbUserName(), 
						dynLookupSC.getConnCO().getDbPassword());
				statement = con.createStatement();
				resultSet = statement.executeQuery(queryBuilder.toString());
				if (resultSet.next()) 
				{
					count = resultSet.getInt("COUNT_ROW");
				}
			}
			catch (Exception e) 
			{
				log.error(e,"");
			}
			finally 
			{
				if(statement != null){ statement.close(); }
				if(resultSet != null){ resultSet.close(); }
				if(con != null){ con.close(); }
			}
			return count;
		}
		
		//sqlStr is set to empty by replaceQryArgByVal() call if one argument's value used inside the query is empty
		if("".equals(sqlStr))
		{
		    return 0;
		}
		else
		{
		    return (Integer) getSqlMap().queryForObject(
				"Lookups.returnQryResultCnt", dynLookupSC);
		}
	}

	@Override
	public String replaceQryArgByVal(DynLookupSC dynLookupSC) {
		HashMap<String, String> hmQryParam = dynLookupSC.getHmQryParam();
		String sqlStr = dynLookupSC.getSqlStr();
		for (Entry<String, String> entry : hmQryParam.entrySet()) {
			String paramName = entry.getKey();
			String argTypeName = paramName + "@TYPE";
			// String paramVal = entry.getValue();
			String[] paramValArray = entry.getValue().split("-");
			String paramVal = paramValArray[0];
			String argTypeVal = null;
			
			if(!paramName.endsWith("@TYPE"))
			{
	
			if(hmQryParam.get(argTypeName) != null)
			{
			    argTypeVal = hmQryParam.get(argTypeName);
            	    	}
			
			//return empty string if one argument's value used in a query is empty
			if( "".equals(paramVal) && sqlStr.contains("$P{" + paramName+ "}"))
			{
			    return "";
			}
			
			if (paramValArray.length == 1) {
			    
			    if(argTypeVal != null && ConstantsCommon.PARAM_TYPE_DATE.equals(argTypeVal))
			    {
				if (dynLookupSC.getIsSybase() == 0) {
					sqlStr = StringUtil.replaceInString(sqlStr, "$P{"
							+ paramName + "}", "TO_DATE('" + paramVal + "'"
							+ ",'DD/MM/YYYY HH:MI:SS AM')");
				} else {

					sqlStr = StringUtil.replaceInString(sqlStr, "$P{"
							+ paramName + "}", "CONVERT(DATETIME," + "'"
							+ paramVal + "',103)");
				}
			    }
			    else
			    {
				sqlStr = StringUtil.replaceInString(sqlStr, "$P{" + paramName
						+ "}", paramVal);
				sqlStr = StringUtil.replaceInString(sqlStr, "$P!{" + paramName
						+ "}", paramVal);
			    }
			}
			else {
				String paramType = paramValArray[1];
				if ( ConstantsCommon.PARAM_TYPE_DATE.equals(paramType)) {
					if (dynLookupSC.getIsSybase() == 0) {
						sqlStr = StringUtil.replaceInString(sqlStr, "$P{"
								+ paramName + "}", "TO_DATE('" + paramVal + "'"
								+ ",'DD/MM/YYYY HH:MI:SS AM')");
					} else {

						sqlStr = StringUtil.replaceInString(sqlStr, "$P{"
								+ paramName + "}", "CONVERT(DATETIME," + "'"
								+ paramVal + "',103)");
					}
				} else {
					sqlStr = StringUtil.replaceInString(sqlStr, "$P{"
							+ paramName + "}", paramVal);
					sqlStr = StringUtil.replaceInString(sqlStr, "$P!{"
							+ paramName + "}", paramVal);
				}
			}
			}
		}
		return sqlStr;
	}

	@Override
	public List<String[]> returnColsList(String qryId) throws DAOException,
			ClassNotFoundException, IOException, SQLException {

		BigDecimal selQry = new BigDecimal(qryId);
		IRP_AD_HOC_QUERYCO query = (IRP_AD_HOC_QUERYCO) getSqlMap()
				.queryForObject("query.selectQueryById", selQry);
        	if(query.getQUERY_OBJECT() == null)
        	{
        	    return null;
        	}
		SQL_OBJECT sqlObj = fillSqlObj(query);
		query.setSqlObject(sqlObj);
		ArrayList<String> qryColumnsList = new ArrayList<String>();
		qryColumnsList.add("");
		//HashMap<String, String> hm = new HashMap<String, String>();
		String[] col = null;
		List<String[]> list = new ArrayList<String[]>();
		//LinkedHashMap<Long, IRP_FIELDSCO> fieldsMap = new LinkedHashMap<Long, IRP_FIELDSCO>();
		LinkedHashMap<Long, IRP_FIELDSCO> fieldsMap = query.getSqlObject().getFields();
		for (Entry<Long, IRP_FIELDSCO> entry : fieldsMap.entrySet()) {
			col = new String[2];
			col[1] = CommonUtil.returnJavaFieldType(entry.getValue()
					.getFIELD_DATA_TYPE());
			col[0] = entry.getValue().getLabelAlias();
			list.add(col);
		}

		/*
		 * SQL_OBJECT sqlObj=fillSqlObj(query); //StringBuffer sql =
		 * sqlObj.getSqbSyntax();//QueryBuilder.buildQuery(sqlObj); StringBuffer
		 * sql = null; if(sqlObj.getSqbSyntax()==null) { sql =
		 * QueryBuilder.buildQuery(sqlObj); } else { sql =
		 * sqlObj.getSqbSyntax(); } String sqlStr = new String(sql); //sqlStr
		 * ="select cif_no as cif_no from cif"; Connection con =
		 * getSqlMap().getDataSource().getConnection(); Statement stmt =
		 * con.createStatement(); ResultSet rs = stmt.executeQuery(sqlStr);
		 * ResultSetMetaData rsmd = rs.getMetaData(); int columnCount =
		 * rsmd.getColumnCount(); String[] col; List<String[]> list = new
		 * ArrayList<String[]>();
		 * 
		 * for (int i = 1; i <= columnCount; i++) { col = new String[2]; col[0]
		 * = rsmd.getColumnName(i); col[1] =
		 * Integer.toString(rsmd.getColumnType(i)); list.add(col); }
		 * 
		 * stmt.close();
		 */
		return list;
	}

	public SQL_OBJECT fillSqlObj(IRP_AD_HOC_QUERYCO query)
			throws ClassNotFoundException, IOException {
		SQL_OBJECT sqlObj;
		byte[] qryObj = query.getQUERY_OBJECT();
		StringBuffer xml;
		String xmlStr = new String(qryObj,CommonUtil.ENCODING);
		xml = new StringBuffer(xmlStr);
		if (xml.indexOf("<?xml version=\"1.0\" encoding=\"UTF-8\"?>") == -1) {
			sqlObj = (SQL_OBJECT) CommonUtil.objectFromBytes(query
					.getQUERY_OBJECT());
		} else {
			sqlObj = xmlToSqlObject(query.getQUERY_OBJECT());
		}
		return sqlObj;
	}

	public SQL_OBJECT xmlToSqlObject(byte[] qryObj) {
		SQL_OBJECT sqlObj = new SQL_OBJECT();
		try {
			// /* To be removed before commit*/
			// String xmlStr;
			// StringBuffer xml;
			// xmlStr=new String(qryObj);
			// xml=new StringBuffer(xmlStr);
			// FileUtil.writeFile(ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder)+"/openQry.xml",
			// xml.toString());
			// /* End to be removed before commit*/

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
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

			Element parentElem;
			Element qryElem;
			Element childElem;

			NamedNodeMap attrs;

			String str;
			String str1;
			int j;

			IRP_ENTITIESCO entCO;
			IRP_FIELDSCO feCO;
			IRP_REP_ARGUMENTSCO argCO;
			CONDITION_OBJECT condCO;
			QRY_GRIDSC qryGridSC;

			for (int i = 0; i < qryLst.getLength(); i++) {
				qry = qryLst.item(i);

				// fill qryName and outputLayout
				attrs = qry.getAttributes();
				if (attrs.getNamedItem("name") == null) {
				    qryElem = (Element) qry;
					childNodeLst = qryElem.getElementsByTagName("name");
					childNode = childNodeLst.item(0);
					sqlObj.setQUERY_NAME(childNode.getChildNodes().item(0)
							.getNodeValue());
					
				} else {
					
					sqlObj.setQUERY_NAME(attrs.getNamedItem("name")
						.getNodeValue());
				}
				sqlObj.setOutputLayout(attrs.getNamedItem("outputLayout")
						.getNodeValue());
				str = attrs.getNamedItem("type").getNodeValue();

				// if designer query
				if ("0".equals(str)) {
					if (qry.getNodeType() == Node.ELEMENT_NODE) {
						qryElem = (Element) qry;

						// fill entities
						parentNodeLst = qryElem
								.getElementsByTagName("entities");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("entity");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								entCO = new IRP_ENTITIESCO();
								entCO.setIndex(Long.valueOf(attrs
										.getNamedItem("id").getNodeValue()));
								entCO.setType(attrs.getNamedItem("type")
										.getNodeValue());
								entCO.setSyntaxAlias(attrs.getNamedItem(
										"syntAlias").getNodeValue());
								entCO.setENTITY_DB_NAME(attrs.getNamedItem(
										"dbName").getNodeValue());

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("alias");
									ch2NodeLst = childElem
											.getElementsByTagName("aliasWithCnt");
									entCO.setENTITY_ALIAS(ch1NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									entCO.setEntityAliasWithCount(ch2NodeLst
											.item(0).getChildNodes().item(0)
											.getNodeValue());
								}
								sqlObj.getEntities().put(entCO.getIndex(),
										entCO);
							}
						}

						// fill fields
						parentNodeLst = qryElem.getElementsByTagName("fields");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("field");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								feCO = new IRP_FIELDSCO();
								feCO.setIndex(Long.valueOf(attrs.getNamedItem("id")
										.getNodeValue()));
								feCO.setFIELD_DB_NAME(attrs.getNamedItem(
										"dbName").getNodeValue());
								feCO.setFIELD_DATA_TYPE(attrs.getNamedItem(
										"feType").getNodeValue());
								feCO.setPARENT_INDEX(Long.valueOf(attrs
										.getNamedItem("pId").getNodeValue()));
								feCO.setType(attrs.getNamedItem("type")
										.getNodeValue());
								feCO.setLabelAlias(attrs.getNamedItem(
										"lblAlias").getNodeValue());
								feCO.setOrder(attrs.getNamedItem("order")
										.getNodeValue());
								if (attrs.getNamedItem("hasBusName") != null
										&& ("0").equals(attrs.getNamedItem(
												"hasBusName").getNodeValue())) {
									feCO.setHasBusinessName(0);
								}

								entCO = sqlObj.getEntities().get(
										feCO.getPARENT_INDEX());
								feCO.setENTITY_DB_NAME(entCO
										.getENTITY_DB_NAME());
								feCO.setENTITY_ALIAS(entCO.getENTITY_ALIAS());
								feCO.setEntityAliasWithCount(entCO
										.getEntityAliasWithCount());
								feCO.setFieldSyntax(entCO.getSyntaxAlias()
										+ "." + feCO.getFIELD_DB_NAME());
								feCO.setParent(feCO.getPARENT_INDEX().toString());
								feCO.setIsLeaf("true");
								feCO.setLevel("3");

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("alias");
									ch2NodeLst = childElem
											.getElementsByTagName("grp");
									feCO.setFIELD_ALIAS(ch1NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									if (ch2NodeLst.getLength() > 0) {
										feCO.setGroupName(ch2NodeLst.item(0)
												.getChildNodes().item(0)
												.getNodeValue());
									}
								}

								sqlObj.getFields().put(feCO.getIndex(), feCO);
								entCO.getSelectedFields().put(feCO.getIndex(),
										feCO);
								if ("1".equals(attrs.getNamedItem("sel")
										.getNodeValue())) {
									sqlObj.getDisplayFields().put(
											feCO.getIndex(), feCO);
								}

							}
						}

						// fill expression
						parentNodeLst = qryElem.getElementsByTagName("exps");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("exp");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								feCO = new IRP_FIELDSCO();
								feCO.setFIELD_DATA_TYPE(attrs.getNamedItem(
										"feType").getNodeValue());
								feCO.setIndex(Long.valueOf(attrs.getNamedItem("id")
										.getNodeValue()));
								feCO.setType(attrs.getNamedItem("type")
										.getNodeValue());
								feCO.setParent("2");
								feCO.setIsLeaf("true");
								feCO.setLabelAlias("2");
								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("feSynt");
									ch2NodeLst = childElem
											.getElementsByTagName("lblAlias");
									ch3NodeLst = childElem
											.getElementsByTagName("html");

									feCO.setFieldSyntax(ch1NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									feCO.setLabelAlias(ch2NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									feCO.setHtml(ch3NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());

								}
								sqlObj.getExpressionFields().put(
										feCO.getIndex(), feCO);
							}
						}

						// fill aggregates
						parentNodeLst = qryElem.getElementsByTagName("aggrs");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("aggr");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								feCO = new IRP_FIELDSCO();
								feCO.setIndex(Long.valueOf(attrs.getNamedItem("id")
										.getNodeValue()));
								feCO.setParent("3");
								feCO.setLevel("2");
								feCO.setIsLeaf("true");
								feCO.setAggregate(attrs.getNamedItem("aggrFn")
										.getNodeValue());
								feCO.setFIELD_ALIAS(attrs.getNamedItem(
										"feAlias").getNodeValue());
								feCO.setFIELD_DB_NAME(attrs
										.getNamedItem("feDb").getNodeValue());
								feCO.setType(attrs.getNamedItem("type")
										.getNodeValue());
								feCO.setPARENT_INDEX(Long.valueOf(attrs
										.getNamedItem("pId").getNodeValue()));
								feCO.setFIELD_DATA_TYPE(attrs.getNamedItem(
										"feType").getNodeValue());
								// get entityCO from the parent_index
								entCO = sqlObj.getEntities().get(
										feCO.getPARENT_INDEX());
								feCO.setEntityAliasWithCount(entCO
										.getEntityAliasWithCount());
								feCO.setENTITY_ALIAS(entCO.getENTITY_ALIAS());
								feCO.setENTITY_DB_NAME(entCO
										.getENTITY_DB_NAME());

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("lblAlias");
									ch2NodeLst = childElem
											.getElementsByTagName("feSynt");
									feCO.setLabelAlias(ch1NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									feCO.setFieldSyntax(ch2NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									feCO.setFeName(feCO.getLabelAlias());
								}

								sqlObj.getAggregateFields().put(
										feCO.getIndex(), feCO);
							}
						}

						// fill arguments
						parentNodeLst = qryElem.getElementsByTagName("args");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("arg");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								argCO = new IRP_REP_ARGUMENTSCO();
								argCO.setIndex(Long.parseLong(attrs
										.getNamedItem("id").getNodeValue()));
								argCO.setARGUMENT_NAME(attrs.getNamedItem(
										"name").getNodeValue());
								argCO.setARGUMENT_TYPE(attrs.getNamedItem(
										"type").getNodeValue());
								argCO.setARGUMENT_SOURCE(new BigDecimal(attrs
										.getNamedItem("src").getNodeValue()));
								argCO.setSESSION_ATTR_NAME(attrs.getNamedItem(
										"sess").getNodeValue());
								if (attrs.getNamedItem("qryId") != null
										&& !"null".equals(attrs.getNamedItem(
												"qryId").getNodeValue())) {
									argCO.setQUERY_ID(new BigDecimal(attrs
											.getNamedItem("qryId")
											.getNodeValue()));
								}
								argCO.setQUERY_NAME(attrs.getNamedItem(
										"qryName").getNodeValue());
								str = attrs.getNamedItem("col").getNodeValue();
								if ("null".equalsIgnoreCase(str)) {
									str = null;
								}
								argCO.setCOLUMN_NAME(str);
								argCO
										.setValueFlag(Boolean.valueOf(attrs
												.getNamedItem("valFlg")
												.getNodeValue()));
								argCO.setARGUMENT_ORDER(new BigDecimal(attrs
										.getNamedItem("order").getNodeValue()));
								argCO.setDISPLAY_FLAG(attrs.getNamedItem(
										"displ").getNodeValue());
								argCO.setENABLE_FLAG(attrs.getNamedItem(
										"enable").getNodeValue());

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("lbl");
									ch2NodeLst = childElem
											.getElementsByTagName("val");
									argCO.setARGUMENT_LABEL(ch1NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									argCO.setARGUMENT_VALUE(ch2NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
								}
								sqlObj.getArgumentsList().put(argCO.getIndex(),
										argCO);
							}

						}

						// fill joins
						parentNodeLst = qryElem.getElementsByTagName("joins");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("join");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								condCO = new CONDITION_OBJECT();
								condCO.setIndex((Long.parseLong(attrs.getNamedItem(
										"id").getNodeValue())));
								str = attrs.getNamedItem("entId1")
										.getNodeValue();
								str1 = attrs.getNamedItem("entId2")
										.getNodeValue();
								// get the ent1 and ent2 from the entId1 and
								// entId2
								condCO.setEntity1(sqlObj.getEntities().get(
										Long.valueOf(str)));
								condCO.setEntity2(sqlObj.getEntities().get(
										Long.valueOf(str1)));
								condCO.setJoin(attrs.getNamedItem("join")
										.getNodeValue());

								str = attrs.getNamedItem("feDb1")
										.getNodeValue();
								str1 = attrs.getNamedItem("feDb2")
										.getNodeValue();

								qryGridSC = new QRY_GRIDSC();
								qryGridSC.setENTITY_DB_NAME(condCO.getEntity1()
										.getENTITY_DB_NAME());
								qryGridSC.setFIELD_NAME(str);
								List<IRP_FIELDSCO> field1 = queryDAO
										.retDBFields(qryGridSC);
								addObjsIndex(field1);
								if (field1 == null || field1.isEmpty()) {
								    condCO.setField1(null);
								} else {
								    condCO.setField1(field1.get(0));
								}

								qryGridSC = new QRY_GRIDSC();
								qryGridSC.setENTITY_DB_NAME(condCO.getEntity2()
										.getENTITY_DB_NAME());
								qryGridSC.setFIELD_NAME(str1);
								List<IRP_FIELDSCO> field2 = queryDAO
										.retDBFields(qryGridSC);
								addObjsIndex(field2);
								if (field2 == null || field2.isEmpty()) {
								    condCO.setField2(null);
								} else {		
								    condCO.setField2(field2.get(0));
								}

								sqlObj.getJoinsList().put(condCO.getIndex(),
										condCO);

							}

						}

						// fill conditions
						parentNodeLst = qryElem.getElementsByTagName("conds");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("cond");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								condCO = new CONDITION_OBJECT();
								condCO.setIndex((Long.parseLong(attrs.getNamedItem(
										"id").getNodeValue())));
								condCO.setConjunction(attrs
										.getNamedItem("conj").getNodeValue());

								str = attrs.getNamedItem("entId")
										.getNodeValue();
								// get the entityCO from the entCO
								entCO = sqlObj.getEntities().get(Long.valueOf(str));
								condCO.setEntity1(entCO);

								// return the feCO from the feDbName and the
								// entDbName
								str1 = attrs.getNamedItem("feDb")
										.getNodeValue();
								qryGridSC = new QRY_GRIDSC();
								qryGridSC.setFIELD_NAME(str1);
								qryGridSC.setENTITY_DB_NAME(entCO
										.getENTITY_DB_NAME());
								qryGridSC.setPARENT_ID(Long.valueOf("0"));
								List<IRP_FIELDSCO> field1 = queryDAO
										.retDBFields(qryGridSC);
								if (field1 == null || field1.isEmpty()) {
								    condCO.setField1(null);
								} else {
									condCO.setField1(field1.get(0));
								}

								condCO.setLparenthesis(attrs.getNamedItem("lP")
										.getNodeValue());
								condCO.setRparenthesis(attrs.getNamedItem("rP")
										.getNodeValue());

								// check if the argId exist , then get the argCO
								// from the argId
								if (attrs.getNamedItem("arg1") != null) {
									str = attrs.getNamedItem("arg1")
											.getNodeValue();
									condCO.setArgument1(sqlObj
											.getArgumentsList().get(
													Long.valueOf(str)));
								}
								if (attrs.getNamedItem("arg2") != null) {
									str1 = attrs.getNamedItem("arg2")
											.getNodeValue();
									condCO.setArgument2(sqlObj
											.getArgumentsList().get(
													Long.valueOf(str1)));
								}

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("val");
									ch2NodeLst = childElem
											.getElementsByTagName("oper");
									ch3NodeLst = childElem
											.getElementsByTagName("operName");
									condCO.setValue(ch1NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									condCO.setOperator(ch2NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									condCO.setOperatorName(ch3NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
								}
								sqlObj.getConditionsList().put(
										condCO.getIndex(), condCO);
							}
						}

						// fill having
						parentNodeLst = qryElem.getElementsByTagName("havings");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("having");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								condCO = new CONDITION_OBJECT();
								condCO.setIndex((Long.parseLong(attrs.getNamedItem(
										"id").getNodeValue())));
								condCO.setConjunction(attrs
										.getNamedItem("conj").getNodeValue());
								condCO.setLparenthesis(attrs.getNamedItem("lP")
										.getNodeValue());
								condCO.setRparenthesis(attrs.getNamedItem("rP")
										.getNodeValue());

								str = attrs.getNamedItem("feAggrId")
										.getNodeValue();
								condCO.setField1(sqlObj.getAggregateFields()
										.get(Long.valueOf(str)));

								// return the argCO from the argId
								if (attrs.getNamedItem("arg1") != null) {
									str = attrs.getNamedItem("arg1")
											.getNodeValue();
									condCO.setArgument1(sqlObj
											.getArgumentsList().get(
													Long.valueOf(str)));
								}
								if (attrs.getNamedItem("arg2") != null) {
									str = attrs.getNamedItem("arg2")
											.getNodeValue();
									condCO.setArgument2(sqlObj
											.getArgumentsList().get(
													Long.valueOf(str)));
								}
								if (attrs.getNamedItem("entId2") != null) {
									str = attrs.getNamedItem("entId2")
											.getNodeValue();
									str1 = attrs.getNamedItem("feDb2")
											.getNodeValue();
									// get entCO from the entId
									condCO.setEntity2(sqlObj.getEntities().get(
											Long.valueOf(str)));

									// return the fieldCO from the entDbName and
									// the the feDbName
									qryGridSC = new QRY_GRIDSC();
									qryGridSC.setFIELD_NAME(str1);
									qryGridSC.setENTITY_DB_NAME(condCO
											.getEntity2().getENTITY_DB_NAME());
									qryGridSC.setPARENT_ID(Long.valueOf("0"));
									List<IRP_FIELDSCO> field = queryDAO
											.retDBFields(qryGridSC);
									if (field == null || field.isEmpty()) 
									{
									    condCO.setField2(null);
									}
									else 
									{
									    condCO.setField2(field.get(0));
									}
								}

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("val");
									ch2NodeLst = childElem
											.getElementsByTagName("oper");
									ch3NodeLst = childElem
											.getElementsByTagName("operName");
									condCO.setValue(ch1NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									condCO.setOperator(ch2NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									condCO.setOperatorName(ch3NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
								}

								sqlObj.getHavingList().put(condCO.getIndex(),
										condCO);
							}
						}

						// fill group by
						parentNodeLst = qryElem.getElementsByTagName("grpsBy");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("grpBy");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();
								str = attrs.getNamedItem("id").getNodeValue();
								// fill the grpByHm from the fields map
								// following the feId
								sqlObj.getGroupByHM().put(Long.valueOf(str),
										sqlObj.getFields().get(Long.valueOf(str)));
							}
						}
					}
				}
				// if syntax query
				else {
					if (qry.getNodeType() == Node.ELEMENT_NODE) {
						qryElem = (Element) qry;
						// fill fields
						parentNodeLst = qryElem.getElementsByTagName("fields");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("field");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								feCO = new IRP_FIELDSCO();
								feCO.setIndex(Long.valueOf(attrs.getNamedItem("id")
										.getNodeValue()));
								feCO.setFIELD_DB_NAME(attrs.getNamedItem(
										"dbName").getNodeValue());
								feCO.setFIELD_DATA_TYPE(attrs.getNamedItem(
										"feType").getNodeValue());
								feCO.setType(attrs.getNamedItem("type")
										.getNodeValue());
								feCO.setLabelAlias(attrs.getNamedItem(
										"lblAlias").getNodeValue());
								feCO.setIsLeaf("true");
								feCO.setLevel("3");
								if (attrs.getNamedItem("hasBusName") != null
										&& ("0").equals(attrs.getNamedItem(
												"hasBusName").getNodeValue())) {
									feCO.setHasBusinessName(0);
								}
								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("alias");
									feCO.setFIELD_ALIAS(ch1NodeLst.item(0)
											.getChildNodes().item(0)
											.getNodeValue());
									feCO.setFeName(feCO.getFIELD_ALIAS());
									ch2NodeLst = childElem
											.getElementsByTagName("grp");
									if (ch2NodeLst.getLength() > 0) {
										feCO.setGroupName(ch2NodeLst.item(0)
												.getChildNodes().item(0)
												.getNodeValue());
									}
								}

								sqlObj.getFields().put(feCO.getIndex(), feCO);
								if ("1".equals(attrs.getNamedItem("sel")
										.getNodeValue())) {
									sqlObj.getDisplayFields().put(
											feCO.getIndex(), feCO);
								}

							}
						}

						// fill arguments
						Node argLblNode;
						Node argValNode;
						parentNodeLst = qryElem.getElementsByTagName("args");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("arg");
							for (j = 0; j < childNodeLst.getLength(); j++) {
								childNode = childNodeLst.item(j);
								attrs = childNode.getAttributes();

								argCO = new IRP_REP_ARGUMENTSCO();
								argCO.setIndex(Long.parseLong(attrs
										.getNamedItem("id").getNodeValue()));
								argCO.setARGUMENT_NAME(attrs.getNamedItem(
										"name").getNodeValue());
								argCO.setARGUMENT_TYPE(attrs.getNamedItem(
										"type").getNodeValue());
								argCO.setARGUMENT_SOURCE(new BigDecimal(attrs
										.getNamedItem("src").getNodeValue()));
								argCO.setSESSION_ATTR_NAME(attrs.getNamedItem(
										"sess").getNodeValue());
								if (attrs.getNamedItem("qryId") != null
										&& !"null".equals(attrs.getNamedItem(
												"qryId").getNodeValue())) {
									argCO.setQUERY_ID(new BigDecimal(attrs
											.getNamedItem("qryId")
											.getNodeValue()));
								}
								argCO.setQUERY_NAME(attrs.getNamedItem(
										"qryName").getNodeValue());
								str = attrs.getNamedItem("col").getNodeValue();
								if ("null".equalsIgnoreCase(str)) {
									str = null;
								}
								argCO.setCOLUMN_NAME(str);
								argCO
										.setValueFlag(Boolean.valueOf(attrs
												.getNamedItem("valFlg")
												.getNodeValue()));
								argCO.setARGUMENT_ORDER(new BigDecimal(attrs
										.getNamedItem("order").getNodeValue()));
								argCO.setDISPLAY_FLAG(attrs.getNamedItem(
										"displ").getNodeValue());
								argCO.setENABLE_FLAG(attrs.getNamedItem(
										"enable").getNodeValue());

								if (childNode.getNodeType() == Node.ELEMENT_NODE) {
									childElem = (Element) childNode;
									ch1NodeLst = childElem
											.getElementsByTagName("lbl");
									ch2NodeLst = childElem
											.getElementsByTagName("val");
									String argLabel=argCO.getARGUMENT_NAME();
									String argValue="";
									argLblNode=ch1NodeLst.item(0).getFirstChild();
									argValNode=ch2NodeLst.item(0).getFirstChild();
									if(argLblNode!=null)
									{
										argLabel=argLblNode.getNodeValue();
									}
									if(argValNode!=null )
									{
										argValue=argValNode.getNodeValue();
									}
									argCO.setARGUMENT_LABEL(argLabel);
									argCO.setARGUMENT_VALUE(argValue);

								}
								sqlObj.getArgumentsList().put(argCO.getIndex(),
										argCO);
							}
						}

						// fill syntax
						parentNodeLst = qryElem.getElementsByTagName("syntax");
						parentNode = parentNodeLst.item(0);
						if (parentNode.getNodeType() == Node.ELEMENT_NODE) {
							parentElem = (Element) parentNode;
							childNodeLst = parentElem
									.getElementsByTagName("val");
							childNode = childNodeLst.item(0);
							sqlObj.setSqbSyntax(new StringBuffer(childNode
									.getChildNodes().item(0).getNodeValue()));
						}
					}
				}

			}

		} catch (Exception e) {
			log.error(e, e.getMessage());
		}

		return sqlObj;
	}

	public void addObjsIndex(List<IRP_FIELDSCO> lst) {
		// add index to each obj since sybase does not have a conversion of
		// rownum
		IRP_FIELDSCO feCO;
		for (int i = 0; i < lst.size(); i++) {
			feCO = lst.get(i);
			feCO.setIndex(Long.valueOf(i));
		}
	}


	public S_ADDITIONS_OPTIONSVO getAdditionsOptionsDependency(
			S_ADDITIONS_OPTIONSVO sAdditionsOptionsVO) throws DAOException {
		return (S_ADDITIONS_OPTIONSVO) getSqlMap().queryForObject(
				"Lookups.getAdditionsOptionsDependency", sAdditionsOptionsVO);
	}

	public List<S_ADDITIONS_OPTIONSVO> getAdditionsOptionsList(
			S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws DAOException {
		DAOHelper.fixGridMaps(sAdditionsOptionsSC, getSqlMap(),
				"Lookups.additionsOptionsMap");
		return getSqlMap().queryForList(
				"Lookups.getAdditionsOptionsList", sAdditionsOptionsSC,
				sAdditionsOptionsSC.getRecToskip(),
				sAdditionsOptionsSC.getNbRec());
	}

	public int getAdditionsOptionsListCount(
			S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws DAOException {
		DAOHelper.fixGridMaps(sAdditionsOptionsSC, getSqlMap(),
				"Lookups.additionsOptionsMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject(
				"Lookups.getAdditionsOptionsListCount", sAdditionsOptionsSC))
				.intValue();
		return nb;
	}

	public String getSmartText(S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC)
			throws DAOException {
		return (String) getSqlMap().queryForObject("Lookups.getSmartText",
				sAdditionsOptionsSC);
	}

	public String getCountryDesc(COUNTRYSC countrySC) throws DAOException {
		return (String) getSqlMap().queryForObject("Lookups.getCountryDesc",
				countrySC);
	}

	public List<GLSHEADERVO> returnTemplateHeaderVal(BigDecimal tempHeaderCode) throws DAOException
	{
	    return getSqlMap().queryForList("Lookups.returnTemplateHeaderVal", tempHeaderCode);
	}
	
	public List<CsvItemsCO> getReportsList(CsvItemsSC csvItemsSC) throws DAOException
	{
	    DAOHelper.fixGridMaps(csvItemsSC, getSqlMap(), "Lookups.retReportsListMap"); 
	    return getSqlMap().queryForList("Lookups.retReportsList", csvItemsSC,csvItemsSC.getRecToskip(), csvItemsSC.getNbRec());
	}

	public int getReportsCount(CsvItemsSC csvItemsSC) throws DAOException
	{
	    int nb = 0;
	    nb = ((Integer) getSqlMap().queryForObject("Lookups.retReportsCount", csvItemsSC)).intValue();
	    return nb;
	}
	
	public FTR_OPTVO retIfIsFcrReport(FTR_OPTCO ftrOptCO) throws DAOException
	{
	    return (FTR_OPTVO) getSqlMap().queryForObject("Lookups.retIfIsFcrReport",ftrOptCO);
	}
	
	public List<IRP_CONNECTIONSVO> returnConnectionsListByApp(IRP_CONNECTIONSSC conSC) throws DAOException {
		return getSqlMap().queryForList(
				"Lookups.returnConnectionsListByApp",conSC);
	}
	
	    @Override
	    public List<CommonDetailsVO> getRegionList(CommonDetailsSC commDet) throws DAOException
	    {
		if(commDet.getSortOrder() == null)
		{
		    commDet.setSortOrder(" ORDER BY TBL.REGION_CODE ");
		}
		if(commDet.isGrid())
		{
		    DAOHelper.fixGridMaps(commDet, getSqlMap(), "Lookups.getDependencyMap");
		    return getSqlMap().queryForList("Lookups.getRegionList", commDet,
			    commDet.getRecToskip(), commDet.getNbRec());
		}
		else
		{
		    return getSqlMap().queryForList("Lookups.getRegionList", commDet);
		}
	    }

	    public int getRegionListCount(CommonDetailsSC commDet) throws DAOException
	    {
		DAOHelper.fixGridMaps(commDet, getSqlMap(), "Lookups.getDependencyMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("Lookups.getRegionListCount", commDet)).intValue();
		return nb;
	    }

	    @Override
	    public CommonDetailsVO findMaxRegion(TmplGridSC tmplGridSC) throws DAOException
	    {
		return (CommonDetailsVO) getSqlMap().queryForObject(
			"templateGL.findMaxRegion", tmplGridSC);
	    }

	    @Override
	    public CommonDetailsVO findMinRegion(TmplGridSC tmplGridSC) throws DAOException
	    {
		return (CommonDetailsVO) getSqlMap().queryForObject(
			"templateGL.findMinRegion", tmplGridSC);
	    }
}
