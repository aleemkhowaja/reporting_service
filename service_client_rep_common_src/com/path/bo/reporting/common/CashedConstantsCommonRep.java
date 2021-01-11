package com.path.bo.reporting.common;

import java.math.BigDecimal;
import java.util.HashMap;

import com.path.dbmaps.vo.GLSHEADERVO;

public class CashedConstantsCommonRep
{
    public static final HashMap<BigDecimal, GLSHEADERVO> tempHeaderHm = new HashMap<BigDecimal, GLSHEADERVO>();// to put in cash the value of template header
    public static final HashMap<String, String> sessionAttrHm = new HashMap<String, String>();// to put in cash the session attributes
}
