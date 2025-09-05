/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package culturarte.graficos;

import culturarte.logica.DTPropuesta;
import culturarte.logica.Estado;
import culturarte.logica.EstadoPropuesta;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import culturarte.logica.TipoRetorno;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.DecimalFormatSymbols;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author faxcundo
 */
public class ModificarPropuesta extends javax.swing.JInternalFrame {

    private IController controller;
    private String imagenProponente;

    public ModificarPropuesta() {
        IControllerFactory fabrica = IControllerFactory.getInstance();
        this.controller = fabrica.getIController();
        initComponents();
        NumberFormatter numberFormatter = (NumberFormatter) this.precioentrada.getFormatter();
        numberFormatter.setMinimum(0);
        NumberFormatter numberFormatter2 = (NumberFormatter) this.montoreunir.getFormatter();
        numberFormatter2.setMinimum(0);
        controller.listarPropuestas();
        DefaultListModel<String> modelo = (DefaultListModel<String>) ListaPropuestas.getModel();
        modelo.clear();
        for (String prop : controller.listarPropuestas()) {
            modelo.addElement(prop);
        }

        ListaPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selec = ListaPropuestas.getSelectedValue();
                if (selec != null) {
                    enableCampos();
                    expandirCat();
                    DTPropuesta dtprop = controller.obtenerDTPropuesta(selec);
                    String basePath = System.getProperty("user.dir") + "/imagenesUsuarios/";
                    String imagen = dtprop.getImagen();
                    if (dtprop != null) {
                        if (imagen != null && !imagen.isEmpty()) {
                            File archivoImagen = new File(basePath + imagen);
                            if (archivoImagen.exists()) {
                                try {
                                    BufferedImage temp = ImageIO.read(archivoImagen);
                                    Image imagenEscalada = temp.getScaledInstance(133, 133, Image.SCALE_SMOOTH);
                                    this.campoImagen.setIcon(new ImageIcon(imagenEscalada));
                                } catch (IOException ex) {
                                    System.getLogger(ConsultarColaborador.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                                }
                            } else {
                                this.campoImagen.setIcon(null);
                            }
                        } else {
                            this.campoImagen.setIcon(null);
                        }
                        this.descripcion.setText(dtprop.getDescripcion());
                        String tipo = dtprop.getTipoPropuesta();
                        for (int row = 0; row < this.categoria.getRowCount(); row++) {
                            TreePath path = this.categoria.getPathForRow(row);
                            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) path.getLastPathComponent();

                            if (tipo.equals(nodo.getUserObject().toString())) {
                                this.categoria.setSelectionRow(row);
                                break;
                            }
                        }
                        List<TipoRetorno> aux = dtprop.getTiposRetorno();
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

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fechaRealizar = dtprop.getFechaRealizara();
                        this.fecharealizar.setText(fechaRealizar.format(formatter));
                        this.lugar.setText(dtprop.getLugarRealizara());
                        EstadoPropuesta ese = dtprop.getEstadoActual().getEstado();
                        this.estado.setSelectedIndex(ese.ordinal());
                        this.montoreunir.setText(Float.toString(dtprop.getMontoAReunir()));
                        ModificarPropuesta.this.precioentrada.setText(Float.toString(dtprop.getPrecioEntrada()));

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
    private void enableCampos() {
        this.descripcion.setEnabled(true);
        this.categoria.setEnabled(true);
        this.fecharealizar.setEnabled(true);
        this.lugar.setEnabled(true);
        this.montoreunir.setEnabled(true);
        this.precioentrada.setEnabled(true);
        this.Aceptar.setEnabled(true);
        this.SelectImagen.setEnabled(true);
        this.estado.setEnabled(true);
        this.retornoGratis.setEnabled(true);
        this.retornoGanancia.setEnabled(true);

    }
    
    private void expandirCat(){
        for (int i = 0; i < categoria.getRowCount(); i++) {
            categoria.expandRow(i); // expande cada fila visible
        }
    }

    private String tipoImagen(File archivo) throws IOException {
        Path path = archivo.toPath();
        String mimeType = Files.probeContentType(path);
        if (mimeType != null) {
            if (mimeType.equals("image/png")) {
                return "png";
            } else if (mimeType.equals("image/jpeg")) {
                return "jpg";
            }
        }
        return "desconocido";
    }

    private boolean sonValidosLosCampos() {

        if (this.descripcion.getText().isBlank()) {
            return false;
        }
        if (this.fecharealizar.getText().isBlank()) {
            return false;
        }
        if (this.lugar.getText().isBlank()) {
            return false;
        }
        if (this.montoreunir.getText().isBlank()) {
            return false;
        }
        if (Float.parseFloat(this.montoreunir.getText()) <= 0) {
            JOptionPane.showMessageDialog(this, "El monto a reunir debe ser mayor a 0", "Modificar Propuesta", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (this.precioentrada.getText().isBlank()) {
            return false;
        }
        if (Float.parseFloat(this.precioentrada.getText()) <= 0) {
            JOptionPane.showMessageDialog(this, "El precio de la entrada debe ser mayor a 0", "Modificar Propuesta", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(this.retornoGratis.isSelected() || this.retornoGanancia.isSelected())) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un tipo de retorno", "Modificar Propuesta", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelImagen = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        Aceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        ListaPropuestas = new javax.swing.JList<>(new javax.swing.DefaultListModel<>());
        labelDescripcion = new javax.swing.JLabel();
        labelMontoReunir = new javax.swing.JLabel();
        labelCategoria = new javax.swing.JLabel();
        labelLugar = new javax.swing.JLabel();
        labelFechaRealizar = new javax.swing.JLabel();
        labelPrecio = new javax.swing.JLabel();
        lugar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        descripcion = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        campoImagen = new javax.swing.JLabel();
        SelectImagen = new javax.swing.JButton();
        labelCategoria1 = new javax.swing.JLabel();
        fecharealizar = new javax.swing.JFormattedTextField();
        estado = new javax.swing.JComboBox<>();
        categoria = new javax.swing.JTree(this.controller.listarCategorias());
        retornoGanancia = new javax.swing.JCheckBox();
        labelRetorno = new javax.swing.JLabel();
        retornoGratis = new javax.swing.JCheckBox();
        precioentrada = new javax.swing.JFormattedTextField();
        montoreunir = new javax.swing.JFormattedTextField();

        labelImagen.setText("jLabel2");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Modificar propuesta");
        setPreferredSize(new java.awt.Dimension(900, 415));

        jButton1.setText("Cancelar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        Aceptar.setText("Aceptar");
        Aceptar.setEnabled(false);
        Aceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AceptarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(ListaPropuestas);

        labelDescripcion.setText("Descripción:");

        labelMontoReunir.setText("Monto a reunir:");

        labelCategoria.setText("Categoría:");

        labelLugar.setText("Lugar a realizar:");

        labelFechaRealizar.setText("Fecha a realizar:");

        labelPrecio.setText("Precio de entrada:");

        lugar.setEnabled(false);

        jScrollPane2.setEnabled(false);

        descripcion.setColumns(20);
        descripcion.setRows(5);
        descripcion.setEnabled(false);
        jScrollPane2.setViewportView(descripcion);

        jLabel1.setText("Propuestas");

        campoImagen.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                campoImagenAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        SelectImagen.setText("Seleccionar Imagen");
        SelectImagen.setEnabled(false);
        SelectImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SelectImagenActionPerformed(evt);
            }
        });

        labelCategoria1.setText("Estado:");

        fecharealizar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/y"))));
        fecharealizar.setEnabled(false);
        fecharealizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fecharealizarActionPerformed(evt);
            }
        });

        estado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INGRESADA", "PUBLICADA", "EN_FINANCIACION", "FINANCIADA", "NO_FINANCIADA", "CANCELADA" }));

        retornoGanancia.setText("Porcentaje de Ganancias");
        retornoGanancia.setEnabled(false);

        labelRetorno.setText("Tipo de Retorno:");

        retornoGratis.setText("Entrada Gratis");
        retornoGratis.setEnabled(false);

        precioentrada.setEnabled(false);
        precioentrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)))));

        precioentrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioentradaActionPerformed(evt);
            }
        });
        precioentrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                precioentradaActionPerformed(evt);
            }
        });

        montoreunir.setEnabled(false);
        montoreunir.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)))));

        montoreunir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                montoreunirActionPerformed(evt);
            }
        });
        montoreunir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                montoreunirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(SelectImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(campoImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(retornoGratis, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(retornoGanancia, javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(labelRetorno)
                            .addGap(65, 65, 65))))
                .addGap(47, 47, 47)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(labelDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelMontoReunir, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelPrecio, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFechaRealizar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelLugar, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCategoria1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lugar, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(Aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(fecharealizar)
                            .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(precioentrada)
                            .addComponent(montoreunir)))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 298, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(41, 41, 41))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelDescripcion)
                            .addComponent(labelCategoria))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(lugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelLugar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelFechaRealizar)
                                    .addComponent(fecharealizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(campoImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(categoria, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelPrecio)
                            .addComponent(precioentrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelMontoReunir)
                                    .addComponent(SelectImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(montoreunir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelCategoria1)
                                    .addComponent(estado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelRetorno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(retornoGratis)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(retornoGanancia)))
                        .addGap(43, 50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(Aceptar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        labelImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        labelImagen.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
        if (sonValidosLosCampos()) {
            String x = this.ListaPropuestas.getSelectedValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            List<TipoRetorno> tiposRetorno = new ArrayList<TipoRetorno>();
            if (this.retornoGratis.isSelected()) {
                tiposRetorno.add(TipoRetorno.ENTRADA_GRATIS);
            }
            if (this.retornoGanancia.isSelected()) {
                tiposRetorno.add(TipoRetorno.PORCENTAJE_GANANCIAS);
            }
            String hora = this.fecharealizar.getText();
            String descri = this.descripcion.getText();
            LocalDate f = LocalDate.parse(hora,formatter);
            DefaultMutableTreeNode cat = (DefaultMutableTreeNode) categoria.getLastSelectedPathComponent();
            String tipoPropuesta = cat.toString();
            String titulo = this.ListaPropuestas.getSelectedValue();
            EstadoPropuesta auxEst =  EstadoPropuesta.valueOf(this.estado.getSelectedItem().toString());
            Estado est = new Estado(auxEst);
            String lug = this.lugar.getText();
            DTPropuesta propuesta = new DTPropuesta(titulo,descri,this.imagenProponente,lug,f,Float.parseFloat(this.precioentrada.getText()),Float.parseFloat(this.montoreunir.getText()),tipoPropuesta,null,tiposRetorno,est);
            controller.modPropuesta(propuesta);
            
            this.dispose();
        }
    }//GEN-LAST:event_AceptarActionPerformed

    private void SelectImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SelectImagenActionPerformed
        JFileChooser selectorImagen = new JFileChooser();
        String[] tiposImagen = {"jpg", "png"};
        FileNameExtensionFilter filtroImagen = new FileNameExtensionFilter("Archivos .jpg o .png", tiposImagen);
        selectorImagen.setFileFilter(filtroImagen);
        selectorImagen.setAcceptAllFileFilterUsed(false);

        int resultado = selectorImagen.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoElegido = selectorImagen.getSelectedFile();
            try {
                String extensionImagen = tipoImagen(archivoElegido);

                if (extensionImagen.equals("jpg") || extensionImagen.equals("png")) {
                    BufferedImage temp = ImageIO.read(archivoElegido);

                    File carpetaImagenes = new File("imagenesUsuarios");
                    if (!carpetaImagenes.exists()) {
                        carpetaImagenes.mkdirs();
                    }

                    String nombreArchivo = System.currentTimeMillis() + "." + extensionImagen;
                    File archivoDestino = new File(carpetaImagenes, nombreArchivo);
                    ImageIO.write(temp, extensionImagen, archivoDestino);

                    this.imagenProponente = nombreArchivo;

                    Image imagenEscalada = temp.getScaledInstance(133, 133, Image.SCALE_SMOOTH);
                    this.campoImagen.setIcon(new ImageIcon(imagenEscalada));
                }
            } catch (IOException ex) {
                System.getLogger(AltaUsuario.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
    }//GEN-LAST:event_SelectImagenActionPerformed

    private void campoImagenAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_campoImagenAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_campoImagenAncestorAdded

    private void fecharealizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fecharealizarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fecharealizarActionPerformed

    private void precioentradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_precioentradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_precioentradaActionPerformed

    private void montoreunirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_montoreunirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_montoreunirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private javax.swing.JList<String> ListaPropuestas;
    private javax.swing.JButton SelectImagen;
    private javax.swing.JLabel campoImagen;
    private javax.swing.JTree categoria;
    private javax.swing.JTextArea descripcion;
    private javax.swing.JComboBox<String> estado;
    private javax.swing.JFormattedTextField fecharealizar;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel labelCategoria;
    private javax.swing.JLabel labelCategoria1;
    private javax.swing.JLabel labelDescripcion;
    private javax.swing.JLabel labelFechaRealizar;
    private javax.swing.JLabel labelImagen;
    private javax.swing.JLabel labelLugar;
    private javax.swing.JLabel labelMontoReunir;
    private javax.swing.JLabel labelPrecio;
    private javax.swing.JLabel labelRetorno;
    private javax.swing.JTextField lugar;
    private javax.swing.JFormattedTextField montoreunir;
    private javax.swing.JFormattedTextField precioentrada;
    private javax.swing.JCheckBox retornoGanancia;
    private javax.swing.JCheckBox retornoGratis;
    // End of variables declaration//GEN-END:variables
}
