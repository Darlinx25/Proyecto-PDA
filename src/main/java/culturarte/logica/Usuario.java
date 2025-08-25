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
    
    @Column(nullable  = false, unique = true)
    private String email;
    
    private LocalDate fechaNacimiento;
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length = 40000000)//40 MB
    private byte[] imagen;
    
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
    protected Usuario(String nickname, String nombre, String apellido, String email, LocalDate fechaNacimiento, byte[] imagen) {
        this.nickname = nickname;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.fechaNacimiento = fechaNacimiento;
        this.imagen = imagen;
        this.usuariosSeguidos = new ArrayList<Usuario>();
        this.propuestasFavoritas = new ArrayList<Propuesta>();
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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public List<Usuario> getUsuariosSeguidos() {
        return usuariosSeguidos;
    }

    public void setUsuariosSeguidos(ArrayList<Usuario> usuariosSeguidos) {
        this.usuariosSeguidos = usuariosSeguidos;
    }

    public List<Propuesta> getPropuestasFavoritas() {
        return propuestasFavoritas;
    }

    public void setPropuestasFavoritas(ArrayList<Propuesta> propuestasFavoritas) {
        this.propuestasFavoritas = propuestasFavoritas;
    }
    
    
}
