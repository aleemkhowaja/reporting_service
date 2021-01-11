package com.path.dao.reporting.ftr.columntemplate;

import java.math.BigDecimal;
import java.util.List;

import com.path.dbmaps.vo.COLMNTMPLTVO;
import com.path.dbmaps.vo.COLMNTMPLT_PARAM_LINKVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLT_PARAM_LINKCO;
import com.path.vo.reporting.ftr.columnTemplate.FTR_TMPLT_EXPRCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;

public interface ColumnTemplateDAO {

	public List<COLMNTMPLTCO> findAllColumnTemplates(COLMNTMPLTSC sc)
			throws DAOException;

	public Integer findAllColumnTemplatesCount(COLMNTMPLTSC sc)
			throws DAOException;

	public List<COLMNTMPLTCO> findColumnTemplateDetails(COLMNTMPLTSC sc)
			throws DAOException;

	public List<FTR_TMPLT_EXPRCO> findExprList(COLMNTMPLTVO sc)
			throws DAOException;

	public void delete(COLMNTMPLTVO key) throws DAOException;

	public void deleteLines(COLMNTMPLTVO key) throws DAOException;

	public COLMNTMPLTVO findSingleColumnTemplate(COLMNTMPLTVO key)
			throws DAOException;

	public List<CommonDetailsVO> findDepartmentsBetweenDiv(
			CommonDetailsSC commonDetailsVO) throws DAOException;

	public List<CommonDetailsVO> findDepartments(CommonDetailsSC commonDetailsVO)
			throws DAOException;

	public List<CommonDetailsVO> findUnits(CommonDetailsSC commonDetailsVO)
			throws DAOException;

	public int findUnitsListCount(CommonDetailsSC sc) throws DAOException;

	public CommonDetailsVO findSingleUnit(CommonDetailsSC commonDetailsVO)
			throws DAOException;

	public void insert(COLMNTMPLTVO vo) throws DAOException;

	public Integer update(COLMNTMPLTVO vo) throws DAOException;

	public COLMNTMPLTCO findSingleColumnTemplateDet(COLMNTMPLTSC sc)
			throws DAOException;

	public Integer findColumnTemplateDetailsCount(COLMNTMPLTSC sc)
			throws DAOException;

	public List<COLMNTMPLT_PARAM_LINKCO> getCrtList(COLMNTMPLTSC colTmplSC)
			throws DAOException;

	public List<CommonDetailsVO> getFilterCrtDetList(COLMNTMPLTSC colTmplSC)
			throws DAOException;

	public List<CommonDetailsVO> getFilterCrtDetLList(COLMNTMPLTSC colTmplSC)
			throws DAOException;

	public void saveCreateLike(COLMNTMPLTSC sc) throws DAOException;

        /**
         * delete criteria by company , template and line number
         * 
         * @param COLMNTMPLT_PARAM_LINKVO key
         * @return
         * @throws DAOException
         * 
         */
	public void deleteCrtsByColumnTemplate(COLMNTMPLT_PARAM_LINKVO key)
			throws DAOException;

	public void deleteExpressionsByColumnTemplate(FTR_TMPLT_EXPRVO key)
			throws DAOException;

	public void deleteExpressionsByColTempLine(FTR_TMPLT_EXPRVO key)
			throws DAOException;

	public void deleteExpression(FTR_TMPLT_EXPRVO vo) throws DAOException;

	public CommonDetailsVO getMaxCrtSubLineNb(CommonDetailsSC crtSC)
			throws DAOException;

	Integer countOfDepartmentsFunc(CommonDetailsSC sc) throws DAOException;

	Integer findSingleColumnTemplatesCount(COLMNTMPLTSC sc) throws DAOException;

	void saveExpr(FTR_TMPLT_EXPRVO vo) throws DAOException;
	public int checkColTempCalcType(BigDecimal colCode,BigDecimal compCode)throws DAOException;
}