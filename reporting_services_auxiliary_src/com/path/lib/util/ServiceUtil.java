package com.path.lib.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;

import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.NumberUtil;
import com.path.lib.common.util.StringUtil;
import com.path.vo.common.DynamicFilter;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 
 * @author EliasAoun
 *
 */
public class ServiceUtil
{
    /**
     * Dynamic method used for filter in grid list when called from webservice
     * 
     * @param : searchCol : array of columns used as filter in the grid
     *            filtersMap : contains the value for each criteria
     */
    public static Map<String, Object> fillFilterMap(String[] searchCol, HashMap<String, Object> filtersMap)
    {
	Map<String, Object> paramMap = new HashMap<String, Object>();
	for(int i = 0; i < searchCol.length; i++)
	{
	    String columnName = searchCol[i];
	    String[] value = new String[1];
	    if(filtersMap.get(columnName) != null)
	    {
		String[] value1 = new String[1];
		// in case of number
		if(filtersMap.get(columnName) instanceof BigDecimal || filtersMap.get(columnName) instanceof Integer
			|| filtersMap.get(columnName) instanceof Double || filtersMap.get(columnName) instanceof Float)
		{
		    value[0] = filtersMap.get(columnName).toString();
		    paramMap.put(columnName, value);
		    value1[0] = "1";
		    paramMap.put(columnName + "_isNbr", value1);

		}
		else
		{
		    // in case of text
		    if(filtersMap.get(columnName) instanceof String)
		    {
			if(filtersMap.get(columnName).toString().length() > 0)
			{
			    value[0] = filtersMap.get(columnName).toString();
			    paramMap.put(columnName, value);
			    value1[0] = "0";
			    paramMap.put(columnName + "_isNbr", value1);
			}
		    }
		    else
		    {
			value[0] = filtersMap.get(columnName).toString();
			paramMap.put(columnName, value);
			value1[0] = "0";
			paramMap.put(columnName + "_isNbr", value1);
		    }
		}
	    }
	}
	return paramMap;
    }
    
    
    public static String filterAndValidateList(DynamicFilter dynamicFilter, String[] searchCol) throws BaseException
    {
	if(dynamicFilter == null || dynamicFilter.getFilters() == null)
	{
	    //TODO should be null ??
	    return "";
	}
	Object[][] array = new Object[dynamicFilter.getFilters().size()][4];
	for(int i = 0; i < dynamicFilter.getFilters().size(); i++)
	{
	    String key = dynamicFilter.getFilters().get(i).getKey();
	    if(key == null || "".equals(key))
	    {
		// TODO should be null ??
		return "";
	    }
	    String colName = key;
	    String type = "text";
	    if(key.indexOf(".") > -1)
	    {
		String[] keySplited = key.split("\\.");
		colName = keySplited[1];
		String className = keySplited[0].toUpperCase() + "VO";
		Class<?> clazz;
		try
		{
		    clazz = Class.forName("com.path.dbmaps.vo." + className);
		    Class<?> typeClass = PropertyUtils.getPropertyType(clazz.newInstance(),
			    keySplited[1].replace("ABS(", "").replace(")", "").toUpperCase());
		    if(typeClass != null)
		    {
			if(typeClass.getName().contains("String"))
			{
			    // colTypeMap.put(key[1].toUpperCase(), "text");
			    type = "text";
			}
			else if(typeClass.getName().contains("BigDecimal"))
			{
			    // colTypeMap.put(key[1].toUpperCase(),
			    // "number");
			    type = "number";
			}
			else if(typeClass.getName().contains("Date"))
			{
			    // colTypeMap.put(key[1].toUpperCase(), "date");
			    type = "date";
			}
		    }
		    else
		    {
			// colTypeMap.put(key[1].toUpperCase(), "text");
			type = "text";
		    }
		}
		catch(Exception e)
		{
		    // TODO Auto-generated catch block
		    e.printStackTrace();
		    // colTypeMap.put(key[1].toUpperCase(), "text");
		    type = "text";
		    //Bug Tp #885906
		    throw new BOException("Invalid Table or Alias name ");
		}
	    }
	    // added if condition by bahaa
	    if(!(colName == null && dynamicFilter.getFilters().get(i).getOperator() == null
		    && dynamicFilter.getFilters().get(i).getValue() == null))
	    {
		// String type =
		// retAuxServiceSC.getColTypeMap().get(StringUtil.nullToEmpty(key));
		// added StringUtil.nullToEmpty by retAuxServiceCO for #bug
		// 726837
		String column = StringUtil.nullToEmpty(colName).toUpperCase();
		String operator = StringUtil
			.nullToEmpty(dynamicFilter.getFilters().get(i).getOperator());
		String value = StringUtil.nullToEmpty(dynamicFilter.getFilters().get(i).getValue());
		
		String[] searchCols = searchCol;
		if(searchCols != null && Arrays.asList(searchCols).contains(column))
		{
		    List<String> list = new ArrayList<String>(Arrays.asList(searchCols));
		    list.remove(column);
		    searchCols = list.toArray(new String[0]);
		    //TODO test this
		    searchCol = searchCols;
		}

		if("date".equals(type))
		{
		    value = convertFormatDateTime(value);
		    //TODO check this
//		    if(retAuxServiceSC.getDateSearchCols() == null)
//		    {
//			retAuxServiceSC.setDateSearchCols(new HashMap<>());
//		    }
//		    retAuxServiceSC.getDateSearchCols().put(colName, value);
		}
		// commented if condition by bahaa for #BUG 726837
		// if(column != null && operator != null && value != null)
		// {
		// validate column name
		if(!"number".equals(type) && !"text".equals(type) && !"date".equals(type))
		{
		    // need to use ctsmessage instead this
		    throw new BOException("Invalid column Name " + column);
		}

		// validate on operator of every column type
		if((("number".equals(type) || "date".equals(type))
			&& (!"eq".equals(operator) && !"ne".equals(operator) && !"lt".equals(operator)
				&& !"le".equals(operator) && !"gt".equals(operator) && !"ge".equals(operator)
				&& !"in".equals(operator) && !"not in".equals(operator)))
			|| ("text".equals(type) && (!"eq".equals(operator) && !"ne".equals(operator)
				&& !"bw".equals(operator) && !"bn".equals(operator) && !"ew".equals(operator)
				&& !"en".equals(operator) && !"cn".equals(operator) && !"nc".equals(operator)
				&& !"nn".equals(operator) && !"nu".equals(operator) && !"in".equals(operator)
				&& !"not in".equals(operator))))
		{
		    // need to use ctsmessage instead this
		    throw new BOException("Invalid Operator " + operator + " For Type " + type);
		}
		
		if(!"All".equals(dynamicFilter.getAllAny())
			&& !"Any".equals(dynamicFilter.getAllAny()))
		{
		    throw new BOException("Invalid Operator AllAny");
		}

		// added by bahaa for #BUG 726982
		if("number".equals(type) && !NumberUtil.isNumber(value)
			&& !"in".equals(operator) && !"not in".equals(operator))//because in case operator is "in | not in" it contains ","
		{
		    // need to use ctsmessage instead this
		    throw new BOException("Invalid value for key with type Numeric");
		}

		if("number".equals(type) && NumberUtil.isNumber(value))
		{
		    value = removeLeadingZeros(value);
		}

		if("in".equals(operator)
		    || "not in".equals(operator))
		{
		    if("text".equals(type))
		    {
			//in case of varchar, e.g : turn 1, 36, 5 into '1', '36', '5'
			array[i][1] = "'" + String.join("','", value.split(",")) + "'";
		    }
		    else
		    {
			//in case of number, e.g: 1, 36, 5
			array[i][1] = value;
		    }
		}
		else if("nu".equals(operator) || "nn".equals(operator))
		{
		    array[i][1] = "";
		}

		else
		{
		    array[i][1] = value;
		}

		array[i][0] = column;
		array[i][2] = operator;
		array[i][3] = type;
		// }
	    }
	}

	return fillAdvancedFilterMap(array, dynamicFilter.getAllAny());
    }
    
    
    private static String convertFormatDateTime(String dateString) throws BaseException
    {
	String pattern = "";
	pattern = "yyyy-MM-dd";

	try
	{
	    SimpleDateFormat sdf = new SimpleDateFormat(pattern);
	    Date date = (Date) sdf.parse(dateString);
	    sdf.setLenient(false);
	    date = (Date) sdf.parse(dateString);
	    return DateUtil.format(date, "dd/MM/yyyy");
	}
	catch(Exception e)
	{
	    throw new BaseException(e);
	}
    }
    
    
    private static String removeLeadingZeros(String str)
    {
	String strPattern = "^0+(?!$)";
	return str.replaceAll(strPattern, "");
    }
    
    
    private static String fillAdvancedFilterMap(Object[][] objArr, String anyAll)
    {
	JSONArray array = new JSONArray();
	JSONObject item;
	for(Object[] row : objArr)
	{
	    // in case the value is null, which mean it is not sent,
	    // then do not send it as filter parameter
	    // GridBaseAction.getRuleQuery()
	    if(row[1] == null || row[3] == null)
	    {
		continue;
	    }
	    item = new JSONObject();
	    item.put("field", row[0].toString());
	    item.put("data", row[1]);
	    item.put("op", row[2].toString());
	    item.put("colType", row[3].toString());

	    array.add(item);
	}
	if("Any".equals(anyAll))
	{
	    anyAll = "OR";
	}
	else
	{
	    anyAll = "AND";
	}
	JSONObject filters = new JSONObject();
	filters.put("groupOp", anyAll);
	filters.put("rules", array);
	return filters.toString();
    }

}
