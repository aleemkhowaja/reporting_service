package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class FTR_TIME_BUCKETSVO extends BaseVO {
    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.COMP_CODE
     */
    private BigDecimal COMP_CODE;

    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.CURRENCY_CODE
     */
    private BigDecimal CURRENCY_CODE;

    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.LINE_NO
     */
    private BigDecimal LINE_NO;

    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.REP_REF
     */
    private String REP_REF;

    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.RATE_DESC
     */
    private String RATE_DESC;

    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.LIMIT
     */
    private BigDecimal LIMIT;

    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.RATE
     */
    private BigDecimal RATE;

    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.NO_OF_DAYS
     */
    private BigDecimal NO_OF_DAYS;

    /**
     * This field corresponds to the database column FTR_TIME_BUCKETS.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.COMP_CODE
     *
     * @return the value of FTR_TIME_BUCKETS.COMP_CODE
     */
    public BigDecimal getCOMP_CODE() {
        return COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.COMP_CODE
     *
     * @param COMP_CODE the value for FTR_TIME_BUCKETS.COMP_CODE
     */
    public void setCOMP_CODE(BigDecimal COMP_CODE) {
        this.COMP_CODE = COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.CURRENCY_CODE
     *
     * @return the value of FTR_TIME_BUCKETS.CURRENCY_CODE
     */
    public BigDecimal getCURRENCY_CODE() {
        return CURRENCY_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.CURRENCY_CODE
     *
     * @param CURRENCY_CODE the value for FTR_TIME_BUCKETS.CURRENCY_CODE
     */
    public void setCURRENCY_CODE(BigDecimal CURRENCY_CODE) {
        this.CURRENCY_CODE = CURRENCY_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.LINE_NO
     *
     * @return the value of FTR_TIME_BUCKETS.LINE_NO
     */
    public BigDecimal getLINE_NO() {
        return LINE_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.LINE_NO
     *
     * @param LINE_NO the value for FTR_TIME_BUCKETS.LINE_NO
     */
    public void setLINE_NO(BigDecimal LINE_NO) {
        this.LINE_NO = LINE_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.REP_REF
     *
     * @return the value of FTR_TIME_BUCKETS.REP_REF
     */
    public String getREP_REF() {
        return REP_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.REP_REF
     *
     * @param REP_REF the value for FTR_TIME_BUCKETS.REP_REF
     */
    public void setREP_REF(String REP_REF) {
        this.REP_REF = REP_REF == null ? null : REP_REF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.RATE_DESC
     *
     * @return the value of FTR_TIME_BUCKETS.RATE_DESC
     */
    public String getRATE_DESC() {
        return RATE_DESC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.RATE_DESC
     *
     * @param RATE_DESC the value for FTR_TIME_BUCKETS.RATE_DESC
     */
    public void setRATE_DESC(String RATE_DESC) {
        this.RATE_DESC = RATE_DESC == null ? null : RATE_DESC.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.LIMIT
     *
     * @return the value of FTR_TIME_BUCKETS.LIMIT
     */
    public BigDecimal getLIMIT() {
        return LIMIT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.LIMIT
     *
     * @param LIMIT the value for FTR_TIME_BUCKETS.LIMIT
     */
    public void setLIMIT(BigDecimal LIMIT) {
        this.LIMIT = LIMIT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.RATE
     *
     * @return the value of FTR_TIME_BUCKETS.RATE
     */
    public BigDecimal getRATE() {
        return RATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.RATE
     *
     * @param RATE the value for FTR_TIME_BUCKETS.RATE
     */
    public void setRATE(BigDecimal RATE) {
        this.RATE = RATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.NO_OF_DAYS
     *
     * @return the value of FTR_TIME_BUCKETS.NO_OF_DAYS
     */
    public BigDecimal getNO_OF_DAYS() {
        return NO_OF_DAYS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.NO_OF_DAYS
     *
     * @param NO_OF_DAYS the value for FTR_TIME_BUCKETS.NO_OF_DAYS
     */
    public void setNO_OF_DAYS(BigDecimal NO_OF_DAYS) {
        this.NO_OF_DAYS = NO_OF_DAYS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TIME_BUCKETS.DATE_UPDATED
     *
     * @return the value of FTR_TIME_BUCKETS.DATE_UPDATED
     */
    public Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TIME_BUCKETS.DATE_UPDATED
     *
     * @param DATE_UPDATED the value for FTR_TIME_BUCKETS.DATE_UPDATED
     */
    public void setDATE_UPDATED(Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }
}