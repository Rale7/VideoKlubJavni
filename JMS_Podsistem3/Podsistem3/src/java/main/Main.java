/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package main;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Topic;
import operacija.Operacija;
import podsistem3.BrisanjeVideoSnimka;
import podsistem3.DodavanjeKorisnika;
import podsistem3.DodavanjeVideoSnimka;
import podsistem3.Obrada;

/**
 *
 * @author Rale
 */
public class Main {

    @Resource(lookup = "jms/__defaultConnectionFactory")
    public static ConnectionFactory cf;
    
    @Resource(lookup = "RedZaZahteve")
    public static Queue queue;
    
    @Resource(lookup = "Podsistem1Topic")
    public static Topic topicPS1;
    
    @Resource(lookup = "Podsistem2Topic")
    public static Topic topicPS2;
    
    @Resource(lookup = "Podsistem3Topic")
    public static Topic topicPS3;
    
    public static void main(String[] args) {
        new DodavanjeKorisnika().start();
        new DodavanjeVideoSnimka().start();
        new BrisanjeVideoSnimka().start();
        
        System.out.println("Radi");
        
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue, "zahtevZa='Podsistem3'");
        
        while (true) {            
            Operacija operacija = consumer.receiveBody(Operacija.class);
            new Obrada(operacija).start();
        }
    }
    
}
