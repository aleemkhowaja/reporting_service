package com.path.dao.reporting.designer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_AD_HOC_QUERYVO;
import com.path.dbmaps.vo.IRP_QUERY_EXEC_LOGVO;
import com.path.dbmaps.vo.IRP_REPORT_QUERYVOKey;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_ENTITIESCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.QRY_GRIDSC;
import com.path.vo.reporting.designer.IRP_AD_HOC_QUERYSC;

public interface QueryDAO 
{
	public List<IRP_ENTITIESCO> retDBEntities(QRY_GRIDSC qryGridSC) throws DAOException;
	public int retDBEntitiesCount(QRY_GRIDSC qryGridSC)throws DAOException;
	public List<IRP_FIELDSCO> retDBFields(QRY_GRIDSC qryGridSC) throws DAOException;
	public int retDBFieldsCount(QRY_GRIDSC qryGridSC)throws DAOException;
	public void saveQuery(IRP_AD_HOC_QUERYVO vo) throws DAOException;
	public void insertReportQry(IRP_REPORT_QUERYVOKey rqVO) throws DAOException;
	public List<IRP_AD_HOC_QUERYCO> queriesByReportId(BigDecimal reportId) throws DAOException;
	public void deleteQuery(List<BigDecimal> queriesId) throws DAOException;
	public int getQueriesCount(IRP_AD_HOC_QUERYSC sc) throws DAOException;
	public List<IRP_AD_HOC_QUERYVO> getQueriesList(IRP_AD_HOC_QUERYSC sc) throws DAOException;
	public IRP_AD_HOC_QUERYCO returnQueryById(BigDecimal queryId) throws DAOException;
	public String testQuery(String sql)throws DAOException;
	public String checkQryName(String qryName)throws DAOException;
	public Integer update(IRP_AD_HOC_QUERYVO vo) throws DAOException;
	public void deleteQueries(List<BigDecimal> reprotsId) throws DAOException;
	public String testSqbQuery(String sql)throws DAOException;
	/**
	 * Method that fills the sql fields
	 * @param sql
	 * @param con
	 * @return
	 * @throws DAOException
	 */
	public LinkedHashMap fillSqlFields(String sql, Connection con)throws DAOException;
	public HashMap<String,String> returnQryColumns(String syntax) throws DAOException;
	public int checkIfIsQryArg(List<BigDecimal> queriesId)throws DAOException;

	/**
	 * Method Insert IRP_QUERY_EXEC_LOG
	 * @param IRP_QUERY_EXEC_LOGVO
	 * @throws DAOException
	 */
	public void insertQueryExecLog(IRP_QUERY_EXEC_LOGVO exec_LOGVO) throws DAOException;
}
