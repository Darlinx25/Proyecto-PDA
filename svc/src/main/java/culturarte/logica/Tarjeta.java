package culturarte.logica;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("TARJETA")
public class Tarjeta extends FormaPago {
    private String tipo;
    private String numero;
    private String fechaVenc;
    private String cvc;
    private String titular;

    public Tarjeta(String tipo, String numero, String fechaVenc, String cvc, String titular) {
        this.tipo = tipo;
        this.numero = numero;
        this.fechaVenc = fechaVenc;
        this.cvc = cvc;
        this.titular = titular;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(String fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
    
    
}
