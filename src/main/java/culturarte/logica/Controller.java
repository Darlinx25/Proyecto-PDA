/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import java.util.HashMap;
import java.util.Map;

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
        
        categorias.put("Categorías", new Categoria("Categorias"));
    }
    
    public static Controller getInstance() {
        if (instancia == null) {
            instancia = new Controller();
        }
        return instancia;
    }
}
