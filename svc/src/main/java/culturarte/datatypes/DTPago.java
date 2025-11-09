package culturarte.datatypes;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class DTPago {
    private float montoPago;
    private DTFormaPago formaPago;
    private LocalDateTime fechaPago;
    private String metodoPago;

    public DTPago(float montoPago, DTFormaPago formaPago, LocalDateTime fechaPago,String metodoPago) {
        this.metodoPago = metodoPago;
        this.montoPago = montoPago;
        this.formaPago = formaPago;
        this.fechaPago = fechaPago;
    }

    public DTPago() {
    }

    public void setMontoPago(float montoPago) {
        this.montoPago = montoPago;
    }

    public void setFormaPago(DTFormaPago formaPago) {
        this.formaPago = formaPago;
    }

    public void setFechaPago(LocalDateTime fechaPago) {
        this.fechaPago = fechaPago;
    }

    public void setMetodoPago(String metodoPago) {
        this.metodoPago = metodoPago;
    }

    public float getMontoPago() {
        return montoPago;
    }

    public DTFormaPago getFormaPago() {
        return formaPago;
    }

    public LocalDateTime getFechaPago() {
        return fechaPago;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    
}
