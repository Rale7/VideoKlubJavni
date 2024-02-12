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
import redovi.Pretplata;
import redovi.Red;

/**
 *
 * @author Rale
 */
public class PrikazPretplata extends KontrolaPrikazParametar {

    private static final String URL = "http://localhost:8080/CentralniServer/api/pretplata/dohvatanje";
    private static final String SABLON = "<pretplata>(.*?)</pretplata>"; 
    
    public PrikazPretplata() {
        super("Pretplate");
    }

    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected TableView napraviTabelu() {
        TableView<Pretplata> tv = new TableView<>();
        
        TableColumn<Pretplata, Integer> idPret = new TableColumn<>("IdPret");
        idPret.setCellValueFactory(new PropertyValueFactory<>("idPret"));
        
        TableColumn<Pretplata, LocalDateTime> datum = new TableColumn<>("Datum Pocetka");
        datum.setCellFactory(col -> new DatumKonvertor<>());
        datum.setCellValueFactory(new PropertyValueFactory<>("datum"));
        
        TableColumn<Pretplata, Integer> trenCena = new TableColumn<>("Tren Cena");
        trenCena.setCellValueFactory(new PropertyValueFactory<>("trenCena"));
        
        TableColumn<Pretplata, Integer>cenaPaketa = new TableColumn<>("Cena Paketa");
        cenaPaketa.setCellValueFactory(new PropertyValueFactory<>("cenaPaketa"));
        
        TableColumn<Pretplata, Integer> idKor = new TableColumn<>("IdPak");
        idKor.setCellValueFactory(new PropertyValueFactory<>("idPak"));
        
        tv.getColumns().addAll(idPret, datum, trenCena, cenaPaketa, idKor);
        
        return tv;
    }

    @Override
    protected Red napraviRed(String xmlData) throws GLosXMLFormat {        
        return new Pretplata(xmlData);
    }

    @Override
    protected String dohvatiSablon() {
        return SABLON;
    }

    @Override
    protected String dohvatiOpisUnosa() {
        return "Korisnik (id)";
    }
    
}
