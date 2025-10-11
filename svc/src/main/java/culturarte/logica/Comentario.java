
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;



@Embeddable
public class Comentario {
   private String informacion;
   private String nombreColaborador;
   
   protected Comentario(){

}

   protected Comentario(String informacion,String nombreCol){
       this.informacion = informacion;
       this.nombreColaborador = nombreCol;
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
}

