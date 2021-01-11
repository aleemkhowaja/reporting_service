package com.path.bo.reporting.common.impl;

import java.util.ArrayList;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepMenuBO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.MenuVO;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;

public class RepMenuBOImpl extends BaseBO implements RepMenuBO
{
	public ArrayList<MenuVO> queriesMenu(String qryMenuId,	List<IRP_AD_HOC_QUERYCO> queriesList, String... menuTrans) throws BaseException {
	    	String menuId= qryMenuId;
	    	ArrayList<MenuVO> menuList = new ArrayList<MenuVO>();
		MenuVO menuVO = null;
		IRP_AD_HOC_QUERYCO queryCO = null;
		ArrayList<IRP_FIELDSCO> fieldsList = null;
		ArrayList<IRP_REP_ARGUMENTSCO> parametersList = new ArrayList<IRP_REP_ARGUMENTSCO>();
//		List<VARIABLE_OBJECT> variablesList = null;
		boolean showFields = false, showParameters = false;//, showVariables = false
		
		
		if(! ConstantsCommon.PROGREF_ROOT.equals(menuId)){
			if(menuId.contains("Fields_DKL1SBH9UJWC_")){
				showFields = true;
				menuId = menuId.substring(20);
			}
			else if(menuId.contains("Parameters_DKL1SBH9UJWC_")){
				showParameters = true;
				menuId = menuId.substring(24);
			}
//			else if(menuId.contains("Variables_DKL1SBH9UJWC_")){
//				showVariables = true;
//				menuId = menuId.substring(23);
//			}
		}
		
		
		for(IRP_AD_HOC_QUERYCO query : queriesList) 
		{
			if(ConstantsCommon.PROGREF_ROOT.equals(menuId))
			{
				menuVO = new MenuVO();
				menuVO.setMenuName(query.getQUERY_NAME());
				menuVO.setMenuVar(query.getQUERY_NAME().replaceAll(" ", ""));
				menuVO.setLeaf(false);
				menuList.add(menuVO);
			}
			else if( query.getQUERY_NAME().replaceAll(" ", "").equals(menuId) ) {
				queryCO = query;
				break;
			}
		}			
		
		if(queryCO != null)
		{
			if(showFields){
				fieldsList = queryCO.getSqlObject().getAllFields();
				if(fieldsList != null){				
					for(IRP_FIELDSCO field : fieldsList)
					{					
						menuVO = new MenuVO();
						menuVO.setMenuName(field.getFeName());
						menuVO.setMenuVar(String.valueOf(field.getIndex()));
						menuVO.setLeaf(true);
						menuVO.setOnLeafClick("insertField('"+field.getFeName()+"','"+field.getLabelAlias().replaceAll(" ", "")+"','"+field.getFIELD_DATA_TYPE()+"','"+queryCO.getIndex()+"','"+queryCO.getSqlObject().getOutputLayout()+"')");
						menuList.add(menuVO);
					}
				}
			}
			else if(showParameters){
				if(queryCO.getSqlObject().getArgumentsList() != null){
					parametersList.addAll(queryCO.getSqlObject().getArgumentsList().values());
					for(IRP_REP_ARGUMENTSCO param : parametersList)
					{					
						menuVO = new MenuVO();
						menuVO.setMenuName(param.getARGUMENT_NAME());
						menuVO.setMenuVar(String.valueOf(param.getIndex()));
						menuVO.setLeaf(true);
						menuVO.setOnLeafClick("insertParameter('"+param.getARGUMENT_NAME()+"','"+param.getJrxmlType()+"')");
						menuList.add(menuVO);
					}
				}
			}
//			else if(showVariables){
//				variablesList = queryCO.getVariablesList();
//				for(VARIABLE_OBJECT var : variablesList)
//				{					
//					menuVO = new MenuVO();
//					menuVO.setMenuName(var.getVarName());
//					menuVO.setMenuVar(var.getVarName());
//					menuVO.setLeaf(true);
//					menuVO.setOnLeafClick("alert('NOTHING FOR NOW')");
//					menuList.add(menuVO);
//				}
//			}
			else{
				menuVO = new MenuVO();
				menuVO.setMenuName(menuTrans[0]);
				menuVO.setMenuVar("Fields_DKL1SBH9UJWC_"+queryCO.getQUERY_NAME().replaceAll(" ", ""));
				menuVO.setLeaf(false);
				menuList.add(menuVO);
				
				menuVO = new MenuVO();
				menuVO.setMenuName(menuTrans[1]);
				menuVO.setMenuVar("Parameters_DKL1SBH9UJWC_"+queryCO.getQUERY_NAME().replaceAll(" ", ""));
				menuVO.setLeaf(false);
				menuList.add(menuVO);
				
//				menuVO = new MenuVO();
//				menuVO.setMenuName("Additional Fields");
//				menuVO.setMenuVar("Variables_DKL1SBH9UJWC_"+queryCO.getQUERY_NAME().replaceAll(" ", ""));
//				menuVO.setLeaf(false);
//				menuList.add(menuVO);
			}
		}	
		
		return menuList;
	}

}
