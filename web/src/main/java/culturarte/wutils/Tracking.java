package culturarte.wutils;

import culturarte.datatypes.DTRegistroAcceso;
import jakarta.servlet.http.HttpServletRequest;

public final class Tracking {

    private Tracking() {
        throw new UnsupportedOperationException("No se puede instanciar esta clase.");
    }

    public static DTRegistroAcceso generarDTRegistroAcceso(HttpServletRequest request) {
        
        String ip = request.getRemoteAddr();
        String url;
        String browser;
        String os;
        
        StringBuilder requestURL = new StringBuilder(request.getRequestURL().toString());
        String queryString = request.getQueryString();
        
        if (queryString == null || queryString.isEmpty()) {
            url = requestURL.toString();
        } else {
            url = requestURL.append('?').append(queryString).toString();
        }
        
        String userAgent = request.getHeader("User-Agent");
        browser = parseBrowser(userAgent);
        os = parseOs(userAgent);
        
        return new DTRegistroAcceso(ip, url, browser, os);
    }
    
    private static String parseBrowser(String userAgent) {
        if (userAgent.contains("Firefox")) {
            return "Firefox";
        } else if (userAgent.contains("Safari")) {
            return "Safari";
        } else if (userAgent.contains("Chrome")) {
            return "Chrome";
        } else if (userAgent.contains("Opera")) {
            return "Opera";
        } else if (userAgent.contains("Edge")) {
            return "Edge";
        } else if (userAgent.contains("IE")) {
            return "IE";
        } else {
            return "desconocido";
        }
    }
    
    private static String parseOs(String userAgent) {
        if (userAgent.contains("Android")) {
            return "Android";
        } else if (userAgent.contains("Windows")) {
            return "Windows";
        } else if (userAgent.contains("Mac")) {
            return "Mac";
        } else if (userAgent.contains("Linux")) {
            return "Linux";
        } else if (userAgent.contains("FreeBSD")) {
            return "FreeBSD";
        } else {
            return "desconocido";
        }
    }
}
