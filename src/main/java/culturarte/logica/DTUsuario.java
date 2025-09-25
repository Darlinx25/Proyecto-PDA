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
public abstract class DTUsuario {
    private String nickname;
    private String nombre;
    private String apellido;
    private char[] password;
    private char[] passwordConfirm;
    private String email;
    private LocalDate fechaNacimiento;
    private String imagen;
    
    protected DTUsuario(){
        
    }
    /*para crear*/
    protected DTUsuario(String nickname, String nombre, String apellido, char[] password, char[] passwordConfirm, String email, LocalDate fechaNacimiento, String imagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.password = password;
        this.passwordConfirm = passwordConfirm;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.imagen = imagen;
    }
    /*para consultar*/
    protected DTUsuario(String nickname, String nombre, String apellido, String email, LocalDate fechaNacimiento, String imagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.imagen = imagen;
    }

    public String getNickname() {
        return nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public char[] getPassword() {
        return password;
    }

    public char[] getPasswordConfirm() {
        return passwordConfirm;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public String getImagen() {
        return imagen;
    }
}
