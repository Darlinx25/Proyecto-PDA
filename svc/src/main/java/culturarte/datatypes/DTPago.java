package culturarte.datatypes;

import java.time.LocalDateTime;

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
