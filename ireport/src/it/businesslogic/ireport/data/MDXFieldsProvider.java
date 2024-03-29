/*
 * Copyright (C) 2005-2007 JasperSoft http://www.jaspersoft.com
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 *
 *
 * SQLFieldsProvider.java
 *
 * Created on December 7, 2006, 9:22 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package it.businesslogic.ireport.data;

import it.businesslogic.ireport.FieldsProvider;
import it.businesslogic.ireport.FieldsProviderEditor;
import it.businesslogic.ireport.IReportConnection;
import it.businesslogic.ireport.gui.ReportQueryDialog;

import java.util.Map;

import net.sf.jasperreports.engine.JRDataset;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import rex.event.RexWizardEvent;
import rex.event.RexWizardListener;

/**
 *
 * @author gtoffoli
 */
public class MDXFieldsProvider implements FieldsProvider, RexWizardListener {

    //private OlapBrowser olapBrowser = null;
    protected String getQueryFromRex = "";

    public static boolean useVisualDesigner = true;

	public String designQuery(IReportConnection con, String query,
			ReportQueryDialog reportQueryDialog) throws JRException,
			UnsupportedOperationException {
		// FIXME Auto-generated method stub
		return null;
	}

	public FieldsProviderEditor getEditorComponent(
			ReportQueryDialog reportQueryDialog) {
		// FIXME Auto-generated method stub
		return null;
	}

	public JRField[] getFields(IReportConnection con, JRDataset reportDataset,
			Map parameters) throws JRException, UnsupportedOperationException {
		// FIXME Auto-generated method stub
		return null;
	}

	public boolean hasEditorComponent() {
		// FIXME Auto-generated method stub
		return false;
	}

	public boolean hasQueryDesigner() {
		// FIXME Auto-generated method stub
		return false;
	}

	public boolean supportsAutomaticQueryExecution() {
		// FIXME Auto-generated method stub
		return false;
	}

	public boolean supportsGetFieldsOperation() {
		// FIXME Auto-generated method stub
		return false;
	}

	public void getMdx(RexWizardEvent arg0) {
		// FIXME Auto-generated method stub

	}

    /*static {

        java.util.Properties p = new java.util.Properties();
        try {
            p.load( MDXFieldsProvider.class.getClass().getResourceAsStream("/it/businesslogic/ireport/data/fieldsprovider.properties")  );

            if (p.getProperty("mdx").equals("0"))
            {
                useVisualDesigner = false;
                System.out.println("ReX designer disabled");
            }
        } catch (Exception ex)
        {
            ex.printStackTrace();
        }

    }



    *//** Creates a new instance of SQLFieldsProvider *//*
    public MDXFieldsProvider() {
    }

    *//**
     * Returns true if the provider supports the {@link #getFields(IReportConnection,JRDataset,Map) getFields}
     * operation. By returning true in this method the data source provider indicates
     * that it is able to introspect the data source and discover the available fields.
     *
     * @return true if the getFields() operation is supported.
     *//*
    public boolean supportsGetFieldsOperation() {
        return false;
    }

    public JRField[] getFields(IReportConnection con, JRDataset reportDataset, Map parameters) throws JRException, UnsupportedOperationException {
        return null;
    }

    public boolean supportsAutomaticQueryExecution() {
        return true;
    }

    public boolean hasQueryDesigner() {
        return useVisualDesigner;
    }

    public boolean hasEditorComponent() {
        return true;
    }

    *//**
     * Copyright (C) 2006 CINCOM SYSTEMS, INC.
     * All Rights Reserved
     * www.cincom.com
     *//*


     * Opens the Rex MDX Query Editor
     * @param MdxQuery
     * @retrun mdx Query from REX
     * sending the Query to Rex and bringing back the modified MDX query

    boolean gotMdxResult = false;
    public String designQuery(IReportConnection con,  String query, ReportQueryDialog reportQueryDialog) throws JRException, UnsupportedOperationException {

        String newMDXQuery = query;
        if (query == null) newMDXQuery = "";

        if (con instanceof JRXMLADataSourceConnection)
        {
            String strURL = ((JRXMLADataSourceConnection)con).getUrl();

            String strDataSource = ((JRXMLADataSourceConnection)con).getDatasource();

            String strCatalog = ((JRXMLADataSourceConnection)con).getCatalog();

            String strCubeName=((JRXMLADataSourceConnection)con).getCube();

            String username = ((JRXMLADataSourceConnection)con).getUsername();
            if (username != null && username.length() > 0)
            {

                Authenticator.setDefault(new CustomHTTPAuthenticator( username, ((JRXMLADataSourceConnection)con).getPassword() ));
            }

            //invoke MDXEditor
            RexWizard mdxWizard= new RexWizard(strURL, strDataSource, strCatalog,
                    strCubeName,newMDXQuery,I18n.getCurrentLocale());
            // Passing the MDX Query to Rex Wizard
            mdxWizard.addRexWizardListener(this);


            mdxWizard.showDialog();

            //Wait for the return value....
            //System.out.println("Waiting for MDX query");
            //System.out.flush();
            //while (!gotMdxResult)
            //{
            //    Thread.yield();
            //}

            //System.out.println("My MDX query " + getQueryFromRex);
            //System.out.flush();
            //condition added to handle Cancel click's in RexWizard
            if (getQueryFromRex.length() > 0){
                return getQueryFromRex;
            }
        }
        else
        {
            javax.swing.JOptionPane.showMessageDialog(MainFrame.getMainInstance(),
                        I18n.getString("messages.reportQueryDialog.connectionNotSupported","In order to use the MDX query designer, you need an XMLA datasource active."),
                        "",javax.swing.JOptionPane.WARNING_MESSAGE );
                return null;

        }

        return query;
    }

    *//**
     * Rex Passes the MDX query to Report Query Dialog
     *//*
     public void getMdx(RexWizardEvent evt){

          getQueryFromRex = evt.getQuery();
          gotMdxResult = true;

     }

 end of modifications

    public FieldsProviderEditor getEditorComponent(ReportQueryDialog reportQueryDialog) {

        if (olapBrowser == null)
        {
            olapBrowser = new OlapBrowser();
            olapBrowser.setReportQueryDialog(reportQueryDialog);
            if (reportQueryDialog != null)
            {
                olapBrowser.setJTableFields( reportQueryDialog.getFieldsTable() );
            }
        }
        return olapBrowser;
    }
    */
}
