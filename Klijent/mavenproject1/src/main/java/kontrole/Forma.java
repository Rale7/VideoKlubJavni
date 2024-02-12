/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontrole;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;

/**
 *
 * @author Rale
 */
public abstract class Forma extends Kontrola {
    
    public Forma(String tekst) {
        super(tekst);
    }
    
    @Override
    public void izvrsi() {
        Client client = ClientBuilder.newClient();        
        
        Response response = client.target(dohvatiURL()).request().post(Entity.form(dohvatiFormu()));        
        if (response.getStatus() >= 400 ) {
            prikaziDijalog(response);
        }
    }

    @Override
    public void dodaj(TabPane pane) {
        if (pane.getTabs().contains(tab)) {
            pane.getSelectionModel().select(tab);
            return;
        }
        
        GridPane glavni = new GridPane();
        glavni.getStyleClass().add("grid");
        
        dodajKomponente(glavni);
                
        tab = new Tab(getText());
        tab.setContent(glavni);
        pane.getTabs().add(tab);
        pane.getSelectionModel().select(tab);
    }
    
    protected abstract void dodajKomponente(GridPane glavni);
    
    protected abstract String dohvatiURL();
    
    protected abstract Form dohvatiFormu();
}
