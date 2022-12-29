/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.io.Serializable;

/**
 *
 * @author franc
 */
public class Bola extends Personagem implements Serializable {
    
    public Bola (String sNomeImagePNG, boolean type ) {
        super(sNomeImagePNG);
        this.bDestruivel = type;
        this.bEmpurravel = true;
        this.bGravidade = true;
        this.bTransponivel = true;
    }

    @Override
    public void autoDesenho() {
        super.autoDesenho();
    }  
}
