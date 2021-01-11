package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;

public class REP_SNAPSHOT_DRILLDOWN_COLUMNVO extends BaseVO {
    /**
     * This field corresponds to the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_DRILLDOWN
     */
    private String COLUMN_DRILLDOWN;

    /**
     * This field corresponds to the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.REP_ID
     */
    private BigDecimal REP_ID;

    /**
     * This field corresponds to the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_TYPE
     */
    private String COLUMN_TYPE;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_DRILLDOWN
     *
     * @return the value of REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_DRILLDOWN
     */
    public String getCOLUMN_DRILLDOWN() {
        return COLUMN_DRILLDOWN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_DRILLDOWN
     *
     * @param COLUMN_DRILLDOWN the value for REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_DRILLDOWN
     */
    public void setCOLUMN_DRILLDOWN(String COLUMN_DRILLDOWN) {
        this.COLUMN_DRILLDOWN = COLUMN_DRILLDOWN == null ? null : COLUMN_DRILLDOWN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.REP_ID
     *
     * @return the value of REP_SNAPSHOT_DRILLDOWN_COLUMN.REP_ID
     */
    public BigDecimal getREP_ID() {
        return REP_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.REP_ID
     *
     * @param REP_ID the value for REP_SNAPSHOT_DRILLDOWN_COLUMN.REP_ID
     */
    public void setREP_ID(BigDecimal REP_ID) {
        this.REP_ID = REP_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_TYPE
     *
     * @return the value of REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_TYPE
     */
    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_TYPE
     *
     * @param COLUMN_TYPE the value for REP_SNAPSHOT_DRILLDOWN_COLUMN.COLUMN_TYPE
     */
    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE == null ? null : COLUMN_TYPE.trim();
    }
}