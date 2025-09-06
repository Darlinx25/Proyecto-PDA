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
        
        usu.add(new DTProponente(new DTDireccion("Montevideo", " Colonia", 4321),"Martín Buscaglia (Montevideo, 1972) es un artista, músico, compositor y productor uruguayo. Tanto con su banda" +
        "(“Los Bochamakers”) como en su formato “Hombre orquesta”, o solo con su guitarra, ha recorrido el mundo" +
        "tocando entre otros países en España, Estados Unidos, Inglaterra, Francia, Australia, Brasil, Colombia, Argentina," +
        "Chile, Paraguay, México y Uruguay. (Actualmente los Bochamakers son Matías Rada, Martín Ibarburu, Mateo" +
        "Moreno, Herman Klang) Paralelamente, tiene proyectos a dúo con el español Kiko Veneno, la cubana Yusa, el" +
        "argentino Lisandro Aristimuño, su compatriota Antolín, y a trío junto a los brasileros Os Mulheres Negras.",
        "http://www.martinbuscaglia.com/","mbusca","Martín","Buscaglia","Martin.bus@agadu.org.uy",LocalDate.of(1972, 6, 14),"../imagenesRespaldoBD/MBimg.jpg"));
        
        usu.add(new DTProponente(new DTDireccion("Montevideo", " Gral. Flores", 5645),"En 1972 ingresó a la Escuela de Arte Dramático del teatro El Galpón. Participó en más de treinta obras teatrales y" +
        "varios largometrajes. Integró el elenco estable de Radioteatro del Sodre, y en 2006 fue asesor de su Consejo" +
        "Directivo. Como actor recibió múltiples reconocimientos: cuatro premios Florencio, premio al mejor actor" +
        "extranjero del Festival de Miami y premio Mejor Actor de Cine 2008. Durante varios períodos fue directivo del" +
        "teatro El Galpón y dirigente de la Sociedad Uruguaya de Actores (SUA); integró también la Federación Uruguaya" +
        "de Teatros Independientes (FUTI). Formó parte del equipo de gestión de la refacción de los teatros La Máscara," +
        "Astral y El Galpón, y del equipo de gestión en la construcción del teatro De la Candela y de la sala Atahualpa de El" +
        "Galpón.",
        "http://www.martinbuscaglia.com/","hectorg","Héctor","Guido","hector.gui@elgalpon.org.uy",LocalDate.of(1954, 1, 7),"../imagenesRespaldoBD/HGimg.jpg"));
        
        usu.add(new DTProponente(new DTDireccion("Montevideo", " Santiago Rivas", 1212),"Tabaré Cardozo (Montevideo, 24 de julio de 1971) es un cantante, compositor y murguista uruguayo; conocido por" +
        "su participación en la murga Agarrate Catalina, conjunto que fundó junto a su hermano Yamandú y Carlos" +
        "Tanco en el año 2001.",
        "https://www.facebook.com/Tabar%C3%A9-\nCardozo-55179094281/?ref=br_rs","tabarec","Tabaré","Cardozo","tabare.car@agadu.org.uy",LocalDate.of(1971, 7, 24),"../imagenesRespaldoBD/TCimg.jpg"));
        
        usu.add(new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567),"Nace en el año 1947 en el conventillo \"Medio Mundo\" ubicado en pleno Barrio Sur. Es heredero parcialmente-" +
        "junto al resto de sus hermanos- de la Comparsa \"Morenada\" (inactiva desde el fallecimiento de Juan Ángel Silva)," +
        "en 1999 forma su propia Comparsa de negros y lubolos \"Cuareim 1080\". Director responsable, compositor y" +
        "cantante de la misma.",
        "https://www.facebook.com/C1080?ref=br_rs","cachilas","Waldemar “Cachila”","Silva","Cachila.sil@c1080.org.uy",LocalDate.of(1947, 1, 1),"../imagenesRespaldoBD/CSimg.jpg"));
        
        usu.add(new DTProponente(new DTDireccion("Montevideo", " Benito Blanco", 4321),"",
        "","juliob","Julio","Bocca","juliobocca@sodre.com.uy",LocalDate.of(1967, 3, 16),"../imagenesRespaldoBD/JBimg.jpg"));
        
        usu.add(new DTProponente(new DTDireccion("Montevideo", " Emilio Frugoni Ap. 02", 1138),"",
        "http://www.efectocine.com","diegop","Diego","Parodi","diego@efectocine.com",LocalDate.of(1975, 1, 1),"../imagenesRespaldoBD/DPimg.jpg"));
        
        usu.add(new DTProponente(new DTDireccion("Montevideo", " Paraguay", 1423),"",
        "","kairoh","Kairo","Herrera","kairoher@pilsenrock.com.uy",LocalDate.of(1840, 4, 25),"../imagenesRespaldoBD/KHimg.jpg"));
        
        usu.add(new DTProponente(new DTDireccion("Montevideo", " 8 de Octubre", 1429),"Queremos ser vistos y reconocidos como una organización: referente en divulgación científica con un fuerte" +
        "espíritu didáctico y divertido, a través de acciones coordinadas con otros divulgadores científicos, que permitan" +
        "establecer puentes de comunicación. Impulsora en la generación de espacios de democratización y apropiación" +
        "social del conocimiento científico.",
        "https://bardocientifico.com/","losBardo","Los","Bardo","losbardo@bardocientifico.com",LocalDate.of(1980, 10, 31),"../imagenesRespaldoBD/LBimg.jpg"));
        
        return usu;
    }
    private List<DTUsuario> obtenerColaboradoresPrueba() {
        List<DTUsuario> usu = new ArrayList<>();
        usu.add(new DTColaborador("robinh", "Robin", "Henderson", "Robin.h@tinglesa.com.uy", LocalDate.of(1940, 8, 3), "../imagenesRespaldoBD/RHimg.jpg"));
        
        usu.add(new DTColaborador("marcelot", "Marcelo", "Tinelli", "marcelot@ideasdelsur.com.ar", LocalDate.of(1960, 4, 1), "../imagenesRespaldoBD/MTimg.jpg"));
        
        usu.add(new DTColaborador("novick", "Edgardo", "Novick", "edgardo@novick.com.uy", LocalDate.of(1952, 7, 17), "../imagenesRespaldoBD/ENimg.jpg"));
        
        usu.add(new DTColaborador("sergiop", "Sergio", "Puglia", "puglia@alpanpan.com.uy", LocalDate.of(1950, 1, 28), "../imagenesRespaldoBD/SPimg.jpg"));
        
        usu.add(new DTColaborador("chino", "Alvaro", "Recoba", "chino@trico.org.uy", LocalDate.of(1976, 3, 17), "../imagenesRespaldoBD/ARimg.jpg"));
        
        usu.add(new DTColaborador("tonyp", "Antonio", "Pacheco", "eltony@manya.org.uy", LocalDate.of(1955, 2, 14), "../imagenesRespaldoBD/APimg.jpg"));
        
        usu.add(new DTColaborador("nicoJ", "Nicolás", "Jodal", "jodal@artech.com.uy", LocalDate.of(1960, 8, 9), "../imagenesRespaldoBD/NJimg.jpg"));
        
        usu.add(new DTColaborador("juanP", "Juan", "Perez", "juanp@elpueblo.com", LocalDate.of(1970, 1, 1), "../imagenesRespaldoBD/JPimg.jpg"));
        
        usu.add(new DTColaborador("Mengano", "Mengano", "Gómez", "menganog@elpueblo.com", LocalDate.of(1982, 2, 2), "../imagenesRespaldoBD/MGimg.jpg"));
        
        usu.add(new DTColaborador("Perengano", "Perengano", "López", "pere@elpueblo.com", LocalDate.of(1985, 3, 3), "../imagenesRespaldoBD/PLimg.jpg"));
        
        usu.add(new DTColaborador("Tiajaci", "Tía", "Jacinta", "jacinta@elpueblo.com", LocalDate.of(1990, 4, 4), "../imagenesRespaldoBD/TJimg.jpg"));
        
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
