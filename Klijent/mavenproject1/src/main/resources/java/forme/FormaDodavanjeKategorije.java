/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import kontrole.Forma;

/**
 *
 * @author Rale
 */
public class FormaDodavanjeKategorije extends Forma {
    
    private static final String URL = "http://localhost:8080/CentralniServer/api/videoSnimak/dodavanjeKategorije";
    
    private TextField idVS = new TextField();
    private TextField idKat = new TextField();
    
    
    public FormaDodavanjeKategorije() {
        super("Dodaj kategoriju za snimak");
    }
    
    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(new Label("Snimak (id)"), 0, 0);
        glavni.add(idVS, 1, 0);
        glavni.add(new Label("Kategorija (id)"), 0, 1);
        glavni.add(idKat, 1, 1);        
        
        Button dugme = new Button("Dodaj");
        dugme.setOnAction(event -> {
            new Thread(this).start();
        });
        glavni.add(dugme, 0, 2);
    }

    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected Form dohvatiFormu() {
        Form form = new Form();
        form.param("idKat", idKat.getText());        
        
        return form;
    }

    @Override
    public void izvrsi() {
        Client client = ClientBuilder.newClient();
        Response response = client.target(dohvatiURL()).path("{idVS}").resolveTemplate("idVS", idVS.getText())
                .request().post(Entity.form(dohvatiFormu()));
        
        if (response.getStatus() >= 400) {
            prikaziDijalog(response);
        }
    }
    
}
