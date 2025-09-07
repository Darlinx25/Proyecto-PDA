/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 *
 * @author mark
 */

@Embeddable
public class Estado {
    
    
   
    private EstadoPropuesta estado;
    
    
    private LocalDateTime fechaEstado;
    
    public Estado(){
        
    }
    
    public Estado(EstadoPropuesta estado) {
        this.estado = estado;
        this.fechaEstado = LocalDateTime.now();
    }

    
    
    public EstadoPropuesta getEstado() {
        return estado;
    }

    public void setEstado(EstadoPropuesta estado) {
        this.estado = estado;
    }

    public LocalDateTime getFechaEstado() {
        return fechaEstado;
    }

    public void setFechaEstado(LocalDateTime fechaEstado) {
        this.fechaEstado = fechaEstado;
    }
    
    
}
