package com.path.bo.reporting.reportviewer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.path.bo.common.ConstantsCommon;
import com.path.bo.reporting.ReportingConstantsCommon;
import com.path.bo.reporting.ReportingFileUtil;
import com.path.bo.reporting.common.RepConstantsCommon;
import com.path.lib.common.base.BaseBO;
import com.path.lib.common.exception.BOException;
import com.path.lib.common.exception.BaseException;
import com.path.lib.common.util.FileUtil;
import com.path.lib.common.util.PathFileSecure;
import com.path.vo.reporting.reportviewer.ReportViewerCO;

public class ReportViewerBOImpl extends BaseBO implements ReportViewerBO
{
    public ArrayList<ReportViewerCO> retLinkPathList(ReportViewerCO reportViewerCO) throws BaseException
    {
	ArrayList<ReportViewerCO> colLinkHeader = new ArrayList<ReportViewerCO>();
	try
	{
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    File parentPath_dir = new PathFileSecure(repositoryPath + "/" + reportViewerCO.getElementPath());

	    if(parentPath_dir.exists())
	    {
		String[] linkPathFolder = reportViewerCO.getElementPath().split("/");
		String elementPath = "";
		String elem = "";
		for(int i = 0; i < linkPathFolder.length; i++)
		{
		    //
		    elem = linkPathFolder[i];
		    elementPath += elem + "/";
		    reportViewerCO = new ReportViewerCO();
		    reportViewerCO.setElementPath(elementPath);
		    reportViewerCO.setElementName(elem);
		    reportViewerCO.setElementType(RepConstantsCommon.FOLDER);
		    colLinkHeader.add(reportViewerCO);
		}
	    }
	}
	catch(Exception e)
	{
	    throw new BOException(e.getMessage(), e);
	}
	return colLinkHeader;
    }

    public ArrayList<ReportViewerCO> retLeftMenuList(ReportViewerCO reportViewerCOParam) throws BaseException
    {
	ArrayList<ReportViewerCO> col = new ArrayList<ReportViewerCO>();

	String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	File parentPath_dir = null;
	try {
		parentPath_dir = new PathFileSecure(repositoryPath + "/" + reportViewerCOParam.getElementPath());
	} catch (Exception e1) {
		log.error(e1, e1.getMessage());
	}

	if(parentPath_dir.exists())
	{
		File f = null;
	    LinkedList<String> elems = new LinkedList<String>();
	    String elem;
	    String elemType;
	    ReportViewerCO reportViewerCO;
	    String elementPath;
	    List<File> filenames = Arrays.asList(parentPath_dir.listFiles());
	    Collections.sort(filenames, new Comparator<File>() 
	    {
	            private final Comparator<String> NATURAL_SORT = new WindowsExplorerComparator();

	            @Override
	            public int compare(File o1, File o2) {;
	                return NATURAL_SORT.compare(o1.getName(), o2.getName());
	            }
	    });
	    for(File file : filenames)
	    {
		elems.addFirst(file.getName());
	    }
	    for(int i = 0; i < elems.size(); i++)
	    {
		elem = elems.get(i);
		try {
			f = new PathFileSecure(parentPath_dir + "/" + elem);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		elemType = checkIfFolderOrFile(f);
		reportViewerCOParam.setElementType(elemType);
		if((RepConstantsCommon.FOLDER.equals(elemType))
			|| (RepConstantsCommon.FILE.equals(elemType)
				&& elem.toUpperCase().endsWith(".".concat(ConstantsCommon.HTML_REP_FORMAT))
				|| elem.toUpperCase().endsWith(".".concat(ConstantsCommon.PDF_REP_FORMAT))
				|| elem.toUpperCase().endsWith(".".concat(ConstantsCommon.DOC_REP_FORMAT))
				|| elem.toUpperCase().endsWith(".".concat(ConstantsCommon.XLS_REP_FORMAT))
				|| elem.toUpperCase().endsWith(".".concat(ConstantsCommon.CSV_REP_FORMAT)) 
				|| elem.toUpperCase().endsWith(".".concat(ConstantsCommon.ODS_REP_FORMAT)) || elem
				.toLowerCase().endsWith(".".concat(ConstantsCommon.TXT_EXT))))
		{
		    reportViewerCO = new ReportViewerCO();
		    elementPath = reportViewerCOParam.getElementPath();
		    if(!elementPath.endsWith("/"))
		    {
			elementPath = elementPath.concat("/");
		    }
		    reportViewerCO.setElementPath(elementPath.concat(elem));
		    reportViewerCO.setElementName(elem);
		    reportViewerCO.setElementType(elemType);
		    col.add(reportViewerCO);
		}
	    }
	}
	return col;
    }

    public static class WindowsExplorerComparator implements Comparator<String> {

        private static final Pattern splitPattern = Pattern.compile("\\d+|\\.|\\s");

        @Override
        public int compare(String str1, String str2) {
            Iterator<String> i1 = splitStringPreserveDelimiter(str1).iterator();
            Iterator<String> i2 = splitStringPreserveDelimiter(str2).iterator();
            while (true) {
                //Til here all is equal.
                if (!i1.hasNext() && !i2.hasNext()) {
                    return 0;
                }
                //first has no more parts -> comes first
                if (!i1.hasNext() && i2.hasNext()) {
                    return -1;
                }
                //first has more parts than i2 -> comes after
                if (i1.hasNext() && !i2.hasNext()) {
                    return 1;
                }

                String data1 = i1.next();
                String data2 = i2.next();
                int result;
                try {
                    //If both datas are numbers, then compare numbers
                    result = Long.compare(Long.valueOf(data1), Long.valueOf(data2));
                    //If numbers are equal than longer comes first
                    if (result == 0) {
                        result = -Integer.compare(data1.length(), data2.length());
                    }
                } catch (NumberFormatException ex) {
                    //compare text case insensitive
                    result = data1.compareToIgnoreCase(data2);
                }

                if (result != 0) {
                    return result;
                }
            }
        }

        private List<String> splitStringPreserveDelimiter(String str) {
            Matcher matcher = splitPattern.matcher(str);
            List<String> list = new ArrayList<String>();
            int pos = 0;
            while (matcher.find()) {
                list.add(str.substring(pos, matcher.start()));
                list.add(matcher.group());
                pos = matcher.end();
            }
            list.add(str.substring(pos));
            return list;
        }
    }
    
    public static long getFileCreationEpoch(File file)
    {
	try
	{
	    BasicFileAttributes attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
	    return attr.creationTime().toInstant().toEpochMilli();
	}
	catch(IOException e)
	{
	    throw new RuntimeException(file.getAbsolutePath(), e);
	}
    }
    
    public String checkIfFolderOrFile(File file)
    {
	if(file.isDirectory())
	{
	    return (RepConstantsCommon.FOLDER);
	}
	else
	{
	    return (RepConstantsCommon.FILE);
	}
    }

    public byte[] retReportBytes(ReportViewerCO reportViewerCO) throws BaseException
    {
	byte[] inputByte;
	try
	{
	    String repositoryPath = ReportingFileUtil.getReportingRepoPath(ReportingConstantsCommon.repositoryFolder);
	    File parentPath_dir = new PathFileSecure(repositoryPath + "/" + reportViewerCO.getElementPath());
	    String linkPath = parentPath_dir.toString();
	    inputByte = FileUtil.readFileBytes(linkPath);
	}
	catch(Exception e)
	{
	    throw new BOException(e.getMessage(), e);
	}
	return inputByte;
    }
}
