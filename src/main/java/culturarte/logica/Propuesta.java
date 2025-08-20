/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author mark
 */
public class Propuesta {
    private String titulo;
    private String descripcion;
    private BufferedImage imagen;
    private String lugarRealizara;
    private LocalDate fechaRealizara;
    private float precioEntrada;
    private float montoAReunir;
    private LocalDate fechaPublicacion;
    
    private Categoria tipoPropuesta;
    private Proponente proponedor;
    private Estado estadoActual;
    private ArrayList<Estado> historialEstados;
    private ArrayList<Colaboracion> colaboraciones;
}
