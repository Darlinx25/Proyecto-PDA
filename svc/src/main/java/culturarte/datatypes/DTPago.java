package culturarte.datatypes;

public class DTPago {
    private float montoPago;
    private DTFormaPago formaPago;

    public DTPago(float montoPago, DTFormaPago formaPago) {
        this.montoPago = montoPago;
        this.formaPago = formaPago;
    }

    public float getMontoPago() {
        return montoPago;
    }

    public DTFormaPago getFormaPago() {
        return formaPago;
    }
    
    
}
