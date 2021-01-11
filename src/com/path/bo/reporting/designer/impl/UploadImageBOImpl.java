package com.path.bo.reporting.designer.impl;

import com.path.bo.reporting.designer.UploadImageBO;
import com.path.dao.reporting.designer.UploadImageDAO;
import com.path.dbmaps.vo.IRP_REP_IMAGESVO;
import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;

public class UploadImageBOImpl  extends BaseBO implements UploadImageBO
{
    private UploadImageDAO uploadImageDAO;

    public UploadImageDAO getUploadImageDAO()
    {
        return uploadImageDAO;
    }

    public void setUploadImageDAO(UploadImageDAO uploadImageDAO)
    {
        this.uploadImageDAO = uploadImageDAO;
    }

    public PTH_CTRL1VO retImgParams() throws BaseException
    {
	return uploadImageDAO.retImgParams();
	
    }
    @Override
    public void saveUploadedImage(byte[] data, String fileName)  throws BaseException

    {
	IRP_REP_IMAGESVO imageVO = new IRP_REP_IMAGESVO();
	imageVO.setIMAGE_NAME(fileName);
	imageVO.setIMAGE_OBJECT(data);
	if(genericDAO.selectByPK(imageVO)==null)
	{
	    genericDAO.insert(imageVO);
	}
	else
	{
	    genericDAO.update(imageVO);
	}
	//dummy update for integration team
	IRP_REP_IMAGESVO dummyImgVO = new IRP_REP_IMAGESVO();
	dummyImgVO.setIMAGE_NAME(imageVO.getIMAGE_NAME());
	dummyImgVO.setIMAGE_OBJECT(imageVO.getIMAGE_OBJECT());
	genericDAO.update(dummyImgVO);
    }
    
    @Override
    public boolean checkImageExists(String newImageName) throws BaseException
    {
	IRP_REP_IMAGESVO imageVO = new IRP_REP_IMAGESVO();
	imageVO.setIMAGE_NAME(newImageName);
	if(genericDAO.selectByPK(imageVO) != null)
	{
	    return true;
	}
	return false;
    }

}
