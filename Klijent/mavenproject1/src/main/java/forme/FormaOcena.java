/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.ws.rs.core.Form;
import kontrole.Forma;

/**
 *
 * @author Rale
 */
public class FormaOcena extends Forma {

    private static final String URL = "http://localhost:8080/CentralniServer/api/ocena/kreiranje";
    private TextField idVS = new TextField();
    private TextField idKor = new TextField();
    private ChoiceBox<Integer> ocena = new ChoiceBox<>();
    
    
    public FormaOcena() {
        super("Oceni");
        for (int i = 1; i < 6; i++) {
            ocena.getItems().add(i);
        }
    }

    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(new Label("Snimak (id)"), 0, 0);
        glavni.add(idVS, 1, 0);
        glavni.add(new Label("Korisnik (id)"), 0, 1);
        glavni.add(idKor, 1, 1);
        glavni.add(new Label("Ocena"), 0, 2);
        glavni.add(ocena, 1, 2);
        
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
        form.param("idKor", idKor.getText());
        form.param("idVS", idVS.getText());
        form.param("ocena", ocena.getValue().toString());
        
        return form;
    }
    
}
