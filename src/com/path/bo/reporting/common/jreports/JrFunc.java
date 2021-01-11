package com.path.bo.reporting.common.jreports;

import java.io.BufferedReader;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import org.apache.commons.lang.WordUtils;

import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.StringUtil;
import com.path.lib.log.Log;

//import com.path.vo.common.SessionCO;
//import com.path.vo.common.path.reports.common.vo.common.UsrLogInInfoVO;

public final class JrFunc
{
    static Log log = Log.getInstance();
    private JrFunc() 
    {
	log.error("This Class Should not be Instantiated");
    }
    static DecimalFormat nbFormat = new DecimalFormat();

    public static Double convertToDouble(Object myObj) throws BaseException
    {
	Double toReturn = null;
	if(myObj != null)
	{
	    if(myObj instanceof Number)
	    {
		toReturn = new Double(((Number) myObj).doubleValue());
	    }
	    /*
	     * else if(myObj instanceof Long) { toReturn = new
	     * Double(((Long)myObj).longValue()); } else if(myObj instanceof
	     * Float) { toReturn = new Double(((Float)myObj).floatValue()); }
	     * else if(myObj instanceof Double ) { toReturn = (Double)myObj; }
	     * else if( myObj instanceof BigDecimal) { toReturn = new
	     * Double(((BigDecimal)myObj).doubleValue()); }
	     */
	    else if(myObj instanceof String)
	    {
		toReturn = new Double(myObj.toString());
	    }
	    else
	    {
		log.warning("\n\n\n  convertToDouble(Double) is of unrecognized type return IS null be careful \n\n\n");
	    }
	}

	return toReturn;
    }

    public static Integer convertToInteger(Object myObj) throws BaseException
    {
	Integer toReturn = null;
	if(myObj != null)
	{
	    if(myObj instanceof Number)
	    {
		toReturn = Integer.valueOf(((Number) myObj).intValue());
	    }
	    else if(myObj instanceof String)
	    {
		toReturn = Integer.valueOf(myObj.toString());
	    }
	    else
	    {
		log
			.warning("\n\n\n  convertToInteger(Integer) is of unrecognized type return vlue is = null be careful \n\n\n");
	    }
	}

	return toReturn;
    }
    
    
    /**
     * Method that converts an input of type clob to a String , developped by Patricia Rahme
     * @return String
     */
    public static String convertClobToString(Clob clob) throws BaseException
      {
         if(clob == null)
               {
                      return null;
               }
               String strlob = null;
               try
               {
                      int bufSize = 8192;
                      char[] buf = new char[bufSize];
                      Reader reader = new BufferedReader(clob.getCharacterStream(), bufSize);
                      StringBuilder sb = new StringBuilder((int) clob.length());
                      for (int read = reader.read(buf); read > 0; read = reader.read(buf))
                      {
                             sb.append(buf, 0, read);
                      }
                      strlob = sb.toString();
               }
               catch (Exception e)
               {
                      log.error(e,"Error in method convertClobToString");
               }
             return strlob;
      }


    public static int myCompare(Object obj, Object toObj) throws BaseException
    {
	int toReturn = -1;
	if(obj == null && toObj == null)
	{
	    toReturn = 0;
	}
	else
	{
	    if(obj == null)
	    {
		if(toObj != null)
		{
			return 1;
		}	
	    }
	    else
	    {
		if(toObj == null)
		{
			toReturn = 1;
		}
		else if(obj instanceof Integer)
		{
			toReturn = ((Integer) obj).compareTo((Integer) toObj);
		}
		else if(obj instanceof Long)
		{
			toReturn = ((Long) obj).compareTo((Long) toObj);
		}
		else if(obj instanceof Double)
		{
			toReturn = ((Double) obj).compareTo((Double) toObj);
		}
		else if(obj instanceof Float)
		{
			toReturn = ((Float) obj).compareTo((Float) toObj);
		}
		else if(obj instanceof BigDecimal)
		{
			toReturn = ((BigDecimal) obj).compareTo((BigDecimal) toObj);
		}
		else if(obj instanceof String)
		{
			toReturn = ((String) obj).compareTo((String) toObj);
		}
		else
		{
			log
				.warning("\n\n\n  myCompare(Object obj, Object toObj) obj is of unrecognized type return IS -1 be careful \n\n\n");
		}
	    }
	}
	return toReturn;
    }

    /**
     * PB is Null function to check whether object is null
     * 
     * @param obj Object to check
     * @return
     * @throws BaseException
     */
    public static boolean isNull(Object obj) throws BaseException
    {
	return obj == null ? true : false;
    }

    public static String string(Object value) throws BaseException
    {
	return value == null ? null :value.toString();
    }

    
    public static String string(Object value, String format) throws BaseException
    {
	 
	return  string(value,  format,null, null);
    }
    
    public static String string(Object value, String format,String groupSepa, String decimalSepa) throws BaseException
    {
	SimpleDateFormat dateFormat = new SimpleDateFormat();
	if((value == null) || (format == null))
	{
	    if(format.indexOf(";") == -1)
	    {
		return "";
	    }
	    else
	    {
		return multiFormatNumber((Number) value, format,groupSepa, decimalSepa);
	    }
	}
	else
	{  
	    if(value instanceof String)
	    {
		value.toString();
		return String.format(format);
	    }
	    else if(value instanceof Number)
	    {
		String retVal = multiFormatNumber((Number) value, format,groupSepa, decimalSepa);
		// retVal = retVal.replace(",",""); commented by anthony malak
		// 21/11/2013 to make the ',' shown in a bigdecimal number
		// instead of no space or anything else
		return retVal;
	    }
	    else if(value instanceof Date)
	    {
		dateFormat.applyPattern(format);
		return dateFormat.format(value);
	    }
	    else
	    {
		log.warning("\n\n\n string method unrecognized data type for value argument =" + value
			+ "  returning null \n\n\n");
		return null;
	    }
	}
    }

    private static String multiFormatNumber(Number value, String format,String groupSepa, String decimalSepa) throws BaseException
    {
    return NumberUtil.multiFormatNumber(value, format, groupSepa,decimalSepa);
    }
    public static Integer hour(String value) throws BaseException
    {	Integer result = null;
    	String splitter = ":";
    	String[] hour;
    	if (value != null) 
    	{
        	hour = value.split(splitter);
        	if (hour.length > 1)
        	{
        	    result = convertToInteger(hour[1]); 
        	}
        	if (result <0 || result > 23)
        	{
        	    result = 0;
        	}
    	}
    	return result;
    }
    
    public static Date date(Object value) throws BaseException
    {
	Date result = null;
	String day = null, month = null, year = null, parsableDate = null, splitter = null, val = null;
	String[] possibleSplitters = { "/", " ", "-" };
	String[] date = null;

	if(value != null)
	{
	    if(value instanceof String)
	    {
		for(int i = 0; i < possibleSplitters.length; i++)
		{
		    if(value.toString().indexOf(possibleSplitters[i]) != -1)
		    {
			splitter = possibleSplitters[i];
			break;
		    }
		}

		if(splitter != null)
		{
		    date = value.toString().split(splitter);
		    for(int j = 0; j < date.length; j++)
		    {
			val = date[j];
			if(j == 1)
			{
			    if(val.matches("[a-zA-Z]+"))
			    {
				month = monthStringToNumber(val);
			    }
			    else
			    {
				month = val;
			    }
			}
			else if(val.length() == 4)
			{
			    year = val;
			}
			else if(val.length() <= 2)
			{
			    day = val;
			}
		    }

		    try
		    {
			if(day == null || month == null || year == null)
			{
			    result = DateUtil.parseDate("01/01/1900", "dd/MM/yyyy");
			}
			else
			{
			    parsableDate = day + "/" + month + "/" + year;
			    if(isValidDate(parsableDate))
			    {
				result = DateUtil.parseDate(parsableDate, "dd/MM/yyyy");
			    }
			}
		    }
		    catch(BaseException e)
		    {
			log.error(e, "invalid date to be parsed ");
		    }
		}
	    }
	    else if(value instanceof Date)
	    {
		return DateUtil.parseDate(DateUtil.format((Date) value, "dd/MM/yyyy"), "dd/MM/yyyy");
	    }
	}

	return result;
    }

    public static Date datetime(Object date) throws BaseException
    {
	return datetime(date, null);
    }

    public static Date datetime(Object date, Object time) throws BaseException
    {
	if(time == null)
	{
	    return DateUtil.parseDate(DateUtil.format((Date) date, "dd/MM/yyyy HH:mm:ss"), "dd/MM/yyyy HH:mm:ss");
	}
	String dateStr = DateUtil.format((Date) date, "dd/MM/yyyy");
	String timeStr = DateUtil.format((Date) date, "HH:mm:ss");
	String dateTimeStr = dateStr + " " + timeStr;
	return DateUtil.parseDate(dateTimeStr, "dd/MM/yyyy HH:mm:ss");
    }
    
    public static Date relativedate(Date inDate, Integer days)throws BaseException
    {     Date result;
          result = DateUtil.relativeDate(inDate, days);
          return result;
    }

    private static boolean isValidDate(String inDate)
    {
	try
	{
	    DateUtil.parseDate(inDate.trim(), "dd/mm/yyyy");
	}
	catch(BaseException e)
	{
	    log.error(e, "invalid date to be parsed " + inDate);
	    return false;
	}

	return true;
    }

    private static String monthStringToNumber(String month)
    {
	String monthNum = null;

	if(month.toUpperCase(Locale.ENGLISH).equals("JANUARY"))
	{
	    monthNum = String.valueOf(Calendar.JANUARY + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("FEBRUARY"))
	{
	    monthNum = String.valueOf(Calendar.FEBRUARY + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("MARCH"))
	{
	    monthNum = String.valueOf(Calendar.MARCH + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("APRIL"))
	{
	    monthNum = String.valueOf(Calendar.APRIL + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("MAY"))
	{
	    monthNum = String.valueOf(Calendar.MAY + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("JUNE"))
	{
	    monthNum = String.valueOf(Calendar.JUNE + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("JULY"))
	{
	    monthNum = String.valueOf(Calendar.JULY + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("AUGUST"))
	{
	    monthNum = String.valueOf(Calendar.AUGUST + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("SEPTEMBER"))
	{
	    monthNum = String.valueOf(Calendar.SEPTEMBER + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("OCTOBER"))
	{
	    monthNum = String.valueOf(Calendar.OCTOBER + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("NOVEMBER"))
	{
	    monthNum = String.valueOf(Calendar.NOVEMBER + 1);
	}
	else if(month.toUpperCase(Locale.ENGLISH).equals("DECEMBER"))
	{
	    monthNum = String.valueOf(Calendar.DECEMBER + 1);
	}

	return monthNum;
    }

    public static Double number(String val) throws BaseException
    {
	String value=val;
	Double result = null;

	if(value != null)
	{
		if(value.indexOf(",") == -1)
		{
		    result = Double.parseDouble(value);
		}
		else
		{
		    value = value.replace(",", "");
		    result = Double.parseDouble(value);
		}
	}
	return result;
    }

    public static BigDecimal abs(Number value) throws BaseException
    {
	Double result = new Double(0);

	if(value != null)
	{
	    result = Math.abs(Double.parseDouble(value.toString()));
	}

	return new BigDecimal(result.doubleValue());
    }

    public static Date today() throws BaseException
    {
	Calendar cal = Calendar.getInstance();
	Date result = cal.getTime();

	return result;
    }

    public static String now() throws BaseException
    {
	return DateUtil.format(Calendar.getInstance().getTime(), "HH:mm:ss");
    }

    /**
     * 
     * used to round a number following the sent precision
     * 
     * @param value
     * @param precision
     * @return BigDecimal
     * @throws BaseException
     */
    public static BigDecimal round_bigDec(Number value, Number precision) throws BaseException
    {
	BigDecimal result = null;
	StringBuffer presc = new StringBuffer("#.");

	if(value != null && precision != null && (precision.intValue() >= 0 && precision.intValue() <= 30))
	{
	    for(int i = 0; i < precision.intValue(); i++)
	    {
		presc.append("#");
	    }
	    nbFormat.setRoundingMode(RoundingMode.HALF_UP);
	    nbFormat.applyPattern(presc.toString());
	    result = new BigDecimal(nbFormat.format(value));
	}

	return result;
    }
    
    /**
     * 
     * used to round a number following the sent precision
     * 
     * @param value
     * @param precision
     * @return Double
     * @throws BaseException
     */
    public static Double round(Number value, Number precision) throws BaseException
    {
	Double result = null;
	StringBuffer presc = new StringBuffer("#.");

	if(value != null && precision != null && (precision.intValue() >= 0 && precision.intValue() <= 30))
	{
	    for(int i = 0; i < precision.intValue(); i++)
	    {
		presc.append("#");
	    }
	    nbFormat.setRoundingMode(RoundingMode.HALF_UP);
	    nbFormat.applyPattern(presc.toString());
	    result = Double.parseDouble(nbFormat.format(value));
	}

	return result;
    }

    public static Long len(Object value) throws BaseException
    {
	Long result = null;

	if(value != null)
	{
	    if(value instanceof Blob)
	    {
		try
		{
		    result = ((Blob) value).length();
		}
		catch(SQLException ex)
		{
		    ex.printStackTrace();
		    return Long.parseLong("-1");
		}
	    }
	    else if(value instanceof String)
	    {
		result = Long.parseLong(String.valueOf(((String) value).length()));
	    }
	}

	return result;
    }

    public static String upper(String value) throws BaseException
    {
	String result = null;

	if(value != null)
	{
	    result = value.toUpperCase(Locale.ENGLISH);
	}

	return result;
    }

    public static String lower(String value) throws BaseException
    {
	String result = null;

	if(value != null)
	{
	    result = value.toLowerCase(Locale.ENGLISH);
	}

	return result;
    }

    public static Long daysAfter(Date baseDate, Date forwardDate) throws BaseException
    {
	Long result = null;
	int oneDay = 24 * 60 * 60 * 1000;

	if(baseDate != null && forwardDate != null)
	{
	    result = forwardDate.getTime() - (baseDate.getTime());
	    result = result / oneDay;
	    // result = DateUtil.numberOfDays(baseDate, forwardDate);
	}
	return result;
    }

    public static String replace(String value, Integer startIndex, Integer numOfChar, String repVal)
	    throws BaseException
    {
	String result = null;
	StringBuffer buff = new StringBuffer();

	if(value != null && startIndex != null && numOfChar != null && repVal != null)
	{
	    buff.append(value);
	    if(startIndex > value.length())
	    {
		buff.append(repVal);
	    }
	    else
	    {
		buff.delete(startIndex - 1, startIndex - 1 + numOfChar);
		buff.insert(startIndex - 1, repVal);
	    }
	    result = buff.toString();
	}

	return result;
    }

    public static String mid(String value, Number startIndex) throws BaseException
    {
	String result = "";
	StringBuffer buff = new StringBuffer();

	if(value != null && startIndex != null && startIndex.intValue() < value.length())
	{
	    buff.append(value);
	    buff.delete(0, startIndex.intValue() - 1);
	    result = buff.toString();
	}

	return result;
    }

    public static String mid(String value, Number startIndex, Number lgth) throws BaseException
    {
      Number length=lgth;
      String result = "";
      int endIndex = 0;
      if(value != null && startIndex != null && length != null)
      {
          if(startIndex.intValue() > value.length())
          {
            return "";
          }
          if(length.intValue() > value.length())
          {
            length = value.length();
          }
          endIndex = startIndex.intValue() - 1 + length.intValue();
          if(endIndex >= value.length())
          {
            result = value.substring(startIndex.intValue() - 1).trim();
          }
          else
          {
            result = value.substring(startIndex.intValue() - 1, endIndex).trim();
            
          }
      }

      return result;
    }

    /**
     * corresponds to PB fill function that return a what String with nbTimes
     * 
     * @param what what to return
     * @param nbTimes how many times concatenated
     * @return
     */
    public static String fill(String what, int nbTimes) // Modified by Nathalie
    // the integer to
    // BigDecimal
    {

	return NumberUtil.fill(what, new BigDecimal(nbTimes));
	
    }
    /**
     * corresponds to PB space function that return a String with nbTimes spaces
     * 
     * @param nbTimes how many spaces 
     * @return
     */
    public static String space(int nbTimes) // Modified by Nathalie
    // the integer to
    // BigDecimal
    {
	String result = null;
	StringBuffer buff = new StringBuffer("");
	 for(int i = 1; i < nbTimes; i++)
	     {
	     	buff.append(" ");
	     }
	 result = buff.toString();
	return result;
	
    }
    public static BigDecimal mod(BigDecimal x, BigDecimal y )
    {	BigDecimal result ;
    	result = x.remainder(y);
	return result;
    }
    /**
     * case statment with 8 choices and else
     * 
     * @throws BaseException
     */
    public static Object myCase(Object obj1, Object Obj2, Object toresult2, Object Obj3, Object toresult3, Object Obj4,
	    Object toresult4, Object Obj5, Object toresult5, Object Obj6, Object toresult6, Object Obj7,
	    Object toresult7, Object Obj8, Object toresult8, Object Obj9, Object toresult9, Object toresultElse)
	    throws BaseException
    {
	return myCase(obj1, Obj2, toresult2, myCase(obj1, Obj3, toresult3, myCase(obj1, Obj4, toresult4, Obj5,
		toresult5, Obj6, toresult6, Obj7, toresult7, Obj8, toresult8, Obj9, toresult9, toresultElse)));

    }

    /**
     * case statment with 7 choices and else
     * 
     * @throws BaseException
     */
    public static Object myCase(Object obj1, Object Obj2, Object toresult2, Object Obj3, Object toresult3, Object Obj4,
	    Object toresult4, Object Obj5, Object toresult5, Object Obj6, Object toresult6, Object Obj7,
	    Object toresult7, Object Obj8, Object toresult8, Object toresultElse) throws BaseException
    {
	return myCase(obj1, Obj2, toresult2, myCase(obj1, Obj3, toresult3, myCase(obj1, Obj4, toresult4, Obj5,
		toresult5, Obj6, toresult6, Obj7, toresult7, Obj8, toresult8, toresultElse)));

    }

    /**
     * case statement with 6 choices and else
     * 
     * @throws BaseException
     */
    public static Object myCase(Object obj1, Object Obj2, Object toresult2, Object Obj3, Object toresult3, Object Obj4,
	    Object toresult4, Object Obj5, Object toresult5, Object Obj6, Object toresult6, Object Obj7,
	    Object toresult7, Object toresultElse) throws BaseException
    {
	return myCase(obj1, Obj2, toresult2, myCase(obj1, Obj3, toresult3, myCase(obj1, Obj4, toresult4, Obj5,
		toresult5, Obj6, toresult6, Obj7, toresult7, toresultElse)));

    }

    /**
     * case statement with 5 choices and else
     * 
     * @throws BaseException
     */
    public static Object myCase(Object obj1, Object Obj2, Object toresult2, Object Obj3, Object toresult3, Object Obj4,
	    Object toresult4, Object Obj5, Object toresult5, Object Obj6, Object toresult6, Object toresultElse)
	    throws BaseException
    {
	return myCase(obj1, Obj2, toresult2, myCase(obj1, Obj3, toresult3, myCase(obj1, Obj4, toresult4, Obj5,
		toresult5, Obj6, toresult6, toresultElse)));

    }

    /**
     * case statement with 4 choices and else
     * 
     * @throws BaseException
     */
    public static Object myCase(Object obj1, Object Obj2, Object toresult2, Object Obj3, Object toresult3, Object Obj4,
	    Object toresult4, Object Obj5, Object toresult5, Object toresultElse) throws BaseException
    {
	return myCase(obj1, Obj2, toresult2, myCase(obj1, Obj3, toresult3, myCase(obj1, Obj4, toresult4, Obj5,
		toresult5, toresultElse)));

    }

    /**
     * case statement with 3 choices and else
     * 
     * @throws BaseException
     */
    public static Object myCase(Object obj1, Object Obj2, Object toresult2, Object Obj3, Object toresult3, Object Obj4,
	    Object toresult4, Object toresultElse) throws BaseException
    {
	return myCase(obj1, Obj2, toresult2, myCase(obj1, Obj3, toresult3, Obj4, toresult4, toresultElse));

    }

    /**
     * case statement with 2 choices and else
     * 
     * @throws BaseException
     */
    public static Object myCase(Object obj1, Object Obj2, Object toresult2, Object Obj3, Object toresult3,
	    Object toresultElse) throws BaseException
    {
	return myCase(obj1, Obj2, toresult2, myCase(obj1, Obj3, toresult3, toresultElse));

    }

    /**
     * case statement with 1 choices and else
     * 
     * @throws BaseException
     */
    public static Object myCase(Object obj, Object toObj, Object toresultYes, Object toresultElse) throws BaseException
    {
	int toReturn = myCompare(obj, toObj);
	if(toReturn == 0)
	{
	    return toresultYes;
	}
	else
	{
	    return toresultElse;
	}
    }

    public static String trim(String value)
    {
	if(value != null)
	{
	    return value.trim();
	}
	return "";
    }

    public static String left(String value, int length)
    {
	if(value != null && length > 0 && value.length() > length)
	{
		return value.substring(0, length);
	}
	return StringUtil.nullToEmpty(value);
    }

    public static Integer sign(Number value)
    {
	if(value == null)
	{
	    return null;
	}
	if((value.intValue() > 0) || (value.doubleValue() > 0 && value.doubleValue() < 1))
	{
	    return 1;
	}
	if(value.intValue() < 0)
	{
	    return -1;
	}
	return 0;

    }

    public static Integer sign(BigDecimal value)
    {
	if(value == null)
	{
	    return null;
	}
	if(value.signum() > 0)
	{
	    return 1;
	}
	if(value.signum() < 0)
	{
	    return -1;
	}
	return 0;
    }

//    public static void main(String[] arg)
//    {
//	String a = "200909";
//	String year = a.substring(0, 4);
//	String month = a.substring(5);
//    }

    public static String wordcap(String value) throws BaseException
    {
	String result = null;
	if(value != null)
	{
	    result = WordUtils.capitalizeFully(value);
	}
	return result;

    }
    
    public static String dayname(Date inDate) throws BaseException
    {
          return DateUtil.getDayShortName(inDate);
    }

    public static Double truncate(Number value, Number precision) throws BaseException
    {
	Double result = null;
	StringBuffer presc = new StringBuffer("#.");
	if(value != null && precision != null && (precision.intValue() >= 0 && precision.intValue() <= 30))
	{
	    for(int i = 0; i < precision.intValue(); i++)
	    {
		presc.append("#");
	    }
	    nbFormat.setRoundingMode(RoundingMode.DOWN);
	    nbFormat.applyPattern(presc.toString());
	    result = Double.parseDouble(nbFormat.format(value));
	}
	return result;

    }

    public static String right(String value, int length)

    {

	if(value != null && length > 0)
	{

	    if(value.length() > length)
	    {	
		return value.substring(value.length() - length , value.length());
	    }
	    else
	    {
		return value;
	    }
	}
	return StringUtil.nullToEmpty(value);

    }
    

    /**
     * return the value sent as parameter without spaces at the beginning and the end
          * @param value
     * @return
          */
    public static String f_get_title(String value)
    {
    	if(value==null)
    	{
    		return value;
    	}
    	else
    	{
    		return value.trim();
    	}
    }

}
