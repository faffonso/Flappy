/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package flappy;

import Controler.Tela;

/**
 *
 * @author franc
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                int fase = 0;
                    Tela tTela = new Tela();
                    tTela.setVisible(true);
                    tTela.createBufferStrategy(2);
                    tTela.go();
            }
        });
    }
    
}
