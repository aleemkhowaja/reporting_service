package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class FTR_TMPL_REFVO extends BaseVO {
    /**
     * This field corresponds to the database column FTR_TMPL_REF.APP_NAME
     */
    private String APP_NAME;

    /**
     * This field corresponds to the database column FTR_TMPL_REF.COMP_CODE
     */
    private BigDecimal COMP_CODE;

    /**
     * This field corresponds to the database column FTR_TMPL_REF.REPORT_REF
     */
    private String REPORT_REF;

    /**
     * This field corresponds to the database column FTR_TMPL_REF.TEMPLATE_CODE
     */
    private BigDecimal TEMPLATE_CODE;

    /**
     * This field corresponds to the database column FTR_TMPL_REF.TEMPLATE_ORDER
     */
    private BigDecimal TEMPLATE_ORDER;

    /**
     * This field corresponds to the database column FTR_TMPL_REF.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TMPL_REF.APP_NAME
     *
     * @return the value of FTR_TMPL_REF.APP_NAME
     */
    public String getAPP_NAME() {
        return APP_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TMPL_REF.APP_NAME
     *
     * @param APP_NAME the value for FTR_TMPL_REF.APP_NAME
     */
    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME == null ? null : APP_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TMPL_REF.COMP_CODE
     *
     * @return the value of FTR_TMPL_REF.COMP_CODE
     */
    public BigDecimal getCOMP_CODE() {
        return COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TMPL_REF.COMP_CODE
     *
     * @param COMP_CODE the value for FTR_TMPL_REF.COMP_CODE
     */
    public void setCOMP_CODE(BigDecimal COMP_CODE) {
        this.COMP_CODE = COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TMPL_REF.REPORT_REF
     *
     * @return the value of FTR_TMPL_REF.REPORT_REF
     */
    public String getREPORT_REF() {
        return REPORT_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TMPL_REF.REPORT_REF
     *
     * @param REPORT_REF the value for FTR_TMPL_REF.REPORT_REF
     */
    public void setREPORT_REF(String REPORT_REF) {
        this.REPORT_REF = REPORT_REF == null ? null : REPORT_REF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TMPL_REF.TEMPLATE_CODE
     *
     * @return the value of FTR_TMPL_REF.TEMPLATE_CODE
     */
    public BigDecimal getTEMPLATE_CODE() {
        return TEMPLATE_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TMPL_REF.TEMPLATE_CODE
     *
     * @param TEMPLATE_CODE the value for FTR_TMPL_REF.TEMPLATE_CODE
     */
    public void setTEMPLATE_CODE(BigDecimal TEMPLATE_CODE) {
        this.TEMPLATE_CODE = TEMPLATE_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TMPL_REF.TEMPLATE_ORDER
     *
     * @return the value of FTR_TMPL_REF.TEMPLATE_ORDER
     */
    public BigDecimal getTEMPLATE_ORDER() {
        return TEMPLATE_ORDER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TMPL_REF.TEMPLATE_ORDER
     *
     * @param TEMPLATE_ORDER the value for FTR_TMPL_REF.TEMPLATE_ORDER
     */
    public void setTEMPLATE_ORDER(BigDecimal TEMPLATE_ORDER) {
        this.TEMPLATE_ORDER = TEMPLATE_ORDER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_TMPL_REF.DATE_UPDATED
     *
     * @return the value of FTR_TMPL_REF.DATE_UPDATED
     */
    public Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_TMPL_REF.DATE_UPDATED
     *
     * @param DATE_UPDATED the value for FTR_TMPL_REF.DATE_UPDATED
     */
    public void setDATE_UPDATED(Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }
}