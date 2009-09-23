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

import java.io.Serializable;
import java.util.Date;

/**
 * @author ��ï
 * @since 3.0
 * @version $Id: IJasperReport.java 2009-9-16 ����03:15:27 $
 *
 */
//begin IJasperReport.java
public class IJasperReport implements Serializable{

	private String repId;

	private String ecId;

	private String repName;

	private String repVer;

	private String repDesc;

	private String creator;

	private Date creatTime;

	private String publishState;

	private Date publishTime;

	private String publisher;

	// ������ʾ����
	private String repSType;

	// ������ID
	private String repId2;

	// �����ļ������·��������û�У�ֻ�ڳ�����ʹ��
	private String filePath;

	// �޸ı����ʱ���Ƿ񴴽��°汾������û�У�ֻ�ڳ�����ʹ��
	private boolean isCreateNew;

	// �����ļ������ͣ�.raq,.jrxml
	private String fileType;

	/**
	 * @return creator
	 */
	public String getCreator() {
		return creator;
	}

	/**
	 * @param creator
	 *            Ҫ���õ� creator
	 */
	public void setCreator(String creator) {
		this.creator = creator;
	}

	/**
	 * @return creatTime
	 */
	public Date getCreatTime() {
		return creatTime;
	}

	/**
	 * @param creatTime
	 *            Ҫ���õ� creatTime
	 */
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}

	/**
	 * @return ecId
	 */
	public String getEcId() {
		return ecId;
	}

	/**
	 * @param ecId
	 *            Ҫ���õ� ecId
	 */
	public void setEcId(String ecId) {
		this.ecId = ecId;
	}

	/**
	 * @return publisher
	 */
	public String getPublisher() {
		return publisher;
	}

	/**
	 * @param publisher
	 *            Ҫ���õ� publisher
	 */
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}

	/**
	 * @return publishState
	 */
	public String getPublishState() {
		return publishState;
	}

	/**
	 * @param publishState
	 *            Ҫ���õ� publishState
	 */
	public void setPublishState(String publishState) {
		this.publishState = publishState;
	}

	/**
	 * @return publishTime
	 */
	public Date getPublishTime() {
		return publishTime;
	}

	/**
	 * @param publishTme
	 *            Ҫ���õ� publishTime
	 */
	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	/**
	 * @return repDesc
	 */
	public String getRepDesc() {
		return repDesc;
	}

	/**
	 * @param repDesc
	 *            Ҫ���õ� repDesc
	 */
	public void setRepDesc(String repDesc) {
		this.repDesc = repDesc;
	}

	/**
	 * @return repId
	 */
	public String getRepId() {
		return repId;
	}

	/**
	 * @param repId
	 *            Ҫ���õ� repId
	 */
	public void setRepId(String repId) {
		this.repId = repId;
	}

	/**
	 * @return repName
	 */
	public String getRepName() {
		return repName;
	}

	/**
	 * @param repName
	 *            Ҫ���õ� repName
	 */
	public void setRepName(String repName) {
		this.repName = repName;
	}

	/**
	 * @return repVer
	 */
	public String getRepVer() {
		return repVer;
	}

	/**
	 * @param repVer
	 *            Ҫ���õ� repVer
	 */
	public void setRepVer(String repVer) {
		this.repVer = repVer;
	}

	/**
	 * @return repId2
	 */
	public String getRepId2() {
		return repId2;
	}

	/**
	 * @param repId2
	 *            Ҫ���õ� repId2
	 */
	public void setRepId2(String repId2) {
		this.repId2 = repId2;
	}

	/**
	 * @return repSType
	 */
	public String getRepSType() {
		return repSType;
	}

	/**
	 * @param repSType
	 *            Ҫ���õ� repSType
	 */
	public void setRepSType(String repSType) {
		this.repSType = repSType;
	}

	/**
	 * @return filePath
	 */
	public String getFilePath() {
		return filePath;
	}

	/**
	 * @param filePath
	 *            Ҫ���õ� filePath
	 */
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	/**
	 * @return isCreateNew
	 */
	public boolean getIsCreateNew() {
		return isCreateNew;
	}

	/**
	 * @param isCreateNew
	 *            Ҫ���õ� isCreateNew
	 */
	public void setIsCreateNew(boolean isCreateNew) {
		this.isCreateNew = isCreateNew;
	}

	/**
	 * @return fileType
	 */
	public String getFileType() {
		return fileType;
	}

	/**
	 * @param fileType
	 *            Ҫ���õ� fileType
	 */
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}

//end IJasperReport.java