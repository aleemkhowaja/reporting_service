package com.path.dao.reporting.ftr.controlRecord.impl;
import com.path.dao.reporting.ftr.controlRecord.ControlRecordDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLSC;

public class ControlRecordDAOImpl extends BaseDAO implements ControlRecordDAO
{
    public BTR_CONTROLCO retrieveControlRecordData(BTR_CONTROLSC btSC) throws DAOException
    {
	return (BTR_CONTROLCO)getSqlMap().queryForObject("BTR_CONTROLADD.retrieveData", btSC);
    }   
}
