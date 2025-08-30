/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import jakarta.persistence.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mark
 */
@Entity
@Table(name = "colaboradores")
public class Colaborador extends Usuario {
    
    @OneToMany(mappedBy = "colaborador", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Colaboracion> colaboraciones = new ArrayList<>();
    
    public Colaborador(){
        
    }
    
    public Colaborador(String nickname, String nombre, String apellido, String email, LocalDate fechaNacimiento, String imagen) {
        super(nickname, nombre, apellido, email, fechaNacimiento, imagen);
        this.colaboraciones = new ArrayList<Colaboracion>();
    }
    
    public List<Colaboracion> getColaboraciones() {
        return colaboraciones;
    }

    public void setColaboraciones(ArrayList<Colaboracion> colaboraciones) {
        this.colaboraciones = colaboraciones;
    }
    
    
}
