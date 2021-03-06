package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class IRP_VERIFIED_REPORTSVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.BRANCH_CODE
     */
    private BigDecimal BRANCH_CODE;

    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.USER_ID
     */
    private String USER_ID;

    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.VERIFICATION_DATE
     */
    private Date VERIFICATION_DATE;

    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.REP_ID
     */
    private BigDecimal REP_ID;

    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.PAGES_NUMBER
     */
    private BigDecimal PAGES_NUMBER;

    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.REP_TITLE
     */
    private String REP_TITLE;

    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.PROG_REF
     */
    private String PROG_REF;

    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.APP_NAME
     */
    private String APP_NAME;

    /**
     * This field corresponds to the database column IRP_VERIFIED_REPORTS.VERIFICATION_ID
     */
    private BigDecimal VERIFICATION_ID;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.BRANCH_CODE
     *
     * @return the value of IRP_VERIFIED_REPORTS.BRANCH_CODE
     */
    public BigDecimal getBRANCH_CODE() {
        return BRANCH_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.BRANCH_CODE
     *
     * @param BRANCH_CODE the value for IRP_VERIFIED_REPORTS.BRANCH_CODE
     */
    public void setBRANCH_CODE(BigDecimal BRANCH_CODE) {
        this.BRANCH_CODE = BRANCH_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.USER_ID
     *
     * @return the value of IRP_VERIFIED_REPORTS.USER_ID
     */
    public String getUSER_ID() {
        return USER_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.USER_ID
     *
     * @param USER_ID the value for IRP_VERIFIED_REPORTS.USER_ID
     */
    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID == null ? null : USER_ID.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.VERIFICATION_DATE
     *
     * @return the value of IRP_VERIFIED_REPORTS.VERIFICATION_DATE
     */
    public Date getVERIFICATION_DATE() {
        return VERIFICATION_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.VERIFICATION_DATE
     *
     * @param VERIFICATION_DATE the value for IRP_VERIFIED_REPORTS.VERIFICATION_DATE
     */
    public void setVERIFICATION_DATE(Date VERIFICATION_DATE) {
        this.VERIFICATION_DATE = VERIFICATION_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.REP_ID
     *
     * @return the value of IRP_VERIFIED_REPORTS.REP_ID
     */
    public BigDecimal getREP_ID() {
        return REP_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.REP_ID
     *
     * @param REP_ID the value for IRP_VERIFIED_REPORTS.REP_ID
     */
    public void setREP_ID(BigDecimal REP_ID) {
        this.REP_ID = REP_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.PAGES_NUMBER
     *
     * @return the value of IRP_VERIFIED_REPORTS.PAGES_NUMBER
     */
    public BigDecimal getPAGES_NUMBER() {
        return PAGES_NUMBER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.PAGES_NUMBER
     *
     * @param PAGES_NUMBER the value for IRP_VERIFIED_REPORTS.PAGES_NUMBER
     */
    public void setPAGES_NUMBER(BigDecimal PAGES_NUMBER) {
        this.PAGES_NUMBER = PAGES_NUMBER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.REP_TITLE
     *
     * @return the value of IRP_VERIFIED_REPORTS.REP_TITLE
     */
    public String getREP_TITLE() {
        return REP_TITLE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.REP_TITLE
     *
     * @param REP_TITLE the value for IRP_VERIFIED_REPORTS.REP_TITLE
     */
    public void setREP_TITLE(String REP_TITLE) {
        this.REP_TITLE = REP_TITLE == null ? null : REP_TITLE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.PROG_REF
     *
     * @return the value of IRP_VERIFIED_REPORTS.PROG_REF
     */
    public String getPROG_REF() {
        return PROG_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.PROG_REF
     *
     * @param PROG_REF the value for IRP_VERIFIED_REPORTS.PROG_REF
     */
    public void setPROG_REF(String PROG_REF) {
        this.PROG_REF = PROG_REF == null ? null : PROG_REF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.APP_NAME
     *
     * @return the value of IRP_VERIFIED_REPORTS.APP_NAME
     */
    public String getAPP_NAME() {
        return APP_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.APP_NAME
     *
     * @param APP_NAME the value for IRP_VERIFIED_REPORTS.APP_NAME
     */
    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME == null ? null : APP_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_VERIFIED_REPORTS.VERIFICATION_ID
     *
     * @return the value of IRP_VERIFIED_REPORTS.VERIFICATION_ID
     */
    public BigDecimal getVERIFICATION_ID() {
        return VERIFICATION_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_VERIFIED_REPORTS.VERIFICATION_ID
     *
     * @param VERIFICATION_ID the value for IRP_VERIFIED_REPORTS.VERIFICATION_ID
     */
    public void setVERIFICATION_ID(BigDecimal VERIFICATION_ID) {
        this.VERIFICATION_ID = VERIFICATION_ID;
    }
}