package culturarte.servlets;

import culturarte.datatypes.DTFormaPago;
import culturarte.datatypes.DTPago;
import culturarte.datatypes.DTPaypal;
import culturarte.datatypes.DTTarjeta;
import culturarte.datatypes.DTTransferenciaBancaria;
import culturarte.excepciones.PropuestaYaColaboradaException;
import culturarte.logica.IController;
import culturarte.logica.IControllerFactory;
import static culturarte.wutils.SesionUtils.esVisitante;
import culturarte.wutils.Tracking;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.time.ZoneId;

@WebServlet(name = "ColaboracionServelet", urlPatterns = {"/registrar-colaboracion", "/pagar"})
public class ColaboracionServelet extends HttpServlet {

    private IController controller = IControllerFactory.getInstance().getIController();

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
 
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        this.controller.registrarAcceso(Tracking.generarDTRegistroAcceso(request));
        response.setContentType("text/html;charset=UTF-8");
        String path = request.getServletPath();

        String userAgent = request.getHeader("User-Agent").toLowerCase();
        boolean esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                || userAgent.contains("iphone") || userAgent.contains("ipad");

        switch (path) {
            case "/registrar-colaboracion":
                String titulo = (String) request.getParameter("propuesta");
                request.setAttribute("propuestaC", titulo);

                if (esMovil) {
                    request.getRequestDispatcher("/WEB-INF/jsp/registrarColaboracionMovil.jsp").forward(request, response);
                } else {
                    request.getRequestDispatcher("/WEB-INF/jsp/registrarColaboracion.jsp").forward(request, response);
                }

                break;
            case "/pagar":
                if (esMovil) {
                    if (esVisitante(request.getSession())) {
                        response.sendRedirect("/");
                    } else {
                        request.getRequestDispatcher("/WEB-INF/jsp/pagarColaboracionMovil.jsp")
                                .forward(request, response);
                    }
                } else {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
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
        HttpSession session = request.getSession(false);
        String path = request.getServletPath();
        
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        boolean esMovil = userAgent.contains("mobi") || userAgent.contains("android")
                || userAgent.contains("iphone") || userAgent.contains("ipad");

        switch (path) {
            case "/registrar-colaboracion":
                String nick = session.getAttribute("username").toString();
                String titulo = request.getParameter("propuesta");
                String retorno = request.getParameter("opcion");
                float monto = Float.parseFloat(request.getParameter("colaboracion"));

                try {
                    this.controller.realizarColaboracion(nick, titulo, monto, retorno);
                } catch (PropuestaYaColaboradaException ex) {
                    System.getLogger(ColaboracionServelet.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
                }
                response.sendRedirect("/index");
                break;
            
            case "/pagar":
                if (!esMovil || esVisitante(request.getSession())) {
                    response.sendError(HttpServletResponse.SC_FORBIDDEN);
                    return;
                }
                float montoPago = Float.parseFloat(request.getParameter("montoPago"));
                Long idColaboracion = Long.parseLong(request.getParameter("idColaboracion"));
                String metodoPago = request.getParameter("metodoPago");
                LocalDateTime fechaPago = LocalDateTime.now(ZoneId.systemDefault());
                
                if ("tarjeta".equals(metodoPago)) {
                    String tipoTarjeta = request.getParameter("tipoTarjeta");
                    String nroTarjeta = request.getParameter("nroTarjeta");
                    String vencTarjeta = request.getParameter("vencTarjeta");
                    String cvc = request.getParameter("cvc");
                    String titularTarjeta = request.getParameter("titularTarjeta");
                    
                    DTFormaPago formaPago = new DTTarjeta(tipoTarjeta, nroTarjeta,
                            vencTarjeta, cvc, titularTarjeta);
                    DTPago pago = new DTPago(montoPago, formaPago, fechaPago);
                    this.controller.pagarColaboracion(pago, idColaboracion);
                    
                    mandarMailProponente();
                    mandarMailColaborador();
                    
                    response.sendRedirect("/pagar");
                } else if ("transferencia".equals(metodoPago)) {
                    String nombreBanco = request.getParameter("nombreBanco");
                    String cuentaBanco = request.getParameter("cuentaBanco");
                    String titularBanco = request.getParameter("titularBanco");
                    
                    DTFormaPago formaPago = new DTTransferenciaBancaria(nombreBanco,
                            cuentaBanco, titularBanco);
                    DTPago pago = new DTPago(montoPago, formaPago, fechaPago);
                    this.controller.pagarColaboracion(pago, idColaboracion);
                    response.sendRedirect("/pagar");
                } else if ("paypal".equals(metodoPago)) {
                    String cuentaPaypal = request.getParameter("cuentaPaypal");
                    String titularPaypal = request.getParameter("titularPaypal");
                    
                    DTFormaPago formaPago = new DTPaypal(cuentaPaypal, titularPaypal);
                    DTPago pago = new DTPago(montoPago, formaPago, fechaPago);
                    this.controller.pagarColaboracion(pago, idColaboracion);
                    response.sendRedirect("/pagar");
                } else {
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
                }
                break;
            default:
                response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        }

    }

    // </editor-fold>
    
    private void mandarMailProponente() {
        
    }
    
    private void mandarMailColaborador() {
        
    }
}
