package com.path.bo.reporting.common.util;

import javax.xml.transform.Source;
import javax.xml.transform.TransformerException;
import javax.xml.transform.URIResolver;
import javax.xml.transform.stream.StreamSource;

import com.path.lib.common.util.ApplicationContextProvider;
import com.path.lib.log.Log;

/**
 * class to point of XSL able to load XSL
 * Copyright 2015, Path Solutions
 * Path Solutions retains all ownership rights to this source code 
 * 
 * @author: deniskhaddad
 *
 * ClasspathResourceURIResolver.java used to
 */
public class ClasspathResourceURIResolver implements URIResolver
{
    private String classpathURI;
    @Override
    public Source resolve(String href, String base) throws TransformerException
    {
	try
	{
	    return new StreamSource(ApplicationContextProvider.getApplicationContext()
		    .getResource(classpathURI.concat(href)).getInputStream());
	   
	}
	catch(Exception e)
	{
	    Log.getInstance().error(e, "Error in reading Files imported from XSL file ");
	    return null;
	}

    }
    public void setClasspathURI(String classpathURI)
    {
        this.classpathURI = classpathURI;
    }

}
