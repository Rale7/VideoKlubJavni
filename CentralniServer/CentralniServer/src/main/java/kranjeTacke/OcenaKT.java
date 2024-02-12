/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import entiteti.podsistem3.Ocena;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Topic;
import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import operacije.podsistem3.BrisanjeOcene;
import operacije.podsistem3.DohvatanjeOcena;
import operacije.podsistem3.KreiranjeOcene;

/**
 *
 * @author Rale
 */
@Path("ocena")
public class OcenaKT extends KrajnjaTacka {
    
    @Resource(lookup = "Podsistem3Topic")
    private Topic topicPS3;
    
    @POST
    @Path("kreiranje")
    public Response kreirajOcenu(@FormParam("idKor") int idKor,
            @FormParam("idVS") int idVS,
            @FormParam("ocena") int ocena) {
        try {
            if (ocena < 1 || ocena > 5) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            
            posaljiPoruku(new KreiranjeOcene(ocena, idKor, idVS), PODSISTEM3);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (JMSException ex) {
            Logger.getLogger(OcenaKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
    }
    
    @POST
    @Path("promeni")
    public Response promeniOcenu(@FormParam("idKor") int idKor,
            @FormParam("idVS") int idVS,
            @FormParam("ocena") int ocena) {
        return kreirajOcenu(idKor, idVS, ocena);
    }
    
    @DELETE
    @Path("brisanje/{idKor}/{idVS}")
    public Response obrisiOcenu(@PathParam("idKor") int idKor,
            @PathParam("idVS") int idVS) {
        try {
            posaljiPoruku(new BrisanjeOcene(idKor, idVS), PODSISTEM3);
            
            return Response.ok().build();
        } catch (JMSException ex) {
            Logger.getLogger(OcenaKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }
    }
    
    @GET
    @Path("dohvatanje/{idVS}")
    public Response dohvatiOcenu(@PathParam("idVS") int idVS) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(topicPS3, "(tip='dohvatanjeOcenaVideo') AND (idVS="+idVS+")");
        try {            
            posaljiPoruku(new DohvatanjeOcena(idVS), PODSISTEM3, context);
            
            ArrayList<Ocena> ocene = consumer.receiveBody(ArrayList.class, TIMEOUT);
            
            if (ocene == null) {
                return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
            
            return Response.ok().entity(new GenericEntity<ArrayList<Ocena>>(ocene){}).build();
        } catch (JMSException ex) {
            Logger.getLogger(OcenaKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
        } finally {
            consumer.close();
            context.close();
        }
    }
    
}
