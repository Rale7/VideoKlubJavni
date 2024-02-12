/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Gledanje;
import entiteti.podsistem3.Korisnik;
import entiteti.podsistem3.VideoSnimak;
import greske.podsistem3.GKorisnikNePostoji;
import greske.podsistem3.GVideoNePostoji;
import operacija.Operacija;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rale
 */
public class KreiranjeGledanja implements Operacija, Serializable {

    private int idKor;
    private int idVS;
    private int zapocetoOd;
    private int sekundiOdgledano;

    public KreiranjeGledanja(int idKor, int idVS, int zapocetoOd, int sekundiOdgledano) {
        this.idKor = idKor;
        this.idVS = idVS;
        this.zapocetoOd = zapocetoOd;
        this.sekundiOdgledano = sekundiOdgledano;
    }        
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            Korisnik korisnik = em.find(Korisnik.class, idKor);
            VideoSnimak videoSnimak = em.find(VideoSnimak.class, idVS);
            
            if (korisnik == null) throw new GKorisnikNePostoji();
            if (videoSnimak == null) throw new GVideoNePostoji();
            
            Gledanje gledanje = new Gledanje();
            gledanje.setIdKor(korisnik);
            gledanje.setIdVS(videoSnimak);
            gledanje.setSekundiOdlgedano(sekundiOdgledano);
            gledanje.setZapocetoOd(zapocetoOd);
            gledanje.setDatumVremePocetka(new Date());
            
            em.getTransaction().begin();
            em.persist(gledanje);
            em.getTransaction().commit();
            
        } catch (GVideoNePostoji ex) {
            Logger.getLogger(KreiranjeGledanja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GKorisnikNePostoji ex) {
            Logger.getLogger(KreiranjeGledanja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
        }
    }
    
}
