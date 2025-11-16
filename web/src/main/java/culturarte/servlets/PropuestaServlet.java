package culturarte.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import webservices.DTPropuesta;
import webservices.Estado;
import webservices.EstadoPropuesta;
import static culturarte.wutils.SesionUtils.puedeCrearPropuesta;
import culturarte.wutils.Tracking;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import webservices.ClienteWS;
import webservices.ControllerWS;
import webservices.ControllerWS_Service;
import webservices.DTColaboracion;
import webservices.PropuestaDuplicadaException_Exception;
import webservices.TipoRetorno;

@WebServlet(name = "PropuestaServlet", urlPatterns = {"/propuestas", "/crear-propuesta",
    "/obtener-propuesta", "/obtener-propuesta-por-estado", "/extender-financiacion",
    "/propuestas-por-estado-usu", "/buscar-propuestas", "/marcar-propuesta-favorita", "/obtener-colaboracion", "/cancelar-propuesta", "/sugerencia", "/consultar-propuesta-movil"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, //1MB+ se escriben al disco
        maxFileSize = 1024 * 1024 * 5, //5MB máximo por archivo
        maxRequestSize = 1024 * 1024 * 10 //10MB máximo tamaño request
)
public class PropuestaServlet extends HttpServlet {

    //private IController controller = IControllerFactory.getInstance().getIController();
    private ControllerWS webServices;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        this.webServices = ClienteWS.getPort();

        this.webServices.registrarAcceso(Tracking.generarDTRegistroAcceso(request));
        sincImg();
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
            case "/crear-propuesta":
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
                    if (puedeCrearPropuesta(request.getSession())) {
                        List<String> categorias = this.webServices.obtenerCategorias();
                        request.setAttribute("categorias", categorias);
                        request.getRequestDispatcher("/WEB-INF/jsp/crearPropuesta.jsp").forward(request, response);
                    } else {
                        response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    }
                }
                break;
            case "/obtener-propuesta":

                String titulo = request.getParameter("titulo");
                if (titulo == null || titulo.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'titulo'");
                    return;
                }
                DTPropuesta prop = webServices.obtenerDTPropuesta(titulo);

                if (prop == null) {

                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Propuesta no encontrada");
                    return;
                }
                response.setContentType("application/json;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.print(obtenerPropuestaJSON(titulo));
                }
                break;
            case "/obtener-propuesta-por-estado":
                String estadoStr = request.getParameter("estado");
                if (estadoStr == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'estado'");
                    return;
                }
                String cat = request.getParameter("categoriaSelec");
                int estado = Integer.parseInt(estadoStr);
                List<String> titulos = webServices.listarPropuestasEstado(estado);

                List<DTPropuesta> propuestas = new ArrayList<>();
                for (String t : titulos) {
                    DTPropuesta p = webServices.obtenerDTPropuesta(t);
                    if (p != null) {
                        //propuestas.add(p); 
                        if ("Todas".equals(cat)) {
                            propuestas.add(p);
                        } else if (cat.equals(p.getTipoPropuesta())) {
                            propuestas.add(p);
                        }

                    }
                }
                String nick3 = (String) request.getSession().getAttribute("username");
                List<String> propuestasFav = recibirPropuestasFavoritas(nick3);
                List<String> propuestasColab = this.webServices.obtenerPropuestasColaboradas(nick3);
                List<String> propsParaColab = propuestasPubliYenFina();
                List<String> propuestasProp = this.webServices.listaPropuestasUsu(nick3);
                List<String> propuestasFinanciadas = propuestasFinanciadas();
                List<String> propuestasComentables = propuestasComentables(nick3);
                List<String> propuestasComentadas = propuestasComentadas(nick3);
                List<Object> respuesta = new ArrayList<>();

                respuesta.add(propuestas);
                respuesta.add(propuestasFav);
                respuesta.add(propuestasColab);
                respuesta.add(propsParaColab);
                respuesta.add(propuestasProp);
                respuesta.add(propuestasFinanciadas);
                respuesta.add(propuestasComentables);
                respuesta.add(propuestasComentadas);

                response.setContentType("application/json;charset=UTF-8");
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                mapper.writeValue(response.getWriter(), respuesta);
                break;
            case "/extender-financiacion":
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
                    request.getRequestDispatcher("/WEB-INF/jsp/extenderFinanciacion.jsp").forward(request, response);
                }
                break;
            case "/propuestas-por-estado-usu":
                session = request.getSession(false);
                String estadoParamExt = request.getParameter("estado");
                if (estadoParamExt == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'estado'");
                    return;
                }

                String nick = session.getAttribute("username").toString();

                int estadoIntExt = Integer.parseInt(estadoParamExt);
                List<String> titulosExt = webServices.listarPropuestasEstadoUsu(estadoIntExt, nick);

                java.time.LocalDate hoy = java.time.LocalDate.now();
                List<DTPropuesta> propuestasExt = new ArrayList<>();

                for (String tituloExt : titulosExt) {
                    DTPropuesta propuestaExt = webServices.obtenerDTPropuesta(tituloExt);
                    java.time.LocalDate tiempo = java.time.LocalDate.parse(propuestaExt.getFechaPublicacion());
                    if (propuestaExt != null && !hoy.isAfter(tiempo.plusDays(30))) {

                        propuestasExt.add(propuestaExt);
                    }
                }

                response.setContentType("application/json;charset=UTF-8");
                ObjectMapper mapperExt = new ObjectMapper();
                mapperExt.registerModule(new JavaTimeModule());
                mapperExt.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                mapperExt.writeValue(response.getWriter(), propuestasExt);
                break;

            case "/buscar-propuestas":
                String patron = request.getParameter("busq");
                List<DTPropuesta> aux = webServices.buscarPropuestasTDL(patron);
                request.setAttribute("propuestas", aux);
                request.getRequestDispatcher("/WEB-INF/jsp/resultadosBusqueda.jsp").forward(request, response);
                break;
            case "/marcar-propuesta-favorita":
                session = request.getSession(false);
                userAgent = request.getHeader("User-Agent").toLowerCase();
                rol = (String) session.getAttribute("rol");
                esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                        || userAgent.contains("iphone") || userAgent.contains("ipad");

                if (esMovil && rol == null) {

                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                } else if (esMovil && "colaborador".equals(rol)) {

                    request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
                } else {

                    String nick2 = session.getAttribute("username").toString();
                    List<String> aux2 = recibirPropuestas(nick2);
                    request.setAttribute("propuestas", aux2);
                    request.getRequestDispatcher("/WEB-INF/jsp/marcarPropuestaFavorita.jsp").forward(request, response);
                }
                break;
            case "/obtener-colaboracion":
                String id = request.getParameter("id");
                if (id == null || id.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'id Colaboracion'");
                    return;
                }
                Long idColab = Long.valueOf(id);
                DTColaboracion colaboracion = this.webServices.obtenerDTColaboracion(idColab);
                if (colaboracion == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Colaboracion no encontrada");
                    return;
                }

                response.setContentType("application/json;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    out.print(obtenerColaboracionJSON(idColab));
                }
                break;
            case "/cancelar-propuesta":
                session = request.getSession(false);
                userAgent = request.getHeader("User-Agent").toLowerCase();
                rol = (String) session.getAttribute("rol");
                esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                        || userAgent.contains("iphone") || userAgent.contains("ipad");

                if (esMovil && rol == null) {

                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                } else if (esMovil && "colaborador".equals(rol)) {

                    request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/jsp/cancelarPropuesta.jsp").forward(request, response);
                }
                break;
            case "/sugerencia":

                session = request.getSession(false);
                userAgent = request.getHeader("User-Agent").toLowerCase();
                rol = (String) session.getAttribute("rol");
                esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                        || userAgent.contains("iphone") || userAgent.contains("ipad");

                if (esMovil && rol == null) {

                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                } else if (esMovil && "colaborador".equals(rol)) {

                    request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
                } else {
                    String nickRecom = session.getAttribute("username").toString();

                    List<String> propuestasPuntaje = this.webServices.obtenerRecomendaciones(nickRecom);
                    request.setAttribute("propuestas", propuestasPuntaje);
                    request.getRequestDispatcher("WEB-INF/jsp/sugerencia.jsp").forward(request, response);
                }
                break;

            case "/consultar-propuesta-movil":
                String titulopropMovil = request.getParameter("titulo");
                String nick1 = (String) request.getSession().getAttribute("username");
                List<String> propuestasColab1 = this.webServices.obtenerPropuestasColaboradas(nick1);
                List<String> propsParaColab1 = propuestasPubliYenFina();

                if (titulopropMovil == null || titulopropMovil.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'titulo'");
                    return;
                }

                DTPropuesta propMovil = webServices.obtenerDTPropuesta(titulopropMovil);
                if (propMovil == null) {
                    response.sendError(HttpServletResponse.SC_NOT_FOUND, "Propuesta no encontrada");
                    return;
                }

                request.setAttribute("propuestaC", titulopropMovil);
                request.setAttribute("propuestasColab", propuestasColab1);
                request.setAttribute("propsparacolab", propsParaColab1);

                request.getRequestDispatcher("/WEB-INF/jsp/consultarPropuestaMovil.jsp").forward(request, response);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();
        this.webServices = ClienteWS.getPort();

        switch (path) {
            case "/crear-propuesta":
                if (puedeCrearPropuesta(request.getSession())) {
                    try {
                        procesarCrearPropuesta(request, response);
                        response.sendRedirect("/index");
                    } catch (PropuestaDuplicadaException_Exception ex) {
                        List<String> categorias = this.webServices.obtenerCategorias();
                        request.setAttribute("categorias", categorias);
                        request.setAttribute("error", "Ya existe una propuesta con ese nombre.");
                        request.getRequestDispatcher("/WEB-INF/jsp/crearPropuesta.jsp").forward(request, response);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                break;
            case "/extender-financiacion":
                String tituloProp = request.getParameter("titulo");
                if (tituloProp == null || tituloProp.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'titulo'");
                    return;
                }
                webServices.extenderFinanciacion(tituloProp);
                response.sendRedirect("/index");
                break;
            case "/marcar-propuesta-favorita":
                String titulo = request.getParameter("propuesta");
                HttpSession session = request.getSession(false);
                String nick = session.getAttribute("username").toString();
                 {
                    try {
                        this.webServices.favoritarPropuesta(nick, titulo);
                    } catch (Exception ex) {
                        Logger.getLogger(PropuestaServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
            case "/cancelar-propuesta":
                String tituloCancelar = request.getParameter("titulo");
                if (tituloCancelar == null || tituloCancelar.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'titulo'");
                    return;
                }

                try {
                    webServices.cambiarEstadoPropuesta(tituloCancelar, EstadoPropuesta.CANCELADA.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al cancelar la propuesta");
                    return;
                }
                response.sendRedirect("/index");
                break;

        }
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Procesamiento de requests.">
    protected void procesarCrearPropuesta(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, PropuestaDuplicadaException_Exception {
        this.webServices = ClienteWS.getPort();
        HttpSession session = request.getSession(false);
        String titulo = request.getParameter("titulo");
        String categoria = request.getParameter("categoria");
        String descripcion = request.getParameter("descripcion");
        String lugar = request.getParameter("lugar");
        String precio = request.getParameter("precio");
        String[] aux = request.getParameterValues("tipoRetorno");
        ArrayList<TipoRetorno> tiposRetorno = new ArrayList<>();
        for (String aux2 : aux) {
            if (aux2.equals("porcentajeGanancia")) {
                tiposRetorno.add(TipoRetorno.PORCENTAJE_GANANCIAS);
            }
            if (aux2.equals("entradaGratis")) {
                tiposRetorno.add(TipoRetorno.ENTRADA_GRATIS);
            }

        }

        float precioF = Float.parseFloat(precio);
        String monto = request.getParameter("monto");
        float montoF = Float.parseFloat(monto);
        String fPrevistaString = request.getParameter("fecha-prevista");
        Part parteArchivo = request.getPart("imagen");
        byte[] bytesImagen = partABytes(parteArchivo);
        String nombreImagen = this.webServices.guardarImagen(bytesImagen);
        EstadoPropuesta estp = EstadoPropuesta.INGRESADA;
        Estado est = new Estado();
        est.setEstado(estp);
        String nickProp = (String) session.getAttribute("username");
        DTPropuesta prop = new DTPropuesta();
        prop.setDescripcion(descripcion);
        prop.setTitulo(titulo);
        prop.setImagen(nombreImagen);
        prop.setLugarRealizara(lugar);
        prop.setFechaRealizara(fPrevistaString);
        prop.setMontoAReunir(montoF);
        prop.setPrecioEntrada(precioF);
        prop.setTipoPropuesta(categoria);
        prop.setNickProponedor(nickProp);
        for (TipoRetorno a : tiposRetorno) {
            prop.getTiposRetorno().add(a);
        }
        prop.setEstadoActual(est);
        this.webServices.addPropuesta(prop);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet PropuestaServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet PropuestaServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // </editor-fold>
    // <editor-fold defaultstate="collapsed" desc="Funciones auxiliares.">
    /*private LocalDate parsearFecha(String fechaString) {
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
    }*/
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

    private String obtenerPropuestaJSON(String titulo) {
        this.webServices = ClienteWS.getPort();
        DTPropuesta prop = this.webServices.obtenerDTPropuesta(titulo);
        if (prop == null) {
            return "{}";
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(prop);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private ArrayList<String> recibirPropuestasFavoritas(String nick) {
        this.webServices = ClienteWS.getPort();
        List<String> aux = this.webServices.listarPropuestas();
        ArrayList<String> aux2 = new ArrayList<>();
        if (nick == null) {
            return aux2;
        }

        for (String prop : aux) {
            Boolean propuestaYaFavorita = this.webServices.propuestaYaFavorita(prop, nick);
            if (propuestaYaFavorita) {
                aux2.add(prop);
            }
        }
        return aux2;
    }

    private ArrayList<String> recibirPropuestas(String nick) {
        this.webServices = ClienteWS.getPort();
        List<String> aux = this.webServices.listarPropuestas();
        ArrayList<String> aux2 = new ArrayList<>();
        if (nick == null) {
            return aux2;
        }
        for (String prop : aux) {
            DTPropuesta aux3 = this.webServices.obtenerDTPropuesta(prop);
            Estado aux4 = aux3.getEstadoActual();
            Boolean propuestaYaFavorita = this.webServices.propuestaYaFavorita(prop, nick);
            if (!propuestaYaFavorita && aux4.getEstado() != EstadoPropuesta.INGRESADA) {
                aux2.add(prop);
            }
        }
        return aux2;
    }

    private ArrayList<String> propuestasPubliYenFina() {
        this.webServices = ClienteWS.getPort();
        List<String> aux = this.webServices.listarPropuestas();
        ArrayList<String> aux2 = new ArrayList<>();

        for (String prop : aux) {
            DTPropuesta propuestaAux = this.webServices.obtenerDTPropuesta(prop);
            if (propuestaAux != null) {
                if (propuestaAux.getEstadoActual().getEstado() == EstadoPropuesta.PUBLICADA || propuestaAux.getEstadoActual().getEstado() == EstadoPropuesta.EN_FINANCIACION) {
                    aux2.add(prop);
                }
            }

        }
        return aux2;
    }

    private ArrayList<String> propuestasFinanciadas() {
        this.webServices = ClienteWS.getPort();
        List<String> aux = this.webServices.listarPropuestas();
        ArrayList<String> aux2 = new ArrayList<>();

        for (String prop : aux) {
            DTPropuesta propuestaAux = this.webServices.obtenerDTPropuesta(prop);
            if (propuestaAux != null) {
                if (propuestaAux.getEstadoActual().getEstado() == EstadoPropuesta.FINANCIADA) {
                    aux2.add(prop);
                }
            }

        }
        return aux2;
    }

    private ArrayList<String> propuestasComentables(String nickCol) {
        this.webServices = ClienteWS.getPort();
        List<String> aux = this.webServices.obtenerPropuestasColaboradas(nickCol);
        ArrayList<String> aux2 = new ArrayList<>();

        for (String prop : aux) {
            DTPropuesta aux3 = this.webServices.obtenerDTPropuesta(prop);

            Estado aux4 = aux3.getEstadoActual();
            Boolean comentarioExiste = this.webServices.comentarioExiste(prop, nickCol);
            if (aux4.getEstado() == EstadoPropuesta.FINANCIADA && !comentarioExiste) {
                aux2.add(prop);
            }

        }
        return aux2;
    }

    private ArrayList<String> propuestasComentadas(String nickCol) {
        this.webServices = ClienteWS.getPort();
        List<String> aux = this.webServices.obtenerPropuestasColaboradas(nickCol);
        ArrayList<String> aux2 = new ArrayList<>();

        for (String prop : aux) {
            DTPropuesta aux3 = this.webServices.obtenerDTPropuesta(prop);

            Estado aux4 = aux3.getEstadoActual();
            Boolean comentarioExiste = this.webServices.comentarioExiste(prop, nickCol);
            if (aux4.getEstado() == EstadoPropuesta.FINANCIADA && comentarioExiste) {
                aux2.add(prop);
            }

        }
        return aux2;
    }

    private String obtenerColaboracionJSON(Long id) {
        this.webServices = ClienteWS.getPort();
        DTColaboracion colab = this.webServices.obtenerDTColaboracion(id);
        if (colab == null) {
            return "{}";
        }
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            return mapper.writeValueAsString(colab);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";
        }
    }

    private void sincImg() {
        List<String> list = this.webServices.listarPropuestas();
        for (String titulo : list) {
            DTPropuesta p = this.webServices.obtenerDTPropuesta(titulo);
            String img = p.getImagen();

            if (img != null && !img.isEmpty() && !existeImg(img)) {
                guardarImagen(this.webServices.obtenerImagen(p.getImagen()), p.getImagen());

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
