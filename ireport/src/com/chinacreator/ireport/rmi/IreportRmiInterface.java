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

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

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

	//�������
	void save(IreportFile ireportFile)throws RemoteException;

	//��Զ���ļ�
	IreportFile open(IreportSession session,String fileName)throws RemoteException;

	//��ȡ����Դ�б�
	String getDataSourceList()throws RemoteException;

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
	 *
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
}

//end IreportRmiInterface.java