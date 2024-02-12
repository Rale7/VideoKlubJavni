/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabele;

import greske.GLosXMLFormat;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kontrole.KontrolaPrikaza;
import redovi.Korisnik;
import redovi.Red;

/**
 *
 * @author Rale
 */
public class PrikazKorisnika extends KontrolaPrikaza {

    private static final String URL = "http://localhost:8080/CentralniServer/api/korisnik/dohvatanje";
    private static final String SABLON = "<korisnik>(.*?)</korisnik>";
    
    public PrikazKorisnika() {
        super("Korisnici");
    }

    @Override
    protected Red napraviRed(String xmlData) throws GLosXMLFormat {
        return new Korisnik(xmlData);
    }

    @Override
    protected String dohvatiSablon() {
        return SABLON;
    }

    @Override
    protected TableView napraviTabelu() {
        TableView tv = new TableView();
        
        TableColumn<Korisnik, Integer> idKor = new TableColumn<>("IdKor");
        idKor.setCellValueFactory(new PropertyValueFactory<>("idKor"));
        
        TableColumn<Korisnik, String> ime = new TableColumn<>("Ime");
        ime.setCellValueFactory(new PropertyValueFactory<>("ime"));
        
        TableColumn<Korisnik, String> email = new TableColumn<>("Email");
        email.setCellValueFactory(new PropertyValueFactory<>("email"));
        
        TableColumn<Korisnik, Integer> godiste = new TableColumn<>("Godiste");
        godiste.setCellValueFactory(new PropertyValueFactory<>("godiste"));
        
        TableColumn<Korisnik, String> pol = new TableColumn<>("Pol");
        pol.setCellValueFactory(new PropertyValueFactory<>("pol"));
        
        TableColumn<Korisnik, String> mesto = new TableColumn<>("Mesto");
        mesto.setCellValueFactory(new PropertyValueFactory<>("mesto"));
        
        tv.getColumns().addAll(idKor, ime, email, godiste, pol, mesto);
        
        return tv;
    }

    @Override
    protected String dohvatiURL() {
        return URL;
    }

    
    
    
    
}
