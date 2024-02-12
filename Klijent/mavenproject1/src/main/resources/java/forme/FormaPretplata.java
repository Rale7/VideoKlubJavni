/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.ws.rs.core.Form;
import kontrole.Forma;

/**
 *
 * @author Rale
 */
public class FormaPretplata extends Forma {

    private static final String URL = "http://localhost:8080/CentralniServer/api/pretplata/kreiranje";
    private TextField idKor = new TextField();
    private TextField idPak = new TextField();
    
    public FormaPretplata() {
        super("Pretplati se");
    }

    @Override
    protected void dodajKomponente(GridPane glavni) {        
        glavni.add(new Label("Korisnik (id)"), 0, 0);
        glavni.add(idKor, 1, 0);
        glavni.add(new Label("Paket (id)"), 0, 1);
        glavni.add(idPak, 1, 1);       
        
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
        form.param("idKor", idKor.getText());
        form.param("idPak", idPak.getText());
        
        return form;
    }
    
}
