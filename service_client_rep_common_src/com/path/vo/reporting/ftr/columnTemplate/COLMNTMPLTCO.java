package com.path.vo.reporting.ftr.columnTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.dbmaps.vo.COLMNTMPLTVO;
import com.path.dbmaps.vo.COLMNTMPLT_PARAM_LINKVO;
import com.path.dbmaps.vo.FTR_TMPLT_EXPRVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;
import com.path.vo.common.smart.SmartCO;

public class COLMNTMPLTCO extends BaseVO {
	private COLMNTMPLTVO colmnTmpltVO = new COLMNTMPLTVO();
	private List<COLMNTMPLTCO> columnDetails=new ArrayList<COLMNTMPLTCO>();
	private List<FTR_TMPLT_EXPRCO> expressions=new ArrayList<FTR_TMPLT_EXPRCO>();
	private List<COLMNTMPLT_PARAM_LINKCO> colmnTmpltParamLink=new ArrayList<COLMNTMPLT_PARAM_LINKCO>();

	private HashMap<BigDecimal, COLMNTMPLTVO> oldColDetMap = new HashMap<BigDecimal, COLMNTMPLTVO>();
	private HashMap<BigDecimal, FTR_TMPLT_EXPRVO> oldExprMap = new HashMap<BigDecimal, FTR_TMPLT_EXPRVO>();
	private HashMap<BigDecimal, COLMNTMPLT_PARAM_LINKVO> oldColParamLinkMap = new HashMap<BigDecimal, COLMNTMPLT_PARAM_LINKVO>();

	private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();

	private LinkedHashMap dbCrtMap = new LinkedHashMap();// saved Criteria in
															// the db
	private LinkedHashMap addCrtMap = new LinkedHashMap();// added Criteria
															// lines
	private HashMap modifCrtMap = new HashMap();// modified Criteria lines
	private HashMap delCrtMap = new HashMap();// deleted Criteria that are saved
												// in the DB
	private HashMap oldCrtMap = new HashMap();// //to store the old Criteria vo
												// for audit

	private String COMP_STR;
	private String FROM_BRANCH_STR;
	private String TO_BRANCH_STR;
	private String FROM_REGION_STR;
	private String TO_REGION_STR;
	private String FROM_DIV_STR;
	private String TO_DIV_STR;
	private String FROM_DEPT_STR;
	private String TO_DEPT_STR;
	private String FROM_UNIT_STR;
	private String TO_UNIT_STR;
	private String COL_TYPE_STR;
	private String FROM_DATE_STR;
	private String TO_DATE_STR;

	private BigDecimal numberAfter;
	private BigDecimal concatKey;
	private BigDecimal newLineNumber;// To handle the reorganize button
	private HashMap operatorsMap = new HashMap();
	private int maxCrtSubLineNb ;
	private ArrayList<SmartCO> smartCOList;
	private String Status;
	private String CURRENCY_STR;
	//used to check if a line is a new one added by reorganize 
	private HashMap<BigDecimal,BigDecimal> newLinesFromReorganize = new HashMap<BigDecimal, BigDecimal>();
	private HashMap<BigDecimal,String> delLinesMap = new HashMap<BigDecimal, String>();

	public HashMap<BigDecimal, String> getDelLinesMap()
	{
	    return delLinesMap;
	}

	public void setDelLinesMap(HashMap<BigDecimal, String> delLinesMap)
	{
	    this.delLinesMap = delLinesMap;
	}

	public HashMap<BigDecimal, BigDecimal> getNewLinesFromReorganize()
	{
	    return newLinesFromReorganize;
	}

	public void setNewLinesFromReorganize(HashMap<BigDecimal, BigDecimal> newLinesFromReorganize)
	{
	    this.newLinesFromReorganize = newLinesFromReorganize;
	}

	public String getCURRENCY_STR()
	{
	    return CURRENCY_STR;
	}

	public void setCURRENCY_STR(String cURRENCYSTR)
	{
	    CURRENCY_STR = cURRENCYSTR;
	}

	private String ProcOsMessage;

	public String getStatus() {
		return Status;
	}

	public void setStatus(String status) {
		Status = status;
	}

	public String getProcOsMessage() {
		return ProcOsMessage;
	}

	public void setProcOsMessage(String procOsMessage) {
		ProcOsMessage = procOsMessage;
	}

	public ArrayList<SmartCO> getSmartCOList() {
		return smartCOList;
	}

	public void setSmartCOList(ArrayList<SmartCO> smartCOList) {
		this.smartCOList = smartCOList;
	}

	public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getBusinessHm() {
		return businessHm;
	}

	public void setBusinessHm(
			HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm) {
		this.businessHm = businessHm;
	}

	public int getMaxCrtSubLineNb() {
		return maxCrtSubLineNb;
	}

	public void setMaxCrtSubLineNb(int maxCrtSubLineNb) {
		this.maxCrtSubLineNb = maxCrtSubLineNb;
	}

	public LinkedHashMap getDbCrtMap() {
		return dbCrtMap;
	}

	public void setDbCrtMap(LinkedHashMap dbCrtMap) {
		this.dbCrtMap = dbCrtMap;
	}

	public LinkedHashMap getAddCrtMap() {
		return addCrtMap;
	}

	public void setAddCrtMap(LinkedHashMap addCrtMap) {
		this.addCrtMap = addCrtMap;
	}

	public HashMap getModifCrtMap() {
		return modifCrtMap;
	}

	public void setModifCrtMap(HashMap modifCrtMap) {
		this.modifCrtMap = modifCrtMap;
	}

	public HashMap getDelCrtMap() {
		return delCrtMap;
	}

	public void setDelCrtMap(HashMap delCrtMap) {
		this.delCrtMap = delCrtMap;
	}

	public HashMap getOldCrtMap() {
		return oldCrtMap;
	}

	public void setOldCrtMap(HashMap oldCrtMap) {
		this.oldCrtMap = oldCrtMap;
	}

	public HashMap getOperatorsMap() {
		return operatorsMap;
	}

	public void setOperatorsMap(HashMap operatorsMap) {
		this.operatorsMap = operatorsMap;
	}

	public BigDecimal getNewLineNumber() {
		return newLineNumber;
	}

	public void setNewLineNumber(BigDecimal newLineNumber) {
		this.newLineNumber = newLineNumber;
	}

	public BigDecimal getConcatKey() {
		return concatKey;
	}

	public void setConcatKey(BigDecimal concatKey) {
		this.concatKey = concatKey;
	}

	public BigDecimal getNumberAfter() {
		return numberAfter;
	}

	public void setNumberAfter(BigDecimal numberAfter) {
		this.numberAfter = numberAfter;
	}

	public COLMNTMPLTVO getColmnTmpltVO() {
		return colmnTmpltVO;
	}

	public void setColmnTmpltVO(COLMNTMPLTVO colmnTmpltVO) {
		this.colmnTmpltVO = colmnTmpltVO;
	}

	public List<COLMNTMPLTCO> getColumnDetails() {
		return columnDetails;
	}

	public void setColumnDetails(List<COLMNTMPLTCO> columnDetails) {
		this.columnDetails = columnDetails;
	}

	public List<FTR_TMPLT_EXPRCO> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<FTR_TMPLT_EXPRCO> expressions) {
		this.expressions = expressions;
	}

	public List<COLMNTMPLT_PARAM_LINKCO> getColmnTmpltParamLink() {
		return colmnTmpltParamLink;
	}

	public void setColmnTmpltParamLink(
			List<COLMNTMPLT_PARAM_LINKCO> colmnTmpltParamLink) {
		this.colmnTmpltParamLink = colmnTmpltParamLink;
	}

	public HashMap<BigDecimal, COLMNTMPLTVO> getOldColDetMap() {
		return oldColDetMap;
	}

	public void setOldColDetMap(HashMap<BigDecimal, COLMNTMPLTVO> oldColDetMap) {
		this.oldColDetMap = oldColDetMap;
	}

	public String getFROM_DATE_STR() {
		return FROM_DATE_STR;
	}

	public void setFROM_DATE_STR(String fROMDATESTR) {
		FROM_DATE_STR = fROMDATESTR;
	}

	public String getTO_DATE_STR() {
		return TO_DATE_STR;
	}

	public void setTO_DATE_STR(String tODATESTR) {
		TO_DATE_STR = tODATESTR;
	}

	public String getCOL_TYPE_STR() {
		return COL_TYPE_STR;
	}

	public void setCOL_TYPE_STR(String cOLTYPESTR) {
		COL_TYPE_STR = cOLTYPESTR;
	}

	public String getCOMP_STR() {
		return COMP_STR;
	}

	public void setCOMP_STR(String cOMPSTR) {
		COMP_STR = cOMPSTR;
	}

	public String getFROM_BRANCH_STR() {
		return FROM_BRANCH_STR;
	}

	public void setFROM_BRANCH_STR(String fROMBRANCHSTR) {
		FROM_BRANCH_STR = fROMBRANCHSTR;
	}

	public String getTO_BRANCH_STR() {
		return TO_BRANCH_STR;
	}

	public void setTO_BRANCH_STR(String tOBRANCHSTR) {
		TO_BRANCH_STR = tOBRANCHSTR;
	}

	public String getFROM_REGION_STR() {
		return FROM_REGION_STR;
	}

	public void setFROM_REGION_STR(String fROMREGIONSTR) {
		FROM_REGION_STR = fROMREGIONSTR;
	}

	public String getTO_REGION_STR() {
		return TO_REGION_STR;
	}

	public void setTO_REGION_STR(String tOREGIONSTR) {
		TO_REGION_STR = tOREGIONSTR;
	}

	public String getFROM_DIV_STR() {
		return FROM_DIV_STR;
	}

	public void setFROM_DIV_STR(String fROMDIVSTR) {
		FROM_DIV_STR = fROMDIVSTR;
	}

	public String getTO_DIV_STR() {
		return TO_DIV_STR;
	}

	public void setTO_DIV_STR(String tODIVSTR) {
		TO_DIV_STR = tODIVSTR;
	}

	public String getFROM_DEPT_STR() {
		return FROM_DEPT_STR;
	}

	public void setFROM_DEPT_STR(String fROMDEPTSTR) {
		FROM_DEPT_STR = fROMDEPTSTR;
	}

	public String getTO_DEPT_STR() {
		return TO_DEPT_STR;
	}

	public void setTO_DEPT_STR(String tODEPTSTR) {
		TO_DEPT_STR = tODEPTSTR;
	}

	public String getFROM_UNIT_STR() {
		return FROM_UNIT_STR;
	}

	public void setFROM_UNIT_STR(String fROMUNITSTR) {
		FROM_UNIT_STR = fROMUNITSTR;
	}

	public String getTO_UNIT_STR() {
		return TO_UNIT_STR;
	}

	public void setTO_UNIT_STR(String tOUNITSTR) {
		TO_UNIT_STR = tOUNITSTR;
	}

	public HashMap<BigDecimal, FTR_TMPLT_EXPRVO> getOldExprMap() {
		return oldExprMap;
	}

	public void setOldExprMap(HashMap<BigDecimal, FTR_TMPLT_EXPRVO> oldExprMap) {
		this.oldExprMap = oldExprMap;
	}

	public HashMap<BigDecimal, COLMNTMPLT_PARAM_LINKVO> getOldColParamLinkMap() {
		return oldColParamLinkMap;
	}

	public void setOldColParamLinkMap(
			HashMap<BigDecimal, COLMNTMPLT_PARAM_LINKVO> oldColParamLinkMap) {
		this.oldColParamLinkMap = oldColParamLinkMap;
	}
}