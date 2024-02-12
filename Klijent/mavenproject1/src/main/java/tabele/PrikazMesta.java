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
import redovi.Mesto;
import redovi.Red;

/**
 *
 * @author Rale
 */
public class PrikazMesta extends KontrolaPrikaza {    
    
    private static final String URL = "http://localhost:8080/CentralniServer/api/mesto/dohvatanje";
    private static final String SABLON = "<mesto>(.*?)</mesto>";
    
    public PrikazMesta() {
        super("Mesta");        
    }             
    
    protected String dohvatiURL() {
        return URL;
    }
    
    protected TableView napraviTabelu() {
        TableView tv = new TableView();
        
        TableColumn<Mesto, Integer> id = new TableColumn<>("Id");
        id.setCellValueFactory(new PropertyValueFactory<>("idMes"));
        TableColumn<Mesto, String> ime = new TableColumn<>("Naziv");        
        ime.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        
        tv.getColumns().addAll(id, ime);
        
        return tv;
    }
 
    protected String dohvatiSablon() {
        return SABLON;
    }
    
    protected Red napraviRed(String xmlData) throws GLosXMLFormat {
        return new Mesto(xmlData);
    }
}
