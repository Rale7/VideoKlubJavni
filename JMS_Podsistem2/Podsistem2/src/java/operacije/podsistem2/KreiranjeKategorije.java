/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem2;

import entiteti.podsistem2.Kategorija;
import operacija.Operacija;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rale
 */
public class KreiranjeKategorije implements Operacija, Serializable {

    private String naziv;

    public KreiranjeKategorije(String naziv) {
        this.naziv = naziv;
    }        
    
    @Override
    public void izvrsi() {        
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            Kategorija kategorija = new Kategorija();
            kategorija.setNaziv(naziv);

            em.getTransaction().begin();
            em.persist(kategorija);
            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
        
    }
    
}
