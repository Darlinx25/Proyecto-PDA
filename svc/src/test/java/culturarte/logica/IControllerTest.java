package culturarte.logica;

import culturarte.datatypes.DTColaboracion;
import culturarte.datatypes.DTColaborador;
import culturarte.datatypes.DTDireccion;
import culturarte.datatypes.DTProponente;
import culturarte.datatypes.DTPropuesta;
import culturarte.datatypes.DTUsuario;
import culturarte.excepciones.BadPasswordException;
import culturarte.excepciones.EmailRepetidoException;
import culturarte.excepciones.NickRepetidoException;
import culturarte.excepciones.PropuestaDuplicadaException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class IControllerTest {
    
    private IController controller;
    
    @BeforeEach
    void init() throws Exception {
        Manejador.setEmfPrueba();
        controller = IControllerFactory.getInstance().getIController();
        controller.cargarDatosPrueba();
    }

    

    @Test
    public void testCargarDatosPrueba() throws Exception {
        assertFalse(controller.listarColaboraciones().isEmpty(), "NO tiene que estar vacía");
        assertFalse(controller.listarColaboradores().isEmpty(), "NO tiene que estar vacía");
        assertFalse(controller.listarProponentes().isEmpty(), "NO tiene que estar vacía");
        assertFalse(controller.listarPropuestas().isEmpty(), "NO tiene que estar vacía");
        assertFalse(controller.listarPropuestasProponentes().isEmpty(), "NO tiene que estar vacía");
        assertFalse(controller.listarPropuestasProponentesIngresadas().isEmpty(), "NO tiene que estar vacía");
        assertFalse(controller.listarUsuarios().isEmpty(), "NO tiene que estar vacía");
    }

    @Test
    public void testAddUsuario() throws Exception {
        assertEquals(20, controller.listarUsuarios().size());
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        assertEquals(21, controller.listarUsuarios().size());
        
        DTUsuario u2 = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe0423@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        NickRepetidoException throw1 = assertThrows(
           NickRepetidoException.class,
           () -> controller.addUsuario(u2),
           "tendría que haber tirado excepción");
        assertTrue(throw1.getMessage().contains("Error al registrar usuario, Nick ya existente."));
        
        DTUsuario u3 = new DTColaborador("carlitos1", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        EmailRepetidoException throw2 = assertThrows(
           EmailRepetidoException.class,
           () -> controller.addUsuario(u3),
           "tendría que haber tirado excepción");
        assertTrue(throw2.getMessage().contains("Error al registrar usuario, Email ya existente."));
        
        DTUsuario u4 = new DTColaborador("juancho1", "Jose", "Mendez", "1234567".toCharArray(), "12345678".toCharArray(), "juancho1@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        BadPasswordException throw3 = assertThrows(
           BadPasswordException.class,
           () -> controller.addUsuario(u4),
           "tendría que haber tirado excepción");
        assertTrue(throw3.getMessage().contains("La contraseña tiene que tener de 8 a 24 caracteres de largo"));
        
        DTUsuario u5 = new DTColaborador("juancho2", "Jose", "Mendez", "12345678".toCharArray(), "1234567".toCharArray(), "juancho2@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        BadPasswordException throw4 = assertThrows(
           BadPasswordException.class,
           () -> controller.addUsuario(u5),
           "tendría que haber tirado excepción");
        assertTrue(throw4.getMessage().contains("La contraseña tiene que tener de 8 a 24 caracteres de largo"));
        
        DTUsuario u6 = new DTColaborador("juancho3", "Jose", "Mendez", "a12345678".toCharArray(), "b12345678".toCharArray(), "juancho3@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        BadPasswordException throw5 = assertThrows(
           BadPasswordException.class,
           () -> controller.addUsuario(u6),
           "tendría que haber tirado excepción");
        assertTrue(throw5.getMessage().contains("La contraseña y la confirmación no coinciden"));
        
    }

    @Test
    public void testListarColaboradores() throws Exception {
        assertFalse(controller.listarColaboradores().isEmpty(), "NO tiene que estar vacía");
    }

    @Test
    public void testObtenerDTColaborador() throws Exception {
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        DTColaborador c = controller.obtenerDTColaborador("pepe");
        assertEquals("pepe", c.getNickname());
        assertEquals("Jose", c.getNombre());
        assertEquals("Mendez", c.getApellido());
        assertEquals("pepe@gmail.com", c.getEmail());
        assertEquals(1960, c.getFechaNacimiento().getYear());
        assertEquals(4, c.getFechaNacimiento().getMonthValue());
        assertEquals(1, c.getFechaNacimiento().getDayOfMonth());
        assertEquals("MTimg.jpg", c.getImagen());
        
        DTColaborador c2 = controller.obtenerDTColaborador("pepe@gmail.com");
        assertNotNull(c2);
        
        DTColaborador c3 = controller.obtenerDTColaborador("ernesto");
        assertNull(c3);
    }

    @Test
    public void testObtenerPropuestasColaboradas() {
        ArrayList<String> propuestas = controller.obtenerPropuestasColaboradas("tonyp");
        assertEquals(2, propuestas.size());
        for (String p : propuestas) {
            assertTrue(p.equals("Pilsen Rock") || p.equals("Un día de Julio"));
        }
    }

    @Test
    public void testListarProponentes() {
        assertFalse(controller.listarColaboradores().isEmpty(), "NO tiene que estar vacía");
    }

    @Test
    public void testObtenerDTProponente() throws Exception {
        DTProponente p = controller.obtenerDTProponente("hrubino");
        assertNotNull(p);
        
        DTProponente p2 = controller.obtenerDTProponente("horacio.rubino@guambia.com.uy");
        assertNotNull(p2);
        
        DTProponente p3 = controller.obtenerDTProponente("doawkepoffs");
        assertNull(p3);
    }

    @Test
    public void testListarColaboracionesColaborador() {
        ArrayList<String> colaboraciones = controller.listarColaboracionesColaborador("tonyp");
        assertEquals(2, colaboraciones.size());
    }

    @Test
    public void testListarUsuarios() {
        ArrayList<String> usuarios = controller.listarUsuarios();
        assertEquals(20, usuarios.size());
    }

    @Test
    public void testSeguirUsuario() throws Exception {
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        DTUsuario u2 = (new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "descripción",
                "https://www.facebook.com/pepe2", "pepe2", "Jose", "González", "12345678".toCharArray(), "12345678".toCharArray(), "pepe2@hotmail.com", LocalDate.of(1947, 1, 1), "CSimg.jpg"));
        controller.addUsuario(u2);
        
        ResultadoSeguirUsuario r = controller.seguirUsuario("pepe", "pepe2");
        
        assertEquals(ResultadoSeguirUsuario.EXITO, r);
    }

    @Test
    public void testDejarDeSeguirUsuario() throws Exception {
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        DTUsuario u2 = (new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "descripción",
                "https://www.facebook.com/pepe2", "pepe2", "Jose", "González", "12345678".toCharArray(), "12345678".toCharArray(), "pepe2@hotmail.com", LocalDate.of(1947, 1, 1), "CSimg.jpg"));
        controller.addUsuario(u2);
        
        controller.seguirUsuario("pepe", "pepe2");
        ResultadoSeguirUsuario r = controller.dejarDeSeguirUsuario("pepe", "pepe");
        
        assertEquals(ResultadoSeguirUsuario.EXITO, r);
    }

    @Test
    public void testListaPropuestasUsu() throws Exception {
        ArrayList<String> propuestas = controller.listaPropuestasUsu("hrubino");
        assertFalse(propuestas.isEmpty());
    }

    @Test
    public void testListarUsuariosSeguir() throws Exception {
        DTUsuario u = new DTColaborador("pepe4", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe4@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        DTUsuario u2 = (new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "descripción",
                "https://www.facebook.com/pepe2", "pepe5", "Jose", "González", "12345678".toCharArray(), "12345678".toCharArray(), "pepe5@hotmail.com", LocalDate.of(1947, 1, 1), "CSimg.jpg"));
        controller.addUsuario(u2);
        
        controller.seguirUsuario("pepe4", "pepe5");
        ArrayList<String> seguidos = controller.listarUsuariosSeguir("pepe4");
        
        assertFalse(seguidos.isEmpty());
    }

    @Test
    public void testListarUsuariosSiguiendo() throws Exception {
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        DTUsuario u2 = (new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "descripción",
                "https://www.facebook.com/pepe2", "pepe2", "Jose", "González", "12345678".toCharArray(), "12345678".toCharArray(), "pepe2@hotmail.com", LocalDate.of(1947, 1, 1), "CSimg.jpg"));
        controller.addUsuario(u2);
        
        controller.seguirUsuario("pepe", "pepe2");
        List<String> seguidos = controller.listarUsuariosSiguiendo("pepe");
        String seguido = seguidos.get(0);
        assertTrue("pepe2".equals(seguido));
    }

    @Test
    public void testObtenerSeguidores() throws Exception {
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        DTUsuario u2 = (new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "descripción",
                "https://www.facebook.com/pepe2", "pepe2", "Jose", "González", "12345678".toCharArray(), "12345678".toCharArray(), "pepe2@hotmail.com", LocalDate.of(1947, 1, 1), "CSimg.jpg"));
        controller.addUsuario(u2);
        
        controller.seguirUsuario("pepe", "pepe2");
        ArrayList<String> seguidores = controller.ObtenerSeguidores("pepe2");
        
        assertFalse(seguidores.isEmpty());
    }

    @Test
    public void testListarCategorias() {
        assertNull(controller.listarCategorias());
    }

    @Test
    public void testAddCategoria() throws Exception {
        assertEquals(21, controller.obtenerCategorias().size());
        controller.addCategoria("Danza Cadabra", "Categorías");
        assertEquals(22, controller.obtenerCategorias().size());
    }

    @Test
    public void testAddPropuesta() throws Exception {
        DTUsuario u2 = (new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "descripción",
                "https://www.facebook.com/pepe2", "pepe11", "Jose", "González", "12345678".toCharArray(), "12345678".toCharArray(), "pepe11@hotmail.com", LocalDate.of(1947, 1, 1), "CSimg.jpg"));
        controller.addUsuario(u2);
        DTPropuesta prop;
        List<TipoRetorno> retornos = new ArrayList<>();
        retornos.add(TipoRetorno.ENTRADA_GRATIS);
        prop = new DTPropuesta("Cine en las nubes",
                "descripción", null, "tropósfera",
                LocalDate.of(2025, 11, 16), 200, 150000, "Cine al Aire Libre", "pepe11",
                retornos, new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 10, 15, 14, 50)));
        
        controller.addPropuesta(prop);
        assertEquals(1, controller.listaPropuestasUsu("pepe11").size());
        PropuestaDuplicadaException throw1 = assertThrows(
           PropuestaDuplicadaException.class,
           () -> controller.addPropuesta(prop),
           "tendría que haber tirado excepción");
        assertTrue(throw1.getMessage().contains("Ya existe una propuesta con ese titulo."));
    }

    @Test
    public void testObtenerDTPropuesta() {
        DTPropuesta p = controller.obtenerDTPropuesta("Pilsen Rock");
        assertNotNull(p);
        DTPropuesta p2 = controller.obtenerDTPropuesta("Cine en el agua");
        assertNull(p2);
    }

    @Test
    public void testListarPropuestasEstado() { 
        assertFalse(controller.listarPropuestasEstado(1).isEmpty());
    }

    @Test
    public void testObtenerDineroRecaudado() {
        assertEquals("290000.0", controller.obtenerDineroRecaudado("Pilsen Rock"));
    }

    @Test
    public void testListarPropuestasProponentes() {
        assertFalse(controller.listarPropuestasProponentes().isEmpty());
    }

    @Test
    public void testListarPropuestasProponentesIngresadas() {
        assertEquals(1, controller.listarPropuestasProponentesIngresadas().size());
    }

    @Test
    public void testCambiarEstadoPropuestaIngresada() {
        controller.cambiarEstadoPropuestaIngresada("Bardo en la FING", EstadoPropuesta.PUBLICADA);
        DTPropuesta p = controller.obtenerDTPropuesta("Bardo en la FING");
        assertEquals(EstadoPropuesta.PUBLICADA, p.getEstadoActual().getEstado(), "tiene que dar publicada");
        
    }

    @Test
    public void testListarPropuestas() {
        assertFalse(controller.listarPropuestas().isEmpty());
    }

    @Test
    public void testModPropuesta() {
        List<TipoRetorno> entrada = new ArrayList<>();
        entrada.add(TipoRetorno.ENTRADA_GRATIS);
        DTPropuesta prop = new DTPropuesta("Bardo en la FING",
                "El 10 de Diciembre se presentará Bardo Científico en la FING. El humor puede ser usado como una herramienta "
                + "importante para el aprendizaje y la democratización de la ciencia, los monólogos científicos son una forma didáctica de "
                + "apropiación del conocimiento científico y contribuyen a que el público aprenda ciencia de forma amena. Los invitamos a "
                + "pasar un rato divertido, en un espacio en el cual aprenderán cosas de la ciencia que los sorprenderán. ¡Los esperamos!", null, "Anfiteatro Edificio \"José Luis Massera\"",
                LocalDate.of(2025, 12, 10), 200, 100000, "Stand-up", "losBardo", entrada, new Estado(EstadoPropuesta.CANCELADA, LocalDateTime.of(2025, 10, 16, 2, 12)));
        
        DTPropuesta p = controller.obtenerDTPropuesta("Bardo en la FING");
        assertEquals(EstadoPropuesta.INGRESADA, p.getEstadoActual().getEstado(), "tiene que dar ingresada");
        
        controller.modPropuesta(prop);
        
        p = controller.obtenerDTPropuesta("Bardo en la FING");
        assertEquals(EstadoPropuesta.CANCELADA, p.getEstadoActual().getEstado(), "tiene que dar cancelada");
    }

    @Test
    public void testCambiarEstadoPropuesta() {
        controller.cambiarEstadoPropuesta("Bardo en la FING", EstadoPropuesta.PUBLICADA);
        DTPropuesta p = controller.obtenerDTPropuesta("Bardo en la FING");
        assertEquals(EstadoPropuesta.PUBLICADA, p.getEstadoActual().getEstado(), "tiene que dar publicada");
    }

    @Test
    public void testHacerComentario() {
        controller.hacerComentario("jejeje", "tonyp", "Pilsen Rock");
    }

    @Test
    public void testComentarioExiste() {
        controller.hacerComentario("jejeje", "tonyp", "Pilsen Rock");
        assertTrue(controller.comentarioExiste("Pilsen Rock", "tonyp"));
        assertFalse(controller.comentarioExiste("Pilsen Rock", "usuarioInexistente"));
    }

    @Test
    public void testListarPropuestasEstadoUsu() {
        assertFalse(controller.listarPropuestasEstadoUsu(EstadoPropuesta.FINANCIADA.ordinal(), "hrubino").isEmpty());
    }

    @Test
    public void testExtenderFinanciacion() {
        DTPropuesta p = controller.obtenerDTPropuesta("Un día de Julio");
        LocalDate fecha1 = p.getPlazoFinanciacion();
        controller.extenderFinanciacion("Un día de Julio");
        p = controller.obtenerDTPropuesta("Un día de Julio");
        LocalDate fecha2 = p.getPlazoFinanciacion();
        assertTrue(fecha2.isAfter(fecha1), "la fecha2 tiene que ser después de la fecha1");
    }

    @Test
    public void testRealizarColaboracion() throws Exception {
        assertEquals("290000.0", controller.obtenerDineroRecaudado("Pilsen Rock"));
        controller.realizarColaboracion("marcelot", "Pilsen Rock", 120, "Porcentaje de ganancias");
        assertEquals("290120.0", controller.obtenerDineroRecaudado("Pilsen Rock"));
    }

    @Test
    public void testObtenerDTColaboracion() {
        DTColaboracion c = controller.obtenerDTColaboracion((long) 4);
        assertEquals("marcelot", c.getColaborador());
        assertEquals(200000.0, c.getMonto());
    }

    @Test
    public void testListarColaboraciones() {
        assertFalse(controller.listarColaboraciones().isEmpty());
    }

    @Test
    public void testEliminarColaboracion() {
        int antes = controller.listarColaboraciones().size();
        controller.eliminarColaboracion((long) 1);
        int despues = controller.listarColaboraciones().size();
        assertTrue(antes > despues);
    }

    @Test
    public void testObtenerColaboradoresColaboracion() {
        assertEquals(3, controller.obtenerColaboradoresColaboracion("Pilsen Rock").size());
    }

    @Test
    public void testObtenerTipoUser() throws Exception {
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        DTUsuario u2 = (new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "descripción",
                "https://www.facebook.com/pepe2", "pepe2", "Jose", "González", "12345678".toCharArray(), "12345678".toCharArray(), "pepe2@hotmail.com", LocalDate.of(1947, 1, 1), "CSimg.jpg"));
        controller.addUsuario(u2);
        
        assertEquals("colaborador", controller.obtenerTipoUser("pepe"));
        assertEquals("proponente", controller.obtenerTipoUser("pepe2"));
    }

    @Test
    public void testAutenticarUsuario() throws Exception {
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        assertTrue(controller.autenticarUsuario("pepe", "12345678".toCharArray()));
        assertFalse(controller.autenticarUsuario("pepe", "asfgafesf".toCharArray()));
    }

    @Test
    public void testObtenerCategorias() {
        ArrayList<String> categorias = controller.obtenerCategorias();
        assertEquals(21, categorias.size());
        
    }

    @Test
    public void testGuardarImagen() {
        byte[] b = {(byte) 0xAA};
        String nombreImagen = controller.guardarImagen(b);
        assertNull(nombreImagen);
    }

    @Test
    public void testBuscarPropuestasTDL() throws Exception {
        DTUsuario u2 = (new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "descripción",
                "https://www.facebook.com/pepe2", "pepe3", "Jose", "González", "12345678".toCharArray(), "12345678".toCharArray(), "pepe3@hotmail.com", LocalDate.of(1947, 1, 1), "CSimg.jpg"));
        controller.addUsuario(u2);
        controller.addCategoria("baile al Aire Libre", "Categorías");
        DTPropuesta prop;
        List<TipoRetorno> retornos = new ArrayList<>();
        retornos.add(TipoRetorno.ENTRADA_GRATIS);
        prop = new DTPropuesta("baile en las nubes",
                "descripción", null, "tropósfera",
                LocalDate.of(2025, 11, 16), 200, 150000, "Cine al Aire Libre", "pepe3",
                retornos, new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2025, 10, 15, 14, 50)));
        
        controller.addPropuesta(prop);
        ArrayList<DTPropuesta> propuestas = controller.buscarPropuestasTDL("baile en las nubes");
        assertEquals(1, propuestas.size());
    }
    
    @Test
    public void testFavoritarPropuesta() {
        controller.favoritarPropuesta("tonyp", "Pilsen Rock");
    }
    
    @Test
    public void testPropuestaYaFavorita() {
        controller.favoritarPropuesta("tonyp", "Pilsen Rock");
        assertTrue(controller.propuestaYaFavorita("Pilsen Rock", "tonyp"));
        assertFalse(controller.propuestaYaFavorita("Pilsen Rock", "usuarioInexistente"));
    }
    
    @Test
    public void testListarPropuestasFavoritas() {
        controller.favoritarPropuesta("tonyp", "Pilsen Rock");
        assertEquals(1, controller.listarPropuestasFavoritas("tonyp").size());
    }
    
    @Test
    public void testActualizarEstado() {
        controller.actualizarEstado();
    }
}
