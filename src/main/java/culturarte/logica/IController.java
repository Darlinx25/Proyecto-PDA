/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package culturarte.logica;

import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mark
 */
public interface IController {
    public void addUsuario(DTUsuario user);
    
    public DefaultMutableTreeNode listarCategorias();
    public void addCategoria(String nombre, String nombrePadre);
    public ArrayList<String> listarColaboradores();
    public ArrayList<String> listarProponentes();
    public void addPropuesta(DTPropuesta prop);
    DTProponente obtenerDTProponente(String nick);
}
