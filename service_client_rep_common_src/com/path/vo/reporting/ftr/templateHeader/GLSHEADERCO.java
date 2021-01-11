package com.path.vo.reporting.ftr.templateHeader;

import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.lib.vo.BaseVO;

public class GLSHEADERCO extends BaseVO
{
    private String TEMPLATE_TYPE_STR;
    
    private GLSHEADERVO glsheaderVO = new GLSHEADERVO();

    public GLSHEADERVO getGlsheaderVO()
    {
        return glsheaderVO;
    }

    public void setGlsheaderVO(GLSHEADERVO glsheaderVO)
    {
        this.glsheaderVO = glsheaderVO;
    }

    public void setTEMPLATE_TYPE_STR(String tEMPLATE_TYPE_STR)
    {
	TEMPLATE_TYPE_STR = tEMPLATE_TYPE_STR;
    }

    public String getTEMPLATE_TYPE_STR()
    {
	return TEMPLATE_TYPE_STR;
    }
}
