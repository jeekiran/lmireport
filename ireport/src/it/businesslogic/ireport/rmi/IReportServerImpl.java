/*
 * Copyright (C) 2005 - 2008 JasperSoft Corporation.  All rights reserved.
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
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
 *
 *
 * IReportServerImpl.java
 *
 */

package it.businesslogic.ireport.rmi;
//-----------------------------------------------------------
// File: SampleServerImpl.java
//-----------------------------------------------------------

import it.businesslogic.ireport.Report;
import it.businesslogic.ireport.gui.JReportFrame;
import it.businesslogic.ireport.gui.MainFrame;
import it.businesslogic.ireport.gui.WizardDialog;
import it.businesslogic.ireport.util.PageSize;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import com.chinacreator.ireport.IreportConstant;
import com.chinacreator.ireport.IreportUtil;
import com.chinacreator.ireport.rmi.ClientRmiServerInterface;
import com.chinacreator.ireport.rmi.ClientRmiServerInterfaceImpl;

public class IReportServerImpl extends UnicastRemoteObject
                             implements IReportServer, Runnable
{
  static IReportServerImpl mainInstance = null;

  public static IReportServerImpl getMainInstance()
  {
      if (mainInstance == null)
      {
          try {
                mainInstance = new IReportServerImpl();
          } catch (Exception ex)
          {
              ex.printStackTrace();
          }
      }

      return mainInstance;
  }

 public IReportServerImpl() throws RemoteException
  {
     super();
  }



  public static void runServer()
  {
        Thread t = new Thread( IReportServerImpl.getMainInstance() );
        t.start();

  }

  public void run()
  {

    try
      {
        int port = Integer.parseInt(IreportConstant.CLIENT_RMI_PORT);

        try {

    		System.setProperty("java.rmi.server.hostname",IreportUtil.getLocalIp()); //perhaps the problem
    		System.setProperty("java.security.policy", "java.policy"); // should be no problem

        	ClientRmiServerInterface ireportRemote = ClientRmiServerInterfaceImpl.getInstance();
   		    LocateRegistry.createRegistry(port);
            Naming.bind("rmi://"+IreportUtil.getLocalIp()+":"+port+"/ireportClientRmiServer", ireportRemote);
            System.out.println("report客户端RMI服务器监听于端口："+port);
   		} catch (Exception e) {
   			e.printStackTrace();
   		}
      }
    catch (Exception re)
      {
         System.out.println("Remote exception: " + re.toString());
      }

  }

  // --------------------------------------------------------------------------------------

  /**
   * Used to check if iReport is alive
   */
  public boolean ping()
  {
      return true;
  }

  /**
   * Used to show the main window and bring the iReport window on top...
   */
  public boolean setVisible(boolean b)
  {
      MainFrame.getMainInstance().setVisible(b);
      if (MainFrame.getMainInstance().getState() == java.awt.Frame.ICONIFIED)
      {
            MainFrame.getMainInstance().setState( java.awt.Frame.NORMAL );
      }
      return MainFrame.getMainInstance().requestFocusInWindow();
  }

  /**
   * Open the file passed as parameter...
   */
  public boolean openFile(String file)
  {
      setVisible(true);
      try {
          JReportFrame jrf = MainFrame.getMainInstance().openFile( file );
          jrf.setSelected(true);
          return true;
      } catch (Exception ex){
           return false;
      }
  }


  public boolean runWizard(String destFile)
  {
  	MainFrame mainFrame = MainFrame.getMainInstance();

  	if (mainFrame == null) return false;



	mainFrame.logOnConsole("Invocato wizard");
	mainFrame.logOnConsole("Pronto ad invocare la nuova finestra..." + Thread.currentThread().getName());


        try {
                // TODO
                // Set the project directory as current directory;

                WizardDialog wd = new WizardDialog(mainFrame,true);

                mainFrame.logOnConsole("Lancio wizard");
                wd.setVisible(true);
                wd.requestFocus();


                Report report = null;
                if (wd.getDialogResult() == javax.swing.JOptionPane.OK_OPTION) {
                    report = wd.getReport();
                    if (report == null)
                    {
                        report = createBlankReport();
                    }
                }
                else
                {
                    report = createBlankReport();
                }

                if (report != null)
                {
                        mainFrame.openNewReportWindow(report);
                        report.setFilename(destFile);
                        report.saveXMLFile();
                        setVisible(true);
                }

        } catch (Exception ex)
        {
              System.out.println(ex.getMessage());
                ex.printStackTrace();
        }


      return true;
  }

  private Report createBlankReport()
  {
      Report newReport = new Report();

        newReport.setName(it.businesslogic.ireport.util.I18n.getString("untitledReport", "untitled_report_")+"1");
        newReport.setUsingMultiLineExpressions(false); //this.isUsingMultiLineExpressions());
        newReport.setWidth(  PageSize.A4.x);
        newReport.setHeight( PageSize.A4.y);
        newReport.setTopMargin(20);
        newReport.setLeftMargin(30);
        newReport.setRightMargin(30);
        newReport.setBottomMargin(20);
        newReport.setColumnCount(1);
        newReport.setColumnWidth( newReport.getWidth() - newReport.getLeftMargin() - newReport.getRightMargin() );
        newReport.setColumnSpacing(0);

        return newReport;
  }

  public static void main(String[] args) {
	try {
		IReportServerImpl.runServer();
	} catch (Exception e) {
		// FIXME Auto-generated catch block
		e.printStackTrace();
	}
}

}



