package com.path.bo.reporting.connection.impl;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.connection.ConnectionBO;
import com.path.dao.reporting.connection.ConnectionDAO;
import com.path.dbmaps.vo.IRP_APP_CONNECTIONVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.SecurityUtils;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.reporting.IRP_CONNECTIONSCO;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONCO;
import com.path.vo.reporting.connection.IRP_APP_CONNECTIONSC;

public class ConnectionBOImpl extends BaseBO implements ConnectionBO
{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private ConnectionDAO connectionDAO;
    
    public void setConnectionDAO(ConnectionDAO connectionDAO)
    {
        this.connectionDAO = connectionDAO;
    }

    public List<IRP_CONNECTIONSVO> retConnectionList(IRP_CONNECTIONSSC irpConSC) throws BaseException
    {
	 return connectionDAO.retConnectionList(irpConSC);
    }
 
    /**
     * get the connection list count
     * @return int
     * @throws Exception
     */
    public int retConnectionListCount(IRP_CONNECTIONSSC irpConSC) throws BaseException
    {
	return connectionDAO.retConnectionListCount(irpConSC);
    }
    
    /**
     * retrieve connection detail from db for the selected row
     * @return IRP_CONNECTIONSVO
     * @throws Exception
     */
    public IRP_CONNECTIONSVO retrieveConnData(IRP_CONNECTIONSVO irpConVO) throws BaseException 
    {
	return (IRP_CONNECTIONSVO) genericDAO.selectByPK(irpConVO);
    }
    
    /**
     * insert the changes made in the form 
     */
    public void insertConDetail(IRP_CONNECTIONSCO icCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException 
    {
	
	IRP_CONNECTIONSVO icVO =icCO.getIrpConnectionsVO();
	String encodedDbPass     = SecurityUtils.encodeB64(icVO.getDB_PASS());
	icVO.setDB_PASS(encodedDbPass);
	
	genericDAO.insert(icVO);
	//appy audit
	auditBO.callAudit(null, icVO, icVO.getAuditRefCO());
    }
    
    /**
     * update the changes made in the form 
     */
    public void updateConDetail(IRP_CONNECTIONSCO icCO,IRP_CONNECTIONSVO oldConnVO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException 
    {
	
	IRP_CONNECTIONSVO icVO =icCO.getIrpConnectionsVO();
	if(!icCO.getOldDbPass().equals(icVO.getDB_PASS()))
	{
	    String encodedDbPass     = SecurityUtils.encodeB64(icVO.getDB_PASS());
	    icVO.setDB_PASS(encodedDbPass);
	}
	
	Integer row = connectionDAO.updateConDetail(icVO);
	 if (row == null || row < 1)
	 {
	     throw new BOException(MessageCodes.RECORD_CHANGED);
	 }
	
	  //apply audit
	  auditBO.callAudit( oldConnVO, icVO ,icVO.getAuditRefCO());
	  auditBO.insertAudit(icVO.getAuditRefCO());
	  
    }
    
    /**
     * check if the connection id is linked to an application before deleted
     * @return
     */
    public int checkIfHaveAppCon(IRP_CONNECTIONSVO icVO)throws BaseException
    {
	IRP_CONNECTIONSSC sc = new IRP_CONNECTIONSSC();
	sc.setCONN_ID(icVO.getCONN_ID());
	sc.setIsSybase(ConstantsCommon.CURR_DBMS_SYBASE);
	return connectionDAO.checkIfHaveAppCon(sc); 
    }
    
    /**
     * delete the selected connection
     */
    public void deleteConDetail(IRP_CONNECTIONSVO icVO) throws BaseException 
    {
	genericDAO.delete(icVO);
	//apply audit
	auditBO.callAudit( icVO, icVO, icVO.getAuditRefCO());
	auditBO.insertAudit(icVO.getAuditRefCO());
    }
    
   
    /**
     * this method test the connection to the db and return the connection problem in case a failed connection 
     */
    public void conTest(IRP_CONNECTIONSCO icCO,String pageRef,BigDecimal compCode,String appName,String lang)throws BaseException, SQLException, ClassNotFoundException 
    {
	
	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setObj_value(icCO);
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);

	commonLibBO.checkRequired(checkRequiredCO); 
	
	Connection con = null;
	
	if(icCO.getIrpConnectionsVO().getCONN_ID() == null)
	{
	    Class.forName(icCO.getIrpConnectionsVO().getDBMS());
	    con = DriverManager.getConnection(icCO.getIrpConnectionsVO().getURL(), icCO.getIrpConnectionsVO().getUSER_ID(), icCO.getIrpConnectionsVO().getDB_PASS());
	}
	else 
	{
	    Class.forName(icCO.getIrpConnectionsVO().getDBMS());
	    if(icCO.getOldDbPass().equals(icCO.getIrpConnectionsVO().getDB_PASS()))
	    {
		String decodedDbPass     = SecurityUtils.decodeB64(icCO.getIrpConnectionsVO().getDB_PASS());
		con = DriverManager.getConnection(icCO.getIrpConnectionsVO().getURL(), icCO.getIrpConnectionsVO().getUSER_ID(), decodedDbPass);
	    }
	    else
	    {
		con = DriverManager.getConnection(icCO.getIrpConnectionsVO().getURL(), icCO.getIrpConnectionsVO().getUSER_ID(), icCO.getIrpConnectionsVO().getDB_PASS()); 
	    }
	}
	if(con != null)
	{
	    con.close();
	}
    }
    
    /**
     * get the list of application connection
     * @return IRP_APP_CONNECTIONCO
     * @throws BaseException
     */
    public List<IRP_APP_CONNECTIONCO> retAppConnectionList(IRP_APP_CONNECTIONSC irpAppConSC) throws BaseException
    {
	 return connectionDAO.retAppConnectionList(irpAppConSC);
    }

    /**
     * get the list of application connection
     * @return int
     * @throws BaseException
     */
    public int retAppConnectionListCount(IRP_APP_CONNECTIONSC irpAppConSC) throws BaseException
    {
	return connectionDAO.retAppConnectionListCount(irpAppConSC);
    }
    
    /**
     * get application name list 
     * @return IRP_APP_CONNECTIONCO
     * @throws BaseException
     */
    public List<IRP_APP_CONNECTIONCO> retAppNameList(IRP_APP_CONNECTIONSC irpAppConSC) throws BaseException
    {
	 return connectionDAO.retAppNameList(irpAppConSC);
    }

    /**
     * get the list of application name
     * @return int
     * @throws BaseException
     */
    public int retAppNameListCount(IRP_APP_CONNECTIONSC irpAppConSC) throws BaseException
    {
	return connectionDAO.retAppNameListCount(irpAppConSC);
    }
    
    /**
     * get the application connection data
     * @return IRP_APP_CONNECTIONCO
     * @throws BaseException
     */
    public IRP_APP_CONNECTIONCO retrieveAppConnData(IRP_APP_CONNECTIONCO icAppCO) throws BaseException 
    {
	IRP_APP_CONNECTIONCO theCO = connectionDAO.retrieveAppConnData(icAppCO);
	
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplayAppCon        = new SYS_PARAM_SCREEN_DISPLAYVO();
	SYS_PARAM_SCREEN_DISPLAYVO screenDisplayAppConLabel   = new SYS_PARAM_SCREEN_DISPLAYVO();
	HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	if(icAppCO.getActionType().equals("edit"))
	{
	    screenDisplayAppCon.setIS_READONLY(BigDecimal.ONE);
	    screenDisplayAppCon.setIS_MANDATORY(BigDecimal.ZERO);
	    screenDisplayAppConLabel.setIS_MANDATORY(BigDecimal.ZERO);
	    businessHm.put("icAppCO.irpAppConnectionVO.APP_NAME", screenDisplayAppCon);
	    businessHm.put("icAppCO.LONG_DESC", screenDisplayAppCon);
	}
	theCO.setBusinessHm(businessHm);
        return theCO;
    }

    
    /**
     * save application connection data
     * @throws BaseException
     */
    public void insertAppCon(IRP_APP_CONNECTIONCO icAppCO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException 
    {
	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setObj_value(icAppCO);
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);

	commonLibBO.checkRequired(checkRequiredCO); 
	IRP_APP_CONNECTIONVO icAppVO =icAppCO.getIrpAppConnectionVO();
	
	genericDAO.insert(icAppVO);
	//appy audit
	auditBO.callAudit(null, icAppVO, icAppVO.getAuditRefCO());
    }
    
    /**
     * update application connection data
     * @throws BaseException
     */
    @SuppressWarnings("unchecked")
    public void updateAppConDetail(IRP_APP_CONNECTIONCO icAppCO,IRP_APP_CONNECTIONVO oldAppConnVO,String pageRef,BigDecimal compCode,String appName,String lang) throws BaseException
    {
	
	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setObj_value(icAppCO);
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);

	commonLibBO.checkRequired(checkRequiredCO); 

	
	IRP_APP_CONNECTIONVO icAppVO =icAppCO.getIrpAppConnectionVO();
	Integer row = connectionDAO.updateAppConDetail(icAppVO);
	 if (row == null || row < 1)
	 {
	     throw new BOException(MessageCodes.RECORD_CHANGED);
	 }
	  //apply audit
	  auditBO.callAudit( oldAppConnVO, icAppVO ,icAppVO.getAuditRefCO());
	  auditBO.insertAudit(icAppVO.getAuditRefCO());
    }
    
    /**
     * delete application connection data
     * @throws BaseException
     */
    public void deleteAppConnection(IRP_APP_CONNECTIONVO icAppVO) throws BaseException 
    {
	genericDAO.delete(icAppVO);
	//apply audit
	auditBO.callAudit( icAppVO, icAppVO, icAppVO.getAuditRefCO());
	auditBO.insertAudit(icAppVO.getAuditRefCO());
    }
    
    public IRP_APP_CONNECTIONCO applyAppConDependency(IRP_APP_CONNECTIONVO icAppVO) throws BaseException 
    {
	return connectionDAO.applyAppConDependency(icAppVO);
    }
    
    public IRP_APP_CONNECTIONCO applyAppNameDependency(IRP_APP_CONNECTIONVO icAppVO) throws BaseException 
    {
	return connectionDAO.applyAppNameDependency(icAppVO);
    }
	
}
