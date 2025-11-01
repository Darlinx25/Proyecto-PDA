package culturarte.logica;

public class Pago {
    private float monto;
    private FormaPago formaPago;

    public Pago(float monto, FormaPago formaPago) {
        this.monto = monto;
        this.formaPago = formaPago;
    }

    public float getMonto() {
        return monto;
    }

    public void setMonto(float monto) {
        this.monto = monto;
    }

    public FormaPago getFormaPago() {
        return formaPago;
    }

    public void setFormaPago(FormaPago formaPago) {
        this.formaPago = formaPago;
    }
    
    
}
