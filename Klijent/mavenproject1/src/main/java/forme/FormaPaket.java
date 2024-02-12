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
public class FormaPaket extends Forma {

    private static final String URL = "http://localhost:8080/CentralniServer/api/paket/kreiranje";
    private TextField mesecnaCena = new TextField();
    
    public FormaPaket() {
        super("Dodaj paket");
    }

    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(mesecnaCena, 1, 0);
        glavni.add(new Label("Mesecna cena"), 0, 0);
        
        Button dugme = new Button("Dodaj");
        dugme.setOnAction(event -> {
            new Thread(this).start();
        });
        
        glavni.add(dugme, 0, 1);
    }    
    
    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected Form dohvatiFormu() {
        Form form = new Form();
        form.param("mesecnaCena", mesecnaCena.getText());
        
        return form;
    }
    
}
