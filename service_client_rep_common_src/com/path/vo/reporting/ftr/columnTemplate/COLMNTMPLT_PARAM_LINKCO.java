package com.path.vo.reporting.ftr.columnTemplate;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashMap;

import com.path.dbmaps.vo.COLMNTMPLT_PARAM_LINKVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;

public class COLMNTMPLT_PARAM_LINKCO extends BaseVO implements Serializable {
	private COLMNTMPLT_PARAM_LINKVO columntmpltParamLinkVO = new COLMNTMPLT_PARAM_LINKVO();
	private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
	private String fromFiltCrt;
	private BigDecimal concatKey;
	private Boolean INCLUDE_CHK;
	private String CRITERIA_NAME;
	private String CODE1_NAME;
	private String CODE2_NAME;
	private String OPERATOR_NAME;
	private String TABLE_NAME1;
	private String TABLE_NAME2;
	private String CRITERIA_TYPE_CODE;
	private String COMPONENT;
	private String CRI_TYPE;
	private String VALUE1;
	private String VALUE2;
	private String CODE1;
	private String CRI_LINE_GL;
	private BigDecimal newLineNumber;
	private String INCLUDE_TRANS;

	public BigDecimal getNewLineNumber() {
		return newLineNumber;
	}

	public void setNewLineNumber(BigDecimal newLineNumber) {
		this.newLineNumber = newLineNumber;
	}

	public String getFromFiltCrt() {
		return fromFiltCrt;
	}

	public void setFromFiltCrt(String fromFiltCrt) {
		this.fromFiltCrt = fromFiltCrt;
	}

	public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getBusinessHm() {
		return businessHm;
	}

	public void setBusinessHm(
			HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm) {
		this.businessHm = businessHm;
	}

	public String getCRI_LINE_GL() {
		return CRI_LINE_GL;
	}

	public void setCRI_LINE_GL(String cRILINEGL) {
		CRI_LINE_GL = cRILINEGL;
	}

	public String getCODE1() {

		return CODE1;
	}

	public void setCODE1(String cODE1) {
		CODE1 = cODE1;
	}

	public String getVALUE1() {
		return VALUE1;
	}

	public void setVALUE1(String vALUE1) {
		VALUE1 = vALUE1;
	}

	public String getVALUE2() {
		return VALUE2;
	}

	public void setVALUE2(String vALUE2) {
		VALUE2 = vALUE2;
	}

	public String getCRI_TYPE() {
		return CRI_TYPE;
	}

	public void setCRI_TYPE(String cRITYPE) {
		CRI_TYPE = cRITYPE;
	}

	public String getCODE1_NAME() {
		return CODE1_NAME;
	}

	public void setCODE1_NAME(String cODE1NAME) {
		CODE1_NAME = cODE1NAME;
	}

	public String getCODE2_NAME() {
		return CODE2_NAME;
	}

	public void setCODE2_NAME(String cODE2NAME) {
		CODE2_NAME = cODE2NAME;
	}

	public String getCRITERIA_NAME() {
		return CRITERIA_NAME;
	}

	public void setCRITERIA_NAME(String cRITERIANAME) {
		CRITERIA_NAME = cRITERIANAME;
	}

	public Boolean getINCLUDE_CHK() {
		return INCLUDE_CHK;
	}

	public void setINCLUDE_CHK(Boolean iNCLUDECHK) {
		INCLUDE_CHK = iNCLUDECHK;
	}

	public BigDecimal getConcatKey() {
		return concatKey;
	}

	public void setConcatKey(BigDecimal concatKey) {
		this.concatKey = concatKey;
	}

	public String getOPERATOR_NAME() {
		return OPERATOR_NAME;
	}

	public void setOPERATOR_NAME(String oPERATORNAME) {
		OPERATOR_NAME = oPERATORNAME;
	}

	public String getTABLE_NAME1() {
		return TABLE_NAME1;
	}

	public void setTABLE_NAME1(String tABLENAME1) {
		TABLE_NAME1 = tABLENAME1;
	}

	public String getTABLE_NAME2() {
		return TABLE_NAME2;
	}

	public void setTABLE_NAME2(String tABLENAME2) {
		TABLE_NAME2 = tABLENAME2;
	}

	public String getCRITERIA_TYPE_CODE() {
		return CRITERIA_TYPE_CODE;
	}

	public void setCRITERIA_TYPE_CODE(String cRITERIATYPECODE) {
		CRITERIA_TYPE_CODE = cRITERIATYPECODE;
	}

	public String getCOMPONENT() {
		return COMPONENT;
	}

	public void setCOMPONENT(String cOMPONENT) {
		COMPONENT = cOMPONENT;
	}

	public COLMNTMPLT_PARAM_LINKVO getColumntmpltParamLinkVO() {
		return columntmpltParamLinkVO;
	}

	public void setColumntmpltParamLinkVO(
			COLMNTMPLT_PARAM_LINKVO columntmpltParamLinkVO) {
		this.columntmpltParamLinkVO = columntmpltParamLinkVO;
	}

	public void setINCLUDE_TRANS(String iNCLUDE_TRANS)
	{
	    INCLUDE_TRANS = iNCLUDE_TRANS;
	}

	public String getINCLUDE_TRANS()
	{
	    return INCLUDE_TRANS;
	}
}
