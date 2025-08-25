/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author kevin*/
public class DTPropuesta {
    private String titulo;
    private String descripcion;
    private byte[] imagen;
    private String lugarRealizara;
    private LocalDate fechaRealizara;
    private float precioEntrada;
    private float montoAReunir;
    private String tipoPropuesta;
    private String nickProponedor;
    private List<TipoRetorno> tiposRetorno;
    private Estado estadoActual;
    
    public DTPropuesta(){
        
    }
    
    public DTPropuesta(String titulo, String descripcion, byte[] imagen, String lugarRealizara, LocalDate fechaRealizara, 
            float precioEntrada, float montoAReunir, String tipoPropuesta, String nickProponedor, List<TipoRetorno> tiposRetorno, Estado estadoActual) {
        
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.lugarRealizara = lugarRealizara;
        this.fechaRealizara = fechaRealizara;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.tipoPropuesta = tipoPropuesta;
        this.nickProponedor = nickProponedor;
        this.tiposRetorno = tiposRetorno;
        this.estadoActual = estadoActual;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public byte[] getImagen() {
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
}
