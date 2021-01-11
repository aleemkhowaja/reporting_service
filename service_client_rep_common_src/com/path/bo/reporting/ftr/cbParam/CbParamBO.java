package com.path.bo.reporting.ftr.cbParam;

import java.util.List;

import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.cbParam.CbParamCO;
import com.path.vo.reporting.ftr.cbParam.CbParamSC;

public interface CbParamBO {
	public List<CbParamCO> getCbParamList(CbParamSC sc) throws BaseException;
	public int retcbParamListCount(CbParamSC cbParamSC) throws BaseException;
	public void updateCbParamCOList( List<CbParamCO> updatedList,AuditRefCO refCO, CbParamSC sc) throws BaseException;
}