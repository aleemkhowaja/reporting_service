package com.path.bo.reporting.designer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_PROCVO;
import com.path.dbmaps.vo.IRP_REP_PROCVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_HASH_TABLECO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_PROCCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.designer.IRP_REP_PROCSC;


public interface ProceduresBO 
{
	public int getProceduresCount(IRP_REP_PROCSC sc) throws BaseException;
	public List<IRP_REP_PROCCO> getProceduresList(IRP_REP_PROCSC sc) throws BaseException;
	public int getSysProcCount(IRP_REP_PROCSC sc) throws BaseException;
	public List<IRP_REP_PROCCO> getSysProcList(IRP_REP_PROCSC sc) throws BaseException;
//	public void saveProcedures(IRP_REP_PROCSC sc,ArrayList<IRP_REP_PROCCO> lstAdd,ArrayList<IRP_REP_PROCCO> lstMod,ArrayList<IRP_REP_PROCCO> lstDel,HashMap<String,ArrayList<IRP_REP_PROC_PARAMSCO>> procMap) throws BaseException;
	
	public int getProceduresParamsCount(IRP_REP_PROCSC sc) throws BaseException;
	public List<IRP_REP_PROC_PARAMSCO> getProceduresParamsList(IRP_REP_PROCSC sc) throws BaseException;
	public int getSysProcParamsCount(IRP_REP_PROCSC sc) throws BaseException;
	public List<IRP_REP_PROC_PARAMSCO> getSysProcParamsList(IRP_REP_PROCSC sc) throws BaseException;
	
	public int retQryParamListCount(IRP_REP_PROCSC sc) throws BaseException;
	public List<IRP_REP_ARGUMENTSCO> retQryParamList(IRP_REP_PROCSC sc) throws BaseException;
	
	public HashMap<String,IRP_REP_PROC_PARAMSCO> retProcParamsValuesMap(IRP_REP_PROCSC procSC) throws BaseException;

    /**
     * execute the procedures that should be called before and after the report execution 
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
     * @throws BaseException
     */
	public HashMap<String, Object> callProcedure(StringBuffer procStr,HashMap<String,Object>procValuesMap,List<IRP_REP_PROC_PARAMSCO>procSysParamsList,Connection con,String appName, IRP_AD_HOC_REPORTCO repCO,String procName,String user)throws BaseException;
	
//	public void saveProceduresParams(HashMap<String,ArrayList<IRP_REP_PROC_PARAMSCO>> paramsMap,IRP_REP_PROCSC sc,HashMap<Long, BigDecimal> argIdsMap)throws BaseException;
	public IRP_PROCVO retProcVOFromRepProcCO(IRP_REP_PROCCO procCO)throws BaseException;
	public IRP_REP_PROCVO retRepProcVOFromRepProcCO(IRP_REP_PROCCO procCO,IRP_REP_PROCSC sc)throws BaseException;
	public void saveParams(ArrayList<IRP_REP_PROC_PARAMSCO> paramsArr,IRP_REP_PROCSC sc,BigDecimal procId,IRP_REP_PROCSC repProcSC,HashMap<Long, BigDecimal> argIdsMap)throws  BaseException; 
	public void createHashTbl(Connection con,List<IRP_HASH_TABLECO> hashTblList)throws BaseException;
	public void dropHashTbl(Connection con,List<IRP_HASH_TABLECO> hashTblList)throws BaseException;
}
