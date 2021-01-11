package com.path.dao.reporting.designer.impl;

import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.designer.ProceduresDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.lib.common.util.StringUtil;
import com.path.struts2.lib.common.BaseObject;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_HASH_TABLECO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_PROCCO;
import com.path.vo.reporting.IRP_REP_PROC_PARAMSCO;
import com.path.vo.reporting.designer.IRP_REP_PROCSC;

public class ProceduresDAOImpl  extends BaseDAO implements ProceduresDAO
{

	public int getProceduresCount(IRP_REP_PROCSC sc) throws DAOException
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("proc.retProcListCount", sc)).intValue();
		return nb;
	}

	public List<IRP_REP_PROCCO> getProceduresList(IRP_REP_PROCSC sc)throws DAOException 
	{
		if(sc.getSortOrder()==null)
		{
			sc.setSortOrder(" ORDER BY  TBL.PROC_ORDER ");
		}
		if(sc.getIsGrid())
		{
			if(sc.getSortOrder()==null)
			{
				sc.setSortOrder(" ORDER BY EXEC_BEFORE,PROC_ORDER ");
			}
			DAOHelper.fixGridMaps(sc, getSqlMap(), "proc.retProcListMap");
			return  getSqlMap().queryForList("proc.retProcList", sc,sc.getRecToskip(),sc.getNbRec());
		}
		else
		{
			return  getSqlMap().queryForList("proc.retProcList", sc);
		}
		
	}

	public int getSysProcCount(IRP_REP_PROCSC sc) throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("proc.retSysProcListCount", sc)).intValue();
		return nb;
	}

	public List<IRP_REP_PROCCO> getSysProcList(IRP_REP_PROCSC sc)throws DAOException 
	{
		if(sc.getSortOrder()==null)
		{
			sc.setSortOrder("  ORDER BY TBL.PROC_NAME ");
		}
			DAOHelper.fixGridMaps(sc, getSqlMap(), "proc.retSysProcListMap");
			return  getSqlMap().queryForList("proc.retSysProcList", sc,sc.getRecToskip(),sc.getNbRec());
	}

	
	
	public int getProceduresParamsCount(IRP_REP_PROCSC sc) throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("proc.retProceduresParamsCount", sc)).intValue();
		return nb;
	}

	public List<IRP_REP_PROC_PARAMSCO> getProceduresParamsList(IRP_REP_PROCSC sc)throws DAOException 
	{
		if(sc.getSortOrder() == null)
		{
			sc.setSortOrder(" ORDER BY TBL.PARAM_ORDER");
		}
		if(sc.getIsGrid())
		{
			DAOHelper.fixGridMaps(sc, getSqlMap(), "proc.retProceduresParamsListMap");
			return  getSqlMap().queryForList("proc.retProceduresParamsList", sc,sc.getRecToskip(),sc.getNbRec());

		}
		else
		{
			return  getSqlMap().queryForList("proc.retProceduresParamsList", sc);
		}
	}

	public int getSysProcParamsCount(IRP_REP_PROCSC sc) throws DAOException 
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("proc.retSysProcParamsCount", sc)).intValue();
		return nb;
	}

	public List<IRP_REP_PROC_PARAMSCO> getSysProcParamsList(IRP_REP_PROCSC sc)throws DAOException 
	{
		if(sc.getSortOrder() == null)
		{
			sc.setSortOrder(" ORDER BY TBL.PARAM_ORDER ");
		}
		if(sc.getIsGrid())
		{
			DAOHelper.fixGridMaps(sc, getSqlMap(), "proc.retProceduresParamsListMap");
			return  getSqlMap().queryForList("proc.retSysProcParamsList", sc,sc.getRecToskip(),sc.getNbRec());
		}
		else
		{
			return  getSqlMap().queryForList("proc.retSysProcParamsList", sc);
		}

	}

	public List<IRP_REP_ARGUMENTSCO> retQryParamList(IRP_REP_PROCSC sc)throws DAOException 
	{
		DAOHelper.fixGridMaps(sc, getSqlMap(), "proc.retQryParamListMap");
		return  getSqlMap().queryForList("proc.retQryParamList", sc,sc.getRecToskip(),sc.getNbRec());

	}

	public int retQryParamListCount(IRP_REP_PROCSC sc) throws DAOException
	{
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject("proc.retQryParamListCount", sc)).intValue();
		return nb;
	}

	public void deleteParamsByProcedure(IRP_REP_PROCSC repProcSC)throws DAOException 
	{
		getSqlMap().delete("proc.deleteParamsByProcedure", repProcSC);
	}

	public HashMap<String, IRP_REP_PROC_PARAMSCO> retProcParamsValuesMap(IRP_REP_PROCSC procSC) throws DAOException 
	{
		return (HashMap)getSqlMap().queryForMap("proc.retProcParamsValuesMap", procSC, "PARAM_NAME");
	}

	public void createHashTbl(Connection con,List<IRP_HASH_TABLECO> hashTblList) throws DAOException 
	{
	    StringBuffer sqlHashTbl;
	   
	    	
	    	sqlHashTbl=new StringBuffer(" ");
	    	
	    	for(int i=0;i<hashTblList.size();i++)
	    	{
	    	    sqlHashTbl.append(hashTblList.get(i).getIrpHashTableVO().getHASH_TABLE_SCRIPT());
	    	    sqlHashTbl.append(" \n");
	    	}
	    
	    	CallableStatement stHashTbl = null;
	       	CallableStatement stHashTab = null;
        	try
        	{
        	    stHashTab = con.prepareCall("COMMIT");
        	    stHashTab.execute();
        	}
        	catch(SQLException e)
        	{
        	    log.error(e,e.getMessage());
        	}
	    	try
		{
	    	    stHashTbl =con.prepareCall(sqlHashTbl.toString());
	    	    stHashTbl.execute();
		}
		catch(SQLException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		finally {
			try
			{
			    //checking if stHashTbl is created before closing it 
			    if(stHashTbl!=null)
			    {
				stHashTbl.close();
			    }
			    if(stHashTab!=null)
			    {
				stHashTab.close();
			    }
			}
			catch(SQLException e)
			{
			    log.error(e,"ERROR in closing CallableStatement in proceduresDAO_createHashTbl");
			}
		}
	}
	
	public void dropHashTbl(Connection con,List<IRP_HASH_TABLECO> hashTblList) throws DAOException 
	{
	    StringBuffer sqlDropHashTbl;
	    boolean conAutoCommit;
	    sqlDropHashTbl=new StringBuffer(" ");
	    for(int i=0;i<hashTblList.size();i++)
	    {
		sqlDropHashTbl.append(" DROP TABLE ");
		sqlDropHashTbl.append(hashTblList.get(i).getIrpHashTableVO().getHASH_TABLE_NAME());
	    }
		//sqlDropHashTbl.append(str)
//	    sqlDropHashTbl.append("DROP TABLE #FTR_CASH_FLOW_TMP "
//                        	    	   +" DROP TABLE #ACC_BAL_TMP "
//                        	    	   +" DROP TABLE #ACC_BAL_TMP1 "
//                        	    	   +" DROP TABLE #TMP_REP_EXPREV " 
//                	                   );
	    CallableStatement stDropHashTbl = null;
	    try
	    {
		//get the connection autocommit state and save it
	    	conAutoCommit = con.getAutoCommit();
		stDropHashTbl =con.prepareCall(sqlDropHashTbl.toString());
		con.setAutoCommit(true);
		stDropHashTbl.execute();
		//return it to its original state
		con.setAutoCommit(conAutoCommit);
	    }
	    catch(SQLException e)
	    {
		log.error(e,"ERROR in executing CallableStatement in proceduresDAO_dropHashTbl");
	    }
	    finally {
		try
		{
		    //checking if stDropHashTbl is created before closing it 
		    if(stDropHashTbl!=null)
		    {
			stDropHashTbl.close();
		    }
		}
		catch(SQLException e)
		{
		    log.error(e,"ERROR in closing CallableStatement in proceduresDAO_dropHashTbl");
		}
	    }
	    
	}
	@Override
	public HashMap<String, Object> callProcedure(StringBuffer procStr,HashMap<String, Object> procValuesMap,List<IRP_REP_PROC_PARAMSCO> procSysParamsList,Connection con,String appName, IRP_AD_HOC_REPORTCO repCO,String procName,String user) throws DAOException  
	{

		CallableStatement st = null;
		StringBuffer result =new StringBuffer("");
		HashMap<String, Object> outProcValuesMap = new HashMap<String, Object>();
		try
		{
			st = con.prepareCall(procStr.toString());
			Object paramVal;
			IRP_REP_PROC_PARAMSCO paramCO;
			String paramType;
			String paramError;
			String paramName;
			String errorMessage ="";
			BigDecimal successValue = BigDecimal.ZERO;
			BigDecimal errorValue = BigDecimal.ZERO; 
			String strSuccessValue = "";
			String strErrorValue ="";
			for(int i=0;i<procSysParamsList.size();i++)
			{
				paramCO=procSysParamsList.get(i);
				paramType=paramCO.getPARAM_TYPE();
				paramVal=procValuesMap.get(paramCO.getPARAM_NAME());
				if((paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_VARCHAR2)) || (paramType.equalsIgnoreCase(RepConstantsCommon.varchar)))
				{
					if(paramVal==null)
					{
							st.setNull(paramCO.getPARAM_ORDER().intValue(),RepConstantsCommon.intVarchar );
					}
					else
					{
						st.setString(paramCO.getPARAM_ORDER().intValue(), (String)paramVal);
					}
				}
				else if(paramType.equalsIgnoreCase(RepConstantsCommon.charact))
				{
					if(paramVal==null)
					{
						st.setNull(paramCO.getPARAM_ORDER().intValue(),RepConstantsCommon.intChar );
					}
					else
					{
						st.setString(paramCO.getPARAM_ORDER().intValue(),(String)paramVal);
					}
				}
				else if(paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_NUMBER))
				{
					if(paramVal==null)
					{
							st.setNull(paramCO.getPARAM_ORDER().intValue(),RepConstantsCommon.intNumeric );
					}
					else
					{
					    	//replaced integer with bigdecimal to cover all decimal
						//st.setInt(paramCO.getPARAM_ORDER().intValue(),new Integer(paramValCO.getPARAM_VALUE()).intValue());
						st.setBigDecimal(paramCO.getPARAM_ORDER().intValue(),(BigDecimal)paramVal);
					}
				}
                		else if(paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_DATE) ||
                			paramType.equalsIgnoreCase(ConstantsCommon.datetime))
                		{
                		    if(paramVal==null)
                		    {
                			st.setNull(paramCO.getPARAM_ORDER().intValue(), RepConstantsCommon.intDate);
                		    }
                		    else
                		    {
                			// check if oracle and tpye =dateTime since oracle only
                			// have time but the user can fill date and time
                			if(paramVal instanceof  java.sql.Timestamp)
                			{
                			    st.setTimestamp(paramCO.getPARAM_ORDER().intValue(), (java.sql.Timestamp)paramVal);
                			}
                			else
                			{
                			    Date paramValDt=(Date)paramVal;
                			    st.setDate(paramCO.getPARAM_ORDER().intValue(),new java.sql.Date(paramValDt.getTime()));

                			}
                		    }
                		}
			}
//			
			if(st != null)
		    {
			
					for(int i=0;i<procSysParamsList.size();i++)
					{paramCO=procSysParamsList.get(i);
					 if (paramCO.getPARAM_IN_OUT().equals(RepConstantsCommon.OUT_PROC_PARAM))
					 {
					     paramType=paramCO.getPARAM_TYPE();
					     if((paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_VARCHAR2)) || (paramType.equalsIgnoreCase(RepConstantsCommon.varchar)))
						{
						 st.registerOutParameter(paramCO.getPARAM_ORDER().intValue(), java.sql.Types.VARCHAR);
						}
					     else if(paramType.equalsIgnoreCase(RepConstantsCommon.charact))
						{
						 st.registerOutParameter(paramCO.getPARAM_ORDER().intValue(), java.sql.Types.CHAR);
						}
					     else if(paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_NUMBER))
						{
						 //if the output parameter value has precision
						 if(!StringUtil.nullToEmpty(paramCO.getSUCCESS_VALUE()).equals("") && paramCO.getSUCCESS_VALUE().indexOf("-") > 0)
						 {
						     int scale=Integer.parseInt(paramCO.getSUCCESS_VALUE().split("-")[1].trim());
						     st.registerOutParameter(paramCO.getPARAM_ORDER().intValue(), java.sql.Types.NUMERIC,scale);
						     paramCO.setSUCCESS_VALUE(paramCO.getSUCCESS_VALUE().split("-")[0].trim());
						 }
						 else
						 {
						     st.registerOutParameter(paramCO.getPARAM_ORDER().intValue(), java.sql.Types.NUMERIC);
						 }
						}
					     else if(paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_DATE))
						{
						 st.registerOutParameter(paramCO.getPARAM_ORDER().intValue(), java.sql.Types.DATE);
						}
					     else if( paramType.equalsIgnoreCase(ConstantsCommon.datetime))
					     {
						 st.registerOutParameter(paramCO.getPARAM_ORDER().intValue(), java.sql.Types.TIMESTAMP);
					     }
									
					 }
					}
					
		BaseObject baseObj = new BaseObject();
		baseObj.applyTraceProps(appName, user, repCO.getPROG_REF(),repCO.getHttpSessionIdForLink());
		String procedureName=procName;
		if(procedureName.length() > 20)
		{
		    procedureName = procedureName.substring(0, 20);
		}
		callSqlSessionTrace(baseObj, "p:" + procedureName + " ra:" + repCO.getAPP_NAME(), con);		

			st.execute();
//			con.commit();
			for(int i=0;i<procSysParamsList.size();i++)
			{
			    paramCO=procSysParamsList.get(i);
			 if (paramCO.getPARAM_IN_OUT().equals(RepConstantsCommon.OUT_PROC_PARAM))
			 {
			     paramType=paramCO.getPARAM_TYPE();
			     paramError = paramCO.getOUTPUT_PARAM_TYPE();
			     paramName = paramCO.getPARAM_NAME();
			     if(((paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_VARCHAR2)) || (paramType.equalsIgnoreCase(RepConstantsCommon.varchar)) ||(paramType.equalsIgnoreCase(RepConstantsCommon.charact))) && st.getString(paramCO.getPARAM_ORDER().intValue())!=null )
				{ outProcValuesMap.put(paramName,st.getString(paramCO.getPARAM_ORDER().intValue()) );
				  if ((result.toString()).isEmpty())
				  {
          			     result = result.append(paramName).append(": ").append(st.getString(paramCO.getPARAM_ORDER().intValue()));
          			   }
				else 
         			   {
           			     result = result.append(",").append(paramName).append(": ").append(st.getString(paramCO.getPARAM_ORDER().intValue()));
           			   }
				
				 if (("2").equals(paramError))  {
					errorMessage =st.getString(paramCO.getPARAM_ORDER().intValue());
				     }
				 if (("1").equals(paramError))	     {
					strSuccessValue = paramCO.getSUCCESS_VALUE();
					strErrorValue =st.getString(paramCO.getPARAM_ORDER().intValue());
				     }
    				    
				}
			     else if(paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_NUMBER)&& (st.getBigDecimal(paramCO.getPARAM_ORDER().intValue())!=null))
				{
				  outProcValuesMap.put(paramName,st.getBigDecimal(paramCO.getPARAM_ORDER().intValue()) );
				     if ((result.toString()).isEmpty())
        				 {
					     result = result.append(paramName).append(": ").append(st.getBigDecimal(paramCO.getPARAM_ORDER().intValue()).toString());		        			
        				 }
        				 else
        				 {   
        				     result = result.append(",").append(paramName).append(": ").append(st.getBigDecimal(paramCO.getPARAM_ORDER().intValue()).toString());
        				 }
        				 
    				   if (("1").equals(paramError))	     {
    					successValue = new BigDecimal(paramCO.getSUCCESS_VALUE());
    					errorValue = st.getBigDecimal(paramCO.getPARAM_ORDER().intValue());
    				     }
				
				     
				}
			     else if(paramType.equalsIgnoreCase(ConstantsCommon.PARAM_TYPE_DATE) && st.getDate(paramCO.getPARAM_ORDER().intValue())!=null)
				{ 
				  outProcValuesMap.put(paramName,st.getDate(paramCO.getPARAM_ORDER().intValue()));
				 if ((result.toString()).isEmpty())
				 {
				    result = result.append(paramName).append(": ").append(st.getDate(paramCO.getPARAM_ORDER().intValue()).toString());		   
				 }
				else
				{
				    result = result.append(",").append(paramName).append(": ").append(st.getDate(paramCO.getPARAM_ORDER().intValue()).toString());
				 }
				    
				}
			     else if( paramType.equalsIgnoreCase(ConstantsCommon.datetime) && st.getTimestamp(paramCO.getPARAM_ORDER().intValue())!=null)
			     { 
				  outProcValuesMap.put(paramName,st.getDate(paramCO.getPARAM_ORDER().intValue()));				
				 if ((result.toString()).isEmpty())
				 {
				     result = result.append(paramName).append(": ").append(st.getTimestamp(paramCO.getPARAM_ORDER().intValue()).toString());				    
				 }
				 else
				 {
				     result = result.append(",").append(paramName).append(": ").append(st.getTimestamp(paramCO.getPARAM_ORDER().intValue()).toString());
				 }
				 
			     }
					
			    
			 }
		    }
			 if (errorValue.equals(successValue) && strErrorValue.equals(strSuccessValue))
			 {
			     result = result.append("Procedure Executed Successfully.");
			 }
			 else 
			 {  if (errorMessage.isEmpty())
			 {
			     log.error(">>>>>>>  The procedure returned an error. Error Code: "+getProcedureErrorMsg(errorValue.toString()));
			     throw new Exception(getProcedureErrorMsg(errorValue.toString()));
		
			 }
			 else 
			 {
			     log.error(">>>>>>>>  The procedure returned an error. Error Code: "+getProcedureErrorMsg(errorValue.toString())+ ", Error Message: "+ errorMessage);
			     throw new Exception(getProcedureErrorMsg(errorMessage));
			 }
				
			 }
		    }
		}
		catch (RuntimeException e) {
		    throw e;
		} 
		catch(Exception e)
		{
			log.error(e,"ERROR in proceduresDAO_callProcedure : "+procStr);
		    if(con != null)
		    {
			try
			{
			    con.rollback();
			}
			catch(Exception e1)
			{
			    log.error(e,"ERROR in rollback upon Exception proceduresDAO_callProcedure : "+procStr);
			}
		    }
		    if ((result.toString()).isEmpty())
			 {
			throw new DAOException(getProcedureErrorMsg(e.getMessage()),e);
			 }
		    else
		    {
			log.error(">>>>>>>>  Procedure Output Parameters: "+result.toString());
			throw new DAOException(getProcedureErrorMsg(e.getMessage()),e);
		
		    }
		}
		finally{
		    try
		    {
			//checking if st is created before closing it 
			if(st != null)
			{
			    st.close();
			}
		    }
		    catch(SQLException e)
		    {
			log.error(e,"ERROR in closing CallableStatement in proceduresDAO_callProcedure : "+procStr);
		    }
		}
		return outProcValuesMap;
		
	}
	private String getProcedureErrorMsg(String procMessage)
	    {
		String procMessage1=procMessage;
		String prefix, suffix = "&"; // for sybase and oracle the character '&'
		int fromIndex, endIndex;

		if(ConstantsCommon.CURR_DBMS_ORACLE == 1)
		{   // for oracle the part that precede the user error contains the
		    // below string
		    prefix = "ORA-20000: #";
		}
		else
		{   // for Sybase it contains the below string
		    prefix = ": # ";
		}

		fromIndex = procMessage.indexOf(prefix);

		if(fromIndex >= 0)
		{   endIndex = procMessage.indexOf(suffix, fromIndex);
		    // extracting the user error with the prefix
		procMessage1 = procMessage.substring(fromIndex, endIndex);
		    // replacing the prefix with empty string
		procMessage1= procMessage1.replace(prefix, "");
		}		

		return StringUtil.replaceProcedureMessage(procMessage1);
	    }
		
	public IRP_REP_PROCCO retProcIdByProcName(IRP_REP_PROCSC prSC)throws DAOException 
	{
		return (IRP_REP_PROCCO) getSqlMap().queryForObject("proc.retProcIdByProcName", prSC);
	}
	
	public int checkProcUsageById(IRP_REP_PROCSC prSC)throws DAOException 
	{
		return ((Integer) getSqlMap().queryForObject("proc.checkProcUsageById", prSC)).intValue();
	}

	public void deleteProcParamsByReportId(IRP_REP_PROCSC procSC)throws DAOException 
	{
		getSqlMap().delete("proc.deleteProcParamsByReportId", procSC);
	}
	
	public void deleteProcsByReportId(IRP_REP_PROCSC procSC)throws DAOException 
	{
		getSqlMap().delete("proc.deleteProcsByReportId", procSC);
	}

}
