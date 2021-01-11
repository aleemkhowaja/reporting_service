package com.path.vo.reporting.scheduler;

import java.io.StringReader;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import com.path.dbmaps.vo.IRP_REPORT_SCHEDULEVO;
import com.path.lib.common.util.FileUtil;
import com.path.lib.log.Log;

public class IRP_REPORT_SCHEDULECO extends IRP_REPORT_SCHEDULEVO {
    	private static final Log log = Log.getInstance();
	private String PROG_REF;
	private String SCHED_NAME;
	private String REPORT_NAME;
	private String IS_PRINT;
	private List<IRP_REPORT_SCHED_PARAMSCO> parameters;
	private byte[] XHTML_FILE;
	private byte[] JRXML_FILE;
	private Date NEXT_RUN_DATE;
	private String REPORT_LANGUAGE;
	private String REPORT_APP_NAME;
	private String MAIL_SERVER_HOST;
	private String FROM_EMAIL_FE_VAL;
	private String TO_EMAIL_FE_VAL;
	private String TO_EMAIL_CIF_VAL;
	private String CC_EMAIL_FE_VAL;
	private String BCC_EMAIL_FE_VAL;
//	private String TO_USERS_LIST;
//	private String CC_USERS_LIST;
    // private String BCC_USERS_LIST;
	private String DEFAULT_FROM_MS;
	private String REPORT_FORMAT_TRANS;
	private BigDecimal CONN_ID;
	private String WHEN_NO_DATA;
	private String LANG_INDEPENDENT_YN;
	private String FCR_REPORT_NAME;
	private String USE_DEFAULT_MS_YN;
	private BigDecimal BATCH_CODE;
	private BigDecimal EVT_ID;
	private BigDecimal IS_FCR_YN;
	private BigDecimal compCode;
		
	public BigDecimal getIS_FCR_YN()
	{
	    return IS_FCR_YN;
	}

	public void setIS_FCR_YN(BigDecimal iS_FCR_YN)
	{
	    IS_FCR_YN = iS_FCR_YN;
	}

	public BigDecimal getEVT_ID()
	{
		return EVT_ID;
	}

	public void setEVT_ID(BigDecimal eVT_ID) 
	{
		EVT_ID = eVT_ID;
	}

	public BigDecimal getBATCH_CODE()
	{
	    return BATCH_CODE;
	}

	public void setBATCH_CODE(BigDecimal bATCH_CODE)
	{
	    BATCH_CODE = bATCH_CODE;
	}

	/**
	 * @return the uSE_DEFAULT_MS_YN
	 */
	public String getUSE_DEFAULT_MS_YN()
	{
	    return USE_DEFAULT_MS_YN;
	}

	/**
	 * @param uSEDEFAULTMSYN the uSE_DEFAULT_MS_YN to set
	 */
	public void setUSE_DEFAULT_MS_YN(String uSEDEFAULTMSYN)
	{
	    USE_DEFAULT_MS_YN = uSEDEFAULTMSYN;
	}

	public String getFCR_REPORT_NAME()
	{
	    return FCR_REPORT_NAME;
	}

	public void setFCR_REPORT_NAME(String fCRREPORTNAME)
	{
	    FCR_REPORT_NAME = fCRREPORTNAME;
	}


	public String getLANG_INDEPENDENT_YN()
	{
	    return LANG_INDEPENDENT_YN;
	}

	public void setLANG_INDEPENDENT_YN(String lANGINDEPENDENTYN)
	{
	    LANG_INDEPENDENT_YN = lANGINDEPENDENTYN;
	}

	public String getWHEN_NO_DATA()
	{
	    return WHEN_NO_DATA;
	}

	public void setWHEN_NO_DATA(String wHENNODATA)
	{
	    WHEN_NO_DATA = wHENNODATA;
	}

	public BigDecimal getCONN_ID()
	{
	    return CONN_ID;
	}

	public void setCONN_ID(BigDecimal cONNID)
	{
	    CONN_ID = cONNID;
	}

	public String getREPORT_FORMAT_TRANS()
	{
	    return REPORT_FORMAT_TRANS;
	}

	public void setREPORT_FORMAT_TRANS(String rEPORTFORMATTRANS)
	{
	    REPORT_FORMAT_TRANS = rEPORTFORMATTRANS;
	}

	public String getDEFAULT_FROM_MS() {
		return DEFAULT_FROM_MS;
	}

	public void setDEFAULT_FROM_MS(String dEFAULTFROMMS) {
		DEFAULT_FROM_MS = dEFAULTFROMMS;
	}

//	public String getTO_USERS_LIST() {
//		return TO_USERS_LIST;
//	}
//
//	public void setTO_USERS_LIST(String tOUSERSLIST) {
//		TO_USERS_LIST = tOUSERSLIST;
//	}
//
//	public String getCC_USERS_LIST() {
//		return CC_USERS_LIST;
//	}
//
//	public void setCC_USERS_LIST(String cCUSERSLIST) {
//		CC_USERS_LIST = cCUSERSLIST;
//	}
//
//	public String getBCC_USERS_LIST() {
//		return BCC_USERS_LIST;
//	}
//
//	public void setBCC_USERS_LIST(String bCCUSERSLIST) {
//		BCC_USERS_LIST = bCCUSERSLIST;
//	}

	public String getFROM_EMAIL_FE_VAL() {
		return FROM_EMAIL_FE_VAL;
	}

	public void setFROM_EMAIL_FE_VAL(String fROMEMAILFEVAL) {
		FROM_EMAIL_FE_VAL = fROMEMAILFEVAL;
	}

	public String getTO_EMAIL_FE_VAL() {
		return TO_EMAIL_FE_VAL;
	}

	public void setTO_EMAIL_FE_VAL(String tOEMAILFEVAL) {
		TO_EMAIL_FE_VAL = tOEMAILFEVAL;
	}

	public String getTO_EMAIL_CIF_VAL() {
		return TO_EMAIL_CIF_VAL;
	}

	public void setTO_EMAIL_CIF_VAL(String tOEMAILCIFVAL) {
		TO_EMAIL_CIF_VAL = tOEMAILCIFVAL;
	}

	public String getCC_EMAIL_FE_VAL() {
		return CC_EMAIL_FE_VAL;
	}

	public void setCC_EMAIL_FE_VAL(String cCEMAILFEVAL) {
		CC_EMAIL_FE_VAL = cCEMAILFEVAL;
	}

	public String getBCC_EMAIL_FE_VAL() {
		return BCC_EMAIL_FE_VAL;
	}

	public void setBCC_EMAIL_FE_VAL(String bCCEMAILFEVAL) {
		BCC_EMAIL_FE_VAL = bCCEMAILFEVAL;
	}

	public String getMAIL_SERVER_HOST() {
		return MAIL_SERVER_HOST;
	}

	public void setMAIL_SERVER_HOST(String mAILSERVERHOST) {
		MAIL_SERVER_HOST = mAILSERVERHOST;
	}

	public String getREPORT_APP_NAME() {
		return REPORT_APP_NAME;
	}

	public void setREPORT_APP_NAME(String rEPORTAPPNAME) {
		REPORT_APP_NAME = rEPORTAPPNAME;
	}

	public String getREPORT_LANGUAGE() {
		return REPORT_LANGUAGE;
	}

	public void setREPORT_LANGUAGE(String rEPORTLANGUAGE) {
		REPORT_LANGUAGE = rEPORTLANGUAGE;
	}

	public Date getNEXT_RUN_DATE() {
		return NEXT_RUN_DATE;
	}

	public void setNEXT_RUN_DATE(Date nEXTRUNDATE) {
		NEXT_RUN_DATE = nEXTRUNDATE;
	}

	public String getPROG_REF() {
		return PROG_REF;
	}

	public void setPROG_REF(String pROGREF) {
		PROG_REF = pROGREF;
	}

	public byte[] getXHTML_FILE() {
		return XHTML_FILE;
	}

	public void setXHTML_FILE(byte[] xHTMLFILE) {
		XHTML_FILE = xHTMLFILE;
	}

	public byte[] getJRXML_FILE() {
		return JRXML_FILE;
	}

	public void setJRXML_FILE(byte[] jRXMLFILE) {
		JRXML_FILE = jRXMLFILE;
	}

	public String getREPORT_NAME() {
		return REPORT_NAME;
	}

	public void setREPORT_NAME(String rEPORTNAME) {
		REPORT_NAME = rEPORTNAME;
	}

	public String getIS_PRINT() {
		return IS_PRINT;
	}

	public void setIS_PRINT(String iSPRINT) {
		IS_PRINT = iSPRINT;
	}

	public List<IRP_REPORT_SCHED_PARAMSCO> getParameters() {
		return parameters;
	}

	public void setParameters(List<IRP_REPORT_SCHED_PARAMSCO> parameters) {
		this.parameters = parameters;
	}
	public String getXHTML() {
		try {
			//System.out.println("this.getXHTML_FILE():: "+this.getXHTML_FILE());
			if(this.getXHTML_FILE() != null)
			{
				String xml = new String(this.getXHTML_FILE(),FileUtil.DEFAULT_FILE_ENCODING);
				log.debug("xml: "+xml);
				Document document = new SAXReader().read(new StringReader(xml));
				Element root = document.getRootElement();
				log.debug("root.asXML(): "+root.asXML());
				List<Element> elements = root.elements();
				Element table = null;
				for(Element element : elements){
					if(element.getName().toUpperCase().equals("TABLE")){
						table = element;
						break;
					}
				}
				log.debug("table: "+table.getName());
				log.debug("table.asXML(): "+table.asXML());
				return table.asXML();
			}
			
		} catch (Exception e) {
			log.debug("IRP_REPORT_SCHEDULECO.getXHTML()   "+e );
		}
		return null;
	}

	public void setSCHED_NAME(String sCHED_NAME)
	{
	    SCHED_NAME = sCHED_NAME;
	}

	public String getSCHED_NAME()
	{
	    return SCHED_NAME;
	}

	public BigDecimal getCompCode() {
		return compCode;
	}

	public void setCompCode(BigDecimal compCode) {
		this.compCode = compCode;
	}
	
}
