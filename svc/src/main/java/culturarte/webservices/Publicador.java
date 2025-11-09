package culturarte.webservices;

import culturarte.logica.Controller;
import culturarte.excepciones.BadPasswordException;
import culturarte.excepciones.CategoriaDuplicadaException;
import culturarte.excepciones.EmailRepetidoException;
import culturarte.excepciones.NickRepetidoException;
import culturarte.excepciones.PropuestaDuplicadaException;


public class Publicador {

    public static void main(String[] args)
            throws NickRepetidoException, EmailRepetidoException,
            PropuestaDuplicadaException, CategoriaDuplicadaException,
            BadPasswordException {

        CulturarteWS ws = new CulturarteWS();
        ws.publicar();
    }
}