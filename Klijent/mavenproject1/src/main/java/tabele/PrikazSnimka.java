/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabele;

import greske.GLosXMLFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kontrole.KontrolaPrikaza;
import redovi.Red;
import redovi.VideoSnimak;

/**
 *
 * @author Rale
 */
public class PrikazSnimka extends KontrolaPrikaza {

    private static final String URL = "http://localhost:8080/CentralniServer/api/videoSnimak/dohvatanje";
    private static final String SABLON = "<videoSnimak>(.*?)</videoSnimak>";
    
    public PrikazSnimka() {
        super("Snimci");
    }

    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected TableView napraviTabelu() {        
        TableView<VideoSnimak> tv = new TableView<>();
        
        TableColumn<VideoSnimak, Integer> idVS = new TableColumn<>("IdVS");
        idVS.setCellValueFactory(new PropertyValueFactory<>("idVS"));
        
        TableColumn<VideoSnimak, String> naziv = new TableColumn<>("Naziv");
        naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        
        TableColumn<VideoSnimak, LocalTime> trajanje = new TableColumn<>("Trajanje");
        trajanje.setCellValueFactory(new PropertyValueFactory<>("trajanje"));
        
        TableColumn<VideoSnimak, LocalDateTime> datum = new TableColumn<>("Datum");
        datum.setCellFactory(col -> new DatumKonvertor());
        datum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        
        TableColumn<VideoSnimak, Integer> idKor = new TableColumn<>("IdKor");
        idKor.setCellValueFactory(new PropertyValueFactory<>("idKor"));
        
        tv.getColumns().addAll(idVS, naziv, trajanje, datum, idKor);
        
        return tv;
    }

    @Override
    protected Red napraviRed(String xmlData) throws GLosXMLFormat {        
        return new VideoSnimak(xmlData);
    }

    @Override
    protected String dohvatiSablon() {
        return SABLON;
    }
    
}
