/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontrole;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import kontrole.Forma;

/**
 *
 * @author Rale
 */
public abstract class BrisanjeForma extends Forma {

    protected TextField idKor = new TextField();
    protected TextField idVS = new TextField();

    public BrisanjeForma(String tekst) {
        super(tekst);
    }
    
    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(new Label("Korisnik (id)"), 0, 0);
        glavni.add(idKor, 1, 0);
        glavni.add(new Label("Snimak (id)"), 0, 1);
        glavni.add(idVS, 1, 1);
        
        Button dugme = new Button("Obrisi");
        glavni.add(dugme, 0, 2);
        
        dugme.setOnAction(event -> {
            new Thread(this).start();
        });
    }

    @Override
    public void izvrsi() {
        Client client = ClientBuilder.newClient();
        
        Response response = client.target(dohvatiURL())
                .path("{idKor}").resolveTemplate("idKor", dohvatiIdKor())
                .path("{idVS}").resolveTemplate("idVS", dohvatiIdVS()).request().delete();
        
        if (response.getStatus() >= 400) {
            prikaziDijalog(response);
        }
    }
    
    protected abstract String dohvatiIdKor();
    
    protected abstract String dohvatiIdVS();

    @Override
    protected Form dohvatiFormu() {
        return null;
    }

    
    
}
