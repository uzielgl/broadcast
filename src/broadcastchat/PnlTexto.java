/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package broadcastchat;

import java.awt.Component;
import javax.swing.JFrame;

/**
 *
 * @author Dai
 */
public class PnlTexto extends javax.swing.JPanel {
    public MainWindow parent;
    /*public static void main(String[] args){
     * JFrame f = new JFrame();
     * PnlTexto texto = new PnlTexto();
     * f.add(texto);
     * f.setVisible(true);
     * }*/

    /**
     * Creates new form PnlVideo
     */
    public PnlTexto() {
        initComponents();
         jScrollPane1.getVerticalScrollBar().setValue(jScrollPane1.getVerticalScrollBar().getMaximum());
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlRecepcionVideo = new javax.swing.JPanel();
        pnlEnviarTexto = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtEnviarTexto = new javax.swing.JTextArea();
        lblRecibiendoAudio = new javax.swing.JLabel();
        btnEnviarTexto = new javax.swing.JButton();

        pnlRecepcionVideo.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recepción Video", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        javax.swing.GroupLayout pnlRecepcionVideoLayout = new javax.swing.GroupLayout(pnlRecepcionVideo);
        pnlRecepcionVideo.setLayout(pnlRecepcionVideoLayout);
        pnlRecepcionVideoLayout.setHorizontalGroup(
            pnlRecepcionVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 226, Short.MAX_VALUE)
        );
        pnlRecepcionVideoLayout.setVerticalGroup(
            pnlRecepcionVideoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        pnlEnviarTexto.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Enviando Texto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        txtEnviarTexto.setColumns(20);
        txtEnviarTexto.setRows(5);
        jScrollPane1.setViewportView(txtEnviarTexto);

        javax.swing.GroupLayout pnlEnviarTextoLayout = new javax.swing.GroupLayout(pnlEnviarTexto);
        pnlEnviarTexto.setLayout(pnlEnviarTextoLayout);
        pnlEnviarTextoLayout.setHorizontalGroup(
            pnlEnviarTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
        );
        pnlEnviarTextoLayout.setVerticalGroup(
            pnlEnviarTextoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );

        lblRecibiendoAudio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblRecibiendoAudio.setText("Recibiendo Audio");

        btnEnviarTexto.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEnviarTexto.setText("Iniciar Envio de texto");
        btnEnviarTexto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarTextoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlRecepcionVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(72, 72, 72)
                        .addComponent(lblRecibiendoAudio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlEnviarTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(btnEnviarTexto)
                .addGap(55, 55, 55))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlEnviarTexto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlRecepcionVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(lblRecibiendoAudio)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEnviarTexto)
                .addContainerGap(20, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarTextoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarTextoActionPerformed
        // TODO add your handling code here:
        Texto texto = new Texto();
        texto.iniciarEnvio( this );
    }//GEN-LAST:event_btnEnviarTextoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarTexto;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblRecibiendoAudio;
    private javax.swing.JPanel pnlEnviarTexto;
    public javax.swing.JPanel pnlRecepcionVideo;
    private javax.swing.JTextArea txtEnviarTexto;
    // End of variables declaration//GEN-END:variables
}
