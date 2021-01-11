package com.path.bo.reporting.common;

import java.util.ArrayList;
import java.util.List;

import com.path.lib.common.exception.BaseException;
import com.path.lib.vo.MenuVO;
import com.path.vo.reporting.IRP_AD_HOC_QUERYCO;

public interface RepMenuBO {
	public ArrayList<MenuVO> queriesMenu(String menuId, List<IRP_AD_HOC_QUERYCO> queriesList, String... menuTrans) throws BaseException;
}