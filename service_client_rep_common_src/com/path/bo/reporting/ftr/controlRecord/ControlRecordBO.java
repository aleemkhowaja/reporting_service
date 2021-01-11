package com.path.bo.reporting.ftr.controlRecord;

import com.path.dbmaps.vo.BTR_CONTROLVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLSC;

public interface ControlRecordBO
{
    public BTR_CONTROLCO retrieveControlRecordData(BTR_CONTROLSC btSC) throws BaseException;
    public void updateBtrRecord(BTR_CONTROLCO btrControlCO,String mode) throws BaseException;
    public BTR_CONTROLVO retBtrCtrVO(BTR_CONTROLVO btrControlVO) throws BaseException;
}
