package com.path.dao.reporting.common;

import java.util.Collection;

import com.path.lib.common.exception.DAOException;
import com.path.lib.vo.MenuVO;
import com.path.vo.common.menu.MenuSC;

public interface RepMenuDAO {
	public Collection<MenuVO> getMenu(MenuSC menuSc) throws DAOException;
}
