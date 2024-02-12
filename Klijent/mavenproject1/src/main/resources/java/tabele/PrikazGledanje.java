/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabele;

import greske.GLosXMLFormat;
import java.time.LocalDateTime;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kontrole.KontrolaPrikazParametar;
import redovi.Gledanje;
import redovi.Red;

/**
 *
 * @author Rale
 */
public class PrikazGledanje extends KontrolaPrikazParametar {
    
    private static final String URL = "http://localhost:8080/CentralniServer/api/gledanje/dohvatanje";
    private static final String SABLON = "<gledanje>(.*?)</gledanje>"; 

    public PrikazGledanje() {
        super("Gledanja");
    }

    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected TableView napraviTabelu() {
        TableView<Gledanje> tv = new TableView<>();
        
        TableColumn<Gledanje, Integer> idGle = new TableColumn<>("ID");
        idGle.setCellValueFactory(new PropertyValueFactory<>("idGle"));
        
        TableColumn<Gledanje, LocalDateTime> datum = new TableColumn<>("Datum");
        datum.setCellFactory(col -> new DatumKonvertor<>());
        datum.setCellValueFactory(new PropertyValueFactory<>("datum"));        
        
        TableColumn<Gledanje, Integer> zapocetoOd = new TableColumn<>("Zapoceto Od");
        zapocetoOd.setCellValueFactory(new PropertyValueFactory<>("zapocetoOd"));
        
        TableColumn<Gledanje, Integer> sekundiOdgledano = new TableColumn<>("Sekundi Odgledano");
        sekundiOdgledano.setCellValueFactory(new PropertyValueFactory<>("sekundiOdgledano"));
        
        TableColumn<Gledanje, Integer> idKor = new TableColumn<>("IdKor");
        idKor.setCellValueFactory(new PropertyValueFactory<>("idKor"));
        
        tv.getColumns().addAll(idGle, datum, zapocetoOd, sekundiOdgledano, idKor);
        
        return tv;
    }

    @Override
    protected Red napraviRed(String xmlData) throws GLosXMLFormat {        
        return new Gledanje(xmlData);
    }

    @Override
    protected String dohvatiSablon() {
        return SABLON;
    }

    @Override
    protected String dohvatiOpisUnosa() {
        return "Snimak (id)";
    }
    
    
    
}
