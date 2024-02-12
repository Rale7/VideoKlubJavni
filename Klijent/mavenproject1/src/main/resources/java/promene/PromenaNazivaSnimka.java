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
public class PromenaNazivaSnimka extends Forma {
    
    private static final String URL = "http://localhost:8080/CentralniServer/api/videoSnimak/promenaNaziva";
    
    private TextField idVS = new TextField();
    private TextField idKor = new TextField();
    private TextField noviNaziv = new TextField();
    
    public PromenaNazivaSnimka() {
        super("Promeni naziv snimka");
    }   
    
    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(new Label("Snimak (id)"), 0, 0);
        glavni.add(idVS, 1, 0);
        glavni.add(new Label("Korisnik (id)"), 0, 1);
        glavni.add(idKor, 1, 1);
        glavni.add(new Label("Novi naziv"), 0, 2);
        glavni.add(noviNaziv, 1, 2);
        
        Button dugme = new Button("Promeni");
        dugme.setOnAction(event -> {
            new Thread(this).start();
        });
        glavni.add(dugme, 0, 3);
    }

    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected Form dohvatiFormu() {
        Form form = new Form();
        form.param("idKor", idKor.getText());
        form.param("naziv", noviNaziv.getText());
        
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
