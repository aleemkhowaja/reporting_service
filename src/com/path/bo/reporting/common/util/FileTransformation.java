package com.path.bo.reporting.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.xml.JRPrintXmlLoader;

import org.springframework.core.io.Resource;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.path.lib.common.util.ApplicationContextProvider;
import com.path.lib.log.Log;
import com.path.reporting.lib.common.util.CommonUtil;

public final class FileTransformation
{
    private static final Log log = Log.getInstance();
    private FileTransformation() 
    {
	log.debug("This Class Should not be Instantiated");
    }

    public static String xslTransformation(String xhtml, String xslFileName)
    {
	try
	{
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    StreamResult xmlResult = new StreamResult(stream);
	    // byte[] xmlInput = readFile(new File(xmlFileName));
	    // ByteArrayInputStream inputXml = new
	    // ByteArrayInputStream(xmlInput);
	    log.debug("xhtml::: \n" + xhtml);
	    ByteArrayInputStream inputXml = new ByteArrayInputStream(xhtml.getBytes(CommonUtil.ENCODING));
	    StreamSource streamSource = new StreamSource(inputXml);
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    // initialise class URI loader in order to be able of any XSL that have  import of other XSL to work properly
	    ClasspathResourceURIResolver uriResolver = new ClasspathResourceURIResolver();
	    uriResolver.setClasspathURI("classpath:com/path/bo/reporting/common/jasperRules/");
	    tFactory.setURIResolver(uriResolver);
	    Resource res =ApplicationContextProvider.getApplicationContext().getResource(xslFileName);
	    Transformer t = tFactory.newTransformer(new StreamSource(res.getInputStream()));
	    t.transform(streamSource, xmlResult);
	    return stream.toString(CommonUtil.ENCODING);
	}
	catch(Exception e)
	{
	    log.error(e, "Error in transformation process");
	    return null;
	}
    }

    public static String transformationToXhtml(byte[] jrxmlFILE, String xslFileName)
    {
	try
	{
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    StreamResult xhtmlResult = new StreamResult(stream);
	    String jrxml = new String(jrxmlFILE, CommonUtil.ENCODING);
	    ByteArrayInputStream inputXml = new ByteArrayInputStream(jrxml.getBytes(CommonUtil.ENCODING));
	    StreamSource streamSource = new StreamSource(inputXml);
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    
	    // initialise class URI loader in order to be able of any XSL that have  import of other XSL to work properly
	    ClasspathResourceURIResolver uriResolver = new ClasspathResourceURIResolver();
	    uriResolver.setClasspathURI("classpath:com/path/bo/reporting/common/jasperRules/");
	    tFactory.setURIResolver(uriResolver);
	    Resource res = ApplicationContextProvider.getApplicationContext().getResource(xslFileName);
	   
	    Transformer t = tFactory.newTransformer(new StreamSource(res.getInputStream()));
	    t.transform(streamSource, xhtmlResult);
	    return stream.toString(CommonUtil.ENCODING);
	}
	catch(Exception e)
	{
	    log.error(e, "Error in transforming jrxml format into xhtml format");
	    return null;
	}
    }

    public static String reorderJRxml(byte[] jrxmlFILE, String xslFileName)
    {
	try
	{
	    ByteArrayOutputStream stream = new ByteArrayOutputStream();
	    StreamResult newJRxml = new StreamResult(stream);
	    ByteArrayInputStream inputXml = new ByteArrayInputStream(jrxmlFILE);
	    StreamSource streamSource = new StreamSource(inputXml);
	    TransformerFactory tFactory = TransformerFactory.newInstance();
	    Resource res =ApplicationContextProvider.getApplicationContext().getResource(xslFileName);
	    Transformer t = tFactory.newTransformer(new StreamSource(res.getInputStream()));
	    t.transform(streamSource, newJRxml);
	    return stream.toString(CommonUtil.ENCODING);
	}
	catch(Exception e)
	{
	    log.error(e, "Error in transforming jrxml format into xhtml format.");
	    return null;
	}
    }

    /**
     * removes the watermark from jasperPrint
     * 
     * @param jasperPrint
     * @return
     * @throws JRException
     */
    public static JasperPrint removeWatermarkFromJP(JasperPrint jasperPrint) throws JRException
    {
	try
	{
	    String reportAsXML = JasperExportManager.exportReportToXml(jasperPrint);
	    log.debug(reportAsXML);
	    DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document document = builder.parse(new InputSource(new StringReader(reportAsXML)));
	    XPath xpath = XPathFactory.newInstance().newXPath();
	    String expression = "//property[@name=\"my.watermark\"]";
	    NodeList nodes = (NodeList) xpath.evaluate(expression, document, XPathConstants.NODESET);
	    if(nodes != null && nodes.getLength() > 0)
	    {
		Node node;
		Node nodeToRemove;
		for(int i = 0; i < nodes.getLength(); i++)
		{
		    node = nodes.item(i);
		    nodeToRemove = node.getParentNode().getParentNode();
		    nodeToRemove.getParentNode().removeChild(nodeToRemove);
		}
	    }

	    Transformer transformer = TransformerFactory.newInstance().newTransformer();
	    StreamResult result = new StreamResult(new StringWriter());
	    DOMSource source = new DOMSource(document);
	    transformer.transform(source, result);
	    String reportAsStr = result.getWriter().toString();
	    InputStream is = new ByteArrayInputStream(reportAsStr.getBytes(CommonUtil.ENCODING));
	    return JRPrintXmlLoader.load(is);
	}
	catch(Exception e)
	{
	   log.error(e, e.getMessage());
	   return null;
	}
    }
}
