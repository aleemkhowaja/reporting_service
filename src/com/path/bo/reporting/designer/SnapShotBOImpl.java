package com.path.bo.reporting.designer;

import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.CommonLookupBO;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dao.reporting.designer.SnapShotDAO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.IRP_SNAPSHOTSVO;
import com.path.dbmaps.vo.USRVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.SecurityUtils;
import com.path.vo.admin.user.UsrSC;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSCO;
import com.path.vo.reporting.reports.IRP_SNAPSHOTSSC;
import com.path.vo.reporting.snapshots.ARCHIVECO;
import com.path.vo.reporting.snapshots.ARCHIVESC;
import com.path.vo.reporting.snapshots.IRP_SNAPSHOTS_LOGSCO;
import com.path.vo.reporting.snapshots.USER_ACCESSCO;

public class SnapShotBOImpl extends BaseBO implements SnapShotBO
{
	private SnapShotDAO snapShotDAO;
	private CommonLookupBO commonLookupBO;
	
	
	
	
	public void setCommonLookupBO(CommonLookupBO commonLookupBO)
	{
	    this.commonLookupBO = commonLookupBO;
	}

	public SnapShotDAO getSnapShotDAO() {
		return snapShotDAO;
	}

	public void setSnapShotDAO(SnapShotDAO snapShotDAO) {
		this.snapShotDAO = snapShotDAO;
	}
	
	public void insertSnapShots(IRP_SNAPSHOTSCO resLogCO)throws BaseException 
	{
		snapShotDAO.insertSnapShots(resLogCO);
		//Dummy insert
		IRP_SNAPSHOTSVO dummySnpVO=new IRP_SNAPSHOTSVO();
		dummySnpVO.setSNAPSHOT_ID(resLogCO.getSNAPSHOT_ID());
		dummySnpVO.setREPORT_NAME(resLogCO.getREPORT_NAME());
		genericDAO.update(dummySnpVO);
		
	}

	public List<IRP_SNAPSHOTSCO> retSnapshotList(IRP_SNAPSHOTSSC snpSC)throws BaseException 
	{
		return snapShotDAO.retSnapshotList(snpSC);
	}

	public int retSnapshotListCount(IRP_SNAPSHOTSSC snpSC)throws BaseException
	{
		return snapShotDAO.retSnapshotListCount(snpSC);
	}

	public IRP_SNAPSHOTSCO retReportContent(IRP_SNAPSHOTSSC snpSC)throws BaseException 
	{
	    Connection con = null;
	    if((new BigDecimal(3)).equals(snpSC.getIS_DB()))
	    {
		   IRP_CONNECTIONSVO conVO = commonLookupBO.returnConnById(snpSC.getCONN_ID());
       	    if(conVO != null){
       		
       		String decodedDbPass  = SecurityUtils.decodeB64(conVO.getDB_PASS());
       		try
		{
		    Class.forName(conVO.getDBMS());
		}
		catch(ClassNotFoundException e1)
		{
		    // TODO Auto-generated catch block
		    e1.printStackTrace();
		} 
       		try{
       		    con =
       			DriverManager.getConnection(
       				conVO.getURL(),
       				conVO.getUSER_ID(),
       				decodedDbPass
       			);
       		}
       		catch (Exception e)
       		{
       		    e.printStackTrace();
       		}
       	    }
       	    snpSC.setCon(con);
	    }
		return snapShotDAO.retReportContent(snpSC);
	}
	
	public void archivingFlow(ARCHIVECO archiveCO)throws Exception 
	{
	if("2".equals(archiveCO.getDateRange()) &&( archiveCO.getStartRangeDate() == null
			|| archiveCO.getEndRangeDate() == null)
			)
	{
		    throw new BOException("tmplProc.startOrEndEmpty");
	}
	    else if("2".equals(archiveCO.getDateRange()) && archiveCO.getStartRangeDate().compareTo(archiveCO.getEndRangeDate())>0)
	{
	    throw new BOException("tmplProc.checkDatesAlert");
	}
	    Connection con = null;
	    archiveCO.setIsSybase(commonLibBO.returnIsSybase());
	    if((new BigDecimal(2)).equals(archiveCO.getArchiveLocation()))
	    {
		   IRP_CONNECTIONSVO conVO = commonLookupBO.returnConnById(archiveCO.getConnection());
       	    if(conVO != null){
       		
       		String decodedDbPass  = SecurityUtils.decodeB64(conVO.getDB_PASS());
       		try
		{
		    Class.forName(conVO.getDBMS());
		}
		catch(ClassNotFoundException e1)
		{
		    log.error(e1.getMessage());
		} 
       		try{
       		    con =
       			DriverManager.getConnection(
       				conVO.getURL(),
       				conVO.getUSER_ID(),
       				decodedDbPass
       			);
       		}
       		catch (Exception e)
       		{
       		    throw new BOException(e.getMessage(),e);
       		}
       	    }
	    }
	    
	    snapShotDAO.archivingFlow(archiveCO,con);
	}
	
	public List<ARCHIVECO> retUserList(ARCHIVESC archiveSC)throws BaseException 
	{
		return snapShotDAO.retUserList(archiveSC);
	}

	public int retUserListCount(ARCHIVESC archiveSC)throws BaseException
	{
		return snapShotDAO.retUserListCount(archiveSC);
	}
	
	public List<IRP_SNAPSHOTS_LOGSCO> retArchiveLogsList(ARCHIVESC archiveSC)throws BaseException 
	{
		return snapShotDAO.retArchiveLogsList(archiveSC);
	}

	public int retArchiveLogsListCount(ARCHIVESC archiveSC)throws BaseException
	{
		return snapShotDAO.retArchiveLogsListCount(archiveSC);
	}
	public List<USER_ACCESSCO> retUserAccessList(UsrSC usrSC)throws BaseException 
	{
		return snapShotDAO.retUserAccessList(usrSC);
	}

	public int retUserAccessListCount(UsrSC usrSC)throws BaseException
	{
		return snapShotDAO.retUserAccessListCount(usrSC);
	}
	
	public List<USER_ACCESSCO> retViewedUserList(UsrSC usrSC)throws BaseException 
	{
		return snapShotDAO.retViewedUserList(usrSC);
	}

	public int retViewedUserListCount(UsrSC usrSC)throws BaseException
	{
		return snapShotDAO.retViewedUserListCount(usrSC);
	}
	
	public BigDecimal retEodViewer(USER_ACCESSCO userAccessCO)throws BaseException
	{	
	    	userAccessCO.setIsSybase(commonLibBO.returnIsSybase());
		return snapShotDAO.retEodViewer(userAccessCO);
	}
	
	public void saveUserAccess(List<USER_ACCESSCO> lstViewedUsers ,List<USER_ACCESSCO> lstSelectedBranch)throws BaseException 
	{
	    // throw exception if no branch is selected
	    if(//test if no viewed user is selected to delete it
		 (!lstViewedUsers.isEmpty() && (lstViewedUsers.get(0).getIntCheckBox())!=null) && 
		//test if no branch is selected from the grid and if eodViewr =0 to throw an exception. 
	        (lstSelectedBranch == null || (lstSelectedBranch.isEmpty() && (lstViewedUsers.get(0).getEodViewer().equals(BigDecimal.ZERO)))))
	    {
		    throw new BOException("emptyBr_key");
	    }
	    	Integer row = snapShotDAO.saveUserAccess(lstViewedUsers,lstSelectedBranch);	
	    	if(row==null)
	    	{
	    	    throw new BOException(MessageCodes.RECORD_CHANGED);
	    	}
	    	
	    	USRVO oldUsrVO = new USRVO();
		
		if(lstViewedUsers.get(0).getEodViewer().equals(BigDecimal.ONE))
		{
		    for(int i=0;i<lstViewedUsers.size();i++)
		    {
			USRVO newUsrVO = new USRVO();
			newUsrVO.setUSER_ID(lstViewedUsers.get(i).getUSER_ID());
			newUsrVO.setUSER_GRP_ID(lstViewedUsers.get(i).getViewerUserId());
			oldUsrVO.setUSER_GRP_ID(lstViewedUsers.get(i).getViewerUserId());
			auditBO.callAudit( oldUsrVO, newUsrVO ,lstViewedUsers.get(i).getAuditRefCO()); 
			
		    }
		    auditBO.insertAudit(lstViewedUsers.get(0).getAuditRefCO());
		}
		else
		{
		    for(int i=0;i<lstViewedUsers.size();i++)
		    {
			USRVO newUsrVO = new USRVO();
			newUsrVO.setUSER_ID(lstViewedUsers.get(i).getUSER_ID());
			newUsrVO.setUSER_GRP_ID(lstViewedUsers.get(i).getViewerUserId());
			oldUsrVO.setUSER_GRP_ID(lstViewedUsers.get(i).getViewerUserId());
			if(i==lstViewedUsers.size()-1)
			{
			    if(lstSelectedBranch!=null)
			    {
        			    for(int j=0;j<lstSelectedBranch.size();j++)
        			    {
        				if(j==0)
        				{
        				    auditBO.callAudit( oldUsrVO, newUsrVO ,lstViewedUsers.get(i).getAuditRefCO()); 
        				    oldUsrVO.setUSER_ID(lstViewedUsers.get(i).getUSER_ID());
        				}
        				newUsrVO.setEMP_BRANCH_CODE(lstSelectedBranch.get(j).getEMP_BRANCH_CODE());
        				if(j>0)
        				{
        				    oldUsrVO.setUSER_ID(lstViewedUsers.get(i).getUSER_ID());
        				}
        				auditBO.callAudit( oldUsrVO, newUsrVO ,lstViewedUsers.get(i).getAuditRefCO()); 
        			    }
			    }
			}
			else
			{
			    if(i>0)
			    {
				lstViewedUsers.get(i-1).getAuditRefCO().getAuditCO().getAuditDtlVO().get(i-1).setOLD_VALUE(null);
			    }
			    auditBO.callAudit( oldUsrVO, newUsrVO ,lstViewedUsers.get(i).getAuditRefCO()); 
			}
			
		    }
		    if(lstViewedUsers.get(0).getAuditRefCO().getAuditCO()!=null)
		    {
			auditBO.insertAudit(lstViewedUsers.get(0).getAuditRefCO()); 
		    }
		}
	    	
	    	
	
	}
	
	public List<USER_ACCESSCO> retAllViewedUserList(UsrSC usrSC)throws BaseException 
	{
		return snapShotDAO.retAllViewedUserList(usrSC);
	}
	public void deleteUserAccess(USER_ACCESSCO userAccessCO)throws BaseException 
	{
		snapShotDAO.deleteUserAccess(userAccessCO);
	}
	public List<USER_ACCESSCO> retBranchGridList(UsrSC usrSC)throws BaseException 
	{
		return snapShotDAO.retBranchGridList(usrSC);
	}

	public int retBranchGridListCount(UsrSC usrSC)throws BaseException
	{
		return snapShotDAO.retBranchGridListCount(usrSC);
	}
	
	public List<USER_ACCESSCO> retSelectedBranchList(UsrSC usrSC)throws BaseException
	{
		return snapShotDAO.retSelectedBranchList(usrSC);
	}

    /**
     * method to read the report's snapshot from the services repository
     */
    public Object[] readSnapshotFromRepository(String repName, String ext) throws BaseException
    {
	Object[] objLst = new Object[2];
	try
	{
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    String fileName = repositoryPath + "/" + RepConstantsCommon.SNAPSHOT_LOCATION + "/" + repName ;
	    byte[] reportBytes = null;
	    File repFile = new PathFileSecure(fileName+ "." + ext);
	    if(repFile.exists())
	    {
		reportBytes = FileUtil.readFileBytes(fileName+ "." + ext);
		objLst[0] = reportBytes;
		objLst[1] = ext;
	    }
	    else
	    {
		repFile = new PathFileSecure(fileName + "."+ConstantsCommon.ZIP_EXT);
		if(repFile.exists())
		{
		    reportBytes = FileUtil.readFileBytes(fileName + "."+ConstantsCommon.ZIP_EXT);
		    objLst[0] = reportBytes;
		    objLst[1] = ConstantsCommon.ZIP_EXT;
		}
	    }
	}
	catch(Exception e)
	{
	    throw new BOException(e.getMessage(),e);
	}
	return objLst;
    }

    @Override
    public int retUserAccessSnpCount(IRP_SNAPSHOTSSC snpSC) throws BaseException
    {
	return snapShotDAO.retUserAccessSnpCount(snpSC);
    }

    @Override
    public List<IRP_SNAPSHOTSCO> retUserAccessSnpList(IRP_SNAPSHOTSSC snpSC) throws BaseException
    {
	return snapShotDAO.retUserAccessSnpList(snpSC);
    }
    
}
