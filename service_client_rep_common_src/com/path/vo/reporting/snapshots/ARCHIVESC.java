package com.path.vo.reporting.snapshots;

import com.path.struts2.lib.common.GridParamsSC;

public class ARCHIVESC extends GridParamsSC
{

    
    
    public ARCHIVESC()
    {
	super.setSearchCols(new String[] {"SNAPSHOT_ID","ARCHIVE_DATE","STATUS","STATUSDESC","ARCHIVE_DATE"});
    }
    
}
