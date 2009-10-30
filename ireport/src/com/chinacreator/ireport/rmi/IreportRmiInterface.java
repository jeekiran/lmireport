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
package com.chinacreator.ireport.rmi;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * @author ��ï
 * @since 3.0
 * @version $Id: IreportRmiInterface.java 2009-8-19 ����08:47:03 $
 *
 */
//begin IreportRmiInterface.java
public interface IreportRmiInterface extends Remote{
	//���Է���
	int add(int a,int b)throws RemoteException;

	/**
	 * ��һ�������ļ����浽������
	 * @param ireportFile
	 * @throws RemoteException
	 */
	void save(IreportFile ireportFile)throws RemoteException;

	/**
	 * �򿪷������˱����ļ�
	 * @param session
	 * @param fileName
	 * @return
	 * @throws RemoteException
	 */
	IreportFile open(IreportSession session,String fileName)throws RemoteException;

	/**
	 * ���Զ�̷���������Դ���ʽ��xml�ַ���
	 * @return
	 * @throws RemoteException
	 */
	String getDataSourceList()throws RemoteException;

	/**
	 * ��÷������˵����в����Ϣ
	 * @return
	 * @throws RemoteException
	 */
	List<IreportFile> getAllPlugins() throws RemoteException;

	/**
	 * �������ģ����Ϣ
	 * @return
	 * @throws RemoteException
	 */
	List<IreportFile> getAllTemplates() throws RemoteException;

	/**
	 * ����������˶�Ӧ����ID����
	 * @param ��ǰ�ͻ���IP
	 * @param repId
	 * @return
	 */
	String unLockReport(String ip,String repId)throws RemoteException;
	
	/**
	 * �������������¼
	 * @throws RemoteException ��ɾ����Ϣ�����쳣
	 */
	void unLockAllReport()throws RemoteException;
	
	/**
	 * ��ȡ�ñ����Ӧ������������¼
	 * @param repid
	 * @return
	 * @throws RemoteException
	 */
	ReportLock getCurrentLock(String repid) throws RemoteException;

	/**
	 *	��������
	 * @return
	 */
	boolean lockReport(ReportLock rl) throws RemoteException;

	/**
	 * ���ͻ����½����ļ����浽������
	 * @param ife
	 * @return
	 */
	String addNewReport(IreportFile ife)throws RemoteException;

	/**
	 * ִ�з������˵�ĳ������
	 * @param businessCode �Զ���ĳֵ��Ӧĳ����ҵ�������ȫ���Լ�����
	 * @return
	 */
	Object invokeServerMethod(int businessCode,Object... obj)throws RemoteException;

	/**
	 * һЩ��Ҫ��ͬ�����ͻ��˵�jar��
	 * @return
	 * @throws RemoteException
	 */
	Object synchronizationLibJar()throws RemoteException;

	/**
	 * ����һ�����������ļ����ͻ���
	 * @param ifi
	 * @param cilentPath
	 * @return
	 * @throws RemoteException
	 */
	Object sendFileToClient(String serverFileNamePath)throws RemoteException;

	/**
	 * ����ģ���ļ�������ģ��xml�ļ���ģ��Ԥ��ͼƬ
	 * @param tf ģ���ļ�����
	 * @return 
	 * @throws RemoteException
	 */
	Object saveTemplatesFile(TemplateFiles tf,boolean editor)throws RemoteException;

	/**
	 * ɾ����������ģ���ļ�
	 * @param tf ģ���ļ�
	 * @return
	 * @throws RemoteException
	 */
	Object deleteTemplateFile(TemplateFiles tf)throws RemoteException;
	
	/**
	 * ��·���
	 * @return
	 * @throws RemoteException
	 */
	boolean ping()throws RemoteException;
	
	/**
	 * ��÷�����
	 * @param path    ��ֵΪ����ĳ������Ӧ�õ��ļ��� ���� /creatorepp/report/lib
	 * 			      ����Ҫȡ��libĿ¼�µ��ļ��б�path��ֵӦ�õ���/report/lib ���� report/lib
	 * @param allowed ����ͨ�����ļ����ͣ������Ķ����ˣ���Ϊ�ձ�ʾ��������
	 * @return
	 * @throws RemoteException
	 */
	File[] getServerFileList(String path,String[] allowed)throws RemoteException;

	/**
	 * ��÷�������������Ϣ
	 * @return
	 * @throws RemoteException
	 */
	IndexInfo indexInfo() throws RemoteException;
	
	/**
	 * ���ɸ������µ�classesĿ¼�ļ�����
	 * @return 
	 * @throws RemoteException
	 */
	boolean generateIndex() throws RemoteException;
	
	/**
	 * ��ѯ��������
	 * @param condition ��ѯ����
	 * @return
	 * @throws RemoteException
	 */
	 List<String>  searchIndex(String condition) throws RemoteException;
	
	/**
	 * ��÷���������������
	 * @return �������飬��һ��Ϊ�ܹ���Ҫ��ͳ�������ڶ���Ϊ����������ϵ���Ŀ
	 * @throws RemoteException
	 */
	int[] indexProgress() throws RemoteException;
	
	/**
	 * ��ѯ��������з���
	 * @return
	 * @throws RemoteException
	 */
	String[] searchClassMethods(String fullClassName) throws RemoteException;
	
	/**
	 * ִ��javabean����Դ�еķ�����Ĭ���������Ҫ��÷����Ǿ�̬����
	 * @param fullClassName
	 * @param methodName
	 * @return
	 */
	boolean invokeJavaBeanMethod(String fullClassName,String methodName,Object[] obj) throws RemoteException;

	/**
	 * ��÷�������ĳ�������������
	 * @param classname
	 * @return
	 * @throws RemoteException
	 */
	List<RemoteBeanPropertyDescriptor> getRemoteBeanProperty(String classname)throws RemoteException;
	
	/**
	 * ִ�з���˵�ĳ�����ĳ���������һ�����ݼ��������ݼ�����ireportjavabean���ݼ���Ҫ��Ҫô��collectionҪô��object����
	 * ���Ƿ���ֵ��Ҫ���д�����Ϊԭ���ķ���ֵ�Ƿ�������ĳjavabean�ļ��ϣ������javabean�Ǵ����ڷ������˵ģ���ireport�ͻ�����
	 * �����ڵģ�������Ҫ��javabeanת��Ϊ��ͨ��map���ϣ���Ĭ����������Ǳ����Ӧjavabean�е�ֵ��map�е�ֵ��ȣ�������ע�⣬javabean
	 * ��ĳ���Ե�ֵҲ��Ҳ��һ����������ireport������ͻ��˵�javabean���������������ǽ�Ĭ�Ϸ��ط������˸ö���toString�������صĶ���
	 * �ַ�����
	 * @param className
	 * @param methodName
	 * @return
	 * @throws RemoteException
	 */
	List<Map<String,Object>> remoteBeanCollectionDataset(String className,String methodName,int collectionType)throws RemoteException;

	/**
	 * ���һ��javabean����Դ��¼
	 * @param rdb
	 * @throws RemoteException
	 */
	void addJavaBeanDataSourceRecord(ReportDatasourceBean rdb) throws RemoteException;

	/**
	 * ɾ��һ��javabean����Դ��¼
	 * @param repid
	 */
	void deleteJavaBeanDataSourceRecord(String repid)throws RemoteException;
	
	/**
	 * �޸�һ��javabean����Դ��¼
	 * @param rdb
	 * @throws RemoteException
	 */
	void updateJavaBeanDataSourceRecord(ReportDatasourceBean rdb)throws RemoteException;
}

//end IreportRmiInterface.java