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
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import redovi.Mesto;
import redovi.Red;

/**
 *
 * @author Rale
 */
public class Kontrola extends Label implements Runnable {       
    
    protected Tab tab;
    
    public Kontrola(String tekst) {
        super(tekst);
    }
    
    public void izvrsi() {
        
    }
    
    public void dodaj(TabPane pane) {
        
    }

    @Override
    public void run() {        
        izvrsi();        
    }
    
    protected void prikaziDijalog(Response response) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.getDialogPane().getStylesheets().add(getClass()
                    .getResource("/com/mycompany/mavenproject1/iskacuciProzor.css").toExternalForm());
            alert.setHeaderText("ERROR " + response.getStatus());            
            alert.setContentText(response.getStatusInfo().getReasonPhrase());            
            alert.showAndWait();
        });
    }
}
