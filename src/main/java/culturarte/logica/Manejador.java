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

    private static Manejador instancia = null;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Proyecto_PDA");
    private EntityManager em = emf.createEntityManager();

    public static Manejador getInstance() {

        if (instancia == null) {
            instancia = new Manejador();
        }
        return instancia;
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
    
    public void mod(Object clase){
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
        return em.find(clase, id);
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
    
    public ArrayList<String> obtenerPropuestasEstado(int estado){
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
    
    public List<Usuario> obtenerUsuario(String nickname){
       return em.createQuery("SELECT u FROM Usuario u WHERE u.nickname = :nickname", Usuario.class)
                .setParameter("nickname", nickname).getResultList();
    }
    public ArrayList<String> obtenerUsuariosSeguir(String nickname){
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
    
    
    
    
    
}

