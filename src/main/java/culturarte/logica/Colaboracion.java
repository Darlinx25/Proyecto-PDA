/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.time.LocalDateTime;

/**
 *
 * @author mark
 */
public class Colaboracion {
    private float monto;
    private LocalDateTime fechaHora;
    private String tipoRetorno;
    
    private Colaborador colaborador;
    private Propuesta propuestaColaborada;

    public float getMonto() {
        return monto;
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
    
    
}
