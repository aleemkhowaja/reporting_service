package com.path.bo.reporting.designer;

import java.math.BigDecimal;
import java.util.List;

import com.path.lib.common.exception.BaseException;
import com.path.vo.admin.user.UsrSC;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSSC;
import com.path.vo.reporting.snapshots.ARCHIVECO;
import com.path.vo.reporting.snapshots.ARCHIVESC;
import com.path.vo.reporting.snapshots.IRP_SNAPSHOTS_LOGSCO;
import com.path.vo.reporting.snapshots.USER_ACCESSCO;

public interface SnapShotBO 
{
	public void insertSnapShots(IRP_SNAPSHOTSCO resLogCO) throws BaseException;
	public List<IRP_SNAPSHOTSCO> retSnapshotList(IRP_SNAPSHOTSSC snpSC)throws BaseException;
	public int retSnapshotListCount(IRP_SNAPSHOTSSC snpSC)throws BaseException;
	public  IRP_SNAPSHOTSCO retReportContent(IRP_SNAPSHOTSSC snpSC)throws BaseException;
	public void archivingFlow(ARCHIVECO archiveCO)throws Exception;
	public List<ARCHIVECO> retUserList(ARCHIVESC archiveSC)throws BaseException;
	public int retUserListCount(ARCHIVESC archiveSC)throws BaseException;
	public List<IRP_SNAPSHOTS_LOGSCO> retArchiveLogsList(ARCHIVESC archiveSC)throws BaseException;
	public int retArchiveLogsListCount(ARCHIVESC archiveSC)throws BaseException;
	public List<USER_ACCESSCO> retUserAccessList(UsrSC usrSC)throws BaseException;
	public int retUserAccessListCount(UsrSC usrSC)throws BaseException;
	public List<USER_ACCESSCO> retViewedUserList(UsrSC usrSC)throws BaseException;
	public int retViewedUserListCount(UsrSC usrSC)throws BaseException;
	public BigDecimal retEodViewer(USER_ACCESSCO userAccessCO)throws BaseException;
	public void saveUserAccess(List<USER_ACCESSCO> lstViewedUsers,List<USER_ACCESSCO> lstSelectedBranch)throws BaseException;
	public List<USER_ACCESSCO> retAllViewedUserList(UsrSC usrSC)throws BaseException;
	public void deleteUserAccess(USER_ACCESSCO userAccessCO) throws BaseException;
	public int retBranchGridListCount(UsrSC usrSC)throws BaseException;
	public List<USER_ACCESSCO> retBranchGridList(UsrSC usrSC)throws BaseException;
	public List<USER_ACCESSCO> retSelectedBranchList(UsrSC usrSC)throws BaseException;
    
        public Object[] readSnapshotFromRepository(String repName, String ext) throws BaseException;
    
        /**
         * Returns the list of snapshots based on a selected user
         * 
         * @param snpSC
         * @return list of IRP_SNAPSHOTSCO
         * @throws BaseException
         */
        public List<IRP_SNAPSHOTSCO> retUserAccessSnpList(IRP_SNAPSHOTSSC snpSC) throws BaseException;
    
        /**
         * Returns the count of the list of snapshots based on a selected user
         * 
         * @param snpSC
         * @return list of IRP_SNAPSHOTSCO
         * @throws BaseException
         */
        public int retUserAccessSnpCount(IRP_SNAPSHOTSSC snpSC) throws BaseException;
    }
