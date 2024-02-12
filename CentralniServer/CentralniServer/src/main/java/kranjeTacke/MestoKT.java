/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import entiteti.podsistem1.Mesto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import operacije.podsistem1.DohvatanjeSvihMesta;
import operacije.podsistem1.KreiranjeMesta;

/**
 *
 * @author Rale
 */
@Path("mesto")
public class MestoKT extends KrajnjaTacka {       
    
    @Resource(lookup = "Podsistem1Topic")
    private Topic topicPS1;
    
    @POST
    @Path("kreiranje")
    public Response kreirajMesto(@FormParam("naziv") String naziv) {
        if (!validanString(naziv)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        try {
            posaljiPoruku(new KreiranjeMesta(naziv), PODSISTEM1);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (JMSException ex) {
            Logger.getLogger(MestoKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }        
    }
    
    @GET
    @Path("dohvatanje")
    public Response dohvatiMesta() {        
        JMSContext context = cf.createContext();            
        JMSConsumer consumer = context.createConsumer(topicPS1, "tip='odgovorSvaMesta'"); 
        try {            
            posaljiPoruku(new DohvatanjeSvihMesta(), PODSISTEM1, context);
            
            ArrayList<Mesto> odgovor = consumer.receiveBody(ArrayList.class, TIMEOUT);
            
            if (odgovor == null) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }                                                                       
            
            return Response.ok().entity(new GenericEntity<List<Mesto>>(odgovor){}).build();
        } catch (JMSException ex) {
            Logger.getLogger(MestoKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
        } finally {
            consumer.close();
            context.close();
        }
    }
    
}
