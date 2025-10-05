/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package culturarte.servlets;

import culturarte.excepciones.PropuestaDuplicadaException;
import culturarte.logica.DTPropuesta;
import culturarte.logica.Estado;
import culturarte.logica.EstadoPropuesta;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import culturarte.logica.TipoRetorno;
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
@WebServlet(name = "PropuestaServlet", urlPatterns = {"/propuestas", "/crear-propuesta", "/obtener-propuesta", "/obtener-propuesta-por-estado"})
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
                HttpSession session = request.getSession();
                String rol = (String) session.getAttribute("rol");
                if (rol != "proponente") {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                
                ArrayList<String> categorias = this.controller.obtenerCategorias();
                request.setAttribute("categorias", categorias);
                request.getRequestDispatcher("/WEB-INF/jsp/crearPropuesta.jsp").forward(request, response);
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
                int estado = Integer.parseInt(estadoStr);
                ArrayList<String> titulos = controller.listarPropuestasEstado(estado);
                response.setContentType("application/json;charset=UTF-8");
                try (PrintWriter out = response.getWriter()) {
                    StringBuilder json = new StringBuilder("[");
                    for (int i = 0; i < titulos.size(); i++) {
                        DTPropuesta p = controller.obtenerDTPropuesta(titulos.get(i));
                        if (p != null) {
                            json.append(obtenerPropuestaJSON(p.getTitulo()));
                        }
                        if (i < titulos.size() - 1) {
                            json.append(",");
                        }
                    }
                    json.append("]");
                    out.print(json.toString());
                }
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
                HttpSession session = request.getSession();
                String rol = (String) session.getAttribute("rol");
                if (rol != "proponente") {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                    return;
                }
                
                procesarCrearPropuesta(request, response);
                response.sendRedirect("/index");
                break;
        }
    }
    private String obtenerPropuestaJSON(String titulo) {
        DTPropuesta prop = this.controller.obtenerDTPropuesta(titulo);
        if(prop == null){
            return "{}";
        }
        StringBuilder json = new StringBuilder();
        json.append("{");
        json.append("\"titulo\": \"").append(prop.getTitulo()).append("\",");
        json.append("\"descripcion\": \"").append(prop.getDescripcion()).append("\",");
        json.append("\"imagen\": \"").append(prop.getImagen()).append("\",");
        json.append("\"lugarRealizara\": \"").append(prop.getLugarRealizara()).append("\",");
        json.append("\"fechaPrevista\": \"").append(prop.getFechaRealizara() != null ? prop.getFechaRealizara().toString() : "N/A").append("\",");
        json.append("\"fechaPublicacion\": \"").append(prop.getFechaPublicacion() != null ? prop.getFechaPublicacion().toString() : "N/A").append("\",");
        json.append("\"precioEntrada\": ").append(prop.getPrecioEntrada()).append(",");
        json.append("\"montoAReunir\": ").append(prop.getMontoAReunir()).append(",");
        json.append("\"categoria\": \"").append(prop.getTipoPropuesta()).append("\",");
        json.append("\"nickProponedor\": \"").append(prop.getNickProponedor()).append("\",");
        json.append("\"estadoActual\": \"").append(prop.getEstadoActual() != null ? prop.getEstadoActual().getEstado().toString() : "N/A").append("\",");
        json.append("\"tiposRetorno\": [");
        List<TipoRetorno> tipos = prop.getTiposRetorno();
        for (int i = 0; i < tipos.size(); i++) {
            json.append("\"").append(tipos.get(i).toString()).append("\"");
            if (i < tipos.size() - 1) {
                json.append(",");
            }
        }
        json.append("]");
        json.append("}");
        return json.toString();
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
    // </editor-fold>

}
