package com.path.bo.reporting.common.util;

import java.awt.Color;
import java.io.File;
import java.math.BigDecimal;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.sf.jasperreports.components.table.DesignCell;
import net.sf.jasperreports.components.table.StandardColumn;
import net.sf.jasperreports.components.table.StandardTable;
import net.sf.jasperreports.components.table.WhenNoDataTypeTableEnum;
import net.sf.jasperreports.crosstabs.design.JRDesignCrosstab;
import net.sf.jasperreports.engine.JRBand;
import net.sf.jasperreports.engine.JRElement;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRFrame;
import net.sf.jasperreports.engine.JRGroup;
import net.sf.jasperreports.engine.JRPropertiesMap;
import net.sf.jasperreports.engine.JRSection;
import net.sf.jasperreports.engine.JRSortField;
import net.sf.jasperreports.engine.JRTextField;
import net.sf.jasperreports.engine.JRVariable;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.component.Component;
import net.sf.jasperreports.engine.design.JRDesignBand;
import net.sf.jasperreports.engine.design.JRDesignChart;
import net.sf.jasperreports.engine.design.JRDesignComponentElement;
import net.sf.jasperreports.engine.design.JRDesignDataset;
import net.sf.jasperreports.engine.design.JRDesignExpression;
import net.sf.jasperreports.engine.design.JRDesignGroup;
import net.sf.jasperreports.engine.design.JRDesignImage;
import net.sf.jasperreports.engine.design.JRDesignParameter;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JRDesignSection;
import net.sf.jasperreports.engine.design.JRDesignSortField;
import net.sf.jasperreports.engine.design.JRDesignStaticText;
import net.sf.jasperreports.engine.design.JRDesignTextField;
import net.sf.jasperreports.engine.design.JRDesignVariable;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.type.HorizontalTextAlignEnum;
import net.sf.jasperreports.engine.type.HyperlinkTargetEnum;
import net.sf.jasperreports.engine.type.HyperlinkTypeEnum;
import net.sf.jasperreports.engine.type.ModeEnum;
import net.sf.jasperreports.engine.type.RunDirectionEnum;
import net.sf.jasperreports.engine.type.SortFieldTypeEnum;
import net.sf.jasperreports.engine.type.SortOrderEnum;
import net.sf.jasperreports.engine.type.SplitTypeEnum;
import net.sf.jasperreports.engine.type.VerticalTextAlignEnum;
import net.sf.jasperreports.engine.type.WhenNoDataTypeEnum;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.xml.JRXmlWriter;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.dbmaps.vo.GLSHEADERVO;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.DateUtil;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.lib.common.util.SecurityUtils;
import com.path.lib.common.util.StringUtil;
import com.path.lib.log.Log;
import com.path.reporting.lib.common.util.CommonUtil;
import com.path.vo.reporting.CONDITION_OBJECT;
import com.path.vo.reporting.IRP_AD_HOC_REPORTCO;
import com.path.vo.reporting.IRP_FIELDSCO;
import com.path.vo.reporting.IRP_REP_ARGUMENTSCO;
import com.path.vo.reporting.IRP_SUB_REPORTCO;
import com.path.vo.reporting.ImageCO;
import com.path.vo.reporting.ReportParamsCO;
import com.path.vo.reporting.ftr.reportsmismatch.REP_MISMATCH_COLUMNCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_DRILLDOWN_COLUMNCO;
import com.path.vo.reporting.ftr.snapshots.REP_SNAPSHOT_MODIFY_COLUMNCO;

public final class ReportUtils
{

    private static final Log log = Log.getInstance();
    private ReportUtils() 
    {
	log.debug("This Class Should not be Instantiated");
    }

    public static void mirrorLayout(JasperDesign design, IRP_SUB_REPORTCO subrep, String reportFormat) throws BaseException
    {
	try
	{
	int pageWidth = design.getPageWidth();
	if(subrep != null) // when report is a sub report we assign the report
	// width of this report in main report to pagewidth
	{
	    pageWidth = subrep.getSubRepWidth();
	}
	int rightMargin = design.getRightMargin();
	int leftMargin = design.getLeftMargin();
	JRBand titleBand = design.getTitle();
	int totalWidth = pageWidth - leftMargin - rightMargin;
	JRElement[] elements = null;
	if(titleBand != null)
	{
	    elements = titleBand.getElements();
	    mirrorDesign(elements, totalWidth, reportFormat);
	}

	JRBand pageHeaderBand = design.getPageHeader();
	if(pageHeaderBand != null)
	{
	    elements = pageHeaderBand.getElements();
	    mirrorDesign(elements, totalWidth, reportFormat);
	}
	// Add part to code related to page summary band to be reversed
	JRBand PageSummaryBand = design.getSummary();
	if(PageSummaryBand != null)
	{
	    elements = PageSummaryBand.getElements();
	    mirrorDesign(elements, totalWidth, reportFormat);
	}
	JRBand columnHeaderBand = design.getColumnHeader();
	if(columnHeaderBand != null)
	{
	    elements = columnHeaderBand.getElements();
	    mirrorDesign(elements, totalWidth, reportFormat);
	}

	JRSection detailsBand = design.getDetailSection();
	if(detailsBand != null)
	{
	    JRBand[] details = detailsBand.getBands();
	    for(int i = 0; i < details.length; i++)
	    {
		elements = details[i].getElements();
		mirrorDesign(elements, totalWidth, reportFormat);
	    }
	}

	JRBand columnFooterBand = design.getColumnFooter();
	if(columnFooterBand != null)
	{
	    elements = columnFooterBand.getElements();
	    mirrorDesign(elements, totalWidth, reportFormat);
	}

	JRBand pageFooterBand = design.getPageFooter();
	if(pageFooterBand != null)
	{
	    elements = pageFooterBand.getElements();
	    mirrorDesign(elements, totalWidth, reportFormat);
	}
	// code added to read the groupHeader & groupFooter Elements
	JRGroup[] groups = design.getGroups();
	for(int i = 0; i < groups.length; i++)
	{
	    JRSection groupHeaderBand = groups[i].getGroupHeaderSection();
	    JRBand[] groupsHeader = groupHeaderBand.getBands();
	    if(groupsHeader != null)
	    {
		for(int k = 0; k < groupsHeader.length; ++k)
		{
		    elements = groupsHeader[k].getElements();
		    mirrorDesign(elements, totalWidth, reportFormat);
		}
	    }
	    JRSection groupFooterBand = groups[i].getGroupFooterSection();
	    JRBand[] groupsFooter = groupFooterBand.getBands();
	    if(groupsFooter != null)
	    {
		for(int k = 0; k < groupsFooter.length; ++k)
		{
		    elements = groupsFooter[k].getElements();
		    mirrorDesign(elements, totalWidth, reportFormat);
		}
	    }

	}

	design.setRightMargin(leftMargin);
	design.setLeftMargin(rightMargin);
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in Mirroring the Layout: "+e.getMessage(),e);
	}
    }

    private static void mirrorDesign(JRElement[] elements, int totalWidth, String reportFormat) throws BaseException
    {
	try
	{
	JRElement element;
	JRTextField textField;
	JRPropertiesMap propMap;
	for(int i = 0; i < elements.length; i++)
	{
	    element = elements[i];
	    if(!((ConstantsCommon.HTML_EXT).equalsIgnoreCase(reportFormat)))
	    {
		int mirrorX = totalWidth - element.getX() - element.getWidth();
		element.setX(mirrorX);
	    }
	    if(element instanceof JRTextField)
	    {
		textField = (JRTextField) element;
		propMap = textField.getPropertiesMap();
		if(textField.getHorizontalTextAlign() == HorizontalTextAlignEnum.LEFT)
		{
		    textField.setHorizontalTextAlign(HorizontalTextAlignEnum.RIGHT);
		}

		else if ( (textField.getHorizontalTextAlign() == HorizontalTextAlignEnum.RIGHT) && !("0".equals(propMap.getProperty("MirrorFlag"))) )
		{
			textField.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
		}
	    }
	    if(element instanceof JRFrame)
	    {
		JRFrame frame = (JRFrame) element;
		mirrorDesign(frame.getElements(), frame.getWidth(), reportFormat);
	    }
	    if((element instanceof JRDesignCrosstab) && !((ConstantsCommon.HTML_EXT).equalsIgnoreCase(reportFormat)) ) 
            {
		    JRDesignCrosstab crosstab = (JRDesignCrosstab) element;
		    crosstab.setRunDirection(RunDirectionEnum.RTL);
	    }
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in Mirroring the design: "+e.getMessage(),e);
	}
    }

    // annasuccar- 15 jan 2014: commented out the method below since the
    // orientation of fcr reports is always landscape
    // and the pattern is set in the f_currency_mask_def_sign() and
    // f_currency_mask_def_sign_without_dec() in jrglobal
    // public static void changeDefaultSettings(JasperDesign jasperDesign, int
    // orientation, int decimalPts, String dispVal)
    // {
    // if(orientation == 2) // Portrait
    // {
    // jasperDesign.setOrientation(OrientationEnum.PORTRAIT);
    // jasperDesign.setPageWidth(595);
    // jasperDesign.setPageHeight(842);
    // jasperDesign.setColumnWidth(555);
    //
    // JRElement[] children = jasperDesign.getTitle().getElements();
    // children[0].setX(122); // set x-coordinate for report title
    // children[3].setX(424); // set x-coordinate for report date
    // }
    //
    // if("D".equals(dispVal) && decimalPts > 0)
    // {
    // JRElement[] elements = jasperDesign.getSummary().getElements();
    // JRElement crosstab = (JRElement) elements[0];
    // if(crosstab instanceof JRDesignCrosstab)
    // {
    // // List<JRDesignCrosstabCell> cellsList = ((JRDesignCrosstab)
    // // crosstab).getCellsList();
    // List<JRCrosstabCell> cellsList = ((JRDesignCrosstab)
    // crosstab).getCellsList();
    // // JRDesignCrosstabCell cell = cellsList.get(0);
    // // JRCellContents content = cell.getContents();
    // // JRDesignTextField textfield = (JRDesignTextField)
    // // content.getChildren().get(0);
    // JRCrosstabCell cell = cellsList.get(0);
    // JRCellContents content = cell.getContents();
    // JRDesignTextField textfield = (JRDesignTextField)
    // content.getChildren().get(0);
    //
    // String pattern = "#,##0;(#,##0)";
    // if(decimalPts == 1)
    // pattern = "#,##0.0;(#,##0.0)";
    // else if(decimalPts == 2)
    // pattern = "#,##0.00;(#,##0.00)";
    // else if(decimalPts == 3)
    // pattern = "#,##0.000;(#,##0.000)";
    //
    // textfield.setPattern(pattern);
    // }
    // }
    // }

    public static void updateImageLocation(JasperDesign jasperDesign, String serverUrl, String saveCopyLocation,
	    String reportFormat)throws BaseException
    {
	try
	{
	    JRBand[] allBandList = jasperDesign.getAllBands();
	    if(allBandList != null)
	    {
		for(int b = 0; b < allBandList.length; b++)
		{
		    changeImageSrc(allBandList[b].getElements(), serverUrl, saveCopyLocation,reportFormat);
		}
	    }
    	}
	catch(Exception e)
	{
	    log.error(e, e.getMessage());
	    throw new BaseException("Error in updating the image location: "+e.getMessage(),e);
	}
    }

    public static void changeImageSrc(JRElement[] elements, String serverUrl, String saveCopyLocation,String reportFormat) throws BaseException
    {
	try
	{
	    JRElement elem;
	    JRDesignImage img;
	    JRDesignComponentElement compElem;
	    JRDesignExpression expr;
	    String text;
	    String imgName;
	    Component comp;
	    StandardTable tbl;
	    List columns;
	    StandardColumn col;
	    DesignCell head;
	    DesignCell foot;
	    DesignCell det;
	    JRElement[] headElems;
	    JRElement[] footElems;
	    JRElement[] detElems;
	    for(int i = 0; i < elements.length; i++)
	    {
		elem = elements[i];
		if(elem instanceof JRDesignImage)
		{
		    img = (JRDesignImage) elem;
		    // set the image expression to be equal to a url action for
		    // standard images
		    if(!img.getPropertiesMap().containsProperty("blobImg"))
		    {
			if("".equals(saveCopyLocation))
			{
			    img.setLazy(true);
			}
			expr = (JRDesignExpression) img.getExpression();
			text = expr.getText();
			imgName = text.substring(text.lastIndexOf("/") + 1, text.length() - 1);
			expr.setText("\"" + serverUrl + "servlets/image?reportFormat="+reportFormat+"&image=" + imgName +"&rand="+Calendar.getInstance().getTimeInMillis()+"\"");
		    }
		}
		else if(elem instanceof JRDesignComponentElement)
		{
		    compElem = (JRDesignComponentElement) elem;
		    comp = compElem.getComponent();
		    if(comp instanceof StandardTable)
		    {
			tbl = (StandardTable) comp;
			columns = tbl.getColumns();
			for(int k = 0; k < columns.size(); k++)
			{
			    col = (StandardColumn) columns.get(k);
			    head = (DesignCell) col.getColumnHeader();
			    foot = (DesignCell) col.getTableFooter();
			    det = (DesignCell) col.getDetailCell();

			    if(head != null)
			    {
				headElems = head.getElements();
				if(headElems != null)
				{
				    changeImageSrc(headElems, serverUrl, saveCopyLocation,reportFormat);
				}
			    }

			    if(foot != null)
			    {
				footElems = foot.getElements();
				if(footElems != null)
				{
				    changeImageSrc(footElems, serverUrl, saveCopyLocation,reportFormat);
				}
			    }
			    detElems = det.getElements();
			    if(detElems != null)
			    {
				changeImageSrc(detElems, serverUrl, saveCopyLocation,reportFormat);
			    }
			}
		    }
		}
	    }

	}
	catch(Exception e)
	{
	    throw new BaseException("Error in changing the image source: "+e.getMessage(),e);
	}
    }

    public static void addSortFields(JasperDesign jasperDesign, IRP_AD_HOC_REPORTCO reportSession) throws BaseException
    {
	try
	{
	    JRDesignDataset ds;
	    if(jasperDesign.getDatasetsList().size() == 0)// freeForm
	    {
		ds = jasperDesign.getMainDesignDataset();
	    }
	    else
	    {
		ds = (JRDesignDataset) jasperDesign.getDatasetsList().get(0);
	    }

	    List<IRP_FIELDSCO> orderFeLst = reportSession.getPrevOrderList();
	    IRP_FIELDSCO feCO;
	    // List<JRDesignSortField> sortList;
	    List<JRSortField> sortList;
	    if(ds == null)
	    {

		sortList = jasperDesign.getSortFieldsList();
	    }
	    else
	    {
		sortList = ds.getSortFieldsList();

	    }
	    JRDesignSortField sortField;

	    for(int i = 0; i < orderFeLst.size(); i++)
	    {
		feCO = orderFeLst.get(i);
		if(feCO.getOrder() != null && !feCO.getOrder().equals(""))
		{
		    sortField = new JRDesignSortField();
		    sortField.setName(feCO.getLabelAlias());
		    if(feCO.getOrder() != null && feCO.getOrder().equalsIgnoreCase("asc"))
		    {
			sortField.setOrder(SortOrderEnum.ASCENDING);
		    }
		    else
		    {
			sortField.setOrder(SortOrderEnum.DESCENDING);
		    }
		    sortField.setType(SortFieldTypeEnum.FIELD);
		    sortList.add(sortField);
		}
	    }
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding sorting fields: "+e.getMessage(),e);
	}
    }

    public static void addFilterFields(JasperDesign jasperDesign, IRP_AD_HOC_REPORTCO reportSession)throws BaseException
    {
	try
	{
	    LinkedHashMap<Long, CONDITION_OBJECT> filterMap = reportSession.getPrevFilterMap();
	    if(!filterMap.isEmpty())
	    {
		JRDesignExpression filterExpr = new JRDesignExpression();
		filterExpr.setText(generateExprText(filterMap));
		filterExpr.setValueClass(java.lang.Boolean.class);
		JRDesignDataset ds;
		if(jasperDesign.getDatasetsList().size() == 0)// freeForm
		{
		    ds = jasperDesign.getMainDesignDataset();
		}
		else
		{
		    ds = (JRDesignDataset) jasperDesign.getDatasetsList().get(0);
		}

		if(ds == null)
		{
		    jasperDesign.setFilterExpression(filterExpr);
		}
		else
		{
		    ds.setFilterExpression(filterExpr);
		}
	    }
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding filter fields: "+e.getMessage(),e);
	}
    }

    public static String generateExprText(LinkedHashMap<Long, CONDITION_OBJECT> filterMap)throws BaseException
    {
	StringBuffer  exprText=new StringBuffer("");
	try
	{
	Iterator it = filterMap.values().iterator();
	CONDITION_OBJECT cond;
	IRP_FIELDSCO fe;
	String oper;
	while(it.hasNext())
	{
	    cond = (CONDITION_OBJECT) it.next();
	    fe = cond.getField1();
	    String conj = cond.getConjunctionName();
	    if(conj != null && conj.equals(""))
	    {
		conj = null;
	    }
	    exprText.append((conj == null) ? " " : (("And".equals(conj)) ? " &&" : " ||")  );
	    exprText.append(" ").append(cond.getLparenthesis()).append("$F{").append(fe.getLabelAlias()).append("}");
	    if(RepConstantsCommon.DATE_TYPE_JASPER.equals(fe.getFIELD_DATA_TYPE()) || RepConstantsCommon.TIMESTAMP_TYPE_JASPER.equals(fe.getFIELD_DATA_TYPE()))
	    {
		oper = cond.getOperatorName();
		    Date dt;
		    try
		    {
			dt = DateUtil.parseDate(cond.getValue(), "dd/MM/yyyy");
			  if("=".equals(oper))
			  {
			      oper = "==";
			  }
			  if("<>".equals(oper))
			  {
			      oper = "!=";
			  }
			  exprText.append(".compareTo(new Date(").append(dt.getYear()).append( "," ).append(dt.getMonth()).append(",").append(dt.getDate()).append( "))").append(oper ).append("0");
		    }
		    catch(BaseException e)
		    {
			log.error(e,"__________ error parsing the date value in filter section ");
		    }


	    }
		else if(RepConstantsCommon.NUMBER_TYPE_JASPER.equals(fe.getFIELD_DATA_TYPE()))
		{
		    oper = cond.getOperatorName();
		    if(cond.getOperatorName().equals("<>"))
		    {
			oper = "!=";

		    }
		    else if(cond.getOperatorName().equals("="))
		    {
			oper = "==";
		    }

		    exprText.append(".compareTo( new BigDecimal(\"").append(cond.getValue()).append("\"))")
			    .append(oper).append("0");
		}
		else
		{
		    if(cond.getOperatorName().equals("<>"))
		    {
			exprText.append("!=");
		    }
		    else if(cond.getOperatorName().equals("="))
		    {
			exprText.append(".equals(\"");
		    }
		    else if("contains".equals(cond.getOperatorName()))
		    {
			exprText.append(".").append("indexOf").append("(\"");
		    }
		    else if("startsWith".equals(cond.getOperatorName())|| "endsWith".equals(cond.getOperatorName()))
		    {
			exprText.append(".").append(cond.getOperatorName()).append("(\"");
		    }
		    else
		    {
			exprText.append(cond.getOperatorName());
		    }

		    exprText.append(cond.getValue());
		    if("contains".equals(cond.getOperatorName()))
		    {
			exprText.append("\")!=-1");
		    }
		    else if("startsWith".equals(cond.getOperatorName()) || "endsWith".equals(cond.getOperatorName()))
		    {
			exprText.append("\")");
		    }
		    else if("=".equals(cond.getOperatorName())
			    && RepConstantsCommon.VARCHAR_TYPE_JASPER.equals(fe.getFIELD_DATA_TYPE()))
		    {
			exprText.append("\")");
		    }
		}
	    exprText.append(cond.getRparenthesis());
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in generating expression text: "+e.getMessage(),e);
	}
	return exprText.toString();
	
    }

    public static void addFieldProperty(JasperDesign jasperDesign, HashMap<String, String> fePropsMap)throws BaseException
    {
	try
	{
	addFieldProperty( jasperDesign,  fePropsMap, null);
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding a property to a field: "+e.getMessage(),e);
	}
    }
    public static void addFieldProperty(JasperDesign jasperDesign, HashMap<String, String> fePropsMap,HashMap<String ,REP_SNAPSHOT_MODIFY_COLUMNCO> snpEditFeMap)throws BaseException
    {
	try
	{
//	JRSection detailsBand = jasperDesign.getDetailSection();
	JRElement[] elements = null;
	JRTextField textField;
	JRElement element;
	String txt;
	JRDesignComponentElement compElem;
	Component comp;
	StandardTable tbl;
	List columns;
	StandardColumn col;
	// GroupCell grpCell;
	DesignCell cell;
	JRElement[] cellElem;
	JRElement txtElem;
	JRDesignImage img;
	JRDesignChart chart;
//	if(detailsBand != null)
//	{
	    JRBand[] details = jasperDesign.getAllBands();
	    for(int i = 0; i < details.length; i++)
	    {
		elements = details[i].getElements();
		for(int j = 0; j < elements.length; j++)
		{
		    element = elements[j];
		    if(element instanceof JRTextField)
		    {
			textField = (JRTextField) element;
			if(textField.getExpression()!=null)
			{
			    txt = textField.getExpression().getText();
				putProperties(fePropsMap, txt, textField,snpEditFeMap);
			}
			

		    }
		    else if(element instanceof JRDesignImage)
		    {
			img = (JRDesignImage) element;
			txt = img.getExpression().getText();
			putImgProperties(txt, img);
		    }
		    else if(element instanceof JRDesignChart)
		    {
			chart = (JRDesignChart) element;
			chart.getPropertiesMap().setProperty("my.dataFe", "1");
		    }
		    else if(element instanceof JRDesignComponentElement)
		    {
			compElem = (JRDesignComponentElement) element;
			comp = compElem.getComponent();
			if(comp instanceof StandardTable)
			{
			    tbl = (StandardTable) comp;
			    columns = tbl.getColumns();
			    for(int k = 0; k < columns.size(); k++)
			    {
				col = (StandardColumn) columns.get(k);
				cell = (DesignCell) col.getDetailCell();
				cellElem = cell.getElements();
				for(int l = 0; l < cellElem.length; l++)
				{
				    txtElem = cellElem[l];
				    if(txtElem instanceof JRDesignTextField)
				    {
					textField = (JRDesignTextField) txtElem;
					txt = textField.getExpression().getText();
					putProperties(fePropsMap, txt, textField,snpEditFeMap);
				    }
				    else if(element instanceof JRDesignImage)
				    {
					img = (JRDesignImage) element;
					txt = img.getExpression().getText();
					putImgProperties(txt, img);
				    }
				}
			    }
			}

		    }
		}

	    }
//	}
	    /*//do not remove ,will be used later
	     * 	if(jasperDesign.getSummary()!=null)
		{
			JRBand summaryBand = jasperDesign.getSummary();
			elements=summaryBand.getElements();
			JRDesignCrosstab crossT;
			List<JRCrosstabColumnGroup> colGrpLst;
			JRCrosstabColumnGroup colGrp;
			JRCellContents cellCont;
			JRElement[] cellElems = null;
			JRElement ctCellElem;
			JRDesignTextField ctCellTxt;
			for(int j = 0; j < elements.length; j++)
			{
			    element = elements[j];
			    if(element instanceof JRDesignCrosstab)
			    {
				crossT=(JRDesignCrosstab)element;
				colGrpLst=crossT.getColumnGroupsList();
				for(int i=0;i<colGrpLst.size();i++)
				{
				    colGrp=colGrpLst.get(i);
				    cellCont=colGrp.getHeader();
				    cellElems=cellCont.getElements();
//				    for(int k=0;k<cellElems.length;k++)
//				    {
//					ctCellElem=cellElems[k];
//					if(ctCellElem instanceof JRDesignTextField)
//					{
//					    ctCellTxt=(JRDesignTextField)ctCellElem;
//					    if("$V{COL_DESC}".equals(ctCellTxt.getExpression().getText()))
//					    {
//						ctCellTxt.getPropertiesMap().setProperty("my.editFe", "1"); 
//						ctCellTxt.setForecolor(Color.green);
//					    }
//					    if("$V{group1}".equals(ctCellTxt.getExpression().getText()))
//					    {
//						ctCellTxt.setForecolor(Color.blue);
//					    }
//					    if("$V{COL_TYPE}".equals(ctCellTxt.getExpression().getText()))
//					    {
//						ctCellTxt.setForecolor(Color.pink);
//					    }
//					    if("$V{LINE_NBR2}".equals(ctCellTxt.getExpression().getText()))
//					    {
//						ctCellTxt.setForecolor(Color.orange);
//					    }
//					}
//				    }
				    
				    List<JRCrosstabCell> cellLst = crossT.getCellsList();
				    JRCrosstabCell zCell;
				    for(int l = 0; l < cellLst.size(); l++)
				    {
					zCell = cellLst.get(l);
					cellCont = zCell.getContents();
					cellElems = cellCont.getElements();
					for(int k = 0; k < cellElems.length; k++)
					{
					    ctCellElem = cellElems[k];
					    if(ctCellElem instanceof JRDesignTextField)
					    {
						ctCellTxt = (JRDesignTextField) ctCellElem;
//						 if("$V{BASE_CURRENCYMeasure}".equals(ctCellTxt.getExpression().getText()))
						     if("$V{COMPUTE_0005Measure}".equals(ctCellTxt.getExpression().getText()))
						    {
							ctCellTxt.getPropertiesMap().setProperty("my.editFe", "1"); 
							ctCellTxt.setForecolor(Color.green);
						    }
					    }
					}
				    }
				}
				
			    }
			} 
		}*/
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding filter fields: "+e.getMessage(),e);
	}
    }

    public static void putProperties(HashMap<String, String> fePropsMap, String txt, JRTextField textField,
	    HashMap<String, REP_SNAPSHOT_MODIFY_COLUMNCO> snpEditFeMap)throws BaseException
    {
	try
	{
	if(snpEditFeMap == null)
	{
	    String fromFe = fePropsMap.get("fromFe") == null ? "" : "$F{" + fePropsMap.get("fromFe") + "}";
	    String toFe = fePropsMap.get("toFe") == null ? "" : "$F{" + fePropsMap.get("toFe") + "}";
	    String toFeComp = fePropsMap.get("toFeComp") == null ? "" : "$V{" + fePropsMap.get("toFeComp") + "}";
	    String ccFeComp = fePropsMap.get("ccFeComp") == null ? "" : "$V{" + fePropsMap.get("ccFeComp") + "}";
	    String bccFeComp = fePropsMap.get("bccFeComp") == null ? "" : "$V{" + fePropsMap.get("bccFeComp") + "}";
	    String toCif = fePropsMap.get("toCif") == null ? "" : "$F{" + fePropsMap.get("toCif") + "}";
	    String ccFe = fePropsMap.get("ccFe") == null ? "" : "$F{" + fePropsMap.get("ccFe") + "}";
	    String bccFe = fePropsMap.get("bccFe") == null ? "" : "$F{" + fePropsMap.get("bccFe") + "}";
	    String attachFileFe = fePropsMap.get("attachFileFe") == null ? "" : "$F{" + fePropsMap.get("attachFileFe")
		    + "}";
	    String pwdFe = fePropsMap.get("pwdFe")== null ? "" : "$F{" + fePropsMap.get("pwdFe") + "}";
	    if(toFeComp.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.toFeComp", "1");
	    }
	    if(ccFeComp.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.ccFeComp", "1");
	    }
	    if(bccFeComp.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.bccFeComp", "1");
	    }
	    if(fromFe.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.fromFe", "1");
	    }
	    if(toFe.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.toFe", "1");
	    }
	    if(toCif.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.toCif", "1");
	    }
	    if(ccFe.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.ccFe", "1");
	    }
	    if(bccFe.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.bccFe", "1");
	    }
	    if(pwdFe.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.pwdFe", "1");
	    }
	    if(attachFileFe.equals(txt))
	    {
		textField.getPropertiesMap().setProperty("my.attachFileFe", "1");
	    }
	    // for each element of type field add a property that will be used
	    // to
	    // export report in sql format
	    if(txt.indexOf("$F{") == 0)
	    {
		textField.getPropertiesMap().setProperty("my.dataFe", "1");
	    }
	    
	    //set the name of the field as property in order to use it later
	    if(fePropsMap.get(txt) !=null)
	    {
		textField.getPropertiesMap().setProperty("my.feName", fePropsMap.get(txt));
	    }
	    
	}
	else
	{
	    REP_SNAPSHOT_MODIFY_COLUMNCO editFeCO = snpEditFeMap.get(txt);
	    if(editFeCO != null)
	    {
		textField.getPropertiesMap().setProperty("my.editFe", "1");
	    }
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adjusting properties: "+e.getMessage(),e);
	}
    }

    public static void putImgProperties(String txt, JRDesignImage img)throws BaseException
    {
	try
	{
	if(txt.indexOf("$F{") == 0)
	{
	    img.getPropertiesMap().setProperty("my.dataFe", "1");
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in putting images properties: "+e.getMessage(),e);
	}
    }

    public static void addGroupFields(JasperDesign jasperDesign, IRP_AD_HOC_REPORTCO reportSession)throws BaseException
    {
	try
	{
	addGroupFields(jasperDesign, reportSession.getPrevGrpMap(), false, null);
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding/groupping fields: "+e.getMessage(),e);
	}
    }

    public static void addGroupFields(JasperDesign jasperDesign, LinkedHashMap<Long, IRP_FIELDSCO> prevGrpMap,
	    boolean isDyn, HashMap<String, String> fePropsMap)throws BaseException
    {
	try
	{
	    // add property to a field in order to catch its value in the page
	    if(isDyn)
	    {
		addFieldProperty(jasperDesign, fePropsMap);
	    }
	    if(prevGrpMap != null && !prevGrpMap.isEmpty())
	    {
		// List<JRDesignSortField> sortList;
		List<JRSortField> sortList;
		JRDesignDataset ds;
		Boolean isFreeForm = false;
		if(jasperDesign.getDatasetsList().size() == 0)// freeForm
		{
		    ds = jasperDesign.getMainDesignDataset();
		    isFreeForm = true;
		}
		else
		{
		    ds = (JRDesignDataset) jasperDesign.getDatasetsList().get(0);
		}

		if(ds == null)
		{
		    sortList = jasperDesign.getSortFieldsList();
		}
		else
		{
		    sortList = ds.getSortFieldsList();
		}

		if(isFreeForm )
		{
		    String dynFeTxtVal = "\"" + RepConstantsCommon.MAIL_SPLIT_VAR + "\"";
		    StringBuffer  dynFeTxtValSB=new StringBuffer(dynFeTxtVal);
		    Iterator<IRP_FIELDSCO> it = prevGrpMap.values().iterator();
		    IRP_FIELDSCO feCO;
		    JRDesignGroup grp;
		    JRDesignExpression expr;
		    // String feType;
		    JRDesignSortField sortField1;
		    JRDesignTextField txtFe;
		    JRDesignExpression exprFeGrp;
		    JRDesignBand band;
		    int i = 0;
		    while(it.hasNext())
		    {
			feCO = it.next();
			// feType=feCO.getFIELD_DATA_TYPE();
			// create group
			grp = new JRDesignGroup();
			grp.setName(feCO.getGroupName());

			// create grp expr
			expr = new JRDesignExpression();
			// if(feType.equals("java.lang.String"))
			// {
			// expr.setValueClass(java.lang.String.class);
			// }
			// else
			// {
			// expr.setValueClass(java.math.BigDecimal.class);
			// }
			expr.setText("$F{" + feCO.getLabelAlias() + "}");
			grp.setExpression(expr);

			// add groupHeader
			txtFe = new JRDesignTextField();

			txtFe.setFontName("Arial");
			txtFe.setPdfFontName("arial.ttf");
			txtFe.setPdfEncoding("Identity-H");
			txtFe.setPdfEmbedded(true);
			if(feCO.getFIELD_DATA_TYPE() != null
				&& ("java.util.Date".equals(feCO.getFIELD_DATA_TYPE()) || "java.sql.Timestamp"
					.equals(feCO.getFIELD_DATA_TYPE())))
			{
			    txtFe.setPattern("dd/MM/yyyy");// set the pattern as
			    // set in the jrxml
			    // for the fields of
			    // type date
			}

			txtFe.setBlankWhenNull(false);

			band = new JRDesignBand();

			if(isDyn)
			// if called from the scheduler in order to split report
		        // data into manny files
			{
			    band.setHeight(5);
			    grp.setStartNewPage(true);
			    if(i == prevGrpMap.size() - 1)
			    {
				// display the groupped fields as 1 output
				dynFeTxtValSB.append("+$F{").append(feCO.getLabelAlias()).append("}");

				txtFe.setMode(ModeEnum.OPAQUE);
				txtFe.setBackcolor(Color.WHITE);// MAGENTA
				// minimize the size of the grp text field
//				if(isDyn)
//				{
				    txtFe.setWidth(100);
				    txtFe.setHeight(4);
				    txtFe.setFontSize(12);
				    txtFe.setForecolor(Color.WHITE);
//				}
//				else
//				{
//				    txtFe.setWidth(116);
//				    txtFe.setHeight(14);
//				    txtFe.setFontSize(12);
//				    txtFe.setForecolor(Color.BLACK);
//				}
				txtFe.setX(0);
				txtFe.setY(0);
				txtFe.setBold(false);
				exprFeGrp = new JRDesignExpression();
				exprFeGrp.setText(dynFeTxtValSB.toString());
				txtFe.setExpression(exprFeGrp);
				band.addElement(txtFe);
			    }
			    else
			    {
				dynFeTxtValSB.append("+$F{").append(feCO.getLabelAlias()).append("}");
			    }
			}
			else
			{
			    band.setHeight(38);

			    exprFeGrp = new JRDesignExpression();
			    // if(feCO.getFIELD_DATA_TYPE().equals("java.lang.String"))
			    // {
			    // exprFeGrp.setValueClass(java.lang.String.class);
			    // }
			    // else
			    // {
			    // exprFeGrp.setValueClass(java.math.BigDecimal.class);
			    // }
			    exprFeGrp.setText("$F{" + feCO.getLabelAlias() + "}");
			    txtFe.setExpression(exprFeGrp);
			    txtFe.setWidth(116);
			    txtFe.setHeight(14);
			    txtFe.setX(i * 50);
			    txtFe.setY(1);
			    txtFe.setStretchWithOverflow(true);
			    txtFe.setBold(true);

			    band.addElement(txtFe);
			}

			// grp.setGroupHeader(band);
			((JRDesignSection) grp.getGroupHeaderSection()).addBand(band);

			// add sortField
			sortField1 = new JRDesignSortField();
			sortField1.setName(feCO.getLabelAlias());
			sortField1.setOrder(SortOrderEnum.ASCENDING);
			sortField1.setType(SortFieldTypeEnum.FIELD);
			sortList.add(sortField1);

			if(ds == null)
			{
			    jasperDesign.addGroup(grp);
			}
			else
			{
			    ds.addGroup(grp);
			}
			i++;
		    }

		}
		else
		{
		    Iterator<IRP_FIELDSCO> it = prevGrpMap.values().iterator();
		    IRP_FIELDSCO feCO;
		    JRDesignGroup grp;
		    JRDesignExpression expr;
		    // String feType;
		    JRDesignSortField sortField1;
		    int l = 0;
		    while(it.hasNext())
		    {
			l++;
			feCO = it.next();
			// feType=feCO.getFIELD_DATA_TYPE();
			// create group
			grp = new JRDesignGroup();
			grp.setName(feCO.getGroupName());

			// create grp expr
			expr = new JRDesignExpression();
			// if(feType.equals("java.lang.String"))
			// {
			// expr.setValueClass(java.lang.String.class);
			// }
			// else
			// {
			// expr.setValueClass(java.math.BigDecimal.class);
			// }
			expr.setText("$F{" + feCO.getLabelAlias() + "}");
			grp.setExpression(expr);

			// if it is the last grp add a pageBreak in order to
			// split the pages in scheduler section
			if(isDyn && l == prevGrpMap.size())
			{
			    grp.setStartNewPage(true);
			}
			// add sortField
			sortField1 = new JRDesignSortField();
			sortField1.setName(feCO.getLabelAlias());
			sortField1.setOrder(SortOrderEnum.ASCENDING);
			sortField1.setType(SortFieldTypeEnum.FIELD);
			sortList.add(sortField1);

			if(ds == null)
			{
			    jasperDesign.addGroup(grp);
			}
			else
			{
			    ds.addGroup(grp);
			}
		    }

		    // add groupHeader
		    JRDesignComponentElement compElem;
		    JRDesignSection desSection = (JRDesignSection) jasperDesign.getDetailSection();
		    JRBand[] band = desSection.getBands();
		    JRElement[] elements;
		    JRElement elem;
		    Component comp;
		    StandardTable tbl;
		    List columns;
		    StandardColumn col;
		    DesignCell cell;
		    JRDesignTextField txtFe;
		    ArrayList<IRP_FIELDSCO> grpLst = new ArrayList<IRP_FIELDSCO>();
		    grpLst.addAll(prevGrpMap.values());
		    IRP_FIELDSCO grpFe;
		    JRDesignExpression exprFeGrp;
		    if(band.length > 0)
		    {
			elements = band[0].getElements();
			if(elements != null)
			{
			    String dynFeTxtVal = "\"" + RepConstantsCommon.MAIL_SPLIT_VAR + "\"";
			    StringBuffer  dynFeTxtValSB=new StringBuffer(dynFeTxtVal);
			    for(int i = 0; i < elements.length; i++)
			    {
				elem = elements[i];
				if(elem instanceof JRDesignComponentElement)
				{
				    compElem = (JRDesignComponentElement) elem;
				    comp = compElem.getComponent();
				    if(comp instanceof StandardTable)
				    {
					tbl = (StandardTable) comp;
					columns = tbl.getColumns();
					for(int k = 0; k < columns.size(); k++)
					{
					    col = (StandardColumn) columns.get(k);
					    cell = new DesignCell();
					    cell.setHeight(20);

					    if(isDyn)
					    {
						if(grpLst.size() >= k + 1)
						{
						    grpFe = grpLst.get(k);
						    dynFeTxtValSB.append("+$F{").append((grpLst.get(k)).getLabelAlias()).append("}");
						    if(k == prevGrpMap.size() - 1)
						    {
							txtFe = new JRDesignTextField();
							txtFe.setBlankWhenNull(true);
							txtFe.setStretchWithOverflow(true);

							exprFeGrp = new JRDesignExpression();
							exprFeGrp.setText(dynFeTxtValSB.toString());
							txtFe.setExpression(exprFeGrp);

							txtFe.setX(1);
							txtFe.setY(1);
							// txtFe.setWidth(116);
							// txtFe.setHeight(14);
							txtFe.setBackcolor(Color.WHITE);// MAGENTA
//							if(isDyn)
//							{
							    txtFe.setWidth(100);
							    txtFe.setHeight(4);
							    txtFe.setFontSize(12);
							    txtFe.setForecolor(Color.WHITE);
//							}
//							else
//							{
//							    txtFe.setWidth(116);
//							    txtFe.setHeight(14);
//							    txtFe.setFontSize(12);
//							    txtFe.setForecolor(Color.BLACK);
//							}
							txtFe.setFontName("Arial");
							txtFe.setPdfFontName("arial.ttf");
							txtFe.setPdfEncoding("Identity-H");
							txtFe.setPdfEmbedded(true);
							txtFe.setMode(ModeEnum.OPAQUE);
							txtFe.setBold(true);
							if(grpFe.getFIELD_DATA_TYPE() != null
								&& ("java.util.Date".equals(grpFe.getFIELD_DATA_TYPE()) || "java.sql.Timestamp"
									.equals(grpFe.getFIELD_DATA_TYPE())))
							{
							    // set the pattern
							    // as set in the
							    // jrxml for the
							    // fields of type
							    // date
							    txtFe.setPattern("dd/MM/yyyy");
							}
							txtFe.setBlankWhenNull(false);
							// txtFe.setForecolor(Color.DARK_GRAY);
							cell.addElement(txtFe);
						    }
						    col.setGroupHeader(grpFe.getGroupName(), cell);
						}
						else
						{
						    /************** naaab ***/
						    col.setGroupHeader((grpLst.get(0)).getGroupName(), cell);
						}
					    }
					    else
					    {
						if(grpLst.size() >= k + 1)
						{
						    grpFe = grpLst.get(k);
						    txtFe = new JRDesignTextField();
						    txtFe.setBlankWhenNull(true);
						    txtFe.setStretchWithOverflow(true);

						    exprFeGrp = new JRDesignExpression();
						    // if(grpFe.getFIELD_DATA_TYPE().equals("java.lang.String"))
						    // {
						    // exprFeGrp.setValueClass(java.lang.String.class);
						    // }
						    // else
						    // {
						    // exprFeGrp.setValueClass(java.math.BigDecimal.class);
						    // }
						    exprFeGrp.setText("$F{" + grpFe.getLabelAlias() + "}");
						    txtFe.setExpression(exprFeGrp);

						    txtFe.setX(1);
						    txtFe.setY(1);
						    // txtFe.setWidth(116);
						    // txtFe.setHeight(14);
						    txtFe.setBackcolor(Color.WHITE);// MAGENTA
						    if(isDyn)
						    {
							txtFe.setWidth(100);
							txtFe.setHeight(4);
							txtFe.setFontSize(12);
							txtFe.setForecolor(Color.WHITE);
						    }
						    else
						    {
							txtFe.setWidth(col.getWidth().intValue()-1);
							txtFe.setHeight(14);
							txtFe.setFontSize(12);
							txtFe.setForecolor(Color.BLACK);
						    }
						    txtFe.setFontName("Arial");
						    txtFe.setPdfFontName("arial.ttf");
						    txtFe.setPdfEncoding("Identity-H");
						    txtFe.setPdfEmbedded(true);
						    txtFe.setMode(ModeEnum.OPAQUE);
						    txtFe.setBold(true);
						    if(grpFe.getFIELD_DATA_TYPE() != null
							    && ("java.util.Date".equals(grpFe.getFIELD_DATA_TYPE()) || "java.sql.Timestamp"
								    .equals(grpFe.getFIELD_DATA_TYPE())))
						    {
							// set the pattern as
							// set in the jrxml for
							// the fields of type
							// date
							txtFe.setPattern("dd/MM/yyyy");
						    }
						    txtFe.setBlankWhenNull(false);
						    // txtFe.setForecolor(Color.DARK_GRAY);
						    cell.addElement(txtFe);
						    col.setGroupHeader(grpFe.getGroupName(), cell);
						}
						else
						{
						    col.setGroupHeader((grpLst.get(0)).getGroupName(), cell);
						}
					    }					

					}
				    }
				}
			    }
			}

		    }
		}
	    }

	}

	catch(Exception e)
	{
	    throw new BaseException("Error in adding/groupping fields: "+e.getMessage(),e);
	}
    }

    public static void addRepHyperLink(JasperDesign jasperDesign, String repUrl, IRP_AD_HOC_REPORTCO reportSession,
	    HashMap<String, String> hypParamsValMap, ReportParamsCO repParamsCO)throws BaseException
    {
	try
	{
	    JRDesignComponentElement compElem;
	    JRDesignSection desSection = (JRDesignSection) jasperDesign.getDetailSection();
	    JRBand[] band = desSection.getBands();
	    JRElement[] elements;
	    JRElement[] cellElem;
	    JRElement elem;
	    JRElement txtElem;
	    JRDesignExpression expr;
	    // JRDesignExpression toolTipExpr;
	    Component comp;
	    StandardTable tbl;
	    List columns;
	    StandardColumn col;
	    // GroupCell grpCell;
	    DesignCell cell;
	    JRDesignTextField txtFe;
	    // ArrayList<IRP_FIELDSCO> grpLst = new ArrayList<IRP_FIELDSCO>();
	    // IRP_FIELDSCO grpFe;

	    // send the session parameters in the url in order to fill the
	    // session object in case it is empty
	    String sessionParamsUrl = "s_bcn=" + repParamsCO.getBaseCurrencyName() + "&s_bn="
		    + repParamsCO.getBranchName() + "&s_ct=" + repParamsCO.getClientType() + "&s_can="
		    + repParamsCO.getCompanyArabName() + "&s_cn=" + repParamsCO.getCompanyName() + "&s_cAppn="
		    + repParamsCO.getCurrentAppName() + "&s_ecn=" + repParamsCO.getExposCurName() + "&s_l="
		    + repParamsCO.getLanguage() + "&s_bcdp=" + repParamsCO.getBaseCurrDecPoint() + "&s_bcc="
		    + repParamsCO.getBaseCurrencyCode() + "&s_bc=" + repParamsCO.getBranchCode() + "&s_cc="
		    + repParamsCO.getCompanyCode() + "&s_excn=" + repParamsCO.getExposCurName() + "&s_fy="
		    + repParamsCO.getFiscalYear() + "&s_rdr=" + repParamsCO.getRunningDateRET() + "&s_un="
		    + repParamsCO.getUserName();

	    String colName;
	    String paramsVal;
	    if(band.length > 0)
	    {
		elements = band[0].getElements();
		if(elements != null)
		{
		    for(int i = 0; i < elements.length; i++)
		    {
			elem = elements[i];
			if(elem instanceof JRDesignComponentElement)
			{
			    compElem = (JRDesignComponentElement) elem;
			    comp = compElem.getComponent();
			    if(comp instanceof StandardTable)
			    {
				tbl = (StandardTable) comp;
				columns = tbl.getColumns();
				for(int k = 0; k < columns.size(); k++)
				{
				    col = (StandardColumn) columns.get(k);
				    cell = (DesignCell) col.getDetailCell();
				    cellElem = cell.getElements();
				    for(int j = 0; j < cellElem.length; j++)
				    {
					txtElem = cellElem[j];
					if(txtElem instanceof JRDesignTextField)
					{
					    txtFe = (JRDesignTextField) txtElem;
					    colName = txtFe.getExpression().getText();
					    paramsVal = hypParamsValMap.get(colName);
					    if(paramsVal != null)
					    {
						txtFe.setHyperlinkType(HyperlinkTypeEnum.REFERENCE);
						txtFe.setHyperlinkTarget(HyperlinkTargetEnum.BLANK);
						expr = new JRDesignExpression();
						// the action is unsecure to
						// open the link from pdf,
						// word...

						String paramsUrl = sessionParamsUrl + "&" + "r_r="
							+ paramsVal.split("~r~")[0] + "&a=REP&r_i="
							+ paramsVal.split("~r~")[1];
						// encode the parameters
						StringBuffer encParamsUrl = new StringBuffer(SecurityUtils.encodeB64(paramsUrl));
						// do not encode the r_a_p since
						// the mapped parameters will
						// not be filled with
						// corresponding values
						encParamsUrl.append("&r_a_p=").append(paramsVal.split("~r~")[2]);
						expr.setText("\"" + repUrl
							+ "reportsHyp/jasperReport.action?zHypParams=" + encParamsUrl.toString()
							+ "\"");

						// expr.setText("\""+repUrl+"reportsHyp/jasperReport.action?r_r="+paramsVal.split("~r~")[0]+"&a=REP&r_a_p="+paramsVal.split("~r~")[2]+"&r_i="+paramsVal.split("~r~")[1]+"\"");
						expr.setValueClass(java.lang.String.class);
						expr.setValueClassName("java.lang.String");
						txtFe.setHyperlinkReferenceExpression(expr);

						// toolTipExpr=new
						// JRDesignExpression();
						// toolTipExpr.setValueClass(java.lang.String.class);
						// toolTipExpr.setValueClassName("java.lang.String");
						// toolTipExpr.setText("$F{ACC_BC_4}");
						// txtFe.setHyperlinkTooltipExpression(toolTipExpr);
					    }

					}
				    }

				}
			    }
			}
			else if(elem instanceof JRDesignTextField) // free form
			{
			    txtFe = (JRDesignTextField) elem;
			    colName = txtFe.getExpression().getText();
			    paramsVal = hypParamsValMap.get(colName);
			    if(paramsVal != null)
			    {
				txtFe.setHyperlinkType(HyperlinkTypeEnum.REFERENCE);
				txtFe.setHyperlinkTarget(HyperlinkTargetEnum.BLANK);
				expr = new JRDesignExpression();
				// the action is unsecure to open the link from
				// pdf, word...

				String paramsUrl = sessionParamsUrl + "&" + "r_r=" + paramsVal.split("~r~")[0]
					+ "&a=REP&r_i=" + paramsVal.split("~r~")[1];
				// encode the parameters
				StringBuffer encParamsUrl = new StringBuffer(SecurityUtils.encodeB64(paramsUrl));
				// do not encode the r_a_p since the mapped
				// parameters will not be filled with
				// corresponding values
				encParamsUrl.append("&r_a_p=").append(paramsVal.split("~r~")[2]);
				expr.setText("\"" + repUrl + "reportsHyp/jasperReport.action?zHypParams="
					+ encParamsUrl.toString() + "\"");

				// expr.setText("\""+repUrl+"reportsHyp/jasperReport.action?r_r="+paramsVal.split("~r~")[0]+"&a=REP&r_a_p="+paramsVal.split("~r~")[2]+"&r_i="+paramsVal.split("~r~")[1]+"\"");
				expr.setValueClass(java.lang.String.class);
				expr.setValueClassName("java.lang.String");
				txtFe.setHyperlinkReferenceExpression(expr);
			    }
			}
		    }
		}

	    }
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding hyperlinks: "+e.getMessage(),e);
	}
    }

    public static JasperDesign addVarToJRDesign(JasperDesign jasperDesign, String sessionId)throws BaseException
    {

	try
	{
	JRDesignExpression filterExpr = new JRDesignExpression();
	filterExpr.setText(sessionId);
	filterExpr.setValueClass(java.lang.Boolean.class);
	JRDesignVariable v = new JRDesignVariable();
	v.setName("userSession");
	v.setValueClassName("");
	v.setValueClass(java.lang.String.class);
	v.setExpression(filterExpr);
	try
	{
	    jasperDesign.addVariable(v);
	}
	catch(JRException e)
	{
	    e.printStackTrace();
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding vars to report design: "+e.getMessage(),e);
	}
	return jasperDesign;
    }

    public static void addRepGlobalParam(JasperDesign jasperDesign) throws JRException, BaseException
    {
	try
	{
	Map params = jasperDesign.getParametersMap();
	if(!params.containsKey("repParamsCO"))
	{
	    JRDesignParameter parameter = new JRDesignParameter();
	    parameter.setName("repParamsCO");
	    parameter.setValueClass(ReportParamsCO.class);
	    parameter.setForPrompting(false);
	    jasperDesign.addParameter(parameter);
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding report global parameter: "+e.getMessage(),e);
	}
    }

    public static void addWhenNoData(JasperDesign jasperDesign,String val,HashMap<String,String>transMap) throws JRException, BaseException
    {
	try
	{
	    if(jasperDesign.getDatasetsList().size() == 0)// freeForm
	    {
//		    val=RepConstantsCommon.WHEN_NO_DATA_NO_DATA_SECTION;
		    if(RepConstantsCommon.WHEN_NO_DATA_NO_DATA_SECTION.equals(val))
		    {
			    String whenNoDataText=transMap.get("reporting.noDataRetrieved");
			    JRDesignBand band=new JRDesignBand();
			    jasperDesign.setNoData(band);
			    JRDesignTextField text=new JRDesignTextField();
			    JRDesignExpression expression=new JRDesignExpression();
			    expression.setText("\"" + whenNoDataText + "\"");
			    text.setExpression(expression);
			    text.setWidth(300);
			    text.setBold(true);
			    text.setFontSize(14);
			    text.setHeight(50);
			    text.setY(70);
			    text.setX(200);
			    band.addElement(text);
			    band.setHeight(120);
				    
			    jasperDesign.setWhenNoDataType(WhenNoDataTypeEnum.NO_DATA_SECTION);
		    }
		    else  if(RepConstantsCommon.WHEN_NO_DATA_ALL_SECTION_NO_DETAIL.equals(val))
		    {
			 jasperDesign.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
		    }
		    else  if(RepConstantsCommon.WHEN_NO_DATA_BLANK.equals(val))
		    {
			jasperDesign.setWhenNoDataType(WhenNoDataTypeEnum.BLANK_PAGE);
		    }
		    else  if(RepConstantsCommon.WHEN_NO_DATA_NO_PAGES.equals(val))
		    {
			jasperDesign.setWhenNoDataType(WhenNoDataTypeEnum.NO_PAGES);
		    }
	    }
	    else
	    {
		showTblColHeaderWhenNoData(jasperDesign,val);
	    }

	    
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in addWhenNoData  :  "+e.getMessage(),e);
	}
    }
    public static void removeHeaderAndFooter(JasperDesign jasperDesign) throws JRException, BaseException
    {
	try
	{
	JRDesignBand emptyHeader = new JRDesignBand();
	emptyHeader.setHeight(0);
	jasperDesign.setPageHeader(emptyHeader);

	JRDesignBand emptyFooter = new JRDesignBand();
	emptyFooter.setHeight(0);
	jasperDesign.setPageFooter(emptyFooter);
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in removing report's header and footer: "+e.getMessage(),e);
	}
    }

    public static void showTblColHeaderWhenNoData(JasperDesign jasperDesign,String whenNoDataType) throws JRException, BaseException
    {
	try
	{
	JRElement[] elements;
	JRDesignSection desSection = (JRDesignSection) jasperDesign.getDetailSection();
	JRBand[] band = desSection.getBands();
	if(band.length > 0)
	{
	    elements = band[0].getElements();
	    if(elements != null)
	    {
		JRElement elem;
		JRDesignComponentElement compElem;
		Component comp;
		StandardTable tbl;
		for(int i = 0; i < elements.length; i++)
		{
		    elem = elements[i];
		    if(elem instanceof JRDesignComponentElement)
		    {
			compElem = (JRDesignComponentElement) elem;
			comp = compElem.getComponent();
			if(comp instanceof StandardTable)
			{
			    tbl = (StandardTable) comp;
			    if(RepConstantsCommon.WHEN_NO_DATA_DEFAULT.equals(whenNoDataType))
			    {
				tbl.setWhenNoDataType(WhenNoDataTypeTableEnum.ALL_SECTIONS_NO_DETAIL);
			    }
			    else if(RepConstantsCommon.WHEN_NO_DATA_BLANK.equals(whenNoDataType))
			    {
				tbl.setWhenNoDataType(WhenNoDataTypeTableEnum.BLANK);
				jasperDesign.setWhenNoDataType(WhenNoDataTypeEnum.BLANK_PAGE);
			    }
			    else if(RepConstantsCommon.WHEN_NO_DATA_ALL_SECTION_NO_DETAIL.equals(whenNoDataType))
			    {
				tbl.setWhenNoDataType(WhenNoDataTypeTableEnum.ALL_SECTIONS_NO_DETAIL);
				jasperDesign.setWhenNoDataType(WhenNoDataTypeEnum.ALL_SECTIONS_NO_DETAIL);
			    }
			}
		    }
		}
	    }
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in showing column header when no data: "+e.getMessage(),e);
	}
    }

    /**
     * method used to create the jasper design based on the passed VO
     * 
     * @param glsheaderVO
     * @return
     */
    public static JasperDesign previewTemplateHeader(GLSHEADERVO glsheaderVO)throws BaseException
    {
	try
	{
	    // create the design
	    JasperDesign design = new JasperDesign();
	    design.setProperty("ireport.zoom", "1.0");
	    design.setProperty("ireport.x", "0");
	    design.setProperty("ireport.y", "0");
	    design.setColumnWidth(555);
	    design.setName("report1");
	    design.setPageWidth(595);
	    design.setPageHeight(600);
	    design.setLeftMargin(20);
	    design.setRightMargin(20);
	    design.setTopMargin(5);
	    design.setBottomMargin(20);
	    JRDesignQuery query = new JRDesignQuery();
	    query.setText("SELECT 1 FROM DUAL");
	    design.setQuery(query);
	    JRDesignBand background = new JRDesignBand();
	    background.setSplitType(SplitTypeEnum.STRETCH);
	    design.setBackground(background);
	    JRDesignBand pageHeader = new JRDesignBand();
	    JRDesignBand band = new JRDesignBand();
	    pageHeader.setHeight(400);
	    pageHeader.addElementGroup(band);

	    JRDesignStaticText left1 = new JRDesignStaticText();
	    left1.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    left1.setX(0);
	    left1.setY(20);
	    left1.setWidth(100);
	    left1.setHeight(30);
	    left1.setText(glsheaderVO.getLEFT1());
	    band.addElement(left1);

	    JRDesignStaticText left2 = new JRDesignStaticText();
	    left2.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    left2.setX(0);
	    left2.setY(80);
	    left2.setWidth(100);
	    left2.setHeight(30);
	    left2.setText(glsheaderVO.getLEFT2());
	    band.addElement(left2);

	    JRDesignStaticText left3 = new JRDesignStaticText();
	    left3.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    left3.setX(0);
	    left3.setY(140);
	    left3.setWidth(100);
	    left3.setHeight(30);
	    left3.setText(glsheaderVO.getLEFT3());
	    band.addElement(left3);

	    JRDesignStaticText left4 = new JRDesignStaticText();
	    left4.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    left4.setX(0);
	    left4.setY(200);
	    left4.setWidth(100);
	    left4.setHeight(30);
	    left4.setText(glsheaderVO.getLEFT4());
	    band.addElement(left4);

	    JRDesignStaticText center1 = new JRDesignStaticText();
	    center1.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    center1.setX(182);
	    center1.setY(20);
	    center1.setWidth(215);
	    center1.setHeight(30);
	    center1.setText(glsheaderVO.getCENTER1());
	    band.addElement(center1);

	    JRDesignStaticText center2 = new JRDesignStaticText();
	    center2.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    center2.setX(182);
	    center2.setY(80);
	    center2.setWidth(215);
	    center2.setHeight(30);
	    center2.setText(glsheaderVO.getCENTER2());
	    band.addElement(center2);

	    JRDesignStaticText center3 = new JRDesignStaticText();
	    center3.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    center3.setX(182);
	    center3.setY(140);
	    center3.setWidth(215);
	    center3.setHeight(30);
	    center3.setText(glsheaderVO.getCENTER3());
	    band.addElement(center3);

	    JRDesignStaticText right1 = new JRDesignStaticText();
	    right1.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    right1.setX(455);
	    right1.setY(20);
	    right1.setWidth(100);
	    right1.setHeight(30);
	    right1.setText(glsheaderVO.getRIGHT1());
	    band.addElement(right1);

	    JRDesignStaticText right2 = new JRDesignStaticText();
	    right2.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    right2.setX(455);
	    right2.setY(80);
	    right2.setWidth(100);
	    right2.setHeight(30);
	    right2.setText(glsheaderVO.getRIGHT2());
	    band.addElement(right2);

	    JRDesignStaticText right3 = new JRDesignStaticText();
	    right3.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    right3.setX(455);
	    right3.setY(140);
	    right3.setWidth(100);
	    right3.setHeight(30);
	    right3.setText(glsheaderVO.getRIGHT3());
	    band.addElement(right3);

	    JRDesignStaticText right4 = new JRDesignStaticText();
	    right4.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    right4.setX(455);
	    right4.setY(200);
	    right4.setWidth(100);
	    right4.setHeight(30);
	    right4.setText(glsheaderVO.getRIGHT4());
	    band.addElement(right4);
	    
	    JRDesignStaticText title = new JRDesignStaticText();
	    title.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    title.setX(182);
	    title.setY(260);
	    title.setWidth(215);
	    title.setHeight(30);
	    title.setText("");
	    band.addElement(title);
	    
	    JRDesignStaticText title1 = new JRDesignStaticText();
	    title1.setHorizontalTextAlign(HorizontalTextAlignEnum.LEFT);
	    title1.setX(182);
	    title1.setY(320);
	    title1.setWidth(215);
	    title1.setHeight(30);
	    title1.setText(glsheaderVO.getBRIEF_NAME_ENG());
	    band.addElement(title1);

	    design.setPageHeader(pageHeader);
	    return design;
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in previewing the template header: "+e.getMessage(),e);
	}
    }

    public static ImageCO readImageFromJrxml(Object child)throws BaseException
    {
	ImageCO imgCO = new ImageCO();
	try
	{
	JRDesignImage img;
	JRDesignExpression expr;
	img = (JRDesignImage) child;
	expr = (JRDesignExpression) img.getExpression();
	String text = expr.getText();
	String oldText = text.replace("\\", "/");
	String[] splitImagePath = oldText.split("/");
	expr.addVariableChunk(text);
	imgCO.setImgName(splitImagePath[splitImagePath.length - 1].substring(0,
		splitImagePath[splitImagePath.length - 1].length() - 1));
	if(imgCO.getImgName().endsWith("\""))// some images path contain a quotation(")
	{
	    imgCO.setImgName(imgCO.getImgName().substring(0, imgCO.getImgName().length() - 1));
	}
	
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in reading the image from JRXML file: "+e.getMessage(),e);
	}
	return imgCO;
    }

    public static JasperDesign updateImgPath(JasperDesign jasperDesign, ArrayList<ImageCO> imgLst, String repositoryPath)throws BaseException
    {

	try
	{
	String pathName = repositoryPath + "/updateJasperImgReport.jrxml";

	JRBand[] allBandList = jasperDesign.getAllBands();
	ImageCO imgCO;
	JRDesignImage img;
	JRDesignExpression expr;
	List childLstBand;
	Object childBand;

	JRDesignComponentElement compElem;
	Component comp;
	StandardTable tbl;
	List columns;
	StandardColumn col;
	DesignCell head;
	DesignCell foot;
	DesignCell det;
	JRElement[] headElems;
	JRElement[] footElems;
	JRElement[] detElems;

	for(int b = 0; b < allBandList.length; b++)
	{
	    childLstBand = allBandList[b].getChildren();
	    for(int c = 0; c < childLstBand.size(); c++)
	    {
		childBand = childLstBand.get(c);
		if(childBand instanceof JRDesignImage)
		{
		    if(!((JRDesignImage) childBand).getPropertiesMap().containsProperty("blobImg"))
		    {
			imgCO = readImageFromJrxml(childBand);
			img = (JRDesignImage) childBand;
			expr = (JRDesignExpression) img.getExpression();
			for(int g = 0; g < imgLst.size(); g++)
			{
			    ImageCO myCO = new ImageCO();
			    myCO = imgLst.get(g);
			    if(myCO.getImgName().equals(imgCO.getImgName()))
			    {

				expr.setText('"' + RepConstantsCommon.IMAGES_LOCATION + "/" + myCO.getMappedImgName()
					+ '"');

					//perform all modifications

					

				
				imgCO.setImgLocation(repositoryPath + "\\images\\");
			//	JasperCompileManager.compileReport(jasperDesign);
				try
				{
				    JRXmlWriter.writeReport(jasperDesign, pathName, CommonUtil.ENCODING);
				}
				catch(Exception e)
				{
				    log.error(e, e.getMessage());
				}
			    }
			}

		    }
		}
		else if(childBand instanceof JRDesignComponentElement)
		{

		    compElem = (JRDesignComponentElement) childBand;
		    comp = compElem.getComponent();
		    if(comp instanceof StandardTable)
		    {
			tbl = (StandardTable) comp;
			columns = tbl.getColumns();
			for(int k = 0; k < columns.size(); k++)
			{
			    col = (StandardColumn) columns.get(k);
			    head = (DesignCell) col.getColumnHeader();
			    foot = (DesignCell) col.getTableFooter();
			    det = (DesignCell) col.getDetailCell();

			    if(head != null)
			    {
				headElems = head.getElements();
				if(headElems != null)
				{
				    for(int i = 0; i < headElems.length; i++)
				    {
					if( headElems[i] instanceof JRDesignImage && !(headElems[i].getPropertiesMap().containsProperty("blobImg")) )
					{
						imgCO = readImageFromJrxml(headElems[i]);
						img = (JRDesignImage) headElems[i];
						expr = (JRDesignExpression) img.getExpression();
						for(int g = 0; g < imgLst.size(); g++)
						{
						    ImageCO myCO = new ImageCO();
						    myCO = imgLst.get(g);
						    if(myCO.getImgName().equals(imgCO.getImgName()))
						    {

							expr.setText('"' + RepConstantsCommon.IMAGES_LOCATION + "/"
								+ myCO.getMappedImgName() + '"');
							imgCO.setImgLocation(repositoryPath + "\\images\\");
							try
							{
							    //convert jasperDesign to jrxml
							    JRXmlWriter.writeReport(jasperDesign, pathName,
								    CommonUtil.ENCODING);
							}
							catch(Exception e)
							{
							    log.error(e, e.getMessage());
							}
						    }
						}
					}
				    }
				}
			    }

			    if(foot != null)
			    {
				footElems = foot.getElements();
				if(footElems != null)
				{
				    for(int i = 0; i < footElems.length; i++)
				    {
					if(footElems[i] instanceof JRDesignImage && !(footElems[i].getPropertiesMap().containsProperty("blobImg"))  )
					{
						imgCO = readImageFromJrxml(footElems[i]);
						img = (JRDesignImage) footElems[i];
						expr = (JRDesignExpression) img.getExpression();
						
						for(int g = 0; g < imgLst.size(); g++)
						{
						    ImageCO myCO = new ImageCO();
						    myCO = imgLst.get(g);
						    if(myCO.getImgName().equals(imgCO.getImgName()))
						    {

							expr.setText('"' + RepConstantsCommon.IMAGES_LOCATION + "/"
								+ myCO.getMappedImgName() + '"');
							imgCO.setImgLocation(repositoryPath + "\\images\\");
							try
							{
							    JRXmlWriter.writeReport(jasperDesign, pathName,
								    CommonUtil.ENCODING);
							}
							catch(Exception e)
							{
							    log.error(e, e.getMessage());
							}
						    }
						}    
					}
				    }
				}
			    }
			    detElems = det.getElements();
			    if(detElems != null)
			    {
				for(int i = 0; i < detElems.length; i++)
				{
				    if(detElems[i] instanceof JRDesignImage && !(detElems[i].getPropertiesMap().containsProperty("blobImg")) )
				    {
					    imgCO = readImageFromJrxml(detElems[i]);
					    img = (JRDesignImage) detElems[i];
					    expr = (JRDesignExpression) img.getExpression();
					    for(int g = 0; g < imgLst.size(); g++)
					    {
						ImageCO myCO = new ImageCO();
						myCO = imgLst.get(g);
						if(myCO.getImgName().equals(imgCO.getImgName()))
						{

						    expr.setText('"' + RepConstantsCommon.IMAGES_LOCATION + "/"
							    + myCO.getMappedImgName() + '"');
						    imgCO.setImgLocation(repositoryPath + "\\images\\");
						    try
						    {
							JRXmlWriter.writeReport(jasperDesign, pathName,
								CommonUtil.ENCODING);
						    }
						    catch(Exception e)
						    {
							log.error(e, e.getMessage());
						    }
						}
					    }
				    }
				}
			    }
			}
		    }

		}
	    }
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in updating the image path: "+e.getMessage(),e);
	}
	return jasperDesign;
    }

    public static JasperDesign modifyReportsColumnsSnapshot(IRP_AD_HOC_REPORTCO repCO,
	    HashMap<String, Object> hashLiveSearch, String repositoryPath, String type, BigDecimal repId,
	    BigDecimal crtColOrRelCol)throws BaseException
    {
	try
	{
	String fileName = repCO.getPROG_REF() + Calendar.getInstance().getTimeInMillis();
	String filePath = repositoryPath + "\\" + fileName + ".jrxml";
	try
	{
	    String xmlStr = new String(repCO.getJRXML_FILE(), CommonUtil.ENCODING);
	    FileUtil.writeFile(filePath, xmlStr);
	    JasperDesign jasperDesign = JRXmlLoader.load(filePath);

	    // loop the bands and replace the $F with a static value read from
	    // the property lblField
	    JRBand[] allBandList = jasperDesign.getAllBands();
	    if(allBandList != null)
	    {
		for(int i = 0; i < allBandList.length; i++)
		{
		    changeFieldToStatic(allBandList[i].getElements(), jasperDesign, hashLiveSearch, type, repId,
			    crtColOrRelCol);
		}
	    }
	    return jasperDesign;
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
	finally
	{
	    File file = new PathFileSecure(filePath);
	    boolean isDel = file.delete();
	    if(!isDel)
	    {
		log.debug("\n\n  >>>>>>>>>>>>  The following file has not been deleted :");
	    }
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in modifying report's columns snapshot: "+e.getMessage(),e);
	}
	return null;
    }

    // @SuppressWarnings("unchecked")
    public static void changeFieldToStatic(JRElement[] elements, JasperDesign jasperDesign,
	    HashMap<String, Object> hashLiveSearch,  String type, BigDecimal repId,
	    BigDecimal crtColOrRelCol)throws BaseException
    {
	try
	{
	JRTextField textField;
	JRElement element;
	JRElement elem;
	JRDesignComponentElement compElem;
	Component comp;
	StandardTable tbl;
	List columns;
	StandardColumn col;
	DesignCell cell;
	JRElement[] cellElem;
	for(int j = 0; j < elements.length; j++)
	{
	    element = elements[j];
	    if(element instanceof JRTextField) // free form
	    {
		textField = (JRDesignTextField) element;
		//if called from fillColTechNameMap() method
		if(type==null && repId==null  && crtColOrRelCol==null)
		{
		    fillRepColTechNameMap(textField,hashLiveSearch);
		}
		else
		{
		    fillFieldWithStatic(textField, type, hashLiveSearch, repId, crtColOrRelCol);
		}
	    }

	    else if(element instanceof JRDesignComponentElement) // tabular
	    {
		compElem = (JRDesignComponentElement) element;
		comp = compElem.getComponent();
		if(comp instanceof StandardTable)
		{
		    tbl = (StandardTable) comp;
		    columns = tbl.getColumns();
		    for(int k = 0; k < columns.size(); k++)
		    {
			col = (StandardColumn) columns.get(k);
			cell = (DesignCell) col.getDetailCell();
			cellElem = cell.getElements();
			for(int l = 0; l < cellElem.length; l++)
			{
			    elem = cellElem[l];

			    if(elem instanceof JRDesignTextField)
			    {
				textField = (JRDesignTextField) elem;
				//if called from fillColTechNameMap() method
				if(type==null && repId==null  && crtColOrRelCol==null)
				{
				    fillRepColTechNameMap(textField,hashLiveSearch);
				}
				else
				{
				    fillFieldWithStatic(textField, type, hashLiveSearch, repId, crtColOrRelCol);
				}
			    }
			}
		    }
		}

	    }

	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in changing fields to static: "+e.getMessage(),e);
	}
    }

    public static void fillRepColTechNameMap(JRTextField textField,HashMap<String, Object> hm)
    {
	try
	{
	    if(textField.getExpression() != null)
		{
		String  txtExp = textField.getExpression().getText();
		  String colTechName=txtExp.replace("$F{", "").replace("}", "");
		 if(textField.getPropertiesMap().containsProperty(RepConstantsCommon.SNAPSHOT_MODF_COL_LBL_FIELD)
			    || txtExp.indexOf("$F{") == 0)
		    {
		     String lblName = textField.getPropertiesMap().getProperty(RepConstantsCommon.SNAPSHOT_MODF_COL_LBL_FIELD);
		     hm.put(colTechName, lblName);
		    }
		}
	}
	catch(Exception e)
	{
	    log.error(e,"");
	}
    }
    
    /**
     * Method to fill report's columns names in hash
     * @param textField
     * @param type
     * @param hashLiveSearch
     * @param repId
     * @param crtColOrRelCol
     * @throws BaseException
     */
    public static void fillFieldWithStatic(JRTextField textField, String type, HashMap<String, Object> hashLiveSearch,
	    BigDecimal repId, BigDecimal crtColOrRelCol)throws BaseException
    {
	try
	{
	if(textField.getExpression() != null)
	{
	    String lblName;
	    String  txtExp = textField.getExpression().getText();
	    String colTechName=txtExp.replace("$F{", "").replace("}", "");
	    
	    if(textField.getPropertiesMap().containsProperty(RepConstantsCommon.SNAPSHOT_MODF_COL_LBL_FIELD)
		    || txtExp.indexOf("$F{") == 0 || txtExp.indexOf(RepConstantsCommon.TXT_FILE_DATELBL)==0)
	    {
		// set the lblField value in the text
		// object of the
		// textField
		// fill the liveSearch hashMap(lblField,
		// CO (lblField,
		// type))
		JRDesignExpression expr = new JRDesignExpression();
		if(txtExp.indexOf(RepConstantsCommon.TXT_FILE_DATELBL)==0)
		{
		    lblName = txtExp.substring(txtExp.indexOf("$F{") + 3, txtExp.lastIndexOf("}"));
		    expr.setText("\"" + lblName + "\"");// lbl=col1
		}
		else if(textField.getPropertiesMap().containsProperty(RepConstantsCommon.SNAPSHOT_MODF_COL_LBL_FIELD))
		{
		    lblName = textField.getPropertiesMap().getProperty(RepConstantsCommon.SNAPSHOT_MODF_COL_LBL_FIELD);
		    expr.setText("\"" + lblName + "\"");// lbl=col1
		}
		else
		{
		    lblName=txtExp.replaceAll("\\$F\\{", "\"");
		    lblName=lblName.replaceAll("\\}", "\"");
		    expr.setText(lblName);// lbl=col1
		}
		String type_col = textField.getPropertiesMap().getProperty(RepConstantsCommon.SNAPSHOT_TYP_FIELD);
		if(type_col!=null)
   		{
        		((JRDesignTextField) textField).setExpression(expr);
        
        		if(type.equals(RepConstantsCommon.SNAPSHOT_MODIFIED))
        		{
        		    REP_SNAPSHOT_MODIFY_COLUMNCO repSnapshotModColCO = new REP_SNAPSHOT_MODIFY_COLUMNCO();
        		    repSnapshotModColCO.getRepSnapshotModifyColumnVO().setCOLUMN_MODIFY(
        			    expr.getText().replace("\"", ""));
        		    repSnapshotModColCO.getRepSnapshotModifyColumnVO().setCOLUMN_TYPE(type_col);
        		    repSnapshotModColCO.getRepSnapshotModifyColumnVO().setREP_ID(repId);
        		    repSnapshotModColCO.setColTechName(colTechName);
        		    hashLiveSearch.put(lblName, repSnapshotModColCO);
        		}
        		else if(type.equals(RepConstantsCommon.SNAPSHOT_DRILLDOWN))
        		{
        		    REP_SNAPSHOT_DRILLDOWN_COLUMNCO repSnapshotDrilColCO = new REP_SNAPSHOT_DRILLDOWN_COLUMNCO();
        		    repSnapshotDrilColCO.getRepSnapshotDrilColVO()
        			    .setCOLUMN_DRILLDOWN(expr.getText().replace("\"", ""));
        		    repSnapshotDrilColCO.getRepSnapshotDrilColVO().setCOLUMN_TYPE(type_col);
        		    repSnapshotDrilColCO.getRepSnapshotDrilColVO().setREP_ID(repId);
        		    repSnapshotDrilColCO.setTECH_COL_NAME(colTechName);
        		    hashLiveSearch.put(lblName, repSnapshotDrilColCO);
        		}
        		else if(type.equals(RepConstantsCommon.MISMATCH_CRITERIA_COLUMN))
        		{
        		    REP_MISMATCH_COLUMNCO repMismatchColumnCO = new REP_MISMATCH_COLUMNCO();
        		    if(type_col.equals(RepConstantsCommon.NUMBER_TYPE_JASPER)
        			    && BigDecimal.valueOf(2).equals(crtColOrRelCol))
        		    {
        			repMismatchColumnCO.getRepMismatchColumnVO()
        				.setRELATED_COLUMN(expr.getText().replace("\"", ""));
        			repMismatchColumnCO.getRepMismatchColumnVO().setCOLUMN_TYPE(type_col);
        			repMismatchColumnCO.getRepMismatchColumnVO().setREP_MISMATCH_ID(repId);
        			repMismatchColumnCO.setTECH_COL_NAME(colTechName);
        			hashLiveSearch.put(lblName, repMismatchColumnCO);
        		    }
        		    else if(BigDecimal.valueOf(1).equals(crtColOrRelCol))
        		    {
        			repMismatchColumnCO.getRepMismatchColumnVO()
        				.setRELATED_COLUMN(expr.getText().replace("\"", ""));
        			repMismatchColumnCO.getRepMismatchColumnVO().setCOLUMN_TYPE(type_col);
        			repMismatchColumnCO.getRepMismatchColumnVO().setREP_MISMATCH_ID(repId);
        			repMismatchColumnCO.setTECH_COL_NAME(colTechName);
        			hashLiveSearch.put(lblName, repMismatchColumnCO);
        		    }
        		}
    		}
	    }
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in filling field with static values: "+e.getMessage(),e);
	}
    }

    /**
     * check if a watermark exists inside a background band, if not add it
     * 
     * @param jasperDesign
     * @param subrep 
     */
    public static void addWatermark(JasperDesign jasperDesign, IRP_SUB_REPORTCO subrep)throws BaseException
    {
	try
	{
	int bandHeight = jasperDesign.getPageHeight() - jasperDesign.getTopMargin() - jasperDesign.getBottomMargin();
	
	if(subrep != null)
	{
	    bandHeight = subrep.getSubRepHeight();
	}
	
	if(jasperDesign.getBackground() == null)
	{
	    JRDesignBand band = new JRDesignBand();
	    // Add band as background
	    jasperDesign.setBackground(band);
	}

	JRDesignBand band = (JRDesignBand) jasperDesign.getBackground();
	band.setHeight(bandHeight);
	if(band.getElements().length == 0)
	{
	    JRDesignExpression expression = new JRDesignExpression();
	    expression.setText("\"" + RepConstantsCommon.WATERMARK_TXT + "\"");
	    JRDesignTextField field = new JRDesignTextField();
	    field.setExpression(expression);
	    field.setVerticalTextAlign(VerticalTextAlignEnum.MIDDLE);
	    field.setHorizontalTextAlign(HorizontalTextAlignEnum.CENTER);
	    field.setX(0);
	    field.setY(0);
	    field.setWidth(jasperDesign.getPageWidth() - jasperDesign.getLeftMargin() - jasperDesign.getRightMargin());
	    field.setHeight(band.getHeight());
	    field.setFontSize((float)22);
	    field.setBold(false);
	    field.setForecolor(Color.lightGray);	    
	    band.addElement(field);
	}

	JRElement[] elements = band.getElements();
	JRElement element;
	for(int i = 0; i < elements.length; i++)
	{
	    element = elements[i];
	    element.getPropertiesMap().setProperty("my.watermark", "1");
	}
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in adding watermark: "+e.getMessage(),e);
	}
    }

    /**
     * creates a JasperDesign to test a query
     * @param sqlString
     * @param args
     * @throws JRException 
     * @throws BaseException 
     */
    public static JasperDesign createJasperDesign(StringBuffer sqlString, ArrayList<IRP_REP_ARGUMENTSCO> args) throws JRException, BaseException
    {
	    JasperDesign design = new JasperDesign();
	    try
	    {
	    design.setProperty("ireport.zoom", "1.0");
	    design.setProperty("ireport.x", "0");
	    design.setProperty("ireport.y", "0");
	    design.setColumnWidth(555);
	    design.setName("report1");
	    design.setPageWidth(595);
	    design.setPageHeight(240);
	    design.setLeftMargin(20);
	    design.setRightMargin(20);
	    design.setTopMargin(5);
	    design.setBottomMargin(20);
	    JRDesignQuery query = new JRDesignQuery();
	    query.setText(sqlString.toString());
	    design.setQuery(query);
	    JRDesignParameter parameter;
	    JRDesignExpression expression; 
	    String argType;
	    for(IRP_REP_ARGUMENTSCO arg : args)
	    {
		parameter = new JRDesignParameter();
		 expression = new JRDesignExpression();
		argType = arg.getARGUMENT_TYPE();
		if(argType != null && argType.equals("NUMBER") && arg.getVALUE_ARGUMENT()!=null)
		{
		    expression.setText("new BigDecimal("+arg.getVALUE_ARGUMENT()+")");			
		}
		else if(argType != null && argType.equals("VARCHAR2") && arg.getVALUE_ARGUMENT()!=null)
		{
		    expression.setText("new String("+arg.getVALUE_ARGUMENT()+")");	
		}
		else if(argType != null && argType.equals("DATE") && arg.getVALUE_ARGUMENT()!=null)
		{
		    expression.setText("new Date("+arg.getVALUE_ARGUMENT()+")");
		}
		parameter.setName(arg.getARGUMENT_NAME());
		parameter.setValueClassName(arg.getJrxmlType());
		parameter.setDefaultValueExpression(expression);
		parameter.setForPrompting(false);
		design.addParameter(parameter);
	    }	    
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in creating jasper design: "+e.getMessage(),e);
	}
	    return design;
    }
    
    /**
     * function that call createJasperDesign function to create a sample jrxml file based
     * on the passed args and test the query inside it
     * @param sqlString
     * @param args
     * @param connection
     * @throws JRException
     * @throws BaseException 
     */
    public static void testQryExecution(StringBuffer sqlString, ArrayList<IRP_REP_ARGUMENTSCO> args, Connection connection) throws JRException, BaseException
    {
	try
	{
	JasperDesign design = createJasperDesign(sqlString, args);
	String fileName=ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder)+"/myTestRep_"+ System.currentTimeMillis() +".jasper";
	JasperCompileManager.compileReportToFile(design, fileName);	 
	File reportFile = new PathFileSecure(fileName);
	JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile);
	boolean isDel = reportFile.delete();
	if(!isDel)
	 {
	    log.debug("Error deleting file");
	 }
	JasperFillManager.fillReport(jasperReport,new HashMap<String,Object>(),connection);
	}
	catch(Exception e)
	{
	    throw new BaseException("Error in testing the query execution: "+e.getMessage(),e);
	}
    }
    
    public static HashMap<String,Object>fillColTechNameMap(IRP_AD_HOC_REPORTCO repCO, String filePath)
    {
	HashMap<String,Object> hm= new HashMap<String, Object>();
	try
	{
	    String xmlStr = new String(repCO.getJRXML_FILE(), CommonUtil.ENCODING);
	    FileUtil.writeFile(filePath, xmlStr);
	    JasperDesign jasperDesign = JRXmlLoader.load(filePath);
	    
	    JRBand[] allBandList = jasperDesign.getAllBands();
	    if(allBandList != null)
	    {
		for(int i = 0; i < allBandList.length; i++)
		{
		    changeFieldToStatic(allBandList[i].getElements(), jasperDesign, hm, null, null,null);
		}
	    }
	    
	}
	catch(Exception e)
	{
	    log.error(e,"");
	}
	finally
	{
	    File file;
		try {
			file = new PathFileSecure(filePath);
			boolean isDel = file.delete();
		    if(!isDel)
		    {
			log.debug("\n\n  >>>>>>>>>>>>  The following file has not been deleted :");
		    }
		} catch (Exception e) {
			log.error(e, e.getMessage());
		}
	}
	return hm;
    }
    
    public static HashMap<Long, IRP_FIELDSCO> returnRepVariablesMap(JasperDesign jasperDesign)
    {
	HashMap<Long, IRP_FIELDSCO> varMap = new HashMap<Long, IRP_FIELDSCO>();
	JRElement[] elements = null;
	JRElement element;
	JRTextField textField;
	String txt;
	JRDesignComponentElement compElem;
	Component comp;
	StandardTable tbl;
	List columns;
	StandardColumn col;
	DesignCell cell;
	JRElement[] cellElem;
	JRElement txtElem;
	Map<String, JRVariable> jasperVarMap;
	if(jasperDesign.getDatasetsList().size() == 0)// freeForm
	{
	    jasperVarMap = jasperDesign.getVariablesMap();
	}
	else
	{
	    JRDesignDataset ds = (JRDesignDataset) jasperDesign.getDatasetsList().get(0);
	    jasperVarMap = ds.getVariablesMap();
	}

	JRBand[] details = jasperDesign.getAllBands();
	for(int i = 0; i < details.length; i++)
	{
	    elements = details[i].getElements();
	    for(int j = 0; j < elements.length; j++)
	    {
		element = elements[j];
		if(element instanceof JRTextField)
		{
		    textField = (JRTextField) element;
		    if(textField.getExpression() != null)
		    {
			txt = textField.getExpression().getText();
			fillVariablesMap(txt, varMap, jasperVarMap);
		    }
		}
		else if(element instanceof JRDesignComponentElement)
		{
		    compElem = (JRDesignComponentElement) element;
		    comp = compElem.getComponent();
		    if(comp instanceof StandardTable)
		    {
			tbl = (StandardTable) comp;
			columns = tbl.getColumns();
			for(int k = 0; k < columns.size(); k++)
			{
			    col = (StandardColumn) columns.get(k);
			    cell = (DesignCell) col.getDetailCell();
			    cellElem = cell.getElements();
			    for(int l = 0; l < cellElem.length; l++)
			    {
				txtElem = cellElem[l];
				if(txtElem instanceof JRDesignTextField)
				{
				    textField = (JRDesignTextField) txtElem;
				    txt = textField.getExpression().getText();
				    fillVariablesMap(txt, varMap, jasperVarMap);
				}
			    }
			}
		    }
		}
	    }
	}
	return varMap;
    }
    
    
    public static void fillVariablesMap(String txt, HashMap<Long, IRP_FIELDSCO> varMap,Map<String, JRVariable> jasperVarMap)
    {
	String varName;
	IRP_FIELDSCO feCO;
	JRVariable jasperVar;
	String jasperVarType;
	try
	{
	    if(txt.indexOf("$V{") == 0)
	    {
		varName = txt.replace("$V{", "");
		varName = varName.substring(0, varName.length() - 1);
		jasperVar = jasperVarMap.get(varName);
		jasperVarType = jasperVar.getValueClassName();
		if(RepConstantsCommon.VARCHAR_TYPE_JASPER.equals(jasperVarType))
		{
		    feCO = new IRP_FIELDSCO();
		    feCO.setFIELD_ALIAS(varName);
		    feCO.setFIELD_DATA_TYPE(jasperVarType);
		    feCO.setLabelAlias(varName);
		    varMap.put(Calendar.getInstance().getTimeInMillis(), feCO);
		}
	    }
	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }   
  
    public static void updateFtrFcrReportQuery(JasperDesign jasperDesign)
    {
	try
	{
	    JRDesignDataset ds;
	    if(jasperDesign.getDatasetsList().size() == 0)// freeForm
	    {
		ds = jasperDesign.getMainDesignDataset();
	    }
	    else
	    {
		ds = (JRDesignDataset) jasperDesign.getDatasetsList().get(0);
	    }
	    JRDesignQuery jrDsQry = (JRDesignQuery) ds.getQuery();
	    String qryStr = jrDsQry.getText();
	  
	    //Replace FTR_REP1 with FTR_REP_TEMP and FTR_REP with FTR_REP_TMP
	    qryStr = StringUtil.replaceInString(qryStr, RepConstantsCommon.FTR_REP1_TBL,RepConstantsCommon.REP1_FTR_TEMP); 
	    qryStr = StringUtil.replaceInString(qryStr, RepConstantsCommon.FTR_REP_TBL,RepConstantsCommon.FTR_REP_TMP_HASH); 
	    qryStr = StringUtil.replaceInString(qryStr, RepConstantsCommon.REP1_FTR_TEMP,RepConstantsCommon.FTR_REP_TEMP_HASH); 

	    jrDsQry.setText(qryStr);

	}
	catch(Exception e)
	{
	    log.error(e, "");
	}
    }
 
    
}