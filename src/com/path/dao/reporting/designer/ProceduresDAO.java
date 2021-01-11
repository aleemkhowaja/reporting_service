package com.path.dao.reporting.designer;

import java.sql.Connection;
import java.util.HashMap;
import java.util.List;

import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_HASH_TABLECO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_PROCCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.designer.IRP_REP_PROCSC;

public interface ProceduresDAO 
{
	public int getProceduresCount(IRP_REP_PROCSC sc)  throws DAOException;;
	public List<IRP_REP_PROCCO> getProceduresList(IRP_REP_PROCSC sc)  throws DAOException;
	public int getSysProcCount(IRP_REP_PROCSC sc) throws DAOException;
	public List<IRP_REP_PROCCO> getSysProcList(IRP_REP_PROCSC sc) throws DAOException;
	
	public int getProceduresParamsCount(IRP_REP_PROCSC sc)  throws DAOException;;
	public List<IRP_REP_PROC_PARAMSCO> getProceduresParamsList(IRP_REP_PROCSC sc)  throws DAOException;
	public int getSysProcParamsCount(IRP_REP_PROCSC sc) throws DAOException;
	public List<IRP_REP_PROC_PARAMSCO> getSysProcParamsList(IRP_REP_PROCSC sc) throws DAOException;
	
	public int retQryParamListCount(IRP_REP_PROCSC sc) throws DAOException;
	public List<IRP_REP_ARGUMENTSCO> retQryParamList(IRP_REP_PROCSC sc) throws DAOException;
	
	public void deleteParamsByProcedure(IRP_REP_PROCSC repProcSC) throws DAOException;
	
	public HashMap<String,IRP_REP_PROC_PARAMSCO> retProcParamsValuesMap(IRP_REP_PROCSC procSC) throws DAOException;

    /**
     * execute the procedures that should be called before and after the report
     * execution
     * 
     * @param procStr
     * @param procValuesMap
     * @param procSysParamsList
     * @param con
     * @param appName
     * @param repCO
     * @param procName
     * @param user
     * @return
     * @throws DAOException
     */
	public HashMap<String, Object> callProcedure(StringBuffer procStr,HashMap<String,Object>procValuesMap,List<IRP_REP_PROC_PARAMSCO>procSysParamsList,Connection con,String appName, IRP_AD_HOC_REPORTCO repCO,String procName,String user)throws DAOException;
	public IRP_REP_PROCCO retProcIdByProcName(IRP_REP_PROCSC prSC)throws DAOException;
	public int checkProcUsageById(IRP_REP_PROCSC prSC)throws DAOException;
	public void deleteProcParamsByReportId(IRP_REP_PROCSC procSC)throws DAOException;
	public void deleteProcsByReportId(IRP_REP_PROCSC procSC)throws DAOException;
	public void createHashTbl(Connection con,List<IRP_HASH_TABLECO> hashTblList) throws DAOException;
	public void dropHashTbl(Connection con,List<IRP_HASH_TABLECO> hashTblList) throws DAOException;
}
