/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redovi;

import greske.GLosXMLFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Rale
 */
public class Mesto extends Red {
     private final SimpleStringProperty idMes;
        private final SimpleStringProperty naziv;
        
        public Mesto(String xmlData) throws GLosXMLFormat {                        
            this.idMes = new SimpleStringProperty(dohvatiIzmedju("idMes", xmlData));                                               
            this.naziv = new SimpleStringProperty(dohvatiIzmedju("naziv", xmlData));
        }
        
        public Integer getIdMes() {
            return Integer.parseInt(idMes.get());
        }
        
        public void setIdMes(Integer idMes) {
            this.idMes.set(idMes + "");
        }
        
        public String getNaziv() {
            return naziv.get();
        }
        
        public void setNaziv(String naziv) {
            this.naziv.set(naziv);
        }
}
