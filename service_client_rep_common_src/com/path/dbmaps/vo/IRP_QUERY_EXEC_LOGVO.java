package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class IRP_QUERY_EXEC_LOGVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.QUERY_EXEC_LOG_ID
     */
    private BigDecimal QUERY_EXEC_LOG_ID;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.USER_ID
     */
    private String USER_ID;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.COMP_CODE
     */
    private BigDecimal COMP_CODE;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.BRANCH_CODE
     */
    private BigDecimal BRANCH_CODE;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.APP_NAME
     */
    private String APP_NAME;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.HTTP_SESSION
     */
    private String HTTP_SESSION;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.EXEC_START_TIME
     */
    private Date EXEC_START_TIME;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.EXEC_END_TIME
     */
    private Date EXEC_END_TIME;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.QUERY_EXCEPTION
     */
    private String QUERY_EXCEPTION;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.QUERY_ID
     */
    private BigDecimal QUERY_ID;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.SOURCE_ID
     */
    private BigDecimal SOURCE_ID;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.COUNT_LIST_YN
     */
    private String COUNT_LIST_YN;

    /**
     * This field corresponds to the database column IRP_QUERY_EXEC_LOG.CONN_ID
     */
    private BigDecimal CONN_ID;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.QUERY_EXEC_LOG_ID
     *
     * @return the value of IRP_QUERY_EXEC_LOG.QUERY_EXEC_LOG_ID
     */
    public BigDecimal getQUERY_EXEC_LOG_ID() {
        return QUERY_EXEC_LOG_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.QUERY_EXEC_LOG_ID
     *
     * @param QUERY_EXEC_LOG_ID the value for IRP_QUERY_EXEC_LOG.QUERY_EXEC_LOG_ID
     */
    public void setQUERY_EXEC_LOG_ID(BigDecimal QUERY_EXEC_LOG_ID) {
        this.QUERY_EXEC_LOG_ID = QUERY_EXEC_LOG_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.USER_ID
     *
     * @return the value of IRP_QUERY_EXEC_LOG.USER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.USER_ID
     *
     * @param USER_ID the value for IRP_QUERY_EXEC_LOG.USER_ID
     */
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID == null ? null : USER_ID.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.COMP_CODE
     *
     * @return the value of IRP_QUERY_EXEC_LOG.COMP_CODE
     */
    public BigDecimal getCOMP_CODE() {
        return COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.COMP_CODE
     *
     * @param COMP_CODE the value for IRP_QUERY_EXEC_LOG.COMP_CODE
     */
    public void setCOMP_CODE(BigDecimal COMP_CODE) {
        this.COMP_CODE = COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.BRANCH_CODE
     *
     * @return the value of IRP_QUERY_EXEC_LOG.BRANCH_CODE
     */
    public BigDecimal getBRANCH_CODE() {
        return BRANCH_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.BRANCH_CODE
     *
     * @param BRANCH_CODE the value for IRP_QUERY_EXEC_LOG.BRANCH_CODE
     */
    public void setBRANCH_CODE(BigDecimal BRANCH_CODE) {
        this.BRANCH_CODE = BRANCH_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.APP_NAME
     *
     * @return the value of IRP_QUERY_EXEC_LOG.APP_NAME
     */
    public String getAPP_NAME() {
        return APP_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.APP_NAME
     *
     * @param APP_NAME the value for IRP_QUERY_EXEC_LOG.APP_NAME
     */
    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME == null ? null : APP_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.HTTP_SESSION
     *
     * @return the value of IRP_QUERY_EXEC_LOG.HTTP_SESSION
     */
    public String getHTTP_SESSION() {
        return HTTP_SESSION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.HTTP_SESSION
     *
     * @param HTTP_SESSION the value for IRP_QUERY_EXEC_LOG.HTTP_SESSION
     */
    public void setHTTP_SESSION(String HTTP_SESSION) {
        this.HTTP_SESSION = HTTP_SESSION == null ? null : HTTP_SESSION.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.EXEC_START_TIME
     *
     * @return the value of IRP_QUERY_EXEC_LOG.EXEC_START_TIME
     */
    public Date getEXEC_START_TIME() {
        return EXEC_START_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.EXEC_START_TIME
     *
     * @param EXEC_START_TIME the value for IRP_QUERY_EXEC_LOG.EXEC_START_TIME
     */
    public void setEXEC_START_TIME(Date EXEC_START_TIME) {
        this.EXEC_START_TIME = EXEC_START_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.EXEC_END_TIME
     *
     * @return the value of IRP_QUERY_EXEC_LOG.EXEC_END_TIME
     */
    public Date getEXEC_END_TIME() {
        return EXEC_END_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.EXEC_END_TIME
     *
     * @param EXEC_END_TIME the value for IRP_QUERY_EXEC_LOG.EXEC_END_TIME
     */
    public void setEXEC_END_TIME(Date EXEC_END_TIME) {
        this.EXEC_END_TIME = EXEC_END_TIME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.QUERY_EXCEPTION
     *
     * @return the value of IRP_QUERY_EXEC_LOG.QUERY_EXCEPTION
     */
    public String getQUERY_EXCEPTION() {
        return QUERY_EXCEPTION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.QUERY_EXCEPTION
     *
     * @param QUERY_EXCEPTION the value for IRP_QUERY_EXEC_LOG.QUERY_EXCEPTION
     */
    public void setQUERY_EXCEPTION(String QUERY_EXCEPTION) {
        this.QUERY_EXCEPTION = QUERY_EXCEPTION == null ? null : QUERY_EXCEPTION.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.QUERY_ID
     *
     * @return the value of IRP_QUERY_EXEC_LOG.QUERY_ID
     */
    public BigDecimal getQUERY_ID() {
        return QUERY_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.QUERY_ID
     *
     * @param QUERY_ID the value for IRP_QUERY_EXEC_LOG.QUERY_ID
     */
    public void setQUERY_ID(BigDecimal QUERY_ID) {
        this.QUERY_ID = QUERY_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.SOURCE_ID
     *
     * @return the value of IRP_QUERY_EXEC_LOG.SOURCE_ID
     */
    public BigDecimal getSOURCE_ID() {
        return SOURCE_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.SOURCE_ID
     *
     * @param SOURCE_ID the value for IRP_QUERY_EXEC_LOG.SOURCE_ID
     */
    public void setSOURCE_ID(BigDecimal SOURCE_ID) {
        this.SOURCE_ID = SOURCE_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.COUNT_LIST_YN
     *
     * @return the value of IRP_QUERY_EXEC_LOG.COUNT_LIST_YN
     */
    public String getCOUNT_LIST_YN() {
        return COUNT_LIST_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.COUNT_LIST_YN
     *
     * @param COUNT_LIST_YN the value for IRP_QUERY_EXEC_LOG.COUNT_LIST_YN
     */
    public void setCOUNT_LIST_YN(String COUNT_LIST_YN) {
        this.COUNT_LIST_YN = COUNT_LIST_YN == null ? null : COUNT_LIST_YN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_QUERY_EXEC_LOG.CONN_ID
     *
     * @return the value of IRP_QUERY_EXEC_LOG.CONN_ID
     */
    public BigDecimal getCONN_ID() {
        return CONN_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_QUERY_EXEC_LOG.CONN_ID
     *
     * @param CONN_ID the value for IRP_QUERY_EXEC_LOG.CONN_ID
     */
    public void setCONN_ID(BigDecimal CONN_ID) {
        this.CONN_ID = CONN_ID;
    }
}