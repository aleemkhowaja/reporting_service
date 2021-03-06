package com.path.dbmaps.vo;

import java.math.BigDecimal;
import java.util.Date;

public class FTR_CB_CODEVO extends FTR_CB_CODEVOKey {
    /**
     * This field corresponds to the database column FTR_CB_CODE.ENTITY_CB_CODE
     */
    private BigDecimal ENTITY_CB_CODE;

    /**
     * This field corresponds to the database column FTR_CB_CODE.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_CB_CODE.ENTITY_CB_CODE
     *
     * @return the value of FTR_CB_CODE.ENTITY_CB_CODE
     */
    public BigDecimal getENTITY_CB_CODE() {
        return ENTITY_CB_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_CB_CODE.ENTITY_CB_CODE
     *
     * @param ENTITY_CB_CODE the value for FTR_CB_CODE.ENTITY_CB_CODE
     */
    public void setENTITY_CB_CODE(BigDecimal ENTITY_CB_CODE) {
        this.ENTITY_CB_CODE = ENTITY_CB_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column FTR_CB_CODE.DATE_UPDATED
     *
     * @return the value of FTR_CB_CODE.DATE_UPDATED
     */
    public Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column FTR_CB_CODE.DATE_UPDATED
     *
     * @param DATE_UPDATED the value for FTR_CB_CODE.DATE_UPDATED
     */
    public void setDATE_UPDATED(Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }
}