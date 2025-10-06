/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.wutils;

import jakarta.servlet.http.HttpSession;

/**
 *
 * @author mark
 */
public final class SesionUtils {
    private SesionUtils() {
        throw new UnsupportedOperationException("No se puede instanciar esta clase.");
    }
    
    public static boolean esVisitante(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        return rol == null;
    }
    
    public static boolean puedeCrearPropuesta(HttpSession session) {
        String rol = (String) session.getAttribute("rol");
        if (rol == null || rol.equals("colaborador")) {
            return false;
        } else {
            return true;
        }
    }
}
