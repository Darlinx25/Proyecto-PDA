/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement
public class DTProponente extends DTUsuario {
    private DTDireccion direccion;
    private String biografia;
    private String sitioWeb;
    
    private LocalDate fechaBaja;
    
    public DTProponente(){
        
    }

    public void setDireccion(DTDireccion direccion) {
        this.direccion = direccion;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
    /*para crear*/
    public DTProponente(DTDireccion direccion, String biografia, String sitioWeb, String nickname, String nombre, String apellido, char[] password, char[] passwordConfirm, String email, LocalDate fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, password, passwordConfirm, email, fechaNacimiento, imagen);
        this.direccion = direccion;
        this.biografia = biografia;
        this.sitioWeb = sitioWeb;
    }
    /*para consultar*/
    public DTProponente(DTDireccion direccion, String biografia, String sitioWeb, String nickname, String nombre, String apellido, String email, LocalDate fechaNacimiento, String imagen,LocalDate fechaBaja) {
        super(nickname, nombre, apellido, email, fechaNacimiento, imagen);
        this.direccion = direccion;
        this.biografia = biografia;
        this.sitioWeb = sitioWeb;
        this.fechaBaja = fechaBaja;
    }

    public DTDireccion getDireccion() {
        return direccion;
    }

    public String getBiografia() {
        return biografia;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }
    
    public LocalDate getFechaBaja(){
        return this.fechaBaja;
    }


}
