package com.path.dao.reporting.ftr.reportsmismatch.impl;

import java.util.ArrayList;
import java.util.List;

import com.path.dao.reporting.ftr.reportsmismatch.ReportsMismatchProcessDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;

public class ReportsMismatchProcessDAOImpl extends BaseDAO implements ReportsMismatchProcessDAO
{

    public ArrayList<REP_MISMATCH_PROCESSCO> retCrtProgRefLkpLst(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException
    {
	DAOHelper.fixGridMaps(misProcSC, getSqlMap(), "repMisProcMapper.retCrtProgRefLkpMap");
	return (ArrayList<REP_MISMATCH_PROCESSCO>) getSqlMap().queryForList("repMisProcMapper.retCrtProgRefLkpLst",
		misProcSC, misProcSC.getRecToskip(), misProcSC.getNbRec());
    }

    public int retCrtProgRefLkpCnt(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException
    {
	DAOHelper.fixGridMaps(misProcSC, getSqlMap(), "repMisProcMapper.retCrtProgRefLkpMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("repMisProcMapper.retCrtProgRefLkpCnt", misProcSC)).intValue();
	return nb;
    }

    public int retMisProcGridInterCnt(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException
    {
	DAOHelper.fixGridMaps(misProcSC, getSqlMap(), "repMisProcMapper.retCrtProgRefLkpMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("repMisProcMapper.retMisProcGridInterCnt", misProcSC)).intValue();
	return nb;
    }

    public ArrayList<REP_MISMATCH_PROCESSCO> retMisProcGridInterLst(REP_MISMATCH_PROCESSSC misProcSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(misProcSC, getSqlMap(), "repMisProcMapper.retCrtProgRefLkpMap");
	
	if(misProcSC.getSortOrder()==null)
	{
	    misProcSC.setSortOrder(" ORDER BY REP_MISMATCH_ID ");
	}
	
	return (ArrayList<REP_MISMATCH_PROCESSCO>) getSqlMap().queryForList("repMisProcMapper.retMisProcGridInterLst",
		misProcSC, misProcSC.getRecToskip(), misProcSC.getNbRec());
    }

    public int retMisProcGridIntraCnt(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException
    {
	DAOHelper.fixGridMaps(misProcSC, getSqlMap(), "repMisProcMapper.retCrtProgRefLkpMap");
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("repMisProcMapper.retMisProcGridIntraCnt", misProcSC)).intValue();
	return nb;
    }

    public ArrayList<REP_MISMATCH_PROCESSCO> retMisProcGridIntraLst(REP_MISMATCH_PROCESSSC misProcSC)
	    throws DAOException
    {
	DAOHelper.fixGridMaps(misProcSC, getSqlMap(), "repMisProcMapper.retCrtProgRefLkpMap");
	return (ArrayList<REP_MISMATCH_PROCESSCO>) getSqlMap().queryForList("repMisProcMapper.retMisProcGridIntraLst",
		misProcSC, misProcSC.getRecToskip(), misProcSC.getNbRec());
    }

    public List<REP_SNAPSHOT_INFORMATIONCO> retMisProcSnpInfoList(REP_MISMATCH_PROCESSSC procSc) throws DAOException
    {
	return getSqlMap().queryForList("repMisProcMapper.retMisProcSnpInfoList",procSc);
    }

    public List<REP_MISMATCH_COLUMNCO> retRelColBySnp(REP_MISMATCH_PROCESSSC sc) throws DAOException
    {
	return getSqlMap().queryForList("repMisProcMapper.retRelColBySnp",sc);
    }

    
    
    
}
