/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package broadcastchat;

/**
 *
 * @author Dai
 */
public class PnlAudio extends javax.swing.JPanel {
    public MainWindow parent;
    /**
     * Creates new form PnlVideo
     */
    public PnlAudio() {
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

        btnEnviarAudio = new javax.swing.JButton();
        pnlRecepcionVideo = new javax.swing.JPanel();
        pnlHistorial = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtRecepcionTexto = new javax.swing.JTextArea();

        btnEnviarAudio.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnEnviarAudio.setText("Enviar Audio");
        btnEnviarAudio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEnviarAudioActionPerformed(evt);
            }
        });

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

        pnlHistorial.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Recepción Texto", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 12))); // NOI18N

        txtRecepcionTexto.setColumns(20);
        txtRecepcionTexto.setRows(5);
        jScrollPane1.setViewportView(txtRecepcionTexto);

        javax.swing.GroupLayout pnlHistorialLayout = new javax.swing.GroupLayout(pnlHistorial);
        pnlHistorial.setLayout(pnlHistorialLayout);
        pnlHistorialLayout.setHorizontalGroup(
            pnlHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
        );
        pnlHistorialLayout.setVerticalGroup(
            pnlHistorialLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 273, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(btnEnviarAudio))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(pnlRecepcionVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlHistorial, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(pnlRecepcionVideo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEnviarAudio, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 27, Short.MAX_VALUE)))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnEnviarAudioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEnviarAudioActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnEnviarAudioActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEnviarAudio;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlHistorial;
    public javax.swing.JPanel pnlRecepcionVideo;
    public javax.swing.JTextArea txtRecepcionTexto;
    // End of variables declaration//GEN-END:variables
}
