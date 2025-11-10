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

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "DTPropuesta")
@XmlRootElement
public class DTPropuesta {
    private String titulo;
    private String descripcion;
    private String imagen;
    private String lugarRealizara;
    private String fechaRealizara;
    private float precioEntrada;
    private float montoAReunir;
    private float dineroRecaudado;
    private String fechaPublicacion;
    private String plazoFinanciacion;
    private String tipoPropuesta;
    private String nickProponedor;
    private List<TipoRetorno> tiposRetorno;
    private Estado estadoActual;
    private List<String> nicksColabs;
    private List<String> comentarios;
    
    
    public DTPropuesta(){
        
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void setLugarRealizara(String lugarRealizara) {
        this.lugarRealizara = lugarRealizara;
    }

    public void setFechaRealizara(String fechaRealizara) {
        this.fechaRealizara = fechaRealizara;
    }

    public void setPrecioEntrada(float precioEntrada) {
        this.precioEntrada = precioEntrada;
    }

    public void setMontoAReunir(float montoAReunir) {
        this.montoAReunir = montoAReunir;
    }

    public void setDineroRecaudado(float dineroRecaudado) {
        this.dineroRecaudado = dineroRecaudado;
    }

    public void setFechaPublicacion(String fechaPublicacion) {
        this.fechaPublicacion = fechaPublicacion;
    }

    public void setPlazoFinanciacion(String plazoFinanciacion) {
        this.plazoFinanciacion = plazoFinanciacion;
    }

    public void setTipoPropuesta(String tipoPropuesta) {
        this.tipoPropuesta = tipoPropuesta;
    }

    public void setNickProponedor(String nickProponedor) {
        this.nickProponedor = nickProponedor;
    }

    public void setTiposRetorno(List<TipoRetorno> tiposRetorno) {
        this.tiposRetorno = tiposRetorno;
    }

    public void setEstadoActual(Estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public void setNicksColabs(List<String> nicksColabs) {
        this.nicksColabs = nicksColabs;
    }

    public void setComentarios(List<String> comentarios) {
        this.comentarios = comentarios;
    }
    
    //este para obtener los datos de una propuesta
    public DTPropuesta(String titulo, String descripcion, String imagen, String lugarRealizara, String fechaRealizara, 
            float precioEntrada, float montoAReunir, float dineroRecaudado, String fechaPublicacion,
            String tipoPropuesta, String nickProponedor, List<TipoRetorno> tiposRetorno, Estado estadoActual,
            String fechaFinanciacion, List<String> nicksColabs ,List<String> comentarios) {
        
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
    public DTPropuesta(String titulo, String descripcion, String imagen, String lugarRealizara, String fechaRealizara, 
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

    public String getFechaRealizara() {
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
    
    public String getFechaPublicacion() {
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
    
    public String getPlazoFinanciacion(){
        return plazoFinanciacion;
    }
    
    public List<String> getComentarios(){
        return this.comentarios;
    }
    

    public List<String> getNicksColabs() {
        return nicksColabs;
    }
}
