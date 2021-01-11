package com.path.bo.reporting.common.impl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.TimeZone;

import net.sf.jasperreports.engine.util.DefaultFormatFactory;

public class ArabicFormatFactory extends DefaultFormatFactory 
{
	//in the JRXML for the Strings including numbers and dates, the below are samples that needs simple adjustments
	//"Test Arabic Number<<<<"+(new com.path.bo.reporting.common.impl.ArabicFormatFactory()).createNumberFormat("", new Locale("ar") ).format(123456)+">>>Test Arabic Number"
	//"Test ArabicDate<<<"+(new com.path.bo.reporting.common.impl.ArabicFormatFactory()).createDateFormat("", new Locale("ar"), TimeZone.getDefault() ).format( new java.util.Date())+">>>Test ArabicDate"
	@Override
	public NumberFormat createNumberFormat(String patttern, Locale localle) 
	{	
	    		String pattern= patttern;
	    		Locale locale= localle;	
	    		//to check the pattern
			if (pattern == null) {
				pattern="#.#";
			}
			if ("".equalsIgnoreCase(pattern )){
				pattern="#.#";
			}
			locale = new Locale("ar");
			NumberFormat numberFormat = super.createNumberFormat(pattern, locale);
			DecimalFormat decimalFormat = (DecimalFormat) numberFormat;
			DecimalFormatSymbols symbols = decimalFormat.getDecimalFormatSymbols();
			symbols.setZeroDigit('\u0660');
			decimalFormat.setDecimalFormatSymbols(symbols);
					
		return numberFormat;
	}

	 public  DateFormat createDateFormat(String patttern, Locale localle, TimeZone timezoone)
	 {
	     	 Locale locale=localle;
	     	 String pattern=patttern;
	     	 TimeZone timezone=timezoone;
		 locale = new Locale("ar");
		 pattern="";
		 timezone=TimeZone.getDefault();
		 DateFormat dtFormat= super.createDateFormat(pattern, locale, timezone);
		 dtFormat.setNumberFormat(this.createNumberFormat(pattern,locale));
		 return dtFormat;
		
		 
	 }

	
}

