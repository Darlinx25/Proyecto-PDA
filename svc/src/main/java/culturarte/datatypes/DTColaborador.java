/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;

import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import java.time.LocalDate;

/**
 *
 * @author mark
 */
@XmlType(name = "DTColaborador")
@XmlRootElement
public class DTColaborador extends DTUsuario {
    
    public DTColaborador(){
        
    }
    /*para crear*/
    public DTColaborador(String nickname, String nombre, String apellido, String password, String passwordConfirm, String email, String fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, password, passwordConfirm, email, fechaNacimiento, imagen);
    }
    /*para consultar*/
    public DTColaborador(String nickname, String nombre, String apellido, String email, String fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, email, fechaNacimiento, imagen);
    }
}
