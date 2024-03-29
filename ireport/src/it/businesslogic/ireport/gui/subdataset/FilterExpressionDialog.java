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
 * FilterExpressionDialog.java
 * 
 * Created on 9 maggio 2003, 17.25
 *
 */

package it.businesslogic.ireport.gui.subdataset;

import it.businesslogic.ireport.SubDataset;
import it.businesslogic.ireport.gui.*;
import it.businesslogic.ireport.util.*;
import it.businesslogic.ireport.*;
/**
 *
 * @author  Administrator
 */
public class FilterExpressionDialog extends javax.swing.JDialog {
	/** Creates new form JRParameterDialog */
	private String filterExpression = "";
	
	public FilterExpressionDialog(java.awt.Frame parent, boolean modal) {
		super(parent, modal);
		initAll();
	}
        
        public FilterExpressionDialog(java.awt.Dialog parent, boolean modal) {
		super(parent, modal);
		initAll();
	}
        
        
        public void initAll()
        {
            initComponents();
            applyI18n();
            this.pack();
                
            javax.swing.KeyStroke escape =  javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, false);
            javax.swing.Action escapeAction = new javax.swing.AbstractAction() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    jButtonCancelActionPerformed(e);
                }
            };

            getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
            getRootPane().getActionMap().put("ESCAPE", escapeAction);


            //to make the default button ...
            this.getRootPane().setDefaultButton(this.jButtonOK);    
            
        }
        
        
        
	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jLabel1 = new javax.swing.JLabel();
        jRTextExpressionAreaFilterExpression = new it.businesslogic.ireport.gui.JRTextExpressionArea();
        jPanel1 = new javax.swing.JPanel();
        jButtonOK = new javax.swing.JButton();
        jButtonCancel = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setTitle("Add/modify field");
        setModal(true);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });

        jLabel1.setText("Filter expression");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(jLabel1, gridBagConstraints);

        jRTextExpressionAreaFilterExpression.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jRTextExpressionAreaFilterExpression.setCaretVisible(false);
        jRTextExpressionAreaFilterExpression.setElectricScroll(0);
        jRTextExpressionAreaFilterExpression.setMinimumSize(new java.awt.Dimension(657, 50));
        jRTextExpressionAreaFilterExpression.setPreferredSize(new java.awt.Dimension(600, 200));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTHWEST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(0, 3, 3, 3);
        getContentPane().add(jRTextExpressionAreaFilterExpression, gridBagConstraints);

        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jButtonOK.setText("OK");
        jButtonOK.setMnemonic('o');
        jButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonOKActionPerformed(evt);
            }
        });

        jPanel1.add(jButtonOK);

        jButtonCancel.setText("Cancel");
        jButtonCancel.setMnemonic('c');
        jButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonCancelActionPerformed(evt);
            }
        });

        jPanel1.add(jButtonCancel);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 7;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.SOUTHEAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(3, 3, 3, 3);
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        java.awt.Dimension dialogSize = getSize();
        setLocation((screenSize.width-dialogSize.width)/2,(screenSize.height-dialogSize.height)/2);
    }// </editor-fold>//GEN-END:initComponents
	
    private void jButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonCancelActionPerformed
	    setVisible(false);
	    this.setDialogResult( javax.swing.JOptionPane.CANCEL_OPTION);
	    dispose();
    }//GEN-LAST:event_jButtonCancelActionPerformed
    
    private void jButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonOKActionPerformed
	    
	    filterExpression = jRTextExpressionAreaFilterExpression.getText();
	    setVisible(false);
	    this.setDialogResult( javax.swing.JOptionPane.OK_OPTION);
	    dispose();
    }//GEN-LAST:event_jButtonOKActionPerformed
    
    /** Closes the dialog */
    private void closeDialog(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closeDialog
	    setVisible(false);
	    this.setDialogResult( javax.swing.JOptionPane.CLOSED_OPTION);
	    dispose();
    }//GEN-LAST:event_closeDialog
    
    
   
    /** Getter for property dialogResult.
     * @return Value of property dialogResult.
     *
     */
    public int getDialogResult() {
	    return dialogResult;
    }
    
    /** Setter for property dialogResult.
     * @param dialogResult New value of property dialogResult.
     *
     */
    public void setDialogResult(int dialogResult) {
	    this.dialogResult = dialogResult;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonCancel;
    private javax.swing.JButton jButtonOK;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JPanel jPanel1;
    private it.businesslogic.ireport.gui.JRTextExpressionArea jRTextExpressionAreaFilterExpression;
    // End of variables declaration//GEN-END:variables
	
	private int dialogResult;

    public String getFilterExpression() {
        return filterExpression;
    }

    public void setFilterExpression(String filterExpression) {
        setFilterExpression(filterExpression, null);
    }
    
    public void setFilterExpression(String filterExpression, SubDataset subDataset) {
        this.filterExpression = filterExpression;
        jRTextExpressionAreaFilterExpression.setText(filterExpression);
        if (subDataset != null) jRTextExpressionAreaFilterExpression.setSubDataset(subDataset);
    }
	
    public void applyI18n(){
                // Start autogenerated code ----------------------
                jButtonCancel.setText(I18n.getString("filterExpressionDialog.buttonCancel","Cancel"));
                jButtonOK.setText(I18n.getString("filterExpressionDialog.buttonOK","OK"));
                jLabel1.setText(I18n.getString("filterExpressionDialog.label1","Filter expression"));
                // End autogenerated code ----------------------
                
                //
                this.setTitle(I18n.getString("filterExpressionDialog.title","Add/modify field"));
                jButtonCancel.setMnemonic(I18n.getString("filterExpressionDialog.buttonCancelMnemonic","c").charAt(0));
                jButtonOK.setMnemonic(I18n.getString("filterExpressionDialog.buttonOKMnemonic","o").charAt(0));
                //
    }
    
    
    public static final int COMPONENT_NONE=0;
    public static final int COMPONENT_EXPRESSION=1;

    /**
     * This method set the focus on a specific component.
     * Valid constants are something like:
     * FIELD_XXX
     */
    public void setFocusedExpression(int expID )
    {
        switch (expID)
        {
            case COMPONENT_EXPRESSION:
                Misc.selectTextAndFocusArea(jRTextExpressionAreaFilterExpression);
                break;
        }
        
    }
}
