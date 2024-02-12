/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package kontrole;

import greske.GLosXMLFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import redovi.Red;

/**
 *
 * @author Rale
 */
public abstract class KontrolaPrikaza extends Kontrola {
    
    protected Pane centralni = new StackPane();
    protected BorderPane borderPane;
    protected FlowPane gornjiPane;
    
    public KontrolaPrikaza(String naziv) {
        super(naziv);
    }
    
    public void izvrsi() {
        try {
            Response response = posaljiZahtev();

            if (response.getStatus() >= 400) {
                prikaziDijalog(response);
            }
            
            String xmlData = response.readEntity(String.class);            
            TableView tv = napraviTabelu();

            Pattern patern = Pattern.compile(dohvatiSablon());
            Matcher matcher = patern.matcher(xmlData);

            while (matcher.find()) {            
                tv.getItems().add(napraviRed(matcher.group(1)));
            } 

            tv.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
            Platform.runLater(() -> {
                centralni.getChildren().clear();
                centralni.getChildren().add(tv);
            });
        } catch (GLosXMLFormat ex) {
            Logger.getLogger(Kontrola.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void dodaj(TabPane tabpane) {
        if (tabpane.getTabs().contains(tab)) {
             tabpane.getSelectionModel().select(tab);
             return;
        }
        
        borderPane = new BorderPane();
        Button dugme = new Button("Prikazi");        
        dugme.setOnAction(event -> {
            new Thread(this).start();
        });
        
        borderPane.setCenter(centralni);
                
        gornjiPane = new FlowPane();
        gornjiPane.setPadding(new Insets(4));
        gornjiPane.setHgap(4);
        gornjiPane.getChildren().add(dugme);        
        borderPane.setTop(gornjiPane);
        
        tab = new Tab(getText());
        tab.setContent(borderPane);
        tabpane.getTabs().add(tab);
        tabpane.getSelectionModel().select(tab);        
    }    
    
    protected abstract String dohvatiURL();
    
    protected abstract TableView napraviTabelu();
    
    protected abstract Red napraviRed(String xmlData) throws GLosXMLFormat;
    
    protected abstract String dohvatiSablon();

    protected Response posaljiZahtev(){
        Client client = ClientBuilder.newClient();
        Response response = client.target(dohvatiURL())
                .request().get(Response.class);
        
        return response;
    }
}
