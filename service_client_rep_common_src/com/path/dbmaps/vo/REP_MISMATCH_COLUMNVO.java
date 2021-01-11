package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;

public class REP_MISMATCH_COLUMNVO extends BaseVO {
    /**
     * This field corresponds to the database column REP_MISMATCH_COLUMN.RELATED_COLUMN
     */
    private String RELATED_COLUMN;

    /**
     * This field corresponds to the database column REP_MISMATCH_COLUMN.REP_MISMATCH_ID
     */
    private BigDecimal REP_MISMATCH_ID;

    /**
     * This field corresponds to the database column REP_MISMATCH_COLUMN.COLUMN_TYPE
     */
    private String COLUMN_TYPE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_COLUMN.RELATED_COLUMN
     *
     * @return the value of REP_MISMATCH_COLUMN.RELATED_COLUMN
     */
    public String getRELATED_COLUMN() {
        return RELATED_COLUMN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_COLUMN.RELATED_COLUMN
     *
     * @param RELATED_COLUMN the value for REP_MISMATCH_COLUMN.RELATED_COLUMN
     */
    public void setRELATED_COLUMN(String RELATED_COLUMN) {
        this.RELATED_COLUMN = RELATED_COLUMN == null ? null : RELATED_COLUMN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_COLUMN.REP_MISMATCH_ID
     *
     * @return the value of REP_MISMATCH_COLUMN.REP_MISMATCH_ID
     */
    public BigDecimal getREP_MISMATCH_ID() {
        return REP_MISMATCH_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_COLUMN.REP_MISMATCH_ID
     *
     * @param REP_MISMATCH_ID the value for REP_MISMATCH_COLUMN.REP_MISMATCH_ID
     */
    public void setREP_MISMATCH_ID(BigDecimal REP_MISMATCH_ID) {
        this.REP_MISMATCH_ID = REP_MISMATCH_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_COLUMN.COLUMN_TYPE
     *
     * @return the value of REP_MISMATCH_COLUMN.COLUMN_TYPE
     */
    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_COLUMN.COLUMN_TYPE
     *
     * @param COLUMN_TYPE the value for REP_MISMATCH_COLUMN.COLUMN_TYPE
     */
    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE == null ? null : COLUMN_TYPE.trim();
    }
}