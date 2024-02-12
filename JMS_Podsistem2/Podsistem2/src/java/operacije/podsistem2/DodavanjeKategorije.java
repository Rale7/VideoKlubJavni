/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem2;

import entiteti.podsistem2.Kategorija;
import entiteti.podsistem2.VideoSnimak;
import greske.podsistem2.GKategorijaNePostoji;
import greske.podsistem2.GSnimakNePostoji;
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
public class DodavanjeKategorije implements Operacija, Serializable {

    private int idKat;
    private int idVS;

    public DodavanjeKategorije(int idKat, int idVS) {
        this.idKat = idKat;
        this.idVS = idVS;
    }        
    
    @Override
    public void izvrsi(){
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            
            VideoSnimak videoSnimak = em.find(VideoSnimak.class, idVS);
            Kategorija kategorija = em.find(Kategorija.class, idKat);
            
            if (videoSnimak == null) throw new GSnimakNePostoji();
            if (kategorija == null) throw new GKategorijaNePostoji();
            
            em.getTransaction().begin();
            videoSnimak.getKategorijaList().add(kategorija);
            kategorija.getVideoSnimakList().add(videoSnimak);
            em.getTransaction().commit();
            
        } catch (GSnimakNePostoji ex) {
            Logger.getLogger(DodavanjeKategorije.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GKategorijaNePostoji ex) {
            Logger.getLogger(DodavanjeKategorije.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
        }
    }
    
}
