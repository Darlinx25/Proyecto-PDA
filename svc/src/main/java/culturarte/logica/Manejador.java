/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author faxcundo
 */
public class Manejador {

    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("Proyecto_PDA");
    private final EntityManager em = emf.createEntityManager();

    public static Manejador getInstance() {

        return new Manejador();
    }

    public void close() {
        if (em.isOpen()) em.close();
    }
    
    public void add(Object clase) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.persist(clase);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
        }
    }

    public void mod(Object clase) {
        EntityTransaction t = em.getTransaction();
        try {
            t.begin();
            em.merge(clase);
            t.commit();
        } catch (Exception e) {
            t.rollback();
            e.printStackTrace();
        }
    }

    public <T, ID> T find(Class<T> clase, ID id) {
        T entidad = em.find(clase, id);

        return entidad;
    }

    public Long datoUsuarioRepetido(String campo, String valor) {
        if (!campo.equals("nickname") && !campo.equals("email")) {
            throw new IllegalArgumentException("Campo no permitido: " + campo);
        }
        String jpql = "SELECT COUNT(u) FROM Usuario u WHERE u." + campo + " = :valor";
        TypedQuery<Long> q1 = em.createQuery(jpql, Long.class);
        q1.setParameter("valor", valor);

        return q1.getSingleResult();
    }

    public <T> List<T> listarAtributo(Class<T> tipoResultado, String atributo, String entidad) {
        try {
            String jpql = "SELECT e." + atributo + " FROM " + entidad + " e";
            return em.createQuery(jpql, tipoResultado).getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }

    public ArrayList<String> obtenerPropuestasEstado(int estado) {
        List<String> aux;
        EstadoPropuesta est = EstadoPropuesta.values()[estado];
        String query = "SELECT p.titulo FROM Propuesta p WHERE p.estadoActual.estado = :est";
        try {
            aux = em.createQuery(query, String.class).setParameter("est", est).getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
            e.printStackTrace();
        }
        return new ArrayList<>(aux);
    }
        public ArrayList<String> obtenerPropuestasEstadoUsu(int estado, String nick) {
        List<String> aux;
        EstadoPropuesta est = EstadoPropuesta.values()[estado];
        String query = "SELECT p.titulo FROM Propuesta p WHERE p.estadoActual.estado = :est AND p.proponente.nickname = :nick";
        try {
            aux = em.createQuery(query, String.class).setParameter("est", est).setParameter("nick", nick).getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
            e.printStackTrace();
        }
        return new ArrayList<>(aux);
    }

    public List<Usuario> obtenerUsuario(String nickname) {
        return em.createQuery("SELECT u FROM Usuario u WHERE u.nickname = :nickname", Usuario.class)
                .setParameter("nickname", nickname).getResultList();
    }

    

    
    public ArrayList<String> obtenerUsuariosSeguir(String nickname) {
        List<String> aux;
        String query = """
                SELECT u.nickname 
                FROM Usuario u 
                WHERE u.nickname != :nick 
                AND u.nickname NOT IN (
                    SELECT us.nickname 
                    FROM Usuario user 
                    JOIN user.usuariosSeguidos us 
                    WHERE user.nickname = :nick
                )
        """;

        try {
            aux = em.createQuery(query, String.class).setParameter("nick", nickname).getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
            e.printStackTrace();
        }
        return new ArrayList<>(aux);
    }

    public ArrayList<String> listaPropuestasUsuario(String nick) {
        List<String> aux;

        String query = "SELECT p.titulo FROM Propuesta p WHERE p.proponente.nickname = :nick";
        try {
            aux = em.createQuery(query, String.class).setParameter("nick", nick).getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
            e.printStackTrace();
        }
        return new ArrayList<>(aux);
    }

    public ArrayList<String> listPropuestasProponentes() {
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

    public ArrayList<String> listPropuestasProponentesIngresadas() {
        List<Object[]> aux;
        List<String> aux2 = new ArrayList<>();

        String query = "SELECT p.titulo, p.proponente.nickname FROM Propuesta p"
                + " WHERE p.estadoActual.estado = :estado1";
        try {
            aux = em.createQuery(query, Object[].class)
                    .setParameter("estado1", EstadoPropuesta.INGRESADA)
                    .getResultList();
            for (Object[] fila : aux) {
                aux2.add(fila[0] + " - " + fila[1]);
            }
        } catch (Exception e) {
            aux2 = Collections.emptyList();
        }
        return (ArrayList<String>) aux2;
    }

    public ArrayList<String> propuestaColaboradaPorUser(String nickColab, String tituloProp) {
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
            aux = Collections.emptyList();
        }
        return (ArrayList<String>) aux;
    }

    public ArrayList<Float> obtenerDinero(String tituloProp) {
        List<Float> aux;
        Float resultado = 0f;
        String query = "SELECT c.monto FROM Colaboracion c WHERE c.propuestaColaborada.titulo = :tituloProp";
        try {
            aux = em.createQuery(query, Float.class).setParameter("tituloProp", tituloProp).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            aux = Collections.emptyList();;
        }
        return (ArrayList<Float>) aux;
    }

    public ArrayList<String> colaboradoresColaboracion(String tituloProp) {
        List<String> aux;
        String query = "SELECT c.colaborador.nickname FROM Colaboracion c WHERE c.propuestaColaborada.titulo = :tituloProp";
        try {
            aux = em.createQuery(query, String.class).setParameter("tituloProp", tituloProp).getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
            e.printStackTrace();

        }
        return (ArrayList<String>) aux;
    }

    public ArrayList<String> propuestasColaboradas(String nick) {
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

        return (ArrayList<String>) aux;
    }

    public ArrayList<String> colaboracionesColaborador(String nickColab) {
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

    public ArrayList<String> Colaboraciones() {
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

    public ArrayList<String> darCategorias() {
        List<String> aux;
        String query = "SELECT c.nombre FROM Categoria c WHERE c.nombre != 'Categorías'";
        try {
            aux = em.createQuery(query, String.class).getResultList();
            System.out.println("Categorias encontradas: " + aux);
        } catch (Exception e) {
            e.printStackTrace();
            aux = Collections.emptyList();
        }
        return new ArrayList<>(aux);
    }

    public void eliminarColab(Long id) {
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
    
    public ArrayList<String> obtenerTitulosPropPatron(String patron) {
        List<String> aux;
        String patronBusqueda = "%" + patron + "%";
        EstadoPropuesta est = EstadoPropuesta.INGRESADA;
        String query = "SELECT DISTINCT p.titulo FROM Propuesta p WHERE p.estadoActual.estado <> :est AND "
                + "p.titulo LIKE :patronBusqueda OR p.descripcion LIKE :patronBusqueda OR p.lugarRealizara LIKE :patronBusqueda";
        try {
            aux = em.createQuery(query, String.class)
                    .setParameter("est", est)
                    .setParameter("patronBusqueda", patronBusqueda)
                    .getResultList();
        } catch (Exception e) {
            aux = Collections.emptyList();
            e.printStackTrace();
        }
        return new ArrayList<>(aux);
    }

    /*public <T,V> T listarAtributoPorCondicion(
            Class <T> tipoResultado,
            String atributo,
            String entidad,
            String atributo_cond,
            V condicion ){
        try{
            String jpql = "Select e." + atributo + " FROM " + entidad + " e " + " WHERE e." + atributo_cond + " =:valor";
            return em.createQuery(jpql, tipoResultado).setParameter("valor",condicion);
        }catch(Exception e){
            return Collections.emptyList();
        }
        
    } No anda ni se usa esto de momento
     */

    Usuario findUserPorEmail(String email) {
        Usuario aux;
        String query = "SELECT u FROM Usuario u WHERE u.email = :email";
        try {
            aux = em.createQuery(query, Usuario.class)
                    .setParameter("email", email).getSingleResult();
        } catch (Exception e) {
            aux = null;
            e.printStackTrace();
        }
        return aux;
    }

}
