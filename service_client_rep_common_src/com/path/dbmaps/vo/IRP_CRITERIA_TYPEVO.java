package com.path.dbmaps.vo;

public class IRP_CRITERIA_TYPEVO extends IRP_CRITERIA_TYPEVOKey {
    /**
     * This field corresponds to the database column IRP_CRITERIA_TYPE.CRITERIA_TYPE_DESCRIPTION
     */
    private String CRITERIA_TYPE_DESCRIPTION;

    /**
     * This field corresponds to the database column IRP_CRITERIA_TYPE.COMPONENT
     */
    private String COMPONENT;

    /**
     * This field corresponds to the database column IRP_CRITERIA_TYPE.TABLE1
     */
    private String TABLE1;

    /**
     * This field corresponds to the database column IRP_CRITERIA_TYPE.TABLE2
     */
    private String TABLE2;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_CRITERIA_TYPE.CRITERIA_TYPE_DESCRIPTION
     *
     * @return the value of IRP_CRITERIA_TYPE.CRITERIA_TYPE_DESCRIPTION
     */
    public String getCRITERIA_TYPE_DESCRIPTION() {
        return CRITERIA_TYPE_DESCRIPTION;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_CRITERIA_TYPE.CRITERIA_TYPE_DESCRIPTION
     *
     * @param CRITERIA_TYPE_DESCRIPTION the value for IRP_CRITERIA_TYPE.CRITERIA_TYPE_DESCRIPTION
     */
    public void setCRITERIA_TYPE_DESCRIPTION(String CRITERIA_TYPE_DESCRIPTION) {
        this.CRITERIA_TYPE_DESCRIPTION = CRITERIA_TYPE_DESCRIPTION == null ? null : CRITERIA_TYPE_DESCRIPTION.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_CRITERIA_TYPE.COMPONENT
     *
     * @return the value of IRP_CRITERIA_TYPE.COMPONENT
     */
    public String getCOMPONENT() {
        return COMPONENT;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_CRITERIA_TYPE.COMPONENT
     *
     * @param COMPONENT the value for IRP_CRITERIA_TYPE.COMPONENT
     */
    public void setCOMPONENT(String COMPONENT) {
        this.COMPONENT = COMPONENT == null ? null : COMPONENT.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_CRITERIA_TYPE.TABLE1
     *
     * @return the value of IRP_CRITERIA_TYPE.TABLE1
     */
    public String getTABLE1() {
        return TABLE1;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_CRITERIA_TYPE.TABLE1
     *
     * @param TABLE1 the value for IRP_CRITERIA_TYPE.TABLE1
     */
    public void setTABLE1(String TABLE1) {
        this.TABLE1 = TABLE1 == null ? null : TABLE1.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column IRP_CRITERIA_TYPE.TABLE2
     *
     * @return the value of IRP_CRITERIA_TYPE.TABLE2
     */
    public String getTABLE2() {
        return TABLE2;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column IRP_CRITERIA_TYPE.TABLE2
     *
     * @param TABLE2 the value for IRP_CRITERIA_TYPE.TABLE2
     */
    public void setTABLE2(String TABLE2) {
        this.TABLE2 = TABLE2 == null ? null : TABLE2.trim();
    }
}