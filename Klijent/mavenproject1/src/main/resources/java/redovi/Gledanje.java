/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redovi;

import greske.GLosXMLFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Rale
 */
public class Gledanje extends Red {
    
    private final SimpleStringProperty idGle;
    private final SimpleStringProperty datum;
    private final SimpleStringProperty zapocetoOd;
    private final SimpleStringProperty sekundiOdgledano;
    private final SimpleStringProperty idKor;
    
    public Gledanje(String xmlData) throws GLosXMLFormat {
        idGle = new SimpleStringProperty(dohvatiIzmedju("idGle", xmlData));
        datum = new SimpleStringProperty(dohvatiIzmedju("datumVremePocetka", xmlData));
        sekundiOdgledano = new SimpleStringProperty(dohvatiIzmedju("sekundiOdlgedano", xmlData));
        zapocetoOd = new SimpleStringProperty(dohvatiIzmedju("zapocetoOd", xmlData));        
        idKor = new SimpleStringProperty(dohvatiIdKor(xmlData));                        
    }
    
    private String dohvatiIdKor(String xmlData) throws GLosXMLFormat {
        String sablon = "<idKor>([0-9]*?)</idKor>"; 
        
        Pattern pattern = Pattern.compile(sablon);
        Matcher matcher = pattern.matcher(xmlData);
        
        if (matcher.find()) {
            return matcher.group(1);
        } else throw new GLosXMLFormat();
    }
    
    public Integer getIdGle() {
        return Integer.parseInt(idGle.get());
    }
    
    public LocalDateTime getDatum() {                
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return LocalDateTime.parse(datum.get(), formatter);        
    }
    
    public Integer getSekundiOdgledano() {
        return Integer.parseInt(sekundiOdgledano.get());
    }
    
    public Integer getZapocetoOd() {
        return Integer.parseInt(zapocetoOd.get());
    }
    
    public Integer getIdKor() {
        return Integer.parseInt(idKor.get());
    }
}
