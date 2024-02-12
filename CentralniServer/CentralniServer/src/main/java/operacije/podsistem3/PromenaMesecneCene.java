/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Paket;
import greske.podsistem3.GPaketNePostoji;
import operacija.Operacija;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rale
 */
public class PromenaMesecneCene implements Operacija, Serializable {

    private int novaMesecnaCena;
    private int idPaketa;

    public PromenaMesecneCene(int novaMesecnaCena, int idPaketa) {
        this.novaMesecnaCena = novaMesecnaCena;
        this.idPaketa = idPaketa;
    }        
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        try {
             Paket paket = em.find(Paket.class, idPaketa);
             if (paket == null) throw new GPaketNePostoji();
             
             em.getTransaction().begin();
             paket.setMesecnaCena(novaMesecnaCena);
             em.getTransaction().commit();
        } catch (GPaketNePostoji ex) {
            Logger.getLogger(PromenaMesecneCene.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
        }
        
    }
 
    
}
