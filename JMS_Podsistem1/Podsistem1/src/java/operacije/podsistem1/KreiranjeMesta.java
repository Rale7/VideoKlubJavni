/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem1;

import entiteti.podsistem1.Mesto;
import operacija.Operacija;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rale
 */
public class KreiranjeMesta implements Operacija, Serializable {
    
    private String nazivMesta;

    public KreiranjeMesta(String nazivMesta) {
        this.nazivMesta = nazivMesta;
    }
    
    @Override
    public void izvrsi() {
        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        EntityManager em = emf.createEntityManager();
        
        Mesto mesto = new Mesto();
        mesto.setNaziv(nazivMesta);
        
        em.getTransaction().begin();
        em.persist(mesto);
        em.getTransaction().commit();
        
        em.close();
        emf.close();
    }
    
}
