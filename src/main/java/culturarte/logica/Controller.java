/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;
import jakarta.persistence.*;
import java.awt.image.BufferedImage;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.tree.DefaultMutableTreeNode;

/**
 *
 * @author mark
 */

//SINGLETON
public class Controller implements IController {
    private Map<String, Usuario> usuarios;
    private Map<String, Propuesta> propuestas;
    private Map<String, Categoria> categorias;

    private static Controller instancia;
    
    private EntityManagerFactory emf; // se declara estos objetos como atributos, para que todos los métodos puedan usarlos
    private EntityManager em;
    
    
    
    
    private Controller() {
        usuarios = new HashMap<>();
        propuestas = new HashMap<>();
        categorias = new HashMap<>();

        emf = Persistence.createEntityManagerFactory("Proyecto_PDA");
        em = emf.createEntityManager();

    // Verificar si la raíz ya existe en la DB
    Categoria raiz = em.find(Categoria.class, "Categorías");
    if (raiz == null) {
        raiz = new Categoria("Categorías");
        EntityTransaction t = em.getTransaction();
        t.begin();
        em.persist(raiz);
        t.commit();
    }
}

    
  
    public static Controller getInstance() {
        if (instancia == null) {
            instancia = new Controller();
        }
        return instancia;
    }

    @Override
    public void addUsuario(DTUsuario user) {
        String nick = user.getNickname();
        
        if (this.usuarios.containsKey(nick)) {
            return;//agregar exception luego
        }
        //corroborar emails únicos luego
        String nombre = user.getNombre();
        String apellido = user.getApellido();
        String email = user.getEmail();
        LocalDate fechaNac = user.getFechaNacimiento();
        BufferedImage imagen = user.getImagen();
        
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
        this.usuarios.put(nick, usu);
        EntityTransaction t = em.getTransaction();
        try{
           t.begin();
           em.persist(usu);
           t.commit();
        }catch(Exception  e){
            t.rollback();
            e.printStackTrace();
        }
        
        
    }
    
    @Override
    public DefaultMutableTreeNode listarCategorias() {
        //throw new UnsupportedOperationException("Not supported yet.");
        Categoria catRaiz = em.find(Categoria.class, "Categorías");
        DefaultMutableTreeNode raiz = nodosArbolCategorias(catRaiz);
        return raiz;
    }
    //helper para listarCategorias() recursivo
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
        if (this.categorias.containsKey(nombre)) {
            return; // exception después
        }
        Categoria cat = new Categoria(nombre);

        if (nombrePadre != null) {
            Categoria padre = em.find(Categoria.class, nombrePadre);
            if (padre != null) {
                padre.addSubcategoria(cat);
                cat.setPadre(padre);
            }
        } else {
            Categoria raiz = em.find(Categoria.class, "Categorías");
            raiz.addSubcategoria(cat);
            cat.setPadre(raiz);
        }

        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(cat);   // gracias a la relación, JPA guarda con la FK al padre
            t.commit();
        } catch (Exception e) {
            if (t.isActive()) t.rollback();
            e.printStackTrace();
        }
    }

    
    @Override
    public ArrayList<String> listarColaboradores() {
        List<String> aux;
        
        String query = "SELECT c.nickname FROM Colaborador c";
        try {
            aux = em.createQuery(query, String.class).getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
        }
        return (ArrayList<String>) aux;
    }
    
    @Override
    public ArrayList<String> listarProponentes() {
        ArrayList<String> aux = new ArrayList<String>();
        
         try {
        List<Proponente> result = em.createQuery("SELECT p FROM Proponente p", Proponente.class)
                                    .getResultList();
        for (Proponente p : result) {
            aux.add(p.getNickname());
        }
        } catch (Exception e) {
        e.printStackTrace();
        }
        
        return aux;
    }
    
    @Override
    public void addPropuesta(DTPropuesta prop) {
        String titulo = prop.getTitulo();
        
        if (this.propuestas.containsKey(titulo)) {
            return;//agregar exception luego
        }
        
        String descripcion = prop.getDescripcion();
        BufferedImage imagen = prop.getImagen();
        String lugarRealizara = prop.getLugarRealizara();
        LocalDate fechaRealizara = prop.getFechaRealizara();
        float precioEntrada = prop.getPrecioEntrada();
        float montoAReunir = prop.getMontoAReunir();
        
        // Buscar en la base de datos la categoría y el proponente
        Categoria tipoPropuesta = em.find(Categoria.class, prop.getTipoPropuesta());
        Proponente proponedor = em.find(Proponente.class, prop.getNickProponedor());

        List<TipoRetorno> tiposRetorno = prop.getTiposRetorno();
        
        Propuesta propuesta = new Propuesta(titulo, descripcion, imagen, lugarRealizara, fechaRealizara, precioEntrada, montoAReunir, tiposRetorno, 
                tipoPropuesta, proponedor);
        
        this.propuestas.put(titulo, propuesta);
        EntityTransaction t = em.getTransaction();
        try{
           t.begin();
           em.persist(propuesta);
           t.commit();
        }catch(Exception  e){
            t.rollback();
            e.printStackTrace();
        }
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


   

 
    
}

    
