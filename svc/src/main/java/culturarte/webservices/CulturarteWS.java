package culturarte.webservices;

import culturarte.datatypes.*;
import culturarte.excepciones.*;
import culturarte.logica.Controller;
import culturarte.logica.EstadoPropuesta;
import culturarte.logica.ResultadoSeguirUsuario;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;
import javax.swing.tree.DefaultMutableTreeNode;

@WebService(serviceName = "CulturarteWS")
public class CulturarteWS {

    private Endpoint endpoint;

    public CulturarteWS() {}

    @WebMethod(exclude = true)
    public void publicar() {
        this.endpoint = Endpoint.publish("http://localhost:9128/culturarteWS", this);
    }

    @WebMethod(exclude = true)
    public Endpoint getEndpoint() {
        return this.endpoint;
    }

    @WebMethod(operationName = "addUsuario")
    public void addUsuario(@WebParam(name = "user") DTUsuario user) throws NickRepetidoException, EmailRepetidoException, BadPasswordException {
        Controller.getInstance().addUsuario(user);
    }

    @WebMethod(operationName = "listarColaboradores")
    public ArrayList<String> listarColaboradores() {
        return Controller.getInstance().listarColaboradores();
    }

    @WebMethod(operationName = "obtenerDTColaborador")
    public DTColaborador obtenerDTColaborador(@WebParam(name = "nick") String nick) {
        return Controller.getInstance().obtenerDTColaborador(nick);
    }

    @WebMethod(operationName = "obtenerPropuestasColaboradas")
    public ArrayList<String> obtenerPropuestasColaboradas(@WebParam(name = "nick") String nick) {
        return Controller.getInstance().obtenerPropuestasColaboradas(nick);
    }

    @WebMethod(operationName = "listarProponentes")
    public ArrayList<String> listarProponentes() {
        return Controller.getInstance().listarProponentes();
    }

    @WebMethod(operationName = "obtenerDTProponente")
    public DTProponente obtenerDTProponente(@WebParam(name = "nick") String nick) {
        return Controller.getInstance().obtenerDTProponente(nick);
    }

    @WebMethod(operationName = "listarColaboracionesColaborador")
    public ArrayList<String> listarColaboracionesColaborador(@WebParam(name = "nickColab") String nickColab) {
        return Controller.getInstance().listarColaboracionesColaborador(nickColab);
    }

    @WebMethod(operationName = "listarUsuarios")
    public ArrayList<String> listarUsuarios() {
        return Controller.getInstance().listarUsuarios();
    }

    @WebMethod(operationName = "seguirUsuario")
    public ResultadoSeguirUsuario seguirUsuario(@WebParam(name = "nickSegui") String nickSegui, @WebParam(name = "nickUsu") String nickUsu) {
        return Controller.getInstance().seguirUsuario(nickSegui, nickUsu);
    }

    @WebMethod(operationName = "dejarDeSeguirUsuario")
    public ResultadoSeguirUsuario dejarDeSeguirUsuario(@WebParam(name = "nickSegui") String nickSegui, @WebParam(name = "nickSiguiendo") String nickSiguiendo) {
        return Controller.getInstance().dejarDeSeguirUsuario(nickSegui, nickSiguiendo);
    }

    @WebMethod(operationName = "listaPropuestasUsu")
    public ArrayList<String> listaPropuestasUsu(@WebParam(name = "nickname") String nickname) {
        return Controller.getInstance().listaPropuestasUsu(nickname);
    }

    @WebMethod(operationName = "listarUsuariosSeguir")
    public ArrayList<String> listarUsuariosSeguir(@WebParam(name = "nickname") String nickname) {
        return Controller.getInstance().listarUsuariosSeguir(nickname);
    }

    @WebMethod(operationName = "listarUsuariosSiguiendo")
    public List<String> listarUsuariosSiguiendo(@WebParam(name = "nickname") String nickname) {
        return Controller.getInstance().listarUsuariosSiguiendo(nickname);
    }

    @WebMethod(operationName = "ObtenerSeguidores")
    public ArrayList<String> ObtenerSeguidores(@WebParam(name = "nickname") String nickname) {
        return Controller.getInstance().ObtenerSeguidores(nickname);
    }

    @WebMethod(operationName = "obtenerUsuariosPorRanking")
    public ArrayList<String> obtenerUsuariosPorRanking() {
        return Controller.getInstance().obtenerUsuariosPorRanking();
    }

    @WebMethod(operationName = "bajaProponente")
    public void bajaProponente(@WebParam(name = "nickname") String nickname) {
        Controller.getInstance().bajaProponente(nickname);
    }

    @WebMethod(operationName = "listaProponentesDeBaja")
    public ArrayList<String> listaProponentesDeBaja() {
        return Controller.getInstance().listaProponentesDeBaja();
    }

    @WebMethod(operationName = "obtenerDTProponenteDeBaja")
    public DTProponente obtenerDTProponenteDeBaja(@WebParam(name = "nick") String nick) {
        return Controller.getInstance().obtenerDTProponenteDeBaja(nick);
    }

    @WebMethod(operationName = "altaProponente")
    public void altaProponente(@WebParam(name = "nickname") String nickname) {
        Controller.getInstance().altaProponente(nickname);
    }

    @WebMethod(operationName = "listarCategorias")
    public DefaultMutableTreeNode listarCategorias() {
        return Controller.getInstance().listarCategorias();
    }

    @WebMethod(operationName = "addCategoria")
    public void addCategoria(@WebParam(name = "nombre") String nombre, @WebParam(name = "nombrePadre") String nombrePadre) throws CategoriaDuplicadaException {
        Controller.getInstance().addCategoria(nombre, nombrePadre);
    }

    @WebMethod(operationName = "addPropuesta")
    public void addPropuesta(@WebParam(name = "prop") DTPropuesta prop) throws PropuestaDuplicadaException {
        Controller.getInstance().addPropuesta(prop);
    }

    @WebMethod(operationName = "obtenerDTPropuesta")
    public DTPropuesta obtenerDTPropuesta(@WebParam(name = "titulo") String titulo) {
        return Controller.getInstance().obtenerDTPropuesta(titulo);
    }

    @WebMethod(operationName = "listarPropuestasEstado")
    public ArrayList<String> listarPropuestasEstado(@WebParam(name = "estado") int estado) {
        return Controller.getInstance().listarPropuestasEstado(estado);
    }

    @WebMethod(operationName = "obtenerDineroRecaudado")
    public String obtenerDineroRecaudado(@WebParam(name = "nombre") String nombre) {
        return Controller.getInstance().obtenerDineroRecaudado(nombre);
    }

    @WebMethod(operationName = "listarPropuestasProponentes")
    public ArrayList<String> listarPropuestasProponentes() {
        return Controller.getInstance().listarPropuestasProponentes();
    }

    @WebMethod(operationName = "listarPropuestasProponentesIngresadas")
    public ArrayList<String> listarPropuestasProponentesIngresadas() {
        return Controller.getInstance().listarPropuestasProponentesIngresadas();
    }

    @WebMethod(operationName = "cambiarEstadoPropuestaIngresada")
    public void cambiarEstadoPropuestaIngresada(@WebParam(name = "tituloProp") String tituloProp, @WebParam(name = "estProp") EstadoPropuesta estProp) {
        Controller.getInstance().cambiarEstadoPropuestaIngresada(tituloProp, estProp);
    }

    @WebMethod(operationName = "listarPropuestas")
    public ArrayList<String> listarPropuestas() {
        return Controller.getInstance().listarPropuestas();
    }

    @WebMethod(operationName = "modPropuesta")
    public void modPropuesta(@WebParam(name = "prop") DTPropuesta prop) {
        Controller.getInstance().modPropuesta(prop);
    }

    @WebMethod(operationName = "cambiarEstadoPropuesta")
    public void cambiarEstadoPropuesta(@WebParam(name = "tituloProp") String tituloProp, @WebParam(name = "estProp") EstadoPropuesta estProp) {
        Controller.getInstance().cambiarEstadoPropuesta(tituloProp, estProp);
    }

    @WebMethod(operationName = "hacerComentario")
    public void hacerComentario(@WebParam(name = "comentario") String comentario, @WebParam(name = "nombreColaborador") String nombreColaborador, @WebParam(name = "tituloProp") String tituloProp) {
        Controller.getInstance().hacerComentario(comentario, nombreColaborador, tituloProp);
    }

    @WebMethod(operationName = "comentarioExiste")
    public Boolean comentarioExiste(@WebParam(name = "titulo") String titulo, @WebParam(name = "nombreColaborador") String nombreColaborador) {
        return Controller.getInstance().comentarioExiste(titulo, nombreColaborador);
    }

    @WebMethod(operationName = "listarPropuestasEstadoUsu")
    public ArrayList<String> listarPropuestasEstadoUsu(@WebParam(name = "estado") int estado, @WebParam(name = "nick") String nick) {
        return Controller.getInstance().listarPropuestasEstadoUsu(estado, nick);
    }

    @WebMethod(operationName = "extenderFinanciacion")
    public void extenderFinanciacion(@WebParam(name = "tituloProp") String tituloProp) {
        Controller.getInstance().extenderFinanciacion(tituloProp);
    }

    @WebMethod(operationName = "favoritarPropuesta")
    public void favoritarPropuesta(@WebParam(name = "nick") String nick, @WebParam(name = "titulo") String titulo) {
        Controller.getInstance().favoritarPropuesta(nick, titulo);
    }

    @WebMethod(operationName = "propuestaYaFavorita")
    public Boolean propuestaYaFavorita(@WebParam(name = "titulo") String titulo, @WebParam(name = "nick") String nick) {
        return Controller.getInstance().propuestaYaFavorita(titulo, nick);
    }

    @WebMethod(operationName = "actualizarEstado")
    public void actualizarEstado() {
        Controller.getInstance().actualizarEstado();
    }

    @WebMethod(operationName = "listarPropuestasFavoritas")
    public ArrayList<String> listarPropuestasFavoritas(@WebParam(name = "nick") String nick) {
        return Controller.getInstance().listarPropuestasFavoritas(nick);
    }

    @WebMethod(operationName = "calcularPuntajePropuesta")
    public void calcularPuntajePropuesta(@WebParam(name = "titulo") String titulo) {
        Controller.getInstance().calcularPuntajePropuesta(titulo);
    }

    @WebMethod(operationName = "actualizarPuntajes")
    public void actualizarPuntajes() {
        Controller.getInstance().actualizarPuntajes();
    }

    @WebMethod(operationName = "obtenerRecomendaciones")
    public ArrayList<String> obtenerRecomendaciones(@WebParam(name = "nick") String nick) {
        return Controller.getInstance().obtenerRecomendaciones(nick);
    }

    @WebMethod(operationName = "listaPropuestasUsuDeBaja")
    public ArrayList<String> listaPropuestasUsuDeBaja(@WebParam(name = "nick") String nick) {
        return Controller.getInstance().listaPropuestasUsuDeBaja(nick);
    }

    @WebMethod(operationName = "obtenerDTPropuestaDeBaja")
    public DTPropuesta obtenerDTPropuestaDeBaja(@WebParam(name = "titulo") String titulo) {
        return Controller.getInstance().obtenerDTPropuestaDeBaja(titulo);
    }

    @WebMethod(operationName = "obtenerDineroRecaudadoDeBaja")
    public String obtenerDineroRecaudadoDeBaja(@WebParam(name = "tituloProp") String tituloProp) {
        return Controller.getInstance().obtenerDineroRecaudadoDeBaja(tituloProp);
    }

    @WebMethod(operationName = "realizarColaboracion")
    public void realizarColaboracion(@WebParam(name = "nickColab") String nickColab, @WebParam(name = "tituloProp") String tituloProp, @WebParam(name = "montoColab") float montoColab, @WebParam(name = "tipoRetorno") String tipoRetorno) throws PropuestaYaColaboradaException {
        Controller.getInstance().realizarColaboracion(nickColab, tituloProp, montoColab, tipoRetorno);
    }

    @WebMethod(operationName = "obtenerDTColaboracion")
    public DTColaboracion obtenerDTColaboracion(@WebParam(name = "id") Long id) {
        return Controller.getInstance().obtenerDTColaboracion(id);
    }

    @WebMethod(operationName = "listarColaboraciones")
    public ArrayList<String> listarColaboraciones() {
        return Controller.getInstance().listarColaboraciones();
    }

    @WebMethod(operationName = "eliminarColaboracion")
    public void eliminarColaboracion(@WebParam(name = "id") Long id) {
        Controller.getInstance().eliminarColaboracion(id);
    }

    @WebMethod(operationName = "obtenerColaboradoresColaboracion")
    public ArrayList<String> obtenerColaboradoresColaboracion(@WebParam(name = "tituloProp") String tituloProp) {
        return Controller.getInstance().obtenerColaboradoresColaboracion(tituloProp);
    }

    @WebMethod(operationName = "constanciaEmitida")
    public void constanciaEmitida(@WebParam(name = "id") Long id) {
        Controller.getInstance().constanciaEmitida(id);
    }

    @WebMethod(operationName = "obtenerTipoUser")
    public String obtenerTipoUser(@WebParam(name = "nickname") String nickname) {
        return Controller.getInstance().obtenerTipoUser(nickname);
    }

    @WebMethod(operationName = "autenticarUsuario")
    public boolean autenticarUsuario(@WebParam(name = "nickname") String nickname, @WebParam(name = "password") char[] password) {
        return Controller.getInstance().autenticarUsuario(nickname, password);
    }

    @WebMethod(operationName = "obtenerCategorias")
    public ArrayList<String> obtenerCategorias() {
        return Controller.getInstance().obtenerCategorias();
    }

    @WebMethod(operationName = "guardarImagen")
    public String guardarImagen(@WebParam(name = "bytesImagen") byte[] bytesImagen) {
        return Controller.getInstance().guardarImagen(bytesImagen);
    }

    @WebMethod(operationName = "buscarPropuestasTDL")
    public ArrayList<DTPropuesta> buscarPropuestasTDL(@WebParam(name = "patron") String patron) {
        return Controller.getInstance().buscarPropuestasTDL(patron);
    }

    @WebMethod(operationName = "registrarAcceso")
    public void registrarAcceso(@WebParam(name = "dataRegistro") DTRegistroAcceso dataRegistro) {
        Controller.getInstance().registrarAcceso(dataRegistro);
    }

    @WebMethod(operationName = "listDTRegistroAcceso")
    public List<DTRegistroAcceso> listDTRegistroAcceso() {
        return Controller.getInstance().listDTRegistroAcceso();
    }

    @WebMethod(operationName = "listDTColaboracionUser")
    public List<DTColaboracion> listDTColaboracionUser(@WebParam(name = "nickname") String nickname) {
        return Controller.getInstance().listDTColaboracionUser(nickname);
    }

    @WebMethod(operationName = "pagarColaboracion")
    public void pagarColaboracion(@WebParam(name = "dtPago") DTPago dtPago, @WebParam(name = "idColab") Long idColab) {
        Controller.getInstance().pagarColaboracion(dtPago, idColab);
    }

    @WebMethod(operationName = "mandarMail")
    public void mandarMail(@WebParam(name = "dtMail") DTMail dtMail) {
        Controller.getInstance().mandarMail(dtMail);
    }
}