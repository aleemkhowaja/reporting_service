package com.path.vo.reporting.ftr.controlRecord;

import com.path.dbmaps.vo.BTR_CONTROLVO;
import com.path.lib.vo.BaseVO;

public class BTR_CONTROLCO extends BaseVO {
	
	private BTR_CONTROLVO btrControlVO = new BTR_CONTROLVO();
	private String NATIONALITY_BRIEF_DESC_ENG;
	private String sitcomEnableYn;
	

	public String getSitcomEnableYn()
	{
	    return sitcomEnableYn;
	}

	public void setSitcomEnableYn(String sitcomEnableYn)
	{
	    this.sitcomEnableYn = sitcomEnableYn;
	}

	public String getNATIONALITY_BRIEF_DESC_ENG()
	{
	    return NATIONALITY_BRIEF_DESC_ENG;
	}

	public void setNATIONALITY_BRIEF_DESC_ENG(String nATIONALITYBRIEFDESCENG)
	{
	    NATIONALITY_BRIEF_DESC_ENG = nATIONALITYBRIEFDESCENG;
	}

	public BTR_CONTROLVO getBtrControlVO()
	{
	    return btrControlVO;
	}

	public void setBtrControlVO(BTR_CONTROLVO btrControlVO)
	{
	    this.btrControlVO = btrControlVO;
	}
	
}