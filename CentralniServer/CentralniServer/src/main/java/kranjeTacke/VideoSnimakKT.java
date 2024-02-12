/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import entiteti.podsistem2.VideoSnimak;
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
import operacije.podsistem2.BrisanjeSnimka;
import operacije.podsistem2.DodavanjeKategorije;
import operacije.podsistem2.DohvatanjeSvihSnimaka;
import operacije.podsistem2.KreiranjeSnimka;
import operacije.podsistem2.MenjanjeNazivaSnimka;

/**
 *
 * @author Rale
 */
@Path("videoSnimak")
public class VideoSnimakKT extends KrajnjaTacka{      
    
    @Resource(lookup = "Podsistem2Topic")
    private Topic topicPS2;
    
    @POST
    @Path("kreiranje")
    public Response kreirajSnimak(@FormParam("naziv") String naziv,
            @FormParam("trajanje") int trajanje,            
            @FormParam("idKor") int  idKor) {
        
        if (!validanString(naziv)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        try {
            posaljiPoruku(new KreiranjeSnimka(idKor, naziv, trajanje), PODSISTEM2);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (JMSException ex) {
            Logger.getLogger(VideoSnimakKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
    
    @POST
    @Path("promenaNaziva/{idVS}")
    public Response promeniNaziv(@PathParam("idVS") int idVS, 
            @FormParam("idKor") int idKor,
            @FormParam("naziv") String noviNaziv) {
        
        if (!validanString(noviNaziv)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        try {
            posaljiPoruku(new MenjanjeNazivaSnimka(noviNaziv, idVS, idKor), PODSISTEM2);
            
            return Response.ok().build();
        } catch (JMSException ex) {
            Logger.getLogger(VideoSnimakKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
 
    @POST
    @Path("dodavanjeKategorije/{idVS}")
    public Response dodajKategoriju(@PathParam("idVS") int idVS, 
            @FormParam("idKat") int idKat) {
        
        try {
            posaljiPoruku(new DodavanjeKategorije(idKat, idVS), PODSISTEM2);
            
            return Response.ok().build();
        } catch (JMSException ex) {
            Logger.getLogger(VideoSnimakKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
    
    @GET
    @Path("dohvatanje")
    public Response dohvatiSnimke() {        
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(topicPS2, "tip='sviSnimci'");
        try {            
            posaljiPoruku(new DohvatanjeSvihSnimaka(), PODSISTEM2, context);
            
            ArrayList<VideoSnimak> snimci = consumer.receiveBody(ArrayList.class, TIMEOUT);
            
            if (snimci == null) {
                return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
            
            return Response.ok().entity(new GenericEntity<ArrayList<VideoSnimak>>(snimci){}).build();
        } catch (JMSException ex) {
            Logger.getLogger(VideoSnimakKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.NOT_FOUND).build();
        } finally {
            consumer.close();
            context.close();
        }
    }

    @DELETE
    @Path("brisanje/{idKor}/{idVS}/")
    public Response obrisiSnimak(@PathParam("idVS") int idVS, @PathParam("idKor") int idKor) {
        try {
            posaljiPoruku(new BrisanjeSnimka(idKor, idVS), PODSISTEM2);
            
            return Response.ok().build();
        } catch (JMSException ex) {
            Logger.getLogger(VideoSnimakKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
}
