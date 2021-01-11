package com.path.dao.reporting.ftr.cbParam.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.dao.reporting.ftr.cbParam.CbParamDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.cbParam.CbParamCO;
import com.path.vo.reporting.ftr.cbParam.CbParamSC;

public class CbParamDAOImpl extends BaseDAO implements CbParamDAO {

        /**
         * function getCbParamList get the list of data in the database
         * @return list of data from the database
         */
	public List<CbParamCO> getCbParamList(CbParamSC sc) throws DAOException{
		DAOHelper.fixGridMaps(sc, getSqlMap(), "cbParam.cbResultMap");
		return (ArrayList<CbParamCO>) getSqlMap().queryForList("cbParam.select"+sc.getPageRef(), sc);
	}
	/**
	  * function retcbParamListCount get the count of all rows in the table
	  * @return integer which is a count 
	  */
	public int retCbParamListCount(CbParamSC cbParamSC) throws DAOException
	{
	    return ((Integer)(getSqlMap().queryForObject("cbParam.ret"+cbParamSC.getPageRef()+"ListCount", cbParamSC))).intValue();
	}
	
	 /**
	   * function retCbParamMap added to get a hashMap of the data in database to check between the old values and the modified 
	   * values in the BO is used
	   * @return HashMap 
	   */
	public HashMap<String,CbParamCO> retCbParamMap(CbParamSC sc) throws DAOException 
	{
		return (HashMap)getSqlMap().queryForMap("cbParam.retCbParamMap"+sc.getPageRef(), sc, "concatKey");
	}
}
