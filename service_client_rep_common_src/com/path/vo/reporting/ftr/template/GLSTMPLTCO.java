package com.path.vo.reporting.ftr.template;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

import com.path.dbmaps.vo.GLSTMPLTVO;
import com.path.dbmaps.vo.SYS_PARAM_SCREEN_DISPLAYVO;
import com.path.lib.vo.BaseVO;
import com.path.vo.common.smart.SmartCO;

public class GLSTMPLTCO extends BaseVO implements Serializable
{
    public GLSTMPLTVO glstmpltVO = new GLSTMPLTVO();
    private BigDecimal newLineNumber; // to handle the reorganize button

    private HashMap<String, String> CalcTypeMap = new HashMap<String, String>();
    private HashMap<String, String> operatorsMap = new HashMap<String, String>();
    private LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();// saved
    // lines
    // in
    // the
    // db
    private LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap = new LinkedHashMap<BigDecimal, GLSTMPLTCO>();// added
    // new
    // lines
    private HashMap<BigDecimal, GLSTMPLTCO> modifLinesMap = new HashMap<BigDecimal, GLSTMPLTCO>();// modified
    // saved
    // lines
    private HashMap<BigDecimal, GLSTMPLTCO> delLinesMap = new HashMap<BigDecimal, GLSTMPLTCO>();// deleted
    // lines
    // that
    // are
    // saved
    // in the DB
    private HashMap<BigDecimal, GLSTMPLTCO> oldLinesMap = new HashMap<BigDecimal, GLSTMPLTCO>();// to
    // store
    // the old
    // value of
    // the line
    // before
    // updating
    // it(for
    // audit)

    // added all the below linked lists from irp_template_linesvo
    private LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> dbGLMap = new LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO>();// saved
    // GL
    // in
    // the
    // db
    private LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> addGLMap = new LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO>();// added
    // GL
    // lines
    private HashMap<BigDecimal, GLSTMPLTGLSDETCO> modifGLMap = new HashMap<BigDecimal, GLSTMPLTGLSDETCO>();// modified
    // GL
    // lines
    private HashMap<BigDecimal, GLSTMPLTGLSDETCO> delGLMap = new HashMap<BigDecimal, GLSTMPLTGLSDETCO>();// deleted
    // GL
    // that
    // are
    // saved
    // in
    // the
    // DB
    private HashMap<BigDecimal, GLSTMPLTGLSDETCO> oldGLMap = new HashMap<BigDecimal, GLSTMPLTGLSDETCO>();// to
    // store
    // the
    // old
    // vo
    // for
    // audit

    private LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> dbExprMap = new LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO>();// saved
    // Expr
    // in
    // the
    // db
    private LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> addExprMap = new LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO>();// added
    // Expr
    // lines
    private HashMap<BigDecimal, FTR_TMPLT_EXPRCO> modifExprMap = new HashMap<BigDecimal, FTR_TMPLT_EXPRCO>();// modified
    // Expr
    // lines
    private HashMap<BigDecimal, FTR_TMPLT_EXPRCO> delExprMap = new HashMap<BigDecimal, FTR_TMPLT_EXPRCO>();// deleted
    // Expr
    // that
    // are
    // saved
    // in
    // the DB
    private HashMap<BigDecimal, FTR_TMPLT_EXPRCO> oldExprMap = new HashMap<BigDecimal, FTR_TMPLT_EXPRCO>();// //to
    // store
    // the
    // old
    // vo
    // for
    // audit

    private LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> dbCrtMap = new LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>();// saved
    // Expr
    // in
    // the
    // db
    private LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> addCrtMap = new LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>();// added
    // Expr
    // lines
    private HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> modifCrtMap = new HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>();// modified
    // Expr
    // lines
    private HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> delCrtMap = new HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>();// deleted
    // Expr
    // that
    // are
    // saved
    // in
    // the DB
    private HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> oldCrtMap = new HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO>();// //to
    // store
    // the
    // old
    // vo
    // for
    // audit

    private LinkedHashMap<String, FTR_TMPL_REFCO> relatedReportsList = new LinkedHashMap<String, FTR_TMPL_REFCO>(); // list
    // related
    // reports
    private LinkedHashMap<String, FTR_TMPL_REFCO> addedReportsList = new LinkedHashMap<String, FTR_TMPL_REFCO>(); // added
    // related
    // reports
    private LinkedHashMap<String, FTR_TMPL_REFCO> deletedReportsList = new LinkedHashMap<String, FTR_TMPL_REFCO>(); // deleted
    // related
    // reports
    private LinkedHashMap<String, FTR_TMPL_REFCO> updatedReportsList = new LinkedHashMap<String, FTR_TMPL_REFCO>(); // updated
    // related
    // reports

    private LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> dbMismatchMap = new LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO>();
    private LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> addMismatchMap = new LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO>();
    private LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> delMismatchMap = new LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO>();
    private LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> oldMismatchMap = new LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO>();

    private int MaxSubLineNb;
    private int maxGLSubLineNb;
    private int maxCrtSubLineNb;
    private BigDecimal concatKey;
    private String CURRENCYStr;
    private String BOTTOM_BORDERStr;
    private String CSV;
    private String FCRStr;
    private String PRINTEDStr;
    private ArrayList<SmartCO> smartCOList;
    private HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm = new HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO>();
    private int numberAfter;
    private String Status;
    private String ProcOsMessage;
    private String glTypeStr;
    private String csvStr;
    private String forRoundStr;
    private String isSubTotalStr;
    private boolean anyModif;
    private String templateTitle;
    
    
    public String getTemplateTitle()
    {
        return templateTitle;
    }

    public void setTemplateTitle(String templateTitle)
    {
        this.templateTitle = templateTitle;
    }

    // used to check if a line is a new one added by reorganize
    private HashMap<BigDecimal, BigDecimal> newLinesFromReorganize = new HashMap<BigDecimal, BigDecimal>();

    public HashMap<BigDecimal, BigDecimal> getNewLinesFromReorganize()
    {
	return newLinesFromReorganize;
    }

    public void setNewLinesFromReorganize(HashMap<BigDecimal, BigDecimal> newLinesFromReorganize)
    {
	this.newLinesFromReorganize = newLinesFromReorganize;
    }

    public boolean isAnyModif()
    {
	return anyModif;
    }

    public void setAnyModif(boolean anyModif)
    {
	this.anyModif = anyModif;
    }

    public String getForRoundStr()
    {
	return forRoundStr;
    }

    public void setForRoundStr(String forRoundStr)
    {
	this.forRoundStr = forRoundStr;
    }

    public String getGlTypeStr()
    {
	return glTypeStr;
    }

    public void setGlTypeStr(String glTypeStr)
    {
	this.glTypeStr = glTypeStr;
    }

    public String getCsvStr()
    {
	return csvStr;
    }

    public void setCsvStr(String csvStr)
    {
	this.csvStr = csvStr;
    }

    public String getIsSubTotalStr()
    {
	return isSubTotalStr;
    }

    public void setIsSubTotalStr(String isSubTotalStr)
    {
	this.isSubTotalStr = isSubTotalStr;
    }

    public String getStatus()
    {
	return Status;
    }

    public void setStatus(String status)
    {
	Status = status;
    }

    public String getProcOsMessage()
    {
	return ProcOsMessage;
    }

    public void setProcOsMessage(String procOsMessage)
    {
	ProcOsMessage = procOsMessage;
    }

    public LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> getDbMismatchMap()
    {
	return dbMismatchMap;
    }

    public void setDbMismatchMap(LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> dbMismatchMap)
    {
	this.dbMismatchMap = dbMismatchMap;
    }

    public LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> getAddMismatchMap()
    {
	return addMismatchMap;
    }

    public void setAddMismatchMap(LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> addMismatchMap)
    {
	this.addMismatchMap = addMismatchMap;
    }

    public LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> getDelMismatchMap()
    {
	return delMismatchMap;
    }

    public void setDelMismatchMap(LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> delMismatchMap)
    {
	this.delMismatchMap = delMismatchMap;
    }

    public LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> getOldMismatchMap()
    {
	return oldMismatchMap;
    }

    public void setOldMismatchMap(LinkedHashMap<BigDecimal, FTR_MISMATCH_REPORTCO> oldMismatchMap)
    {
	this.oldMismatchMap = oldMismatchMap;
    }

    public BigDecimal getNewLineNumber()
    {
	return newLineNumber;
    }

    public int getNumberAfter()
    {
	return numberAfter;
    }

    public void setNumberAfter(int numberAfter)
    {
	this.numberAfter = numberAfter;
    }

    public void setNewLineNumber(BigDecimal newLineNumber)
    {
	this.newLineNumber = newLineNumber;
    }

    public HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> getBusinessHm()
    {
	return businessHm;
    }

    public void setBusinessHm(HashMap<String, SYS_PARAM_SCREEN_DISPLAYVO> businessHm)
    {
	this.businessHm = businessHm;
    }

    public ArrayList<SmartCO> getSmartCOList()
    {
	return smartCOList;
    }

    public void setSmartCOList(ArrayList<SmartCO> smartCOList)
    {
	this.smartCOList = smartCOList;
    }

    public String getFCRStr()
    {
	return FCRStr;
    }

    public void setFCRStr(String fCRStr)
    {
	FCRStr = fCRStr;
    }

    public LinkedHashMap<String, FTR_TMPL_REFCO> getAddedReportsList()
    {
	return addedReportsList;
    }

    public void setAddedReportsList(LinkedHashMap<String, FTR_TMPL_REFCO> addedReportsList)
    {
	this.addedReportsList = addedReportsList;
    }

    public LinkedHashMap<String, FTR_TMPL_REFCO> getDeletedReportsList()
    {
	return deletedReportsList;
    }

    public void setDeletedReportsList(LinkedHashMap<String, FTR_TMPL_REFCO> deletedReportsList)
    {
	this.deletedReportsList = deletedReportsList;
    }

    public LinkedHashMap<String, FTR_TMPL_REFCO> getUpdatedReportsList()
    {
	return updatedReportsList;
    }

    public void setUpdatedReportsList(LinkedHashMap<String, FTR_TMPL_REFCO> updatedReportsList)
    {
	this.updatedReportsList = updatedReportsList;
    }

    public LinkedHashMap<String, FTR_TMPL_REFCO> getRelatedReportsList()
    {
	return relatedReportsList;
    }

    public void setRelatedReportsList(LinkedHashMap<String, FTR_TMPL_REFCO> relatedReportsList)
    {
	this.relatedReportsList = relatedReportsList;
    }

    public String getCSV()
    {
	return CSV;
    }

    public void setCSV(String cSV)
    {
	CSV = cSV;
    }

    public String getCURRENCYStr()
    {
	return CURRENCYStr;
    }

    public void setCURRENCYStr(String cURRENCYStr)
    {
	CURRENCYStr = cURRENCYStr;
    }

    public String getBOTTOM_BORDERStr()
    {
	return BOTTOM_BORDERStr;
    }

    public void setBOTTOM_BORDERStr(String bOTTOMBORDERStr)
    {
	BOTTOM_BORDERStr = bOTTOMBORDERStr;
    }

    public BigDecimal getConcatKey()
    {
	return concatKey;
    }

    public void setConcatKey(BigDecimal concatKey)
    {
	this.concatKey = concatKey;
    }

    // end adding lists from irp_template_linesvo

    public String getPRINTEDStr()
    {
	return PRINTEDStr;
    }

    public LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> getDbGLMap()
    {
	return dbGLMap;
    }

    public void setDbGLMap(LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> dbGLMap)
    {
	this.dbGLMap = dbGLMap;
    }

    public LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> getAddGLMap()
    {
	return addGLMap;
    }

    public void setAddGLMap(LinkedHashMap<BigDecimal, GLSTMPLTGLSDETCO> addGLMap)
    {
	this.addGLMap = addGLMap;
    }

    public HashMap<BigDecimal, GLSTMPLTGLSDETCO> getModifGLMap()
    {
	return modifGLMap;
    }

    public void setModifGLMap(HashMap<BigDecimal, GLSTMPLTGLSDETCO> modifGLMap)
    {
	this.modifGLMap = modifGLMap;
    }

    public HashMap<BigDecimal, GLSTMPLTGLSDETCO> getDelGLMap()
    {
	return delGLMap;
    }

    public void setDelGLMap(HashMap<BigDecimal, GLSTMPLTGLSDETCO> delGLMap)
    {
	this.delGLMap = delGLMap;
    }

    public HashMap<BigDecimal, GLSTMPLTGLSDETCO> getOldGLMap()
    {
	return oldGLMap;
    }

    public void setOldGLMap(HashMap<BigDecimal, GLSTMPLTGLSDETCO> oldGLMap)
    {
	this.oldGLMap = oldGLMap;
    }

    public LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> getDbExprMap()
    {
	return dbExprMap;
    }

    public void setDbExprMap(LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> dbExprMap)
    {
	this.dbExprMap = dbExprMap;
    }

    public LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> getAddExprMap()
    {
	return addExprMap;
    }

    public void setAddExprMap(LinkedHashMap<BigDecimal, FTR_TMPLT_EXPRCO> addExprMap)
    {
	this.addExprMap = addExprMap;
    }

    public HashMap<BigDecimal, FTR_TMPLT_EXPRCO> getModifExprMap()
    {
	return modifExprMap;
    }

    public void setModifExprMap(HashMap<BigDecimal, FTR_TMPLT_EXPRCO> modifExprMap)
    {
	this.modifExprMap = modifExprMap;
    }

    public HashMap<BigDecimal, FTR_TMPLT_EXPRCO> getDelExprMap()
    {
	return delExprMap;
    }

    public void setDelExprMap(HashMap<BigDecimal, FTR_TMPLT_EXPRCO> delExprMap)
    {
	this.delExprMap = delExprMap;
    }

    public HashMap<BigDecimal, FTR_TMPLT_EXPRCO> getOldExprMap()
    {
	return oldExprMap;
    }

    public void setOldExprMap(HashMap<BigDecimal, FTR_TMPLT_EXPRCO> oldExprMap)
    {
	this.oldExprMap = oldExprMap;
    }

    public LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> getDbCrtMap()
    {
	return dbCrtMap;
    }

    public void setDbCrtMap(LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> dbCrtMap)
    {
	this.dbCrtMap = dbCrtMap;
    }

    public LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> getAddCrtMap()
    {
	return addCrtMap;
    }

    public void setAddCrtMap(LinkedHashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> addCrtMap)
    {
	this.addCrtMap = addCrtMap;
    }

    public HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> getModifCrtMap()
    {
	return modifCrtMap;
    }

    public void setModifCrtMap(HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> modifCrtMap)
    {
	this.modifCrtMap = modifCrtMap;
    }

    public HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> getDelCrtMap()
    {
	return delCrtMap;
    }

    public void setDelCrtMap(HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> delCrtMap)
    {
	this.delCrtMap = delCrtMap;
    }

    public HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> getOldCrtMap()
    {
	return oldCrtMap;
    }

    public void setOldCrtMap(HashMap<BigDecimal, GLSTMPLT_PARAM_LINKCO> oldCrtMap)
    {
	this.oldCrtMap = oldCrtMap;
    }

    public int getMaxSubLineNb()
    {
	return MaxSubLineNb;
    }

    public void setMaxSubLineNb(int maxSubLineNb)
    {
	MaxSubLineNb = maxSubLineNb;
    }

    public int getMaxGLSubLineNb()
    {
	return maxGLSubLineNb;
    }

    public void setMaxGLSubLineNb(int maxGLSubLineNb)
    {
	this.maxGLSubLineNb = maxGLSubLineNb;
    }

    public int getMaxCrtSubLineNb()
    {
	return maxCrtSubLineNb;
    }

    public void setMaxCrtSubLineNb(int maxCrtSubLineNb)
    {
	this.maxCrtSubLineNb = maxCrtSubLineNb;
    }

    public void setPRINTEDStr(String pRINTEDStr)
    {
	PRINTEDStr = pRINTEDStr;
    }

    public GLSTMPLTVO getGlstmpltVO()
    {
	return glstmpltVO;
    }

    public void setGlstmpltVO(GLSTMPLTVO glstmpltVO)
    {
	this.glstmpltVO = glstmpltVO;
    }

    public HashMap<BigDecimal, GLSTMPLTCO> getOldLinesMap()
    {
	return oldLinesMap;
    }

    public void setOldLinesMap(HashMap<BigDecimal, GLSTMPLTCO> oldLinesMap)
    {
	this.oldLinesMap = oldLinesMap;
    }

    public LinkedHashMap<BigDecimal, GLSTMPLTCO> getDbLinesMap()
    {
	return dbLinesMap;
    }

    public void setDbLinesMap(LinkedHashMap<BigDecimal, GLSTMPLTCO> dbLinesMap)
    {
	this.dbLinesMap = dbLinesMap;
    }

    public LinkedHashMap<BigDecimal, GLSTMPLTCO> getAddLinesMap()
    {
	return addLinesMap;
    }

    public void setAddLinesMap(LinkedHashMap<BigDecimal, GLSTMPLTCO> addLinesMap)
    {
	this.addLinesMap = addLinesMap;
    }

    public HashMap<BigDecimal, GLSTMPLTCO> getModifLinesMap()
    {
	return modifLinesMap;
    }

    public void setModifLinesMap(HashMap<BigDecimal, GLSTMPLTCO> modifLinesMap)
    {
	this.modifLinesMap = modifLinesMap;
    }

    public HashMap<BigDecimal, GLSTMPLTCO> getDelLinesMap()
    {
	return delLinesMap;
    }

    public void setDelLinesMap(HashMap<BigDecimal, GLSTMPLTCO> delLinesMap)
    {
	this.delLinesMap = delLinesMap;
    }

    private ArrayList<Object> dispValArrList = new ArrayList<Object>();

    public ArrayList<Object> getDispValArrList()
    {
	return dispValArrList;
    }

    public void setDispValArrList(ArrayList<Object> dispValArrList)
    {
	this.dispValArrList = dispValArrList;
    }

    public HashMap<String, String> getCalcTypeMap()
    {
	return CalcTypeMap;
    }

    public void setCalcTypeMap(HashMap<String, String> calcTypeMap)
    {
	CalcTypeMap = calcTypeMap;
    }

    public HashMap<String, String> getOperatorsMap()
    {
	return operatorsMap;
    }

    public void setOperatorsMap(HashMap<String, String> operatorsMap)
    {
	this.operatorsMap = operatorsMap;
    }
}
