package com.path.dbmaps.vo;

import java.math.BigDecimal;

import com.path.lib.vo.BaseVO;

public class IRP_REPORT_SCHEDULEVO extends BaseVO {
    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.REPORT_ID
     */
    private BigDecimal REPORT_ID;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.SCHED_ID
     */
    private BigDecimal SCHED_ID;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.REPORT_FORMAT
     */
    private String REPORT_FORMAT;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.IS_ACTIVE
     */
    private String IS_ACTIVE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.PRINTER_NAME
     */
    private String PRINTER_NAME;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.DATA_ONLY
     */
    private String DATA_ONLY;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.CSV_SEPARATOR
     */
    private String CSV_SEPARATOR;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.SHOW_HEAD_FOOT
     */
    private String SHOW_HEAD_FOOT;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.REPORT_LANGUAGE
     */
    private String REPORT_LANGUAGE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.ATTACH_FILE_NAME
     */
    private String ATTACH_FILE_NAME;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.BCC_EMAIL_TYPE
     */
    private BigDecimal BCC_EMAIL_TYPE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.BCC_EMAIL_VAL
     */
    private String BCC_EMAIL_VAL;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.CC_EMAIL_TYPE
     */
    private BigDecimal CC_EMAIL_TYPE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.CC_EMAIL_VAL
     */
    private String CC_EMAIL_VAL;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.EMAIL_BODY
     */
    private String EMAIL_BODY;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.EMAIL_SUBJECT
     */
    private String EMAIL_SUBJECT;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.FROM_EMAIL_TYPE
     */
    private BigDecimal FROM_EMAIL_TYPE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.FROM_EMAIL_VAL
     */
    private String FROM_EMAIL_VAL;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.MAIL_SERVER_CODE
     */
    private BigDecimal MAIL_SERVER_CODE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.PRINT_IF_NO_DATA_YN
     */
    private String PRINT_IF_NO_DATA_YN;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.SEND_IF_NO_DATA_YN
     */
    private String SEND_IF_NO_DATA_YN;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.TO_EMAIL_TYPE
     */
    private BigDecimal TO_EMAIL_TYPE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.TO_EMAIL_VAL
     */
    private String TO_EMAIL_VAL;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.EMAIL_CONSOLIDATED_YN
     */
    private String EMAIL_CONSOLIDATED_YN;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.DATE_TYPE
     */
    private String DATE_TYPE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.SAVE_DATA_TYPE
     */
    private BigDecimal SAVE_DATA_TYPE;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.REPORT_REF
     */
    private String REPORT_REF;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.IS_FCR_YN
     */
    private BigDecimal IS_FCR_YN;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.SECURED_FILE_YN
     */
    private String SECURED_FILE_YN;

    /**
     * This field corresponds to the database column IRP_REPORT_SCHEDULE.SECURED_PWD_FIELD_NAME
     */
    private String SECURED_PWD_FIELD_NAME;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.REPORT_ID
     *
     * @return the value of IRP_REPORT_SCHEDULE.REPORT_ID
     */
    public BigDecimal getREPORT_ID() {
        return REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.REPORT_ID
     *
     * @param REPORT_ID the value for IRP_REPORT_SCHEDULE.REPORT_ID
     */
    public void setREPORT_ID(BigDecimal REPORT_ID) {
        this.REPORT_ID = REPORT_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.SCHED_ID
     *
     * @return the value of IRP_REPORT_SCHEDULE.SCHED_ID
     */
    public BigDecimal getSCHED_ID() {
        return SCHED_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.SCHED_ID
     *
     * @param SCHED_ID the value for IRP_REPORT_SCHEDULE.SCHED_ID
     */
    public void setSCHED_ID(BigDecimal SCHED_ID) {
        this.SCHED_ID = SCHED_ID;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.REPORT_FORMAT
     *
     * @return the value of IRP_REPORT_SCHEDULE.REPORT_FORMAT
     */
    public String getREPORT_FORMAT() {
        return REPORT_FORMAT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.REPORT_FORMAT
     *
     * @param REPORT_FORMAT the value for IRP_REPORT_SCHEDULE.REPORT_FORMAT
     */
    public void setREPORT_FORMAT(String REPORT_FORMAT) {
        this.REPORT_FORMAT = REPORT_FORMAT == null ? null : REPORT_FORMAT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.IS_ACTIVE
     *
     * @return the value of IRP_REPORT_SCHEDULE.IS_ACTIVE
     */
    public String getIS_ACTIVE() {
        return IS_ACTIVE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.IS_ACTIVE
     *
     * @param IS_ACTIVE the value for IRP_REPORT_SCHEDULE.IS_ACTIVE
     */
    public void setIS_ACTIVE(String IS_ACTIVE) {
        this.IS_ACTIVE = IS_ACTIVE == null ? null : IS_ACTIVE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.PRINTER_NAME
     *
     * @return the value of IRP_REPORT_SCHEDULE.PRINTER_NAME
     */
    public String getPRINTER_NAME() {
        return PRINTER_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.PRINTER_NAME
     *
     * @param PRINTER_NAME the value for IRP_REPORT_SCHEDULE.PRINTER_NAME
     */
    public void setPRINTER_NAME(String PRINTER_NAME) {
        this.PRINTER_NAME = PRINTER_NAME == null ? null : PRINTER_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.DATA_ONLY
     *
     * @return the value of IRP_REPORT_SCHEDULE.DATA_ONLY
     */
    public String getDATA_ONLY() {
        return DATA_ONLY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.DATA_ONLY
     *
     * @param DATA_ONLY the value for IRP_REPORT_SCHEDULE.DATA_ONLY
     */
    public void setDATA_ONLY(String DATA_ONLY) {
        this.DATA_ONLY = DATA_ONLY == null ? null : DATA_ONLY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.CSV_SEPARATOR
     *
     * @return the value of IRP_REPORT_SCHEDULE.CSV_SEPARATOR
     */
    public String getCSV_SEPARATOR() {
        return CSV_SEPARATOR;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.CSV_SEPARATOR
     *
     * @param CSV_SEPARATOR the value for IRP_REPORT_SCHEDULE.CSV_SEPARATOR
     */
    public void setCSV_SEPARATOR(String CSV_SEPARATOR) {
        this.CSV_SEPARATOR = CSV_SEPARATOR == null ? null : CSV_SEPARATOR.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.SHOW_HEAD_FOOT
     *
     * @return the value of IRP_REPORT_SCHEDULE.SHOW_HEAD_FOOT
     */
    public String getSHOW_HEAD_FOOT() {
        return SHOW_HEAD_FOOT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.SHOW_HEAD_FOOT
     *
     * @param SHOW_HEAD_FOOT the value for IRP_REPORT_SCHEDULE.SHOW_HEAD_FOOT
     */
    public void setSHOW_HEAD_FOOT(String SHOW_HEAD_FOOT) {
        this.SHOW_HEAD_FOOT = SHOW_HEAD_FOOT == null ? null : SHOW_HEAD_FOOT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.REPORT_LANGUAGE
     *
     * @return the value of IRP_REPORT_SCHEDULE.REPORT_LANGUAGE
     */
    public String getREPORT_LANGUAGE() {
        return REPORT_LANGUAGE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.REPORT_LANGUAGE
     *
     * @param REPORT_LANGUAGE the value for IRP_REPORT_SCHEDULE.REPORT_LANGUAGE
     */
    public void setREPORT_LANGUAGE(String REPORT_LANGUAGE) {
        this.REPORT_LANGUAGE = REPORT_LANGUAGE == null ? null : REPORT_LANGUAGE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.ATTACH_FILE_NAME
     *
     * @return the value of IRP_REPORT_SCHEDULE.ATTACH_FILE_NAME
     */
    public String getATTACH_FILE_NAME() {
        return ATTACH_FILE_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.ATTACH_FILE_NAME
     *
     * @param ATTACH_FILE_NAME the value for IRP_REPORT_SCHEDULE.ATTACH_FILE_NAME
     */
    public void setATTACH_FILE_NAME(String ATTACH_FILE_NAME) {
        this.ATTACH_FILE_NAME = ATTACH_FILE_NAME == null ? null : ATTACH_FILE_NAME.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.BCC_EMAIL_TYPE
     *
     * @return the value of IRP_REPORT_SCHEDULE.BCC_EMAIL_TYPE
     */
    public BigDecimal getBCC_EMAIL_TYPE() {
        return BCC_EMAIL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.BCC_EMAIL_TYPE
     *
     * @param BCC_EMAIL_TYPE the value for IRP_REPORT_SCHEDULE.BCC_EMAIL_TYPE
     */
    public void setBCC_EMAIL_TYPE(BigDecimal BCC_EMAIL_TYPE) {
        this.BCC_EMAIL_TYPE = BCC_EMAIL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.BCC_EMAIL_VAL
     *
     * @return the value of IRP_REPORT_SCHEDULE.BCC_EMAIL_VAL
     */
    public String getBCC_EMAIL_VAL() {
        return BCC_EMAIL_VAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.BCC_EMAIL_VAL
     *
     * @param BCC_EMAIL_VAL the value for IRP_REPORT_SCHEDULE.BCC_EMAIL_VAL
     */
    public void setBCC_EMAIL_VAL(String BCC_EMAIL_VAL) {
        this.BCC_EMAIL_VAL = BCC_EMAIL_VAL == null ? null : BCC_EMAIL_VAL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.CC_EMAIL_TYPE
     *
     * @return the value of IRP_REPORT_SCHEDULE.CC_EMAIL_TYPE
     */
    public BigDecimal getCC_EMAIL_TYPE() {
        return CC_EMAIL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.CC_EMAIL_TYPE
     *
     * @param CC_EMAIL_TYPE the value for IRP_REPORT_SCHEDULE.CC_EMAIL_TYPE
     */
    public void setCC_EMAIL_TYPE(BigDecimal CC_EMAIL_TYPE) {
        this.CC_EMAIL_TYPE = CC_EMAIL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.CC_EMAIL_VAL
     *
     * @return the value of IRP_REPORT_SCHEDULE.CC_EMAIL_VAL
     */
    public String getCC_EMAIL_VAL() {
        return CC_EMAIL_VAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.CC_EMAIL_VAL
     *
     * @param CC_EMAIL_VAL the value for IRP_REPORT_SCHEDULE.CC_EMAIL_VAL
     */
    public void setCC_EMAIL_VAL(String CC_EMAIL_VAL) {
        this.CC_EMAIL_VAL = CC_EMAIL_VAL == null ? null : CC_EMAIL_VAL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.EMAIL_BODY
     *
     * @return the value of IRP_REPORT_SCHEDULE.EMAIL_BODY
     */
    public String getEMAIL_BODY() {
        return EMAIL_BODY;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.EMAIL_BODY
     *
     * @param EMAIL_BODY the value for IRP_REPORT_SCHEDULE.EMAIL_BODY
     */
    public void setEMAIL_BODY(String EMAIL_BODY) {
        this.EMAIL_BODY = EMAIL_BODY == null ? null : EMAIL_BODY.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.EMAIL_SUBJECT
     *
     * @return the value of IRP_REPORT_SCHEDULE.EMAIL_SUBJECT
     */
    public String getEMAIL_SUBJECT() {
        return EMAIL_SUBJECT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.EMAIL_SUBJECT
     *
     * @param EMAIL_SUBJECT the value for IRP_REPORT_SCHEDULE.EMAIL_SUBJECT
     */
    public void setEMAIL_SUBJECT(String EMAIL_SUBJECT) {
        this.EMAIL_SUBJECT = EMAIL_SUBJECT == null ? null : EMAIL_SUBJECT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.FROM_EMAIL_TYPE
     *
     * @return the value of IRP_REPORT_SCHEDULE.FROM_EMAIL_TYPE
     */
    public BigDecimal getFROM_EMAIL_TYPE() {
        return FROM_EMAIL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.FROM_EMAIL_TYPE
     *
     * @param FROM_EMAIL_TYPE the value for IRP_REPORT_SCHEDULE.FROM_EMAIL_TYPE
     */
    public void setFROM_EMAIL_TYPE(BigDecimal FROM_EMAIL_TYPE) {
        this.FROM_EMAIL_TYPE = FROM_EMAIL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.FROM_EMAIL_VAL
     *
     * @return the value of IRP_REPORT_SCHEDULE.FROM_EMAIL_VAL
     */
    public String getFROM_EMAIL_VAL() {
        return FROM_EMAIL_VAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.FROM_EMAIL_VAL
     *
     * @param FROM_EMAIL_VAL the value for IRP_REPORT_SCHEDULE.FROM_EMAIL_VAL
     */
    public void setFROM_EMAIL_VAL(String FROM_EMAIL_VAL) {
        this.FROM_EMAIL_VAL = FROM_EMAIL_VAL == null ? null : FROM_EMAIL_VAL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.MAIL_SERVER_CODE
     *
     * @return the value of IRP_REPORT_SCHEDULE.MAIL_SERVER_CODE
     */
    public BigDecimal getMAIL_SERVER_CODE() {
        return MAIL_SERVER_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.MAIL_SERVER_CODE
     *
     * @param MAIL_SERVER_CODE the value for IRP_REPORT_SCHEDULE.MAIL_SERVER_CODE
     */
    public void setMAIL_SERVER_CODE(BigDecimal MAIL_SERVER_CODE) {
        this.MAIL_SERVER_CODE = MAIL_SERVER_CODE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.PRINT_IF_NO_DATA_YN
     *
     * @return the value of IRP_REPORT_SCHEDULE.PRINT_IF_NO_DATA_YN
     */
    public String getPRINT_IF_NO_DATA_YN() {
        return PRINT_IF_NO_DATA_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.PRINT_IF_NO_DATA_YN
     *
     * @param PRINT_IF_NO_DATA_YN the value for IRP_REPORT_SCHEDULE.PRINT_IF_NO_DATA_YN
     */
    public void setPRINT_IF_NO_DATA_YN(String PRINT_IF_NO_DATA_YN) {
        this.PRINT_IF_NO_DATA_YN = PRINT_IF_NO_DATA_YN == null ? null : PRINT_IF_NO_DATA_YN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.SEND_IF_NO_DATA_YN
     *
     * @return the value of IRP_REPORT_SCHEDULE.SEND_IF_NO_DATA_YN
     */
    public String getSEND_IF_NO_DATA_YN() {
        return SEND_IF_NO_DATA_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.SEND_IF_NO_DATA_YN
     *
     * @param SEND_IF_NO_DATA_YN the value for IRP_REPORT_SCHEDULE.SEND_IF_NO_DATA_YN
     */
    public void setSEND_IF_NO_DATA_YN(String SEND_IF_NO_DATA_YN) {
        this.SEND_IF_NO_DATA_YN = SEND_IF_NO_DATA_YN == null ? null : SEND_IF_NO_DATA_YN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.TO_EMAIL_TYPE
     *
     * @return the value of IRP_REPORT_SCHEDULE.TO_EMAIL_TYPE
     */
    public BigDecimal getTO_EMAIL_TYPE() {
        return TO_EMAIL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.TO_EMAIL_TYPE
     *
     * @param TO_EMAIL_TYPE the value for IRP_REPORT_SCHEDULE.TO_EMAIL_TYPE
     */
    public void setTO_EMAIL_TYPE(BigDecimal TO_EMAIL_TYPE) {
        this.TO_EMAIL_TYPE = TO_EMAIL_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.TO_EMAIL_VAL
     *
     * @return the value of IRP_REPORT_SCHEDULE.TO_EMAIL_VAL
     */
    public String getTO_EMAIL_VAL() {
        return TO_EMAIL_VAL;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.TO_EMAIL_VAL
     *
     * @param TO_EMAIL_VAL the value for IRP_REPORT_SCHEDULE.TO_EMAIL_VAL
     */
    public void setTO_EMAIL_VAL(String TO_EMAIL_VAL) {
        this.TO_EMAIL_VAL = TO_EMAIL_VAL == null ? null : TO_EMAIL_VAL.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.EMAIL_CONSOLIDATED_YN
     *
     * @return the value of IRP_REPORT_SCHEDULE.EMAIL_CONSOLIDATED_YN
     */
    public String getEMAIL_CONSOLIDATED_YN() {
        return EMAIL_CONSOLIDATED_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.EMAIL_CONSOLIDATED_YN
     *
     * @param EMAIL_CONSOLIDATED_YN the value for IRP_REPORT_SCHEDULE.EMAIL_CONSOLIDATED_YN
     */
    public void setEMAIL_CONSOLIDATED_YN(String EMAIL_CONSOLIDATED_YN) {
        this.EMAIL_CONSOLIDATED_YN = EMAIL_CONSOLIDATED_YN == null ? null : EMAIL_CONSOLIDATED_YN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.DATE_TYPE
     *
     * @return the value of IRP_REPORT_SCHEDULE.DATE_TYPE
     */
    public String getDATE_TYPE() {
        return DATE_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.DATE_TYPE
     *
     * @param DATE_TYPE the value for IRP_REPORT_SCHEDULE.DATE_TYPE
     */
    public void setDATE_TYPE(String DATE_TYPE) {
        this.DATE_TYPE = DATE_TYPE == null ? null : DATE_TYPE.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.SAVE_DATA_TYPE
     *
     * @return the value of IRP_REPORT_SCHEDULE.SAVE_DATA_TYPE
     */
    public BigDecimal getSAVE_DATA_TYPE() {
        return SAVE_DATA_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.SAVE_DATA_TYPE
     *
     * @param SAVE_DATA_TYPE the value for IRP_REPORT_SCHEDULE.SAVE_DATA_TYPE
     */
    public void setSAVE_DATA_TYPE(BigDecimal SAVE_DATA_TYPE) {
        this.SAVE_DATA_TYPE = SAVE_DATA_TYPE;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.REPORT_REF
     *
     * @return the value of IRP_REPORT_SCHEDULE.REPORT_REF
     */
    public String getREPORT_REF() {
        return REPORT_REF;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.REPORT_REF
     *
     * @param REPORT_REF the value for IRP_REPORT_SCHEDULE.REPORT_REF
     */
    public void setREPORT_REF(String REPORT_REF) {
        this.REPORT_REF = REPORT_REF == null ? null : REPORT_REF.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.IS_FCR_YN
     *
     * @return the value of IRP_REPORT_SCHEDULE.IS_FCR_YN
     */
    public BigDecimal getIS_FCR_YN() {
        return IS_FCR_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.IS_FCR_YN
     *
     * @param IS_FCR_YN the value for IRP_REPORT_SCHEDULE.IS_FCR_YN
     */
    public void setIS_FCR_YN(BigDecimal IS_FCR_YN) {
        this.IS_FCR_YN = IS_FCR_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.SECURED_FILE_YN
     *
     * @return the value of IRP_REPORT_SCHEDULE.SECURED_FILE_YN
     */
    public String getSECURED_FILE_YN() {
        return SECURED_FILE_YN;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.SECURED_FILE_YN
     *
     * @param SECURED_FILE_YN the value for IRP_REPORT_SCHEDULE.SECURED_FILE_YN
     */
    public void setSECURED_FILE_YN(String SECURED_FILE_YN) {
        this.SECURED_FILE_YN = SECURED_FILE_YN == null ? null : SECURED_FILE_YN.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_REPORT_SCHEDULE.SECURED_PWD_FIELD_NAME
     *
     * @return the value of IRP_REPORT_SCHEDULE.SECURED_PWD_FIELD_NAME
     */
    public String getSECURED_PWD_FIELD_NAME() {
        return SECURED_PWD_FIELD_NAME;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_REPORT_SCHEDULE.SECURED_PWD_FIELD_NAME
     *
     * @param SECURED_PWD_FIELD_NAME the value for IRP_REPORT_SCHEDULE.SECURED_PWD_FIELD_NAME
     */
    public void setSECURED_PWD_FIELD_NAME(String SECURED_PWD_FIELD_NAME) {
        this.SECURED_PWD_FIELD_NAME = SECURED_PWD_FIELD_NAME == null ? null : SECURED_PWD_FIELD_NAME.trim();
    }
}