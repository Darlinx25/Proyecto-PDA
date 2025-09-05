/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author mark
 */

@Entity
@Table(name = "propuestas")
public class Propuesta {
    
    @Id
    private String titulo;
    
    private String descripcion;
    
    private String imagen;
    
    private String lugarRealizara;
    private LocalDate fechaRealizara;
    private float precioEntrada;
    private float montoAReunir;
    private LocalDate fechaPublicacion;
    
    @ElementCollection
    private List<TipoRetorno> tiposRetorno;
    
    
    @ManyToOne
    private Categoria tipoPropuesta;
    
    @ManyToOne
    @JoinColumn(name = "proponente_nick")
    private Proponente proponente;
    
    @Embedded
    private Estado estadoActual;
    
    @ElementCollection
    private List<Estado> historialEstados;
    
    @OneToMany(mappedBy = "propuestaColaborada", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colaboracion> colaboraciones;
    
    public Propuesta(){
        
    }
    
    public Propuesta(String titulo, String descripcion, String imagen, String lugarRealizara, LocalDate fechaRealizara, float precioEntrada, 
            float montoAReunir, List<TipoRetorno> tiposRetorno, Categoria tipoPropuesta, Proponente proponente,Estado estadoActual ) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.lugarRealizara = lugarRealizara;
        this.fechaRealizara = fechaRealizara;
        this.precioEntrada = precioEntrada;
        this.montoAReunir = montoAReunir;
        this.fechaPublicacion = null;//se cambia cuando el estado pasa a publicada
        this.tiposRetorno = tiposRetorno;
        this.tipoPropuesta = tipoPropuesta;
        this.proponente = proponente;
        this.estadoActual = estadoActual;
        this.historialEstados = new ArrayList<>();
        this.colaboraciones = new ArrayList<>();
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

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
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

    public List<TipoRetorno> getTiposRetorno() {
        return tiposRetorno;
    }

    public void setTiposRetorno(List<TipoRetorno> tiposRetorno) {
        this.tiposRetorno = tiposRetorno;
    }
    
    public Categoria getTipoPropuesta() {
        return tipoPropuesta;
    }

    public void setTipoPropuesta(Categoria tipoPropuesta) {
        this.tipoPropuesta = tipoPropuesta;
    }

    public Proponente getProponedor() {
        return proponente;
    }

    public void setProponedor(Proponente proponente) {
        this.proponente = proponente;
    }

    public Estado getEstadoActual() {
        return estadoActual;
    }

    public void setEstadoActual(Estado estadoActual) {
        this.estadoActual = estadoActual;
    }

    public List<Estado> getHistorialEstados() {
        return historialEstados;
    }

    public void setHistorialEstados(List<Estado> historialEstados) {
        this.historialEstados = historialEstados;
    }

    public List<Colaboracion> getColaboraciones() {
        return colaboraciones;
    }

    public void setColaboraciones(List<Colaboracion> colaboraciones) {
        this.colaboraciones = colaboraciones;
    }
    
    
}
