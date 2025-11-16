package webservices;

import webservices.ControllerWS;
import webservices.ControllerWS_Service;
import jakarta.xml.ws.BindingProvider;
import java.net.URL; // ¡Importante!
import javax.xml.namespace.QName; // ¡Importante!

public class ClienteWS {

    private final static QName SERVICE_QNAME = new QName("http://webservices.culturarte/", "ControllerWS");
    private final static String DEFAULT_ENDPOINT = "http://localhost:9128/ControllerWS";

    public static ControllerWS getPort() {

        String endpointUrl = System.getenv("WS_IP");

        if (endpointUrl == null) {
            endpointUrl = DEFAULT_ENDPOINT; 
        }

        String wsdlUrl = endpointUrl + "?wsdl";
        
        ControllerWS_Service service = null;
        try {
            URL wsdlLocation = new URL(wsdlUrl);
            service = new ControllerWS_Service(wsdlLocation, SERVICE_QNAME);

        } catch (Exception e) {
            System.err.println("Error al cargar WSDL desde: " + wsdlUrl + ". Usando default.");
            System.err.println(e.getMessage());
            service = new ControllerWS_Service();
        }
        
        ControllerWS port = service.getControllerWSPort();

        ((BindingProvider) port).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            endpointUrl
        );

        return port;
    }
}