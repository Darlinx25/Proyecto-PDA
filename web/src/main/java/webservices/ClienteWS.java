package webservices;

import jakarta.xml.ws.BindingProvider;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ClienteWS {

    private static final String CONFIG_DIR = System.getProperty("user.home") + File.separator + ".Culturarte";
    private static final String CONFIG_FILE = CONFIG_DIR + File.separator + "web.properties";
    private static final String DEFAULT_URL = "http://localhost:9128/ControllerWS";

    private static String endpointUrl = null;

    private ClienteWS() {}

    public static ControllerWS getPort() {
        ControllerWS_Service service = new ControllerWS_Service();
        ControllerWS port = service.getControllerWSPort();

        if (endpointUrl == null) {
            endpointUrl = loadEndpointUrl();
        }

        // Forzamos la URL
        ((BindingProvider) port).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            endpointUrl
        );

        // Timeout razonable (30 segundos) para evitar hangs eternos
        ((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.connect.timeout", 30000);
        ((BindingProvider) port).getRequestContext().put("com.sun.xml.ws.request.timeout", 60000);

        return port;
    }

    private static String loadEndpointUrl() {
        Properties props = new Properties();
        File configDir = new File(CONFIG_DIR);
        File file = new File(CONFIG_FILE);

        // Creamos la carpeta si no existe
        if (!configDir.exists()) {
            configDir.mkdirs();
        }

        if (file.exists() && file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                props.load(fis);
                String url = props.getProperty("ws.endpoint");
                if (url != null && !url.trim().isEmpty()) {
                    String finalUrl = url.trim();
                    System.out.println("Conectando al servidor central en: " + finalUrl);
                    return finalUrl;
                }
            } catch (IOException e) {
                System.err.println("Error leyendo web.properties: " + e.getMessage());
            }
        }

        System.out.println("No se encontró configuración → usando por defecto: " + DEFAULT_URL);
        return DEFAULT_URL;
    }
}