package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class IRP_REPORT_SCHED_LOGVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.REPORT_ID
     */
    private BigDecimal REPORT_ID;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.SCHEDULED_DATE
     */
    private Date SCHEDULED_DATE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.SCHED_ID
     */
    private BigDecimal SCHED_ID;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.START_DATE
     */
    private Date START_DATE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.END_DATE
     */
    private Date END_DATE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.STATUS
     */
    private String STATUS;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.REMARKS
     */
    private String REMARKS;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.REPORT_FORMAT
     */
    private String REPORT_FORMAT;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.SNAPSHOT_ID
     */
    private BigDecimal SNAPSHOT_ID;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_LOG.SAVE_DATA_TYPE
     */
    private BigDecimal SAVE_DATA_TYPE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.REPORT_ID
     *
     * @return the value of IRP_REPORT_SCHED_LOG.REPORT_ID
     */
    public BigDecimal getREPORT_ID() {
        return REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.REPORT_ID
     *
     * @param REPORT_ID the value for IRP_REPORT_SCHED_LOG.REPORT_ID
     */
    public void setREPORT_ID(BigDecimal REPORT_ID) {
        this.REPORT_ID = REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.SCHEDULED_DATE
     *
     * @return the value of IRP_REPORT_SCHED_LOG.SCHEDULED_DATE
     */
    public Date getSCHEDULED_DATE() {
        return SCHEDULED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.SCHEDULED_DATE
     *
     * @param SCHEDULED_DATE the value for IRP_REPORT_SCHED_LOG.SCHEDULED_DATE
     */
    public void setSCHEDULED_DATE(Date SCHEDULED_DATE) {
        this.SCHEDULED_DATE = SCHEDULED_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.SCHED_ID
     *
     * @return the value of IRP_REPORT_SCHED_LOG.SCHED_ID
     */
    public BigDecimal getSCHED_ID() {
        return SCHED_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.SCHED_ID
     *
     * @param SCHED_ID the value for IRP_REPORT_SCHED_LOG.SCHED_ID
     */
    public void setSCHED_ID(BigDecimal SCHED_ID) {
        this.SCHED_ID = SCHED_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.START_DATE
     *
     * @return the value of IRP_REPORT_SCHED_LOG.START_DATE
     */
    public Date getSTART_DATE() {
        return START_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.START_DATE
     *
     * @param START_DATE the value for IRP_REPORT_SCHED_LOG.START_DATE
     */
    public void setSTART_DATE(Date START_DATE) {
        this.START_DATE = START_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.END_DATE
     *
     * @return the value of IRP_REPORT_SCHED_LOG.END_DATE
     */
    public Date getEND_DATE() {
        return END_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.END_DATE
     *
     * @param END_DATE the value for IRP_REPORT_SCHED_LOG.END_DATE
     */
    public void setEND_DATE(Date END_DATE) {
        this.END_DATE = END_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.STATUS
     *
     * @return the value of IRP_REPORT_SCHED_LOG.STATUS
     */
    public String getSTATUS() {
        return STATUS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.STATUS
     *
     * @param STATUS the value for IRP_REPORT_SCHED_LOG.STATUS
     */
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS == null ? null : STATUS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.REMARKS
     *
     * @return the value of IRP_REPORT_SCHED_LOG.REMARKS
     */
    public String getREMARKS() {
        return REMARKS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.REMARKS
     *
     * @param REMARKS the value for IRP_REPORT_SCHED_LOG.REMARKS
     */
    public void setREMARKS(String REMARKS) {
        this.REMARKS = REMARKS == null ? null : REMARKS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.REPORT_FORMAT
     *
     * @return the value of IRP_REPORT_SCHED_LOG.REPORT_FORMAT
     */
    public String getREPORT_FORMAT() {
        return REPORT_FORMAT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.REPORT_FORMAT
     *
     * @param REPORT_FORMAT the value for IRP_REPORT_SCHED_LOG.REPORT_FORMAT
     */
    public void setREPORT_FORMAT(String REPORT_FORMAT) {
        this.REPORT_FORMAT = REPORT_FORMAT == null ? null : REPORT_FORMAT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.SNAPSHOT_ID
     *
     * @return the value of IRP_REPORT_SCHED_LOG.SNAPSHOT_ID
     */
    public BigDecimal getSNAPSHOT_ID() {
        return SNAPSHOT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.SNAPSHOT_ID
     *
     * @param SNAPSHOT_ID the value for IRP_REPORT_SCHED_LOG.SNAPSHOT_ID
     */
    public void setSNAPSHOT_ID(BigDecimal SNAPSHOT_ID) {
        this.SNAPSHOT_ID = SNAPSHOT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_LOG.SAVE_DATA_TYPE
     *
     * @return the value of IRP_REPORT_SCHED_LOG.SAVE_DATA_TYPE
     */
    public BigDecimal getSAVE_DATA_TYPE() {
        return SAVE_DATA_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_LOG.SAVE_DATA_TYPE
     *
     * @param SAVE_DATA_TYPE the value for IRP_REPORT_SCHED_LOG.SAVE_DATA_TYPE
     */
    public void setSAVE_DATA_TYPE(BigDecimal SAVE_DATA_TYPE) {
        this.SAVE_DATA_TYPE = SAVE_DATA_TYPE;
    }
}