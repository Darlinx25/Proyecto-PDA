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
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author mark
 */
@WebServlet(name = "PropuestaServlet", urlPatterns = {"/propuestas", "/crear-propuesta"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, //1MB+ se escriben al disco
        maxFileSize = 1024 * 1024 * 5, //5MB máximo por archivo
        maxRequestSize = 1024 * 1024 * 10 //10MB máximo tamaño request
)
public class PropuestaServlet extends HttpServlet {

    private IController controller = IControllerFactory.getInstance().getIController();

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
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

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
            case "/crear-propuesta":
                ArrayList<String> categorias = this.controller.obtenerCategorias();
                request.setAttribute("categorias", categorias);
                request.getRequestDispatcher("/WEB-INF/jsp/crearPropuesta.jsp").forward(request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
            case "/crear-propuesta":
                procesarCrearPropuesta(request, response);
                response.sendRedirect("/index");
                break;
        }

    }
    
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
            if(aux2.equals("porcentajeGanancia")){
                tiposRetorno.add(TipoRetorno.PORCENTAJE_GANANCIAS);
            }
             if(aux2.equals("entradaGratis")){
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
        EstadoPropuesta estp = EstadoPropuesta.INGRESADA;
        Estado est = new Estado(estp);
        String nickProp =(String) session.getAttribute("username");
        DTPropuesta prop = new DTPropuesta(titulo, descripcion,
        null, lugar, fechaPrevista, precioF, montoF, categoria,
                nickProp, tiposRetorno, est);
        
        try {
            this.controller.addPropuesta(prop);
        } catch (PropuestaDuplicadaException ex) {
            Logger.getLogger(PropuestaServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
