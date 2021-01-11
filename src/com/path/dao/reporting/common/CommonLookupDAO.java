package com.path.dao.reporting.common;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.path.dbmaps.vo.FTR_OPTVO;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.dbmaps.vo.IRP_CONNECTIONSVO;
import com.path.dbmaps.vo.S_ADDITIONS_OPTIONSVO;
import com.path.dbmaps.vo.csvitems.CsvItemsCO;
import com.path.dbmaps.vo.csvitems.CsvItemsSC;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.exception.DAOException;
import com.path.vo.common.COUNTRYSC;
import com.path.vo.common.CURRENCIESCO;
import com.path.vo.reporting.DynLookupSC;
import com.path.vo.reporting.IRP_CONNECTIONSSC;
import com.path.vo.reporting.ftr.fcr.FTR_OPTCO;
import com.path.vo.reporting.ftr.filterCriteria.S_ADDITIONS_OPTIONSSC;
import com.path.vo.reporting.ftr.template.CommonDetailsSC;
import com.path.vo.reporting.ftr.template.CommonDetailsVO;
import com.path.vo.reporting.ftr.template.TmplGridSC;

public interface CommonLookupDAO {

	public List<Object> getLookupList(Object obj) throws DAOException;
	public List<CommonDetailsVO> getFilterCrtList(TmplGridSC tmplGridS) throws DAOException;
	public int getFilterCrtListCount(TmplGridSC tmplGridSC) throws DAOException;
	public CommonDetailsVO getFilterCrtDependency(TmplGridSC tmplGridSC) throws DAOException;
	public CommonDetailsVO getCompDependency(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMaxBranch(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMinBranch(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMaxDivision(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMinDivision(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMaxDepartment(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMinDepartment(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMaxUnit(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMinUnit(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMaxCurrency(TmplGridSC tmplGridSC) throws DAOException;
	CommonDetailsVO findMinCurrency(TmplGridSC tmplGridSC) throws DAOException;
        /**
         * returns the region code and description having the maximum region_code 
         * @param tmplGridSC
         * @return
         * @throws DAOException
         */
        CommonDetailsVO findMaxRegion(TmplGridSC tmplGridSC) throws DAOException;
        /**
         * returns the region code and description having the minimum region_code
         * @param tmplGridSC
         * @return
         * @throws DAOException
         */
	CommonDetailsVO findMinRegion(TmplGridSC tmplGridSC) throws DAOException;
	public CURRENCIESCO returnCompCurrencyDet(BigDecimal companyCode) throws DAOException;
	public BigDecimal returnConnectionId(String appName) throws DAOException;
	public List<IRP_CONNECTIONSVO> returnConnectionsList() throws DAOException;
	public IRP_CONNECTIONSVO returnConnById(BigDecimal dbConn) throws DAOException;
	public ArrayList<LinkedHashMap> returnQryResult(DynLookupSC dynLookupSC) throws DAOException, ClassNotFoundException, IOException, SQLException;
	public Integer returnQryResultCnt(DynLookupSC dynLookupSC) throws DAOException, ClassNotFoundException, IOException, SQLException;
	public List<String[]> returnColsList(String qryId) throws DAOException, ClassNotFoundException, IOException, SQLException;
	public S_ADDITIONS_OPTIONSVO getAdditionsOptionsDependency(S_ADDITIONS_OPTIONSVO sAdditionsOptionsVO) throws DAOException;
	public List<S_ADDITIONS_OPTIONSVO> getAdditionsOptionsList(S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws DAOException;
	public int getAdditionsOptionsListCount(S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws DAOException;
	public String getSmartText(S_ADDITIONS_OPTIONSSC sAdditionsOptionsSC) throws DAOException;
	public String getCountryDesc(COUNTRYSC countrySC)throws DAOException;
	public List<GLSHEADERVO> returnTemplateHeaderVal(BigDecimal tempHeaderCode) throws DAOException;
	public int getReportsCount(CsvItemsSC csvItemsSC) throws BaseException;
	public List<CsvItemsCO> getReportsList(CsvItemsSC csvItemsSC) throws BaseException;
	public FTR_OPTVO retIfIsFcrReport(FTR_OPTCO ftrOptCO) throws DAOException;
	public List<IRP_CONNECTIONSVO> returnConnectionsListByApp(IRP_CONNECTIONSSC conSC) throws DAOException;
	public List<CommonDetailsVO> getRegionList(CommonDetailsSC commDet)
		throws DAOException;
	public int getRegionListCount(CommonDetailsSC commDet) throws DAOException;
	public String replaceQryArgByVal(DynLookupSC dynLookupSC) throws DAOException;
}
