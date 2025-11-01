package culturarte.datatypes;

public class DTPaypal extends DTFormaPago {

    private String cuentaPaypal;
    private String titularPaypal;

    public DTPaypal(String cuentaPaypal, String titularPaypal) {
        this.cuentaPaypal = cuentaPaypal;
        this.titularPaypal = titularPaypal;
    }

    public String getCuentaPaypal() {
        return cuentaPaypal;
    }

    public String getTitularPaypal() {
        return titularPaypal;
    }
    
    
}
