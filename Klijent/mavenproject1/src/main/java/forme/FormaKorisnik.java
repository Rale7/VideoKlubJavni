/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import java.time.Year;
import javafx.collections.FXCollections;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
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
public class FormaKorisnik extends Forma {

    private static final String URL = "http://localhost:8080/CentralniServer/api/korisnik/kreiranje";
    
    private TextField ime = new TextField();
    private TextField email = new TextField();
    private ChoiceBox<Integer> godiste = new ChoiceBox<>();
    private ChoiceBox<String> pol;
    private TextField idMes = new TextField();
    
    public FormaKorisnik() {
        super("Dodaj korisnika");
        this.pol = new ChoiceBox<>(FXCollections.observableArrayList("M", "Z"));
        int currYear = Year.now().getValue();
        for (int i = 1920; i < currYear; i++) {
            godiste.getItems().add(i);
        }
    }    
    
    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected Form dohvatiFormu() {
        Form form = new Form();
        
        form.param("ime", ime.getText());
        form.param("email", email.getText());
        form.param("godiste", godiste.getValue().toString());
        form.param("pol", pol.getValue());
        form.param("idMes", idMes.getText());
        
        return form;
    }

    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(new Label("Ime"), 0, 0);
        glavni.add(ime, 1, 0);
        glavni.add(new Label("Email"), 0, 1);
        glavni.add(email, 1, 1);
        glavni.add(new Label("Godiste"), 0, 2);
        glavni.add(godiste, 1, 2);
        glavni.add(new Label("Pol"), 0, 3);
        glavni.add(pol, 1, 3);
        glavni.add(new Label("Mesto (id)"), 0, 4);
        glavni.add(idMes, 1, 4);
        
        Button dugme = new Button("Dodaj");
        dugme.setOnAction(event -> {
            new Thread(this).start();
        });
        
        glavni.add(dugme, 0, 5);
    }
    
}
