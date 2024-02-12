/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redovi;

import greske.GLosXMLFormat;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Rale
 */
public class VideoSnimak extends Red {
    
    private final SimpleStringProperty idVS;
    private final SimpleStringProperty naziv;
    private final SimpleStringProperty trajanje;
    private final SimpleStringProperty datum;
    private final SimpleStringProperty idKor;

    public VideoSnimak(String xmlData) throws GLosXMLFormat {
        idVS = new SimpleStringProperty(dohvatiIzmedju("idVS", xmlData));
        naziv = new SimpleStringProperty(dohvatiIzmedju("naziv", xmlData));
        trajanje = new SimpleStringProperty(dohvatiIzmedju("trajanje", xmlData));
        datum = new SimpleStringProperty(dohvatiIzmedju("datumIVremePostavljanja", xmlData));
        String korisnik = dohvatiIzmedju("idKor", xmlData);
        idKor = new SimpleStringProperty(dohvatiIzmedju("idKor", korisnik));
    }
    
    public Integer getIdVS() {
        return Integer.parseInt(idVS.get());
    }
    
    public String getNaziv() {
        return naziv.get();
    }
    
    public LocalTime getTrajanje() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return LocalTime.parse(trajanje.get(), formatter);
    }
    
    public LocalDateTime getDatum() {                
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return LocalDateTime.parse(datum.get(), formatter);        
    }
    
    public Integer getIdKor() {
        return Integer.parseInt(idKor.get());
    }
}
