package culturarte.datatypes;

import java.time.LocalDateTime;

public class DTPago {
    private float montoPago;
    private DTFormaPago formaPago;
    private LocalDateTime fechaPago;

    public DTPago(float montoPago, DTFormaPago formaPago, LocalDateTime fechaPago) {
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
    
    
}
