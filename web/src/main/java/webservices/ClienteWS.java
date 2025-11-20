package webservices;

import jakarta.xml.ws.BindingProvider;

public class ClienteWS {

    private static ControllerWS_Service service;

    static {
        service = new ControllerWS_Service();
    }
    
    public static ControllerWS getPort() {
        
        ControllerWS port = service.getControllerWSPort();
        
        String endpointUrl = WebConfig.get("soap.endpoint");

        ((BindingProvider) port).getRequestContext().put(
            BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
            endpointUrl
        );

        return port;
    }
}