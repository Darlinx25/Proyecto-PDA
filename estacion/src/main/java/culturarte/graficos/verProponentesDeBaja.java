/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package culturarte.graficos;
import culturarte.datatypes.DTProponente;
import culturarte.datatypes.DTPropuesta;
import culturarte.logica.EstadoPropuesta;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import culturarte.logica.TipoRetorno;
import static culturarte.logica.TipoRetorno.ENTRADA_GRATIS;
import static culturarte.logica.TipoRetorno.PORCENTAJE_GANANCIAS;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
/**
 *
 * @author faxcundo
 */
public class verProponentesDeBaja extends javax.swing.JInternalFrame {

     private IController controller;

    public verProponentesDeBaja() {
        IControllerFactory fabrica = IControllerFactory.getInstance();
        this.controller = fabrica.getIController();
        initComponents();

        DefaultListModel<String> modelo = (DefaultListModel<String>) listaProponentes.getModel();
        modelo.clear();
        for (String nick : controller.listaProponentesDeBaja()) {
            modelo.addElement(nick);
        }

        listaProponentes.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String seleccionado = listaProponentes.getSelectedValue();
                if (seleccionado != null) {
                    DTProponente dt = controller.obtenerDTProponenteDeBaja(seleccionado);
                    if (dt != null) {
                        campoNick.setText(dt.getNickname());
                        campoNombre.setText(dt.getNombre());
                        campoApellido.setText(dt.getApellido());
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fechaNacimiento = dt.getFechaNacimiento();
                        if (fechaNacimiento == null) {
                            campoFechaNac.setText("");
                        } else {
                            campoFechaNac.setText(fechaNacimiento.format(formatter));
                        }

                        campoCorreo.setText(dt.getEmail());
                        areaBiografia.setText(dt.getBiografia());
                        campoSitioWeb.setText(dt.getSitioWeb());

                        String basePath = System.getProperty("user.dir") + "/imagenesUsuarios/";
                        String imagen = dt.getImagen();

                        if (imagen != null && !imagen.isEmpty()) {
                            File archivoImagen = new File(basePath + imagen);
                            if (archivoImagen.exists()) {
                                try {
                                    BufferedImage temp = ImageIO.read(archivoImagen);
                                    Image imagenEscalada = temp.getScaledInstance(133, 133, Image.SCALE_SMOOTH);
                                    this.labelImagen.setIcon(new ImageIcon(imagenEscalada));
                                } catch (IOException ex) {
                                    System.getLogger(ConsultarColaborador.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                                }
                            } else {
                                this.labelImagen.setIcon(null);
                            }
                        } else {
                            this.labelImagen.setIcon(null);
                        }
                        //sigue aca!!!
                        campoDineroRecaudado.setText("");
                        listaColaboradores.setModel(new DefaultListModel<>());
                        String nickdelp = dt.getNickname();
                        DefaultListModel<String> modelo1 = (DefaultListModel<String>) listaPropuestas.getModel();
                        modelo1.clear();
                        for (String nick1 : controller.listaPropuestasUsuDeBaja(nickdelp)) {
                            DTPropuesta dtprop = controller.obtenerDTPropuestaDeBaja(nick1);
                            EstadoPropuesta ese = dtprop.getEstadoActual().getEstado();
                            String estado = ese.toString();
                            modelo1.addElement(nick1 + " - " + estado);

                        }
                        listaPropuestas.addListSelectionListener(i -> {
                            if (!i.getValueIsAdjusting()) {
                                String seleccionado2 = listaPropuestas.getSelectedValue();
                                if (seleccionado2 != null) {
                                    String[] partes = seleccionado2.split(" - ");
                                    String nombrePropuesta = partes[0];
                                    campoDineroRecaudado.setText(controller.obtenerDineroRecaudadoDeBaja(nombrePropuesta));
                                    DefaultListModel<String> modeloC = new DefaultListModel<>();
                                    for (String nickColaborador : controller.obtenerColaboradoresColaboracion(nombrePropuesta)) {
                                        modeloC.addElement(nickColaborador);
                                    }
                                    listaColaboradores.setModel(modeloC);
                                }
                            }
                        });

                    }
                }
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        campoNick = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        campoNombre = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        campoSitioWeb = new javax.swing.JTextField();
        campoApellido = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaColaboradores = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaProponentes = listaProponentes = new javax.swing.JList<>(new javax.swing.DefaultListModel<>());
        campoFechaNac = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Proponentes = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        campoCorreo = new javax.swing.JTextField();
        campoDineroRecaudado = new javax.swing.JTextField();
        labelImagen = new javax.swing.JLabel();
        Proponentes1 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        areaBiografia = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaPropuestas = listaPropuestas = new javax.swing.JList<>(new javax.swing.DefaultListModel<>());
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();

        setClosable(true);
        setMaximizable(true);
        setResizable(true);

        campoNick.setEditable(false);
        campoNick.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNickActionPerformed(evt);
            }
        });

        jLabel6.setText("Biografia:");

        campoNombre.setEditable(false);
        campoNombre.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoNombreActionPerformed(evt);
            }
        });

        jLabel7.setText("Sitio Web:");

        jLabel3.setText("Apellido:");

        campoSitioWeb.setEditable(false);
        campoSitioWeb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoSitioWebActionPerformed(evt);
            }
        });

        campoApellido.setEditable(false);
        campoApellido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoApellidoActionPerformed(evt);
            }
        });

        jLabel9.setText("Colaboradores: ");

        jLabel4.setText("Fecha nacimiento:");

        listaColaboradores.setEnabled(false);
        jScrollPane2.setViewportView(listaColaboradores);

        jScrollPane1.setViewportView(listaProponentes);

        campoFechaNac.setEditable(false);
        campoFechaNac.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoFechaNacActionPerformed(evt);
            }
        });

        jLabel5.setText("Corre electrínico:");

        Proponentes.setText("Proponentes");

        jLabel8.setText("Dinero Recaudado :");

        campoCorreo.setEditable(false);
        campoCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoCorreoActionPerformed(evt);
            }
        });

        campoDineroRecaudado.setEditable(false);
        campoDineroRecaudado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoDineroRecaudadoActionPerformed(evt);
            }
        });

        labelImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        labelImagen.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        Proponentes1.setText("Propuestas");

        areaBiografia.setEditable(false);
        areaBiografia.setColumns(20);
        areaBiografia.setLineWrap(true);
        areaBiografia.setRows(5);
        jScrollPane4.setViewportView(areaBiografia);

        jLabel1.setText("Nickname:");

        jScrollPane3.setViewportView(listaPropuestas);

        jLabel2.setText("Nombre:");

        jButton1.setText("Alta");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Proponentes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(30, 30, 30)
                        .addComponent(jButton1)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(Proponentes1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(36, 36, 36)
                                .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel8)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(campoDineroRecaudado, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1)))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(campoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(campoFechaNac, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(campoCorreo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(campoNick, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(campoSitioWeb, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(88, 88, 88))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(campoNick, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(campoNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(campoApellido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(campoFechaNac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(campoCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addGap(112, 112, 112))
                            .addComponent(jScrollPane4)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Proponentes, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Proponentes1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                        .addComponent(jLabel8)
                                        .addComponent(campoDineroRecaudado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(jLabel7)
                                    .addComponent(campoSitioWeb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 147, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jButton1)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoNickActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNickActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNickActionPerformed

    private void campoNombreActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoNombreActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoNombreActionPerformed

    private void campoSitioWebActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoSitioWebActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoSitioWebActionPerformed

    private void campoApellidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoApellidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoApellidoActionPerformed

    private void campoFechaNacActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoFechaNacActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoFechaNacActionPerformed

    private void campoCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoCorreoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoCorreoActionPerformed

    private void campoDineroRecaudadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoDineroRecaudadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoDineroRecaudadoActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        String seleccionado = listaProponentes.getSelectedValue();
        System.out.println(seleccionado);
        this.controller.altaProponente(seleccionado);
        this.dispose();
        
    }//GEN-LAST:event_jButton1ActionPerformed
            

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Proponentes;
    private javax.swing.JLabel Proponentes1;
    private javax.swing.JTextArea areaBiografia;
    private javax.swing.JTextField campoApellido;
    private javax.swing.JTextField campoCorreo;
    private javax.swing.JTextField campoDineroRecaudado;
    private javax.swing.JTextField campoFechaNac;
    private javax.swing.JTextField campoNick;
    private javax.swing.JTextField campoNombre;
    private javax.swing.JTextField campoSitioWeb;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JList<String> listaColaboradores;
    private javax.swing.JList<String> listaProponentes;
    private javax.swing.JList<String> listaPropuestas;
    // End of variables declaration//GEN-END:variables
}
