package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;

public class REP_MISMATCH_INTRA_CRITERIAVO extends BaseVO {
    /**
     * This field corresponds to the database column REP_MISMATCH_INTRA_CRITERIA.RELATED_CRITERIA
     */
    private String RELATED_CRITERIA;

    /**
     * This field corresponds to the database column REP_MISMATCH_INTRA_CRITERIA.REP_MISMATCH_ID
     */
    private BigDecimal REP_MISMATCH_ID;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_INTRA_CRITERIA.RELATED_CRITERIA
     *
     * @return the value of REP_MISMATCH_INTRA_CRITERIA.RELATED_CRITERIA
     */
    public String getRELATED_CRITERIA() {
        return RELATED_CRITERIA;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_INTRA_CRITERIA.RELATED_CRITERIA
     *
     * @param RELATED_CRITERIA the value for REP_MISMATCH_INTRA_CRITERIA.RELATED_CRITERIA
     */
    public void setRELATED_CRITERIA(String RELATED_CRITERIA) {
        this.RELATED_CRITERIA = RELATED_CRITERIA == null ? null : RELATED_CRITERIA.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column REP_MISMATCH_INTRA_CRITERIA.REP_MISMATCH_ID
     *
     * @return the value of REP_MISMATCH_INTRA_CRITERIA.REP_MISMATCH_ID
     */
    public BigDecimal getREP_MISMATCH_ID() {
        return REP_MISMATCH_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column REP_MISMATCH_INTRA_CRITERIA.REP_MISMATCH_ID
     *
     * @param REP_MISMATCH_ID the value for REP_MISMATCH_INTRA_CRITERIA.REP_MISMATCH_ID
     */
    public void setREP_MISMATCH_ID(BigDecimal REP_MISMATCH_ID) {
        this.REP_MISMATCH_ID = REP_MISMATCH_ID;
    }
}