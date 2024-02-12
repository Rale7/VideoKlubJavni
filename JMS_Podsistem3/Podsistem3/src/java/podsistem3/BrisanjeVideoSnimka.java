/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package podsistem3;

import entiteti.podsistem3.Korisnik;
import entiteti.podsistem3.VideoSnimak;
import greske.podsistem3.GKorisnikNePostoji;
import greske.podsistem3.GVideoNePostoji;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import main.Main;
import static main.Main.cf;
import static main.Main.topicPS2;

/**
 *
 * @author Rale
 */
public class BrisanjeVideoSnimka extends Thread {

    private static final int clientId = 300;
    
    @Override
    public void run() {
        JMSContext context = cf.createContext();
        //JMSConsumer consumer = context.createDurableConsumer(topicPS2, "sub" + clientId, "tip='obrisanSnimak'", true);
        JMSConsumer consumer = context.createConsumer(topicPS2, "tip='obrisanSnimak'");
        
        while (true) {
            try {
                Message msg = consumer.receive();
                
                if (!(msg instanceof TextMessage)) continue;
                
                TextMessage txtMsg = (TextMessage) msg;
                String text = txtMsg.getText();
                int idVS = Integer.parseInt(text);
                
                obrisiVideo(idVS);
            } catch (JMSException ex) {
                Logger.getLogger(DodavanjeVideoSnimka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GVideoNePostoji ex) {
                Logger.getLogger(BrisanjeVideoSnimka.class.getName()).log(Level.SEVERE, null, ex);
            }
        }        
    }
    
    private void obrisiVideo(int idVS) throws GVideoNePostoji {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        VideoSnimak vs = em.find(VideoSnimak.class, idVS);
        
        if (vs == null) throw new GVideoNePostoji();
        
        em.getTransaction().begin();
        em.remove(vs);
        em.getTransaction().commit();
        
        try {
            
        } finally {
            em.close();
            emf.close();
        }
    }            
}
