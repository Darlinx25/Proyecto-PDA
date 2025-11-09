/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;
import jakarta.persistence.*;

/**
 *
 * @author mark
 */
import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement
@Embeddable
public class DTDireccion {
    private String ciudad;
    private String calle;
    private int numeroPuerta;
    
    
    public DTDireccion(){
        
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public void setNumeroPuerta(int numeroPuerta) {
        this.numeroPuerta = numeroPuerta;
    }
    
    public DTDireccion(String ciudad, String calle, int numeroPuerta) {
        this.ciudad = ciudad;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
    }
    
    public String getCiudad() {
        return ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public int getNumeroPuerta() {
        return numeroPuerta;
    }
}
