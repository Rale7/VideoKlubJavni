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
public class Korisnik extends Red {
    
    private final SimpleStringProperty idKor;
    private final SimpleStringProperty ime;
    private final SimpleStringProperty email;
    private final SimpleStringProperty godiste;
    private final SimpleStringProperty pol;
    private final SimpleStringProperty mesto;
    
    public Korisnik(String xmlData) throws GLosXMLFormat {
        idKor = new SimpleStringProperty(dohvatiIzmedju("idKor", xmlData));
        ime = new SimpleStringProperty(dohvatiIzmedju("ime", xmlData));
        email = new SimpleStringProperty(dohvatiIzmedju("email", xmlData));
        godiste = new SimpleStringProperty(dohvatiIzmedju("godiste", xmlData));
        pol = new SimpleStringProperty(dohvatiIzmedju("pol", xmlData));
        String mesto = dohvatiIzmedju("idMes", xmlData);
        this.mesto = new SimpleStringProperty(dohvatiIzmedju("naziv", mesto));
    }
    
    public Integer getIdKor() {
        return Integer.parseInt(idKor.get());
    }
    
    public String getIme() {
        return ime.get();
    }
    
    public String getEmail() {
        return email.get();
    }
    
    public Integer getGodiste() {
        return Integer.parseInt(godiste.get());
    }
    
    public String getPol() {
        return pol.get();
    }
    
    public String getMesto() {
        return mesto.get();
    }
}
