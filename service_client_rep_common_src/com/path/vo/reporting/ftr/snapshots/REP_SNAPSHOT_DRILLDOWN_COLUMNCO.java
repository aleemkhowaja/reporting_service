package com.path.vo.reporting.ftr.snapshots;

import java.io.Serializable;

import com.path.dbmaps.vo.REP_SNAPSHOT_DRILLDOWN_COLUMNVO;
import com.path.lib.vo.BaseVO;

public class REP_SNAPSHOT_DRILLDOWN_COLUMNCO extends BaseVO implements Serializable
{
    private REP_SNAPSHOT_DRILLDOWN_COLUMNVO repSnapshotDrilColVO = new REP_SNAPSHOT_DRILLDOWN_COLUMNVO();
    private String TECH_COL_NAME;

    public String getTECH_COL_NAME()
    {
        return TECH_COL_NAME;
    }

    public void setTECH_COL_NAME(String tECHCOLNAME)
    {
        TECH_COL_NAME = tECHCOLNAME;
    }

    public REP_SNAPSHOT_DRILLDOWN_COLUMNVO getRepSnapshotDrilColVO()
    {
        return repSnapshotDrilColVO;
    }

    public void setRepSnapshotDrilColVO(REP_SNAPSHOT_DRILLDOWN_COLUMNVO repSnapshotDrilColVO)
    {
        this.repSnapshotDrilColVO = repSnapshotDrilColVO;
    }
    

}
