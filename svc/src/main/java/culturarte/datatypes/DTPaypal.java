package culturarte.datatypes;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "DTPaypal")
@XmlRootElement
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

    public DTPaypal() {
    }

    public void setCuentaPaypal(String cuentaPaypal) {
        this.cuentaPaypal = cuentaPaypal;
    }

    public void setTitularPaypal(String titularPaypal) {
        this.titularPaypal = titularPaypal;
    }
    
    
}
