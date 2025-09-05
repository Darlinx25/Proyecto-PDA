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
        NumberFormatter numberFormatter = (NumberFormatter) this.campoPrecioEntrada.getFormatter();
        numberFormatter.setMinimum(0);
        NumberFormatter numberFormatter2 = (NumberFormatter) this.campoMontoReunir.getFormatter();
        numberFormatter2.setMinimum(0);
        controller.listarPropuestas();
        DefaultListModel<String> modelo = (DefaultListModel<String>) listaPropuestas.getModel();
        modelo.clear();
        for (String prop : controller.listarPropuestas()) {
            modelo.addElement(prop);
        }

        listaPropuestas.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selec = listaPropuestas.getSelectedValue();
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
                        this.areaDescripcion.setText(dtprop.getDescripcion());
                        String tipo = dtprop.getTipoPropuesta();
                        for (int row = 0; row < this.arbolCategoria.getRowCount(); row++) {
                            TreePath path = this.arbolCategoria.getPathForRow(row);
                            DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) path.getLastPathComponent();

                            if (tipo.equals(nodo.getUserObject().toString())) {
                                this.arbolCategoria.setSelectionRow(row);
                                break;
                            }
                        }
                        List<TipoRetorno> aux = dtprop.getTiposRetorno();
                        this.checkRetornoGratis.setSelected(false);
                        this.checkRetornoGanancia.setSelected(false);
                        for (TipoRetorno item : aux) {

                            switch (item) {
                                case ENTRADA_GRATIS:
                                    this.checkRetornoGratis.setSelected(true);
                                    break;
                                case PORCENTAJE_GANANCIAS:
                                    this.checkRetornoGanancia.setSelected(true);
                                    break;
                            }
                        }

                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        LocalDate fechaRealizar = dtprop.getFechaRealizara();
                        this.campoFechaRealizar.setText(fechaRealizar.format(formatter));
                        this.campoLugar.setText(dtprop.getLugarRealizara());
                        EstadoPropuesta ese = dtprop.getEstadoActual().getEstado();
                        this.comboEstado.setSelectedIndex(ese.ordinal());
                        this.campoMontoReunir.setText(Float.toString(dtprop.getMontoAReunir()));
                        ModificarPropuesta.this.campoPrecioEntrada.setText(Float.toString(dtprop.getPrecioEntrada()));

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
        this.areaDescripcion.setEnabled(true);
        this.arbolCategoria.setEnabled(true);
        this.campoFechaRealizar.setEnabled(true);
        this.campoLugar.setEnabled(true);
        this.campoMontoReunir.setEnabled(true);
        this.campoPrecioEntrada.setEnabled(true);
        this.botonAceptar.setEnabled(true);
        this.botonAddImagen.setEnabled(true);
        this.comboEstado.setEnabled(true);
        this.checkRetornoGratis.setEnabled(true);
        this.checkRetornoGanancia.setEnabled(true);

    }
    
    private void expandirCat(){
        for (int i = 0; i < arbolCategoria.getRowCount(); i++) {
            arbolCategoria.expandRow(i); // expande cada fila visible
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

        if (this.areaDescripcion.getText().isBlank()) {
            return false;
        }
        if (this.campoFechaRealizar.getText().isBlank()) {
            return false;
        }
        if (this.campoLugar.getText().isBlank()) {
            return false;
        }
        if (this.campoMontoReunir.getText().isBlank()) {
            return false;
        }
        if (Float.parseFloat(this.campoMontoReunir.getText()) <= 0) {
            JOptionPane.showMessageDialog(this, "El monto a reunir debe ser mayor a 0", "Modificar Propuesta", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (this.campoPrecioEntrada.getText().isBlank()) {
            return false;
        }
        if (Float.parseFloat(this.campoPrecioEntrada.getText()) <= 0) {
            JOptionPane.showMessageDialog(this, "El precio de la entrada debe ser mayor a 0", "Modificar Propuesta", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if (!(this.checkRetornoGratis.isSelected() || this.checkRetornoGanancia.isSelected())) {
            JOptionPane.showMessageDialog(this, "Seleccione al menos un tipo de retorno", "Modificar Propuesta", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        labelImagen = new javax.swing.JLabel();
        botonCancelar = new javax.swing.JButton();
        botonAceptar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaPropuestas = new javax.swing.JList<>(new javax.swing.DefaultListModel<>());
        labelDescripcion = new javax.swing.JLabel();
        labelMontoReunir = new javax.swing.JLabel();
        labelCategoria = new javax.swing.JLabel();
        labelLugar = new javax.swing.JLabel();
        labelFechaRealizar = new javax.swing.JLabel();
        labelPrecio = new javax.swing.JLabel();
        campoLugar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaDescripcion = new javax.swing.JTextArea();
        jLabel1 = new javax.swing.JLabel();
        campoImagen = new javax.swing.JLabel();
        botonAddImagen = new javax.swing.JButton();
        labelCategoria1 = new javax.swing.JLabel();
        campoFechaRealizar = new javax.swing.JFormattedTextField();
        comboEstado = new javax.swing.JComboBox<>();
        arbolCategoria = new javax.swing.JTree(this.controller.listarCategorias());
        checkRetornoGanancia = new javax.swing.JCheckBox();
        labelRetorno = new javax.swing.JLabel();
        checkRetornoGratis = new javax.swing.JCheckBox();
        campoPrecioEntrada = new javax.swing.JFormattedTextField();
        campoMontoReunir = new javax.swing.JFormattedTextField();

        labelImagen.setText("jLabel2");

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setTitle("Modificar propuesta");
        setPreferredSize(new java.awt.Dimension(900, 415));

        botonCancelar.setText("Cancelar");
        botonCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCancelarActionPerformed(evt);
            }
        });

        botonAceptar.setText("Aceptar");
        botonAceptar.setEnabled(false);
        botonAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAceptarActionPerformed(evt);
            }
        });

        jScrollPane1.setViewportView(listaPropuestas);

        labelDescripcion.setText("Descripción:");

        labelMontoReunir.setText("Monto a reunir:");

        labelCategoria.setText("Categoría:");

        labelLugar.setText("Lugar a realizar:");

        labelFechaRealizar.setText("Fecha a realizar:");

        labelPrecio.setText("Precio de entrada:");

        campoLugar.setEnabled(false);

        jScrollPane2.setEnabled(false);

        areaDescripcion.setColumns(20);
        areaDescripcion.setRows(5);
        areaDescripcion.setEnabled(false);
        jScrollPane2.setViewportView(areaDescripcion);

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

        botonAddImagen.setText("Seleccionar Imagen");
        botonAddImagen.setEnabled(false);
        botonAddImagen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonAddImagenActionPerformed(evt);
            }
        });

        labelCategoria1.setText("Estado:");

        campoFechaRealizar.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.DateFormatter(new java.text.SimpleDateFormat("dd/MM/y"))));
        campoFechaRealizar.setEnabled(false);
        campoFechaRealizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoFechaRealizarActionPerformed(evt);
            }
        });

        comboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "INGRESADA", "PUBLICADA", "EN_FINANCIACION", "FINANCIADA", "NO_FINANCIADA", "CANCELADA" }));

        checkRetornoGanancia.setText("Porcentaje de Ganancias");
        checkRetornoGanancia.setEnabled(false);

        labelRetorno.setText("Tipo de Retorno:");

        checkRetornoGratis.setText("Entrada Gratis");
        checkRetornoGratis.setEnabled(false);

        campoPrecioEntrada.setEnabled(false);
        campoPrecioEntrada.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)))));

        campoPrecioEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoPrecioEntradaActionPerformed(evt);
            }
        });
        campoPrecioEntrada.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoPrecioEntradaActionPerformed(evt);
            }
        });

        campoMontoReunir.setEnabled(false);
        campoMontoReunir.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.NumberFormatter(new java.text.DecimalFormat("0.00", new DecimalFormatSymbols(Locale.US)))));

        campoMontoReunir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoMontoReunirActionPerformed(evt);
            }
        });
        campoMontoReunir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                campoMontoReunirActionPerformed(evt);
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
                    .addComponent(botonAddImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(campoImagen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(labelCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(arbolCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(checkRetornoGratis, javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(checkRetornoGanancia, javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(botonCancelar, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelCategoria1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(campoLugar, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE)
                            .addComponent(botonAceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoFechaRealizar)
                            .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(campoPrecioEntrada)
                            .addComponent(campoMontoReunir)))
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
                                    .addComponent(campoLugar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(labelLugar))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelFechaRealizar)
                                    .addComponent(campoFechaRealizar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(campoImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(arbolCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(labelPrecio)
                            .addComponent(campoPrecioEntrada, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelMontoReunir)
                                    .addComponent(botonAddImagen, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(campoMontoReunir, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(labelCategoria1)
                                    .addComponent(comboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(labelRetorno)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkRetornoGratis)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(checkRetornoGanancia)))
                        .addGap(43, 50, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(botonCancelar)
                            .addComponent(botonAceptar)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        labelImagen.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        labelImagen.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCancelarActionPerformed
        this.dispose();
    }//GEN-LAST:event_botonCancelarActionPerformed

    private void botonAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAceptarActionPerformed
        if (sonValidosLosCampos()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            List<TipoRetorno> tiposRetorno = new ArrayList<>();
            if (this.checkRetornoGratis.isSelected()) {
                tiposRetorno.add(TipoRetorno.ENTRADA_GRATIS);
            }
            if (this.checkRetornoGanancia.isSelected()) {
                tiposRetorno.add(TipoRetorno.PORCENTAJE_GANANCIAS);
            }
            String hora = this.campoFechaRealizar.getText();
            String descri = this.areaDescripcion.getText();
            LocalDate f = LocalDate.parse(hora,formatter);
            DefaultMutableTreeNode cat = (DefaultMutableTreeNode) arbolCategoria.getLastSelectedPathComponent();
            String tipoPropuesta = cat.toString();
            String titulo = this.listaPropuestas.getSelectedValue();
            EstadoPropuesta auxEst =  EstadoPropuesta.valueOf(this.comboEstado.getSelectedItem().toString());
            Estado est = new Estado(auxEst);
            String lug = this.campoLugar.getText();
            DTPropuesta propuesta = new DTPropuesta(titulo,descri,this.imagenProponente,lug,f,Float.parseFloat(this.campoPrecioEntrada.getText()),Float.parseFloat(this.campoMontoReunir.getText()),tipoPropuesta,null,tiposRetorno,est);
            controller.modPropuesta(propuesta);
            
            this.dispose();
        }
    }//GEN-LAST:event_botonAceptarActionPerformed

    private void botonAddImagenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonAddImagenActionPerformed
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
    }//GEN-LAST:event_botonAddImagenActionPerformed

    private void campoImagenAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_campoImagenAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_campoImagenAncestorAdded

    private void campoFechaRealizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoFechaRealizarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoFechaRealizarActionPerformed

    private void campoPrecioEntradaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoPrecioEntradaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoPrecioEntradaActionPerformed

    private void campoMontoReunirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_campoMontoReunirActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_campoMontoReunirActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTree arbolCategoria;
    private javax.swing.JTextArea areaDescripcion;
    private javax.swing.JButton botonAceptar;
    private javax.swing.JButton botonAddImagen;
    private javax.swing.JButton botonCancelar;
    private javax.swing.JFormattedTextField campoFechaRealizar;
    private javax.swing.JLabel campoImagen;
    private javax.swing.JTextField campoLugar;
    private javax.swing.JFormattedTextField campoMontoReunir;
    private javax.swing.JFormattedTextField campoPrecioEntrada;
    private javax.swing.JCheckBox checkRetornoGanancia;
    private javax.swing.JCheckBox checkRetornoGratis;
    private javax.swing.JComboBox<String> comboEstado;
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
    private javax.swing.JList<String> listaPropuestas;
    // End of variables declaration//GEN-END:variables
}
