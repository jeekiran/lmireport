package a.b.c;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.standard.PrinterName;

/**
 *@author ��ï
 *@since  3.0
 *@version $Id: Printer.java 2009 Sep 27, 2009 9:38:44 AM $
 */

//begin Printer.java
public class Printer {
	public static  boolean findPrinter(String printerName) { 
	    HashAttributeSet hash = new HashAttributeSet();    // �洢��ӡ�����Եļ��� 
	    hash.add(new PrinterName(printerName, null));    // ��Ӵ�ӡ���������ԣ�����������������ô�ӡ��Ӳ��ʱָ�������� 
	    PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, null);    // ���ҿ��õĴ�ӡ�����񲢲�ָ���Զ��Ĵ�ӡ��ʽ 
	    
	    for (int i = 0; i < pss.length; i++) {
			System.out.println(pss[i].getName());
		}
	    if (pss.length == 0) { 
	    return false; // û�� 
	    } else { 
	    return true; // �ҵ� 
	    } 
	}
	
}

//end Printer.java