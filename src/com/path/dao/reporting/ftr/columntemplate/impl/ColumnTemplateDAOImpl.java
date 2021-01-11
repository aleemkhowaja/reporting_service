package com.path.dao.reporting.ftr.columntemplate.impl;

import java.math.BigDecimal;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.ftr.columntemplate.ColumnTemplateDAO;
import com.path.dbmaps.vo.COLMNTMPLTVO;
import com.path.dbmaps.vo.COLMNTMPLT_PARAM_LINKVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLT_PARAM_LINKCO;
import com.path.vo.reporting.ftr.columnTemplate.FTR_TMPLT_EXPRCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;

public class ColumnTemplateDAOImpl extends BaseDAO implements ColumnTemplateDAO {
	@Override
	public List<COLMNTMPLTCO> findAllColumnTemplates(COLMNTMPLTSC sc)
			throws DAOException {
		if (sc.getSortOrder() == null) {
			sc.setSortOrder(" ORDER BY CODE ");
		}

		DAOHelper.fixGridMaps(sc, getSqlMap(),
				"colmnTmpltMapper.ColumnSearchResultMap");

		return getSqlMap().queryForList(
				"colmnTmpltMapper.columnTemplateList", sc, sc.getRecToskip(),
				sc.getNbRec());
	}

	@Override
	public Integer findAllColumnTemplatesCount(COLMNTMPLTSC sc)
			throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(),
				"colmnTmpltMapper.ColumnSearchResultMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject(
				"colmnTmpltMapper.columnTemplateListCount", sc));
		return nb;
	}

	@Override
	public List<COLMNTMPLTCO> findColumnTemplateDetails(COLMNTMPLTSC sc)
			throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(),
				"colmnTmpltMapper.DetResultMapCO");
		return getSqlMap().queryForList(
				"colmnTmpltMapper.select_COLUMNS_DET_template", sc);
	}

	@Override
	public List<FTR_TMPLT_EXPRCO> findExprList(COLMNTMPLTVO sc)
			throws DAOException {
		return getSqlMap().queryForList(
				"templateExpression.select_expr_list", sc);
	}

	public void delete(COLMNTMPLTVO key) throws DAOException {
		getSqlMap().delete("colmnTmpltMapper.deleteColumnTemplate", key);
	}

	// Select COLMNTMPLT
	public COLMNTMPLTVO findSingleColumnTemplate(COLMNTMPLTVO key)
			throws DAOException {
		COLMNTMPLTVO vo = new COLMNTMPLTVO();
		vo.setCOMP_CODE(key.getCOMP_CODE());
		vo.setCODE(vo.getCODE());
		return (COLMNTMPLTVO) getSqlMap().queryForObject(
				"colmnTmpltMapper.selectSingleColumnTemplate", key);
	}

        public List<CommonDetailsVO> findDepartmentsBetweenDiv(CommonDetailsSC commonDetailsVO) throws DAOException
        {
        	DAOHelper.fixGridMaps(commonDetailsVO, getSqlMap(), "colmnTmpltMapper.getGLkupMap");
        	if(commonDetailsVO.getSortOrder() == null)
        	{
        	    commonDetailsVO.setSortOrder(" ORDER BY CODE ");
        	}
        	if(commonDetailsVO.isGrid())
        	{
        	    return getSqlMap().queryForList("colmnTmpltMapper.getDepartmentsBetweenDiv", commonDetailsVO,
        		    commonDetailsVO.getRecToskip(), commonDetailsVO.getNbRec());
        	}
        	else
        	{
        	    return getSqlMap().queryForList("colmnTmpltMapper.getDepartmentsBetweenDiv", commonDetailsVO);
        	}
        }

	public List<CommonDetailsVO> findDepartments(CommonDetailsSC commonDetailsVO)
			throws DAOException {
		// getGLkupMap
		DAOHelper.fixGridMaps(commonDetailsVO, getSqlMap(),
				"colmnTmpltMapper.getGLkupMap");
		return getSqlMap().queryForList(
				"colmnTmpltMapper.getDepartments", commonDetailsVO,
				commonDetailsVO.getRecToskip(), commonDetailsVO.getNbRec());
	}

	public List<CommonDetailsVO> findUnits(CommonDetailsSC commonDetailsVO)
			throws DAOException {
		DAOHelper.fixGridMaps(commonDetailsVO, getSqlMap(),
				"colmnTmpltMapper.getGLkupMap");
		return getSqlMap().queryForList(
				"colmnTmpltMapper.getUnits", commonDetailsVO);
	}

	@Override
	public int findUnitsListCount(CommonDetailsSC sc) throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "colmnTmpltMapper.getGLkupMap");
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject(
				"colmnTmpltMapper.findUnitsCount", sc)).intValue();
		return nb;
	}

	@Override
	public CommonDetailsVO findSingleUnit(CommonDetailsSC commonDetailsVO)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"colmnTmpltMapper.findSingleUnit", commonDetailsVO);
	}

	/*
	 * public List<CommonDetailsVO> findCurrencies(CommonDetailsSC
	 * commonDetailsVO)throws DAOException {
	 * DAOHelper.fixGridMaps(commonDetailsVO, getSqlMap(),
	 * "COLMNTMPLT.getGLkupMap"); return (ArrayList<CommonDetailsVO>)
	 * getSqlMap().queryForList("COLMNTMPLT.get_currencies",
	 * commonDetailsVO,commonDetailsVO
	 * .getRecToskip(),commonDetailsVO.getNbRec()); }
	 * 
	 * @Override public int findCurrenciesCount(CommonDetailsSC
	 * commonDetailsVO)throws DAOException {
	 * DAOHelper.fixGridMaps(commonDetailsVO, getSqlMap(),
	 * "COLMNTMPLT.getGLkupMap"); return
	 * ((Integer)getSqlMap().queryForObject("COLMNTMPLT.get_currencies_count",
	 * commonDetailsVO)).intValue(); }
	 */

	public void insert(COLMNTMPLTVO vo) throws DAOException {
		getSqlMap().insert("COLMNTMPLT.insertCOLMNTMPLT", vo);
	}

	public Integer update(COLMNTMPLTVO vo) throws DAOException {
	    return getSqlMap().update("COLMNTMPLT.updateCOLMNTMPLT", vo);
	}

	@Override
	public COLMNTMPLTCO findSingleColumnTemplateDet(COLMNTMPLTSC sc)
			throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "colmnTmpltMapper.DetResultMap");
		return (COLMNTMPLTCO) getSqlMap().queryForObject(
				"colmnTmpltMapper.find_single_COLUMN_DET", sc);
	}

	@Override
	public Integer findColumnTemplateDetailsCount(COLMNTMPLTSC sc)
			throws DAOException {
		return ((Integer) getSqlMap().queryForObject(
				"colmnTmpltMapper.get_columnDetailsCount", sc));
	}

	@Override
	public Integer findSingleColumnTemplatesCount(COLMNTMPLTSC sc)
			throws DAOException {
		int nb = 0;
		nb = ((Integer) getSqlMap().queryForObject(
				"colmnTmpltMapper.findSingleColumnTemplateCount", sc));
		return nb;
	}

	@Override
	public Integer countOfDepartmentsFunc(CommonDetailsSC sc)
			throws DAOException {
		DAOHelper.fixGridMaps(sc, getSqlMap(), "colmnTmpltMapper.getGLkupMap");
		return ((Integer) getSqlMap().queryForObject(
				"colmnTmpltMapper.findDepartmentsCount", sc));
	}

	@Override
	public void deleteLines(COLMNTMPLTVO key) throws DAOException {
		getSqlMap().delete("colmnTmpltMapper.delete_lines", key);
	}

	@Override
	public void saveExpr(FTR_TMPLT_EXPRVO vo) throws DAOException {
		getSqlMap().insert("templateExpression.saveExpr", vo);
	}

	@Override
	public List<COLMNTMPLT_PARAM_LINKCO> getCrtList(COLMNTMPLTSC colTmplSC)
			throws DAOException {
	    	colTmplSC.setFROM_COL(ConstantsCommon.TRUE);
		return getSqlMap().queryForList(
				"columnTemplateCrt.getCrtList", colTmplSC);
	}

	public List<CommonDetailsVO> getFilterCrtDetList(COLMNTMPLTSC colTmplSC)
			throws DAOException {
		if (colTmplSC.isGrid()) {
			DAOHelper.fixGridMaps(colTmplSC, getSqlMap(),
					"columnTemplateCrt.getFilterCrtDetMap");
			return getSqlMap().queryForList(
					"columnTemplateCrt.getFilterCrtDetList", colTmplSC,
					colTmplSC.getRecToskip(), colTmplSC.getNbRec());
		} else {
			return getSqlMap().queryForList(
					"columnTemplateCrt.getFilterCrtDetList", colTmplSC);
		}
	}

	public List<CommonDetailsVO> getFilterCrtDetLList(COLMNTMPLTSC colTmplSC)
			throws DAOException {
	if(RepConstantsCommon.DEAL_PROV_CATEG_VIEW.equals(colTmplSC.getTABLE_NAME())
		|| RepConstantsCommon.REGION_VIEW.equals(colTmplSC.getTABLE_NAME()))
	{
	    colTmplSC.setCOMP_CODE(null);
	}
		if (colTmplSC.isGrid()) {
			DAOHelper.fixGridMaps(colTmplSC, getSqlMap(),
					"columnTemplateCrt.getFilterCrtDetMap");
			if (colTmplSC.getTABLE_NAME().equals("V_IRP_SUB_ECO_SECTORS")) {
				return getSqlMap().queryForList(
						"columnTemplateCrt.getFilterCrtDetLListSubEco",
						colTmplSC, colTmplSC.getRecToskip(),
						colTmplSC.getNbRec());
			}
			return getSqlMap().queryForList(
					"columnTemplateCrt.getFilterCrtDetLList", colTmplSC,
					colTmplSC.getRecToskip(), colTmplSC.getNbRec());
		} else {
			if (colTmplSC.getTABLE_NAME().equals("V_IRP_SUB_ECO_SECTORS")) {
				return getSqlMap().queryForList(
						"columnTemplateCrt.getFilterCrtDetLListSubEco",
						colTmplSC);
			}
			return getSqlMap().queryForList(
					"columnTemplateCrt.getFilterCrtDetLList", colTmplSC);
		}
	}

	public void saveCreateLike(COLMNTMPLTSC sc) throws DAOException {
		getSqlMap().insert("colmnTmpltMapper.createLike", sc);
	}

	@Override
	public void deleteCrtsByColumnTemplate(COLMNTMPLT_PARAM_LINKVO key)
			throws DAOException {
		getSqlMap().delete("columnTemplateCrt.deleteCrtsByColumnTemplate", key);
	}

	@Override
	public void deleteExpressionsByColumnTemplate(FTR_TMPLT_EXPRVO key)
			throws DAOException {
		getSqlMap().delete(
				"templateExpression.deleteExpressionsByColumnTemplate", key);
	}

	@Override
	public void deleteExpressionsByColTempLine(FTR_TMPLT_EXPRVO key)
			throws DAOException {
		getSqlMap().delete("templateExpression.deleteExpressionsByColTempLine",
				key);
	}

	@Override
	public void deleteExpression(FTR_TMPLT_EXPRVO vo) throws DAOException {
		getSqlMap().delete("templateExpression.deleteExpression", vo);
	}

	@Override
	public CommonDetailsVO getMaxCrtSubLineNb(CommonDetailsSC crtSC)
			throws DAOException {
		return (CommonDetailsVO) getSqlMap().queryForObject(
				"columnTemplateCrt.getMaxCrtSubLineNb", crtSC);
	}
	
    /**
     * Method that checks for existence of ABD calc type in column template
     */
    public int checkColTempCalcType(BigDecimal colCode, BigDecimal compCode) throws DAOException
    {
	COLMNTMPLTSC sc = new COLMNTMPLTSC();
	sc.setCOMP_CODE(compCode);
	sc.setCODE(colCode);
	sc.setCOL_TYPE(RepConstantsCommon.COL_TYPE_ABD);
	return (Integer) getSqlMap().queryForObject("colmnTmpltMapper.checkColTempCalcType", sc);
    }
}
