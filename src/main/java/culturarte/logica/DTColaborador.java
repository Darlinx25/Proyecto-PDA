/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.time.LocalDate;

/**
 *
 * @author mark
 */
public class DTColaborador extends DTUsuario {
    
    public DTColaborador(){
        
    }
    public DTColaborador(String nickname, String nombre, String apellido, String email, LocalDate fechaNacimiento, byte[] imagen) {
        super(nickname, nombre, apellido, email, fechaNacimiento, imagen);
    }
    
}
