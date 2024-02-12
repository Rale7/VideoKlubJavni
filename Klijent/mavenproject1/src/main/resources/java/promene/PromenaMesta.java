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
import javax.ws.rs.core.Response;
import kontrole.Forma;

/**
 *
 * @author Rale
 */
public class PromenaMesta extends Forma {

    private static final String URL = "http://localhost:8080/CentralniServer/api/korisnik/promenaMesta";
    
    private TextField idKor = new TextField();
    private TextField idMes = new TextField();
    
    public PromenaMesta() {
        super("Promeni mesto");
    }   
    
    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(new Label("Korisnik (id)"), 0, 0);
        glavni.add(idKor, 1, 0);
        glavni.add(new Label("Mesto (id)"), 0, 1);
        glavni.add(idMes, 1, 1);
        
        Button dugme = new Button("Promeni");
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
        form.param("idMes", idMes.getText());
        
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
