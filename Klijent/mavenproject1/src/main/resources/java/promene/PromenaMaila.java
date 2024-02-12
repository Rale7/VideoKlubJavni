/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package promene;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import kontrole.Forma;

/**
 *
 * @author Rale
 */
public class PromenaMaila extends Forma {

    private static final String URL = "http://localhost:8080/CentralniServer/api/korisnik/promenaEmaila";        

    private TextField idKor = new TextField();
    private TextField noviMail = new TextField();

    public PromenaMaila() {
        super("Promeni mail");
    }
    
    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(new Label("Korisnik (id)"), 0, 0);
        glavni.add(idKor, 1, 0);
        glavni.add(new Label("Novi mail"), 0, 1);
        glavni.add(noviMail, 1, 1);
        
        Button dugme = new Button("Promeni");
        glavni.add(dugme, 0, 2);
        
        dugme.setOnAction(event -> {
            new Thread(this).start();
        });
    }

    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected Form dohvatiFormu() {
        Form form = new Form();
        form.param("email", noviMail.getText());
        
        return form;
    }

    @Override
    public void izvrsi() {
        Client client = ClientBuilder.newClient();
        Response response = client.target(dohvatiURL()).path("{idKor}").resolveTemplate("idKor", idKor.getText())
                .request().post(Entity.form(dohvatiFormu()));
        
        if (response.getStatus() >= 400) {
            prikaziDijalog(response);
        }
    }
    
    
    
    
    
}
