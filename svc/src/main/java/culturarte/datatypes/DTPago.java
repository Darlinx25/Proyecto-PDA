package culturarte.datatypes;

import java.time.LocalDateTime;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "DTPago")
@XmlRootElement
public class DTPago {
    private float montoPago;
    private DTFormaPago formaPago;
    private String fechaPago;
    private String metodoPago;

    public DTPago(float montoPago, DTFormaPago formaPago, String fechaPago,String metodoPago) {
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

    public void setFechaPago(String fechaPago) {
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

    public String getFechaPago() {
        return fechaPago;
    }
    
    public String getMetodoPago() {
        return metodoPago;
    }
    
    
}
