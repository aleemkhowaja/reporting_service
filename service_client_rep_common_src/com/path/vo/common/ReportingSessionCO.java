package com.path.vo.common;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_REP_SCHED_MAIL_RECEIVERSVO;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_QUERY_ARG_MAPPINGCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_REP_HYPERLINKSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.SQL_OBJECT;
import com.path.vo.reporting.designer.IRP_ENTITIESSC;
import com.path.vo.reporting.ftr.columnTemplate.COLMNTMPLTCO;
import com.path.vo.reporting.ftr.template.GLSTMPLTCO;
import com.path.vo.reporting.scheduler.IRP_REP_SCHED_MAIL_GROUP_BYCO;
import com.path.vo.reporting.snapshots.USER_ACCESSCO;

public class ReportingSessionCO extends
		com.path.vo.reporting.CommonReportingSessionCO {
	private COLMNTMPLTCO CurrentTemplate;
	private IRP_AD_HOC_REPORTCO oldReportCO;
	private SQL_OBJECT oldSqlObj;
	private List critTypesList;
	private GLSTMPLTCO allTempl;
	//contains list of COs of modified columns + list of COs of drilldown columns + livesearch hash for the grid
	private HashMap<String,Object> snParameterScreenHash = new HashMap<String, Object>();
	//contains same of previous but related to the mismatch reports section
	private HashMap<String,Object> repMisParameterScreenHash = new HashMap<String, Object>();
	private CURRENCIESCO currenciesCO;
	private String menuQryName; // added to store the qryName on click on the
	// edit Qry since the arabic characters are not
	// sent correctly on
	// load of the dialog because the parameters are sent via GET request
	private IRP_ENTITIESSC entityFields = new IRP_ENTITIESSC();// Mira
	// 04-Jun-2012
	// Entities
	// Screen
	private HashMap<String, String> sessionAttrList;
	private HashMap<String, String> qryColumnsList;
	private HashMap<String, String> qryDfltValColumnsList;

	private boolean reportParams;
	private HashMap<String, List<IRP_REP_HYPERLINKSCO>> hyperlinkParamsMap = new HashMap<String, List<IRP_REP_HYPERLINKSCO>>();// to
	// store
	// the
	// mapping of the hyperlink parameters
	private HashMap<String, List<IRP_REP_HYPERLINKSCO>> oldHyperlinkParamsMap = new HashMap<String, List<IRP_REP_HYPERLINKSCO>>();// to
	// store
	// the
	// oldValue of the hypVO that will be needed in audit
	private HashMap<String, List<IRP_SUB_REPORTCO>> subRepParamsMap = new HashMap<String, List<IRP_SUB_REPORTCO>>();// to
	// store
	// the
	// mapping of the sub report parameters

	private HashMap<String, List<IRP_REP_ARGUMENTSCO>> subRepParamsMap1 = new HashMap<String, List<IRP_REP_ARGUMENTSCO>>();// to
	// store
	// the
	// mapping of the sub report parameters

	private HashMap<String, List<IRP_QUERY_ARG_MAPPINGCO>> linkQryArsMap = new HashMap<String, List<IRP_QUERY_ARG_MAPPINGCO>>();// to
	// store
	// the
	// mapping of qry parameters

	// This HashMap used to save the old list of Time Buckets that retrieved, so
	// we can check the old and new objects when apply the audit functionality
	public HashMap timeBucketsOld = new HashMap();
	public HashMap fileDetailsOld = new HashMap();
	public HashMap<String, List<GLSTMPLTCO>> tmplProcMap = new HashMap<String, List<GLSTMPLTCO>>();
	public HashMap<String, List<COLMNTMPLTCO>> colTmpProcMap = new HashMap<String, List<COLMNTMPLTCO>>();
	public LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList = new LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO>();
	// this map is used to store the list of users by reciever type (to,cc, bcc)
	public HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap = new HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>>();

	private HashMap<String, USER_ACCESSCO> viewedUserAccessMap = new HashMap<String, USER_ACCESSCO>();// to
	// store
	// the
	// mapping of user access
	private HashMap<String, List<USER_ACCESSCO>> viewedBranchMap = new HashMap<String, List<USER_ACCESSCO>>();// to

	// store
	// the

	// mapping of user access

	
	//the session's properties
	private HashMap<String,String> sessionProps = new HashMap<String, String>();
	private HashMap<String,String> reportProps = new HashMap<String, String>();
	private StringBuffer warningsAutomatedImport;

	public StringBuffer getWarningsAutomatedImport()
	{
	    return warningsAutomatedImport;
	}

	public void setWarningsAutomatedImport(StringBuffer warningsAutomatedImport)
	{
	    this.warningsAutomatedImport = warningsAutomatedImport;
	}

	public HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> getRepSchedMailUsrsMap() {
		return repSchedMailUsrsMap;
	}

	public HashMap<String, USER_ACCESSCO> getViewedUserAccessMap() {
		return viewedUserAccessMap;
	}
	
	public HashMap<String, String> getQryDfltValColumnsList()
	{
	    return qryDfltValColumnsList;
	}

	public void setQryDfltValColumnsList(HashMap<String, String> qryDfltValColumnsList)
	{
	    this.qryDfltValColumnsList = qryDfltValColumnsList;
	}

	public void setViewedUserAccessMap(
			HashMap<String, USER_ACCESSCO> viewedUserAccessMap) {
		this.viewedUserAccessMap = viewedUserAccessMap;
	}

	public HashMap<String, List<USER_ACCESSCO>> getViewedBranchMap() {
		return viewedBranchMap;
	}

	public void setViewedBranchMap(
			HashMap<String, List<USER_ACCESSCO>> viewedBranchMap) {
		this.viewedBranchMap = viewedBranchMap;
	}

	public void setRepSchedMailUsrsMap(
			HashMap<String, LinkedHashMap<String, IRP_REP_SCHED_MAIL_RECEIVERSVO>> repSchedMailUsrsMap) {
		this.repSchedMailUsrsMap = repSchedMailUsrsMap;
	}

	public LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> getRepScheMailGrpByList() {
		return repScheMailGrpByList;
	}

	public void setRepScheMailGrpByList(
			LinkedHashMap<String, IRP_REP_SCHED_MAIL_GROUP_BYCO> repScheMailGrpByList) {
		this.repScheMailGrpByList = repScheMailGrpByList;
	}

	public HashMap<String, List<GLSTMPLTCO>> getTmplProcMap() {
		return tmplProcMap;
	}

	public void setTmplProcMap(HashMap<String, List<GLSTMPLTCO>> tmplProcMap) {
		this.tmplProcMap = tmplProcMap;
	}

	public HashMap<String, List<COLMNTMPLTCO>> getColTmpProcMap() {
		return colTmpProcMap;
	}

	public void setColTmpProcMap(
			HashMap<String, List<COLMNTMPLTCO>> colTmpProcMap) {
		this.colTmpProcMap = colTmpProcMap;
	}

	public HashMap getTimeBucketsOld() {
		return timeBucketsOld;
	}

	public void setTimeBucketsOld(HashMap timeBucketsOld) {
		this.timeBucketsOld = timeBucketsOld;
	}
	
	public HashMap getFileDetailsOld() {
		return fileDetailsOld;
	}

	public void setFileDetailsOld(HashMap fileDetailsOld) {
		this.fileDetailsOld = fileDetailsOld;
	}

	public HashMap<String, List<IRP_REP_HYPERLINKSCO>> getHyperlinkParamsMap() {
		return hyperlinkParamsMap;
	}

	public HashMap<String, List<IRP_QUERY_ARG_MAPPINGCO>> getLinkQryArsMap() {
		return linkQryArsMap;
	}

	public void setLinkQryArsMap(
			HashMap<String, List<IRP_QUERY_ARG_MAPPINGCO>> linkQryArsMap) {
		this.linkQryArsMap = linkQryArsMap;
	}

	public HashMap<String, List<IRP_REP_ARGUMENTSCO>> getSubRepParamsMap1() {
		return subRepParamsMap1;
	}

	public void setSubRepParamsMap1(
			HashMap<String, List<IRP_REP_ARGUMENTSCO>> subRepParamsMap1) {
		this.subRepParamsMap1 = subRepParamsMap1;
	}

	public HashMap<String, List<IRP_SUB_REPORTCO>> getSubRepParamsMap() {
		return subRepParamsMap;
	}

	public void setSubRepParamsMap(
			HashMap<String, List<IRP_SUB_REPORTCO>> subRepParamsMap) {
		this.subRepParamsMap = subRepParamsMap;
	}

	public HashMap<String, List<IRP_REP_HYPERLINKSCO>> getOldHyperlinkParamsMap() {
		return oldHyperlinkParamsMap;
	}

	public void setOldHyperlinkParamsMap(
			HashMap<String, List<IRP_REP_HYPERLINKSCO>> oldHyperlinkParamsMap) {
		this.oldHyperlinkParamsMap = oldHyperlinkParamsMap;
	}

	public void setHyperlinkParamsMap(
			HashMap<String, List<IRP_REP_HYPERLINKSCO>> hyperlinkParamsMap) {
		this.hyperlinkParamsMap = hyperlinkParamsMap;
	}

	public String getMenuQryName() {
		return menuQryName;
	}

	public void setMenuQryName(String menuQryName) {
		this.menuQryName = menuQryName;
	}

	public IRP_AD_HOC_REPORTCO getOldReportCO() {
		return oldReportCO;
	}

	public void setOldReportCO(IRP_AD_HOC_REPORTCO oldReportCO) {
		this.oldReportCO = oldReportCO;
	}

	public SQL_OBJECT getOldSqlObj() {
		return oldSqlObj;
	}

	public void setOldSqlObj(SQL_OBJECT oldSqlObj) {
		this.oldSqlObj = oldSqlObj;
	}

	public List getCritTypesList() {
		return critTypesList;
	}

	public void setCritTypesList(List critTypesList) {
		this.critTypesList = critTypesList;
	}

	public GLSTMPLTCO getAllTempl() {
		return allTempl;
	}

	public void setAllTempl(GLSTMPLTCO allTempl) {
		this.allTempl = allTempl;
	}

	public COLMNTMPLTCO getCurrentTemplate() {
		return CurrentTemplate;
	}

	public void setCurrentTemplate(COLMNTMPLTCO currentTemplate) {
		CurrentTemplate = currentTemplate;
	}

	public CURRENCIESCO getCurrenciesCO() {
		return currenciesCO;
	}

	public void setCurrenciesCO(CURRENCIESCO currenciesCO) {
		this.currenciesCO = currenciesCO;
	}

	public IRP_ENTITIESSC getEntityFields() {
		return entityFields;
	}

	public void setEntityFields(IRP_ENTITIESSC entityFields) {
		this.entityFields = entityFields;
	}

	public HashMap<String, String> getSessionAttrList() {
		return sessionAttrList;
	}

	public void setSessionAttrList(HashMap<String, String> sessionAttrList) {
		this.sessionAttrList = sessionAttrList;
	}

	public HashMap<String, String> getQryColumnsList() {
		return qryColumnsList;
	}

	public void setQryColumnsList(HashMap<String, String> qryColumnsList) {
		this.qryColumnsList = qryColumnsList;
	}

	public boolean isReportParams() {
		return reportParams;
	}

	public void setReportParams(boolean reportParams) {
		this.reportParams = reportParams;
	}

	public HashMap<String, Object> getSnParameterScreenHash()
	{
	    return snParameterScreenHash;
	}

	public void setSnParameterScreenHash(HashMap<String, Object> snParameterScreenHash)
	{
	    this.snParameterScreenHash = snParameterScreenHash;
	}

	public HashMap<String, Object> getRepMisParameterScreenHash()
	{
	    return repMisParameterScreenHash;
	}

	public void setRepMisParameterScreenHash(HashMap<String, Object> repMisParameterScreenHash)
	{
	    this.repMisParameterScreenHash = repMisParameterScreenHash;
	}

	public HashMap<String, String> getSessionProps()
	{
	    return sessionProps;
	}

	public void setSessionProps(HashMap<String, String> sessionProps)
	{
	    this.sessionProps = sessionProps;
	}

	public HashMap<String, String> getReportProps()
	{
	    return reportProps;
	}

	public void setReportProps(HashMap<String, String> reportProps)
	{
	    this.reportProps = reportProps;
	}
	
	
}
