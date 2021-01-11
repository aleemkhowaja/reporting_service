package com.path.bo.reporting.common.jreports;

//import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;

import com.path.lib.common.exception.BaseException;
import com.path.lib.log.Log;
import com.path.vo.reporting.ReportParamsCO;

public final class JrDataFunc
{
	static Log log = Log.getInstance();
	private JrDataFunc() 
	{
	    log.debug("This Class Should not be Instantiated");
	}
	@SuppressWarnings("unchecked")
	public static Double getField_Double(String fieldName, Integer nextPrevLocation,Object rowData,Long rowIndex, ArrayList dataArray) throws BaseException
	{
		Object theDouble = getField_Object(fieldName,nextPrevLocation,rowData,rowIndex,dataArray);
		if((theDouble instanceof Double))
		{			
		    return (Double)theDouble;
		}
		else
		{
		    return JrFunc.convertToDouble(theDouble);
		}
		
	}
	@SuppressWarnings("unchecked")
	public static Integer getField_Integer(String fieldName, Integer nextPrevLocation,Object rowData,Long rowIndex, ArrayList dataArray) throws BaseException
	{
		Object theInteger = getField_Object(fieldName,nextPrevLocation,rowData,rowIndex,dataArray);
		if((theInteger instanceof Integer))
		{
		    return (Integer)theInteger;
		}
		else
		{
		    return JrFunc.convertToInteger(theInteger);
		}
			
	}
	
	@SuppressWarnings("unchecked")
	public static Long getField_Long(String fieldName, Integer nextPrevLocation,Object rowData,Long rowIndex, ArrayList dataArray) throws BaseException
	{
		return (Long)getField_Object(fieldName,nextPrevLocation,rowData,rowIndex,dataArray);
	}
	
	@SuppressWarnings("unchecked")
	public static Date getField_Date(String fieldName, Integer nextPrevLocation,Object rowData,Long rowIndex, ArrayList dataArray) throws BaseException
	{
		return (Date)getField_Object(fieldName,nextPrevLocation,rowData,rowIndex,dataArray);
	}
	
	@SuppressWarnings("unchecked")
	public static String getField_String(String fieldName, Integer nextPrevLocation,Object rowData,Long rowIndex, ArrayList dataArray) throws BaseException
	{
		return (String)getField_Object(fieldName,nextPrevLocation,rowData,rowIndex,dataArray);
	}
	
	@SuppressWarnings("unchecked")
	private static Object getField_Object(String fieldName, Integer nextPrevLocation,Object rowData,Long rowIndex, ArrayList dataArray) throws BaseException
	{
		Object toReturn = null;
		if(fieldName == null)
		{
		    toReturn = null;
		}
		else
		{
			// check of not next or previous records to be managed
			if(nextPrevLocation == null)
			{
				// check if data is not empty record
				if(rowData != null)
				{
					if(rowData instanceof HashMap)
					{
						// check if the field available in the rowdata.
						if(((HashMap)rowData).containsKey(fieldName))
						{
							toReturn = ((HashMap)rowData).get(fieldName);
						}
						else
						{
							log.warning("\n\n\n fieldName="+fieldName+" is not found in HashMap rowData="+rowData+" \n\n\n");
						}
					}
					else
					{
						if(PropertyUtils.isReadable(rowData, fieldName))
						{
							try
							{
							 toReturn = PropertyUtils.getProperty(rowData, fieldName);
							}
							catch(Exception e)
							{
								log.error(e,"error in reading property in bean = "+rowData+" and field = "+fieldName);
							}
						}
						else
						{
							log.warning("\n\n\n fieldName="+fieldName+" is not found in Bean rowData="+rowData+" \n\n\n");
						}
							
					}
				}
			}
			else
			{
				long curRowIndx = rowIndex;// to be passed as parameter
				Long rowIndxToRead = curRowIndx + nextPrevLocation.intValue();
				if(rowIndxToRead < 0 || rowIndxToRead > dataArray.size())
				{
					toReturn = null;
				}
				else
				{
//					@SuppressWarnings("unused")
//					Iterator<HashMap> dataIt = dataArray.iterator();
					
					Object rowToRead = dataArray.get(rowIndxToRead.intValue());
					if(rowToRead instanceof HashMap)
					{
						if(((HashMap)rowToRead).containsKey(fieldName))
						{
							toReturn = ((HashMap)rowToRead).get(fieldName);
						}
						else
						{
							log.warning("\n\n\n fieldName="+fieldName+" is not found in HashMap [nextPrevLocation] rowToRead="+rowToRead+" of index="+rowIndxToRead+" \n\n\n");
						}
					}
					else
					{
						if(PropertyUtils.isReadable(rowData, fieldName))
						{
							try
							{
							 toReturn = PropertyUtils.getProperty(rowData, fieldName);
							}
							catch(Exception e)
							{
								log.error(e,"error in reading property [nextPrevLocation] in bean = "+rowData+" and field = "+fieldName);
							}
						}
						else
						{
							log.warning("\n\n\n fieldName="+fieldName+" is not found in Bean [nextPrevLocation] rowData="+rowData+" \n\n\n");
						}
					}
					/*while(dataIt.hasNext())
					{
						counter ++;
						// check if we reached to the same row
						if(counter == rowIndxToRead)
						{
							rowToRead = dataIt.next();
							// check if the field available in the rowdata.
							if(rowToRead.containsKey(fieldName))
							{
								toReturn = rowToRead.get(fieldName);
							}
							else
							{
								log.warning("\n\n\n fieldName="+fieldName+" is not found in rowToRead="+rowToRead+" \n\n\n");
							}
							break;
						}
					}*/
				}
				return toReturn;
			}	
		}
		return toReturn;
	}
	
	
	
	/**retrieve the Session Variable (PB Global Variables)*/
	public static Integer getGlobal_Integer(String fieldName,HttpServletRequest request)throws BaseException
	{
		Integer toReturn = null;
		toReturn = (Integer) request.getSession().getAttribute(fieldName);
		return toReturn;
	}
	public static Double getGlobal_Double(String fieldName,HttpServletRequest request)throws BaseException
	{
		Double toReturn = null;
		toReturn = (Double) request.getSession().getAttribute(fieldName);
		return toReturn;
	}
	public static Date getGlobal_Date(String fieldName,HttpServletRequest request)throws BaseException
	{
		Date toReturn = null;
		toReturn = (Date) request.getSession().getAttribute(fieldName);
		return toReturn;
	}
	public static String getGlobal_String(String fieldName,HttpServletRequest request)throws BaseException
	{
		String toReturn = null;
		toReturn = (String) request.getSession().getAttribute(fieldName);
		return toReturn;
	}
	public static Long getGlobal_Long(String fieldName,HttpServletRequest request)throws BaseException
	{
		Long toReturn = null;
		toReturn = (Long) request.getSession().getAttribute(fieldName);
		return toReturn;
	}
	
	/**
	 * return the Parameter of the Query retrieved
	 * @param paramName parameter name in the list of parameters could be property name or hashMap key
	 * @param paramBean
	 * @return
	 * @throws BaseException
	 */
	public static Double getArgument_Double(String paramName, Object paramBean) throws BaseException
	{
		Object theDouble = getArgument_Object(paramName,paramBean);
		if((theDouble instanceof Double))
		{
		    return (Double)theDouble;
		}
		else
		{
		    return JrFunc.convertToDouble(theDouble);
		}
	}
	public static Integer getArgument_Integer(String paramName, Object paramBean) throws BaseException
	{
		Object theInteger = getArgument_Object(paramName,paramBean);
		if((theInteger instanceof Integer))
		{
		    return (Integer)theInteger;
		}
		else
		{
			
		    return JrFunc.convertToInteger(theInteger);
		}
			
	}
	
	public static Long getArgument_Long(String paramName, Object paramBean) throws BaseException
	{
		return (Long)getArgument_Object(paramName, paramBean);
	}
	
	public static Date getArgument_Date(String paramName, Object paramBean) throws BaseException
	{
		return (Date)getArgument_Object(paramName,paramBean);
	}
	public static String getArgument_String(String paramName, Object paramBean) throws BaseException
	{
		return (String)getArgument_Object(paramName,paramBean);
	}
	
	@SuppressWarnings("unchecked")
	private static Object getArgument_Object(String paramName, Object paramBean) throws BaseException
	{
		Object toReturn = null;
		if(paramName == null)
		{
		    toReturn = null;
		}
		else
		{
							// check if data is not empty record
				if(paramBean != null)
				{
					if(paramBean instanceof HashMap)
					{
						if(((HashMap)paramBean).get(paramName) == null)
						{
						    log.warning("\n\n\n fieldName="+paramName+" is not found in Parameter Object (HashMap)="+paramBean+" \n\n\n");
						}
						else
						{
						    toReturn = ((HashMap)paramBean).get(paramName);
						}
					}
					else
					{
						if(PropertyUtils.isReadable(paramBean, paramName))
						{
							try
							{
							  toReturn = PropertyUtils.getProperty(paramBean, paramName);
							}
							catch(Exception e)
							{
								log.error(e,"getArgument_Object error in reading property "+paramName+" from bean "+paramBean);
							}
						}
					}
				}
		}
		return toReturn;
	}
	/**
	 * Method to set property in a specific bean Object (VO, HashMap)
	 * @param beanObj
	 * @param propertyName
	 * @return
	 * @throws BaseException
	 */
	public static void setProperty(Object beanObj, String propertyName, String theValue) throws BaseException
	{
		
		try
		{
			PropertyUtils.setProperty(beanObj, propertyName, theValue);
		} 
		catch (Exception e)
		{
			log.error(e,"Error in [setProperty] setting property ="+propertyName+" theValue ="+theValue+" beanObj="+beanObj);
			throw new BaseException("Error in setProperty mwthod of DataFunc Class",e);
		} 
	}
	
	/**
	 * Method to get property from a specific bean Object (VO, HashMap)
	 * @param beanObj
	 * @param propertyName
	 * @return
	 * @throws BaseException
	 */
	public static Object getProperty(Object beanObj, String propertyName) throws BaseException
	{
		try
		{
		  return PropertyUtils.getProperty(beanObj, propertyName);
		} 
		catch (Exception e)
		{
			log.error(e,"Error in [getProperty] getting property ="+propertyName+" beanObj="+beanObj);
			throw new BaseException("Error in getProperty mwthod of DataFunc Class",e);
		} 
	}
	
	public static String getListBoxValue(Object stat , String listOfVall, ReportParamsCO sessCO) throws BaseException
	{
		String result = "";
		String currStat = null;
		String[] arr;
		String value = null;
		String status = null;
		String listOfVal=listOfVall;
		
		if(stat instanceof String)
		{
			status = (String) stat;
		}
		else
		{
			status = stat.toString();
		}
		
		
		if (status != null && listOfVal != null && !listOfVal.isEmpty())
		{
			listOfVal = listOfVal.replaceAll("//", "!~!");
			arr = listOfVal.split("/");
			for (int i=0; i<arr.length; i++)
			{
				result = null;
				value = arr[i];
				if(!value.isEmpty())
				{
					value = value.replaceAll("\\t", "%%");  // replacing by %% instead of space " " because the result would be a space " " which cause conflict
					currStat = value.substring(value.lastIndexOf("%%")+2);
					result = value.substring(0,value.lastIndexOf("%%"));
					
					if(result.indexOf("!~!") >= 0)
					{
						result = result.replaceAll("!~!", "/");
					}
					try
					{
					    result = JrGlobal.f_translate_string(result,sessCO);
					}
					catch (Exception e)
					{
						log.error(e,"Error in translating the parameters of f_get_list_box_value");
						throw new BaseException("Error in translating the parameters of f_get_list_box_value",e);
					} 
					
					if(status.trim().equals(currStat))
					{
						return result;// sessCO.getDictMngr().translate(result, "reports") ;
					}
				}
			}
			result = "";
		}
		return result;
	}
}
