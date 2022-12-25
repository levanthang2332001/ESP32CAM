/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.firebaseesp32;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import java.io.IOException;
import com.google.firebase.database.ValueEventListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.google.gson.*;
import java.util.Iterator;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import java.text.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author ASUS
 */
public class frmServer extends javax.swing.JFrame {

    RealtimeFirebase fbs = null;
    
    DatabaseReference ref = null;

    private static JSONObject jObject = null;

    private Date firebaseLastConnectedTime = null;

    private boolean isConnected = false;

    /**
     * Creates new form frmClient
     */
    public frmServer() throws IOException {
        initComponents();

    }

    public void run() {
        try {
            this.fbs = new RealtimeFirebase();

            this.ref = fbs.getDb()
                    .getReference("/diemdanh");
            this.ref.addValueEventListener(new ValueEventListener() {
                public void onDataChange(DataSnapshot dataSnapshot) {
                    handleDataChange(dataSnapshot);
                }

                public void onCancelled(DatabaseError error) {
                    System.out.print("Error: " + error.getMessage());
                }
            });

            this.firebaseLastConnectedTime = new Date();
            this.isConnected = true;
            this.jButton1.setText("Disconnect");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Kết nối Firebase thất bại!");
            e.printStackTrace();
        }

    }

    public long convertEpochTime(String time) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy - HH:mm:SS");
        Date date = df.parse(time);
        long epoch = date.getTime();
        return epoch;

    }

    public String convertEpochTime(int time) {
        Date date = new Date(time);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        return formatted;

    }

    public boolean checkId(String str) {
        if (str != null) {
            int length = str.length();
            String id = str.substring(4, length - 1);
            if (id.length() == 10) {
                return true;
            }
            return false;
        }
        return false;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jProgressBar1 = new javax.swing.JProgressBar();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listData = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        cbbTime = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jButton1.setText("Connection");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        listData.setColumns(20);
        listData.setRows(5);
        jScrollPane1.setViewportView(listData);

        jButton2.setText("Thoát");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        cbbTime.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "1 Phút", "5 Phút", "10 Phút", "15 Phút", "30 Phút" }));
        cbbTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbbTimeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(cbbTime, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(67, 67, 67)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbbTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 39, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void handleDataChange(DataSnapshot dataSnapshot) {
        Object document = dataSnapshot.getValue();
        String dc = document.toString();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement je = JsonParser.parseString(dc);
        String prettyJsonString = gson.toJson(je).trim();

        String el = "{ data: " + prettyJsonString + "}";
        JSONObject ob = new JSONObject(el);

        JSONObject songs = ob.getJSONObject("data");
        Iterator x = songs.keys();

        String resultData = "";
        while (x.hasNext()) {
            String key = (String) x.next();

            if (key.equals("1")) {
                continue;
            }

            String k = String.valueOf(songs.get(key));
            k = k.substring(1, k.length() - 1);

            String[] arrTime = k.split(",");

            String lastScanTime = arrTime[arrTime.length - 1];

            long epoch = Long.parseLong(lastScanTime);
            Date date = new Date(epoch * 1000);
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy - HH:mm:SS");
            String dates = DATE_FORMAT.format(date);
            
            // Filter the time after the Firebase is initialized
            Calendar initCal = Calendar.getInstance();
            initCal.setTime(this.firebaseLastConnectedTime);
            
            Calendar scanCal = Calendar.getInstance();
            scanCal.setTime(date);
            
            if (initCal.compareTo(scanCal) >= 0) {
                continue;
            }

            if (this.isLated(date)) {
                resultData += (key + " : " + dates + " (Lated)" + "\n");
            } else {
                resultData += (key + " : " + dates + "\n");
            }
        }

        updateTextArea(resultData);
    }

    private int getComboBoxNumber() {
        String selected = this.cbbTime.getSelectedItem().toString();
        String mystr = selected.replaceAll("[^\\d]", "");
        return Integer.parseInt(mystr);
    }

    private boolean isLated(Date scanTimeRaw) {
        // Get calendar object from firebase's last connected time
        Calendar lastConn = Calendar.getInstance();
        lastConn.setTime(this.firebaseLastConnectedTime);
        lastConn.add(Calendar.MINUTE, this.getComboBoxNumber());

        // Get calendar object from scan time
        Calendar scanTime = Calendar.getInstance();
        scanTime.setTime(scanTimeRaw);

        return lastConn.compareTo(scanTime) < 0;
    }

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if (this.isConnected) {
            this.ref.onDisconnect();
            this.fbs.db.goOffline();
            
            this.fbs = null;
            this.jButton1.setText("Connect");
            this.isConnected = false;
            return;
        }
        
        run();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        System.exit(0);
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton2ActionPerformed

    private void cbbTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbbTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbbTimeActionPerformed

    public void updateTextArea(String data) {
        listData.setText(data);
    }

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
            java.util.logging.Logger.getLogger(frmServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmServer.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new frmServer().setVisible(true);
                } catch (IOException ex) {
                    Logger.getLogger(frmServer.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> cbbTime;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea listData;
    // End of variables declaration//GEN-END:variables
}
