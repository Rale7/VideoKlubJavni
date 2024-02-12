/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package forme;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.ws.rs.core.Form;
import kontrole.Forma;

/**
 *
 * @author Rale
 */
public class FormaGledanje extends Forma {

    private static final String URL = "http://localhost:8080/CentralniServer/api/gledanje/kreiranje";
    private TextField idKor = new TextField();
    private TextField idVS = new TextField();
    private TextField zapocetoOd = new TextField();
    private TextField sekundiOdgledano = new TextField();
    
    public FormaGledanje() {
        super("Gledaj");
    }    
    
    @Override
    protected void dodajKomponente(GridPane glavni) {
        glavni.add(new Label("Korisnik (id)"), 0, 0);
        glavni.add(idKor, 1, 0);
        glavni.add(new Label("Snimak (id)"), 0, 1);
        glavni.add(idVS, 1, 1);
        glavni.add(new Label("Zapoceto od"), 0, 2);
        glavni.add(zapocetoOd, 1, 2);
        glavni.add(new Label("Sekundi odgledano"), 0, 3);
        glavni.add(sekundiOdgledano, 1, 3);
        
        Button dugme = new Button("Dodaj");
        dugme.setOnAction(event -> {
            new Thread(this).start();
        });
        glavni.add(dugme, 0, 4);
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
        form.param("zapocetoOd", zapocetoOd.getText());
        form.param("sekundiOdgledano", sekundiOdgledano.getText());
        
        return form;
    }
    
}
