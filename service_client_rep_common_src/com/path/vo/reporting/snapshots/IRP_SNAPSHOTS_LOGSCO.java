package com.path.vo.reporting.snapshots;

import com.path.dbmaps.vo.IRP_SNAPSHOTS_LOGSVO;
import com.path.lib.vo.BaseVO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;

public class IRP_SNAPSHOTS_LOGSCO extends BaseVO
{
    private IRP_SNAPSHOTS_LOGSVO irpSnapshotsLogsVO = new IRP_SNAPSHOTS_LOGSVO();
    private IRP_SNAPSHOTSCO irpSnapShotsCO = new IRP_SNAPSHOTSCO();
    //private String statusDesc;

    
    
    public IRP_SNAPSHOTS_LOGSVO getIrpSnapshotsLogsVO()
    {
        return irpSnapshotsLogsVO;
    }

    public IRP_SNAPSHOTSCO getIrpSnapShotsCO()
    {
        return irpSnapShotsCO;
    }

    public void setIrpSnapShotsCO(IRP_SNAPSHOTSCO irpSnapShotsCO)
    {
        this.irpSnapShotsCO = irpSnapShotsCO;
    }

    public void setIrpSnapshotsLogsVO(IRP_SNAPSHOTS_LOGSVO irpSnapshotsLogsVO)
    {
        this.irpSnapshotsLogsVO = irpSnapshotsLogsVO;
    }
    
    public String getStatusDesc() {
	return (this.getIrpSnapshotsLogsVO().getSTATUS().equals("s")? "Succeed" : "Failed");
}	
}
