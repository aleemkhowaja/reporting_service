package com.path.dao.reporting.ftr.reportsmismatch;

import java.util.ArrayList;
import java.util.List;

import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;

public interface ReportsMismatchProcessDAO
{
    public ArrayList<REP_MISMATCH_PROCESSCO> retCrtProgRefLkpLst(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException;

    public int retCrtProgRefLkpCnt(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException;
    
    public ArrayList<REP_MISMATCH_PROCESSCO> retMisProcGridInterLst(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException;
    
    public int retMisProcGridInterCnt(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException;
    
    public ArrayList<REP_MISMATCH_PROCESSCO> retMisProcGridIntraLst(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException;
    
    public int retMisProcGridIntraCnt(REP_MISMATCH_PROCESSSC misProcSC) throws DAOException;
    
    public List<REP_SNAPSHOT_INFORMATIONCO> retMisProcSnpInfoList(REP_MISMATCH_PROCESSSC procSc)throws DAOException;
    
    public List<REP_MISMATCH_COLUMNCO> retRelColBySnp(REP_MISMATCH_PROCESSSC sc)throws DAOException;
}
