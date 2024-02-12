/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kranjeTacke;

import entiteti.podsistem1.Korisnik;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import operacije.podsistem1.DohvatanjeSvihKorisnika;
import operacije.podsistem1.KreiranjeKorisnika;
import operacije.podsistem1.PromenaEmaila;
import operacije.podsistem1.PromenaMesta;

/** 
 *
 * @author Rale
 */
@Path("korisnik")
public class KorisnikKT extends KrajnjaTacka {                
    
    @Resource(lookup = "Podsistem1Topic")
    private Topic topicPS1;
    
    @POST
    @Path("kreiranje")
    public Response kreirajKorisnika(@FormParam("ime") String ime,
            @FormParam("email") String mail,
            @FormParam("godiste") int godiste,
            @FormParam("pol") String pol,
            @FormParam("idMes") int idMes) {
        
        if (!validanString(ime) || !validanString(pol) || !validanString(mail)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        pol = pol.toUpperCase();
        if (!(pol.equals("M") || pol.equals("Z"))) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        try {            
            posaljiPoruku(new KreiranjeKorisnika(ime, mail, godiste, pol.charAt(0), idMes), PODSISTEM1);
            
            return Response.status(Response.Status.CREATED).build();
        } catch (JMSException ex) {
            Logger.getLogger(KorisnikKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
    
    
    @POST
    @Path("promenaEmaila/{idKor}")
    public Response promeniMail(@PathParam("idKor") int idKor, @FormParam("email") String noviMail) {
        if (!validanString(noviMail)) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        
        try {            
            posaljiPoruku(new PromenaEmaila(noviMail, idKor), PODSISTEM1);
            
            return Response.ok().build();
        } catch (JMSException ex) {
            Logger.getLogger(KorisnikKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
    
    @POST
    @Path("promenaMesta/{idKor}")
    public Response promeniMesto(@PathParam("idKor") int idKor, @FormParam("idMes") int idMes) {
        try {                        
            posaljiPoruku(new PromenaMesta(idMes, idKor), PODSISTEM1);
            
            return Response.ok().build();
        } catch (JMSException ex) {
            Logger.getLogger(KorisnikKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_GATEWAY).build();
        }                
    }
    
    @GET
    @Path("dohvatanje")
    public Response dohvatiKorisnike() {
        JMSContext context = cf.createContext();
        JMSConsumer consumer = context.createConsumer(topicPS1, "tip='odgovorSviKorisnici'");
        try {                        
            posaljiPoruku(new DohvatanjeSvihKorisnika(), PODSISTEM1, context);
            
            ArrayList<Korisnik> odgovor = consumer.receiveBody(ArrayList.class, TIMEOUT);
            
            if (odgovor == null) {
                 return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
            }
            
            return Response.ok().entity(new GenericEntity<List<Korisnik>>(odgovor){}).build();
        } catch (JMSException ex) {
            Logger.getLogger(KorisnikKT.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.GATEWAY_TIMEOUT).build();
        } finally {
            consumer.close();
            context.close();
        }
    }
        
}
