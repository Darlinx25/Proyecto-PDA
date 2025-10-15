package culturarte.logica;

import culturarte.datatypes.DTColaborador;
import culturarte.datatypes.DTUsuario;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

public class IControllerTest {
    
    private IController controller;
    
    @BeforeEach
    void init() {
        Manejador.setEmfPrueba();
        controller = IControllerFactory.getInstance().getIController();
    }

    

    @Test
    public void testCargarDatosPrueba() throws Exception {
        controller.cargarDatosPrueba();
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
        assertTrue(controller.listarUsuarios().isEmpty(), "tiene que estar vacía");
        DTUsuario u = new DTColaborador("pepe", "Jose", "Mendez", "12345678".toCharArray(), "12345678".toCharArray(), "pepe@gmail.com", LocalDate.of(1960, 4, 1), "MTimg.jpg");
        controller.addUsuario(u);
        assertFalse(controller.listarUsuarios().isEmpty(), "NO tiene que estar vacía");
    }

    @Test
    public void testListarColaboradores() throws Exception {
        assertTrue(controller.listarColaboradores().isEmpty(), "tiene que estar vacía");
        controller.cargarDatosPrueba();
        assertFalse(controller.listarColaboradores().isEmpty(), "NO tiene que estar vacía");
    }

    @Test
    public void testObtenerDTColaborador() {
    }

    @Test
    public void testObtenerPropuestasColaboradas() {
    }

    @Test
    public void testListarProponentes() {
    }

    @Test
    public void testObtenerDTProponente() {
    }

    @Test
    public void testListarColaboracionesColaborador() {
    }

    @Test
    public void testListarUsuarios() {
    }

    @Test
    public void testSeguirUsuario() {
    }

    @Test
    public void testDejarDeSeguirUsuario() {
    }

    @Test
    public void testListaPropuestasUsu() {
    }

    @Test
    public void testListarUsuariosSeguir() {
    }

    @Test
    public void testListarUsuariosSiguiendo() {
    }

    @Test
    public void testObtenerSeguidores() {
    }

    @Test
    public void testListarCategorias() {
    }

    @Test
    public void testAddCategoria() throws Exception {
    }

    @Test
    public void testAddPropuesta() throws Exception {
    }

    @Test
    public void testObtenerDTPropuesta() {
    }

    @Test
    public void testListarPropuestasEstado() {
    }

    @Test
    public void testObtenerDineroRecaudado() {
    }

    @Test
    public void testListarPropuestasProponentes() {
    }

    @Test
    public void testListarPropuestasProponentesIngresadas() {
    }

    @Test
    public void testCambiarEstadoPropuestaIngresada() {
    }

    @Test
    public void testListarPropuestas() {
    }

    @Test
    public void testModPropuesta() {
    }

    @Test
    public void testCambiarEstadoPropuesta() {
    }

    @Test
    public void testHacerComentario() {
    }

    @Test
    public void testComentarioExiste() {
    }

    @Test
    public void testListarPropuestasEstadoUsu() {
    }

    @Test
    public void testExtenderFinanciacion() {
    }

    @Test
    public void testRealizarColaboracion() throws Exception {
    }

    @Test
    public void testObtenerDTColaboracion() {
    }

    @Test
    public void testListarColaboraciones() {
    }

    @Test
    public void testEliminarColaboracion() {
    }

    @Test
    public void testObtenerColaboradoresColaboracion() {
    }

    @Test
    public void testObtenerTipoUser() {
    }

    @Test
    public void testAutenticarUsuario() {
    }

    @Test
    public void testObtenerCategorias() {
    }

    @Test
    public void testGuardarImagen() {
    }

    @Test
    public void testBuscarPropuestasTDL() {
    }
    
}
