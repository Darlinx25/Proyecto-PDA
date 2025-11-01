package culturarte.logica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("TRANSFERENCIA")
public class TransferenciaBancaria extends FormaPago {
    private String banco;
    private int nroCuenta;
    private String titular;

    public TransferenciaBancaria(String banco, int nroCuenta, String titular) {
        this.banco = banco;
        this.nroCuenta = nroCuenta;
        this.titular = titular;
    }

    public String getBanco() {
        return banco;
    }

    public void setBanco(String banco) {
        this.banco = banco;
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
