package culturarte.datatypes;

public class DTTarjeta extends DTFormaPago {

    private String tipoTarjeta;
    private String nroTarjeta;
    private String vencTarjeta;
    private String cvc;
    private String titularTarjeta;

    public DTTarjeta(String tipoTarjeta, String nroTarjeta,
            String vencTarjeta, String cvc, String titularTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
        this.nroTarjeta = nroTarjeta;
        this.vencTarjeta = vencTarjeta;
        this.cvc = cvc;
        this.titularTarjeta = titularTarjeta;
    }

    public String getTipoTarjeta() {
        return tipoTarjeta;
    }

    public String getNroTarjeta() {
        return nroTarjeta;
    }

    public String getVencTarjeta() {
        return vencTarjeta;
    }

    public String getCvc() {
        return cvc;
    }

    public String getTitularTarjeta() {
        return titularTarjeta;
    }
    
    
}
