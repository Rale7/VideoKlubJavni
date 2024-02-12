/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redovi;

import greske.GLosXMLFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Rale
 */
public class Pretplata extends Red {
    
    private SimpleStringProperty idPret;
    private SimpleStringProperty datum;
    private SimpleStringProperty trenCena;
    private SimpleStringProperty cenaPaketa;
    private  SimpleStringProperty idPak;
    
    public Pretplata(String xmlData) throws GLosXMLFormat {
        idPret = new SimpleStringProperty(dohvatiIzmedju("idPret", xmlData));
        datum = new SimpleStringProperty(dohvatiIzmedju("datumIVremePocetka", xmlData));
        trenCena = new SimpleStringProperty(dohvatiIzmedju("trenCena", xmlData));
        String paket = dohvatiIzmedju("idPak", xmlData);
        cenaPaketa = new SimpleStringProperty(dohvatiIzmedju("mesecnaCena", paket));        
        idPak = new SimpleStringProperty(dohvatiIzmedju("idPak", paket));
    }
    
    public Integer getIdPret() {
        return Integer.parseInt(idPret.get());
    }
    
    public LocalDateTime getDatum() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return LocalDateTime.parse(datum.get(), formatter);  
    }
    
    public Integer getTrenCena() {
        return Integer.parseInt(trenCena.get());
    }
    
    public Integer getCenaPaketa() {
        return Integer.parseInt(cenaPaketa.get());
    }
    
    public Integer getIdPak() {
        return Integer.parseInt(idPak.get());
    }
}
