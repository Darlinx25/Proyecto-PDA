package culturarte.logica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("PAYPAL")
public class Paypal extends FormaPago {
    private String nroCuenta;
    private String titular;

    public Paypal() {}
    
    public Paypal(String nroCuenta, String titular) {
        this.nroCuenta = nroCuenta;
        this.titular = titular;
    }

    public String getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(String nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
    
    
}
