package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class IRP_SNAPSHOTS_LOGSVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_SNAPSHOTS_LOGS.SNAPSHOT_ID
     */
    private BigDecimal SNAPSHOT_ID;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS_LOGS.ARCHIVE_DATE
     */
    private Date ARCHIVE_DATE;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS_LOGS.STATUS
     */
    private String STATUS;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS_LOGS.SNAPSHOT_ID
     *
     * @return the value of IRP_SNAPSHOTS_LOGS.SNAPSHOT_ID
     */
    public BigDecimal getSNAPSHOT_ID() {
        return SNAPSHOT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS_LOGS.SNAPSHOT_ID
     *
     * @param SNAPSHOT_ID the value for IRP_SNAPSHOTS_LOGS.SNAPSHOT_ID
     */
    public void setSNAPSHOT_ID(BigDecimal SNAPSHOT_ID) {
        this.SNAPSHOT_ID = SNAPSHOT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS_LOGS.ARCHIVE_DATE
     *
     * @return the value of IRP_SNAPSHOTS_LOGS.ARCHIVE_DATE
     */
    public Date getARCHIVE_DATE() {
        return ARCHIVE_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS_LOGS.ARCHIVE_DATE
     *
     * @param ARCHIVE_DATE the value for IRP_SNAPSHOTS_LOGS.ARCHIVE_DATE
     */
    public void setARCHIVE_DATE(Date ARCHIVE_DATE) {
        this.ARCHIVE_DATE = ARCHIVE_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS_LOGS.STATUS
     *
     * @return the value of IRP_SNAPSHOTS_LOGS.STATUS
     */
    public String getSTATUS() {
        return STATUS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS_LOGS.STATUS
     *
     * @param STATUS the value for IRP_SNAPSHOTS_LOGS.STATUS
     */
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS == null ? null : STATUS.trim();
    }
}