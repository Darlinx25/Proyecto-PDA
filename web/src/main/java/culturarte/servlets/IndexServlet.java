package culturarte.servlets;

import culturarte.wutils.Tracking;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import webservices.ControllerWS;
import webservices.ControllerWS_Service;
import webservices.DTColaborador;
import webservices.DTProponente;

@WebServlet(name = "IndexServlet", urlPatterns = {"/index"})
public class IndexServlet extends HttpServlet {

    //private IController controller = IControllerFactory.getInstance().getIController();
    private ControllerWS webServices;

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods.">
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();
        
        this.webServices.registrarAcceso(Tracking.generarDTRegistroAcceso(request));
        sincImg();
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Procesamiento de requests.">
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();
        ControllerWS_Service service = new ControllerWS_Service();
        this.webServices = service.getControllerWSPort();
        String userAgent = request.getHeader("User-Agent").toLowerCase();
        String rol = (String) session.getAttribute("rol");
        List<String> cat = this.webServices.obtenerCategorias();
        this.webServices.actualizarEstado();
        this.webServices.actualizarPuntajes();
        request.setAttribute("categorias",cat);
        response.setContentType("text/html;charset=UTF-8");
        boolean esMovil = userAgent.contains("mobi") || userAgent.contains("android") 
                          || userAgent.contains("iphone") || userAgent.contains("ipad");

        if (esMovil && rol==null) {
            
            request.getRequestDispatcher("/WEB-INF/jsp/iniciarSesionMovil.jsp").forward(request, response);
            
        }else if (esMovil && "colaborador".equals(rol)){
        
        request.getRequestDispatcher("/WEB-INF/jsp/indexMovil.jsp").forward(request, response);
        }
        
        else {
            
            request.getRequestDispatcher("/WEB-INF/jsp/index.jsp").forward(request, response);
            
        }
        
        
    }
    // </editor-fold>
    
    
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
   
    private void guardarImagen(byte[] bytesImagen,String nombreArchivo) {
        Path pathImagen = Paths.get(System.getProperty("user.home"),"imgProyePDA", nombreArchivo);
        if(bytesImagen == null){
            return;
        }
        try {
            Files.createDirectories(pathImagen.getParent());
            Files.write(pathImagen, bytesImagen, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);

        } catch (IOException ex) {

        }
    }
    
    
}






/*String userAgent = request.getHeader("User-Agent").toLowerCase();
 boolean esMovil = userAgent.contains("mobi") || userAgent.contains("android") 
                          || userAgent.contains("iphone") || userAgent.contains("ipad");

        if (esMovil) {
            
            
            
        } else {
            
            
            
        }
*/