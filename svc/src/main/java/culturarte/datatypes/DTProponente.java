/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;

import java.time.LocalDate;

/**
 *
 * @author mark
 */
public class DTProponente extends DTUsuario {
    private DTDireccion direccion;
    private String biografia;
    private String sitioWeb;
    
    public DTProponente(){
        
    }
    /*para crear*/
    public DTProponente(DTDireccion direccion, String biografia, String sitioWeb, String nickname, String nombre, String apellido, char[] password, char[] passwordConfirm, String email, LocalDate fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, password, passwordConfirm, email, fechaNacimiento, imagen);
        this.direccion = direccion;
        this.biografia = biografia;
        this.sitioWeb = sitioWeb;
    }
    /*para consultar*/
    public DTProponente(DTDireccion direccion, String biografia, String sitioWeb, String nickname, String nombre, String apellido, String email, LocalDate fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, email, fechaNacimiento, imagen);
        this.direccion = direccion;
        this.biografia = biografia;
        this.sitioWeb = sitioWeb;
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
}
