package com.path.vo.reporting.ftr.columnTemplate;

import java.math.BigDecimal;

import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.lib.vo.BaseVO;

public class FTR_TMPLT_EXPRCO extends BaseVO {

	private FTR_TMPLT_EXPRVO ftrTmpltExprVO=new FTR_TMPLT_EXPRVO();
	private BigDecimal concatKey;
	private String VALUE_READONLY;
	private String EXP_LINE_NO_READONLY;
	private String exprOrGLByLine;
	private BigDecimal EXP_ORDER;
	private BigDecimal newLineNumber;

	public BigDecimal getNewLineNumber()
	{
	    return newLineNumber;
	}

	public void setNewLineNumber(BigDecimal newLineNumber)
	{
	    this.newLineNumber = newLineNumber;
	}

	public FTR_TMPLT_EXPRVO getFtrTmpltExprVO() {
		return ftrTmpltExprVO;
	}

	public void setFtrTmpltExprVO(FTR_TMPLT_EXPRVO ftrTmpltExprVO) {
		this.ftrTmpltExprVO = ftrTmpltExprVO;
	}

	public String getEXP_LINE_NO_READONLY() {
		return EXP_LINE_NO_READONLY;
	}

	public void setEXP_LINE_NO_READONLY(String eXPLINENOREADONLY) {
		EXP_LINE_NO_READONLY = eXPLINENOREADONLY;
	}

	public String getVALUE_READONLY() {
		return VALUE_READONLY;
	}

	public void setVALUE_READONLY(String vALUEREADONLY) {
		VALUE_READONLY = vALUEREADONLY;
	}

	public BigDecimal getConcatKey() {
		return concatKey;
	}

	public void setConcatKey(BigDecimal concatKey) {
		this.concatKey = concatKey;
	}

	public String getExprOrGLByLine() {
		return exprOrGLByLine;
	}

	public void setExprOrGLByLine(String exprOrGLByLine) {
		this.exprOrGLByLine = exprOrGLByLine;
	}

	public BigDecimal getEXP_ORDER() {
		return EXP_ORDER;
	}

	public void setEXP_ORDER(BigDecimal eXPORDER) {
		EXP_ORDER = eXPORDER;
	}
}
