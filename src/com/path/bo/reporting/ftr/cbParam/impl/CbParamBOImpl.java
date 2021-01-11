package com.path.bo.reporting.ftr.cbParam.impl;

import java.util.HashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.cbParam.CbParamBO;
import com.path.dao.reporting.ftr.cbParam.CbParamDAO;
import com.path.dbmaps.vo.FTR_CB_CODEVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.cbParam.CbParamCO;
import com.path.vo.reporting.ftr.cbParam.CbParamSC;

public class CbParamBOImpl extends BaseBO implements CbParamBO {
	private CbParamDAO cbParamDAO;

	public CbParamDAO getCbParamDAO()
	{
	    return cbParamDAO;
	}

	public void setCbParamDAO(CbParamDAO cbParamDAO)
	{
	    this.cbParamDAO = cbParamDAO;
	}

	    /**
	     * function getCbParamList returns the list of data in the database
	     * @return list of CO Values
	     */
	public List<CbParamCO> getCbParamList(CbParamSC sc) throws BaseException
	{
	    return cbParamDAO.getCbParamList(sc);
	}
	 /**
	     * function retcbParamListCount get the count of all rows in the table
	     * @return integer which is a count 
	     */
	public int retcbParamListCount(CbParamSC cbParamSC) throws BaseException
	{
	    if(1==ConstantsCommon.CURR_DBMS_SQLSERVER)
	    {
		cbParamSC.setIsSQLServer(ConstantsCommon.CURR_DBMS_SQLSERVER);
	    }
	    return cbParamDAO.retCbParamListCount(cbParamSC);
	}
	 /**
	     * function updateCbParamCOList get the modified from the action and make the modification in 
	     * the for loop make sure if entity_cb_code is null make the delete
	     * else if Date_Updated  is null that means their is no value for this row make the insert
	     *      else make the update if their is no one changing the row  in the same time 
	     * when finished we assure to make the audit
	     * @return integer list of C0
	     */
	public void updateCbParamCOList(List<CbParamCO> updatedList,AuditRefCO refCO, CbParamSC sc) throws BaseException
	{
		HashMap<String,CbParamCO> oldMap=cbParamDAO.retCbParamMap(sc);
		FTR_CB_CODEVO cbVO;
		CbParamCO oldCbVO;
		Integer row;
		for(int i=0;i<updatedList.size();i++)
		{
		    cbVO=updatedList.get(i).getFtr_cb_codeVO();
		    if(NumberUtil.emptyDecimalToNull(cbVO.getENTITY_CB_CODE()) == null)
		    {
			    genericDAO.delete(cbVO) ;
		    }
		    else
		    {
			if(cbVO.getDATE_UPDATED()== null)
			{
			    if(sc.getPageRef().equals(RepConstantsCommon.TFA_PRODUCT_PAGE_REF))
			    {
				oldCbVO=oldMap.get(cbVO.getENTITY_CODE().toString()+cbVO.getENTITY_TYPE());
			    }
			    else
			    {
				oldCbVO = oldMap.get(cbVO.getENTITY_CODE().toString()+cbVO.getSUB_ENTITY_CODE().toString());
			    }
			    if(oldCbVO != null && oldCbVO.getFtr_cb_codeVO().getENTITY_CB_CODE()==null)
			    {
			    	genericDAO.insert(cbVO);
			    }
			    else
			    {
				throw new BOException(MessageCodes.RECORD_CHANGED); 
			    }
			}
			else
			{
			    row = genericDAO.update(cbVO);
			    if (row == null || row < 1)
			    {
				throw new BOException(MessageCodes.RECORD_CHANGED); 
			    }
			}
		    }
		    FTR_CB_CODEVO oldVO;
		    if(sc.getPageRef().equals(RepConstantsCommon.TFA_PRODUCT_PAGE_REF))
		    {
			oldVO=oldMap.get(cbVO.getENTITY_CODE().toString()+cbVO.getENTITY_TYPE()).getFtr_cb_codeVO();
		    }
		    else
		    {
			oldVO=oldMap.get(cbVO.getENTITY_CODE().toString()+ cbVO.getSUB_ENTITY_CODE().toString()).getFtr_cb_codeVO();
		    }
		    if (ConstantsCommon.EMPTY_BIGDECIMAL_VALUE.equals(cbVO.getENTITY_CB_CODE()))
		    {
			cbVO.setENTITY_CB_CODE(null);
		    }
		
		    auditBO.callAudit(oldVO, cbVO, refCO);
		    auditBO.insertAudit(refCO);
		    
		}
	}
}
