package com.path.lib.common.util;

import java.math.BigDecimal;

import javax.xml.bind.annotation.adapters.XmlAdapter;


public class BigDecimalAdapter  extends XmlAdapter<String, BigDecimal> 
{
	public static final BigDecimal EMPTY_BIGDECIMAL_VALUE = BigDecimal.valueOf(-9999999);
	public String marshal(BigDecimal value) throws Exception 
	{
		if(value == null )
			return null;
		return value.toPlainString();
	}
	
	public BigDecimal unmarshal(String numberString) throws Exception 
	{
		if(numberString == null || numberString.isEmpty())
			return EMPTY_BIGDECIMAL_VALUE;
		return new BigDecimal(numberString);
	}
}
