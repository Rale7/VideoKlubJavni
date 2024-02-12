/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package redovi;

import greske.GLosXMLFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Rale
 */
public abstract class Red {
    
    protected String dohvatiIzmedju(String del, String xmlData) throws GLosXMLFormat {
        String sablon = "<" + del + ">(.*)</" + del + ">"; 
        
        Pattern pattern = Pattern.compile(sablon);
        Matcher matcher = pattern.matcher(xmlData);
        
        if (matcher.find()) {
            return matcher.group(1);
        } else throw new GLosXMLFormat();
    }
    
}
