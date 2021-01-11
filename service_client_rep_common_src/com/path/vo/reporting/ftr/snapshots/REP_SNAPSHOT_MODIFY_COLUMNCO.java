package com.path.vo.reporting.ftr.snapshots;

import java.io.Serializable;

import com.path.dbmaps.vo.REP_SNAPSHOT_MODIFY_COLUMNVO;
import com.path.lib.vo.BaseVO;

public class REP_SNAPSHOT_MODIFY_COLUMNCO extends BaseVO implements Serializable
{
    private REP_SNAPSHOT_MODIFY_COLUMNVO repSnapshotModifyColumnVO = new REP_SNAPSHOT_MODIFY_COLUMNVO();
    private String description;
    private String colTechName;

    public String getColTechName()
    {
        return colTechName;
    }

    public void setColTechName(String colTechName)
    {
        this.colTechName = colTechName;
    }

    public REP_SNAPSHOT_MODIFY_COLUMNVO getRepSnapshotModifyColumnVO()
    {
        return repSnapshotModifyColumnVO;
    }

    public void setRepSnapshotModifyColumnVO(REP_SNAPSHOT_MODIFY_COLUMNVO repSnapshotModifyColumnVO)
    {
        this.repSnapshotModifyColumnVO = repSnapshotModifyColumnVO;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }
    
    
}
