/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.servlets;

import culturarte.logica.DTPropuesta;
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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevin
 */
@WebServlet(name = "ComentarioServlet", urlPatterns = {"/hacer-comentario"})
public class ComentarioServlet extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        switch (path) {
          case "/hacer-comentario":
                HttpSession session = request.getSession(false);
                String nick = session.getAttribute("username").toString();
                ArrayList<String> propuestaColaborada = this.controller.listarColaboracionesColaborador(nick);
                request.setAttribute("propuestas", propuestaColaborada);
                request.getRequestDispatcher("WEB-INF/jsp/hacerComentario.jsp").forward(request, response);
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
           case "/hacer-comentario":
                String titulo = request.getParameter("tituloProp");
                HttpSession session = request.getSession(false);
                String nick = session.getAttribute("username").toString();
                String comentario = request.getParameter("comentario");
                System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCCC");
                {
                    try {
                        System.out.println("BBBBBBBBBBBBBBBBBBBBBB");
                        this.controller.hacerComentario(comentario, nick, titulo);
                    } catch(Exception ex){
                        Logger.getLogger(PropuestaServlet.class.getName()).log(Level.SEVERE, null, ex);
                    }
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
