/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Ocena;
import entiteti.podsistem3.OcenaPK;
import operacija.Operacija;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rale
 */
public class BrisanjeOcene implements Operacija, Serializable {

    private int idKor;
    private int idVS;

    public BrisanjeOcene(int idKor, int idVS) {
        this.idKor = idKor;
        this.idVS = idVS;
    }        
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            
            Ocena ocena = em.find(Ocena.class, new OcenaPK(idKor, idVS));
            
            if (ocena != null) {
                em.getTransaction().begin();
                em.remove(ocena);
                em.getTransaction().commit();
            }
            
        } finally {
            em.close();
            emf.close();
        }
    }
    
}
