/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import culturarte.excepciones.BadPasswordException;
import culturarte.excepciones.CategoriaDuplicadaException;
import culturarte.excepciones.EmailRepetidoException;
import culturarte.excepciones.NickRepetidoException;
import culturarte.excepciones.PropuestaDuplicadaException;
import culturarte.excepciones.PropuestaYaColaboradaException;
import jakarta.persistence.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collections;

import java.util.List;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mark
 */
//SINGLETON
public class Controller implements IController {

    private static Controller instancia;
    private Manejador emr = Manejador.getInstance();

    private Controller() {

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

    // <editor-fold defaultstate="collapsed" desc="Cargar datos de prueba.">
    @Override
    public void cargarDatosPrueba() throws NickRepetidoException, EmailRepetidoException, PropuestaDuplicadaException, CategoriaDuplicadaException, BadPasswordException {
        cargarUsuariosPrueba();
        cargarSeguidoresPrueba();
        cargarCategoriasPrueba();
        cargarPropuestasPrueba();
        cargarColaboracionesPrueba();
    }

    private void cargarColaboracionesPrueba() {
        try {
            realizarColaboracionPaPrueba("novick", "Cine en el Botánico", LocalDateTime.of(2017, 5, 20, 14, 30), 50000, "Porcentaje de ganancias");
            realizarColaboracionPaPrueba("robinh", "Cine en el Botánico", LocalDateTime.of(2017, 5, 24, 17, 25), 50000, "Porcentaje de ganancias");
            realizarColaboracionPaPrueba("nicoJ", "Cine en el Botánico", LocalDateTime.of(2017, 5, 30, 18, 30), 50000, "Porcentaje de ganancias");

            realizarColaboracionPaPrueba("marcelot", "Religiosamente", LocalDateTime.of(2017, 6, 30, 14, 25), 200000, "Porcentaje de ganancias");
            realizarColaboracionPaPrueba("Tiajaci", "Religiosamente", LocalDateTime.of(2017, 7, 1, 18, 5), 500, "Entradas gratis");
            realizarColaboracionPaPrueba("Mengano", "Religiosamente", LocalDateTime.of(2017, 7, 7, 17, 45), 600, "Entradas gratis");
            realizarColaboracionPaPrueba("novick", "Religiosamente", LocalDateTime.of(2017, 7, 10, 14, 35), 50000, "Porcentaje de ganancias");
            realizarColaboracionPaPrueba("sergiop", "Religiosamente", LocalDateTime.of(2017, 7, 15, 9, 45), 50000, "Porcentaje de ganancias");

            realizarColaboracionPaPrueba("marcelot", "El Pimiento Indomable", LocalDateTime.of(2017, 8, 1, 7, 40), 200000, "Porcentaje de ganancias");
            realizarColaboracionPaPrueba("sergiop", "El Pimiento Indomable", LocalDateTime.of(2017, 8, 3, 9, 25), 80000, "Porcentaje de ganancias");

            realizarColaboracionPaPrueba("chino", "Pilsen Rock", LocalDateTime.of(2017, 8, 5, 16, 50), 50000, "Entradas gratis");
            realizarColaboracionPaPrueba("novick", "Pilsen Rock", LocalDateTime.of(2017, 8, 10, 15, 50), 120000, "Porcentaje de ganancias");
            realizarColaboracionPaPrueba("tonyp", "Pilsen Rock", LocalDateTime.of(2017, 8, 15, 19, 30), 120000, "Entradas gratis");

            realizarColaboracionPaPrueba("sergiop", "Romeo y Julieta", LocalDateTime.of(2017, 8, 13, 4, 58), 100000, "Porcentaje de ganancias");
            realizarColaboracionPaPrueba("marcelot", "Romeo y Julieta", LocalDateTime.of(2017, 8, 14, 11, 25), 200000, "Porcentaje de ganancias");

            realizarColaboracionPaPrueba("tonyp", "Un día de Julio", LocalDateTime.of(2017, 8, 15, 4, 48), 30000, "Entradas gratis");
            realizarColaboracionPaPrueba("marcelot", "Un día de Julio", LocalDateTime.of(2017, 8, 17, 15, 30), 150000, "Porcentaje de ganancias");
        } catch (PropuestaYaColaboradaException ex) {
            System.getLogger(Controller.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
    }

    private void realizarColaboracionPaPrueba(String nickColab, String tituloProp, LocalDateTime fecha, float montoColab, String tipoRetorno) throws PropuestaYaColaboradaException {
        Colaborador colab = emr.find(Colaborador.class, nickColab);
        Propuesta prop = emr.find(Propuesta.class, tituloProp);
        List<String> aux = new ArrayList<>();

        emr.propuestaColaboradaPorUser(nickColab, tituloProp);

        if (colab != null && prop != null && aux.isEmpty()) {
            Colaboracion colaboracion = new Colaboracion(montoColab, tipoRetorno, colab, prop);
            colaboracion.setFechaHora(fecha);

            emr.add(colaboracion);
        } else {
            throw new PropuestaYaColaboradaException(nickColab + " ya tiene una colaboración con " + tituloProp);
        }
    }

    private void cargarPropuestasPrueba() throws PropuestaDuplicadaException {
        List<TipoRetorno> porcentaje = new ArrayList<>();
        porcentaje.add(TipoRetorno.PORCENTAJE_GANANCIAS);
        List<TipoRetorno> entrada = new ArrayList<>();
        entrada.add(TipoRetorno.ENTRADA_GRATIS);
        List<TipoRetorno> entPor = new ArrayList<>();
        entPor.add(TipoRetorno.PORCENTAJE_GANANCIAS);
        entPor.add(TipoRetorno.ENTRADA_GRATIS);
        Propuesta aux;
        List<Estado> listAux;
        DTPropuesta prop;
        prop = new DTPropuesta("Cine en el Botánico",
                "El 16 de Diciembre a la hora 20 se proyectará la película \"Clever\", en el Jardín Botánico (Av. 19 de Abril 1181) en el marco"
                + " de las actividades realizadas por el ciclo Cultura al Aire Libre. El largometraje uruguayo de ficción Clever es dirigido por "
                + "Federico Borgia y Guillermo Madeiro. Es apto para mayores de 15 años.", "CEBimg.jpg", "Jardín Botánico",
                LocalDate.of(2017, 9, 16), 200, 150000, "Cine al Aire Libre", "diegop", porcentaje, new Estado(EstadoPropuesta.CANCELADA, LocalDateTime.of(2017, 6, 15, 14, 50)));
        addPropuesta(prop);
        aux = emr.find(Propuesta.class, "Cine en el Botánico");
        listAux = new ArrayList<>();
        listAux.add(new Estado(EstadoPropuesta.INGRESADA, LocalDateTime.of(2017, 5, 15, 15, 30)));
        listAux.add(new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2017, 5, 17, 8, 30)));
        listAux.add(new Estado(EstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2017, 5, 20, 14, 30)));
        listAux.add(new Estado(EstadoPropuesta.FINANCIADA, LocalDateTime.of(2017, 5, 30, 18, 30)));
        aux.setHistorialEstados(listAux);

        prop = new DTPropuesta("Religiosamente",
                "MOMOSAPIENS presenta \"Religiosamente\". Mediante dos parodias y un hilo conductor que aborda la temática de la "
                + "religión Momosapiens, mediante el humor y la reflexión, hilvana una historia que muestra al hombre inmerso en el tema "
                + "religioso. El libreto está escrito utilizando diferentes lenguajes de humor, dando una visión satírica y reflexiva desde "
                + "distintos puntos de vista, logrando mediante situaciones paródicas armar una propuesta plena de arte carnavalero.", "MOMimg.jpg", "Teatro de Verano",
                LocalDate.of(2017, 10, 7), 300, 300000, "Parodistas", "hrubino", entPor, new Estado(EstadoPropuesta.FINANCIADA, LocalDateTime.of(2017, 7, 15, 9, 45)));
        addPropuesta(prop);
        aux = emr.find(Propuesta.class, "Religiosamente");
        listAux = new ArrayList<>();
        listAux.add(new Estado(EstadoPropuesta.INGRESADA, LocalDateTime.of(2017, 6, 18, 4, 28)));
        listAux.add(new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2017, 6, 20, 4, 56)));
        listAux.add(new Estado(EstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2017, 6, 30, 14, 25)));
        aux.setHistorialEstados(listAux);

        prop = new DTPropuesta("El Pimiento Indomable",
                "El Pimiento Indomable, formación compuesta por Kiko Veneno y el uruguayo Martín Buscaglia, presentará este 19 de "
                + "Octubre, su primer trabajo. Bajo un título homónimo al del grupo, es un disco que según los propios protagonistas “no se "
                + "parece al de ninguno de los dos por separado. Entre los títulos que se podrán escuchar se encuentran “Nadador salvador”, "
                + "“América es más grande”, “Pescaito Enroscado” o “La reina del placer”.", "PIMimg.jpg", "Teatro Solís",
                LocalDate.of(2017, 10, 19), 400, 400000, "Concierto", "mbusca", porcentaje, new Estado(EstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2017, 8, 1, 7, 40)));
        addPropuesta(prop);
        aux = emr.find(Propuesta.class, "El Pimiento Indomable");
        listAux = new ArrayList<>();
        listAux.add(new Estado(EstadoPropuesta.INGRESADA, LocalDateTime.of(2017, 7, 26, 15, 30)));
        listAux.add(new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2017, 7, 31, 8, 30)));
        aux.setHistorialEstados(listAux);

        prop = new DTPropuesta("Pilsen Rock",
                "La edición 2017 del Pilsen Rock se celebrará el 21 de Octubre en la Rural del Prado y contará con la participación de más "
                + "de 15 bandas nacionales. Quienes no puedan trasladarse al lugar, tendrán la posibilidad de disfrutar los shows a través de "
                + "Internet, así como entrevistas en vivo a los músicos una vez finalizados los conciertos.", "PILimg.jpg", "Rural de Prado",
                LocalDate.of(2017, 10, 21), 1000, 900000, "Festival", "kairoh", entPor, new Estado(EstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2017, 8, 5, 16, 50)));
        addPropuesta(prop);
        aux = emr.find(Propuesta.class, "Pilsen Rock");
        listAux = new ArrayList<>();
        listAux.add(new Estado(EstadoPropuesta.INGRESADA, LocalDateTime.of(2017, 7, 30, 15, 40)));
        listAux.add(new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2017, 8, 1, 14, 30)));
        aux.setHistorialEstados(listAux);

        prop = new DTPropuesta("Romeo y Julieta",
                "Romeo y Julieta de Kenneth MacMillan, uno de los ballets favoritos del director artístico Julio Bocca, se presentará "
                + "nuevamente el 5 de Noviembre en el Auditorio Nacional del Sodre. Basada en la obra homónima de William Shakespeare, "
                + "Romeo y Julieta es considerada la coreografía maestra del MacMillan. La producción de vestuario y escenografía se realizó "
                + "en los Talleres del Auditorio Adela Reta, sobre los diseños originales.", "RYJimg.jpg", "Auditorio Nacional del Sodre",
                LocalDate.of(2017, 11, 5), 800, 750000, "Ballet", "juliob", porcentaje, new Estado(EstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2017, 8, 5, 16, 50)));
        addPropuesta(prop);
        aux = emr.find(Propuesta.class, "Romeo y Julieta");
        listAux = new ArrayList<>();
        listAux.add(new Estado(EstadoPropuesta.INGRESADA, LocalDateTime.of(2017, 8, 4, 12, 20)));
        listAux.add(new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2017, 8, 10, 10, 25)));
        aux.setHistorialEstados(listAux);

        prop = new DTPropuesta("Un día de Julio",
                "La Catalina presenta el espectáculo \"Un Día de Julio\" en Landia. Un hombre misterioso y solitario vive encerrado entre las "
                + "cuatro paredes de su casa. Intenta, con sus teorías extravagantes, cambiar el mundo exterior que le resulta inhabitable. Un "
                + "día de Julio sucederá algo que cambiará su vida y la de su entorno para siempre.", "UDJimg.jpg", "Landia",
                LocalDate.of(2017, 11, 16), 650, 300000, "Murga", "tabarec", entPor, new Estado(EstadoPropuesta.EN_FINANCIACION, LocalDateTime.of(2017, 8, 15, 4, 48)));
        addPropuesta(prop);
        aux = emr.find(Propuesta.class, "Un día de Julio");
        listAux = new ArrayList<>();
        listAux.add(new Estado(EstadoPropuesta.INGRESADA, LocalDateTime.of(2017, 8, 6, 2, 0)));
        listAux.add(new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2017, 8, 12, 4, 50)));
        aux.setHistorialEstados(listAux);

        prop = new DTPropuesta("El Lazarillo de Tormes",
                "Vuelve unas de las producciones de El Galpón más aclamadas de los últimos tiempos. Esta obra se ha presentado en "
                + "Miami, Nueva York, Washington, México, Guadalajara, Río de Janeiro y La Habana. En nuestro país, El Lazarillo de "
                + "Tormes fue nominado en los rubros mejor espectáculo y mejor dirección a los Premios Florencio 1995, obteniendo su "
                + "protagonista Héctor Guido el Florencio a Mejor actor de ese año.", "LDTimg.jpg", "Teatro el Galpón",
                LocalDate.of(2017, 12, 3), 350, 175000, "Teatro Dramático", "hectorg", entrada, new Estado(EstadoPropuesta.PUBLICADA, LocalDateTime.of(2017, 8, 20, 21, 58)));
        addPropuesta(prop);
        aux = emr.find(Propuesta.class, "El Lazarillo de Tormes");
        listAux = new ArrayList<>();
        listAux.add(new Estado(EstadoPropuesta.INGRESADA, LocalDateTime.of(2017, 8, 18, 2, 40)));
        aux.setHistorialEstados(listAux);

        prop = new DTPropuesta("Bardo en la FING",
                "El 10 de Diciembre se presentará Bardo Científico en la FING. El humor puede ser usado como una herramienta "
                + "importante para el aprendizaje y la democratización de la ciencia, los monólogos científicos son una forma didáctica de "
                + "apropiación del conocimiento científico y contribuyen a que el público aprenda ciencia de forma amena. Los invitamos a "
                + "pasar un rato divertido, en un espacio en el cual aprenderán cosas de la ciencia que los sorprenderán. ¡Los esperamos!", "BEFimg.jpg", "Anfiteatro Edificio \"José Luis Massera\"",
                LocalDate.of(2017, 12, 10), 200, 100000, "Stand-up", "losBardo", entrada, new Estado(EstadoPropuesta.INGRESADA, LocalDateTime.of(2017, 8, 23, 2, 12)));
        addPropuesta(prop);
    }

    private void cargarCategoriasPrueba() throws CategoriaDuplicadaException {
        addCategoria("Teatro", "Categorías");
        addCategoria("Teatro Dramático", "Teatro");
        addCategoria("Teatro Musical", "Teatro");
        addCategoria("Comedia", "Teatro");
        addCategoria("Stand-up", "Comedia");

        addCategoria("Literatura", "Categorías");

        addCategoria("Música", "Categorías");
        addCategoria("Festival", "Música");
        addCategoria("Concierto", "Música");

        addCategoria("Cine", "Categorías");
        addCategoria("Cine al Aire Libre", "Cine");
        addCategoria("Cine a Pedal", "Cine");

        addCategoria("Danza", "Categorías");
        addCategoria("Ballet", "Danza");
        addCategoria("Flamenco", "Danza");

        addCategoria("Carnaval", "Categorías");
        addCategoria("Murga", "Carnaval");
        addCategoria("Humoristas", "Carnaval");
        addCategoria("Parodistas", "Carnaval");
        addCategoria("Lubolos", "Carnaval");
        addCategoria("Revista", "Carnaval");
    }

    private void cargarSeguidoresPrueba() {
        seguirUsuario("hrubino", "hectorg");
        seguirUsuario("hrubino", "diegop");
        seguirUsuario("hrubino", "losBardo");

        seguirUsuario("mbusca", "tabarec");
        seguirUsuario("mbusca", "cachilas");
        seguirUsuario("mbusca", "kairoh");

        seguirUsuario("hectorg", "mbusca");
        seguirUsuario("hectorg", "juliob");

        seguirUsuario("tabarec", "hrubino");
        seguirUsuario("tabarec", "cachilas");

        seguirUsuario("cachilas", "hrubino");

        seguirUsuario("juliob", "mbusca");
        seguirUsuario("juliob", "diegop");

        seguirUsuario("diegop", "hectorg");
        seguirUsuario("diegop", "losBardo");

        seguirUsuario("kairoh", "sergiop");

        seguirUsuario("losBardo", "hrubino");
        seguirUsuario("losBardo", "nicoJ");

        seguirUsuario("robinh", "hectorg");
        seguirUsuario("robinh", "juliob");
        seguirUsuario("robinh", "diegop");

        seguirUsuario("marcelot", "cachilas");
        seguirUsuario("marcelot", "juliob");
        seguirUsuario("marcelot", "kairoh");

        seguirUsuario("novick", "hrubino");
        seguirUsuario("novick", "tabarec");
        seguirUsuario("novick", "cachilas");

        seguirUsuario("sergiop", "mbusca");
        seguirUsuario("sergiop", "juliob");
        seguirUsuario("sergiop", "diegop");

        seguirUsuario("chino", "tonyp");

        seguirUsuario("tonyp", "chino");

        seguirUsuario("nicoJ", "diegop");
        seguirUsuario("nicoJ", "losBardo");

        seguirUsuario("juanP", "tabarec");
        seguirUsuario("juanP", "cachilas");
        seguirUsuario("juanP", "kairoh");

        seguirUsuario("Mengano", "hectorg");
        seguirUsuario("Mengano", "juliob");
        seguirUsuario("Mengano", "chino");

        seguirUsuario("Perengano", "diegop");
        seguirUsuario("Perengano", "tonyp");

        seguirUsuario("Tiajaci", "juliob");
        seguirUsuario("Tiajaci", "kairoh");
        seguirUsuario("Tiajaci", "sergiop");
    }

    private void cargarUsuariosPrueba() throws NickRepetidoException, EmailRepetidoException, BadPasswordException {
        List<DTUsuario> usu = obtenerProponentesPrueba();
        usu.addAll(obtenerColaboradoresPrueba());

        for (DTUsuario u : usu) {
            addUsuario(u);
        }
    }

    private List<DTUsuario> obtenerProponentesPrueba() {
        List<DTUsuario> usu = new ArrayList<>();
        usu.add(new DTProponente(new DTDireccion("Montevideo", " 18 de Julio", 1234), "Horacio Rubino Torres nace el 25 de febrero de 1962, es conductor, actor y libretista. Debuta en 1982 en carnaval\n"
                + "en Los \"Klaper´s\", donde estuvo cuatro años, actuando y libretando. Luego para \"Gaby´s\" (6 años), escribió en\n"
                + "categoría revistas y humoristas y desde el comienzo y hasta el presente en su propio conjunto Momosapiens.",
                "https://twitter.com/horaciorubino", "hrubino", "Horacio", "Rubino", "12345678".toCharArray(), "12345678".toCharArray(), "horacio.rubino@guambia.com.uy", LocalDate.of(1980, 5, 20), "HRimg.jpg"));

        usu.add(new DTProponente(new DTDireccion("Montevideo", " Colonia", 4321), "Martín Buscaglia (Montevideo, 1972) es un artista, músico, compositor y productor uruguayo. Tanto con su banda"
                + "(“Los Bochamakers”) como en su formato “Hombre orquesta”, o solo con su guitarra, ha recorrido el mundo"
                + "tocando entre otros países en España, Estados Unidos, Inglaterra, Francia, Australia, Brasil, Colombia, Argentina,"
                + "Chile, Paraguay, México y Uruguay. (Actualmente los Bochamakers son Matías Rada, Martín Ibarburu, Mateo"
                + "Moreno, Herman Klang) Paralelamente, tiene proyectos a dúo con el español Kiko Veneno, la cubana Yusa, el"
                + "argentino Lisandro Aristimuño, su compatriota Antolín, y a trío junto a los brasileros Os Mulheres Negras.",
                "http://www.martinbuscaglia.com/", "mbusca", "Martín", "Buscaglia", "12345678".toCharArray(), "12345678".toCharArray(), "Martin.bus@agadu.org.uy", LocalDate.of(1972, 6, 14), "MBimg.jpg"));

        usu.add(new DTProponente(new DTDireccion("Montevideo", " Gral. Flores", 5645), "En 1972 ingresó a la Escuela de Arte Dramático del teatro El Galpón. Participó en más de treinta obras teatrales y"
                + "varios largometrajes. Integró el elenco estable de Radioteatro del Sodre, y en 2006 fue asesor de su Consejo"
                + "Directivo. Como actor recibió múltiples reconocimientos: cuatro premios Florencio, premio al mejor actor"
                + "extranjero del Festival de Miami y premio Mejor Actor de Cine 2008. Durante varios períodos fue directivo del"
                + "teatro El Galpón y dirigente de la Sociedad Uruguaya de Actores (SUA); integró también la Federación Uruguaya"
                + "de Teatros Independientes (FUTI). Formó parte del equipo de gestión de la refacción de los teatros La Máscara,"
                + "Astral y El Galpón, y del equipo de gestión en la construcción del teatro De la Candela y de la sala Atahualpa de El"
                + "Galpón.",
                "http://www.martinbuscaglia.com/", "hectorg", "Héctor", "Guido", "12345678".toCharArray(), "12345678".toCharArray(), "hector.gui@elgalpon.org.uy", LocalDate.of(1954, 1, 7), "HGimg.jpg"));

        usu.add(new DTProponente(new DTDireccion("Montevideo", " Santiago Rivas", 1212), "Tabaré Cardozo (Montevideo, 24 de julio de 1971) es un cantante, compositor y murguista uruguayo; conocido por"
                + "su participación en la murga Agarrate Catalina, conjunto que fundó junto a su hermano Yamandú y Carlos"
                + "Tanco en el año 2001.",
                "https://www.facebook.com/Tabar%C3%A9-\nCardozo-55179094281/?ref=br_rs", "tabarec", "Tabaré", "Cardozo", "12345678".toCharArray(), "12345678".toCharArray(), "tabare.car@agadu.org.uy", LocalDate.of(1971, 7, 24), "TCimg.jpg"));

        usu.add(new DTProponente(new DTDireccion("Montevideo", " Br. Artigas", 4567), "Nace en el año 1947 en el conventillo \"Medio Mundo\" ubicado en pleno Barrio Sur. Es heredero parcialmente-"
                + "junto al resto de sus hermanos- de la Comparsa \"Morenada\" (inactiva desde el fallecimiento de Juan Ángel Silva),"
                + "en 1999 forma su propia Comparsa de negros y lubolos \"Cuareim 1080\". Director responsable, compositor y"
                + "cantante de la misma.",
                "https://www.facebook.com/C1080?ref=br_rs", "cachilas", "Waldemar “Cachila”", "Silva", "12345678".toCharArray(), "12345678".toCharArray(), "Cachila.sil@c1080.org.uy", LocalDate.of(1947, 1, 1), "CSimg.jpg"));

        usu.add(new DTProponente(new DTDireccion("Montevideo", " Benito Blanco", 4321), "",
                "", "juliob", "Julio", "Bocca", "12345678".toCharArray(), "12345678".toCharArray(), "juliobocca@sodre.com.uy", LocalDate.of(1967, 3, 16), "JBimg.jpg"));

        usu.add(new DTProponente(new DTDireccion("Montevideo", " Emilio Frugoni Ap. 02", 1138), "",
                "http://www.efectocine.com", "diegop", "Diego", "Parodi", "12345678".toCharArray(), "12345678".toCharArray(), "diego@efectocine.com", LocalDate.of(1975, 1, 1), "DPimg.jpg"));

        usu.add(new DTProponente(new DTDireccion("Montevideo", " Paraguay", 1423), "",
                "", "kairoh", "Kairo", "Herrera", "12345678".toCharArray(), "12345678".toCharArray(), "kairoher@pilsenrock.com.uy", LocalDate.of(1840, 4, 25), "KHimg.jpg"));

        usu.add(new DTProponente(new DTDireccion("Montevideo", " 8 de Octubre", 1429), "Queremos ser vistos y reconocidos como una organización: referente en divulgación científica con un fuerte"
                + "espíritu didáctico y divertido, a través de acciones coordinadas con otros divulgadores científicos, que permitan"
                + "establecer puentes de comunicación. Impulsora en la generación de espacios de democratización y apropiación"
                + "social del conocimiento científico.",
                "https://bardocientifico.com/", "losBardo", "Los", "Bardo", "12345678".toCharArray(), "12345678".toCharArray(), "losbardo@bardocientifico.com", LocalDate.of(1980, 10, 31), "LBimg.jpg"));

        return usu;
    }

    private List<DTUsuario> obtenerColaboradoresPrueba() {
        List<DTUsuario> usu = new ArrayList<>();
        usu.add(new DTColaborador("robinh", "Robin", "Henderson", "12345678".toCharArray(), "12345678".toCharArray(), "Robin.h@tinglesa.com.uy", LocalDate.of(1940, 8, 3), "RHimg.jpg"));

        usu.add(new DTColaborador("marcelot", "Marcelo", "Tinelli", "12345678".toCharArray(), "12345678".toCharArray(), "marcelot@ideasdelsur.com.ar", LocalDate.of(1960, 4, 1), "MTimg.jpg"));

        usu.add(new DTColaborador("novick", "Edgardo", "Novick", "12345678".toCharArray(), "12345678".toCharArray(), "edgardo@novick.com.uy", LocalDate.of(1952, 7, 17), "ENimg.jpg"));

        usu.add(new DTColaborador("sergiop", "Sergio", "Puglia", "12345678".toCharArray(), "12345678".toCharArray(), "puglia@alpanpan.com.uy", LocalDate.of(1950, 1, 28), "SPimg.jpg"));

        usu.add(new DTColaborador("chino", "Alvaro", "Recoba", "12345678".toCharArray(), "12345678".toCharArray(), "chino@trico.org.uy", LocalDate.of(1976, 3, 17), "ARimg.jpg"));

        usu.add(new DTColaborador("tonyp", "Antonio", "Pacheco", "12345678".toCharArray(), "12345678".toCharArray(), "eltony@manya.org.uy", LocalDate.of(1955, 2, 14), "APimg.jpg"));

        usu.add(new DTColaborador("nicoJ", "Nicolás", "Jodal", "12345678".toCharArray(), "12345678".toCharArray(), "jodal@artech.com.uy", LocalDate.of(1960, 8, 9), "NJimg.jpg"));

        usu.add(new DTColaborador("juanP", "Juan", "Perez", "12345678".toCharArray(), "12345678".toCharArray(), "juanp@elpueblo.com", LocalDate.of(1970, 1, 1), "JPimg.jpg"));

        usu.add(new DTColaborador("Mengano", "Mengano", "Gómez", "12345678".toCharArray(), "12345678".toCharArray(), "menganog@elpueblo.com", LocalDate.of(1982, 2, 2), "MGimg.jpg"));

        usu.add(new DTColaborador("Perengano", "Perengano", "López", "12345678".toCharArray(), "12345678".toCharArray(), "pere@elpueblo.com", LocalDate.of(1985, 3, 3), "PLimg.jpg"));

        usu.add(new DTColaborador("Tiajaci", "Tía", "Jacinta", "12345678".toCharArray(), "12345678".toCharArray(), "jacinta@elpueblo.com", LocalDate.of(1990, 4, 4), "TJimg.jpg"));

        return usu;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones usuarios.">
    @Override
    public void addUsuario(DTUsuario user) throws NickRepetidoException, EmailRepetidoException, BadPasswordException {
        String nick = user.getNickname();
        String email = user.getEmail();

        if (emr.datoUsuarioRepetido("nickname", nick) > 0) {
            throw new NickRepetidoException("Error al registrar usuario, Nick ya existente.");
        }
        if (emr.datoUsuarioRepetido("email", email) > 0) {
            throw new EmailRepetidoException("Error al registrar usuario, Email ya existente.");
        }
        validarPassword(user.getPassword(), user.getPasswordConfirm());
        
        String passwordSalt = crearPasswordSalt();
        String passwordHash;
        try {
            passwordHash = crearPasswordHash(passwordSalt, user.getPassword());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.getLogger(Controller.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            Arrays.fill(user.getPassword(), 'x');
            Arrays.fill(user.getPasswordConfirm(), 'x');
            return;
        }
        
        String nombre = user.getNombre();
        String apellido = user.getApellido();
        LocalDate fechaNac = user.getFechaNacimiento();
        String imagen = user.getImagen();
        Usuario usu = null;
        if (user instanceof DTColaborador) {
            usu = new Colaborador(nick, nombre, apellido, passwordSalt, passwordHash, email, fechaNac, imagen);
        } else if (user instanceof DTProponente) {
            DTProponente userProp = (DTProponente) user;//downcast
            DTDireccion direccion = userProp.getDireccion();
            String biografia = userProp.getBiografia();
            String sitioWeb = userProp.getSitioWeb();
            usu = new Proponente(direccion, biografia, sitioWeb, nick, nombre, apellido, passwordSalt, passwordHash, email, fechaNac, imagen);
        }

        emr.add(usu);
        
        Arrays.fill(user.getPassword(), 'x');
        Arrays.fill(user.getPasswordConfirm(), 'x');
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
    public ArrayList<String> obtenerPropuestasColaboradas(String nick) {
        return emr.propuestasColaboradas(nick);
    }
    
    @Override
    public ArrayList<String> listarProponentes() {
        List<String> aux = emr.listarAtributo(String.class, "nickname", "Proponente");
        return new ArrayList<>(aux);
    }
    
    @Override
    public DTProponente obtenerDTProponente(String nick) {
        try {
            Proponente p = emr.find(Proponente.class, nick); // Buscar proponente por PK
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
    public ArrayList<String> listarColaboracionesColaborador(String nickColab) {
        return emr.colaboracionesColaborador(nickColab);
    }
    
    @Override
    public ArrayList<String> listarUsuarios() {
        List<String> aux = emr.listarAtributo(String.class, "nickname", "Usuario");
        return new ArrayList<>(aux);
    }
    
    @Override
    public ResultadoSeguirUsuario seguirUsuario(String nickSegui, String nickUsu) {
        List<Usuario> usu1 = emr.obtenerUsuario(nickSegui);

        List<Usuario> usu2 = emr.obtenerUsuario(nickUsu);

        List<Usuario> aux = usu1.get(0).getUsuariosSeguidos();
        aux.add(usu2.get(0));
        usu1.get(0).setUsuariosSeguidos(aux);
        emr.add(usu1.get(0));
        return ResultadoSeguirUsuario.EXITO;
    }
    
    @Override
    public ResultadoSeguirUsuario dejarDeSeguirUsuario(String nickSegui, String nickSiguiendo) {
        List<Usuario> usu1 = emr.obtenerUsuario(nickSegui);

        List<Usuario> usu2 = emr.obtenerUsuario(nickSiguiendo);

        List<Usuario> aux = usu1.get(0).getUsuariosSeguidos();
        aux.remove(usu2.get(0));
        usu1.get(0).setUsuariosSeguidos(aux);
        emr.add(usu1.get(0));
        return ResultadoSeguirUsuario.EXITO;
    }
    
    @Override
    public ArrayList<String> listaPropuestasUsu(String nick) {

        return emr.listaPropuestasUsuario(nick);
    }
    
    @Override
    public ArrayList<String> listarUsuariosSeguir(String nickname) {
        return emr.obtenerUsuariosSeguir(nickname);
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones categorías.">
    @Override
    public DefaultMutableTreeNode listarCategorias() {
        Categoria catRaiz = emr.find(Categoria.class, "Categorías");
        return nodosArbolCategorias(catRaiz);
    }
    
    @Override
    public void addCategoria(String nombre, String nombrePadre) throws CategoriaDuplicadaException {
        Categoria existente = emr.find(Categoria.class, nombre);
        if (existente != null) {
            throw new CategoriaDuplicadaException("Ya existe una categoría con ese nombre");
        }
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
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones propuestas.">
    @Override
    public void addPropuesta(DTPropuesta prop) throws PropuestaDuplicadaException {
        String titulo = prop.getTitulo();
        if (emr.find(Propuesta.class, titulo) != null) {
            throw new PropuestaDuplicadaException("Ya existe una propuesta con ese titulo.");
        }
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
    public ArrayList<String> listarPropuestasEstado(int estado) {
        return emr.obtenerPropuestasEstado(estado);
    }
    
    @Override
    public String obtenerDineroRecaudado(String tituloProp) {
        List<Float> aux = emr.obtenerDinero(tituloProp);
        float resultado = 0f;
        if (aux.isEmpty()) {
            return "0";
        }
        for (Float actual : aux) {
            resultado += actual;
        }
        return String.valueOf(resultado);
    }
    
    @Override
    public ArrayList<String> listarPropuestasProponentes() {

        return emr.listPropuestasProponentes();
    }
    
    @Override
    public ArrayList<String> listarPropuestasProponentesIngresadas() {
        return emr.listPropuestasProponentesIngresadas();
    }
    
    @Override
    public void cambiarEstadoPropuestaIngresada(String tituloProp, EstadoPropuesta estProp) {
        Propuesta prop = emr.find(Propuesta.class, tituloProp);
        if (prop != null && prop.getEstadoActual().getEstado() == EstadoPropuesta.INGRESADA
                && (estProp == EstadoPropuesta.PUBLICADA || estProp == EstadoPropuesta.CANCELADA)) {
            Estado estadoNuevo = new Estado(estProp);
            prop.agregarEstadoActualAlHistorial();
            prop.setEstadoActual(estadoNuevo);
            if (estProp == EstadoPropuesta.PUBLICADA) {
                prop.setFechaPublicacion(estadoNuevo.getFechaEstado().toLocalDate());
            }
            emr.mod(prop);
        }
    }
    @Override
      public void cambiarEstadoPropuesta(String tituloProp, EstadoPropuesta estProp) {
        Propuesta prop = emr.find(Propuesta.class, tituloProp);
        if (prop != null){
            Estado estadoNuevo = new Estado(estProp);
            prop.agregarEstadoActualAlHistorial();
            prop.setEstadoActual(estadoNuevo);
            if (estProp == EstadoPropuesta.PUBLICADA) {
                prop.setFechaPublicacion(estadoNuevo.getFechaEstado().toLocalDate());
            }
            emr.mod(prop);
        }
    }
    
    @Override
    public ArrayList<String> listarPropuestas() {
        List<String> aux = emr.listarAtributo(String.class, "titulo", "Propuesta");
        return new ArrayList<>(aux);
    }
    
    @Override
    public void modPropuesta(DTPropuesta prop) {
        String titulo = prop.getTitulo();

        Propuesta aux = null;
        Categoria cat = null;
        try {
            cat = emr.find(Categoria.class, prop.getTipoPropuesta());
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
        if (aux.getEstadoActual().getEstado() != prop.getEstadoActual().getEstado()) {
            aux.agregarEstadoActualAlHistorial();
            aux.setEstadoActual(prop.getEstadoActual());
        }
        aux.setLugarRealizara(prop.getLugarRealizara());
        aux.setFechaRealizara(prop.getFechaRealizara());
        aux.setPrecioEntrada(prop.getPrecioEntrada());
        aux.setMontoAReunir(prop.getMontoAReunir());
        aux.setTiposRetorno(prop.getTiposRetorno());
        aux.setTipoPropuesta(cat);
        aux.setEstadoActual(prop.getEstadoActual());
        emr.mod(aux);
    }
    
    
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones colaboraciones.">
    @Override
    public void realizarColaboracion(String nickColab, String tituloProp, float montoColab, String tipoRetorno) throws PropuestaYaColaboradaException {
        Colaborador colab = emr.find(Colaborador.class, nickColab);
        Propuesta prop = emr.find(Propuesta.class, tituloProp);
        List<String> aux = new ArrayList<>();

        emr.propuestaColaboradaPorUser(nickColab, tituloProp);

        if (colab != null && prop != null && aux.isEmpty()) {
            Colaboracion colaboracion = new Colaboracion(montoColab, tipoRetorno, colab, prop);

            emr.add(colaboracion);
        } else {
            throw new PropuestaYaColaboradaException(nickColab + " ya tiene una colaboración con " + tituloProp);
        }
    }
    
    @Override
    public DTColaboracion obtenerDTColaboracion(Long id) {
        try {
            Colaboracion c = emr.find(Colaboracion.class, id);
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
    public ArrayList<String> listarColaboraciones() {
        return emr.Colaboraciones();
    }
    
    @Override
    public void eliminarColaboracion(Long id) {
        emr.eliminarColab(id);
    }
    
    @Override
    public ArrayList<String> obtenerColaboradoresColaboracion(String tituloProp) {
        return emr.colaboradoresColaboracion(tituloProp);
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones web.">
    @Override
    public String obtenerTipoUser(String nickname){
        Usuario usu =  emr.find(Usuario.class, nickname);
        if (usu == null){
            return null;
        }
        if (usu instanceof Colaborador) {
            return "colaborador";
        } else {
            return "proponente";
        }
    }
    
    @Override
    public boolean autenticarUsuario(String nickname, char[] password) {
        Usuario usu = emr.find(Usuario.class, nickname);
        if (usu == null) {
            return false;
        }
        String b64Hash = usu.getPasswordHash();
        String b64Salt = usu.getPasswordSalt();
        
        String passwordHash;
        try {
            passwordHash = crearPasswordHash(b64Salt, password);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException ex) {
            System.getLogger(Controller.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            Arrays.fill(password, 'x');
            return false;
        }
        Arrays.fill(password, 'x');
        return passwordHash != null && passwordHash.equals(b64Hash);
    }
    
    @Override
    public ArrayList<String> obtenerCategorias(){
        return emr.darCategorias();
    }

    @Override
    public String guardarImagen(byte[] bytesImagen) {
        String tipoImagen = obtenerTipoImagen(bytesImagen);
        if (tipoImagen == null) {
            return null;
        }
        String nombreArchivo = System.currentTimeMillis() + "." + tipoImagen;
        
        //porque si no tomcat mete el archivo a otra carpeta, en docker luego lo podemos cambiar más fácil
        Path pathImagen = Paths.get(System.getProperty("user.home"), "Documents", "NetBeansProjects", "Proyecto-PDA", "estacion", "imagenesUsuarios", nombreArchivo);
        
        try {
            Files.createDirectories(pathImagen.getParent());
            Files.write(pathImagen, bytesImagen, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
            return nombreArchivo;
        } catch (IOException ex) {
            System.getLogger(Controller.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
            return null;
        }
    }
    // buscar por patrón en título, descripción y lugar
    @Override
    public ArrayList<DTPropuesta> buscarPropuestasTDL(String patron) {
        ArrayList<String> titulosProps = emr.obtenerTitulosPropPatron(patron);
        
        ArrayList<DTPropuesta> propuestas = new ArrayList();
        
        for (String titulo : titulosProps) {
            propuestas.add(obtenerDTPropuesta(titulo));
        }
        
        return propuestas;
    }
    // </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc="Funciones auxiliares.">
    private String crearPasswordHash(String passwordSalt, char[] password) throws NoSuchAlgorithmException, InvalidKeySpecException {
        byte[] salt = Base64.getDecoder().decode(passwordSalt);
        KeySpec spec = new PBEKeySpec(password, salt, 65536, 128);
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
        byte[] hash = factory.generateSecret(spec).getEncoded();
        
        return Base64.getEncoder().encodeToString(hash);
    }
    
    private String crearPasswordSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        
        return Base64.getEncoder().encodeToString(salt);
    }
    
    private void validarPassword(char[] pass, char[] passConfirm) throws BadPasswordException {
        if (pass.length < 8 || pass.length > 24) {
            throw new BadPasswordException("La contraseña tiene que tener de 8 a 24 caracteres de largo");
        }
        if (passConfirm.length < 8 || passConfirm.length > 24) {
            throw new BadPasswordException("La contraseña tiene que tener de 8 a 24 caracteres de largo");
        }
        if (!Arrays.equals(pass, passConfirm)) {
            throw new BadPasswordException("La contraseña y la confirmación no coinciden");
        }
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
    
    private String obtenerTipoImagen(byte[] bytesImagen) {
        if (bytesImagen == null) {
            return null;
        }
        if (bytesImagen[0] == (byte) 0xFF && bytesImagen[1] == (byte) 0xD8 && bytesImagen[2] == (byte) 0xFF) {
            return "jpg";
        } else if (bytesImagen[0] == (byte) 0x89 && bytesImagen[1] == (byte) 0x50 && bytesImagen[2] == (byte) 0x4E &&
                 bytesImagen[3] == (byte) 0x47 && bytesImagen[4] == (byte) 0x0D && bytesImagen[5] == (byte) 0x0A &&
                 bytesImagen[6] == (byte) 0x1A && bytesImagen[7] == (byte) 0x0A) {
            return "png";
        }
        return null;
    }
    // </editor-fold>
    
}
