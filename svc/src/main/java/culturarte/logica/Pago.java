package culturarte.logica;

import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

public class Pago {
    private float montoPago;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "forma_pago_id")
    private FormaPago formaPago;

    public Pago(float montoPago, FormaPago formaPago) {
        this.montoPago = montoPago;
        this.formaPago = formaPago;
    }

    public float getMontoPago() {
        return montoPago;
    }

    public void setMonto(float monto) {
        this.montoPago = monto;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }
    
    
}
