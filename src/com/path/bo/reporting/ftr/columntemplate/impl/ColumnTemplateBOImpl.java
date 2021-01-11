package com.path.bo.reporting.ftr.columntemplate.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.CommonRepFuncBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.columntemplate.ColumnTemplateBO;
import com.path.bo.reporting.ftr.columntemplate.ColumnTemplateDetBO;
import com.path.dao.reporting.ftr.columntemplate.ColumnTemplateDAO;
import com.path.dbmaps.vo.COLMNTMPLTVO;
import com.path.dbmaps.vo.COLMNTMPLT_PARAM_LINKVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLT_PARAM_LINKCO;
import com.path.vo.reporting.ftr.columnTemplate.FTR_TMPLT_EXPRCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;

public class ColumnTemplateBOImpl extends BaseBO implements ColumnTemplateBO {
	private ColumnTemplateDAO columnTemplateDAO;
	private ColumnTemplateDetBO columnTemplateDetBO;
	private CommonRepFuncBO commonRepFuncBO;
	private COLMNTMPLTCO columnTmpltCO;

	public COLMNTMPLTCO getColumnTmpltCO() {
		return columnTmpltCO;
	}

	public void setColumnTmpltCO(COLMNTMPLTCO columnTmpltCO) {
		this.columnTmpltCO = columnTmpltCO;
	}

	public CommonRepFuncBO getCommonRepFuncBO() {
		return commonRepFuncBO;
	}

	public void setCommonRepFuncBO(CommonRepFuncBO commonRepFuncBO) {
		this.commonRepFuncBO = commonRepFuncBO;
	}

	public ColumnTemplateDetBO getColumnTemplateDetBO() {
		return columnTemplateDetBO;
	}

	public void setColumnTemplateDetBO(ColumnTemplateDetBO columnTemplateDetBO) {
		this.columnTemplateDetBO = columnTemplateDetBO;
	}

	public ColumnTemplateDAO getColumnTemplateDAO() {
		return columnTemplateDAO;
	}

	public void setColumnTemplateDAO(ColumnTemplateDAO columnTemplateDAO) {
		this.columnTemplateDAO = columnTemplateDAO;
	}

	// Get all the Column Template Headers, that means the Column Templates that
	// have Line Number equal to zero
	@Override
	public List<COLMNTMPLTCO> findAllColumnTemplates(COLMNTMPLTSC sc)
			throws BaseException {
		return columnTemplateDAO.findAllColumnTemplates(sc);
	}

	// Get count all the Column Template Headers, that means count of Column
	// Templates that have Line Number equal to zero
	@Override
	public Integer findAllColumnTemplatesCount(COLMNTMPLTSC sc)
			throws BaseException {
		return columnTemplateDAO.findAllColumnTemplatesCount(sc);
	}

	@Override
	public COLMNTMPLTVO retrieveColumnTemplate(COLMNTMPLTVO colVO)
			throws BaseException {
		return (COLMNTMPLTVO) genericDAO.selectByPK(colVO);
	}

	// Get all Column Template Details of a Column Template Header
	@Override
	public List<COLMNTMPLTCO> findColumnTemplateDetails(COLMNTMPLTSC sc)
			throws BaseException {
		return columnTemplateDAO.findColumnTemplateDetails(sc);
	}

	@Override
	public List<FTR_TMPLT_EXPRCO> findExprList(COLMNTMPLTVO sc)
			throws BaseException {
		List<FTR_TMPLT_EXPRCO> exprList = columnTemplateDAO.findExprList(sc);
		for (int i = 0; i < exprList.size(); i++) {
			FTR_TMPLT_EXPRCO exprCO = exprList.get(i);
			if (exprCO.getFtrTmpltExprVO().getEXP_TYPE()
					.equals(RepConstantsCommon.TMPLT_EXPR_LINE)) {
				exprCO.getFtrTmpltExprVO().setEXP_VALUE(
						exprCO.getFtrTmpltExprVO().getEXP_LINE());
				exprCO.getFtrTmpltExprVO().setEXP_LINE(null);
			}
		}
		return exprList;
	}

	public void deleteColumnTemplate(COLMNTMPLTVO key) throws BaseException {
		// Delete related Criterias
	    	COLMNTMPLT_PARAM_LINKVO paramLinkVO = new COLMNTMPLT_PARAM_LINKVO();
	    	paramLinkVO.setCOMP_CODE(key.getCOMP_CODE());
		paramLinkVO.setTEMP_CODE(key.getCODE());
		deleteCrtsByColumnTemplate(paramLinkVO);

		// Delete related Expressions
		FTR_TMPLT_EXPRVO ftrTmpltExprVO = new FTR_TMPLT_EXPRVO();
		ftrTmpltExprVO.setCOMP_CODE(key.getCOMP_CODE());
		ftrTmpltExprVO.setCODE(key.getCODE());
		ftrTmpltExprVO.setTMPLT_TYPE("C");
		deleteExpressionsByColumnTemplate(ftrTmpltExprVO);

		// Delete the Column Template with related lines
		delete(key);

		// apply audit
		auditBO.callAudit(key, key, key.getAuditRefCO());
		auditBO.insertAudit(key.getAuditRefCO());
	}

	 /**
         * delete criteria by company , template and line number
         * 
         * @param COLMNTMPLT_PARAM_LINKVO key
         * @return
         * @throws BaseException 
         * 
         */
	private void deleteCrtsByColumnTemplate(COLMNTMPLT_PARAM_LINKVO key)

			throws BaseException {
		columnTemplateDAO.deleteCrtsByColumnTemplate(key);
	}

	// Delete the Column Template with related lines, that means no need to send
	// the line number with the other parameters,
	// Since the header and lines exist in the same table with the same
	// COMP_CODE AND Template CODE
	public void delete(COLMNTMPLTVO key) throws BaseException {
		columnTemplateDAO.delete(key);
	}

	public void deleteLines(COLMNTMPLTVO key) throws BaseException {
		columnTemplateDAO.deleteLines(key);
	}

	@Override
	public COLMNTMPLTVO findSingleColumnTemplate(COLMNTMPLTVO key)
			throws BaseException {
		return columnTemplateDAO.findSingleColumnTemplate(key);
	}

	@Override
	public List<CommonDetailsVO> findDepartmentsBetweenDiv(
			CommonDetailsSC commonDetailsSC) throws BaseException {
		return columnTemplateDAO.findDepartmentsBetweenDiv(commonDetailsSC);
	}

	@Override
	public List<CommonDetailsVO> findDepartments(CommonDetailsSC commonDetailsSC)
			throws BaseException {
		return columnTemplateDAO.findDepartments(commonDetailsSC);
	}

	public List<CommonDetailsVO> findUnits(CommonDetailsSC commonDetailsSC)
			throws BaseException {
		return columnTemplateDAO.findUnits(commonDetailsSC);
	}

	public int findUnitsListCount(CommonDetailsSC sc) throws BaseException {
		return columnTemplateDAO.findUnitsListCount(sc);
	}

	public CommonDetailsVO findSingleUnit(CommonDetailsSC commonDetailsVO)
			throws BaseException {
		return columnTemplateDAO.findSingleUnit(commonDetailsVO);
	}

	@Override
	public void insert(COLMNTMPLTVO vo) throws BaseException {
		columnTemplateDAO.insert(vo);
	}

	public void update(COLMNTMPLTVO vo) throws BaseException {
		Integer row = columnTemplateDAO.update(vo);
		if (row == null || row < 1) {
			throw new BOException(MessageCodes.RECORD_CHANGED);
		}
	}

	@Override
	public COLMNTMPLTCO findSingleColumnTemplateDet(COLMNTMPLTSC sc)
			throws BaseException {

		return columnTemplateDAO.findSingleColumnTemplateDet(sc);
	}

	@Override
	public Integer findColumnTemplateDetailsCount(COLMNTMPLTSC sc)
			throws BaseException {
		return columnTemplateDAO.findColumnTemplateDetailsCount(sc);
	}

	@Override
	public Integer countOfDepartmentsFunc(CommonDetailsSC commonDetailsSC)
			throws BaseException {
		return columnTemplateDAO.countOfDepartmentsFunc(commonDetailsSC);
	}

	public Integer findSingleColumnTemplatesCount(COLMNTMPLTSC sc)
			throws BaseException {
		return columnTemplateDAO.findSingleColumnTemplatesCount(sc);
	}

	public void saveColumnTemplate(String detGridMode, String linesGridMode,
			COLMNTMPLTCO currentTemplate, String userName,
			COLMNTMPLTVO oldColTmpl,String lang) throws BaseException {
		java.util.Date dt = new java.util.Date();
		Calendar cal = Calendar.getInstance(); 
		cal.setTime(dt);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);		
		
		AuditRefCO refCO = currentTemplate.getAuditRefCO();
		if (RepConstantsCommon.MODE_EDIT.equals(detGridMode)) {
			refCO.setOperationType(AuditConstant.UPDATE);
			currentTemplate.getColmnTmpltVO().setDATE_MODIFIED(dt);
			currentTemplate.getColmnTmpltVO().setMODIFIED_BY(userName);
			currentTemplate.getColmnTmpltVO().setDATE_UPDATED(
					oldColTmpl.getDATE_UPDATED());
			update(currentTemplate.getColmnTmpltVO());

			// apply audit
			COLMNTMPLTVO currVO = currentTemplate.getColmnTmpltVO();
			currVO.setDATE_CREATED(oldColTmpl.getDATE_CREATED());
			currVO.setCREATED_BY(oldColTmpl.getCREATED_BY());
			auditBO.callAudit(oldColTmpl, currVO, refCO);
		} else// i.e when detGridMode="add"
		{
			refCO.setOperationType(AuditConstant.CREATED);
			currentTemplate.getColmnTmpltVO().setDATE_CREATED(cal.getTime());
			currentTemplate.getColmnTmpltVO().setCREATED_BY(userName);
			currentTemplate.getColmnTmpltVO().setLINE_NBR(BigDecimal.ZERO);
			insert(currentTemplate.getColmnTmpltVO());

			// apply audit
			COLMNTMPLTVO currVO = currentTemplate.getColmnTmpltVO();
			auditBO.callAudit(null, currVO, refCO);
		}

		/*
		 * SmartSC smartSC = new SmartSC();
		 * smartSC.setAppName(refCO.getAppName());
		 * smartSC.setCompCode(columnTmpltCO.getColmnTmpltVO().getCOMP_CODE());
		 * smartSC.setBranchCode(new BigDecimal(1));
		 * smartSC.setRunningDate(refCO.getRunningDate());
		 * smartSC.setUserId(refCO.getUserID());
		 * smartSC.setProgRef(refCO.getProgRef());
		 * smartBO.validateAndUpdateSmartDetails(columnTmpltCO.getSmartCOList(),
		 * smartSC, refCO,columnTmpltCO);
		 */

		// Check if any update done on the lines, expressions or criteria of the
		// column template
		if (linesGridMode != null && !"".equals(linesGridMode)) {
			HashMap<BigDecimal, COLMNTMPLTVO> oldColDetMap = currentTemplate
					.getOldColDetMap();
			FTR_TMPLT_EXPRVO key = new FTR_TMPLT_EXPRVO();
			BigDecimal compCode = currentTemplate.getColmnTmpltVO().getCOMP_CODE();
			BigDecimal comntmpltCode = currentTemplate.getColmnTmpltVO().getCODE();
			key.setCOMP_CODE(compCode);
			key.setCODE(comntmpltCode);
			key.setTMPLT_TYPE("C");

			// delete existing lines and expressions
			deleteExpressionsByColumnTemplate(key);
			deleteLines(currentTemplate.getColmnTmpltVO());
			FTR_TMPLT_EXPRVO vo;
			FTR_TMPLT_EXPRVO oldExprVO;
			
			//check if the criteria is not loaded before then fill the DB crtMap before calling the 'deleteCrtsByColumnTemplate'
            	    if(currentTemplate.getColumnDetails() != null)
            	    {
            		for(COLMNTMPLTCO co : currentTemplate.getColumnDetails())
            		{
            		    if(co.getDbCrtMap().isEmpty() && co.getAddCrtMap().isEmpty() && co.getDelCrtMap().isEmpty()
            			    && co.getModifCrtMap().isEmpty())
            		    {
            			COLMNTMPLTSC crtSC = new COLMNTMPLTSC();
            			crtSC.setCOMP_CODE(compCode);
            			crtSC.setCODE(co.getColmnTmpltVO().getCODE());
            			crtSC.setLINE_NBR(co.getColmnTmpltVO().getLINE_NBR());
            			crtSC.setLANG_CODE(lang);
            			// get the db criterias as list then put it in the
            			// linkedHashMap
            			List<COLMNTMPLT_PARAM_LINKCO> crtList = getCrtList(crtSC);
            			for(int j = 0; j < crtList.size(); j++)
            			{
            			    COLMNTMPLT_PARAM_LINKCO crtCO = crtList.get(j);
            			    co.getDbCrtMap().put(crtCO.getConcatKey(), crtCO);
            			}
            		    }
            		}
            	    }
	       		 
			COLMNTMPLT_PARAM_LINKVO crtVO = new COLMNTMPLT_PARAM_LINKVO();
			crtVO.setCOMP_CODE(compCode);
			crtVO.setTEMP_CODE(comntmpltCode);
			deleteCrtsByColumnTemplate(crtVO);
			if (currentTemplate.getColumnDetails() != null) {
				for (COLMNTMPLTCO co : currentTemplate.getColumnDetails()) {
				    co.getColmnTmpltVO().setLINE_NBR(co.getNewLineNumber());
                    		    if(NumberUtil.isEmptyDecimal(co.getColmnTmpltVO().getFROM_CY())                    			    )
                    		    {
                    			co.getColmnTmpltVO().setFROM_CY(BigDecimal.ZERO);
                    			co.getColmnTmpltVO().setTO_CY(BigDecimal.ZERO);
                    		    }
                    		    else
                    		    {
				    	co.getColmnTmpltVO().setTO_CY(co.getColmnTmpltVO().getFROM_CY());
                    		    }
				    	insert(co.getColmnTmpltVO());

					// apply audit
					refCO.setOperationType(AuditConstant.UPDATE);
					if (oldColDetMap.get(co.getColmnTmpltVO().getLINE_NBR()) == null) 
					{
					    auditBO.callAudit(co, co, refCO);
					} 
					else 
					{
					    auditBO.callAudit(oldColDetMap.get(co.getColmnTmpltVO()
							.getLINE_NBR()), co.getColmnTmpltVO(), refCO);
					}

					if (co.getExpressions() != null) {
						for (FTR_TMPLT_EXPRCO exprCO : co.getExpressions()) {
							if (exprCO.getFtrTmpltExprVO().getEXP_TYPE() != null) // if
																					// it
																					// not
																					// an
																					// empty
																					// row
							{
								vo = new FTR_TMPLT_EXPRVO();
								vo.setCOMP_CODE(exprCO.getFtrTmpltExprVO()
										.getCOMP_CODE());
								vo.setTMPLT_TYPE(exprCO.getFtrTmpltExprVO()
										.getTMPLT_TYPE());
								vo
										.setCODE(exprCO.getFtrTmpltExprVO()
												.getCODE());
								if(NumberUtil.isEmptyDecimal(exprCO.getNewLineNumber()))
                                				{
                                				    vo.setTMPLT_LINE_NO(exprCO.getFtrTmpltExprVO().getTMPLT_LINE_NO());
                                				}
                                				else
                                				{
                                				    vo.setTMPLT_LINE_NO(exprCO.getNewLineNumber());
                                				}
								vo.setLINE_NO(exprCO.getFtrTmpltExprVO()
										.getLINE_NO());
								vo.setEXP_TYPE(exprCO.getFtrTmpltExprVO()
										.getEXP_TYPE());
								vo.setLEFT_OPERATOR(exprCO.getFtrTmpltExprVO()
										.getLEFT_OPERATOR());
								vo.setEXP_LINE(exprCO.getFtrTmpltExprVO()
										.getEXP_LINE());
								vo.setEXP_VALUE(exprCO.getFtrTmpltExprVO()
										.getEXP_VALUE());
								vo.setRIGHT_OPERATOR(exprCO.getFtrTmpltExprVO()
										.getRIGHT_OPERATOR());
								vo.setOPERATOR(exprCO.getFtrTmpltExprVO()
										.getOPERATOR());

								// To be compared with PB business
								if (vo.getEXP_TYPE().equals("L")) {
									exprCO.getFtrTmpltExprVO().setEXP_LINE(
											vo.getEXP_VALUE());
									exprCO.getFtrTmpltExprVO().setEXP_VALUE(
											null);
								}

								saveExpr(vo);

								// apply audit
								oldExprVO = co.getOldExprMap()
										.get(
												exprCO.getFtrTmpltExprVO()
														.getLINE_NO());
								if (oldExprVO != null) {
									auditBO.callAudit(oldExprVO, vo, refCO);
								}
							}
						}
					}

					// SAVE REALTED CRITERIAS
					Iterator<COLMNTMPLT_PARAM_LINKCO> itCrt = co.getDbCrtMap()
							.values().iterator();
					while (itCrt.hasNext()) {
						COLMNTMPLT_PARAM_LINKCO myCrtCO = itCrt
								.next();
						if (co.getColmnTmpltVO() == null) // if it is a new
															// template
						{
							saveCrt(myCrtCO, RepConstantsCommon.MODE_ADD, null, null);
						} else {
							saveCrt(myCrtCO, RepConstantsCommon.MODE_ADD, refCO, null
									);
						}
					}

					// Delete existing crts
					itCrt = co.getDelCrtMap().values().iterator();
					while (itCrt.hasNext()) {
						COLMNTMPLT_PARAM_LINKCO myCrtCO = itCrt
								.next();
						saveCrt(myCrtCO, RepConstantsCommon.MODE_DELETE, refCO, null);
					}
					
					// Save new crts
					itCrt = co.getAddCrtMap().values().iterator();
					while (itCrt.hasNext()) {
						COLMNTMPLT_PARAM_LINKCO myCrtCO = itCrt
								.next();
						if (co.getColmnTmpltVO() == null) // if it is a new
															// template
						{
							saveCrt(myCrtCO, RepConstantsCommon.MODE_ADD, null, null);
						} else {
							saveCrt(myCrtCO, RepConstantsCommon.MODE_ADD, refCO, null
								);
						}
					}

					// Update existing crts
					Iterator<BigDecimal> ittCrt = co.getModifCrtMap().keySet()
							.iterator();
					while (ittCrt.hasNext()) {
						BigDecimal crtKey = ittCrt.next();
						COLMNTMPLT_PARAM_LINKCO myCrtCO = (COLMNTMPLT_PARAM_LINKCO) co
								.getModifCrtMap().get(crtKey);
						saveCrt(myCrtCO,  RepConstantsCommon.MODE_UPDATE, refCO,
								(COLMNTMPLT_PARAM_LINKCO) co.getOldCrtMap()
										.get(crtKey));
					}

				}
			}
		}

		if ( RepConstantsCommon.MODE_EDIT.equals(detGridMode)) {
			auditBO.insertAudit(refCO);
		}
	}

	public void saveCrt(COLMNTMPLT_PARAM_LINKCO myCrtCO,
			 String mode, AuditRefCO refCO,
			COLMNTMPLT_PARAM_LINKCO oldCrtCO) {
		try {
		    	COLMNTMPLT_PARAM_LINKVO crtVO = convertCrtCOToVO(myCrtCO);
			if (RepConstantsCommon.MODE_ADD.equals(mode)) {
				saveCrt(crtVO);

				// apply audit
				if (refCO != null) {
					refCO.setOperationType(AuditConstant.UPDATE);
					refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
					auditBO.callAudit(crtVO, crtVO, refCO);
				}
			} else if (RepConstantsCommon.MODE_UPDATE.equals(mode)) {
				updateCrt(crtVO);

				// apply audit
				COLMNTMPLT_PARAM_LINKVO oldCrtVO = convertCrtCOToVO(oldCrtCO);

				refCO.setOperationType(AuditConstant.UPDATE);
				refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
				auditBO.callAudit(oldCrtVO, crtVO, refCO);
			} else if (RepConstantsCommon.MODE_DELETE.equals(mode)) {
			    	if(!NumberUtil.isEmptyDecimal(myCrtCO.getNewLineNumber()))
			    	{
			    	    crtVO.setLINE_NO(myCrtCO.getNewLineNumber());
			    	}
				deleteCrt(crtVO);

				// apply audit
				refCO.setOperationType(AuditConstant.UPDATE);
				refCO.setKeyRef(AuditConstant.TEMPLATE_KEY_REF);
				auditBO.callAudit(crtVO, crtVO, refCO);
			}
		} catch (BaseException e) {
			e.printStackTrace();
		}
	}

	public void saveCrt(COLMNTMPLT_PARAM_LINKVO crtVO) throws BaseException {
		genericDAO.insert(crtVO);
	}

	public void updateCrt(COLMNTMPLT_PARAM_LINKVO crtVO) throws BaseException {
		genericDAO.update(crtVO);
	}

	public void deleteCrt(COLMNTMPLT_PARAM_LINKVO crtVO) throws BaseException {
		genericDAO.delete(crtVO);
	}

	public COLMNTMPLT_PARAM_LINKVO convertCrtCOToVO(
			COLMNTMPLT_PARAM_LINKCO crtCO ) {
	    	COLMNTMPLT_PARAM_LINKVO crtVO = new COLMNTMPLT_PARAM_LINKVO();
		try {
			crtVO.setCODE1(new BigDecimal(crtCO.getCODE1()));
		} catch (Exception e) {
			crtVO.setCODE1(crtCO.getColumntmpltParamLinkVO().getCODE1());
		}

		crtVO.setCODE2(crtCO.getColumntmpltParamLinkVO().getCODE2());
		crtVO.setCOMP_CODE(crtCO.getColumntmpltParamLinkVO().getCOMP_CODE());
		crtVO.setCRITERIA_CODE(crtCO.getColumntmpltParamLinkVO()
				.getCRITERIA_CODE());
		String include = crtCO.getColumntmpltParamLinkVO().getINCLUDE().equals(
				RepConstantsCommon.YES_CAP) ? RepConstantsCommon.Y : RepConstantsCommon.N;
		crtVO.setINCLUDE(include);
		crtVO.setOPERATOR(crtCO.getColumntmpltParamLinkVO().getOPERATOR());
		crtVO
				.setSUB_LINE_NO(crtCO.getColumntmpltParamLinkVO()
						.getSUB_LINE_NO());
		crtVO.setTEMP_CODE((crtCO.getColumntmpltParamLinkVO().getTEMP_CODE()));
		if(NumberUtil.isEmptyDecimal(crtCO.getNewLineNumber()))
		{
		    crtVO.setLINE_NO((crtCO.getColumntmpltParamLinkVO().getLINE_NO()));
		}
		else
		{
		    crtVO.setLINE_NO(crtCO.getNewLineNumber());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMT_BY_GL)) {
			crtVO.setOPERATOR(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}
		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MAT_DATE)) {
			crtVO.setMAT_DATE(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_ASSET_TRANS_TYPE)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_FOREX_DEAL_TYPE)) {
			crtVO.setTRS_ASSET_TYPE(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_TENURE)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_PERIOD)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MBK)) {
			crtVO.setOPERATOR1(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMT_BY_GL)) {
			crtVO.setOPERATOR(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_PER)
				&& (crtCO.getCRI_LINE_GL() != null && crtCO.getCRI_LINE_GL()
						.equals("L"))) {
			crtVO.setPERCENTAGE(new BigDecimal(crtCO.getVALUE1() == null ? null
					: crtCO.getVALUE1()));
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_CAT_TYPE)) {
			crtVO.setTYPE1(crtCO.getCODE1() == null ? null : crtCO.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_RESIDENT)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_TOTAL_DC)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_ASSET_TRANS_TYPE)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_FOREX_DEAL_TYPE)
				|| crtCO.getCRITERIA_TYPE_CODE().equals("WCL")
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CATEG_REGION)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MAT_DATE)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_PERIOD)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CBK_CIF_TYPE)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_GL_TERM)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_SEC_CATEG)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_TENURE)
				|| crtCO.getCRITERIA_TYPE_CODE().equals("WOD")
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMT_BY_GL)) {
			crtVO.setCODE1(crtCO.getVALUE1() == null ? null : new BigDecimal(
					crtCO.getVALUE1()));
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_COLLATERALIZED_TYPE)) {
			crtVO.setCOLL_PER_TYPE(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEBIT_CREDIT)) {
			crtVO.setDEBIT_CREDIT_IND(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_TOTAL_DC)) {
			crtVO.setTOTAL_AMT_SIGN(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if ((crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_TENURE)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_PERIOD)) && (null != crtCO.getVALUE1()))
		{
		    crtVO.setDEAL_TENURE(new BigDecimal(crtCO.getVALUE1()));

		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_SOURCE_USAGES)) {
			crtVO.setSOURCE_USAGES(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CBK_CIF_TYPE)) {
			crtVO.setCBK_CIF_TYPE(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_SEC_CATEG)) {
			crtVO.setSECURITY_LISTED(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}

		if ((crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CATEG_REGION)) && (null != crtCO.getCODE1()))
		{
			crtVO.setCATEGORY(new BigDecimal(crtCO.getCODE1()));

		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_RESIDENT)) {
			crtVO.setRESIDENT(crtCO.getCODE1() == null ? null : crtCO
					.getCODE1());
		}
	
		if(RepConstantsCommon.CRITERIA_GENDER.equals(crtCO.getCRITERIA_TYPE_CODE()))
		{
		    crtVO.setGENDER(crtCO.getColumntmpltParamLinkVO().getGENDER());
		}
	
	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MBK)
		|| RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY.equals(crtCO.getCRITERIA_TYPE_CODE()))
	{
	    crtVO.setDATE_TYPE(crtCO.getColumntmpltParamLinkVO().getDATE_TYPE() == null ? null : crtCO
		    .getColumntmpltParamLinkVO().getDATE_TYPE());
	}
		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_GL_TERM)) {
			crtVO
					.setGL_TERM(crtCO.getCODE1() == null ? null : crtCO
							.getCODE1());
		}

		// VALUE2
		if(crtCO.getColumntmpltParamLinkVO().getOPERATOR() != null
				&& ((!crtCO.getColumntmpltParamLinkVO().getOPERATOR().equals(">")
					&& !crtCO.getColumntmpltParamLinkVO().getOPERATOR().equals("<")
					&& !crtCO.getColumntmpltParamLinkVO().getOPERATOR().equals(">=")
					&& !crtCO.getColumntmpltParamLinkVO().getOPERATOR().equals("<=") && (crtCO
					.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT) || RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY
					.equals(crtCO.getCRITERIA_TYPE_CODE()))) ||
					(crtCO.getColumntmpltParamLinkVO().getOPERATOR().equals(">")
						|| crtCO.getColumntmpltParamLinkVO().getOPERATOR().equals("<")
						|| crtCO.getColumntmpltParamLinkVO().getOPERATOR().equals(">=")
						|| crtCO.getColumntmpltParamLinkVO().getOPERATOR().equals("<=") && (crtCO
						.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_AMOUNT)))||
						(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MBK))
				 ))
	{
	    if(null != crtCO.getVALUE2())
	    {
		crtVO.setAMOUNT2(new BigDecimal(crtCO.getVALUE2()));
	    }
	    if(null != crtCO.getVALUE1())
	    {
		crtVO.setAMOUNT(new BigDecimal(crtCO.getVALUE1()));
	    }
	}
	
	if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MBK))
	{
	    crtVO.setAMOUNT(crtCO.getVALUE1() == null ? null : new BigDecimal(crtCO.getVALUE1()));
	}
	
	if(RepConstantsCommon.CRITERIA_ORIGINAL_MATURITY.equals(crtCO.getCRITERIA_TYPE_CODE()))
	{
	    crtVO.setOPERATOR1(crtCO.getCODE1());
	}

		if(crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_VAL_SEC))
		{
		    crtVO.setSECURITY_CODE1(crtCO.getCODE1() == null ? null : new BigDecimal(crtCO.getCODE1()));
		    crtVO.setSECURITY_CODE2(crtCO.getColumntmpltParamLinkVO().getCODE2() == null ? null : crtCO
			    .getColumntmpltParamLinkVO().getCODE2());
		}

		if (crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_ECONOMIC_SEC)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_CONTINENT)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_MAIN_ECO_SEC)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_GOODS)
				|| crtCO.getCRITERIA_TYPE_CODE().equals(RepConstantsCommon.CRITERIA_DEAL_ECONOMIC_SEC)) {
			crtVO
					.setCODE2(crtCO.getColumntmpltParamLinkVO().getCODE2() == null ? null
							: crtCO.getColumntmpltParamLinkVO().getCODE2());
		}

		return crtVO;
	}

	public COLMNTMPLTVO convertColTemplCOToVO(COLMNTMPLTCO colCO) {
		COLMNTMPLTVO colVO = new COLMNTMPLTVO();
		colVO.setBRIEF_NAME_ENG(colCO.getColmnTmpltVO().getBRIEF_NAME_ENG());
		colVO.setBRIEF_NAME_ARAB(colCO.getColmnTmpltVO().getBRIEF_NAME_ARAB());
		colVO.setPRINT_PAPER_ORIENTATION(colCO.getColmnTmpltVO()
				.getPRINT_PAPER_ORIENTATION());
		colVO
				.setPRINT_PAPER_SIZE(colCO.getColmnTmpltVO()
						.getPRINT_PAPER_SIZE());
		colVO.setCOMP_CODE(colCO.getColmnTmpltVO().getCOMP_CODE());
		colVO.setCODE(colCO.getColmnTmpltVO().getCODE());
		return colVO;
	}

	public COLMNTMPLTVO convertColDetCOToVO(COLMNTMPLTCO colDetCO) {
		COLMNTMPLTVO colDetVO = new COLMNTMPLTVO();
		colDetVO.setLINE_NBR(colDetCO.getColmnTmpltVO().getLINE_NBR());
		colDetVO.setCOMP_CODE(colDetCO.getColmnTmpltVO().getCOMP_CODE());
		colDetVO.setCODE(colDetCO.getColmnTmpltVO().getCODE());

		colDetVO.setCOL_TYPE(colDetCO.getColmnTmpltVO().getCOL_TYPE());
		colDetVO.setFROM_DATE(colDetCO.getColmnTmpltVO().getFROM_DATE());
		colDetVO.setTO_DATE(colDetCO.getColmnTmpltVO().getTO_DATE());
		colDetVO.setCREATED_BY(colDetCO.getColmnTmpltVO().getCREATED_BY());
		colDetVO.setMODIFIED_BY(colDetCO.getColmnTmpltVO().getMODIFIED_BY());
		colDetVO.setDATE_CREATED(colDetCO.getColmnTmpltVO().getDATE_CREATED());
		colDetVO
				.setDATE_MODIFIED(colDetCO.getColmnTmpltVO().getDATE_MODIFIED());
		colDetVO.setCOMP(colDetCO.getColmnTmpltVO().getCOMP());
		colDetVO.setFROM_BRANCH(colDetCO.getColmnTmpltVO().getFROM_BRANCH());
		colDetVO.setTO_BRANCH(colDetCO.getColmnTmpltVO().getTO_BRANCH());
		colDetVO.setFROM_CY(colDetCO.getColmnTmpltVO().getFROM_CY());
		colDetVO.setTO_CY(colDetCO.getColmnTmpltVO().getTO_CY());
		colDetVO.setFROM_DEPT(colDetCO.getColmnTmpltVO().getFROM_DEPT());
		colDetVO.setTO_DEPT(colDetCO.getColmnTmpltVO().getTO_DEPT());
		colDetVO.setFROM_DIV(colDetCO.getColmnTmpltVO().getFROM_DIV());
		colDetVO.setTO_DIV(colDetCO.getColmnTmpltVO().getTO_DIV());
		colDetVO.setFROM_UNIT(colDetCO.getColmnTmpltVO().getFROM_UNIT());
		colDetVO.setTO_UNIT(colDetCO.getColmnTmpltVO().getTO_UNIT());
		colDetVO.setBOLD(colDetCO.getColmnTmpltVO().getBOLD());
		colDetVO.setDISP_COL_TOT_ZERO(colDetCO.getColmnTmpltVO()
				.getDISP_COL_TOT_ZERO());
		// colDetVO.setEXPRESSION(colDetCO.getEXPRESSION());
		// colDetVO.setEXPRESSION_LINES(colDetCO.getEXPRESSION_LINES());
		colDetVO.setALL_CRITERIA(colDetCO.getColmnTmpltVO().getALL_CRITERIA());
		colDetVO.setBRIEF_NAME_ENG(colDetCO.getColmnTmpltVO()
				.getBRIEF_NAME_ENG());
		colDetVO.setBRIEF_NAME_ARAB(colDetCO.getColmnTmpltVO()
				.getBRIEF_NAME_ARAB());
		return colDetVO;
	}

	@Override
	public void saveExpr(FTR_TMPLT_EXPRVO vo) throws BaseException {
		vo.setTMPLT_TYPE("C");
		if(vo.getEXP_TYPE().equals(RepConstantsCommon.COL_EXP_LINE_TYPE))
        	{
        	    vo.setEXP_LINE(vo.getEXP_VALUE());
        	    vo.setEXP_VALUE(null);
        	}
        	else
        	{
        	    vo.setEXP_LINE(null);
        	}
		columnTemplateDAO.saveExpr(vo);
	}

	@Override
	public List<COLMNTMPLT_PARAM_LINKCO> getCrtList(COLMNTMPLTSC colTmplSC)
			throws BaseException {
		return columnTemplateDAO.getCrtList(colTmplSC);
	}

	public List<CommonDetailsVO> getFilterCrtDetList(COLMNTMPLTSC colTmplSC)
			throws BaseException {
		return columnTemplateDAO.getFilterCrtDetList(colTmplSC);
	}

	public List<CommonDetailsVO> getFilterCrtDetLList(COLMNTMPLTSC colTmplSC)
			throws BaseException {
		List<CommonDetailsVO> retLst;
		String tblName = colTmplSC.getTABLE_NAME();
		BigDecimal detCode = colTmplSC.getCODE();
		if (tblName != null
				&& tblName.toUpperCase(Locale.ENGLISH).equals("V_IRP_SUB_ECO_SECTORS")
				&& detCode != null && detCode.intValue() == 0) {
			retLst = new ArrayList<CommonDetailsVO>();
			CommonDetailsVO detVO = new CommonDetailsVO();
			detVO.setBRIEF_DESC_ENG("All");
			detVO.setCODE_STR("0");
			retLst.add(detVO);
			return retLst;
		} else {
			List<CommonDetailsVO> retLst1 = columnTemplateDAO
					.getFilterCrtDetLList(colTmplSC);
			retLst = new ArrayList<CommonDetailsVO>();
			if (tblName != null
					&& tblName.toUpperCase(Locale.ENGLISH).equals("V_IRP_SUB_ECO_SECTORS")
					&& detCode == null) {
				CommonDetailsVO detVO = new CommonDetailsVO();
				detVO.setBRIEF_DESC_ENG("All");
				detVO.setCODE_STR("0");
				retLst.add(detVO);
			}
			retLst.addAll(retLst1);
			return retLst;
		}
	}

	public void checkRequiredFields(Object myCO, String pageRef,
			BigDecimal compCode, String appName, String lang)
			throws BaseException {
		CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
		checkRequiredCO.setOpt(pageRef);
		checkRequiredCO.setCompCode(compCode);
		checkRequiredCO.setLanguage(lang);
		checkRequiredCO.setApp(appName);

		if (myCO instanceof COLMNTMPLTCO) {
			COLMNTMPLTCO coltmpltCO = (COLMNTMPLTCO) myCO;
			checkRequiredCO.setObj_value(coltmpltCO);
			commonLibBO.checkRequired(coltmpltCO.getBusinessHm(),
					checkRequiredCO);
		} else if (myCO instanceof FTR_TMPLT_EXPRCO) {
			FTR_TMPLT_EXPRCO exprCO = (FTR_TMPLT_EXPRCO) myCO;
			checkRequiredCO.setObj_value(exprCO.getFtrTmpltExprVO());
			commonLibBO.checkRequired(checkRequiredCO);
		} else if (myCO instanceof COLMNTMPLT_PARAM_LINKCO) {
			COLMNTMPLT_PARAM_LINKCO crtCO = (COLMNTMPLT_PARAM_LINKCO) myCO;
			checkRequiredCO.setObj_value(crtCO);
			commonLibBO.checkRequired(crtCO.getBusinessHm(), checkRequiredCO);
		}
	}

	public void saveCreateLike(COLMNTMPLTSC sc) throws BaseException {
		columnTemplateDAO.saveCreateLike(sc);
	}

	@Override
	public void deleteExpressionsByColumnTemplate(FTR_TMPLT_EXPRVO key)
			throws BaseException {
		columnTemplateDAO.deleteExpressionsByColumnTemplate(key);
	}

	@Override
	public void deleteExpressionsByColTempLine(FTR_TMPLT_EXPRVO key)
			throws BaseException {
		columnTemplateDAO.deleteExpressionsByColTempLine(key);
	}

	@Override
	public void deleteExpression(FTR_TMPLT_EXPRVO vo) throws BaseException {
		columnTemplateDAO.deleteExpression(vo);
	}

	@Override
	public CommonDetailsVO getMaxCrtSubLineNb(CommonDetailsSC crtSC)
			throws BaseException {
		return columnTemplateDAO.getMaxCrtSubLineNb(crtSC);
	}
	
    /**
     * Method that checks for existence of ABD calc type in column template
     */
    public int checkColTempCalcType(BigDecimal colCode, BigDecimal compCode) throws BaseException
    {
	return columnTemplateDAO.checkColTempCalcType(colCode, compCode);
    }
}