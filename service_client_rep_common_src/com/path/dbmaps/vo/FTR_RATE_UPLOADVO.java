package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class FTR_RATE_UPLOADVO extends BaseVO {
    /**
     * This field corresponds to the database column FTR_RATE_UPLOAD.COMP_CODE
     */
    private BigDecimal COMP_CODE;

    /**
     * This field corresponds to the database column FTR_RATE_UPLOAD.CURRENCY_CODE
     */
    private BigDecimal CURRENCY_CODE;

    /**
     * This field corresponds to the database column FTR_RATE_UPLOAD.VALUE_DATE
     */
    private Date VALUE_DATE;

    /**
     * This field corresponds to the database column FTR_RATE_UPLOAD.DISC_FACTOR
     */
    private BigDecimal DISC_FACTOR;

    /**
     * This field corresponds to the database column FTR_RATE_UPLOAD.RATE
     */
    private BigDecimal RATE;

    /**
     * This field corresponds to the database column FTR_RATE_UPLOAD.ADJUST_RATE
     */
    private BigDecimal ADJUST_RATE;

    /**
     * This field corresponds to the database column FTR_RATE_UPLOAD.NET_RATE
     */
    private BigDecimal NET_RATE;

    /**
     * This field corresponds to the database column FTR_RATE_UPLOAD.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_RATE_UPLOAD.COMP_CODE
     *
     * @return the value of FTR_RATE_UPLOAD.COMP_CODE
     */
    public BigDecimal getCOMP_CODE() {
        return COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_RATE_UPLOAD.COMP_CODE
     *
     * @param COMP_CODE the value for FTR_RATE_UPLOAD.COMP_CODE
     */
    public void setCOMP_CODE(BigDecimal COMP_CODE) {
        this.COMP_CODE = COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_RATE_UPLOAD.CURRENCY_CODE
     *
     * @return the value of FTR_RATE_UPLOAD.CURRENCY_CODE
     */
    public BigDecimal getCURRENCY_CODE() {
        return CURRENCY_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_RATE_UPLOAD.CURRENCY_CODE
     *
     * @param CURRENCY_CODE the value for FTR_RATE_UPLOAD.CURRENCY_CODE
     */
    public void setCURRENCY_CODE(BigDecimal CURRENCY_CODE) {
        this.CURRENCY_CODE = CURRENCY_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_RATE_UPLOAD.VALUE_DATE
     *
     * @return the value of FTR_RATE_UPLOAD.VALUE_DATE
     */
    public Date getVALUE_DATE() {
        return VALUE_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_RATE_UPLOAD.VALUE_DATE
     *
     * @param VALUE_DATE the value for FTR_RATE_UPLOAD.VALUE_DATE
     */
    public void setVALUE_DATE(Date VALUE_DATE) {
        this.VALUE_DATE = VALUE_DATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_RATE_UPLOAD.DISC_FACTOR
     *
     * @return the value of FTR_RATE_UPLOAD.DISC_FACTOR
     */
    public BigDecimal getDISC_FACTOR() {
        return DISC_FACTOR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_RATE_UPLOAD.DISC_FACTOR
     *
     * @param DISC_FACTOR the value for FTR_RATE_UPLOAD.DISC_FACTOR
     */
    public void setDISC_FACTOR(BigDecimal DISC_FACTOR) {
        this.DISC_FACTOR = DISC_FACTOR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_RATE_UPLOAD.RATE
     *
     * @return the value of FTR_RATE_UPLOAD.RATE
     */
    public BigDecimal getRATE() {
        return RATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_RATE_UPLOAD.RATE
     *
     * @param RATE the value for FTR_RATE_UPLOAD.RATE
     */
    public void setRATE(BigDecimal RATE) {
        this.RATE = RATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_RATE_UPLOAD.ADJUST_RATE
     *
     * @return the value of FTR_RATE_UPLOAD.ADJUST_RATE
     */
    public BigDecimal getADJUST_RATE() {
        return ADJUST_RATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_RATE_UPLOAD.ADJUST_RATE
     *
     * @param ADJUST_RATE the value for FTR_RATE_UPLOAD.ADJUST_RATE
     */
    public void setADJUST_RATE(BigDecimal ADJUST_RATE) {
        this.ADJUST_RATE = ADJUST_RATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_RATE_UPLOAD.NET_RATE
     *
     * @return the value of FTR_RATE_UPLOAD.NET_RATE
     */
    public BigDecimal getNET_RATE() {
        return NET_RATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_RATE_UPLOAD.NET_RATE
     *
     * @param NET_RATE the value for FTR_RATE_UPLOAD.NET_RATE
     */
    public void setNET_RATE(BigDecimal NET_RATE) {
        this.NET_RATE = NET_RATE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_RATE_UPLOAD.DATE_UPDATED
     *
     * @return the value of FTR_RATE_UPLOAD.DATE_UPDATED
     */
    public Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_RATE_UPLOAD.DATE_UPDATED
     *
     * @param DATE_UPDATED the value for FTR_RATE_UPLOAD.DATE_UPDATED
     */
    public void setDATE_UPDATED(Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }
}