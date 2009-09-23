/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with this
 * work for additional information regarding copyright ownership. The ASF
 * licenses this file to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 *
 */
package com.chinacreator.ireport;

import it.businesslogic.ireport.IReportConnection;
import it.businesslogic.ireport.Report;
import it.businesslogic.ireport.gui.JReportFrame;
import it.businesslogic.ireport.gui.MainFrame;
import it.businesslogic.ireport.util.I18n;

import java.awt.Font;
import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.xerces.parsers.DOMParser;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.chinacreator.ireport.component.DialogFactory;
import com.chinacreator.ireport.rmi.IreportFile;
import com.chinacreator.ireport.rmi.IreportRmiClient;
import com.chinacreator.ireport.rmi.IreportSession;
import com.chinacreator.ireport.rmi.ReportLock;

/**
 * @author ��ï
 * @since 3.0
 * @version $Id: AddedOperator.java 2009-9-1 ����02:50:45 $
 *
 */
//begin AddedOperator.java

//���к�����ӵĲ������������������
public class AddedOperator implements AddedOepretorInterface{
	private static Log logger = LogFactory.getLog(AddedOperator.class);
	public static AddedOepretorInterface addInstance = null;
	public Object afterClose(String closeFilePath) {
		logger.info("**********��ʼafterClose***********");
		String id = null;
		String realId = null;
		try {
			//�ж��ǲ����ɱ����½����ļ�
			 String localRealFileId = IreportUtil.isLocalNewToServerFile(closeFilePath);

			 IreportRmiClient.getInstance();
			 File f = new File(closeFilePath);
			 String repid = IreportUtil.getIdFromReport(f.getName());

			 realId = repid;
			 if(IreportUtil.isBlank(repid)){
				 log("�ر�ʱȡ���ı���IDΪ��", IreportConstant.ERROR_);
				 DialogFactory.showErrorMessageDialog(null, "�ر�ʱȡ���ı���IDΪ�գ�", "����");
			 }


			 if(!IreportUtil.isBlank(localRealFileId)){
				//���������ж�Ϊ�ɱ����½����ļ�
				 realId = localRealFileId;
			 }

			  id = repid;//���Ǳ��ؿ�������
	          IreportRmiClient.rmiInterfactRemote.unLockReport(IreportUtil.getLocalIp(),IreportUtil.removeNameSuffix(realId));

	          //�������������Ϣ
	         IreportUtil.removeLocalToServerLock(closeFilePath, false);
		} catch (Exception e) {
			e.printStackTrace();
			log("�ر�ʱȡ��["+id+"]����ʱ���������Ҫ�ֶ����������\n"+e.getMessage(), IreportConstant.ERROR_);
			DialogFactory.showErrorMessageDialog(null, "�ر�ʱȡ��["+id+"]����ʱ���������Ҫ�ֶ����������\n"+e.getMessage(), "����");
		}
		logger.info("����afterClose");
		return null;
	}

	public Object afterOpen() {
		// FIXME Auto-generated method stub
		return null;
	}

	//�������ı����������Ҫ������Ĵ�����Ҫ���Խ�����ļ����浽������
	public Object afterSave(final String saveFilePath) {
		new Thread(new Runnable(){
		public void run() {
		logger.info("************��ʼִ��afterSave***********");
		 try {
			   IreportRmiClient.getInstance();
			   String repid = IreportUtil.getIdFromReportPath(saveFilePath);
			   String localRealFileId = IreportUtil.isLocalNewToServerFile(saveFilePath);
			   if(IreportUtil.isBlank(localRealFileId)){//���Ǳ����½����ļ�
			   ReportLock r = IreportRmiClient.rmiInterfactRemote.getCurrentLock(IreportUtil.removeNameSuffix(repid));

			   //����ǰ�ñ������������ģ����ǲ������������
			   if(!IreportUtil.isYourLock(r)){
				   log("���������ļ�������������ƣ��㲻�ܽ��з������������", IreportConstant.ERROR_);
				   DialogFactory.showErrorMessageDialog(null, "���������ļ�������������ƣ��㲻�ܽ��з������������", "������ʾ");
				   return;
			   }
			   }
	           String filePath =  saveFilePath;
	           File f = new File(filePath);
	           IreportFile rf = new IreportFile();
	           rf.setFileName(IreportUtil.removeNameSuffix(f.getName()));

	           byte[] content = new byte[(int)f.length()];
	           BufferedInputStream input = new BufferedInputStream(new FileInputStream(f));
	           input.read(content);
	           if(input != null ){
		              try{
		                  input.close();
		                  input = null ;
		              }catch(Exception ex){
		              }
		       }

	           //�����½������������ļ�
	           if(!IreportUtil.isBlank(localRealFileId)){
	        	   rf.setFileName(localRealFileId+".jrxml");
	           }
	           rf.setContent(content);

	           IreportRmiClient.rmiInterfactRemote.save(rf);
	            } catch (Exception e) {
					e.printStackTrace();
					log("�������ļ�����ʧ��", IreportConstant.ERROR_);
					DialogFactory.showErrorMessageDialog(null, "�ڽ��з������ļ�����ʱ�����쳣:"+e.getMessage(), "������ʾ");

		}
	        	}

	    		}).start();
	            logger.info("************����ִ��afterSave***********");
		return null;
	}

	public Object afterSaveAll() {
		// FIXME Auto-generated method stub
		return null;
	}

	public Object beforeClose() {
		// FIXME Auto-generated method stub
		return null;
	}

	public Object beforeOpen() {
		// FIXME Auto-generated method stub
		return null;
	}

	public Object beforeSave() {
		// FIXME Auto-generated method stub
		return null;
	}

	public Object beforeSaveAll() {
		// FIXME Auto-generated method stub
		return null;
	}

	//����Ҫͬ����
	public static AddedOepretorInterface getInstance(){

		if(addInstance == null){
			addInstance =  new AddedOperator();
		}

		return addInstance;
	}




	public Object addRemotDatasource() {

		logger.info("���Զ������Դ��ϢaddRemotDatasource");
		//new Thread(new Runnable(){
			//public void run() {

		IReportConnection myDefaulconn = null;
				try {
					 IreportRmiClient.getInstance();
					 String xmlString = IreportRmiClient.rmiInterfactRemote.getDataSourceList();
					//����.....
					//1:ɾ������Զ������
					 Vector conns = (Vector) MainFrame.getMainInstance().getConnections().clone();
					if(conns != null){
					for (int i = 0; i < conns.size(); i++) {
						IReportConnection irc = (IReportConnection)conns.elementAt(i);
						//��������Զ��������ɾ����������Դ

						if(irc.getName().endsWith(IreportConstant.REMOTE_SUFFIX)){
							MainFrame.getMainInstance().getConnections().removeElement(irc);
							//MainFrame.getMainInstance().getConnections().r
						}
					}
					}
					//2:��������Զ������Դ
					   if(IreportUtil.isBlank(xmlString)){
						   return null;
					   }

				             DOMParser parser = new DOMParser();
				             org.xml.sax.InputSource input_sss  = new org.xml.sax.InputSource(new ByteArrayInputStream(xmlString.getBytes()));
				             parser.parse( input_sss );

				             Document document = parser.getDocument();
				             Node node = document.getDocumentElement();


				             NodeList list_child = node.getChildNodes(); // The root is iReportConnections
				             int dataSourceCount = 0;
				             for (int ck=0; ck< list_child.getLength(); ck++) {
				                 Node connectionNode = (Node)list_child.item(ck);
				                 if (connectionNode.getNodeName() != null && connectionNode.getNodeName().equals("iReportConnection"))
				                 {
				                    // Take the CDATA...
				                        String connectionName = "";
				                        String connectionClass = "";
				                        HashMap hm = new HashMap();
				                        NamedNodeMap nnm = connectionNode.getAttributes();
				                        if ( nnm.getNamedItem("name") != null) connectionName = nnm.getNamedItem("name").getNodeValue();
				                        if ( nnm.getNamedItem("connectionClass") != null) connectionClass = nnm.getNamedItem("connectionClass").getNodeValue();

				                        // Get all connections parameters...
				                        NodeList list_child2 = connectionNode.getChildNodes();
				                        for (int ck2=0; ck2< list_child2.getLength(); ck2++) {
				                            String parameterName = "";
				                            Node child_child = (Node)list_child2.item(ck2);
				                            if (child_child.getNodeType() == Node.ELEMENT_NODE && child_child.getNodeName().equals("connectionParameter")) {

				                                NamedNodeMap nnm2 = child_child.getAttributes();
				                                if ( nnm2.getNamedItem("name") != null)
				                                    parameterName = nnm2.getNamedItem("name").getNodeValue();
				                                hm.put( parameterName,Report.readPCDATA(child_child));
				                            }
				                        }

				                        // ������ִ��ڣ�������Ϊ "name (2)"
				                        //Զ������Դ�ڵ�һ����ȫ��ɾ����Ȼ���ڼ���Զ�̺�׺���ǲ������ظ���
				                        //connectionName = ConnectionsDialog.getAvailableConnectionName(connectionName);
				                        connectionName +=IreportConstant.REMOTE_SUFFIX; //����Զ������Դ��Ҫ��Ӻ�׺��ʶ
				                        try {
				                            IReportConnection con = (IReportConnection) Class.forName(connectionClass).newInstance();
				                            con.loadProperties(hm);
				                            con.setName(connectionName);

				                            MainFrame.getMainInstance().getConnections().add(con);
				                            //����Ĭ��ѡ��
				                            if(IreportConstant.DEFAULT_DATASOURCE_NAME.equals(connectionName)){
				                            	myDefaulconn = con;
				                            	MainFrame.getMainInstance().setActiveConnection(myDefaulconn);
				                            }

				                        } catch (Exception ex) {

				                            JOptionPane.showMessageDialog(MainFrame.getMainInstance(),
				                                I18n.getFormattedString("messages.connectionsDialog.errorLoadingConnection" ,"Error loading  {0}", new Object[]{connectionName}),
				                                I18n.getString("message.title.error","Error"), JOptionPane.ERROR_MESSAGE);
				                        }
				                        dataSourceCount++;
				                }
				             }

				             log("�ɹ����ط���������Դ"+dataSourceCount+"��", JOptionPane.INFORMATION_MESSAGE);
				         } catch (Exception ex)
				         {
				        	 log("����Զ������Դʧ��", JOptionPane.ERROR_MESSAGE);
				        	 /*  JOptionPane.showMessageDialog(MainFrame.getMainInstance(),
				                                I18n.getFormattedString("messages.connectionsDialog.errorLoadingConnections" ,"Error loading connections.\n{0}", new Object[]{ex.getMessage()}),
				                                I18n.getString("message.title.error","Error"), JOptionPane.ERROR_MESSAGE);
				              ex.printStackTrace();*/
				         }
				         MainFrame.getMainInstance().setActiveConnection(myDefaulconn);
				         MainFrame.getMainInstance().saveiReportConfiguration();

			//}

		//}).start();
		logger.info("����addRemotDatasource");
		return null;
	}

	public Object openRemoteFile() {
		logger.info("��ʼopenRemoteFile");
		try {
			String fileName = MyReportProperties.getStringProperties(IreportConstant.REPORT_ID);
			if(IreportUtil.isBlank(fileName)){
			logger.warn(">>>>>>>>>>δ�ҵ�Զ�̷������ļ�");
				return null;
			}
			if(!fileName.toLowerCase().endsWith(".jrxml")){
				fileName+=".jrxml";
			}
			logger.info("=============���Դ�Զ�̷������ļ�"+fileName+"==========");

		    IreportFile ireportFile = null;

	        IreportSession session = new IreportSession();
	        session.setIp(MyReportProperties.getStringProperties(IreportConstant.IP));
	        session.setRepid(fileName.split("\\.")[0]);
	        session.setUsername(MyReportProperties.getStringProperties(IreportConstant.USERNAME));

	        //���ܴ��ڴ򿪵Ķ���ļ�������Ҫ����һ�ݵ����أ��ڱ����ʱ����Ҫ�õ�
	        MyReportProperties.setProperties(IreportConstant.SESSION_SUFFIX+session.getRepid(), session);
	        logger.info("��ʼִ��Զ�̵���open");
	        ireportFile = IreportRmiClient.getInstance().rmiInterfactRemote.open(session,fileName);
	        logger.info("����ִ��Զ�̵���open�����Ϊ��"+ireportFile);
	        if(ireportFile != null){
	        	logger.info("ireportFileΪ��"+ireportFile.getFileName()+"::::"+ireportFile.getContent().length);
				String tmp_file_path = MainFrame.getMainInstance().IREPORT_TMP_FILE_DIR;
				File tmp_file = new File(tmp_file_path);
				if(!tmp_file.exists()){
					tmp_file.mkdirs();
				}
				String path = MainFrame.getMainInstance().IREPORT_TMP_FILE_DIR+"/"+IreportConstant.NAME_SUFFIX+ireportFile.getFileName();
				logger.info("�����ļ��д���ڣ�"+path);
				File oldFile = new File(path);
				if(oldFile.exists()){
					oldFile.delete();
				}

				byte[] content = ireportFile.getContent();
			    File f = IreportUtil.bytesToFile(path, content);
			    log("�ɹ��򿪷������ļ�:"+fileName, JOptionPane.INFORMATION_MESSAGE);
			    return f;
			  }else{
				  logger.warn(">>>>>>>>>>�ӷ������˻�õ��ļ�Ϊ��");
			  }
	        } catch (Exception e) {
	        	logger.error(e);
	        	log("��Զ�̱����ļ�ʧ�ܡ�", JOptionPane.ERROR_MESSAGE);
				e.printStackTrace();
				//DialogFactory.showErrorMessageDialog(null, "��Զ�̱����ļ�ʧ�ܣ�\n��Ϣ:"+e.getMessage() , "����");
	        }
	        logger.info("����openRemoteFile");
		return null;
	}

	//swing ��Ҫע��������ܲ�����չʾʱ��������
	public Object registerSongTi() {
		logger.info("��ʼregisterSongTi");
		try{
		Font font =  new Font("����",Font.PLAIN,12);
    	UIManager.put("Button.font",font);
    	UIManager.put("ToggleButton.font",font);
    	UIManager.put("RadioButton.font",font);
    	UIManager.put("CheckBox.font",font);
    	UIManager.put("ColorChooser.font",font);
    	UIManager.put("ToggleButton.font",font);
    	UIManager.put("ComboBox.font",font);
    	UIManager.put("ComboBoxItem.font",font);
    	UIManager.put("InternalFrame.titleFont",font);
    	UIManager.put("Label.font",font);
    	UIManager.put("List.font",font);
    	UIManager.put("MenuBar.font",font);
    	UIManager.put("Menu.font",font);
    	UIManager.put("MenuItem.font",font);
    	UIManager.put("RadioButtonMenuItem.font",font);
    	UIManager.put("CheckBoxMenuItem.font",font);
    	UIManager.put("PopupMenu.font",font);
    	UIManager.put("OptionPane.font",font);

    	UIManager.put("ProgressBar.font",font);
    	UIManager.put("ScrollPane.font",font);
    	UIManager.put("Viewport",font);
    	UIManager.put("TabbedPane.font",font);
    	UIManager.put("TableHeader.font",font);
    	UIManager.put("Table.font",font);
    	UIManager.put("TextField.font",font);
    	UIManager.put("PasswordFiled.font",font);
    	UIManager.put("TextArea.font",font);
    	UIManager.put("TextPane.font",font);
    	UIManager.put("EditorPane.font",font);
    	UIManager.put("TitledBorder.font",font);
    	UIManager.put("ToolBar.font",font);
    	UIManager.put("ToolTip.font",font);
    	UIManager.put("Tree.font",font);

    	UIManager.put("ComboBox", font);
    	UIManager.put("ComboBox.font", font);
    	UIManager.put("JComboBox.font", font);
    	UIManager.put("JComboBox", font);
    	UIManager.put("JTextField", font);
    	} catch (Exception e) {
    		logger.error(e);
			e.printStackTrace();
		}
    	logger.info("����registerSongTi");
		return null;
	}


	public Object login(String serverApp, String username, String password) {
		if(IreportUtil.isBlank(serverApp) || IreportUtil.isBlank(username) || IreportUtil.isBlank(password)){
			return "��Ҫ��Ϣδ��д����";
		}

		//��ʼlogin

		return IreportConstant.SUCCESS;
	}

	public Object logout() {

		return null;
	}


	public Object linkCheck() {
		// FIXME Auto-generated method stub
		return null;
	}

	public Object initTemplate() {
		logger.info("��ʼinitTemplate");
			Thread initT = new Thread(new Runnable(){
				public void run() {
					try {
					System.out.println("��ʼ����ģ���ļ��У�"+MainFrame.getMainInstance().IREPORT_TMP_TEMPLATE_DIR);
					File tmplateDir = new File(MainFrame.getMainInstance().IREPORT_TMP_TEMPLATE_DIR);
					if(!tmplateDir.exists()){
						tmplateDir.mkdirs();
					}
					File[] fs = tmplateDir.listFiles();
					if(fs==null || fs.length==0){

						System.out.println("��ʼ�ӷ�������ȡģ���ļ�...");
						//ɾ������ģ���ļ�
						for (int i = 0; i < fs.length; i++) {
							if(fs[i].isFile()){
								fs[i].delete();
							}
						}
						IreportRmiClient.getInstance();
						List<IreportFile> templates = IreportRmiClient.getRmiRemoteInterface().getAllTemplates();

						for (int i = 0; i < templates.size(); i++) {
							IreportUtil.bytesToFile(tmplateDir+"/"+templates.get(i).getFileName(), templates.get(i).getContent());
						}
						if(templates.size()==0){
							AddedOperator.log("δ�ҵ�ģ����Ϣ.", IreportConstant.WARN_);
						}else{
						AddedOperator.log("����ģ�����.", IreportConstant.INFO_);
						}
					}else{
						System.out.println("̽�⵽�����Ѿ�����ģ���ļ�������Ҫͬ��������ģ����Ϣ����Ҫ�ֶ�������");
					}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			initT.setDaemon(true);
			initT.start();

		logger.info("����initTemplate");
		return null;
	}

	public Object initRemoteArgs(String[] args) {
		logger.info("��ʼinitRemoteArgs");

		System.out.println("args..............."+args.length);
		if(args!=null && args.length>0){
			for (int i = 0; i < args.length; i++) {
				System.out.println("********����"+(i+1)+":["+args[i]+"]********");
				switch (i) {
				case 0: //��һ����Ϊrmi_ip
					MyReportProperties.setProperties(IreportConstant.RMI_IP, args[i]);
					break;
				case 1: //��2����Ϊrmi_port
					MyReportProperties.setProperties(IreportConstant.RMI_PORT, args[i]);
					break;
				case 2: //��3����Ϊreport_id
					MyReportProperties.setProperties(IreportConstant.REPORT_ID, args[i]);
					break;

				case 3: //��4����Ϊusername
					MyReportProperties.setProperties(IreportConstant.USERNAME, args[i]);
					break;
				case 4: //��5����Ϊip
					if(args[i]==null || "127.0.0.1".equals(args[i])){
						MyReportProperties.setProperties(IreportConstant.IP, IreportUtil.getLocalIp());
					}else{
						MyReportProperties.setProperties(IreportConstant.IP, args[i]);
					}
					break;
				}

		}
			}
		logger.info("����initRemoteArgs");
		return null;
	}

	//�����������ļ�ϵͳ������������
	public Object initPluginsConfig() {
		logger.info("��ʼinitPluginsConfig");
		try {
			File plugDir = new File(MainFrame.getMainInstance().IREPORT_PLUGIN_DIR);
			if (plugDir == null || !plugDir.exists() || plugDir.isFile()) {
				//û���ҵ������ļ�Ŀ¼������Ŀ¼
				log("δ�ҵ�����ļ��У�"+plugDir.getPath()+"���������ļ���",IreportConstant.INFO_);
				System.out.println("δ�ҵ�����ļ��У�"+plugDir.getPath()+"���������ļ���");
				plugDir.mkdirs();
			}
			if(plugDir.listFiles()!=null && plugDir.listFiles().length==0){
				logger.info("�ӷ��������ز���ļ�...");
				//���Դӷ������˼���
				IreportRmiClient.getInstance();
				List<IreportFile>  fs = IreportRmiClient.rmiInterfactRemote.getAllPlugins();
		        if(fs==null){
		        	return null;
		        }
				for (int i = 0; i < fs.size(); i++) {
		        	IreportFile irf= fs.get(i);
		        	File f = IreportUtil.bytesToFile(MainFrame.getMainInstance().IREPORT_PLUGIN_DIR+"/"+irf.getFileName(), irf.getContent());
					f.mkdir();
					System.out.println("�ɹ��������ò���ļ�:"+f.getPath());
		        }
		        log("�ɹ��������ò���ļ�"+fs.size()+"��", IreportConstant.INFO_);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("����initPluginsConfig");
		return null;
	}

	public Object beforeIreportLoadCheck() {
		logger.info("��ʼbeforeIreportLoadCheck");
		boolean b = false;
		ServerSocket ss =null;
		try {
			 ss = new ServerSocket(Integer.parseInt(IreportConstant.CLIENT_RMI_PORT));
		} catch (Exception e) {
			//�����쳣��ʾ�˿ڱ�ռ�ã��ͻ��˽���ֹ�˳�
			DialogFactory.showErrorMessageDialog(null, "�˿�"+IreportConstant.CLIENT_RMI_PORT+"�Ѿ���ռ�ã����ǲ����Ѿ�������һ��ireportʵ��?", "����");
			b = true;
			e.printStackTrace();
		}finally{
			if(ss!=null){
				try {ss.close();} catch (IOException e) {}
			}
		}

		if(b){
			System.out.println("�˿ڱ�ռ�ã�ϵͳ�˳���");
			System.exit(0);
		}
		logger.info("����beforeIreportLoadCheck");
		return null;
	}

	public Object beforeCloseApplication(JInternalFrame[] jif) {
		logger.info("��ʼbeforeCloseApplication");
		if(jif == null){
			return null;
		}
		JReportFrame jf = null;
		boolean isError = false;
		String realId = null;
		String repid = null;

		for (int i = 0; i < jif.length; i++) {
			if(jif[i]!=null && jif[i] instanceof JReportFrame){

				jf = (JReportFrame) jif[i];
				String path = jf.getReport().getFilename();

				 repid = IreportUtil.getIdFromReportPath(path);

				if(IreportUtil.isRemoteFile(repid)){
				try {

					 String isLocal = MyReportProperties.getStringProperties(repid+IreportConstant.LOCAL_TO_SERVER);
					 if(!IreportUtil.isBlank(isLocal)){
						 realId = isLocal;
					 }else{
						 realId = repid;
					 }

					 String id = IreportRmiClient.rmiInterfactRemote.unLockReport(IreportUtil.getLocalIp(),IreportUtil.removeNameSuffix(realId));
					 IreportUtil.removeLocalToServerLock(repid,true);

					 if(IreportUtil.isBlank(id)){isError = true;}
				} catch (Exception e) {
					e.printStackTrace();
					isError = true;
				}
				}

			}
			if(isError){
				log("���["+repid+"]��������!",IreportConstant.ERROR_);
			}else{
				log("�Ѿ����"+repid+".jrxml�ļ��ڷ������˵�����", IreportConstant.RIGHT_);
			}
		}

		if(isError){
			DialogFactory.showErrorMessageDialog(null, "��������ļ�����!", "����");

		}
		logger.info("����beforeCloseApplication");
		return null;
	}

	public Object afterCloseJReportFrame(final JReportFrame jrf) {
		logger.info("��ʼafterCloseJReportFrame");
		//new Thread( new Runnable(){
			//public void run() {
				String errMsg = "";
				 String realId = null;
				if(jrf == null ){return null;}
				String path  = jrf.getReport().getFilename();
				String repid = IreportUtil.getIdFromReportPath(path);
				boolean isError = false;
				if(IreportUtil.isRemoteFile(repid)){
					try {

						 String isLocal = MyReportProperties.getStringProperties(repid+IreportConstant.LOCAL_TO_SERVER);
						 if(!IreportUtil.isBlank(isLocal)){
							 realId = isLocal;
						 }else{
							 realId = repid;
						 }
						 String id = IreportRmiClient.rmiInterfactRemote.unLockReport(IreportUtil.getLocalIp(),IreportUtil.removeNameSuffix(realId));
						 IreportUtil.removeLocalToServerLock(realId, true);
						 if(IreportUtil.isBlank(id)){isError = true;}
					} catch (Exception e) {
						errMsg = e.getMessage();
						e.printStackTrace();
						isError = true;
					}
					}
				if(isError){
					log("���["+repid+"]��������!\n"+errMsg,IreportConstant.ERROR_);
					DialogFactory.showErrorMessageDialog(null, "���["+repid+"]��������!\n"+errMsg, "����");

				}else{
					if(IreportUtil.isRemoteFile(repid)){
					log("�Ѿ����"+repid+".jrxml�ļ��ڷ������˵�����", IreportConstant.RIGHT_);
					}
				}
		//	}

		//}).start();
		logger.info("����afterCloseJReportFrame");
		return null;
	}

	public static void log(String text,int type){
		if(type==JOptionPane.WARNING_MESSAGE){
			type = IreportConstant.WARN_;
		}else if(type == JOptionPane.ERROR_MESSAGE){
			type = IreportConstant.ERROR_;
		}
		MainFrame.getMainInstance().getLogPane().getMainLogTextArea().logOnConsole(text, type);
	}

	public Object initLibJarFiles() {
		new Thread(new Runnable(){

			public void run() {
				try {
					String lib = MainFrame.IREPORT_USER_HOME_DIR+File.separator+"lib";
					File libDir = new File(lib);
					if(!libDir.exists()){
						libDir.mkdirs();
					}
					if(libDir!=null && libDir.isDirectory() && libDir.listFiles().length!=0){
						AddedOperator.log("̽�⵽�����Ѿ�����jar�ļ���������jar����������ͬ��", IreportConstant.INFO_);
						return;
					}
					IreportRmiClient.getInstance();
					List<IreportFile> list = (List<IreportFile>) IreportRmiClient.getRmiRemoteInterface().synchronizationLibJar();

					for (int i = 0; i < list.size(); i++) {
						File f = new File(lib,list.get(i).getFileName());
						if(!f.exists()){
						IreportUtil.bytesToFile(f.getPath(), list.get(i).getContent());
						AddedOperator.log("ͬ��������jar��"+list.get(i).getFileName()+"������Ŀ¼"+lib, IreportConstant.INFO_);
						}
					}
				} catch (Exception e) {

					e.printStackTrace();
					AddedOperator.log("ͬ��������libĿ¼�ļ�����\n"+e.getMessage()+"\n"+e.getLocalizedMessage()+"\n"+e.getCause(),IreportConstant.ERROR_);
				}

			}

		}).start();

		return null;
	}
}

//end AddedOperator.java