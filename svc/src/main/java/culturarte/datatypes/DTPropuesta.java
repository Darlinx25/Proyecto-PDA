/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;

import culturarte.logica.Estado;
import culturarte.logica.TipoRetorno;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kevin*/
public class DTPropuesta {
    private String titulo;
    private String descripcion;
    private String imagen;
    private String lugarRealizara;
    private LocalDate fechaRealizara;
    private float precioEntrada;
    private float montoAReunir;
    private float dineroRecaudado;
    private LocalDate fechaPublicacion;
    private LocalDate plazoFinanciacion;
    private String tipoPropuesta;
    private String nickProponedor;
    private List<TipoRetorno> tiposRetorno;
    private Estado estadoActual;
    private List<String> nicksColabs;
    private List<String> comentarios;
    
    
    public DTPropuesta(){
        
    }
    
    //este para obtener los datos de una propuesta
    public DTPropuesta(String titulo, String descripcion, String imagen, String lugarRealizara, LocalDate fechaRealizara, 
            float precioEntrada, float montoAReunir, float dineroRecaudado, LocalDate fechaPublicacion,
            String tipoPropuesta, String nickProponedor, List<TipoRetorno> tiposRetorno, Estado estadoActual,
            LocalDate fechaFinanciacion, List<String> nicksColabs ,List<String> comentarios) {
        
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.lugarRealizara = lugarRealizara;
        this.fechaRealizara = fechaRealizara;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.dineroRecaudado = dineroRecaudado;
        this.fechaPublicacion = fechaPublicacion;
        this.tipoPropuesta = tipoPropuesta;
        this.nickProponedor = nickProponedor;
        this.tiposRetorno = tiposRetorno;
        this.estadoActual = estadoActual;
        this.plazoFinanciacion = fechaFinanciacion;
        this.nicksColabs = nicksColabs;
        this.comentarios = comentarios;
    }
    
    //este para crear la propuesta
    public DTPropuesta(String titulo, String descripcion, String imagen, String lugarRealizara, LocalDate fechaRealizara, 
            float precioEntrada, float montoAReunir, String tipoPropuesta, String nickProponedor, List<TipoRetorno> tiposRetorno, Estado estadoActual) {
        
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.lugarRealizara = lugarRealizara;
        this.fechaRealizara = fechaRealizara;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.fechaPublicacion = null;
        this.tipoPropuesta = tipoPropuesta;
        this.nickProponedor = nickProponedor;
        this.tiposRetorno = tiposRetorno;
        this.estadoActual = estadoActual;
        this.plazoFinanciacion = null;
        //this.comentarios = new ArrayList<>();
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public String getLugarRealizara() {
        return lugarRealizara;
    }

    public LocalDate getFechaRealizara() {
        return fechaRealizara;
    }

    public float getPrecioEntrada() {
        return precioEntrada;
    }

    public float getMontoAReunir() {
        return montoAReunir;
    }

    public float getDineroRecaudado() {
        return dineroRecaudado;
    }
    
    public LocalDate getFechaPublicacion() {
        return fechaPublicacion;
    }

    public String getTipoPropuesta() {
        return tipoPropuesta;
    }

    public String getNickProponedor() {
        return nickProponedor;
    }

    public List<TipoRetorno> getTiposRetorno() {
        return tiposRetorno;
    }

    public Estado getEstadoActual(){
        return estadoActual;
    }
    
    public LocalDate getPlazoFinanciacion(){
        return plazoFinanciacion;
    }
    
    public List<String> getComentarios(){
        return this.comentarios;
    }
    

    public List<String> getNicksColabs() {
        return nicksColabs;
    }
}
