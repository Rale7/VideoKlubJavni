/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem1;

import entiteti.podsistem1.Mesto;
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
import static main.Main.topicPS1;

/**
 *
 * @author Rale
 */
public class DohvatanjeSvihMesta implements Operacija, Serializable {   
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        EntityManager em = emf.createEntityManager();
        JMSContext context = cf.createContext();
        
        try {                        
            JMSProducer producer = context.createProducer();
            
            ArrayList<Mesto> mesta = new ArrayList<>(em.createNamedQuery("Mesto.findAll", Mesto.class).getResultList());
            
            
            ObjectMessage objMsg = context.createObjectMessage(mesta);
            objMsg.setStringProperty("tip", "odgovorSvaMesta");
            producer.send(topicPS1, objMsg);
        } catch (JMSException ex) {
            Logger.getLogger(DohvatanjeSvihKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
            context.close();
        }
    }
    
}
