/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author kevin
 */
public class DTPropuesta {
        private String titulo;
        private String descripcion;
        private BufferedImage imagen;
        private String lugarRealizara;
        private LocalDate fechaRealizara;
        private float precioEntrada;
        private float montoAReunir;
        private LocalDate fechaPublicacion;
        private Categoria tipoPropuesta;
        private Proponente proponedor;
        private Estado estadoActual;
        private ArrayList<Estado> historialEstados;
        private ArrayList<Colaboracion> colaboraciones;

    public DTPropuesta(String titulo, String descripcion, BufferedImage imagen, String lugarRealizara, LocalDate fechaRealizara, float precioEntrada, float montoAReunir, LocalDate fechaPublicacion, Categoria tipoPropuesta, Proponente proponedor, Estado estadoActual, ArrayList<Estado> historialEstados, ArrayList<Colaboracion> colaboraciones) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.lugarRealizara = lugarRealizara;
        this.fechaRealizara = fechaRealizara;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.fechaPublicacion = fechaPublicacion;
        this.tipoPropuesta = tipoPropuesta;
        this.proponedor = proponedor;
        this.estadoActual = estadoActual;
        this.historialEstados = historialEstados;
        this.colaboraciones = colaboraciones;
    }
        
        
        
}
