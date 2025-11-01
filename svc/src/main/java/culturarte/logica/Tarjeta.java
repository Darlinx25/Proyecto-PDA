package culturarte.logica;

import java.time.LocalDate;

public class Tarjeta implements FormaPago {
    private String tipo;
    private int numero;
    private LocalDate fechaVenc;
    private int cvc;
    private String titular;

    public Tarjeta(String tipo, int numero, LocalDate fechaVenc, int cvc, String titular) {
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

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public LocalDate getFechaVenc() {
        return fechaVenc;
    }

    public void setFechaVenc(LocalDate fechaVenc) {
        this.fechaVenc = fechaVenc;
    }

    public int getCvc() {
        return cvc;
    }

    public void setCvc(int cvc) {
        this.cvc = cvc;
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }
    
    
}
