/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import entiteti.podsistem3.Paket;
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
import operacije.podsistem3.DohvatanjePaketa;
import operacije.podsistem3.KreiranjePaketa;
import operacije.podsistem3.PromenaMesecneCene;

/**
 *
 * @author Rale
 */
@Path("paket")
public class PaketKT extends KrajnjaTacka {
    
    @Resource(lookup = "Podsistem3Topic")
    private Topic topicPS3;
    
    @POST
    @Path("kreiranje")
    public Response kreirajPaket(@FormParam("mesecnaCena")int mesecnaCena) {
        
        try {
            posaljiPoruku(new KreiranjePaketa(mesecnaCena), PODSISTEM3);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (JMSException ex) {
            Logger.getLogger(PaketKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
 
    @POST
    @Path("promenaCene/{idPak}")
    public Response promeniMesecnuCenu(@PathParam("idPak") int idPak, @FormParam("mesecnaCena") int novaMesecnaCena) {
        try {
            posaljiPoruku(new PromenaMesecneCene(novaMesecnaCena, idPak), PODSISTEM3);
            
            return Response.status(Response.Status.OK).build();
        } catch (JMSException ex) {
            Logger.getLogger(PaketKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
    
    @GET
    @Path("dohvatanje")
    public Response dohvatiPakete() {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(topicPS3, "tip='dohvatanjeSvihPaketa'");
        try {            
            
            posaljiPoruku(new DohvatanjePaketa(), PODSISTEM3, context);
            
            ArrayList<Paket> paketi = consumer.receiveBody(ArrayList.class, TIMEOUT);
            
            if (paketi == null) {
                return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
            
            return Response.ok().entity(new GenericEntity<ArrayList<Paket>>(paketi){}).build();
        } catch (JMSException ex) {
            Logger.getLogger(PaketKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
        } finally {
            consumer.close();
            context.close();
        }       
    }
}
