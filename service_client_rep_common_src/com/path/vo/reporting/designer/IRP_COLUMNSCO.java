package com.path.vo.reporting.designer;

import com.path.lib.vo.BaseVO;

public class IRP_COLUMNSCO extends BaseVO {
    
    /**
     * This field corresponds to the column name of the fields existing in the selected object (table/view/synonym)
     */
    private String COLUMN_NAME;

    /**
     * This field corresponds to type of the database object : Table / View / Synonym
     */
    private String COLUMN_TYPE;

    /**
     * @return the value of COLUMN_NAME
     */
    public String getCOLUMN_NAME() {
        return COLUMN_NAME;
    }

    /**
     * @param COLUMN_NAME the value for COLUMN_NAME
     */
    public void setCOLUMN_NAME(String COLUMN_NAME) {
        this.COLUMN_NAME = COLUMN_NAME == null ? null : COLUMN_NAME.trim();
    }

    /**
     *
     * @return the value of COLUMN_TYPE
     */
    public String getCOLUMN_TYPE() {
        return COLUMN_TYPE;
    }

    /**
     * @param COLUMN_TYPE the value for COLUMN_TYPE
     */
    public void setCOLUMN_TYPE(String COLUMN_TYPE) {
        this.COLUMN_TYPE = COLUMN_TYPE == null ? null : COLUMN_TYPE.trim();
    }
}