
package culturarte.datatypes;

public class DTRegistroAcceso {
    
    private long id;
    private String ip;
    private String url;
    private String browser;
    private String os;

    //para obtener de la base de datos
    public DTRegistroAcceso(long id, String ip, String url, String browser, String os) {
        this.id = id;
        this.ip = ip;
        this.url = url;
        this.browser = browser;
        this.os = os;
    }
    
    //para registrar
    public DTRegistroAcceso(String ip, String url, String browser, String os) {
        this.ip = ip;
        this.url = url;
        this.browser = browser;
        this.os = os;
    }
    
    public long getId() {
        return id;
    }

    public String getIp() {
        return ip;
    }

    public String getUrl() {
        return url;
    }

    public String getBrowser() {
        return browser;
    }

    public String getOs() {
        return os;
    }
    
    
}
