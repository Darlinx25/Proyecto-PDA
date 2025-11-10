package culturarte.datatypes;


import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlType(name = "DTTransferenciaBancaria")
@XmlRootElement
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

    public DTTransferenciaBancaria() {
        super();
    }

    public void setNombreBanco(String nombreBanco) {
        this.nombreBanco = nombreBanco;
    }

    public void setCuentaBanco(String cuentaBanco) {
        this.cuentaBanco = cuentaBanco;
    }

    public void setTitularBanco(String titularBanco) {
        this.titularBanco = titularBanco;
    }
    
    
}
