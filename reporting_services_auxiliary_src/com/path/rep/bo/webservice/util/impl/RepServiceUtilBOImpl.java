/**
 * 
 */
package com.path.rep.bo.webservice.util.impl;

import java.math.BigDecimal;
import java.util.Date;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.common.MessageCodes;
import com.path.bo.common.login.LoginBO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.SecurityUtils;
import com.path.rep.bo.webservice.util.RepServiceUtilBO;
import com.path.vo.common.CommonLibSC;
import com.path.vo.common.ImBaseRequest;

/**
 * @author EliasAoun
 *
 */
public class RepServiceUtilBOImpl extends BaseBO implements RepServiceUtilBO
{
    LoginBO loginBO;

    public void setLoginBO(LoginBO loginBO)
    {
	this.loginBO = loginBO;
    }

    /**
     * authenticate user
     * 
     * @param baseRequest
     * @return
     */
    @Override
    public void authenticateUser(ImBaseRequest baseRequest) throws BaseException
    {
	int isAuthenticateUser = 0;
	String user = (String) baseRequest.getRequesterContext().getUserID();
	String password = SecurityUtils.decodeB64((String) baseRequest.getRequesterContext().getPassword());
	// return 1 in case of success
	isAuthenticateUser = loginBO.authenticateUserAndPass(user, password);
	if(isAuthenticateUser == 0)
	{
	    throw new BOException(MessageCodes.ENTER_A_VALID_AUTHORISED_USER);
	}
    }
    
    /**
     * S_RUNDTE
     */
    @Override
    public Date returnRunningDate(BigDecimal compCode, BigDecimal branchCode, String applicationName) throws BaseException
    {
	return loginBO.returnRunningDate(compCode, branchCode, applicationName);
    }

    @Override
    public BigDecimal[] returnFiscalYearMonth(BigDecimal companyCode, BigDecimal branchCode, Date runningDate)
	    throws BaseException
    {
	return loginBO.returnFiscalYearMonth(companyCode, branchCode, runningDate);
    }
    
    @Override
    public String returnKeyLabelTrans(String language, String labelKey) throws BaseException
    {
	CommonLibSC sc = new CommonLibSC();
	sc.setAppName(ConstantsCommon.IMAL_APP_NAME);
	sc.setProgRef(ConstantsCommon.PROGREF_ROOT);
	sc.setLanguage(language);
	sc.setKeyLabelCode(labelKey);
	return commonLibBO.returnKeyLabelTrans(sc);
    }
}
