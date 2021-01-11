package com.path.bo.reporting.ftr.fcr.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.path.bo.common.CommonLibBO;
import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.audit.AuditConstant;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.bo.reporting.ftr.fcr.FcrBO;
import com.path.dao.reporting.ftr.fcr.FcrDAO;
import com.path.dbmaps.vo.AXSVO;
import com.path.dbmaps.vo.AXSVOKey;
import com.path.dbmaps.vo.FTR_OPTVO;
import com.path.dbmaps.vo.IRP_FCR_FIXED_COLSVO;
import com.path.dbmaps.vo.IRP_FCR_REPORTSVO;
import com.path.dbmaps.vo.OPTVO;
import com.path.dbmaps.vo.OPTVOKey;
import com.path.dbmaps.vo.OPT_EXTENDEDVO;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.dbmaps.vo.S_APPVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.PathPropertyUtil;
import com.path.lib.common.util.StringUtil;
import com.path.vo.common.CheckRequiredCO;
import com.path.vo.common.audit.AuditRefCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.IRP_FCR_REPORTSCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.template.AXSCO;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.GLSTMPLTSC;

public class FcrBOImpl extends BaseBO implements FcrBO
{
    private FcrDAO fcrDAO;
    private CommonLibBO commonLibBO;

    public CommonLibBO getCommonLibBO()
    {
	return commonLibBO;
    }

    public void setCommonLibBO(CommonLibBO commonLibBO)
    {
	this.commonLibBO = commonLibBO;
    }

    public FcrDAO getFcrDAO()
    {
	return fcrDAO;
    }

    public void setFcrDAO(FcrDAO fcrDAO)
    {
	this.fcrDAO = fcrDAO;
    }

    public List<FTR_OPTCO> loadFcrList(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return fcrDAO.loadFcrList(commonDetailsSC);
    }

    public int retFcrListCount(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return fcrDAO.retFcrListCount(commonDetailsSC);
    }

    public List<CommonDetailsVO> retParentRefList(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return fcrDAO.retParentRefList(commonDetailsSC);
    }

    public int retParentRefListCount(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return fcrDAO.retParentRefListCount(commonDetailsSC);
    }

    public int checkProgRef(CommonDetailsSC commonDetailsSC) throws BaseException
    {
	return fcrDAO.checkProgRef(commonDetailsSC);
    }

    public void saveFcr(FTR_OPTVO fcrVO) throws BaseException
    {
	genericDAO.insert(fcrVO);
    }

    public void updateFcr(FTR_OPTVO fcrVO) throws BaseException
    {
	Integer row = fcrDAO.updateFcr(fcrVO);
	if(row == null || row < 1)
	{
	    throw new BOException(MessageCodes.RECORD_CHANGED);
	}
    }

    public void deleteFcr(FTR_OPTVO fcrVO) throws BaseException
    {
	genericDAO.delete(fcrVO);
    }

    public void saveOpt1(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.saveOpt1(fcrCO);
    }

    public void saveOpt2(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.saveOpt2(fcrCO);
    }

    public void saveOpt3(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.saveOpt3(fcrCO);
    }

    public void updateOpt1(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.updateOpt1(fcrCO);
    }

    public void updateOpt2(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.updateOpt2(fcrCO);
    }

    public void updateOpt3(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.updateOpt3(fcrCO);
    }

    public int retProgOrder(FTR_OPTCO fcrCO) throws BaseException
    {
	return fcrDAO.retProgOrder(fcrCO);
    }

    public void deleteOpt(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.deleteOpt(fcrCO);
    }

    public void deleteOpts(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.deleteOpts(fcrCO);
    }

    public void saveAxs(AXSVO axsVO) throws BaseException
    {
	genericDAO.insert(axsVO);
    }

    public void deleteAxs(AXSCO axsCO) throws BaseException
    {
	fcrDAO.deleteAxs(axsCO);
    }

    public FTR_OPTCO returnDynamicReportByRef(FTR_OPTCO optCO) throws BaseException
    {
	return fcrDAO.returnDynamicReportByRef(optCO);
    }

    public void saveFCR(FTR_OPTCO fcrCO, BigDecimal compCode, BigDecimal branchCode, String appName, String userName,
	    HashMap<String, String> optTrans, String mode, String pageRef, String lang) throws BaseException
    {
	FTR_OPTVO fcrVO = fcrCO.getFtrOptVO();
	AuditRefCO refCO = fcrVO.getAuditRefCO();

	CheckRequiredCO checkRequiredCO = new CheckRequiredCO();
	checkRequiredCO.setObj_value(fcrCO);
	checkRequiredCO.setOpt(pageRef);
	checkRequiredCO.setCompCode(compCode);
	checkRequiredCO.setLanguage(lang);
	checkRequiredCO.setApp(appName);

	commonLibBO.checkRequired(checkRequiredCO);
	//if value null, set it to 0 for the procedures
	if(NumberUtil.isEmptyDecimal(fcrCO.getFtrOptVO().getREP_CURRENCY()))
	{
	    fcrCO.getFtrOptVO().setREP_CURRENCY(BigDecimal.ZERO);
	}
	if(ConstantsCommon.CREATE_MODE.equals(mode)) // new
	{
	    // save in FTR_OPT
	    if(RepConstantsCommon.FCR_SUMMARIZED.equals(fcrVO.getREP_TYPE()))
	    {
		fcrVO.setGROUP_BY(RepConstantsCommon.N);
	    }
	    saveFcr(fcrVO);

	    if(!("").equals(fcrVO.getPROG_REF()))
	    {

		// save in AXS table
		AXSVO axsVO = new AXSVO();
		axsVO.setCOMP_CODE(compCode);
		axsVO.setBRANCH_CODE(branchCode);
		axsVO.setAPP_NAME(appName);
		axsVO.setPROG_REF(fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION);// read
											   // value
											   // from
											   // constantcommon.java
		axsVO.setUSER_ID(userName);
		axsVO.setCREATED_BY(userName);
		axsVO.setAUTHORIZED_BY(userName);
		axsVO.setSTATUS("P");
		axsVO.setDIRECT_ACCESS(userName);
		axsVO.setDATE_CREATED(commonLibBO.addSystemTimeToDate(fcrCO.getRUNNING_DATE()));
		axsVO.setDATE_AUTHORIZED(commonLibBO.addSystemTimeToDate(fcrCO.getRUNNING_DATE()));
		axsVO.setTO_BE_DELETED("N");
		saveAxs(axsVO);

		// save in OPT table 3 different records
		fcrVO.setAPP_NAME(appName);
		String lsRef = fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION;
		fcrCO.setLS_REF(lsRef);
		saveOpt1(fcrCO);

		String lsRefp = fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION + "P";
		String lsPrint = fcrVO.getBRIEF_NAME_ENG() + " - " + optTrans.get("PR");
		fcrCO.setLS_REF(lsRefp);
		fcrCO.setLS_PRINT(lsPrint);
		saveOpt2(fcrCO);

		String lsRefSav = fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION + "SAV";
		String lsSav = fcrVO.getBRIEF_NAME_ENG() + " - " + optTrans.get("SV");
		fcrCO.setLS_REF(lsRefSav);
		fcrCO.setLS_SAV(lsSav);
		saveOpt3(fcrCO);

		OPT_EXTENDEDVO optExtendedVO = new OPT_EXTENDEDVO();
		optExtendedVO.setAPP_NAME(appName);
		optExtendedVO.setPROG_REF(fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION);
		optExtendedVO.setOPT_URL("/path/reportsRet/dynRepParamsAction_loadReportFromMenu?menu="
			+ fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION);
		saveOptExtended(optExtendedVO);
	    }
	    // apply audit
	    refCO.setOperationType(AuditConstant.CREATED);
	    auditBO.callAudit(null, fcrVO, refCO);

	}
	else
	// update
	{
	    // fcrVO.setCODE(fcrVO.getCODE());
	    if(RepConstantsCommon.FCR_SUMMARIZED.equals(fcrVO.getREP_TYPE()))
	    {
		fcrVO.setGROUP_BY(RepConstantsCommon.N);
	    }
	    updateFcr(fcrVO);

	    if(fcrVO.getPROG_REF() != null && !fcrVO.getPROG_REF().equals(""))
	    {
		updateOPTs(fcrCO, appName);
	    }
	    // apply audit
	    refCO.setOperationType(AuditConstant.UPDATE);
	    auditBO.callAudit(fcrVO.getAuditObj(), fcrVO, refCO);
	    auditBO.insertAudit(refCO);
	}
    }

    public void updateOPTs(FTR_OPTCO fcrCO, String appName)
    {
	updateOPTs(fcrCO, appName, false);
    }

    public void updateOPTs(FTR_OPTCO fcrCO, String appName, boolean fromUpDown)
    {
	try
	{
	    FTR_OPTVO fcrVO = fcrCO.getFtrOptVO();
	    // update table OPT
	    fcrVO.setAPP_NAME(appName);
	    String lsRefp;
	    String lsRefSav;
	    String lsRef;
	    lsRefp = fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION + "P";
	    lsRefSav = fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION + "SAV";
	    lsRef = fcrVO.getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION;

	    if(fcrVO.getBRIEF_NAME_ENG() == null)
	    {
		fcrVO.setBRIEF_NAME_ENG("");
	    }
	    fcrCO.setLS_UPPER_NAME(fcrVO.getBRIEF_NAME_ENG().toUpperCase());
	    // int progOrder=retProgOrder(fcrCO);
	    // fcrCO.setPROG_ORDER(new BigDecimal(progOrder));

	    fcrVO.setPROG_REF(lsRef);
	    updateOpt1(fcrCO);

	    String lsPrint = fcrVO.getBRIEF_NAME_ENG() + "- PRINT";

	    fcrCO.setLS_PRINT(lsPrint);
	    fcrVO.setPROG_REF(lsRefp);
	    updateOpt2(fcrCO);

	    String lsSav = fcrVO.getBRIEF_NAME_ENG() + "- SAVE";

	    fcrCO.setLS_SAV(lsSav);
	    fcrVO.setPROG_REF(lsRefSav);
	    updateOpt3(fcrCO);
	}
	catch(BaseException e)
	{
	    log.error(e, "");
	}
    }

    public void updateOptVOBeforeUpdate(OPTVO optVO, String progDesc, String progRef)
    {
	optVO.setPROG_REF(progRef);

	optVO.setPROG_DESC(progDesc);
	optVO.setPROG_DESC_FR(progDesc);
	optVO.setMENU_TITLE_ENG(progDesc);
	optVO.setMENU_TITLE_ARAB(progDesc);
	optVO.setMENU_TITLE_FR(progDesc);
	optVO.setMENU_TITLE_FA(progDesc);
	optVO.setPROG_DESC_ARAB(progDesc);
	optVO.setPROG_DESC_FA(progDesc);
	optVO.setMENU_TITLE_TK(progDesc);
	optVO.setPROG_DESC_TK(progDesc);
	optVO.setMENU_TITLE_RU(progDesc);
	optVO.setPROG_DESC_RU(progDesc);
    }

    public void deleteFCR(BigDecimal compCode, BigDecimal branchCode, String appName, String userName, FTR_OPTCO fcrCO,String pageRef)
	    throws BaseException
    {

	// delete from opt_extended
	FTR_OPTCO ftrCO = new FTR_OPTCO();
	ftrCO.getFtrOptVO().setAPP_NAME(appName);
	ArrayList<String> progRefs = new ArrayList<String>();
	progRefs.add(fcrCO.getFtrOptVO().getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION);
	ftrCO.setPROG_REFS(progRefs);
	fcrCO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,fcrCO.getHttpSessionIdForLink());
	deleteOptExtended(ftrCO);

	FTR_OPTVO fcrVO = new FTR_OPTVO();
	fcrVO.setCODE(fcrCO.getFtrOptVO().getCODE());

	deleteFcr(fcrVO);

	// delete from table AXS
	AXSVOKey axsVO = new AXSVOKey();
	axsVO.setCOMP_CODE(compCode);
	axsVO.setBRANCH_CODE(branchCode);
	axsVO.setAPP_NAME(appName);
	axsVO.setPROG_REF(fcrCO.getFtrOptVO().getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION);
	//axsVO.setUSER_ID(userName);
	//deleteGenAxs(axsVO);
	axsVO.applyTraceProps(ConstantsCommon.REP_APP_NAME, userName, pageRef,fcrCO.getHttpSessionIdForLink());
	fcrDAO.deleteAXSByCompBranchApp(axsVO);
	// delete from opt table
	String progRefDel = fcrCO.getFtrOptVO().getPROG_REF() + ConstantsCommon.OPT_FCR_EXTENSION + "%";
	fcrCO.getFtrOptVO().setPROG_REF(progRefDel);
	fcrCO.getFtrOptVO().setAPP_NAME(appName);
	deleteOpt(fcrCO);

	// apply audit
	auditBO.callAudit(fcrCO.getFtrOptVO(), fcrCO.getFtrOptVO(), fcrCO.getAuditRefCO());
	auditBO.insertAudit(fcrCO.getAuditRefCO());
    }

    public void saveReportOpts(BigDecimal compCode, BigDecimal branchCode, String appName, String userName,
	    IRP_AD_HOC_REPORTCO reportCO, Date runningDate) throws BaseException
    {
	PTH_CTRL1VO pthCtrl1VO = commonLibBO.returnPthCtrl1();
	
	// save in AXS table
	if(pthCtrl1VO.getREP_ALLOW_AXS_YN().equals(BigDecimal.ONE))
	{
	    saveAllReportAxs(fillAxsVOProps(compCode, branchCode, appName, userName, runningDate, reportCO
		    .getPROG_REF(), RepConstantsCommon.AXS_STATUS_P, RepConstantsCommon.AXS_TO_BE_DELETED_N));
	}
	
	// Save in Opt table

	FTR_OPTCO fcrCO = new FTR_OPTCO();
	fcrCO.getFtrOptVO().setAPP_NAME(appName);
	fcrCO.getFtrOptVO().setBRIEF_NAME_ENG(reportCO.getREPORT_NAME());
	fcrCO.getFtrOptVO().setPARENT_REF(reportCO.getPARENT_REF());

	String repName = reportCO.getREPORT_NAME();
	String progRef = reportCO.getPROG_REF();
	OPTVO optVO = new OPTVO();
	optVO.setPROG_REF(progRef);
	optVO.setPROG_DESC(repName);
	optVO.setAPP_NAME(appName);
	optVO.setALLOW_ADD_OPTIONS("0");
	optVO.setADD_OPT_REF(progRef);
	optVO.setAUDIT_SAVE("0");
	optVO.setAUDIT_DELETE("0");
	optVO.setAUDIT_RETRIEVE("0");
	optVO.setMAIN_OPTION_1("0");
	optVO.setMAIN_OPTION_2("1");
	optVO.setDYNAMIC_OPT("2");
	optVO.setPROG_DESC_FR(repName);
	optVO.setMENU_TITLE_ENG(repName);
	optVO.setMENU_TITLE_ARAB(repName);
	optVO.setMENU_TITLE_FR(repName);
	optVO.setMENU_TITLE_FA(repName);
	optVO.setPROG_DESC_ARAB(repName);
	optVO.setPROG_DESC_FA(repName);
	optVO.setMENU_TITLE_TK(repName);
	optVO.setPROG_DESC_TK(repName);
	optVO.setMENU_TITLE_RU(repName);
	optVO.setPROG_DESC_RU(repName);
	optVO.setPARENT_REF(reportCO.getPARENT_REF());
	optVO.setPROG_TYPE("P");
	optVO.setPROG_ORDER(new BigDecimal(retProgOrder(fcrCO)));
	optVO.setAPP_VERSION(BigDecimal.ONE);
	optVO.setDISP_ORDER(new BigDecimal(7));
	
	//check if the the is_web_yn==2 in the table s_app then set the categ_id of the opt the same as its parent 
	S_APPVO sAppVO = new S_APPVO();
	sAppVO.setAPP_NAME(reportCO.getAPP_NAME());
	sAppVO = commonLibBO.returnApplication(sAppVO);
	if(BigDecimal.valueOf(2).equals(sAppVO.getIS_WEB_YN()))
	{
		if(RepConstantsCommon.ROOT.equals(reportCO.getPARENT_REF()))
		{
			optVO.setCATEG_ID(reportCO.getCATEGORY_ID());
		}
		else
		{
			OPTVO zVO=new OPTVO();
		    zVO.setAPP_NAME(reportCO.getAPP_NAME());
		    zVO.setPROG_REF(reportCO.getPARENT_REF());
		    zVO=(OPTVO) genericDAO.selectByPK(zVO);
		    optVO.setCATEG_ID(zVO.getCATEG_ID());//bia			
		}
	}

	saveAllReportOpts(optVO,true);

	// save in optExtended
	OPT_EXTENDEDVO optExtendedVO = new OPT_EXTENDEDVO();
	optExtendedVO.setAPP_NAME(appName);
	optExtendedVO.setPROG_REF(reportCO.getPROG_REF());

	optExtendedVO.setOPT_URL("REPORT");
	
	saveOptExtended(optExtendedVO);
    }

    public void saveAllReportOpts(OPTVO optVO) throws BaseException
    {
	saveAllReportOpts( optVO, false);
    }
    
    public void saveAllReportOpts(OPTVO optVO,Boolean isFromSaveRep) throws BaseException
    {

	String progRef = optVO.getPROG_REF();
	
	// save Main record
	genericDAO.insert(optVO);
	// in case we are saving a report from uplDownl or from designer then
	// empty the categId for the 7 others opt in case it was fill(case of is_Web_yn=2 in s_app)
	if(isFromSaveRep)
	{
	    optVO.setCATEG_ID(null);
	}

	/* //commonLibBO.returnTranslErrorMessage(code, language) */
	// Delete
	updateOptVOBeforInsert(optVO, progRef,progRef+ RepConstantsCommon.OPT_DEL_D);
	// Modify
	updateOptVOBeforInsert(optVO, progRef,progRef+ RepConstantsCommon.OPT_MOD_M);
	// Save
	updateOptVOBeforInsert(optVO, progRef, progRef+RepConstantsCommon.OPT_SV_SV);
	// Save as
	updateOptVOBeforInsert(optVO, progRef, progRef+RepConstantsCommon.OPT_SA_SA);
	// Send mail
	updateOptVOBeforInsert(optVO, progRef,progRef+RepConstantsCommon.OPT_SM_SM);
	// Scheduler
	updateOptVOBeforInsert(optVO, progRef, progRef+RepConstantsCommon.OPT_SC_SC);
	// Print
	updateOptVOBeforInsert(optVO, progRef, progRef+RepConstantsCommon.OPT_PR_PR);

    }

    public void updateOptVOBeforInsert(OPTVO optVO, String progRef, String currOpt) throws BaseException
    {
	String extEN = "";
	String extFR = "";
	String extAR = "";
	String extFA = "";
	String extTK = "";
	String extRU = "";
	OPTVO optVONew = new OPTVO();
	try
	{
	    String encodingType = PathPropertyUtil.getPathRemotingProp("PathRemoting", "default.database.encoding");
	    log.debug(">>>>>>>>>>>>>>>>>>fcrBO>>>>>>>>>>>>>>>   encodingType===" + encodingType);
	    if(encodingType == null)
	    {
		encodingType = "";
	    }
	    if((progRef + RepConstantsCommon.OPT_DEL_D).equals(currOpt))
	    {
		extEN = RepConstantsCommon.OPT_DEL_EN;
		extAR = RepConstantsCommon.OPT_DEL_AR;

		if(RepConstantsCommon.ENCODING_TYPE_ASCII.equals(encodingType))
		{
		    extFR = new String(RepConstantsCommon.OPT_DEL_FR.getBytes(encodingType), encodingType);
		    extFA = new String(RepConstantsCommon.OPT_DEL_FA.getBytes(encodingType), encodingType);
		    extTK = new String(RepConstantsCommon.OPT_DEL_TK.getBytes(encodingType), encodingType);
		    extRU = new String(RepConstantsCommon.OPT_DEL_RU.getBytes(encodingType), encodingType);
		}
		else
		{
		    extFR = RepConstantsCommon.OPT_DEL_FR;
		    extFA = RepConstantsCommon.OPT_DEL_FA;
		    extTK = RepConstantsCommon.OPT_DEL_TK;
		    extRU = RepConstantsCommon.OPT_DEL_RU;
		}

	    }
	    else if((progRef + RepConstantsCommon.OPT_MOD_M).equals(currOpt))
	    {
		extEN = RepConstantsCommon.OPT_MOD_EN;
		extAR = RepConstantsCommon.OPT_MOD_AR;
		if(RepConstantsCommon.ENCODING_TYPE_ASCII.equals(encodingType))
		{
		    extFR = new String(RepConstantsCommon.OPT_MOD_FR.getBytes(encodingType), encodingType);
		    extFA = new String(RepConstantsCommon.OPT_MOD_FA.getBytes(encodingType), encodingType);
		    extTK = new String(RepConstantsCommon.OPT_MOD_TK.getBytes(encodingType), encodingType);
		    extRU = new String(RepConstantsCommon.OPT_MOD_RU.getBytes(encodingType), encodingType);
		}
		else
		{
		    extFR = RepConstantsCommon.OPT_MOD_FR;
		    extFA = RepConstantsCommon.OPT_MOD_FA;
		    extTK = RepConstantsCommon.OPT_MOD_TK;
		    extRU = RepConstantsCommon.OPT_MOD_RU;
		}
	    }
	    else if((progRef + RepConstantsCommon.OPT_SV_SV).equals(currOpt))
	    {
		extEN = RepConstantsCommon.OPT_SV_EN;
		extAR = RepConstantsCommon.OPT_SV_AR;
		if(RepConstantsCommon.ENCODING_TYPE_ASCII.equals(encodingType))
		{
		    extFR = new String(RepConstantsCommon.OPT_SV_FR.getBytes(encodingType), encodingType);
		    extFA = new String(RepConstantsCommon.OPT_SV_FA.getBytes(encodingType), encodingType);
		    extTK = new String(RepConstantsCommon.OPT_SV_TK.getBytes(encodingType), encodingType);
		    extRU = new String(RepConstantsCommon.OPT_SV_RU.getBytes(encodingType), encodingType);
		}
		else
		{
		    extFR = RepConstantsCommon.OPT_SV_FR;
		    extFA = RepConstantsCommon.OPT_SV_FA;
		    extTK = RepConstantsCommon.OPT_SV_TK;
		    extRU = RepConstantsCommon.OPT_SV_RU;
		}
	    }
	    else if((progRef + RepConstantsCommon.OPT_SA_SA).equals(currOpt))
	    {
		extEN = RepConstantsCommon.OPT_SA_EN;
		extAR = RepConstantsCommon.OPT_SA_AR;
		if(RepConstantsCommon.ENCODING_TYPE_ASCII.equals(encodingType))
		{
		    extFR = new String(RepConstantsCommon.OPT_SA_FR.getBytes(encodingType), encodingType);
		    extFA = new String(RepConstantsCommon.OPT_SA_FA.getBytes(encodingType), encodingType);
		    extTK = new String(RepConstantsCommon.OPT_SA_TK.getBytes(encodingType), encodingType);
		    extRU = new String(RepConstantsCommon.OPT_SA_RU.getBytes(encodingType), encodingType);
		}
		else
		{
		    extFR = RepConstantsCommon.OPT_SA_FR;
		    extFA = RepConstantsCommon.OPT_SA_FA;
		    extTK = RepConstantsCommon.OPT_SA_TK;
		    extRU = RepConstantsCommon.OPT_SA_RU;
		}
	    }
	    else if((progRef + RepConstantsCommon.OPT_SM_SM).equals(currOpt))
	    {
		extEN = RepConstantsCommon.OPT_SM_EN;
		extAR = RepConstantsCommon.OPT_SM_AR;
		if(RepConstantsCommon.ENCODING_TYPE_ASCII.equals(encodingType))
		{
		    extFR = new String(RepConstantsCommon.OPT_SM_FR.getBytes(encodingType), encodingType);
		    extFA = new String(RepConstantsCommon.OPT_SM_FA.getBytes(encodingType), encodingType);
		    extTK = new String(RepConstantsCommon.OPT_SM_TK.getBytes(encodingType), encodingType);
		    extRU = new String(RepConstantsCommon.OPT_SM_RU.getBytes(encodingType), encodingType);
		}
		else
		{
		    extFR = RepConstantsCommon.OPT_SM_FR;
		    extFA = RepConstantsCommon.OPT_SM_FA;
		    extTK = RepConstantsCommon.OPT_SM_TK;
		    extRU = RepConstantsCommon.OPT_SM_RU;
		}
	    }
	    else if((progRef + RepConstantsCommon.OPT_SC_SC).equals(currOpt))
	    {

		extEN = RepConstantsCommon.OPT_SC_EN;
		extAR = RepConstantsCommon.OPT_SC_AR;
		if(RepConstantsCommon.ENCODING_TYPE_ASCII.equals(encodingType))
		{
		    extFR = new String(RepConstantsCommon.OPT_SC_FR.getBytes(encodingType), encodingType);
		    extFA = new String(RepConstantsCommon.OPT_SC_FA.getBytes(encodingType), encodingType);
		    extTK = new String(RepConstantsCommon.OPT_SC_TK.getBytes(encodingType), encodingType);
		    extRU = new String(RepConstantsCommon.OPT_SC_RU.getBytes(encodingType), encodingType);
		}
		else
		{
		    extFR = RepConstantsCommon.OPT_SC_FR;
		    extFA = RepConstantsCommon.OPT_SC_FA;
		    extTK = RepConstantsCommon.OPT_SC_TK;
		    extRU = RepConstantsCommon.OPT_SC_RU;
		}
	    }
	    else if((progRef + RepConstantsCommon.OPT_PR_PR).equals(currOpt))
	    {
		extEN = RepConstantsCommon.OPT_PR_EN;
		extAR = RepConstantsCommon.OPT_PR_AR;
		if(RepConstantsCommon.ENCODING_TYPE_ASCII.equals(encodingType))
		{
		    extFR = new String(RepConstantsCommon.OPT_PR_FR.getBytes(encodingType), encodingType);
		    extFA = new String(RepConstantsCommon.OPT_PR_FA.getBytes(encodingType), encodingType);
		    extTK = new String(RepConstantsCommon.OPT_PR_TK.getBytes(encodingType), encodingType);
		    extRU = new String(RepConstantsCommon.OPT_PR_RU.getBytes(encodingType), encodingType);
		}
		else
		{
		    extFR = RepConstantsCommon.OPT_PR_FR;
		    extFA = RepConstantsCommon.OPT_PR_FA;
		    extTK = RepConstantsCommon.OPT_PR_TK;
		    extRU = RepConstantsCommon.OPT_PR_RU;
		}
	    }
	    //IF THE OPT IS THE ONE WITHOUT THE EXTENSIONS,ADD THE PARENT_REF
	    else if (progRef.equals(currOpt))
	    {
		optVONew.setPARENT_REF(optVO.getPARENT_REF());
		
		//check if the the is_web_yn==2 in the table s_app then set the categ_id of the opt the same as its parent 
		S_APPVO sAppVO = new S_APPVO();
		sAppVO.setAPP_NAME(optVO.getAPP_NAME());
		sAppVO = commonLibBO.returnApplication(sAppVO);
		if(BigDecimal.valueOf(2).equals(sAppVO.getIS_WEB_YN()))
		{
		    OPTVO zVO=new OPTVO();
		    zVO.setAPP_NAME(optVO.getAPP_NAME());
		    zVO.setPROG_REF(optVO.getPARENT_REF());
		    zVO=(OPTVO) genericDAO.selectByPK(zVO);
		    optVONew.setCATEG_ID(zVO.getCATEG_ID());
		}

	    }
	    //Add PARENT_REF for opt with extensions
	    if(!progRef.equals(currOpt)) {
	    	optVONew.setPARENT_REF(optVO.getPARENT_REF());
	    }

	}
	catch(UnsupportedEncodingException e)
	{
	    log.error(e, "");
	    throw new BOException(e);
	}
	catch(Exception e)
	{
	    log.error(e, "");
	    throw new BOException(e);
	}


	optVONew.setAPP_NAME(optVO.getAPP_NAME());

	optVONew.setALLOW_ADD_OPTIONS("0");
	optVONew.setADD_OPT_REF(currOpt);
	optVONew.setAUDIT_SAVE("0");
	optVONew.setAUDIT_DELETE("0");
	optVONew.setAUDIT_RETRIEVE("0");
	optVONew.setMAIN_OPTION_1("0");
	optVONew.setMAIN_OPTION_2("1");
	optVONew.setDYNAMIC_OPT("2");

	optVONew.setPROG_REF(currOpt);

	optVONew.setPROG_DESC(((optVO.getPROG_DESC() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(optVO
		.getPROG_DESC(), " - " + extEN, RepConstantsCommon.PROG_DESC_SIZE)));

	optVONew.setMENU_TITLE_ENG(((optVO.getMENU_TITLE_ENG() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getMENU_TITLE_ENG(), " - " + extEN, RepConstantsCommon.MENU_TITLE_ENG_SIZE)));

	optVONew.setMENU_TITLE_FR(((optVO.getMENU_TITLE_FR() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getMENU_TITLE_FR(), " - " + extFR, RepConstantsCommon.MENU_TITLE_FR_SIZE)));

	optVONew.setPROG_DESC_FR(((optVO.getPROG_DESC_FR() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getPROG_DESC_FR(), " - " + extFR, RepConstantsCommon.PROG_DESC_FR_SIZE)));

	optVONew.setMENU_TITLE_ARAB(((optVO.getMENU_TITLE_ARAB() == null) ? optVO.getPROG_DESC()
		: returnStrpStrWithExtn(optVO.getMENU_TITLE_ARAB(), " - " + extAR,
			RepConstantsCommon.MENU_TITLE_ARAB_SIZE)));

	optVONew.setPROG_DESC_ARAB(((optVO.getPROG_DESC_ARAB() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getPROG_DESC_ARAB(), " - " + extAR, RepConstantsCommon.PROG_DESC_ARAB_SIZE)));

	optVONew.setMENU_TITLE_FA(((optVO.getMENU_TITLE_FA() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getMENU_TITLE_FA(), " - " + extFA, RepConstantsCommon.MENU_TITLE_FA_SIZE)));

	optVONew.setPROG_DESC_FA(((optVO.getPROG_DESC_FA() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getPROG_DESC_FA(), " - " + extFA, RepConstantsCommon.PROG_DESC_FA_SIZE)));

	optVONew.setMENU_TITLE_TK(((optVO.getMENU_TITLE_TK() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getMENU_TITLE_TK(), " - " + extTK, RepConstantsCommon.MENU_TITLE_TK_SIZE)));

	optVONew.setPROG_DESC_TK(((optVO.getPROG_DESC_TK() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getPROG_DESC_TK(), " - " + extTK, RepConstantsCommon.PROG_DESC_TK_SIZE)));

	optVONew.setMENU_TITLE_RU(((optVO.getMENU_TITLE_RU() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getMENU_TITLE_RU(), " - " + extRU, RepConstantsCommon.MENU_TITLE_RU_SIZE)));
	optVONew.setPROG_DESC_RU(((optVO.getPROG_DESC_RU() == null) ? optVO.getPROG_DESC() : returnStrpStrWithExtn(
		optVO.getPROG_DESC_RU(), " - " + extRU, RepConstantsCommon.PROG_DESC_RU_SIZE)));

	genericDAO.insert(optVONew);

    }
    
    /**
     * function that checks if a value exceeds the db related column size and
     * removes extra characters
     * @param desc
     * @param ext
     * @param size
     * @return
     */
    public String returnStrpStrWithExtn (String desc, String ext, int size)
    {
       return StringUtil.substring(desc, size - ext.length()).concat(ext);
    }

    
    public void saveAllReportAxs(AXSVO axsVO) throws BaseException
    {
	String progRef = axsVO.getPROG_REF();
	// save Main record
	saveAxs(axsVO);
	// Delete
	axsVO.setPROG_REF(progRef + "D");
	saveAxs(axsVO);
	// Modify
	axsVO.setPROG_REF(progRef + "M");
	saveAxs(axsVO);
	// Save
	axsVO.setPROG_REF(progRef + "SV");
	saveAxs(axsVO);
	// Save As
	axsVO.setPROG_REF(progRef + "SA");
	saveAxs(axsVO);
	// Send mail
	axsVO.setPROG_REF(progRef + "SM");
	saveAxs(axsVO);
	// Scheduler
	axsVO.setPROG_REF(progRef + "SC");
	saveAxs(axsVO);
	// Print
	axsVO.setPROG_REF(progRef + "PR");
	saveAxs(axsVO);
    }

    private void saveOptExtended(OPT_EXTENDEDVO optExtendedVO) throws BaseException
    {
	genericDAO.insert(optExtendedVO);
    }

    @Override
    public void deleteOptExtended(FTR_OPTCO fcrCO) throws BaseException
    {
	fcrDAO.deleteOptExtended(fcrCO);
    }

    public FTR_OPTCO retrieveFcr(CommonDetailsSC sc) throws BaseException
    {
	return fcrDAO.retrieveFcr(sc);
    }

    public List<FTR_OPTCO> retSavedOptsByProgRef(FTR_OPTCO optCO) throws BaseException
    {
	return fcrDAO.retSavedOptsByProgRef(optCO);
    }

    public void saveMissedOpts(IRP_AD_HOC_REPORTCO reportCO, List<String> missedOpts,String optDesc) throws BaseException
    {
	// String repName=reportCO.getREPORT_NAME();
	String progRef = reportCO.getPROG_REF();

	String currOpt;
	OPTVOKey voExists = new OPTVOKey();
	voExists.setAPP_NAME(reportCO.getAPP_NAME());
	voExists.setPROG_REF(reportCO.getPROG_REF());		
	OPTVO optVOdb = (OPTVO) genericDAO.selectByPK(voExists);
	/*
	 * checking added for the case where the original opt doesn't exist but
	 * one of its extensions exist in the db
	 */
	if(optVOdb==null)
	{
	    optVOdb = new OPTVO();
	    optVOdb.setPROG_REF(reportCO.getPROG_REF());
	    optVOdb.setAPP_NAME(reportCO.getAPP_NAME());
	    optVOdb.setPARENT_REF(reportCO.getPARENT_REF());
	    String repName=reportCO.getREPORT_NAME();
	    optVOdb.setMENU_TITLE_ENG(repName);
	    optVOdb.setMENU_TITLE_ARAB(repName);
	    optVOdb.setMENU_TITLE_FR(repName);
	    optVOdb.setMENU_TITLE_FA(repName);
	    optVOdb.setMENU_TITLE_TK(repName);
	    optVOdb.setMENU_TITLE_RU(repName);
	    optVOdb.setPROG_DESC(repName);
	    optVOdb.setPROG_DESC_FR(repName);
	    optVOdb.setPROG_DESC_ARAB(repName);
	    optVOdb.setPROG_DESC_FA(repName);
	    optVOdb.setPROG_DESC_TK(repName);
	    optVOdb.setPROG_DESC_RU(repName);
	}
	for(int i = 0; i < missedOpts.size(); i++)
	{
	    currOpt = missedOpts.get(i);
	    updateOptVOBeforInsert(optVOdb, progRef, currOpt);   
	}
	// add if existing in database 
    }
    
    
	public IRP_FCR_REPORTSCO retFcrRep(IRP_AD_HOC_REPORTSC repSC) throws BaseException 
	{
		return fcrDAO.retFcrRep(repSC);
	}
	
	public String returnFcrProgRef(Map parameters) throws BaseException 
	{
		return fcrDAO.returnFcrProgRef(parameters);
	}
	
    public List<AXSCO> retAxsByReport(AXSCO axsCO) throws BaseException
    {
	return fcrDAO.retAxsByReport(axsCO);
    }

    public void saveMissedAxs(AXSVO axsVO, List<String> missedAxs) throws BaseException
    {
	String currAxs;
	for(int i = 0; i < missedAxs.size(); i++)
	{
	    currAxs = missedAxs.get(i);
	    saveAxs(fillAxsVOProps(axsVO.getCOMP_CODE(), axsVO.getBRANCH_CODE(), axsVO.getAPP_NAME(), axsVO
		    .getUSER_ID(), axsVO.getDATE_CREATED(),currAxs, axsVO.getSTATUS(), axsVO
		    .getTO_BE_DELETED()));
	}
    }

    public AXSVO fillAxsVOProps(BigDecimal compCode, BigDecimal branchCode, String appName, String userName,
	    Date runningDate, String progRef, String status, String toBeDeleted) throws BaseException
    {
	AXSVO axsVO = new AXSVO();
	axsVO.setCOMP_CODE(compCode);
	axsVO.setBRANCH_CODE(branchCode);
	axsVO.setAPP_NAME(appName);
	axsVO.setPROG_REF(progRef);
	axsVO.setUSER_ID(userName);
	axsVO.setCREATED_BY(userName);
	axsVO.setAUTHORIZED_BY(userName);
	axsVO.setSTATUS(status);
	axsVO.setDIRECT_ACCESS(userName);
	axsVO.setDATE_CREATED(runningDate);
	axsVO.setDATE_AUTHORIZED(runningDate);
	axsVO.setTO_BE_DELETED(toBeDeleted);
	return axsVO;
    }

    /**
     * Method that returns the english desc for an fcr report
     */
    public String retFcrReportEngDesc(String progRef) throws BaseException
    {
	return fcrDAO.retFcrReportEngDesc(progRef);
    }

    @Override
    public List<IRP_FCR_FIXED_COLSVO> retFixedFcrColsByRef(String progRef) throws BaseException
    {
	return fcrDAO.retFixedFcrColsByRef(progRef);
    }

    @Override
    public List<COLMNTMPLTCO> retDynamicFcrColsFromColTempl(COLMNTMPLTSC colTmplSC) throws BaseException
    {
	return fcrDAO.retDynamicFcrColsFromColTempl(colTmplSC);
    }

    @Override
    public List<COLMNTMPLTCO> retDynamicFcrColsFromTempl(GLSTMPLTSC tmplSC) throws BaseException
    {
	return fcrDAO.retDynamicFcrColsFromTempl(tmplSC);
    }

    @Override
    public List<IRP_FCR_REPORTSVO>  returnFcrDetByFtrOptRef(IRP_FCR_REPORTSVO vo) throws BaseException
    {
	return fcrDAO.returnFcrDetByFtrOptRef(vo);
    }

	@Override
	public List<CommonDetailsVO> retCategoriesLkpList(CommonDetailsSC commonDetailsSC)throws BaseException
	{
		return fcrDAO.retCategoriesLkpList(commonDetailsSC);
	}

	@Override
	public int retCategoriesLkpCount(CommonDetailsSC commonDetailsSC) throws BaseException
	{
		return fcrDAO.retCategoriesLkpCount(commonDetailsSC);
	}

	@Override
	public void deleteSRoleDetail(FTR_OPTCO fcrCO) throws BaseException {
		fcrDAO.deleteSRoleDetail(fcrCO);
	}
    
    
    

}
