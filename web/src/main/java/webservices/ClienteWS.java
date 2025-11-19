package webservices;

import jakarta.xml.ws.BindingProvider;
import webservices.ControllerWS;
import webservices.ControllerWS_Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ClienteWS {

    private static final String CONFIG_DIR = System.getProperty("user.home") + "/.Culturarte";
    private static final String CONFIG_FILE = CONFIG_DIR + "/web.properties";
    private static final String DEFAULT_URL = "http://localhost:9128/ControllerWS";

    private static String endpointUrl = null;

    public static ControllerWS getPort() {
        ControllerWS_Service service = new ControllerWS_Service();
        ControllerWS port = service.getControllerWSPort();

        if (endpointUrl == null) {
            endpointUrl = loadEndpointUrl();
        }

        ((BindingProvider) port).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            endpointUrl
        );

        return port;
    }

    private static String loadEndpointUrl() {
        Properties props = new Properties();
        File file = new File(CONFIG_FILE);

        if (file.exists() && file.isFile()) {
            try (FileInputStream fis = new FileInputStream(file)) {
                props.load(fis);
                String url = props.getProperty("ws.endpoint");
                if (url != null && !url.trim().isEmpty()) {
                    System.out.println("INFO: Conectando al servidor central en: " + url.trim());
                    return url.trim();
                }
            } catch (IOException e) {
                System.err.println("Error leyendo configuración: " + e.getMessage());
            }
        }

        System.out.println("ADVERTENCIA: No se encontró ~/.Culturarte/web.properties → usando URL por defecto: " + DEFAULT_URL);
        return DEFAULT_URL;
    }
}