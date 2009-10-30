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

/**
 * @author ��ï
 * @since 3.0
 * @version $Id: ClientRmiServerInterface.java 2009-9-7 ����09:54:40 $
 *
 */
//begin ClientRmiServerInterface.java
public interface ClientRmiServerInterface extends Remote{
	/**
	 * ��·��⣬���ɵ������ӷ���true����false
	 * �������ڷ��������ʱ������Ҫʹ�õ�
	 * @return
	 */
	boolean linkCheck() throws RemoteException;

	/**
	 * ���ͻ����Ѿ�������һ��ireportʵ�������ͻ��˳����ٴδ�ireportʱ����
	 * ����������֪̽�����Ҳ����ڿͻ��˴��µ�ireportʵ�������´򿪵��ļ���ͨ��
	 * ���������Ѿ�������ireport��ʵ���д򿪸��ļ�
	 * @param file
	 * @return
	 */
	Object openFile(IreportSession session,IreportFile file) throws RemoteException;

	/**
	 * �򿪿ͻ��˴���
	 * @return
	 */
	Object openCilentDialog(String text,String title,int messageType)throws RemoteException;
}

//end ClientRmiServerInterface.java