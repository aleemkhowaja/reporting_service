package com.path.vo.reporting.snapshots;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_SNAPSHOTSVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;

public class ARCHIVECO extends BaseVO
{
    
  private String selDate;
  private Date startRangeDate;
  private Date endRangeDate;
  private BigDecimal archiveLocation;
  private BigDecimal connection;
  private IRP_SNAPSHOTSVO irpSnapshotsVO = new IRP_SNAPSHOTSVO();
  private String userId;
  private List<String> userList;
  private Boolean reportSnapshot;
  private Boolean reportScheduler;	 
  private String dateRange;	 
  private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm; 
  
  

public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getBusinessHm()
{
   return businessHm;
}

public void setBusinessHm(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm)
{
    this.businessHm = businessHm;
}
  
public String getDateRange()
{
    return dateRange;
}
public void setDateRange(String dateRange)
{
    this.dateRange = dateRange;
}
public Boolean getReportSnapshot()
{
    return reportSnapshot;
}
public void setReportSnapshot(Boolean reportSnapshot)
{
    this.reportSnapshot = reportSnapshot;
}
public Boolean getReportScheduler()
{
    return reportScheduler;
}
public void setReportScheduler(Boolean reportScheduler)
{
    this.reportScheduler = reportScheduler;
}
public List<String> getUserList()
{
    return userList;
}
public void setUserList(List<String> userList)
{
    this.userList = userList;
}
public String getUserId()
{
    return userId;
}
public void setUserId(String userId)
{
    this.userId = userId;
}
public IRP_SNAPSHOTSVO getIrpSnapshotsVO()
{
    return irpSnapshotsVO;
}
public void setIrpSnapshotsVO(IRP_SNAPSHOTSVO irpSnapshotsVO)
{
    this.irpSnapshotsVO = irpSnapshotsVO;
}
public String getSelDate()
{
    return selDate;
}
public void setSelDate(String selDate)
{
    this.selDate = selDate;
}

public Date getStartRangeDate()
{
    return startRangeDate;
}
public void setStartRangeDate(Date startRangeDate)
{
    this.startRangeDate = startRangeDate;
}
public Date getEndRangeDate()
{
    return endRangeDate;
}
public void setEndRangeDate(Date endRangeDate)
{
    this.endRangeDate = endRangeDate;
}

public BigDecimal getArchiveLocation()
{
    return archiveLocation;
}
public void setArchiveLocation(BigDecimal archiveLocation)
{
    this.archiveLocation = archiveLocation;
}
public BigDecimal getConnection()
{
    return connection;
}
public void setConnection(BigDecimal connection)
{
    this.connection = connection;
}
  
  
  
}
