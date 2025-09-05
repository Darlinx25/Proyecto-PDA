/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package culturarte.graficos;

import culturarte.logica.DTPropuesta;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import culturarte.logica.TipoRetorno;
import static culturarte.logica.TipoRetorno.ENTRADA_GRATIS;
import static culturarte.logica.TipoRetorno.PORCENTAJE_GANANCIAS;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
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
public class ConsultarPropuestaEstado extends javax.swing.JInternalFrame {

    private IController controller;

    /**
     * Creates new form ConsultaPropuestaEstado
     */
    public ConsultarPropuestaEstado() {
        IControllerFactory fabrica = IControllerFactory.getInstance();
        this.controller = fabrica.getIController();
        initComponents();

        ListaEstados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selec = ListaEstados.getSelectedValue();

                if (selec != null) {
                    int estado = ListaEstados.getSelectedIndex();
                    controller.listarPropuestasEstado(estado);
                    DefaultListModel<String> modelo = (DefaultListModel<String>) listPropEstado.getModel();
                    modelo.clear();
                    for (String prop : controller.listarPropuestasEstado(estado)) {
                        modelo.addElement(prop);
                    }
                    resetCampos();
                    listPropEstado.addListSelectionListener(i -> {

                        if (!i.getValueIsAdjusting()) {
                            String propuesta = listPropEstado.getSelectedValue();
                            if (propuesta != null) {
                                DTPropuesta datosProp = controller.obtenerDTPropuesta(propuesta);
                                this.areaDescripcion.setText(datosProp.getDescripcion());
                                this.campoLugar.setText(datosProp.getLugarRealizara());
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                                LocalDate fechaRealizar = datosProp.getFechaRealizara();
                                this.campoFechaRealizar.setText(fechaRealizar.format(formatter));
                                this.campoPrecio.setText(Float.toString(datosProp.getPrecioEntrada()));
                                this.campoMontoReunir.setText(Float.toString(datosProp.getMontoAReunir()));
                                /*AGREGAR AL DTPROPUESTA LA FECHA DE PUBLICACIÓN*/
                                LocalDate fechaPublicacion = datosProp.getFechaPublicacion();
                                if (fechaPublicacion != null) {
                                    this.campoFechaPublicacion.setText(fechaPublicacion.format(formatter));
                                } else {
                                    this.campoFechaPublicacion.setText("");
                                }
                                this.campoCategoria.setText(datosProp.getTipoPropuesta());
                                String basePath = System.getProperty("user.dir") + "/imagenesUsuarios/";
                                String imagen = datosProp.getImagen();
                                if (datosProp != null) {
                                    if (imagen != null && !imagen.isEmpty()) {
                                        File archivoImagen = new File(basePath + imagen);
                                        if (archivoImagen.exists()) {
                                            try {
                                                BufferedImage temp = ImageIO.read(archivoImagen);
                                                Image imagenEscalada = temp.getScaledInstance(133, 133, Image.SCALE_SMOOTH);
                                                this.imagen.setIcon(new ImageIcon(imagenEscalada));
                                            } catch (IOException ex) {
                                                System.getLogger(ConsultarColaborador.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                                            }
                                        } else {
                                            this.imagen.setIcon(null);
                                        }

                                    }
                                }
                                List<TipoRetorno> aux = datosProp.getTiposRetorno();
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
                            }
                        }
                    });
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

        jScrollPane1 = new javax.swing.JScrollPane();
        ListaEstados = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listPropEstado = new javax.swing.JList<>(new javax.swing.DefaultListModel<>());
        jLabel2 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        areaDescripcion = new javax.swing.JTextArea();
        campoFechaPublicacion = new javax.swing.JTextField();
        labelFechaPublicacion = new javax.swing.JLabel();
        labelDescripcion = new javax.swing.JLabel();
        campoCategoria = new javax.swing.JTextField();
        campoLugar = new javax.swing.JTextField();
        labelCategoria = new javax.swing.JLabel();
        labelLugar = new javax.swing.JLabel();
        campoFechaRealizar = new javax.swing.JTextField();
        labelFechaRealizar = new javax.swing.JLabel();
        campoPrecio = new javax.swing.JTextField();
        labelPrecio = new javax.swing.JLabel();
        campoMontoReunir = new javax.swing.JTextField();
        labelImagen = new javax.swing.JLabel();
        labelMontoReunir = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        imagen = new javax.swing.JLabel();
        labelRetorno = new javax.swing.JLabel();
        retornoGratis = new javax.swing.JCheckBox();
        retornoGanancia = new javax.swing.JCheckBox();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setPreferredSize(new java.awt.Dimension(720, 450));

        ListaEstados.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Ingresada", "Publicada", "En Financiacion", "Financiada", "No Financida", "Cancelada" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        ListaEstados.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ListaEstadosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(ListaEstados);

        jLabel1.setText("Estados");

        jScrollPane2.setViewportView(listPropEstado);

        jLabel2.setText("Propuestas");

        areaDescripcion.setEditable(false);
        areaDescripcion.setColumns(20);
        areaDescripcion.setLineWrap(true);
        areaDescripcion.setRows(5);
        areaDescripcion.setCaretColor(new java.awt.Color(255, 255, 255));
        jScrollPane3.setViewportView(areaDescripcion);

        campoFechaPublicacion.setEditable(false);
        campoFechaPublicacion.setCaretColor(new java.awt.Color(255, 255, 255));

        labelFechaPublicacion.setText("Fecha de publicación:");

        labelDescripcion.setText("Descripción:");

        campoCategoria.setEditable(false);
        campoCategoria.setCaretColor(new java.awt.Color(255, 255, 255));

        campoLugar.setEditable(false);
        campoLugar.setCaretColor(new java.awt.Color(255, 255, 255));

        labelCategoria.setText("Categoría:");

        labelLugar.setText("Lugar a realizar:");

        campoFechaRealizar.setEditable(false);
        campoFechaRealizar.setCaretColor(new java.awt.Color(255, 255, 255));

        labelFechaRealizar.setText("Fecha a realizar:");

        campoPrecio.setEditable(false);
        campoPrecio.setCaretColor(new java.awt.Color(255, 255, 255));

        labelPrecio.setText("Precio de entrada:");

        campoMontoReunir.setEditable(false);
        campoMontoReunir.setCaretColor(new java.awt.Color(255, 255, 255));

        labelMontoReunir.setText("Monto a reunir:");

        jButton1.setText("Salir");
        jButton1.setToolTipText("");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        imagen.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                imagenAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        labelRetorno.setText("Tipo de Retorno:");

        retornoGratis.setText("Entrada Gratis");
        retornoGratis.setEnabled(false);

        retornoGanancia.setText("Porcentaje de Ganancias");
        retornoGanancia.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(76, 76, 76))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(labelDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane3)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(labelMontoReunir, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelPrecio, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelFechaRealizar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(labelFechaPublicacion, javax.swing.GroupLayout.Alignment.LEADING))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                                        .addComponent(campoFechaPublicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                    .addGap(18, 18, 18)
                                                    .addComponent(campoCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(18, 18, 18)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(retornoGratis, javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(retornoGanancia, javax.swing.GroupLayout.Alignment.LEADING))))))
                                .addGap(69, 69, 69))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelRetorno)
                                .addContainerGap())))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(labelDescripcion)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                            .addComponent(labelPrecio)))
                    .addComponent(labelImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel2))
                        .addGap(3, 3, 3)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
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
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelRetorno)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(retornoGratis)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(retornoGanancia)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)
                        .addGap(23, 23, 23))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(imagen, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(77, Short.MAX_VALUE))))
        );

        labelImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        labelImagen.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ListaEstadosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ListaEstadosMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ListaEstadosMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void imagenAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_imagenAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_imagenAncestorAdded

    private void resetCampos() {
        this.areaDescripcion.setText("");
        this.campoLugar.setText("");
        this.campoFechaRealizar.setText("");
        this.campoPrecio.setText("");
        this.campoMontoReunir.setText("");
        this.campoCategoria.setText("");
        this.campoFechaPublicacion.setText("");
        this.imagen.setIcon(null);

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList<String> ListaEstados;
    private javax.swing.JTextArea areaDescripcion;
    private javax.swing.JTextField campoCategoria;
    private javax.swing.JTextField campoFechaPublicacion;
    private javax.swing.JTextField campoFechaRealizar;
    private javax.swing.JTextField campoLugar;
    private javax.swing.JTextField campoMontoReunir;
    private javax.swing.JTextField campoPrecio;
    private javax.swing.JLabel imagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel labelCategoria;
    private javax.swing.JLabel labelDescripcion;
    private javax.swing.JLabel labelFechaPublicacion;
    private javax.swing.JLabel labelFechaRealizar;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelLugar;
    private javax.swing.JLabel labelMontoReunir;
    private javax.swing.JLabel labelPrecio;
    private javax.swing.JLabel labelRetorno;
    private javax.swing.JList<String> listPropEstado;
    private javax.swing.JCheckBox retornoGanancia;
    private javax.swing.JCheckBox retornoGratis;
    // End of variables declaration//GEN-END:variables
}
