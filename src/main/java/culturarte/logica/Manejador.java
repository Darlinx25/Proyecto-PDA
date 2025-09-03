/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;

import jakarta.persistence.*;
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
    
    
        public List<String> listarNickColaboradores() {
        try {
            String jpql = "SELECT c.nickname FROM Colaborador c";
            return em.createQuery(jpql, String.class).getResultList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
        
        
        /* Esta esta alternativa generica a la funcion de arriba, 
        implica pasar por parametro asi (String.class, "nickname", "Colaborador")
        public <T> List<T> listarAtributos(Class<T> tipoResultado, String atributo, String entidad) {
        try {
        String jpql = "SELECT e." + atributo + " FROM " + entidad + " e";
        return em.createQuery(jpql, tipoResultado).getResultList();
         } catch (Exception e) {
        return Collections.emptyList();
        }
}

        
        */
    }
}
