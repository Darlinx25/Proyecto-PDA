/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 *
 * @author faxcundo
 */
public class Usuario {
    private String nickname;
    private String nombre;
    private String apellido;
    private String email;
    private DTFecha fechaNacimiento;
    private BufferedImage imagen;
    
    private ArrayList<Usuario> usuariosSeguidos;
    private ArrayList<Propuesta> propuestasFavoritas;
}
