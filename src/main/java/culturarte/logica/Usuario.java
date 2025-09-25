/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author faxcundo
 */

@Entity 
@Table(name = "usuario")
@Inheritance(strategy = InheritanceType.JOINED)//ver si joinded o ver si typeperclas
public abstract class Usuario {
    
    @Id
    private String nickname;
    
    private String nombre;
    private String apellido;
    private String passwordSalt;
    private String passwordHash;
    
    @Column(nullable  = false, unique = true)
    private String email;
    
    private LocalDate fechaNacimiento;
    
    String imagen;
    
    @ManyToMany
    @JoinTable(
    name = "usuario_seguidos",
    joinColumns = @JoinColumn(name = "seguidor_id"),
    inverseJoinColumns = @JoinColumn(name = "seguido_id"))
    private List<Usuario> usuariosSeguidos = new ArrayList<>();
    
    
    @ManyToMany
    @JoinTable(
    name = "usuario_favoritas",
    joinColumns = @JoinColumn(name = "usuario_nick"),
    inverseJoinColumns = @JoinColumn(name = "propuesta_id"))
    private List<Propuesta> propuestasFavoritas = new ArrayList<>();
    
    protected Usuario(){
        
    }
    protected Usuario(String nickname, String nombre, String apellido, String passwordSalt, String passwordHash, String email, LocalDate fechaNacimiento, String imagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.passwordSalt = passwordSalt;
        this.passwordHash = passwordHash;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.imagen = imagen;
        this.usuariosSeguidos = new ArrayList<>();
        this.propuestasFavoritas = new ArrayList<>();
    }
    
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getPasswordSalt() {
        return passwordSalt;
    }

    public void setPasswordSalt(String passwordSalt) {
        this.passwordSalt = passwordSalt;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public List<Usuario> getUsuariosSeguidos() {
        return usuariosSeguidos;
    }

    public void setUsuariosSeguidos(List<Usuario> usuariosSeguidos) {
        this.usuariosSeguidos = usuariosSeguidos;
    }

    public List<Propuesta> getPropuestasFavoritas() {
        return propuestasFavoritas;
    }

    public void setPropuestasFavoritas(List<Propuesta> propuestasFavoritas) {
        this.propuestasFavoritas = propuestasFavoritas;
    }
    
    
}
