/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redovi;

import greske.GLosXMLFormat;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Rale
 */
public class Paket extends Red {
    
    private final SimpleStringProperty idPak;
    private final SimpleStringProperty mesecnaCena;
    
    public Paket(String xmlData) throws GLosXMLFormat {
        idPak = new SimpleStringProperty(dohvatiIzmedju("idPak", xmlData));
        mesecnaCena = new SimpleStringProperty(dohvatiIzmedju("mesecnaCena", xmlData));
    }
    
    public Integer getIdPak() {
        return Integer.parseInt(idPak.get());
    }
    
    public Integer getMesecnaCena() {
        return Integer.parseInt(mesecnaCena.get());
    }
    
}
