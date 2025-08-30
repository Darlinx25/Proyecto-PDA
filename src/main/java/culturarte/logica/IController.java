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
    public DTColaborador obtenerDTColaborador(String nick);
    public ArrayList<String> listarProponentes();
    public void addPropuesta(DTPropuesta prop);
    public DTProponente obtenerDTProponente(String nick);
    public DTPropuesta obtenerDTPropuesta(String titulo);
    public ArrayList<String> listarUsuarios();
    public void agregarUsuarioSeg(Usuario seguidor, Usuario  usuASeguir);
    public void seguirUsuario(String nickSegui, String nickUsu);
    public ArrayList<String> listaPropuestasUsu(String nickname);
    public ArrayList<String> listarUsuariosSeguir(String nickname);
    public ArrayList<String> listarPropuestasProponentes();
    public ArrayList<String> listarPropuestasEstado(int estado);
    //usar TipoRetorno luego en vez de String
    public void realizarColaboracion(String nickColab, String tituloProp, float montoColab, String tipoRetorno);
    public String obtenerDineroRecaudado(String nombre);
    public ArrayList<String> obtenerColaboradoresColaboracion(String tituloProp);
}
