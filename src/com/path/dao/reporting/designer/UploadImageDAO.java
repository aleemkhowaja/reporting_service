package com.path.dao.reporting.designer;

import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.lib.common.exception.DAOException;

public interface UploadImageDAO
{
    public PTH_CTRL1VO retImgParams() throws DAOException;
}
