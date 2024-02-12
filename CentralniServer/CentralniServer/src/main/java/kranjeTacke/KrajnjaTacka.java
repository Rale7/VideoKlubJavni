/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import operacija.Operacija;

/**
 *
 * @author Rale
 */
public abstract class KrajnjaTacka {
    
    protected static final String PODSISTEM1 = "Podsistem1";
    protected static final String PODSISTEM2 = "Podsistem2";
    protected static final String PODSISTEM3 = "Podsistem3";
    
    protected static final int TIMEOUT = 2000;
    
    @Resource(lookup = "jms/__defaultConnectionFactory")
    protected ConnectionFactory cf;
    
    @Resource(lookup = "RedZaZahteve")
    protected Queue queue;
    
    protected void posaljiPoruku(Operacija operacija, String cilj) throws JMSException {               
        JMSContext context = cf.createContext();        
        try {            
            posaljiPoruku(operacija, cilj, context);
        } finally {
            context.close();
        }       
    }
    
    protected void posaljiPoruku(Operacija operacija, String cilj, JMSContext context) throws JMSException {
        JMSProducer producer = context.createProducer();
        ObjectMessage objMsg = context.createObjectMessage(operacija);
        objMsg.setStringProperty("zahtevZa", cilj);       
        producer.send(queue, objMsg);
    }
    
    protected boolean validanString(String s) {
        if (s.contains("<")) return false;
        if (s.contains(">")) return false;
        
        return true;
    }
}
