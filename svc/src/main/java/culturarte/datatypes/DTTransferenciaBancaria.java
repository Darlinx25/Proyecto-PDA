package culturarte.datatypes;

public class DTTransferenciaBancaria extends DTFormaPago {

    private String nombreBanco;
    private String cuentaBanco;
    private String titularBanco;

    public DTTransferenciaBancaria(String nombreBanco, String cuentaBanco, String titularBanco) {
        this.nombreBanco = nombreBanco;
        this.cuentaBanco = cuentaBanco;
        this.titularBanco = titularBanco;
    }

    public String getNombreBanco() {
        return nombreBanco;
    }

    public String getCuentaBanco() {
        return cuentaBanco;
    }

    public String getTitularBanco() {
        return titularBanco;
    }
    
    
}
