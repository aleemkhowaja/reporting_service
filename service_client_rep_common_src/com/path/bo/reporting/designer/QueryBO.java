package com.path.bo.reporting.designer;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_QUERY_EXEC_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_QUERYVOKey;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_ENTITIESCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.QRY_GRIDSC;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.designer.IRP_AD_HOC_QUERYSC;

public interface QueryBO 
{
	public List<IRP_AD_HOC_QUERYCO> queriesByReportId(BigDecimal reportId) throws BaseException, IOException, ClassNotFoundException;
	public int getQueriesCount(IRP_AD_HOC_QUERYSC sc) throws BaseException;
	public List<IRP_AD_HOC_QUERYVO> getQueriesList(IRP_AD_HOC_QUERYSC sc) throws BaseException;
	public void deleteQuery(List<BigDecimal> queriesId,AuditRefCO refCO) throws BaseException;
	public IRP_AD_HOC_QUERYCO returnQueryById(BigDecimal queryId, boolean generateSyntax) throws BaseException, IOException, ClassNotFoundException;
	public List<IRP_ENTITIESCO> retDBEntities(QRY_GRIDSC qryGridSC) throws BaseException;
	public int retDBEntitiesCount(QRY_GRIDSC qryGridSC) throws BaseException;
	public List<IRP_FIELDSCO> retDBFields(QRY_GRIDSC qryGridSC) throws BaseException;
	public int retDBFieldsCount(QRY_GRIDSC qryGridSC) throws BaseException;
	public String parseExpression(String expressionString,SQL_OBJECT sqlObj)throws BaseException;
	public String checkExpressionValidity(String expStr)throws BaseException;
	public StringBuffer generateSql(SQL_OBJECT sqlObj) throws BaseException;
	public String checkQryName(String qryName) throws BaseException;
	public void deleteQueries(List<BigDecimal> reprotsId) throws BaseException;
	public void validateSqbQuery(String sqlString)throws BaseException;
	/**
	 * Method that validates the query passed as argument against current db or the conId passed
	 * as argument
	 * @param sqlString
	 * @param args
	 * @param conId
	 * @throws BaseException
	 */
	public void validateSqbQueryDesigner(StringBuffer sqlString, ArrayList<IRP_REP_ARGUMENTSCO> args, BigDecimal conId)throws BaseException;
	/**
	 * Method that fills the query fields
	 * @param sqlString
	 * @param conId
	 * @return
	 * @throws BaseException
	 */
	public LinkedHashMap fillSqlFields(String sqlString, BigDecimal conId)throws BaseException;
	public void updateQuery(IRP_AD_HOC_QUERYVO queryvo) throws BaseException;
	public void insertQuery(IRP_AD_HOC_QUERYVO queryvo) throws BaseException;
	public void insertReportQry(IRP_REPORT_QUERYVOKey rqVO) throws BaseException;
	public HashMap<String,String> returnQryColumns(String syntax) throws BaseException;
	public SQL_OBJECT xmlToSqlObject(byte[] qryObj)throws BaseException;   
	/**
	 * Method to fill Sql Object
	 * @param query
	 * @throws ClassNotFoundException
	 * @throws IOException
	 */
	public void fillSqlObj(IRP_AD_HOC_QUERYCO query) throws ClassNotFoundException, IOException;
	
	/**
	 * Method Insert IRP_QUERY_EXEC_LOG
	 * @param IRP_QUERY_EXEC_LOGVO
	 * @throws BaseException
	 */
	public void insertQueryExecLog_NewTrans(IRP_QUERY_EXEC_LOGVO exec_LOGVO) throws BaseException;
	
	/**
	 * Method Update IRP_QUERY_EXEC_LOG
	 * @param IRP_QUERY_EXEC_LOGVO
	 * @throws BaseException
	 */
	public void updateQueryExecLog(IRP_QUERY_EXEC_LOGVO exec_LOGVO) throws BaseException;
}
