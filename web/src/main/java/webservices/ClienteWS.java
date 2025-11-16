package webservices;
import webservices.ControllerWS;
import webservices.ControllerWS_Service;
import jakarta.xml.ws.BindingProvider;

public class ClienteWS {

    public static ControllerWS getPort() {

        ControllerWS_Service service = new ControllerWS_Service();
        ControllerWS port = service.getControllerWSPort();

        String url = System.getenv("WS_IP");
        if (url == null) {
            url = "http://localhost:9128/servidor/servicio";
        }

        ((BindingProvider) port).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            url
        );

        return port;
    }
}
