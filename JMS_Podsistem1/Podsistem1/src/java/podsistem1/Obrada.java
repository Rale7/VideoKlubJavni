/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package podsistem1;

import operacija.Operacija;

/**
 *
 * @author Rale
 */
public class Obrada extends Thread {

    Operacija operacija;

    public Obrada(Operacija operacija) {
        this.operacija = operacija;
    }
    
    
    
    @Override
    public void run() {
        operacija.izvrsi();
    }
    
    
    
}
