/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.datatypes;

import java.time.LocalDate;

import jakarta.xml.bind.annotation.XmlRootElement;
@XmlRootElement
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

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setPassword(char[] password) {
        this.password = password;
    }

    public void setPasswordConfirm(char[] passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
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
