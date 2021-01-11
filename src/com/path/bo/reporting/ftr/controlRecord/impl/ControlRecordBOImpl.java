package com.path.bo.reporting.ftr.controlRecord.impl;
import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.controlRecord.ControlRecordBO;
import com.path.dao.reporting.ftr.controlRecord.ControlRecordDAO;
import com.path.dbmaps.vo.BTR_CONTROLVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLCO;
import com.path.vo.reporting.ftr.controlRecord.BTR_CONTROLSC;

public class ControlRecordBOImpl extends BaseBO implements ControlRecordBO
{
private ControlRecordDAO controlRecordDAO;


public void setControlRecordDAO(ControlRecordDAO controlRecordDAO)
{
    this.controlRecordDAO = controlRecordDAO;
}
	
public BTR_CONTROLCO retrieveControlRecordData(BTR_CONTROLSC btSC) throws BaseException
{
    return controlRecordDAO.retrieveControlRecordData(btSC);
    
}

public void updateBtrRecord(BTR_CONTROLCO btrControlCO,String mode) throws BaseException
{
     if(  RepConstantsCommon.UPDATE_MODE.equals(mode))
     {
	 Integer row = genericDAO.update(btrControlCO.getBtrControlVO());
	      /*

	      * Record Changed between retrieve and update

	      */

	      if(row == null || row < 1)
	      {

	          throw new BOException(MessageCodes.RECORD_CHANGED);

	      }

	 BTR_CONTROLVO oldVO =(BTR_CONTROLVO)btrControlCO.getBtrControlVO().getAuditRefCO().getAuditObj();
	 BTR_CONTROLVO newVO=btrControlCO.getBtrControlVO();
	 oldVO.setDATE_UPDATED(newVO.getDATE_UPDATED());
	 oldVO.setACBRD_AMOUNT(newVO.getACBRD_AMOUNT());
	 auditBO.callAudit(oldVO, newVO, btrControlCO.getBtrControlVO().getAuditRefCO());
	 auditBO.insertAudit(btrControlCO.getBtrControlVO().getAuditRefCO());
     }
     else
     {
	 genericDAO.insert(btrControlCO.getBtrControlVO());
	 auditBO.callAudit(null, btrControlCO.getBtrControlVO(),btrControlCO.getBtrControlVO().getAuditRefCO());
     }
}

/**
 * Method that returns the btr_control_vo by company 
 * @return
 */
public BTR_CONTROLVO retBtrCtrVO(BTR_CONTROLVO btrControlVO) throws BaseException
{
	return (BTR_CONTROLVO) genericDAO.selectByPK(btrControlVO);
}

}
