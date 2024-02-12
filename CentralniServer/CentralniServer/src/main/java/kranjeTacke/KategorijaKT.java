/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import entiteti.podsistem2.Kategorija;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import operacije.podsistem2.DohvatanjeKategorijaZaSnimak;
import operacije.podsistem2.DohvatanjeSvihKategorija;
import operacije.podsistem2.KreiranjeKategorije;

/**
 *
 * @author Rale
 */
@Path("kategorija")
public class KategorijaKT extends KrajnjaTacka {       
    
    @Resource(lookup = "Podsistem2Topic")
    private Topic topicPS2;
    
    @POST
    @Path("kreiranje")
    public Response kreirajKategoriju(@FormParam("naziv") String naziv) {        
        if (!validanString(naziv)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        try {
            posaljiPoruku(new KreiranjeKategorije(naziv), PODSISTEM2);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (JMSException ex) {
            Logger.getLogger(KategorijaKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
    
    @GET
    @Path("dohvatanje")
    public Response dohvatiKategorije(@QueryParam("idVS") Integer idVS) {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(topicPS2, "((tip='sveKategorijeZaSnimak') AND (idVS=" + idVS + ")) OR (tip='sveKategorije')");
        try {    
            if (idVS != null) {
                posaljiPoruku(new DohvatanjeKategorijaZaSnimak(idVS), PODSISTEM2, context);
            } else {
                posaljiPoruku(new DohvatanjeSvihKategorija(), PODSISTEM2, context);
            }                        
            
            ArrayList<Kategorija> kategorije = consumer.receiveBody(ArrayList.class, TIMEOUT);
            
            if (kategorije == null) {
                return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
            
            return Response.ok().entity(new GenericEntity<ArrayList<Kategorija>>(kategorije){}).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(KategorijaKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
        } finally {
            consumer.close();
            context.close();
        }
    }            
}
