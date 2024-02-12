/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Ocena;
import entiteti.podsistem3.VideoSnimak;
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
public class DohvatanjeOcena implements Operacija, Serializable {

    private int idVS;        

    public DohvatanjeOcena(int idVS) {
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
            
            ArrayList<Ocena> ocene = new ArrayList(videoSnimak.getOcenaList());                        
            
            ObjectMessage objMsg = context.createObjectMessage(ocene);
            objMsg.setStringProperty("tip", "dohvatanjeOcenaVideo");
            objMsg.setIntProperty("idVS", idVS);
            producer.send(topicPS3, objMsg);            
        } catch (GVideoNePostoji ex) {
            Logger.getLogger(DohvatanjeOcena.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JMSException ex) {
            Logger.getLogger(DohvatanjeOcena.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
            context.close();
        }
    }
    
    
    
}
