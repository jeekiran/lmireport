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
 * TimeZoneDialog.java
 * 
 * Created on 17 novembre 2004, 1.07
 *
 */

package it.businesslogic.ireport.gui.locale;
import it.businesslogic.ireport.gui.sheet.Tag;
import it.businesslogic.ireport.util.I18n;
import it.businesslogic.ireport.util.Misc;
import java.util.Locale;
import java.util.Vector;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * 
 * @author  Administrator
 */
public class LocaleSelectorDialog extends javax.swing.JDialog {
    
    private int dialogResult = javax.swing.JOptionPane.CANCEL_OPTION;
    
    private Locale selectedLocale = null;
    private boolean updating = false;
    
    /** Creates new form NewLocaleFileDialog */
    public LocaleSelectorDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initAll();
    }
    
     public LocaleSelectorDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initAll();
    }
     
     public void initAll()
     {
         initComponents();
        
        
        Locale[] locales = Locale.getAvailableLocales();
        java.util.Vector v = new java.util.Vector();
        v.add( I18n.getString("timezone.default","Default") );
        for (int i=0; i<locales.length; ++i)
        {
            v.add(new Tag(locales[i], locales[i].getDisplayName(I18n.getCurrentLocale()) ));
        }
        
        jListLocales.setListData( v );
        
        jListLocales.setSelectedIndex(0);
        
        it.businesslogic.ireport.util.Misc.centerFrame(this);
        
        javax.swing.KeyStroke escape =  javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_ESCAPE, 0, false);
        javax.swing.Action escapeAction = new javax.swing.AbstractAction() {
            public void actionPerformed(java.awt.event.ActionEvent e) {
                jButton2ActionPerformed(e);
            }
        };
        
        DocumentListener dl = new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                updateCustomLocale();
            }
            public void insertUpdate(DocumentEvent e) {
                updateCustomLocale();
            }
            public void removeUpdate(DocumentEvent e) {
                updateCustomLocale();
            }
        };
        
        jTextFieldCountry.getDocument().addDocumentListener(dl);
        jTextFieldLanguage.getDocument().addDocumentListener(dl);
        jTextFieldVariant.getDocument().addDocumentListener(dl);
       
        getRootPane().getInputMap(javax.swing.JComponent.WHEN_IN_FOCUSED_WINDOW).put(escape, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);

        applyI18n();
        //to make the default button ...
        this.getRootPane().setDefaultButton(this.jButton1);
     }
    
    public int getDialogResult() {
        return dialogResult;
    }

    public void setDialogResult(int dialogResult) {
        this.dialogResult = dialogResult;
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jListLocales = new javax.swing.JList();
        jPanelCustom = new javax.swing.JPanel();
        jLabelLanguage = new javax.swing.JLabel();
        jTextFieldLanguage = new javax.swing.JTextField();
        jLabelLanguageTip = new javax.swing.JLabel();
        jLabelCountry = new javax.swing.JLabel();
        jTextFieldCountry = new javax.swing.JTextField();
        jLabelCountryTip = new javax.swing.JLabel();
        jLabelVariant = new javax.swing.JLabel();
        jTextFieldVariant = new javax.swing.JTextField();
        jLabelVariantTip = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jButtonDefault = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        getContentPane().setLayout(new java.awt.GridBagLayout());

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        jPanel2.setLayout(new java.awt.GridBagLayout());

        jPanel2.setMinimumSize(new java.awt.Dimension(350, 80));
        jPanel2.setPreferredSize(new java.awt.Dimension(400, 300));
        jListLocales.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jListLocalesValueChanged(evt);
            }
        });

        jScrollPane1.setViewportView(jListLocales);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        jPanel2.add(jScrollPane1, gridBagConstraints);

        jTabbedPane1.addTab("Predefined locales", jPanel2);
        jPanel2.getAccessibleContext().setAccessibleName("Locale");

        jPanelCustom.setLayout(new java.awt.GridBagLayout());

        jLabelLanguage.setText("Language");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(16, 8, 0, 4);
        jPanelCustom.add(jLabelLanguage, gridBagConstraints);

        jTextFieldLanguage.setMinimumSize(new java.awt.Dimension(90, 19));
        jTextFieldLanguage.setPreferredSize(new java.awt.Dimension(90, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(14, 0, 0, 4);
        jPanelCustom.add(jTextFieldLanguage, gridBagConstraints);

        jLabelLanguageTip.setText("<html>ISO Language Code (lower case, two-letter code. i.e. \"en\")");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(16, 0, 0, 8);
        jPanelCustom.add(jLabelLanguageTip, gridBagConstraints);

        jLabelCountry.setText("Country");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 4);
        jPanelCustom.add(jLabelCountry, gridBagConstraints);

        jTextFieldCountry.setMinimumSize(new java.awt.Dimension(90, 19));
        jTextFieldCountry.setPreferredSize(new java.awt.Dimension(90, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 4);
        jPanelCustom.add(jTextFieldCountry, gridBagConstraints);

        jLabelCountryTip.setText("<html>Optional ISO Country Code (upper case, two-letter code, i.e. \"US\")");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 8);
        jPanelCustom.add(jLabelCountryTip, gridBagConstraints);

        jLabelVariant.setText("Variant");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(6, 8, 0, 4);
        jPanelCustom.add(jLabelVariant, gridBagConstraints);

        jTextFieldVariant.setMinimumSize(new java.awt.Dimension(90, 19));
        jTextFieldVariant.setPreferredSize(new java.awt.Dimension(90, 19));
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 1;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 0, 4);
        jPanelCustom.add(jTextFieldVariant, gridBagConstraints);

        jLabelVariantTip.setText("<html>Optional specific code");
        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 2;
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.anchor = java.awt.GridBagConstraints.NORTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(6, 0, 0, 8);
        jPanelCustom.add(jLabelVariantTip, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.weighty = 1.0;
        jPanelCustom.add(jPanel3, gridBagConstraints);

        jTabbedPane1.addTab("Custom locale", jPanelCustom);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weighty = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 4, 4, 4);
        getContentPane().add(jTabbedPane1, gridBagConstraints);

        jPanel1.setLayout(new java.awt.GridBagLayout());

        jButtonDefault.setText("System default");
        jButtonDefault.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed1(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        jPanel1.add(jButtonDefault, gridBagConstraints);

        jButton1.setText("OK");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        jPanel1.add(jButton1, gridBagConstraints);

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.anchor = java.awt.GridBagConstraints.EAST;
        gridBagConstraints.insets = new java.awt.Insets(4, 0, 4, 4);
        jPanel1.add(jButton2, gridBagConstraints);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.gridx = 0;
        gridBagConstraints.fill = java.awt.GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        getContentPane().add(jPanel1, gridBagConstraints);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed1(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed1

        this.setSelectedLocale( Locale.getDefault());
        jButton1ActionPerformed(evt);
       
    }//GEN-LAST:event_jButton1ActionPerformed1

    private void jListLocalesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_jListLocalesValueChanged

        updating = true;
        try {
            if (jListLocales.getSelectedIndex() >=0) 
            {
                jButton1.setEnabled(true);
                if (jListLocales.getSelectedIndex() > 0)
                {
                    Locale locale = (Locale)((Tag)jListLocales.getSelectedValue()).getValue();
                    jTextFieldLanguage.setText( locale.getLanguage() );
                    jTextFieldCountry.setText( locale.getCountry() );
                    jTextFieldVariant.setText( locale.getVariant() );
                }
                else
                {
                    jTextFieldLanguage.setText("");
                    jTextFieldCountry.setText("");
                    jTextFieldVariant.setText("");
                }
            }
        } finally
        {
            updating = false;
        }
        
    }//GEN-LAST:event_jListLocalesValueChanged

    private void updateCustomLocale()
    {
        if (updating) return;
        jListLocales.clearSelection();
        
        if (jTextFieldLanguage.getText().trim().length() == 0)
        {
            jButton1.setEnabled(false);
        }
    }
    
    
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
       this.setDialogResult( javax.swing.JOptionPane.CANCEL_OPTION);
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        

        if (jListLocales.getSelectedIndex() < 0)
        {
            this.selectedLocale = null;
            String language = jTextFieldLanguage.getText().trim();
            String country = jTextFieldCountry.getText().trim();
            String variant = jTextFieldVariant.getText().trim();

            if (language != null && language.trim().length() > 0)
            {
                if (country != null && country.trim().length() > 0)
                {
                    if (variant != null && variant.trim().length() > 0)
                    {
                        selectedLocale = new Locale(language, country, variant);
                    }
                    else
                    {
                        selectedLocale = new Locale(language, country);
                    }
                }
                else
                {
                    selectedLocale = new Locale(language);
                }
            }
        }
        else if (jListLocales.getSelectedIndex() == 0)
        {
            this.selectedLocale = null;
        }
        else
        {
            this.selectedLocale = (Locale)((Tag)jListLocales.getSelectedValue()).getValue(); 
        }   

        this.setDialogResult(javax.swing.JOptionPane.OK_OPTION);
        
        this.setVisible(false);
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButtonDefault;
    private javax.swing.JLabel jLabelCountry;
    private javax.swing.JLabel jLabelCountryTip;
    private javax.swing.JLabel jLabelLanguage;
    private javax.swing.JLabel jLabelLanguageTip;
    private javax.swing.JLabel jLabelVariant;
    private javax.swing.JLabel jLabelVariantTip;
    private javax.swing.JList jListLocales;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanelCustom;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextFieldCountry;
    private javax.swing.JTextField jTextFieldLanguage;
    private javax.swing.JTextField jTextFieldVariant;
    // End of variables declaration//GEN-END:variables
    
    public void applyI18n(){
                // Start autogenerated code ----------------------
                jButton1.setText(I18n.getString("localeSelectorDialog.button1","OK"));
                jButton2.setText(I18n.getString("localeSelectorDialog.button2","Cancel"));
                
                jButton1.setMnemonic(I18n.getString("localeSelectorDialog.button1Mnemonic","o").charAt(0));
                jButton2.setMnemonic(I18n.getString("localeSelectorDialog.button2Mnemonic","c").charAt(0));
                
                jButtonDefault.setText(I18n.getString("localeSelectorDialog.buttonDefault","System default"));
                
                setTitle( I18n.getString("localeSelectorDialog.title","Locale selection") );
                jLabelCountry.setText( I18n.getString("localeSelectorDialog.labelCountry","Country" ) );
                jLabelLanguage.setText( I18n.getString("localeSelectorDialog.labelLanguage","Language" ) );
                jLabelVariant.setText( I18n.getString("localeSelectorDialog.labelVariant","Variant" ) );
                
                jLabelCountryTip.setText("<html>" + I18n.getString("localeSelectorDialog.labelCountryTip","Optional ISO Country Code (upper case, two-letter code, i.e. \"US\")" ) );
                jLabelLanguageTip.setText( "<html>" + I18n.getString("localeSelectorDialog.labelLanguageTip","ISO Language Code (lower case, two-letter code. i.e. \"en\")" ) );
                jLabelVariantTip.setText( "<html>" + I18n.getString("localeSelectorDialog.labelVariantTip","Optional specific code" ) );
                
                jTabbedPane1.setTitleAt(0, I18n.getString("localeSelectorDialog.tab.predefinedLocales","Predefined locales" ) );
                jTabbedPane1.setTitleAt(1, I18n.getString("localeSelectorDialog.tab.customeLocale","Custom locale" ) );

    }

    public Locale getSelectedLocale() {
        return selectedLocale;
    }

    public void setSelectedLocale(Locale selectedLocale) {
        this.selectedLocale = selectedLocale;
        
        for (int i=1; i<jListLocales.getModel().getSize(); ++i)
        {
            Locale loc = (Locale)((Tag)jListLocales.getModel().getElementAt(i)).getValue();
            if (loc.equals(selectedLocale))
            {
                jListLocales.setSelectedIndex(i);
                jListLocales.ensureIndexIsVisible(i);
                return;
            }
        }
        
        jListLocales.clearSelection();
        jTextFieldLanguage.setText( selectedLocale.getLanguage() );
        jTextFieldCountry.setText( selectedLocale.getCountry() );
        jTextFieldVariant.setText( selectedLocale.getVariant() );
        jTabbedPane1.setSelectedIndex(1);
        
    }
    
    public String getSelectedLocaleId()
    {
        Locale loc = getSelectedLocale();
        if (loc == null) return null;
        String s = loc.getLanguage();
        if (loc.getCountry().length() > 0)
        {
            s += "_" + loc.getCountry();
            if (loc.getVariant().length() >0)
            {
                s += "_" + loc.getVariant();
            }
        }
        return s;
    }
    
    public void setSelectedLocaleId(String s)
    {
         if (s == null) return;
         Locale newLocale = Misc.getLocaleFromString(s, null);
         
          if (newLocale != null)
          {
             setSelectedLocale( newLocale );
          }
    }
    
}
