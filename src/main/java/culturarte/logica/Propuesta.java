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
 * @author mark
 */
public class Propuesta {
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

    public Propuesta(String titulo, String descripcion, BufferedImage imagen, String lugarRealizara, LocalDate fechaRealizara, float precioEntrada, float montoAReunir, LocalDate fechaPublicacion, Categoria tipoPropuesta, Proponente proponedor, Estado estadoActual) {
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
        this.estadoActual = new Estado();
        this.historialEstados = new ArrayList<Estado>();
        this.colaboraciones = new ArrayList<Colaboracion>();
    }

    
    
    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BufferedImage getImagen() {
        return imagen;
    }

    public void setImagen(BufferedImage imagen) {
        this.imagen = imagen;
    }

    public String getLugarRealizara() {
        return lugarRealizara;
    }

    public void setLugarRealizara(String lugarRealizara) {
        this.lugarRealizara = lugarRealizara;
    }

    public LocalDate getFechaRealizara() {
        return fechaRealizara;
    }

    public void setFechaRealizara(LocalDate fechaRealizara) {
        this.fechaRealizara = fechaRealizara;
    }

    public float getPrecioEntrada() {
        return precioEntrada;
    }

    public void setPrecioEntrada(float precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public float getMontoAReunir() {
        return montoAReunir;
    }

    public void setMontoAReunir(float montoAReunir) {
        this.montoAReunir = montoAReunir;
    }

    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public void setFechaPublicacion(LocalDate fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public Categoria getTipoPropuesta() {
        return tipoPropuesta;
    }

    public void setTipoPropuesta(Categoria tipoPropuesta) {
        this.tipoPropuesta = tipoPropuesta;
    }

    public Proponente getProponedor() {
        return proponedor;
    }

    public void setProponedor(Proponente proponedor) {
        this.proponedor = proponedor;
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public ArrayList<Estado> getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(ArrayList<Estado> historialEstados) {
        this.historialEstados = historialEstados;
    }

    public ArrayList<Colaboracion> getColaboraciones() {
        return colaboraciones;
    }

    public void setColaboraciones(ArrayList<Colaboracion> colaboraciones) {
        this.colaboraciones = colaboraciones;
    }
    
    
}
