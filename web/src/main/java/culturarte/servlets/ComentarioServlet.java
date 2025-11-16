package culturarte.servlets;



import culturarte.wutils.Tracking;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import webservices.ClienteWS;
import webservices.ControllerWS;
import webservices.ControllerWS_Service;
import webservices.DTPropuesta;
import webservices.Estado;
import webservices.EstadoPropuesta;

@WebServlet(name = "ComentarioServlet", urlPatterns = {"/hacer-comentario"})
public class ComentarioServlet extends HttpServlet {

    //private IController controller = IControllerFactory.getInstance().getIController();
    private ControllerWS webServices;

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
            out.println("<title>Servlet ComentarioServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ComentarioServlet at " + request.getContextPath() + "</h1>");
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
        this.webServices = ClienteWS.getPort();
        
        this.webServices.registrarAcceso(Tracking.generarDTRegistroAcceso(request));
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
            case "/hacer-comentario":
                HttpSession session = request.getSession();
                String userAgent = request.getHeader("User-Agent").toLowerCase();
                String rol = (String) session.getAttribute("rol");
                boolean esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                        || userAgent.contains("iphone") || userAgent.contains("ipad");

                if (esMovil && rol == null) {

                    request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);

                } else if (esMovil && "colaborador".equals(rol)) {

                    request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
                } else{
                String propuestaColaborada = (String) request.getParameter("tituloProp");
                request.setAttribute("propuesta", propuestaColaborada);
                request.getRequestDispatcher("WEB-INF/jsp/hacerComentario.jsp").forward(request, response);
                }
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
        
        this.webServices = ClienteWS.getPort();
        
        switch (path) {
            case "/hacer-comentario":
                String titulo = request.getParameter("propuesta").trim();
                System.out.println("");
                HttpSession session = request.getSession(false);
                String nick = session.getAttribute("username").toString();
                String comentario = request.getParameter("comentario");
                 {
                    try {
                        this.webServices.hacerComentario(comentario, nick, titulo);
                    } catch (Exception ex) {
                        Logger.getLogger(PropuestaServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                response.sendRedirect("/index");
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }

    }


    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private ArrayList<String> recibirPropuestas(String nickCol) {
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
}
