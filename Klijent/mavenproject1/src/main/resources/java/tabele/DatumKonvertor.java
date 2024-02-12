/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tabele;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TableCell;
import javafx.util.StringConverter;
import redovi.VideoSnimak;

/**
 *
 * @author Rale
 */
public class DatumKonvertor<T> extends TableCell<T, LocalDateTime> {

    private static class Konvertor extends StringConverter<LocalDateTime> {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        @Override
        public String toString(LocalDateTime t) {
            return (t != null) ? formatter.format(t) : ""; 
        }

        @Override
        public LocalDateTime fromString(String string) {
            return (string != null && !string.isEmpty()) ? LocalDateTime.parse(string, formatter) : null;
        }       
    }
    
    private Konvertor konvertor = new Konvertor();

    @Override
    protected void updateItem(LocalDateTime t, boolean bln) {
        super.updateItem(t, bln);

        if (bln || t == null) {
            setText(null);
        } else {
            setText(this.konvertor.toString(t));
        }
    }

    
    
    
}
