/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;

import culturarte.logica.Pago;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.time.LocalDateTime;

/**
 *
 * @author mark
 */
@XmlType(name = "DTColaboracion")
@XmlRootElement
public class DTColaboracion {
    private Long id;
    private float monto;
    private LocalDateTime fechaHora;
    private String tipoRetorno;
    private String colaborador;
    private String propuestaColaborada;
    private boolean pagada;
    private boolean constanciaEmitida;
    private Pago pago;

    public DTColaboracion() {
    }

    public boolean isConstanciaEmitida() {
        return constanciaEmitida;
    }

    public void setConstanciaEmitida(boolean constanciaEmitida) {
        this.constanciaEmitida = constanciaEmitida;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public void setTipoRetorno(String tipoRetorno) {
        this.tipoRetorno = tipoRetorno;
    }

    public void setColaborador(String colaborador) {
        this.colaborador = colaborador;
    }

    public void setPropuestaColaborada(String propuestaColaborada) {
        this.propuestaColaborada = propuestaColaborada;
    }

    public void setPagada(boolean pagada) {
        this.pagada = pagada;
    }

    public void setPago(Pago pago) {
        this.pago = pago;
    }
    
    
    
    public DTColaboracion(Long id, float monto, LocalDateTime fechaHora, String tipoRetorno, String colaborador, String propuestaColaborada) {
        this.id = id;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.tipoRetorno = tipoRetorno;
        this.colaborador = colaborador;
        this.propuestaColaborada = propuestaColaborada;
    }
    
    //es para la web
    public DTColaboracion(boolean pagada,Pago pago, Long id, float monto, LocalDateTime fechaHora, String tipoRetorno, String colaborador, String propuestaColaborada, boolean constanciaEmitida) {
        this.pagada = pagada;
        this.pago = pago;
        this.id = id;
        this.monto = monto;
        this.fechaHora = fechaHora;
        this.tipoRetorno = tipoRetorno;
        this.colaborador = colaborador;
        this.propuestaColaborada = propuestaColaborada;
        this.constanciaEmitida = constanciaEmitida;
    }

    public boolean isPagada() {
        return pagada;
    }

    
    
    public Long getId() {
        return id;
    }

    public float getMonto() {
        return monto;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public String getTipoRetorno() {
        return tipoRetorno;
    }

    public String getColaborador() {
        return colaborador;
    }

    public String getPropuestaColaborada() {
        return propuestaColaborada;
    }
    
    public Pago getPago() {
        return pago;
    }
    
    public boolean getConstanciaEmitida(){
        return this.constanciaEmitida;
    }
    
}
