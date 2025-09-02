/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package culturarte.logica;


import jakarta.persistence.*;


/**
 *
 * @author faxcundo
 */
public class Manejador {
    private static Manejador instancia = null;
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Proyecto_PDA");
    private EntityManager em =emf.createEntityManager();
    
    
    
    public static Manejador getInstance(){
        
        if(instancia == null){
            instancia = new Manejador();
        }
        return instancia;
    }
    
    
    public void addUsuario(Usuario usu){
        EntityTransaction t = em.getTransaction();
        try{
            t.begin();
            em.persist(usu);
            t.commit();
        }
        catch(Exception e){
            t.rollback();
            e.printStackTrace();
        }
        
        
    }
    
}
