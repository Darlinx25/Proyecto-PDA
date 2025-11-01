/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * @author mark
 */

@Entity
@Table(name = "colaboraciones")
public class Colaboracion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;
    
    private boolean deBaja;
    
    public Colaboracion(){
        
    }

    public Colaboracion(float monto, String tipoRetorno, Colaborador colaborador, Propuesta propuestaColaborada) {
        this.monto = monto;
        this.tipoRetorno = tipoRetorno;
        this.colaborador = colaborador;
        this.propuestaColaborada = propuestaColaborada;
        
        this.fechaHora = LocalDateTime.now();
        this.deBaja = false;
    }
    
    
    
    private float monto;
    private LocalDateTime fechaHora;
    private String tipoRetorno;
    
    
    @ManyToOne
    @JoinColumn(name = "colaborador_nick")
    private Colaborador colaborador;
    
    @ManyToOne
    private Propuesta propuestaColaborada;

    public float getMonto() {
        return monto;
    }

    public Long getId() {
        return id;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public Colaborador getColaborador() {
        return colaborador;
    }

    public void setColaborador(Colaborador colaborador) {
        this.colaborador = colaborador;
    }

    public Propuesta getPropuestaColaborada() {
        return propuestaColaborada;
    }

    public void setPropuestaColaborada(Propuesta propuestaColaborada) {
        this.propuestaColaborada = propuestaColaborada;
    }
    
    public boolean setBaja(boolean deBaja){
        return this.deBaja = deBaja;
    }
    
    public boolean getBaja(){
        return this.deBaja;
    }
    
    
}
