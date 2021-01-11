package com.path.dbmaps.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.path.lib.vo.BaseVO;

public class IRP_FILE_DETVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_FILE_DET.FILE_ID
     */
    private BigDecimal FILE_ID;

    /**
     * This field corresponds to the database column IRP_FILE_DET.LINE_NO
     */
    private BigDecimal LINE_NO;

    /**
     * This field corresponds to the database column IRP_FILE_DET.APP_NAME
     */
    private String APP_NAME;

    /**
     * This field corresponds to the database column IRP_FILE_DET.PROG_REF
     */
    private String PROG_REF;

    /**
     * This field corresponds to the database column IRP_FILE_DET.SUB_FILE_NAME
     */
    private String SUB_FILE_NAME;

    /**
     * This field corresponds to the database column IRP_FILE_DET.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FILE_DET.FILE_ID
     *
     * @return the value of IRP_FILE_DET.FILE_ID
     */
    public BigDecimal getFILE_ID() {
        return FILE_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FILE_DET.FILE_ID
     *
     * @param FILE_ID the value for IRP_FILE_DET.FILE_ID
     */
    public void setFILE_ID(BigDecimal FILE_ID) {
        this.FILE_ID = FILE_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FILE_DET.LINE_NO
     *
     * @return the value of IRP_FILE_DET.LINE_NO
     */
    public BigDecimal getLINE_NO() {
        return LINE_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FILE_DET.LINE_NO
     *
     * @param LINE_NO the value for IRP_FILE_DET.LINE_NO
     */
    public void setLINE_NO(BigDecimal LINE_NO) {
        this.LINE_NO = LINE_NO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FILE_DET.APP_NAME
     *
     * @return the value of IRP_FILE_DET.APP_NAME
     */
    public String getAPP_NAME() {
        return APP_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FILE_DET.APP_NAME
     *
     * @param APP_NAME the value for IRP_FILE_DET.APP_NAME
     */
    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME == null ? null : APP_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FILE_DET.PROG_REF
     *
     * @return the value of IRP_FILE_DET.PROG_REF
     */
    public String getPROG_REF() {
        return PROG_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FILE_DET.PROG_REF
     *
     * @param PROG_REF the value for IRP_FILE_DET.PROG_REF
     */
    public void setPROG_REF(String PROG_REF) {
        this.PROG_REF = PROG_REF == null ? null : PROG_REF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FILE_DET.SUB_FILE_NAME
     *
     * @return the value of IRP_FILE_DET.SUB_FILE_NAME
     */
    public String getSUB_FILE_NAME() {
        return SUB_FILE_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FILE_DET.SUB_FILE_NAME
     *
     * @param SUB_FILE_NAME the value for IRP_FILE_DET.SUB_FILE_NAME
     */
    public void setSUB_FILE_NAME(String SUB_FILE_NAME) {
        this.SUB_FILE_NAME = SUB_FILE_NAME == null ? null : SUB_FILE_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FILE_DET.DATE_UPDATED
     *
     * @return the value of IRP_FILE_DET.DATE_UPDATED
     */
    public Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FILE_DET.DATE_UPDATED
     *
     * @param DATE_UPDATED the value for IRP_FILE_DET.DATE_UPDATED
     */
    public void setDATE_UPDATED(Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }
}