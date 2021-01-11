package com.path.bo.reporting.designer;

import com.path.dbmaps.vo.PTH_CTRL1VO;
import com.path.lib.common.exception.BaseException;

public interface UploadImageBO
{
    public PTH_CTRL1VO retImgParams()throws BaseException; 
    public void saveUploadedImage(byte[] data, String fileName) throws BaseException;
    public boolean checkImageExists(String newImageName) throws BaseException;

}