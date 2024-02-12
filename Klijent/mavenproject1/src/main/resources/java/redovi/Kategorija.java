/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redovi;

import greske.GLosXMLFormat;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Rale
 */
public class Kategorija extends Red {
    
    private final SimpleStringProperty idKat;
    private final SimpleStringProperty naziv;
    
    public Kategorija(String xmlData) throws GLosXMLFormat {
        idKat = new SimpleStringProperty(dohvatiIzmedju("idKat", xmlData));
        naziv = new SimpleStringProperty(dohvatiIzmedju("naziv", xmlData));        
    }
    
    public Integer getIdKat() {
        return Integer.parseInt(idKat.get());
    }
    
    public String getNaziv() {
        return naziv.get();
    }
    
}
