package com.path.dbmaps.vo;

import java.math.BigDecimal;

import com.path.lib.vo.BaseVO;

public class IRP_REP_SCHED_MAIL_GROUP_BYVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_REP_SCHED_MAIL_GROUP_BY.SCHED_ID
     */
    private BigDecimal SCHED_ID;

    /**
     * This field corresponds to the database column IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_ID
     */
    private BigDecimal REPORT_ID;

    /**
     * This field corresponds to the database column IRP_REP_SCHED_MAIL_GROUP_BY.FIELD_ALIAS
     */
    private String FIELD_ALIAS;

    /**
     * This field corresponds to the database column IRP_REP_SCHED_MAIL_GROUP_BY.GRP_ORDER
     */
    private BigDecimal GRP_ORDER;

    /**
     * This field corresponds to the database column IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_REF
     */
    private String REPORT_REF;
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.SCHED_ID
     *
     * @return the value of IRP_REP_SCHED_MAIL_GROUP_BY.SCHED_ID
     */
    public BigDecimal getSCHED_ID() {
        return SCHED_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.SCHED_ID
     *
     * @param SCHED_ID the value for IRP_REP_SCHED_MAIL_GROUP_BY.SCHED_ID
     */
    public void setSCHED_ID(BigDecimal SCHED_ID) {
        this.SCHED_ID = SCHED_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_ID
     *
     * @return the value of IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_ID
     */
    public BigDecimal getREPORT_ID() {
        return REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_ID
     *
     * @param REPORT_ID the value for IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_ID
     */
    public void setREPORT_ID(BigDecimal REPORT_ID) {
        this.REPORT_ID = REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.FIELD_ALIAS
     *
     * @return the value of IRP_REP_SCHED_MAIL_GROUP_BY.FIELD_ALIAS
     */
    public String getFIELD_ALIAS() {
        return FIELD_ALIAS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.FIELD_ALIAS
     *
     * @param FIELD_ALIAS the value for IRP_REP_SCHED_MAIL_GROUP_BY.FIELD_ALIAS
     */
    public void setFIELD_ALIAS(String FIELD_ALIAS) {
        this.FIELD_ALIAS = FIELD_ALIAS == null ? null : FIELD_ALIAS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.GRP_ORDER
     *
     * @return the value of IRP_REP_SCHED_MAIL_GROUP_BY.GRP_ORDER
     */
    public BigDecimal getGRP_ORDER() {
        return GRP_ORDER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.GRP_ORDER
     *
     * @param GRP_ORDER the value for IRP_REP_SCHED_MAIL_GROUP_BY.GRP_ORDER
     */
    public void setGRP_ORDER(BigDecimal GRP_ORDER) {
        this.GRP_ORDER = GRP_ORDER;
    }
    
    
    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_REF
     *
     * @return the value of IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_REF
     */
    public String getREPORT_REF() {
        return REPORT_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_REF
     *
     * @param REPORT_REF the value for IRP_REP_SCHED_MAIL_GROUP_BY.REPORT_REF
     */
    public void setREPORT_REF(String REPORT_REF) {
        this.REPORT_REF = REPORT_REF == null ? null : REPORT_REF.trim();
    }
}