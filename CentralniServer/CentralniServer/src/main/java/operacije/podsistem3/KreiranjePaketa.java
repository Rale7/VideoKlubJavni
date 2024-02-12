/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Paket;
import operacija.Operacija;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rale
 */
public class KreiranjePaketa implements Operacija, Serializable {

    private int mesecnaCena;

    public KreiranjePaketa(int mesecnaCena) {
        this.mesecnaCena = mesecnaCena;
    }        
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            
            Paket paket = new Paket();
            paket.setMesecnaCena(mesecnaCena);
            
            em.getTransaction().begin();
            em.persist(paket);
            em.getTransaction().commit();
            
        } finally {
            em.close();
            emf.close();
        }
    }
    
}
