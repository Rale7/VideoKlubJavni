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
public class Ocena extends Red {

    private final SimpleStringProperty idKor;
    private final SimpleStringProperty idVS;
    private final SimpleStringProperty ocena;
    private final SimpleStringProperty datum;
    
    public Ocena(String xmlData) throws GLosXMLFormat {
        String pk = dohvatiIzmedju("ocenaPK", xmlData);
        idKor = new SimpleStringProperty(dohvatiIzmedju("idKor", pk));
        idVS = new SimpleStringProperty(dohvatiIzmedju("idVS", pk));
        ocena = new SimpleStringProperty(dohvatiIzmedju("ocenaBroj", xmlData));
        datum = new SimpleStringProperty(dohvatiIzmedju("datumIVremeDavanja", xmlData));
    }

    public Integer getIdKor() {
        return Integer.parseInt(idKor.get());
    }

    public Integer getIdVS() {
        return Integer.parseInt(idVS.get());
    }

    public Integer getOcena() {
        return Integer.parseInt(ocena.get());
    }

    public LocalDateTime getDatum() {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_OFFSET_DATE_TIME;
        return LocalDateTime.parse(datum.get(), formatter);    
    }
    
    
    
}
