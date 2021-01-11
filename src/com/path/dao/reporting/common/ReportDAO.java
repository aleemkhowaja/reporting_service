package com.path.dao.reporting.common;

import java.sql.Connection;
import java.math.BigDecimal;

import com.path.dbmaps.vo.IRP_VERIFIED_REPORTSVO;
import com.path.lib.common.exception.DAOException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;

public interface ReportDAO {
	public Connection getConnection() throws DAOException;
	public void verifyReport(IRP_VERIFIED_REPORTSVO irpVerReports) throws DAOException;
	/**
	 * return Oversize Limit
	 * @return
	 * @throws DAOException
	 */
	public BigDecimal returnOversizeLimit() throws DAOException;
	/**
	 * delete all record in irp_report_pagination related to a specific prog_ref,user id and app_name 
	 * @param paginationSC
	 * @throws DAOException
	 */
	public void deletePagination(IRP_AD_HOC_REPORTSC paginationSC ) throws DAOException;
	/**
	 * return Report ByteArray related to CurrentPage number
	 * @param paginationSC
	 * @return
	 * @throws DAOException
	 */
	public byte[] returnReportByteArrayCurrentPage(IRP_AD_HOC_REPORTSC paginationSC ) throws DAOException;
	
}
