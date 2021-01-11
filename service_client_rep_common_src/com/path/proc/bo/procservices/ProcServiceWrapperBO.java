package com.path.proc.bo.procservices;

import java.util.HashMap;
import java.util.List;

import com.path.lib.common.exception.BaseException;

/**
 * @author AdelNasrallah
 *
 */
public interface ProcServiceWrapperBO 
{
	/**
	 * @param HashMap<String,Object>
	 * @return HashMap<String,Object>
	 * @throws BaseException
	 */
	public HashMap<String,Object> returnFirstChartProgress(HashMap<String,Object> batchHM) throws BaseException;
	
	/**
	 * @param <HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 * @throws BaseException
	 */
	public List<HashMap<String, Object>> returnSecondChartProgress(HashMap<String,Object> batchHM) throws BaseException;
	
	/**
	 * @param <HashMap<String, Object>
	 * @return List<HashMap<String, Object>>
	 * @throws BaseException
	 */
	public List<HashMap<String, Object>> returnThirdChartProgress(HashMap<String,Object> batchHM) throws BaseException;
	
	/**
	 * @param HashMap<String,Object>
	 * @return List<HashMap<String, Object>>
	 * @throws BaseException
	 */
	public List<HashMap<String, Object>> returnThreadsInfo(HashMap<String,Object> batchHM) throws BaseException;
	
	public int returnThreadsInfoCount(HashMap<String,Object> batchHM) throws BaseException;
	
	/**
     *  return List of EOD Exception
     */
	public List<HashMap<String, Object>> returnEodExceptionList(HashMap<String,Object> batchHM) throws BaseException;
	
	/**
	 * @param HashMap<String,Object>
	 * @return List<HashMap<String, Object>>
	 * @throws BaseException
	 */
	public List<HashMap<String, Object>> returnBatchHistoricalChart(HashMap<String, Object> batchHM) throws BaseException;
	
	/**
	 * @param batchCriteria
	 * @return
	 * @throws BaseException
	 */
	public List<HashMap<String, Object>> returnBatchMonitorList(HashMap<String, Object> batchCriteria) throws BaseException;
	
	/**
	 * @param batchHM
	 * @return
	 * @throws BaseException
	 */
	public HashMap<String,HashMap<String,Object>> returnBatchControl(HashMap<String,Object> batchHM) throws BaseException;
	
	/**
	 * @param batchCriteria
	 * @return
	 */
	public HashMap<String, Object> runBatch(HashMap<String, Object> batchCriteria) throws BaseException;
	
	/**
	 * @param batchCriteria
	 * @return
	 */
	public HashMap<String, Object> pauseBatch(HashMap<String, Object> batchCriteria) throws BaseException;

	/**
	 * @param batchCriteria
	 * @return
	 */
	public HashMap<String, Object> resumeBatch(HashMap<String, Object> batchCriteria) throws BaseException;

	/**
	 * @param batchCriteria
	 * @return
	 */
	public HashMap<String, Object> stopBatch(HashMap<String, Object> batchCriteria) throws BaseException;
	
	/**
	 * @param batchCriteria
	 * @return
	 */
	public HashMap<String, Object> returnBatchStatus(HashMap<String, Object> batchCriteria) throws BaseException;

	/**
	 * @param microthreadCriteria
	 * @return
	 */
	public HashMap<String, Object> runMicrothread(HashMap<String, Object> microthreadCriteria) throws BaseException;
	
	/**
	 * Return list of all batches
	 * @param criteria
	 * @return
	 * @throws BaseException
	 */
	public List<HashMap<String, Object>> returnBatchList( HashMap<String, Object> criteria) throws BaseException;
	
	/**
	 * Return the status of the last run at a specific date
	 * @param batchCriteria
	 * @return
	 * @throws BaseException
	 */
	public boolean returnBatchInstanceStatus(HashMap<String, Object> batchCriteria) throws BaseException;

	/**
	 * Return list of all batches
	 * @param criteria
	 * @return
	 * @throws BaseException
	 */
	public int returnBatchListCount(HashMap<String, Object> criteria) throws BaseException;
	
	/**
	 * @param batchHM
	 * @return
	 * @throws BaseException
	 */
	public List<HashMap<String, Object>> returnBatchCompaniesList(HashMap<String, Object> batchHM) throws BaseException;
}
