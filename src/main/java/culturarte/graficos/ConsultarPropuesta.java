/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package culturarte.graficos;

import culturarte.logica.DTPropuesta;
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
 * @author kevin
 */
public class ConsultarPropuesta extends javax.swing.JInternalFrame {

    private IController controller;
    private BufferedImage imagenPropuesta;

    public ConsultarPropuesta() {
        IControllerFactory fabrica = IControllerFactory.getInstance();
        this.controller = fabrica.getIController();
        this.imagenPropuesta = null;
        initComponents();

        DefaultListModel<String> modelo = (DefaultListModel<String>) listPropuestas.getModel();
        modelo.clear();
        for (String nombreProp : controller.listarPropuestas()) {
            modelo.addElement(nombreProp);
        }
        listPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String seleccionado = listPropuestas.getSelectedValue();
                if (seleccionado != null) {
                    DTPropuesta DTProp = controller.obtenerDTPropuesta(seleccionado);
                    areaDescripcion.setText(DTProp.getDescripcion());
                    campoLugar.setText(DTProp.getLugarRealizara());
                    campoPrecio.setText(Float.toString(DTProp.getPrecioEntrada()));
                    campoMontoReunir.setText(Float.toString(DTProp.getMontoAReunir()));
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                    LocalDate fechaRealizar = DTProp.getFechaRealizara();
                    if (fechaRealizar == null) {
                        campoFechaRealizar.setText("");
                    } else {
                        campoFechaRealizar.setText(fechaRealizar.format(formatter));
                    }
                    LocalDate fechaPublicacion = DTProp.getFechaPublicacion();
                    if (fechaPublicacion == null) {
                        campoFechaPublicacion.setText("no hay publicacion");
                    } else {
                        campoFechaPublicacion.setText(fechaPublicacion.format(formatter));
                    }
                    campoCategoria.setText(DTProp.getTipoPropuesta());
                    EstadoPropuesta ese = DTProp.getEstadoActual().getEstado();
                    String estado = ese.toString();
                    campoEstado.setText(estado);
                    DefaultListModel<String> modeloC = new DefaultListModel<>();
                    for (String nickColaborador : controller.obtenerColaboradoresColaboracion(seleccionado)) {
                        modeloC.addElement(nickColaborador);
                    }
                    Colaboradores.setModel(modeloC);
                    String basePath = System.getProperty("user.dir") + "/imagenesUsuarios/";
                    String imagen = DTProp.getImagen();

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
                    List<TipoRetorno> aux = DTProp.getTiposRetorno();
                    this.retornoGratis.setSelected(false);
                    this.retornoGanancia.setSelected(false);
                    for (TipoRetorno item : aux) {

                        switch (item) {
                            case ENTRADA_GRATIS:
                                this.retornoGratis.setSelected(true);
                                break;
                            case PORCENTAJE_GANANCIAS:
                                this.retornoGanancia.setSelected(true);
                                break;
                        }
                    }
                    dineroRecaudado.setText(controller.obtenerDineroRecaudado(DTProp.getTitulo()));
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

        jScrollPane3 = new javax.swing.JScrollPane();
        listPropuestas = listPropuestas = new javax.swing.JList<>(new javax.swing.DefaultListModel<>());
        Proponentes1 = new javax.swing.JLabel();
        campoPrecio = new javax.swing.JTextField();
        labelPrecio = new javax.swing.JLabel();
        campoFechaPublicacion = new javax.swing.JTextField();
        campoMontoReunir = new javax.swing.JTextField();
        labelFechaPublicacion = new javax.swing.JLabel();
        labelDescripcion = new javax.swing.JLabel();
        labelMontoReunir = new javax.swing.JLabel();
        campoCategoria = new javax.swing.JTextField();
        campoLugar = new javax.swing.JTextField();
        labelCategoria = new javax.swing.JLabel();
        labelLugar = new javax.swing.JLabel();
        campoFechaRealizar = new javax.swing.JTextField();
        labelFechaRealizar = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        areaDescripcion = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        campoEstado = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Colaboradores = new javax.swing.JList<>();
        labelImagen = new javax.swing.JLabel();
        retornoGanancia = new javax.swing.JCheckBox();
        labelRetorno = new javax.swing.JLabel();
        retornoGratis = new javax.swing.JCheckBox();
        dineroRecaudado = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setPreferredSize(new java.awt.Dimension(1000, 600));

        jScrollPane3.setViewportView(listPropuestas);

        Proponentes1.setText("Propuestas");

        campoPrecio.setEditable(false);
        campoPrecio.setCaretColor(new java.awt.Color(255, 255, 255));

        labelPrecio.setText("Precio de entrada:");

        campoFechaPublicacion.setEditable(false);
        campoFechaPublicacion.setCaretColor(new java.awt.Color(255, 255, 255));

        campoMontoReunir.setEditable(false);
        campoMontoReunir.setCaretColor(new java.awt.Color(255, 255, 255));

        labelFechaPublicacion.setText("Fecha de publicación:");

        labelDescripcion.setText("Descripción:");

        labelMontoReunir.setText("Monto a reunir:");

        campoCategoria.setEditable(false);
        campoCategoria.setCaretColor(new java.awt.Color(255, 255, 255));

        campoLugar.setEditable(false);
        campoLugar.setCaretColor(new java.awt.Color(255, 255, 255));

        labelCategoria.setText("Categoría:");

        labelLugar.setText("Lugar a realizar:");

        campoFechaRealizar.setEditable(false);
        campoFechaRealizar.setCaretColor(new java.awt.Color(255, 255, 255));

        labelFechaRealizar.setText("Fecha a realizar:");

        areaDescripcion.setEditable(false);
        areaDescripcion.setColumns(20);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setRows(5);
        areaDescripcion.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane4.setViewportView(areaDescripcion);

        jLabel1.setText("Estado :");

        campoEstado.setEditable(false);
        campoEstado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoEstadoActionPerformed(evt);
            }
        });

        jLabel9.setText("Colaboradores: ");

        jScrollPane2.setViewportView(Colaboradores);

        retornoGanancia.setText("Porcentaje de Ganancias");
        retornoGanancia.setEnabled(false);

        labelRetorno.setText("Tipo de Retorno:");

        retornoGratis.setText("Entrada Gratis");
        retornoGratis.setEnabled(false);

        dineroRecaudado.setEditable(false);
        dineroRecaudado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                dineroRecaudadoActionPerformed(evt);
            }
        });

        jLabel8.setText("Dinero Recaudado :");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addComponent(Proponentes1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addGap(59, 59, 59)
                .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(labelDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jScrollPane4)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(labelMontoReunir, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelFechaRealizar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(labelFechaPublicacion, javax.swing.GroupLayout.Alignment.LEADING))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(campoLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addComponent(campoFechaRealizar, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                            .addGap(18, 18, 18)
                                            .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(campoMontoReunir, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(campoFechaPublicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(campoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel1)
                                .addComponent(jLabel8))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(campoEstado, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                .addComponent(dineroRecaudado))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelRetorno)
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(retornoGratis)
                            .addComponent(retornoGanancia))))
                .addContainerGap(201, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Proponentes1, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(28, 28, 28)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelDescripcion)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelLugar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoFechaRealizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelFechaRealizar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelPrecio))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoMontoReunir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelMontoReunir))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoFechaPublicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelFechaPublicacion))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelCategoria))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(campoEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel1)))
                            .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 244, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel8)
                            .addComponent(dineroRecaudado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelRetorno)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(retornoGratis)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(retornoGanancia)))))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void campoEstadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoEstadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoEstadoActionPerformed

    private void dineroRecaudadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dineroRecaudadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dineroRecaudadoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> Colaboradores;
    private javax.swing.JLabel Proponentes1;
    private javax.swing.JTextArea areaDescripcion;
    private javax.swing.JTextField campoCategoria;
    private javax.swing.JTextField campoEstado;
    private javax.swing.JTextField campoFechaPublicacion;
    private javax.swing.JTextField campoFechaRealizar;
    private javax.swing.JTextField campoLugar;
    private javax.swing.JTextField campoMontoReunir;
    private javax.swing.JTextField campoPrecio;
    private javax.swing.JTextField dineroRecaudado;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel labelCategoria;
    private javax.swing.JLabel labelDescripcion;
    private javax.swing.JLabel labelFechaPublicacion;
    private javax.swing.JLabel labelFechaRealizar;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelLugar;
    private javax.swing.JLabel labelMontoReunir;
    private javax.swing.JLabel labelPrecio;
    private javax.swing.JLabel labelRetorno;
    private javax.swing.JList<String> listPropuestas;
    private javax.swing.JCheckBox retornoGanancia;
    private javax.swing.JCheckBox retornoGratis;
    // End of variables declaration//GEN-END:variables
}
