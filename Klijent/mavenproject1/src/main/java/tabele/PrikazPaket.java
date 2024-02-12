/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabele;

import greske.GLosXMLFormat;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import kontrole.KontrolaPrikaza;
import redovi.Paket;
import redovi.Red;

/**
 *
 * @author Rale
 */
public class PrikazPaket extends KontrolaPrikaza {

    private static final String URL = "http://localhost:8080/CentralniServer/api/paket/dohvatanje";
    private static final String SABLON = "<paket>(.*?)</paket>";  
    
    public PrikazPaket() {
        super("Paketi");
    }    
    
    @Override
    protected String dohvatiURL() {
        return URL;
    }

    @Override
    protected TableView napraviTabelu() {
        TableView<Paket> tv = new TableView();
        
        TableColumn<Paket, Integer> idPak = new TableColumn<>("IdPak");
        idPak.setCellValueFactory(new PropertyValueFactory<>("idPak"));
        
        TableColumn<Paket, Integer> mesecnaCena = new TableColumn<>("MesecnaCena");
        mesecnaCena.setCellValueFactory(new PropertyValueFactory<>("mesecnaCena"));
        
        tv.getColumns().addAll(idPak, mesecnaCena);
        
        return tv;
    }

    @Override
    protected Red napraviRed(String xmlData) throws GLosXMLFormat {
        return new Paket(xmlData);
    }

    @Override
    protected String dohvatiSablon() {
        return SABLON;
    }
    
    
}
