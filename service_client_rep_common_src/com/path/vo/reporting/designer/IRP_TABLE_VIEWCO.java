package com.path.vo.reporting.designer;

import com.path.lib.vo.BaseVO;

public class IRP_TABLE_VIEWCO extends BaseVO {
    /**
     * This field corresponds to the table or view name returned from the select
     */
    private String OBJECT_DB_NAME;

    /**
     * This field corresponds to type of the database object : Table / View / Synonym
     */
    private String OBJECT_TYPE;

    /**
     * @return the value of OBJECT_DB_NAME
     */
    public String getOBJECT_DB_NAME() {
        return OBJECT_DB_NAME;
    }

    /**
     * @param OBJECT_DB_NAME the value for OBJECT_DB_NAME
     */
    public void setOBJECT_DB_NAME(String OBJECT_DB_NAME) {
        this.OBJECT_DB_NAME = OBJECT_DB_NAME == null ? null : OBJECT_DB_NAME.trim();
    }

    /**
     *
     * @return the value of OBJECT_TYPE
     */
    public String getOBJECT_TYPE() {
        return OBJECT_TYPE;
    }

    /**
     * @param OBJECT_ALIAS the value for OBJECT_TYPE
     */
    public void setOBJECT_TYPE(String OBJECT_TYPE) {
        this.OBJECT_TYPE = OBJECT_TYPE == null ? null : OBJECT_TYPE.trim();
    }
}