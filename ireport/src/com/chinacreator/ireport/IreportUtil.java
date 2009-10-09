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

import it.businesslogic.ireport.Report;
import it.businesslogic.ireport.gui.JReportFrame;
import it.businesslogic.ireport.gui.MainFrame;
import it.businesslogic.ireport.gui.TemplateWizardDialog;
import it.businesslogic.ireport.plugin.templatemanager.TemplatesFrame;
import it.businesslogic.ireport.util.PageSize;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JComboBox;
import javax.swing.JInternalFrame;

import com.chinacreator.ireport.rmi.IreportRmiClient;
import com.chinacreator.ireport.rmi.PageInfo;
import com.chinacreator.ireport.rmi.ReportLock;
import com.chinacreator.ireport.rmi.TemplateFiles;

/**
 * @author ��ï
 * @since 3.0
 * @version $Id: IreportUtil.java 2009-8-20 ����04:42:59 $
 * 
 */
// begin IreportUtil.java
public class IreportUtil {
	private static Pattern p = Pattern.compile("_\\d{20}");
	private static Pattern p1 = Pattern.compile("\\d{1}");

	/**
	 * ���ֽ���ת��Ϊ�ļ�
	 * 
	 * @param filePath
	 *            �ļ�ȫ·��
	 * @param content
	 *            �ļ������ֽ���
	 * @return
	 */
	public static File bytesToFile(String filePath, byte[] content) {
		try {
			if (isBlank(filePath)) {
				return null;
			}
			BufferedOutputStream output = null;
			File file = new File(filePath);
			if (!file.exists()) {
				file.createNewFile();
			}
			output = new BufferedOutputStream(new FileOutputStream(file));
			output.write(content);
			if (output != null) {
				try {
					output.close();
					output = null;
				} catch (Exception ex) {
				}
			}
			return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * ���ļ�����ת��Ϊ�ֽ���
	 * 
	 * @param filePath
	 *            �ļ�λ��ȫ·��
	 * @return
	 */
	public static byte[] fileToBytes(String filePath) {
		try {
			File f = new File(filePath);
			if (!f.exists()) {
				return null;
			}
			byte[] content = new byte[(int) f.length()];
			BufferedInputStream input = new BufferedInputStream(
					new FileInputStream(f));
			input.read(content);
			if (input != null) {
				try {
					input.close();
					input = null;
				} catch (Exception ex) {
				}
			}
			return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * �ж��ַ����Ƿ�Ϊ�մ���null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isBlank(String str) {
		return str == null ? true : str.trim().equals("");
	}

	/**
	 * �ж϶����Ƿ�Ϊnull
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * �ж϶����Ƿ�ΪԶ���ļ����ͣ�����Զ���ļ�����Ϊ��_��ʼ�ټ�20λ���֡�����
	 * MyReportProperties.getStringProperties(fileName+IreportConstant.LOCAL_TO_SERVER)
	 * ��Ϊ��
	 * 
	 * @see MyReportProperties#getStringProperties(String)
	 * @param fileName
	 * @return
	 */
	public static boolean isRemoteFile(String fileName) {

		if (IreportUtil.isBlank(fileName)) {
			return false;
		}

		if (fileName.toLowerCase().endsWith(".jrxml")) {
			fileName = fileName.split("\\.")[0];
		}

		String isLocal = MyReportProperties.getStringProperties(fileName
				+ IreportConstant.LOCAL_TO_SERVER);
		if (!isBlank(isLocal)) {
			return true;
		}
		Matcher m = p.matcher(fileName);
		boolean b = m.matches();
		return b;

	}

	public static String getIdFromReport(String reportName) {
		if (isBlank(reportName)) {
			return "";
		}

		if (reportName.toLowerCase().endsWith(".jrxml")) {
			return reportName.split("\\.")[0];
		}

		return reportName;
	}

	public static String getIdFromReportPath(String reportPath) {
		String reportName = new File(reportPath).getName();
		if (isBlank(reportName)) {
			return "";
		}
		if (reportName.toLowerCase().endsWith(".jrxml")) {
			return reportName.split("\\.")[0];
		}
		return reportName;
	}

	public static String getLocalIp() {
		try {
			InetAddress ia = InetAddress.getLocalHost();
			String ip = ia.getHostAddress();
			return ip;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	// �ǲ����㵱ǰ�������ļ�¼
	// �ڱ��ֵ�ʱ�����ж�
	public static boolean isYourLock(ReportLock r) {

		if (r == null) {
			return false;
		}
		System.out.println("RIP:" + r.getOpen_user_ip());
		if (getLocalIp().equals(r.getOpen_user_ip())
				&& r.getStatues().equals("Y")) {
			return true;
		}
		return false;
	}

	public static ReportLock getReportLock(String repid) {

		ReportLock r = new ReportLock();
		r.setLock_id(IreportUtil.getPrimaryKey());
		r.setRep_id(repid);
		r.setOpen_time(new Date());
		r.setOpen_user(MyReportProperties
				.getStringProperties(IreportConstant.USERNAME));
		r.setRep_type(IreportConstant.REPORT_TYPE_JASPERREPORT);
		r.setStatues(IreportConstant.LOCK);// �򿪼�����
		r.setRep_ref_file("");
		r.setTime_out_time(IreportUtil.addDate(new Date(),
				IreportConstant.DEFAULT_LOCK_LIMIT_HOURE));
		r.setOpen_user_ip(getLocalIp());
		r.setRep_name(repid + ".jrxml");
		return r;
	}

	public static String getPrimaryKey() {
		return System.currentTimeMillis() + "";
	}

	public static String dateFormat(String format, Date date) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		return sf.format(date);
	}

	public static String defaultDateFormat(Date date) {
		if (date == null) {
			return null;
		}
		return dateFormat("yyyy-MM-dd HH:mm:ss", date);

	}

	public static Date addDate(Date date, int hours) {
		Calendar c = Calendar.getInstance();
		if (date == null) {
			c.setTime(new Date());
		} else {
			c.setTime(date);
		}

		c.add(Calendar.HOUR_OF_DAY, hours);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * �ǲ��Ǵӱ����½������������ļ�
	 * 
	 * @param saveFilePath
	 * @return
	 */

	public static String isLocalNewToServerFile(String saveFilePath) {
		if (isBlank(saveFilePath)) {
			return null;
		}
		String key = getIdFromReportPath(saveFilePath)
				+ IreportConstant.LOCAL_TO_SERVER;
		return MyReportProperties.getStringProperties(key);
	}

	/**
	 * �Ƴ������ڴ������ֵĶ�ĳ�½����ļ������ı��
	 * 
	 * @param pathOrName
	 * @param isName
	 */
	public static void removeLocalToServerLock(String pathOrName, boolean isName) {
		String key = null;
		if (isName) {// �ļ�
			key = getIdFromReport(pathOrName) + IreportConstant.LOCAL_TO_SERVER;
		} else {// ·��
			key = getIdFromReportPath(pathOrName)
					+ IreportConstant.LOCAL_TO_SERVER;
		}
		MyReportProperties.removeProperties(key);
	}

	/**
	 * ֻҪ������������trueʱ���ܼ����½�����
	 * 
	 * @param reportName
	 * @return
	 */
	public static boolean isAllowedNewReport(String reportName) {
		MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT, "");
		String error = "";
		if (isBlank(reportName)) {
			error = "�ļ�������Ϊ��";
			MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT,
					error);
			return false;
		}
		if (isRemoteFile(reportName)) {
			error = "�ļ������Ϸ�������ʹ��Զ���ļ��������½�";
			MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT,
					error);
			return false;
		}

		Matcher m = p1.matcher(reportName.substring(0, 1));
		boolean b = m.matches();
		if (b) {
			error = "�ļ������������ֿ�ʼ";
			MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT,
					error);
			return !b;
		}

		JInternalFrame[] intfs = MainFrame.getMainInstance()
				.getJMDIDesktopPane().getAllFrames();

		for (int i = 0; i < intfs.length; i++) {
			if (intfs != null && intfs[i] instanceof JReportFrame) {
				JReportFrame jf = (JReportFrame) intfs[i];
				if (jf.getReport().getFilename() == null
						|| jf.getReport().getFilename().trim().equals("")) {
					continue;// �ļ����½��ģ���δ���б���
				}
				String reportname = jf.getReport().getName();
				if (reportname.equals(reportName)) {
					error = "�ļ������Ѿ��򿪵��ļ��д���";
					MyReportProperties.setProperties(
							IreportConstant.NEW_FILE_LIMIT, error);
					return false;
				}
			}
		}

		return true;
	}

	public static String removeNameSuffix(String name) {
		if (isBlank(name)) {
			return "";
		}
		if (name.indexOf("_") != -1) {
			name = name.substring(1);
		}
		return name;
	}

	public static String getTemplateType(String tName) {
		/*
		 * String tmpTemplate = MainFrame.IREPORT_TMP_TEMPLATE_DIR;
		 * System.out.println(tmpTemplate + File.separator+tName+"C.xml"); File
		 * cf = new File(tmpTemplate + File.separator+tName+"C.xml"); File tf =
		 * new File(tmpTemplate + File.separator+tName+"T.xml");
		 */
		if (isBlank(tName)) {
			return null;
		}
		if (tName.endsWith("C")) {
			return IreportConstant.TEMPLATE_C;
		}

		if (tName.endsWith("T")) {
			return IreportConstant.TEMPLATE_T;
		}
		return null;
	}

	public static Object saveTemplatesFile(TemplateFiles tf, boolean editor) {
		File xf = null;
		File mf = null;

		String tpath = MainFrame.IREPORT_TMP_TEMPLATE_DIR + File.separator;
		try {
			if (tf == null) {
				throw new RuntimeException("�ϴ�ģ����Ϣ�ļ�Ϊ��");
			}

			String xmlPath = tpath + tf.getName() + ".xml";
			String imgPath = tpath + tf.getName() + ".png";

			xf = new File(xmlPath);
			mf = new File(imgPath);

			if ((!editor) && (xf.exists() || mf.exists())) {
				throw new RuntimeException("ģ�����Ѿ�����");
			}
			if (editor) {
				String xp = tpath + tf.getOldName() + ".xml";

				System.out.println(xp);
				if (!new File(xp).exists()) {
					throw new RuntimeException("����Ҫ�޸ĵ�ģ��[" + tf.getOldName()
							+ "]�ڿͻ��˲�����");
				}
				// �Ƿ��޸����ļ�
				if (tf.getXmlContent() != null && tf.getXmlContent().length > 0) {
					IreportUtil.bytesToFile(xmlPath, tf.getXmlContent());

					if (!tf.getName().equals(tf.getOldName())) {
						String oldXml = tpath + tf.getOldName() + ".xml";
						File oldXmlFile = new File(oldXml);
						if (oldXmlFile != null && oldXmlFile.exists()) {
							oldXmlFile.delete();
						}
					}
				}

				// �Ƿ��޸���ͼƬ
				if (tf.getImgContent() != null && tf.getImgContent().length > 0) {
					IreportUtil.bytesToFile(imgPath, tf.getImgContent());

					if (!tf.getName().equals(tf.getOldName())) {
						String imgP = tpath + tf.getOldName() + ".png";
						File oldImgFile = new File(imgP);

						if (oldImgFile != null && oldImgFile.exists()) {
							oldImgFile.delete();
						}
					}
				}

				// �Ƿ񴴽������ļ�
				if (tf.getName().equals(tf.getOldName())) {
					// δ�ı�����
				} else {
					// �ı�������
					String oldXml = tpath + tf.getOldName() + ".xml";
					String imgP = tpath + tf.getOldName() + ".png";
					File oldXmlFile = new File(oldXml);
					File oldImgFile = new File(imgP);

					if (oldXmlFile != null && oldXmlFile.exists()) {
						oldXmlFile.renameTo(new File(tpath + tf.getName()
								+ ".xml"));
					}

					if (oldImgFile != null && oldImgFile.exists()) {
						oldImgFile.renameTo(new File(tpath + tf.getName()
								+ ".png"));
					}

				}

			} else {
				// ģ���ļ��������
				if (tf.getXmlContent() == null
						|| tf.getXmlContent().length <= 0) {
					throw new RuntimeException("ģ���ļ����ݲ�����");
				}
				IreportUtil.bytesToFile(xmlPath, tf.getXmlContent());

				// Ԥ��ͼƬ���Բ�����
				if (tf.getImgContent() != null && tf.getImgContent().length > 0) {
					IreportUtil.bytesToFile(imgPath, tf.getImgContent());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			// ���޸Ĳ�����
			if (!editor) {
				if (xf != null && xf.exists()) {
					xf.delete();
				}
				if (mf != null && mf.exists()) {
					mf.delete();
				}
			}
			throw new RuntimeException(e.getMessage());
		}

		return null;
	}

	public static void throwRuntimeException(String msg) {
		throw new RuntimeException(msg);
	}

	public static void deleteFileIfExsit(File f) {
		if (f == null) {
			return;
		}
		if (f.exists()) {
			f.delete();
		}
	}

	/**
	 * ɾ��ģ���ļ�
	 * 
	 * @param templateName
	 */
	public static void deleteTemplate(String templateName) {
		if (IreportUtil.isBlank(templateName)) {
			throwRuntimeException("ģ���ļ���Ϊ��");
		}

		String tpath = MainFrame.IREPORT_TMP_TEMPLATE_DIR + File.separator;
		File xmlFile = new File(tpath + templateName + ".xml");
		File imgFile = new File(tpath + templateName + ".png");

		deleteFileIfExsit(xmlFile);
		deleteFileIfExsit(imgFile);

	}

	/**
	 * ��һ��ģ���ļ�����������
	 * 
	 * @param destFile
	 *            �ļ�����
	 * @param tmplateName
	 *            ģ���ļ���
	 * @return
	 */
	public static boolean runWizard(String destFile, String templateName) {
		MainFrame mainFrame = MainFrame.getMainInstance();

		if (mainFrame == null)
			return false;

		try {

			TemplateWizardDialog wd = new TemplateWizardDialog(mainFrame, true,
					templateName);
			wd.setVisible(true);
			wd.requestFocus();

			Report report = null;
			if (wd.getDialogResult() == javax.swing.JOptionPane.OK_OPTION) {
				report = wd.getReport();

				System.out.println(report);
				if (report == null) {
					report = createBlankReport();
				}
			} else {
				return false;
				// report = createBlankReport();
			}

			if (report != null) {
				mainFrame.openNewReportWindow(report);
				destFile = wd.getYouSetName();
				report.setFilename(MainFrame.IREPORT_TMP_FILE_DIR
						+ File.separator + destFile + ".jrxml");
				report.saveXMLFile();
				setVisible(true);
			}
			AddedOperator.log("����ģ���򵼳ɹ����ɱ����ļ�" + destFile + ".jrxml",
					IreportConstant.RIGHT_);
		} catch (Exception ex) {
			System.out.println(ex.getMessage());
			ex.printStackTrace();
		}

		return true;
	}

	private static Report createBlankReport() {
		Report newReport = new Report();

		newReport.setName(it.businesslogic.ireport.util.I18n.getString(
				"untitledReport", "untitled_report_")
				+ "1");
		newReport.setUsingMultiLineExpressions(false); // this.isUsingMultiLineExpressions());
		newReport.setWidth(PageSize.A4.x);
		newReport.setHeight(PageSize.A4.y);
		newReport.setTopMargin(20);
		newReport.setLeftMargin(30);
		newReport.setRightMargin(30);
		newReport.setBottomMargin(20);
		newReport.setColumnCount(1);
		newReport.setColumnWidth(newReport.getWidth()
				- newReport.getLeftMargin() - newReport.getRightMargin());
		newReport.setColumnSpacing(0);

		return newReport;
	}

	private static boolean setVisible(boolean b) {
		MainFrame.getMainInstance().setVisible(b);
		if (MainFrame.getMainInstance().getState() == java.awt.Frame.ICONIFIED) {
			MainFrame.getMainInstance().setState(java.awt.Frame.NORMAL);
		}
		return MainFrame.getMainInstance().requestFocusInWindow();
	}

	/**
	 * �򿪡�ģ���������
	 */
	public static void reShowTemplateFrame() {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				final TemplatesFrame dialog = new TemplatesFrame(MainFrame
						.getMainInstance(), true);
				dialog.addWindowListener(new java.awt.event.WindowAdapter() {
					public void windowClosing(java.awt.event.WindowEvent e) {
						dialog.setVisible(false);
						dialog.dispose();
					}
				});
				dialog.setVisible(true);

			}

		});
	}

	/**
	 * ����Ѿ��򿪵�����ģ���ļ�������Ϊxml�ļ���׺
	 * 
	 * @return
	 */
	public static List<ComboxObject> getAllOpenedTemplate() {
		JComboBox j = null;
		JInternalFrame[] jif = MainFrame.getMainInstance().getJMDIDesktopPane()
				.getAllFrames();
		JReportFrame jf = null;
		List<ComboxObject> list = new ArrayList<ComboxObject>();

		for (int i = 0; i < jif.length; i++) {
			if (jif[i] != null && jif[i] instanceof JReportFrame) {
				jf = (JReportFrame) jif[i];
				String path = jf.getReport().getFilename();
				if (isBlank(path)) {
					continue;
				}
				File f = new File(path);
				if (f != null && f.getPath().toLowerCase().endsWith(".xml")) {
					list.add(new ComboxObject(f, delFileSuffix(f.getName())));
				}

			}

		}

		return list;
	}

	/**
	 * ɾ���ļ�����׺
	 * 
	 * @param filename
	 * @return
	 */
	public static String delFileSuffix(String filename) {
		if (isBlank(filename)) {
			return "";
		}

		if (filename.indexOf(".") == -1) {
			return filename;
		}

		return filename.substring(0, filename.lastIndexOf("."));
	}

	public static PageInfo getReportLockList(String repid,
			int pageIndex, int pageSize) {

		try {
			return (PageInfo) IreportRmiClient.getInstance()
					.getRmiRemoteInterface().invokeServerMethod(
							IreportConstant.FIND_REPORT_LOCK_LIST, repid,
							pageIndex, pageSize);
		} catch (RemoteException e){
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * ��һ������list���Ϊ��ά�������� 
	 * @param list
	 * @return
	 */
	public static Object[][] ReportLockToObjectArray(List<ReportLock> list){
		if(list!=null && list.size()>0){
			Object[][] date = new Object[list.size()][6];
			for (int i = 0; i < list.size(); i++) {
				date[i][0] =  false;
				date[i][1] =  list.get(i).getRep_id();
				date[i][2] =  list.get(i).getRep_name();
				date[i][3] =  list.get(i).getOpen_user();
				date[i][4] =  list.get(i).getOpen_user_ip();
				date[i][5] =  defaultDateFormat(list.get(i).getOpen_time());			
				}
			return date;
		}
		return null;
	}
	
	
	/**
	 * ִ��dos����
	 * @param command
	 */
	public static void executeDosCommand(String command){
		try {
			Runtime.getRuntime().exec(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * ɾ���ļ�����ȫ���ļ�
	 * @return ʧ���ļ����󼯺�
	 */
	public static List<File> deleteAllFileByFolder(String folderPath){
		List<File> list = new ArrayList<File>();
		File f = new File(folderPath);
		if(f.exists() && f.isDirectory()){
			File[] fs = f.listFiles();
			for (int i = 0; i < fs.length; i++) {
				try {
					boolean b = fs[i].delete();
					if(!b){
						list.add(fs[i]);
					}
				} catch (Exception e) {
					e.printStackTrace();
					list.add(fs[i]);
				}
			}
		}
		return list;	
	}
	
	/**
	 * �÷���ֻ��������ͬ���ļ����ļ�ʱ�õ�
	 * @param cilentDir
	 * @param files
	 * @throws Exception
	 */
	public static void increamentHelper(String cilentDir,File[] files) throws Exception{
		
		if(files!=null){
			
			for (int i = 0; i < files.length; i++) {
				File thisFile = new File(cilentDir+files[i].getName());
				if( !thisFile.exists() ){
					//��������Ҫͬ��
					Object fileObj = IreportRmiClient.rmiInterfactRemote.sendFileToClient(files[i].getPath());
					byte[] bytes = (byte[])fileObj;
					if(bytes == null){
						continue;
					}
					
					IreportUtil.bytesToFile(thisFile.getPath(), bytes);
					AddedOperator.log("--->ͬ��:"+thisFile.getPath(), IreportConstant.RIGHT_);
				}else{
				    //�����ڣ�����ʱ�����һ����ͬ��ͬ��
					//1:��������ʱ������ڿͻ���
					if(files[i].lastModified() > thisFile.lastModified()){
						//��Ҫͬ��
						String savePath = thisFile.getPath();
						Object fileObj = IreportRmiClient.rmiInterfactRemote.sendFileToClient(files[i].getPath());
						byte[] bytes = (byte[])fileObj;
						if(bytes == null){
							continue;
						}
						thisFile.delete();
						IreportUtil.bytesToFile(savePath, bytes);
						AddedOperator.log("--->ͬ��:"+thisFile.getPath(), IreportConstant.RIGHT_);
					}
					
					//2:���Ƿ��޸��ˣ�
					if(files[i].lastModified() < thisFile.lastModified()){
						//�ͻ��������Լ��޸�?
						//��Ҫͬ��
						Object fileObj = IreportRmiClient.rmiInterfactRemote.sendFileToClient(files[i].getPath());
						//���ͻ����Լ������޸ģ�Ҳ��������ԭ���������ﲻֱ��ɾ�����Ǳ���
						thisFile.renameTo(new File(thisFile.getPath()+".bak")); 
						byte[] bytes = (byte[])fileObj;
						if(bytes == null){
							continue;
						}
						IreportUtil.bytesToFile(thisFile.getPath(), bytes);
						AddedOperator.log("--->ͬ��[����]:"+thisFile.getPath()+" ����:"+thisFile.getPath()+".bak", IreportConstant.RIGHT_);
					}
					
				}
			}
		}
	}
	
}

// end IreportUtil.java
