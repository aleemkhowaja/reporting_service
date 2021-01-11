package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;
import java.util.Date;

public class REP_MISMATCH_PARAMVO extends BaseVO {
    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.REP_MISMATCH_ID
     */
    private BigDecimal REP_MISMATCH_ID;

    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.COMP_CODE
     */
    private BigDecimal COMP_CODE;

    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.REP_REFERENCE
     */
    private String REP_REFERENCE;

    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.CRITERIA_CODE
     */
    private String CRITERIA_CODE;

    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.MISMATCH_TYPE
     */
    private BigDecimal MISMATCH_TYPE;

    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.ROW_YN
     */
    private BigDecimal ROW_YN;

    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.CRITERIA_COLUMN
     */
    private String CRITERIA_COLUMN;

    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.COLUMN_TYPE
     */
    private String COLUMN_TYPE;

    /**
     * This field corresponds to the database column REP_MISMATCH_PARAM.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.REP_MISMATCH_ID
     *
     * @return the value of REP_MISMATCH_PARAM.REP_MISMATCH_ID
     */
    public BigDecimal getREP_MISMATCH_ID() {
        return REP_MISMATCH_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.REP_MISMATCH_ID
     *
     * @param REP_MISMATCH_ID the value for REP_MISMATCH_PARAM.REP_MISMATCH_ID
     */
    public void setREP_MISMATCH_ID(BigDecimal REP_MISMATCH_ID) {
        this.REP_MISMATCH_ID = REP_MISMATCH_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.COMP_CODE
     *
     * @return the value of REP_MISMATCH_PARAM.COMP_CODE
     */
    public BigDecimal getCOMP_CODE() {
        return COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.COMP_CODE
     *
     * @param COMP_CODE the value for REP_MISMATCH_PARAM.COMP_CODE
     */
    public void setCOMP_CODE(BigDecimal COMP_CODE) {
        this.COMP_CODE = COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.REP_REFERENCE
     *
     * @return the value of REP_MISMATCH_PARAM.REP_REFERENCE
     */
    public String getREP_REFERENCE() {
        return REP_REFERENCE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.REP_REFERENCE
     *
     * @param REP_REFERENCE the value for REP_MISMATCH_PARAM.REP_REFERENCE
     */
    public void setREP_REFERENCE(String REP_REFERENCE) {
        this.REP_REFERENCE = REP_REFERENCE == null ? null : REP_REFERENCE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.CRITERIA_CODE
     *
     * @return the value of REP_MISMATCH_PARAM.CRITERIA_CODE
     */
    public String getCRITERIA_CODE() {
        return CRITERIA_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.CRITERIA_CODE
     *
     * @param CRITERIA_CODE the value for REP_MISMATCH_PARAM.CRITERIA_CODE
     */
    public void setCRITERIA_CODE(String CRITERIA_CODE) {
        this.CRITERIA_CODE = CRITERIA_CODE == null ? null : CRITERIA_CODE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.MISMATCH_TYPE
     *
     * @return the value of REP_MISMATCH_PARAM.MISMATCH_TYPE
     */
    public BigDecimal getMISMATCH_TYPE() {
        return MISMATCH_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.MISMATCH_TYPE
     *
     * @param MISMATCH_TYPE the value for REP_MISMATCH_PARAM.MISMATCH_TYPE
     */
    public void setMISMATCH_TYPE(BigDecimal MISMATCH_TYPE) {
        this.MISMATCH_TYPE = MISMATCH_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.ROW_YN
     *
     * @return the value of REP_MISMATCH_PARAM.ROW_YN
     */
    public BigDecimal getROW_YN() {
        return ROW_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.ROW_YN
     *
     * @param ROW_YN the value for REP_MISMATCH_PARAM.ROW_YN
     */
    public void setROW_YN(BigDecimal ROW_YN) {
        this.ROW_YN = ROW_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.CRITERIA_COLUMN
     *
     * @return the value of REP_MISMATCH_PARAM.CRITERIA_COLUMN
     */
    public String getCRITERIA_COLUMN() {
        return CRITERIA_COLUMN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.CRITERIA_COLUMN
     *
     * @param CRITERIA_COLUMN the value for REP_MISMATCH_PARAM.CRITERIA_COLUMN
     */
    public void setCRITERIA_COLUMN(String CRITERIA_COLUMN) {
        this.CRITERIA_COLUMN = CRITERIA_COLUMN == null ? null : CRITERIA_COLUMN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.COLUMN_TYPE
     *
     * @return the value of REP_MISMATCH_PARAM.COLUMN_TYPE
     */
    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.COLUMN_TYPE
     *
     * @param COLUMN_TYPE the value for REP_MISMATCH_PARAM.COLUMN_TYPE
     */
    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE == null ? null : COLUMN_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_PARAM.DATE_UPDATED
     *
     * @return the value of REP_MISMATCH_PARAM.DATE_UPDATED
     */
    public Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_PARAM.DATE_UPDATED
     *
     * @param DATE_UPDATED the value for REP_MISMATCH_PARAM.DATE_UPDATED
     */
    public void setDATE_UPDATED(Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }
}