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

import javax.swing.JInternalFrame;

/**
 * @author ��ï
 * @since 3.0
 * @version $Id: AddedOepretorInterface.java 2009-9-1 ����02:54:18 $
 *
 */
//begin AddedOepretorInterface.java
public interface AddedOepretorInterface {
	/**
	 * ��ĳ�ļ�֮ǰ
	 * @return
	 */
	Object beforeOpen();

	/**
	 * ���ļ�֮��
	 * @return
	 */
	Object afterOpen();

	Object beforeSave();

	Object afterSave(String saveFilePath);

	Object beforeSaveAll();

	Object afterSaveAll();

	Object beforeClose();

	Object afterClose(String closeFilePath);

	Object registerSongTi();

	/**
	 * ��������ʼ����Ϻ󣬼���Զ������Դ������Ϊ��
	 * ���ֱ������Ӳ��䣬�����ڱ��������ļ��в����Ƿ�������Դ����û��ֱ���������Զ������Դ
	 * ���У���ɾ����������Դ���������Զ������Դ.
	 * @return
	 */
	Object addRemotDatasource();

	/**
	 * ��������ʼ����Ϻ����Ѿ���������Ҫ�򿪵�Զ���ļ����������Դ�Զ���ļ�
	 * @return
	 */
	Object openRemoteFile();

	/**
	 * �û���½
	 * @param serverApp ��½Ӧ�� ���� "http://192.168.11.110:8080/app3"
	 * @param username ��½�û���
	 * @return password ��½����
	 */
	Object login(String serverApp,String username,String password);

	/**
	 * �û�ע��
	 * @return
	 */
	Object logout();

	/**
	 * ��·���
	 * @return
	 */
	Object linkCheck();

	/**
	 * ��ʼ������ģ���ļ����ͻ��ˣ���ʼ������Ϊ
	 * �Ȳ鿴�������Ƕ�ģ���ļ����Ƿ���ģ���ļ�����û�г��Դӷ������˻�ȡ
	 * �������ڳ�ʼ���ͻ��˵�ʱ���ȡ�������ṩ����Ĺ���ȥͬ���������˵�ģ��
	 * ��ģ��ĳ�ʼ��Ӧ�����첽�ģ��ɰܲ�Ӧ��Ӱ�������������
	 * @return
	 */
	Object initTemplate();

	/**
	 * ���ⲿ���ݹ����Ĳ���...
	 * ע��˳��
	 * @param args
	 * @return
	 */
	Object initRemoteArgs(String[] args);

	/**
	 * ���ȱ��ؼ��ز���
	 * ��������ļ��ļ��ظ�Ϊ����ӷ��������أ�����ɴӷ����������صĸ���
	 * ÿ�����������Զ�ȡ�����ļ������Ƿ��в��������Ϣ����û�н��ӷ�����
	 * �˻�ȡ��Ϣ
	 * @return File[]
	 */
	Object initPluginsConfig();

	/**
	 * ��ireport���ص�ʱ����Ҫ�жϣ��ͻ����Ƿ��Ѿ����õ�һ��ireportʵ��
	 * ���Ѿ���������������ʵ������������Ĵ������ڿͻ�����������ͨ��ҳ������
	 * ��ʱ����ֵģ�������ҳ��������ҳ���Ȼ��жϿͻ����Ƿ��Ѿ�����ireportʵ��
	 * ���Ѿ�����������ӷ���������ireportӦ�ã�������ֱ�����ӿͻ���ireport��
	 * @return
	 */
	Object beforeIreportLoadCheck();

	/**
	 * �÷��������û��ر�Ӧ��ʱ�ص���һ������
	 * �ú�����Ҫ�����ҵ���߼�Ϊ�Ӵ��������˶�Ӧ�ļ�������
	 * @param jif
	 * @return
	 */
	Object beforeCloseApplication(JInternalFrame[] jif);

	/**
	 * �ڽ���ر�ĳ��JReportFrame���õķ���
	 * ����Ч���൱�� ��close��
	 * ����������������ʱӦ�ý����ǰ���������
	 * @param jrf
	 * @return
	 */
	Object afterCloseJReportFrame(JReportFrame jrf);

	/**
	 * ��Ҫ�ӷ�����ͬ��LibĿ¼jar�ļ�
	 * @return
	 */
	Object initLibJarFiles();
	
	/**
	 * ����ͬ���������ļ�������
	 * @return
	 */
	Object incrementAddFile(int type);
	
}

//end AddedOepretorInterface.java