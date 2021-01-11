package com.path.dbmaps.vo;

import com.path.lib.vo.BaseVO;
import java.math.BigDecimal;

public class IRP_FCR_FIXED_COLSVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_FCR_FIXED_COLS.COL_NAME
     */
    private String COL_NAME;

    /**
     * This field corresponds to the database column IRP_FCR_FIXED_COLS.PROG_REF
     */
    private String PROG_REF;

    /**
     * This field corresponds to the database column IRP_FCR_FIXED_COLS.COL_TYPE
     */
    private String COL_TYPE;

    /**
     * This field corresponds to the database column IRP_FCR_FIXED_COLS.COL_HEADER
     */
    private String COL_HEADER;

    /**
     * This field corresponds to the database column IRP_FCR_FIXED_COLS.COL_ORDER
     */
    private BigDecimal COL_ORDER;

    /**
     * This field corresponds to the database column IRP_FCR_FIXED_COLS.COL_SHOW_HIDE_YN
     */
    private BigDecimal COL_SHOW_HIDE_YN;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FCR_FIXED_COLS.COL_NAME
     *
     * @return the value of IRP_FCR_FIXED_COLS.COL_NAME
     */
    public String getCOL_NAME() {
        return COL_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FCR_FIXED_COLS.COL_NAME
     *
     * @param COL_NAME the value for IRP_FCR_FIXED_COLS.COL_NAME
     */
    public void setCOL_NAME(String COL_NAME) {
        this.COL_NAME = COL_NAME == null ? null : COL_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FCR_FIXED_COLS.PROG_REF
     *
     * @return the value of IRP_FCR_FIXED_COLS.PROG_REF
     */
    public String getPROG_REF() {
        return PROG_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FCR_FIXED_COLS.PROG_REF
     *
     * @param PROG_REF the value for IRP_FCR_FIXED_COLS.PROG_REF
     */
    public void setPROG_REF(String PROG_REF) {
        this.PROG_REF = PROG_REF == null ? null : PROG_REF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FCR_FIXED_COLS.COL_TYPE
     *
     * @return the value of IRP_FCR_FIXED_COLS.COL_TYPE
     */
    public String getCOL_TYPE() {
        return COL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FCR_FIXED_COLS.COL_TYPE
     *
     * @param COL_TYPE the value for IRP_FCR_FIXED_COLS.COL_TYPE
     */
    public void setCOL_TYPE(String COL_TYPE) {
        this.COL_TYPE = COL_TYPE == null ? null : COL_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FCR_FIXED_COLS.COL_HEADER
     *
     * @return the value of IRP_FCR_FIXED_COLS.COL_HEADER
     */
    public String getCOL_HEADER() {
        return COL_HEADER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FCR_FIXED_COLS.COL_HEADER
     *
     * @param COL_HEADER the value for IRP_FCR_FIXED_COLS.COL_HEADER
     */
    public void setCOL_HEADER(String COL_HEADER) {
        this.COL_HEADER = COL_HEADER == null ? null : COL_HEADER.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FCR_FIXED_COLS.COL_ORDER
     *
     * @return the value of IRP_FCR_FIXED_COLS.COL_ORDER
     */
    public BigDecimal getCOL_ORDER() {
        return COL_ORDER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FCR_FIXED_COLS.COL_ORDER
     *
     * @param COL_ORDER the value for IRP_FCR_FIXED_COLS.COL_ORDER
     */
    public void setCOL_ORDER(BigDecimal COL_ORDER) {
        this.COL_ORDER = COL_ORDER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_FCR_FIXED_COLS.COL_SHOW_HIDE_YN
     *
     * @return the value of IRP_FCR_FIXED_COLS.COL_SHOW_HIDE_YN
     */
    public BigDecimal getCOL_SHOW_HIDE_YN() {
        return COL_SHOW_HIDE_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_FCR_FIXED_COLS.COL_SHOW_HIDE_YN
     *
     * @param COL_SHOW_HIDE_YN the value for IRP_FCR_FIXED_COLS.COL_SHOW_HIDE_YN
     */
    public void setCOL_SHOW_HIDE_YN(BigDecimal COL_SHOW_HIDE_YN) {
        this.COL_SHOW_HIDE_YN = COL_SHOW_HIDE_YN;
    }
}