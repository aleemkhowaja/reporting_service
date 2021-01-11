package com.path.rep.bo.webservice.util;

import java.math.BigDecimal;
import java.util.Date;

import com.path.lib.common.exception.BaseException;
import com.path.vo.common.ImBaseRequest;

/**
 * 
 * @author EliasAoun
 *
 */
public interface RepServiceUtilBO
{

    void authenticateUser(ImBaseRequest baseRequest) throws BaseException;

    public Date returnRunningDate(BigDecimal compCode, BigDecimal branchCode, String applicationName)
	    throws BaseException;

    BigDecimal[] returnFiscalYearMonth(BigDecimal companyCode, BigDecimal branchCode, Date runningDate) throws BaseException;

    String returnKeyLabelTrans(String language, String labelKey) throws BaseException;
 
}
