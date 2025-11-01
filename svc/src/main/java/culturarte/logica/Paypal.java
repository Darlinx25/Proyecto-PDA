package culturarte.logica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("PAYPAL")
public class Paypal extends FormaPago {
    private int nroCuenta;
    private String titular;

    public Paypal(int nroCuenta, String titular) {
        this.nroCuenta = nroCuenta;
        this.titular = titular;
    }

    public int getNroCuenta() {
        return nroCuenta;
    }

    public void setNroCuenta(int nroCuenta) {
        this.nroCuenta = nroCuenta;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
    
    
}
