package com.path.dao.reporting.common.impl;

import java.sql.Connection;

import com.path.dao.reporting.common.ReportDAO;
import com.path.dbmaps.vo.IRP_VERIFIED_REPORTSVO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;
import java.math.BigDecimal;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;

public class ReportDAOImpl extends BaseDAO implements ReportDAO {
	
	public Connection getConnection() throws DAOException {
		try
		{
			Connection c = getSqlMap().returnConnection();
			c.setAutoCommit(true);
			return c;
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void verifyReport(IRP_VERIFIED_REPORTSVO irpVerReports) throws DAOException
	{
	    getSqlMap().insert("commonReports.verifyReport",irpVerReports);
	}
	
	@Override
	public BigDecimal returnOversizeLimit() throws DAOException
	{
		return ((BigDecimal)getSqlMap().queryForObject("commonReports.returnOversizeLimit",null));
	}
	
	@Override
	public void deletePagination(IRP_AD_HOC_REPORTSC paginationSC ) throws DAOException
	{
		getSqlMap().delete("commonReports.deletePagination",paginationSC);
	}
	
	public byte[] returnReportByteArrayCurrentPage(IRP_AD_HOC_REPORTSC paginationSC ) throws DAOException
	{
		byte[] reportContent = (byte[]) getSqlMap().queryForObject("commonReports.returnReportByteArrayCurrentPage",paginationSC);
		return reportContent;
	}
	
}
