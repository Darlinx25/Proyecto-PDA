package culturarte.webservices;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class SvcConfig {
    private static final Properties props = new Properties();
    
    static {
        try {
            Path path = Paths.get(System.getProperty("user.home"),
                    ".Culturarte", "svc.properties");
            props.load(Files.newBufferedReader(path));
        } catch (Exception ex) {
            throw new RuntimeException("No se puede cargar configuración .Culturarte", ex);
        }
    }
    
    public static String get(String key) {
        return props.getProperty(key);
    }
}
