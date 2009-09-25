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

import it.businesslogic.ireport.gui.JReportFrame;
import it.businesslogic.ireport.gui.MainFrame;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.InetAddress;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JInternalFrame;

import com.chinacreator.ireport.rmi.ReportLock;
import com.chinacreator.ireport.rmi.TemplateFiles;


/**
 * @author ��ï
 * @since 3.0
 * @version $Id: IreportUtil.java 2009-8-20 ����04:42:59 $
 *
 */
//begin IreportUtil.java
public class IreportUtil {
	private static Pattern  p = Pattern.compile("_\\d{20}");
	private static Pattern  p1 = Pattern.compile("\\d{1}");
	public static File bytesToFile(String filePath,byte[] content){
		try {
			if(isBlank(filePath)){
				return null;
			}
			 BufferedOutputStream output = null ;
	         File file = new File(filePath);
	         if(!file.exists()){
	             file.createNewFile();
	         }
	         //save the content to the file
	         output = new BufferedOutputStream(new FileOutputStream(file));
	         output.write(content);
	         if(output != null ){
	              try{
	                  output.close();
	                  output = null ;
	              }catch(Exception ex){
	              }
	           }
	         return file;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}


	}

	public static byte[] fileToBytes(String filePath){
		try {
			 File f = new File(filePath);
			  if(!f.exists()){
				  return null;
			  }
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
	          return content;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}


	}
	public static boolean isBlank(String str){
		return str==null?true:str.trim().equals("");
	}

	public static boolean isNull(Object obj){
		return obj==null;
	}

	public static boolean isRemoteFile(String fileName){


		if(IreportUtil.isBlank(fileName)){
			return false;
		}



		if(fileName.toLowerCase().endsWith(".jrxml")){
			fileName = fileName.split("\\.")[0];
		}

		 String isLocal = MyReportProperties.getStringProperties(fileName+IreportConstant.LOCAL_TO_SERVER);
		 if(!isBlank(isLocal)){
			 return true;
		 }
		 Matcher m = p.matcher(fileName);
		 boolean b = m.matches();
		 return b;

	}

	public static String getIdFromReport(String reportName){
		if(isBlank(reportName)){
			return "";
		}

		if(reportName.toLowerCase().endsWith(".jrxml")){
			return reportName.split("\\.")[0];
		}

		return reportName;
	}

	public static String getIdFromReportPath(String reportPath){
		String reportName = new File(reportPath).getName();
		if(isBlank(reportName)){
			return "";
		}
		if(reportName.toLowerCase().endsWith(".jrxml")){
			return reportName.split("\\.")[0];
		}
		return reportName;
	}

	public static String getLocalIp(){
		try {
			InetAddress ia=InetAddress.getLocalHost();
		    String ip=ia.getHostAddress();
		    return ip;
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}

	}

	//�ǲ����㵱ǰ�������ļ�¼
	//�ڱ��ֵ�ʱ�����ж�
	public static boolean isYourLock(ReportLock r){

		if(r==null){
			return false;
		}
		System.out.println("RIP:"+r.getOpen_user_ip());
		if(getLocalIp().equals(r.getOpen_user_ip()) && r.getStatues().equals("Y")){
			return true;
		}
		return false;
	}

	public static ReportLock getReportLock(String repid){

		   ReportLock r = new ReportLock();
	       r.setLock_id(IreportUtil.getPrimaryKey());
	       r.setRep_id(repid);
	       r.setOpen_time(new Date());
	       r.setOpen_user(MyReportProperties.getStringProperties(IreportConstant.USERNAME));
	       r.setRep_type(IreportConstant.REPORT_TYPE_JASPERREPORT);
	       r.setStatues(IreportConstant.LOCK);//�򿪼�����
	       r.setRep_ref_file("");
	       r.setTime_out_time(IreportUtil.addDate(new Date(),IreportConstant.DEFAULT_LOCK_LIMIT_HOURE));
	       r.setOpen_user_ip(getLocalIp());
	       r.setRep_name(repid+".jrxml");
	       return r;
	}

	public static String getPrimaryKey(){
		return System.currentTimeMillis()+"";
	}

	public static String dateFormat(String format,Date date){
		SimpleDateFormat sf=new SimpleDateFormat(format);
		return sf.format(date);
	}

	public static String defaultDateFormat(Date date){
		if(date==null){
			return null;
		}
		return dateFormat("yyyy-MM-dd HH:mm:ss",date);

	}
	public static Date addDate(Date date,int hours){
		Calendar c = Calendar.getInstance();
		if(date == null){
			c.setTime(new Date());
		}else{
			c.setTime(date);
		}

		c.add(Calendar.HOUR_OF_DAY, hours);
		return new Date(c.getTimeInMillis());
	}

	/**
	 * �ǲ��Ǵӱ����½������������ļ�
	 * @param saveFilePath
	 * @return
	 */

	public static String isLocalNewToServerFile(String saveFilePath){
		if(isBlank(saveFilePath)){
			return null;
		}
		String key = getIdFromReportPath(saveFilePath)+IreportConstant.LOCAL_TO_SERVER;
		return MyReportProperties.getStringProperties(key);
	}

	/**
	 * �Ƴ������ڴ������ֵĶ�ĳ�½����ļ������ı��
	 * @param pathOrName
	 * @param isName
	 */
	public static void removeLocalToServerLock(String pathOrName,boolean isName){
		String key = null;
		if(isName){//�ļ�
		key = getIdFromReport(pathOrName)+IreportConstant.LOCAL_TO_SERVER;
		}else{//·��
		key = getIdFromReportPath(pathOrName)+IreportConstant.LOCAL_TO_SERVER;
		}
		MyReportProperties.removeProperties(key);
	}

	/**
	 * ֻҪ������������trueʱ���ܼ����½�����
	 * @param reportName
	 * @return
	 */
	public static boolean isAllowedNewReport(String reportName){
		MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT, "");
		String error = "";
		if(isBlank(reportName)){
			error = "�ļ�������Ϊ��";
			MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT, error);
			return false;
		}
		if(isRemoteFile(reportName)){
			error = "�ļ������Ϸ�������ʹ��Զ���ļ��������½�";
			MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT, error);
			return false;
		}

		 Matcher m = p1.matcher(reportName.substring(0, 1));
		 boolean b = m.matches();
		 if(b){
			 error = "�ļ������������ֿ�ʼ";
			 MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT, error);
			 return !b;
		 }

		JInternalFrame[] intfs = MainFrame.getMainInstance().getJMDIDesktopPane().getAllFrames();

		for (int i = 0; i < intfs.length; i++) {
			if(intfs!=null && intfs[i] instanceof JReportFrame){
				JReportFrame jf = (JReportFrame) intfs[i];
				if(jf.getReport().getFilename() == null ||
		                jf.getReport().getFilename().trim().equals("")){
					continue;//�ļ����½��ģ���δ���б���
				}
				String reportname = jf.getReport().getName();
				if(reportname.equals(reportName)){
					error = "�ļ������Ѿ��򿪵��ļ��д���";
					 MyReportProperties.setProperties(IreportConstant.NEW_FILE_LIMIT, error);
					return false;
				}
			}
		}

		return true;
	}


	public static String removeNameSuffix(String name){
		if(isBlank(name)){
			return"";
		}
		if(name.indexOf("_")!=-1){
			name = name.substring(1);
		}
		return name;
	}
	
	
	public static String getTemplateType(String tName){
		String tmpTemplate = MainFrame.IREPORT_TMP_TEMPLATE_DIR;
		System.out.println(tmpTemplate + File.separator+tName+"C.xml");
		File cf = new File(tmpTemplate + File.separator+tName+"C.xml");
		File tf = new File(tmpTemplate + File.separator+tName+"T.xml");
		if((!cf.exists()) && (!tf.exists())){
			return null;
		}
		if(cf.exists()){
			return IreportConstant.TEMPLATE_C;
		}
		
		if(tf.exists()){
			return IreportConstant.TEMPLATE_T;
		}
		return null;
	}
	
	public static Object saveTemplatesFile(TemplateFiles tf,boolean editor) {
		File xf = null;
		File mf = null;
		String typeString = null;
		String tpath = MainFrame.IREPORT_TMP_TEMPLATE_DIR+File.separator;
		try {
		if(tf==null){
			throw new RuntimeException("�ϴ�ģ����Ϣ�ļ�Ϊ��");
		}
		typeString = tf.getType().equals(IreportConstant.TEMPLATE_C)?"C":"T";
		
		String xmlPath = tpath + tf.getName()+typeString+".xml";
		String imgPath = tpath+ tf.getName() + ".png";
		
		 xf = new File(xmlPath);
		 mf = new File(imgPath);
		
		if((!editor) && (xf.exists() || mf.exists()) ){
			throw new RuntimeException("ģ�����Ѿ�����");
		}
		if(editor){
			String xp= tpath + tf.getOldName()+typeString+".xml";
			
			System.out.println(xp);
			if(!new File(xp).exists()){
				throw new RuntimeException("����Ҫ�޸ĵ�ģ��["+tf.getOldName()+"]�ڿͻ��˲�����");
			}
			//�Ƿ��޸����ļ�
			if(tf.getXmlContent()!=null && tf.getXmlContent().length>0){
				IreportUtil.bytesToFile(xmlPath, tf.getXmlContent());
				
				if(!tf.getName().equals(tf.getOldName())){
					 String oldXml =  tpath + tf.getOldName()+typeString+".xml";
					 File oldXmlFile = new File(oldXml);
					 if(oldXmlFile!=null && oldXmlFile.exists()){
						 oldXmlFile.delete();
					 }
				}
			}
			
			//�Ƿ��޸���ͼƬ
			if(tf.getImgContent()!=null && tf.getImgContent().length>0){
				IreportUtil.bytesToFile(imgPath, tf.getImgContent());	
				
				if(!tf.getName().equals(tf.getOldName())){
					 String imgP = tpath+ tf.getOldName() + ".png";
					 File oldImgFile = new File(imgP);
					 
					 if(oldImgFile!=null && oldImgFile.exists()){
						 oldImgFile.delete();
					 }
				}
			}
			
			//�Ƿ񴴽������ļ�
			if(tf.getName().equals(tf.getOldName())){
				//δ�ı�����
			}else{
				//�ı�������
				 String oldXml =  tpath + tf.getOldName()+typeString+".xml";
				 String imgP = tpath+ tf.getOldName() + ".png";
				 File oldXmlFile = new File(oldXml);
				 File oldImgFile = new File(imgP);
				 
				 if(oldXmlFile!=null && oldXmlFile.exists()){
					 oldXmlFile.renameTo(new File( tpath + tf.getName()+typeString+".xml"));
				 }
				 
				 if(oldImgFile!=null && oldImgFile.exists()){
					 oldImgFile.renameTo(new File(tpath+ tf.getName() + ".png"));
				 }
				 
			}
			
		}else{
			//ģ���ļ��������
			if(tf.getXmlContent()==null || tf.getXmlContent().length<=0){
				throw new RuntimeException("ģ���ļ����ݲ�����");
			}
			IreportUtil.bytesToFile(xmlPath, tf.getXmlContent());	
			
			//Ԥ��ͼƬ���Բ�����
			if(tf.getImgContent()!=null && tf.getImgContent().length>0){
				IreportUtil.bytesToFile(imgPath, tf.getImgContent());	
			}
		}
		} catch (Exception e) {
			e.printStackTrace();
			//���޸Ĳ�����
			if(!editor){
				if(xf!=null && xf.exists()){
					xf.delete();
				}
				if(mf!=null && mf.exists()){
					mf.delete();
				}
			}
			throw new RuntimeException(e.getMessage());
		}
		
		
		return null;
	}
	
	public static void main(String[] args) {
		System.out.println(isAllowedNewReport("_12345678901234567890"));
	}



}

//end IreportUtil.java