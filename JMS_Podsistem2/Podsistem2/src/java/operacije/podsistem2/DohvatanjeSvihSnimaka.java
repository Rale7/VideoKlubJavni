/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem2;

import entiteti.podsistem2.VideoSnimak;
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
import static main.Main.topicPS2;

/**
 *
 * @author Rale
 */
public class DohvatanjeSvihSnimaka implements Operacija, Serializable {
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        EntityManager em = emf.createEntityManager();
        JMSContext context = cf.createContext();
        
        try {            
            JMSProducer producer = context.createProducer();
            
            ArrayList<VideoSnimak> kategorije = new ArrayList<>(em.createNamedQuery("VideoSnimak.findAll", VideoSnimak.class).getResultList());
            ObjectMessage objMsg = context.createObjectMessage(kategorije);
            objMsg.setStringProperty("tip", "sviSnimci");        
            producer.send(topicPS2, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(DohvatanjeSvihKategorija.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
            context.close();
        }
    }
    
}
