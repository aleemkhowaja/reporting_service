package com.path.bo.reporting.ftr.columntemplate;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.COLMNTMPLTVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLT_PARAM_LINKCO;
import com.path.vo.reporting.ftr.columnTemplate.FTR_TMPLT_EXPRCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;

public interface ColumnTemplateBO {

	public List<COLMNTMPLTCO> findAllColumnTemplates(COLMNTMPLTSC sc)
			throws BaseException;

	public Integer findAllColumnTemplatesCount(COLMNTMPLTSC sc)
			throws BaseException;

	public COLMNTMPLTVO retrieveColumnTemplate(COLMNTMPLTVO colVO)
			throws BaseException;

	public List<COLMNTMPLTCO> findColumnTemplateDetails(COLMNTMPLTSC sc)
			throws BaseException;

	public List<FTR_TMPLT_EXPRCO> findExprList(COLMNTMPLTVO sc)
			throws BaseException;

	public void deleteColumnTemplate(COLMNTMPLTVO key) throws BaseException;

	public COLMNTMPLTVO findSingleColumnTemplate(COLMNTMPLTVO key)
			throws BaseException;

	public List<CommonDetailsVO> findDepartmentsBetweenDiv(
			CommonDetailsSC commonDetailsSC) throws BaseException;

	public List<CommonDetailsVO> findDepartments(CommonDetailsSC commonDetailsSC)
			throws BaseException;

	public List<CommonDetailsVO> findUnits(CommonDetailsSC commonDetailsSC)
			throws BaseException;

	public int findUnitsListCount(CommonDetailsSC sc) throws BaseException;

	public CommonDetailsVO findSingleUnit(CommonDetailsSC commonDetailsVO)
			throws BaseException;

	public void insert(COLMNTMPLTVO vo) throws BaseException;

	public void update(COLMNTMPLTVO vo) throws BaseException;

	public COLMNTMPLTCO findSingleColumnTemplateDet(COLMNTMPLTSC sc)
			throws BaseException;

	public Integer findColumnTemplateDetailsCount(COLMNTMPLTSC sc)
			throws BaseException;

	public void saveColumnTemplate(String detGridMode, String linesGridMode,
			COLMNTMPLTCO currentTemplate, String userName,
			COLMNTMPLTVO oldColTmpl,String lang) throws BaseException;

	public void delete(COLMNTMPLTVO key) throws BaseException;

	public void deleteLines(COLMNTMPLTVO key) throws BaseException;

	public void saveExpr(FTR_TMPLT_EXPRVO vo) throws BaseException;

	public List<COLMNTMPLT_PARAM_LINKCO> getCrtList(COLMNTMPLTSC colTmplSC)
			throws BaseException;

	public List<CommonDetailsVO> getFilterCrtDetList(COLMNTMPLTSC colTmplSC)
			throws BaseException;

	public List<CommonDetailsVO> getFilterCrtDetLList(COLMNTMPLTSC colTmplSC)
			throws BaseException;

	public void checkRequiredFields(Object obj, String pageRef,
			BigDecimal compCode, String appName, String lang)
			throws BaseException;

	public void saveCreateLike(COLMNTMPLTSC sc) throws BaseException;

	public void deleteExpressionsByColumnTemplate(FTR_TMPLT_EXPRVO key)
			throws BaseException;

	public void deleteExpressionsByColTempLine(FTR_TMPLT_EXPRVO key)
			throws BaseException;

	public void deleteExpression(FTR_TMPLT_EXPRVO vo) throws BaseException;

	public CommonDetailsVO getMaxCrtSubLineNb(CommonDetailsSC crtSC)
			throws BaseException;

	Integer countOfDepartmentsFunc(CommonDetailsSC commonDetailsSC)
			throws BaseException;

	Integer findSingleColumnTemplatesCount(COLMNTMPLTSC sc)
			throws BaseException;
	public int checkColTempCalcType(BigDecimal colCode,BigDecimal compCode)throws BaseException;
}
