/*
 * Copyright (C) 2005 - 2008 JasperSoft Corporation.  All rights reserved. 
 * http://www.jaspersoft.com.
 *
 * Unless you have purchased a commercial license agreement from JasperSoft,
 * the following license terms apply:
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2 as published by
 * the Free Software Foundation.
 *
 * This program is distributed WITHOUT ANY WARRANTY; and without the
 * implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, see http://www.gnu.org/licenses/gpl.txt
 * or write to:
 *
 * Free Software Foundation, Inc.,
 * 59 Temple Place - Suite 330,
 * Boston, MA  USA  02111-1307
 *
 *
 *
 *
 * PromptDialog.java
 * 
 * Created on 5 maggio 2005, 0.25
 *
 */

package it.businesslogic.ireport.gui.prompt;

import it.businesslogic.ireport.util.I18n;
import java.awt.BorderLayout;

import java.awt.Frame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.StringTokenizer;

import java.util.Vector;

import javax.swing.JOptionPane;


/**
 * @author Administrator
 */
public class PromptDialog
    extends javax.swing.JDialog
{

    static Vector cachedValues = new Vector();
    private int dialogResult = JOptionPane.CANCEL_OPTION;
    private Object value = null;
    
    private com.michaelbaranov.microba.calendar.DatePicker datePicker = null;
    private JDateTimePicker datetimePicker = null;
    
    private boolean isCollection = false;
    
    /**
     * Creates new form PromptDialog
     * 
     * @param parent DOCUMENT ME!
     * @param modal DOCUMENT ME!
     */
    public PromptDialog(Frame parent, boolean modal)
    {
        super(parent, modal);
        initComponents();
        applyI18n();
        jLabelYouCan.setText(it.businesslogic.ireport.util.I18n.getString(
                                     "gui.prompt.parameter", 
                                     "You can provide a value for the parameter:"));

        for (int i = 0; i < cachedValues.size(); ++i)
        {
            this.jComboBox1.addItem(cachedValues.elementAt(i));
        }

        this.jComboBox1.getEditor().getEditorComponent().addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent evt)
            {
                jButton1KeyPressed(evt);
            }

            public void keyTyped(KeyEvent evt)
            {
            }
        });

        it.businesslogic.ireport.util.Misc.centerFrame(this);
        
        javax.swing.KeyStroke escape =  javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, false);
        javax.swing.Action escapeAction = new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jButton2ActionPerformed(e);
            }
        };
       
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);


        //to make the default button ...
        this.getRootPane().setDefaultButton(this.jButton2);
    }

    /**
     * DOCUMENT ME!
     * 
     * @param param DOCUMENT ME!
     */
    public void setParameter(it.businesslogic.ireport.JRParameter param)
    {

        Object val = param.getLastDefaultValue();

        String format = "";
        
        if (param.getClassType().equals("java.util.Date"))
        {
            format="";
            jPanel4.removeAll();
            datePicker = new com.michaelbaranov.microba.calendar.DatePicker();
            datePicker.setLocale( I18n.getCurrentLocale() );
            SimpleDateFormat sdf = new SimpleDateFormat();
            try {
                if (val instanceof java.util.Date)
                {
                    datePicker.setDate( (java.util.Date)val );
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            jPanel4.add(datePicker, BorderLayout.CENTER);
        }
        else if (param.getClassType().equals("java.sql.Time") || 
            param.getClassType().equals("java.sql.Timestamp"))
        {
            format="";
            jPanel4.removeAll();
            datetimePicker = new JDateTimePicker();
            datetimePicker.setLocale( I18n.getCurrentLocale() );
            
            try {
                if (val instanceof java.util.Date)
                {
                    datetimePicker.setDate( (java.util.Date)val );
                }
            } catch (Exception ex)
            {
                ex.printStackTrace();
            }
            jPanel4.add(datetimePicker, BorderLayout.CENTER);
        }
        else
        {
            val = (val == null) ? "" : val;
            this.jComboBox1.setSelectedItem(val);
            this.jComboBox1.getEditor().selectAll();
        }
        
        jLabelParamName.setText(param.getName());

        if (param.getDescription() != null && 
            param.getDescription().length() > 0)
        {
            jLabelParamName.setToolTipText(param.getDescription());
        }

        

        if (param.getClassType().equals("java.util.Date"))
        {
            //format = " (" + 
            //         it.businesslogic.ireport.gui.MainFrame.getMainInstance().getProperties()
            //                          .getProperty("dateformat", "d/M/y") + 
            //         ")";
        }
        else if (param.getClassType().equals("java.sql.Time") || 
            param.getClassType().equals("java.sql.Timestamp"))
        {
            //format = " (" + 
            //         it.businesslogic.ireport.gui.MainFrame.getMainInstance().getProperties()
            //                          .getProperty("timeformat", "d/M/y H:m:s") + 
            //         ")";
        }
        else if (param.getClassType().equals("java.lang.Boolean"))
        {
            format = " (true | false)";
        }
        else if (param.getClassType().equals("java.lang.String"))
        {
            
        }
        else
        {
            
            try {
                Class clazz = Class.forName(param.getClassType());
                if ( java.util.Collection.class.isAssignableFrom(clazz) )
                {
                        format = " ( foo,bar,test )";
                }
                
            } catch (Exception ex)
            {

            }
        }

        jLabelClass.setText(
                it.businesslogic.ireport.util.I18n.getString(
                        "gui.prompt.class", "The class type is:") + " " + 
                param.getClassType() + format);

        if (param.getDescription() != null &&
            param.getDescription().length()>0)
        {
            jTextArea1.setText( param.getDescription() );
            jScrollPane1.setVisible(true);
        }
        else
        {
            jScrollPane1.setVisible(false);
        }
        if (jComboBox1.isVisible()) this.jComboBox1.requestFocusInWindow();
    }

    /**
     * This method is called from within the constructor to initialize the
     * form. WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */


    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabelYouCan = new javax.swing.JLabel();
        jLabelParamName = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jComboBox1 = new javax.swing.JComboBox();
        jLabelClass = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Parameter prompt");
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/it/businesslogic/ireport/icons/keyboard.png")));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 10;
        gridBagConstraints.ipady = 10;
        getContentPane().add(jLabel1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jLabelYouCan.setFont(new java.awt.Font("SansSerif", 0, 12));
        jLabelYouCan.setText("You can provide a value for the parameter:");
        jLabelYouCan.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabelYouCan, gridBagConstraints);

        jLabelParamName.setFont(new java.awt.Font("SansSerif", 1, 16));
        jLabelParamName.setText("Param name");
        jLabelParamName.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jLabelParamName, gridBagConstraints);

        jPanel4.setLayout(new java.awt.BorderLayout());

        jPanel4.setMinimumSize(new java.awt.Dimension(118, 18));
        jPanel4.setPreferredSize(new java.awt.Dimension(400, 25));
        jComboBox1.setEditable(true);
        jComboBox1.setPreferredSize(new java.awt.Dimension(400, 25));
        jComboBox1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jComboBox1KeyPressed(evt);
            }
        });

        jPanel4.add(jComboBox1, java.awt.BorderLayout.CENTER);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        jPanel1.add(jPanel4, gridBagConstraints);

        jLabelClass.setFont(new java.awt.Font("SansSerif", 0, 12));
        jLabelClass.setText("The class  type is:");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        jPanel1.add(jLabelClass, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 2, 4);
        jPanel1.add(jSeparator1, gridBagConstraints);

        jScrollPane1.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(3, 40));
        jTextArea1.setEditable(false);
        jTextArea1.setOpaque(false);
        jScrollPane1.setViewportView(jTextArea1);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 4);
        jPanel1.add(jScrollPane1, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.ipadx = 1;
        gridBagConstraints.ipady = 1;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(50, 40));
        jPanel2.setPreferredSize(new java.awt.Dimension(40, 40));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.weightx = 1.0;
        jPanel2.add(jPanel3, gridBagConstraints);

        jButton1.setText("Ok");
        jButton1.setPreferredSize(new java.awt.Dimension(100, 26));
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jButton1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jButton1KeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jButton1KeyTyped(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel2.add(jButton1, gridBagConstraints);

        jButton2.setText("Use default");
        jButton2.setPreferredSize(new java.awt.Dimension(100, 26));
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.insets = new java.awt.Insets(0, 0, 0, 4);
        jPanel2.add(jButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        getContentPane().add(jPanel2, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents
    private void jComboBox1KeyPressed(java.awt.event.KeyEvent evt//GEN-FIRST:event_jComboBox1KeyPressed
                                      )
    {
    }//GEN-LAST:event_jComboBox1KeyPressed

    private void jButton1KeyPressed(java.awt.event.KeyEvent evt//GEN-FIRST:event_jButton1KeyPressed
                                    )
    {

        if (evt.getKeyCode() == evt.VK_ENTER)
        {
            jComboBox1.setSelectedItem(jComboBox1.getEditor().getItem());
            this.jButton1.requestFocusInWindow();
            jButton1ActionPerformed(null);
        }
    }//GEN-LAST:event_jButton1KeyPressed

    private void jButton1KeyTyped(java.awt.event.KeyEvent evt//GEN-FIRST:event_jButton1KeyTyped
                                  )
    {
    }//GEN-LAST:event_jButton1KeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt//GEN-FIRST:event_jButton2ActionPerformed
                                         )
    {
        setDialogResult(javax.swing.JOptionPane.CANCEL_OPTION);
        setValue(null);
        setVisible(false);
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt//GEN-FIRST:event_jButton1ActionPerformed
                                         )
    {
        setDialogResult(javax.swing.JOptionPane.OK_OPTION);

        if (jPanel4.getComponent(0) == jComboBox1)
        {
           if (jComboBox1.getSelectedItem() == null)
            {
                setValue(null);
            }
            else
            {
                setValue(jComboBox1.getSelectedItem());  
                if (!cachedValues.contains(getValue()))
                {
                    cachedValues.addElement(getValue());
                }
            }
        }
        else if (jPanel4.getComponent(0) == datePicker)
        {
            setValue( datePicker.getDate() );
        }
        else if (jPanel4.getComponent(0) == datetimePicker)
        {
            setValue( datetimePicker.getDate() );
        }
        

        setVisible(false);
        dispose();

    }//GEN-LAST:event_jButton1ActionPerformed

   

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public int getDialogResult()
    {

        return dialogResult;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param dialogResult DOCUMENT ME!
     */
    public void setDialogResult(int dialogResult)
    {
        this.dialogResult = dialogResult;
    }

    /**
     * DOCUMENT ME!
     * 
     * @return DOCUMENT ME!
     */
    public Object getValue()
    {

        return value;
    }

    /**
     * DOCUMENT ME!
     * 
     * @param value DOCUMENT ME!
     */
    public void setValue(Object value)
    {
        this.value = value;
    }
    
    
    
    

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabelClass;
    private javax.swing.JLabel jLabelParamName;
    private javax.swing.JLabel jLabelYouCan;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextArea jTextArea1;
    // End of variables declaration//GEN-END:variables
    public void applyI18n(){
                // Start autogenerated code ----------------------
                jButton1.setText(I18n.getString("promptDialog.button1","Ok"));
                jButton2.setText(I18n.getString("promptDialog.button2","Use default"));
                jLabelClass.setText(I18n.getString("promptDialog.labelClass","The class  type is:"));
                jLabelParamName.setText(I18n.getString("promptDialog.labelParamName","Param name"));
                // End autogenerated code ----------------------
                
                this.setTitle(I18n.getString("promptDialog.title","Parameter prompt"));
                jLabelYouCan.setText(I18n.getString("promptDialog.labelYouCan","You can provide a value for the parameter:"));
    }
}
