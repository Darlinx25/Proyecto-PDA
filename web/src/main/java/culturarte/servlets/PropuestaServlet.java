/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package culturarte.servlets;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import culturarte.datatypes.DTColaboracion;
import culturarte.excepciones.PropuestaDuplicadaException;
import culturarte.datatypes.DTPropuesta;
import culturarte.logica.Estado;
import culturarte.logica.EstadoPropuesta;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import culturarte.logica.TipoRetorno;
import culturarte.wutils.SesionUtils;
import static culturarte.wutils.SesionUtils.puedeCrearPropuesta;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mark
 */
@WebServlet(name = "PropuestaServlet", urlPatterns = {"/propuestas", "/crear-propuesta",
    "/obtener-propuesta", "/obtener-propuesta-por-estado", "/extender-financiacion",
    "/propuestas-por-estado-usu", "/buscar-propuestas", "/marcar-propuesta-favorita", "/obtener-colaboracion", "/cancelar-propuesta"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, //1MB+ se escriben al disco
        maxFileSize = 1024 * 1024 * 5, //5MB máximo por archivo
        maxRequestSize = 1024 * 1024 * 10 //10MB máximo tamaño request
)
public class PropuestaServlet extends HttpServlet {

    private IController controller = IControllerFactory.getInstance().getIController();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
            case "/crear-propuesta":
                if (puedeCrearPropuesta(request.getSession())) {
                    ArrayList<String> categorias = this.controller.obtenerCategorias();
                    request.setAttribute("categorias", categorias);
                    request.getRequestDispatcher("/WEB-INF/jsp/crearPropuesta.jsp").forward(request, response);
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                }
                break;
            case "/obtener-propuesta":
                String titulo = request.getParameter("titulo");
                if (titulo == null || titulo.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'titulo'");
                    return;
                }
                DTPropuesta prop = controller.obtenerDTPropuesta(titulo);
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
                System.out.println(cat);
                int estado = Integer.parseInt(estadoStr);
                ArrayList<String> titulos = controller.listarPropuestasEstado(estado);

                List<DTPropuesta> propuestas = new ArrayList<>();
                for (String t : titulos) {
                    DTPropuesta p = controller.obtenerDTPropuesta(t);
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
                List<Object> respuesta = new ArrayList<>();
                respuesta.add(propuestas);
                respuesta.add(propuestasFav);
                
                response.setContentType("application/json;charset=UTF-8");
                ObjectMapper mapper = new ObjectMapper();
                mapper.registerModule(new JavaTimeModule());
                mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                mapper.writeValue(response.getWriter(), respuesta);
                break;
            case "/extender-financiacion":
                request.getRequestDispatcher("/WEB-INF/jsp/extenderFinanciacion.jsp").forward(request, response);

                break;
            case "/propuestas-por-estado-usu":
                HttpSession session = request.getSession(false);
                String estadoParamExt = request.getParameter("estado");
                if (estadoParamExt == null) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'estado'");
                    return;
                }

                String nick = session.getAttribute("username").toString();

                int estadoIntExt = Integer.parseInt(estadoParamExt);
                ArrayList<String> titulosExt = controller.listarPropuestasEstadoUsu(estadoIntExt, nick);

                LocalDate hoy = LocalDate.now();
                List<DTPropuesta> propuestasExt = new ArrayList<>();

                for (String tituloExt : titulosExt) {
                    DTPropuesta propuestaExt = controller.obtenerDTPropuesta(tituloExt);
                    if (propuestaExt != null && !hoy.isAfter(propuestaExt.getFechaPublicacion().plusDays(30))) {

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
                List<DTPropuesta> aux = controller.buscarPropuestasTDL(patron);
                request.setAttribute("propuestas", aux);
                request.getRequestDispatcher("/WEB-INF/jsp/resultadosBusqueda.jsp").forward(request, response);
                break;
            case "/marcar-propuesta-favorita":
                session = request.getSession(false);
                String nick2 = session.getAttribute("username").toString();
                List<String> aux2 = recibirPropuestas(nick2);
                request.setAttribute("propuestas", aux2);
                request.getRequestDispatcher("/WEB-INF/jsp/marcarPropuestaFavorita.jsp").forward(request, response);
                break;
            case "/obtener-colaboracion":
                String id = request.getParameter("id");

                if (id == null || id.isEmpty()) {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta el parámetro 'id Colaboracion'");
                    return;
                }
                Long idColab = Long.valueOf(id);
                DTColaboracion colaboracion = this.controller.obtenerDTColaboracion(idColab);
                System.out.println(idColab);
                System.out.println(id);
                System.out.println(colaboracion.getColaborador());
                System.out.println("ACAAAAAAAAAAAAAAAAA");
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
                request.getRequestDispatcher("/WEB-INF/jsp/cancelarPropuesta.jsp").forward(request, response);
                break;

        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
            case "/crear-propuesta":
                if (puedeCrearPropuesta(request.getSession())) {
                    procesarCrearPropuesta(request, response);
                    response.sendRedirect("/index");
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
                controller.extenderFinanciacion(tituloProp);
                response.sendRedirect("/index");
                break;
            case "/marcar-propuesta-favorita":
                String titulo = request.getParameter("propuesta");
                HttpSession session = request.getSession(false);
                String nick = session.getAttribute("username").toString();
                 {
                    try {
                        this.controller.favoritarPropuesta(nick, titulo);
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
                    controller.cambiarEstadoPropuesta(tituloCancelar, EstadoPropuesta.CANCELADA);
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
            throws ServletException, IOException {
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
        LocalDate fechaPrevista = parsearFecha(fPrevistaString);
        Part parteArchivo = request.getPart("imagen");
        byte[] bytesImagen = partABytes(parteArchivo);
        String nombreImagen = this.controller.guardarImagen(bytesImagen);
        EstadoPropuesta estp = EstadoPropuesta.INGRESADA;
        Estado est = new Estado(estp);
        String nickProp = (String) session.getAttribute("username");
        DTPropuesta prop = new DTPropuesta(titulo, descripcion,
                nombreImagen, lugar, fechaPrevista, precioF, montoF, categoria,
                nickProp, tiposRetorno, est);

        try {
            this.controller.addPropuesta(prop);
        } catch (PropuestaDuplicadaException ex) {
            Logger.getLogger(PropuestaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
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
        DTPropuesta prop = this.controller.obtenerDTPropuesta(titulo);
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

        ArrayList<String> aux = this.controller.listarPropuestas();
        ArrayList<String> aux2 = new ArrayList<>();
        if (nick == null) {
            return aux2;
        }
        
        for (String prop : aux) {
            Boolean propuestaYaFavorita = this.controller.propuestaYaFavorita(prop, nick);
            if (propuestaYaFavorita) {
                aux2.add(prop);
            }
        }
        return aux2;
    }
    
    private ArrayList<String> recibirPropuestas(String nick) {

        ArrayList<String> aux = this.controller.listarPropuestas();
        ArrayList<String> aux2 = new ArrayList<>();
        if (nick == null) {
            return aux2;
        }
        
        for (String prop : aux) {
            DTPropuesta aux3 = this.controller.obtenerDTPropuesta(prop);
            Estado aux4 = aux3.getEstadoActual();
            Boolean propuestaYaFavorita = this.controller.propuestaYaFavorita(prop, nick);
            if (!propuestaYaFavorita && aux4.getEstado() != EstadoPropuesta.INGRESADA) {
                aux2.add(prop);
            }
        }
        return aux2;
    }

    private String obtenerColaboracionJSON(Long id) {
        DTColaboracion colab = this.controller.obtenerDTColaboracion(id);
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

    // </editor-fold>
}
