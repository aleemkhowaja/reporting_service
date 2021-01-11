package com.path.dao.reporting.ftr.fcr.impl;

import java.util.List;
import java.util.Map;

import com.path.bo.common.ConstantsCommon;
import com.path.dao.reporting.ftr.fcr.FcrDAO;
import com.path.dbmaps.vo.AXSVOKey;
import com.path.dbmaps.vo.FTR_OPTVO;
import com.path.dbmaps.vo.IRP_FCR_FIXED_COLSVO;
import com.path.dbmaps.vo.IRP_FCR_REPORTSVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FCR_REPORTSCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.AXSCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;

public class FcrDAOImpl extends BaseDAO implements FcrDAO
{
    @SuppressWarnings("unchecked")
    public List<FTR_OPTCO> loadFcrList(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "fcrRep.loadFcrListMap");
	if(commonDetailsSC.getSortOrder() == null)
	{
	    commonDetailsSC.setSortOrder(" ORDER BY CODE ");
	}
	return getSqlMap().queryForList("fcrRep.loadFcrList", commonDetailsSC,
		commonDetailsSC.getRecToskip(), commonDetailsSC.getNbRec());

    }

    public int retFcrListCount(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "fcrRep.loadFcrListMap");
	return ((Integer) getSqlMap().queryForObject("fcrRep.retFcrListCount", commonDetailsSC)).intValue();
    }

    @SuppressWarnings("unchecked")
    public List<CommonDetailsVO> retParentRefList(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	if(commonDetailsSC.isGrid())
	{
	    DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "fcrRep.pRefLkpMap");
	    return getSqlMap().queryForList("fcrRep.retParentRefList", commonDetailsSC,
		    commonDetailsSC.getRecToskip(), commonDetailsSC.getNbRec());
	}
	else
	{
	    return getSqlMap().queryForList("fcrRep.retParentRefList", commonDetailsSC);
	}
    }

    public int retParentRefListCount(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "fcrRep.pRefLkpMap");
	return ((Integer) getSqlMap().queryForObject("fcrRep.retParentRefListCount", commonDetailsSC)).intValue();
    }

    public int checkProgRef(CommonDetailsSC commonDetailsSC) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("fcrRep.checkProgRef", commonDetailsSC)).intValue();

    }

    public void saveOpt1(FTR_OPTCO fcrCO) throws DAOException
    {
	if(fcrCO.getFtrOptVO().getBRIEF_NAME_ARAB() == null)
	{
	    fcrCO.getFtrOptVO().setBRIEF_NAME_ARAB("");
	}
	getSqlMap().insert("fcrRep.saveOpt1", fcrCO);
    }

    public void saveOpt2(FTR_OPTCO fcrCO) throws DAOException
    {
	getSqlMap().insert("fcrRep.saveOpt2", fcrCO);
    }

    public void saveOpt3(FTR_OPTCO fcrCO) throws DAOException
    {
	getSqlMap().insert("fcrRep.saveOpt3", fcrCO);
    }

    public int retProgOrder(FTR_OPTCO fcrCO) throws DAOException
    {
	return ((Integer) getSqlMap().queryForObject("fcrRep.retProgOrder", fcrCO)).intValue();
    }

    public void updateOpt1(FTR_OPTCO fcrCO) throws DAOException
    {
	if(fcrCO.getFtrOptVO().getBRIEF_NAME_ARAB() == null)
	{
	    fcrCO.getFtrOptVO().setBRIEF_NAME_ARAB("");
	}
	getSqlMap().update("fcrRep.updateOpt1", fcrCO);
    }

    public void updateOpt2(FTR_OPTCO fcrCO) throws DAOException
    {
	getSqlMap().update("fcrRep.updateOpt2", fcrCO);
    }

    public void updateOpt3(FTR_OPTCO fcrCO) throws DAOException
    {
	getSqlMap().update("fcrRep.updateOpt3", fcrCO);
    }

    public void deleteOpt(FTR_OPTCO fcrCO) throws DAOException
    {
	getSqlMap().delete("fcrRep.deleteOpt", fcrCO);
    }

    public void deleteOpts(FTR_OPTCO fcrCO) throws DAOException
    {
	getSqlMap().delete("fcrRep.deleteOpts", fcrCO);
    }

    public void deleteAxs(AXSCO axsCO) throws DAOException
    {
	getSqlMap().delete("fcrRep.deleteAxs", axsCO);
    }

    public FTR_OPTCO returnDynamicReportByRef(FTR_OPTCO optCO) throws DAOException
    {
	return (FTR_OPTCO) getSqlMap().queryForObject("fcrRep.returnDynamicReportByRef", optCO);
    }

    @Override
    public void deleteOptExtended(FTR_OPTCO fcrCO) throws DAOException
    {
	getSqlMap().delete("fcrRep.deleteOptExtended", fcrCO);
    }

    public FTR_OPTCO retrieveFcr(CommonDetailsSC sc) throws DAOException
    {

	return (FTR_OPTCO) getSqlMap().queryForObject("fcrRep.retrieveFcr", sc);
    }

    public Integer updateFcr(FTR_OPTVO optVO) throws DAOException
    {
	return getSqlMap().update("fcrRep.updateFcr", optVO);
    }

    @SuppressWarnings("unchecked")
    public List<FTR_OPTCO> retSavedOptsByProgRef(FTR_OPTCO optCO) throws DAOException
    {
	return getSqlMap().queryForList("fcrRep.retSavedOptsByProgRef", optCO);
    }
    
    public IRP_FCR_REPORTSCO retFcrRep(IRP_AD_HOC_REPORTSC repSC) throws DAOException {
		
	return (IRP_FCR_REPORTSCO) getSqlMap().queryForObject("fcrRep.retFcrRep", repSC);
    }

    public String returnFcrProgRef(Map parameters) throws DAOException
    {
	parameters.put("isSybase", ConstantsCommon.CURR_DBMS_SYBASE);
	String result = (String) getSqlMap().queryForObject("fcrRep.returnFcrProgRef", parameters);
	parameters.remove("isSybase");
	return result;
    }
    
    public void deleteAXSByCompBranchApp(AXSVOKey axsVO) throws DAOException
    {
	getSqlMap().delete("fcrRep.deleteAXSByCompBranchApp", axsVO);
    }
    
    public List<AXSCO> retAxsByReport(AXSCO axsCO) throws DAOException
    {
	return getSqlMap().queryForList("fcrRep.retAxsByReport", axsCO);
    }
    
    /**
     * MEthod that returns the english desc of an fcr report
     */
    public String retFcrReportEngDesc(String progRef) throws DAOException
    {
	return (String) getSqlMap().queryForObject("fcrRep.retFcrReportEngDesc", progRef);
    }
    @Override
    public List<IRP_FCR_FIXED_COLSVO> retFixedFcrColsByRef(String progRef) throws DAOException
    {
	return getSqlMap().queryForList("fcrRep.retFixedFcrColsByRef", progRef);
    }

    @Override
    public List<COLMNTMPLTCO> retDynamicFcrColsFromColTempl(COLMNTMPLTSC colTmplSC) throws DAOException
    {
	return getSqlMap().queryForList("fcrRep.retDynamicFcrColsFromColTempl", colTmplSC);
    }

    @Override
    public List<COLMNTMPLTCO> retDynamicFcrColsFromTempl(GLSTMPLTSC tmplSC) throws DAOException
    {
	return getSqlMap().queryForList("fcrRep.retDynamicFcrColsFromTempl", tmplSC);
    }

    @Override
    public List<IRP_FCR_REPORTSVO> returnFcrDetByFtrOptRef(IRP_FCR_REPORTSVO vo) throws DAOException
    {
	return  getSqlMap().queryForList("fcrRep.returnFcrDetByFtrOptRef", vo);
    }

	@Override
	public List<CommonDetailsVO> retCategoriesLkpList(CommonDetailsSC commonDetailsSC) throws DAOException 
	{
		if(commonDetailsSC.isGrid())
		{
			DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "fcrRep.retCategoriesLkpListMap");
			if(commonDetailsSC.getSortOrder() == null)
			{
			    commonDetailsSC.setSortOrder(" ORDER BY CATEG_ID ");
			}
			return getSqlMap().queryForList("fcrRep.retCategoriesLkpList", commonDetailsSC,	commonDetailsSC.getRecToskip(), commonDetailsSC.getNbRec());
		}
		else
		{
			return getSqlMap().queryForList("fcrRep.retCategoriesLkpList", commonDetailsSC);
		}

	}

	@Override
	public int retCategoriesLkpCount(CommonDetailsSC commonDetailsSC)throws DAOException 
	{
		DAOHelper.fixGridMaps(commonDetailsSC, getSqlMap(), "fcrRep.retCategoriesLkpListMap");
		return ((Integer) getSqlMap().queryForObject("fcrRep.retCategoriesLkpCount", commonDetailsSC)).intValue();
	}

	@Override
	public void deleteSRoleDetail(FTR_OPTCO fcrCO) throws DAOException 
	{
		getSqlMap().delete("fcrRep.deleteSRoleDetail", fcrCO);
	}    

    
}
