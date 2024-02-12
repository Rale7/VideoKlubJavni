/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package podsistem3;

import entiteti.podsistem3.Korisnik;
import entiteti.podsistem3.VideoSnimak;
import greske.podsistem3.GKorisnikNePostoji;
import java.util.StringTokenizer;
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
import static main.Main.topicPS2;

/**
 *
 * @author Rale
 */
public class DodavanjeVideoSnimka extends Thread {
    
    private static final String delimiter = ":";        

    @Override
    public void run() {
        JMSContext context = cf.createContext();
        //JMSConsumer consumer = context.createDurableConsumer(topicPS2, "sub3", "Tip=noviSnimak", true);
        JMSConsumer consumer = context.createConsumer(topicPS2, "tip='noviSnimak'");
        
        while (true) {
            try {
                Message msg = consumer.receive();
                
                if (!(msg instanceof TextMessage)) continue;
                
                TextMessage txtMsg = (TextMessage) msg;
                String text = txtMsg.getText();                
                StringTokenizer stringTokenizer = new StringTokenizer(text, delimiter);
                int idVS = Integer.parseInt(stringTokenizer.nextToken());
                int idKor = Integer.parseInt(stringTokenizer.nextToken());                                
                
                perzistiraj(idKor, idVS);
            } catch (JMSException ex) {
                Logger.getLogger(DodavanjeVideoSnimka.class.getName()).log(Level.SEVERE, null, ex);
            } catch (GKorisnikNePostoji ex) {
                Logger.getLogger(DodavanjeVideoSnimka.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            
        }
    }
    
    private void perzistiraj(int idKor, int idVS) throws GKorisnikNePostoji {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem3PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            
            Korisnik korisnik = em.find(Korisnik.class, idKor);
            if (korisnik == null) throw new GKorisnikNePostoji();
            
            VideoSnimak videoSnimak = new VideoSnimak(idVS);
            videoSnimak.setIdKor(korisnik);
            
            em.getTransaction().begin();
            em.persist(videoSnimak);
            em.getTransaction().commit();
            
        } finally {
            em.close();
            emf.close();
        }
    }
    
}
