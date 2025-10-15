/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import culturarte.datatypes.DTDireccion;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author mark
 */
@Entity
@Table(name = "proponentes")
public class Proponente extends Usuario {
    @Embedded
    private DTDireccion direccion;
    
    @Column(length = 2000)
    private String biografia;
    private String sitioWeb;
    
    @OneToMany(mappedBy = "proponente", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Propuesta> propuestas = new ArrayList<>();

    public Proponente(){}
    
    public Proponente(DTDireccion direccion, String biografia, String sitioWeb, String nickname, String nombre, String apellido, String passwordSalt, String passwordHash, String email, LocalDate fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, passwordSalt, passwordHash, email, fechaNacimiento, imagen);
        this.direccion = direccion;
        this.biografia = biografia;
        this.sitioWeb = sitioWeb;
        this.propuestas = new ArrayList<>();
    }

    public DTDireccion getDireccion() {
        return direccion;
    }

    public void setDireccion(DTDireccion direccion) {
        this.direccion = direccion;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getSitioWeb() {
        return sitioWeb;
    }

    public void setSitioWeb(String sitioWeb) {
        this.sitioWeb = sitioWeb;
    }

    public List<Propuesta> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(ArrayList<Propuesta> propuestas) {
        this.propuestas = propuestas;
    }
    
    
}
