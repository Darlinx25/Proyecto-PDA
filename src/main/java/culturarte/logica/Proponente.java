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
public class Proponente extends Usuario {
    private DTDireccion direccion;
    private String biografia;
    private String sitioWeb;
    
    private ArrayList<Propuesta> propuestas;

    public Proponente(DTDireccion direccion, String biografia, String sitioWeb, String nickname, String nombre, String apellido, String email, LocalDate fechaNacimiento, BufferedImage imagen) {
        super(nickname, nombre, apellido, email, fechaNacimiento, imagen);
        this.direccion = direccion;
        this.biografia = biografia;
        this.sitioWeb = sitioWeb;
        this.propuestas = new ArrayList<Propuesta>();
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

    public ArrayList<Propuesta> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(ArrayList<Propuesta> propuestas) {
        this.propuestas = propuestas;
    }
    
    
}
