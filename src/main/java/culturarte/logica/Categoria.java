/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.util.ArrayList;

/**
 *
 * @author mark
 */
public class Categoria {
    private String nombre;
    
    private ArrayList<Categoria> subCategorias;
    private ArrayList<Propuesta> propuestas;
    
    public Categoria(String nombre) {
        this.nombre = nombre;
        this.subCategorias = new ArrayList<Categoria>();//si no inicializamos quedan en null, no vacías
        this.propuestas = new ArrayList<Propuesta>();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ArrayList<Categoria> getSubCategorias() {
        return subCategorias;
    }

    public void setSubCategorias(ArrayList<Categoria> subCategorias) {
        this.subCategorias = subCategorias;
    }

    public ArrayList<Propuesta> getPropuestas() {
        return propuestas;
    }

    public void setPropuestas(ArrayList<Propuesta> propuestas) {
        this.propuestas = propuestas;
    }
    
    public void addSubcategoria(Categoria cat) {
        this.subCategorias.add(cat);
    }
    
}
