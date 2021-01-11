package com.path.bo.reporting.designer.impl;
import java.util.List;

import com.path.bo.reporting.designer.ImageBO;
import com.path.dao.reporting.designer.DesignerDAO;
import com.path.dbmaps.vo.IRP_REP_IMAGESVO;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ImageCO;

public class ImageBOImpl extends BaseBO  implements ImageBO
{
    private DesignerDAO designerDAO;
    
    @Override
    public List<IRP_REP_IMAGESVO> retImagesGridsRecords(IRP_AD_HOC_REPORTSC sc)throws BaseException
    {
	return designerDAO.retImagesGridsRecords(sc);
    }
    
    @Override
    public int retImagesGridRecordsCount(IRP_AD_HOC_REPORTSC sc) throws BaseException
    {
	return designerDAO.retImagesGridRecordsCount(sc);
    }
    
    @Override
    public void deleteImg(ImageCO imgCO) throws BaseException
    {
	IRP_REP_IMAGESVO imageVO = new IRP_REP_IMAGESVO();
	imageVO.setIMAGE_NAME(imgCO.getFileName());
	genericDAO.delete(imageVO);
    }
    
    
    
    @Override
    public byte[] retImgObject(String imageName) throws BaseException
    {
	IRP_REP_IMAGESVO vo = new IRP_REP_IMAGESVO();
	vo.setIMAGE_NAME(imageName);
	vo = (IRP_REP_IMAGESVO) genericDAO.selectByPK(vo);
	return (vo==null)? null:vo.getIMAGE_OBJECT();
    }


    public void setDesignerDAO(DesignerDAO designerDAO)
    {
        this.designerDAO = designerDAO;
    }


}
