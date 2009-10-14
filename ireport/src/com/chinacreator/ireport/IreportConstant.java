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

/**
 * @author ��ï
 * @since 3.0
 * @version $Id: IreportConstant.java 2009-8-21 ����02:45:51 $
 *
 */
//begin IreportConstant.java
public final class IreportConstant {
	public final static String FONT_SONGTI = "����";

	public final static String SUCCESS = "SUCCESS";

	public final static String FAIL = "FAIL";

	public final static String REMOTE_SUFFIX = "(Զ��)";

	public final static String DEFAULT_DATASOURCE_NAME="bspf"+REMOTE_SUFFIX;

	public final static String USERNAME="USERNAME";

	public final static String PASSWORD="PASSWORD";

	public final static String IP="IP";

	public final static String RMI_IP="RMI_IP";

	public final static String RMI_PORT="RMI_PORT";

	public final static String REPORT_ID="REPORT_ID";

	public final static String CLIENT_RMI_PORT="10087"; //�ͻ��˷�������RMI�˿�

	public final static String SESSION_SUFFIX="session_";

	public final static String REPORT_TYPE_JASPERREPORT = "6";

	public final static String LOCK = "Y";
	public final static String UN_LOCK = "N";

	public final static int DEFAULT_LOCK_LIMIT_HOURE=1;

	public final static String EFORM_TREE_SELECT = "EFORM_TREE_SELECT";
	public final static String EFORM_TREE_SELECT_PATH = "EFORM_TREE_SELECT_PATH";
	
	public final static String LOCAL_TO_SERVER = "LOCAL_TO_SERVER";

	public final static String NAME_SUFFIX = "_";

	public final static String NEW_FILE_LIMIT="NEW_FILE_LIMIT";
	//LOG
	public final static int ERROR_ = -50;
	public final static int RIGHT_ = -49;
	public final static int INFO_ = -48;
	public final static int WARN_ = -47;
	
	//ģ������
	public final static String TEMPLATE_C = "��ʽ";
	public final static String TEMPLATE_T = "���ʽ";
	
	//��·���
	public final static String LINK_CHECK = "LINK_CHECK"; 
	public final static String LINK_CHECK_TIME_OUT = "LINK_CHECK_TIME_OUT"; 
	public static long PTIME = 30000; //��ʾִ������
	
	//ִ��Զ�̴��������
	public final static int FIND_REPORT_LOCK_LIST = 2;	
	
	//��ҳ
	
	public final static int DEFAULT_PAGE_SIZE= 10;
}

//end IreportConstant.java