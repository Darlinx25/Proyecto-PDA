/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

/**
 *
 * @author mark
 */
public class DTDireccion {
    private String ciudad;
    private String calle;
    private int numeroPuerta;

    public DTDireccion(String ciudad, String calle, int numeroPuerta) {
        this.ciudad = ciudad;
        this.calle = calle;
        this.numeroPuerta = numeroPuerta;
    }
    

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public int getNumeroPuerta() {
        return numeroPuerta;
    }

    public void setNumeroPuerta(int numeroPuerta) {
        this.numeroPuerta = numeroPuerta;
    }
    
    
}
