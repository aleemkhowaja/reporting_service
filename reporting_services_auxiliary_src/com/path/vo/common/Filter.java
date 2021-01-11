package com.path.vo.common;

import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder={})
public class Filter {

	private String key;
	private String operator;
	private String value;

	public void setKey(String key)
	{
	   this.key = key;
	}
	public String getKey()
	{
	   return key;
	}
	public void setOperator(String operator)
	{
	   this.operator = operator;
	}
	public String getOperator()
	{
	   return operator;
	}
	public void setValue(String value)
	{
	   this.value = value;
	}
	public String getValue()
	{
	   return value;
	}
}
