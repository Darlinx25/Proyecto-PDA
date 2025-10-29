
package culturarte.datatypes;

public class DTRegistroAcceso {
    
    private String ip;
    private String url;
    private String browser;
    private String os;

    public DTRegistroAcceso(String ip, String url, String browser, String os) {
        this.ip = ip;
        this.url = url;
        this.browser = browser;
        this.os = os;
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
