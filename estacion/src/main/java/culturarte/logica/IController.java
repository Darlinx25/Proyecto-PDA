/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package culturarte.logica;

import culturarte.excepciones.BadPasswordException;
import culturarte.excepciones.CategoriaDuplicadaException;
import culturarte.excepciones.EmailRepetidoException;
import culturarte.excepciones.NickRepetidoException;
import culturarte.excepciones.PropuestaDuplicadaException;
import culturarte.excepciones.PropuestaYaColaboradaException;
import java.util.ArrayList;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mark
 */
public interface IController {
    public void addUsuario(DTUsuario user) throws NickRepetidoException, EmailRepetidoException, BadPasswordException; 
    
    /*Alta de Categoría*/
    public DefaultMutableTreeNode listarCategorias();
    public void addCategoria(String nombre, String nombrePadre) throws CategoriaDuplicadaException;
    /*Consulta de Perfil de colaborador*/
    public ArrayList<String> listarColaboradores();
    public DTColaborador obtenerDTColaborador(String nick);
    public ArrayList<String> obtenerPropuestasColaboradas(String nick);
    public ArrayList<String> listarProponentes();
    public void addPropuesta(DTPropuesta prop)throws PropuestaDuplicadaException;
    public DTProponente obtenerDTProponente(String nick);
    public DTPropuesta obtenerDTPropuesta(String titulo);
    public ArrayList<String> listarColaboracionesColaborador(String nickColab);
    public DTColaboracion obtenerDTColaboracion(Long id);
    public ArrayList<String> listarColaboraciones();
    public void eliminarColaboracion(Long id);
    public ArrayList<String> listarUsuarios();
    public ResultadoSeguirUsuario seguirUsuario(String nickSegui, String nickUsu);
    public ResultadoSeguirUsuario dejarDeSeguirUsuario(String nickSegui, String nickSiguiendo);
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
    public void cargarDatosPrueba() throws NickRepetidoException, EmailRepetidoException, PropuestaDuplicadaException, CategoriaDuplicadaException, BadPasswordException;
    
    public ArrayList<String> listarPropuestasProponentesIngresadas();
    public void cambiarEstadoPropuestaIngresada(String tituloProp, EstadoPropuesta estProp);
    
    public boolean obtenerUser(String nombre);
}
