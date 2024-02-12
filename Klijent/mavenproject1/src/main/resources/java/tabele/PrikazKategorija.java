/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabele;

import greske.GLosXMLFormat;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import kontrole.KontrolaPrikaza;
import redovi.Kategorija;
import redovi.Red;

/**
 *
 * @author Rale
 */
public class PrikazKategorija extends KontrolaPrikaza {

    private static final String URL = "http://localhost:8080/CentralniServer/api/kategorija/dohvatanje";
    private static final String SABLON = "<kategorija>(.*?)</kategorija>";   
    
    public PrikazKategorija() {
        super("Kategorije");
    }
    
    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected TableView napraviTabelu() {
        TableView tv = new TableView();
        
        TableColumn<Kategorija, Integer> idKat = new TableColumn<>("Id");
        idKat.setCellValueFactory(new PropertyValueFactory<>("idKat"));
        
        TableColumn<Kategorija, String> naziv = new TableColumn<>("Naziv");
        naziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        
        tv.getColumns().addAll(idKat, naziv);
        
        return tv;
    }

    @Override
    protected Red napraviRed(String xmlData) throws GLosXMLFormat {
        return new Kategorija(xmlData);
    }

    @Override
    protected String dohvatiSablon() {
        return SABLON;
        
    }            
}
