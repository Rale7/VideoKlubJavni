/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package operacije.podsistem2;

import entiteti.podsistem2.Kategorija;
import entiteti.podsistem2.VideoSnimak;
import operacija.Operacija;
import java.io.Serializable;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Rale
 */
public class MenjanjeNazivaSnimka implements Operacija, Serializable {

    private String noviNaziv;
    private int idVS;
    private int idKor;

    public MenjanjeNazivaSnimka(String noviNaziv, int idVS, int idKor) {
        this.noviNaziv = noviNaziv;
        this.idVS = idVS;
        this.idKor = idKor;
    }        
    
    @Override
    public void izvrsi() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("Podsistem2PU");
        EntityManager em = emf.createEntityManager();
        
        try {
            VideoSnimak videoSnimak = em.find(VideoSnimak.class, idVS);

            if (videoSnimak != null && videoSnimak.getIdKor().getIdKor() == idKor) {
                em.getTransaction().begin();
                videoSnimak.setNaziv(noviNaziv);
                em.getTransaction().commit();
            }
        } finally {
            em.close();
            emf.close();
        }
    }
    
}
