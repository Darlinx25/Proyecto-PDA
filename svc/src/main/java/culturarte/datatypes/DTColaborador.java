/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;

import jakarta.xml.bind.annotation.XmlRootElement;
import java.time.LocalDate;

/**
 *
 * @author mark
 */
@XmlRootElement
public class DTColaborador extends DTUsuario {
    
    public DTColaborador(){
        
    }
    /*para crear*/
    public DTColaborador(String nickname, String nombre, String apellido, char[] password, char[] passwordConfirm, String email, LocalDate fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, password, passwordConfirm, email, fechaNacimiento, imagen);
    }
    /*para consultar*/
    public DTColaborador(String nickname, String nombre, String apellido, String email, LocalDate fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, email, fechaNacimiento, imagen);
    }
}
