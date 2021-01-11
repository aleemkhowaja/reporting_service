package com.path.bo.reporting.ftr.reportsmismatch;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_PROCESSSC;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_INFORMATIONCO;

public interface ReportsMismatchProcessBO
{
    public ArrayList<REP_MISMATCH_PROCESSCO> retCrtProgRefLkpLst(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException;

    public int retCrtProgRefLkpCnt(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException;
    
    public ArrayList<REP_MISMATCH_PROCESSCO> retMisProcGridInterLst(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException;
    
    public int retMisProcGridInterCnt(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException;
    
    public ArrayList<REP_MISMATCH_PROCESSCO> retMisProcGridIntraLst(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException;
    
    public int retMisProcGridIntraCnt(REP_MISMATCH_PROCESSSC misProcSC) throws BaseException;
    
    public List<REP_SNAPSHOT_INFORMATIONCO> retMisProcSnpInfoList(REP_MISMATCH_PROCESSSC procSc)throws BaseException;
    
    public StringBuffer retProcessHtml(List<REP_SNAPSHOT_INFORMATIONCO> snpLst,REP_MISMATCH_PROCESSCO misProcCO, HashMap<String,String> hm)throws Exception;
    

}
