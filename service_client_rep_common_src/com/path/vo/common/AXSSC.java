package com.path.vo.common;

import java.math.BigDecimal;
import java.util.List;

import com.path.struts2.lib.common.BaseSC;

public class AXSSC extends BaseSC
{

    private String APP_NAME;
    private BigDecimal BRANCH_CODE;
    private BigDecimal COMP_CODE;
    private String PROG_REF;
    private String USER_ID;
	private List<String> PROG_REFS;

	public List<String> getPROG_REFS() {
		return PROG_REFS;
	}

	public void setPROG_REFS(List<String> pROGREFS) {
		PROG_REFS = pROGREFS;
	}
    public String getAPP_NAME() {
        return APP_NAME;
    }

    public void setAPP_NAME(String APP_NAME) {
        this.APP_NAME = APP_NAME == null ? null : APP_NAME.trim();
    }

    public BigDecimal getBRANCH_CODE() {
        return BRANCH_CODE;
    }

    public void setBRANCH_CODE(BigDecimal BRANCH_CODE) {
        this.BRANCH_CODE = BRANCH_CODE;
    }

    public BigDecimal getCOMP_CODE() {
        return COMP_CODE;
    }

    public void setCOMP_CODE(BigDecimal COMP_CODE) {
        this.COMP_CODE = COMP_CODE;
    }

    public String getPROG_REF() {
        return PROG_REF;
    }

    public void setPROG_REF(String PROG_REF) {
        this.PROG_REF = PROG_REF == null ? null : PROG_REF.trim();
    }

    public String getUSER_ID() {
        return USER_ID;
    }

    public void setUSER_ID(String USER_ID) {
        this.USER_ID = USER_ID == null ? null : USER_ID.trim();
    }

}
