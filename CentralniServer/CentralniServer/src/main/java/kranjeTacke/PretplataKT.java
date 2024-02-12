/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import entiteti.podsistem3.Pretplata;
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
import operacije.podsistem3.DohvatanjePretplata;
import operacije.podsistem3.KreiranjePretplate;

/**
 *
 * @author Rale
 */
@Path("pretplata")
public class PretplataKT extends KrajnjaTacka {
    
    @Resource(lookup = "Podsistem3Topic")
    private Topic topicPS3;
    
    @POST
    @Path("kreiranje")
    public Response kreiranjePretplate(@FormParam("idKor") int idKor, @FormParam("idPak") int idPak) {
        try {
            posaljiPoruku(new KreiranjePretplate(idKor, idPak), PODSISTEM3);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (JMSException ex) {
            Logger.getLogger(PretplataKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
    
    @GET
    @Path("dohvatanje/{idKor}")
    public Response dohvatiPretplate(@PathParam("idKor")  int idKor) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(topicPS3, "(tip='dohvatanjePretplataKorisnik') AND (idKor=" + idKor + ")");
        try {            
            posaljiPoruku(new DohvatanjePretplata(idKor), PODSISTEM3, context);
            
            ArrayList<Pretplata> pretplate = consumer.receiveBody(ArrayList.class, TIMEOUT);
            
            if (pretplate == null) {
                return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
            
            return Response.ok().entity(new GenericEntity<ArrayList<Pretplata>>(pretplate){}).build();
        } catch (JMSException ex) {
            Logger.getLogger(PretplataKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        } finally {
            consumer.close();
            context.close();
        }
        
    }
    
}
