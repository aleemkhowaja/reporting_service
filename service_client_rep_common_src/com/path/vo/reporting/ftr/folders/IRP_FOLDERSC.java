package com.path.vo.reporting.ftr.folders;

import com.path.struts2.lib.common.GridParamsSC;

public class IRP_FOLDERSC extends GridParamsSC
{

    private String APP_NAME;

    public IRP_FOLDERSC()
    {
	super
		.setSearchCols(new String[] { "BRIEF_NAME_ENG", "BRIEF_NAME_ARAB", "FOLDER_REF", "DISP_ORDER",
			"APP_NAME" });
    }

    public String getAPP_NAME()
    {
	return APP_NAME;
    }

    public void setAPP_NAME(String aPPNAME)
    {
	APP_NAME = aPPNAME;
    }

}
