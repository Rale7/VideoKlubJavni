/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem1;

import entiteti.podsistem1.Korisnik;
import operacija.Operacija;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rale
 */
public class PromenaEmaila implements Operacija, Serializable {

    private String noviMail;
    private int idKor;

    public PromenaEmaila(String noviMail, int idKor) {
        this.noviMail = noviMail;
        this.idKor = idKor;
    }
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        EntityManager em = emf.createEntityManager();
                
        Korisnik korisnik = em.find(Korisnik.class, idKor);        
        if (korisnik != null) {
            em.getTransaction().begin();
            korisnik.setEmail(noviMail);
            em.getTransaction().commit();
            
        }        
        
        em.close();
        emf.close();  
    }
    
    
    
}
