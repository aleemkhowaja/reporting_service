package com.path.vo.reporting.ftr.columnTemplate;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;

import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNSVO;
import com.path.dbmaps.vo.IRP_TEMPLATE_COLUMNS_DETVO;

public class IRP_TEMPLATE_COLUMNSCO extends IRP_TEMPLATE_COLUMNSVO {
	private List<IRP_TEMPLATE_COLUMNS_DETCO> columnDetails;
	private HashMap<BigDecimal,IRP_TEMPLATE_COLUMNS_DETVO> oldColDetMap=new HashMap<BigDecimal, IRP_TEMPLATE_COLUMNS_DETVO>();

	public List<IRP_TEMPLATE_COLUMNS_DETCO> getColumnDetails() {
		return columnDetails;
	}
	
	public void setColumnDetails(List<IRP_TEMPLATE_COLUMNS_DETCO> columnDetails) {
		this.columnDetails = columnDetails;
	}

	public HashMap<BigDecimal, IRP_TEMPLATE_COLUMNS_DETVO> getOldColDetMap() {
		return oldColDetMap;
	}

	public void setOldColDetMap(
			HashMap<BigDecimal, IRP_TEMPLATE_COLUMNS_DETVO> oldColDetMap) {
		this.oldColDetMap = oldColDetMap;
	}
}
