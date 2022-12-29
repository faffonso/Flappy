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
public class Chegada extends Personagem implements Serializable {
    
    public Chegada (String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false;
    }
}
