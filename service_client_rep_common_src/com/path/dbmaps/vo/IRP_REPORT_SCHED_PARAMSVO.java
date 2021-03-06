package com.path.dbmaps.vo;

import java.math.BigDecimal;

import com.path.lib.vo.BaseVO;

public class IRP_REPORT_SCHED_PARAMSVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_PARAMS.PARAM_ID
     */
    private BigDecimal PARAM_ID;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_PARAMS.REPORT_ID
     */
    private BigDecimal REPORT_ID;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_PARAMS.REPORT_REF
     */
    private String REPORT_REF;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_PARAMS.SCHED_ID
     */
    private BigDecimal SCHED_ID;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_PARAMS.PARAM_NAME
     */
    private String PARAM_NAME;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_PARAMS.PARAM_VALUE
     */
    private String PARAM_VALUE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHED_PARAMS.PARAM_TYPE
     */
    private String PARAM_TYPE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_PARAMS.PARAM_ID
     *
     * @return the value of IRP_REPORT_SCHED_PARAMS.PARAM_ID
     */
    public BigDecimal getPARAM_ID() {
        return PARAM_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_PARAMS.PARAM_ID
     *
     * @param PARAM_ID the value for IRP_REPORT_SCHED_PARAMS.PARAM_ID
     */
    public void setPARAM_ID(BigDecimal PARAM_ID) {
        this.PARAM_ID = PARAM_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_PARAMS.REPORT_ID
     *
     * @return the value of IRP_REPORT_SCHED_PARAMS.REPORT_ID
     */
    public BigDecimal getREPORT_ID() {
        return REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_PARAMS.REPORT_ID
     *
     * @param REPORT_ID the value for IRP_REPORT_SCHED_PARAMS.REPORT_ID
     */
    public void setREPORT_ID(BigDecimal REPORT_ID) {
        this.REPORT_ID = REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_PARAMS.REPORT_REF
     *
     * @return the value of IRP_REPORT_SCHED_PARAMS.REPORT_REF
     */
    public String getREPORT_REF() {
        return REPORT_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_PARAMS.REPORT_REF
     *
     * @param REPORT_REF the value for IRP_REPORT_SCHED_PARAMS.REPORT_REF
     */
    public void setREPORT_REF(String REPORT_REF) {
        this.REPORT_REF = REPORT_REF == null ? null : REPORT_REF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_PARAMS.SCHED_ID
     *
     * @return the value of IRP_REPORT_SCHED_PARAMS.SCHED_ID
     */
    public BigDecimal getSCHED_ID() {
        return SCHED_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_PARAMS.SCHED_ID
     *
     * @param SCHED_ID the value for IRP_REPORT_SCHED_PARAMS.SCHED_ID
     */
    public void setSCHED_ID(BigDecimal SCHED_ID) {
        this.SCHED_ID = SCHED_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_PARAMS.PARAM_NAME
     *
     * @return the value of IRP_REPORT_SCHED_PARAMS.PARAM_NAME
     */
    public String getPARAM_NAME() {
        return PARAM_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_PARAMS.PARAM_NAME
     *
     * @param PARAM_NAME the value for IRP_REPORT_SCHED_PARAMS.PARAM_NAME
     */
    public void setPARAM_NAME(String PARAM_NAME) {
        this.PARAM_NAME = PARAM_NAME == null ? null : PARAM_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_PARAMS.PARAM_VALUE
     *
     * @return the value of IRP_REPORT_SCHED_PARAMS.PARAM_VALUE
     */
    public String getPARAM_VALUE() {
        return PARAM_VALUE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_PARAMS.PARAM_VALUE
     *
     * @param PARAM_VALUE the value for IRP_REPORT_SCHED_PARAMS.PARAM_VALUE
     */
    public void setPARAM_VALUE(String PARAM_VALUE) {
        this.PARAM_VALUE = PARAM_VALUE == null ? null : PARAM_VALUE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHED_PARAMS.PARAM_TYPE
     *
     * @return the value of IRP_REPORT_SCHED_PARAMS.PARAM_TYPE
     */
    public String getPARAM_TYPE() {
        return PARAM_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHED_PARAMS.PARAM_TYPE
     *
     * @param PARAM_TYPE the value for IRP_REPORT_SCHED_PARAMS.PARAM_TYPE
     */
    public void setPARAM_TYPE(String PARAM_TYPE) {
        this.PARAM_TYPE = PARAM_TYPE == null ? null : PARAM_TYPE.trim();
    }
}