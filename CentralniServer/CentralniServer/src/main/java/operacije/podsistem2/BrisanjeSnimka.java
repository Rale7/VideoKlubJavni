/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem2;

import entiteti.podsistem2.Korisnik;
import entiteti.podsistem2.VideoSnimak;
import greske.podsistem2.GKorisnikNePostoji;
import greske.podsistem2.GSnimakNePostoji;
import operacija.Operacija;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static main.Main.cf;
import static main.Main.topicPS2;

/**
 *
 * @author Rale
 */
public class BrisanjeSnimka implements Operacija, Serializable {

    private int idKor;
    private int idVS;

    public BrisanjeSnimka(int idKor, int idVS) {
        this.idKor = idKor;
        this.idVS = idVS;
    }        
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        EntityManager em = emf.createEntityManager();
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        
        try {                        
            VideoSnimak videoSnimak = em.find(VideoSnimak.class, idVS);
            Korisnik korisnik = em.find(Korisnik.class, idKor);
            
            if (videoSnimak == null) throw new GSnimakNePostoji();
            if (korisnik == null) throw new GKorisnikNePostoji();
            
            if (videoSnimak.getIdKor().equals(korisnik)) {
                em.getTransaction().begin();
                em.remove(videoSnimak);
                em.getTransaction().commit();                                
                
                TextMessage txtMsg = context.createTextMessage(idVS + "");
                txtMsg.setStringProperty("tip", "obrisanSnimak");
                producer.send(topicPS2, txtMsg);
            }
        } catch (GSnimakNePostoji ex) {
            Logger.getLogger(BrisanjeSnimka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GKorisnikNePostoji ex) {
            Logger.getLogger(BrisanjeSnimka.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(BrisanjeSnimka.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
            context.close();
        }
    }
    
}
