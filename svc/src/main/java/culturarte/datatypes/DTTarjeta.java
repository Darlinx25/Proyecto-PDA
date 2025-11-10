package culturarte.datatypes;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "DTTarjeta")
@XmlRootElement
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

    public DTTarjeta() {
        super();
    }

    public void setTipoTarjeta(String tipoTarjeta) {
        this.tipoTarjeta = tipoTarjeta;
    }

    public void setNroTarjeta(String nroTarjeta) {
        this.nroTarjeta = nroTarjeta;
    }

    public void setVencTarjeta(String vencTarjeta) {
        this.vencTarjeta = vencTarjeta;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    public void setTitularTarjeta(String titularTarjeta) {
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
