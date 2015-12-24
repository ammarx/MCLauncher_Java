/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkg_launcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import javax.swing.DefaultListModel;
import sk.tomsik68.mclauncher.api.common.IObservable;
import sk.tomsik68.mclauncher.api.common.IObserver;
import sk.tomsik68.mclauncher.api.common.mc.MinecraftInstance;
import sk.tomsik68.mclauncher.api.versions.IVersion;
import sk.tomsik68.mclauncher.impl.versions.mcdownload.MCDownloadVersionList;
import java.util.logging.Logger;
import sk.tomsik68.mclauncher.api.common.MCLauncherAPI;

/**
 *
 * @author ammar
 */
public class Download extends javax.swing.JFrame {

    /**
     * Creates new form Download
     */
    
    
       public void test3() {
        final MCDownloadVersionList list = new MCDownloadVersionList();
        final MinecraftInstance mc = new MinecraftInstance(new File("testmc"));
        list.addObserver(new IObserver<String>() {
            private boolean installed = false;

            @Override
            public void onUpdate(IObservable<String> observable, String id) {
                id = (String) jComboBox1.getSelectedItem();
                
                IVersion changed = null;
                try {
                    changed = list.retrieveVersionInfo(id);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("Version "+changed.getId());
                if(installed) return;
                installed = true;
                System.out.println("Installing " + changed.getDisplayName());
                try {
                    changed.getInstaller().install(changed, mc, null);
                } catch (Exception e) {
                    e.printStackTrace();
                    //fail();
                }
            }
        });
        try {
          
            list.startDownload();
          
        } catch (Exception e) {
            e.printStackTrace();
            //fail();

        }
    }
    
    
    
    
    public void test1() {
        jComboBox1.removeAllItems();
        
        MCDownloadVersionList list = new MCDownloadVersionList();
        list.addObserver(new IObserver<String>() {
            @Override
            public void onUpdate(IObservable<String> observable, String changed) {
                System.out.println(changed);
                jComboBox1.addItem(changed);
            }
        });
        try {
            list.startDownload();
        } catch (Exception e) {
            e.printStackTrace();
        }
        

    }


         private static List strHolder = new ArrayList();
    
    public Download() {
        initComponents();
         jComboBox1.removeAllItems();
        jComboBox1.enable(false);
        

 MCLauncherAPI.log.addHandler(new Handler() {
      @Override
      public void publish(LogRecord logRecord) {
        //strHolder.add(logRecord.getLevel() + ":");
        //strHolder.add(logRecord.getSourceClassName() + ":");
        //strHolder.add(logRecord.getSourceMethodName() + ":");
        //strHolder.add("<" + logRecord.getMessage() + ">");
        //strHolder.add("\n");
        jLabel3.setText(logRecord.getMessage());
        
      }

      @Override
      public void flush() {
      }

      @Override
      public void close() {
      }
    });        

        ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                test1(); //version list (FULL LIST)
                jComboBox1.enable(true);
                return null;
            }
        } );
        executor.shutdown();
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Download");
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closing(evt);
            }
        });

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setText("List of available versions");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jButton1.setText("Download Version");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Status of download shown here...");
        jLabel3.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                jLabel3PropertyChange(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 479, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addGap(12, 12, 12)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_closing
        // TODO add your handling code here:    
    }//GEN-LAST:event_closing

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
         ExecutorService executor = Executors.newCachedThreadPool();
        executor.submit(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                test3(); //download latest (IF NO VERSION GIVEN..)
                //test4(); //installing...
                
                jLabel3.setText("DONE!");
        
                return null;
            }
        }
        );
        executor.shutdown();

    }//GEN-LAST:event_jButton1ActionPerformed

    private void jLabel3PropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_jLabel3PropertyChange
        // TODO add your handling code here:
        //if property changes...
        //String NewX = jLabel3.getText();
        //String OldX = jTextPane1.getText();
        //String FinalX = OldX + NewX;
        
        //jTextPane1.setText(FinalX);
        
    }//GEN-LAST:event_jLabel3PropertyChange

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Download.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Download.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Download.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Download.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Download().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
}