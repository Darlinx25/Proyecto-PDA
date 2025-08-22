/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.util.HashMap;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mark
 */

//SINGLETON
public class Controller implements IController {
    private Map<String, Usuario> usuarios;
    private Map<String, Propuesta> propuestas;
    private Map<String, Categoria> categorias;

    private static Controller instancia;
    
    private Controller() {
        usuarios = new HashMap<>();
        propuestas = new HashMap<>();
        categorias = new HashMap<>();
        
        categorias.put("Categorías", new Categoria("Categorías"));
    }
    
    public static Controller getInstance() {
        if (instancia == null) {
            instancia = new Controller();
        }
        return instancia;
    }

    @Override
    public DefaultMutableTreeNode listarCategorias() {
        //throw new UnsupportedOperationException("Not supported yet.");
        Categoria catRaiz = this.categorias.get("Categorías");
        DefaultMutableTreeNode raiz = nodosArbolCategorias(catRaiz);
        return raiz;
    }
    //helper para listarCategorias() recursivo
    private DefaultMutableTreeNode nodosArbolCategorias(Categoria cat) {
        if (cat == null) {
            return null;
        }
        DefaultMutableTreeNode nodito = new DefaultMutableTreeNode(cat.getNombre());
        if (cat.getSubCategorias() == null) {
            return nodito;
        }
        for (Categoria c : cat.getSubCategorias()) {
            nodito.add(nodosArbolCategorias(c));
        }
        return nodito;
    }

    @Override
    public void addCategoria(String nombre, String nombrePadre) {
        
        if (this.categorias.containsKey(nombre)) {
            return;//agregar exception luego
        }
        Categoria cat = new Categoria(nombre);
        this.categorias.put(nombre, cat);
        
        if (nombrePadre != null) {
            Categoria padre = this.categorias.get(nombrePadre);
            if (padre != null) {
                padre.addSubcategoria(cat);
            }
        } else {
            this.categorias.get("Categorías").addSubcategoria(cat);
        }
    }
    
}
