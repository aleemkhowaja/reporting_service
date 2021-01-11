package com.path.dao.reporting.ftr.cbParam;

import java.util.HashMap;
import java.util.List;

import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.cbParam.CbParamCO;
import com.path.vo.reporting.ftr.cbParam.CbParamSC;

public interface CbParamDAO {
	public List<CbParamCO> getCbParamList(CbParamSC sc) throws DAOException;
	public int retCbParamListCount(CbParamSC cbParamSC) throws DAOException;
	public HashMap<String,CbParamCO> retCbParamMap(CbParamSC sc) throws DAOException ;
}