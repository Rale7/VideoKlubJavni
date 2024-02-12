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
import redovi.Ocena;
import redovi.Red;

/**
 *
 * @author Rale
 */
public class PrikazOcena  extends KontrolaPrikazParametar {

    private static final String URL = "http://localhost:8080/CentralniServer/api/ocena/dohvatanje";
    private static final String SABLON = "<ocena>(.*?)</ocena>";   

   public PrikazOcena() {
       super("Ocene");
   }
   
    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected TableView napraviTabelu() {
        TableView<Ocena> tv = new TableView<>();
        
        TableColumn<Ocena, Integer> idKor = new TableColumn<>("IdKor");
        idKor.setCellValueFactory(new PropertyValueFactory<>("idKor"));
        
        TableColumn<Ocena, Integer> idVS = new TableColumn<>("IdVS");
        idVS.setCellValueFactory(new PropertyValueFactory<>("idVS"));
        
        TableColumn<Ocena, Integer> ocena = new TableColumn<>("Ocena");
        ocena.setCellValueFactory(new PropertyValueFactory<>("ocena"));
        
        TableColumn<Ocena, LocalDateTime> datum = new TableColumn<>("Datum");
        datum.setCellFactory(col -> new DatumKonvertor());
        datum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        
        tv.getColumns().addAll(idKor, idVS, ocena, datum);
        
        return tv;
    }

    @Override
    protected Red napraviRed(String xmlData) throws GLosXMLFormat {
        return new Ocena(xmlData);
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
