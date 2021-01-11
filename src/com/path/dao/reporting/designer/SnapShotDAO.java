package com.path.dao.reporting.designer;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;

import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.admin.user.UsrSC;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSSC;
import com.path.vo.reporting.snapshots.ARCHIVECO;
import com.path.vo.reporting.snapshots.ARCHIVESC;
import com.path.vo.reporting.snapshots.IRP_SNAPSHOTS_LOGSCO;
import com.path.vo.reporting.snapshots.USER_ACCESSCO;

public interface SnapShotDAO 
{
	public void insertSnapShots(IRP_SNAPSHOTSCO resLogCO)throws DAOException;
	public List<IRP_SNAPSHOTSCO> retSnapshotList(IRP_SNAPSHOTSSC snpSC)throws DAOException;
	public int retSnapshotListCount(IRP_SNAPSHOTSSC snpSC)throws DAOException;
	public IRP_SNAPSHOTSCO retReportContent(IRP_SNAPSHOTSSC snpSC)throws DAOException;
	public void archivingFlow(ARCHIVECO archiveCO,Connection con)throws DAOException;
	public List<ARCHIVECO> retUserList(ARCHIVESC archiveSC)throws DAOException;
	public int retUserListCount(ARCHIVESC archiveSC)throws DAOException;
	public List<IRP_SNAPSHOTS_LOGSCO> retArchiveLogsList(ARCHIVESC archiveSC)throws DAOException;
	public int retArchiveLogsListCount(ARCHIVESC archiveSC)throws DAOException;
	public List<USER_ACCESSCO> retUserAccessList(UsrSC usrSC)throws DAOException;
	public int retUserAccessListCount(UsrSC usrSC)throws DAOException;
	public List<USER_ACCESSCO> retViewedUserList(UsrSC usrSC)throws DAOException;
	public int retViewedUserListCount(UsrSC usrSC)throws DAOException;
	public BigDecimal retEodViewer(USER_ACCESSCO userAccessCO)throws DAOException;
	public Integer saveUserAccess(List<USER_ACCESSCO> lstViewedUsers,List<USER_ACCESSCO> lstSelectedBranch)throws DAOException;
	public List<USER_ACCESSCO> retAllViewedUserList(UsrSC usrSC)throws DAOException;
	public void deleteUserAccess(USER_ACCESSCO userAccessCO)throws DAOException;
	public List<USER_ACCESSCO> retBranchGridList(UsrSC usrSC)throws DAOException;
	public int retBranchGridListCount(UsrSC usrSC)throws DAOException;
	public List<USER_ACCESSCO> retSelectedBranchList(UsrSC usrSC)throws DAOException;
    
        /**
         * Returns the list of snapshots based on a selected user
         * 
         * @param snpSC
         * @return list of IRP_SNAPSHOTSCO
         * @throws BaseException
         */
        public List<IRP_SNAPSHOTSCO> retUserAccessSnpList(IRP_SNAPSHOTSSC snpSC) throws DAOException;
    
        /**
         * Returns the count of the list of snapshots based on a selected user
         * 
         * @param snpSC
         * @return list of IRP_SNAPSHOTSCO
         * @throws BaseException
         */
        public int retUserAccessSnpCount(IRP_SNAPSHOTSSC snpSC) throws DAOException;
    }
