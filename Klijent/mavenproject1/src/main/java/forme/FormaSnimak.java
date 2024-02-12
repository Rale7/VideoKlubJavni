/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.ws.rs.core.Form;
import kontrole.Forma;

/**
 *
 * @author Rale
 */
public class FormaSnimak extends Forma {
    
    private static final String URL = "http://localhost:8080/CentralniServer/api/videoSnimak/kreiranje";
    private TextField nazivSnimka = new TextField();
    private TextField trajanje = new TextField();
    private TextField idKor = new TextField();
    
    
    public FormaSnimak() {
        super("Dodaj snimak");
    }

    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(nazivSnimka, 1, 0);
        glavni.add(new Label("Naziv snimka"), 0, 0);
        glavni.add(new Label("Trajanje"), 0, 1);
        glavni.add(trajanje, 1, 1);
        glavni.add(new Label("Korisnik (id)"), 0, 2);
        glavni.add(idKor, 1, 2);
        
        Button dugme = new Button("Dodaj");
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
        form.param("naziv", nazivSnimka.getText());
        form.param("trajanje", trajanje.getText());
        form.param("idKor", idKor.getText());
        
        return form;
    }
    
}
