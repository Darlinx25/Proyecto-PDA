/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package culturarte.logica;

import culturarte.excepciones.PropuestaYaColaboradaException;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mark
 */
public interface IController {
    public ResultadoRegistroUsr addUsuario(DTUsuario user);
    
    /*Alta de Categoría*/
    public DefaultMutableTreeNode listarCategorias();
    public void addCategoria(String nombre, String nombrePadre);
    /*Consulta de Perfil de colaborador*/
    public ArrayList<String> listarColaboradores();
    public DTColaborador obtenerDTColaborador(String nick);
    public ArrayList<String> obtenerPropuestasColaboradas(String nick);
    public ArrayList<String> listarProponentes();
    public void addPropuesta(DTPropuesta prop);
    public DTProponente obtenerDTProponente(String nick);
    public DTPropuesta obtenerDTPropuesta(String titulo);
    public ArrayList<String> listarColaboracionesColaborador(String nickColab);
    public DTColaboracion obtenerDTColaboracion(Long id);
    public ArrayList<String> listarColaboraciones();
    public void eliminarColaboracion(Long id);
    public ArrayList<String> listarUsuarios();
    public void seguirUsuario(String nickSegui, String nickUsu);
    public void dejarDeSeguirUsuario(String nickSegui, String nickSiguiendo);
    public ArrayList<String> listaPropuestasUsu(String nickname);//no devuelve las propuestas del usuario, devuelve todas
    public ArrayList<String> listarUsuariosSeguir(String nickname);
    
    public ArrayList<String> listarPropuestasEstado(int estado);
    public ArrayList<String> listarUsuariosSiguiendo(String nickname);
    /*Registrar colaboracion a propuesta*/
    public ArrayList<String> listarPropuestasProponentes();
    //usar TipoRetorno luego en vez de String
    public void realizarColaboracion(String nickColab, String tituloProp, float montoColab, String tipoRetorno) throws PropuestaYaColaboradaException;
    public String obtenerDineroRecaudado(String nombre);
    public ArrayList<String> obtenerColaboradoresColaboracion(String tituloProp);
    public ArrayList<String> listarPropuestas();
    public void modPropuesta(DTPropuesta prop);
}
