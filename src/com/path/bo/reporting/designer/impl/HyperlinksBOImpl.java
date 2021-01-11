package com.path.bo.reporting.designer.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.designer.HyperlinksBO;
import com.path.dao.reporting.designer.HyperlinksDAO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.DesignerGridSC;
import com.path.vo.reporting.IRP_REP_HYPERLINKSCO;

public class HyperlinksBOImpl  extends BaseBO implements HyperlinksBO 
{
	private HyperlinksDAO hyperlinksDAO;

	public HyperlinksDAO getHyperlinksDAO() {
		return hyperlinksDAO;
	}

	public void setHyperlinksDAO(HyperlinksDAO hyperlinksDAO) {
		this.hyperlinksDAO = hyperlinksDAO;
	}

	public int retHyperlinksCnt(DesignerGridSC sc) throws BaseException 
	{
		return hyperlinksDAO.retHyperlinksCnt(sc);
	}

	public List<IRP_REP_HYPERLINKSCO> retHyperlinksList(DesignerGridSC sc)throws BaseException 
	{
		return hyperlinksDAO.retHyperlinksList(sc);
	}

	@Override
	public Integer deleteHyperlink(IRP_REP_HYPERLINKSCO hypCO)throws BaseException 
	{
		Integer row= hyperlinksDAO.deleteHyperlink(hypCO);
		if(hypCO.getPARAM_DELETE()==null) //called when deleting a hyperlink from the grid
		{
			//apply audit
			auditBO.callAudit( hypCO.getHyperlinkVO(), hypCO.getHyperlinkVO(), hypCO.getAuditRefCO());
			auditBO.insertAudit(hypCO.getAuditRefCO());
		}
		return row;
	}

	public int retHyperlinkParamsCnt(DesignerGridSC sc) throws BaseException
	{
		return hyperlinksDAO.retHyperlinkParamsCnt(sc);
	}

	public List<IRP_REP_HYPERLINKSCO> retHyperlinkParamsList(DesignerGridSC sc)throws BaseException 
	{
		return hyperlinksDAO.retHyperlinkParamsList(sc);
	}

	public void saveHyperlinks(HashMap<String,List<IRP_REP_HYPERLINKSCO>> hypParamsMap,ArrayList<IRP_REP_HYPERLINKSCO> lstAdd,ArrayList<IRP_REP_HYPERLINKSCO> lstMod,AuditRefCO refCO,HashMap<String, List<IRP_REP_HYPERLINKSCO>> oldHypParamsMap)throws BaseException
	{
		try 
		{
			
			IRP_REP_HYPERLINKSCO hypCO;
			BigDecimal reportId;
			Long fieldIndex;
			Integer row=null;
			List<IRP_REP_HYPERLINKSCO> lstParams;
			DesignerGridSC designerGridSC;
			//save new records
			for(int i=0;i<lstAdd.size();i++)
			{
				hypCO=lstAdd.get(i);
				reportId=hypCO.getHyperlinkVO().getREPORT_ID();
				fieldIndex=hypCO.getHyperlinkVO().getFIELD_INDEX().longValue();
				lstParams=hypParamsMap.get(reportId+"_"+fieldIndex);
				if(lstParams==null) // the params are not mapped , then we will save the default values
				{
					designerGridSC=new DesignerGridSC();
					designerGridSC.setRep_Id(reportId);
					designerGridSC.setLink_rep_Id(hypCO.getHyperlinkVO().getLINKED_REPORT_ID());
					designerGridSC.setField_index(new BigDecimal(fieldIndex));  //to put it long later
					designerGridSC.setCol_name(hypCO.getHyperlinkVO().getCOLUMN_NAME());
					lstParams=retHyperlinkParamsList(designerGridSC);
				}
				saveHyperlink(lstParams,false,hypCO,row);
				hypParamsMap.remove(reportId+"_"+fieldIndex);
				
				//apply audit
				refCO.setAuditCO(null);
				refCO.setOperationType(AuditConstant.CREATED);
				auditBO.callAudit(null, hypCO.getHyperlinkVO(),refCO);
			}
			
			List<IRP_REP_HYPERLINKSCO> oldList;
			String key;
			for(int i=0;i<lstMod.size();i++)
			{
				hypCO=lstMod.get(i);
				reportId=hypCO.getHyperlinkVO().getREPORT_ID();
				fieldIndex=hypCO.getHyperlinkVO().getFIELD_INDEX().longValue();
				key=reportId+"_"+fieldIndex;
				lstParams=hypParamsMap.get(key);
				saveHyperlink(lstParams,true,hypCO,row);
				
				//apply audit
				
				oldList=oldHypParamsMap.get(key);
				if(oldList!=null)
				{
					refCO.setOperationType(AuditConstant.UPDATE);
					refCO.setAuditCO(null);
					for(int j=0;j<oldList.size();j++)
					{
						auditBO.callAudit((oldList.get(j)).getHyperlinkVO(),(lstParams.get(j)).getHyperlinkVO(),refCO);
					}
					auditBO.insertAudit(refCO);
					
					hypParamsMap.remove(key);
				}
			}
			
			//save the updated parameters
			for(Map.Entry<String,List<IRP_REP_HYPERLINKSCO>> e : hypParamsMap.entrySet())
			{
			    	key=e.getKey();
				lstParams=e.getValue();
				if(!lstParams.isEmpty())
				{
					hypCO=lstParams.get(0);
					saveHyperlink(lstParams,true,hypCO,row);
					
					
					//apply audit
					refCO.setOperationType(AuditConstant.UPDATE);
					refCO.setAuditCO(null);
					oldList=oldHypParamsMap.get(key);
					for(int j=0;j<oldList.size();j++)
					{
						auditBO.callAudit((oldList.get(j)).getHyperlinkVO(),(lstParams.get(j)).getHyperlinkVO(),refCO);
					}
					auditBO.insertAudit(refCO);
				}
				
			}
			hypParamsMap.clear();
		} 
		catch (Exception e) 
		{
			log.error(e,"");
			throw new BOException(e);
		}
		
	}
	
	public void saveHyperlink(List<IRP_REP_HYPERLINKSCO> lstParam,boolean isDelete,IRP_REP_HYPERLINKSCO hypCO,Integer rrow)throws Exception
	{
	    List<IRP_REP_HYPERLINKSCO> lstParams=lstParam;
	    IRP_REP_HYPERLINKSCO hypParamCO;
	    Integer row=rrow;
		if(lstParams!=null && !lstParams.isEmpty())
		{
			if(isDelete)
			{
				//check if date_updated has been changed
				for(int j=0;j<lstParams.size();j++)
				{
					hypParamCO=lstParams.get(j);
					hypParamCO.setPARAM_DELETE("1");
					row=deleteHyperlink(hypParamCO);
					if(row==null || row <1)
					{
						throw new BOException(MessageCodes.RECORD_CHANGED);
					}
				}
				
				
			}
		
			for(int j=0;j<lstParams.size();j++)
			{
				hypParamCO=lstParams.get(j);
				genericDAO.insert(hypParamCO.getHyperlinkVO());
			}
		}
	}

	public List<IRP_REP_HYPERLINKSCO> retHyperlinksParams(DesignerGridSC sc)throws BaseException 
	{
		return hyperlinksDAO.retHyperlinksParams(sc);
	}

	public List<BigDecimal> retHypReportUsage(List<BigDecimal> reprotsId)throws BaseException 
	{
		return hyperlinksDAO.retHypReportUsage(reprotsId);
	}

	public int retIfRepHasHyp(BigDecimal reportId) throws BaseException 
	{
		return hyperlinksDAO.retIfRepHasHyp(reportId);
	}
	
}
