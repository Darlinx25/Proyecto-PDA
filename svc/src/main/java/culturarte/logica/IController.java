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
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mark
 */
public interface IController {
    public void cargarDatosPrueba() throws NickRepetidoException, EmailRepetidoException, PropuestaDuplicadaException, CategoriaDuplicadaException, BadPasswordException;
    
    // <editor-fold defaultstate="collapsed" desc="Funciones usuarios.">
    public void addUsuario(DTUsuario user) throws NickRepetidoException, EmailRepetidoException, BadPasswordException; 
    /*Consulta de Perfil de colaborador*/
    public ArrayList<String> listarColaboradores();
    public DTColaborador obtenerDTColaborador(String nick);
    public ArrayList<String> obtenerPropuestasColaboradas(String nick);
    public ArrayList<String> listarProponentes();
    public DTProponente obtenerDTProponente(String nick);
    public ArrayList<String> listarColaboracionesColaborador(String nickColab);
    public ArrayList<String> listarUsuarios();
    public ResultadoSeguirUsuario seguirUsuario(String nickSegui, String nickUsu);
    public ResultadoSeguirUsuario dejarDeSeguirUsuario(String nickSegui, String nickSiguiendo);
    public ArrayList<String> listaPropuestasUsu(String nickname);//no devuelve las propuestas del usuario, devuelve todas
    public ArrayList<String> listarUsuariosSeguir(String nickname);
    public List<String> listarUsuariosSiguiendo(String nickname);
    public ArrayList<String> ObtenerSeguidores(String nickname);
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones categorías.">
    /*Alta de Categoría*/
    public DefaultMutableTreeNode listarCategorias();
    public void addCategoria(String nombre, String nombrePadre) throws CategoriaDuplicadaException;
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones propuestas.">
    public void addPropuesta(DTPropuesta prop)throws PropuestaDuplicadaException;
    public DTPropuesta obtenerDTPropuesta(String titulo);
    public ArrayList<String> listarPropuestasEstado(int estado);
    public String obtenerDineroRecaudado(String nombre);
    /*Registrar colaboracion a propuesta*/
    public ArrayList<String> listarPropuestasProponentes();
    public ArrayList<String> listarPropuestasProponentesIngresadas();
    public void cambiarEstadoPropuestaIngresada(String tituloProp, EstadoPropuesta estProp);
    public ArrayList<String> listarPropuestas();
    public void modPropuesta(DTPropuesta prop);
    public void cambiarEstadoPropuesta(String tituloProp, EstadoPropuesta estProp);
    public void hacerComentario(String comentario, String nombreColaborador, String tituloProp);
    public ArrayList<String> listarPropuestasEstadoUsu(int estado, String nick);
    public void extenderFinanciacion(String tituloProp);
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones colaboraciones.">
    //usar TipoRetorno luego en vez de String
    public void realizarColaboracion(String nickColab, String tituloProp, float montoColab, String tipoRetorno) throws PropuestaYaColaboradaException;
    public DTColaboracion obtenerDTColaboracion(Long id);
    public ArrayList<String> listarColaboraciones();
    public void eliminarColaboracion(Long id);
    public ArrayList<String> obtenerColaboradoresColaboracion(String tituloProp);
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones web.">
    public String obtenerTipoUser(String nickname);
    public boolean autenticarUsuario(String nickname, char[] password);
    public ArrayList<String> obtenerCategorias();
    public String guardarImagen(byte[] bytesImagen);//devuelve el nombre de la imagen luego de guardarla
    public ArrayList<DTPropuesta> buscarPropuestasTDL(String patron);
    // </editor-fold>
    
}
