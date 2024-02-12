/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem3;

import entiteti.podsistem3.Paket;
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
public class DohvatanjePaketa implements Operacija, Serializable {    
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();
        
        try {
            
            ArrayList<Paket> paketi = new ArrayList<>(em.createNamedQuery("Paket.findAll", Paket.class).getResultList());                      
            
            ObjectMessage objMsg = context.createObjectMessage(paketi);
            objMsg.setStringProperty("tip", "dohvatanjeSvihPaketa");
            producer.send(topicPS3, objMsg);
            
        } catch (JMSException ex) {
            Logger.getLogger(DohvatanjePaketa.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            em.close();
            emf.close();
            context.close();
        }
    }
    
}
