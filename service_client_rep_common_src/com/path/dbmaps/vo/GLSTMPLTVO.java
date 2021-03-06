package com.path.dbmaps.vo;

import java.math.BigDecimal;
import java.util.Date;

import com.path.lib.vo.BaseVO;

public class GLSTMPLTVO extends BaseVO {
    /**
     * This field corresponds to the database column GLSTMPLT.CODE
     */
    private BigDecimal CODE;

    /**
     * This field corresponds to the database column GLSTMPLT.COMP_CODE
     */
    private BigDecimal COMP_CODE;

    /**
     * This field corresponds to the database column GLSTMPLT.LINE_NBR
     */
    private BigDecimal LINE_NBR;

    /**
     * This field corresponds to the database column GLSTMPLT.TEMPLATE_TYPE
     */
    private String TEMPLATE_TYPE;

    /**
     * This field corresponds to the database column GLSTMPLT.BRIEF_NAME_ENG
     */
    private String BRIEF_NAME_ENG;

    /**
     * This field corresponds to the database column GLSTMPLT.BRIEF_NAME_ARAB
     */
    private String BRIEF_NAME_ARAB;

    /**
     * This field corresponds to the database column GLSTMPLT.FROM_GL
     */
    private BigDecimal FROM_GL;

    /**
     * This field corresponds to the database column GLSTMPLT.TO_GL
     */
    private BigDecimal TO_GL;

    /**
     * This field corresponds to the database column GLSTMPLT.PRINTED
     */
    private BigDecimal PRINTED;

    /**
     * This field corresponds to the database column GLSTMPLT.GL_TYPE
     */
    private String GL_TYPE;

    /**
     * This field corresponds to the database column GLSTMPLT.IS_SUB_TOTAL
     */
    private String IS_SUB_TOTAL;

    /**
     * This field corresponds to the database column GLSTMPLT.CREATED_BY
     */
    private String CREATED_BY;

    /**
     * This field corresponds to the database column GLSTMPLT.DATE_CREATED
     */
    private Date DATE_CREATED;

    /**
     * This field corresponds to the database column GLSTMPLT.MODIFIED_BY
     */
    private String MODIFIED_BY;

    /**
     * This field corresponds to the database column GLSTMPLT.DATE_MODIFIED
     */
    private Date DATE_MODIFIED;

    /**
     * This field corresponds to the database column GLSTMPLT.PER_LINE_GL
     */
    private String PER_LINE_GL;

    /**
     * This field corresponds to the database column GLSTMPLT.BOLD
     */
    private String BOLD;

    /**
     * This field corresponds to the database column GLSTMPLT.DISPLAY_ZEROS
     */
    private String DISPLAY_ZEROS;

    /**
     * This field corresponds to the database column GLSTMPLT.DISPLAY_VALUES
     */
    private String DISPLAY_VALUES;

    /**
     * This field corresponds to the database column GLSTMPLT.DISP_LINE_TOT_ZERO
     */
    private String DISP_LINE_TOT_ZERO;

    /**
     * This field corresponds to the database column GLSTMPLT.EXPRESSION_LINES
     */
    private String EXPRESSION_LINES;

    /**
     * This field corresponds to the database column GLSTMPLT.FCR
     */
    private String FCR;

    /**
     * This field corresponds to the database column GLSTMPLT.ADD_DESC
     */
    private String ADD_DESC;

    /**
     * This field corresponds to the database column GLSTMPLT.GL_CODES
     */
    private String GL_CODES;

    /**
     * This field corresponds to the database column GLSTMPLT.FOR_ROUND
     */
    private String FOR_ROUND;

    /**
     * This field corresponds to the database column GLSTMPLT.BOTTOM_BORDER
     */
    private BigDecimal BOTTOM_BORDER;

    /**
     * This field corresponds to the database column GLSTMPLT.CURRENCY
     */
    private BigDecimal CURRENCY;

    /**
     * This field corresponds to the database column GLSTMPLT.CSV
     */
    private String CSV;

    /**
     * This field corresponds to the database column GLSTMPLT.FCR_EXCEL_PATH
     */
    private String FCR_EXCEL_PATH;

    /**
     * This field corresponds to the database column GLSTMPLT.ADD_DESC1
     */
    private String ADD_DESC1;

    /**
     * This field corresponds to the database column GLSTMPLT.DATE_UPDATED
     */
    private Date DATE_UPDATED;

    /**
     * This field corresponds to the database column GLSTMPLT.SAVE_DATA
     */
    private String SAVE_DATA;

    /**
     * This field corresponds to the database column GLSTMPLT.POST_CODE
     */
    private String POST_CODE;

    /**
     * This field corresponds to the database column GLSTMPLT.PERCENTAGE
     */
    private BigDecimal PERCENTAGE;

    /**
     * This field corresponds to the database column GLSTMPLT.EXPRESSION
     */
    private String EXPRESSION;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.CODE
     *
     * @return the value of GLSTMPLT.CODE
     */
    public BigDecimal getCODE() {
        return CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.CODE
     *
     * @param CODE the value for GLSTMPLT.CODE
     */
    public void setCODE(BigDecimal CODE) {
        this.CODE = CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.COMP_CODE
     *
     * @return the value of GLSTMPLT.COMP_CODE
     */
    public BigDecimal getCOMP_CODE() {
        return COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.COMP_CODE
     *
     * @param COMP_CODE the value for GLSTMPLT.COMP_CODE
     */
    public void setCOMP_CODE(BigDecimal COMP_CODE) {
        this.COMP_CODE = COMP_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.LINE_NBR
     *
     * @return the value of GLSTMPLT.LINE_NBR
     */
    public BigDecimal getLINE_NBR() {
        return LINE_NBR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.LINE_NBR
     *
     * @param LINE_NBR the value for GLSTMPLT.LINE_NBR
     */
    public void setLINE_NBR(BigDecimal LINE_NBR) {
        this.LINE_NBR = LINE_NBR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.TEMPLATE_TYPE
     *
     * @return the value of GLSTMPLT.TEMPLATE_TYPE
     */
    public String getTEMPLATE_TYPE() {
        return TEMPLATE_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.TEMPLATE_TYPE
     *
     * @param TEMPLATE_TYPE the value for GLSTMPLT.TEMPLATE_TYPE
     */
    public void setTEMPLATE_TYPE(String TEMPLATE_TYPE) {
        this.TEMPLATE_TYPE = TEMPLATE_TYPE == null ? null : TEMPLATE_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.BRIEF_NAME_ENG
     *
     * @return the value of GLSTMPLT.BRIEF_NAME_ENG
     */
    public String getBRIEF_NAME_ENG() {
        return BRIEF_NAME_ENG;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.BRIEF_NAME_ENG
     *
     * @param BRIEF_NAME_ENG the value for GLSTMPLT.BRIEF_NAME_ENG
     */
    public void setBRIEF_NAME_ENG(String BRIEF_NAME_ENG) {
        this.BRIEF_NAME_ENG = BRIEF_NAME_ENG == null ? null : BRIEF_NAME_ENG.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.BRIEF_NAME_ARAB
     *
     * @return the value of GLSTMPLT.BRIEF_NAME_ARAB
     */
    public String getBRIEF_NAME_ARAB() {
        return BRIEF_NAME_ARAB;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.BRIEF_NAME_ARAB
     *
     * @param BRIEF_NAME_ARAB the value for GLSTMPLT.BRIEF_NAME_ARAB
     */
    public void setBRIEF_NAME_ARAB(String BRIEF_NAME_ARAB) {
        this.BRIEF_NAME_ARAB = BRIEF_NAME_ARAB == null ? null : BRIEF_NAME_ARAB.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.FROM_GL
     *
     * @return the value of GLSTMPLT.FROM_GL
     */
    public BigDecimal getFROM_GL() {
        return FROM_GL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.FROM_GL
     *
     * @param FROM_GL the value for GLSTMPLT.FROM_GL
     */
    public void setFROM_GL(BigDecimal FROM_GL) {
        this.FROM_GL = FROM_GL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.TO_GL
     *
     * @return the value of GLSTMPLT.TO_GL
     */
    public BigDecimal getTO_GL() {
        return TO_GL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.TO_GL
     *
     * @param TO_GL the value for GLSTMPLT.TO_GL
     */
    public void setTO_GL(BigDecimal TO_GL) {
        this.TO_GL = TO_GL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.PRINTED
     *
     * @return the value of GLSTMPLT.PRINTED
     */
    public BigDecimal getPRINTED() {
        return PRINTED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.PRINTED
     *
     * @param PRINTED the value for GLSTMPLT.PRINTED
     */
    public void setPRINTED(BigDecimal PRINTED) {
        this.PRINTED = PRINTED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.GL_TYPE
     *
     * @return the value of GLSTMPLT.GL_TYPE
     */
    public String getGL_TYPE() {
        return GL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.GL_TYPE
     *
     * @param GL_TYPE the value for GLSTMPLT.GL_TYPE
     */
    public void setGL_TYPE(String GL_TYPE) {
        this.GL_TYPE = GL_TYPE == null ? null : GL_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.IS_SUB_TOTAL
     *
     * @return the value of GLSTMPLT.IS_SUB_TOTAL
     */
    public String getIS_SUB_TOTAL() {
        return IS_SUB_TOTAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.IS_SUB_TOTAL
     *
     * @param IS_SUB_TOTAL the value for GLSTMPLT.IS_SUB_TOTAL
     */
    public void setIS_SUB_TOTAL(String IS_SUB_TOTAL) {
        this.IS_SUB_TOTAL = IS_SUB_TOTAL == null ? null : IS_SUB_TOTAL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.CREATED_BY
     *
     * @return the value of GLSTMPLT.CREATED_BY
     */
    public String getCREATED_BY() {
        return CREATED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.CREATED_BY
     *
     * @param CREATED_BY the value for GLSTMPLT.CREATED_BY
     */
    public void setCREATED_BY(String CREATED_BY) {
        this.CREATED_BY = CREATED_BY == null ? null : CREATED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.DATE_CREATED
     *
     * @return the value of GLSTMPLT.DATE_CREATED
     */
    public Date getDATE_CREATED() {
        return DATE_CREATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.DATE_CREATED
     *
     * @param DATE_CREATED the value for GLSTMPLT.DATE_CREATED
     */
    public void setDATE_CREATED(Date DATE_CREATED) {
        this.DATE_CREATED = DATE_CREATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.MODIFIED_BY
     *
     * @return the value of GLSTMPLT.MODIFIED_BY
     */
    public String getMODIFIED_BY() {
        return MODIFIED_BY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.MODIFIED_BY
     *
     * @param MODIFIED_BY the value for GLSTMPLT.MODIFIED_BY
     */
    public void setMODIFIED_BY(String MODIFIED_BY) {
        this.MODIFIED_BY = MODIFIED_BY == null ? null : MODIFIED_BY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.DATE_MODIFIED
     *
     * @return the value of GLSTMPLT.DATE_MODIFIED
     */
    public Date getDATE_MODIFIED() {
        return DATE_MODIFIED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.DATE_MODIFIED
     *
     * @param DATE_MODIFIED the value for GLSTMPLT.DATE_MODIFIED
     */
    public void setDATE_MODIFIED(Date DATE_MODIFIED) {
        this.DATE_MODIFIED = DATE_MODIFIED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.PER_LINE_GL
     *
     * @return the value of GLSTMPLT.PER_LINE_GL
     */
    public String getPER_LINE_GL() {
        return PER_LINE_GL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.PER_LINE_GL
     *
     * @param PER_LINE_GL the value for GLSTMPLT.PER_LINE_GL
     */
    public void setPER_LINE_GL(String PER_LINE_GL) {
        this.PER_LINE_GL = PER_LINE_GL == null ? null : PER_LINE_GL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.BOLD
     *
     * @return the value of GLSTMPLT.BOLD
     */
    public String getBOLD() {
        return BOLD;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.BOLD
     *
     * @param BOLD the value for GLSTMPLT.BOLD
     */
    public void setBOLD(String BOLD) {
        this.BOLD = BOLD == null ? null : BOLD.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.DISPLAY_ZEROS
     *
     * @return the value of GLSTMPLT.DISPLAY_ZEROS
     */
    public String getDISPLAY_ZEROS() {
        return DISPLAY_ZEROS;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.DISPLAY_ZEROS
     *
     * @param DISPLAY_ZEROS the value for GLSTMPLT.DISPLAY_ZEROS
     */
    public void setDISPLAY_ZEROS(String DISPLAY_ZEROS) {
        this.DISPLAY_ZEROS = DISPLAY_ZEROS == null ? null : DISPLAY_ZEROS.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.DISPLAY_VALUES
     *
     * @return the value of GLSTMPLT.DISPLAY_VALUES
     */
    public String getDISPLAY_VALUES() {
        return DISPLAY_VALUES;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.DISPLAY_VALUES
     *
     * @param DISPLAY_VALUES the value for GLSTMPLT.DISPLAY_VALUES
     */
    public void setDISPLAY_VALUES(String DISPLAY_VALUES) {
        this.DISPLAY_VALUES = DISPLAY_VALUES == null ? null : DISPLAY_VALUES.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.DISP_LINE_TOT_ZERO
     *
     * @return the value of GLSTMPLT.DISP_LINE_TOT_ZERO
     */
    public String getDISP_LINE_TOT_ZERO() {
        return DISP_LINE_TOT_ZERO;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.DISP_LINE_TOT_ZERO
     *
     * @param DISP_LINE_TOT_ZERO the value for GLSTMPLT.DISP_LINE_TOT_ZERO
     */
    public void setDISP_LINE_TOT_ZERO(String DISP_LINE_TOT_ZERO) {
        this.DISP_LINE_TOT_ZERO = DISP_LINE_TOT_ZERO == null ? null : DISP_LINE_TOT_ZERO.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.EXPRESSION_LINES
     *
     * @return the value of GLSTMPLT.EXPRESSION_LINES
     */
    public String getEXPRESSION_LINES() {
        return EXPRESSION_LINES;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.EXPRESSION_LINES
     *
     * @param EXPRESSION_LINES the value for GLSTMPLT.EXPRESSION_LINES
     */
    public void setEXPRESSION_LINES(String EXPRESSION_LINES) {
        this.EXPRESSION_LINES = EXPRESSION_LINES == null ? null : EXPRESSION_LINES.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.FCR
     *
     * @return the value of GLSTMPLT.FCR
     */
    public String getFCR() {
        return FCR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.FCR
     *
     * @param FCR the value for GLSTMPLT.FCR
     */
    public void setFCR(String FCR) {
        this.FCR = FCR == null ? null : FCR.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.ADD_DESC
     *
     * @return the value of GLSTMPLT.ADD_DESC
     */
    public String getADD_DESC() {
        return ADD_DESC;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.ADD_DESC
     *
     * @param ADD_DESC the value for GLSTMPLT.ADD_DESC
     */
    public void setADD_DESC(String ADD_DESC) {
        this.ADD_DESC = ADD_DESC == null ? null : ADD_DESC.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.GL_CODES
     *
     * @return the value of GLSTMPLT.GL_CODES
     */
    public String getGL_CODES() {
        return GL_CODES;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.GL_CODES
     *
     * @param GL_CODES the value for GLSTMPLT.GL_CODES
     */
    public void setGL_CODES(String GL_CODES) {
        this.GL_CODES = GL_CODES == null ? null : GL_CODES.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.FOR_ROUND
     *
     * @return the value of GLSTMPLT.FOR_ROUND
     */
    public String getFOR_ROUND() {
        return FOR_ROUND;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.FOR_ROUND
     *
     * @param FOR_ROUND the value for GLSTMPLT.FOR_ROUND
     */
    public void setFOR_ROUND(String FOR_ROUND) {
        this.FOR_ROUND = FOR_ROUND == null ? null : FOR_ROUND.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.BOTTOM_BORDER
     *
     * @return the value of GLSTMPLT.BOTTOM_BORDER
     */
    public BigDecimal getBOTTOM_BORDER() {
        return BOTTOM_BORDER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.BOTTOM_BORDER
     *
     * @param BOTTOM_BORDER the value for GLSTMPLT.BOTTOM_BORDER
     */
    public void setBOTTOM_BORDER(BigDecimal BOTTOM_BORDER) {
        this.BOTTOM_BORDER = BOTTOM_BORDER;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.CURRENCY
     *
     * @return the value of GLSTMPLT.CURRENCY
     */
    public BigDecimal getCURRENCY() {
        return CURRENCY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.CURRENCY
     *
     * @param CURRENCY the value for GLSTMPLT.CURRENCY
     */
    public void setCURRENCY(BigDecimal CURRENCY) {
        this.CURRENCY = CURRENCY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.CSV
     *
     * @return the value of GLSTMPLT.CSV
     */
    public String getCSV() {
        return CSV;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.CSV
     *
     * @param CSV the value for GLSTMPLT.CSV
     */
    public void setCSV(String CSV) {
        this.CSV = CSV == null ? null : CSV.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.FCR_EXCEL_PATH
     *
     * @return the value of GLSTMPLT.FCR_EXCEL_PATH
     */
    public String getFCR_EXCEL_PATH() {
        return FCR_EXCEL_PATH;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.FCR_EXCEL_PATH
     *
     * @param FCR_EXCEL_PATH the value for GLSTMPLT.FCR_EXCEL_PATH
     */
    public void setFCR_EXCEL_PATH(String FCR_EXCEL_PATH) {
        this.FCR_EXCEL_PATH = FCR_EXCEL_PATH == null ? null : FCR_EXCEL_PATH.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.ADD_DESC1
     *
     * @return the value of GLSTMPLT.ADD_DESC1
     */
    public String getADD_DESC1() {
        return ADD_DESC1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.ADD_DESC1
     *
     * @param ADD_DESC1 the value for GLSTMPLT.ADD_DESC1
     */
    public void setADD_DESC1(String ADD_DESC1) {
        this.ADD_DESC1 = ADD_DESC1 == null ? null : ADD_DESC1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.DATE_UPDATED
     *
     * @return the value of GLSTMPLT.DATE_UPDATED
     */
    public Date getDATE_UPDATED() {
        return DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.DATE_UPDATED
     *
     * @param DATE_UPDATED the value for GLSTMPLT.DATE_UPDATED
     */
    public void setDATE_UPDATED(Date DATE_UPDATED) {
        this.DATE_UPDATED = DATE_UPDATED;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.SAVE_DATA
     *
     * @return the value of GLSTMPLT.SAVE_DATA
     */
    public String getSAVE_DATA() {
        return SAVE_DATA;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.SAVE_DATA
     *
     * @param SAVE_DATA the value for GLSTMPLT.SAVE_DATA
     */
    public void setSAVE_DATA(String SAVE_DATA) {
        this.SAVE_DATA = SAVE_DATA == null ? null : SAVE_DATA.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.POST_CODE
     *
     * @return the value of GLSTMPLT.POST_CODE
     */
    public String getPOST_CODE() {
        return POST_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.POST_CODE
     *
     * @param POST_CODE the value for GLSTMPLT.POST_CODE
     */
    public void setPOST_CODE(String POST_CODE) {
        this.POST_CODE = POST_CODE == null ? null : POST_CODE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.PERCENTAGE
     *
     * @return the value of GLSTMPLT.PERCENTAGE
     */
    public BigDecimal getPERCENTAGE() {
        return PERCENTAGE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.PERCENTAGE
     *
     * @param PERCENTAGE the value for GLSTMPLT.PERCENTAGE
     */
    public void setPERCENTAGE(BigDecimal PERCENTAGE) {
        this.PERCENTAGE = PERCENTAGE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column GLSTMPLT.EXPRESSION
     *
     * @return the value of GLSTMPLT.EXPRESSION
     */
    public String getEXPRESSION() {
        return EXPRESSION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column GLSTMPLT.EXPRESSION
     *
     * @param EXPRESSION the value for GLSTMPLT.EXPRESSION
     */
    public void setEXPRESSION(String EXPRESSION) {
        this.EXPRESSION = EXPRESSION == null ? null : EXPRESSION.trim();
    }
}