
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comentarios")

public class Comentario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

   private String informacion;
   private String nombreColaborador;
    
   @ManyToOne
    private Propuesta propuestaComentada;
 
   
protected Comentario(){

    }

   protected Comentario(String informacion,String nombreCol,Propuesta propuestaComentada){
       this.informacion = informacion;
       this.nombreColaborador = nombreCol;
       this.propuestaComentada = propuestaComentada;
   }
    public String getInformacion() {
        return informacion;
    }
    
    public void setInformacion(String info){
        this.informacion = info;
    }
    
    public String getNombreColaborador(){
        return nombreColaborador;
    }
    
    public void setNombreColaborador(String nombreCol){
        this.nombreColaborador = nombreCol;
    }

     public Long getId() {
        return id;
    }

    public Propuesta getPropuestaComentada(){
        return propuestaComentada;
    }
}
 
