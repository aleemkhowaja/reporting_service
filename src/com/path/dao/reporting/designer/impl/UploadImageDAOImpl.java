package com.path.dao.reporting.designer.impl;

import com.path.dao.reporting.designer.UploadImageDAO;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.lib.common.base.BaseDAO;
import com.path.lib.common.exception.DAOException;

public class UploadImageDAOImpl extends BaseDAO implements UploadImageDAO
{

    public PTH_CTRL1VO retImgParams() throws DAOException
    {
	return (PTH_CTRL1VO) getSqlMap().queryForObject("uploadImage.retImgParams", null);
    }

}
