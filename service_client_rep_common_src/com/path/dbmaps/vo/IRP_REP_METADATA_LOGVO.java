package com.path.dbmaps.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.path.lib.vo.BaseVO;

public class IRP_REP_METADATA_LOGVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_REP_METADATA_LOG.LOG_ID
     */
    private BigDecimal LOG_ID;

    /**
     * This field corresponds to the database column IRP_REP_METADATA_LOG.REPORT_ID
     */
    private BigDecimal REPORT_ID;

    /**
     * This field corresponds to the database column IRP_REP_METADATA_LOG.REP_FILE_NAME
     */
    private String REP_FILE_NAME;

    /**
     * This field corresponds to the database column IRP_REP_METADATA_LOG.LOG_DATE
     */
    private Date LOG_DATE;

    /**
     * This field corresponds to the database column IRP_REP_METADATA_LOG.PROG_REF
     */
    private String PROG_REF;

    /**
     * This field corresponds to the database column IRP_REP_METADATA_LOG.REPORT_ARGUMENTS
     */
    private String REPORT_ARGUMENTS;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_METADATA_LOG.LOG_ID
     *
     * @return the value of IRP_REP_METADATA_LOG.LOG_ID
     */
    public BigDecimal getLOG_ID() {
        return LOG_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_METADATA_LOG.LOG_ID
     *
     * @param LOG_ID the value for IRP_REP_METADATA_LOG.LOG_ID
     */
    public void setLOG_ID(BigDecimal LOG_ID) {
        this.LOG_ID = LOG_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_METADATA_LOG.REPORT_ID
     *
     * @return the value of IRP_REP_METADATA_LOG.REPORT_ID
     */
    public BigDecimal getREPORT_ID() {
        return REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_METADATA_LOG.REPORT_ID
     *
     * @param REPORT_ID the value for IRP_REP_METADATA_LOG.REPORT_ID
     */
    public void setREPORT_ID(BigDecimal REPORT_ID) {
        this.REPORT_ID = REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_METADATA_LOG.REP_FILE_NAME
     *
     * @return the value of IRP_REP_METADATA_LOG.REP_FILE_NAME
     */
    public String getREP_FILE_NAME() {
        return REP_FILE_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_METADATA_LOG.REP_FILE_NAME
     *
     * @param REP_FILE_NAME the value for IRP_REP_METADATA_LOG.REP_FILE_NAME
     */
    public void setREP_FILE_NAME(String REP_FILE_NAME) {
        this.REP_FILE_NAME = REP_FILE_NAME == null ? null : REP_FILE_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_METADATA_LOG.LOG_DATE
     *
     * @return the value of IRP_REP_METADATA_LOG.LOG_DATE
     */
    public Date getLOG_DATE() {
        return LOG_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_METADATA_LOG.LOG_DATE
     *
     * @param LOG_DATE the value for IRP_REP_METADATA_LOG.LOG_DATE
     */
    public void setLOG_DATE(Date LOG_DATE) {
        this.LOG_DATE = LOG_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_METADATA_LOG.PROG_REF
     *
     * @return the value of IRP_REP_METADATA_LOG.PROG_REF
     */
    public String getPROG_REF() {
        return PROG_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_METADATA_LOG.PROG_REF
     *
     * @param PROG_REF the value for IRP_REP_METADATA_LOG.PROG_REF
     */
    public void setPROG_REF(String PROG_REF) {
        this.PROG_REF = PROG_REF == null ? null : PROG_REF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_METADATA_LOG.REPORT_ARGUMENTS
     *
     * @return the value of IRP_REP_METADATA_LOG.REPORT_ARGUMENTS
     */
    public String getREPORT_ARGUMENTS() {
        return REPORT_ARGUMENTS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_METADATA_LOG.REPORT_ARGUMENTS
     *
     * @param REPORT_ARGUMENTS the value for IRP_REP_METADATA_LOG.REPORT_ARGUMENTS
     */
    public void setREPORT_ARGUMENTS(String REPORT_ARGUMENTS) {
        this.REPORT_ARGUMENTS = REPORT_ARGUMENTS == null ? null : REPORT_ARGUMENTS.trim();
    }
}