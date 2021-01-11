package com.path.dao.reporting.designer.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.designer.SnapShotDAO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.lib.common.util.DAOHelper;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.vo.admin.user.UsrSC;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSSC;
import com.path.vo.reporting.snapshots.ARCHIVECO;
import com.path.vo.reporting.snapshots.ARCHIVESC;
import com.path.vo.reporting.snapshots.IRP_SNAPSHOTS_LOGSCO;
import com.path.vo.reporting.snapshots.USER_ACCESSCO;

public class SnapShotDAOImpl extends BaseDAO implements SnapShotDAO
{

    public void insertSnapShots(IRP_SNAPSHOTSCO resLogCO) throws DAOException
    {
	getSqlMap().insert("snapShot.insertSnapShots", resLogCO);
    }

    public List<IRP_SNAPSHOTSCO> retSnapshotList(IRP_SNAPSHOTSSC snpSC) throws DAOException
    {
	if(snpSC.getSortOrder() == null)
	{
	    snpSC.setSortOrder(" ORDER BY SNAPSHOT_ID ");
	}
	USER_ACCESSCO usrCO = new USER_ACCESSCO();
	usrCO.setEMP_COMP_CODE(snpSC.getCompCode());
	usrCO.setUSER_ID(snpSC.getEXECUTED_BY());
	getSqlMap().delete("snapShot.deleteUnusedBranch", usrCO);
	BigDecimal eod = (BigDecimal) getSqlMap().queryForObject("snapShot.retEodViewer", snpSC);

	snpSC.setEod(eod);

	DAOHelper.fixGridMaps(snpSC, getSqlMap(), "snapShot.retSnapshotListMap");
	return getSqlMap().queryForList("snapShot.retSnapshotList", snpSC,
		snpSC.getRecToskip(), snpSC.getNbRec());

    }

    public int retSnapshotListCount(IRP_SNAPSHOTSSC snpSC) throws DAOException
    {
	int nb = 0;
	BigDecimal eod = (BigDecimal) getSqlMap().queryForObject("snapShot.retEodViewer", snpSC);
	snpSC.setEod(eod);

	nb = ((Integer) getSqlMap().queryForObject("snapShot.retSnapshotListCount", snpSC)).intValue();
	return nb;
    }

    public IRP_SNAPSHOTSCO retReportContent(IRP_SNAPSHOTSSC snpSC) throws DAOException
    {
	if((new BigDecimal(3)).equals(snpSC.getIS_DB()))
	{

	    Connection con = snpSC.getCon();
	    StringBuffer sqlStr;
	    sqlStr = new StringBuffer(" ");
	    IRP_SNAPSHOTSCO irpSnapshotsCO = new IRP_SNAPSHOTSCO();
	    sqlStr.append("SELECT REPORT_CONTENT FROM IRP_SNAPSHOTS_ARCHIVE WHERE SNAPSHOT_ID=" + "(?)");

	    CallableStatement st = null;

	    try
	    {
		st = con.prepareCall(sqlStr.toString());
		st.setBigDecimal(1, snpSC.getSNAPSHOT_ID());
		st.execute();
		ResultSet rs = st.getResultSet();

		while(rs.next())
		{
		    irpSnapshotsCO.setREPORT_CONTENT(rs.getBytes("REPORT_CONTENT"));
		}

	    }
	    catch(SQLException e)
	    {
		log.error("Error in executing statement");
	    }
	    finally {
		try
		{
		    st.close();
		    con.close();
		}
		catch(SQLException e)
		{
		    log.error("Error in closing connection");
		}
	    }
	    return irpSnapshotsCO;

	}
	else
	{
	    return (IRP_SNAPSHOTSCO) getSqlMap().queryForObject("snapShot.retReportContent", snpSC);
	}
    }

    public void archivingFlow(ARCHIVECO archiveCO, Connection con) throws DAOException
    {

	ArrayList<IRP_SNAPSHOTSCO> snapListIds = (ArrayList<IRP_SNAPSHOTSCO>) getSqlMap().queryForList("snapShot.retSnapshotIdsForArchiveList",
		archiveCO); // .queryForList("snapShot.retSnapshotForArchiveList",
	// archiveCO,0,0);
	IRP_SNAPSHOTSCO irpSnapshotsCO;

	if(BigDecimal.ONE.equals(archiveCO.getArchiveLocation()))
	{
	    for(int i = 0; i < snapListIds.size(); i++)
	    {
		archiveCO.getIrpSnapshotsVO().setSNAPSHOT_ID(snapListIds.get(i).getSNAPSHOT_ID());
		irpSnapshotsCO = (IRP_SNAPSHOTSCO) getSqlMap().queryForObject(
			"snapShot.retSnapshotReportForArchiveList", archiveCO);
		irpSnapshotsCO.setStatus("s");
		try
		{
		    getSqlMap().insert("snapShot.insertSnapshotForArchiveList", irpSnapshotsCO);
		    getSqlMap().update("snapShot.updateSnapshotForArchiveList", irpSnapshotsCO);
		    getSqlMap().insert("snapShot.insertSnapshotArchiveLogsList", irpSnapshotsCO);
		    commitTransaction();
		}
		catch(Exception e)
		{
		    // TODO Auto-generated catch block
		    irpSnapshotsCO.setStatus("f");
		    e.printStackTrace();
		    rollBackTransaction();
		    getSqlMap().insert("snapShot.insertSnapshotArchiveLogsList", irpSnapshotsCO);
		    commitTransaction();

		    break;
		}
	    }
	}

	else if(new BigDecimal(2).equals(archiveCO.getArchiveLocation()))
	{
	    for(int i = 0; i < snapListIds.size(); i++)
	    {
		archiveCO.getIrpSnapshotsVO().setSNAPSHOT_ID(snapListIds.get(i).getSNAPSHOT_ID());
		irpSnapshotsCO = (IRP_SNAPSHOTSCO) getSqlMap().queryForObject(
			"snapShot.retSnapshotReportForArchiveList", archiveCO);
		irpSnapshotsCO.setStatus("s");
		StringBuffer sqlStr;
		sqlStr = new StringBuffer(" ");
		String exeDate = DateUtil.format(irpSnapshotsCO.getEXECUTION_DATE(), "dd/MM/yyyy");
		if(archiveCO.getIsSybase() == 0)
		{
		    sqlStr.append("INSERT INTO IRP_SNAPSHOTS_ARCHIVE(SNAPSHOT_ID," + "EXECUTION_DATE," + "REPORT_NAME,"
			    + "REPORT_FORMAT," + "EXECUTED_BY," + "FILE_NAME," + "IS_DB," + "REPORT_CONTENT,"
			    + "SNAPSHOT_REF)" + "VALUES(?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?)");
		}
		else
		{
		    sqlStr.append("INSERT INTO IRP_SNAPSHOTS_ARCHIVE(SNAPSHOT_ID," + "EXECUTION_DATE," + "REPORT_NAME,"
			    + "REPORT_FORMAT," + "EXECUTED_BY," + "FILE_NAME," + "IS_DB," + "REPORT_CONTENT,"
			    + "SNAPSHOT_REF)" + "VALUES(?,CONVERT(DATETIME,?,103),?,?,?,?,?,?,?)");
		}

		CallableStatement st = null;

		try
		{
		    st = con.prepareCall(sqlStr.toString());
		}
		catch(SQLException e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		}
		try
		{
		    st.setBigDecimal(1, irpSnapshotsCO.getSNAPSHOT_ID());
		    st.setString(2, exeDate);
		    st.setString(3, irpSnapshotsCO.getREPORT_NAME());
		    st.setString(4, irpSnapshotsCO.getREPORT_FORMAT());
		    st.setString(5, irpSnapshotsCO.getEXECUTED_BY());
		    st.setString(6, irpSnapshotsCO.getFILE_NAME());
		    st.setBigDecimal(7, irpSnapshotsCO.getIS_DB());
		    st.setBytes(8, irpSnapshotsCO.getREPORT_CONTENT());
		    st.setBigDecimal(9, irpSnapshotsCO.getSNAPSHOT_REF());
		    con.setAutoCommit(false);
		    st.execute();
		    archiveCO.setIrpSnapshotsVO(irpSnapshotsCO);
		    getSqlMap().update("snapShot.updateArchiveOtherDBList", archiveCO);
		    getSqlMap().insert("snapShot.insertSnapshotArchiveLogsList", irpSnapshotsCO);
		    con.commit();
		    commitTransaction();

		}
		catch(Exception e)
		{
		    // TODO Auto-generated catch block
		    irpSnapshotsCO.setStatus("f");
		    e.printStackTrace();
		    rollBackTransaction();
		    try
		    {
			con.rollback();
		    }
		    catch(SQLException e1)
		    {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		    }
		    getSqlMap().insert("snapShot.insertSnapshotArchiveLogsList", irpSnapshotsCO);
		    commitTransaction();
		    break;
		}

	    }
	}

	else if(new BigDecimal(3).equals(archiveCO.getArchiveLocation()))
	{
	    // create foler if it does not exist
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
		try {
			File fl = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION);
			
			if(!fl.exists())
		    {
			boolean isCreated = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION).mkdir();
			if(!isCreated)
			{
			    log.debug("The directory " + RepConstantsCommon.SNAPSHOT_LOCATION + " has not been created");
			}
		    }
		} catch (Exception e1) {
			log.error(e1, e1.getMessage());
		}

	    for(int i = 0; i < snapListIds.size(); i++)
	    {
		FileOutputStream foStream = null;
		String repName = "";
		String repNameExt = "";
		archiveCO.getIrpSnapshotsVO().setSNAPSHOT_ID(snapListIds.get(i).getSNAPSHOT_ID());
		irpSnapshotsCO = (IRP_SNAPSHOTSCO) getSqlMap().queryForObject(
			"snapShot.retSnapshotReportForArchiveList", archiveCO);
		File file = null;

		try
		{
		    // String repName
		    // =snapList.get(i).getFILE_NAME()+"_"+snapList.get(i).getEXECUTED_BY()+"_"+DateUtil.format(snapList.get(i).getEXECUTION_DATE(),
		    // "yyyy-MM-dd-kk-mm-ss")+"."+snapList.get(i).getREPORT_FORMAT();
		    // snapList.get(i).getEXECUTION_DATE_STR()
		    // DateUtil.format(new Date(), "yyyy-MM-dd-hh-mm-ss");
		    irpSnapshotsCO.setStatus("s");
		    // repName = irpSnapshotsCO.getFILE_NAME() + "_" +
		    // irpSnapshotsCO.getEXECUTED_BY() + "_"
		    // + irpSnapshotsCO.getEXECUTION_DATE_STR();

		    repName = irpSnapshotsCO.getFILE_NAME();

		    if(archiveCO.getIsSybase() == 1)
		    {
			repName = repName.replace("/", "-");
			repName = repName.replace(":", "-");
		    }
		    repNameExt = repName + "." + irpSnapshotsCO.getREPORT_FORMAT();
		    try {
				file = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + repNameExt);
			} catch (Exception e) {
				log.error(e, e.getMessage());
			}
		    foStream = new FileOutputStream(file);
		}
		catch(FileNotFoundException e)
		{
		    irpSnapshotsCO.setStatus("f");
		    e.printStackTrace();
		}

		try
		{
//		    if(irpSnapshotsCO.getREPORT_CONTENT()!=null)
//		    {
        		foStream.write(irpSnapshotsCO.getREPORT_CONTENT());
        		archiveCO.setIrpSnapshotsVO(irpSnapshotsCO);
        		archiveCO.getIrpSnapshotsVO().setFILE_NAME(repName);
        		getSqlMap().update("snapShot.updateArchiveRepoList", archiveCO);
        		getSqlMap().insert("snapShot.insertSnapshotArchiveLogsList", irpSnapshotsCO);
        		commitTransaction();
//		    }

		}
		catch(Exception e)
		{
		    rollBackTransaction();
		    irpSnapshotsCO.setStatus("f");
		    getSqlMap().insert("snapShot.insertSnapshotArchiveLogsList", irpSnapshotsCO);
		    commitTransaction();
			try {
				File f = new PathFileSecure(repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + repNameExt);
				boolean isDel = f.delete();
				if(!isDel)
				{
					log.error("Error in deleting the report");
				}
			} catch (Exception e1) {
				log.error(e1, e1.getMessage());
			}
		    log.error(e.getMessage());
		    break;
		}
		try
		{
		    foStream.flush();
		}
		catch(IOException e)
		{
		    rollBackTransaction();
		    irpSnapshotsCO.setStatus("f");
		    getSqlMap().insert("snapShot.insertSnapshotArchiveLogsList", irpSnapshotsCO);
		    commitTransaction();
		    boolean isDel = file.delete();
		    if(!isDel)
		    {
			log.error("Error in deleting the report");
		    }
		    log.error(e.getMessage());
		    break;

		}
		try
		{
		    foStream.close();
		}
		catch(IOException e)
		{
		    // TODO Auto-generated catch block
		    rollBackTransaction();
		    irpSnapshotsCO.setStatus("f");
		    getSqlMap().insert("snapShot.insertSnapshotArchiveLogsList", irpSnapshotsCO);
		    commitTransaction();
		    boolean isDel = file.delete();
		    if(!isDel)
		    {
			log.error("Error in deleting the report");
		    }
		    log.error(e.getMessage());
		    break;
		}

	    }
	}

    }

    @SuppressWarnings("unchecked")
    public List<ARCHIVECO> retUserList(ARCHIVESC archiveSC) throws DAOException
    {

	DAOHelper.fixGridMaps(archiveSC, getSqlMap(), "snapShot.retUserListMap");
	return getSqlMap().queryForList("snapShot.retUserList", archiveSC,
		archiveSC.getRecToskip(), archiveSC.getNbRec());

    }

    public int retUserListCount(ARCHIVESC archiveSC) throws DAOException
    {
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapShot.retUserListCount", archiveSC)).intValue();
	return nb;
    }

    @SuppressWarnings("unchecked")
    public List<IRP_SNAPSHOTS_LOGSCO> retArchiveLogsList(ARCHIVESC archiveSC) throws DAOException
    {

	DAOHelper.fixGridMaps(archiveSC, getSqlMap(), "snapShot.retArchiveLogsListMap");
	return getSqlMap().queryForList("snapShot.retArchiveLogsList", archiveSC,
		archiveSC.getRecToskip(), archiveSC.getNbRec());

    }

    public int retArchiveLogsListCount(ARCHIVESC archiveSC) throws DAOException
    {
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapShot.retArchiveLogsListCount", archiveSC)).intValue();
	return nb;
    }

    @SuppressWarnings("unchecked")
    public List<USER_ACCESSCO> retUserAccessList(UsrSC usrSC) throws DAOException
    {

	DAOHelper.fixGridMaps(usrSC, getSqlMap(), "snapShot.retUserAccessListMap");
	return getSqlMap().queryForList("snapShot.retUserAccessList", usrSC,
		usrSC.getRecToskip(), usrSC.getNbRec());

    }

    public int retUserAccessListCount(UsrSC usrSC) throws DAOException
    {
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapShot.retUserAccessListCount", usrSC)).intValue();
	return nb;
    }

    @SuppressWarnings("unchecked")
    public List<USER_ACCESSCO> retViewedUserList(UsrSC usrSC) throws DAOException
    {

	DAOHelper.fixGridMaps(usrSC, getSqlMap(), "snapShot.retViewedUserListMap");
	return getSqlMap().queryForList("snapShot.retViewedUserList", usrSC,
		usrSC.getRecToskip(), usrSC.getNbRec());

    }

    public int retViewedUserListCount(UsrSC usrSC) throws DAOException
    {
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapShot.retViewedUserListCount", usrSC)).intValue();
	return nb;
    }

    public BigDecimal retEodViewer(USER_ACCESSCO userAccessCO) throws DAOException
    {
	BigDecimal nb = null;
	UsrSC usrSC = null;
	getSqlMap().delete("snapShot.deleteUnusedBranch", userAccessCO);
	nb = (BigDecimal) getSqlMap().queryForObject("snapShot.retEodViewer", usrSC);

	return nb;
    }

    @SuppressWarnings("unchecked")
    public Integer saveUserAccess(List<USER_ACCESSCO> lstViewedUsers, List<USER_ACCESSCO> lstSelectedBranch)
	    throws DAOException
    {

	UsrSC usrSC = new UsrSC();
	usrSC.setUser_id(lstViewedUsers.get(0).getViewerUserId());
	ArrayList<USER_ACCESSCO> myLst = (ArrayList<USER_ACCESSCO>) getSqlMap().queryForList("snapShot.retAllViewedUserList", usrSC);
	Long oldTime = null;
	Long newTime = null;
	Date oldDateUp = lstViewedUsers.get(0).getDATE_UPDATED();
	if(oldDateUp != null)
	{
	    String newDateUpStr = DateUtil.format(myLst.get(0).getDATE_UPDATED(), "dd/MM/yyyy hh:mm:ss");
	    Date newDateUp = null;
	    try
	    {
		newDateUp = DateUtil.parseDate(newDateUpStr, "dd/MM/yyyy hh:mm:ss");
	    }
	    catch(BaseException e)
	    {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }

	    // DateUtil.parseDate(myLst.get(0).getDATE_UPDATED(),"dd/MM/yyyy hh:mm:ss");

	    // Date newDateUp = DateUtil.parseDate(
	    // myLst.get(0).getDATE_UPDATED(),"dd/MM/yyyy hh:mm:ss");

	    oldTime = oldDateUp.getTime();
	    newTime = newDateUp.getTime();
	}
	if(oldTime == null || newTime.equals(oldTime))
	{

	    getSqlMap().insert("snapShot.deleteUserAccess", lstViewedUsers.get(0));
	    for(int i = 0; i < lstViewedUsers.size(); i++)
	    {
		USER_ACCESSCO usrCO = new USER_ACCESSCO();
		usrCO = lstViewedUsers.get(i);
		if(usrCO.getIntCheckBox() != null)
		{
		    if(lstSelectedBranch == null || lstSelectedBranch.isEmpty())
		    {
			usrCO.setEMP_BRANCH_CODE(BigDecimal.ZERO);
			usrCO.setEMP_COMP_CODE(BigDecimal.ZERO);
			getSqlMap().insert("snapShot.saveUserAccess", usrCO);
		    }
		    else
		    {
			
			for(int j = 0; j < lstSelectedBranch.size(); j++)
			{
			    usrCO.setEMP_BRANCH_CODE(lstSelectedBranch.get(j).getEMP_BRANCH_CODE());
			    getSqlMap().insert("snapShot.saveUserAccess", usrCO);
			}
		    }
		}
	    }
	}
	else
	{
	    return null;
	}

	return 1;

    }

    public void deleteUserAccess(USER_ACCESSCO userAccessCO) throws DAOException
    {
	getSqlMap().insert("snapShot.deleteUserAccess", userAccessCO);
    }

    @SuppressWarnings("unchecked")
    public List<USER_ACCESSCO> retAllViewedUserList(UsrSC usrSC) throws DAOException
    {

	return getSqlMap().queryForList("snapShot.retAllViewedUserList", usrSC);

    }

    @SuppressWarnings("unchecked")
    public List<USER_ACCESSCO> retBranchGridList(UsrSC usrSC) throws DAOException
    {
	if(usrSC.getSortOrder() == null)
	{
	    usrSC.setSortOrder(" ORDER BY EMP_BRANCH_CODE ");
	}

	DAOHelper.fixGridMaps(usrSC, getSqlMap(), "snapShot.retBranchGridListMap");
	return getSqlMap().queryForList("snapShot.retBranchGridList", usrSC,
		usrSC.getRecToskip(), usrSC.getNbRec());

    }

    public int retBranchGridListCount(UsrSC usrSC) throws DAOException
    {
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapShot.retBranchGridListCount", usrSC)).intValue();
	return nb;
    }

    @SuppressWarnings("unchecked")
    public List<USER_ACCESSCO> retSelectedBranchList(UsrSC usrSC) throws DAOException
    {
	return getSqlMap().queryForList("snapShot.retSelectedBranchList", usrSC);
    }

    @Override
    public int retUserAccessSnpCount(IRP_SNAPSHOTSSC snpSC) throws DAOException
    {
	int nb = 0;
	nb = ((Integer) getSqlMap().queryForObject("snapShot.retUserAccessSnpCount", snpSC)).intValue();
	return nb;
    }

    @Override
    public List<IRP_SNAPSHOTSCO> retUserAccessSnpList(IRP_SNAPSHOTSSC snpSC) throws DAOException
    {
	if(snpSC.getSortOrder() == null)
	{
	    snpSC.setSortOrder(" ORDER BY REPORT_NAME ");
	}
	DAOHelper.fixGridMaps(snpSC, getSqlMap(), "snapShot.retUserAccessSnpListMap");
	return getSqlMap().queryForList("snapShot.retUserAccessSnpList", snpSC,snpSC.getRecToskip(), snpSC.getNbRec());
    }

}
