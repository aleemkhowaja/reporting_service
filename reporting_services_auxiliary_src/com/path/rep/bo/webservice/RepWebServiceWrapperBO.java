package com.path.rep.bo.webservice;

import java.util.HashMap;

import com.path.lib.common.exception.BaseException;

public interface RepWebServiceWrapperBO
{
    /**
     * Method that will be used temporarily to import dynamic File, before it is moved to integration gateway stand alone module
     * @param hashIn HashMap with the parameters for which the import to be done
     * @return result of the import operation
     * @throws BaseException
     */
    public HashMap<String, Object> generateReport(HashMap<String, Object> hashIn) throws BaseException;
}
