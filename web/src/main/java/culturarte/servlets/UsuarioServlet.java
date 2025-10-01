/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package culturarte.servlets;

import culturarte.excepciones.BadPasswordException;
import culturarte.excepciones.EmailRepetidoException;
import culturarte.excepciones.NickRepetidoException;
import culturarte.logica.DTColaborador;
import culturarte.logica.DTDireccion;
import culturarte.logica.DTProponente;
import culturarte.logica.DTUsuario;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
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
import java.util.Date;

/**
 *
 * @author mark
 */
@WebServlet(name = "UsuarioServlet", urlPatterns = {"/usuarios", "/crear-cuenta", "/perfil", "/login"})
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024, //1MB+ se escriben al disco
        maxFileSize = 1024 * 1024 * 5, //5MB máximo por archivo
        maxRequestSize = 1024 * 1024 * 10 //10MB máximo tamaño request
)
public class UsuarioServlet extends HttpServlet {

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
            case "/crear-cuenta":
                request.getRequestDispatcher("/WEB-INF/jsp/crearCuenta.jsp").forward(request, response);
                break;
            case "/login":
                request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesion.jsp").forward(request, response);
                break;
            default:
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
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
            case "/crear-cuenta":
                procesarCrearCuenta(request, response);
                response.sendRedirect("/index");
                break;
            case "/login":
                iniciarSesion(request, response);
                response.sendRedirect("/index");
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }
    }

    protected void iniciarSesion(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String password = request.getParameter("password");
        if(this.controller.obtenerUser(nombre)){ // AGREGAR CONTROL DE CLAVE
            HttpSession session = request.getSession(true);
            session.setAttribute("rol", "colaborador");
            session.setAttribute("username", nombre);
        }else{
            //AGREGAR MENSAJE POR USUARIO O CLAVE INEXISTENTE
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
                    password.toCharArray(), passwordConfirm.toCharArray(), email, fechaNacimiento, null);

        } else if (tipoUsuario.equals("colaborador")) {
            user = new DTColaborador(nickname, nombre, apellido,
                    password.toCharArray(), passwordConfirm.toCharArray(), email, fechaNacimiento, null);
        }

        try {
            this.controller.addUsuario(user);
        } catch (NickRepetidoException | EmailRepetidoException | BadPasswordException ex) {
            System.getLogger(UsuarioServlet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
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
