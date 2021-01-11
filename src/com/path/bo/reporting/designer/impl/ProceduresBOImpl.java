package com.path.bo.reporting.designer.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.designer.ProceduresBO;
import com.path.dao.reporting.designer.ProceduresDAO;
import com.path.dbmaps.vo.IRP_PROCVO;
import com.path.dbmaps.vo.IRP_REP_PROCVO;
import com.path.dbmaps.vo.IRP_REP_PROC_PARAMSVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_HASH_TABLECO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_PROCCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.designer.IRP_REP_PROCSC;

public class ProceduresBOImpl extends BaseBO implements ProceduresBO
{
	private ProceduresDAO procDAO;
	private CommonRepFuncBO commonRepFuncBO;

	public ProceduresDAO getProcDAO() {
		return procDAO;
	}

	public void setProcDAO(ProceduresDAO procDAO) {
		this.procDAO = procDAO;
	}

	
	public CommonRepFuncBO getCommonRepFuncBO() {
		return commonRepFuncBO;
	}

	public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO) {
		this.commonRepFuncBO = commonRepFuncBO;
	}

	public int getProceduresCount(IRP_REP_PROCSC sc) throws BaseException 
	{
		return procDAO.getProceduresCount(sc);
	}

	public List<IRP_REP_PROCCO> getProceduresList(IRP_REP_PROCSC sc)throws BaseException 
	{
		return procDAO.getProceduresList(sc);
	}

	public int getSysProcCount(IRP_REP_PROCSC sc) throws BaseException 
	{
		return procDAO.getSysProcCount(sc);
	}

	public List<IRP_REP_PROCCO> getSysProcList(IRP_REP_PROCSC sc)throws BaseException 
	{
		return procDAO.getSysProcList(sc);
	}



	public void saveParams(ArrayList<IRP_REP_PROC_PARAMSCO> paramsArr,IRP_REP_PROCSC sc,BigDecimal procedureId,IRP_REP_PROCSC repProcSC,HashMap<Long, BigDecimal> argIdsMap)throws BaseException 
	{
	    IRP_REP_PROC_PARAMSCO paramCO;
	    IRP_REP_PROC_PARAMSVO paramVO;
	    BigDecimal procId=procedureId;
		try
		{
			if(paramsArr!=null && !paramsArr.isEmpty())
			{
				BigDecimal newParamId;
				for(int j=0;j<paramsArr.size();j++)
				{
					paramCO=paramsArr.get(j);
					if(procId==null)
					{
						procId=paramCO.getPROC_ID();
					}
					if(paramCO.getPARAM_ID()!=null )
					{
						paramVO=new IRP_REP_PROC_PARAMSVO();
						paramVO.setREPORT_ID(sc.getREP_ID());
						paramVO.setPROC_ID(procId);
						
						newParamId=argIdsMap.get(paramCO.getPARAM_ID().longValue());
						if(newParamId==null)
						{
							paramVO.setPARAM_ID(paramCO.getPARAM_ID());
						}
						else
						{
							paramVO.setPARAM_ID(newParamId);
						}
						
						
						paramVO.setPARAM_NAME(paramCO.getPARAM_NAME());
						paramVO.setPARAM_ORDER(paramCO.getPARAM_ORDER());
						paramVO.setOUTPUT_PARAM_TYPE(paramCO.getOUTPUT_PARAM_TYPE());
						paramVO.setSUCCESS_VALUE(paramCO.getSUCCESS_VALUE());
						genericDAO.insert(paramVO);
					}
				}
			}
		}
		catch(Exception e)
		{
			 throw new BaseException(e);
		}
	}
	public IRP_PROCVO retProcVOFromRepProcCO(IRP_REP_PROCCO procCO)
	{
		IRP_PROCVO procVO=new IRP_PROCVO();
		procVO.setPROC_DESC(procCO.getPROC_DESC());
		procVO.setPROC_NAME(procCO.getPROC_NAME());
		procVO.setPROC_ID(procCO.getPROC_ID());
		return procVO;
	}
	
	public IRP_REP_PROCVO retRepProcVOFromRepProcCO(IRP_REP_PROCCO procCO,IRP_REP_PROCSC sc)
	{
		IRP_REP_PROCVO repProcVO=new IRP_REP_PROCVO();
		repProcVO.setPROC_ID(procCO.getPROC_ID());
		repProcVO.setREPORT_ID(sc.getREP_ID());
		repProcVO.setPROC_ORDER(procCO.getPROC_ORDER());
		repProcVO.setEXEC_BEFORE(procCO.getEXEC_BEFORE());
		return repProcVO;
	}

	public int getProceduresParamsCount(IRP_REP_PROCSC sc) throws BaseException 
	{
		return procDAO.getProceduresParamsCount(sc);
	}


	public int getSysProcParamsCount(IRP_REP_PROCSC sc) throws BaseException {
		return procDAO.getSysProcParamsCount(sc);
	}

	public List<IRP_REP_PROC_PARAMSCO> getProceduresParamsList(IRP_REP_PROCSC sc)throws BaseException 
	{
		return  procDAO.getProceduresParamsList(sc);
	}

	public List<IRP_REP_PROC_PARAMSCO> getSysProcParamsList(IRP_REP_PROCSC sc)throws BaseException 
	{
		return procDAO.getSysProcParamsList(sc);
	}

	public List<IRP_REP_ARGUMENTSCO> retQryParamList(IRP_REP_PROCSC sc)throws BaseException 
	{
		return procDAO.retQryParamList(sc);
	}

	public int retQryParamListCount(IRP_REP_PROCSC sc) throws BaseException 
	{
		return procDAO.retQryParamListCount(sc);
	}

	public HashMap<String, IRP_REP_PROC_PARAMSCO> retProcParamsValuesMap(IRP_REP_PROCSC procSC) throws BaseException 
	{
		return procDAO.retProcParamsValuesMap(procSC);
	}

	@Override
	public HashMap<String, Object> callProcedure(StringBuffer procStr,HashMap<String, Object> procValuesMap,List<IRP_REP_PROC_PARAMSCO> procSysParamsList,Connection con,String appName, IRP_AD_HOC_REPORTCO repCO,String procName,String user) throws BaseException 
	{
		return procDAO.callProcedure( procStr,procValuesMap,procSysParamsList,con, appName,  repCO, procName, user);
	}
	
	public void createHashTbl(Connection con,List<IRP_HASH_TABLECO> hashTblList) throws BaseException 
	{
	    procDAO.createHashTbl(con,hashTblList);
	}
	
	public void dropHashTbl(Connection con,List<IRP_HASH_TABLECO> hashTblList) throws BaseException 
	{
	    procDAO.dropHashTbl(con,hashTblList);
	}
}
