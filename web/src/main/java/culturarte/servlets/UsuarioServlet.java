package culturarte.servlets;

import static culturarte.wutils.SesionUtils.esVisitante;
import culturarte.wutils.Tracking;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import webservices.BadPasswordException_Exception;
import webservices.ControllerWS;
import webservices.ControllerWS_Service;
import webservices.DTColaborador;
import webservices.DTDireccion;
import webservices.DTProponente;
import webservices.DTPropuesta;
import webservices.DTUsuario;
import webservices.EmailRepetidoException_Exception;
import webservices.Estado;
import webservices.EstadoPropuesta;
import webservices.NickRepetidoException_Exception;
import webservices.ResultadoSeguirUsuario;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuarios", "/crear-cuenta", "/perfil", "/login", "/logout", "/seguir-usuario", "/consultar-perfil-usuario", "/ranking-usuario", "/baja-proponente", "/verificarUsuario", "/verificarCorreo"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, //1MB+ se escriben al disco
        maxFileSize = 1024 * 1024 * 5, //5MB máximo por archivo
        maxRequestSize = 1024 * 1024 * 10 //10MB máximo tamaño request
)
public class UsuarioServlet extends HttpServlet {

    private ControllerWS webServices;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();

        this.webServices.registrarAcceso(Tracking.generarDTRegistroAcceso(request));
        sincImg();
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
                    String userAgent = request.getHeader("User-Agent").toLowerCase();
                    boolean esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                            || userAgent.contains("iphone") || userAgent.contains("ipad");

                    if (esMovil) {

                        request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                    } else {

                        request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesion.jsp").forward(request, response);

                    }

                } else {
                    response.sendRedirect("/index");
                }
                break;
            case "/seguir-usuario":
                HttpSession session = request.getSession();
                String userAgent = request.getHeader("User-Agent").toLowerCase();
                String rol = (String) session.getAttribute("rol");
                boolean esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                        || userAgent.contains("iphone") || userAgent.contains("ipad");

                if (esMovil && rol == null) {

                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                } else if (esMovil && "colaborador".equals(rol)) {

                    request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/jsp/seguirUsuario.jsp").forward(request, response);
                }
                break;
            case "/perfil":
                session = request.getSession();
                userAgent = request.getHeader("User-Agent").toLowerCase();
                rol = (String) session.getAttribute("rol");
                esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                        || userAgent.contains("iphone") || userAgent.contains("ipad");

                if (esMovil && rol == null) {

                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                } else if (esMovil && "colaborador".equals(rol)) {

                    request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
                } else {
                    cargarDatosPerfil(request, response, request.getParameter("user"));
                    request.getRequestDispatcher("/WEB-INF/jsp/perfilUsuario.jsp").forward(request, response);
                }
                break;
            case "/consultar-perfil-usuario":
                session = request.getSession();
                userAgent = request.getHeader("User-Agent").toLowerCase();
                rol = (String) session.getAttribute("rol");
                esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                        || userAgent.contains("iphone") || userAgent.contains("ipad");

                if (esMovil && rol == null) {

                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                } else if (esMovil && "colaborador".equals(rol)) {

                    request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
                } else {
                    listarUsuarios(request, response);
                    request.getRequestDispatcher("/WEB-INF/jsp/consultaPerfilUsuario.jsp").forward(request, response);
                }
                break;
            case "/ranking-usuario":
                session = request.getSession();
                userAgent = request.getHeader("User-Agent").toLowerCase();
                rol = (String) session.getAttribute("rol");
                esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                        || userAgent.contains("iphone") || userAgent.contains("ipad");

                if (esMovil && rol == null) {

                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                } else if (esMovil && "colaborador".equals(rol)) {

                    request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
                } else {
                    List<String> usuarios = this.webServices.obtenerUsuariosPorRanking();
                    request.setAttribute("usuarios", usuarios);
                    request.getRequestDispatcher("/WEB-INF/jsp/consultaPerfilUsuario.jsp").forward(request, response);
                }
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
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();

        switch (path) {
            case "/crear-cuenta":
                if (esVisitante(request.getSession())) {
                    try {
                        procesarCrearCuenta(request, response);
                        response.sendRedirect("/login");
                    } catch (NickRepetidoException_Exception | EmailRepetidoException_Exception | BadPasswordException_Exception ex) {
                        if (ex instanceof NickRepetidoException_Exception) {
                            request.setAttribute("error", "Error el nombre de usuario ya existe.");
                        } else if (ex instanceof EmailRepetidoException_Exception) {
                            request.setAttribute("error", "Error el correo electrónico ya está registrado.");
                        } else if (ex instanceof BadPasswordException_Exception) {
                            request.setAttribute("error", "Error la contraseña no cumple los requisitos.");
                        }
                        request.getRequestDispatcher("/WEB-INF/jsp/crearCuenta.jsp").forward(request, response);
                    }
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
            case "/baja-proponente":
                HttpSession session = request.getSession(false);
                String user = (String) session.getAttribute("username");
                this.webServices.bajaProponente(user);
                if (!esVisitante(request.getSession())) {
                    cerrarSesion(request, response);
                }
                response.sendRedirect("/index");
                break;

            case "/verificarUsuario": {
                String usuarioVerificar = request.getParameter("usuario");
                DTUsuario dtColaborador = webServices.obtenerDTColaborador(usuarioVerificar);
                DTUsuario dtProponente = webServices.obtenerDTProponente(usuarioVerificar);

                String estado = (dtColaborador == null && dtProponente == null) ? "disponible" : "ocupado";
                response.setContentType("text/plain");
                response.getWriter().write(estado);
                break;
            }

            case "/verificarCorreo": {
                String correo = request.getParameter("correo");
                DTUsuario dtColaborador = webServices.obtenerDTColaborador(correo);
                DTUsuario dtProponente = webServices.obtenerDTProponente(correo);

                String estado = (dtColaborador == null && dtProponente == null) ? "disponible" : "ocupado";
                response.setContentType("text/plain");
                response.getWriter().write(estado);
                break;
            }

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
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();

        String tipoUser = this.webServices.obtenerTipoUser(u);

        List<String> usuariosSeguidosSinRol = this.webServices.listarUsuariosSiguiendo(u);

        List<String> seguidores = this.webServices.obtenerSeguidores(u);
        List<String> propsFav = this.webServices.listarPropuestasFavoritas(u);

        request.setAttribute("propuestasFav", propsFav);
        request.setAttribute("rol", tipoUser);
        ArrayList<String> usuariosSeguidos = new ArrayList<>();
        if (session.getAttribute("username") != u) {
            List<String> usuariosSeguidosPorlog = this.webServices.listarUsuariosSiguiendo((String) session.getAttribute("username"));
            request.setAttribute("usuariosSeguidosLog", usuariosSeguidosPorlog);
        }

        for (String cat : usuariosSeguidosSinRol) {
            String tipoU = this.webServices.obtenerTipoUser(cat);
            if (tipoU != null) {
                usuariosSeguidos.add(cat + " - " + tipoU);
            }

        }
        ArrayList<String> seguidoresConRol = new ArrayList<>();
        for (String cat : seguidores) {
            String tipoUs = this.webServices.obtenerTipoUser(cat);
            if (tipoUs != null) {
                seguidoresConRol.add(cat + " - " + tipoUs);
            }

        }
        request.setAttribute("usuariosSeguidos", usuariosSeguidos);
        request.setAttribute("seguidores", seguidoresConRol);

        if ("colaborador".equals(tipoUser)) {
            DTColaborador colab = this.webServices.obtenerDTColaborador(u);
            if (colab != null) {
                List<String> propColaboradas = this.webServices.listarColaboracionesColaborador(colab.getNickname());
                request.setAttribute("propuestasColab", propColaboradas);
                request.setAttribute("username", u);
                request.setAttribute("nombre", colab.getNombre());
                request.setAttribute("apellido", colab.getApellido());
                request.setAttribute("email", colab.getEmail());
                String img = colab.getImagen();
                request.setAttribute("ubiImagen", img);
            }

        } else if ("proponente".equals(tipoUser)) {
            DTProponente prop = this.webServices.obtenerDTProponente(u);

            if (prop != null) {
                request.setAttribute("propuestasPropias", this.webServices.listaPropuestasUsu(prop.getNickname()));
                request.setAttribute("propuestasUsu", listaPropuestasPropPublicadas(prop.getNickname()));
                request.setAttribute("username", u);
                request.setAttribute("biografia", prop.getBiografia());
                request.setAttribute("sitioWeb", prop.getSitioWeb());
                request.setAttribute("nombre", prop.getNombre());
                request.setAttribute("apellido", prop.getApellido());
                String img = prop.getImagen();
                request.setAttribute("ubiImagen", img);
            }
        }
    }

    private void seguirUser(HttpServletRequest request, HttpServletResponse response, String userAseguir, String accion)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();
        if (session != null) {
            String user = (String) session.getAttribute("username");
            if (accion.equals("seguir")) {
                ResultadoSeguirUsuario s = this.webServices.seguirUsuario(user, userAseguir);
            } else {
                ResultadoSeguirUsuario s = this.webServices.dejarDeSeguirUsuario(user, userAseguir);
            }

        }
    }

    protected void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String recordarme = request.getParameter("recordarme");
        String tipoUsuario = this.webServices.obtenerTipoUser(nickname);
        boolean autValida = this.webServices.autenticarUsuario(nickname, password);
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        boolean esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                || userAgent.contains("iphone") || userAgent.contains("ipad");
        if (tipoUsuario != null && autValida) {
            HttpSession session = request.getSession(true);
            System.out.println(tipoUsuario + "Nombre");
            if (tipoUsuario.equals("colaborador")) {
                DTColaborador colab = this.webServices.obtenerDTColaborador(nickname);
                String nom = colab.getNombre();
                String apell = colab.getApellido();
                String ubi = colab.getImagen();
                session.setAttribute("nombre", nom);
                session.setAttribute("apellido", apell);
                session.setAttribute("ubiImagen", ubi);
                session.setAttribute("rol", tipoUsuario);
                session.setAttribute("username", nickname);
                if (recordarme != null) {
                    Cookie cookieUser = new Cookie("usuarioRecordado", nickname);
                    cookieUser.setMaxAge(7 * 24 * 60 * 60); // 7 días
                    cookieUser.setPath("/");
                    Cookie cookiePass = new Cookie("passwordRecordado", password);
                    cookiePass.setMaxAge(7 * 24 * 60 * 60);
                    cookiePass.setPath("/");
                    response.addCookie(cookieUser);
                    response.addCookie(cookiePass);
                } else {
                    Cookie cookieUser = new Cookie("usuarioRecordado", "");
                    cookieUser.setMaxAge(0);
                    cookieUser.setPath("/");
                    Cookie cookiePass = new Cookie("passwordRecordado", "");
                    cookiePass.setMaxAge(0);
                    cookiePass.setPath("/");
                    response.addCookie(cookieUser);
                    response.addCookie(cookiePass);
                }
                response.sendRedirect("/index");
            } else if (tipoUsuario.equals("proponente") && !esMovil) {
                DTProponente prop = this.webServices.obtenerDTProponente(nickname);
                String nom = prop.getNombre();
                String apell = prop.getApellido();
                String ubi = prop.getImagen();
                session.setAttribute("nombre", nom);
                session.setAttribute("apellido", apell);
                session.setAttribute("ubiImagen", ubi);
                session.setAttribute("rol", tipoUsuario);
                session.setAttribute("username", nickname);
                if (recordarme != null) {
                    Cookie cookieUser = new Cookie("usuarioRecordado", nickname);
                    cookieUser.setMaxAge(7 * 24 * 60 * 60);
                    cookieUser.setPath("/");
                    Cookie cookiePass = new Cookie("passwordRecordado", password);
                    cookiePass.setMaxAge(7 * 24 * 60 * 60);
                    cookiePass.setPath("/");
                    response.addCookie(cookieUser);
                    response.addCookie(cookiePass);
                } else {
                    Cookie cookieUser = new Cookie("usuarioRecordado", "");
                    cookieUser.setMaxAge(0);
                    cookieUser.setPath("/");
                    Cookie cookiePass = new Cookie("passwordRecordado", "");
                    cookiePass.setMaxAge(0);
                    cookiePass.setPath("/");
                    response.addCookie(cookieUser);
                    response.addCookie(cookiePass);
                }
                response.sendRedirect("/index");

            } else if ("proponente".equals(tipoUsuario) && esMovil) {
                request.setAttribute("mensajeError", "No puedes iniciar sesion en un proponente");
                request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);
            }

        } else {

            if (esMovil) {
                request.setAttribute("mensajeError", "Usuario o contraseña incorrectos!");

                request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

            } else {
                request.setAttribute("mensajeError", "Usuario o contraseña incorrectos!");
                request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesion.jsp").forward(request, response);

            }

        }

    }

    protected void procesarCrearCuenta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, BadPasswordException_Exception, EmailRepetidoException_Exception, NickRepetidoException_Exception {
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();
        String nombre = request.getParameter("nombre");
        String apellido = request.getParameter("apellido");
        String email = request.getParameter("email");
        String nickname = request.getParameter("nickname");
        String password = request.getParameter("password");
        String passwordConfirm = request.getParameter("passwordConfirm");
        String tipoUsuario = request.getParameter("tipoUsuario");

        String fNacString = request.getParameter("fechaNacimiento");

        Part parteArchivo = request.getPart("imagen");
        byte[] bytesImagen = partABytes(parteArchivo);
        String nombreImagen = this.webServices.guardarImagen(bytesImagen);

        if (tipoUsuario.equals("proponente")) {
            String ciudad = request.getParameter("ciudad");
            String calle = request.getParameter("calle");
            String numPuertaString = request.getParameter("numPuerta");
            int numPuerta = Integer.parseInt(numPuertaString);
            DTDireccion direccion = new DTDireccion();
            direccion.setCalle(calle);
            direccion.setCiudad(ciudad);
            direccion.setNumeroPuerta(numPuerta);
            String biografia = request.getParameter("biografia");
            String sitioWeb = request.getParameter("sitioWeb");
            DTProponente prop = new DTProponente();
            prop.setDireccion(direccion);
            prop.setBiografia(biografia);
            prop.setSitioWeb(sitioWeb);
            prop.setNickname(nickname);
            prop.setNombre(nombre);
            prop.setApellido(apellido);
            prop.setPassword(password);
            prop.setPasswordConfirm(passwordConfirm);
            prop.setEmail(email);
            prop.setFechaNacimiento(fNacString);
            prop.setImagen(nombreImagen);
            this.webServices.addUsuario(prop);
        } else if (tipoUsuario.equals("colaborador")) {
            DTColaborador colab = new DTColaborador();
            colab.setNickname(nickname);
            colab.setNombre(nombre);
            colab.setApellido(apellido);
            colab.setPassword(password);
            colab.setPasswordConfirm(passwordConfirm);
            colab.setEmail(email);
            colab.setFechaNacimiento(fNacString);
            colab.setImagen(nombreImagen);

            this.webServices.addUsuario(colab);
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
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();
        List<String> aux = this.webServices.listaPropuestasUsu(nick);
        ArrayList<String> propuestasPubli = new ArrayList<>();
        for (String p : aux) {
            DTPropuesta prop = this.webServices.obtenerDTPropuesta(p);
            Estado est = prop.getEstadoActual();
            if (est.getEstado() != EstadoPropuesta.INGRESADA) {
                propuestasPubli.add(p);
            }
        }

        return propuestasPubli;
    }

    private void listarUsuarios(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();
        List<String> usuarios = this.webServices.listarUsuarios();
        request.setAttribute("usuarios", usuarios);
        request.getRequestDispatcher("/WEB-INF/jsp/consultaPerfilUsuario.jsp").forward(request, response);
    }

    private void sincImg() {
        List<String> list = this.webServices.listarUsuarios();
        for (String nick : list) {
            if (this.webServices.obtenerTipoUser(nick).equals("colaborador")) {
                DTColaborador p = this.webServices.obtenerDTColaborador(nick);
                String img = p.getImagen();
                if (img != null && !img.isEmpty() && !existeImg(img)) {
                    guardarImagen(this.webServices.obtenerImagen(p.getImagen()), p.getImagen());

                }
            } else if (this.webServices.obtenerTipoUser(nick).equals("proponente")) {
                DTProponente p = this.webServices.obtenerDTProponente(nick);
                String img = p.getImagen();
                if (img != null && !img.isEmpty() && !existeImg(img)) {
                    guardarImagen(this.webServices.obtenerImagen(p.getImagen()), p.getImagen());

                }
            }

        }

    }

    private boolean existeImg(String IDimg) {

        Path ruta = Paths.get(
                System.getProperty("user.home"),
                "imgProyePDA",
                IDimg
        );

        return Files.exists(ruta);

    }

    private void guardarImagen(byte[] bytesImagen, String nombreArchivo) {
        Path pathImagen = Paths.get(System.getProperty("user.home"), "imgProyePDA", nombreArchivo);
        if (bytesImagen == null) {
            return;
        }
        try {
            Files.createDirectories(pathImagen.getParent());
            Files.write(pathImagen, bytesImagen, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException ex) {

        }
    }

    // </editor-fold>
}
