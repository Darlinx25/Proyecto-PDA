package culturarte.logica;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagos")
public class Pago {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private float montoPago;
    
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "forma_pago_id")
    private FormaPago formaPago;

    public Pago() {}
    
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
