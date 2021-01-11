package com.path.bo.reporting.reportviewer;

import java.util.ArrayList;

import com.path.lib.common.exception.BaseException;
import com.path.vo.reporting.reportviewer.ReportViewerCO;

public interface ReportViewerBO 
{
    /**
    * Method used to return list of ReportViewerCO related to link path header
    * 
    * @param ReportViewerCO
    *   Search ReportViewerCO Parameter
    * @return list
    * @throws BaseException
    */
    public ArrayList<ReportViewerCO> retLinkPathList(ReportViewerCO reportViewerCO)throws BaseException;
    /**
     * Method used to return list of ReportViewerCO related to left menu
     * 
     * @param ReportViewerCO
     *   Search ReportViewerCO Parameter
     * @return list
     * @throws BaseException
     */
    public ArrayList<ReportViewerCO> retLeftMenuList(ReportViewerCO reportViewerCO)throws BaseException;
    /**
     * Method used to return input file in byte
     * 
     * @param ReportViewerCO
     *   Search ReportViewerCO Parameter
     * @return byte
     * @throws BaseException
     */
    public byte[] retReportBytes(ReportViewerCO reportViewerCO)throws BaseException;
}
