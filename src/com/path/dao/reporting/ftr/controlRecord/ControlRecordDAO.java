package com.path.dao.reporting.ftr.controlRecord;

import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLSC;

public interface ControlRecordDAO
{
    public BTR_CONTROLCO retrieveControlRecordData(BTR_CONTROLSC btSC) throws DAOException;
}
