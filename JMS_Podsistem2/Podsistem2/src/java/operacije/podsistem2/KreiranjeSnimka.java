/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem2;

import entiteti.podsistem2.Korisnik;
import entiteti.podsistem2.VideoSnimak;
import greske.podsistem2.GKorisnikNePostoji;
import java.io.Serializable;
import java.sql.Time;
import java.time.Duration;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static main.Main.cf;
import static main.Main.topicPS2;
import operacija.Operacija;

/**
 *
 * @author Rale
 */
public class KreiranjeSnimka implements Operacija, Serializable{  
            
    private int idKor;
    private String naziv;
    private int trajanje;    

    public KreiranjeSnimka(int idKor, String naziv, int trajanje) {
        this.idKor = idKor;
        this.naziv = naziv;
        this.trajanje = trajanje;
    }        
    
    @Override
    public void izvrsi() {
        try {
            int indeks = perzistiranje();
            
            posaljiPoruku(indeks);
        } catch (Exception ex) {
            Logger.getLogger(KreiranjeSnimka.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void posaljiPoruku(int id) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        
        try {
            TextMessage txtMsg = context.createTextMessage(id + ":" + idKor);
            txtMsg.setStringProperty("tip", "noviSnimak");
            producer.send(topicPS2, txtMsg);
        } finally {
            context.close();
        }
    }
    
    private int perzistiranje() throws GKorisnikNePostoji {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        EntityManager em = emf.createEntityManager();
        int ret;
        
        try {        
        
            VideoSnimak videoSnimak = new VideoSnimak();
            Korisnik korisnik = em.find(Korisnik.class, idKor);

            if (korisnik == null) throw new GKorisnikNePostoji();

            videoSnimak.setNaziv(naziv);
            videoSnimak.setIdKor(korisnik);
            Time vremeTrajanja = new Time(trajanje * 1000);
            vremeTrajanja.setTime(vremeTrajanja.getTime() - 3600000);
            videoSnimak.setTrajanje(vremeTrajanja);
            videoSnimak.setDatumIVremePostavljanja(new Date());
            
            em.getTransaction().begin();
            em.persist(videoSnimak);
            em.getTransaction().commit();

            ret = videoSnimak.getIdVS();

            videoSnimak.setNaziv(naziv);
        } finally {
            em.close();
            emf.close();            
        }
        
        return ret;
    }
    
}
