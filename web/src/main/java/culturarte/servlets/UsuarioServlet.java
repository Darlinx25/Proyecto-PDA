/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package culturarte.servlets;

import culturarte.excepciones.BadPasswordException;
import culturarte.excepciones.EmailRepetidoException;
import culturarte.excepciones.NickRepetidoException;
import culturarte.logica.DTColaboracion;
import culturarte.logica.DTColaborador;
import culturarte.logica.DTDireccion;
import culturarte.logica.DTProponente;
import culturarte.logica.DTPropuesta;
import culturarte.logica.DTUsuario;
import culturarte.logica.Estado;
import culturarte.logica.EstadoPropuesta;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import culturarte.logica.ResultadoSeguirUsuario;
import static culturarte.wutils.SesionUtils.esVisitante;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import static java.lang.System.console;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author mark
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuarios", "/crear-cuenta", "/perfil", "/login", "/logout", "/seguir-usuario", "/consultar-perfil-usuario"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, //1MB+ se escriben al disco
        maxFileSize = 1024 * 1024 * 5, //5MB máximo por archivo
        maxRequestSize = 1024 * 1024 * 10 //10MB máximo tamaño request
)
public class UsuarioServlet extends HttpServlet {

    private IController controller = IControllerFactory.getInstance().getIController();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
            case "/crear-cuenta":
                if (esVisitante(request.getSession())) {
                    request.getRequestDispatcher("/WEB-INF/jsp/crearCuenta.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/index");
                }
                break;
            case "/login":
                if (esVisitante(request.getSession())) {
                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesion.jsp").forward(request, response);
                } else {
                    response.sendRedirect("/index");
                }
                break;
            case "/seguir-usuario":
                request.getRequestDispatcher("/WEB-INF/jsp/seguirUsuario.jsp").forward(request, response);
                break;
            case "/perfil":
                cargarDatosPerfil(request, response, request.getParameter("user"));
                request.getRequestDispatcher("/WEB-INF/jsp/perfilUsuario.jsp").forward(request, response);
                break;
            case "/consultar-perfil-usuario":
                listarUsuarios(request, response);
                request.getRequestDispatcher("/WEB-INF/jsp/consultaPerfilUsuario.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
            case "/crear-cuenta":
                if (esVisitante(request.getSession())) {
                    procesarCrearCuenta(request, response);
                    response.sendRedirect("/login");
                } else {
                    response.sendRedirect("/index");
                }
                break;
            case "/login":
                if (esVisitante(request.getSession())) {
                    iniciarSesion(request, response);
                } else {
                    response.sendRedirect("/index");
                }
                break;
            case "/logout":
                if (!esVisitante(request.getSession())) {
                    cerrarSesion(request, response);
                }
                response.sendRedirect("/index");
                break;
            case "/seguir-usuario":
                String accion = request.getParameter("accion");
                String usuario = request.getParameter("usuario");
                if (usuario != null) {
                    seguirUser(request, response, usuario, accion);

                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Usuario no recibido");
                }
                break;

            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Procesamiento de requests.">
    protected void cargarDatosPerfil(HttpServletRequest request, HttpServletResponse response, String u)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        String tipoUser = this.controller.obtenerTipoUser(u);

        List<String> usuariosSeguidosSinRol = this.controller.listarUsuariosSiguiendo(u);

        ArrayList<String> seguidores = this.controller.ObtenerSeguidores(u);

        request.setAttribute("rol", tipoUser);

        ArrayList<String> usuariosSeguidos = new ArrayList<>();
        if (session.getAttribute("username") != u) {
                List<String> usuariosSeguidosPorlog = this.controller.listarUsuariosSiguiendo((String) session.getAttribute("username"));
                request.setAttribute("usuariosSeguidosLog", usuariosSeguidosPorlog);
        }

        if (session != null && session.getAttribute("usuario") != null) {

            

            if (session.getAttribute("username").equals(u)) {
                if ("colaborador".equals(tipoUser)) {
                    DTColaborador colab = this.controller.obtenerDTColaborador(u);
                    if (colab != null) {
                        
                        List<String> propuestasColaboradas = (List<String>) request.getAttribute("propuestasColab");
                        //Long id = Long.valueOf(request.propuestasColaboradas);

                        //DTColaboracion colaboracion = this.controller.obtenerDTColaboracion(id);
                        //SEGUIR VIENDO AGREGAR DATOS DE COLABORACION AL CONSULTAR COLABS EN PERFIL COLAB
                    }
                } else if ("proponente".equals(tipoUser)) {
                    DTProponente prop = this.controller.obtenerDTProponente(u);
                    if (prop != null) {

                    }
                }
            }

        }
        for (String cat : usuariosSeguidosSinRol) {
            String tipoU = this.controller.obtenerTipoUser(cat);
            usuariosSeguidos.add(cat + " - " + tipoU);
        }
        ArrayList<String> seguidoresConRol = new ArrayList<>();
        for (String cat : seguidores) {
            String tipoUs = this.controller.obtenerTipoUser(cat);
            seguidoresConRol.add(cat + " - " + tipoUs);
        }
        request.setAttribute("usuariosSeguidos", usuariosSeguidos);
        request.setAttribute("seguidores", seguidoresConRol);

        if ("colaborador".equals(tipoUser)) {
            DTColaborador colab = this.controller.obtenerDTColaborador(u);
            if (colab != null) {
                ArrayList<String> propColaboradas = this.controller.obtenerPropuestasColaboradas(colab.getNickname());
                request.setAttribute("propuestasColab", propColaboradas);
                request.setAttribute("username", u);
                request.setAttribute("nombre", colab.getNombre());
                request.setAttribute("apellido", colab.getApellido());
                request.setAttribute("email", colab.getEmail());
                request.setAttribute("ubiImagen", colab.getImagen());
            }

        } else if ("proponente".equals(tipoUser)) {
            DTProponente prop = this.controller.obtenerDTProponente(u);

            if (prop != null) {
                request.setAttribute("propuestasUsu", listaPropuestasPropPublicadas(prop.getNickname()));
                request.setAttribute("username", u);
                request.setAttribute("biografia", prop.getBiografia());
                request.setAttribute("sitioWeb", prop.getSitioWeb());
                request.setAttribute("nombre", prop.getNombre());
                request.setAttribute("apellido", prop.getApellido());
                request.setAttribute("ubiImagen", prop.getImagen());

            }
        }
    }

    private void seguirUser(HttpServletRequest request, HttpServletResponse response, String userAseguir, String accion)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            String user = (String) session.getAttribute("username");
            if (accion.equals("seguir")) {
                ResultadoSeguirUsuario s = this.controller.seguirUsuario(user, userAseguir);
            } else {
                ResultadoSeguirUsuario s = this.controller.dejarDeSeguirUsuario(user, userAseguir);
            }

        }
    }

    protected void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String tipoUsuario = this.controller.obtenerTipoUser(nickname);
        boolean autValida = this.controller.autenticarUsuario(nickname, password.toCharArray());

        if (tipoUsuario != null && autValida) {
            HttpSession session = request.getSession(true);
            session.setAttribute("rol", tipoUsuario);
            session.setAttribute("username", nickname);
            response.sendRedirect("/index");
            if (tipoUsuario.equals("colaborador")) {
                DTColaborador colab = this.controller.obtenerDTColaborador(nickname);
                String nom = colab.getNombre();
                String apell = colab.getApellido();
                String ubi = colab.getImagen();
                session.setAttribute("nombre", nom);
                session.setAttribute("apellido", apell);
                session.setAttribute("ubiImagen", ubi);
            } else if (tipoUsuario.equals("proponente")) {
                DTProponente prop = this.controller.obtenerDTProponente(nickname);
                String nom = prop.getNombre();
                String apell = prop.getApellido();
                String ubi = prop.getImagen();
                session.setAttribute("nombre", nom);
                session.setAttribute("apellido", apell);
                session.setAttribute("ubiImagen", ubi);

            }

        } else {
            request.setAttribute("mensajeError", "Usuario o contraseña incorrectos!");
            request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesion.jsp").forward(request, response);
        }

    }

    protected void procesarCrearCuenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String tipoUsuario = request.getParameter("tipoUsuario");

        String fNacString = request.getParameter("fechaNacimiento");
        LocalDate fechaNacimiento = parsearFecha(fNacString);

        Part parteArchivo = request.getPart("imagen");
        byte[] bytesImagen = partABytes(parteArchivo);
        String nombreImagen = this.controller.guardarImagen(bytesImagen);

        DTUsuario user = null;

        if (tipoUsuario.equals("proponente")) {
            String ciudad = request.getParameter("ciudad");
            String calle = request.getParameter("calle");
            String numPuertaString = request.getParameter("numPuerta");
            int numPuerta = Integer.parseInt(numPuertaString);
            DTDireccion direccion = new DTDireccion(ciudad, calle, numPuerta);
            String biografia = request.getParameter("biografia");
            String sitioWeb = request.getParameter("sitioWeb");

            user = new DTProponente(direccion, biografia, sitioWeb, nickname, nombre, apellido,
                    password.toCharArray(), passwordConfirm.toCharArray(), email, fechaNacimiento, nombreImagen);

        } else if (tipoUsuario.equals("colaborador")) {
            user = new DTColaborador(nickname, nombre, apellido,
                    password.toCharArray(), passwordConfirm.toCharArray(), email, fechaNacimiento, nombreImagen);
        }

        try {
            this.controller.addUsuario(user);
        } catch (NickRepetidoException | EmailRepetidoException | BadPasswordException ex) {
            System.getLogger(UsuarioServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    protected void cerrarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Funciones auxiliares.">
    private byte[] partABytes(Part parteArchivo) {
        byte[] bytesArchivo = null;

        if (parteArchivo != null && parteArchivo.getSize() > 0) {
            try (InputStream input = parteArchivo.getInputStream()) {
                bytesArchivo = input.readAllBytes();
            } catch (IOException ex) {
                System.getLogger(UsuarioServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            }
        }
        return bytesArchivo;
    }

    private ArrayList<String> listaPropuestasPropPublicadas(String nick) {
        ArrayList<String> aux = this.controller.listaPropuestasUsu(nick);
        ArrayList<String> propuestasPubli = new ArrayList<>();
        for (String p : aux) {
            DTPropuesta prop = this.controller.obtenerDTPropuesta(p);
            Estado est = prop.getEstadoActual();
            if (est.getEstado() != EstadoPropuesta.INGRESADA) {
                propuestasPubli.add(p);
            }
        }

        return propuestasPubli;
    }

    private LocalDate parsearFecha(String fechaString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date utilDate = null;
        try {
            utilDate = formatter.parse(fechaString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        LocalDate fecha = null;
        if (utilDate != null) {
            fecha = utilDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }
        return fecha;
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<String> usuarios = this.controller.listarUsuarios();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/jsp/consultaPerfilUsuario.jsp").forward(request, response);
    }

    // </editor-fold>
}
