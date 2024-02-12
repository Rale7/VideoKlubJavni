/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontrole;

import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;

/**
 *
 * @author Rale
 */
public abstract class KontrolaPrikazParametar extends KontrolaPrikaza {

    protected TextField poljeZaId;

    public KontrolaPrikazParametar(String naziv) {
        super(naziv);
    }

    @Override
    protected Response posaljiZahtev() {
        String id = poljeZaId.getText();
        
        Client client = ClientBuilder.newClient();
        Response response = client.target(dohvatiURL()).path("{oid}").resolveTemplate("oid", id)
                .request().get(Response.class);
        
        return response;
    }

    @Override
    public void dodaj(TabPane tabpane) {        
        if (tabpane.getTabs().contains(tab)) {
            tabpane.getSelectionModel().select(tab);
            return;
        }        
        super.dodaj(tabpane);       
        poljeZaId = new TextField();        
        gornjiPane.getChildren().add(0,poljeZaId);
        gornjiPane.getChildren().add(0, new Label(dohvatiOpisUnosa()));        
        
    }
    
    protected abstract String dohvatiOpisUnosa();
    
}
