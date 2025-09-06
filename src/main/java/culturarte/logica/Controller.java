/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import culturarte.excepciones.PropuestaYaColaboradaException;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Collections;

import java.util.List;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mark
 */
//SINGLETON
public class Controller implements IController {

    private static Controller instancia;

    private EntityManagerFactory emf;//HAY Q SACAR ESTO
    private EntityManager em;//Y ESTO
    private Manejador emr = Manejador.getInstance(); //Y USAR ESTO, DE MOMENTO SE LLAMA EMR pero cuando saquemos lo anterior taria bueno llamro em

    private Controller() {

        emf = Persistence.createEntityManagerFactory("Proyecto_PDA");//ESTO TAMBIEN SE VA
        em = emf.createEntityManager();

        // Verificar si la raíz ya existe en la DB
        Categoria raiz = emr.find(Categoria.class, "Categorías");
        if (raiz == null) {
            raiz = new Categoria("Categorías");
            emr.add(raiz);
        }
    }

    public static Controller getInstance() {
        if (instancia == null) {
            instancia = new Controller();
        }
        return instancia;
    }
    
    @Override
    public void cargarDatosPrueba(){
        cargarUsuariosPrueba();
        
        
        
        
    }
    
    private void cargarUsuariosPrueba() {
        List<DTUsuario> usu = obtenerProponentesPrueba();
        usu.addAll(obtenerColaboradoresPrueba());
        
        for (DTUsuario u : usu) {
            addUsuario(u);
        }
    }
    private List<DTUsuario> obtenerProponentesPrueba() {
        List<DTUsuario> usu = new ArrayList<>();
        usu.add(new DTProponente(new DTDireccion("Montevideo"," 18 de Julio", 1234),"Horacio Rubino Torres nace el 25 de febrero de 1962, es conductor, actor y libretista. Debuta en 1982 en carnaval\n" +
        "en Los \"Klaper´s\", donde estuvo cuatro años, actuando y libretando. Luego para \"Gaby´s\" (6 años), escribió en\n" +
        "categoría revistas y humoristas y desde el comienzo y hasta el presente en su propio conjunto Momosapiens.",
        "https://twitter.com/horaciorubino","hrubino","Horacio","Rubino","horacio.rubino@guambia.com.uy",LocalDate.of(1980,5, 20),"../imagenesRespaldoBD/HRimg.jpg"));
        
        return usu;
    }
    private List<DTUsuario> obtenerColaboradoresPrueba() {
        List<DTUsuario> usu = new ArrayList<>();
        usu.add(new DTColaborador("robinh", "Robin", "Henderson", "Robin.h@tinglesa.com.uy", LocalDate.of(1940, 8, 3), "../imagenesRespladoBD/RHimg.jpg"));
        
        return usu;
    }
    
    @Override
    public ResultadoRegistroUsr addUsuario(DTUsuario user) {
        String nick = user.getNickname();
        String email = user.getEmail();

        if (emr.datoUsuarioRepetido("nickname", nick) > 0) {
            return ResultadoRegistroUsr.NICK_REPETIDO;
        }
        if (emr.datoUsuarioRepetido("email", email) > 0) {
            return ResultadoRegistroUsr.EMAIL_REPETIDO;
        }
        String nombre = user.getNombre();
        String apellido = user.getApellido();
        LocalDate fechaNac = user.getFechaNacimiento();
        String imagen = user.getImagen();
        Usuario usu = null;
        if (user instanceof DTColaborador) {
            usu = new Colaborador(nick, nombre, apellido, email, fechaNac, imagen);
        } else if (user instanceof DTProponente) {
            DTProponente userProp = (DTProponente) user;//downcast
            DTDireccion direccion = userProp.getDireccion();
            String biografia = userProp.getBiografia();
            String sitioWeb = userProp.getSitioWeb();
            usu = new Proponente(direccion, biografia, sitioWeb, nick, nombre, apellido, email, fechaNac, imagen);
        }

        emr.add(usu);
        return ResultadoRegistroUsr.EXITO;

    }

    @Override
    public DefaultMutableTreeNode listarCategorias() {
        Categoria catRaiz = emr.find(Categoria.class, "Categorías");
        return nodosArbolCategorias(catRaiz);
    }

    private DefaultMutableTreeNode nodosArbolCategorias(Categoria cat) {
        if (cat == null) {
            return null;
        }
        DefaultMutableTreeNode nodito = new DefaultMutableTreeNode(cat.getNombre());
        if (cat.getSubCategorias().isEmpty()) {
            return nodito;
        }
        for (Categoria c : cat.getSubCategorias()) {
            nodito.add(nodosArbolCategorias(c));
        }
        return nodito;
    }

    @Override
    public void addCategoria(String nombre, String nombrePadre) {

        Categoria cat = new Categoria(nombre);
        if (nombrePadre != null) {
            Categoria padre = emr.find(Categoria.class, nombrePadre);
            if (padre != null) {
                padre.addSubcategoria(cat);
                cat.setPadre(padre);
            }
        } else {
            Categoria raiz = emr.find(Categoria.class, "Categorías");
            raiz.addSubcategoria(cat);
            cat.setPadre(raiz);
        }
        emr.add(cat);
    }

    @Override
    public ArrayList<String> listarColaboradores() {
        List<String> aux = emr.listarAtributo(String.class, "nickname", "Colaborador");
        return new ArrayList<>(aux);
    }

    @Override
    public DTColaborador obtenerDTColaborador(String nick) {
        try {
            Colaborador c = emr.find(Colaborador.class, nick);
            if (c != null) {
                return new DTColaborador(
                        c.getNickname(), c.getNombre(), c.getApellido(),
                        c.getEmail(), c.getFechaNacimiento(), c.getImagen());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> listarProponentes() {
        List<String> aux = emr.listarAtributo(String.class, "nickname", "Proponente");
        return new ArrayList<>(aux);
    }

    @Override
    public void addPropuesta(DTPropuesta prop) {
        String titulo = prop.getTitulo();

        String descripcion = prop.getDescripcion();
        String imagen = prop.getImagen();
        String lugarRealizara = prop.getLugarRealizara();
        LocalDate fechaRealizara = prop.getFechaRealizara();
        float precioEntrada = prop.getPrecioEntrada();
        float montoAReunir = prop.getMontoAReunir();

        Estado est = prop.getEstadoActual();
        // Buscar en la base de datos la categoría y el proponente
        Categoria tipoPropuesta = emr.find(Categoria.class, prop.getTipoPropuesta());
        Proponente proponedor = emr.find(Proponente.class, prop.getNickProponedor());

        List<TipoRetorno> tiposRetorno = prop.getTiposRetorno();

        Propuesta propuesta = new Propuesta(titulo, descripcion, imagen, lugarRealizara, fechaRealizara, precioEntrada, montoAReunir, tiposRetorno,
                tipoPropuesta, proponedor, est);

        emr.add(propuesta);

    }

    @Override
    public void modPropuesta(DTPropuesta prop) {
        String titulo = prop.getTitulo();

        Propuesta aux = null;
        try {
            aux = emr.find(Propuesta.class, titulo);
        } catch (NoResultException e) {
            return;
        }
        EstadoPropuesta ese = prop.getEstadoActual().getEstado();
        if (ese.ordinal() == 1) {
            aux.setFechaPublicacion(LocalDate.now());
        }
        aux.setDescripcion(prop.getDescripcion());
        if (prop.getImagen() != null) {
            aux.setImagen(prop.getImagen());
        }

        aux.setLugarRealizara(prop.getLugarRealizara());
        aux.setFechaRealizara(prop.getFechaRealizara());
        aux.setPrecioEntrada(prop.getPrecioEntrada());
        aux.setMontoAReunir(prop.getMontoAReunir());
        aux.setEstadoActual(prop.getEstadoActual());
        aux.setTiposRetorno(prop.getTiposRetorno());
        Categoria cat = emr.find(Categoria.class, prop.getTipoPropuesta());
        aux.setTipoPropuesta(cat);
        emr.mod(aux);

    }

    @Override
    public DTProponente obtenerDTProponente(String nick) {
        try {
            Proponente p = em.find(Proponente.class, nick); // Buscar proponente por PK
            if (p != null) {
                return new DTProponente(
                        p.getDireccion(),
                        p.getBiografia(),
                        p.getSitioWeb(),
                        p.getNickname(),
                        p.getNombre(),
                        p.getApellido(),
                        p.getEmail(),
                        p.getFechaNacimiento(),
                        p.getImagen()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> listaPropuestasUsu(String nick) {

        return emr.listaPropuestasUsuario(nick);
    }

    @Override
    public ArrayList<String> listarPropuestasEstado(int estado) {
        return emr.obtenerPropuestasEstado(estado);
    }

    @Override
    public ArrayList<String> listarPropuestas() {
        List<String> aux = emr.listarAtributo(String.class, "titulo", "Propuesta");
        return new ArrayList<>(aux);
    }

    @Override
    public DTPropuesta obtenerDTPropuesta(String titulo) {
        try {
            Propuesta p = emr.find(Propuesta.class, titulo);
            if (p != null) {
                return new DTPropuesta(
                        p.getTitulo(), p.getDescripcion(), p.getImagen(), p.getLugarRealizara(), p.getFechaRealizara(),
                        p.getPrecioEntrada(), p.getMontoAReunir(), p.getFechaPublicacion(),
                        p.getTipoPropuesta().getNombre(),
                        p.getProponedor().getNickname(),
                        p.getTiposRetorno(), p.getEstadoActual());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> listarUsuarios() {
        List<String> aux = emr.listarAtributo(String.class, "nickname", "Usuario");
        return new ArrayList<>(aux);
    }

    @Override
    public void seguirUsuario(String nickSegui, String nickUsu) {
        List<Usuario> usu1 = emr.obtenerUsuario(nickSegui);

        List<Usuario> usu2 = emr.obtenerUsuario(nickUsu);

        List<Usuario> aux = usu1.get(0).getUsuariosSeguidos();
        aux.add(usu2.get(0));
        usu1.get(0).setUsuariosSeguidos(aux);
        emr.add(usu1.get(0));

    }

    @Override
    public ArrayList<String> listarUsuariosSeguir(String nickname) {
        return emr.obtenerUsuariosSeguir(nickname);
    }

    @Override
    public ArrayList<String> listarPropuestasProponentes() {
        List<Object[]> aux;
        List<String> aux2 = new ArrayList<>();

        String query = "SELECT p.titulo, p.proponente.nickname FROM Propuesta p"
                + " WHERE p.estadoActual.estado = :estado1 OR p.estadoActual.estado = :estado2";
        try {
            aux = em.createQuery(query, Object[].class)
                    .setParameter("estado1", EstadoPropuesta.PUBLICADA)
                    .setParameter("estado2", EstadoPropuesta.EN_FINANCIACION)
                    .getResultList();
            for (Object[] fila : aux) {
                aux2.add(fila[0] + " - " + fila[1]);
            }
        } catch (Exception e) {
            aux2 = Collections.emptyList();
        }
        return (ArrayList<String>) aux2;
    }

    @Override
    public ArrayList<String> listarUsuariosSiguiendo(String nickname) {
        List<Usuario> aux;
        List<Usuario> aux2;
        List<String> aux3 = new ArrayList<>();
        try {
            aux = emr.obtenerUsuario(nickname);
            aux2 = aux.get(0).getUsuariosSeguidos();
            for (Usuario u : aux2) {
                aux3.add(u.getNickname());
            }
        } catch (Exception e) {
            aux3 = Collections.emptyList();
            e.printStackTrace();
        }
        return (ArrayList<String>) aux3;

    }

    @Override
    public void realizarColaboracion(String nickColab, String tituloProp, float montoColab, String tipoRetorno) throws PropuestaYaColaboradaException {
        Colaborador colab = em.find(Colaborador.class, nickColab);
        Propuesta prop = em.find(Propuesta.class, tituloProp);
        List<String> aux = new ArrayList<>();

        String query = "SELECT c.propuestaColaborada.titulo FROM Colaboracion c WHERE c.colaborador.nickname = :nickColab"
                + " AND c.propuestaColaborada.titulo = :tituloProp";

        try {
            aux = em.createQuery(query, String.class)
                    .setParameter("nickColab", nickColab)
                    .setParameter("tituloProp", tituloProp)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        if (colab != null && prop != null && aux.isEmpty()) {
            Colaboracion colaboracion = new Colaboracion(montoColab, tipoRetorno, colab, prop);

            EntityTransaction t = em.getTransaction();
            try {
                t.begin();
                em.persist(colaboracion);
                t.commit();
            } catch (Exception e) {
                t.rollback();
                e.printStackTrace();
            }
        } else {
            throw new PropuestaYaColaboradaException(nickColab + " ya tiene una colaboración con " + tituloProp);
        }
    }

    @Override
    public String obtenerDineroRecaudado(String tituloProp) {
        List<Float> aux;
        Float resultado = 0f;
        String query = "SELECT c.monto FROM Colaboracion c WHERE c.propuestaColaborada.titulo = :tituloProp";
        try {
            aux = em.createQuery(query, Float.class).setParameter("tituloProp", tituloProp).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        for (Float actual : aux) {
            resultado += actual;
        }
        return resultado.toString();

    }

    @Override
    public ArrayList<String> obtenerColaboradoresColaboracion(String tituloProp) {
        List<String> aux;
        String query = "SELECT c.colaborador.nickname FROM Colaboracion c WHERE c.propuestaColaborada.titulo = :tituloProp";
        try {
            aux = em.createQuery(query, String.class).setParameter("tituloProp", tituloProp).getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
            e.printStackTrace();

        }
        return new ArrayList<>(aux);
    }

    @Override
    public ArrayList<String> obtenerPropuestasColaboradas(String nick) {

        List<String> aux;

        String query = "SELECT c.propuestaColaborada.titulo FROM Colaboracion c"
                + " WHERE c.colaborador.nickname = :nick";
        try {
            aux = em.createQuery(query, String.class)
                    .setParameter("nick", nick)
                    .getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
        }

        return new ArrayList<>(aux);
    }

    @Override
    public void dejarDeSeguirUsuario(String nickSegui, String nickSiguiendo) {
        List<Usuario> usu1 = emr.obtenerUsuario(nickSegui);

        List<Usuario> usu2 = emr.obtenerUsuario(nickSiguiendo);

        List<Usuario> aux = usu1.get(0).getUsuariosSeguidos();
        aux.remove(usu2.get(0));
        usu1.get(0).setUsuariosSeguidos(aux);
        emr.add(usu1.get(0));
    }

    @Override
    public DTColaboracion obtenerDTColaboracion(Long id) {
        try {
            Colaboracion c = em.find(Colaboracion.class, id);
            if (c != null) {
                return new DTColaboracion(
                        c.getId(), c.getMonto(), c.getFechaHora(),
                        c.getTipoRetorno(), c.getColaborador().getNickname(), c.getPropuestaColaborada().getTitulo());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public ArrayList<String> listarColaboracionesColaborador(String nickColab) {
        List<Object[]> aux;
        List<String> aux2 = new ArrayList<>();

        String query = "SELECT c.propuestaColaborada.titulo, c.id FROM Colaboracion c"
                + " WHERE c.colaborador.nickname = :nick";
        try {
            aux = em.createQuery(query, Object[].class)
                    .setParameter("nick", nickColab)
                    .getResultList();
            for (Object[] fila : aux) {
                aux2.add(fila[0] + " - " + fila[1].toString());
            }
        } catch (Exception e) {
            aux2 = Collections.emptyList();
        }
        return (ArrayList<String>) aux2;
    }

    @Override
    public ArrayList<String> listarColaboraciones() {
        List<Object[]> aux;
        List<String> aux2 = new ArrayList<>();

        String query = "SELECT c.propuestaColaborada.titulo, c.id FROM Colaboracion c";
        try {
            aux = em.createQuery(query, Object[].class).getResultList();
            for (Object[] fila : aux) {
                aux2.add(fila[0] + " - " + fila[1].toString());
            }
        } catch (Exception e) {
            aux2 = Collections.emptyList();
        }
        return (ArrayList<String>) aux2;
    }

    @Override
    public void eliminarColaboracion(Long id) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            Colaboracion c = em.find(Colaboracion.class, id);
            if (c != null) {
                if (c.getColaborador() != null) {
                    c.getColaborador().getColaboraciones().remove(c);
                    c.setColaborador(null);
                }
                if (c.getPropuestaColaborada() != null) {
                    c.getPropuestaColaborada().getColaboraciones().remove(c);
                    c.setPropuestaColaborada(null);
                }
                em.remove(c);
                em.flush();
            }
            t.commit();
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
        }
    }

}
