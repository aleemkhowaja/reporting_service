package com.path.vo.common;
import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlType;

import com.path.lib.vo.BaseObject;

@XmlType(propOrder={})
public class DynamicFilter extends BaseObject
{
	private String allAny;
	private ArrayList<Filter> filters= new ArrayList<>();
	
	public String getAllAny() {
		return allAny;
	}
	public void setAllAny(String allAny) {
		this.allAny = allAny;
	}
	
	@XmlElementWrapper(name = "filters")
	   @XmlElement(name = "filter")
	public ArrayList<Filter> getFilters() {
		return filters;
	}
	public void setFilters(ArrayList<Filter> filters) {
		this.filters = filters;
	}
}
