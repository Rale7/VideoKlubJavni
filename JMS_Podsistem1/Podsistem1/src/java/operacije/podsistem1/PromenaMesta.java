/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem1;

import entiteti.podsistem1.Korisnik;
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
public class PromenaMesta implements Operacija, Serializable {

    private int idMes;
    private int idKor;

    public PromenaMesta(int idMes, int idKor) {
        this.idMes = idMes;
        this.idKor = idKor;
    }
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        EntityManager em = emf.createEntityManager();
                
        Korisnik korisnik = em.find(Korisnik.class, idKor);
        Mesto mesto = em.find(Mesto.class, idMes);
        if (korisnik != null && mesto != null) {
            em.getTransaction().begin();
            korisnik.setIdMes(mesto);
            em.getTransaction().commit();
            
        }        
        
        em.close();
        emf.close();       
    }
    
}
