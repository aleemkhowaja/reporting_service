package com.path.bo.reporting.designer;

import java.util.List;

import com.path.dbmaps.vo.IRP_REP_IMAGESVO;
import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.IRP_AD_HOC_REPORTSC;
import com.path.vo.reporting.ImageCO;

public interface ImageBO
{
    /**
     * Method to return images from db
     * @param sc
     * @return
     * @throws BaseException
     */
    public List<IRP_REP_IMAGESVO> retImagesGridsRecords(IRP_AD_HOC_REPORTSC sc)throws BaseException;
    /**
     * Method to count the images in db
     * @param sc
     * @return
     * @throws BaseException
     */
    public int retImagesGridRecordsCount(IRP_AD_HOC_REPORTSC sc) throws BaseException;
    public void deleteImg(ImageCO imgCO) throws BaseException;
    /**
     * Method to retrive an image object from db
     * @param imageName
     * @return
     * @throws BaseException
     */
    public byte[] retImgObject(String imageName) throws BaseException;
}
