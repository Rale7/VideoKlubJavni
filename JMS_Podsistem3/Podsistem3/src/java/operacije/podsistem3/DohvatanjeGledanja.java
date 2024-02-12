/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Gledanje;
import entiteti.podsistem3.Korisnik;
import entiteti.podsistem3.Pretplata;
import entiteti.podsistem3.VideoSnimak;
import greske.podsistem3.GKorisnikNePostoji;
import greske.podsistem3.GVideoNePostoji;
import operacija.Operacija;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Topic;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import static main.Main.cf;
import static main.Main.topicPS3;
/**
 *
 * @author Rale
 */
public class DohvatanjeGledanja implements Operacija, Serializable {
    
    private int idVS;    

    public DohvatanjeGledanja(int idVS) {
        this.idVS = idVS;
    }        
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        
        try {
            
            VideoSnimak videoSnimak = em.find(VideoSnimak.class, idVS);
            if (videoSnimak == null) throw new GVideoNePostoji();
            
            ArrayList<Gledanje> gledanja = new ArrayList<>(videoSnimak.getGledanjeList());                        
            
            ObjectMessage objMsg = context.createObjectMessage(gledanja);
            objMsg.setStringProperty("tip", "dohvatanjeGledanjaSnimak");
            objMsg.setIntProperty("idVS", idVS);
            producer.send(topicPS3, objMsg);
        } catch (GVideoNePostoji ex) {
            Logger.getLogger(DohvatanjePretplata.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(DohvatanjePretplata.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
            context.close();
        }
    }
    
}
