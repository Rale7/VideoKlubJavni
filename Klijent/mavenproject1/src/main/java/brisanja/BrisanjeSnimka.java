/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package brisanja;

import kontrole.BrisanjeForma;

/**
 *
 * @author Rale
 */
public class BrisanjeSnimka extends BrisanjeForma {

    private static final String URL = "http://localhost:8080/CentralniServer/api/videoSnimak/brisanje";
    
    public BrisanjeSnimka() {
        super("Obrisi snimak");
    }

    @Override
    protected String dohvatiIdKor() {
        return idKor.getText();
    }

    @Override
    protected String dohvatiIdVS() {
        return idVS.getText();
    }

    @Override
    protected String dohvatiURL() {
        return URL;
    }
    
}
