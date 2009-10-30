package com.chinacreator.ireport.rmi;

import java.io.Serializable;

/**
 * ��������Զ��bean�����ԣ���Ӧչʾ
 * @author limao
 *
 */
public class RemoteBeanPropertyDescriptor implements Serializable {
	private String fieldName;
	private String returnType;
	private Class propertyType;
	boolean bChildrens;

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getReturnType() {
		return returnType;
	}

	public void setReturnType(String returnType) {
		this.returnType = returnType;
	}

	public boolean isBChildrens() {
		return bChildrens;
	}

	public void setBChildrens(boolean childrens) {
		bChildrens = childrens;
	}

	public Class getPropertyType() {
		return propertyType;
	}

	public void setPropertyType(Class propertyType) {
		this.propertyType = propertyType;
	}
}
