/*
 * ============================================================================
 * GNU Lesser General Public License
 * ============================================================================
 *
 * JasperReports - Free Java report-generating library.
 * Copyright (C) 2001-2006 JasperSoft Corporation http://www.jaspersoft.com
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307, USA.
 * 
 * JasperSoft Corporation
 * 303 Second Street, Suite 450 North
 * San Francisco, CA 94107
 * http://www.jaspersoft.com
 */
package net.sf.jasperreports.engine.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import net.sf.jasperreports.engine.JRException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * XML parsing utilities.
 * 
 * @author Lucian Chirita (lucianc@users.sourceforge.net)
 * @version $Id: JRXmlUtils.java 2154 2008-04-17 14:46:12Z lucianc $
 */
public class JRXmlUtils
{
	private static final Log log = LogFactory.getLog(JRXmlUtils.class);
	
	
	/**
	 * Parses an input source into a document.
	 * 
	 * @param is the input source
	 * @return the parsed document
	 * @throws JRException
	 */
	public static Document parse(InputSource is) throws JRException
	{
		try
		{
			return createDocumentBuilder().parse(is);
		}
		catch (SAXException e)
		{
			throw new JRException("Failed to parse the xml document", e);
		}
		catch (IOException e)
		{
			throw new JRException("Failed to parse the xml document", e);
		}
	}
	
	
	/**
	 * Parses a document specified by an URI.
	 * 
	 * @param uri the URI
	 * @return the parsed document
	 * @throws JRException
	 */
	public static Document parse(String uri) throws JRException
	{
		return parse(new InputSource(uri));
	}

	
	/**
	 * Parses a file into a document.
	 * 
	 * @param file the XML file
	 * @return the document
	 * @throws JRException
	 */
	public static Document parse(File file) throws JRException
	{
		try
		{
			return createDocumentBuilder().parse(file);
		}
		catch (SAXException e)
		{
			throw new JRException("Failed to parse the xml document", e);
		}
		catch (IOException e)
		{
			throw new JRException("Failed to parse the xml document", e);
		}
	}

	
	/**
	 * Parses an input stream into a XML document.
	 * 
	 * @param is the input stream
	 * @return the document
	 * @throws JRException
	 */
	public static Document parse(InputStream is) throws JRException
	{
		return parse(new InputSource(is));
	}

	
	/**
	 * Parses an URL stream as a XML document.
	 * 
	 * @param url the URL
	 * @return the document
	 * @throws JRException
	 */
	public static Document parse(URL url) throws JRException
	{
		InputStream is = null;
		try
		{
			is = url.openStream();
			return createDocumentBuilder().parse(is);
		}
		catch (SAXException e)
		{
			throw new JRException("Failed to parse the xmlf document", e);
		}
		catch (IOException e)
		{
			throw new JRException("Failed to parse the xml document", e);
		}
		finally
		{
			if (is != null)
			{
				try
				{
					is.close();
				}
				catch (IOException e)
				{
					log.warn("Error closing stream of URL " + url, e);
				}
			}
		}
	}

	
	/**
	 * Creates a XML document builder.
	 * 
	 * @return a XML document builder
	 * @throws JRException
	 */
	public static DocumentBuilder createDocumentBuilder() throws JRException
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		dbf.setValidating(false);
		dbf.setIgnoringComments(true);

		try
		{
			return dbf.newDocumentBuilder();
		}
		catch (ParserConfigurationException e)
		{
			throw new JRException("Failed to create a document builder factory", e);
		}
	}

	
	/**
	 * Creates a document having a node as root.
	 * 
	 * @param sourceNode the node
	 * @return a document having the specified node as root
	 * @throws JRException
	 */
	public static Document createDocument(Node sourceNode) throws JRException
	{
		Document doc = JRXmlUtils.createDocumentBuilder().newDocument();
		Node source;
		if (sourceNode.getNodeType() == Node.DOCUMENT_NODE) {
			source = ((Document) sourceNode).getDocumentElement();
		} else {
			source = sourceNode;
		}

		Node node = doc.importNode(source, true);
		doc.appendChild(node);
		
		return doc;
	}
}
