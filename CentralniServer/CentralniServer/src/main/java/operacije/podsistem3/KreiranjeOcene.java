/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Gledanje;
import entiteti.podsistem3.Korisnik;
import entiteti.podsistem3.Ocena;
import entiteti.podsistem3.OcenaPK;
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
public class KreiranjeOcene implements Operacija, Serializable {

    private int novaOcena;
    private int idKor;
    private int idVS;

    public KreiranjeOcene(int novaOcena, int idKor, int idVS) {
        this.novaOcena = novaOcena;
        this.idKor = idKor;
        this.idVS = idVS;
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
            
            
            Ocena ocena = em.find(Ocena.class, new OcenaPK(idKor, idVS));
            
            if (ocena == null) {
                kreirajOcenu(em, korisnik, videoSnimak);
            } else {
                azurirajOcenu(em, ocena);
            }            
            
        } catch (GVideoNePostoji ex) {
            Logger.getLogger(KreiranjeGledanja.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GKorisnikNePostoji ex) {
            Logger.getLogger(KreiranjeGledanja.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
        }                
    }
    
    private void kreirajOcenu(EntityManager em, Korisnik korisnik, VideoSnimak videoSnimak) {
        Ocena ocena = new Ocena();
        ocena.setKorisnik(korisnik);
        ocena.setVideoSnimak(videoSnimak);
        ocena.setOcena(novaOcena);
        ocena.setDatumIVremeDavanja(new Date());
        ocena.setOcenaPK(new OcenaPK(idKor, idVS));

        em.getTransaction().begin();
        em.persist(ocena);
        em.getTransaction().commit();
    }
    
    private void azurirajOcenu(EntityManager em, Ocena ocena) {
        em.getTransaction().begin();
        ocena.setOcena(novaOcena);
        ocena.setDatumIVremeDavanja(new Date());
        em.getTransaction().commit();
    }
}
