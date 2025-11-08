/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;

import culturarte.logica.Pago;
import java.time.LocalDateTime;

/**
 *
 * @author mark
 */
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
