package culturarte.webservices;

import culturarte.datatypes.DTColaboracion;
import culturarte.datatypes.DTColaborador;
import culturarte.datatypes.DTMail;
import culturarte.datatypes.DTPago;
import culturarte.datatypes.DTProponente;
import culturarte.datatypes.DTPropuesta;
import culturarte.datatypes.DTRegistroAcceso;
import culturarte.datatypes.DTUsuario;
import culturarte.excepciones.BadPasswordException;
import culturarte.excepciones.CategoriaDuplicadaException;
import culturarte.excepciones.EmailRepetidoException;
import culturarte.excepciones.NickRepetidoException;
import culturarte.excepciones.PropuestaDuplicadaException;
import culturarte.excepciones.PropuestaYaColaboradaException;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import culturarte.logica.ResultadoSeguirUsuario;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebService;
import jakarta.xml.ws.Endpoint;
import java.util.ArrayList;
import java.util.List;


@WebService(serviceName = "ControllerWS")
public class ControllerWS {
    private Endpoint endpoint;
    private IController controller = IControllerFactory.getInstance().getIController();

    public ControllerWS() {
    }
    
    @WebMethod(exclude = true)
    public void publicar(){
        this.endpoint = 
                Endpoint.publish("http://localhost:9128/ControllerWS", this);
        //this.endpoint = Endpoint.publish("../service", this);
        System.out.println("Servicio publicado en "+this.endpoint.toString());
        System.out.println("Servicio publicado en "+this.endpoint.getEndpointReference().toString());
    }
    
    @WebMethod(exclude = true)
    public Endpoint getEndpoint(){
        return this.endpoint;
    }
    
    // <editor-fold defaultstate="collapsed" desc="Usuarios">
    
    @WebMethod(operationName = "addUsuario")
    public void addUsuario(@WebParam(name = "user") DTUsuario user) throws NickRepetidoException, EmailRepetidoException, BadPasswordException {
        controller.addUsuario(user);
    }

    @WebMethod(operationName = "listarColaboradores")
    public ArrayList<String> listarColaboradores() {
        return controller.listarColaboradores();
    }

    @WebMethod(operationName = "obtenerDTColaborador")
    public DTColaborador obtenerDTColaborador(@WebParam(name = "nick") String nick) {
        return controller.obtenerDTColaborador(nick);
    }

    @WebMethod(operationName = "obtenerPropuestasColaboradas")
    public ArrayList<String> obtenerPropuestasColaboradas(@WebParam(name = "nick") String nick) {
        return controller.obtenerPropuestasColaboradas(nick);
    }

    @WebMethod(operationName = "listarProponentes")
    public ArrayList<String> listarProponentes() {
        return controller.listarProponentes();
    }

    @WebMethod(operationName = "obtenerDTProponente")
    public DTProponente obtenerDTProponente(@WebParam(name = "nick") String nick) {
        return controller.obtenerDTProponente(nick);
    }

    @WebMethod(operationName = "listarColaboracionesColaborador")
    public ArrayList<String> listarColaboracionesColaborador(@WebParam(name = "nickColab") String nickColab) {
        return controller.listarColaboracionesColaborador(nickColab);
    }

    @WebMethod(operationName = "listarUsuarios")
    public ArrayList<String> listarUsuarios() {
        return controller.listarUsuarios();
    }

    @WebMethod(operationName = "seguirUsuario")
    public ResultadoSeguirUsuario seguirUsuario(@WebParam(name = "nickSegui") String nickSegui, @WebParam(name = "nickUsu") String nickUsu) {
        return controller.seguirUsuario(nickSegui, nickUsu);
    }

    @WebMethod(operationName = "dejarDeSeguirUsuario")
    public ResultadoSeguirUsuario dejarDeSeguirUsuario(@WebParam(name = "nickSegui") String nickSegui, @WebParam(name = "nickSiguiendo") String nickSiguiendo) {
        return controller.dejarDeSeguirUsuario(nickSegui, nickSiguiendo);
    }

    @WebMethod(operationName = "listaPropuestasUsu")
    public ArrayList<String> listaPropuestasUsu(@WebParam(name = "nickname") String nickname) {
        return controller.listaPropuestasUsu(nickname);
    }

    @WebMethod(operationName = "listarUsuariosSeguir")
    public ArrayList<String> listarUsuariosSeguir(@WebParam(name = "nickname") String nickname) {
        return controller.listarUsuariosSeguir(nickname);
    }

    @WebMethod(operationName = "listarUsuariosSiguiendo")
    public List<String> listarUsuariosSiguiendo(@WebParam(name = "nickname") String nickname) {
        return controller.listarUsuariosSiguiendo(nickname);
    }

    @WebMethod(operationName = "ObtenerSeguidores")
    public ArrayList<String> ObtenerSeguidores(@WebParam(name = "nickname") String nickname) {
        return controller.ObtenerSeguidores(nickname);
    }

    @WebMethod(operationName = "obtenerUsuariosPorRanking")
    public ArrayList<String> obtenerUsuariosPorRanking() {
        return controller.obtenerUsuariosPorRanking();
    }

    @WebMethod(operationName = "bajaProponente")
    public void bajaProponente(@WebParam(name = "nickname") String nickname) {
        controller.bajaProponente(nickname);
    }

    @WebMethod(operationName = "listaProponentesDeBaja")
    public ArrayList<String> listaProponentesDeBaja() {
        return controller.listaProponentesDeBaja();
    }

    @WebMethod(operationName = "obtenerDTProponenteDeBaja")
    public DTProponente obtenerDTProponenteDeBaja(@WebParam(name = "nick") String nick) {
        return controller.obtenerDTProponenteDeBaja(nick);
    }

    @WebMethod(operationName = "altaProponente")
    public void altaProponente(@WebParam(name = "nickname") String nickname) {
        controller.altaProponente(nickname);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Categorías">

    @WebMethod(operationName = "addCategoria")
    public void addCategoria(@WebParam(name = "nombre") String nombre, @WebParam(name = "nombrePadre") String nombrePadre) throws CategoriaDuplicadaException {
        controller.addCategoria(nombre, nombrePadre);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Propuestas">

    @WebMethod(operationName = "addPropuesta")
    public void addPropuesta(@WebParam(name = "prop") DTPropuesta prop) throws PropuestaDuplicadaException {
        controller.addPropuesta(prop);
    }

    @WebMethod(operationName = "obtenerDTPropuesta")
    public DTPropuesta obtenerDTPropuesta(@WebParam(name = "titulo") String titulo) {
        return controller.obtenerDTPropuesta(titulo);
    }

    @WebMethod(operationName = "listarPropuestasEstado")
    public ArrayList<String> listarPropuestasEstado(@WebParam(name = "estado") int estado) {
        return controller.listarPropuestasEstado(estado);
    }

    @WebMethod(operationName = "obtenerDineroRecaudado")
    public String obtenerDineroRecaudado(@WebParam(name = "nombre") String nombre) {
        return controller.obtenerDineroRecaudado(nombre);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Colaboraciones">

    @WebMethod(operationName = "realizarColaboracion")
    public void realizarColaboracion(@WebParam(name = "nickColab") String nickColab, @WebParam(name = "tituloProp") String tituloProp,
                                      @WebParam(name = "montoColab") float montoColab, @WebParam(name = "tipoRetorno") String tipoRetorno) throws PropuestaYaColaboradaException {
        controller.realizarColaboracion(nickColab, tituloProp, montoColab, tipoRetorno);
    }

    @WebMethod(operationName = "obtenerDTColaboracion")
    public DTColaboracion obtenerDTColaboracion(@WebParam(name = "id") Long id) {
        return controller.obtenerDTColaboracion(id);
    }

    @WebMethod(operationName = "listarColaboraciones")
    public ArrayList<String> listarColaboraciones() {
        return controller.listarColaboraciones();
    }

    @WebMethod(operationName = "eliminarColaboracion")
    public void eliminarColaboracion(@WebParam(name = "id") Long id) {
        controller.eliminarColaboracion(id);
    }

    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Funciones Web">

    @WebMethod(operationName = "obtenerTipoUser")
    public String obtenerTipoUser(@WebParam(name = "nickname") String nickname) {
        return controller.obtenerTipoUser(nickname);
    }

    @WebMethod(operationName = "autenticarUsuario")
    public boolean autenticarUsuario(@WebParam(name = "nickname") String nickname, @WebParam(name = "password") char[] password) {
        return controller.autenticarUsuario(nickname, password);
    }

    @WebMethod(operationName = "obtenerCategorias")
    public ArrayList<String> obtenerCategorias() {
        return controller.obtenerCategorias();
    }

    @WebMethod(operationName = "guardarImagen")
    public String guardarImagen(@WebParam(name = "bytesImagen") byte[] bytesImagen) {
        return controller.guardarImagen(bytesImagen);
    }

    @WebMethod(operationName = "buscarPropuestasTDL")
    public ArrayList<DTPropuesta> buscarPropuestasTDL(@WebParam(name = "patron") String patron) {
        return controller.buscarPropuestasTDL(patron);
    }

    @WebMethod(operationName = "registrarAcceso")
    public void registrarAcceso(@WebParam(name = "dataRegistro") DTRegistroAcceso dataRegistro) {
        controller.registrarAcceso(dataRegistro);
    }

    @WebMethod(operationName = "listDTRegistroAcceso")
    public List<DTRegistroAcceso> listDTRegistroAcceso() {
        return controller.listDTRegistroAcceso();
    }

    @WebMethod(operationName = "listDTColaboracionUser")
    public List<DTColaboracion> listDTColaboracionUser(@WebParam(name = "nickname") String nickname) {
        return controller.listDTColaboracionUser(nickname);
    }

    @WebMethod(operationName = "pagarColaboracion")
    public void pagarColaboracion(@WebParam(name = "dtPago") DTPago dtPago, @WebParam(name = "idColab") Long idColab) {
        controller.pagarColaboracion(dtPago, idColab);
    }

    @WebMethod(operationName = "mandarMail")
    public void mandarMail(@WebParam(name = "dtMail") DTMail dtMail) {
        controller.mandarMail(dtMail);
    }

    // </editor-fold>
}

    

