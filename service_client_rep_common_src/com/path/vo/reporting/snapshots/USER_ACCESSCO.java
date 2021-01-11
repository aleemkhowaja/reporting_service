package com.path.vo.reporting.snapshots;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.path.dbmaps.vo.USRVO;
import com.path.vo.admin.user.UsrCO;
public class USER_ACCESSCO extends UsrCO 
{

    private BigDecimal eodViewer;
    private List<String> viewedUserLstId;
    private String viewedUsrId;
    private List<USRVO> lstUsrVO=new ArrayList <USRVO>();
    private Boolean intCheckBox;		
    private String viewerUserId;
    private Boolean branchCheckBox;
    private String BRANCH_DESC;
    private Date DATE_UPDATED;
    
    
    
    
    public Date getDATE_UPDATED()
    {
        return DATE_UPDATED;
    }
    public void setDATE_UPDATED(Date dATEUPDATED)
    {
        DATE_UPDATED = dATEUPDATED;
    }
    public String getBRANCH_DESC()
    {
        return BRANCH_DESC;
    }
    public void setBRANCH_DESC(String bRANCHDESC)
    {
        BRANCH_DESC = bRANCHDESC;
    }
    public BigDecimal getEodViewer()
    {
        return eodViewer;
    }
    public void setEodViewer(BigDecimal eodViewer)
    {
        this.eodViewer = eodViewer;
    }
    public List<String> getViewedUserLstId()
    {
        return viewedUserLstId;
    }
    public void setViewedUserLstId(List<String> viewedUserLstId)
    {
        this.viewedUserLstId = viewedUserLstId;
    }
    public String getViewedUsrId()
    {
        return viewedUsrId;
    }
    public void setViewedUsrId(String viewedUsrId)
    {
        this.viewedUsrId = viewedUsrId;
    }
    public List<USRVO> getLstUsrVO()
    {
        return lstUsrVO;
    }
    public void setLstUsrVO(List<USRVO> lstUsrVO)
    {
        this.lstUsrVO = lstUsrVO;
    }
   
    /**
     * @return the intCheckBox
     */
    public Boolean getIntCheckBox()
    {
        return intCheckBox;
    }
    /**
     * @param intCheckBox the intCheckBox to set
     */
    public void setIntCheckBox(Boolean intCheckBox)
    {
        this.intCheckBox = intCheckBox;
    }
    public String getViewerUserId()
    {
        return viewerUserId;
    }
    public void setViewerUserId(String viewerUserId)
    {
        this.viewerUserId = viewerUserId;
    }
    /**
     * @return the branchCheckBox
     */
    public Boolean getBranchCheckBox()
    {
        return branchCheckBox;
    }
    /**
     * @param branchCheckBox the branchCheckBox to set
     */
    public void setBranchCheckBox(Boolean branchCheckBox)
    {
        this.branchCheckBox = branchCheckBox;
    }
}
