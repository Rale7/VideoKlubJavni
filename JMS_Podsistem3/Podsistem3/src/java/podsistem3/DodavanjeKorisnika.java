/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package podsistem3;

import entiteti.podsistem3.Korisnik;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
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
public class DodavanjeKorisnika extends Thread {       
    
    private static final int clientId = 200;
    
    @Override
    public void run() {        
        JMSContext context = cf.createContext();
        //JMSConsumer consumer = context.createDurableConsumer(topicPS1, "sub" + clientId, "tip='noviKorisnik'", true);
        JMSConsumer consumer = context.createConsumer(topicPS1, "tip='noviKorisnik'");
            
        while (true) {
            try {
                Message msg = consumer.receive();
                if (!(msg instanceof TextMessage)) continue;
                
                TextMessage txtMsg = (TextMessage) msg;
                int id = Integer.parseInt(txtMsg.getText());
                            
                perzistirajKorisnika(id);
            } catch (JMSException ex) {
                Logger.getLogger(DodavanjeKorisnika.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    private void perzistirajKorisnika(int id) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            Korisnik korisnik = new Korisnik(id);
            em.getTransaction().begin();
            em.persist(korisnik);
            em.getTransaction().commit();
        } finally {
            em.close();
            emf.close();
        }
    }
    
}
