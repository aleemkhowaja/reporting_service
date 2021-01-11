/**
 * 
 */
package com.path.vo.reporting.reportviewer;


import com.path.lib.vo.BaseVO;

public class ReportViewerCO extends BaseVO
{
    private String elementPath;
    private String elementName;
    private String elementType;
    
    public String getElementPath()
    {
        return elementPath;
    }
    public void setElementPath(String elementPath)
    {
        this.elementPath = elementPath;
    }
    public String getElementName()
    {
        return elementName;
    }
    public void setElementName(String elementName)
    {
        this.elementName = elementName;
    }
    public String getElementType()
    {
        return elementType;
    }
    public void setElementType(String elementType)
    {
        this.elementType = elementType;
    }
}
