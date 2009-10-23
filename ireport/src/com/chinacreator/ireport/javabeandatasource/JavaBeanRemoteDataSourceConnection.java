package com.chinacreator.ireport.javabeandatasource;
import it.businesslogic.ireport.IReportConnectionEditor;
import it.businesslogic.ireport.gui.MainFrame;
import it.businesslogic.ireport.plugin.newserverfile.Params;
import it.businesslogic.ireport.util.I18n;
import it.businesslogic.ireport.util.Misc;

import java.util.List;
import java.util.Map;

import javax.swing.JDialog;
import javax.swing.JOptionPane;

import com.chinacreator.ireport.AddedOperator;
import com.chinacreator.ireport.IreportConstant;
import com.chinacreator.ireport.IreportUtil;
import com.chinacreator.ireport.component.DialogFactory;
import com.chinacreator.ireport.rmi.IreportRmiClient;
/**
 *
 * @author  ��ï
 */
public class JavaBeanRemoteDataSourceConnection extends it.businesslogic.ireport.IReportConnection {
    
    public static String BEAN_ARRAY = "BEAN_ARRAY";
    public static String BEAN_COLLECTION = "BEAN_COLLECTION";
    
    private String name;
    
    private String factoryClass;
    
    private String methodToCall;
    
    private boolean useFieldDescription;
    
    public static String type = "REMOTE_BEAN_COLLECTION";
    
    /** Creates a new instance of JDBCConnection */
    
    
    public JavaBeanRemoteDataSourceConnection() {
    }
    
    /**  This method return an instanced connection to the database.
     *  If isJDBCConnection() return false => getConnection() return null
     *
     */
    public java.sql.Connection getConnection() {       
            return null;
    }
    
    public boolean isJDBCConnection() {
        return false;
    }
    
    /*
     *  This method return all properties used by this connection
     */
    public java.util.HashMap getProperties()
    {    
        java.util.HashMap map = new java.util.HashMap();
        map.put("FactoryClass", Misc.nvl(this.getFactoryClass() ,"") );
        map.put("MethodToCall", Misc.nvl(this.getMethodToCall(),""));
        map.put("Type", Misc.nvl(this.getType(),""));
        map.put("UseFieldDescription", ""+this.isUseFieldDescription());
       // map.put("Remotebean", value)
        return map;
    }
    
    public void loadProperties(java.util.HashMap map)
    {
        this.setFactoryClass( (String)map.get("FactoryClass"));
        this.setMethodToCall( (String)map.get("MethodToCall"));
        if (map.containsKey("UseFieldDescription"))
        {
            this.setUseFieldDescription( ((String)map.get("UseFieldDescription")).equals("true") );
        }
        if (map.containsKey("Type"))
        {
            this.setType( (String)map.get("Type"));
        }
    }
    

    
    /** Getter for property methodToCall.
     * @return Value of property methodToCall.
     *
     */
    public java.lang.String getMethodToCall() {
        return methodToCall;
    }
    
    /** Setter for property methodToCall.
     * @param methodToCall New value of property methodToCall.
     *
     */
    public void setMethodToCall(java.lang.String methodToCall) {
        this.methodToCall = methodToCall;
    }
    
    /** Getter for property factoryClass.
     * @return Value of property factoryClass.
     *
     */
    public java.lang.String getFactoryClass() {
        return factoryClass;
    }
    
    /** Setter for property factoryClass.
     * @param factoryClass New value of property factoryClass.
     *
     */
    public void setFactoryClass(java.lang.String factoryClass) {
        this.factoryClass = factoryClass;
    }
    
    /**
     * Getter for property type.
     * @return Value of property type.
     */
    public java.lang.String getType() {
        return "REMOTE_BEAN_COLLECTION"; //�޸�Ϊ��ֵ
    }    
   
    /**
     * Setter for property type.
     * @param type New value of property type.
     */
    public void setType(java.lang.String type) {
        this.type = type;
    }    
    
        /**
     *  This method return an instanced JRDataDource to the database.
     *  If isJDBCConnection() return true => getJRDataSource() return false
     */
    public net.sf.jasperreports.engine.JRDataSource getJRDataSource()
    { 
        try {
        	//���ݼ�ͨ��Զ�̻��
        	System.out.println("-------------------------");
        	List<Map<String,Object>> list = IreportRmiClient.getInstance().rmiInterfactRemote.remoteBeanCollectionDataset(this.getFactoryClass(), this.getMethodToCall(), 1);//Զ�̻��
        	System.out.println("ִ��Զ�����ݼ�����,������ݳ���Ϊ:"+(list==null?0:list.size()));
        	AddedOperator.log("ִ��Զ�����ݼ�����,������ݳ���Ϊ:"+(list==null?0:list.size()), IreportConstant.INFO_);
        	return new net.sf.jasperreports.engine.data.JRMapCollectionDataSource(list);
       		
       		             
        } catch (Exception ex)
        {
            ex.printStackTrace();
            return super.getJRDataSource();
        }
    }

    public boolean isUseFieldDescription() {
        return useFieldDescription;
    }

    public void setUseFieldDescription(boolean useFieldDescription) {
        this.useFieldDescription = useFieldDescription;
    }
    
    public String getDescription(){ return "������ JavaBean ����Դ"; }
    
    public IReportConnectionEditor getIReportConnectionEditor()
    {
        return new JavaBeanRemoteDataSourceConnectionEditor();
    }
    
    public void test() throws Exception 
    {
    	String classname =this.factoryClass;
		String methodname = this.methodToCall;
		JDialog jda = ((JavaBeanRemoteDataSourceConnectionEditor)this.getIReportConnectionEditor()).getFather();
		if (IreportUtil.isBlank(classname)) {
			DialogFactory.showErrorMessageDialog(jda, "����������д", "����");
			return;
		}

		if (IreportUtil.isBlank(methodname)) {
			DialogFactory.showErrorMessageDialog(jda, "������������д", "����");
			return;
		}

	
		try {
			IreportRmiClient.getInstance();
			Object object = IreportRmiClient.rmiInterfactRemote.invokeJavaBeanMethod(
					classname, methodname, null);
			
			DialogFactory.showInfoMessageDialog(jda, "���Գɹ�\n��÷���ֵ��"+object, "�ɹ�");
		} catch (Exception e) {
			e.printStackTrace();
			DialogFactory.showErrorMessageDialog(jda, "����ʧ��\n"
					+ e.getMessage(), "����ʧ��");
		}

    }
}
