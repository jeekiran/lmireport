package com.chinacreator.ireport;

/**
 *@author ��ï
 *@since  3.0
 *@version $Id: PrinterCheck.java 2009 Sep 28, 2009 9:08:09 AM $
 */

//begin PrinterCheck.java
public class PrinterCheck {

	static {
		System./*author ��ï*/loadLibrary/*author ��ï*/("PRINTS")/*author ��ï*/;
	}
	
	public native int chekcInstall();
	
	public native void showInstall();
	
	public static void main(String[] args) {
		new PrinterCheck().showInstall();
	
	}
}

//end PrinterCheck.java