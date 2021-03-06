package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class IRP_SNAPSHOTSVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.SNAPSHOT_ID
     */
    private BigDecimal SNAPSHOT_ID;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.EXECUTION_DATE
     */
    private Date EXECUTION_DATE;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.REPORT_NAME
     */
    private String REPORT_NAME;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.REPORT_FORMAT
     */
    private String REPORT_FORMAT;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.EXECUTED_BY
     */
    private String EXECUTED_BY;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.FILE_NAME
     */
    private String FILE_NAME;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.IS_DB
     */
    private BigDecimal IS_DB;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.CONN_ID
     */
    private BigDecimal CONN_ID;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.BRANCH_CODE
     */
    private BigDecimal BRANCH_CODE;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.COMP_CODE
     */
    private BigDecimal COMP_CODE;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.SNAPSHOT_REF
     */
    private BigDecimal SNAPSHOT_REF;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.APP_NAME
     */
    private String APP_NAME;

    /**
     * This field corresponds to the database column IRP_SNAPSHOTS.REPORT_CONTENT
     */
    private byte[] REPORT_CONTENT;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.SNAPSHOT_ID
     *
     * @return the value of IRP_SNAPSHOTS.SNAPSHOT_ID
     */
    public BigDecimal getSNAPSHOT_ID() {
        return SNAPSHOT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.SNAPSHOT_ID
     *
     * @param SNAPSHOT_ID the value for IRP_SNAPSHOTS.SNAPSHOT_ID
     */
    public void setSNAPSHOT_ID(BigDecimal SNAPSHOT_ID) {
        this.SNAPSHOT_ID = SNAPSHOT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.EXECUTION_DATE
     *
     * @return the value of IRP_SNAPSHOTS.EXECUTION_DATE
     */
    public Date getEXECUTION_DATE() {
        return EXECUTION_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.EXECUTION_DATE
     *
     * @param EXECUTION_DATE the value for IRP_SNAPSHOTS.EXECUTION_DATE
     */
    public void setEXECUTION_DATE(Date EXECUTION_DATE) {
        this.EXECUTION_DATE = EXECUTION_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.REPORT_NAME
     *
     * @return the value of IRP_SNAPSHOTS.REPORT_NAME
     */
    public String getREPORT_NAME() {
        return REPORT_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.REPORT_NAME
     *
     * @param REPORT_NAME the value for IRP_SNAPSHOTS.REPORT_NAME
     */
    public void setREPORT_NAME(String REPORT_NAME) {
        this.REPORT_NAME = REPORT_NAME == null ? null : REPORT_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.REPORT_FORMAT
     *
     * @return the value of IRP_SNAPSHOTS.REPORT_FORMAT
     */
    public String getREPORT_FORMAT() {
        return REPORT_FORMAT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.REPORT_FORMAT
     *
     * @param REPORT_FORMAT the value for IRP_SNAPSHOTS.REPORT_FORMAT
     */
    public void setREPORT_FORMAT(String REPORT_FORMAT) {
        this.REPORT_FORMAT = REPORT_FORMAT == null ? null : REPORT_FORMAT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.EXECUTED_BY
     *
     * @return the value of IRP_SNAPSHOTS.EXECUTED_BY
     */
    public String getEXECUTED_BY() {
        return EXECUTED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.EXECUTED_BY
     *
     * @param EXECUTED_BY the value for IRP_SNAPSHOTS.EXECUTED_BY
     */
    public void setEXECUTED_BY(String EXECUTED_BY) {
        this.EXECUTED_BY = EXECUTED_BY == null ? null : EXECUTED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.FILE_NAME
     *
     * @return the value of IRP_SNAPSHOTS.FILE_NAME
     */
    public String getFILE_NAME() {
        return FILE_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.FILE_NAME
     *
     * @param FILE_NAME the value for IRP_SNAPSHOTS.FILE_NAME
     */
    public void setFILE_NAME(String FILE_NAME) {
        this.FILE_NAME = FILE_NAME == null ? null : FILE_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.IS_DB
     *
     * @return the value of IRP_SNAPSHOTS.IS_DB
     */
    public BigDecimal getIS_DB() {
        return IS_DB;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.IS_DB
     *
     * @param IS_DB the value for IRP_SNAPSHOTS.IS_DB
     */
    public void setIS_DB(BigDecimal IS_DB) {
        this.IS_DB = IS_DB;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.CONN_ID
     *
     * @return the value of IRP_SNAPSHOTS.CONN_ID
     */
    public BigDecimal getCONN_ID() {
        return CONN_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.CONN_ID
     *
     * @param CONN_ID the value for IRP_SNAPSHOTS.CONN_ID
     */
    public void setCONN_ID(BigDecimal CONN_ID) {
        this.CONN_ID = CONN_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.BRANCH_CODE
     *
     * @return the value of IRP_SNAPSHOTS.BRANCH_CODE
     */
    public BigDecimal getBRANCH_CODE() {
        return BRANCH_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.BRANCH_CODE
     *
     * @param BRANCH_CODE the value for IRP_SNAPSHOTS.BRANCH_CODE
     */
    public void setBRANCH_CODE(BigDecimal BRANCH_CODE) {
        this.BRANCH_CODE = BRANCH_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.COMP_CODE
     *
     * @return the value of IRP_SNAPSHOTS.COMP_CODE
     */
    public BigDecimal getCOMP_CODE() {
        return COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.COMP_CODE
     *
     * @param COMP_CODE the value for IRP_SNAPSHOTS.COMP_CODE
     */
    public void setCOMP_CODE(BigDecimal COMP_CODE) {
        this.COMP_CODE = COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.SNAPSHOT_REF
     *
     * @return the value of IRP_SNAPSHOTS.SNAPSHOT_REF
     */
    public BigDecimal getSNAPSHOT_REF() {
        return SNAPSHOT_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.SNAPSHOT_REF
     *
     * @param SNAPSHOT_REF the value for IRP_SNAPSHOTS.SNAPSHOT_REF
     */
    public void setSNAPSHOT_REF(BigDecimal SNAPSHOT_REF) {
        this.SNAPSHOT_REF = SNAPSHOT_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.APP_NAME
     *
     * @return the value of IRP_SNAPSHOTS.APP_NAME
     */
    public String getAPP_NAME() {
        return APP_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.APP_NAME
     *
     * @param APP_NAME the value for IRP_SNAPSHOTS.APP_NAME
     */
    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME == null ? null : APP_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_SNAPSHOTS.REPORT_CONTENT
     *
     * @return the value of IRP_SNAPSHOTS.REPORT_CONTENT
     */
    public byte[] getREPORT_CONTENT() {
        return REPORT_CONTENT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_SNAPSHOTS.REPORT_CONTENT
     *
     * @param REPORT_CONTENT the value for IRP_SNAPSHOTS.REPORT_CONTENT
     */
    public void setREPORT_CONTENT(byte[] REPORT_CONTENT) {
        this.REPORT_CONTENT = REPORT_CONTENT;
    }
}