/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import entiteti.podsistem3.Gledanje;
import java.util.ArrayList;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import operacije.podsistem3.DohvatanjeGledanja;
import operacije.podsistem3.KreiranjeGledanja;

/**
 *
 * @author Rale
 */
@Path("gledanje")
public class GledanjeKT extends KrajnjaTacka {
    
    @Resource(lookup = "Podsistem3Topic")
    private Topic topicPS3;
    
    @POST
    @Path("kreiranje")
    public Response kreirajGledanje(@FormParam("idKor") int idKor,
            @FormParam("idVS") int idVS,
            @FormParam("zapocetoOd") int zapocetoOd,
            @FormParam("sekundiOdgledano") int sekundiOdgledano) {        
        try {
            posaljiPoruku(new KreiranjeGledanja(idKor, idVS, zapocetoOd, sekundiOdgledano), PODSISTEM3);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (JMSException ex) {
            Logger.getLogger(GledanjeKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }        
    }
    
    @GET
    @Path("dohvatanje/{idVS}")
    public Response dohvatiGledanja(@PathParam("idVS") int idVS) {        
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(topicPS3, "(tip='dohvatanjeGledanjaSnimak') AND (idVS=" + idVS + ")");
        try {            
            posaljiPoruku(new DohvatanjeGledanja(idVS), PODSISTEM3, context);
            
            ArrayList<Gledanje> gledanja = consumer.receiveBody(ArrayList.class, TIMEOUT);
            
            if (gledanja == null) {
                return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
            
            return Response.ok().entity(new GenericEntity<ArrayList<Gledanje>>(gledanja){}).build();
        } catch (JMSException ex) {
            Logger.getLogger(GledanjeKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
        } finally {
            consumer.close();
            context.close();
        }
    }
    
}
