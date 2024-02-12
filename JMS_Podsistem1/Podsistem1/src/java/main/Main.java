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
import podsistem1.Obrada;

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
    
    public static void main(String[] args) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(queue, "zahtevZa='Podsistem1'");
        
        System.out.println("Radi");
        
        while (true) {
            Operacija operacija = consumer.receiveBody(Operacija.class);            
            new Obrada(operacija).start();                      
        }
    }
    
}
