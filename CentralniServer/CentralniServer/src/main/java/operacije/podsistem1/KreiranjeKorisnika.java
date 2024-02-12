/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem1;

import entiteti.podsistem1.Korisnik;
import entiteti.podsistem1.Mesto;
import greske.podsistem1.GMestoNepostoji;
import operacija.Operacija;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
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
public class KreiranjeKorisnika implements Operacija, Serializable {   
    
    private String ime;
    private String email;
    private int godiste;
    private char pol;
    private int idMes;

    public KreiranjeKorisnika(String ime, String email, int godiste, char pol, int idMes) {
        this.ime = ime;
        this.email = email;
        this.godiste = godiste;
        this.pol = pol;
        this.idMes = idMes;
    }
    
    @Override
    public void izvrsi() {          
        
        try {
            int id = perzistiraj();
            
            posaljiPoruku(id);            
        } catch (JMSException ex) {
            Logger.getLogger(KreiranjeKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        } catch (GMestoNepostoji ex) {
            Logger.getLogger(KreiranjeKorisnika.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private int perzistiraj() throws GMestoNepostoji{
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem1PU");
        EntityManager em = emf.createEntityManager();
        int ret;
        
        try {
            Mesto mesto = em.find(Mesto.class, idMes);
            if (mesto == null) throw new GMestoNepostoji();

            Korisnik korisnik = new Korisnik();
            korisnik.setIme(ime);
            korisnik.setEmail(email);
            korisnik.setGodiste(godiste);
            korisnik.setPol(pol + "");

            em.getTransaction().begin();      

            korisnik.setIdMes(mesto);
            em.persist(korisnik);
            em.getTransaction().commit();
            ret = korisnik.getIdKor();
        } finally {        
            em.close();
            emf.close();
        }
        
        return ret;
    }
    
    private void posaljiPoruku(int id) throws JMSException {
        JMSContext context = cf.createContext();
        JMSProducer producer = context.createProducer();                                                

        try {
            TextMessage txtMsg = context.createTextMessage(id + "");
            txtMsg.setStringProperty("tip", "noviKorisnik");        
            producer.send(topicPS1, txtMsg);
        } finally {
            context.close();
        }
    }
}
