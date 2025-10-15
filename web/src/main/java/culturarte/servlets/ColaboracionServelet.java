/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package culturarte.servlets;

import culturarte.excepciones.PropuestaYaColaboradaException;
import culturarte.datatypes.DTPropuesta;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;

/**
 *
 * @author kevin
 */
@WebServlet(name = "ColaboracionServelet", urlPatterns = {"/registrar-colaboracion"})
public class ColaboracionServelet extends HttpServlet {

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
            out.println("<title>Servlet ColaboracionServelet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ColaboracionServelet at " + request.getContextPath() + "</h1>");
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
            case "/registrar-colaboracion":
                ArrayList<String> propuestas = this.controller.listarPropuestasProponentes();
                request.setAttribute("propuestas", propuestas);
                request.getRequestDispatcher("/WEB-INF/jsp/registrarColaboracion.jsp").forward(request, response);
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
        HttpSession session = request.getSession(false);
        String path = request.getServletPath();

        switch (path) {
            case "/registrar-colaboracion":
                String nick = session.getAttribute("username").toString();
                String titulo = request.getParameter("tituloProp");
                String retorno = request.getParameter("opcion");
                float monto = Float.parseFloat(request.getParameter("colaboracion"));

                try {
                    this.controller.realizarColaboracion(nick, titulo, monto, retorno);
                } catch (PropuestaYaColaboradaException ex) {
                    System.getLogger(ColaboracionServelet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                response.sendRedirect("/index");
                break;

            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }

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
