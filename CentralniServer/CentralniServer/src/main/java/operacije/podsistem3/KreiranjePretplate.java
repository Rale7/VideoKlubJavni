/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Korisnik;
import entiteti.podsistem3.Paket;
import entiteti.podsistem3.Pretplata;
import greske.podsistem3.GKorisnikNePostoji;
import greske.podsistem3.GPaketNePostoji;
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
public class KreiranjePretplate implements Operacija, Serializable {

    private int idKor;
    private int idPak;

    public KreiranjePretplate(int idKor, int idPak) {
        this.idKor = idKor;
        this.idPak = idPak;
    }        
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            
            Korisnik korisnik = em.find(Korisnik.class, idKor);
            Paket paket = em.find(Paket.class, idPak);
            
            if (korisnik == null) throw new GKorisnikNePostoji();
            if (paket == null) throw new GPaketNePostoji();
            
            Pretplata pretplata = new Pretplata();
            pretplata.setIdKor(korisnik);
            pretplata.setIdPak(paket);
            pretplata.setTrenCena(paket.getMesecnaCena());
            pretplata.setDatumIVremePocetka(new Date());
            
            em.getTransaction().begin();
            em.persist(pretplata);
            em.getTransaction().commit();
            
        } catch (GKorisnikNePostoji ex) {
            Logger.getLogger(KreiranjePretplate.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GPaketNePostoji ex) {
            Logger.getLogger(KreiranjePretplate.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
        }
        
        
    }
    
}
