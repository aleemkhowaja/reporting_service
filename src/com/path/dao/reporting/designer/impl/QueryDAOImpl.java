package com.path.dao.reporting.designer.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.designer.QueryDAO;
import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_QUERY_EXEC_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_QUERYVOKey;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_ENTITIESCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.QRY_GRIDSC;
import com.path.vo.reporting.designer.IRP_AD_HOC_QUERYSC;

public class QueryDAOImpl  extends BaseDAO implements QueryDAO
{
	public List<IRP_ENTITIESCO> retDBEntities(QRY_GRIDSC qryGridSC)	throws DAOException
	{
		DAOHelper.fixGridMaps(qryGridSC, getSqlMap(), "query.getDBEntitiesMap");
		return  getSqlMap().queryForList("query.getDBEntities", qryGridSC,qryGridSC.getRecToskip(),qryGridSC.getNbRec());

	}

	public int retDBEntitiesCount(QRY_GRIDSC qryGridSC) throws DAOException 
	{
		DAOHelper.fixGridMaps(qryGridSC, getSqlMap(), "query.getDBEntitiesMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("query.getDBEntitiesCnt", qryGridSC)).intValue();
		return nb;
	}
	
	public List<IRP_FIELDSCO> retDBFields(QRY_GRIDSC qryGridSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(qryGridSC, getSqlMap(), "query.getDBFieldsMap");
		ArrayList<IRP_FIELDSCO> list;
		if( qryGridSC.getNbRec() > 0 )
		{
		    list = (ArrayList<IRP_FIELDSCO>) getSqlMap().queryForList("query.getDBFields", qryGridSC,qryGridSC.getRecToskip(),qryGridSC.getNbRec());
		}
		else
		{
		    list = (ArrayList<IRP_FIELDSCO>) getSqlMap().queryForList("query.getDBFields", qryGridSC);
		}
		return  list;

	}
	
	public int retDBFieldsCount(QRY_GRIDSC qryGridSC) throws DAOException
	{
		DAOHelper.fixGridMaps(qryGridSC, getSqlMap(), "query.getDBFieldsMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("query.getDBFieldsCnt", qryGridSC)).intValue();
		return nb;
	}

	public void saveQuery(IRP_AD_HOC_QUERYVO vo) throws DAOException {		
		getSqlMap().insert("query.insertQuery", vo);
	}
	
	public void insertReportQry(IRP_REPORT_QUERYVOKey rqVO) throws DAOException {		
		getSqlMap().insert("IRP_REPORT_QUERY.insertIRP_REPORT_QUERY", rqVO);
	}
	
	public Integer update(IRP_AD_HOC_QUERYVO vo) throws DAOException {
	    return getSqlMap().update("query.updateQuery", vo);
	}

	public List<IRP_AD_HOC_QUERYCO> queriesByReportId(BigDecimal reportId) throws DAOException {		
		return getSqlMap().queryForList("query.selectQueriesByReportId", reportId);
	}

	public void deleteQuery(List<BigDecimal> queriesId) throws DAOException {
		getSqlMap().delete("query.delete", queriesId);			
	}
	
	public int checkIfIsQryArg(List<BigDecimal> queriesId) throws DAOException {
	    	int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("query.checkIfIsQryArg", queriesId)).intValue();
		return nb;
	}

	public int getQueriesCount(IRP_AD_HOC_QUERYSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "query.BaseResultMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("query.queriesCnt", sc))
				.intValue();
		return nb;
	}

	public List<IRP_AD_HOC_QUERYVO> getQueriesList(IRP_AD_HOC_QUERYSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "query.BaseResultMap");
		if(sc.getSortOrder()==null)
		{
			sc.setSortOrder(" ORDER BY QUERY_ID ");
		}
		return getSqlMap().queryForList("query.selectQueries", sc, sc.getRecToskip(), sc.getNbRec());
	}

	public IRP_AD_HOC_QUERYCO returnQueryById(BigDecimal queryId) throws DAOException {
		return (IRP_AD_HOC_QUERYCO) getSqlMap().queryForObject("query.selectQueryById", queryId);
	}

	public String testQuery(String sql) throws DAOException 
	{
		return (String)getSqlMap().queryForObject("query.testQuery", sql);
	}

	public String checkQryName(String qryName) throws DAOException 
	{
		return (String)getSqlMap().queryForObject("query.checkQryName", qryName);
	}

	public void deleteQueries(List<BigDecimal> reprotsId) throws DAOException {
		List<IRP_REPORT_QUERYVOKey> voList = getSqlMap().queryForList("query.selectQueriesId",reprotsId);
		List<BigDecimal> queriesId = new ArrayList<BigDecimal>();
		for(IRP_REPORT_QUERYVOKey vo :voList)
		{
			queriesId.add(vo.getQUERY_ID());
		}
		
		if(!queriesId.isEmpty())
		{
			getSqlMap().delete("query.deleteReportQuery", reprotsId);
			getSqlMap().delete("query.delete", queriesId);
		}		
	}
	
	public String testSqbQuery(String sql) throws DAOException 
	{
		return (String)getSqlMap().queryForObject("query.testSqbQuery", sql);
	}

	@Override
	public LinkedHashMap fillSqlFields(String sql,Connection paramCon) throws DAOException 
	{
        try
        { 
            IRP_FIELDSCO feCO;
            LinkedHashMap feMap=new LinkedHashMap();
//            Connection con = getSqlMap().getCurrentConnection();
            Connection con = paramCon;
            if(con==null)
            {
        	 con =getSqlMap().returnConnection();
            }

            Statement stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            String colBusinessName;
            String colType;
            HashMap<String, String> dbCols = new HashMap<String, String>();

            if (columnCount > 0)
            {
                for (int i = 1; i <= columnCount; i++)
                {

                    colBusinessName= rsmd.getColumnLabel(i);
                    if(dbCols.get(colBusinessName) != null)
                    {
                	throw new DAOException("Select expression results in more than one column having same name. Please assign an alias for duplicate columns. ");
                    }
                    
                    colType = rsmd.getColumnTypeName(i);
                    log.debug("_____"+colType);
                    if(ConstantsCommon.PARAM_TYPE_VARCHAR2.equals(colType) || RepConstantsCommon.varchar.toLowerCase(Locale.ENGLISH).equals(colType) || RepConstantsCommon.charact.equalsIgnoreCase(colType) ||  RepConstantsCommon.univarchar.toLowerCase(Locale.ENGLISH).equals(colType))
                    {
                    	colType=RepConstantsCommon.VARCHAR_TYPE_JASPER;
                    }
                    else if(ConstantsCommon.PARAM_TYPE_NUMBER.equals(colType) || RepConstantsCommon.numeric.toLowerCase(Locale.ENGLISH).equals(colType) || RepConstantsCommon.int_type.toLowerCase(Locale.ENGLISH).equals(colType) || RepConstantsCommon.decimal.toLowerCase(Locale.ENGLISH).equals(colType)){
                    	colType=RepConstantsCommon.NUMBER_TYPE_JASPER;
                    }
                    else if(ConstantsCommon.PARAM_TYPE_DATE.equalsIgnoreCase(colType) || ConstantsCommon.datetime.toLowerCase(Locale.ENGLISH).equals(colType))
                    {
                    	colType=RepConstantsCommon.DATE_TYPE_JASPER;
                    }
                    else if(RepConstantsCommon.blob.equalsIgnoreCase(colType))
                    {
                	colType=RepConstantsCommon.BLOB_TYPE_JASPER;
                    }
                    
                    feCO=new IRP_FIELDSCO();
                    feCO.setIndex(generateId()+i);
                    feCO.setFeName(colBusinessName);
                    feCO.setFIELD_ALIAS(colBusinessName);
                    feCO.setFIELD_DB_NAME(colBusinessName);
                    feCO.setFIELD_DATA_TYPE(colType);
                    feCO.setLabelAlias(colBusinessName);
                    feCO.setType("1");
                    feCO.setParent("1");
                    feCO.setIsLeaf("true");
                    feCO.setLevel("2");
                    feMap.put(feCO.getIndex(), feCO);
                    dbCols.put(colBusinessName, colBusinessName);
                }
            }

            stmt.close();
            con.close();
            rs.close();

            return feMap;

        }
        catch (Exception ex)
        {
            log.error(ex, ex.getMessage());
            throw new DAOException(ex.getMessage(), ex);
        }
	}
	
	public Long generateId()	
	{
		 Calendar cal = Calendar.getInstance();
		 return cal.getTimeInMillis();
	}

	@Override
	public HashMap<String,String> returnQryColumns(String syntax)	throws DAOException {
		HashMap<String,String> hm = new HashMap<String,String>();
        
		try {
			Connection con = getSqlMap().returnConnection();
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(syntax);
	        ResultSetMetaData rsmd = rs.getMetaData();
	        int columnCount = rsmd.getColumnCount();

	        if (columnCount > 0)
	        {
	            for (int i = 1; i <= columnCount; i++)
	            {
	                hm.put(rsmd.getColumnName(i).toUpperCase(), rsmd.getColumnTypeName(i));
	            }
	        }

	        stmt.close();
	        con.close();
		} catch (SQLException e) {
			log.error(e, "Error getting the columns from the resultset");
			e.printStackTrace();
		}
		
        return hm;
	}

	@Override
	public void insertQueryExecLog(IRP_QUERY_EXEC_LOGVO exec_LOGVO) throws DAOException 
	{
		getSqlMap().insert("query.insertQueryExecLog", exec_LOGVO);
	}
}
